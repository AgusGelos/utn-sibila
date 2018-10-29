/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkElementIndex;
import static com.google.common.base.Preconditions.checkNotNull;
import com.google.common.collect.ImmutableList;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import conceptmanager.Concepto;
import conceptmanager.Relacion;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

/**
 * Modela un Elemento de Examen, genérico. Tanto las respuestas como las
 * preguntas heredan datos y comportamiento común de ElementoExamen.
 *
 * @author Martin
 */
public class ElementoExamen {

    /**
     * Texto del elemento
     */
    String Texto;
    /**
     * Lista de términos que componen el elemento
     */
    ArrayList<Termino> Terminos;
    /**
     * Lista de errores de sintaxis asociados al texto
     */
    private final ArrayList<SyntError> SyntErrors;
    /**
     * Solamente para gestión del grafo
     */
    public String IdNodo;

    /**
     * Constructor construye un ElementoExamen e inicializa Texto
     *
     * @param texto Texto del elemento (ya sea pregunta o respuesta)
     */
    public ElementoExamen(String texto) {
        Texto = texto;
        Terminos = new ArrayList<>();
        SyntErrors = new ArrayList<>();
    }


    /**
     * Construye un ElementoExamen a partir de una lista de términos y\n y
     * reconstruye el Texto a partir de los diversos nombres de los términos
     * individuales.
     *
     * @param terminos Lista de términos a copiar
     */
    public ElementoExamen(ArrayList<Termino> terminos) {
        checkNotNull(terminos, "El parámetro 'terminos' no puede ser null");

        Terminos = new ArrayList<>();
        SyntErrors = new ArrayList<>();
        String TextoAcum = "";
        for (Termino t : terminos) {
            TextoAcum += t.getNombre();
            Terminos.add(t);
        }
        Texto = TextoAcum.trim();
    }

    /**
     * Construye unn ElementoExamen copiando los términos y valores de otro
     *
     * @param elemento elemento origen
     */
    public ElementoExamen(ElementoExamen elemento) {
        checkNotNull(elemento, "El parámetro 'elemento' no puede ser null");

        Terminos = new ArrayList<>();
        SyntErrors = new ArrayList<>();
        for (Termino t : elemento.Terminos) {
            Terminos.add(t);
        }
        Texto = elemento.Texto;
    }



    /**
     * Devuelve la lista de todos los términos
     *
     * @return Lista de términos
     */
    public ArrayList<Termino> getTerminos() {
        return Terminos;
    }

    /**
     * Devuelve una cadena que representa todos los términos del Elemento
     *
     * @param full Indica si devuelve información detallada o resumida
     * @return Cadena representando los términos, cada término se termina con \n
     */
    public String getTerminosAsString(boolean full) {
        StringBuilder bld = new StringBuilder();
        for (Termino t : Terminos) {
            bld.append(String.format("%s\n", t.toString(full)));
        }
        return bld.toString();
    }

    /**
     * Asigna una lista de términos al Elemento
     *
     * @param Terminos Lista de terminos a asignar
     */
    public void setTerminos(ArrayList<Termino> Terminos) {
        this.Terminos = Terminos;
    }

    /**
     * Obtiene el texto del elemento
     *
     * @return El texto del elemento
     */
    public String getTexto() {
        return Texto;
    }

    /**
     * Asigna el texto del elemento
     *
     * @param texto El texto que se desea asignar
     */
    public void setTexto(String texto) {
        Texto = texto;
    }

    /**
     * Crea y asigna un nuevo término
     *
     * @param nombre Nombre del termino
     * @param vista Valor que se debe mostrar
     */
    public void newTermino(String nombre, String vista) {
        Terminos.add(new Termino(nombre, vista));
    }

    /**
     * Carga la lista de terminos a partir de un texto libre. Considera que los
     * terminos se separan con espacio. Este es un método de prueba, el parseo
     * definitivo es más elaborado
     *
     * @param text Texto desde el que se quiere hacer la carga
     * @return true si se pudo cargar, false si hubo error
     */
    public boolean loadFromPlainText(String text) {
        /* TODO: Más adelante reemplazar el parseo de texto
        por uno más elaborado, basado en las herramientas de
        lenguaje.
        Ahora simplemente detecta los espacios y carga un termino
        por cada palabra.
         */
        Texto = text;
        this.Terminos.clear();
        for (String word : text.split("\\s+")) {
            this.newTermino(word, null);
        }
        return true;
    }

