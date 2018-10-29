/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
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
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Clase que modela un Termino.
 * Un termino es un elemento atómico e indivisible que debe ser procesado
 * para evaluar una respuesta. Un término puede ser un Concepto o una Relación
 * y está relacionado de forma directa con la base de conocimientos.
 * @author Martin Casatti
 * 
 */
public class Termino
{
    /** Indica que el Término es un Concepto */
    public static final String tipoConcepto = "C";    
    /** Indica que el Término es una Relación */
    public static final String tipoRelacion = "R";    
    /** Indica que se solicita agregar un Concepto */
    public static final String addConcepto = "C+";
    /** Indica que se solicita agregar una Relación */
    public static final String addRelacion = "R+";
    
    private String Nombre;
    private String Vista;
    private String Tipo;
    private String Accion;
    private String ErrorAccion;
    private double Peso;
    /* Desplazamiento del concepto con respecto al mismo 
    concepto en la respuesta base
    */
    private Integer Delta;
    
    /* Contiene una lista con las palabras que se pueden utilizar para corregir
    el error. Esto es para mostrar al usuario y que el decida si quiere usar
    alguna de estas alternativas. Esto VUELVE DE LANGUAGE TOOLS.
    */
    private List<String> SugerenciasCorreccion=new ArrayList<>();
    private int posicionInicioEnTexto, posicionFinEnTexto;
    private String mensajeDeError;

    /**
     * Constructor.
     * @param Nombre Nombre del término
     * @param Vista Texto que se debe visualizar, si difiere del nombre
     */
    public Termino (String Nombre, String Vista)
    {
        this.Nombre = Nombre;
        this.Vista = Vista;
        this.Peso = 1;
    }
    
    public Termino (String Nombre)
    {
        this.Nombre = Nombre;
        this.Peso = 1;
    }    

//<editor-fold defaultstate="collapsed" desc="Setters">

    /**
     * Almacena la posición de inicio del texto en que se encuentra el error
     * @param posicionInicioEnTexto Posición de inicio
     */
    public void setPosicionInicioEnTexto (int posicionInicioEnTexto)
    {
        this.posicionInicioEnTexto = posicionInicioEnTexto;
    }

    /**
     * Almacena la posición de fin del texto en que se encuentra el error
     * @param posicionFinEnTexto Posición de fin 
     */
    public void setPosicionFinEnTexto(int posicionFinEnTexto) {
        this.posicionFinEnTexto = posicionFinEnTexto;
    }
    
    /**
     * Almacena el mensaje de error asociado al término (por ejemplo si no se
     * pudo dar de alta)
     * @param mensajeDeError 
     */
    public void setMensajeDeError(String mensajeDeError) {
        this.mensajeDeError = mensajeDeError;
    }

    /**
     * Asigna la lista de sugerencias de corrección para el término
     * @param SugerenciasCorreccion 
     */
    public void setSugerenciasCorreccion(List<String> SugerenciasCorreccion) {
        this.SugerenciasCorreccion = SugerenciasCorreccion;
    }

    /**
     * Almacena el tipo de término (C=Concepto, R=Relacion)
     * @param Tipo 
     */
    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }
    
    /**
     * Almacena la acción que se debe realizar, C+ = Alta de Concepto,
     * R+ = Alta de Relación
     * @param Accion 
     */
    public void setAccion (String Accion)
    {
        this.Accion = Accion;
    }
    
    /**
     * Almacena el error producido durante la ejecución de Acción.
     * Si no hubo error la propiedad queda vacía
     * @param Error 
     */
    public void setErrorAccion (String Error)
    {
        this.ErrorAccion = Error;
    }
    
    /**
     * Almacena el Peso correspondiente al Término
     * @param Peso 
     */
    public void setPeso (double Peso)
    {
        this.Peso = Peso;
    }
    
    /**
     * Almacena el Delta correspondiente al Término
     * @param Delta 
     */
    public void setDelta (Integer Delta)
    {
        this.Delta = Delta;
    }
    
    /**
     * Almacena el Nombre del Término
     * @param nombre 
     */
    public void setNombre (String nombre)
    {
        this.Nombre = nombre;
    }
//</editor-fold>
    
    
//<editor-fold defaultstate="collapsed" desc="Getters">
    /**
     * Obtiene el tipo de término
     * @return 
     */
    public String getTipo ()
    {
        return this.Tipo;
    }
    
    /**
     * Obtiene el String tipo de término
     * @return 
     */
    public String getTipoAsString ()
    {
        if (this.getTipo().equalsIgnoreCase("R")){
            return "Relacion";
        }
        else if (this.getTipo().equalsIgnoreCase("C")) {
            return "Concepto";
        }
        else if (this.getTipo().equalsIgnoreCase("I")){        
            return "Ignorar";
        }
        else{
            return "";
        }
        
    }
    
    /**
     * Obtiene la Accion asociada al término
     * @return 
     */
    public String getAccion ()
    {
        return this.Accion;
    }
    
    /** 
     * Obtiene el error asociado a la ejecución de la acción (si lo hubiera)
     * @return 
     */
    public String getErrorAccion()
    {
        return this.ErrorAccion;
    }
    
    /**
     * Obtiene el peso asociado al Termino
     * @return 
     */
    public double getPeso ()
    {
        return this.Peso;
    }
    
    /**
     * Obtiene el Nombre del Termino
     * @return 
     */
    public String getNombre ()
    {
        return (Nombre != null)? this.Nombre : this.Tipo;
    }
    
    /**
     * Obtiene el valor de visualización (si difiere del nombre)
     * @return 
     */
    public String getVista ()
    {
        return this.Vista;
    }
    
    /**
     * Obtiene el Delta asociado al término
     * @return 
     */
    public Integer getDelta ()
    {
        return this.Delta != null ? this.Delta : 0;
    }
