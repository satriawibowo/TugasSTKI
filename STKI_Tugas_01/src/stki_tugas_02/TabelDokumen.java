/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stki_tugas_02;

/**
 *
 * @author Satria Wibowo
 */
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.table.AbstractTableModel;

/**
 *
 * @author Satria Wibowo
 */
public final class TabelDokumen extends AbstractTableModel {

    private String[] colNames = {"", ""};
    private Object[][] data = new Object[1][2];

    public TabelDokumen() {

    }
 
    public TabelDokumen(String dokumen[][]) {
        int vs;
        vs = dokumen.length;
        colNames[0] = "Dokumen";
        colNames[1] = "Path";
        data = new Object[vs][2];
        try {
            this.setData(dokumen,vs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Tampilkan Daftar File !!\n"+e.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);
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

    public void setData(String dok[][], int j) {
        int i;
        for (i = 0; i < j; i++) {
            data[i][0] = dok[i][0];
            data[i][1] = dok[i][1];
        }
    }

}
