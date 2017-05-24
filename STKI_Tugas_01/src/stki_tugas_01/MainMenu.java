/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package stki_tugas_01;

import java.awt.Desktop;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.StringTokenizer;
import java.util.Vector;
import javax.swing.JOptionPane;
import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.pdf.PDFParser;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.SAXException;

/**
 *
 * @author Satria Wibowo
 */
public class MainMenu extends javax.swing.JFrame {

    /**
     * Creates new form MainMenu
     */
    String file_name;
    
    public MainMenu() {
        super("STKI - Satria Wibowo - 06.2015.1.90451");
        initComponents();
    }

    void ekstrakPDF(String pathFile) throws IOException, TikaException, SAXException {
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
        simpanText(Isi, metadata, pathSimpan);
    }

    private void simpanText(String Isi, Metadata metadata, String pathSimpan) {
        PrintWriter fout = null;                                                
        File file1 = new File(pathSimpan);
        try {
            fout = new PrintWriter(new FileWriter(file1));                      
            fout.flush();                                                       
            fout.println(Isi + "\n");                                           
            fout.println("\n");
            String[] metadataNames = metadata.names();                          

            for (String name : metadataNames) {
                fout.println(name + " : " + metadata.get(name) + "\n");
            }

            fout.close();                                                       
            bacaText(pathSimpan);                                               
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "File tidak dapat dituliskan !!", "Message", JOptionPane.INFORMATION_MESSAGE);
            fout.close();
        }
    }

    void bacaText(String pathText) {
        taText.setText("");
        //proses pembacaan
        try {
            byte[] buffer = new byte[1024];
            BufferedInputStream bufferedInput = new BufferedInputStream(new FileInputStream(pathText));
            int bytesRead = 0;
            while ((bytesRead = bufferedInput.read(buffer)) != -1) {
                String kt = new String(buffer, 0, bytesRead);
                taText.append(kt);
            }
            bufferedInput.close();
            bacaIsiFile(pathText);
        } catch (IOException io) {
            JOptionPane.showMessageDialog(null, "File not support !!", "Message", JOptionPane.INFORMATION_MESSAGE);
        } catch (NoSuchElementException e) {
            JOptionPane.showMessageDialog(null, "Format tidak sama !!", "Message", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void bacaIsiFile(String path) {
        Vector dataKata = new Vector();
        Vector jumkata = new Vector();

        int count = 0, loop = 0;
        BufferedReader fileReader;
        StringTokenizer st;
        File file = new File(path);
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
                    } else if (dataKata.isEmpty()) {
                        dataKata.add(kt);
                        jumkata.add(loop, 1);
                    } else {
                        for (int i = 0; i < dataKata.size(); i++) {
                            if (kt.equalsIgnoreCase(dataKata.get(i).toString())) {
                                count = Integer.parseInt(jumkata.get(i).toString());
                                count++;
                                jumkata.set(i, count);
                            }
                        }
                        if (count == 1) {
                            dataKata.add(kt);
                            loop++;
                            jumkata.add(1);
                        }
                    }
                }
            }
            tblKata.setModel(new TabelModel(dataKata, jumkata));

            this.CetakJumlahKata(dataKata, jumkata, path);
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
            fout = new PrintWriter(new FileWriter(file1,true));
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

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        openFile = new javax.swing.JFileChooser();
        txtNama = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        btnCari = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblKata = new javax.swing.JTable();
        jScrollPane1 = new javax.swing.JScrollPane();
        taText = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jLabel1.setText("Nama File");

        btnCari.setText("Cari");
        btnCari.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCariActionPerformed(evt);
            }
        });

        tblKata.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {},
                {},
                {},
                {}
            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tblKata);

        taText.setColumns(20);
        taText.setRows(5);
        jScrollPane1.setViewportView(taText);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(20, 20, 20)
                        .addComponent(txtNama)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnCari))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 319, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 296, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNama, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(btnCari))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 254, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCariActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCariActionPerformed
        // TODO add your handling code here:
        int returnVal = openFile.showOpenDialog(this);
        if (returnVal == javax.swing.JFileChooser.APPROVE_OPTION) {
            java.io.File file = openFile.getSelectedFile();
            file_name = file.toString();
            txtNama.setText(file_name);

            //untuk membuka file pdf.
            if (Desktop.isDesktopSupported()) {
                try {
                    File myFile = new File(file_name);
                    Desktop.getDesktop().open(myFile);
                } catch (IOException e) {
                    // System probably doesn't have a default PDF program
                }
            }
            
            try {
                ekstrakPDF(file_name);
            } catch (IOException | TikaException | SAXException io ) {
                JOptionPane.showMessageDialog(null, "Bukan File PDF!!", "Message", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }//GEN-LAST:event_btnCariActionPerformed

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
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainMenu.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainMenu().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCari;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JFileChooser openFile;
    private javax.swing.JTextArea taText;
    private javax.swing.JTable tblKata;
    private javax.swing.JTextField txtNama;
    // End of variables declaration//GEN-END:variables
}
