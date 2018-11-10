/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package academico;

import api.Pregunta;
import api.Respuesta;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class AcademicManagerTest
{
    
    public AcademicManagerTest()
    {
    }

    /**
     * Test of addPregunta method, of class AcademicManager.
     */
    @Test
    public void testAddPregunta()
    {
        System.out.println("addPregunta");
        Pregunta p = new Pregunta ("¿Cambio en el método, y ahora?");
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        boolean expResult = false;
        Pregunta result = instance.addPregunta(p);
        System.out.println ("Id de la pregunta agregada:" + result.IdNodo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delPregunta method, of class AcademicManager.
     */
    @Test
    public void testDelPregunta()
    {
        System.out.println("delPregunta");
        Pregunta p = new Pregunta("");
        p.IdNodo = "#31:6";
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        boolean expResult = false;
        boolean cascade = true;
        boolean result = instance.delPregunta(p,cascade);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updPregunta method, of class AcademicManager.
     */
    @Test
    public void testUpdPregunta()
    {
        System.out.println("updPregunta");
        Pregunta old = null;
        Pregunta upd = null;
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        boolean expResult = false;
        boolean result = instance.updPregunta(old, upd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPreguntaByText method, of class AcademicManager.
     */
    @Test
    public void testGetPreguntaByText()
    {
        System.out.println("getPreguntaByText");
        String texto = "";
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        Pregunta expResult = null;
        Pregunta result = instance.getPreguntaByText(texto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPreguntaById method, of class AcademicManager.
     */
    @Test
    public void testGetPreguntaById()
    {
        System.out.println("getPreguntaById");
        String Id = "#31:2";
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        Pregunta expResult = null;
        Pregunta result = instance.getPreguntaById(Id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addRespuesta method, of class AcademicManager.
     */
    @Test
    public void testAddRespuesta()
    {
        System.out.println("addRespuesta");
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        Pregunta p = instance.getPreguntaById("#31:6");
        Respuesta r = new Respuesta ("Segunda respuesta a #31:6");

        Respuesta expResult = null;
        Respuesta result = instance.addRespuesta(r, p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delRespuesta method, of class AcademicManager.
     */
    @Test
    public void testDelRespuesta()
    {
        System.out.println("delRespuesta");
        Respuesta r = new Respuesta ("");
        r.IdNodo = "#32:1";
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        boolean expResult = false;
        boolean result = instance.delRespuesta(r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updRespuesta method, of class AcademicManager.
     */
    @Test
    public void testUpdRespuesta()
    {
        System.out.println("updRespuesta");
        Respuesta old = null;
        Respuesta upd = null;
        AcademicManager instance = null;
        boolean expResult = false;
        boolean result = instance.updRespuesta(old, upd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRespuestaByText method, of class AcademicManager.
     */
    @Test
    public void testGetRespuestaByText()
    {
        System.out.println("getRespuestaByText");
        String texto = "";
        AcademicManager instance = null;
        Respuesta expResult = null;
        Respuesta result = instance.getRespuestaByText(texto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRespuestaById method, of class AcademicManager.
     */
    @Test
    public void testGetRespuestaById()
    {
        System.out.println("getRespuestaById");
        String Id = "";
        AcademicManager instance = null;
        Respuesta expResult = null;
        Respuesta result = instance.getRespuestaById(Id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getPreguntas method, of class AcademicManager.
     */
    @Test
    public void testGetPreguntas() {
        System.out.println("getPreguntas");
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        ArrayList<Pregunta> expResult = null;
        ArrayList<Pregunta> result = instance.getPreguntas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRespuestas method, of class AcademicManager.
     */
    @Test
    public void testGetRespuestas() {
        System.out.println("getRespuestas");
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        ArrayList<Respuesta> expResult = null;
        ArrayList<Respuesta> result = instance.getRespuestas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRespuestasByPregunta method, of class AcademicManager.
     */
    @Test
    public void testGetRespuestasByPregunta() {
        System.out.println("getRespuestasByPregunta");
        Pregunta p = new Pregunta();
        p.IdNodo = "#31:1";
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        ArrayList<Respuesta> expResult = null;
        ArrayList<Respuesta> result = instance.getRespuestasByPregunta(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRespuestasCount method, of class AcademicManager.
     */
    @Test
    public void testGetRespuestasCount() {
        System.out.println("getRespuestasCount");
        Pregunta p = new Pregunta();
        p.IdNodo = "#31:4";
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        int expResult = 0;
        int result = instance.getRespuestasCount(p);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
