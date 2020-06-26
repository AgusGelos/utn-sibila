/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlador;

import api.Respuesta;
import conceptmanager.ConceptManager;
import java.io.IOException;

import org.json.JSONException;
import ortografia.GrammarManager;

/**
 *
 * @author fedeb
 */
public class Controller {
    
    private ConceptManager cm;
    private GrammarManager om;
    
    private void cargarModulos(){
        if (cm == null){
            String url = String.format("remote:%s/PPR",web.Config.HOST_ORIENTDB);
            //ConceptManager cm = new ConceptManager("remote:localhost/PPR", "admin", "admin");
            cm = new ConceptManager(url, "admin", "admin");
        }
        if (om == null){
            om = new GrammarManager(cm);
        }
    }
    
    /**
     * Calcula la calificación de una respuesta. Recibe la respuesta candidata
     * y la base, calcula el peso de ambas y luego calcula la calificación de la
     * candidata con respecto a la base
     * 
     * @param candidata respuesta del alumno
     * @param base respuesta del profesor
     * @return calificación de la respuesta candidata
     */
    public Double calcularCalificaciónRespuesta(String candidata, String base) throws IOException{
        Double pesoBase = this.calcularPesoRespuesta(base);

        Double pesoCandidata = this.calcularPesoRespuesta(candidata, base);

        Double calificacion = pesoCandidata / pesoBase;
        
        return calificacion;
    }
    
    
    /**
     * Calcula el peso de una respuesta ingresada por el alumno.
     * Recibe el texto plano de la respuesta y retorna su peso.
     * 
     * @param res ingresada por el alumno. Solo el texto
     * @return Peso de la respuesta
     */
    public Double calcularPesoRespuesta(String res) throws IOException {
        this.cargarModulos();
        Double peso = 0.0;
        
        if (res.length() > 0){
            Respuesta respuesta = new Respuesta(res);
            
            //Validamos ortografía
            respuesta = om.corregirRespuesta(respuesta);
            
            //Clasificamos los términos
            respuesta = cm.evaluarRespuesta(respuesta);
            
            //Detectamos ruta
            respuesta = cm.findRouteRespuesta(respuesta);
            
            //Calculamos el peso de la respuesta
            //(Solo si findRoute encontró el primer concepto
            // si no no se puede evaluar)
            if (respuesta != null)
                peso = respuesta.calcularPeso(respuesta);
        }
        
        return peso;
    }
    
    
    /**
     * Calcula el peso de una respuesta ingresada por el alumno.
     * Recibe el texto plano de la respuesta y retorna su peso.
     * 
     * @param candidata respuesta ingresada candidata (la que ingresa el estudiante)
     * @param base respuesta base (del profesor). Se usa como punto de comparación
     * @return Peso de la respuesta candidata con respecto a la base
     */
    public Double calcularPesoRespuesta(String candidata, String base) throws IOException {
        this.cargarModulos();
        Double peso = 0.0;
        
        if (candidata.length() > 0 && base.length() > 0){
            Respuesta respuestaCandidata = new Respuesta(candidata);
            Respuesta respuestaBase = new Respuesta(base);
            
            //Validamos ortografía
            respuestaCandidata = om.corregirRespuesta(respuestaCandidata);
            respuestaBase = om.corregirRespuesta(respuestaBase);
            
            //Clasificamos los términos
            respuestaCandidata = cm.evaluarRespuesta(respuestaCandidata);
            respuestaBase = cm.evaluarRespuesta(respuestaBase);
            
            //Las hacemos delta-comparable buscando la ruta 
            respuestaCandidata = cm.findRouteRespuesta(respuestaCandidata);
            respuestaBase = cm.findRouteRespuesta(respuestaBase);            
            
            //Calculamos el delta de la candidata con respecto a la base
            respuestaCandidata = respuestaBase.getDeltas(respuestaCandidata);
            
            //Calculamos el peso de la respuesta
            peso = respuestaCandidata.calcularPeso(respuestaBase);
        }
        
        return peso;
    }

}
    
    
   

