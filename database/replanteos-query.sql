/* 
Dado un concepto, obtener todos los conceptos relacionados (por cualquier tipo de relación), 
hasta una profundidad indicada siendo 0 solamente el concepto origen, 1 todos los relacionados 
con él, 2 todos los relacionados con el nivel 1 y así
*/
(ORIGINAL) 
select from (traverse both() from (select from Concepto where Nombre = 'PARADIGMA') while $depth <= 1)

(ALTERNATIVA 1)
select * FROM (MATCH {class: Concepto, as: concepto, where: (Nombre like 'PARADIGMA')}-->{class: Concepto, as: relacionado} RETURN concepto, expand(relacionado))

(ALTERNATIVA 2)
select FROM (
  MATCH {class: Concepto, where: (Nombre = 'PARADIGMA')}.out()
        {class: Concepto, as: dest, while: ($depth < 1)} RETURN expand(dest)
)

(LA MEJOR HASTA EL MOMENTO)
select FROM ( 
  MATCH {class: Concepto, where: (Nombre = 'PARADIGMA')}.out() 
        {class: Concepto, as: dest, while: ($depth < 1)} 
  RETURN 
    dest.@rid as idConcepto,
    dest.Nombre as Concepto,  
    dest.out() as idConceptosRelacionados, 
    dest.outE().@class as Relaciones, 
    dest.out().Nombre as ConceptosRelacionados
)

/*
Usando solamente MATCH y expandiendo para poder graficar.
*/
match {class: Concepto, as: raiz, where: (Nombre = 'POO')}.out() {class: Concepto, as: hijo, while: ($depth < 4)} RETURN expand(hijo)

/*
Dado un concepto inicial y una lista de conceptos relacionados (sin orden específico), obtener la ruta
que une la mayor cantidad de conceptos posibles. Sin limite de profundidad (revisar)
*/
(ORIGINAL)
select from (traverse out() from (select from Concepto where Nombre = 'PARADIGMA')) 
where Nombre in ['POO','OBJETO','CLASE','ATRIBUTO','METODO','MENSAJE','ENTIDAD'] or Nombre = 'PARADIGMA'

(CANDIDATO - pero no funciona asi tal cual)
select FROM (
  MATCH {class: Concepto, where: (Nombre = 'PARADIGMA')}.out()
        {class: Concepto, as: dest, where: (Nombre IN ['POO','OBJETO','CLASE','ATRIBUTO','MENSAJE'])} RETURN expand(dest)
)

(CANDIDATO - funciona mejor pero trae conceptos que no forman parte de la ruta)
select FROM (
  MATCH {class: Concepto, where: (Nombre = 'POO')}.out()
        {class: Concepto, as: dest, while: ($depth < 8), where:(Nombre IN ['POO','PROGRAMA','ALGORITMO','TEST','MENSAJE','ESTADO','HERENCIA','SIMPLE'])} 
  RETURN DISTINCT dest.@rid, dest.Nombre, dest.outE().@class, dest.out().Nombre, dest.in().@rid, dest.in().Nombre
)

(PARECE SER LA MEJOR OPCION - Incluye validación anti-loop)
select FROM (
  MATCH {class: Concepto, where: (Nombre = 'POO')}.out()
        {class: Concepto, as: dest, 
            while: (
              ($depth < 8) and 
              (Nombre IN ['POO','PROGRAMA','ALGORITMO','TEST','MENSAJE','ESTADO','OBJETO','SIMPLE'])
            ),
            where: (
              Nombre IN ['POO','PROGRAMA','ALGORITMO','TEST','MENSAJE','ESTADO','OBJETO','SIMPLE']
            )
        } 
  RETURN 
    DISTINCT 
        dest.@rid as RID,
        dest.Nombre as Concepto, 
        dest.outE().@class as Relaciones, 
        dest.out().Nombre as ConceptosRelacionados,
        dest.out().@rid as RIDRelacionados
)


/**
 * Genera la consulta que permite recorrer las rutas a partir de un concepto
 * inicial y que contiene uno o más de los conceptos incluidos como
 * parámetro
 *
 * @param concepto_inicial    Concepto desde el que se debe iniciar la búsqueda
 * @param conceptos_incluidos Lista de conceptos que deben estar en el
 *                            resultado.
 * @return String con el query que, al ejecutar en el servidor, realiza la
 * búsqueda de las rutas necesarias
 */
public String buildRouteQuery(Concepto concepto_inicial, ArrayList<String> conceptos_incluidos) {
    String qry = String.format("select from (traverse out() from (select from Concepto where Nombre = '%s'))", concepto_inicial.getNombre());
    if (conceptos_incluidos != null && !conceptos_incluidos.isEmpty()) {
        // where Nombre in ['#1','#2','#3','#n'];
        StringBuilder bld = new StringBuilder();
        bld.append(" where Nombre in ");
        bld.append("[");
        boolean first_value = true;
        for (String s : conceptos_incluidos) {
            String param = String.format("'%s'", s);
            if (first_value) {
                bld.append(param);
                first_value = false;
            } else {
                param = ',' + param;
                bld.append(param);
            }
        }
        bld.append("]");
        // incluir nuevamente el nodo de origen porque si no comienza en el siguiente
        String origen = String.format(" or Nombre = '%s'", concepto_inicial.getNombre());
        bld.append(origen);
        qry = qry + bld.toString();
    }
    return qry;
}

