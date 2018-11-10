/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package administracion;

import academico.Alumno;
import academico.AlumnosManager;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class AlumnosMgrTest
{
    
    public AlumnosMgrTest()
    {
    }

    /**
     * Test of getAlumnos method, of class AlumnosManager.
     */
    @Test
    public void testGetAlumnos()
    {
        System.out.println("getAlumnos");
        AlumnosManager instance = new AlumnosManager ("remote:localhost/PPR","admin","admin");
        List<Alumno> result = instance.getAlumnos();
        assertNotNull(result);
        //fail("The test case is a prototype.");
    }

    /**
     * Test of getAlumnoByLegajo method, of class AlumnosManager.
     */
    @Test
    public void testGetAlumnoByLegajo()
    {
        System.out.println("getAlumnoByLegajo");
        String legajo = "26207";
        AlumnosManager instance = new AlumnosManager ("remote:localhost/PPR","admin","admin");
        Alumno result = instance.getAlumnoByLegajo(legajo);
        assertNotNull(result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }

    /**
     * Test of addAlumno method, of class AlumnosManager.
     */
    @Test
    public void testAddAlumno()
    {
        System.out.println("addAlumno");
        Alumno a = new Alumno ()
                {
                    {
                    Nombre = "El";
                    Apellido = "Finlandes";
                    Legajo = "100010";
                    Condicion = "L";
                    }
                };
        AlumnosManager instance = new AlumnosManager ("remote:localhost/PPR","admin","admin");
        boolean expResult = true;
        boolean result = instance.addAlumno(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of delAlumno method, of class AlumnosManager.
     */
    @Test
    public void testDelAlumno()
    {
        System.out.println("delAlumno");
        Alumno a = new Alumno()
                {
                    {
                    IdNodo = "#25:3";
                    }
                };
        AlumnosManager instance = new AlumnosManager ("remote:localhost/PPR","admin","admin");
        boolean expResult = true;
        boolean result = instance.delAlumno(a);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        //fail("The test case is a prototype.");
    }

    /**
     * Test of updAlumno method, of class AlumnosManager.
     */
    @Test
    public void testUpdAlumno()
    {
        System.out.println("updAlumno");
        
        AlumnosManager instance = new AlumnosManager ("remote:localhost/PPR","admin","admin");
        
        Alumno old = instance.getAlumnoByLegajo("26207");
        Alumno upd = new Alumno ()
                {
                    {
                        Nombre = "Ing. Martín";
                        Apellido = "Casatti";
                        Legajo = "26207-1";
                        Condicion = "R";
                    }
                };
        
        if (old == null)
            fail ("No se encontró el alumno a modificar");
        
        boolean expResult = true;
        boolean result = instance.updAlumno(old, upd);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        // fail("The test case is a prototype.");
    }
    
}
