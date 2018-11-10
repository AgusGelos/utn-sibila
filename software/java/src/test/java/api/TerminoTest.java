/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import org.junit.Test;
import static org.junit.Assert.*;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *
 * @author Martin
 */
public class TerminoTest
{
    
    public TerminoTest()
    {
    }

    /**
     * Test of setTipo method, of class Termino.
     */
    @Test
    public void testSetTipo()
    {
        System.out.println("setTipo");
        String Tipo = "";
        Termino instance = null;
        instance.setTipo(Tipo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setAccion method, of class Termino.
     */
    @Test
    public void testSetAccion()
    {
        System.out.println("setAccion");
        String Accion = "";
        Termino instance = null;
        instance.setAccion(Accion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setErrorAccion method, of class Termino.
     */
    @Test
    public void testSetErrorAccion()
    {
        System.out.println("setErrorAccion");
        String Error = "";
        Termino instance = null;
        instance.setErrorAccion(Error);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPeso method, of class Termino.
     */
    @Test
    public void testSetPeso()
    {
        System.out.println("setPeso");
        double Peso = 0.0;
        Termino instance = null;
        instance.setPeso(Peso);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTipo method, of class Termino.
     */
    @Test
    public void testGetTipo()
    {
        System.out.println("getTipo");
        Termino instance = null;
        String expResult = "";
        String result = instance.getTipo();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAccion method, of class Termino.
     */
    @Test
    public void testGetAccion()
    {
        System.out.println("getAccion");
        Termino instance = null;
        String expResult = "";
        String result = instance.getAccion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getErrorAccion method, of class Termino.
     */
    @Test
    public void testGetErrorAccion()
    {
        System.out.println("getErrorAccion");
        Termino instance = null;
        String expResult = "";
        String result = instance.getErrorAccion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPeso method, of class Termino.
     */
    @Test
    public void testGetPeso()
    {
        System.out.println("getPeso");
        Termino instance = null;
        double expResult = 0.0;
        double result = instance.getPeso();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Termino.
     */
    @Test
    public void testToString()
    {
        System.out.println("toString");
        Termino instance = null;
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toXML method, of class Termino.
     */
    @Test
    public void testToXML() throws Exception
    {
        System.out.println("toXML");
        Termino instance = new Termino ("Prueba","Prueba de conversion XML");
        String expResult = "";
        String result = instance.toXML(false);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    @Test
    public void testGetXML() throws Exception
    {
        System.out.println("toXML");
        Termino instance = new Termino ("Prueba","Prueba de conversion XML");
        String expResult = "";
        Document result = instance.getXML();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of Validate method, of class Termino.
     */
    @Test
    public void testValidate()
    {
        System.out.println("Validate");
        Termino instance = null;
        boolean expResult = false;
        boolean result = instance.Validate();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toXML method, of class Termino.
     */
    @Test
    public void testToXML_0args() throws Exception
    {
        System.out.println("toXML");
        Termino instance = null;
        String expResult = "";
        String result = instance.toXML();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toXML method, of class Termino.
     */
    @Test
    public void testToXML_boolean() throws Exception
    {
        System.out.println("toXML");
        boolean compact = false;
        Termino instance = null;
        String expResult = "";
        String result = instance.toXML(compact);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toElement method, of class Termino.
     */
    @Test
    public void testToElement() throws Exception
    {
        System.out.println("toElement");
        Termino instance = new Termino ("Prueba","Segunda");
        Element expResult = null;
        Element result = instance.toElement();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
