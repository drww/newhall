package org.psu.newhall.flerry;

public class ConnectorInvocationExample {

  /**public static void main(String[] args) {
    example();
  }**/
  
  public static void example() {

    /**
     * Input into the connector is a simple string.  An example of such is listed below.
     * Note that special characters are escaped as to fit in-line.  The XML file represented
     * printed to the console would look properly indented and such. 
     **/

    String inputFile = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<model version=\"1.0.0\">\n  <metadata>\n    <stninfo>\n      <stnname>Renovo</stnname>\n      <stnid>PAXXX</stnid>\n      <stnelev>201</stnelev>\n      <stateprov>PA</stateprov>\n      <country>USA</country>\n    </stninfo>\n    <mlra>\n      <mlraname>Eastern Allegheny Plateau and Mountains</mlraname>\n      <mlraid>127</mlraid>\n    </mlra>\n    <cntinfo>\n      <cntper>\n        <firstname>Brian</firstname>\n        <lastname>Bills</lastname>\n        <title>Assistant Director</title>\n      </cntper>\n      <cntorg>CEI, Penn State</cntorg>\n      <cntaddr>\n        <address>2217 EES Bldg</address>\n        <city>University Park</city>\n        <stateprov>PA</stateprov>\n        <postal>16802</postal>\n        <country>USA</country>\n      </cntaddr>\n      <cntemail>bbills@eesi.psu.edu</cntemail>\n      <cntphone>814-865-5745</cntphone>\n    </cntinfo>\n    <notes>\n      <note>This is a sample NSM file.</note>\n      <note>This is another note in the file.</note>\n    </notes>\n    <rundate>20100412</rundate>\n    <nsmver>1.0.0</nsmver>\n    <unitsys>metric</unitsys>\n  </metadata>\n  <input>\n    <location>\n      <lat>41.3166</lat>\n      <lon>-77.7166</lon>\n      <usercoordfmt>DMS</usercoordfmt>\n    </location>\n    <recordpd>\n      <pdtype>normal</pdtype>\n      <pdbegin>1971</pdbegin>\n      <pdend>2000</pdend>\n    </recordpd>\n    <precips>\n      <precip id=\"Jan\">59.2</precip>\n      <precip id=\"Feb\">61.7</precip>\n      <precip id=\"Mar\">79.8</precip>\n      <precip id=\"Apr\">83.3</precip>\n      <precip id=\"May\">84.8</precip>\n      <precip id=\"Jun\">124.0</precip>\n      <precip id=\"Jul\">91.2</precip>\n      <precip id=\"Aug\">87.1</precip>\n      <precip id=\"Sep\">95.8</precip>\n      <precip id=\"Oct\">73.7</precip>\n      <precip id=\"Nov\">88.6</precip>\n      <precip id=\"Dec\">88.6</precip>\n    </precips>\n    <airtemps>\n      <airtemp id=\"Jan\">-3.3</airtemp>\n      <airtemp id=\"Feb\">-2.0</airtemp>\n      <airtemp id=\"Mar\">3.1</airtemp>\n      <airtemp id=\"Apr\">9.2</airtemp>\n      <airtemp id=\"May\">15.1</airtemp>\n      <airtemp id=\"Jun\">19.5</airtemp>\n      <airtemp id=\"Jul\">21.8</airtemp>\n      <airtemp id=\"Aug\">21.0</airtemp>\n      <airtemp id=\"Sep\">16.8</airtemp>\n      <airtemp id=\"Oct\">10.7</airtemp>\n      <airtemp id=\"Nov\">5.0</airtemp>\n      <airtemp id=\"Dec\">-0.3</airtemp>\n    </airtemps>\n    <smcsawc></smcsawc>\n    <soilairrel>\n      <lag>18</lag>\n      <ampltd>0.66</ampltd>\n      <maatmast></maatmast>\n    </soilairrel>\n  </input>\n  </model>";

    boolean exportToMetric = true;
    Integer waterHoldingCapacityInMM = 201;
    Integer soilAirOffsetInDegrees = 3;

    System.out.println("NewhallFlerryConnector.runModel(inputFile, " + exportToMetric + ", " + waterHoldingCapacityInMM + ", " +
            soilAirOffsetInDegrees + ")");

    /**
     * The connector class provides a function to invoke the model with.  It has four parameters.
     *
     *   String inputFile - The input XML file represented as a string.
     *   boolean exportToMetric - If true, the results are put into terms of metric units.  If false, english units.
     *   double waterHoldingCapacityInMM - The waterholding capacity parameter to the model in mm.
     *   double soilAirOffsetInDegrees - The soil temperature offset from air temperature in raw degrees.  If metric
     *                                     is selected, these are degrees C, otherwise F.
     */

    String resultingXMLFile = NewhallFlerryConnector.runModel(inputFile, exportToMetric, waterHoldingCapacityInMM, soilAirOffsetInDegrees);

    /**
     * Much like the inputFile, the result of calling the model is the XML file in the representation of a String,
     * complete with newlines and indentation.
     */

    System.out.println("Resulting XML File:\n" + resultingXMLFile);

  }

}
