/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package conceptmanager;

import com.google.common.collect.ImmutableList;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JComponent;
import javax.swing.JFrame;
import org.graphstream.graph.Graph;
import org.graphstream.graph.Node;
import org.graphstream.graph.implementations.SingleGraph;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.layout.Layout;
import org.graphstream.ui.layout.springbox.implementations.LinLog;
import org.graphstream.ui.layout.springbox.implementations.SpringBox;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import scala.Tuple2;

/**
 *
 * @author Martin
 */
public class GraphViewer {
    private String css;
    Graph graph;
    Viewer vw = null;

    public GraphViewer() {
        css = "graph "
              + "{ fill-color: rgb(255,255,255); padding: 50px;}"
              + "edge "
              + "{ size:1px; text-size:12px; text-style:italic; text-background-mode: rounded-box;"
              + "  text-background-color:black; text-alignment: along; text-color:white;"
              + "  shape:line; text-padding: 2px; text-font:\"Consolas\";}"
              + "node "
              + "{ size:65px; text-size:10px; fill-color:lightgray; "
              + "  text-color:rgb(0,0,0); stroke-mode:plain; "
              + "  stroke-width:1px; stroke-color:black; shape: circle; text-font:Verdana;}"
              + "node.root "
              + "{ size: 120px, 50px; text-size: 12px; fill-color: darkgreen; shape: circle; text-color:white; text-style:bold;}"
              + "node.rootdb "
              + "{ size: 120px, 50px; text-size: 12px; fill-color: darkgreen; shape: circle; text-color:white; text-style:bold;"
              + " stroke-width: 3px; icon-mode: above; icon:url('images/database_blue.png');}"
              + "node.db "
              + "{ stroke-width: 3px; icon-mode: above; icon:url('images/database_blue.png');}";

        System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");

        graph = new SingleGraph("Graph");
        setGraphProperties();
        /**
         * No consigo hacer funcionar el render del grafo dentro de un frame
         * propio. Si funciona para un frame externo, autocreado.
         */
        /*
        if (dest_view != null)
        {
            Viewer viewer = new Viewer (graph,  Viewer.ThreadingModel.GRAPH_IN_ANOTHER_THREAD);
            View view = viewer.addDefaultView(false);
            dest_view.add((JComponent) view,BorderLayout.CENTER);
            dest_view.pack();
        }
         */
        vw = graph.display(false);
        //HierarchicalLayout hl = new HierarchicalLayout();
        //vw.enableAutoLayout(hl);
        //LinLog h1 = new LinLog();
        //vw.enableAutoLayout(h1);
        SpringBox h1 = new SpringBox();
        h1.setGravityFactor(0.02);
        vw.enableAutoLayout(h1);
        //vw.enableAutoLayout();
        vw.setCloseFramePolicy(Viewer.CloseFramePolicy.HIDE_ONLY);
    }

    private void setGraphProperties() {
        graph.addAttribute("ui.stylesheet", css);
        graph.addAttribute("ui.quality");
        graph.addAttribute("ui.antialias");
    }

    public void graphReset() {
        graph.clear();
        setGraphProperties();
    }
    
