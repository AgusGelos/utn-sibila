/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptmanager;

import java.util.Date;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class ConceptoTest {
    
    public ConceptoTest() {
    }

    /**
     * Test of setNombre method, of class Concepto.
     */
    @Test
    public void testSetNombre() {
        System.out.println("setNombre");
        String Nombre = "";
        Concepto instance = new Concepto();
        instance.setNombre(Nombre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setId method, of class Concepto.
     */
    @Test
    public void testSetId() {
        System.out.println("setId");
        String Id = "";
        Concepto instance = new Concepto();
        instance.setId(Id);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setUsuario method, of class Concepto.
     */
    @Test
    public void testSetUsuario() {
        System.out.println("setUsuario");
        String Usuario = "";
        Concepto instance = new Concepto();
        instance.setUsuario(Usuario);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setActualizado method, of class Concepto.
     */
    @Test
    public void testSetActualizado() {
        System.out.println("setActualizado");
        Date Actualizado = null;
        Concepto instance = new Concepto();
        instance.setActualizado(Actualizado);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getNombre method, of class Concepto.
     */
    @Test
    public void testGetNombre() {
        System.out.println("getNombre");
        Concepto instance = new Concepto();
        String expResult = "";
        String result = instance.getNombre();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getId method, of class Concepto.
     */
    @Test
    public void testGetId() {
        System.out.println("getId");
        Concepto instance = new Concepto();
        String expResult = "";
        String result = instance.getId();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUsuario method, of class Concepto.
     */
    @Test
    public void testGetUsuario() {
        System.out.println("getUsuario");
        Concepto instance = new Concepto();
        String expResult = "";
        String result = instance.getUsuario();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getActualizado method, of class Concepto.
     */
    @Test
    public void testGetActualizado() {
        System.out.println("getActualizado");
        Concepto instance = new Concepto();
        Date expResult = null;
        Date result = instance.getActualizado();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
