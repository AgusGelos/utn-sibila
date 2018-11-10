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
public class ConceptoComplejoTest {
    
    public ConceptoComplejoTest() {
    }

    /**
     * Test of getConcepto method, of class ConceptoComplejo.
     */
    @Test
    public void testGetConcepto() {
        System.out.println("getConcepto");
        ConceptoComplejo instance = null;
        Concepto expResult = null;
        Concepto result = instance.getConcepto();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getOrden method, of class ConceptoComplejo.
     */
    @Test
    public void testGetOrden() {
        System.out.println("getOrden");
        ConceptoComplejo instance = null;
        int expResult = 0;
        int result = instance.getOrden();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
