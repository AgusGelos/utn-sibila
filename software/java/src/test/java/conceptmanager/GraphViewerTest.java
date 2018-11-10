/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptmanager;

import com.google.common.collect.ImmutableList;
import com.tinkerpop.blueprints.Vertex;
import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;
import com.google.common.collect.*;
import java.util.LinkedHashMap;
import javax.swing.JFrame;
import scala.Tuple2;

/**
 *
 * @author Martin
 */
public class GraphViewerTest
{
    
    public GraphViewerTest()
    {
    }

    /**
     * Test of viewVertexList method, of class GraphViewer.
     */
    @Test
    public void testViewVertexList()
    {
        System.out.println("viewVertexList");

        GraphViewer instance = new GraphViewer ();
        ConceptManager cm = new ConceptManager("remote:localhost/PPR","admin","admin");
        Concepto concepto_inicial = cm.getConceptoByName("POO");
        ArrayList<String> conceptos_incluidos = null; 
        
        ImmutableList<Vertex> result = cm.findRoute(concepto_inicial, conceptos_incluidos);
        instance.viewVertexList(result,null);

        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    } 

    /**
     * Test of graphReset method, of class GraphViewer.
     */
    @Test
    public void testGraphReset() {
        System.out.println("graphReset");
        GraphViewer instance = new GraphViewer();
        instance.graphReset();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of viewMap method, of class GraphViewer.
     */
    @Test
    public void testViewMap() {
        System.out.println("viewMap");
        LinkedHashMap<Integer, String> conceptos = new LinkedHashMap();
        conceptos.put(1, "Concepto1");
        conceptos.put(2, "Relacion1");
        conceptos.put(3, "Concepto2");
        conceptos.put(4, "Relacion2");
        conceptos.put(5, "Concepto3");
        conceptos.put(6, "Relacion3");
        conceptos.put(7, "Concepto4");
        boolean resaltar_primer_nodo = true;
        GraphViewer instance = new GraphViewer();
        instance.viewMap(conceptos, resaltar_primer_nodo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of viewVertexList method, of class GraphViewer.
     */
    @Test
    public void testViewVertexList_ImmutableList_JFrame() {
        System.out.println("viewVertexList");
        ImmutableList<Vertex> vertices = null;
        JFrame dest_view = null;
        GraphViewer instance = new GraphViewer();
        instance.viewVertexList(vertices, dest_view);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of viewVertexList method, of class GraphViewer.
     */
    @Test
    public void testViewVertexList_3args() {
        System.out.println("viewVertexList");
        ImmutableList<Vertex> vertices = null;
        JFrame dest_view = null;
        boolean resaltar_primer_nodo = false;
        GraphViewer instance = new GraphViewer();
        instance.viewVertexList(vertices, dest_view, resaltar_primer_nodo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of closeViewer method, of class GraphViewer.
     */
    @Test
    public void testCloseViewer() {
        System.out.println("closeViewer");
        GraphViewer instance = new GraphViewer();
        instance.closeViewer();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of viewMapTuple method, of class GraphViewer.
     */
    @Test
    public void testViewMapTuple() {
        System.out.println("viewMapTuple");
        LinkedHashMap<Integer, Tuple2<String, Boolean>> conceptos = null;
        boolean resaltar_primer_nodo = false;
        GraphViewer instance = new GraphViewer();
        instance.viewMapTuple(conceptos, resaltar_primer_nodo);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
