package org.psu.bulksummarizer.util;

public class Dataset {

  private String stnName;
  private String stnID;
  private String latDD;
  private String lonDD;
  private String elev;
  private String smrclass;
  private String subgrpmod;
  private String strclass;
  private String awb;
  private String swb;
  private String yrdryCum;
  private String yrmdCum;
  private String yrmstCum;
  private String bio5dryCum;
  private String bio5mdCum;
  private String bio5mstCum;
  private String yrmstCons;
  private String bio8mstCons;
  private String smrdryCons;
  private String wtrmstCons;
  private String petJan;
  private String petFeb;
  private String petMar;
  private String petApr;
  private String petMay;
  private String petJun;
  private String petJul;
  private String petAug;
  private String petSep;
  private String petOct;
  private String petNov;
  private String petDec;
  private String wbJan;
  private String wbFeb;
  private String wbMar;
  private String wbApr;
  private String wbMay;
  private String wbJun;
  private String wbJul;
  private String wbAug;
  private String wbSep;
  private String wbOct;
  private String wbNov;
  private String wbDec;
  private String stJan;
  private String stFeb;
  private String stMar;
  private String stApr;
  private String stMay;
  private String stJun;
  private String stJul;
  private String stAug;
  private String stSep;
  private String stOct;
  private String stNov;
  private String stDec;
  private String pdType;
  private String runDate;
  private String fileName;

  @Override
  public String toString() {
    return stnName + "," + stnID + "," + latDD + "," + lonDD + "," + elev + "," + smrclass + "," + subgrpmod + ","
            + strclass + "," + awb + "," + swb + "," + yrdryCum + "," + yrmdCum + "," + yrmstCum + "," + bio5dryCum + ","
            + bio5mdCum + "," + bio5mstCum + "," + yrmstCons + "," + bio8mstCons + "," + smrdryCons + "," + wtrmstCons
            + "," + petJan + "," + petFeb + "," + petMar + "," + petApr + "," + petMay + "," + petJun + "," + petJul
            + "," + petAug + "," + petSep + "," + petOct + "," + petNov + "," + petDec + "," + wbJan + "," + wbFeb + ","
            + wbMar + "," + wbApr + "," + wbMay + "," + wbJun + "," + wbJul + "," + wbAug + "," + wbSep + "," + wbOct
            + "," + wbNov + "," + wbDec + "," + stJan + "," + stFeb + "," + stMar + "," + stApr + "," + stMay + ","
            + stJun + "," + stJul + "," + stAug + "," + stSep + "," + stOct + "," + stNov + "," + stDec + "," + pdType
            + "," + runDate + "," + fileName;
  }

  public String[] toStringArray() {
    return this.toString().split(",");
  }

  public String getAwb() {
    return awb;
  }

  public void setAwb(String awb) {
    this.awb = awb;
  }

  public String getBio5dryCum() {
    return bio5dryCum;
  }

  public void setBio5dryCum(String bio5dryCum) {
    this.bio5dryCum = bio5dryCum;
  }

  public String getBio5mdCum() {
    return bio5mdCum;
  }

  public void setBio5mdCum(String bio5mdCum) {
    this.bio5mdCum = bio5mdCum;
  }

  public String getBio5mstCum() {
    return bio5mstCum;
  }

  public void setBio5mstCum(String bio5mstCum) {
    this.bio5mstCum = bio5mstCum;
  }

  public String getBio8mstCons() {
    return bio8mstCons;
  }

  public void setBio8mstCons(String bio8mstCons) {
    this.bio8mstCons = bio8mstCons;
  }

  public String getElev() {
    return elev;
  }

  public void setElev(String elev) {
    this.elev = elev;
  }

  public String getFileName() {
    return fileName;
  }

  public void setFileName(String fileName) {
    this.fileName = fileName;
  }

  public String getLatDD() {
    return latDD;
  }

  public void setLatDD(String latDD) {
    this.latDD = latDD;
  }

  public String getLonDD() {
    return lonDD;
  }

  public void setLonDD(String lonDD) {
    this.lonDD = lonDD;
  }

  public String getPdType() {
    return pdType;
  }

  public void setPdType(String pdType) {
    this.pdType = pdType;
  }

  public String getPetApr() {
    return petApr;
  }

  public void setPetApr(String petApr) {
    this.petApr = petApr;
  }

  public String getPetAug() {
    return petAug;
  }

  public void setPetAug(String petAug) {
    this.petAug = petAug;
  }

  public String getPetDec() {
    return petDec;
  }

  public void setPetDec(String petDec) {
    this.petDec = petDec;
  }

  public String getPetFeb() {
    return petFeb;
  }

  public void setPetFeb(String petFeb) {
    this.petFeb = petFeb;
  }

  public String getPetJan() {
    return petJan;
  }

  public void setPetJan(String petJan) {
    this.petJan = petJan;
  }

  public String getPetJul() {
    return petJul;
  }

  public void setPetJul(String petJul) {
    this.petJul = petJul;
  }

  public String getPetJun() {
    return petJun;
  }

  public void setPetJun(String petJun) {
    this.petJun = petJun;
  }

  public String getPetMar() {
    return petMar;
  }

  public void setPetMar(String petMar) {
    this.petMar = petMar;
  }

  public String getPetMay() {
    return petMay;
  }

  public void setPetMay(String petMay) {
    this.petMay = petMay;
  }

  public String getPetNov() {
    return petNov;
  }

  public void setPetNov(String petNov) {
    this.petNov = petNov;
  }

