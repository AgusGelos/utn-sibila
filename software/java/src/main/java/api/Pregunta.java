/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.ArrayList;

/**
 * Modela una Pregunta. Hereda de ElementoExamen
 * @author Martin
 */
public class Pregunta extends ElementoExamen
{
    /**
     * Constructor.
     * @param texto Texto de la pregunta
     */
    public Pregunta (String texto) 
    {
        super (texto);
    }
   
    public Pregunta() {
        super ("");
    }
    /** 
     * Construye una pregunta a partir de un elemento (copia parcial)
     * @param elemento Elemento desde el cual se desean copiar los términos
     */
    public Pregunta (ElementoExamen elemento)
    {
        super (elemento.getTexto());
        Terminos.addAll(elemento.getTerminos());
    }

    @Override
    public String toString() {
        return getTexto();
    }
    
    
}

