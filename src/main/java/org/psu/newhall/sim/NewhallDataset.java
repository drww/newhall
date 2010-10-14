package org.psu.newhall.sim;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.psu.newhall.util.CSVFileParser;

public class NewhallDataset {

  private String name;
  private String country;
  private double latitude;
  private double longitude;
  private char nsHemisphere;
  private char ewHemisphere;
  private double elevation;
  private ArrayList<Double> precipitation;
  private ArrayList<Double> temperature;
  private int startYear;
  private int endYear;
  private boolean isMetric;

  public NewhallDataset(String filepath) {

    CSVFileParser parser = null;

    try {
      parser = new CSVFileParser(filepath, false);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(NewhallDataset.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(NewhallDataset.class.getName()).log(Level.SEVERE, null, ex);
    }

    ArrayList<String> firstRow = parser.getRecords().get(0);
    ArrayList<String> secondRow = parser.getRecords().get(1);

    this.precipitation = new ArrayList<Double>(12);
    this.temperature = new ArrayList<Double>(12);
    
    this.name = firstRow.get(0).replace("\"", "");
    this.country = firstRow.get(1).replace("\"", "");
    this.latitude = Double.parseDouble(firstRow.get(2));
    this.latitude += Double.parseDouble(firstRow.get(3)) / 60;
    this.nsHemisphere = firstRow.get(4).toUpperCase().charAt(1);
    this.longitude = Double.parseDouble(firstRow.get(5));
    this.longitude += Double.parseDouble(firstRow.get(6)) / 60;
    this.ewHemisphere = firstRow.get(7).toUpperCase().charAt(1);
    this.elevation = Double.parseDouble(firstRow.get(8));

    for (int i = 0; i <= 11; i++) {
      precipitation.add(Double.parseDouble(secondRow.get(i)));
    }

    for (int i = 12; i <= 23; i++) {
      temperature.add(Double.parseDouble(secondRow.get(i)));
    }

    this.startYear = Integer.parseInt(secondRow.get(24));
    this.endYear = Integer.parseInt(secondRow.get(25));

    this.isMetric = false;
    if (secondRow.get(26).charAt(1) == 'M') {
      this.isMetric = true;
    }


  }

  public String getCountry() {
    return country;
  }

  public double getElevation() {
    return elevation;
  }

  public int getEndYear() {
    return endYear;
  }

  public char getEwHemisphere() {
    return ewHemisphere;
  }

  public boolean isMetric() {
    return isMetric;
  }

  public double getLatitude() {
    return latitude;
  }

  public int getLatitudeDegrees() {
    return (int)latitude;
  }

  public double getLatitudeMinutes() {
    return latitude%1 * 60;
  }

  public double getLongitude() {
    return longitude;
  }

  public int getLongitudeDegrees() {
    return (int)longitude;
  }

  public double getLongitudeMinutes() {
    return longitude%1 * 60;
  }

  public String getName() {
    return name;
  }

  public char getNsHemisphere() {
    return nsHemisphere;
  }

  public ArrayList<Double> getPrecipitation() {
    return precipitation;
  }

  public int getStartYear() {
    return startYear;
  }

  public ArrayList<Double> getTemperature() {
    return temperature;
  }

  @Override
  public String toString() {
    String result = this.getClass().toString();
    result += "\n  Name: " + name;
    result += "\n  Country: " + country;
    result += "\n  Latitude: " + latitude + " " + nsHemisphere;
    result += "\n    " + getLatitudeDegrees() + " degrees, " + getLatitudeMinutes() + " minutes";
    result += "\n  Longitude: " + longitude + " " + ewHemisphere;
    result += "\n    " + getLongitudeDegrees() + " degrees, " + getLongitudeMinutes() + " minutes";
    result += "\n  Elevation: " + elevation;

    if (isMetric) {
      result += " meters";
    } else {
      result += " feet";
    }

    result += "\n  Precipitation(";
    if (isMetric) {
      result += "mm): ";
    } else {
      result += "in): ";
    }
    for (Double precip : precipitation) {
      result += precip + " ";
    }

    result += "\n  Temperature(";
    if (isMetric) {
      result += "C): ";
    } else {
      result += "F): ";
    }
    for (Double temp : temperature) {
      result += temp + " ";
    }

    result += "\n  Starting Year: " + startYear;
    result += "\n  Ending Year: " + endYear;

    return result;
  }
}
