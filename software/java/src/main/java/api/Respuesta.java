/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import conceptmanager.Concepto;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.DocumentFragment;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Modela una respuesta. Hereda de Elemento Examen.
 *
 * @author Martin
 */
public class Respuesta extends ElementoExamen {

    /**
     * Peso de la respuesta, se calcula luego de evaluar la respuesta y se puede
     * comparar con otra respuesta.
     */
    private double Peso;
    /**
     * Utilizado para intercambios entre cliente y base de datos. Sin uso por el
     * momento
     */
    private int Secuencia;
    /**
     * Query que modela la forma en que se debe buscar esta respuesta en el
     * grafo.
     */
    private String Query = "";

    /**
     * Constructor. Crea una Respuesta a partir de un texto
     *
     * @param texto Texto de la Respuesta a crear.
     */
    public Respuesta(String texto) {
        super(texto);
        Peso = 0;
        Secuencia = 0;
    }

    /**
     * Constructor. Crea una respuesta vacía
     */
    public Respuesta() {
        super("");
        Peso = 0;
        Secuencia = 0;
    }

    /**
     * Devuelve el peso de una respuesta
     *
     * @return Peso de la respuesta
     */
    public double getPeso() {
        return Peso;
    }

    /**
     * Devuelve el número de secuencia actual de la Respuesta
     *
     * @return Número de secuencia actual
     */
    public int getSecuencia() {
        return Secuencia;
    }

    /**
     * Asigna el peso a la Respuesta actual
     *
     * @param Peso Peso asignado
     */
    public void setPeso(double Peso) {
        this.Peso = Peso;
    }

    /**
     * Asigna el número de secuencia a la Respuesta actual
     *
     * @param Secuencia Número de secuencia
     */
    public void setSecuencia(int Secuencia) {
        this.Secuencia = Secuencia;
    }

    /**
     * Asigna el query que representa la Respuesta Este query se puede utilizar
     * con el motor de base de datos OrientDB para obtener los conceptos y
     * relaciones que modelan la respuesta
     *
     * @param query Query, con sintaxis OrientDB
     */
    public void setQuery(String query) {
        this.Query = query;
    }

    /**
     * Obtiene el query que representa la Respuesta
     *
     * @return String con el query
     */
    public String getQuery() {
        return this.Query;
    }

    @Override
    public String toString() {
        return getTexto();
    }

    public String toStringF() {
        StringBuilder bld = new StringBuilder();
        for (Termino t : this.Terminos) {
            bld.append(t.toString());
            if (bld.length() > 0) {
                bld.append(",");
            }
        }
        return bld.toString();
    }

    /**
     * Devuelve los términos de la Respuesta como un string formateado, con los
     * términos separados por coma
     *
     * @param full Indica si la información de los terminos es completa o
     * resumida
     * @return String con los términos separados por coma.
     */
    public String toStringF(boolean full) {
        StringBuilder bld = new StringBuilder();
        for (Termino t : this.Terminos) {
            bld.append(t.toString(full));
            if (bld.length() > 0) {
                bld.append(",");
            }
        }
        return bld.toString();
    }

    /**
     * Devuelve los valores Delta de desplazamiento de cada uno de los términos
     *
     * @return String con los Delta de cada término
     */
    public String toStringDelta() {

        StringBuilder bld = new StringBuilder();

        for (Termino t : this.Terminos) {

            bld.append(t.toStringDelta());
            if (bld.length() > 0) {
                bld.append(",");
            }

        }
        return bld.toString();
    }

    /**
     * Devuelve la estructura de la Respuesta, en formato XML, compacto.
     *
     * @return String con formato XML
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public String toXML() throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        return toXML(true);
    }

    /**
     * Devuelve la estructura de la Respuesta, en formato XML, compacto o
     * extendido.
     *
     * @param compact Indica si el formato debe ser compacto(true) o
     * extendido(false)
     * @return String con formato XML
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws TransformerException
     */
    public String toXML(boolean compact) throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        Document doc = getXML();

        DOMSource source = new DOMSource(doc);

        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try {
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            if (!compact) {
                transformer.setOutputProperty(OutputKeys.INDENT, "yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "3");
            }
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        }
        catch (TransformerException e) {
            e.printStackTrace();
        }

        return null;
        /*
        Transformer transformer=TransformerFactory.newInstance().newTransformer();
        transformer.setOutputProperty(OutputKeys.INDENT,"yes");
        transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION,"yes");
        transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","3");
        DOMSource source = new DOMSource(doc);

        // Output to console for testing
        //StreamResult result = new StreamResult(System.out);
        //StreamResult result = new StreamResult(new StringWriter());
        StreamResult result = new StreamResult(new File("C:\\termino.xml",""));
        try
        {
            transformer.transform(source, result);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }


        return ""; //result.getWriter().toString();
         */
    }

