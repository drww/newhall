package org.psu.newhall.sim;

import java.util.ArrayList;
import static org.junit.Assert.*;
import org.junit.Test;

public class BASICSimulationModelTest {

  /**
   * Model accuracy: 99.96% *
   */
  @Test
  public void testSimulationColum97() {
    NewhallResults nr = BASICSimulationModel.runSimulation(makeColum97(), 200.0);

    assertEquals(nr.getMoistureRegime().toLowerCase(), "udic");
    assertEquals(nr.getTemperatureRegime().toLowerCase(), "mesic");
    assertEquals(nr.getAnnualRainfall(), 666.0, 1);

    assertEquals(nr.getNumCumulativeDaysDry(), 0);
    assertEquals(nr.getNumCumulativeDaysMoistDry(), 0);
    assertEquals(nr.getNumCumulativeDaysMoist(), 360);
    assertEquals(nr.getNumCumulativeDaysDryOver5C(), 0);
    assertEquals(nr.getNumCumulativeDaysMoistDryOver5C(), 0);

    // Tolerance for deviation: 1 day
    assertEquals(nr.getNumCumulativeDaysMoistOver5C(), 204, 1);

    assertEquals(nr.getNumConsecutiveDaysMoistInSomeParts(), 360);
    assertEquals(nr.getNumConsecutiveDaysMoistInSomePartsOver8C(), 181);
    assertEquals(nr.getDryDaysAfterSummerSolstice(), 0);
    assertEquals(nr.getMoistDaysAfterWinterSolstice(), 120);

    Double[] expectdEvapoTranspiration = {0.0, 0.0, 14.1, 26.1, 70.8, 121.9, 147.6, 126.6, 90.7, 43.3, 4.1, 0.0};
    for (int i = 0; i < 12; i++) {
      // Tolerance for deviation: 0.4 mm
      assertEquals(nr.getMeanPotentialEvapotranspiration().get(i), expectdEvapoTranspiration[i], 0.4);
    }
  }

  @Test
  public void testSimulationColum98() {
    NewhallResults nr = BASICSimulationModel.runSimulation(makeColum98(), 200.0);

    assertEquals(nr.getMoistureRegime().toLowerCase(), "udic");
    assertEquals(nr.getTemperatureRegime().toLowerCase(), "mesic");
    assertEquals(nr.getAnnualRainfall(), 743, 1);

    assertEquals(nr.getNumCumulativeDaysDry(), 0);
    assertEquals(nr.getNumCumulativeDaysMoistDry(), 24);
    assertEquals(nr.getNumCumulativeDaysMoist(), 336);
    assertEquals(nr.getNumCumulativeDaysDryOver5C(), 0);
    assertEquals(nr.getNumCumulativeDaysMoistDryOver5C(), 24);

    // Tolerance for deviation: 1 day
    assertEquals(nr.getNumCumulativeDaysMoistOver5C(), 188, 1);

    assertEquals(nr.getNumConsecutiveDaysMoistInSomeParts(), 360);
    assertEquals(nr.getNumConsecutiveDaysMoistInSomePartsOver8C(), 196);
    assertEquals(nr.getDryDaysAfterSummerSolstice(), 0);
    assertEquals(nr.getMoistDaysAfterWinterSolstice(), 120);

    Double[] expectdEvapoTranspiration = {0.0, 3.3, 0.0, 34.5, 89.9, 102.7, 155.1, 137.0, 105.4, 40.3, 13.3, 0.0};
    for (int i = 0; i < 12; i++) {
      // Tolerance for deviation: 0.4 mm
      assertEquals(nr.getMeanPotentialEvapotranspiration().get(i), expectdEvapoTranspiration[i], 0.4);
    }
  }

