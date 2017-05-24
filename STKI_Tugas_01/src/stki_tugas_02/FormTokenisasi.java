/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stki_tugas_02;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.JOptionPane;
import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;
import java.util.Vector;
import javax.swing.RowFilter;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.apache.lucene.analysis.id.*;

/**
 *
 * @author Satria Wibowo
 */
public class FormTokenisasi extends javax.swing.JFrame {

    /**
     * Creates new form MainMenu
     */
    String arrayFile[][];
    Vector vFile = new Vector();
    Vector vPathFile = new Vector();
    Vector vDataKata = new Vector();
    Vector vJumkata = new Vector();
    TableRowSorter<TableModel> sorter3;
    TableRowSorter<TableModel> sorter2;
    TableRowSorter<TableModel> sorter1;

    public FormTokenisasi() {
        super("STKI 5 - Satria Wibowo & M. Yusuf Efendi");
        initComponents();
//        tblDokumen.setModel(new TabelDokumen());
//        tblIndex.setModel(new TabelIndex());         
    }

    void ekstrakPDF(String File, String pathFile) throws IOException, TikaException, SAXException {
        //ContentHandler textHandler = new BodyContentHandler(int writeLimit);

        BodyContentHandler handler = new BodyContentHandler();
        Metadata metadata = new Metadata();
        FileInputStream inputstream = new FileInputStream(new File(pathFile));
        ParseContext pcontext = new ParseContext();

        //parsing the document using PDF parser
        PDFParser pdfparser = new PDFParser();
        pdfparser.parse(inputstream, handler, metadata, pcontext);

        String Isi = handler.toString();                                          //simpan isi ke variabel
        //path untuk simpan text hasil ekstrak pdf
        String pathSimpan = pathFile.substring(0, pathFile.length() - 4) + "_toText.txt";
        //simpan hasil ekstrak pdf
        simpanText(Isi, metadata, File, pathSimpan);
    }

    private void simpanText(String Isi, Metadata metadata, String File, String pathSimpan) {
        PrintWriter fout = null;
        File file1 = new File(pathSimpan);
        try {
            fout = new PrintWriter(new FileWriter(file1));
            fout.flush();
            fout.println(Isi + "\n");
            fout.println("\n");
            String[] metadataNames = metadata.names();

//            for (String name : metadataNames) {
//                fout.println(name + " : " + metadata.get(name) + "\n");
//            }
            fout.close();
            bacaIsiFile(File, pathSimpan);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File\n" + pathSimpan + "\n tidak dapat dituliskan !!\n" + e.toString(), "Message", JOptionPane.INFORMATION_MESSAGE);
            fout.close();
        }
    }

