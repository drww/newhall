package org.psu.newhall.xml2csv.util;

import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;

public class XmlFileParser {

  public static Dataset parseXmlFile(File file) throws InvalidFileFormatException, NullPointerException {
    SAXBuilder builder = new SAXBuilder();
    Document doc = null;

    try {
      doc = builder.build(file.getAbsolutePath());
    } catch (JDOMException ex) {
      Logger.getLogger(XmlFileParser.class.getName()).log(Level.SEVERE, null, ex);
      throw new InvalidFileFormatException();
    } catch (IOException ex) {
      Logger.getLogger(XmlFileParser.class.getName()).log(Level.SEVERE, null, ex);
      throw new InvalidFileFormatException();
    }

    Dataset dataset = new Dataset();

    Element root = doc.getRootElement();
    Element input = root.getChild("input");
    Element output = root.getChild("output");
    Element metadata = root.getChild("metadata");

    if (root == null || input == null || output == null || metadata == null) {
      throw new InvalidFileFormatException();
    }

    Element stninfo = metadata.getChild("stninfo");
    dataset.setStnName(stninfo.getChildText("stnname"));
    dataset.setStnID(stninfo.getChildText("stnid"));
    dataset.setElev(stninfo.getChildText("stnelev"));

    Element location = input.getChild("location");
    dataset.setLatDD(location.getChildText("lat"));
    dataset.setLonDD(location.getChildText("lon"));

    dataset.setSmrclass(output.getChildText("smrclass"));
    dataset.setSubgrpmod(output.getChildText("subgrpmod"));
    dataset.setStrclass(output.getChildText("strclass"));
    dataset.setAwb(output.getChildText("awb"));
    dataset.setSwb(output.getChildText("swb"));

    Element smcsstate = output.getChild("smcstates");
    Element cumdays = smcsstate.getChild("cumdays");
    Element consdays = smcsstate.getChild("consdays");

    dataset.setYrdryCum(cumdays.getChildText("yrdry"));
    dataset.setYrmdCum(cumdays.getChildText("yrmd"));
    dataset.setYrmstCum(cumdays.getChildText("yrmst"));
    dataset.setBio5dryCum(cumdays.getChildText("bio5dry"));
    dataset.setBio5mdCum(cumdays.getChildText("bio5md"));
    dataset.setBio5mstCum(cumdays.getChildText("bio5mst"));

    dataset.setYrmstCons(consdays.getChildText("yrmst"));
    dataset.setBio8mstCons(consdays.getChildText("bio8mst"));
    dataset.setSmrdryCons(consdays.getChildText("smrdry"));
    dataset.setWtrmstCons(consdays.getChildText("wtrmst"));

    List<Element> pets = output.getChild("pets").getChildren();
    dataset.setPetJan(pets.get(0).getText());
    dataset.setPetFeb(pets.get(1).getText());
    dataset.setPetMar(pets.get(2).getText());
    dataset.setPetApr(pets.get(3).getText());
    dataset.setPetMay(pets.get(4).getText());
    dataset.setPetJun(pets.get(5).getText());
    dataset.setPetJul(pets.get(6).getText());
    dataset.setPetAug(pets.get(7).getText());
    dataset.setPetSep(pets.get(8).getText());
    dataset.setPetOct(pets.get(9).getText());
    dataset.setPetNov(pets.get(10).getText());
    dataset.setPetDec(pets.get(11).getText());

    List<Element> precips = input.getChild("precips").getChildren();
    Double wbJan = Double.valueOf(precips.get(0).getText()) - Double.valueOf(dataset.getPetJan());
    dataset.setWbJan(round(wbJan, 2).toString());
    Double wbFeb = Double.valueOf(precips.get(1).getText()) - Double.valueOf(dataset.getPetFeb());
    dataset.setWbFeb(round(wbFeb, 2).toString());
    Double wbMar = Double.valueOf(precips.get(2).getText()) - Double.valueOf(dataset.getPetMar());
    dataset.setWbMar(round(wbMar, 2).toString());
    Double wbApr = Double.valueOf(precips.get(3).getText()) - Double.valueOf(dataset.getPetApr());
    dataset.setWbApr(round(wbApr, 2).toString());
    Double wbMay = Double.valueOf(precips.get(4).getText()) - Double.valueOf(dataset.getPetMay());
    dataset.setWbMay(round(wbMay, 2).toString());
    Double wbJun = Double.valueOf(precips.get(5).getText()) - Double.valueOf(dataset.getPetJun());
    dataset.setWbJun(round(wbJun, 2).toString());
    Double wbJul = Double.valueOf(precips.get(6).getText()) - Double.valueOf(dataset.getPetJul());
    dataset.setWbJul(round(wbJul, 2).toString());
    Double wbAug = Double.valueOf(precips.get(7).getText()) - Double.valueOf(dataset.getPetAug());
    dataset.setWbAug(round(wbAug, 2).toString());
    Double wbSep = Double.valueOf(precips.get(8).getText()) - Double.valueOf(dataset.getPetSep());
    dataset.setWbSep(round(wbSep, 2).toString());
    Double wbOct = Double.valueOf(precips.get(9).getText()) - Double.valueOf(dataset.getPetOct());
    dataset.setWbOct(round(wbOct, 2).toString());
    Double wbNov = Double.valueOf(precips.get(10).getText()) - Double.valueOf(dataset.getPetNov());
    dataset.setWbNov(round(wbNov, 2).toString());
    Double wbDec = Double.valueOf(precips.get(11).getText()) - Double.valueOf(dataset.getPetDec());
    dataset.setWbDec(round(wbDec, 2).toString());

    /**List<Element> soiltemps = output.getChild("soiltemps").getChildren();
    dataset.setStJan(soiltemps.get(0).getText());
    dataset.setStFeb(soiltemps.get(1).getText());
    dataset.setStMar(soiltemps.get(2).getText());
    dataset.setStApr(soiltemps.get(3).getText());
    dataset.setStMay(soiltemps.get(4).getText());
    dataset.setStJun(soiltemps.get(5).getText());
    dataset.setStJul(soiltemps.get(6).getText());
    dataset.setStAug(soiltemps.get(7).getText());
    dataset.setStSep(soiltemps.get(8).getText());
    dataset.setStOct(soiltemps.get(9).getText());
    dataset.setStNov(soiltemps.get(10).getText());
    dataset.setStDec(soiltemps.get(11).getText());**/

    dataset.setPdType(input.getChild("recordpd").getChildText("pdtype"));
    dataset.setRunDate(metadata.getChildText("rundate"));
    dataset.setFileName(file.getName());

    //System.out.println("PARSED: " + dataset);

    return dataset;
  }

  private static Double round(Double d, int decimalPlaces) {
    String format = "#.#";
    for (int i = decimalPlaces - 1; i > 0; i--) {
      format += "#";
    }
    DecimalFormat df = new DecimalFormat(format);
    return Double.valueOf(df.format(d));
  }
}
