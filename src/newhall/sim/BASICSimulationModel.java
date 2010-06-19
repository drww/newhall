package newhall.sim;

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
    double[] temperature = new double[13];  // T()
    double[] precip = new double[13];       // MP()
    for(int i = 1; i <= 12; i++) {
      temperature[i] = dataset.getTemperature().get(i-1);
      precip[i] = dataset.getPrecipitation().get(i-1);
    }
    if(!dataset.isMetric()) {
      for(int i = 1; i <= 12; i++) {
        temperature[i] = (0.555 * (temperature[i] - 32));
        precip[i] = dataset.getPrecipitation().get(i-1) * 25.4;
      }
    }

    double[] upe = new double[13];
    double[] mpe = new double[13];
    double[] mwi = new double[13];

    // 405

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

    // 425

    double a = (Math.pow(swi, 3) * (6.75 * 10E-7)) - (Math.pow(swi, 2) * (7.71 * 10E-5)) - (swi * 0.01792) + 0.49239;

    // 430

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

    if(dataset.getNsHemisphere() == 'N') {    // GOTO 505
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
      // 655
    } else {    // GOTO 535
      int nrow = 0;
      for(int i = 1; i <= 13; i++) {
        if(dataset.getLatitude() < BASICSimulationModelConstants.rs[i]) {
          break;  // And go to 560, also when for() block ends.
        } else {
          nrow++;
        }
      }

      // 560

      if(nrow != 0) {
        // 605
        for(int i = 1; i <= 12; i++) {
          if(upe[i] <= 0) {
            // 650
            continue;
          } else if (nrow >= 13 || BASICSimulationModelConstants.fs[nrow][i] ==
                  BASICSimulationModelConstants.fs[nrow+1][i]) {
            // 640
            double cf = BASICSimulationModelConstants.fs[nrow][i];
            mpe[i] = upe[i] * cf;
          } else {
            // 625
            double cf = (BASICSimulationModelConstants.fs[nrow+1][i] - BASICSimulationModelConstants.fs[nrow][i])
                    * ((dataset.getLatitudeMinutes() - BASICSimulationModelConstants.rs[nrow]) * 60) /
                    ((BASICSimulationModelConstants.rs[nrow+1] - BASICSimulationModelConstants.rs[nrow]) * 60);
            cf += BASICSimulationModelConstants.fs[nrow][i];
            // 645
            mpe[i] = upe[i] * cf;
          }
          // Return from GOSUB 205.
        }
      } else {
        // 565
        nrow = 1;
        for(int i = 1; i <= 12; i++) {
          if(upe[i] <= 0) {
            continue;
          } else {
            double cf = (BASICSimulationModelConstants.fs[1][i]
                    - BASICSimulationModelConstants.inz[1][i])
                    * (dataset.getLatitudeDegrees() * 60 + dataset.getLatitudeMinutes())
                    / 300;
            cf += BASICSimulationModelConstants.inz[1][i];
            mpe[i] = upe[i] * cf;
          }
        }
        // Return from GOSUB 205.
      }

      // 140

      double arf = 0;
      double aev = 0;
      for(int i = 1; i <= 12; i++) {
        arf += precip[i];
        aev += mpe[i];
      }

      // GOSUB 660

      

      // 145

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
