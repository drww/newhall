package newhall.sim;

import java.util.ArrayList;

/**
 * This is the 1:1 translation of the original Newhall BASIC source code.  Variable names have
 * been preserved (and unfortunately ambiguous as a result).  Array indexes do not start at zero,
 * among other BASIC quirkiness.  This should be modernized in the future, but this version should
 * remain to assure consistency with the original version.
 */

public class BASICSimulationModel {

  public static void runSimulation(NewhallDataset dataset) {

    System.out.println(dataset);

    // Convert elevation into meters.
    double elevation = dataset.getElevation();
    if(!dataset.isMetric()) {
      elevation *= 0.305;  // 0.3048, technically.
    }

    // Convert temperatures into celsius.
    double[] temperature = new double[13];
    for(int i = 1; i <= 12; i++) {
      temperature[i] = dataset.getTemperature().get(i-1);
    }
    if(!dataset.isMetric()) {
      for(int i = 1; i <= 12; i++) {
        temperature[i] = (0.555 * (temperature[i] - 32));
      }
    }

    double[] upe = new double[13];
    double[] mpe = new double[13];
    double[] mwi = new double[13];

    for(int i = 1; i <= 12; i++) {
      if(temperature[i] > 0) {
        double mwiValue = Math.pow((temperature[i] / 5), 1.514);
        mwi[i] = mwiValue;
      }
    }

    double swi = 0.0;
    for(Double mwiElement : mwi) {
      swi += mwiElement;
    }

    double a = (Math.pow(swi, 3) * (6.75 * 10E-7)) - (Math.pow(swi, 2) * (7.71 * 10E-5)) - (swi * 0.01792) + 0.49239;

    for(int i = 1; i <= 12; i++) {
      if(temperature[i] > 0) {
        if(temperature[i] < 26.5) {
          upe[i] = 16 * Math.pow(((10*temperature[i])/swi), a);
        } else if (temperature[i] >= 38) {
          upe[i] = 185.0;
        } else {
          double[] zt = BASICSimulationModelConstants.zt;
          double[] zpe = BASICSimulationModelConstants.zpe;
          int kl = 0;
          int kk = 0;
          for(int ki = 1; ki <= 24; ki++) {
            kl = ki + 1;
            kk = ki;
            if(temperature[i] >= zt[ki] && temperature[i] < zt[kl]) {
              upe[i] = zpe[kk];
            }
          }
        }
      }
    }

    if(dataset.getNsHemisphere() == 'N') {    // Roughly line 50 in psudocode.
      int nrow = 0;
      for(int i = 1; i <= 31; i++) {
        if(dataset.getLatitude() > BASICSimulationModelConstants.rn[i]) {
          for(int j = 1; j <= 12; j++) {
            if(upe[j] <= 0) {
              mpe[j] = upe[j] * BASICSimulationModelConstants.inz[nrow][j];
            }
          }
          break;
        } else {
          nrow++;
        }
      }
    } else {
      int nrow = 0;
      for(int i = 1; i <= 13; i++) {
        if(dataset.getLatitude() < BASICSimulationModelConstants.rs[i]) {
          // 560
          if(nrow != 0) {
            // 605
          } else {
            nrow = 1;
            for(int j = 1; j <= 12; j++) {
              if(upe[j] > 0) {
                double cf = 
                        BASICSimulationModelConstants.fs[1][j] -
                        (BASICSimulationModelConstants.inz[1][j] *
                        0);
                // junk
                mpe[j] = upe[j] * cf;
              }
            }
            break;
            // 655
          }
        } else {
          nrow++;
        }
      }
    }

    System.out.print("Temp: ");
    for(Double d : temperature) {
      System.out.print(d + " ");
    }
    System.out.println();

    System.out.print("UPE: ");
    for(Double d : upe) {
      System.out.print(d + " ");
    }
    System.out.println();

    System.out.print("MPE: ");
    for(Double d : mpe) {
      System.out.print(d + " ");
    }
    System.out.println();

    System.out.print("MWI: ");
    for(Double d : mwi) {
      System.out.print(d + " ");
    }
    System.out.println();

  }

  private BASICSimulationModel() {
    // Should not be instantiated.
  }

}
