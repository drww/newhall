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

    model.addContent(input);

    /** Extract model output data. **/


    XMLOutputter outputter = new XMLOutputter();
    outputter.setFormat(Format.getPrettyFormat());
    FileWriter writer = new FileWriter(outputFile);
    outputter.output(doc, writer);
    writer.close();

  }

}
