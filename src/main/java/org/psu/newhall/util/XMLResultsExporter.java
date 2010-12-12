package org.psu.newhall.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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

    /**
     * Build Metadata, populate empty tags if no metadata.
     * 
     **/
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

    if (dataset.getMetadata() != null) {
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

    if (dataset.getMetadata() != null) {
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

    if (dataset.getMetadata() != null) {
      firstname.setText(dataset.getMetadata().getContribFirstName());
      lastname.setText(dataset.getMetadata().getContribLastName());
      title.setText(dataset.getMetadata().getContribTitle());
    }

    cntper.addContent(firstname);
    cntper.addContent(lastname);
    cntper.addContent(title);
    cntinfo.addContent(cntper);

    Element cntorg = new Element("cntorg");
    if (dataset.getMetadata() != null) {
      cntorg.setText(dataset.getMetadata().getContribOrg());
    }
    cntinfo.addContent(cntorg);

    Element cntaddr = new Element("cntaddr");
    Element address = new Element("address");
    Element city = new Element("city");
    Element stateprovContrib = new Element("stateprov");
    Element postal = new Element("postal");
    Element countryContrib = new Element("country");

    if (dataset.getMetadata() != null) {
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

    if (dataset.getMetadata() != null) {
      cntemail.setText(dataset.getMetadata().getContribEmail());
      cntphone.setText(dataset.getMetadata().getContribPhone());
    }

    cntinfo.addContent(cntemail);
    cntinfo.addContent(cntphone);
    metadata.addContent(cntinfo);

    Element notes = new Element("notes");
    if (dataset.getMetadata() != null) {
      for (String noteText : dataset.getMetadata().getNotes()) {
        Element note = new Element("note");
        note.setText(noteText);
        notes.addContent(note);
      }
    }
    metadata.addContent(notes);

    SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    Element rundate = new Element("rundate");
    rundate.setText(sdf.format(new Date()));
    metadata.addContent(rundate);

    Element nsmversion = new Element("nsmver");
    Element unitsys = new Element("unitsys");
    nsmversion.setText(org.psu.newhall.Newhall.NSM_VERSION);
    if (dataset.isMetric()) {
      unitsys.setText("metric");
    } else {
      unitsys.setText("english");
    }
    metadata.addContent(nsmversion);
    metadata.addContent(unitsys);

    /**
     * Recite dataset input data.
     *
     **/
    Element input = new Element("input");

    Element location = new Element("location");
    Element lat = new Element("lat");
    lat.setText(Double.toString(dataset.getLatitude()));
    Element lon = new Element("lon");
    lon.setText(Double.toString(dataset.getLongitude()));
    Element usercoordfmt = new Element("usercoordfmt");
    usercoordfmt.setText("DD");
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
    for (int i = 0; i < months.length; i++) {
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
    maatmast.setText(Double.toString(dataset.getMetadata().getSoilAirOffset()));
    soilairrel.addContent(lag);
    soilairrel.addContent(ampltd);
    soilairrel.addContent(maatmast);
    input.addContent(soilairrel);

    /**
     * Export Output data.
     *
     **/
    Element output = new Element("output");

    Element smrclass = new Element("smrclass");
    Element strclass = new Element("strclass");
    Element awb = new Element("awb");
    Element swb = new Element("swb");
    Element smcstates = new Element("smcstates");
    Element pets = new Element("pets");
    Element soiltemps = new Element("soiltemps");
    Element calendars = new Element("calendars");

    smrclass.setText(results.getMoistureRegime());
    strclass.setText(results.getTemperatureRegime());

    /** <WAGS> **/
    awb.setText("340");
    swb.setText("-74");
    /** </WAGS> **/

    Element cumdays = new Element("cumdays");
    Element yrdry = new Element("yrdry");
    Element yrmd = new Element("yrmd");
    Element yrmst = new Element("yrmst");
    Element bio5dry = new Element("bio5dry");
    Element bio5md = new Element("bio5md");
    Element bio5mst = new Element("bio5mst");

    yrdry.setText(Integer.toString(results.getNumCumulativeDaysDry()));
    yrmd.setText(Integer.toString(results.getNumCumulativeDaysMoistDry()));
    yrmst.setText(Integer.toString(results.getNumCumulativeDaysMoist()));

    bio5dry.setText(Integer.toString(results.getNumCumulativeDaysDryOver5C()));
    bio5md.setText(Integer.toString(results.getNumCumulativeDaysMoistDryOver5C()));
    bio5mst.setText(Integer.toString(results.getNumCumulativeDaysMoistOver5C()));

    cumdays.addContent(yrdry);
    cumdays.addContent(yrmd);
    cumdays.addContent(yrmst);
    cumdays.addContent(bio5dry);
    cumdays.addContent(bio5md);
    cumdays.addContent(bio5mst);

    Element consdays = new Element("consdays");
    Element yrmst2 = new Element("yrmst");
    Element bio8mst = new Element("bio8mst");
    Element smrdry = new Element("smrdry");
    Element wtrmst = new Element("wtrmst");

    yrmst2.setText(Integer.toString(results.getNumConsecutiveDaysMoistInSomeParts()));
    bio8mst.setText(Integer.toString(results.getNumConsecutiveDaysMoistInSomePartsOver8C()));
    smrdry.setText(Integer.toString(results.getDryDaysAfterSummerSolstice()));
    wtrmst.setText(Integer.toString(results.getMoistDaysAfterWinterSolstice()));

    consdays.addContent(yrmst2);
    consdays.addContent(bio8mst);
    consdays.addContent(smrdry);
    consdays.addContent(wtrmst);

    smcstates.addContent(cumdays);
    smcstates.addContent(consdays);
    
    for (int i = 0; i < months.length; i++) {
      Element pet = new Element("pet");
      pet.setAttribute("id", months[i]);
      pet.setText(Double.toString(round(results.getMeanPotentialEvapotranspiration().get(i))));
      pets.addContent(pet);
    }

    for (int i = 0; i < months.length; i++) {
      Element soiltemp = new Element("soiltemp");
      soiltemp.setAttribute("id", months[i]);
      soiltemp.setText(Double.toString(round(dataset.getTemperature().get(i) + dataset.getMetadata().getSoilAirOffset())));
      soiltemps.addContent(soiltemp);
    }

    Element tempCalElement = new Element("tempcal");
    List<Character> tempCal = results.getTemperatureCalendar();
    char lastChar = tempCal.get(0);
    int lastPos = 0;
    for(int i = 1; i < tempCal.size(); i++) {
      char thisChar = tempCal.get(i);
      if(thisChar == lastChar && i != tempCal.size() - 1) {
        continue;
      } else {
        Element blockToAdd = null;

        switch(lastChar) {
          case '-': blockToAdd = new Element("stlt5"); break;
          case '5': blockToAdd = new Element("st5to8"); break;
          case '8': blockToAdd = new Element("stgt8"); break;
          default: blockToAdd = new Element("unknown"); break;
        }

        Element beginday = new Element("beginday");
        Element endday = new Element("endday");
        beginday.setText(Integer.toString(lastPos + 1));
        endday.setText(Integer.toString(i));

        // Edge case at the end of the calendar.
        if(i == tempCal.size() - 1) {
          endday.setText(Integer.toString(tempCal.size()));
        }

        blockToAdd.addContent(beginday);
        blockToAdd.addContent(endday);
        tempCalElement.addContent(blockToAdd);
        
        lastChar = thisChar;
        lastPos = i;
      }
    }
    calendars.addContent(tempCalElement);

    Element moistCalElement = new Element("moistcal");
    List<Integer> moistCal = results.getMoistureCalendar();
    int lastVal = moistCal.get(0);
    lastPos = 0;
    for(int i = 1; i < moistCal.size(); i++) {
      int thisVal = moistCal.get(i);
      if(thisVal == lastVal && i != moistCal.size() - 1) {
        continue;
      } else {
        Element blockToAdd = null;

        switch(lastVal) {
          case 1: blockToAdd = new Element("dry"); break;
          case 2: blockToAdd = new Element("moistdry"); break;
          case 3: blockToAdd = new Element("moist"); break;
          default: blockToAdd = new Element("unknown"); break;
        }

        Element beginday = new Element("beginday");
        Element endday = new Element("endday");
        beginday.setText(Integer.toString(lastPos + 1));
        endday.setText(Integer.toString(i));

        // Edge case at the end of the calendar.
        if(i == moistCal.size() - 1) {
          endday.setText(Integer.toString(moistCal.size()));
        }

        blockToAdd.addContent(beginday);
        blockToAdd.addContent(endday);
        moistCalElement.addContent(blockToAdd);

        lastVal = thisVal;
        lastPos = i;
      }
    }
    calendars.addContent(moistCalElement);

    output.addContent(smrclass);
    output.addContent(strclass);
    output.addContent(awb);
    output.addContent(swb);
    output.addContent(smcstates);
    output.addContent(pets);
    output.addContent(soiltemps);
    output.addContent(calendars);

    /** Combine tags into root tag. **/
    model.addContent(metadata);
    model.addContent(input);
    model.addContent(output);

    /** Extract model output data. **/
    XMLOutputter outputter = new XMLOutputter();
    outputter.setFormat(Format.getPrettyFormat());
    FileWriter writer = new FileWriter(outputFile);
    outputter.output(doc, writer);
    writer.close();

  }

  double round(double d) {
    DecimalFormat df = new DecimalFormat("#.##");
    return Double.valueOf(df.format(d));
  }
}