  @Test
  public void testSimulationMead89() {
    NewhallResults nr = BASICSimulationModel.runSimulation(makeMead89(), 200.0);

    assertEquals(nr.getMoistureRegime().toLowerCase(), "ustic");
    assertEquals(nr.getTemperatureRegime().toLowerCase(), "mesic");
    assertEquals(nr.getAnnualRainfall(), 506, 1);

    assertEquals(nr.getNumCumulativeDaysDry(), 49);
    assertEquals(nr.getNumCumulativeDaysMoistDry(), 311);
    assertEquals(nr.getNumCumulativeDaysMoist(), 0);
    assertEquals(nr.getNumCumulativeDaysDryOver5C(), 49);
    assertEquals(nr.getNumCumulativeDaysMoistDryOver5C(), 166);

    // Tolerance for deviation: 1 day
    assertEquals(nr.getNumCumulativeDaysMoistOver5C(), 0, 1);

    assertEquals(nr.getNumConsecutiveDaysMoistInSomeParts(), 297);
    assertEquals(nr.getNumConsecutiveDaysMoistInSomePartsOver8C(), 88);
    assertEquals(nr.getDryDaysAfterSummerSolstice(), 46);
    assertEquals(nr.getMoistDaysAfterWinterSolstice(), 0);

    Double[] expectdEvapoTranspiration = {0.0, 0.0, 7.4, 58.2, 96.6, 122.5, 154.7, 132.2, 80.4, 47.5, 4.4, 0.0};
    for (int i = 0; i < 12; i++) {
      // Tolerance for deviation: 0.4 mm
      assertEquals(nr.getMeanPotentialEvapotranspiration().get(i), expectdEvapoTranspiration[i], 0.4);
    }
  }

  @Test
  public void testSimulationAjo20080() {
    NewhallResults nr = BASICSimulationModel.runSimulation(makeAjo20080(), 200.0);

    assertEquals(nr.getMoistureRegime().toLowerCase(), "aridic");
    assertEquals(nr.getTemperatureRegime().toLowerCase(), "hyperthermic");
    assertEquals(nr.getAnnualRainfall(), 197, 1);

    assertEquals(nr.getNumCumulativeDaysDry(), 360);
    assertEquals(nr.getNumCumulativeDaysMoistDry(), 0);
    assertEquals(nr.getNumCumulativeDaysMoist(), 0);
    assertEquals(nr.getNumCumulativeDaysDryOver5C(), 360);
    assertEquals(nr.getNumCumulativeDaysMoistDryOver5C(), 0);

    // Tolerance for deviation: 1 day
    assertEquals(nr.getNumCumulativeDaysMoistOver5C(), 0, 1);

    assertEquals(nr.getNumConsecutiveDaysMoistInSomeParts(), 0);
    assertEquals(nr.getNumConsecutiveDaysMoistInSomePartsOver8C(), 0);
    assertEquals(nr.getDryDaysAfterSummerSolstice(), 120);
    assertEquals(nr.getMoistDaysAfterWinterSolstice(), 0);

    Double[] expectdEvapoTranspiration = {16.3, 23.8, 41.3, 74.4, 135.0, 192.9, 209.5, 193.2, 160.1, 100.9, 37.8, 16.3};
    for (int i = 0; i < 12; i++) {
      // Tolerance for deviation: 0.4 mm
      assertEquals(nr.getMeanPotentialEvapotranspiration().get(i), expectdEvapoTranspiration[i], 0.4);
    }
  }

  @Test
  public void testSimulationPendleton() {
    NewhallResults nr = BASICSimulationModel.runSimulation(makePendleton(), 200.0);

    assertEquals(nr.getMoistureRegime().toLowerCase(), "xeric");
    assertEquals(nr.getTemperatureRegime().toLowerCase(), "thermic");
    assertEquals(nr.getRegimeSubdivision1().toLowerCase(), "dry");
    assertEquals(nr.getRegimeSubdivision2().toLowerCase(), "xeric");
    assertEquals(nr.getAnnualRainfall(), 336, 1);

    assertEquals(nr.getNumCumulativeDaysDry(), 199);
    assertEquals(nr.getNumCumulativeDaysMoistDry(), 51);
    assertEquals(nr.getNumCumulativeDaysMoist(), 110);
    assertEquals(nr.getNumCumulativeDaysDryOver5C(), 199);
    assertEquals(nr.getNumCumulativeDaysMoistDryOver5C(), 51);

    // Tolerance for deviation: 1 day
    assertEquals(nr.getNumCumulativeDaysMoistOver5C(), 110, 1);

    assertEquals(nr.getNumConsecutiveDaysMoistInSomeParts(), 161);
    assertEquals(nr.getNumConsecutiveDaysMoistInSomePartsOver8C(), 161);
    assertEquals(nr.getDryDaysAfterSummerSolstice(), 120);
    assertEquals(nr.getMoistDaysAfterWinterSolstice(), 75);

    Double[] expectdEvapoTranspiration = {33.8, 35.3, 44.7, 56.9, 77.2, 93.1, 113.3, 113.6, 96.1, 70.8, 44.5, 33.2};
    for (int i = 0; i < 12; i++) {
      // Tolerance for deviation: 0.4 mm
      assertEquals(nr.getMeanPotentialEvapotranspiration().get(i), expectdEvapoTranspiration[i], 0.4);
    }
  }