    Document getXML() throws ParserConfigurationException, TransformerException {
        /*
        <Respuesta
        xmlns:xsi='http://www.w3.org/2001/XMLSchema-instance'
        xsi:noNamespaceSchemaLocation='RespuestaPacket.xsd'
        Secuencia="3"
        Peso="0"
        >
        <Termino>
        <Nombre></Nombre>
        <Vista></Vista>
        <Tipo></Tipo>
        <Accion></Accion>
        <ErrorAccion></ErrorAccion>
        <Peso></Peso>
        </Termino>
        </Respuesta>
         */
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setNamespaceAware(false);
        docFactory.setValidating(false);
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
        Document doc = docBuilder.newDocument();
        Element respuesta = doc.createElement("Respuesta");
        respuesta.setAttribute("Secuencia", String.valueOf(this.Secuencia));
        respuesta.setAttribute("Peso", String.valueOf(this.Peso));
        doc.appendChild(respuesta);
        for (Termino t : this.Terminos) {
            Node n = doc.importNode(t.toNode(), true);
            respuesta.appendChild(n);
        }
        return doc;
    }

    /**
     * @deprecated
     * Dada una respuesta candidata, pasada por parámetro, compara los terminos
     * con esta respuesta y devuelve la original con los valores Delta cargados
     * en sus términos. Los valores de Delta posible son:<br>
     * = null el termino de la candidata no se encontro en la base
     * = 0 el termino está en el mismo lugar en ambas<br>
     * {@literal < 0} el término aparece antes en la respuesta candidata<br>
     * {@literal > 0} el término aparece despues en la respuesta candidata<br>
     * El valor, mayor o menor que cero indica la cantidad de lugares
     * desplazado.
     *
     * @param candidata Respuesta candidata
     * @return Respuesta con los valores Delta actualizados
     */
    public Respuesta getDeltasBackup(Respuesta candidata) {

        System.out.println("-----------Base--------------------");
        System.out.println(this.Terminos.toString());
        System.out.println("-----------Candidata---------------");
        System.out.println(candidata.getTerminos().toString());

        // Sólo obtengo los conceptos de cada respuesta.
        // De este modo, descartamos las relaciones en el cálculo de la posición
        ArrayList<Concepto> baseSoloConceptos = this.getConceptos(this);
        ArrayList<Concepto> candidataSoloConceptos = this.getConceptos(candidata);


        for (Termino t : baseSoloConceptos) {

            // Sólo sigo si es concepto o relación
            if (
                    t.getTipo().equalsIgnoreCase(Termino.tipoConcepto)
                    || 
                    t.getTipo().equalsIgnoreCase(Termino.tipoRelacion)
                ) {

                int pos_base = getConceptoIndexByName(baseSoloConceptos, t.getNombre());
                System.out.println("Posición de " + t.getNombre() + " en base: " + pos_base);

                int pos_cand = getConceptoIndexByName(candidataSoloConceptos,t.getNombre());
                System.out.println("Posición de " + t.getNombre() + " en candidata: " + pos_cand);

                // pos_base == 0 || pos_cand == 0 quieren decir que en una de las
                // respuestas hay un Termino de menos
                // ?????????????????????????????????????????
                // ?????????????????????????????????????????
                if (pos_base == 0 || pos_cand == 0) {
                    t.setDelta(null);
                } else {
                    t.setDelta((pos_cand - pos_base));
                    System.out.println(String.format("Δ= %d",t.getDelta()));
                }
            } else {
                // Si no sé qué es, null
                t.setDelta(null);
                System.out.println("No se pudo determinar si "+t.getNombre()+" es Concepto o Relación");
            }
        }

        return this;
    }
    
    
     /**
     * Dada una respuesta candidata, pasada por parámetro, compara los terminos
     * con esta respuesta y devuelve la candidata con los valores Delta cargados
     * en sus términos. Los valores de Delta posible son:<br>
     * = null el termino de la candidata no se encontro en la base
     * = 0 el termino está en el mismo lugar en ambas<br>
     * {@literal < 0} el término aparece antes en la respuesta candidata<br>
     * {@literal > 0} el término aparece despues en la respuesta candidata<br>
     * El valor, mayor o menor que cero indica la cantidad de lugares
     * desplazado.
     *
     * @param candidata Respuesta candidata
     * @return Respuesta candidata con los valores Delta actualizados
     */
    public Respuesta getDeltas(Respuesta candidata) {
        
        // Índice del término actual
        int index = 0;
        Integer delta = null;
        
        // Recorro los términos de la respuesta candidata
        for (Termino t : candidata.getTerminos()) {
            // En el caso que sea un concepto: 
            if (t.getTipo().equalsIgnoreCase(Termino.tipoConcepto)) {
                // Creo un concepto con dicho término
                Concepto c = new Concepto(t);
                // Obtengo el delta correspondiente a ese concepto
                delta = this.getDeltaConcepto(c,index);
                // Devuelvo -delta porque es la posición relativa respecto a la respuesta base
                t.setDelta(-delta);
            }
            // Voy incrementando el índice
            index++;
        }
        // Retorno la respuesta candidata con los deltas ya seteados.
        return candidata;
    }
    
