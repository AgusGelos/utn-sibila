/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainjframe;

import academico.AcademicManager;
import api.Pregunta;
import api.Respuesta;
import conceptmanager.ConceptManager;
import conceptmanager.Concepto;
import docente.AltaPreguntaWizard;
import java.awt.Font;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTree;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Analia
 */
public class frmAdminPreguntas extends javax.swing.JFrame {

    /**
     * Creates new form frmAdminPreguntas
     */
    private DefaultMutableTreeNode nodoRaiz;
    private DefaultTreeModel modeloTree;
    private DefaultMutableTreeNode nodoSeleccionado;
    
    public frmAdminPreguntas() {
        initComponents();
        
        crearArbol();
        
        loadPreguntas();
    
        expandirArbol();
        setIconosArbol();
    }
 
    /**
     * Crea el arbol donde cargará las preguntas junto con sus respuestas
     */
    private void crearArbol() 
    {
            ///carga el arbol de preguntas
            ///limpia el arbol
            treePreguntas.clearSelection();
            ///crea el nodo raiz
            nodoRaiz=new DefaultMutableTreeNode("Preguntas");
            ///	modelo del arbol
            modeloTree=new DefaultTreeModel((TreeNode) nodoRaiz);
            
            ///Asigna el modelo al arbol
            treePreguntas.setModel(modeloTree);
          
            ///definimos los eventos
            //treePreguntas.getSelectionModel().addTreeSelectionListener(this);
    }
 
    /**
     * Carga las preguntas en el arbol
     */
    private void loadPreguntas()
    {
        String idNodo = null;
        String nombre = null;
        try
        {
            AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
            ArrayList<Pregunta> expResult = null;
            ArrayList<Pregunta> result = instance.getPreguntas();
            
            ///Carga las preguntas dependiente del nodo raiz preguntas
            DefaultMutableTreeNode hijo;
			
            for (Pregunta p : result )
            {
                idNodo = p.IdNodo;
                nombre = p.getTexto();
	        hijo = new DefaultMutableTreeNode(p); 
		modeloTree.insertNodeInto(hijo, nodoRaiz, nodoRaiz.getChildCount());
                loadRespuesta(p, hijo);
            }

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * Carga las respuestas de cada pregunta en el arbol
     */
    private void loadRespuesta(Pregunta p, DefaultMutableTreeNode padre)
    {
        try
        {
            System.out.println("getRespuestasByPregunta");
            
            AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
            ArrayList<Respuesta> expResult = null;
            ArrayList<Respuesta> result = instance.getRespuestasByPregunta(p);
             
            ///Carga las preguntas dependiente del nodo raiz preguntas
            DefaultMutableTreeNode hijo;
			
            for (Respuesta r : result )
            {
        	hijo = new DefaultMutableTreeNode(r); 
		modeloTree.insertNodeInto(hijo, padre, padre.getChildCount());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    
    
    /**
     * Refresca un nodo padre del arbol
     */
    private void refrescarNodoPadre()
    {
        DefaultMutableTreeNode nodeParent = (DefaultMutableTreeNode) nodoSeleccionado.getParent();
        if(nodeParent != null)
        {
                ((DefaultTreeModel) treePreguntas.getModel()).reload(nodeParent);
                treePreguntas.repaint();
        }
    }
    
    /**
     * Expande el arbol
     */
    private void expandirArbol() 
    {
        //expandir el arbol
        Object root = treePreguntas.getModel().getRoot();
        setTreeState(treePreguntas, new TreePath(root),true);
    }    
        
    /**
     * Expande o comprime el arbol
     */
    private void setTreeState(JTree tree, TreePath path, boolean expanded) 
    {
        Object lastNode = path.getLastPathComponent();
        for (int i = 0; i < tree.getModel().getChildCount(lastNode); i++) {
            Object child = tree.getModel().getChild(lastNode,i);
            TreePath pathToChild = path.pathByAddingChild(child);
            setTreeState(tree,pathToChild,expanded);
        }
        if (expanded) 
            tree.expandPath(path);
        else
            tree.collapsePath(path);
  }
 
    /**
     * asigna iconos a las ramas del arbol
     */
    private void setIconosArbol() 
    {
        //obtengo el treecellrendered
        DefaultTreeCellRenderer render= (DefaultTreeCellRenderer)treePreguntas.getCellRenderer();
        
        //altura de cada nodo
        treePreguntas.setRowHeight(26);
        
        // Cambiamos los iconos
        render.setLeafIcon(new ImageIcon("resources/images/check.png"));
        render.setOpenIcon(new ImageIcon("resources/images/CategoriasNegro.png"));
        render.setClosedIcon(new ImageIcon("resources/images/CategoriasNegro.png"));
        
        treePreguntas.setCellRenderer(render);
    }    
    
    /**
     * Agrega una pregunta a la base de conocimiento y al arbol
     */
    private void addPregunta(Pregunta pregunta)
    {
        ///Carga las preguntas dependiente del nodo raiz preguntas
        if (!pregunta.getTexto().equals(""))
        {
            //graba en la base
            AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
            boolean expResult = false;
            Pregunta result = instance.addPregunta(pregunta);
            
            //el resultado se graba en el árbol
            DefaultMutableTreeNode hijo;
            //grabar la pregunta completa en el árbol
            hijo = new DefaultMutableTreeNode(result); 
            modeloTree.insertNodeInto(hijo, nodoRaiz, nodoRaiz.getChildCount());
        }
    }
 
    /**
     * Modifica la pregunta seleccionada de la base de conocimiento y del arbol
     */ 
    private void updPregunta(Pregunta preguntaOld, Pregunta preguntaUpd)
    {
        ///Carga las preguntas dependiente del nodo raiz preguntas
        if (!preguntaUpd.getTexto().equals(""))
        {
            //graba en la base de conocimiento
            AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
            
            boolean result = instance.updPregunta(preguntaOld, preguntaUpd);
        
            if (result)
            {
                //cambia el texto de la pregunta
                nodoSeleccionado.setUserObject(preguntaUpd);
                refrescarNodoPadre();
            }
            else
                JOptionPane.showMessageDialog(this, "No se pudo modificar la pregunta");
        }
    }
    
    /**
     * Borra la pregunta seleccionada de la base de conocimiento y del arbol 
     */ 
    private void delPregunta(Pregunta pregunta)
    {
        //Borra de la base de conocimiento
        AcademicManager instance = new AcademicManager ("remote:localhost/PPR","admin","admin");
        
        // Se pasa TRUE como segundo parámetro para habilitar el borrado en cascada de las respuestas asociadas
        boolean result = instance.delPregunta(pregunta,true);
   
        if (result)
        {
            //borra en el arbol 
            modeloTree.removeNodeFromParent(nodoSeleccionado);
        }
        else
            JOptionPane.showMessageDialog(this, "No se pudo eliminar la pregunta");
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        btnInsertarPregunta = new javax.swing.JButton();
        btnModificarPregunta = new javax.swing.JButton();
        btnEliminarPregunta = new javax.swing.JButton();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel2 = new javax.swing.JLabel();
        btnInsertarRespuesta = new javax.swing.JButton();
        btnModificarRespuesta = new javax.swing.JButton();
        btnEliminarRespuesta = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        treePreguntas = new javax.swing.JTree();

        setTitle("Administrador de Preguntas");
        setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        setName("frmAdminPreguntas"); // NOI18N

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Preguntas");

        btnInsertarPregunta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnInsertarPregunta.setText("Insertar");
        btnInsertarPregunta.setName("btnInsertarPregunta"); // NOI18N
        btnInsertarPregunta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarPreguntaActionPerformed(evt);
            }
        });

        btnModificarPregunta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnModificarPregunta.setLabel("Modificar");
        btnModificarPregunta.setName("btnModificarPregunta"); // NOI18N
        btnModificarPregunta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnModificarPreguntaActionPerformed(evt);
            }
        });

