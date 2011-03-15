package org.psu.newhall.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.jdom.Document;
import org.jdom.Element;
import org.jdom.output.Format;
import org.jdom.output.XMLOutputter;
import org.psu.newhall.sim.NewhallDataset;
import org.psu.newhall.sim.NewhallResults;

public class XMLStringResultsExporter {

  public static String export(NewhallResults results, NewhallDataset dataset) {

    boolean toMetric = true;

    Document doc = new Document();
    Element model = new Element("model");
    doc.setRootElement(model);

    /**
     * Build Metadata, populate empty tags if no metadata.
     * 
     **/
    Element metadata = new Element("metadata");

    Element stninfo = new Element("stninfo");
    Element nettype = new Element("nettype");
    Element stnid = new Element("stnid");
    nettype.setText(dataset.getMetadata().getNetwork());
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

    stninfo.addContent(nettype);
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
    Element srcunitsys = new Element("srcunitsys");
    nsmversion.setText(org.psu.newhall.Newhall.NSM_VERSION);
    srcunitsys.setText(dataset.getMetadata().getUnitSystem());
    /**if (toMetric) {
    unitsys.setText("metric");
    } else {
    unitsys.setText("english");
    }**/
    metadata.addContent(nsmversion);
    metadata.addContent(srcunitsys);

    /**
     * Recite dataset input data.
     *
     **/
    Element input = new Element("input");

    Element location = new Element("location");
    Element lat = new Element("lat");
    lat.setText(Double.toString(round(dataset.getLatitude(), 4)));
    Element lon = new Element("lon");
    lon.setText(Double.toString(round(dataset.getLongitude(), 4)));
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
      Double precipVal = dataset.getPrecipitation().get(i);
      if (toMetric && !dataset.isMetric()) {
        // Convert inches to mm.
        precipVal = precipVal * 25.4;
      } else if (!toMetric && dataset.isMetric()) {
        // Convert mm to inches.
        precipVal = precipVal * 0.0393700787;
      }
      precip.setText(Double.toString(round(precipVal, 2)));
      precips.addContent(precip);
    }
    input.addContent(precips);

    Element airtemps = new Element("airtemps");
    for (int i = 0; i < months.length; i++) {
      Element airtemp = new Element("airtemp");
      airtemp.setAttribute("id", months[i]);
      Double airtempVal = dataset.getTemperature().get(i);
      if (toMetric && !dataset.isMetric()) {
        // Convert F to C.
        airtempVal = (airtempVal - 32) * 5.0 / 9.0;
      } else if (!toMetric && dataset.isMetric()) {
        // Convert C to F.
        airtempVal = (airtempVal * 9.0 / 5.0) + 32;
      }
      airtemp.setText(Double.toString(round(airtempVal, 2)));
      airtemps.addContent(airtemp);
    }
    input.addContent(airtemps);

    Element smcsawc = new Element("smcsawc");
    smcsawc.setText(Double.toString(results.getWaterHoldingCapacity()));
    input.addContent(smcsawc);

    Element soilairrel = new Element("soilairrel");
    //Element lag = new Element("lag");
    //lag.setText("18");

    Element ampltd = new Element("ampltd");
    ampltd.setText(Double.toString(dataset.getMetadata().getAmplitude()));

    Element maatmast = new Element("maatmast");
    double maatmastVal = dataset.getMetadata().getSoilAirOffset();
    if (toMetric && !dataset.isMetric()) {
      // Convert F to C.
      maatmastVal *= 5.0 / 9.0;
    } else if (!toMetric && dataset.isMetric()) {
      // Convert C to F.
      maatmastVal *= 9.0 / 5.0;
    }
    maatmast.setText(Double.toString(round(maatmastVal, 2)));

    //soilairrel.addContent(lag);
    soilairrel.addContent(ampltd);
    soilairrel.addContent(maatmast);
    input.addContent(soilairrel);

    /**
     * Export Output data.  Results object is always metric, so it is converted accordingly.
     *
     **/
    Element output = new Element("output");

