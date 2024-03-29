/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainjframe;

import academico.frmAdministracion;
import com.google.common.collect.ImmutableList;
import com.tinkerpop.blueprints.Direction;
import com.tinkerpop.blueprints.Edge;
import com.tinkerpop.blueprints.Vertex;
import com.tinkerpop.blueprints.impls.orient.OrientEdgeType;
import com.tinkerpop.blueprints.impls.orient.OrientGraph;
import com.tinkerpop.blueprints.impls.orient.OrientGraphFactory;
import com.tinkerpop.blueprints.impls.orient.OrientGraphNoTx;
import conceptmanager.ConceptManager;
import conceptmanager.Concepto;
import conceptmanager.GraphViewer;
import conceptmanager.Relacion;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.table.DefaultTableModel;
// importar la libreria Graph Stream
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;


/**
 *
 * @author Martin
 */
public class frmAdminConceptos extends javax.swing.JFrame
{

    OrientGraphFactory factory;
    GraphViewer gv;
    /**
     * Creates new form mainjframe
     */
    public frmAdminConceptos()
    {
        initComponents();
        InitDatabase();
        LoadConceptos();
        LoadRelaciones();
    }

    private void InitDatabase()
    {
        factory = new OrientGraphFactory("remote:localhost/PPR", "admin", "admin").setupPool(1, 10);
    }

