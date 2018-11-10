/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracion;

import academico.DocentesManager;
import academico.Docente;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class DocentesMgrTest
{
    
    public DocentesMgrTest()
    {
    }
    
    @BeforeClass
    public static void setUpClass()
    {
    }
    
    @AfterClass
    public static void tearDownClass()
    {
    }
    
    @Before
    public void setUp()
    {
    }
    
    @After
    public void tearDown()
    {
    }

    /**
     * Test of getDocentes method, of class DocentesManager.
     */
    @Test
    public void testGetDocentes()
    {
        System.out.println("getDocentes");
        DocentesManager instance = new DocentesManager ("remote:localhost/PPR","admin","admin");
        List<Docente> result = instance.getDocentes();
        assertNotNull(result);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of addDocente method, of class DocentesManager.
     */
    @Test
    public void testAddDocente()
    {
        System.out.println("addDocente");
        Docente d = new Docente ()
                {
                    {
                    Nombre = "Martin";
                    Apellido = "Casatti";
                    Legajo = "26207";
                    }
                };
        DocentesManager instance = new DocentesManager ("remote:localhost/PPR","admin","admin");
        boolean expResult = true;
        boolean result = instance.addDocente(d);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of delDocente method, of class DocentesManager.
     */
    @Test
    public void testDelDocente()
    {
        System.out.println("delDocente");
        Docente d = new Docente()
                {
                    {
                    IdNodo = "#23:3";
                    }
                };
        DocentesManager instance = new DocentesManager ("remote:localhost/PPR","admin","admin");
        boolean expResult = true;
        boolean result = instance.delDocente(d);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of updDocente method, of class DocentesManager.
     */
    @Test
    public void testUpdDocente()
    {
        System.out.println("updDocente");
        
        DocentesManager instance = new DocentesManager ("remote:localhost/PPR","admin","admin");
        
        Docente old = instance.getDocenteByLegajo("26207");
        Docente upd = new Docente ()
                {
                    {
                        Nombre = "Ing. Martín";
                        Apellido = "Casatti";
                        Legajo = "26207-1";
                    }
                };
        
        if (old == null)
            fail ("No se encontró el docente a modificar");
        
        boolean expResult = true;
        boolean result = instance.updDocente(old, upd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of getDocenteByLegajo method, of class DocentesManager.
     */
    @Test
    public void testGetDocenteByLegajo()
    {
        System.out.println("getDocenteByLegajo");
        String legajo = "027";
        DocentesManager instance = new DocentesManager ("remote:localhost/PPR","admin","admin");
        Docente expResult = null;
        Docente result = instance.getDocenteByLegajo(legajo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
    
}