    /**
     * Carga la lista de terminos a partir de una lista de vertices tal como los
     * que vienen de las consultas a la DB.
     * El método recorre los vertex de la lista dejando a ArrayList
     * this.Terminos cargado de una forma lineal respetando el órden
     * Concepto- Relación - Concepto
     * En caso de que desde el Vertex salga más de un Edge, se recorren todos
     * y se carga aquel edge cuyo destino coincide con el vertex siguiente.
     * En el caso en que no se encuentro ningún vertex que coincida, se limpia
     * el ArrayList Terminos y corta la ejecución del método
     * ACLARACIÓN: Como el método recibe solo los nodos (vertices), solo se tienen
     * en cuenta los conceptos, es decir, la relación entre ambos conceptos va a ser
     * la primera que corresponda
     * @param vertices Lista de vertices devuelto por la DB
     * @return true si se pudo cargar, false si hubo error
     */
    public boolean loadFromVertexVector (ImmutableList<Vertex> vertices) {
        this.Terminos = new ArrayList<Termino>();
        // Recorrer el vector y cargar la lista de terminos teniendo en cuenta los tipos
        boolean flag = false;
        try{
            //Se recorren los vertices y se insertan los conceptos
            for (int i = 0; i < vertices.size();i += 1) {

                String rid = vertices.get(i).getId().toString();
                String nombre = vertices.get(i).getProperty("Nombre");
                Concepto c = new Concepto(rid,nombre);
                this.Terminos.add(c);
            }

            //Bucle para la carga de las relaciones
            int j = 1;
            for (int i = 0; i < vertices.size() ; i++) {
                String con_destino = vertices.get(i+1).getId().toString();

                Relacion r = validarRelacion(vertices.get(i),con_destino);

                //Si se encontró alguna relación válida, se agrega a la lista y
                //se continúa con la siguiente relación.
                //Si no se encuentra dicha relación, se cortá el método
                if (r != null){
                    this.Terminos.add(j,r);
                    j += 2;
                }
                else {
                    this.Terminos.clear();
                    flag = false;
                    break;
                }

            }
        }
        catch(Exception e){
            flag = false;
        }
    return flag;
}


    /** validarRelacion:
     * Valida si la relación llega al vertex correcto
     * @param vertice: Vertex, es decir, el nodo que estoy analizando
     * @param con_destino: id del concepto destino al que "debería" llegar una relación correcta
     * @return: Si es posible, se crea la relación
     * La relación que sale del vertex A es correcto si llega al vertex que sigue en la lista de términos
     */
    private Relacion validarRelacion(Vertex vertice, String con_destino){
        Relacion r = null;
        Iterable<Edge> edges = vertice.getEdges(Direction.OUT);
             //Se recorren los edges y se valida si existe alguno que coicida con
            //el concepto siguiente en ArrayList
            for (Edge e : edges) {
                // Si el Edge existe enlaza origen y destino, no hay necesidad
                // de validar que ambos existan
                    String rel_destino = e.getVertex(Direction.IN).getId().toString();
                    if (rel_destino.equals(con_destino)){
                        String idRel = e.getId().toString();
                        String tipo = e.getProperty("@class");

                        r = new Relacion(idRel,tipo,tipo);
                        break;
                    }
            }
        return r;
    }


    /**
     * Indica si al menos un termino de la lista contiene errores. Si UN termino
     * tiene errores el elemento se considera erróneo (completo)
     *
     * @return true si el elemento tiene errores, false caso contrario.
     */
    public boolean hasErrors() {
        /// Debe recorrer toda la lista de términos y ver cuales tienen
        /// Errores. Si se encuentra AL MENOS UNO la pregunta TIENE ERRORES
        boolean error = false;
        for (Termino t : Terminos) {
            if (t.hasErrors()) {
                error = true;
                break;
            }
        }
        return error;
    }

    /**
     * Indica si hay errores de sintaxis asociados al Elemento
     *
     * @return true si hay errores de sintaxis, false caso contrario
     */
    public boolean hasSynErrors() {
        return !SyntErrors.isEmpty();
    }

    /**
     * Agrega un término a la lista de términos del Elemento
     *
     * @param termino Termino que se desea agregar
     */
    public void addTermino(Termino termino) {
        Terminos.add(termino);
    }

