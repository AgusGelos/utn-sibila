/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainjframe;

import api.Termino;
import java.awt.Component;
import java.awt.ItemSelectable;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.AbstractCellEditor;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author colo
 */
public class CeldaComboBoxEditor extends AbstractCellEditor implements TableCellEditor {

    
    JComboBox jcb;
    
    Object ValorActual;
    Termino s = null;

    
    public CeldaComboBoxEditor() {
        jcb=new JComboBox();
        jcb.addItem("");
        jcb.addItem("Ignorar");
        jcb.addItem("Concepto");
        jcb.addItem("Relacion");
        
//        ItemListener il = new ItemListener() {
//            @Override
//            public void itemStateChanged(ItemEvent e) {
//                
//                    ItemSelectable is = e.getItemSelectable();
//                    String bjt = (String) is.getSelectedObjects()[0];
//                    
//                    if (bjt.equalsIgnoreCase("Concepto")) {
//                        bjt="C";
//                    }else if (bjt.equalsIgnoreCase("Relacion")){
//                        bjt="R";
//                    }else if (bjt.equalsIgnoreCase("Ignorar")){
//                        bjt="I";
//                    }else{
//                        bjt="";
//                    }
//                    s.setTipo(bjt);
//                    
//                
//            }
//        };
        ActionListener al= new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bjt = (String)jcb.getSelectedItem();
                if (bjt.equalsIgnoreCase("Concepto")) {
                        bjt="C";
                    }else if (bjt.equalsIgnoreCase("Relacion")){
                        bjt="R";
                    }else if (bjt.equalsIgnoreCase("Ignorar")){
                        bjt="I";
                    }else{
                        bjt="";
                    }
                    s.setTipo(bjt);
                
            }
        };
        jcb.addActionListener(al);
    }

    @Override
    public Object getCellEditorValue() {
        return ValorActual;
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        GrillaTerminos mdl = (GrillaTerminos) table.getModel();
        s = mdl.getProductoAt(row);
        return jcb;
    }

}
