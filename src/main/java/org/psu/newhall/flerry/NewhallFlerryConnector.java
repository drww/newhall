package org.psu.newhall.flerry;

import java.io.IOException;
import org.jdom.JDOMException;
import org.psu.newhall.sim.BASICSimulationModel;
import org.psu.newhall.sim.NewhallDataset;
import org.psu.newhall.sim.NewhallResults;
import org.psu.newhall.util.XMLStringParser;
import org.psu.newhall.util.XMLStringResultsExporter;

public class NewhallFlerryConnector {

  public static String runModel(String inputXmlFile, Boolean isMetric, Number waterHoldingCapacity, Number soilAirRel) {

    XMLStringParser xsp = null;

    try {
      xsp = new XMLStringParser(inputXmlFile);
    } catch (JDOMException ex) {
      return "JDOMException encountered: " + ex.getMessage();
    } catch (IOException ex) {
      return "IOException encountered: " + ex.getMessage();
    }

    NewhallDataset dataset = xsp.getDataset();
    dataset.getMetadata().setSoilAirOffset(soilAirRel.doubleValue());
    NewhallResults results = BASICSimulationModel.runSimulation(dataset, waterHoldingCapacity.doubleValue());

    return XMLStringResultsExporter.export(results, dataset);
    
  }

}