    public void viewMapTuple(LinkedHashMap<Integer, Tuple2<String,Boolean>> conceptos, boolean resaltar_primer_nodo)
    {
        graphReset();
        // Iterar todos los vertices que vengan
        try {
            boolean primer_nodo = true;

            /* Primer recorrido, creo todos los conceptos */
            for (Map.Entry<Integer, Tuple2<String,Boolean>> entry : conceptos.entrySet()) {
                String rid = entry.getKey().toString();
                Integer id = entry.getKey();
                Tuple2 elemento = entry.getValue();
                // Los ID impares son conceptos (1,3,5,7...) 
                // mientras que los pares son relaciones (2,4,6,...)
                boolean impar = (id % 2) != 0;
                if (impar) {
                    Node n = graph.addNode(rid);
                    n.addAttribute("ui.label", elemento._1.toString());
                    if (primer_nodo && resaltar_primer_nodo) {
                        primer_nodo = false;
                        if ((Boolean)elemento._2 == false)
                            n.addAttribute("ui.class", "root");
                        else
                            n.addAttribute("ui.class", "rootdb");
                    }
                    else
                    {
                        if ((Boolean)elemento._2 == true)
                            n.addAttribute("ui.class", "db");
                    }
                }
            }

            /* Segundo recorrido, creo todas las relaciones */
            for (Map.Entry<Integer, Tuple2<String,Boolean>> entry : conceptos.entrySet()) {
                String rid = entry.getKey().toString();
                Integer id = entry.getKey();
                Tuple2 elemento = entry.getValue();
                // Los ID impares son conceptos (1,3,5,7...) 
                // mientras que los pares son relaciones (2,4,6,...)
                boolean impar = (id % 2) != 0;
                if (!impar) {
                    Integer origen_id = id - 1;
                    Integer destino_id = id + 1;
                    try {
                        // Genero un identificador unico para el edge para evitar
                        // colisiones. Se muestra el nombre asi que no importa que
                        // id utilizo.
                        String id_edge = UUID.randomUUID().toString();
                        org.graphstream.graph.Edge ed = graph.addEdge(id_edge, origen_id.toString(), destino_id.toString(), true);
                        ed.addAttribute("ui.label", elemento._1.toString());
                    }
                    catch (Exception ex) {
                        // Puede darse que del nodo actual salga un arco 
                        // a un nodo que ha sido filtrado por la consulta
                        // eso es normal y valido. Debo ignorar el arco
                        System.out.println("Error al establecer el arco de :" + origen_id.toString() + " hasta " + destino_id.toString());
                        System.out.println(ex.toString());
                    }
                }
            }

        }
        catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    public void viewMap(LinkedHashMap<Integer, String> conceptos, boolean resaltar_primer_nodo) {
        graphReset();
        // Iterar todos los vertices que vengan
        try {
            boolean primer_nodo = true;

            /* Primer recorrido, creo todos los conceptos */
            for (Map.Entry<Integer, String> entry : conceptos.entrySet()) {
                String rid = entry.getKey().toString();
                Integer id = entry.getKey();
                String nombre = entry.getValue();
                // Los ID impares son conceptos (1,3,5,7...) 
                // mientras que los pares son relaciones (2,4,6,...)
                boolean impar = (id % 2) != 0;
                if (impar) {
                    Node n = graph.addNode(rid);
                    n.addAttribute("ui.label", nombre);
                    if (primer_nodo && resaltar_primer_nodo) {
                        primer_nodo = false;
                        n.addAttribute("ui.class", "root");
                    }
                }
            }

            /* Segundo recorrido, creo todas las relaciones */
            for (Map.Entry<Integer, String> entry : conceptos.entrySet()) {
                String rid = entry.getKey().toString();
                Integer id = entry.getKey();
                String nombre = entry.getValue();
                // Los ID impares son conceptos (1,3,5,7...) 
                // mientras que los pares son relaciones (2,4,6,...)
                boolean impar = (id % 2) != 0;
                if (!impar) {
                    Integer origen_id = id - 1;
                    Integer destino_id = id + 1;
                    try {
                        // Genero un identificador unico para el edge para evitar
                        // colisiones. Se muestra el nombre asi que no importa que
                        // id utilizo.
                        String id_edge = UUID.randomUUID().toString();
                        org.graphstream.graph.Edge ed = graph.addEdge(id_edge, origen_id.toString(), destino_id.toString(), true);
                        ed.addAttribute("ui.label", nombre);
                    }
                    catch (Exception ex) {
                        // Puede darse que del nodo actual salga un arco 
                        // a un nodo que ha sido filtrado por la consulta
                        // eso es normal y valido. Debo ignorar el arco
                        System.out.println("Error al establecer el arco de :" + origen_id.toString() + " hasta " + destino_id.toString());
                        System.out.println(ex.toString());
                    }
                }
            }

        }
        catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }

    }

    public void viewVertexList(ImmutableList<Vertex> vertices, JFrame dest_view) {
        viewVertexList(vertices, dest_view, true);
    }

    public void viewVertexList(ImmutableList<Vertex> vertices, JFrame dest_view, boolean resaltar_primer_nodo) {
        graphReset();
        // Iterar todos los vertices que vengan
        try {
            boolean primer_nodo = true;
            for (Vertex v : vertices) {
                //Logger.getLogger(this.getClass().getName()).log(Level.INFO, v.toString());
                String rid = v.getId().toString();
                String nombre = v.getProperty("Nombre");

                System.out.println("- Vertex: " + rid + " " + nombre);

                Node n = graph.addNode(rid);
                n.addAttribute("ui.label", nombre);
                if (primer_nodo && resaltar_primer_nodo) {
                    primer_nodo = false;
                    n.addAttribute("ui.class", "root");
                    //n.addAttribute("ui.fill-color","rgb(255,54,14)");
                }

            }
            // Recorrer nuevamente la lista (ahora todos los nodos estan creados)
            // para enlazar los arcos
            for (Vertex v : vertices) {
                //Logger.getLogger(this.getClass().getName()).log(Level.INFO, v.toString());
                //String rid = v.getId().toString();
                // En la segunda vuelta no necesito el nombre, solo el ID
                //String nombre = v.getProperty("Nombre");
                Iterable<Edge> Edges = v.getEdges(Direction.OUT);
                for (Edge e : Edges) {
                    // Si el Edge existe enlaza origen y destino, no hay necesidad
                    // de validar que ambos existan
                    String destino_id = e.getVertex(Direction.IN).getId().toString();
                    String origen_id = e.getVertex(Direction.OUT).getId().toString();
                    System.out.println("Edge desde :" + origen_id + " hasta " + destino_id);
                    try {
                        org.graphstream.graph.Edge ed = graph.addEdge(e.toString(), origen_id, destino_id, true);
                        ed.addAttribute("ui.label", e.getProperty("@class"));
                    }
                    catch (Exception ex) {
                        // Puede darse que del nodo actual salga un arco 
                        // a un nodo que ha sido filtrado por la consulta
                        // eso es normal y valido. Debo ignorar el arco
                        System.out.println("Error al establecer el arco de :" + origen_id + " hasta " + destino_id);
                        System.out.println(ex.toString());
                    }
                }
            }
        }
        catch (Exception e) {
            Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, e);
        }
    }

    /**
     * Este metodo cierra definitivamente el Viewer
     */
    public void closeViewer() {
        vw.close();
    }
}
