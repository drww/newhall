package org.psu.newhall.ui;

import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import org.psu.newhall.sim.BASICSimulationModel;
import org.psu.newhall.sim.NewhallDataset;
import org.psu.newhall.sim.NewhallDatasetMetadata;
import org.psu.newhall.sim.NewhallResults;
import org.psu.newhall.util.CSVFileParser;
import org.psu.newhall.util.XMLFileParser;
import org.psu.newhall.util.XMLResultsExporter;

public class NewerNewhallFrame extends javax.swing.JFrame {

  NewhallDataset dataset;
  NewhallResults results;
  boolean isInMetric;

  public NewerNewhallFrame() {
    initComponents();
    isInMetric = true;
  }

  @SuppressWarnings("unchecked")
  // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
  private void initComponents() {

    tabPane = new javax.swing.JTabbedPane();
    metadataPanel = new javax.swing.JPanel();
    stationNameLabel = new javax.swing.JLabel();
    stationName = new javax.swing.JTextField();
    countryLabel = new javax.swing.JLabel();
    country = new javax.swing.JTextField();
    latitudeLabel = new javax.swing.JLabel();
    latitude = new javax.swing.JTextField();
    latitudeNSLabel = new javax.swing.JLabel();
    longitudeLabel = new javax.swing.JLabel();
    longitude = new javax.swing.JTextField();
    longitudeEWLabel = new javax.swing.JLabel();
    elevationLabel = new javax.swing.JLabel();
    elevation = new javax.swing.JTextField();
    elevationUnitsLabel = new javax.swing.JLabel();
    startYearLabel = new javax.swing.JLabel();
    endYearLabel = new javax.swing.JLabel();
    startYear = new javax.swing.JTextField();
    endYear = new javax.swing.JTextField();
    unitSystemLabel = new javax.swing.JLabel();
    unitSystem = new javax.swing.JTextField();
    stateProvLabel = new javax.swing.JLabel();
    stateProv = new javax.swing.JTextField();
    mlraNameLabel = new javax.swing.JLabel();
    mlraName = new javax.swing.JTextField();
    mlraIDLabel = new javax.swing.JLabel();
    mlraID = new javax.swing.JTextField();
    contributorPanel = new javax.swing.JPanel();
    firstNameLabel = new javax.swing.JLabel();
    lastNameLabel = new javax.swing.JLabel();
    titleLabel = new javax.swing.JLabel();
    organizationLabel = new javax.swing.JLabel();
    addressLabel = new javax.swing.JLabel();
    cityLabel = new javax.swing.JLabel();
    stateProvContribLabel = new javax.swing.JLabel();
    postalLabel = new javax.swing.JLabel();
    countryContribLabel = new javax.swing.JLabel();
    firstName = new javax.swing.JTextField();
    lastName = new javax.swing.JTextField();
    title = new javax.swing.JTextField();
    organization = new javax.swing.JTextField();
    address = new javax.swing.JTextField();
    city = new javax.swing.JTextField();
    stateProvContrib = new javax.swing.JTextField();
    postal = new javax.swing.JTextField();
    countryContrib = new javax.swing.JTextField();
    emailLabel = new javax.swing.JLabel();
    phoneLabel = new javax.swing.JLabel();
    email = new javax.swing.JTextField();
    phone = new javax.swing.JTextField();
    notesPanel = new javax.swing.JPanel();
    notesScrollPane = new javax.swing.JScrollPane();
    notes = new javax.swing.JTextArea();
    lastRunMetadata = new javax.swing.JPanel();
    lastRunDateLabel = new javax.swing.JLabel();
    lastRunDate = new javax.swing.JLabel();
    lastRunVersionLabel = new javax.swing.JLabel();
    lastRunVersion = new javax.swing.JLabel();
    lastRunUnitsysLabel = new javax.swing.JLabel();
    lastRunUnitsys = new javax.swing.JLabel();
    inputPanel = new javax.swing.JPanel();
    precipPanel = new javax.swing.JPanel();
    precipScrollPane = new javax.swing.JScrollPane();
    precipTable = new javax.swing.JTable();
    tempPanel = new javax.swing.JPanel();
    tempScrollPane = new javax.swing.JScrollPane();
    tempTable = new javax.swing.JTable();
    resultsPanel = new javax.swing.JPanel();
    petPanel = new javax.swing.JPanel();
    jScrollPane1 = new javax.swing.JScrollPane();
    petTable = new javax.swing.JTable();
    annualRainfallLabel = new javax.swing.JLabel();
    tempRegimeLabel = new javax.swing.JLabel();
    annualRainfall = new javax.swing.JLabel();
    tempRegime = new javax.swing.JLabel();
    moistRegimeLabel = new javax.swing.JLabel();
    moistRegime = new javax.swing.JLabel();
    extendedStatisticsPanel = new javax.swing.JPanel();
    jScrollPane2 = new javax.swing.JScrollPane();
    extendedStatistics = new javax.swing.JTextArea();
    whcResultLabel = new javax.swing.JLabel();
    whcResult = new javax.swing.JLabel();
    whcResultUnits = new javax.swing.JLabel();
    exportXmlButton = new javax.swing.JButton();
    whcSpinnerLabel = new javax.swing.JLabel();
    whcSpinner = new javax.swing.JSpinner();
    whcUnitsLabel = new javax.swing.JLabel();
    exportFlxButton = new javax.swing.JButton();
    menuBar = new javax.swing.JMenuBar();
    open = new javax.swing.JMenu();
    OpenFileMenuItem = new javax.swing.JMenuItem();

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
    setTitle("Newhall - No Dataset Loaded");

    stationNameLabel.setText("Station Name:");

    countryLabel.setText("Country:");

    latitudeLabel.setText("Latitude:");

    latitudeNSLabel.setText("N");

    longitudeLabel.setText("Longitude:");

    longitudeEWLabel.setText("E");

    elevationLabel.setText("Elevation:");

    elevationUnitsLabel.setText("m");

    startYearLabel.setText("Starting Year:");

    endYearLabel.setText("End Year:");

    unitSystemLabel.setText("Unit System:");

    stateProvLabel.setText("State/Prov.:");

    mlraNameLabel.setText("MLRA Name:");

    mlraIDLabel.setText("MLRA ID:");

    contributorPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Contributor"));

    firstNameLabel.setText("First Name:");

    lastNameLabel.setText("Last Name:");

    titleLabel.setText("Title:");

    organizationLabel.setText("Organization:");

    addressLabel.setText("Address:");

    cityLabel.setText("City:");

    stateProvContribLabel.setText("State/Prov.:");

    postalLabel.setText("Postal:");

    countryContribLabel.setText("Country:");

    address.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        addressActionPerformed(evt);
      }
    });

    emailLabel.setText("Email:");

    phoneLabel.setText("Phone:");

    javax.swing.GroupLayout contributorPanelLayout = new javax.swing.GroupLayout(contributorPanel);
    contributorPanel.setLayout(contributorPanelLayout);
    contributorPanelLayout.setHorizontalGroup(
      contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(contributorPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(lastNameLabel)
          .addComponent(firstNameLabel)
          .addComponent(titleLabel)
          .addComponent(organizationLabel)
          .addComponent(addressLabel)
          .addComponent(cityLabel))
        .addGap(24, 24, 24)
        .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
          .addComponent(city, javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(address, javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(organization, javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(title)
          .addComponent(lastName)
          .addComponent(firstName, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE))
        .addGap(18, 18, 18)
        .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(stateProvContribLabel)
          .addComponent(postalLabel)
          .addComponent(countryContribLabel)
          .addComponent(emailLabel)
          .addComponent(phoneLabel))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addComponent(email, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
          .addComponent(postal, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
          .addComponent(countryContrib, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
          .addComponent(stateProvContrib, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE)
          .addComponent(phone, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 168, Short.MAX_VALUE))
        .addContainerGap())
    );
    contributorPanelLayout.setVerticalGroup(
      contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(contributorPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(contributorPanelLayout.createSequentialGroup()
            .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(firstName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(firstNameLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(lastName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(lastNameLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(titleLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(organization, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(organizationLabel)
              .addComponent(emailLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(address, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(addressLabel)
              .addComponent(phoneLabel)))
          .addGroup(contributorPanelLayout.createSequentialGroup()
            .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(stateProvContribLabel)
              .addComponent(stateProvContrib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(postalLabel)
              .addComponent(postal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(countryContribLabel)
              .addComponent(countryContrib, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(phone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(contributorPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(city, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(cityLabel))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout metadataPanelLayout = new javax.swing.GroupLayout(metadataPanel);
    metadataPanel.setLayout(metadataPanelLayout);
    metadataPanelLayout.setHorizontalGroup(
      metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, metadataPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(contributorPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addGroup(metadataPanelLayout.createSequentialGroup()
            .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(elevationLabel)
              .addGroup(metadataPanelLayout.createSequentialGroup()
                .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(countryLabel)
                  .addComponent(longitudeLabel)
                  .addComponent(latitudeLabel))
                .addGap(38, 38, 38))
              .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, metadataPanelLayout.createSequentialGroup()
                .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(stationNameLabel)
                  .addComponent(stateProvLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(latitude, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                  .addComponent(longitude, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                  .addComponent(elevation, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                  .addComponent(stateProv, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)
                  .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(country, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(stationName, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 151, Short.MAX_VALUE)))))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addComponent(longitudeEWLabel)
              .addComponent(elevationUnitsLabel)
              .addComponent(latitudeNSLabel))
            .addGap(24, 24, 24)
            .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
              .addGroup(metadataPanelLayout.createSequentialGroup()
                .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(mlraNameLabel)
                  .addComponent(mlraIDLabel))
                .addGap(18, 18, 18)
                .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                  .addComponent(mlraName)
                  .addComponent(mlraID, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)))
              .addGroup(metadataPanelLayout.createSequentialGroup()
                .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(startYearLabel)
                  .addComponent(endYearLabel)
                  .addComponent(unitSystemLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                  .addComponent(endYear, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                  .addComponent(startYear, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                  .addComponent(unitSystem, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE))))))
        .addContainerGap())
    );
    metadataPanelLayout.setVerticalGroup(
      metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(metadataPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(stationNameLabel)
          .addComponent(stationName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(mlraNameLabel)
          .addComponent(mlraName, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(countryLabel)
          .addComponent(country, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(mlraIDLabel)
          .addComponent(mlraID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addGroup(metadataPanelLayout.createSequentialGroup()
            .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(stateProvLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(stateProv, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(latitudeLabel)
              .addComponent(latitude, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(latitudeNSLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(longitudeEWLabel)
              .addComponent(longitudeLabel)
              .addComponent(longitude)))
          .addGroup(metadataPanelLayout.createSequentialGroup()
            .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(startYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(startYearLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(endYear, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(endYearLabel))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(unitSystem, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
              .addComponent(unitSystemLabel))))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(metadataPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(elevationUnitsLabel)
          .addComponent(elevationLabel)
          .addComponent(elevation, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(contributorPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(17, 17, 17))
    );

    tabPane.addTab("Dataset Metadata", metadataPanel);

    notes.setColumns(20);
    notes.setRows(5);
    notesScrollPane.setViewportView(notes);

    lastRunMetadata.setBorder(javax.swing.BorderFactory.createTitledBorder("Last Run Details"));

    lastRunDateLabel.setText("Date:");

    lastRunDate.setText(" ");

    lastRunVersionLabel.setText("Model Version:");

    lastRunVersion.setText(" ");

    lastRunUnitsysLabel.setText("Unit System:");

    lastRunUnitsys.setText(" ");

    javax.swing.GroupLayout lastRunMetadataLayout = new javax.swing.GroupLayout(lastRunMetadata);
    lastRunMetadata.setLayout(lastRunMetadataLayout);
    lastRunMetadataLayout.setHorizontalGroup(
      lastRunMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(lastRunMetadataLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(lastRunDateLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(lastRunDate, javax.swing.GroupLayout.PREFERRED_SIZE, 61, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 60, Short.MAX_VALUE)
        .addComponent(lastRunVersionLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(lastRunVersion, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(lastRunUnitsysLabel)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(lastRunUnitsys, javax.swing.GroupLayout.PREFERRED_SIZE, 69, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addGap(24, 24, 24))
    );
    lastRunMetadataLayout.setVerticalGroup(
      lastRunMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(lastRunMetadataLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(lastRunMetadataLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(lastRunDateLabel)
          .addComponent(lastRunDate)
          .addComponent(lastRunVersionLabel)
          .addComponent(lastRunVersion)
          .addComponent(lastRunUnitsysLabel)
          .addComponent(lastRunUnitsys))
        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
    );

    javax.swing.GroupLayout notesPanelLayout = new javax.swing.GroupLayout(notesPanel);
    notesPanel.setLayout(notesPanelLayout);
    notesPanelLayout.setHorizontalGroup(
      notesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, notesPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(notesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(notesScrollPane, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 548, Short.MAX_VALUE)
          .addComponent(lastRunMetadata, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );
    notesPanelLayout.setVerticalGroup(
      notesPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, notesPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(notesScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 349, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(lastRunMetadata, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addContainerGap())
    );

    tabPane.addTab("Notes", notesPanel);

    precipPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Precipitation"));

    precipTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null}
      },
      new String [] {
        " ", " "
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.Double.class
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }
    });
    precipScrollPane.setViewportView(precipTable);

    javax.swing.GroupLayout precipPanelLayout = new javax.swing.GroupLayout(precipPanel);
    precipPanel.setLayout(precipPanelLayout);
    precipPanelLayout.setHorizontalGroup(
      precipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(precipPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(precipScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE)
        .addContainerGap())
    );
    precipPanelLayout.setVerticalGroup(
      precipPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(precipPanelLayout.createSequentialGroup()
        .addComponent(precipScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
        .addContainerGap())
    );

    tempPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Temperature"));

    tempTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null}
      },
      new String [] {
        " ", " "
      }
    ) {
      Class[] types = new Class [] {
        java.lang.String.class, java.lang.Double.class
      };

      public Class getColumnClass(int columnIndex) {
        return types [columnIndex];
      }
    });
    tempScrollPane.setViewportView(tempTable);

    javax.swing.GroupLayout tempPanelLayout = new javax.swing.GroupLayout(tempPanel);
    tempPanel.setLayout(tempPanelLayout);
    tempPanelLayout.setHorizontalGroup(
      tempPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(tempPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(tempScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 238, Short.MAX_VALUE)
        .addContainerGap())
    );
    tempPanelLayout.setVerticalGroup(
      tempPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(tempPanelLayout.createSequentialGroup()
        .addComponent(tempScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
        .addContainerGap())
    );

    javax.swing.GroupLayout inputPanelLayout = new javax.swing.GroupLayout(inputPanel);
    inputPanel.setLayout(inputPanelLayout);
    inputPanelLayout.setHorizontalGroup(
      inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(inputPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(precipPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addComponent(tempPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    inputPanelLayout.setVerticalGroup(
      inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, inputPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(inputPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
          .addComponent(tempPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
          .addComponent(precipPanel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        .addContainerGap())
    );

    tabPane.addTab("Input Datum", inputPanel);

    petPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Potential Evapotranspiration"));

    petTable.setModel(new javax.swing.table.DefaultTableModel(
      new Object [][] {
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null},
        {null, null}
      },
      new String [] {
        " ", " "
      }
    ));
    jScrollPane1.setViewportView(petTable);

    javax.swing.GroupLayout petPanelLayout = new javax.swing.GroupLayout(petPanel);
    petPanel.setLayout(petPanelLayout);
    petPanelLayout.setHorizontalGroup(
      petPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(petPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 179, Short.MAX_VALUE)
        .addContainerGap())
    );
    petPanelLayout.setVerticalGroup(
      petPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(petPanelLayout.createSequentialGroup()
        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 248, Short.MAX_VALUE)
        .addContainerGap())
    );

    annualRainfallLabel.setText("Annual Rainfall:");

    tempRegimeLabel.setText("Temperature Regime:");

    annualRainfall.setText(" ");

    tempRegime.setText(" ");

    moistRegimeLabel.setText("Moisture Regime:");

    moistRegime.setText(" ");

    extendedStatisticsPanel.setBorder(javax.swing.BorderFactory.createTitledBorder("Extended Statistics"));

    extendedStatistics.setColumns(20);
    extendedStatistics.setRows(5);
    jScrollPane2.setViewportView(extendedStatistics);

    javax.swing.GroupLayout extendedStatisticsPanelLayout = new javax.swing.GroupLayout(extendedStatisticsPanel);
    extendedStatisticsPanel.setLayout(extendedStatisticsPanelLayout);
    extendedStatisticsPanelLayout.setHorizontalGroup(
      extendedStatisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, extendedStatisticsPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 291, Short.MAX_VALUE)
        .addContainerGap())
    );
    extendedStatisticsPanelLayout.setVerticalGroup(
      extendedStatisticsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(extendedStatisticsPanelLayout.createSequentialGroup()
        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 384, Short.MAX_VALUE)
        .addContainerGap())
    );

    whcResultLabel.setText("Waterholding Capacity:");

    whcResult.setText(" ");

    whcResultUnits.setText(" ");

    javax.swing.GroupLayout resultsPanelLayout = new javax.swing.GroupLayout(resultsPanel);
    resultsPanel.setLayout(resultsPanelLayout);
    resultsPanelLayout.setHorizontalGroup(
      resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(resultsPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(resultsPanelLayout.createSequentialGroup()
              .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(petPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                  .addComponent(moistRegime, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                  .addGroup(javax.swing.GroupLayout.Alignment.LEADING, resultsPanelLayout.createSequentialGroup()
                    .addComponent(tempRegimeLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(tempRegime, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                  .addGroup(javax.swing.GroupLayout.Alignment.LEADING, resultsPanelLayout.createSequentialGroup()
                    .addComponent(annualRainfallLabel)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(annualRainfall, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE))))
              .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
            .addComponent(moistRegimeLabel, javax.swing.GroupLayout.Alignment.LEADING))
          .addGroup(resultsPanelLayout.createSequentialGroup()
            .addComponent(whcResultLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(whcResult, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(whcResultUnits, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
        .addGap(6, 6, 6)
        .addComponent(extendedStatisticsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addContainerGap())
    );
    resultsPanelLayout.setVerticalGroup(
      resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(resultsPanelLayout.createSequentialGroup()
        .addContainerGap()
        .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(resultsPanelLayout.createSequentialGroup()
            .addComponent(extendedStatisticsPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addContainerGap())
          .addGroup(resultsPanelLayout.createSequentialGroup()
            .addComponent(petPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(annualRainfallLabel)
              .addComponent(annualRainfall))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(tempRegimeLabel)
              .addComponent(tempRegime))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(moistRegimeLabel)
              .addComponent(moistRegime))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(resultsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
              .addComponent(whcResultLabel)
              .addComponent(whcResult)
              .addComponent(whcResultUnits, javax.swing.GroupLayout.DEFAULT_SIZE, 18, Short.MAX_VALUE))
            .addGap(58, 58, 58))))
    );

    tabPane.addTab("Model Results", resultsPanel);

    exportXmlButton.setText("Export to XML");
    exportXmlButton.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        exportXmlButtonActionPerformed(evt);
      }
    });

    whcSpinnerLabel.setText("Water Holding Capacity:");

    whcUnitsLabel.setText("mm");

    exportFlxButton.setText("Export to FLX");

    open.setText("File");

    OpenFileMenuItem.setText("Open ...");
    OpenFileMenuItem.addActionListener(new java.awt.event.ActionListener() {
      public void actionPerformed(java.awt.event.ActionEvent evt) {
        OpenFileMenuItemActionPerformed(evt);
      }
    });
    open.add(OpenFileMenuItem);

    menuBar.add(open);

    setJMenuBar(menuBar);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(layout.createSequentialGroup()
        .addContainerGap()
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
          .addGroup(layout.createSequentialGroup()
            .addComponent(whcSpinnerLabel)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(whcSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(whcUnitsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
            .addComponent(exportFlxButton)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
            .addComponent(exportXmlButton))
          .addComponent(tabPane, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
        .addContainerGap())
    );
    layout.setVerticalGroup(
      layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
      .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
        .addContainerGap()
        .addComponent(tabPane, javax.swing.GroupLayout.DEFAULT_SIZE, 482, Short.MAX_VALUE)
        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
          .addComponent(exportXmlButton)
          .addComponent(whcSpinnerLabel)
          .addComponent(whcSpinner, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
          .addComponent(whcUnitsLabel)
          .addComponent(exportFlxButton))
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

  private void refreshFields() {
    resetFields();
    if(dataset == null) {
      // Don't bother refreshing fields, no data for them.
      return;
    }

    stationName.setText(dataset.getName());
    country.setText(dataset.getCountry());
    latitude.setText(Double.toString(dataset.getLatitude()));
    latitudeNSLabel.setText(dataset.getNsHemisphere() + "");
    longitude.setText(Double.toString(dataset.getLongitude()));
    longitudeEWLabel.setText(dataset.getEwHemisphere() + "");

    if(isInMetric && !dataset.isMetric()) {
      // Convert English to Metric.
      Double elevAsMetric = dataset.getElevation() * 0.3048;
      elevation.setText(elevAsMetric.toString());
      elevationUnitsLabel.setText("m");
    } else if(!isInMetric && dataset.isMetric()) {
      // Convert Metric to English.
      Double elevAsEnglish = dataset.getElevation() / 0.3048;
      elevation.setText(elevAsEnglish.toString());
      elevationUnitsLabel.setText("ft");
    }

    startYear.setText(Integer.toString(dataset.getStartYear()));
    endYear.setText(Integer.toString(dataset.getEndYear()));
    if(dataset.isMetric()) {
      unitSystem.setText("Metric");
    } else {
      unitSystem.setText("English");
    }

    if (dataset.getMetadata() != null) {
      NewhallDatasetMetadata metadata = dataset.getMetadata();
      stateProv.setText(metadata.getStationStateProvidence());
      mlraName.setText(metadata.getMlraName());
      mlraID.setText(Integer.toString(metadata.getMlraId()));
      firstName.setText(metadata.getContribFirstName());
      lastName.setText(metadata.getContribLastName());
      title.setText(metadata.getContribTitle());
      organization.setText(metadata.getContribOrg());
      address.setText(metadata.getContribAddress());
      city.setText(metadata.getContribCity());
      stateProvContrib.setText(metadata.getContribStateProvidence());
      postal.setText(metadata.getContribPostal());
      countryContrib.setText(metadata.getContribCountry());
      email.setText(metadata.getContribEmail());
      phone.setText(metadata.getContribPhone());
      lastRunDate.setText(metadata.getRunDate());
      lastRunVersion.setText(metadata.getModelVersion());
      lastRunUnitsys.setText(metadata.getUnitSystem());

      String formattedNotes = "";
      for(String str : metadata.getNotes()) {
        formattedNotes += str + "\n";
      }
      notes.setText(formattedNotes);
    }
    
    whcSpinner.setValue(200.0);
    whcUnitsLabel.setText("mm");

    /** RESUME HERE **/

    resetTable(precipTable);
    resetTable(tempTable);
    resetTable(petTable);
    annualRainfall.setText("");
    tempRegime.setText("");
    moistRegime.setText("");
    whcResult.setText("");
    whcResultUnits.setText("");
    extendedStatistics.setText("");
  }

  private void resetFields() {
    this.isInMetric = true;
    stationName.setText("");
    country.setText("");
    stateProv.setText("");
    latitude.setText("");
    latitudeNSLabel.setText("");
    longitude.setText("");
    longitude.setText("");
    elevation.setText("");
    elevationUnitsLabel.setText("");
    mlraName.setText("");
    mlraID.setText("");
    startYear.setText("");
    endYear.setText("");
    unitSystem.setText("");
    firstName.setText("");
    lastName.setText("");
    title.setText("");
    organization.setText("");
    address.setText("");
    city.setText("");
    stateProvContrib.setText("");
    postal.setText("");
    countryContrib.setText("");
    email.setText("");
    phone.setText("");
    whcSpinner.setValue(200.0);
    whcUnitsLabel.setText("mm");
    notes.setText("");
    lastRunDate.setText("");
    lastRunVersion.setText("");
    lastRunUnitsys.setText("");
    resetTable(precipTable);
    resetTable(tempTable);
    resetTable(petTable);
    annualRainfall.setText("");
    tempRegime.setText("");
    moistRegime.setText("");
    whcResult.setText("");
    whcResultUnits.setText("");
    extendedStatistics.setText("");
  }

  private void resetTable(JTable table) {
    TableModel model = table.getModel();
    for (int i = 0; i < model.getRowCount(); i++) {
      for (int j = 0; j < model.getColumnCount(); j++) {
        model.setValueAt("", i, j);
      }
    }
  }

  private void exportXmlButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_exportXmlButtonActionPerformed

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
        exporter.export(results, dataset, isInMetric);
      } catch (IOException ex) {
        Logger.getLogger(NewerNewhallFrame.class.getName()).log(Level.SEVERE, null, ex);
      }
    }

  }//GEN-LAST:event_exportXmlButtonActionPerformed

  private void addressActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addressActionPerformed
    // TODO add your handling code here:
  }//GEN-LAST:event_addressActionPerformed

  // Variables declaration - do not modify//GEN-BEGIN:variables
  private javax.swing.JMenuItem OpenFileMenuItem;
  private javax.swing.JTextField address;
  private javax.swing.JLabel addressLabel;
  private javax.swing.JLabel annualRainfall;
  private javax.swing.JLabel annualRainfallLabel;
  private javax.swing.JTextField city;
  private javax.swing.JLabel cityLabel;
  private javax.swing.JPanel contributorPanel;
  private javax.swing.JTextField country;
  private javax.swing.JTextField countryContrib;
  private javax.swing.JLabel countryContribLabel;
  private javax.swing.JLabel countryLabel;
  private javax.swing.JTextField elevation;
  private javax.swing.JLabel elevationLabel;
  private javax.swing.JLabel elevationUnitsLabel;
  private javax.swing.JTextField email;
  private javax.swing.JLabel emailLabel;
  private javax.swing.JTextField endYear;
  private javax.swing.JLabel endYearLabel;
  private javax.swing.JButton exportFlxButton;
  private javax.swing.JButton exportXmlButton;
  private javax.swing.JTextArea extendedStatistics;
  private javax.swing.JPanel extendedStatisticsPanel;
  private javax.swing.JTextField firstName;
  private javax.swing.JLabel firstNameLabel;
  private javax.swing.JPanel inputPanel;
  private javax.swing.JScrollPane jScrollPane1;
  private javax.swing.JScrollPane jScrollPane2;
  private javax.swing.JTextField lastName;
  private javax.swing.JLabel lastNameLabel;
  private javax.swing.JLabel lastRunDate;
  private javax.swing.JLabel lastRunDateLabel;
  private javax.swing.JPanel lastRunMetadata;
  private javax.swing.JLabel lastRunUnitsys;
  private javax.swing.JLabel lastRunUnitsysLabel;
  private javax.swing.JLabel lastRunVersion;
  private javax.swing.JLabel lastRunVersionLabel;
  private javax.swing.JTextField latitude;
  private javax.swing.JLabel latitudeLabel;
  private javax.swing.JLabel latitudeNSLabel;
  private javax.swing.JTextField longitude;
  private javax.swing.JLabel longitudeEWLabel;
  private javax.swing.JLabel longitudeLabel;
  private javax.swing.JMenuBar menuBar;
  private javax.swing.JPanel metadataPanel;
  private javax.swing.JTextField mlraID;
  private javax.swing.JLabel mlraIDLabel;
  private javax.swing.JTextField mlraName;
  private javax.swing.JLabel mlraNameLabel;
  private javax.swing.JLabel moistRegime;
  private javax.swing.JLabel moistRegimeLabel;
  private javax.swing.JTextArea notes;
  private javax.swing.JPanel notesPanel;
  private javax.swing.JScrollPane notesScrollPane;
  private javax.swing.JMenu open;
  private javax.swing.JTextField organization;
  private javax.swing.JLabel organizationLabel;
  private javax.swing.JPanel petPanel;
  private javax.swing.JTable petTable;
  private javax.swing.JTextField phone;
  private javax.swing.JLabel phoneLabel;
  private javax.swing.JTextField postal;
  private javax.swing.JLabel postalLabel;
  private javax.swing.JPanel precipPanel;
  private javax.swing.JScrollPane precipScrollPane;
  private javax.swing.JTable precipTable;
  private javax.swing.JPanel resultsPanel;
  private javax.swing.JTextField startYear;
  private javax.swing.JLabel startYearLabel;
  private javax.swing.JTextField stateProv;
  private javax.swing.JTextField stateProvContrib;
  private javax.swing.JLabel stateProvContribLabel;
  private javax.swing.JLabel stateProvLabel;
  private javax.swing.JTextField stationName;
  private javax.swing.JLabel stationNameLabel;
  private javax.swing.JTabbedPane tabPane;
  private javax.swing.JPanel tempPanel;
  private javax.swing.JLabel tempRegime;
  private javax.swing.JLabel tempRegimeLabel;
  private javax.swing.JScrollPane tempScrollPane;
  private javax.swing.JTable tempTable;
  private javax.swing.JTextField title;
  private javax.swing.JLabel titleLabel;
  private javax.swing.JTextField unitSystem;
  private javax.swing.JLabel unitSystemLabel;
  private javax.swing.JLabel whcResult;
  private javax.swing.JLabel whcResultLabel;
  private javax.swing.JLabel whcResultUnits;
  private javax.swing.JSpinner whcSpinner;
  private javax.swing.JLabel whcSpinnerLabel;
  private javax.swing.JLabel whcUnitsLabel;
  // End of variables declaration//GEN-END:variables
}