    /**
     * Calcula el Delta del concepto de la respuesta candidata.
     * El método recorre los términos hacia adelnte a partir del índice pasado por parámetro
     * Primero se valida si es concepto y en caso de serlo se valida si es igual
     * al término en la respuesta base. Si es concepto pero no coincide con el término
     * en la respuesta base, se suma el delta y se pasa a analizar el siguiente
     * término dentro de la respuesta base.
     * Si se terminan los términos de la respuesta base y aún no se ha encontrado
     * se vuelve al índice inicial - 1 y se recorre en sentido inverso la lista
     * de terminos hasta el principio de la misma
     * @param c Concepto a calcular el delta
     * @param indice_candidato Índice del concepto en la respuesta candidata
     * @return 
     */
    private Integer getDeltaConcepto(Concepto c, int indiceTermino){
        //Declaramos variable auxiliar para llevar a cabo el recorrido
        //y recordar el indice candidato
        int indiceTerminoAuxiliar = indiceTermino;
        Integer delta = 0;
       
        //Recorremos la lista a partir del indice candidato del término y mientras la lista no se termine
        while (indiceTerminoAuxiliar < this.getTerminos().size()){
            //Si es un concepto se aumenta delta en uno
                if (this.getTermino(indiceTerminoAuxiliar).getTipo().equalsIgnoreCase(Termino.tipoConcepto)){ 
                    //En caso de que se encuentre el concepto se RETORNA delta
                    if (c.getNombre().equalsIgnoreCase(this.getTermino(indiceTerminoAuxiliar).getNombre())){
                        return delta; 
                    }
                delta++;
            }     
            //Incrementamos índice auxiliar
            indiceTerminoAuxiliar++;
        }
        
        //Seteamos el ínidce auxiliar en el índice del término -1 para no repetir un elemento
        indiceTerminoAuxiliar = indiceTermino - 1;
        //Reset delta
        delta = 0;
        //Recorremos la lista desde el indice candidato hasta el principio de la misma
        while (indiceTerminoAuxiliar >= 0){
            //Si es un concepto se decrementa delta en uno
            if (this.getTermino(indiceTerminoAuxiliar).getTipo().equalsIgnoreCase(Termino.tipoConcepto)){ 
                //En caso de que se encuentre el concepto se RETORNA delta
                if (c.getNombre().equalsIgnoreCase(this.getTermino(indiceTerminoAuxiliar).getNombre()))
                {
                    return delta; 
                }
                delta--;
            }   
            indiceTerminoAuxiliar--;
        }
        
        //Si no se encuentra el concepto en la respuesta base, se retorna NULL
        return this.getTerminos().size();
    }

    /**
     * Dada una respuesta, devuelve otra que sólo tiene sus conceptos, y no sus
     * relaciones
     *
     * @param r La respuesta de la cual obtenemos los conceptos
     * @return Respuesta sin ninguna relación
     */
    public Respuesta getConceptosRespuesta(Respuesta r) {

        Respuesta nueva = new Respuesta();
        ArrayList<Termino> al = new ArrayList();

        // Recorro la respueta pasada por parámetro
        for (Termino t : r.getTerminos()) {
            // Sólo agrego los conceptos
            if (t.getTipo().equalsIgnoreCase(Termino.tipoConcepto)) {
                al.add(t);
            }
        }

        // Si la respuesta no vino vacía
        if (al.size() > 0) {
            nueva.setTerminos(al);
        }
        return nueva;
    }

    /**
     * Elimina las relaciones de una respuesta, dejando únicamente los
     * conceptos. Cuidado: las relaciones eliminadas no pueden recuperarse.
     */
    public void eliminarRelaciones() {

        ArrayList<Termino> al = new ArrayList();

        for (Termino t : this.getTerminos()) {
            // Sólo se queda con los conceptos
            if (t.getTipo().equalsIgnoreCase(Termino.tipoConcepto)) {
                al.add(t);
            }
        }

        if (al.size() > 0) {
            this.setTerminos(al);
        }
    }

