package newhall.ui;

import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.table.TableModel;
import newhall.sim.BASICSimulationModel;
import newhall.sim.NewhallDataset;
import newhall.sim.NewhallResults;
import newhall.util.CSVResultsExporter;

public class DefaultNewhallFrame extends javax.swing.JFrame {

  private NewhallDataset nd;
  private NewhallResults nr;
  private boolean inMetric = true;

  public DefaultNewhallFrame() {
    initComponents();
    this.setTitle("Newhall");
    this.whcSpinner.setValue(200.0);
    this.whcSpinner.setEnabled(false);
    System.out.println("datasetJTable is null: " + (datasetTable == null));
  }

  /** This method is called from within the constructor to
   * initialize the form.
   * WARNING: Do NOT modify this code. The content of this method is
   * always regenerated by the Form Editor.
   */
  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    jSeparator1 = new javax.swing.JSeparator();
    datasetPanel = new javax.swing.JPanel();
    jLabel1 = new javax.swing.JLabel();
    jLabel2 = new javax.swing.JLabel();
    stationText = new javax.swing.JLabel();
    elevationText = new javax.swing.JLabel();
    jLabel9 = new javax.swing.JLabel();
    latitudeText = new javax.swing.JLabel();
    jLabel11 = new javax.swing.JLabel();
    longitudeText = new javax.swing.JLabel();
    datasetScrollPane = new javax.swing.JScrollPane();
    datasetTable = new javax.swing.JTable();
    modelResultsPanel = new javax.swing.JPanel();
    jLabel3 = new javax.swing.JLabel();
    jLabel4 = new javax.swing.JLabel();
    jLabel5 = new javax.swing.JLabel();
    jLabel6 = new javax.swing.JLabel();
    whcUnitsText = new javax.swing.JLabel();
    whcSpinner = new javax.swing.JSpinner();
    datasetScrollPane1 = new javax.swing.JScrollPane();
    mpeTable = new javax.swing.JTable();
    moistureRegimeText = new javax.swing.JLabel();
    temperatureRegimeText = new javax.swing.JLabel();
    annualRainfallText = new javax.swing.JLabel();
    jPanel1 = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    jTextArea1 = new javax.swing.JTextArea();
    jScrollPane3 = new javax.swing.JScrollPane();
    jTextArea2 = new javax.swing.JTextArea();
    jScrollPane1 = new javax.swing.JScrollPane();
    moistureCalendarText = new javax.swing.JTextArea();
    jLabel7 = new javax.swing.JLabel();
    jPanel2 = new javax.swing.JPanel();
    jScrollPane4 = new javax.swing.JScrollPane();
    jTextArea3 = new javax.swing.JTextArea();
    jScrollPane5 = new javax.swing.JScrollPane();
    jTextArea4 = new javax.swing.JTextArea();
    jScrollPane6 = new javax.swing.JScrollPane();
    temperatureCalendarText = new javax.swing.JTextArea();
    jLabel8 = new javax.swing.JLabel();
    jPanel3 = new javax.swing.JPanel();
    jScrollPane7 = new javax.swing.JScrollPane();
    statisticsText = new javax.swing.JTextArea();
    exportCsvButton = new javax.swing.JButton();
    jMenuBar1 = new javax.swing.JMenuBar();
    fileMenu = new javax.swing.JMenu();
    openDatasetMenuItem = new javax.swing.JMenuItem();
    exitMenuItem = new javax.swing.JMenuItem();
    optionsMenu = new javax.swing.JMenu();
    toggleUnitsMenuItem = new javax.swing.JMenuItem();
    helpMenu = new javax.swing.JMenu();
    aboutMenuItem = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    datasetPanel.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dataset", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("SansSerif", 0, 11))); // NOI18N
    datasetPanel.setFont(datasetPanel.getFont());

    jLabel1.setFont(jLabel1.getFont());
    jLabel1.setText("Station:");

    jLabel2.setFont(jLabel2.getFont());
    jLabel2.setText("Elevation:");