  @Test
  public void testSimulationChadron() {
    NewhallResults nr = BASICSimulationModel.runSimulation(makeChadron(), 200.0);

    assertEquals(nr.getMoistureRegime().toLowerCase(), "aridic");
    assertEquals(nr.getTemperatureRegime().toLowerCase(), "mesic");
    assertEquals(nr.getRegimeSubdivision1().toLowerCase(), "weak");
    assertEquals(nr.getRegimeSubdivision2().toLowerCase(), "aridic");
    assertEquals(nr.getAnnualRainfall(), 422, 1);

    assertEquals(nr.getNumCumulativeDaysDry(), 153);
    assertEquals(nr.getNumCumulativeDaysMoistDry(), 124);
    assertEquals(nr.getNumCumulativeDaysMoist(), 83);
    assertEquals(nr.getNumCumulativeDaysDryOver5C(), 103);
    assertEquals(nr.getNumCumulativeDaysMoistDryOver5C(), 19);

    // Tolerance for deviation: 1 day
    assertEquals(nr.getNumCumulativeDaysMoistOver5C(), 79, 1);

    assertEquals(nr.getNumConsecutiveDaysMoistInSomeParts(), 207);
    assertEquals(nr.getNumConsecutiveDaysMoistInSomePartsOver8C(), 87);
    assertEquals(nr.getDryDaysAfterSummerSolstice(), 93);
    assertEquals(nr.getMoistDaysAfterWinterSolstice(), 15);

    Double[] expectdEvapoTranspiration = {0.0, 0.0, 7.8, 35.1, 78.5, 119.8, 149.4, 134.6, 80.2, 37.5, 2.1, 0.0};
    for (int i = 0; i < 12; i++) {
      // Tolerance for deviation: 0.4 mm
      assertEquals(nr.getMeanPotentialEvapotranspiration().get(i), expectdEvapoTranspiration[i], 0.4);
    }
  }

  @Test
  public void testSimulationPittsburgh1950() {
    NewhallResults nr = BASICSimulationModel.runSimulation(makePittsburgh1950(), 76.2);

    assertEquals(nr.getMoistureRegime().toLowerCase(), "udic");
    assertEquals(nr.getTemperatureRegime().toLowerCase(), "mesic");
    assertEquals(nr.getRegimeSubdivision1().toLowerCase(), "typic");
    assertEquals(nr.getRegimeSubdivision2().toLowerCase(), "udic");
    assertEquals(nr.getAnnualRainfall(), 1205, 1);

    assertEquals(nr.getNumCumulativeDaysDry(), 0);
    assertEquals(nr.getNumCumulativeDaysMoistDry(), 13);
    assertEquals(nr.getNumCumulativeDaysMoist(), 347);
    assertEquals(nr.getNumCumulativeDaysDryOver5C(), 0);
    assertEquals(nr.getNumCumulativeDaysMoistDryOver5C(), 13);

    // Tolerance for deviation: 1 day
    assertEquals(nr.getNumCumulativeDaysMoistOver5C(), 204, 1);

    assertEquals(nr.getNumConsecutiveDaysMoistInSomeParts(), 360);
    assertEquals(nr.getNumConsecutiveDaysMoistInSomePartsOver8C(), 204);
    assertEquals(nr.getDryDaysAfterSummerSolstice(), 0);
    assertEquals(nr.getMoistDaysAfterWinterSolstice(), 120);

    Double[] expectdEvapoTranspiration = {15.7, 2.19, 6.43, 33.63, 96.33, 126.65, 138.96, 127.38, 87.65, 59.89, 11.95, 0.0};
    for (int i = 0; i < 12; i++) {
      // Tolerance for deviation: 0.4 mm
      assertEquals(nr.getMeanPotentialEvapotranspiration().get(i), expectdEvapoTranspiration[i], 0.4);
    }
  }
  