    /**
     * Elimina los conceptos de una respuesta, dejando únicamente las
     * relaciones. Cuidado: los conceptos eliminados no pueden recuperarse.
     */
    public void eliminarConceptos() {

        ArrayList<Termino> al = new ArrayList();

        for (Termino t : this.getTerminos()) {
            // Sólo se queda con las relaciones
            if (t.getTipo().equalsIgnoreCase(Termino.tipoRelacion)) {
                al.add(t);
            }
        }

        if (al.size() > 0) {
            this.setTerminos(al);
        }
    }

    private int getCantidadByType(String tipo) {
        int cantidad = 0;
        if (tipo.equalsIgnoreCase(Termino.tipoConcepto)
            || tipo.equalsIgnoreCase(Termino.tipoRelacion)) {
            for (Termino t : this.getTerminos()) {
                // Sólo se queda con las relaciones
                if (t.getTipo().equalsIgnoreCase(tipo)) {
                    cantidad++;
                }
            }
        }
        return cantidad;
    }

    public int getCantidadConceptos() {
        return getCantidadByType(Termino.tipoConcepto);
    }

    public int getCantidadRelaciones() {
        return getCantidadByType(Termino.tipoRelacion);
    }

    /***
     * Calcula el peso de un concepto desplazado
     * @param c Concepto al que se le quiere calcular el peso
     * @param conceptos_respuesta Cantidad de conceptos totales en la respuesta
     * @return Valor del peso del concepto
     */
    private double getPesoConceptoDesplazado (Termino t, int conceptos_respuesta) {
        // La fórmula de cálculo es la siguiente:
        // Cd = 1 - |(Pc - Pb)/n| siendo
        // Cd: Valor del concepto desplazado
        // Pc-Pb: Posicion del concepto candidato - Posicion del concepto base (se reemplaza por Delta)
        // n: Cantidad de conceptos en la respuesta
        
        Double delta = (double)t.getDelta();
        Double conceptos = (double) conceptos_respuesta;
        double peso = 1 - Math.abs(delta / conceptos);
        return peso;
        }

    /**
     * Calcula el peso de la respuesta teniendo en cuenta los delta (si ya han
     * sido calculados)
     */
    public double calcularPeso(Respuesta respuesta) {
        double peso_aux = 0.0;
        int conceptos_respuesta;
        
        if (respuesta == null) {
            conceptos_respuesta = this.getCantidadConceptos();
        } else {
            conceptos_respuesta = respuesta.getCantidadConceptos();
        }
        System.out.println(conceptos_respuesta);
        // Por el momento solo tenemos en cuenta los conceptos
        for (Termino t : this.getTerminos()) {
            // Sólo se queda con las relaciones
            if (t.getTipo().equalsIgnoreCase(Termino.tipoConcepto)) {
                // Es Concepto.
                // El peso de un Concepto es Inicialmente 1 pero se ajusta de
                // acuerdo a los deltas y (futuro) a las equivalencias
                //
                double peso_concepto = this.getPesoConceptoDesplazado(t, conceptos_respuesta);
                t.setPeso(peso_concepto);
                System.out.println("Término: "+ t.getNombre());
                System.out.println("Peso: " + peso_concepto);
                // Acumular el peso de este concepto
                peso_aux += peso_concepto;
            }
        }
        // Asignar el peso total a la propiedad
        this.setPeso(peso_aux);
        return getPeso();
    }
    
    public double calcularPeso (){
        return calcularPeso(null);
    }
    

    /**
  * Devuelve la ubicación de un término dado su nombre.
  *
  * @param al ArrayList de Conceptos
  * @param name Nombre del término a buscar en el ArrayList
  * @return Posición basada en 0 del término encontrado. -1 si no se
  * encontró.
  */
 private int getConceptoIndexByName(ArrayList<Concepto> al, String name) {

 int index = 0;
 for (Concepto c : al) {
     if (c.getNombre().equalsIgnoreCase(name)) {
             return index;
         }
         index++;
     }
     return -1;
 }

 /**
  * Dada una respuesta, devuelve un ArrayList que sólo tiene sus conceptos,
  * y no sus relaciones
  *
  * @param r La respuesta de la cual obtenemos los conceptos
  * @return ArrayList sin ninguna relación
  */
 public ArrayList<Concepto> getConceptos(Respuesta r) {

     ArrayList<Concepto> al = new ArrayList();

     // Recorro la respueta pasada por parámetro
     for (Termino t : r.getTerminos()) {
         // Sólo agrego los conceptos
         if (t.getTipo().equalsIgnoreCase(Termino.tipoConcepto)) {
             Concepto c = new Concepto(t);
             al.add(c);
         }
     }
     return al;
 }
}