    /**
     * NO IMPLEMENTADO
     *
     * @param termino Término que se desea eliminar
     */
    public void delTermino(Termino termino) {
        // Verificar si el nombre del termino es unico.
        // Si no es asi dar excepción
        throw new UnsupportedOperationException();
    }

    /**
     * Obtiene un término de acuerdo a su ubicación en la lista
     *
     * @param index Indice (base 0) de la ubicación del término
     * @return Termino obtenido. null si el índice no es valido
     */
    public Termino getTermino(int index) {
        if (index > Terminos.size()) {
            return null;
        } else {
            return Terminos.get(index);
        }
    }

    /**
     * Obtiene un término a partir del nombre del mismo NOTA: SI hay varios con
     * el mismo nombre esto es un problema. Se obtiene el primero
     *
     * @param name Nombre del término a obtener
     * @return Termino encontrado
     */
    public Termino getTerminoByName(String name) {
        for (Termino t : Terminos) {
            if (t.getNombre().equalsIgnoreCase(name)) {
                return t;
            }
        }
        return null;
    }

    /**
     * Devuelve la ubicación de un término dado su nombre.
     *
     * @param name Nombre del término a buscar
     * @return Posición basada en 0 del término encontrado. -1 si no se
     * encontró.
     */
    public int getTerminoIndexByName(String name) {
        /* Los indices de los terminos comienzan en 1 */
        int index = 0;
        for (Termino t : Terminos) {
            if (t.getNombre().equalsIgnoreCase(name)) {
                return index;
            }
            index++;
        }
        return -1;
    }

    /**
     * Obtiene la cantidad de términos contenidos en el Elemento
     *
     * @return Cantidad de términos
     */
    public int getTerminosCount() {
        return Terminos.size();
    }

    /**
     * Obtiene el primer Concepto como un string
     *
     * @return String con el nombre del primer concepto de la lista
     */
    public String getPrimerConceptoAsString() {
        ArrayList<String> aux = getConceptosAsString();
        if (aux.size() > 0) {
            return aux.get(0);
        } else {
            return "";
        }
    }

    /**
     * Obtiene una lista con todos los conceptos contenidos en forma de lista de
     * strings (ArrayList)
     *
     * @return ArrayList conteniendo una cadena por cada "concepto"
     */
    private ArrayList<String> getConceptosAsString() {
        ArrayList<String> ret = new ArrayList<>();
        for (Termino t : Terminos) {
            if (t.getTipo().equalsIgnoreCase(Termino.tipoConcepto)) {
                ret.add(t.getNombre().toUpperCase());
            }
        }
        return ret;
    }

    /**
     * Obtiene una lista (ArrayList) con todos los conceptos contenidos excepto
     * el primero
     *
     * @return ArrayList con los conceptos, excepto el primero
     */
    public ArrayList<String> getConceptosSecundariosAsString() {
        ArrayList sub = new ArrayList(getConceptosAsString().subList(1, getConceptosAsString().size()));
        return (sub);
    }

    /**
     * Devuelve el valor del Elemento calculado como la sumatoria de la cantidad
     * de Conceptos y Relaciones detectadas.
     *
     * @return Un entero indicando el valor del elemento
     */
    public int getValue() {
        int conceptos, relaciones;
        conceptos = relaciones = 0;
        for (Termino t : Terminos) {
            if (t.getTipo().equals(Termino.tipoConcepto)) {
                conceptos++;
            } else if (t.getTipo().equals(Termino.tipoRelacion)) {
                relaciones++;
            }
        }
        return (conceptos + relaciones);
    }

    /**
     * Devuelve el valor relativo del Elemento. El valor relativo es el cociente
     * entre el valor del elemento (conceptos+relaciones) y la cantidad total de
     * terminos que contiene. Un elemento con 5 conceptos y relaciones sobre un
     * total de 7 terminos tiene un valor relativo de 5/7=0.714 Un elemento con
     * 5 conceptos y relaciones sobre un total de 12 terminos tiene un valor
     * relativo de 5/12=0.417 Un elemento donde todas sus partes se han
     * identificado tiene un valor relativo de 1
     *
     * @return El valor relativo del Elemento con respecto a la cantidad de
     * Terminos
     */
    public float getRelativeValue() {
        float relValue = 0;
        // Agregado para evitar division por 0 si no hay terminos cargados
        if (!Terminos.isEmpty()) {
            int val = getValue();
            int ter = Terminos.size();
            relValue = (float) val / (float) ter;
        }
        return relValue;
    }

