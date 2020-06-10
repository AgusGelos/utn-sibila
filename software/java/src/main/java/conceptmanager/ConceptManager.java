package conceptmanager;

import api.Respuesta;
import api.Termino;
import com.google.common.base.Joiner;
import com.orientechnologies.orient.core.command.traverse.OTraverse;
import com.orientechnologies.orient.core.db.record.OIdentifiable;
import com.orientechnologies.orient.core.id.ORecordId;
import com.orientechnologies.orient.core.record.impl.ODocument;
import com.orientechnologies.orient.core.sql.OCommandSQL;
import com.orientechnologies.orient.core.sql.filter.OSQLPredicate;
import com.orientechnologies.orient.core.sql.query.OSQLSynchQuery;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import com.google.common.collect.*;
import api.ElementoExamen;
import com.orientechnologies.orient.core.db.document.ODatabaseDocumentTx;
import com.orientechnologies.orient.core.metadata.schema.OType;
import static com.tinkerpop.blueprints.Direction.BOTH;
import com.tinkerpop.blueprints.impls.orient.OrientVertex;
// import groovy.util.logging.Commons;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import ortografia.CommonsFunctions;
import ortografia.LangTools;

/**
 * Esta clase engloba todas las operaciones que se pueden realizar sobre
 * conceptos
 *
 * @author Martin
 */
public class ConceptManager {

    OrientGraphFactory Factory;
    String DBName;
    String Username;
    String Password;

    /**
     * Constructor
     *
     * @param Database Nombre de la base de datos a la que se desea acceder (ej:
     *                 remote:localhost/PPR)
     * @param Username Nombre de usuario que está autorizado para conectarse a
     *                 la DB
     * @param Password Password del usuario
     */
    public ConceptManager(String Database, String Username, String Password) {
        this.DBName = Database;
        this.Username = Username;
        this.Password = Password;
        Factory = new OrientGraphFactory(Database, Username, Password).setupPool(1, 10);
        // Probar a ver si hay mejora en performance
        Factory.setAutoStartTx(false);
    }

