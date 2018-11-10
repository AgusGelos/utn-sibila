/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptmanager;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class RelacionComplejaTest {
    
    public RelacionComplejaTest() {
    }

    /**
     * Test of getRelacion method, of class RelacionCompleja.
     */
    @Test
    public void testGetRelacion() {
        System.out.println("getRelacion");
        RelacionCompleja instance = null;
        Relacion expResult = null;
        Relacion result = instance.getRelacion();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrden method, of class RelacionCompleja.
     */
    @Test
    public void testGetOrden() {
        System.out.println("getOrden");
        RelacionCompleja instance = null;
        int expResult = 0;
        int result = instance.getOrden();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