    stationText.setFont(stationText.getFont());
    stationText.setText(" << No Dataset Loaded >>");

    elevationText.setFont(elevationText.getFont());
    elevationText.setText(" ");

    jLabel9.setFont(jLabel9.getFont());
    jLabel9.setText("Latitude:");

    latitudeText.setFont(latitudeText.getFont());
    latitudeText.setText(" ");

    jLabel11.setFont(jLabel11.getFont());
    jLabel11.setText("Longitude:");

    longitudeText.setFont(longitudeText.getFont());
    longitudeText.setText(" ");

    datasetTable.setFont(datasetTable.getFont());
    datasetTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {"", null, null, null, null, null, null, null, null, null, null, null, null},
        {"", null, null, null, null, null, null, null, null, null, null, null, null}
      },
      new String [] {
        " ", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
      }
    ) {
      boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false, false, false, false, false, false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    datasetTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    datasetScrollPane.setViewportView(datasetTable);

    javax.swing.GroupLayout datasetPanelLayout = new javax.swing.GroupLayout(datasetPanel);
    datasetPanel.setLayout(datasetPanelLayout);
    datasetPanelLayout.setHorizontalGroup(
      datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(datasetPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(datasetScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
          .addGroup(datasetPanelLayout.createSequentialGroup()
            .addGroup(datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel2)
              .addComponent(jLabel1))
            .addGap(18, 18, 18)
            .addGroup(datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(stationText, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(elevationText, javax.swing.GroupLayout.PREFERRED_SIZE, 297, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jLabel9)
              .addComponent(jLabel11))
            .addGap(18, 18, 18)
            .addGroup(datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
              .addComponent(longitudeText, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
              .addComponent(latitudeText, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))))
        .addContainerGap())
    );
    datasetPanelLayout.setVerticalGroup(
      datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(datasetPanelLayout.createSequentialGroup()
        .addGroup(datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel1)
          .addComponent(stationText)
          .addComponent(jLabel9)
          .addComponent(latitudeText))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(longitudeText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(datasetPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(elevationText, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
        .addComponent(datasetScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 61, Short.MAX_VALUE)
        .addContainerGap())
    );

    modelResultsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Model Results"));

    jLabel3.setText("Annual Rainfall:");

    jLabel4.setText("Temperature Regime:");

    jLabel5.setText("Moisture Regime:");

    jLabel6.setText("Waterholding Capacity:");

    whcUnitsText.setText("mm");

    whcSpinner.setModel(new javax.swing.SpinnerNumberModel(Double.valueOf(200.0d), Double.valueOf(0.0d), null, Double.valueOf(0.1d)));
    whcSpinner.addChangeListener(new javax.swing.event.ChangeListener() {
      public void stateChanged(javax.swing.event.ChangeEvent evt) {
        whcSpinnerStateChanged(evt);
      }
    });

    mpeTable.setFont(mpeTable.getFont());
    mpeTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {"", null, null, null, null, null, null, null, null, null, null, null, null}
      },
      new String [] {
        " ", "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"
      }
    ) {
      boolean[] canEdit = new boolean [] {
        false, false, false, false, false, false, false, false, false, false, false, false, false
      };

      public boolean isCellEditable(int rowIndex, int columnIndex) {
        return canEdit [columnIndex];
      }
    });
    mpeTable.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
    datasetScrollPane1.setViewportView(mpeTable);

    moistureRegimeText.setText(" ");

    temperatureRegimeText.setText(" ");

    annualRainfallText.setText(" ");

    jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Moisture Calendar"));

    jTextArea1.setBackground(new java.awt.Color(0, 0, 0));
    jTextArea1.setColumns(30);
    jTextArea1.setFont(new java.awt.Font("Monospaced", 0, 14));
    jTextArea1.setForeground(new java.awt.Color(255, 255, 255));
    jTextArea1.setRows(1);
    jTextArea1.setText("1''''''''''''15'''''''''''''30");
    jTextArea1.setBorder(null);
    jScrollPane2.setViewportView(jTextArea1);

    jTextArea2.setBackground(new java.awt.Color(0, 0, 0));
    jTextArea2.setColumns(5);
    jTextArea2.setFont(new java.awt.Font("Monospaced", 0, 14));
    jTextArea2.setForeground(new java.awt.Color(255, 255, 255));
    jTextArea2.setRows(12);
    jTextArea2.setText(" JAN\n FEB\n MAR\n APR\n MAY\n JUN\n JUL\n AUG\n SEP\n OCT\n NOV\n DEC");
    jTextArea2.setBorder(null);
    jScrollPane3.setViewportView(jTextArea2);

    moistureCalendarText.setBackground(new java.awt.Color(0, 0, 0));
    moistureCalendarText.setColumns(30);
    moistureCalendarText.setEditable(false);
    moistureCalendarText.setFont(new java.awt.Font("Monospaced", 0, 14));
    moistureCalendarText.setForeground(new java.awt.Color(255, 255, 255));
    moistureCalendarText.setRows(12);
    moistureCalendarText.setBorder(null);
    jScrollPane1.setViewportView(moistureCalendarText);

    jLabel7.setFont(new java.awt.Font("SansSerif", 0, 11));
    jLabel7.setText("1 = Dry, 2 = Moist/Dry, 3 = Moist");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
              .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
              .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
              .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addContainerGap()))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
            .addComponent(jLabel7)
            .addContainerGap())))
    );
    jPanel1Layout.setVerticalGroup(
      jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel1Layout.createSequentialGroup()
        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(jScrollPane1)
          .addComponent(jScrollPane3))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Temperature Calendar"));

    jTextArea3.setBackground(new java.awt.Color(0, 0, 0));
    jTextArea3.setColumns(30);
    jTextArea3.setFont(new java.awt.Font("Monospaced", 0, 14));
    jTextArea3.setForeground(new java.awt.Color(255, 255, 255));
    jTextArea3.setRows(1);
    jTextArea3.setText("1''''''''''''15'''''''''''''30");
    jTextArea3.setBorder(null);
    jScrollPane4.setViewportView(jTextArea3);

    jTextArea4.setBackground(new java.awt.Color(0, 0, 0));
    jTextArea4.setColumns(5);
    jTextArea4.setFont(new java.awt.Font("Monospaced", 0, 14));
    jTextArea4.setForeground(new java.awt.Color(255, 255, 255));
    jTextArea4.setRows(12);
    jTextArea4.setText(" JAN\n FEB\n MAR\n APR\n MAY\n JUN\n JUL\n AUG\n SEP\n OCT\n NOV\n DEC");
    jTextArea4.setBorder(null);
    jScrollPane5.setViewportView(jTextArea4);

    temperatureCalendarText.setBackground(new java.awt.Color(0, 0, 0));
    temperatureCalendarText.setColumns(30);
    temperatureCalendarText.setEditable(false);
    temperatureCalendarText.setFont(new java.awt.Font("Monospaced", 0, 14));
    temperatureCalendarText.setForeground(new java.awt.Color(255, 255, 255));
    temperatureCalendarText.setRows(12);
    temperatureCalendarText.setBorder(null);
    jScrollPane6.setViewportView(temperatureCalendarText);

    jLabel8.setFont(new java.awt.Font("SansSerif", 0, 11));
    jLabel8.setText("- = Under 5C, 5 = 5C to 8C, 8 = Excess of 8C");

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(jPanel2Layout.createSequentialGroup()
            .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(jScrollPane6)
              .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(9, 9, 9))
          .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
            .addComponent(jLabel8)
            .addContainerGap())))
    );
    jPanel2Layout.setVerticalGroup(
      jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel2Layout.createSequentialGroup()
        .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(jScrollPane6)
          .addComponent(jScrollPane5))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jLabel8))
    );

    jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Extended Statistics"));

    statisticsText.setColumns(20);
    statisticsText.setEditable(false);
    statisticsText.setFont(new java.awt.Font("Monospaced", 0, 11)); // NOI18N
    statisticsText.setRows(5);
    statisticsText.setAutoscrolls(false);
    statisticsText.setOpaque(false);
    jScrollPane7.setViewportView(statisticsText);

    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
    jPanel3.setLayout(jPanel3Layout);
    jPanel3Layout.setHorizontalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel3Layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
        .addContainerGap())
    );
    jPanel3Layout.setVerticalGroup(
      jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(jPanel3Layout.createSequentialGroup()
        .addComponent(jScrollPane7, javax.swing.GroupLayout.DEFAULT_SIZE, 95, Short.MAX_VALUE)
        .addContainerGap())
    );

    exportCsvButton.setText("Export to FLX");
    exportCsvButton.setEnabled(false);
    exportCsvButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exportCsvButtonActionPerformed(evt);
      }
    });

    javax.swing.GroupLayout modelResultsPanelLayout = new javax.swing.GroupLayout(modelResultsPanel);
    modelResultsPanel.setLayout(modelResultsPanelLayout);
    modelResultsPanelLayout.setHorizontalGroup(
      modelResultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, modelResultsPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(modelResultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(datasetScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 655, Short.MAX_VALUE)
          .addGroup(modelResultsPanelLayout.createSequentialGroup()
            .addComponent(jLabel3)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(annualRainfallText, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel4)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(temperatureRegimeText, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(jLabel5)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(moistureRegimeText, javax.swing.GroupLayout.DEFAULT_SIZE, 132, Short.MAX_VALUE))
          .addGroup(modelResultsPanelLayout.createSequentialGroup()
            .addComponent(jLabel6)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(whcSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(whcUnitsText, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 349, Short.MAX_VALUE)
            .addComponent(exportCsvButton))
          .addGroup(modelResultsPanelLayout.createSequentialGroup()
            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 12, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addContainerGap())
    );
    modelResultsPanelLayout.setVerticalGroup(
      modelResultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(modelResultsPanelLayout.createSequentialGroup()
        .addGroup(modelResultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel3)
          .addComponent(jLabel4)
          .addComponent(jLabel5)
          .addComponent(moistureRegimeText)
          .addComponent(temperatureRegimeText)
          .addComponent(annualRainfallText))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(datasetScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(modelResultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(modelResultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(jLabel6)
          .addComponent(whcSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(whcUnitsText)
          .addComponent(exportCsvButton))
        .addContainerGap())
    );

    jMenuBar1.setFont(jMenuBar1.getFont());

    fileMenu.setText("File");
    fileMenu.setFont(fileMenu.getFont());

    openDatasetMenuItem.setFont(openDatasetMenuItem.getFont());
    openDatasetMenuItem.setText("Open Dataset...");
    openDatasetMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        openDatasetMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(openDatasetMenuItem);

    exitMenuItem.setFont(exitMenuItem.getFont());
    exitMenuItem.setText("Exit");
    exitMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exitMenuItemActionPerformed(evt);
      }
    });
    fileMenu.add(exitMenuItem);

    jMenuBar1.add(fileMenu);

    optionsMenu.setText("Options");
    optionsMenu.setFont(optionsMenu.getFont());

    toggleUnitsMenuItem.setFont(toggleUnitsMenuItem.getFont());
    toggleUnitsMenuItem.setText("Toggle English/Metric Units");
    toggleUnitsMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        toggleUnitsMenuItemActionPerformed(evt);
      }
    });
    optionsMenu.add(toggleUnitsMenuItem);

    jMenuBar1.add(optionsMenu);

    helpMenu.setText("Help");
    helpMenu.setFont(helpMenu.getFont());

    aboutMenuItem.setFont(aboutMenuItem.getFont());
    aboutMenuItem.setText("About");
    aboutMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        aboutMenuItemActionPerformed(evt);
      }
    });
    helpMenu.add(aboutMenuItem);

    jMenuBar1.add(helpMenu);

    setJMenuBar(jMenuBar1);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(modelResultsPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(datasetPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(datasetPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(modelResultsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );

    pack();
  }// </editor-fold>//GEN-END:initComponents

    private void exitMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exitMenuItemActionPerformed
      System.exit(0);
    }//GEN-LAST:event_exitMenuItemActionPerformed

    private void openDatasetMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_openDatasetMenuItemActionPerformed
      JFileChooser jfc = new JFileChooser(".");
      int returnCondition = jfc.showOpenDialog(this);
      if (returnCondition == JFileChooser.APPROVE_OPTION) {
        System.out.println("Opening: " + jfc.getSelectedFile().getAbsolutePath());
        try {
          nd = null;
          nd = new NewhallDataset(jfc.getSelectedFile().getAbsolutePath());
          loadDataset();
        } catch (Exception e) {
          JOptionPane.showMessageDialog(this, "File format was unacceptable.  Only Newhall CSVs are\n"
                  + "accepted.  See documentation for details on file formats.");
          unloadDataset();
        }

      }
      System.out.println("NewhallDataset loaded: " + (nd != null));
    }//GEN-LAST:event_openDatasetMenuItemActionPerformed

    private void toggleUnitsMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_toggleUnitsMenuItemActionPerformed
      this.inMetric = !this.inMetric;

      double originalWhcValue = 0.0;
      if (whcSpinner.getValue() instanceof Double) {
        originalWhcValue = (Double) whcSpinner.getValue();
      } else {
        originalWhcValue = (Integer) whcSpinner.getValue();
      }

      if (this.inMetric) {
        // Convert WHC from English to Metric.
        double whcInMm = originalWhcValue * 25.4;
        whcSpinner.setValue(whcInMm);
        whcUnitsText.setText("mm");
      } else {
        // Convert WHC from Metric to English.
        double whcInInches = originalWhcValue * 0.0393700787;
        whcSpinner.setValue(whcInInches);
        whcUnitsText.setText("in");
      }

      if (nd != null) {
        loadDataset();
      }

      System.out.println("inMetric: " + this.inMetric);
    }//GEN-LAST:event_toggleUnitsMenuItemActionPerformed

    private void whcSpinnerStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_whcSpinnerStateChanged
      if (nd != null) {
        loadDataset();
      }
    }//GEN-LAST:event_whcSpinnerStateChanged

    private void aboutMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_aboutMenuItemActionPerformed
      new AboutFrame().setVisible(true);
    }//GEN-LAST:event_aboutMenuItemActionPerformed

    private void exportCsvButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportCsvButtonActionPerformed
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

        CSVResultsExporter cve = new CSVResultsExporter(nr, jfc.getSelectedFile());
        cve.save();
      }
    }//GEN-LAST:event_exportCsvButtonActionPerformed

  public void loadDataset() {

    // Refresh the frame with the dataset's fields.

    ArrayList<Double> properTemp = new ArrayList<Double>(12);
    ArrayList<Double> properPrecip = new ArrayList<Double>(12);
    double properElevation = 0.0;

    if (this.inMetric && !nd.isMetric()) {
      // Convert English to metric.
      for (int i = 0; i < 12; i++) {
        double tempInC = (nd.getTemperature().get(i) - 32) * (5.0 / 9.0);
        properTemp.add(tempInC);
        double precipInMm = nd.getPrecipitation().get(i) * 25.4;
        properPrecip.add(precipInMm);
        properElevation = nd.getElevation() * 0.3048;
      }
    } else if (!this.inMetric && nd.isMetric()) {
      // Convert metric to English.
      for (int i = 0; i < 12; i++) {
        double tempInF = (nd.getTemperature().get(i) * (9.0 / 5.0)) + 32;
        properTemp.add(tempInF);
        double precipInInches = nd.getPrecipitation().get(i) / 25.4;
        properPrecip.add(precipInInches);
        properElevation = nd.getElevation() * 3.2808399;
      }
    } else {
      // Present format is correct.
      properTemp = nd.getTemperature();
      properPrecip = nd.getPrecipitation();
      properElevation = nd.getElevation();
    }

    TableModel datasetTableModel = this.datasetTable.getModel();
    if (this.inMetric) {
      datasetTableModel.setValueAt("Rainfall (mm)", 0, 0);
      datasetTableModel.setValueAt("Air Temp (C)", 1, 0);
      elevationText.setText(roundForDisplay(properElevation) + " meters");
    } else {
      datasetTableModel.setValueAt("Rainfall (in)", 0, 0);
      datasetTableModel.setValueAt("Air Temp (F)", 1, 0);
      elevationText.setText(roundForDisplay(properElevation) + " feet");
    }

    datasetTable.getColumnModel().getColumn(0).setPreferredWidth(150);
    for (int i = 0; i < 12; i++) {
      datasetTableModel.setValueAt(roundForDisplay(properPrecip.get(i)), 0, i + 1);
      datasetTableModel.setValueAt(roundForDisplay(properTemp.get(i)), 1, i + 1);
    }

    stationText.setText(nd.getName() + ", " + nd.getCountry() + " [" + nd.getStartYear() + "]");
    latitudeText.setText(roundDegreesForDisplay(nd.getLatitude()) + " degrees " + nd.getNsHemisphere());
    longitudeText.setText(roundDegreesForDisplay(nd.getLongitude()) + " degrees " + nd.getEwHemisphere());

    whcSpinner.setEnabled(true);
    exportCsvButton.setEnabled(true);


    // Dataset refreshed, now run the model, and refresh those fields too.
    runModel();
  }

  public void runModel() {

    // Deal with getting the WHC value from the spinner and converting it.

    double inputWhc = 0.0;
    if (whcSpinner.getValue() instanceof Integer) {
      inputWhc = (Integer) whcSpinner.getValue();
    } else {
      inputWhc = (Double) whcSpinner.getValue();
    }
    if (!this.inMetric) {
      // Convert inches to mm, which the dataset anticipates.
      System.out.println("WHC inches prior to conversion: " + inputWhc + " in");
      inputWhc *= 25.4;
      System.out.println("Giving model WHC of: " + inputWhc + " mm");
    }

    // Run the simulation, get the results.

    try {
      nr = null;
      nr = BASICSimulationModel.runSimulation(nd, inputWhc);
    } catch (Exception e) {
      System.out.println("Could not run simulation.");
      e.printStackTrace();
      unloadDataset();
      return;
    }

    // Update model results.

    if (nr != null) {

      TableModel mpeTableModel = this.mpeTable.getModel();
      mpeTable.getColumnModel().getColumn(0).setPreferredWidth(260);

      String properAnnualRainfall;

      if (!this.inMetric) {
        // Results are always in metric, convert to English.
        properAnnualRainfall = roundForDisplay(nr.getAnnualRainfall() * 0.0393700787) + " in";
        whcUnitsText.setText("in");
        mpeTableModel.setValueAt("Evapotranspiration (in)", 0, 0);
        for (int i = 0; i < 12; i++) {
          double mpeValueForDisplay = roundForDisplay(nr.getMeanPotentialEvapotranspiration().get(i) * 0.0393700787);
          mpeTableModel.setValueAt(mpeValueForDisplay, 0, i + 1);
        }
      } else {
        properAnnualRainfall = roundForDisplay(nr.getAnnualRainfall()) + " mm";
        whcUnitsText.setText("mm");
        mpeTableModel.setValueAt("Evapotranspiration (mm)", 0, 0);
        for (int i = 0; i < 12; i++) {
          double mpeValueForDisplay = roundForDisplay(nr.getMeanPotentialEvapotranspiration().get(i));
          mpeTableModel.setValueAt(mpeValueForDisplay, 0, i + 1);
        }
      }

      annualRainfallText.setText(properAnnualRainfall);
      temperatureRegimeText.setText(nr.getTemperatureRegime());
      moistureRegimeText.setText(nr.getMoistureRegime());
      moistureCalendarText.setText(nr.getFormattedMoistureCalendar());
      temperatureCalendarText.setText(nr.getFormattedTemperatureCalendar());
      statisticsText.setText(nr.getFormattedStatistics());

    }

  }

  public void unloadDataset() {
    stationText.setText(" << No Dataset Loaded >>");
    elevationText.setText("");
    latitudeText.setText("");
    longitudeText.setText("");

    TableModel mpeTableModel = this.mpeTable.getModel();
    TableModel datasetTableModel = this.datasetTable.getModel();

    for (int i = 0; i <= 12; i++) {
      for (int j = 0; j < 2; j++) {
        datasetTableModel.setValueAt("", j, i);
      }
    }

    for (int i = 0; i <= 12; i++) {
      mpeTableModel.setValueAt("", 0, i);
    }

    annualRainfallText.setText("");
    temperatureRegimeText.setText("");
    moistureRegimeText.setText("");

    temperatureCalendarText.setText("");
    moistureCalendarText.setText("");
    statisticsText.setText("");

    whcSpinner.setValue(200.0);
    whcSpinner.setEnabled(false);
    exportCsvButton.setEnabled(false);
    this.inMetric = true;
  }

  public double roundForDisplay(Double value) {
    return Double.valueOf(new DecimalFormat("##.##").format(value));
  }

  public double roundDegreesForDisplay(Double degrees) {
    return Double.valueOf(new DecimalFormat("##.####").format(degrees));
  }
  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenuItem aboutMenuItem;
  private javax.swing.JLabel annualRainfallText;
  private javax.swing.JPanel datasetPanel;
  private javax.swing.JScrollPane datasetScrollPane;
  private javax.swing.JScrollPane datasetScrollPane1;
  private javax.swing.JTable datasetTable;
  private javax.swing.JLabel elevationText;
  private javax.swing.JMenuItem exitMenuItem;
  private javax.swing.JButton exportCsvButton;
  private javax.swing.JMenu fileMenu;
  private javax.swing.JMenu helpMenu;
  private javax.swing.JLabel jLabel1;
  private javax.swing.JLabel jLabel11;
  private javax.swing.JLabel jLabel2;
  private javax.swing.JLabel jLabel3;
  private javax.swing.JLabel jLabel4;
  private javax.swing.JLabel jLabel5;
  private javax.swing.JLabel jLabel6;
  private javax.swing.JLabel jLabel7;
  private javax.swing.JLabel jLabel8;
  private javax.swing.JLabel jLabel9;
  private javax.swing.JMenuBar jMenuBar1;
  private javax.swing.JPanel jPanel1;
  private javax.swing.JPanel jPanel2;
  private javax.swing.JPanel jPanel3;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JScrollPane jScrollPane3;
  private javax.swing.JScrollPane jScrollPane4;
  private javax.swing.JScrollPane jScrollPane5;
  private javax.swing.JScrollPane jScrollPane6;
  private javax.swing.JScrollPane jScrollPane7;
  private javax.swing.JSeparator jSeparator1;
  private javax.swing.JTextArea jTextArea1;
  private javax.swing.JTextArea jTextArea2;
  private javax.swing.JTextArea jTextArea3;
  private javax.swing.JTextArea jTextArea4;
  private javax.swing.JLabel latitudeText;
  private javax.swing.JLabel longitudeText;
  private javax.swing.JPanel modelResultsPanel;
  private javax.swing.JTextArea moistureCalendarText;
  private javax.swing.JLabel moistureRegimeText;
  private javax.swing.JTable mpeTable;
  private javax.swing.JMenuItem openDatasetMenuItem;
  private javax.swing.JMenu optionsMenu;
  private javax.swing.JLabel stationText;
  private javax.swing.JTextArea statisticsText;
  private javax.swing.JTextArea temperatureCalendarText;
  private javax.swing.JLabel temperatureRegimeText;
  private javax.swing.JMenuItem toggleUnitsMenuItem;
  private javax.swing.JSpinner whcSpinner;
  private javax.swing.JLabel whcUnitsText;
  // End of variables declaration//GEN-END:variables
}
