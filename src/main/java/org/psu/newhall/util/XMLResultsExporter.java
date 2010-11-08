package org.psu.newhall.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

    Element cntper = new Element("cntper");
    Element firstname = new Element("firstname");
    Element lastname = new Element("lastname");
    Element title = new Element("title");

    if(dataset.getMetadata() != null) {
      firstname.setText(dataset.getMetadata().getContribFirstName());
      lastname.setText(dataset.getMetadata().getContribLastName());
      title.setText(dataset.getMetadata().getContribTitle());
    }

    cntper.addContent(firstname);
    cntper.addContent(lastname);
    cntper.addContent(title);
    cntinfo.addContent(cntper);

    Element cntorg = new Element("cntorg");
    if(dataset.getMetadata() != null) {
      cntorg.setText(dataset.getMetadata().getContribOrg());
    }
    cntinfo.addContent(cntorg);

    Element cntaddr = new Element("cntaddr");
    Element address = new Element("address");
    Element city = new Element("city");
    Element stateprovContrib = new Element("stateprov");
    Element postal = new Element("postal");
    Element countryContrib = new Element("country");

    if(dataset.getMetadata() != null) {
      address.setText(dataset.getMetadata().getContribAddress());
      city.setText(dataset.getMetadata().getContribCity());
      stateprovContrib.setText(dataset.getMetadata().getContribStateProvidence());
      postal.setText(dataset.getMetadata().getContribPostal());
      countryContrib.setText(dataset.getMetadata().getContribCountry());
    }

    cntaddr.addContent(address);
    cntaddr.addContent(city);
    cntaddr.addContent(stateprovContrib);
    cntaddr.addContent(postal);
    cntaddr.addContent(countryContrib);
    cntinfo.addContent(cntaddr);

    Element cntemail = new Element("cntemail");
    Element cntphone = new Element("cntphone");

    if(dataset.getMetadata() != null) {
      cntemail.setText(dataset.getMetadata().getContribEmail());
      cntphone.setText(dataset.getMetadata().getContribPhone());
    }

    cntinfo.addContent(cntemail);
    cntinfo.addContent(cntphone);
    metadata.addContent(cntinfo);

    Element notes = new Element("notes");
    if(dataset.getMetadata() != null) {
      for(String noteText : dataset.getMetadata().getNotes()) {
        Element note = new Element("note");
        note.setText(noteText);
        notes.addContent(note);
      }
    }
    metadata.addContent(notes);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Element rundate = new Element("rundate");
    rundate.setText(sdf.toPattern());
    metadata.addContent(rundate);

    Element nsmversion = new Element("nsmversion");
    Element unitsys = new Element("unitsys");
    nsmversion.setText(org.psu.newhall.Newhall.NSM_VERSION);
    if(dataset.isMetric()) {
      unitsys.setText("metric");
    } else {
      unitsys.setText("english");
    }
    metadata.addContent(nsmversion);
    metadata.addContent(unitsys);

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
