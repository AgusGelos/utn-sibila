/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptmanager;

import api.Termino;
import java.util.Date;

/**
 *
 * @author Martin
 */
public class Concepto extends Termino
{
   // String Nombre;
    String Id;
    
    /* TODO: Implementar el manejo de estas dos propiedades */
    String Usuario;
    Date Actualizado;

    public Concepto(String nombre) {
        super(nombre);
    }

    public Concepto ()
    {
        super(null);
        this.Id = null;
    }
    
    public Concepto (String Id, String Nombre)
    {
        super(Nombre.toUpperCase());
        this.Id = Id;
        super.setTipo(Termino.tipoConcepto);
    }
    
    

    /***
     * Necesario para hacer downcast y crear un Concepto a partir
     * de un Término (java no permite el downcast mediante asignacion
     * del tipo Concepto c = (Concepto)termino;
     * @param termino 
     */
    public Concepto(Termino termino){
        super(termino.getNombre());
        super.setTipo(Termino.tipoConcepto);
    }
    
        public void setId(String Id)
    {
        this.Id = Id;
    }

    public void setUsuario(String Usuario)
    {
        this.Usuario = Usuario;
    }

    public void setActualizado(Date Actualizado)
    {
        this.Actualizado = Actualizado;
    }

    public String getId()
    {
        return Id;
    }

    
    public String getUsuario()
    {
        return Usuario;
    }

    public Date getActualizado()
    {
        return Actualizado;
    }    

    /**
     * 
     * @param nombre El nombre de la equivalencia 
     * @param peso El peso de la equivalencia para el concepto
     */
    public void addEquivalencia(String nombre, float peso) {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}