    Element smrclass = new Element("smrclass");
    Element strclass = new Element("strclass");
    Element subdiv = new Element("subgrpmod");
    Element awb = new Element("awb");
    Element swb = new Element("swb");
    Element smcstates = new Element("smcstates");
    Element pets = new Element("pets");
    Element soiltemps = new Element("soiltemps");
    Element calendars = new Element("calendars");

    smrclass.setText(results.getMoistureRegime());
    strclass.setText(results.getTemperatureRegime());
    subdiv.setText(results.getRegimeSubdivision1() + " " + results.getRegimeSubdivision2());

    awb.setText(Double.toString(round(results.getAnnualWaterBalance(), 2)));
    swb.setText(Double.toString(round(results.getSummerWaterBalance(), 2)));

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
      Double petVal = results.getMeanPotentialEvapotranspiration().get(i);
      if (!toMetric) {
        // Convert mm to inches.  PETs in Results object are always in metric.
        petVal = petVal * 0.0393700787;
      }
      pet.setText(Double.toString(round(petVal, 2)));
      pets.addContent(pet);
    }

    List<Double> soilTempValues = results.getSoilTempAverages();
    for (int i = 0; i < months.length; i++) {
      Element soiltemp = new Element("soiltemp");
      soiltemp.setAttribute("id", months[i]);
      Double soiltempVal = soilTempValues.get(i);
      if (!toMetric) {
        // Results object always in metric.  Convert C to F.
        soiltempVal = (soiltempVal * 9.0 / 5.0) + 32;
      }
      soiltemp.setText(Double.toString(round(soiltempVal + dataset.getMetadata().getSoilAirOffset(), 2)));
      soiltemps.addContent(soiltemp);
    }

    Element tempCalElement = new Element("tempcal");
    List<Character> tempCal = results.getTemperatureCalendar();
    char lastChar = tempCal.get(0);
    int lastPos = 0;
    for (int i = 1; i < tempCal.size(); i++) {
      char thisChar = tempCal.get(i);
      if (thisChar == lastChar && i != tempCal.size() - 1) {
        continue;
      } else {
        Element blockToAdd = null;

        switch (lastChar) {
          case '-':
            blockToAdd = new Element("stlt5");
            break;
          case '5':
            blockToAdd = new Element("st5to8");
            break;
          case '8':
            blockToAdd = new Element("stgt8");
            break;
          default:
            blockToAdd = new Element("unknown");
            break;
        }

        Element beginday = new Element("beginday");
        Element endday = new Element("endday");
        beginday.setText(Integer.toString(lastPos + 1));
        endday.setText(Integer.toString(i));

        // Edge case at the end of the calendar.
        if (i == tempCal.size() - 1) {
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
    for (int i = 1; i < moistCal.size(); i++) {
      int thisVal = moistCal.get(i);
      if (thisVal == lastVal && i != moistCal.size() - 1) {
        continue;
      } else {
        Element blockToAdd = null;

        switch (lastVal) {
          case 1:
            blockToAdd = new Element("dry");
            break;
          case 2:
            blockToAdd = new Element("moistdry");
            break;
          case 3:
            blockToAdd = new Element("moist");
            break;
          default:
            blockToAdd = new Element("unknown");
            break;
        }

        Element beginday = new Element("beginday");
        Element endday = new Element("endday");
        beginday.setText(Integer.toString(lastPos + 1));
        endday.setText(Integer.toString(i));

        // Edge case at the end of the calendar.
        if (i == moistCal.size() - 1) {
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
    output.addContent(subdiv);
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
    ByteArrayOutputStream baos = new ByteArrayOutputStream();
    try {
      outputter.output(doc, baos);
    } catch (IOException ex) {
      return "IOException encountered: " + ex.toString();
    }
    return baos.toString();

  }

  private static double round(double d, int decimalPlaces) {
    String format = "#.#";
    for (int i = decimalPlaces - 1; i > 0; i--) {
      format += "#";
    }
    DecimalFormat df = new DecimalFormat(format);
    return Double.valueOf(df.format(d));
  }
}
