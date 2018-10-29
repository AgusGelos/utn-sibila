/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptmanager;

/**
 * La clase modela un concepto complejo, es decir un concepto que contiene mas
 * de una palabra. Se construye pasando por parámetro un Concepto normal y
 * automaticamente parsea la cantidad de palabras y la asigna a la propiedad
 * Orden
 * @author Martin
 */
public class ConceptoComplejo
{
    private Concepto Concepto;
    private int Orden;

    /**
     * Constructor. Recibe y parsea un Concepto para determinar si es complejo
     * y que orden de complejidad tiene.
     * @param concepto Complejo base a evaluar
     */
    public ConceptoComplejo (Concepto concepto)
    {
        Concepto = concepto;
        Orden = concepto.getNombre().split(" ").length;
    }

    /**
     * Obtiene el Concepto base
     * @return 
     */
    public Concepto getConcepto()
    {
        return Concepto;
    }

    /**
     * Obtiene el Orden de complejidad del concepto
     * @return 
     */
    public int getOrden()
    {
        return Orden;
    }
}
