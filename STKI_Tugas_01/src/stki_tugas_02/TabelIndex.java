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
public final class TabelIndex extends AbstractTableModel {

    private String[] colNames = {"","","",""};
    private Object[][] data = new Object[1][4];
    private int count;

    public TabelIndex() {

    }
     
    public TabelIndex(Vector Dokumen, Vector Path, Vector v, Vector d) {
        int vs = 0;
        vs = v.size();
        
        colNames[0] = "File";
        colNames[1] = "Path";
        colNames[2] = "Kata";
        colNames[3] = "Jumlah Kata";
        data = new Object[vs][4];
        try {
            this.setData(Dokumen, Path, v, d, vs);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Gagal Tampilkan Daftar Index !!\n"+e.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);
            data = new Object[vs][4];
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

    public void setData(Vector Dokumen, Vector Path, Vector v, Vector d, int vs) {
        int i;
        for (i = 0; i < vs; i++) {
            data[i][0] = Dokumen.elementAt(i);
            data[i][1] = Path.elementAt(i);
            data[i][2] = v.elementAt(i);
            data[i][3] = d.elementAt(i);
        }
        count = i;
    }
    
}

