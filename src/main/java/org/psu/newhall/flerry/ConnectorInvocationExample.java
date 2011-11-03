package org.psu.newhall.flerry;

public class ConnectorInvocationExample {

  //public static void main(String[] args) {
  //  example();
  //}
  
  public static void example() {

    /**
     * Input into the connector is a simple string.  An example of such is listed below.
     * Note that special characters are escaped as to fit in-line.  The XML file represented
     * printed to the console would look properly indented and such. 
     **/

    String inputFile = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n<model version=\"1.0.0\">\n  <metadata>\n    <stninfo>\n      <stnname>Renovo</stnname>\n      <stnid>PAXXX</stnid>\n      <stnelev>201</stnelev>\n      <stateprov>PA</stateprov>\n      <country>USA</country>\n    </stninfo>\n    <mlra>\n      <mlraname>Eastern Allegheny Plateau and Mountains</mlraname>\n      <mlraid>127</mlraid>\n    </mlra>\n    <cntinfo>\n      <cntper>\n        <firstname>Brian</firstname>\n        <lastname>Bills</lastname>\n        <title>Assistant Director</title>\n      </cntper>\n      <cntorg>CEI, Penn State</cntorg>\n      <cntaddr>\n        <address>2217 EES Bldg</address>\n        <city>University Park</city>\n        <stateprov>PA</stateprov>\n        <postal>16802</postal>\n        <country>USA</country>\n      </cntaddr>\n      <cntemail>bbills@eesi.psu.edu</cntemail>\n      <cntphone>814-865-5745</cntphone>\n    </cntinfo>\n    <notes>\n      <note>This is a sample NSM file.</note>\n      <note>This is another note in the file.</note>\n    </notes>\n    <rundate>20100412</rundate>\n    <nsmver>1.0.0</nsmver>\n    <unitsys>metric</unitsys>\n  </metadata>\n  <input>\n    <location>\n      <lat>41.3166</lat>\n      <lon>-77.7166</lon>\n      <usercoordfmt>DMS</usercoordfmt>\n    </location>\n    <recordpd>\n      <pdtype>normal</pdtype>\n      <pdbegin>1971</pdbegin>\n      <pdend>2000</pdend>\n    </recordpd>\n    <precips>\n      <precip id=\"Jan\">59.2</precip>\n      <precip id=\"Feb\">61.7</precip>\n      <precip id=\"Mar\">79.8</precip>\n      <precip id=\"Apr\">83.3</precip>\n      <precip id=\"May\">84.8</precip>\n      <precip id=\"Jun\">124.0</precip>\n      <precip id=\"Jul\">91.2</precip>\n      <precip id=\"Aug\">87.1</precip>\n      <precip id=\"Sep\">95.8</precip>\n      <precip id=\"Oct\">73.7</precip>\n      <precip id=\"Nov\">88.6</precip>\n      <precip id=\"Dec\">88.6</precip>\n    </precips>\n    <airtemps>\n      <airtemp id=\"Jan\">-3.3</airtemp>\n      <airtemp id=\"Feb\">-2.0</airtemp>\n      <airtemp id=\"Mar\">3.1</airtemp>\n      <airtemp id=\"Apr\">9.2</airtemp>\n      <airtemp id=\"May\">15.1</airtemp>\n      <airtemp id=\"Jun\">19.5</airtemp>\n      <airtemp id=\"Jul\">21.8</airtemp>\n      <airtemp id=\"Aug\">21.0</airtemp>\n      <airtemp id=\"Sep\">16.8</airtemp>\n      <airtemp id=\"Oct\">10.7</airtemp>\n      <airtemp id=\"Nov\">5.0</airtemp>\n      <airtemp id=\"Dec\">-0.3</airtemp>\n    </airtemps>\n    <smcsawc></smcsawc>\n    <soilairrel>\n      <lag>18</lag>\n      <ampltd>0.66</ampltd>\n      <maatmast></maatmast>\n    </soilairrel>\n  </input>\n  </model>";
    String inputFile2 = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><model>  <metadata>    <stninfo>      <nettype>Unknown</nettype>      <stnname>BRADFORD AIRPORT</stnname>      <stnid />      <stnelev>2117.0</stnelev>      <stateprov />      <country>USA</country>    </stninfo>    <mlra>      <mlraname />      <mlraid>-1</mlraid>    </mlra>    <cntinfo>      <cntper>        <firstname />        <lastname />        <title />      </cntper>      <cntorg />      <cntaddr>        <address />        <city />        <stateprov />        <postal />        <country />      </cntaddr>      <cntemail />      <cntphone />    </cntinfo>    <notes>      <note>Results generated from legacy Newhall input format.</note>    </notes>    <rundate>20111103</rundate>    <nsmver>1.5.0</nsmver>    <srcunitsys>English</srcunitsys>  </metadata>  <input>    <location>      <lat>41.8</lat>      <lon>-78.6333</lon>      <usercoordfmt>DD</usercoordfmt>    </location>    <recordpd>      <pdtype>normal</pdtype>      <pdbegin>1995</pdbegin>      <pdend>1995</pdend>    </recordpd>    <precips>      <precip id=\"Jan\">91.69</precip>      <precip id=\"Feb\">73.66</precip>      <precip id=\"Mar\">96.27</precip>      <precip id=\"Apr\">88.9</precip>      <precip id=\"May\">100.08</precip>      <precip id=\"Jun\">140.46</precip>      <precip id=\"Jul\">105.16</precip>      <precip id=\"Aug\">101.09</precip>      <precip id=\"Sep\">106.93</precip>      <precip id=\"Oct\">81.79</precip>      <precip id=\"Nov\">96.27</precip>      <precip id=\"Dec\">100.08</precip>    </precips>    <airtemps>      <airtemp id=\"Jan\">-6.17</airtemp>      <airtemp id=\"Feb\">-4.83</airtemp>      <airtemp id=\"Mar\">0.22</airtemp>      <airtemp id=\"Apr\">6.39</airtemp>      <airtemp id=\"May\">12.11</airtemp>      <airtemp id=\"Jun\">16.61</airtemp>      <airtemp id=\"Jul\">18.94</airtemp>      <airtemp id=\"Aug\">18.11</airtemp>      <airtemp id=\"Sep\">14.17</airtemp>      <airtemp id=\"Oct\">8.33</airtemp>      <airtemp id=\"Nov\">2.61</airtemp>      <airtemp id=\"Dec\">-3.17</airtemp>    </airtemps>    <smcsawc>222.0</smcsawc>    <soilairrel>      <ampltd>0.66</ampltd>      <maatmast>22.2</maatmast>    </soilairrel>  </input>  <output>    <smrclass>Udic</smrclass>    <strclass>Mesic</strclass>    <subgrpmod>Typic Udic</subgrpmod>    <awb>612.5</awb>    <swb>11.34</swb>    <smcstates>      <cumdays>        <yrdry>0</yrdry>        <yrmd>0</yrmd>        <yrmst>360</yrmst>        <bio5dry>0</bio5dry>        <bio5md>0</bio5md>        <bio5mst>194</bio5mst>      </cumdays>      <consdays>        <yrmst>360</yrmst>        <bio8mst>174</bio8mst>        <smrdry>0</smrdry>        <wtrmst>120</wtrmst>      </consdays>    </smcstates>    <pets>      <pet id=\"Jan\">0.0</pet>      <pet id=\"Feb\">0.0</pet>      <pet id=\"Mar\">1.01</pet>      <pet id=\"Apr\">34.68</pet>      <pet id=\"May\">75.43</pet>      <pet id=\"Jun\">105.25</pet>      <pet id=\"Jul\">121.46</pet>      <pet id=\"Aug\">108.66</pet>      <pet id=\"Sep\">73.75</pet>      <pet id=\"Oct\">39.43</pet>      <pet id=\"Nov\">10.2</pet>      <pet id=\"Dec\">0.0</pet>    </pets>    <calendars>      <tempcal>        <stlt5>          <beginday>1</beginday>          <endday>118</endday>        </stlt5>        <st5to8>          <beginday>119</beginday>          <endday>127</endday>        </st5to8>        <stgt8>          <beginday>128</beginday>          <endday>301</endday>        </stgt8>        <st5to8>          <beginday>302</beginday>          <endday>312</endday>        </st5to8>        <stlt5>          <beginday>313</beginday>          <endday>360</endday>        </stlt5>      </tempcal>      <moistcal>        <moist>          <beginday>1</beginday>          <endday>360</endday>        </moist>      </moistcal>    </calendars>  </output></model>";

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
