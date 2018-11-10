/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package langtools;

import ortografia.ManejoLang;
import api.Termino;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JTextArea;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class ManejoLangTest
{
    
    public ManejoLangTest()
    {
    }



    /**
     * Test of getWords method, of class ManejoLang.
     */
    @Test
    public void testGetWords()
    {
        System.out.println("getWords");
        String text = "";
        ManejoLang instance = new ManejoLang();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getWords(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of esSustantivo method, of class ManejoLang.
     */
    @Test
    public void testEsSustantivo()
    {
        System.out.println("esSustantivo");
        String palabra = "";
        ManejoLang instance = new ManejoLang();
        boolean expResult = false;
        boolean result = instance.esSustantivo(palabra);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

 

    /**
     * Test of palabrasParaItem method, of class ManejoLang.
     */
    @Test
    public void testPalabrasParaItem() throws Exception
    {
        System.out.println("palabrasParaItem");
        String sin = "";
        ManejoLang instance = new ManejoLang();
        String expResult = "";
        String result = instance.palabrasParaItem(sin);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of corregirArea method, of class ManejoLang.
     */
    @Test
    public void testCorregirArea() throws Exception
    {
        System.out.println("corregirArea");
        JTextArea sin = null;
        JTextArea cor = null;
        ManejoLang instance = new ManejoLang();
        instance.corregirArea(sin, cor);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getClasificacionesPalabra method, of class ManejoLang.
     */
    @Test
    public void testGetClasificacionesPalabra()
    {
        System.out.println("getClasificacionesPalabra");
        String palabra = "";
        ManejoLang instance = new ManejoLang();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getClasificacionesPalabra(palabra);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getLemma method, of class ManejoLang.
     */
    @Test
    public void testGetLemma()
    {
        System.out.println("getLemma");
        String texto = "Un paradigma orientado a objetos se basa en";
        ManejoLang instance = new ManejoLang();
        ArrayList<String> expResult = null;
        ArrayList<String> result = instance.getLemma(texto);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getListaDeTerminos method, of class ManejoLang.
     */
    @Test
    public void testGetListaDeTerminos() throws IOException
    {
        System.out.println("getListaDeTerminos");
        String text = "Un paradigma orientado a objetos se basa en";
        ManejoLang instance = new ManejoLang();
        ArrayList<Termino> result = instance.getListaDeTerminos(text);
        assertEquals(null, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getWordsTerminos method, of class ManejoLang.
     */
    @Test
    public void testGetWordsTerminos()
    {
        System.out.println("getWordsTerminos");
        String text = "";
        ManejoLang instance = new ManejoLang();
        ArrayList<Termino> expResult = null;
        ArrayList<Termino> result = instance.getWordsTerminos(text);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
