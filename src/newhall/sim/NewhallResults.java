package newhall.sim;

import java.util.ArrayList;
import java.util.List;

public class NewhallResults {

  private double annualRainfall;
  private double waterHoldingCapacity;
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
  private int numConsecutiveDaysMoistInSomePartsOver5C;
  private ArrayList<Integer> temperatureCalendar;
  private ArrayList<Integer> moistureCalendar;
  private ArrayList<Integer> nsd;
  private ArrayList<Integer> ncpm;
  private String temperatureRegime;
  private String moistureRegime;

  public NewhallResults(double arf, double whc, double[] mpe, int nccd, int nccm, int[] ntd, int[] iday,
          int[] nd, int[] nsd, int[] ncpm, String trr, String ans) {

    this.annualRainfall = arf;
    this.waterHoldingCapacity = whc;
    this.dryDaysAfterSummerSolstice = nccd;
    this.moistDaysAfterWinterSolstice = nccm;
    this.temperatureRegime = new String(trr);
    this.moistureRegime = new String(ans);

    this.meanPotentialEvapotranspiration = new ArrayList<Double>(12);
    for (int i = 1; i <= this.meanPotentialEvapotranspiration.size(); i++) {
      this.meanPotentialEvapotranspiration.add(i - 1, mpe[i]);
    }

    this.temperatureCalendar = new ArrayList<Integer>(360);
    for (int i = 1; i <= this.temperatureCalendar.size(); i++) {
      this.temperatureCalendar.add(i - 1, ntd[i]);
    }

    this.moistureCalendar = new ArrayList<Integer>(360);
    for (int i = 1; i <= this.moistureCalendar.size(); i++) {
      this.moistureCalendar.add(i - 1, iday[i]);
    }

    this.numCumulativeDaysDry = nd[1];
    this.numCumulativeDaysMoistDry = nd[2];
    this.numCumulativeDaysMoist = nd[3];

    this.numCumulativeDaysDryOver5C = nsd[1];
    this.numCumulativeDaysMoistDryOver5C = nsd[2];
    this.numCumulativeDaysMoistOver5C = nsd[3];

    this.numConsecutiveDaysMoistInSomeParts = ncpm[1];
    this.numConsecutiveDaysMoistInSomePartsOver5C = ncpm[2];

  }

  public double getAnnualRainfall() {
    return annualRainfall;
  }

  public int getDryDaysAfterSummerSolstice() {
    return dryDaysAfterSummerSolstice;
  }

  public List<Double> getMeanPotentialEvapotranspiration() {
    return meanPotentialEvapotranspiration;
  }

  public int getMoistDaysAfterWinterSolstice() {
    return moistDaysAfterWinterSolstice;
  }

  public ArrayList<Integer> getMoistureCalendar() {
    return moistureCalendar;
  }

  public String getMoistureRegime() {
    return moistureRegime;
  }

  public ArrayList<Integer> getNcpm() {
    return ncpm;
  }

  public ArrayList<Integer> getNsd() {
    return nsd;
  }

  public int getNumConsecutiveDaysMoistInSomeParts() {
    return numConsecutiveDaysMoistInSomeParts;
  }

  public int getNumConsecutiveDaysMoistInSomePartsOver5C() {
    return numConsecutiveDaysMoistInSomePartsOver5C;
  }

  public int getNumCumulativeDaysDry() {
    return numCumulativeDaysDry;
  }

  public int getNumCumulativeDaysDryOver5C() {
    return numCumulativeDaysDryOver5C;
  }

  public int getNumCumulativeDaysMoist() {
    return numCumulativeDaysMoist;
  }

  public int getNumCumulativeDaysMoistDry() {
    return numCumulativeDaysMoistDry;
  }

  public int getNumCumulativeDaysMoistDryOver5C() {
    return numCumulativeDaysMoistDryOver5C;
  }

  public int getNumCumulativeDaysMoistOver5C() {
    return numCumulativeDaysMoistOver5C;
  }

  public ArrayList<Integer> getTemperatureCalendar() {
    return temperatureCalendar;
  }

  public String getTemperatureRegime() {
    return temperatureRegime;
  }

  public double getWaterHoldingCapacity() {
    return waterHoldingCapacity;
  }
}
