/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package api;

import java.util.ArrayList;
import javax.xml.parsers.ParserConfigurationException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class RespuestaTest
{
    
    public RespuestaTest()
    {
    }

    /**
     * Test of getPeso method, of class Respuesta.
     */
    @Test
    public void testGetPeso()
    {
        System.out.println("getPeso");
        Respuesta instance = new Respuesta();
        double expResult = 0.0;
        double result = instance.getPeso();
        assertEquals(expResult, result, 0.0);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getSecuencia method, of class Respuesta.
     */
    @Test
    public void testGetSecuencia()
    {
        System.out.println("getSecuencia");
        Respuesta instance = new Respuesta();
        int expResult = 0;
        int result = instance.getSecuencia();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setPeso method, of class Respuesta.
     */
    @Test
    public void testSetPeso()
    {
        System.out.println("setPeso");
        double Peso = 0.0;
        Respuesta instance = new Respuesta();
        instance.setPeso(Peso);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of setSecuencia method, of class Respuesta.
     */
    @Test
    public void testSetSecuencia()
    {
        System.out.println("setSecuencia");
        int Secuencia = 0;
        Respuesta instance = new Respuesta();
        instance.setSecuencia(Secuencia);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTermino method, of class Respuesta.
     */
    @Test
    public void testAddTermino()
    {
        System.out.println("addTermino");
        Respuesta instance = new Respuesta();
        for (int i = 1; i<5; i++)
        {
            Termino termino = new Termino ("Termino"+String.valueOf(i),String.valueOf(i));
            instance.addTermino(termino);
        }
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delTermino method, of class Respuesta.
     */
    @Test
    public void testDelTermino()
    {
        System.out.println("delTermino");
        Termino termino = null;
        Respuesta instance = new Respuesta();
        instance.delTermino(termino);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toXML method, of class Respuesta.
     */
    @Test
    public void testToXML() throws Exception
    {
        System.out.println("toXML");
        Respuesta instance = new Respuesta();
        for (int i = 1; i<5; i++)
        {
            Termino termino = new Termino ("Termino"+String.valueOf(i),String.valueOf(i));
            instance.addTermino(termino);
        }
        String resultado = instance.toXML(false);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toXML method, of class Respuesta.
     */
    @Test
    public void testToXML_0args() throws Exception
    {
        System.out.println("toXML");
        Respuesta instance = new Respuesta();
        String expResult = "";
        String result = instance.toXML();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toXML method, of class Respuesta.
     */
    @Test
    public void testToXML_boolean() throws Exception
    {
        System.out.println("toXML");
        boolean compact = false;
        Respuesta instance = new Respuesta();
        String expResult = "";
        String result = instance.toXML(compact);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTerminos method, of class Respuesta.
     */
    @Test
    public void testGetTerminos()
    {
        System.out.println("getTerminos");
        Respuesta instance = new Respuesta();
        ArrayList<Termino> expResult = null;
        ArrayList<Termino> result = instance.getTerminos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of newTermino method, of class Respuesta.
     */
    @Test
    public void testNewTermino()
    {
        System.out.println("newTermino");
        String nombre = "";
        String vista = "";
        Respuesta instance = new Respuesta();
        instance.newTermino(nombre, vista);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Respuesta.
     */
    @Test
    public void testToString_0args()
    {
        System.out.println("toString");
        Respuesta instance = new Respuesta();
        String expResult = "";
        String result = instance.toString();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of toString method, of class Respuesta.
     */
    @Test
    public void testToString_boolean()
    {
        System.out.println("toString");
        boolean full = false;
        Respuesta instance = new Respuesta();
        String expResult = "";
        String result = instance.toStringF(full);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTerminosCount method, of class Respuesta.
     */
    @Test
    public void testGetTerminosCount()
    {
        System.out.println("getTerminosCount");
        Respuesta instance = new Respuesta();
        int expResult = 0;
        int result = instance.getTerminosCount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTermino method, of class Respuesta.
     */
    @Test
    public void testGetTermino()
    {
        System.out.println("getTermino");
        int index = 0;
        Respuesta instance = new Respuesta();
        Termino expResult = null;
        Termino result = instance.getTermino(index);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTerminoByName method, of class Respuesta.
     */
    @Test
    public void testGetTerminoByName()
    {
        System.out.println("getTerminoByName");
        String name = "atributo";
        
        Respuesta resp = new Respuesta ();
        resp.loadFromPlainText("un objeto posee atributo y envia mensaje");
        Termino result = resp.getTerminoByName(name);
        int index = resp.getTerminoIndexByName("Objeto");
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTerminoIndexByName method, of class Respuesta.
     */
    @Test
    public void testGetTerminoIndexByName()
    {
        System.out.println("getTerminoIndexByName");
        String name = "";
        Respuesta instance = new Respuesta();
        int expResult = 0;
        int result = instance.getTerminoIndexByName(name);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of loadFromPlainText method, of class Respuesta.
     */
    @Test
    public void testLoadFromPlainText()
    {
        System.out.println("loadFromPlainText");
        String text = "";
        Respuesta instance = new Respuesta();
        boolean expResult = false;
        boolean result = instance.loadFromPlainText(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getDeltas method, of class Respuesta.
     */
    @Test
    public void testGetDeltas()
    {
        System.out.println("getDeltas");
       
        Respuesta resp_base = new Respuesta();
        resp_base.loadFromPlainText("un objeto posee atributo y envia mensaje");
        Respuesta resp_cand = new Respuesta();
        resp_cand.loadFromPlainText("objeto es todo lo que posee atributo y un constructor");
        
        Respuesta deltas = resp_base.getDeltas(resp_cand);
        String str = deltas.toStringDelta();
        fail("The test case is a prototype.");
    }
    
}
