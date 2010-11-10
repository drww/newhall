package org.psu.newhall.sim;

import java.util.Collections;
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
  double soilAirOffset;

  public NewhallDatasetMetadata(String stationName, String stationId, double stationElevation, String stationStateProvidence,
          String stationCountry, String mlraName, int mlraId, String contribFirstName, String contribLastName, String contribTitle,
          String contribOrg, String contribAddress, String contribCity, String contribStateProvidence, String contribPostal,
          String contribCountry, String contribEmail, String contribPhone, List<String> notes, String runDate, String modelVersion,
          String unitSystem, double soilAirOffset) {

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
    this.soilAirOffset = soilAirOffset;
  }

  public NewhallDatasetMetadata() {
    this.stationName = "";
    this.stationId = "";
    this.stationElevation = 0;
    this.stationStateProvidence = "";
    this.stationCountry = "";
    this.mlraName = "";
    this.mlraId = 0;
    this.contribFirstName = "";
    this.contribLastName = "";
    this.contribTitle = "";
    this.contribOrg = "";
    this.contribAddress = "";
    this.contribCity = "";
    this.contribStateProvidence = "";
    this.contribPostal = "";
    this.contribCountry = "";
    this.contribEmail = "";
    this.contribPhone = "";
    this.notes = Collections.EMPTY_LIST;
    this.runDate = "";
    this.modelVersion = "";
    this.unitSystem = "";
    this.soilAirOffset = 0;
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

  public void setContribAddress(String contribAddress) {
    this.contribAddress = contribAddress;
  }

  public void setContribCity(String contribCity) {
    this.contribCity = contribCity;
  }

  public void setContribCountry(String contribCountry) {
    this.contribCountry = contribCountry;
  }

  public void setContribEmail(String contribEmail) {
    this.contribEmail = contribEmail;
  }

  public void setContribFirstName(String contribFirstName) {
    this.contribFirstName = contribFirstName;
  }

  public void setContribLastName(String contribLastName) {
    this.contribLastName = contribLastName;
  }

  public void setContribOrg(String contribOrg) {
    this.contribOrg = contribOrg;
  }

  public void setContribPhone(String contribPhone) {
    this.contribPhone = contribPhone;
  }

  public void setContribPostal(String contribPostal) {
    this.contribPostal = contribPostal;
  }

  public void setContribStateProvidence(String contribStateProvidence) {
    this.contribStateProvidence = contribStateProvidence;
  }

  public void setContribTitle(String contribTitle) {
    this.contribTitle = contribTitle;
  }

  public void setMlraId(int mlraId) {
    this.mlraId = mlraId;
  }

  public void setMlraName(String mlraName) {
    this.mlraName = mlraName;
  }

  public void setModelVersion(String modelVersion) {
    this.modelVersion = modelVersion;
  }

  public void setNotes(List<String> notes) {
    this.notes = notes;
  }

  public void setRunDate(String runDate) {
    this.runDate = runDate;
  }

  public void setSoilAirOffset(double soilAirOffset) {
    this.soilAirOffset = soilAirOffset;
  }

  public void setStationCountry(String stationCountry) {
    this.stationCountry = stationCountry;
  }

  public void setStationElevation(double stationElevation) {
    this.stationElevation = stationElevation;
  }

  public void setStationId(String stationId) {
    this.stationId = stationId;
  }

  public void setStationName(String stationName) {
    this.stationName = stationName;
  }

  public void setStationStateProvidence(String stationStateProvidence) {
    this.stationStateProvidence = stationStateProvidence;
  }

  public void setUnitSystem(String unitSystem) {
    this.unitSystem = unitSystem;
  }
}