    private void LoadConceptos()
    {
        try
        {
            ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
            DefaultListModel lstModel = new DefaultListModel();
            DefaultComboBoxModel cmbModel1 = new DefaultComboBoxModel();
            DefaultComboBoxModel cmbModel2 = new DefaultComboBoxModel();
            for (Concepto c : cm.getConceptos())
            {
                lstModel.addElement(c.getNombre());
                cmbModel1.addElement(c.getNombre());
                cmbModel2.addElement(c.getNombre());
            }
            lstConceptos.setModel(lstModel);
            lstConceptoOrigen.setModel(cmbModel1);
            lstConceptoDestino.setModel(cmbModel2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void LoadRelaciones()
    {
        try
        {
            ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
            DefaultListModel lstModel = new DefaultListModel();
            DefaultComboBoxModel cmbModel = new DefaultComboBoxModel();
            for (Relacion r : cm.getRelaciones())
            {
                System.out.println(r.getTipo());
                lstModel.addElement(r.getTipo());
                cmbModel.addElement(r.getTipo());
            }
            lstRelaciones.setModel(lstModel);
            lstRelacion.setModel(cmbModel);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        lstConceptos = new javax.swing.JList();
        btnEliminarConcepto = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        edtNuevoConcepto = new javax.swing.JTextField();
        btnAddConcepto = new javax.swing.JButton();
        btnUpdConcepto = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        edtNuevoNombreConcepto = new javax.swing.JTextField();
        edtDepth = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        btnVerConceptos = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        edtEquivalencia = new javax.swing.JTextField();
        btnAgregarEquivalencia = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        edtPesoEquivalencia = new javax.swing.JTextField();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblEquivalencias = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        lstRelaciones = new javax.swing.JList();
        jPanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        btnRelacionar = new javax.swing.JButton();
        lstConceptoOrigen = new javax.swing.JComboBox();
        lstConceptoDestino = new javax.swing.JComboBox();
        lstRelacion = new javax.swing.JComboBox();
        edtNuevoTipoRelacion = new javax.swing.JTextField();
        chkNuevoTipo = new javax.swing.JCheckBox();
        btnVerRelaciones = new javax.swing.JButton();

        setTitle("Concept Manager");
        setResizable(false);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Conceptos"));

        lstConceptos.addListSelectionListener(new javax.swing.event.ListSelectionListener() {
            public void valueChanged(javax.swing.event.ListSelectionEvent evt) {
                lstConceptosValueChanged(evt);
            }
        });
        jScrollPane1.setViewportView(lstConceptos);

        btnEliminarConcepto.setText("Eliminar concepto seleccionado");
        btnEliminarConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEliminarConceptoActionPerformed(evt);
            }
        });

        jLabel5.setText("Nombre del concepto:");

        btnAddConcepto.setText("Agregar");
        btnAddConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAddConceptoActionPerformed(evt);
            }
        });

        btnUpdConcepto.setText("Modificar");
        btnUpdConcepto.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnUpdConceptoActionPerformed(evt);
            }
        });

        jLabel6.setText("Nuevo nombre:");

        edtDepth.setText("2");

        jLabel4.setText("Profundidad:");

        btnVerConceptos.setText("Ver Conceptos Relacionados");
        btnVerConceptos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerConceptosActionPerformed(evt);
            }
        });

        jLabel7.setText("Nueva equivalencia");

        edtEquivalencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtEquivalenciaActionPerformed(evt);
            }
        });

        btnAgregarEquivalencia.setText("Agregar equivalencia");
        btnAgregarEquivalencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAgregarEquivalenciaActionPerformed(evt);
            }
        });

        jLabel8.setText("Peso");

        edtPesoEquivalencia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                edtPesoEquivalenciaActionPerformed(evt);
            }
        });

        tblEquivalencias.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Equivalencia", "Peso"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tblEquivalencias);
        if (tblEquivalencias.getColumnModel().getColumnCount() > 0) {
            tblEquivalencias.getColumnModel().getColumn(0).setResizable(false);
            tblEquivalencias.getColumnModel().getColumn(1).setResizable(false);
        }

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(edtNuevoConcepto)
                                    .addComponent(edtNuevoNombreConcepto)))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 390, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(14, 14, 14)
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 208, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(15, 15, 15)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(btnUpdConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(btnAddConcepto, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7)
                            .addComponent(edtEquivalencia)
                            .addComponent(btnAgregarEquivalencia, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(edtDepth)
                            .addComponent(jLabel4)
                            .addComponent(btnVerConceptos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel8)
                            .addComponent(edtPesoEquivalencia)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnEliminarConcepto)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(11, 11, 11)
                        .addComponent(edtDepth, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnVerConceptos)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(edtEquivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(jLabel8)
                        .addGap(9, 9, 9)
                        .addComponent(edtPesoEquivalencia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAgregarEquivalencia)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnAddConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jLabel5)
                    .addComponent(edtNuevoConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(edtNuevoNombreConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnUpdConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnEliminarConcepto)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Conceptos", jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Relaciones"));

        jScrollPane2.setViewportView(lstRelaciones);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Relacionar conceptos"));

        jLabel1.setText("Origen:");

        jLabel2.setText("Destino:");

        jLabel3.setText("Relación:");

        btnRelacionar.setText("Relacionar los  conceptos");
        btnRelacionar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnRelacionarActionPerformed(evt);
            }
        });

        chkNuevoTipo.setText("Nuevo tipo de relación:");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(chkNuevoTipo)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(edtNuevoTipoRelacion, javax.swing.GroupLayout.PREFERRED_SIZE, 198, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(lstConceptoDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 293, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(lstConceptoOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lstRelacion, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnRelacionar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lstConceptoOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lstConceptoDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chkNuevoTipo)
                    .addComponent(edtNuevoTipoRelacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lstRelacion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnRelacionar)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btnVerRelaciones.setText("Ver usos de la relación seleccionada");
        btnVerRelaciones.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnVerRelacionesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(btnVerRelaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 167, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(4, 4, 4)
                .addComponent(btnVerRelaciones))
        );

        jTabbedPane1.addTab("Relaciones", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 406, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnAddConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAddConceptoActionPerformed
        if (!edtNuevoConcepto.getText().trim().isEmpty())
        {
            edtNuevoConcepto.setText(edtNuevoConcepto.getText().trim().toUpperCase());
            try
            {
                ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
                Concepto c = new Concepto("",edtNuevoConcepto.getText());
                cm.addConcepto(c);
                /*
                OrientGraph db = factory.getTx();
                Vertex v = db.addVertex("class:Concepto");
                v.setProperty("Nombre", edtNuevoConcepto.getText());
                */
                LoadConceptos();
                //db.shutdown();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnAddConceptoActionPerformed

    private void btnEliminarConceptoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEliminarConceptoActionPerformed
        if (lstConceptos.getSelectedIndex() >= 0)
        {
            try
            {
                ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
                Concepto c = new Concepto("",lstConceptos.getSelectedValue().toString());
                if (cm.refsConcepto(c) > 0)
                {
                    if (JOptionPane.showConfirmDialog(this, "Las relaciones de este concepto también se eliminarán. ¿Desea proseguir?", "Eliminar concepto", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
                    {
                        cm.delConcepto(c);
                    }
                }
                else
                {
                    cm.delConcepto(c);
                }
                /*
                OrientGraph db = factory.getTx();
                Iterable<Vertex> vi = db.getVertices("Concepto.Nombre", lstConceptos.getSelectedValue());
                if (vi.iterator().hasNext())
                {
                    Vertex v = vi.iterator().next();
                    if (JOptionPane.showConfirmDialog(this, "Las relaciones de este concepto también se eliminarán. ¿Desea proseguir?", "Eliminar concepto", JOptionPane.YES_NO_OPTION) == JOptionPane.OK_OPTION)
                    {
                        db.removeVertex(v);
                        LoadConceptos();
                    }
                }
                db.shutdown();
                */
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

    }//GEN-LAST:event_btnEliminarConceptoActionPerformed

    private void btnRelacionarActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnRelacionarActionPerformed
    {//GEN-HEADEREND:event_btnRelacionarActionPerformed
        // Primero determinar si debo usar un tipo de relacion existente o crear una nueva
        String selectedType = null;

        // Determinar si debo hacer algo.
        // Si no hay seleccionado un tipo y no está marcado Nuevo, salir
        if (lstRelacion.getSelectedIndex() < 0 && !chkNuevoTipo.isSelected())
        return;
        // Si no hay concepto origen o concepto destino, salir
        if (lstConceptoOrigen.getSelectedIndex() < 0 || lstConceptoDestino.getSelectedIndex() < 0)
        return;

        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");

        if (chkNuevoTipo.isSelected())
        {
            // Crear una nueva clase de tipo Edge y que herede de Relacion
            if (!edtNuevoTipoRelacion.getText().trim().isEmpty())
            {
                edtNuevoTipoRelacion.setText(edtNuevoTipoRelacion.getText().trim());
                try
                {
                    cm.addTipoRelacion(edtNuevoTipoRelacion.getText());
                    LoadRelaciones();
                    selectedType = edtNuevoTipoRelacion.getText();
                }
                catch (Exception e)
                {
                    e.printStackTrace();
                }
            }
        }
        else
        selectedType = lstRelacion.getSelectedItem().toString();

        // Ahora seguir con la asociación
        try
        {
            //OrientGraph db = factory.getTx();
            String concepto_origen, concepto_destino;
            concepto_origen = lstConceptoOrigen.getSelectedItem().toString();
            concepto_destino = lstConceptoDestino.getSelectedItem().toString();
            cm.addRelacion(concepto_origen, concepto_destino, selectedType);
            LoadRelaciones();

            chkNuevoTipo.setSelected(false);
            edtNuevoTipoRelacion.setText("");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }//GEN-LAST:event_btnRelacionarActionPerformed

    private void btnUpdConceptoActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnUpdConceptoActionPerformed
    {//GEN-HEADEREND:event_btnUpdConceptoActionPerformed
        if ((!edtNuevoNombreConcepto.getText().trim().isEmpty())&&(lstConceptos.getSelectedIndex() >= 0))
        {
            edtNuevoNombreConcepto.setText(edtNuevoNombreConcepto.getText().trim().toUpperCase());
            try
            {
                ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
                Concepto c = new Concepto("",lstConceptos.getSelectedValue().toString());
                cm.updNombreConcepto(c, edtNuevoNombreConcepto.getText());
                /*
                OrientGraph db = factory.getTx();
                Vertex v = db.addVertex("class:Concepto");
                v.setProperty("Nombre", edtNuevoConcepto.getText());
                */
                LoadConceptos();
                //db.shutdown();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnUpdConceptoActionPerformed

    private void btnVerConceptosActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnVerConceptosActionPerformed
    {//GEN-HEADEREND:event_btnVerConceptosActionPerformed
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
        Concepto c = cm.getConceptoByName(lstConceptos.getSelectedValue().toString());
        int depth = Integer.parseInt(edtDepth.getText());
        ImmutableList<Vertex> result = cm.findRoutesFrom(c, depth);
        //chequeamos si el objeto fue creado con anterioridad
        if (gv != null) {
            // Si fue creado, cerramos el Viewer y creamos uno nuevo
            gv.closeViewer();
        }
        gv = new GraphViewer();
        gv.viewVertexList(result, null);
    }//GEN-LAST:event_btnVerConceptosActionPerformed

    private void btnVerRelacionesActionPerformed(java.awt.event.ActionEvent evt)//GEN-FIRST:event_btnVerRelacionesActionPerformed
    {//GEN-HEADEREND:event_btnVerRelacionesActionPerformed
        ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
        
        Relacion r = cm.getRelacionByName(lstRelaciones.getSelectedValue().toString());
        ImmutableList<Vertex> result = cm.findRoutesByType(r);
        //chequeamos si el objeto fue creado con anterioridad
        if (gv != null) {
            // Si fue creado, cerramos el Viewer y creamos uno nuevo
            gv.closeViewer();
        }
        gv = new GraphViewer();
        gv.viewVertexList(result, null, false);
    }//GEN-LAST:event_btnVerRelacionesActionPerformed

    private void edtEquivalenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtEquivalenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtEquivalenciaActionPerformed

    private void btnAgregarEquivalenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAgregarEquivalenciaActionPerformed
        // TODO add your handling code here:
        if (!edtEquivalencia.getText().trim().isEmpty()) {
            
            //edtEquivalencia.setText(edtNuevoConcepto.getText().trim().toUpperCase());
            boolean exito = false;
            String c = lstConceptos.getSelectedValue().toString();
                
            try {
                ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");
                String equivalencia = this.edtEquivalencia.getText().toUpperCase();
                Double peso = Double.parseDouble(this.edtPesoEquivalencia.getText());                            
                exito = cm.addEquivalencia(c, equivalencia, peso);
                exito = true;
            }
            catch (Exception e) {
                 e.printStackTrace();
            }
            if (exito){
                this.actualizarTablaDeEquivalencias(c);
                JOptionPane.showMessageDialog(this, "La equivalencia se ha cargado con éxito");
            }
            else {
                JOptionPane.showMessageDialog(this, "Ha ocurrido un error en la carga de la equivalencia");
            }
        }    
    }//GEN-LAST:event_btnAgregarEquivalenciaActionPerformed

    private void edtPesoEquivalenciaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_edtPesoEquivalenciaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_edtPesoEquivalenciaActionPerformed

    private void lstConceptosValueChanged(javax.swing.event.ListSelectionEvent evt) {//GEN-FIRST:event_lstConceptosValueChanged
        // TODO add your handling code here:
        String c = lstConceptos.getSelectedValue().toString();
        this.actualizarTablaDeEquivalencias(c);

    }//GEN-LAST:event_lstConceptosValueChanged

    private void actualizarTablaDeEquivalencias(String c){
        this.edtEquivalencia.setText("");
        this.edtPesoEquivalencia.setText("");
        try {            
            ConceptManager cm = new ConceptManager ("remote:localhost/PPR","admin","admin");            
            String[] columnNames = {"Equivalencia","Peso"};              
            DefaultTableModel model =  new DefaultTableModel(columnNames, 0);
            Map<String, Object> equivalencias = cm.getEquivalencias(c);
            if (equivalencias != null){
                for (Map.Entry<String, Object> equivalencia : equivalencias.entrySet()) {
                        //(DefaultTableModel) this.tblEquivalencias.getModel();
                        if (equivalencia != null){
                            model.addRow(new Object[]{equivalencia.getKey(),equivalencia.getValue()});                      
                        }
                }
            }
            this.tblEquivalencias.setModel(model);
            
        }
        catch (Exception e) {
             e.printStackTrace();
        }
    }
    
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAddConcepto;
    private javax.swing.JButton btnAgregarEquivalencia;
    private javax.swing.JButton btnEliminarConcepto;
    private javax.swing.JButton btnRelacionar;
    private javax.swing.JButton btnUpdConcepto;
    private javax.swing.JButton btnVerConceptos;
    private javax.swing.JButton btnVerRelaciones;
    private javax.swing.JCheckBox chkNuevoTipo;
    private javax.swing.JTextField edtDepth;
    private javax.swing.JTextField edtEquivalencia;
    private javax.swing.JTextField edtNuevoConcepto;
    private javax.swing.JTextField edtNuevoNombreConcepto;
    private javax.swing.JTextField edtNuevoTipoRelacion;
    private javax.swing.JTextField edtPesoEquivalencia;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JComboBox lstConceptoDestino;
    private javax.swing.JComboBox lstConceptoOrigen;
    private javax.swing.JList lstConceptos;
    private javax.swing.JComboBox lstRelacion;
    private javax.swing.JList lstRelaciones;
    private javax.swing.JTable tblEquivalencias;
    // End of variables declaration//GEN-END:variables
}
