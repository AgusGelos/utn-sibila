/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package langtools;

import ortografia.LangTools;
import api.ElementoExamen;
import api.Pregunta;
import api.Respuesta;
import java.io.IOException;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class LangToolsTest
{
    
    public LangToolsTest()
    {
    }

    /**
     * Test of CheckPregunta method, of class LangTools.
     */
    @Test
    public void testCheckPregunta() throws IOException
    {
        System.out.println("CheckPregunta");
        Pregunta pregunta = new Pregunta ("Un ovjeto es una hentidad");
        LangTools instance = new LangTools();
        Pregunta expResult = null;
        Pregunta result = (Pregunta) instance.CheckElementoExamen(pregunta);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of CheckRespuesta method, of class LangTools.
     */
    @Test
    public void testCheckRespuesta() throws Exception
    {
        System.out.println("CheckRespuesta");
        Respuesta respuesta = new Respuesta("Los objetos tienen atributos y envían mensajes");
        LangTools instance = new LangTools();
        Respuesta expResult = null;
        Respuesta result = instance.CheckRespuesta(respuesta,null,null);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
