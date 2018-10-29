/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptmanager;

import api.Termino;
import com.google.common.base.CharMatcher;

/**
 * Modela una relación entre dos conceptos
 * @author Martin
 */
public class Relacion extends Termino
{
    String Id;
    String Nombre;
    String Tipo;
    
    /**
     * Constructor. 
     * @param Id
     * @param Nombre 
     */
    public Relacion (String Id, String Nombre)
    {
        super(Nombre);
        this.Id = Id;
        this.Tipo = null;
    }

    /**
     * Constructor.
     * @param Id
     * @param Nombre
     * @param Tipo 
     */
    public Relacion (String Id, String Nombre, String Tipo)
    {
        super(Nombre);
        this.Id = Id;
        this.Tipo = Tipo;
    }

    /**
     * Obtiene el id de la relacion
     * @return 
     */
    public String getId()
    {
        return Id;
    }

    /**
     * Obtiene el Nombre de la relación
     * @return Nombre, si es null retorna Tipo
     */
    public String getNombre()
    {      
        return Nombre != null ? Nombre : Tipo;
    }

    /**
     * Obtiene el tipo de la relacion
     * @return Tipo, si es null retorna Nombre
     */
    public String getTipo()
    {
        return Tipo != null ? Tipo : Nombre;
    }
    
    /**
     * Almacena el Id de la relacion
     * @param Id 
     */
    public void setId(String Id)
    {
        this.Id = Id;
    }

    /**
     * Almacena el Nombre de la relación
     * @param Nombre 
     */
    public void setNombre(String Nombre)
    {
        this.Nombre = Nombre;
    }

    /**
     * Almacena el tipo de la relación
     * @param Tipo 
     */
    public void setTipo(String Tipo)
    {
        this.Tipo = Tipo;
    }

}


