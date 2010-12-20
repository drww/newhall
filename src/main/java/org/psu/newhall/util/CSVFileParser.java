package org.psu.newhall.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.psu.newhall.sim.BASICSimulationModelConstants;
import org.psu.newhall.sim.NewhallDataset;
import org.psu.newhall.sim.NewhallDatasetMetadata;

public class CSVFileParser {

  NewhallDataset dataset;

  public CSVFileParser(File inputFile) {

    ArrayList<Double> temperature;
    ArrayList<Double> precipitation;
    String name;
    String country;
    double latitude;
    char nsHemisphere;
    double longitude;
    char ewHemisphere;
    double elevation;
    int startYear;
    int endYear;
    boolean isMetric;

    CSVParser parser = null;

    try {
      parser = new CSVParser(inputFile.getAbsolutePath(), false);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(NewhallDataset.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(NewhallDataset.class.getName()).log(Level.SEVERE, null, ex);
    }

    ArrayList<String> firstRow = parser.getRecords().get(0);
    ArrayList<String> secondRow = parser.getRecords().get(1);

    precipitation = new ArrayList<Double>(12);
    temperature = new ArrayList<Double>(12);

    name = firstRow.get(0).replace("\"", "");
    country = firstRow.get(1).replace("\"", "");
    latitude = Double.parseDouble(firstRow.get(2))
            + Double.parseDouble(firstRow.get(3)) / 60;
    nsHemisphere = firstRow.get(4).toUpperCase().charAt(1);
    longitude = Double.parseDouble(firstRow.get(5))
            + Double.parseDouble(firstRow.get(6)) / 60;
    ewHemisphere = firstRow.get(7).toUpperCase().charAt(1);
    elevation = Double.parseDouble(firstRow.get(8));

    for (int i = 0; i <= 11; i++) {
      precipitation.add(Double.parseDouble(secondRow.get(i)));
    }

    for (int i = 12; i <= 23; i++) {
      temperature.add(Double.parseDouble(secondRow.get(i)));
    }

    startYear = Integer.parseInt(secondRow.get(24));
    endYear = Integer.parseInt(secondRow.get(25));

    isMetric = false;
    if (secondRow.get(26).charAt(1) == 'M') {
      isMetric = true;
    }

    this.dataset = new NewhallDataset(name, country, latitude,
            longitude, nsHemisphere, ewHemisphere, elevation,
            precipitation, temperature, startYear, endYear, isMetric);

    // Add mostly empty metadata object.
    NewhallDatasetMetadata ndm = new NewhallDatasetMetadata();
    ndm.setStationName(name);
    ndm.setStationCountry(country);
    ndm.setStationElevation(elevation);
    if(!isMetric) {
      ndm.setSoilAirOffset(BASICSimulationModelConstants.fc * (9.0/5.0));
    } else {
      ndm.setSoilAirOffset(BASICSimulationModelConstants.fc);
    }
    ndm.setAmplitude(BASICSimulationModelConstants.fcd);
    ndm.setMlraId(-1);
    ndm.setNotes(new ArrayList<String>());
    ndm.getNotes().add("Results generated from legacy Newhall input format.");

    this.dataset.setMetadata(ndm);

  }

  public NewhallDataset getDatset() {
    return dataset;
  }

}
