package org.psu.newhall.sim;

import java.util.List;

public class NewhallDataset {

  private String name;
  private String country;
  private double latitude;
  private double longitude;
  private char nsHemisphere;
  private char ewHemisphere;
  private double elevation;
  private List<Double> precipitation;
  private List<Double> temperature;
  private int startYear;
  private int endYear;
  private boolean isMetric;
  private NewhallDatasetMetadata metadata;

  public NewhallDataset(String name, String country, double latitude, double longitude, char nsHemisphere, char ewHemisphere, double elevation, List<Double> precipitation, List<Double> temperature, int startYear, int endYear, boolean isMetric) {
    this.name = name;
    this.country = country;
    this.latitude = latitude;
    this.longitude = longitude;
    this.nsHemisphere = nsHemisphere;
    this.ewHemisphere = ewHemisphere;
    this.elevation = elevation;
    this.precipitation = precipitation;
    this.temperature = temperature;
    this.startYear = startYear;
    this.endYear = endYear;
    this.isMetric = isMetric;
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

  public boolean isIsMetric() {
    return isMetric;
  }

  public double getLatitude() {
    return latitude;
  }

  public double getLongitude() {
    return longitude;
  }

  public String getName() {
    return name;
  }

  public char getNsHemisphere() {
    return nsHemisphere;
  }

  public List<Double> getPrecipitation() {
    return precipitation;
  }

  public int getStartYear() {
    return startYear;
  }

  public List<Double> getTemperature() {
    return temperature;
  }

  public void setMetadata(NewhallDatasetMetadata metadata) {
    this.metadata = metadata;
  }

  public NewhallDatasetMetadata getMetadata() {
    return metadata;
  }
}
