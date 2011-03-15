package org.psu.newhall.util;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.psu.newhall.sim.NewhallDataset;
import org.psu.newhall.sim.NewhallDatasetMetadata;

public class XMLFileParser {

  NewhallDataset dataset;

  public XMLFileParser(File file) throws JDOMException, IOException {

    SAXBuilder builder = new SAXBuilder();
    Document doc = null;
    doc = builder.build(file.getAbsolutePath());

    Element root = doc.getRootElement();
    String modelVers = root.getAttributeValue("version");

    System.out.println("Parsing metadata.");
    /** Metadata **/
    Element metadata = root.getChild("metadata");

    Element stninfo = metadata.getChild("stninfo");
    String stationName = stninfo.getChildText("stnname");
    String nettype = stninfo.getChildText("nettype");
    String stationId = stninfo.getChildText("stnid");
    double stationElevation = Double.parseDouble(stninfo.getChildText("stnelev"));
    String stateProvidence = stninfo.getChildText("stateprov");
    String country = stninfo.getChildText("country");

    Element mlra = metadata.getChild("mlra");
    String mlraName = mlra.getChildText("mlraname");
    int mlraId = Integer.valueOf(mlra.getChildText("mlraid"));

    Element cntinfo = metadata.getChild("cntinfo");

    Element cntper = cntinfo.getChild("cntper");
    String firstName = cntper.getChildText("firstname");
    String lastName = cntper.getChildText("lastname");
    String title = cntper.getChildText("title");

    String cntorg = cntinfo.getChildText("cntorg");

    Element cntaddr = cntinfo.getChild("cntaddr");
    String address = cntaddr.getChildText("address");
    String city = cntaddr.getChildText("city");
    String stateprov = cntaddr.getChildText("stateprov");
    String postal = cntaddr.getChildText("postal");
    String cntCountry = cntaddr.getChildText("country");

    String cntemail = cntinfo.getChildText("cntemail");
    String cntphone = cntinfo.getChildText("cntphone");

    Element notes = metadata.getChild("notes");
    List<Element> allNotes = notes.getChildren();
    List<String> allNotesStr = new ArrayList<String>(allNotes.size());
    for (Element note : allNotes) {
      allNotesStr.add(note.getText());
    }

    String rundate = metadata.getChildText("rundate");
    String nsmver = metadata.getChildText("nsmver");
    String srcunitsys = metadata.getChildText("srcunitsys");

    System.out.println("Parsing input.");
    /** Input **/
    Element input = root.getChild("input");

    Element location = input.getChild("location");
    double lat = Double.parseDouble(location.getChildText("lat"));
    double lon = Double.parseDouble(location.getChildText("lon"));
    String usercoordfmt = location.getChildText("usercoordfmt");

    Element recordpd = input.getChild("recordpd");
    String pdtype = recordpd.getChildText("pdtype");
    int pdbegin = Integer.valueOf(recordpd.getChildText("pdbegin"));
    int pdend = Integer.valueOf(recordpd.getChildText("pdend"));

    Element precips = input.getChild("precips");
    List<Element> allPrecips = precips.getChildren();
    List<Double> allPrecipsDbl = new ArrayList<Double>(12);
    Element airtemps = input.getChild("airtemps");
    List<Element> allAirTemps = airtemps.getChildren();
    List<Double> allAirTempsDbl = new ArrayList<Double>(12);

    String[] monthStr = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

    System.out.println("Parsing precipitation and air temps.");

    for (String month : monthStr) {
      for (Element precip : allPrecips) {
        String id = precip.getAttributeValue("id");
        if (id.equals(month)) {
          allPrecipsDbl.add(Double.valueOf(precip.getText()));
          break;
        }
      }

      for (Element airtemp : allAirTemps) {
        String id = airtemp.getAttributeValue("id");
        if (id.equals(month)) {
          allAirTempsDbl.add(Double.valueOf(airtemp.getText()));
        }
      }
    }

    System.out.println("Parsing soil-air relationship variables.");

    Double smcsawc = Double.parseDouble(input.getChildText("smcsawc"));

    Element soilairrel = input.getChild("soilairrel");
    //Double lag = Double.parseDouble(soilairrel.getChildText("lag"));
    Double ampltd = Double.parseDouble(soilairrel.getChildText("ampltd"));
    Double maatmast = Double.parseDouble(soilairrel.getChildText("maatmast"));

    this.dataset = new NewhallDataset(stationName, country, lat, lon,
            'N', 'E', stationElevation, allPrecipsDbl, allAirTempsDbl,
            //pdbegin, pdend, unitsys.equals("metric"));
            pdbegin, pdend, true);

    NewhallDatasetMetadata ndm = new NewhallDatasetMetadata(stationName, stationId, stationElevation,
            stateProvidence, country, mlraName, mlraId, firstName, lastName, title, cntorg,
            address, city, stateprov, postal, cntCountry, cntemail, cntphone, allNotesStr,
            //rundate, modelVers, unitsys, maatmast, ampltd);
            rundate, modelVers, srcunitsys, maatmast, ampltd, nettype);
    
    this.dataset.setMetadata(ndm);

    System.out.println("XML dataset built.");

  }

  public NewhallDataset getDataset() {
    return dataset;
  }
  
}