  @Test
  public void testSimulationPiedmont1937() {
    NewhallResults nr = BASICSimulationModel.runSimulation(makePiedmont1937(), 154.9);

    assertEquals(nr.getMoistureRegime().toLowerCase(), "udic");
    assertEquals(nr.getTemperatureRegime().toLowerCase(), "thermic");
    assertEquals(nr.getRegimeSubdivision1().toLowerCase(), "typic");
    assertEquals(nr.getRegimeSubdivision2().toLowerCase(), "udic");
    assertEquals(nr.getAnnualRainfall(), 1332, 1);

    assertEquals(nr.getNumCumulativeDaysDry(), 0);
    assertEquals(nr.getNumCumulativeDaysMoistDry(), 0);
    assertEquals(nr.getNumCumulativeDaysMoist(), 360);
    assertEquals(nr.getNumCumulativeDaysDryOver5C(), 0);
    assertEquals(nr.getNumCumulativeDaysMoistDryOver5C(), 0);

    // Tolerance for deviation: 1 day
    assertEquals(nr.getNumCumulativeDaysMoistOver5C(), 246, 1);

    assertEquals(nr.getNumConsecutiveDaysMoistInSomeParts(), 360);
    assertEquals(nr.getNumConsecutiveDaysMoistInSomePartsOver8C(), 222);
    assertEquals(nr.getDryDaysAfterSummerSolstice(), 0);
    assertEquals(nr.getMoistDaysAfterWinterSolstice(), 120);

    Double[] expectdEvapoTranspiration = {11.71, 2.22, 16.13, 45.28, 98.76, 142.58, 153.86, 144.51, 83.11, 42.53, 15.72, 2.71};
    for (int i = 0; i < 12; i++) {
      // Tolerance for deviation: 0.4 mm
      assertEquals(nr.getMeanPotentialEvapotranspiration().get(i), expectdEvapoTranspiration[i], 0.4);
    }
  }

  /**
   * Dataset Builder Functions *
   */
  private NewhallDataset makeColum97() {

    ArrayList<Double> temps = new ArrayList<Double>(12);
    temps.add(-5.0);
    temps.add(-1.7);
    temps.add(3.9);
    temps.add(6.1);
    temps.add(12.8);
    temps.add(20.0);
    temps.add(23.3);
    temps.add(21.7);
    temps.add(18.3);
    temps.add(10.6);
    temps.add(1.7);
    temps.add(-1.7);

    ArrayList<Double> precip = new ArrayList<Double>(12);
    precip.add(7.6);
    precip.add(19.8);
    precip.add(4.1);
    precip.add(81.8);
    precip.add(86.9);
    precip.add(97.8);
    precip.add(116.6);
    precip.add(55.1);
    precip.add(103.6);
    precip.add(79.5);
    precip.add(3.9);
    precip.add(9.9);

    return new NewhallDataset("Columbus 3 NE", "USA", 41.4666, 97.3333, 'N', 'W', 441.96, precip, temps, 1997, 1997, true, 200.0);

  }

  private NewhallDataset makeColum98() {
    ArrayList<Double> temps = new ArrayList<Double>(12);
    temps.add(-2.8);
    temps.add(1.7);
    temps.add(-0.6);
    temps.add(8.3);
    temps.add(16.1);
    temps.add(17.8);
    temps.add(24.4);
    temps.add(23.3);
    temps.add(21.1);
    temps.add(10.6);
    temps.add(5.0);
    temps.add(-1.1);

    ArrayList<Double> precip = new ArrayList<Double>(12);
    precip.add(17.0);
    precip.add(11.2);
    precip.add(73.4);
    precip.add(90.7);
    precip.add(64.5);
    precip.add(183.6);
    precip.add(86.6);
    precip.add(84.3);
    precip.add(28.4);
    precip.add(68.1);
    precip.add(28.4);
    precip.add(6.4);

    return new NewhallDataset("Columbus 3 NE", "USA", 41.4666, 97.3333, 'N', 'W', 441.96, precip, temps, 1998, 1998, true, 200.0);
  }

  private NewhallDataset makeMead89() {
    ArrayList<Double> temps = new ArrayList<Double>(12);
    temps.add(0.0);
    temps.add(-9.4);
    temps.add(2.8);
    temps.add(12.8);
    temps.add(17.2);
    temps.add(20.6);
    temps.add(24.4);
    temps.add(22.8);
    temps.add(17.2);
    temps.add(12.2);
    temps.add(2.2);
    temps.add(-8.3);

    ArrayList<Double> precip = new ArrayList<Double>(12);
    precip.add(20.3);
    precip.add(11.7);
    precip.add(1.5);
    precip.add(25.1);
    precip.add(103.6);
    precip.add(105.2);
    precip.add(82.8);
    precip.add(41.1);
    precip.add(74.7);
    precip.add(21.6);
    precip.add(1.3);
    precip.add(16.8);

    return new NewhallDataset("Mead Agronomy Lab", "USA", 41.4666, 97.3333, 'N', 'W', 359.66, precip, temps, 1989, 1989, true, 200.0);
  }

