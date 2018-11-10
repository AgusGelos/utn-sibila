/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class ElementoExamenTest
{
    
    public ElementoExamenTest()
    {
    }

    /**
     * Test of getTerminos method, of class ElementoExamen.
     */
    @Test
    public void testGetTerminos()
    {
        System.out.println("getTerminos");
        ElementoExamen instance = null;
        ArrayList<Termino> expResult = null;
        ArrayList<Termino> result = instance.getTerminos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTerminosAsString method, of class ElementoExamen.
     */
    @Test
    public void testGetTerminosAsString()
    {
        System.out.println("getTerminosAsString");
        boolean full = false;
        ElementoExamen instance = null;
        String expResult = "";
        String result = instance.getTerminosAsString(full);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTerminos method, of class ElementoExamen.
     */
    @Test
    public void testSetTerminos()
    {
        System.out.println("setTerminos");
        ArrayList<Termino> Terminos = null;
        ElementoExamen instance = null;
        instance.setTerminos(Terminos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTexto method, of class ElementoExamen.
     */
    @Test
    public void testGetTexto()
    {
        System.out.println("getTexto");
        ElementoExamen instance = null;
        String expResult = "";
        String result = instance.getTexto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setTexto method, of class ElementoExamen.
     */
    @Test
    public void testSetTexto()
    {
        System.out.println("setTexto");
        String texto = "";
        ElementoExamen instance = null;
        instance.setTexto(texto);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newTermino method, of class ElementoExamen.
     */
    @Test
    public void testNewTermino()
    {
        System.out.println("newTermino");
        String nombre = "";
        String vista = "";
        ElementoExamen instance = null;
        instance.newTermino(nombre, vista);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadFromPlainText method, of class ElementoExamen.
     */
    @Test
    public void testLoadFromPlainText()
    {
        System.out.println("loadFromPlainText");
        String text = "";
        ElementoExamen instance = null;
        boolean expResult = false;
        boolean result = instance.loadFromPlainText(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasErrors method, of class ElementoExamen.
     */
    @Test
    public void testHasErrors()
    {
        System.out.println("hasErrors");
        ElementoExamen instance = null;
        boolean expResult = false;
        boolean result = instance.hasErrors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of hasSynErrors method, of class ElementoExamen.
     */
    @Test
    public void testHasSynErrors()
    {
        System.out.println("hasSynErrors");
        ElementoExamen instance = null;
        boolean expResult = false;
        boolean result = instance.hasSynErrors();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTermino method, of class ElementoExamen.
     */
    @Test
    public void testAddTermino()
    {
        System.out.println("addTermino");
        Termino termino = null;
        ElementoExamen instance = null;
        instance.addTermino(termino);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delTermino method, of class ElementoExamen.
     */
    @Test
    public void testDelTermino()
    {
        System.out.println("delTermino");
        Termino termino = null;
        ElementoExamen instance = null;
        instance.delTermino(termino);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTermino method, of class ElementoExamen.
     */
    @Test
    public void testGetTermino()
    {
        System.out.println("getTermino");
        int index = 0;
        ElementoExamen instance = null;
        Termino expResult = null;
        Termino result = instance.getTermino(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTerminoByName method, of class ElementoExamen.
     */
    @Test
    public void testGetTerminoByName()
    {
        System.out.println("getTerminoByName");
        String name = "";
        ElementoExamen instance = null;
        Termino expResult = null;
        Termino result = instance.getTerminoByName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTerminoIndexByName method, of class ElementoExamen.
     */
    @Test
    public void testGetTerminoIndexByName()
    {
        System.out.println("getTerminoIndexByName");
        String name = "";
        ElementoExamen instance = null;
        int expResult = 0;
        int result = instance.getTerminoIndexByName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTerminosCount method, of class ElementoExamen.
     */
    @Test
    public void testGetTerminosCount()
    {
        System.out.println("getTerminosCount");
        ElementoExamen instance = null;
        int expResult = 0;
        int result = instance.getTerminosCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPrimerConceptoAsString method, of class ElementoExamen.
     */
    @Test
    public void testGetPrimerConceptoAsString()
    {
        System.out.println("getPrimerConceptoAsString");
        ElementoExamen instance = null;
        String expResult = "";
        String result = instance.getPrimerConceptoAsString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConceptosSecundariosAsString method, of class ElementoExamen.
     */
    @Test
    public void testGetConceptosSecundariosAsString()
    {
        System.out.println("getConceptosSecundariosAsString");
        ElementoExamen instance = null;
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getConceptosSecundariosAsString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValue method, of class ElementoExamen.
     */
    @Test
    public void testGetValue()
    {
        System.out.println("getValue");
        ElementoExamen instance = null;
        int expResult = 0;
        int result = instance.getValue();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRelativeValue method, of class ElementoExamen.
     */
    @Test
    public void testGetRelativeValue()
    {
        System.out.println("getRelativeValue");
        ElementoExamen instance = null;
        float expResult = 0.0F;
        float result = instance.getRelativeValue();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getValuesAsString method, of class ElementoExamen.
     */
    @Test
    public void testGetValuesAsString()
    {
        System.out.println("getValuesAsString");
        ElementoExamen instance = null;
        String expResult = "";
        String result = instance.getValuesAsString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of mergeTerminos method, of class ElementoExamen.
     */
    @Test
    public void testMergeTerminos()
    {
        System.out.println("mergeTerminos");
        String texto = "Los términos se deben unificar a partir del cuarto lugar y por tres.";
        int startTermino = 3;   // DEBEN
        int countTerminos = 3;  // DEBEN UNIFICAR A
        ElementoExamen instance = new ElementoExamen ("");
        instance.loadFromPlainText(texto);
        System.out.println(instance.getTerminosAsString(false));
        
        ArrayList<Termino> expResult = null;
        ArrayList<Termino> result = instance.mergeTerminos(startTermino, countTerminos);
        ElementoExamen merged = new ElementoExamen(result);
        System.out.println(merged.getTerminosAsString(false));
        
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