    private void bacaIsiFile(String File, String pathFile) {

        int count = 0, loop = 0;
        BufferedReader fileReader;
        StringTokenizer st;
        File file = new File(pathFile);
        try {
            fileReader = new BufferedReader(new FileReader(file));
            while (true) {
                String line = fileReader.readLine();
                //    taText.append(line);

                if (line == null) {
                    break;
                }
                line = line.replace(".", " ");
                line = line.replace("/", " ");
                line = line.replace("(", " ");
                line = line.replace(")", " ");
                line = line.replace(",", " ");
                line = line.replace("<", " ");
                line = line.replace(">", " ");
                line = line.replace("?", " ");
                line = line.replace("\'", " ");
                line = line.replace("\"", " ");
                line = line.replace("!", " ");
                line = line.replace("@", " ");
                line = line.replace("#", " ");
                line = line.replace("$", " ");
                line = line.replace("&", " ");
                line = line.replace(":", " ");
                line = line.replace(";", " ");
                line = line.replace("[", " ");
                line = line.replace("]", " ");
                line = line.replace("{", " ");
                line = line.replace("}", " ");
                line = line.replace("\\", " ");
                line = line.replace("|", " ");
                line = line.replace("+", " ");
                line = line.replace("=", " ");
                line = line.replace("-", " ");
                line = line.replace("*", " ");
                line = line.replace("^", " ");
                line = line.replace("%", " ");
                line = line.replace("`", " ");
                line = line.replace("~", " ");

                st = new StringTokenizer(line);
                while (st.hasMoreTokens()) {
                    count = 1;
                    String kt = st.nextToken();
                    kt = kt.toLowerCase();
                    if (kt.equals(":") || kt.equals("|") || kt.equals("?")) {
                        continue;
                    } else if (vFile.isEmpty()) {
                        vFile.add(File);
                        vPathFile.add(pathFile.substring(0, pathFile.length() - 11));
                        vDataKata.add(kt);
                        vJumkata.add(loop, 1);
                    } else {
                        for (int i = 0; i < vFile.size(); i++) {
                            if (File.equalsIgnoreCase(vFile.get(i).toString()) && kt.equalsIgnoreCase(vDataKata.get(i).toString())) {
                                count = Integer.parseInt(vJumkata.get(i).toString());
                                count++;
                                vJumkata.set(i, count);
                            }
                        }
                        if (count == 1) {
                            vFile.add(File);
                            vPathFile.add(pathFile.substring(0, pathFile.length() - 11));
                            vDataKata.add(kt);
                            loop++;
                            vJumkata.add(1);
                        }
                    }
                }
            }
            //tblIndex.setModel(new TabelIndex(File, pathFile.substring(0, pathFile.length() - 11), dataKata, jumkata));

            //this.CetakJumlahKata(dataKata, jumkata, path);
            //this.tulisText(dataKata, jumkata, path.substring(0, path.length() - 4) + "_hasil" + path.substring(path.length() - 4, path.length()));
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "No file  !!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(null, "Format tidak sama !!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void CetakJumlahKata(Vector kt, Vector jum, String target) {
        PrintWriter fout = null;
        File file1 = new File(target);
        try {
            fout = new PrintWriter(new FileWriter(file1, true));
            fout.println("\n");
            fout.println("Jumlah Perkata\n");
            fout.println("---------------\n \n");
            for (int i = 0; i < kt.size(); i++) {
                fout.println(kt.elementAt(i).toString() + " = " + jum.elementAt(i).toString() + "\n");
            }
            fout.close();

            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File(target);
                    Desktop.getDesktop().open(myFile);
                } catch (IOException e) {
                    // System probably doesn't have a default PDF program
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File tidak dapat dituliskan !!", "Message", JOptionPane.INFORMATION_MESSAGE);
            fout.close();
        }
    }

    String stemmKata(String term) {
        IndonesianStemmer stemmer = new IndonesianStemmer();
        char[] chars = term.toCharArray();
        int len = stemmer.stem(chars, chars.length, true);
        String stem = new String(chars, 0, len);
        return stem;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openFile = new javax.swing.JFileChooser();
        saveFile = new javax.swing.JFileChooser();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblIndex = new javax.swing.JTable();
        txtCari = new javax.swing.JTextField();
        btnCari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblIndexClear = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tblDokumen = new javax.swing.JTable();
        lblIndex = new javax.swing.JLabel();
        lblIndexClear = new javax.swing.JLabel();
        lblIndex1 = new javax.swing.JLabel();
        lblStem = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        tblStem = new javax.swing.JTable();
        btnSimpan = new javax.swing.JButton();
        ManuBar = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenuItem2 = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);

        tblIndex.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane1.setViewportView(tblIndex);

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        tblIndexClear.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblIndexClear);

        tblDokumen.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tblDokumen);

        lblIndex.setText("0 Baris Kata");

        lblIndexClear.setText("0 Baris Kata");

        lblIndex1.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        lblIndex1.setText("Pencarian");

        lblStem.setText("0 Baris Kata");

        tblStem.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane4.setViewportView(tblStem);