        btnEliminarPregunta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEliminarPregunta.setLabel("Eliminar");
        btnEliminarPregunta.setName("btnEliminarPregunta"); // NOI18N
        btnEliminarPregunta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarPreguntaActionPerformed(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Respuestas");

        btnInsertarRespuesta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnInsertarRespuesta.setText("Insertar");
        btnInsertarRespuesta.setName("btnInsertarRespuesta"); // NOI18N
        btnInsertarRespuesta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnInsertarRespuestaActionPerformed(evt);
            }
        });

        btnModificarRespuesta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnModificarRespuesta.setText("Graficar");
        btnModificarRespuesta.setEnabled(false);
        btnModificarRespuesta.setName("btnModificarRespuesta"); // NOI18N

        btnEliminarRespuesta.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        btnEliminarRespuesta.setEnabled(false);
        btnEliminarRespuesta.setLabel("Eliminar");
        btnEliminarRespuesta.setName("btnEliminarRespuesta"); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(btnEliminarPregunta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificarPregunta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInsertarPregunta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnEliminarRespuesta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnModificarRespuesta, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnInsertarRespuesta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnInsertarPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnModificarPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarPregunta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(21, 21, 21)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(btnInsertarRespuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnEliminarRespuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnModificarRespuesta, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 52, Short.MAX_VALUE))
        );

        treePreguntas.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        javax.swing.tree.DefaultMutableTreeNode treeNode1 = new javax.swing.tree.DefaultMutableTreeNode("Preguntas");
        javax.swing.tree.DefaultMutableTreeNode treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Pregunta1");
        javax.swing.tree.DefaultMutableTreeNode treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Respuesta1");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Respuesta2");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Pregunta2");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Respuesta1");
        treeNode2.add(treeNode3);
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Respuesta2");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Pregunta3");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Respuesta1");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treeNode2 = new javax.swing.tree.DefaultMutableTreeNode("Pregunta4");
        treeNode3 = new javax.swing.tree.DefaultMutableTreeNode("Respuesta1");
        treeNode2.add(treeNode3);
        treeNode1.add(treeNode2);
        treePreguntas.setModel(new javax.swing.tree.DefaultTreeModel(treeNode1));
        treePreguntas.setName("treePreguntas"); // NOI18N
        treePreguntas.addTreeSelectionListener(new javax.swing.event.TreeSelectionListener() {
            public void valueChanged(javax.swing.event.TreeSelectionEvent evt) {
                treePreguntasValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(treePreguntas);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 541, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnInsertarPreguntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarPreguntaActionPerformed
        /// Insertar una pregunta
        //crear una pregunta vacia
        Pregunta pregunta = new Pregunta();
        dlgPregunta d = new dlgPregunta(this, true, pregunta);
        d.setVisible(true);
        ///agrega la pregunta en el arbol
        addPregunta(pregunta);

    }//GEN-LAST:event_btnInsertarPreguntaActionPerformed

    private void btnInsertarRespuestaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnInsertarRespuestaActionPerformed
        // Validar que haya un nodo nivel 0 seleccionado
        if (nodoSeleccionado != null && nodoSeleccionado.getLevel() == 1) {
            Pregunta pregunta = new Pregunta (nodoSeleccionado.toString());
            JFrame j = new AltaPreguntaWizard(pregunta);
            j.pack();
            j.setLocationRelativeTo(this);
            j.setVisible(true);
        }
        else
            JOptionPane.showMessageDialog(this, "Debe seleccionar una pregunta para crear una respuesta", "Selección no válida", JOptionPane.ERROR_MESSAGE);
    }//GEN-LAST:event_btnInsertarRespuestaActionPerformed

    private void treePreguntasValueChanged(javax.swing.event.TreeSelectionEvent evt) {//GEN-FIRST:event_treePreguntasValueChanged
        // TODO add your handling code here:
        //aca selecionar el nodo
        nodoSeleccionado =(DefaultMutableTreeNode)treePreguntas.getLastSelectedPathComponent();
        
    }//GEN-LAST:event_treePreguntasValueChanged

    private void btnModificarPreguntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarPreguntaActionPerformed
        // modifica la pregunta seleccionada
        //si es una pregunta
        if (nodoSeleccionado.getLevel() == 1)
        {
            //obtengo la pregunta del nodo seleccionado
            Pregunta preguntaActual = (Pregunta) nodoSeleccionado.getUserObject();
            Pregunta preguntaUpd = preguntaActual;
            
            Pregunta preguntaOld = new Pregunta();
            preguntaOld.setTexto(preguntaActual.getTexto());
            preguntaOld.IdNodo = preguntaActual.IdNodo;
            
            //llamo al dialogo para modificar
            dlgPregunta d = new dlgPregunta(this, true, preguntaUpd);
            d.setVisible(true);
        
            ///agrega la pregunta en el arbol
            updPregunta(preguntaOld, preguntaUpd);
        }
        
    }//GEN-LAST:event_btnModificarPreguntaActionPerformed

    private void btnEliminarPreguntaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarPreguntaActionPerformed
        // Borra el elemento seleccionado
        
        //si es una pregunta
        if (nodoSeleccionado.getLevel() == 1)
        {
            if (JOptionPane.showConfirmDialog(this, "Está por eliminar una Pregunta, sus respuestas también se eliminarán. ¿Desea proseguir?", "Eliminar pregunta", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION) 
            {
                //obtengo la pregunta del nodo seleccionado
                Pregunta preguntaActual = (Pregunta) nodoSeleccionado.getUserObject();
           
                delPregunta(preguntaActual);
            }
        }
        else
            JOptionPane.showMessageDialog(this, "Tiene que seleccionar una pregunta para eliminar");
    }//GEN-LAST:event_btnEliminarPreguntaActionPerformed

    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEliminarPregunta;
    private javax.swing.JButton btnEliminarRespuesta;
    private javax.swing.JButton btnInsertarPregunta;
    private javax.swing.JButton btnInsertarRespuesta;
    private javax.swing.JButton btnModificarPregunta;
    private javax.swing.JButton btnModificarRespuesta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTree treePreguntas;
    // End of variables declaration//GEN-END:variables
}