/**
 * Devuelve todas las rutas que contienen un determinado concepto inicial y
 * cualquier cantidad de conceptos secundarios incluidos en alguna parte del
 * recorrido
 *
 * @param concepto_inicial    Concepto a partir del cual parte la búsqueda
 * @param conceptos_incluidos Lista de conceptos que pueden estar incluidos
 * @return Devuelve una lista de Vertex, que son los nodos que componen la
 * ruta, cada nodo contiene a su vez los arcos que lo enlazan a los nodos
 * vecinos
 */
public ImmutableList<Vertex> findRoute(Concepto concepto_inicial, ArrayList<String> conceptos_incluidos) {
    OrientGraph db = Factory.getTx();
    String qry = buildRouteQuery(concepto_inicial, conceptos_incluidos);
    OCommandSQL cmd = new OCommandSQL(qry); //+
    System.out.println("Query findRoute:" + qry);

    Iterable<Vertex> result = db.command(cmd).execute();
    ImmutableList<Vertex> result_list = ImmutableList.copyOf(result);
    return result_list;
}

/**
 * Recibe una respuesta, encuentra la ruta de la misma mediante findRoute()
 * y luego llama al método loadFromVertexVector() La respuesta obtenida
 * puede ser comparada con otra respuesta procesada con este método,
 * utilizando el método getDeltas() presente en Respuesta.
 *
 * @param respuesta Respuesta ya corregida por checkRespuesta() y evaluada
 *                  por evaluarRespuesta()
 * @return Devuelve una respuesta delta-comparable con cualquier otra
 * respuesta procesada del mismo modo.
 */
public Respuesta findRouteRespuesta(Respuesta respuesta) {

    // Se obtiene el primer concepto de la respuesta
    String ci = respuesta.getPrimerConceptoAsString();
    // Si no pude obtener ni siquiera un concepto volver con null
    if (ci == "") {
        return null;
    } else {

        Concepto concepto_inicial = (Concepto) this.getConceptoByName(ci);

        // Se ponen todos los conceptos menos el primero en una lista
        ArrayList<String> conceptos_incluidos = new ArrayList<String>();
        System.out.println(respuesta.getConceptosSecundariosAsString());
        conceptos_incluidos = respuesta.getConceptosSecundariosAsString();

        // Se crea la lista inmutable con los conceptos de la respuesta,
        // siguiendo su ruta.
        ImmutableList il = this.findRoute(concepto_inicial, conceptos_incluidos);

        Respuesta r = new Respuesta(); // La respuesta a devolver
        r.loadFromVertexVector(il);

        return r;
    }
}

/**
 * Devuelve todas las rutas que contienen un determinado concepto inicial y
 * hasta una cierta profundidad de recorrido.
 *
 * @param concepto_inicial Concepto a partir del cual parte la búsqueda
 * @param depth            Profundidad máxima de recorrido
 * @return Devuelve una lista de Vertex, que son los nodos que componen la
 * ruta, cada nodo contiene a su vez los arcos que lo enlazan a los nodos
 * vecinos
 */
public ImmutableList<Vertex> findRoutesFrom(Concepto concepto_inicial, int depth) {
    OrientGraph db = Factory.getTx();
    String qry = buildRouteDepth(concepto_inicial, depth);
    OCommandSQL cmd = new OCommandSQL(qry); //+
    System.out.println("Query findRoutesFrom:" + qry);

    Iterable<Vertex> result = db.command(cmd).execute();
    ImmutableList<Vertex> result_list = ImmutableList.copyOf(result);
    return result_list;
}

/**
 * Devuelve todas las rutas de un tipo determinado
 *
 * @param type Tipo de ruta que se debe devolver
 * @return Devuelve una lista de Vertex, que son los nodos que componen la
 * ruta, cada nodo contiene a su vez los arcos que lo enlazan a los nodos
 * vecinos
 */
public ImmutableList<Vertex> findRoutesByType(Relacion r) {
    OrientGraph db = Factory.getTx();
    String qry = String.format("select from Concepto where out('%s').size() > 0 or in('%s').size() > 0", r.Tipo, r.Tipo);
    OCommandSQL cmd = new OCommandSQL(qry); //+
    System.out.println("Query findRoutesByType:" + qry);

    Iterable<Vertex> result = db.command(cmd).execute();
    ImmutableList<Vertex> result_list = ImmutableList.copyOf(result);
    return result_list;
}