  public String getPetOct() {
    return petOct;
  }

  public void setPetOct(String petOct) {
    this.petOct = petOct;
  }

  public String getPetSep() {
    return petSep;
  }

  public void setPetSep(String petSep) {
    this.petSep = petSep;
  }

  public String getRunDate() {
    return runDate;
  }

  public void setRunDate(String runDate) {
    this.runDate = runDate;
  }

  public String getSmrclass() {
    return smrclass;
  }

  public void setSmrclass(String smrclass) {
    this.smrclass = smrclass;
  }

  public String getSmrdryCons() {
    return smrdryCons;
  }

  public void setSmrdryCons(String smrdryCons) {
    this.smrdryCons = smrdryCons;
  }

  public String getStApr() {
    return stApr;
  }

  public void setStApr(String stApr) {
    this.stApr = stApr;
  }

  public String getStAug() {
    return stAug;
  }

  public void setStAug(String stAug) {
    this.stAug = stAug;
  }

  public String getStDec() {
    return stDec;
  }

  public void setStDec(String stDec) {
    this.stDec = stDec;
  }

  public String getStFeb() {
    return stFeb;
  }

  public void setStFeb(String stFeb) {
    this.stFeb = stFeb;
  }

  public String getStJan() {
    return stJan;
  }

  public void setStJan(String stJan) {
    this.stJan = stJan;
  }

  public String getStJul() {
    return stJul;
  }

  public void setStJul(String stJul) {
    this.stJul = stJul;
  }

  public String getStJun() {
    return stJun;
  }

  public void setStJun(String stJun) {
    this.stJun = stJun;
  }

  public String getStMar() {
    return stMar;
  }

  public void setStMar(String stMar) {
    this.stMar = stMar;
  }

  public String getStMay() {
    return stMay;
  }

  public void setStMay(String stMay) {
    this.stMay = stMay;
  }

  public String getStNov() {
    return stNov;
  }

  public void setStNov(String stNov) {
    this.stNov = stNov;
  }

  public String getStOct() {
    return stOct;
  }

  public void setStOct(String stOct) {
    this.stOct = stOct;
  }

  public String getStSep() {
    return stSep;
  }

  public void setStSep(String stSep) {
    this.stSep = stSep;
  }

  public String getStnID() {
    return stnID;
  }

  public void setStnID(String stnID) {
    this.stnID = stnID;
  }

  public String getStnName() {
    return stnName;
  }

  public void setStnName(String stnName) {
    this.stnName = stnName;
  }

  public String getStrclass() {
    return strclass;
  }

  public void setStrclass(String strclass) {
    this.strclass = strclass;
  }

  public String getSubgrpmod() {
    return subgrpmod;
  }

  public void setSubgrpmod(String subgrpmod) {
    this.subgrpmod = subgrpmod;
  }

  public String getSwb() {
    return swb;
  }

  public void setSwb(String swb) {
    this.swb = swb;
  }

  public String getWbApr() {
    return wbApr;
  }

  public void setWbApr(String wbApr) {
    this.wbApr = wbApr;
  }

  public String getWbAug() {
    return wbAug;
  }

  public void setWbAug(String wbAug) {
    this.wbAug = wbAug;
  }

  public String getWbDec() {
    return wbDec;
  }

  public void setWbDec(String wbDec) {
    this.wbDec = wbDec;
  }

  public String getWbFeb() {
    return wbFeb;
  }

  public void setWbFeb(String wbFeb) {
    this.wbFeb = wbFeb;
  }

  public String getWbJan() {
    return wbJan;
  }

  public void setWbJan(String wbJan) {
    this.wbJan = wbJan;
  }

  public String getWbJul() {
    return wbJul;
  }

  public void setWbJul(String wbJul) {
    this.wbJul = wbJul;
  }

  public String getWbJun() {
    return wbJun;
  }

  public void setWbJun(String wbJun) {
    this.wbJun = wbJun;
  }

  public String getWbMar() {
    return wbMar;
  }

  public void setWbMar(String wbMar) {
    this.wbMar = wbMar;
  }

  public String getWbMay() {
    return wbMay;
  }

  public void setWbMay(String wbMay) {
    this.wbMay = wbMay;
  }

  public String getWbNov() {
    return wbNov;
  }

  public void setWbNov(String wbNov) {
    this.wbNov = wbNov;
  }

  public String getWbOct() {
    return wbOct;
  }

  public void setWbOct(String wbOct) {
    this.wbOct = wbOct;
  }

  public String getWbSep() {
    return wbSep;
  }

  public void setWbSep(String wbSep) {
    this.wbSep = wbSep;
  }

  public String getWtrmstCons() {
    return wtrmstCons;
  }

  public void setWtrmstCons(String wtrmstCons) {
    this.wtrmstCons = wtrmstCons;
  }

  public String getYrdryCum() {
    return yrdryCum;
  }

  public void setYrdryCum(String yrdryCum) {
    this.yrdryCum = yrdryCum;
  }

  public String getYrmdCum() {
    return yrmdCum;
  }

  public void setYrmdCum(String yrmdCum) {
    this.yrmdCum = yrmdCum;
  }

  public String getYrmstCons() {
    return yrmstCons;
  }

  public void setYrmstCons(String yrmstCons) {
    this.yrmstCons = yrmstCons;
  }

  public String getYrmstCum() {
    return yrmstCum;
  }

  public void setYrmstCum(String yrmstCum) {
    this.yrmstCum = yrmstCum;
  }
}