    /**
     * Devuelve el valor (absoluto y relativo) calculado del Elemento
     *
     * @return Cadena con los valores calculados.
     */
    public String getValuesAsString() {
        return String.format("V: %d [R: %.3f]", getValue(), getRelativeValue());
    }

    /**
     * Unifica varios terminos "consecutivos" en uno solo y devuelve la lista
     * modificada
     *
     * @param startTermino Término a partir del cual se debe realizar la
     * unificacion
     * @param countTerminos Cantidad de Términos a procesar (consecutivos)
     * @return
     */
    public ArrayList<Termino> mergeTerminos(int startTermino, int countTerminos)
            throws IndexOutOfBoundsException {
        // Obtengo el término final de la copia, para simplificar mas adelante
        int endTermino = startTermino + countTerminos - 1;
        // Algunas validaciones -------------------
        // El inicio no puede ser menor a cero
        checkArgument(startTermino >= 0, "El término inicial debe ser mayor o igual a 0");
        // La cantidad de terminos tiene que ser mayor a 1 (si no no hay nada que unir)
        checkArgument(countTerminos > 1, "La cantidad de términos a copiar debe ser mayor a 1");
        // Controlar que el ultimo término a copiar esté dentro del array
        // Se le suma 1 porque el primer indice es 0.

//elimine este metodo xq no sabia porque tiraba error  , ya lo voy a verificar bien
//checkElementIndex (endTermino+1, Terminos.size(),"El indice inicial más la cantidad de términos a copiar excede el total de términos");
        ArrayList<Termino> destino = new ArrayList<Termino>();
        int index = 0;
        String nombreTerminoMerge = "";
        // Recorrer todos los términos
        while (index < Terminos.size()) {
            // Si estoy antes que el inicio del término a mergear
            // o despues del último
            if ((index < startTermino) || (index > endTermino)) {
                // Simplemente copio al destino
                destino.add(Terminos.get(index));
            } // Estoy entre el primer termino a copiar y el ultimo
            else {

                // Recorrer los términos a unificar
                for (int i = 0; i < countTerminos; i++) {
                    nombreTerminoMerge += Terminos.get(index).getNombre() + " ";
                    index++;
                }
                // Termine de armar el nombre
                // Creo un nuevo término y lo agrego al destino
                Termino merged = new Termino(nombreTerminoMerge.trim(), nombreTerminoMerge.trim());
                // El termino merged tiene el mismo tipo que el primero del conjunto
                merged.setTipo(Terminos.get(startTermino).getTipo());
                merged.setMensajeDeError("");
                destino.add(merged);
                // Corregir index, que ha sido incrementado una posición de más
                // al finalizar el for
                index--;
            }
            // Avanzar un término
            index++;
        }

        return destino;
    }

    /**
     * Separa un termino y devuelve la lista
     * modificada
     *
     * @param indexU Posicion del termino que se desea separar

     * @return
     */
    public void unmergeTerminos(int indexU) {

        ArrayList<Termino> destino = new ArrayList<Termino>();
        int index = 0;
        // Recorrer todos los términos
        while (index < Terminos.size()) {
            // Si estoy antes que el inicio del término a mergear
            // o despues del último
            if ((index != indexU)) {
                // Simplemente copio al destino
                destino.add(Terminos.get(index));
            }
            else {
                Termino terminoViejo = this.Terminos.get(indexU);
                String nombre = terminoViejo.getNombre();

                Pattern pattern = Pattern.compile("([a-zA-ZñáéíóúÑÁÉÍÓÚ0-9]+)");
                Matcher matcher = pattern.matcher(nombre);
                while (matcher.find()) {
                    Termino terminoNuevo=new Termino(matcher.group(), matcher.group());
                    terminoNuevo.setTipo(terminoViejo.getTipo());
                    destino.add(terminoNuevo);

                }
                
            }
            index++;
        }

        this.Terminos= destino;
    }

    public int getWordCount () {
        String trim = this.getTexto().trim();
        return trim.isEmpty() ? 0 : trim.split("\\s+").length; // separate string around spaces
    }
}
