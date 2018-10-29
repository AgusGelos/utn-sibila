/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptmanager;

import com.google.common.base.CharMatcher;

/**
 * La clase modela una relacion compleja, es decir una relacion que contiene mas
 * de una palabra. Se construye pasando por parámetro una Relacion normal y
 * automaticamente parsea la cantidad de palabras y la asigna a la propiedad
 * Orden
 * Las palabras se detectan contando la cantidad de mayúsculas ya que no están 
 * separadas. Una relación de ejemplo sería CompuestoPor
 * @author Martin
 */
public class RelacionCompleja
{
    private Relacion Relacion;
    private int Orden;

    /**
     * Constructor. Utiliza Guava para detectar cuantas letras capitalizadas
     * hay en el Nombre del concepto y cuenta la cantidad de palabras.
     * @param relacion Relación base que debe ser analizada
     */
    public RelacionCompleja (Relacion relacion)
    {
        Relacion = relacion;
        //String Nombre = relacion.getNombre();
        String Tipo = relacion.getTipo();
        Orden = CharMatcher.JAVA_UPPER_CASE.retainFrom(Tipo).length();
    }

    /**
     * Obtiene la relación base
     * @return 
     */
    public Relacion getRelacion()
    {
        return Relacion;
    }

    /**
     * Obtiene el orden de complejidad de la relación
     * @return 
     */
    public int getOrden()
    {
        return Orden;
    }
}