    /**
     * Obtiene una lista de conceptos incluyendo "todos" los que existen en la
     * DB
     *
     * @return Una {@link ArrayList} de {@link Concepto}s
     */
    public ArrayList<Concepto> getConceptos() {
        try {
            ArrayList<Concepto> result = new ArrayList<>();

            OrientGraph db = Factory.getTx();
            for (Vertex v : db.getVerticesOfClass("Concepto")) {
                result.add(
                        new Concepto(
                                v.getProperty("@rid").toString(),
                                v.getProperty("Nombre").toString()));
            }
            db.shutdown();

            // Ordenar la salida por Nombre
            Collections.sort(result, new Comparator<Concepto>() {
                public int compare(Concepto o1, Concepto o2) {
                    return o1.getNombre().compareTo(o2.getNombre());
                }
            });

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Obtiene los conceptos "complejos". Estos conceptos están formados por más
     * de una palabra. El método devuelve una lista de ConceptoComplejo, que es
     * un objeto que contiene el Concepto base y un entero Orden indicando la
     * cantidad de palabras que forman el complejo. Debe servir para interpretar
     * mejor los textos y no considerar siempre que cada término está formado
     * por una sola palabra
     *
     * @return Lista de Conceptos complejos, formados por más de 1 palabra
     */
    public ArrayList<ConceptoComplejo> getConceptosComplejos() {
        ArrayList<ConceptoComplejo> complejos = new ArrayList();
        for (Concepto c : getConceptos()) {
            ConceptoComplejo complejo = new ConceptoComplejo(c);
            if (complejo.getOrden() > 1) {
                complejos.add(complejo);
            }
        }
        return (complejos.size() > 0) ? complejos : null;
    }

    /**
     * Obtiene las relaciones "complejas". Estas relaciones están formados por
     * más de una palabra, capitalizadas y unidas en una sola. El método
     * devuelve una lista de RelacionCompleja, que es un objeto que contiene la
     * Relacion base y un entero Orden indicando la cantidad de palabras que
     * forman el complejo. Debe servir para interpretar mejor los textos y no
     * considerar siempre que cada término está formado por una sola palabra
     *
     * @return Lista de Relaciones complejas, formados por más de 1 palabra
     */
    public ArrayList<RelacionCompleja> getRelacionesComplejas() {
        ArrayList<RelacionCompleja> complejos = new ArrayList();
        ArrayList<Relacion> relaciones = this.getRelaciones();
        for (Relacion r : relaciones) {
            RelacionCompleja complejo = new RelacionCompleja(r);
            if (complejo.getOrden() > 1) {
                complejos.add(complejo);
            }
        }
        return (complejos.size() > 0) ? complejos : null;
    }

    /**
     * Obtiene una lista de relaciones incluyendo "todas" los que existen en la
     * DB
     *
     * @return Una {@link ArrayList} de {@link Relacion}es
     */
    public ArrayList<Relacion> getRelaciones() {
        try {
            ArrayList<Relacion> result = new ArrayList<>();
            ArrayList<String> str_list = new ArrayList<>();

            OrientGraph db = Factory.getTx();
            for (Edge e : db.getEdgesOfClass("Relacion")) {
                if (!str_list.contains(e.getProperty("@class").toString())) {
                    result.add(new Relacion(
                            e.getProperty("@rid").toString(),
                            e.getProperty("@class").toString(),
                            e.getProperty("@class").toString()));
                    str_list.add(e.getProperty("@class").toString());
                }
            }

            // Ordenar la salida por Clase
            Collections.sort(result, new Comparator<Relacion>() {
                public int compare(Relacion o1, Relacion o2) {
                    return o1.getTipo().compareTo(o2.getTipo());
                }
            });

            db.shutdown();
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Devolver una relación por nombre
     *
     * @param nombre
     * @return
     */
    public Relacion getRelacionByName(String nombre) {
        // Si no viene un nombre devolver null directamente
        if (nombre == null)
            return null;
        // TODO: Mejorar mediante consulta directa.
        // Por ahora funciona obteniendo todas, iterando y devolviendo la que coincida
        ArrayList<Relacion> relaciones = this.getRelaciones();
        for (Relacion r : relaciones) {
            /* La comparacion se hace case insensitive */
            if (r.getNombre().equalsIgnoreCase(nombre)) {
                return r;
            }
        }
        return null;
    }

    /**
     * Obtiene un {@link Concepto} de acuerdo al nombre especificado
     *
     * @param Nombre Nombre del concepto que se desea obtener
     * @return {@link Concepto} o null si el nombre no existe
     */
    public Concepto getConceptoByName(String Nombre) {
        // Si no viene un nombre devolver null directamente
        if (Nombre == null)
            return null;
        Concepto c;
        OrientGraph db = Factory.getTx();
        Iterable<Vertex> vi = db.getVertices("Concepto.Nombre", Nombre.toUpperCase());
        if (vi.iterator().hasNext()) {
            Vertex v = vi.iterator().next();
            c = new Concepto(
                    v.getId().toString(),
                    Nombre
            );
        } else {
            c = null;
        }
        db.shutdown();
        return c;
    }

    /**
     * Obtiene un {@link Vertex} de acuerdo al nombre especificado La función
     * devuelve una clase estándar de OrientDB de tipo Vertex
     *
     * @param Nombre Nombre del concepto que se desea obtener
     * @return {@link Vertex} o null si el nombre no existe
     */
    public Vertex getConceptoVertexByName(String Nombre) {
        // Si no viene un nombre devolver null directamente
        if (Nombre == null)
            return null;
        Vertex v;
        OrientGraph db = Factory.getTx();
        Iterable<Vertex> vi = db.getVertices("Concepto.Nombre", Nombre.toUpperCase());
        if (vi.iterator().hasNext()) {
            v = vi.iterator().next();
        } else {
            v = null;
        }
        db.shutdown();
        return v;
    }

    /**
     * Agrega un concepto a la base de datos
     *
     * @param c es el Concepto que se desea agregar
     */
    public void addConcepto(Concepto c) {
        OrientGraph db = Factory.getTx();
        Vertex v = db.addVertex("class:Concepto", "Nombre", c.getNombre());
        //v.setProperty("Nombre", c.getNombre());
        db.shutdown();
    }
    
    /**
     * Agrega una equivalencia a un concepto
     * 
     * @param concepto concepto para agregar equivalencia. Si no existe no se carga nada
     * @param e cadena que representa a la equivalencia
     * @param p peso de la equivalencia con respecto al concepto
     */
    public boolean addEquivalencia(String concepto, String e, Double p) {
        boolean flag = false;
        try {
            Concepto c = this.getConceptoByName(concepto);
            OrientGraph db = Factory.getTx();
            Vertex conceptoDb = db.getVertex(c.getId());
            Map<String, Object> equivalencias = conceptoDb.getProperty("Equivalencias");
            if (equivalencias == null){
                equivalencias = new HashMap<String, Object>();
            }
            equivalencias.put(e ,p);
            conceptoDb.setProperty("Equivalencias", equivalencias);        
            db.shutdown();
            flag = true;
        }
        catch(Exception ex){
            flag = false;
        }
        return flag;
    }
    
    public Map<String, Object> getEquivalencias(String concepto) {
        Map<String, Object> equivalencias ;
        try {
            Concepto c = this.getConceptoByName(concepto);
            OrientGraph db = Factory.getTx();
            Vertex conceptoDb = db.getVertex(c.getId());
            equivalencias = conceptoDb.getProperty("Equivalencias");
            db.shutdown();
        } catch(Exception e){
            equivalencias = null;
        }
        return equivalencias;
    }


     /**
     * Cambia el nombre de un concepto
     *
     * @param c         es el Concepto que se desea modificar
     * @param newNombre es el nuevo nombre
     */
    public void updNombreConcepto(Concepto c, String newNombre) {
        Vertex v = getConceptoVertexByName(c.getNombre());
        if (v != null) {
            OrientGraph db = Factory.getTx();
            // Ejecutar un query para cambiar la propiedad Nombre
            String qry = String.format("update Concepto set Nombre='%s' where @rid=%s", newNombre, v.getId().toString());
            OCommandSQL cmd = new OCommandSQL(qry);
            db.command(cmd).execute();
            db.shutdown();
        }
    }

    /**
     * Elimina un concepto de la base de datos
     *
     * @param c es el concepto que se desea eliminar. Se busca por nombre y se
     *          elimina
     */
    public void delConcepto(Concepto c) {
        Vertex del = getConceptoVertexByName(c.getNombre());
        if (del != null) {
            OrientGraph db = Factory.getTx();
            db.removeVertex(del);
            db.shutdown();
        }
    }

    /**
     * Elimina un concepto de la base de datos de forma segura, validando que no
     * existan relaciones
     *
     * @param c El concepto que se desea eliminar
     * @return True si el concepto se eliminó, False en caso contrario
     */
    public boolean delConceptoSafe(Concepto c) {
        if (refsConcepto(c) > 0) {
            return false;
        } else {
            delConcepto(c);
            return true;
        }
    }

    /**
     * Obtiene la cantidad de referencias (relaciones) que tiene un concepto en
     * la base de datos Se utiliza para validar las dependencias y saber si un
     * concepto está relacionado o no antes de eliminarlo
     *
     * @param c Concepto del que se desean saber las referencias
     * @return
     */
    public long refsConcepto(Concepto c) {
        OrientGraph db = Factory.getTx();
        String qry = String.format("select Nombre, out().size() as CantOUT, in().size() as CantIN from Concepto where Nombre='%s'", c.getNombre());
        OCommandSQL cmd = new OCommandSQL(qry); //+
        System.out.println("Query findRoute:" + qry);

        Iterable<Vertex> result = db.command(cmd).execute();
        long CantOUT = 0;
        long CantIN = 0;

        Vertex v = Iterables.get(result, 0);
        CantOUT += Long.parseLong(v.getProperty("CantOUT").toString());
        CantIN += Long.parseLong(v.getProperty("CantIN").toString());

        long cant_ref = CantOUT + CantIN;
        db.shutdown();
        return cant_ref;
    }

    /**
     * Analiza término por término la respuesta provista y devuelve una
     * Respuesta con los flags indicadores correctamente colocados
     *
     * @param respuesta Respuesta a Analizar
     * @return La respuesta con las marcas y el numero de secuencia correcto
     */
    public Respuesta evaluarRespuesta(Respuesta respuesta) {

        Respuesta ret = new Respuesta(respuesta.getTexto());
        for (Termino t : respuesta.getTerminos()) {
            // Asigno el tipo del termino
            t.setTipo(getTipoTermino(t));
            ret.addTermino(t);
        }
        return ret;
    }

    /**
     * Obtiene todos los terminos complejos de la base de datos devolviendo los
     * terminos que se hayan encontrado en el texto
     *
     * @param res Respuesta de la cual se extrae el texto
     * @return La respuesta devuelve una lista de terminos complejos tanto
     * conceptos como relaciones
     */
    public ArrayList<Termino> getTerminosComplejos(Respuesta res) {
        LangTools lt = new LangTools();

        return lt.getTerminosComplejosDelElemento(res, this.getConceptosComplejos(), this.getRelacionesComplejas());

    }

    /**
     * Devuelve un string con el tipo del termino pasado por parametro Para eso
     * busca en la base de datos, por nombre e indica si existe en Conceptos o
     * si existe en Relaciones, o si no existe.
     *
     * @param termino El termino que se desea saber que tipo es
     * @return String con los valores C, R o vacia
     */
    public String getTipoTermino(Termino termino) {
        String nombre = termino.getNombre();
        String raiz = termino.getRaiz();
        System.out.println(String.format("ConceptManager::getTipoTermino nombre=%s raiz=%s",nombre,raiz));
        Concepto c = this.getConceptoByName(raiz);
        if (c == null){
            c = this.getConceptoByName(nombre);
        }
        // Si encontro un concepto devolver C
        if (c != null) {
            return Termino.tipoConcepto;
        } else {
            Relacion r = this.getRelacionByName(raiz);
            if (r == null){
                r = this.getRelacionByName(nombre);
            }
            if (r != null) {
                return Termino.tipoRelacion;
            } else {
                return "";
            }
        }
    }

    /**
     * Agrega un tipo de relacion
     *
     * @param nombreTipo nombre del tipo de relacion a agregar
     */
    public void addTipoRelacion(String nombreTipo) {
        // La operacion de creacion del tipo de relacion
        // es atomica, por eso solicito un "NoTx" que quiere decir
        // no transaccional
        OrientGraphNoTx db = Factory.getNoTx();
        OrientEdgeType type = db.createEdgeType(nombreTipo.trim(), "Relacion");
        db.shutdown();
    }

    /**
     * Agrega una relacion entre dos conceptos, utilizando las clases
     *
     * @param origen   Concepto de origen
     * @param destino  Concepto de destino
     * @param relacion Relación que une los dos conceptos
     */
    public void addRelacion(Concepto origen, Concepto destino, Relacion relacion) {
        // Validar si dicha relacion entre origen y destino ya existe
        // si no existe seguir. Si ya existe salir sin hacer nada
        if (!existStruct(origen, destino, relacion)) {
            // Verificar si existe el tipo de relación
            if (!existTipoRelacion(relacion.getNombre())) // Si no existe crear el tipo
            {
                addTipoRelacion(relacion.getNombre());
            }
            // En este punto el tipo existe, ya sea porque estaba de antes
            // o porque lo acabo de crear recién
            // agregar el arco uniendo los conceptos
            Vertex v_origen = this.getConceptoVertexByName(origen.getNombre());
            Vertex v_destino = this.getConceptoVertexByName(destino.getNombre());
            if (v_origen != null && v_destino != null) {
                this.createEdge(v_origen, v_destino, relacion.getNombre());
            }
        }
    }

    /**
     * Agrega una relacion entre dos conceptos, utilizando los nombres de
     * concepto y el nombre de la relacion
     *
     * @param origen   Nombre del concepto de origen
     * @param destino  Nombre del concepto de destino
     * @param relacion Nombre del tipo de relacion
     */
    public void addRelacion(String origen, String destino, String relacion) {
        Concepto orig = new Concepto("", origen);
        Concepto dest = new Concepto("", destino);
        Relacion rel = new Relacion("", relacion);
        addRelacion(orig, dest, rel);
    }

    /**
     * Crea un Edge entre 2 Vertex. Esta rutina trabaja a bajo nivel y es
     * utilizada por los métodos addRelacion
     *
     * @param origen  Vertex de origen
     * @param destino Vertex de destino
     * @param relType Nombre del tipo de relacion
     */
    private void createEdge(Vertex origen, Vertex destino, String relType) {
        String class_edge = "Class:" + relType;
        OrientGraph db = Factory.getTx();
        db.addEdge(class_edge, origen, destino, relType);
        // Cerrar la conexion haciendo commit
        db.shutdown(true);
    }

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
     * Genera la consulta que permite recorrer las rutas a partir de un concepto
     * inicial y, para todas las rutas y hasta una profundidad dada
     *
     * @param concepto_inicial Concepto desde el que se debe iniciar la búsqueda
     * @param depth            Profundidad maxima de recorrido
     * @return String con el query que, al ejecutar en el servidor, realiza la
     * búsqueda de las rutas necesarias
     */
    public String buildRouteDepth(Concepto concepto_inicial, int depth) {
        String qry = String.format(
                "select from (traverse both() from (select from Concepto where Nombre = '%s') while $depth <= %d)",
                concepto_inicial.getNombre(),
                depth
        );

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

    /**
     * Elimina de una lista de terminos todos los que están marcados como
     * "Ignorar" (tipo I) y devuelve la lista resultante (limpia)
     *
     * @param lista Lista de términos a limpiar
     * @return Lista de términos limpia (sin términos marcados I)
     */
    public ArrayList<Termino> cleanTerminos(ArrayList<Termino> lista) {
        // Primero armar una lista quitando los IGNORAR, para hacer una lista
        // contigua de elementos válidos (Concepto o Relacion)
        ArrayList<Termino> result = new ArrayList();
        for (Termino t : lista) {
            if (!t.getTipo().equalsIgnoreCase("I")) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Elimina los terminos (ignorables) de un elemento examen (sobrecargado)
     *
     * @param elemento ElementoExamen que contiene la lista de términos a
     *                 limpiar
     * @return Lista sin elementos ignorables
     */
    public ArrayList<Termino> cleanTerminos(ElementoExamen elemento) {
        return cleanTerminos(elemento.getTerminos());
    }

    /**
     * Dados dos conceptos y una relacion el método detecta si dicha estructura
     * ya existe en la base de conocimientos
     *
     * @param origen   Concepto de Origen
     * @param destino  Concepto de Destino
     * @param relacion Relación que une los dos conceptos
     * @return true = La estructura existe, false = No existe
     */
    public boolean existStruct(Concepto origen, Concepto destino, Relacion relacion) {
        OrientGraph db = Factory.getTx();
        String qry = String.format(
                "select count(*) as cantidad from (select expand(out('%s').Nombre) from Concepto where Nombre = '%s') where value = '%s'",
                relacion.getNombre(), origen.getNombre(), destino.getNombre()
        );
        OCommandSQL cmd = new OCommandSQL(qry); //+
        System.out.println("Query existStruct:" + qry);

        Iterable<Vertex> result = db.command(cmd).execute();
        long cantidad = 0;

        Vertex v = Iterables.get(result, 0);
        cantidad = Long.parseLong(v.getProperty("cantidad").toString());
        db.shutdown();
        return (cantidad > 0);
    }

    /**
     * Dados un concepto el método detecta si dicho concepto
     * ya existe en la base de conocimientos
     *
     * @param concepto Concepto a buscar
     * @return true = El concepto existe, false = No existe
     */
    public boolean existConcept(Concepto concepto) {
        OrientGraph db = Factory.getTx();
        String qry = String.format(
                "select count(*) as cantidad from Concepto where Nombre = '%s'",
                concepto.getNombre());
        OCommandSQL cmd = new OCommandSQL(qry); //
        System.out.println("Query existConcept:" + qry);

        Iterable<Vertex> result = db.command(cmd).execute();
        long cantidad = 0;

        Vertex v = Iterables.get(result, 0);
        cantidad = Long.parseLong(v.getProperty("cantidad").toString());
        db.shutdown();
        return (cantidad > 0);
    }


    /**
     * Dados dos conceptos y una relacion el método detecta si dicha estructura
     * ya existe en la base de conocimientos
     *
     * @param origen   Nombre del concepto de origen
     * @param destino  Nombre del Concepto de Destino
     * @param relacion Nombre de la Relación que une los dos conceptos
     * @return true = La estructura existe, false = No existe
     */
    public boolean existStruct(String origen, String destino, String relacion) {
        Concepto ori = new Concepto("", origen);
        Concepto dest = new Concepto("", destino);
        Relacion rel = new Relacion("", relacion, relacion);
        return existStruct(ori, dest, rel);
    }

    /**
     * Indica si un tipo de relación ya existe en la base de datos
     *
     * @param tipo String con el nombre del tipo
     * @return true = el tipo ya existe, false = el tipo no existe
     */
    public boolean existTipoRelacion(String tipo) {
        OrientGraph db = Factory.getTx();
        ODatabaseDocumentTx dbd = db.getRawGraph();
        // Primero ver si la clase existe
        boolean existeClase = dbd.getMetadata().getSchema().existsClass(tipo);
        // Ahora ver si hereda de Relación (sino puede ser otro tipo de clase, cualquiera)
        if (existeClase) {
            boolean esRelacion = dbd.getMetadata().getSchema().getClass(tipo).getSuperClassesNames().contains("Relacion");
            return (esRelacion);
        } else {
            return false;
        }
    }

    private String normalizeTipoRelacion(String tipo) {
        // Dado un texto, une las palabras (si hay mas de una) y las coloca
        // en camel case
        return CommonsFunctions.toCamelCase(tipo);
    }

    public boolean addStruct(String origen, String destino, String relacion) {

        Concepto conceptoOrigen = new Concepto(origen);
        Concepto conceptoDestino = new Concepto(destino);
        Relacion rel = new Relacion("0", relacion, relacion);

        return addStruct(conceptoOrigen, conceptoDestino, rel);
    }

    public boolean addStruct(Concepto origen, Concepto destino, Relacion relacion) {
        boolean carga = false; //variable que indicará si se cargó o no la estructura
        try {
            //Validamos si ya existe la estructura, en dicho caso, no se hace nada
            if (!this.existStruct(origen, destino, relacion)) {

                //Validamos si los conceptos existen en la BD (por separado)
                //En caso de no existir, los creamos
                //la relación no se valida ya que eso se verifica en el addRelacion
                if (!this.existConcept(origen)) {
                    //creamos concepto origen
                    this.addConcepto(origen);
                }
                if (!this.existConcept(destino)) {
                    //creamos concepto destino
                    this.addConcepto(destino);
                }

                //Una vez asegurados de que los conceptos existen, ahora procedemos a crear la relación
                //ACLARACIÓN: En el método addRelacion se valida si el tipo de relación ya existe y en caso
                //contrario, se crea
                this.addRelacion(origen, destino, relacion);
                carga = true;
            } else {
                System.out.println("Ya existe la estructura");
            }
        } catch (Exception e) {
            carga = false;
            System.out.println(e.getMessage());
        }
        return carga;
    }

    // TODO: Implementar un método que dados dos conceptos y una relacion
    // indique si esa estructura ya existe en la base de datos.
    // Implementar un método que dé de alta una relacion entre dos conceptos
    // controlando si el tipo ya existe
    // Sobrecargar el método anterior para que si el tipo no existe lo cree
    // y luego dé de alta la relacion
}