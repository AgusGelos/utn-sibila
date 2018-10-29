/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mainjframe;

import api.Termino;
import java.awt.MediaTracker;
import java.util.ArrayList;
import static javax.management.Query.value;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableModel;
import ortografia.ManejoLang;

/**
 *
 * @author Diego
 */
public class GrillaTerminos extends AbstractTableModel {

    private static String mNames[]
            = {
                "Término", "Tipo de Término Sugerido", "Tipo", "En DB"
            };
    private ArrayList<Termino> terminos, terminosAgregar = new ArrayList<>();
    private boolean[][] editables;
    public ManejoLang ml = new ManejoLang();

    public GrillaTerminos(ArrayList<Termino> pDatos) {
        terminos = pDatos;
        editables = new boolean[terminos.size()][3];
        setEditable();
    }

    public int getRowCount() {
        return terminos.size();
    }

    public int getColumnCount() {
        return 4;
    }

    public String getColumnName(int columnIndex) {
        return mNames[columnIndex];
    }

    public Class<?> getColumnClass(int columnIndex) {
        if (getRowCount() > 0 && getValueAt(0, columnIndex) != null) {
            return getValueAt(0, columnIndex).getClass();
        }
        else {
            if (columnIndex == 3) {
                return ImageIcon.class;
            }
            else {
                return Object.class;
            }
        }
    }

    public boolean isCellEditable(int rowIndex, int columnIndex) {
        if (columnIndex != 2) {
            return false;
        }
        else {
            return editables[rowIndex][columnIndex];
        }
    }

    public Object getValueAt(int rowIndex, int columnIndex) {
        Termino wAux = terminos.get(rowIndex);
        Object wRes = null;
        switch (columnIndex) {
            case 0:
                wRes = wAux.getNombre();
                break;

            case 1:
                if (wAux.getTipoAsString().equalsIgnoreCase("")) {

                    wRes = ml.sugerenciaTipoConcepto(wAux);
                }

                break;

            case 2:

                if (!wAux.getTipo().equalsIgnoreCase("")) {

                    wRes = wAux.getTipoAsString();
                }

                break;
            // Poner el icono si el termino tiene tipo y no es editable (o sea, viene de la base)    
            case 3:

                if (!wAux.getTipo().equalsIgnoreCase("") && !isCellEditable(rowIndex, 2)) {

                    ImageIcon icono = new ImageIcon("resources/images/database_blue.png");
                    wRes = icono;
                }

                break;

        }

        return wRes;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 2) {
            fireTableCellUpdated(rowIndex, columnIndex);
        }

    }

    public void addTableModelListener(TableModelListener l) {

    }

    public void removeTableModelListener(TableModelListener l) {

    }

    public Termino getProductoAt(int rowIndex) {
        return terminos.get(rowIndex);
    }

    public void setEditable() {
        for (int i = 0; i < terminos.size(); i++) {
            if (!terminos.get(i).getTipoAsString().equalsIgnoreCase("")) {
                editables[i][2] = false;
            }
            else {
                editables[i][2] = true;
                if (terminos.get(i).getTipoAsString().equalsIgnoreCase("")) {
                    terminosAgregar.add(terminos.get(i));
                }
            }
        }
    }

    public ArrayList<Termino> TerminosAgregar() {

        return terminosAgregar;
    }
    
    public ArrayList<Termino> getTerminos() {
        return terminos;
    }

}
