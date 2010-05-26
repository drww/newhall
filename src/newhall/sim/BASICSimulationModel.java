package newhall.sim;

import java.util.ArrayList;

public class BASICSimulationModel {

  

  public void runSimulation(NewhallDataset dataset) {

    // Convert elevation into meters.
    double elevation = dataset.getElevation();
    if(!dataset.isMetric()) {
      elevation *= 0.3048;
    }

    // Convert temperatures into celsius.
    ArrayList<Double> temperature = dataset.getTemperature();
    if(!dataset.isMetric()) {
      for(Double temp : temperature) {
        temp = (5/9) * (temp - 32);
      }
    }

    ArrayList<Double> upe = new ArrayList<Double>(12);
    ArrayList<Double> mpe = new ArrayList<Double>(12);
    ArrayList<Double> mwi = new ArrayList<Double>(12);

    // Zero lists.
    for(int i = 0; i < 12; i++) {
      upe.set(i, 0.0);
      mpe.set(i, 0.0);
      mwi.set(i, 0.0);
    }

    for(int i = 0; i < 12; i++) {
      if(temperature.get(i) > 0) {
        double mwiValue = Math.pow((temperature.get(i) / 5), 1.514);
        mwi.set(i, mwiValue);
      }
    }

    double swi = 0.0;
    for(Double mwiElement : mwi) {
      swi += mwiElement;
    }

    double a = (Math.pow(swi, 3) * (6.75 * 10E-7)) - (Math.pow(swi, 2) * (7.71 * 10E-5)) - (swi * 0.01792) + 0.49239;


  }

  private BASICSimulationModel() {
    // Should not be instantiated.
  }

}
