package org.psu.bulksummarizer;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import org.psu.bulksummarizer.util.XmlFileFilter;
import org.psu.bulksummarizer.util.XmlProcessorThread;

public class PrimaryFrame extends javax.swing.JFrame {

  public PrimaryFrame() {
    initComponents();
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    selectDirectoryLabel = new javax.swing.JLabel();
    selectDirectoryPathText = new javax.swing.JTextField();
    selectDirectoryButton = new javax.swing.JButton();
    selectOutputFileLabel = new javax.swing.JLabel();
    selectCsvFilePathText = new javax.swing.JTextField();
    selectCsvPathButton = new javax.swing.JButton();
    summarizeButton = new javax.swing.JButton();
    statusText = new javax.swing.JLabel();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    selectDirectoryLabel.setText("Select a directory of Newhall XML files:");

    selectDirectoryPathText.setEditable(false);

    selectDirectoryButton.setText("...");
    selectDirectoryButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        selectDirectoryButtonActionPerformed(evt);
      }
    });

    selectOutputFileLabel.setText("Select CSV export file path:");

    selectCsvFilePathText.setEditable(false);

    selectCsvPathButton.setText("...");
    selectCsvPathButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        selectCsvPathButtonActionPerformed(evt);
      }
    });

    summarizeButton.setText("Summarize to CSV");
    summarizeButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        summarizeButtonActionPerformed(evt);
      }
    });

    statusText.setText("Waiting.");

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addComponent(selectDirectoryPathText, javax.swing.GroupLayout.DEFAULT_SIZE, 459, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(selectDirectoryButton))
              .addComponent(selectDirectoryLabel)
              .addComponent(selectOutputFileLabel)))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addGap(24, 24, 24)
            .addComponent(selectCsvFilePathText, javax.swing.GroupLayout.DEFAULT_SIZE, 457, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(selectCsvPathButton))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
            .addContainerGap()
            .addComponent(statusText)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 363, Short.MAX_VALUE)
            .addComponent(summarizeButton)))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(selectDirectoryLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(selectDirectoryPathText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(selectDirectoryButton))
        .addGap(18, 18, 18)
        .addComponent(selectOutputFileLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(selectCsvFilePathText, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(selectCsvPathButton))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(summarizeButton)
          .addComponent(statusText))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void selectDirectoryButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectDirectoryButtonActionPerformed
      JFileChooser jfc = new JFileChooser(".");
      jfc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
      int saveDialogResult = jfc.showOpenDialog(this);

      while (true) {
        if (saveDialogResult == JOptionPane.OK_OPTION) {
          if (jfc.getSelectedFile() != null) {
            System.out.println("Directory selected: " + jfc.getSelectedFile().getAbsolutePath());
            break;
          } else {
            System.out.println("User warned to select a directory, looping.");
            int result = JOptionPane.showConfirmDialog(null, "Please select a directory containing Newhall XML files.",
                    "Select a Directory", JOptionPane.CLOSED_OPTION, JOptionPane.QUESTION_MESSAGE);
            saveDialogResult = jfc.showSaveDialog(this);
          }
        } else {
          System.out.println("Aborted directory selection.");
          selectDirectoryPathText.setText("");
          return;
        }
      }

      System.out.println("Leaving directory selector loop.  Selected: " + jfc.getSelectedFile().getAbsolutePath());
      selectDirectoryPathText.setText(jfc.getSelectedFile().getAbsolutePath());
    }//GEN-LAST:event_selectDirectoryButtonActionPerformed

    private void selectCsvPathButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectCsvPathButtonActionPerformed
      JFileChooser jfc = new JFileChooser(".");
      int saveDialogResult = jfc.showSaveDialog(this);

      if (saveDialogResult == JOptionPane.OK_OPTION) {
        if (jfc.getSelectedFile() != null) {
          if (jfc.getSelectedFile().exists()) {
            int result = JOptionPane.showConfirmDialog(null, "The file " + jfc.getSelectedFile().getName()
                    + " already exists, overwrite it?", "Confirm Overwrite", JOptionPane.OK_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE);
            if (result == JOptionPane.CANCEL_OPTION) {
              System.out.println("Aborted file selection.");
              selectCsvFilePathText.setText("");
              return;
            } else {
              System.out.println("File selected: " + jfc.getSelectedFile().getAbsolutePath());
              selectCsvFilePathText.setText(jfc.getSelectedFile().getAbsolutePath());
            }
          } else {
            if (jfc.getSelectedFile().getAbsoluteFile().getName().endsWith(".csv")) {
              selectCsvFilePathText.setText(jfc.getSelectedFile().getAbsolutePath());
            } else {
              selectCsvFilePathText.setText(jfc.getSelectedFile().getAbsolutePath() + ".csv");
            }
          }
        }
      } else {
        System.out.println("Aborted file selection.");
        selectCsvFilePathText.setText("");
      }
    }//GEN-LAST:event_selectCsvPathButtonActionPerformed

    private void summarizeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_summarizeButtonActionPerformed

      summarizeButton.setEnabled(false);

      // Check status of parameters.
      final File inputDir = new File(selectDirectoryPathText.getText());
      final File outputFile = new File(selectCsvFilePathText.getText());

      if (!inputDir.exists() || !inputDir.isDirectory()) {
        statusText.setText("Input directory is not a valid path, correct it.");
        return;
      }

      if (outputFile == null || !outputFile.getParentFile().exists()) {
        statusText.setText("Output file path is not valid, correct it.");
        return;
      }

      // Setup file output writing.
      FileWriter fw;
      try {
        fw = new FileWriter(outputFile);
      } catch (IOException ex) {
        statusText.setText("Cannot create output file as specified, correct it.");
        summarizeButton.setEnabled(true);
        return;
      }

      // Get list of *.xml files from input directory.
      List<File> xmlFiles = new ArrayList<File>();
      xmlFiles.addAll(Arrays.asList(inputDir.listFiles(new XmlFileFilter())));
      System.out.println("Loaded " + xmlFiles.size() + " XML files.");

      if (xmlFiles.isEmpty()) {
        statusText.setText("No XML files found in the specified directory.");
        summarizeButton.setEnabled(true);
        return;
      } else {
        statusText.setText("Working on " + xmlFiles.size() + " XML files...");
      }

      // Pass FileWriter, xmlFiles, statusText, and summarizeButton to Thread.
      new XmlProcessorThread(fw, xmlFiles, statusText, summarizeButton).start();
    }//GEN-LAST:event_summarizeButtonActionPerformed
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JTextField selectCsvFilePathText;
  private javax.swing.JButton selectCsvPathButton;
  private javax.swing.JButton selectDirectoryButton;
  private javax.swing.JLabel selectDirectoryLabel;
  private javax.swing.JTextField selectDirectoryPathText;
  private javax.swing.JLabel selectOutputFileLabel;
  private javax.swing.JLabel statusText;
  private javax.swing.JButton summarizeButton;
  // End of variables declaration//GEN-END:variables
}
