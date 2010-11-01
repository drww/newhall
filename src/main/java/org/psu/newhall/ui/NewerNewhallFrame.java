package org.psu.newhall.ui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.psu.newhall.sim.BASICSimulationModel;
import org.psu.newhall.sim.NewhallDataset;
import org.psu.newhall.sim.NewhallResults;
import org.psu.newhall.util.CSVFileParser;
import org.psu.newhall.util.XMLFileParser;
import org.psu.newhall.util.XMLResultsExporter;

public class NewerNewhallFrame extends javax.swing.JFrame {

  NewhallDataset dataset;

  public NewerNewhallFrame() {
    initComponents();
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jTabbedPane1 = new javax.swing.JTabbedPane();
    jPanel1 = new javax.swing.JPanel();
    stationNameLabel = new javax.swing.JLabel();
    stationName = new javax.swing.JTextField();
    jButton1 = new javax.swing.JButton();
    whcSpinner = new javax.swing.JSpinner();
    whcSpinnerLabel = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    jPanel3 = new javax.swing.JPanel();
    jMenuBar1 = new javax.swing.JMenuBar();
    jMenu1 = new javax.swing.JMenu();
    OpenFileMenuItem = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    stationNameLabel.setText("Station Name:");

    jButton1.setText("Export to XML");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        jButton1ActionPerformed(evt);
      }
    });

    whcSpinnerLabel.setText("Water Holding Capacity:");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
            .addComponent(stationNameLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(stationName, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE))
          .addGroup(jPanel1Layout.createSequentialGroup()
            .addComponent(whcSpinnerLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(whcSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 300, Short.MAX_VALUE)
            .addComponent(jButton1)))
        .addContainerGap())
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(stationNameLabel)
          .addComponent(stationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 381, Short.MAX_VALUE)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(jButton1)
          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
            .addComponent(whcSpinnerLabel)
            .addComponent(whcSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );

    jTabbedPane1.addTab("Metadata", jPanel1);

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 575, Short.MAX_VALUE)
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 446, Short.MAX_VALUE)
    );

    jTabbedPane1.addTab("Input", jPanel2);

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 575, Short.MAX_VALUE)
    );
    jPanel3Layout.setVerticalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGap(0, 446, Short.MAX_VALUE)
    );

    jTabbedPane1.addTab("Model Results", jPanel3);

    jMenu1.setText("File");

    OpenFileMenuItem.setText("Open ...");
    OpenFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        OpenFileMenuItemActionPerformed(evt);
      }
    });
    jMenu1.add(OpenFileMenuItem);

    jMenuBar1.add(jMenu1);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jTabbedPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 474, Short.MAX_VALUE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

  private void OpenFileMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_OpenFileMenuItemActionPerformed
    JFileChooser jfc = new JFileChooser(".");
    int returnCondition = jfc.showOpenDialog(this);
    if (returnCondition == JFileChooser.APPROVE_OPTION) {
      System.out.println("Attempting XML parsing.");
      File selectedFile = jfc.getSelectedFile();
      NewhallDataset newDataset = null;
      try {
        XMLFileParser xfp = new XMLFileParser(selectedFile);
        newDataset = xfp.getDataset();
      } catch (Exception e) {
        System.out.println("Attempting CSV parsing.");
        try {
          CSVFileParser cfp = new CSVFileParser(selectedFile);
          newDataset = cfp.getDatset();
        } catch (Exception ee) {
          System.out.println("Unacceptable file detected.");
          JOptionPane.showMessageDialog(this,
                  "Selected file is not formatted as a Newhall CSV or XML document.");
          return;
        }
      }

      dataset = newDataset;
      System.out.println("Loaded: " + dataset);

      stationName.setText(dataset.getName());

      // Run model.
      // Populate display.

    }
  }//GEN-LAST:event_OpenFileMenuItemActionPerformed

  private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed

    JFileChooser jfc = new JFileChooser(".");
    int saveDialogResult = jfc.showSaveDialog(this);
    if (saveDialogResult == JOptionPane.OK_OPTION) {
      if (jfc.getSelectedFile() != null && jfc.getSelectedFile().exists()) {
        int result = JOptionPane.showConfirmDialog(null, "The file " + jfc.getSelectedFile().getName()
                + " already exists, overwrite it?", "Confirm Overwrite", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
        if (result == JOptionPane.CANCEL_OPTION) {
          return;
        }
      }

      XMLResultsExporter exporter = new XMLResultsExporter(jfc.getSelectedFile());
      try {
        NewhallResults results = BASICSimulationModel.runSimulation(dataset, Double.valueOf(whcSpinner.getValue().toString()).doubleValue());
        exporter.export(results, dataset);
      } catch (IOException ex) {
        Logger.getLogger(NewerNewhallFrame.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  }//GEN-LAST:event_jButton1ActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenuItem OpenFileMenuItem;
  private javax.swing.JButton jButton1;
  private javax.swing.JMenu jMenu1;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JTabbedPane jTabbedPane1;
  private javax.swing.JTextField stationName;
  private javax.swing.JLabel stationNameLabel;
  private javax.swing.JSpinner whcSpinner;
  private javax.swing.JLabel whcSpinnerLabel;
  // End of variables declaration//GEN-END:variables
}
