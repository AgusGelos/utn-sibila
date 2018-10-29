/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ortografia;

import api.Respuesta;
import api.Termino;
import conceptmanager.ConceptManager;
import java.io.IOException;

/**
 *
 * @author fedeb
 */
public class OrtographyManager {
    
    private ConceptManager cm;
    
    public OrtographyManager(ConceptManager cm){
        this.cm = cm;
    }
    
    /**
     * Valida el texto de la respuesta pasada como parámetro.
     * 
     * Separa términos
     * Analiza ortográficamente la respuesta
     * Carga los errores encontrados en la respuesta
     * Cambia los términos plurales en singulares
     * 
     * @param respuesta
     * @return respuesta con los términos cargados en singular y con los errores
     * cargados (si se encuentran)
     * @throws IOException 
     */
    public Respuesta corregirRespuesta(Respuesta respuesta) throws IOException {

        LangTools lt = new LangTools();
        respuesta = lt.CheckRespuesta(respuesta, cm.getConceptosComplejos(), cm.getRelacionesComplejas());
        
        return respuesta;
    }
    
}
