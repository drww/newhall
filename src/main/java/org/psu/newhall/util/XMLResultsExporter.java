package org.psu.newhall.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.psu.newhall.sim.NewhallDataset;
import org.psu.newhall.sim.NewhallResults;

public class XMLResultsExporter {

  File outputFile;

  public XMLResultsExporter(File outputFile) {
    this.outputFile = outputFile;
  }

  public void export(NewhallResults results, NewhallDataset dataset) throws IOException {

    Document doc = new Document();
    Element model = new Element("model");
    doc.setRootElement(model);

    /** Extract Metadata, build empty set if null. **/

    Element metadata = new Element("metadata");

    Element stninfo = new Element("stninfo");
    Element stnid = new Element("stnid");
    Element stnname = new Element("stnname");
    stnname.setText(dataset.getName());
    Element stnelev = new Element("stnelev");
    stnelev.setText(Double.toString(dataset.getElevation()));
    Element stateprov = new Element("stateprov");
    Element country = new Element("country");
    country.setText(dataset.getCountry());

    if(dataset.getMetadata() != null) {
      stnid.setText(dataset.getMetadata().getStationId());
      stateprov.setText(dataset.getMetadata().getStationStateProvidence());
    }

    stninfo.addContent(stnname);
    stninfo.addContent(stnid);
    stninfo.addContent(stnelev);
    stninfo.addContent(stateprov);
    stninfo.addContent(country);
    metadata.addContent(stninfo);

    Element mlra = new Element("mlra");
    Element mlraname = new Element("mlraname");
    Element mlraid = new Element("mlraid");

    if(dataset.getMetadata() != null) {
      mlraname.setText(dataset.getMetadata().getMlraName());
      mlraid.setText(Integer.toString(dataset.getMetadata().getMlraId()));
    }

    mlra.addContent(mlraname);
    mlra.addContent(mlraid);
    metadata.addContent(mlra);

    Element cntinfo = new Element("cntinfo");

    metadata.addContent(cntinfo);

    /** Extract dataset input data. **/
    
    Element input = new Element("input");

    Element location = new Element("location");
    Element lat = new Element("lat");
    lat.setText(Double.toString(dataset.getLatitude()));
    Element lon = new Element("lon");
    lon.setText(Double.toString(dataset.getLongitude()));
    Element usercoordfmt = new Element("usercoordfmt");
    usercoordfmt.setText("DMS");
    location.addContent(lat);
    location.addContent(lon);
    location.addContent(usercoordfmt);
    input.addContent(location);

    Element recordpd = new Element("recordpd");
    Element pdtype = new Element("pdtype");
    pdtype.setText("normal");
    Element pdbegin = new Element("pdbegin");
    pdbegin.setText(Integer.toString(dataset.getStartYear()));
    Element pdend = new Element("pdend");
    pdend.setText(Integer.toString(dataset.getEndYear()));
    recordpd.addContent(pdtype);
    recordpd.addContent(pdbegin);
    recordpd.addContent(pdend);
    input.addContent(recordpd);

    Element precips = new Element("precips");
    String[] months = {"Jan", "Feb", "Mar", "Apr", "May", "Jun",
      "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};
    for (int i = 0; i < months.length; i++) {
      Element precip = new Element("precip");
      precip.setAttribute("id", months[i]);
      precip.setText(Double.toString(dataset.getPrecipitation().get(i)));
      precips.addContent(precip);
    }
    input.addContent(precips);

    Element airtemps = new Element("airtemps");
    for(int i = 0; i< months.length; i++) {
      Element airtemp = new Element("airtemp");
      airtemp.setAttribute("id", months[i]);
      airtemp.setText(Double.toString(dataset.getTemperature().get(i)));
      airtemps.addContent(airtemp);
    }
    input.addContent(airtemps);

    Element smcsawc = new Element("smcsawc");
    smcsawc.setText(Double.toString(results.getWaterHoldingCapacity()));
    input.addContent(smcsawc);

    Element soilairrel = new Element("soilairrel");
    Element lag = new Element("lag");
    lag.setText("18");
    Element ampltd = new Element("ampltd");
    ampltd.setText("0.66");
    Element maatmast = new Element("maatmast");
    maatmast.setText("1.2");
    soilairrel.addContent(lag);
    soilairrel.addContent(ampltd);
    soilairrel.addContent(maatmast);
    input.addContent(soilairrel);

    model.addContent(input);

    /** Extract model output data. **/
    XMLOutputter outputter = new XMLOutputter();
    outputter.setFormat(Format.getPrettyFormat());
    FileWriter writer = new FileWriter(outputFile);
    outputter.output(doc, writer);
    writer.close();

  }
}
