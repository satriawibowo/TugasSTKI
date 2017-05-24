/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stki_tugas_01;

/**
 *
 * @author Satria Wibowo
 */
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

public final class TabelModel extends AbstractTableModel {

    private String[] colNames = {"", ""};
    private Object[][] data = new Object[1][2];
    private int count;

    public TabelModel() {

    }

    public TabelModel(Vector v, Vector d) {
        int vs, ds = 0;
        vs = v.size();
        ds = d.size();

        colNames[0] = "Kata";
        colNames[1] = "Jumlah Kata";
        data = new Object[vs][2];
        try {
            this.setData(v, d, vs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Format Data tidak sama !!", "Message", JOptionPane.INFORMATION_MESSAGE);
            data = new Object[vs][2];
        }
    }

    public TabelModel(Vector v) {
        int vs;
        vs = v.size();
        colNames[0] = "No";
        colNames[1] = "Nama File";
        data = new Object[vs][2];
        try {
            this.setData(v, vs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Format Data tidak sama !!", "Message", JOptionPane.INFORMATION_MESSAGE);
            data = new Object[vs][2];
        }
    }

    @Override
    public int getRowCount() {
        return data.length;
    }

    @Override
    public int getColumnCount() {
        return colNames.length;
    }

    @Override
    public String getColumnName(int col) {
        return colNames[col];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        return data[rowIndex][columnIndex];
    }

    @Override
    public void setValueAt(Object value, int row, int col) {
        data[row][col] = value;
        fireTableCellUpdated(row, col);
    }

    public void setData(Vector v, Vector d, int j) {
        int i;
        for (i = 0; i < j; i++) {
            data[i][0] = v.elementAt(i);
            data[i][1] = d.elementAt(i);
        }
        count = i;
    }

    public void setData(Vector v, int j) {
        int i;
        for (i = 0; i < j; i++) {
            data[i][0] = i + 1;
            data[i][1] = v.elementAt(i);
        }
        count = i;
    }
}
