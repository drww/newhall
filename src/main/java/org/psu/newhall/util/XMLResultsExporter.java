package org.psu.newhall.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import org.jdom.Document;
import org.jdom.Element;
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
    doc = doc.setRootElement(model);

    /** Extract Metadata, build empty set if null. **/


    /** Extract dataset input data. **/



    /** Extract model output data. **/


    XMLOutputter outputter = new XMLOutputter();
    FileWriter writer = new FileWriter(outputFile);
    outputter.output(doc, writer);
    writer.close();

  }

}
