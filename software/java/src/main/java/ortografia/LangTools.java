/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortografia;

import api.ElementoExamen;
import api.Pregunta;
import api.Respuesta;
import api.Termino;
import conceptmanager.ConceptoComplejo;
import conceptmanager.RelacionCompleja;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.languagetool.JLanguageTool;
import org.languagetool.language.Spanish;
import org.languagetool.tokenizers.es.SpanishWordTokenizer;

/**
 * Herramienta para análisis de texto. Sirve principalmente para mejorar y
 * ampliar las prestaciones de LangTool que es una librería de Java
 *
 * @author Martin
 */
public class LangTools
{

    ManejoLang ml;

    /**
     * Constructor.
     */
    public LangTools()
    {
        this.ml = new ManejoLang();
    }

    public Pregunta CheckOrtografia (String text) throws IOException
    {
        ArrayList<Termino> terminos = ml.getListaDeTerminos(text);        
        Pregunta pregunta = new Pregunta (text);
        pregunta.setTerminos(terminos);
        return pregunta;
    }
    
    /**
     * Procesa una pregunta, detecta los términos y evalúa la ortografía.
     *
     * @param pregunta Pregunta a analizar
     * @return Devuelve una pregunta con los términos divididos e indicaciones
     * de la corrección ortográfica
     * @throws IOException
     */
    public ElementoExamen CheckElementoExamen(ElementoExamen elemento) throws IOException
    {
        return Tokenize(elemento);
    }

    /**
     * Procesa una respuesta, detecta los términos y evalúa la ortografía.
     *
     * @param respuesta Respuesta a analizar
     * @return Devuelve una respuesta con los términos divididos e indicaciones
     * de la corrección ortográfica
     * @throws IOException
     */
    public Respuesta CheckRespuesta(Respuesta respuesta,ArrayList<ConceptoComplejo> conceptos, ArrayList<RelacionCompleja> relaciones) throws IOException
    {
        return TokenizeRespuesta(respuesta,conceptos,relaciones);
    }
//    public Respuesta CheckRespuesta(Respuesta respuesta) {
//        ArrayList<Termino> terminos = ml.getListaDeTerminos(respuesta.getTexto());
//        respuesta.setTerminos(terminos);
//        return respuesta;
//    }

    /**
     * Recibe una pregunta , separa en terminos, luego obtiene los errores de
     * cada termino, por ultimo cambia los terminos en plural por aquellos en
     * singular
     *
     * @param aux Pregunta que se debe analizar
     * @return
     */
    private Pregunta TokenizePregunta(Pregunta aux) throws IOException
    {

        // obtengo los terminos corregidos
        ArrayList<Termino> terminos = ml.getListaDeTerminos(aux.getTexto());
        //modifico los terminos en plural por singular
        terminos = ml.cambiarPluralPorSingular(terminos);

        aux.setTerminos(terminos);
        for (Termino termino : terminos)
        {

            System.out.println(termino.getNombre());
        }
        return aux;
    }

    /**
     * Recibe una respuesta , separa en terminos, luego obtiene los errores de
     * cada termino, por ultimo cambia los terminos en plural por aquellos en
     * singular
     *
     * @param aux Respuesta que se debe analizar
     * @return
     */
    private Respuesta TokenizeRespuesta(Respuesta aux,ArrayList<ConceptoComplejo> conceptos, ArrayList<RelacionCompleja> relaciones) throws IOException
    {

        ArrayList<Termino> terminos = ml.getListaDeTerminosConComplejos(aux.getTexto(),conceptos,relaciones);
        // ArrayList<Termino> singulares = ml.cambiarPluralPorSingular(terminos);
        //terminos = ml.cambiarPluralPorSingular(terminos);
        aux.setTerminos(terminos);

        return aux;
    }

    /**
     * Recibe un elemento examen , separa en terminos, luego obtiene los errores
     * de cada termino, por ultimo cambia los terminos en plural por aquellos en
     * singular
     *
     * @param aux ElementoExamen que se debe analizar
     * @return
     */
    private ElementoExamen Tokenize(ElementoExamen aux) throws IOException
    {

        ArrayList<Termino> terminos = ml.getListaDeTerminos(aux.getTexto());
        ArrayList<Termino> singulares = ml.cambiarPluralPorSingular(terminos);
        aux.setTerminos(singulares);
        return aux;
    }
  
        /**
     * Recibe un elemento examen, los conceptos y relaciones complejas que existen en la base
     * Analiza que terminos complejos existen en el Elemento examen
     * @param aux ElementoExamen que se debe analizar, ConceptosComplejos (los que existen en la base), 
     *              RelacionesComplejas (las que existen en la base)
     * @return ArrayList con todos los terminos complejos que contiene el elementoExamen.
    */   
        public ArrayList<Termino> getTerminosComplejosDelElemento(ElementoExamen aux, ArrayList<ConceptoComplejo> conceptos, ArrayList<RelacionCompleja> relaciones)
        {
            StringBuilder respuestaSoloTexto = new StringBuilder();
            ArrayList<Termino> terminos = new ArrayList<>();

            /*Basandonos en el supuesto de que getTerminos contiene todos los terminos sin signos
            creamos una respuesta sin puntuaciones para luego comparar con los complejos de forma mas rapida.
            Por otro lado hemos visto que en la base de datos no se guarda en cammelCase sino que con espacios
            */
        for (Termino termino : aux.getTerminos()) {
            //Dejamos espacio porque los conceptos estan con espacios y en uppercase
            respuestaSoloTexto.append(termino.getNombre().toUpperCase()).append(" ");
        }
        //Buscamos primero los conceptos complejos
        for (ConceptoComplejo conceptoComplejo : conceptos) {
            if (respuestaSoloTexto.toString().contains(conceptoComplejo.getConcepto().getNombre())) {
                Termino t = new Termino(conceptoComplejo.getConcepto().getNombre().toUpperCase(), CommonsFunctions.toCamelCase(conceptoComplejo.getConcepto().getNombre()));
                //Ya sabemos que es concepto
                t.setTipo(Termino.tipoConcepto);
                terminos.add(t);
            }
        }
        //Ahora las relaciones complejas
        for (RelacionCompleja relacionCompleja : relaciones) {
            //Lo pasamos a cammelcase porque las relaciones estan en cammelcase
            if (CommonsFunctions.toCamelCase(respuestaSoloTexto.toString()).contains(relacionCompleja.getRelacion().getNombre())) {

                Termino t = new Termino(CommonsFunctions.fromCamelCase(relacionCompleja.getRelacion().getNombre()).toUpperCase(), relacionCompleja.getRelacion().getNombre());
                //Ya sabemos que es concepto
                t.setTipo(Termino.tipoRelacion);
                terminos.add(t);
            }
        }
        return terminos;
            
        }
        
        
        
}
