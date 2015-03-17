# The Flerry Connector Class #

Newhall can be used as an API in any system that can work with Java classes. With this, you
can make single invocations of the model by providing an XML file as a String, and being returned a
String XML file result. This is done through the `org.psu.newhall.flerry.NewhallFlerryConnector` class, by calling the `runModel()` function. This connector is mostly meant for integration with Flex via Flerry, but may be used for other purposes. Simply import the
entirety of the Newhall JAR as a library.

# Example #

```
// The following is an invocation made in Java.

// This is the entirety of an XML input file as a single String.
// Make sure special characters are properly encoded.

String inputFile = "<?xml version=\"1.0\" enc ... </model>";

// Specify that we want the results in metric units.
// Acceptable types: boolean, Boolean

boolean exportToMetric = true;

// Define waterholding capacity.
// Acceptable types: double, Double, Integer

double waterHoldingCapcityInMM = 200.0;

// Define soil-air offset in degrees C.
// Acceptable types: double, Double, Integer

double soilAirOffsetInDegrees = 3.0;

// Call the model through the connector, and store the result as an XML file
// in the form of a String, much like the input xmlFile String.

String resultingXMLFile = NewhallFlerryConnector.runModel(
  inputFile, exportToMetric, waterHoldingCapacityInMM,
  soilAirOffsetInDegrees);
```