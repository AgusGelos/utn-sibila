/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptmanager;

import api.ElementoExamen;
import api.Respuesta;
import api.Termino;
import com.google.common.collect.ImmutableList;
import com.tinkerpop.blueprints.Vertex;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Martin
 */
public class ConceptManagerTest
{
    
    public ConceptManagerTest()
    {
    }

    /**
     * Test of getConceptos method, of class ConceptManager.
     */
    @Test
    public void testGetConceptos()
    {
        System.out.println("getConceptos");
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        ArrayList<Concepto> result = instance.getConceptos();
        assertFalse(result.isEmpty());
        //assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRelaciones method, of class ConceptManager.
     */
    @Test
    public void testGetRelaciones()
    {
        System.out.println("getRelaciones");
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        ArrayList<Relacion> result = instance.getRelaciones();
        assertFalse(result.isEmpty());
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConceptoByName method, of class ConceptManager.
     */
    @Test
    public void testGetConceptoByName()
    {
        System.out.println("getConceptoByName");
        String Nombre = "";
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        Concepto expResult = null;
        Concepto result = instance.getConceptoByName(Nombre);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getConceptoVertexByName method, of class ConceptManager.
     */
    @Test
    public void testGetConceptoVertexByName()
    {
        System.out.println("getConceptoVertexByName");
        String Nombre = "";
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        Vertex expResult = null;
        Vertex result = instance.getConceptoVertexByName(Nombre);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addConcepto method, of class ConceptManager.
     */
    @Test
    public void testAddConcepto()
    {
        System.out.println("addConcepto");
        Concepto c = null;
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        instance.addConcepto(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delConcepto method, of class ConceptManager.
     */
    @Test
    public void testDelConcepto()
    {
        System.out.println("delConcepto");
        Concepto c = null;
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        instance.delConcepto(c);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRelacionByName method, of class ConceptManager.
     */
    @Test
    public void testGetRelacionByName()
    {
        System.out.println("getRelacionByName");
        String nombre = "";
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        Relacion expResult = null;
        Relacion result = instance.getRelacionByName(nombre);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of evaluarRespuesta method, of class ConceptManager.
     */
    @Test
    public void testEvaluarRespuesta()
    {
        System.out.println("evaluarRespuesta");
        System.out.println("Cargar los datos de la respuesta");
       
        Respuesta resp = new Respuesta ();
        resp.loadFromPlainText("un objeto posee atributo y envia mensaje");
        /*
        resp.newTermino("Objeto", null);
        resp.newTermino("es", null);
        resp.newTermino("concepto",null);
        resp.newTermino("Complejo", null);
        */
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        Respuesta result = (Respuesta)instance.evaluarRespuesta(resp);

        System.out.println(result.toString());
    }

    /**
     * Test of getTipoTermino method, of class ConceptManager.
     */
    @Test
    public void testGetTipoTermino()
    {
        System.out.println("getTipoTermino");
        Termino termino = new Termino ("FUNCIONAL","");
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        String expResult = "C";
        String result = instance.getTipoTermino(termino);
        assertEquals(expResult, result);
        termino = new Termino ("Tipo","");
        expResult = "R";
        result = instance.getTipoTermino(termino);
        assertEquals(expResult, result);
        
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addTipoRelacion method, of class ConceptManager.
     */
    @Test
    public void testAddTipoRelacion()
    {
        System.out.println("addTipoRelacion");
        String nombreTipo = "";
        ConceptManager instance = null;
        instance.addTipoRelacion(nombreTipo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addRelacion method, of class ConceptManager.
     */
    @Test
    public void testAddRelacion_3args_1()
    {
        System.out.println("addRelacion");
        Concepto origen = null;
        Concepto destino = null;
        Relacion relacion = null;
        ConceptManager instance = null;
        instance.addRelacion(origen, destino, relacion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addRelacion method, of class ConceptManager.
     */
    @Test
    public void testAddRelacion_3args_2()
    {
        System.out.println("addRelacion");
        String origen = "";
        String destino = "";
        String relacion = "";
        ConceptManager instance = null;
        instance.addRelacion(origen, destino, relacion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRoute method, of class ConceptManager.
     */
    @Test
    public void testFindRoute()
    {
        System.out.println("findRoute");
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");

        Concepto concepto_inicial = instance.getConceptoByName("POO");
        ArrayList<String> conceptos_incluidos = null;
        instance.findRoute(concepto_inicial, conceptos_incluidos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
    /**
     * Test of findRouteByTrace method, of class ConceptManager.
    @Test
    public void testFindRouteByTrace()
    {
        System.out.println("findRouteByTrace");
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");

        Concepto concepto_inicial = instance.getConceptoByName("LOGICA PRIMER ORDEN");
        ArrayList<String> conceptos_incluidos = null;
        instance.findRouteByTrace(concepto_inicial, conceptos_incluidos);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
     */

    /**
     * Test of getConceptosComplejos method, of class ConceptManager.
     */
    @Test
    public void testGetConceptosComplejos()
    {
        System.out.println("getConceptosComplejos");
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        ArrayList<ConceptoComplejo> expResult = null;
        ArrayList<ConceptoComplejo> result = instance.getConceptosComplejos();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getRelacionesComplejas method, of class ConceptManager.
     */
    @Test
    public void testGetRelacionesComplejas()
    {
        System.out.println("getRelacionesComplejas");
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        ArrayList<RelacionCompleja> expResult = null;
        ArrayList<RelacionCompleja> result = instance.getRelacionesComplejas();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildRouteQuery method, of class ConceptManager.
     */
    @Test
    public void testBuildRouteQuery()
    {
        System.out.println("buildRouteQuery");
        Concepto concepto_inicial = null;
        ArrayList<String> conceptos_incluidos = null;
        ConceptManager instance = null;
        String expResult = "";
        String result = instance.buildRouteQuery(concepto_inicial, conceptos_incluidos);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of refsConcepto method, of class ConceptManager.
     */
    @Test
    public void testRefsConcepto()
    {
        System.out.println("refsConcepto");
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        Concepto c = instance.getConceptoByName("OBJETO");
        long expResult = 0L;
        long result = instance.refsConcepto(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getTerminosComplejos method, of class ConceptManager.
     */
    @Test
    public void testGetTerminosComplejos()
    {
        System.out.println("getTerminosComplejos");
        Respuesta res = null;
        ConceptManager instance = null;
        ArrayList<Termino> expResult = null;
        ArrayList<Termino> result = instance.getTerminosComplejos(res);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updNombreConcepto method, of class ConceptManager.
     */
    @Test
    public void testUpdNombreConcepto() {
        System.out.println("updNombreConcepto");
        Concepto c = null;
        String newNombre = "";
        ConceptManager instance = null;
        instance.updNombreConcepto(c, newNombre);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of delConceptoSafe method, of class ConceptManager.
     */
    @Test
    public void testDelConceptoSafe() {
        System.out.println("delConceptoSafe");
        Concepto c = null;
        ConceptManager instance = null;
        boolean expResult = false;
        boolean result = instance.delConceptoSafe(c);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of buildRouteDepth method, of class ConceptManager.
     */
    @Test
    public void testBuildRouteDepth() {
        System.out.println("buildRouteDepth");
        Concepto concepto_inicial = null;
        int depth = 0;
        ConceptManager instance = null;
        String expResult = "";
        String result = instance.buildRouteDepth(concepto_inicial, depth);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRoutesFrom method, of class ConceptManager.
     */
    @Test
    public void testFindRoutesFrom() {
        System.out.println("findRoutesFrom");
        Concepto concepto_inicial = null;
        int depth = 0;
        ConceptManager instance = null;
        ImmutableList<Vertex> expResult = null;
        ImmutableList<Vertex> result = instance.findRoutesFrom(concepto_inicial, depth);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of findRoutesByType method, of class ConceptManager.
     */
    @Test
    public void testFindRoutesByType() {
        System.out.println("findRoutesByType");
        Relacion r = null;
        ConceptManager instance = null;
        ImmutableList<Vertex> expResult = null;
        ImmutableList<Vertex> result = instance.findRoutesByType(r);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanTerminos method, of class ConceptManager.
     */
    @Test
    public void testCleanTerminos_ArrayList() {
        System.out.println("cleanTerminos");
        ArrayList<Termino> lista = null;
        ConceptManager instance = null;
        ArrayList<Termino> expResult = null;
        ArrayList<Termino> result = instance.cleanTerminos(lista);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of cleanTerminos method, of class ConceptManager.
     */
    @Test
    public void testCleanTerminos_ElementoExamen() {
        System.out.println("cleanTerminos");
        ElementoExamen elemento = null;
        ConceptManager instance = null;
        ArrayList<Termino> expResult = null;
        ArrayList<Termino> result = instance.cleanTerminos(elemento);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of existStruct method, of class ConceptManager.
     */
    @Test
    public void testExistStructObj() {
        System.out.println("existStruct");
        Concepto origen = null;
        Concepto destino = null;
        Relacion relacion = null;
        ConceptManager instance = null;
        boolean expResult = false;
        boolean result = instance.existStruct(origen, destino, relacion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of existStruct method, of class ConceptManager.
     */
    @Test
    public void testExistStructStr() {
        System.out.println("existStruct");
        String origen = "LENGUAJE FORMAL";
        String destino = "FORMULA";
        String relacion = "Representa";
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        boolean expResult = true;
        boolean result = instance.existStruct(origen, destino, relacion);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of existTipoRelacion method, of class ConceptManager.
     */
    @Test
    public void testExistTipoRelacion() {
        System.out.println("existTipoRelacion");
        String tipo = "Utiliza";
        ConceptManager instance = new ConceptManager("remote:localhost/PPR","admin","admin");
        boolean expResult = false;
        boolean result = instance.existTipoRelacion(tipo);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of addStruct method, of class ConceptManager.
     */
    @Test
    public void testAddStruct() {
        System.out.println("addStruct");
        Concepto origen = null;
        Concepto destino = null;
        Relacion relacion = null;
        ConceptManager instance = null;
        instance.addStruct(origen, destino, relacion);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