//</editor-fold>

    /**
     * Devuelve el Término formateado como String
     * @return String con información del Término
     */    
    @Override
    public String toString()
    {
        return String.format("[%s [%s]]",Nombre,Tipo);
    }
    
    /**
     * Indica si el término tiene errores.
     * Se considera que un término tiene errores si tiene al menos una sugerencia
     * de corrección.
     * @return true=tiene errores, false=no tiene errores
     */
    public boolean hasErrors ()
    {
        return !SugerenciasCorreccion.isEmpty();
    }
    
    /**
     * Obtiene la lista con las sugerencias de corrección para este término
     * @return Lista de strings con las sugerencias de corrección
     */
    public List<String> getSugerenciasCorreccion()
    {
        return SugerenciasCorreccion;
    }
    
    /**
     * Devuelve información del Término como un string
     * @param full Indica si la información debe ser completa o resumida
     * @return String con información del Término
     */
    public String toString (boolean full)
    {
        if (!full)
            return toString();
        else
            return String.format("[%s [%s]] - Vista:%s A:%s E:%s P:%f",Nombre,Tipo,Vista,Accion,ErrorAccion,Peso);    
    }
    
    /**
     * Devuelve el valor delta de este término, junto con su nombre
     * @return String con el Nombre y Delta de éste término
     */
    public String toStringDelta ()
    {
        return String.format("[%s Δ=%d]",Nombre,Delta);
    }
    
    /**
     * Devuelve el término como una cadena (formato compacto)
     * @return String con la estructura XML (compacta)
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws TransformerException 
     */
    public String toXML() throws ParserConfigurationException, TransformerConfigurationException, TransformerException
    {
        return toXML(true);
    }
    
    /**
     * Devuelve el Término como un String XML, pudiendo ser compacto o extendido
     * @param compact Indica si el XML debe ser compacto o extendido
     * @return Cadena XML
     * @throws ParserConfigurationException
     * @throws TransformerConfigurationException
     * @throws TransformerException 
     */
    public String toXML(boolean compact) throws ParserConfigurationException, TransformerConfigurationException, TransformerException
    {
        Document doc = getXML();

        DOMSource source = new DOMSource(doc);
        
        TransformerFactory tf = TransformerFactory.newInstance();
        Transformer transformer;
        try 
        {
            transformer = tf.newTransformer();
            transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
            if (!compact)
            {
                transformer.setOutputProperty(OutputKeys.INDENT,"yes");
                transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount","3");
            }
            StringWriter writer = new StringWriter();
            transformer.transform(source, new StreamResult(writer));
            String output = writer.getBuffer().toString();
            return output;
        } 
        catch (TransformerException e) 
        {
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
    
    /**
     * Valida la estructura del Término.
     * Por el momento NO IMPLEMENTADO, devuelve siempre TRUE
     * @return true=Valido, false=Invalido
     */
    public boolean Validate()
    {
        return true;
    }
    
    /**
     * Devuelve el Término como un Documento XML formateado
     * @return Documento XML
     * @throws ParserConfigurationException 
     */
    Document getXML () throws ParserConfigurationException
    {
        /*
        <Termino>
            <Nombre></Nombre>
            <Vista></Vista>
            <Tipo></Tipo>
            <Accion></Accion>
            <ErrorAccion></ErrorAccion>
            <Peso></Peso>
            <Delta></Delta>
        </Termino>
        */
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        docFactory.setNamespaceAware(false);
        docFactory.setValidating(false);
	DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
	Document doc = docBuilder.newDocument();
        
	Element termino = doc.createElement("Termino");
	doc.appendChild(termino);
        
        Element nombre = doc.createElement("Nombre");
        nombre.appendChild (doc.createTextNode(this.Nombre));
        termino.appendChild(nombre);
        
        Element vista = doc.createElement("Vista");
        vista.appendChild (doc.createTextNode(this.Vista));
        termino.appendChild(vista);

        Element tipo = doc.createElement("Tipo");
        tipo.appendChild (doc.createTextNode(this.Tipo == null ? "" : this.Tipo));
        termino.appendChild(tipo);

        Element accion = doc.createElement("Accion");
        accion.appendChild(doc.createTextNode(this.Accion == null ? "" : this.Accion));
        termino.appendChild(accion);
        
        Element erroraccion = doc.createElement("ErrorAccion");
        erroraccion.appendChild(doc.createTextNode(this.ErrorAccion == null ? "" : this.ErrorAccion));
        termino.appendChild(erroraccion);
        
        Element peso = doc.createElement("Peso");
        peso.appendChild(doc.createTextNode(this.Peso == 0 ? "" : String.valueOf(this.Peso)));
        termino.appendChild(peso);

        Element delta = doc.createElement("Delta");
        peso.appendChild(doc.createTextNode(this.Delta == 0 ? "" : String.valueOf(this.Delta)));
        termino.appendChild(peso);

        return doc;
    }
    
    /**
     * Devuelve el Término como un Elemento XML
     * @return Elemento XML
     * @throws ParserConfigurationException 
     */
    public Element toElement () throws ParserConfigurationException
    {
        return getXML().getDocumentElement();
    }
    
    /**
     * Devuelve el Término como un Nodo XML
     * @return Nodo XML
     * @throws ParserConfigurationException 
     */
    public Node toNode() throws ParserConfigurationException
    {
        return getXML().getFirstChild();
    }

    public void setVista(String Vista) {
        this.Vista = Vista;
    }
    
    
    
    
}
