package org.psu.newhall.sim;

import java.util.List;

public class NewhallDatasetMetadata {

  String stationName;
  String stationId;
  double stationElevation;
  String stationStateProvidence;
  String stationCountry;
  
  String mlraName;
  int mlraId;
  
  String contribFirstName;
  String contribLastName;
  String contribTitle;
  String contribOrg;
  String contribAddress;
  String contribCity;
  String contribStateProvidence;
  String contribPostal;
  String contribCountry;
  String contribEmail;
  String contribPhone;
  
  List<String> notes;
  String runDate;
  String modelVersion;
  String unitSystem;

  public NewhallDatasetMetadata(String stationName, String stationId, double stationElevation, String stationStateProvidence, String stationCountry, String mlraName, int mlraId, String contribFirstName, String contribLastName, String contribTitle, String contribOrg, String contribAddress, String contribCity, String contribStateProvidence, String contribPostal, String contribCountry, String contribEmail, String contribPhone, List<String> notes, String runDate, String modelVersion, String unitSystem) {
    this.stationName = stationName;
    this.stationId = stationId;
    this.stationElevation = stationElevation;
    this.stationStateProvidence = stationStateProvidence;
    this.stationCountry = stationCountry;
    this.mlraName = mlraName;
    this.mlraId = mlraId;
    this.contribFirstName = contribFirstName;
    this.contribLastName = contribLastName;
    this.contribTitle = contribTitle;
    this.contribOrg = contribOrg;
    this.contribAddress = contribAddress;
    this.contribCity = contribCity;
    this.contribStateProvidence = contribStateProvidence;
    this.contribPostal = contribPostal;
    this.contribCountry = contribCountry;
    this.contribEmail = contribEmail;
    this.contribPhone = contribPhone;
    this.notes = notes;
    this.runDate = runDate;
    this.modelVersion = modelVersion;
    this.unitSystem = unitSystem;
  }

  public String getContribAddress() {
    return contribAddress;
  }

  public String getContribCity() {
    return contribCity;
  }

  public String getContribCountry() {
    return contribCountry;
  }

  public String getContribEmail() {
    return contribEmail;
  }

  public String getContribFirstName() {
    return contribFirstName;
  }

  public String getContribLastName() {
    return contribLastName;
  }

  public String getContribOrg() {
    return contribOrg;
  }

  public String getContribPhone() {
    return contribPhone;
  }

  public String getContribPostal() {
    return contribPostal;
  }

  public String getContribStateProvidence() {
    return contribStateProvidence;
  }

  public String getContribTitle() {
    return contribTitle;
  }

  public int getMlraId() {
    return mlraId;
  }

  public String getMlraName() {
    return mlraName;
  }

  public String getModelVersion() {
    return modelVersion;
  }

  public List<String> getNotes() {
    return notes;
  }

  public String getRunDate() {
    return runDate;
  }

  public String getStationCountry() {
    return stationCountry;
  }

  public double getStationElevation() {
    return stationElevation;
  }

  public String getStationId() {
    return stationId;
  }

  public String getStationName() {
    return stationName;
  }

  public String getStationStateProvidence() {
    return stationStateProvidence;
  }

  public String getUnitSystem() {
    return unitSystem;
  }

}
