# Unit System Conventions #

**The XML file format uses the following conventions for measurement units:
  * All values are in Metric.
  * Temperature values are in Celsius.
  * Precipitation is in millimeters.
  * Evapotranspiration is also in millimeters.
  * Latitude and longitude are decimal degrees to an arbitrary amount of figures.
    * DD format infers that latitude and longitude are specified in terms of North and East offsets. Southern Hemisphere locations should have a negative latitude, for example.
  * Soil-air temperature offsets are in Celsius.
  * Water balance are in millimeters.
  * Months are 30 days long. The year is 360 days long.
  * While a “srcunitsys” is specified, this only describes what format the data was originally sourced from. That is, whether or not the measurements were recorded in English or Metric units. It does not affect the model.**

# File Format Structure #

The XML file format consists of a root `<model>` tag that contains three children tags:
`<metadata>`, `<input>`, and `<output>`. The `<metadata>` tag is entirely optional, but the `<input>` tag is
required to run a model. The `<output>` tag is generated when exporting the model results to XML. An
example file is detailed below.

(The `<notes>` tag may have several or no `<note>` child tags. All other tags may not duplicate
children tags. Newhall will only read the first set of children tags if this is the case, but will read all
`<note>` tags.)

# Example File #

```
<?xml version="1.0" encoding="UTF-8"?>
<model>
  <metadata>
    <stninfo>
      <nettype />
      <stnname>Renovo</stnname>
      <stnid>PAXXX</stnid>
      <stnelev>201.0</stnelev>
      <stateprov>PA</stateprov>
      <country>USA</country>
    </stninfo>
    <mlra>
      <mlraname>Eastern Allegheny Plateau and Mountains</mlraname>
      <mlraid>127</mlraid>
    </mlra>
    <cntinfo>
      <cntper>
        <firstname>Guy</firstname>
        <lastname>Dudington</lastname>
        <title>Assistant Director</title>
      </cntper>
      <cntorg>CEI, Penn State</cntorg>
      <cntaddr>
        <address>2217 EES Bldg</address>
        <city>University Park</city>
        <stateprov>PA</stateprov>
        <postal>16802</postal>
        <country>USA</country>
      </cntaddr>
      <cntemail>gdudington@nowhere.org</cntemail>
      <cntphone>555-555-5555</cntphone>
    </cntinfo>
    <notes>
      <note>This is a sample NSM file.</note>
      <note>This is another note in the file.</note>
    </notes>
    <rundate>20110315</rundate>
    <nsmver>1.4.1</nsmver>
    <srcunitsys />
  </metadata>
  <input>
    <location>
      <lat>41.3166</lat>
      <lon>-77.7166</lon>
      <usercoordfmt>DD</usercoordfmt>
    </location>
    <recordpd>
      <pdtype>normal</pdtype>
      <pdbegin>1971</pdbegin>
      <pdend>2000</pdend>
    </recordpd>
    <precips>
      <precip id="Jan">59.2</precip>
      <precip id="Feb">61.7</precip>
      <precip id="Mar">79.8</precip>
      <precip id="Apr">83.3</precip>
      <precip id="May">84.8</precip>
      <precip id="Jun">124.0</precip>
      <precip id="Jul">91.2</precip>
      <precip id="Aug">87.1</precip>
      <precip id="Sep">95.8</precip>
      <precip id="Oct">73.7</precip>
      <precip id="Nov">88.6</precip>
      <precip id="Dec">88.6</precip>
    </precips>
    <airtemps>
      <airtemp id="Jan">-3.3</airtemp>
      <airtemp id="Feb">-2.0</airtemp>
      <airtemp id="Mar">3.1</airtemp>
      <airtemp id="Apr">9.2</airtemp>
      <airtemp id="May">15.1</airtemp>
      <airtemp id="Jun">19.5</airtemp>
      <airtemp id="Jul">21.8</airtemp>
      <airtemp id="Aug">21.0</airtemp>
      <airtemp id="Sep">16.8</airtemp>
      <airtemp id="Oct">10.7</airtemp>
      <airtemp id="Nov">5.0</airtemp>
      <airtemp id="Dec">-0.3</airtemp>
    </airtemps>
    <smcsawc>200.0</smcsawc>
    <soilairrel>
      <ampltd>0.66</ampltd>
      <maatmast>1.2</maatmast>
    </soilairrel>
  </input>
  <output>
    <smrclass>Udic</smrclass>
    <strclass>Mesic</strclass>
    <subgrpmod />
    <awb>362.7</awb>
    <swb>-73.53</swb>
    <smcstates>
      <cumdays>
        <yrdry>0</yrdry>
        <yrmd>0</yrmd>
        <yrmst>360</yrmst>
        <bio5dry>0</bio5dry>
        <bio5md>0</bio5md>
        <bio5mst>221</bio5mst>
      </cumdays>
      <consdays>
        <yrmst>360</yrmst>
        <bio8mst>201</bio8mst>
        <smrdry>0</smrdry>
        <wtrmst>120</wtrmst>
      </consdays>
    </smcstates>
    <pets>
      <pet id="Jan">0.0</pet>
      <pet id="Feb">0.0</pet>
      <pet id="Mar">10.63</pet>
      <pet id="Apr">42.23</pet>
      <pet id="May">86.16</pet>
      <pet id="Jun">118.02</pet>
      <pet id="Jul">135.98</pet>
      <pet id="Aug">121.83</pet>
      <pet id="Sep">81.47</pet>
      <pet id="Oct">43.78</pet>
      <pet id="Nov">15.01</pet>
      <pet id="Dec">0.0</pet>
    </pets>
    <calendars>
      <tempcal>
        <stlt5>
          <beginday>1</beginday>
          <endday>104</endday>
        </stlt5>
        <st5to8>
          <beginday>105</beginday>
          <endday>113</endday>
        </st5to8>
        <stgt8>
          <beginday>114</beginday>
          <endday>314</endday>
        </stgt8>
        <st5to8>
          <beginday>315</beginday>
          <endday>325</endday>
        </st5to8>
        <stlt5>
          <beginday>326</beginday>
          <endday>360</endday>
        </stlt5>
      </tempcal>
      <moistcal>
        <moist>
          <beginday>1</beginday>
          <endday>360</endday>
        </moist>
      </moistcal>
    </calendars>
  </output>
</model>
```