        btnSimpan.setText("Simpan");
        btnSimpan.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSimpanActionPerformed(evt);
            }
        });

        jMenu1.setText("Menu");

        jMenuItem1.setText("Cari File");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuItem2.setText("Proses");
        jMenuItem2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem2ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem2);

        ManuBar.add(jMenu1);

        setJMenuBar(ManuBar);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane3)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblIndex1, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(txtCari, javax.swing.GroupLayout.PREFERRED_SIZE, 573, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblIndex)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIndexClear))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(lblStem)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(btnSimpan)))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 181, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnCari, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCari)
                        .addComponent(lblIndex1, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 219, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addGap(15, 15, 15)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblIndex, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblIndexClear))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblStem)
                            .addComponent(btnSimpan))))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        // TODO add your handling code here:
        vFile = new Vector();
        vPathFile = new Vector();
        vDataKata = new Vector();
        vJumkata = new Vector();

        openFile.setMultiSelectionEnabled(true);
        int returnVal = openFile.showOpenDialog(this);

        try {
            if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
                File[] selectedFile = openFile.getSelectedFiles();
                int jml = selectedFile.length;

                arrayFile = new String[jml][2];

                for (int i = 0; i < jml; i++) {
                    String idx = "File_" + (i + 1);
                    String name = selectedFile[i].toString();

                    arrayFile[i][0] = idx;
                    arrayFile[i][1] = name;
                }

                tblDokumen.setModel(new TabelDokumen(arrayFile));
                tblDokumen.getColumnModel().getColumn(0).setMinWidth(0);
                tblDokumen.getColumnModel().getColumn(0).setMaxWidth(200);
            }
            lblIndexClear.setText("0 Baris Kata");
            lblIndex.setText("0 Baris Kata");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Mendapatkan File\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem2ActionPerformed
        // TODO add your handling code here:
        int jumlah = arrayFile.length;
        for (int i = 0; i < jumlah; i++) {
            try {
                ekstrakPDF(arrayFile[i][0], arrayFile[i][1]);
            } catch (IOException | TikaException | SAXException io) {
                JOptionPane.showMessageDialog(null, "Gagal Proses File\n" + arrayFile[i][1], "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
        tblIndex.setModel(new TabelIndex(vFile, vPathFile, vDataKata, vJumkata));
        tblIndex.getColumnModel().getColumn(1).setMinWidth(0);
        tblIndex.getColumnModel().getColumn(1).setMaxWidth(0);

        sorter1 = new TableRowSorter<TableModel>(tblIndex.getModel());
        tblIndex.setRowSorter(sorter1);

        String sCurrentLine;
        String[] stopwords = new String[2000];
        int k = 0;
        try {
            FileReader fr = new FileReader("stopword.txt");
            BufferedReader br = new BufferedReader(fr);
            while ((sCurrentLine = br.readLine()) != null) {
                stopwords[k] = sCurrentLine;
                k++;
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Gagal Mendapatkan StopWord\n" + e.toString(), "Error", JOptionPane.ERROR_MESSAGE);
        }

        for (int i = 0; i < k; i++) {
            for (int j = 0; j < vFile.size(); j++) {
                if (stopwords[i].equals(vDataKata.get(j).toString())) {
                    vFile.remove(j);
                    vPathFile.remove(j);
                    vDataKata.remove(j);
                    vJumkata.remove(j);
                }
            }
        }
        tblIndexClear.setModel(new TabelIndex(vFile, vPathFile, vDataKata, vJumkata));
        tblIndexClear.getColumnModel().getColumn(1).setMinWidth(0);
        tblIndexClear.getColumnModel().getColumn(1).setMaxWidth(0);

        sorter2 = new TableRowSorter<TableModel>(tblIndexClear.getModel());
        tblIndexClear.setRowSorter(sorter2);

        Vector xFile = new Vector();
        Vector xPathFile = new Vector();
        Vector xDataKata = new Vector();
        Vector xJumkata = new Vector();
        int loop = 0;
        for (int j = 0; j < vFile.size(); j++) {
            int count = 1;
            String kt = stemmKata(vDataKata.get(j).toString());

            if (kt.equals(":") || kt.equals("|") || kt.equals("?") || kt.length() < 3) {
                continue;
            } else if (xFile.isEmpty()) {
                xFile.add(vFile.get(j).toString());
                xPathFile.add(vPathFile.get(j).toString());
                xDataKata.add(kt);
                xJumkata.add(vJumkata.get(j).toString());
            } else {
                for (int i = 0; i < xFile.size(); i++) {
                    if (vFile.get(j).toString().equalsIgnoreCase(xFile.get(i).toString()) && kt.equalsIgnoreCase(xDataKata.get(i).toString())) {
                        int jml = Integer.parseInt(xJumkata.get(i).toString()) + Integer.parseInt(vJumkata.get(j).toString());
                        xJumkata.set(i, jml);
                        count++;
                    }
                }
                if (count == 1) {
                    xFile.add(vFile.get(j).toString());
                    xPathFile.add(vPathFile.get(j).toString());
                    xDataKata.add(kt);
                    loop++;
                    xJumkata.add(vJumkata.get(j).toString());
                }
            }
        }
        tblStem.setModel(new TabelIndex(xFile, xPathFile, xDataKata, xJumkata));
        tblStem.getColumnModel().getColumn(1).setMinWidth(0);
        tblStem.getColumnModel().getColumn(1).setMaxWidth(0);

        sorter3 = new TableRowSorter<TableModel>(tblStem.getModel());
        tblStem.setRowSorter(sorter3);

        lblIndexClear.setText(tblIndexClear.getRowCount() + " Baris Kata");
        lblIndex.setText(tblIndex.getRowCount() + " Baris Kata");
        lblStem.setText(tblStem.getRowCount() + " Baris Kata");

    }//GEN-LAST:event_jMenuItem2ActionPerformed

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        String text = txtCari.getText();
        if (text.length() == 0) {
            sorter1.setRowFilter(null);
            sorter2.setRowFilter(null);
            sorter3.setRowFilter(null);
        } else {
            String aon = null;
            String first = null;
            String last = null;

            int token = 0;
            StringTokenizer st = new StringTokenizer(text);
            while (st.hasMoreTokens()) {
                String kt = st.nextToken();
                if (token == 0) {
                    first = kt.trim();
                }                
                if (kt.equals("AND")) {
                    aon = "AND";
                } else if (kt.equals("OR")) {
                    aon = "OR";
                }
                last = kt.trim();
                token++;
            }

            if (token == 1) {
                sorter1.setRowFilter(RowFilter.regexFilter(text));
                sorter2.setRowFilter(RowFilter.regexFilter(text));
                sorter3.setRowFilter(RowFilter.regexFilter(text));
            }else if (token == 3 && aon == "AND") {
                List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
                filters.add(RowFilter.regexFilter(first));
                filters.add(RowFilter.regexFilter(last));

                sorter1.setRowFilter(RowFilter.andFilter(filters));
                sorter2.setRowFilter(RowFilter.andFilter(filters));
                sorter3.setRowFilter(RowFilter.andFilter(filters));
            } else if (token == 3 && aon == "OR") {
                List<RowFilter<Object, Object>> filters = new ArrayList<RowFilter<Object, Object>>(2);
                filters.add(RowFilter.regexFilter(first));
                filters.add(RowFilter.regexFilter(last));

                sorter1.setRowFilter(RowFilter.orFilter(filters));
                sorter2.setRowFilter(RowFilter.orFilter(filters));
                sorter3.setRowFilter(RowFilter.orFilter(filters));

            } else {
                JOptionPane.showMessageDialog(null, "cek kembali parameter pencarian anda", "Message", JOptionPane.INFORMATION_MESSAGE);
            }

        }
        lblIndexClear.setText(tblIndexClear.getRowCount() + " Baris Kata");
        lblIndex.setText(tblIndex.getRowCount() + " Baris Kata");
        lblStem.setText(tblStem.getRowCount() + " Baris Kata");

    }//GEN-LAST:event_btnCariActionPerformed

    private void btnSimpanActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSimpanActionPerformed
        // TODO add your handling code here:
        saveFile.setCurrentDirectory(new File("C:/"));
        int retrival = saveFile.showSaveDialog(null);
        if (retrival == saveFile.APPROVE_OPTION) {
            try {
                TableModel tableModel = tblStem.getModel();
                FileWriter fOut = new FileWriter(saveFile.getSelectedFile() + ".xls");

                for (int i = 0; i < tableModel.getColumnCount(); i++) {
                    fOut.write(tableModel.getColumnName(i) + "\t");
                }

                fOut.write("\n");

                for (int i = 0; i < tableModel.getRowCount(); i++) {
                    for (int j = 0; j < tableModel.getColumnCount(); j++) {
                        fOut.write(tableModel.getValueAt(i, j).toString() + "\t");
                    }
                    fOut.write("\n");
                }
                fOut.close();

            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }//GEN-LAST:event_btnSimpanActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FormTokenisasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FormTokenisasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FormTokenisasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FormTokenisasi.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new FormTokenisasi().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JMenuBar ManuBar;
    private javax.swing.JButton btnCari;
    private javax.swing.JButton btnSimpan;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JLabel lblIndex;
    private javax.swing.JLabel lblIndex1;
    private javax.swing.JLabel lblIndexClear;
    private javax.swing.JLabel lblStem;
    private javax.swing.JFileChooser openFile;
    private javax.swing.JFileChooser saveFile;
    private javax.swing.JTable tblDokumen;
    private javax.swing.JTable tblIndex;
    private javax.swing.JTable tblIndexClear;
    private javax.swing.JTable tblStem;
    private javax.swing.JTextField txtCari;
    // End of variables declaration//GEN-END:variables
}
