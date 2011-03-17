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

    try {
      xsp = new XMLStringParser(inputXmlFile);
    } catch (JDOMException ex) {
      return "JDOMException encountered: " + ex.getMessage();
    } catch (IOException ex) {
      return "IOException encountered: " + ex.getMessage();
    }

    NewhallDataset dataset = xsp.getDataset();
    dataset.getMetadata().setSoilAirOffset(soilAirRel);
    NewhallResults results = BASICSimulationModel.runSimulation(dataset, waterHoldingCapacity);

    return XMLStringResultsExporter.export(results, dataset);

  }
}
