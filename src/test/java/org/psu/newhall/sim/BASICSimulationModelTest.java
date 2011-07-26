package org.psu.newhall.sim;

import java.util.ArrayList;
import org.junit.Test;
import static org.junit.Assert.*;

public class BASICSimulationModelTest {

  /** Model accuracy: 99.96% **/

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

    return new NewhallDataset("Columbus 3 NE", "USA", 41.4666, 97.3333, 'N', 'W', 441.96, precip, temps, 1997, 1997, true);

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

    return new NewhallDataset("Columbus 3 NE", "USA", 41.4666, 97.3333, 'N', 'W', 441.96, precip, temps, 1998, 1998, true);
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

    return new NewhallDataset("Mead Agronomy Lab", "USA", 41.4666, 97.3333, 'N', 'W', 359.66, precip, temps, 1989, 1989, true);
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

    return new NewhallDataset("Ajo, AZ", "USA", 32.37, -112.87, 'N', 'E', 549.0, precip, temps, 1971, 2000, true);
  }
}
