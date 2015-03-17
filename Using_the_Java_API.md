# Overview #

You can import Newhall as a library JAR and integrate it into other Java applications, given them the ability to natively run the Newhall model.  There are only three primary classes to be concerned with:

  * org.psu.newhall.sim.NewhallDataset
  * org.psu.newhall.sim.NewhallResults
  * org.psu.newhall.sim.BASICSimulationModel

One first assembles a dataset to feed into the model.  This is done by instantiating NewhallDataset and filling out its constructor completely.  Any modern IDE should give you sufficient hints for filling it out, but here is an overview of the constructor:

```
NewhallDataset(String name, String country, double latitude, double longitude, 
  char nsHemisphere, char ewHemisphere, double elevation, List<Double> precipitation, 
  List<Double> temperature, int startYear, int endYear, boolean isMetric)
```

This object is fed into the model.  The model does not need to be instantiated, and is defined as a static class.  Simply state that you wish to run the model, and it will return a NewhallResults object.  You need to at least specify the waterholding capacity for the model to run in addition to the dataset.

```
NewhallResults results = BASICSimulationModel.runSimulation(dataset, waterHoldingCapacity);
```

The NewhallResults have a variety of interesting fields on it that summarize the results of the model.  You can get a nice summary of these things with the `toString()` function off of it.  The FLX file that the original BASIC model would then export is also tacked onto the results object.

```
  private double annualRainfall;
  private double waterHoldingCapacity;
  private double annualWaterBalance;
  private double summerWaterBalance;
  private List<Double> meanPotentialEvapotranspiration;
  private int dryDaysAfterSummerSolstice;
  private int moistDaysAfterWinterSolstice;
  private int numCumulativeDaysDry;
  private int numCumulativeDaysMoistDry;
  private int numCumulativeDaysMoist;
  private int numCumulativeDaysDryOver5C;
  private int numCumulativeDaysMoistDryOver5C;
  private int numCumulativeDaysMoistOver5C;
  private int numConsecutiveDaysMoistInSomeParts;
  private int numConsecutiveDaysMoistInSomePartsOver8C;
  private ArrayList<Character> temperatureCalendar;
  private ArrayList<Integer> moistureCalendar;
  private ArrayList<Double> soilTempCalendar;
  private ArrayList<Integer> nsd;
  private ArrayList<Integer> ncpm;
  private String temperatureRegime;
  private String moistureRegime;
  private String regimeSubdivision1;
  private String regimeSubdivision2;
  private String flxFile;
```

# Example #

```
// First, build the dataset.
NewhallDataset dataset = new NewhallDataset(...);

// Then invoke the model, and store the result.
NewhallResults results = BASICSimulationModel.runSimulation(dataset, 200.0);
```