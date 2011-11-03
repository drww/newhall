package org.psu.newhall.flerry;

import java.io.IOException;
import org.jdom.JDOMException;
import org.psu.newhall.sim.BASICSimulationModel;
import org.psu.newhall.sim.NewhallDataset;
import org.psu.newhall.sim.NewhallResults;
import org.psu.newhall.util.XMLStringParser;
import org.psu.newhall.util.XMLStringResultsExporter;

public class NewhallFlerryConnector {

  public static String runModel(String inputXmlFile, Boolean isMetric, Double waterHoldingCapacity, Double soilAirRel) {
    return runModelProper(inputXmlFile, isMetric.booleanValue(), waterHoldingCapacity.doubleValue(), soilAirRel.doubleValue());
  }

  public static String runModel(String inputXmlFile, Boolean isMetric, Integer waterHoldingCapacity, Integer soilAirRel) {
    return runModelProper(inputXmlFile, isMetric.booleanValue(), waterHoldingCapacity.doubleValue(), soilAirRel.doubleValue());
  }

  public static String runModel(String inputXmlFile, Boolean isMetric, Double waterHoldingCapacity, Integer soilAirRel) {
    return runModelProper(inputXmlFile, isMetric.booleanValue(), waterHoldingCapacity.doubleValue(), soilAirRel.doubleValue());
  }

  public static String runModel(String inputXmlFile, Boolean isMetric, Integer waterHoldingCapacity, Double soilAirRel) {
    return runModelProper(inputXmlFile, isMetric.booleanValue(), waterHoldingCapacity.doubleValue(), soilAirRel.doubleValue());
  }

  private static String runModelProper(String inputXmlFile, boolean isMetric, double waterHoldingCapacity, double soilAirRel) {

    XMLStringParser xsp = null;
    NewhallDataset dataset = null;

    try {
      // Resulting dataset will only have parameter WHC and soil-air if the values are null in the XML string.
      xsp = new XMLStringParser(inputXmlFile, waterHoldingCapacity, soilAirRel);
      dataset = xsp.getDataset();
    } catch (JDOMException ex) {
      return "JDOMException encountered: " + ex.getMessage();
    } catch (IOException ex) {
      return "IOException encountered: " + ex.getMessage();
    }

    NewhallResults results = BASICSimulationModel.runSimulation(dataset, dataset.getWaterholdingCapacity(),
            dataset.getMetadata().getSoilAirOffset(), dataset.getMetadata().getAmplitude());

    return XMLStringResultsExporter.export(results, dataset);

  }
}