  private NewhallDataset makeAjo20080() {
    ArrayList<Double> temps = new ArrayList<Double>(12);
    temps.add(12.5);
    temps.add(14.61);
    temps.add(16.78);
    temps.add(20.56);
    temps.add(24.78);
    temps.add(30.06);
    temps.add(32.17);
    temps.add(31.44);
    temps.add(29.33);
    temps.add(23.89);
    temps.add(17.22);
    temps.add(12.61);

    ArrayList<Double> precip = new ArrayList<Double>(12);
    precip.add(17.02);
    precip.add(17.02);
    precip.add(19.3);
    precip.add(5.84);
    precip.add(3.81);
    precip.add(1.27);
    precip.add(19.3);
    precip.add(41.15);
    precip.add(20.83);
    precip.add(16.0);
    precip.add(12.7);
    precip.add(22.35);

    return new NewhallDataset("Ajo, AZ", "USA", 32.37, -112.87, 'N', 'E', 549.0, precip, temps, 1971, 2000, true, 200.0);
  }

  private NewhallDataset makePendleton() {
    ArrayList<Double> temps = new ArrayList<Double>();
    double[] tempArray = {55.1, 56, 56.8, 59.6, 63.3, 66.7, 70.5, 71.9, 70.6, 65.5, 59.1, 55.2};
    for (double temp : tempArray) {
      temps.add(temp);
    }

    ArrayList<Double> precips = new ArrayList<Double>(12);
    double[] precipArray = {3, 2.91, 2.67, 0.81, 0.32, 0.14, 0.08, 0.02, 0.14, 0.46, 0.93, 1.73};
    for (double precip : precipArray) {
      precips.add(precip);
    }

    return new NewhallDataset("Pendleton, OR", "USA", 33.3, -117.35, 'N', 'E', 75.0, precips, temps, 1971, 2000, false, 200.0);
  }

  private NewhallDataset makeChadron() {
    ArrayList<Double> temps = new ArrayList<Double>();
    double[] tempArray = {22.8, 28.1, 36.2, 45.9, 56.8, 67.2, 74.1, 73, 61.7, 48.9, 33.7, 25.1};
    for (double temp : tempArray) {
      temps.add(temp);
    }

    ArrayList<Double> precips = new ArrayList<Double>(12);
    double[] precipArray = {0.46, 0.47, 0.91, 1.89, 3.02, 2.62, 2.11, 1.67, 1.44, 1.05, 0.57, 0.42};
    for (double precip : precipArray) {
      precips.add(precip);
    }

    return new NewhallDataset("Chadron, NE", "USA", 42.82, -103.0, 'N', 'E', 3510, precips, temps, 1971, 2000, false, 200.0);
  }

  private NewhallDataset makePittsburgh1950() {
    ArrayList<Double> temps = new ArrayList<Double>();
    double[] tempArray = {5.89, 1.33, 2.56, 8.5, 17.39, 21.28, 22.56, 22.33, 18.5, 14.72, 4.83, -1.22};
    for (double temp : tempArray) {
      temps.add(temp);
    }

    ArrayList<Double> precips = new ArrayList<Double>(12);
    double[] precipArray = {158.24, 76.96, 89.66, 66.29, 92.46, 118.11, 127.25, 97.54, 94.23, 33.02, 187.96, 63.5};
    for (double precip : precipArray) {
      precips.add(precip);
    }

    return new NewhallDataset("Pittsburgh, PA", "USA", 40.45, 80.0, 'N', 'W', 310.0, precips, temps, 1950, 1950, true, 76.2);
  }

  private NewhallDataset makePiedmont1937() {
    ArrayList<Double> temps = new ArrayList<Double>();
    double[] tempArray = {5.33, 1.67, 5.83, 11.5, 18.39, 23.67, 24.83, 24.89, 18.33, 12.11, 6.61, 1.94};
    for (double temp : tempArray) {
      temps.add(temp);
    }

    ArrayList<Double> precips = new ArrayList<Double>(12);
    double[] precipArray = {181.1, 65.79, 34.29, 205.49, 84.07, 144.02, 123.19, 156.21, 61.21, 200.91, 62.23, 14.48};
    for (double precip : precipArray) {
      precips.add(precip);
    }

    return new NewhallDataset("Piedmont Research Station, NC", "USA", 38.23, 78.12, 'N', 'E', 158.0, precips, temps, 1937, 1937, true, 154.9);
  }
}
