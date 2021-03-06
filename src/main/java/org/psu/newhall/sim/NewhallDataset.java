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
  private double waterholdingCapacity;
  private NewhallDatasetMetadata metadata;

  public NewhallDataset(String name, String country, double latitude, double longitude, char nsHemisphere,
          char ewHemisphere, double elevation, List<Double> precipitation, List<Double> temperature, int startYear,
          int endYear, boolean isMetric, double waterholdingCapacity) {
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
    this.waterholdingCapacity = waterholdingCapacity;
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
    return (int) latitude;
  }

  public double getLatitudeMinutes() {
    double remainder = latitude - (int) latitude;
    return remainder * 60;
  }

  public double getLongitude() {
    return longitude;
  }

  public int getLongitudeDegrees() {
    return (int) longitude;
  }

  public double getLongitudeMinutes() {
    double remainder = longitude - (int) longitude;
    return remainder * 60;
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

  public double getWaterholdingCapacity() {
    return waterholdingCapacity;
  }

  @Override
  public String toString() {
    String result = this.getClass().toString();
    result += "\n  Name: " + name;
    result += "\n  Country: " + country;
    result += "\n  Latitude: " + latitude + " " + nsHemisphere;
    result += "\n  Longitude: " + longitude + " " + ewHemisphere;
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
    result += "\n  Has Metadata: " + (getMetadata() != null);

    return result;
  }
}
