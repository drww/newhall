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

    if (dataset.getNsHemisphere() == 'N') {    // GOTO 505
      int nrow = 0;
      for (int i = 1; i <= 31; i++) {
        if (dataset.getLatitude() > BASICSimulationModelConstants.rn[i]) {
          for (int j = 1; j <= 12; j++) {
            if (upe[j] <= 0) {
              mpe[j] = upe[j] * BASICSimulationModelConstants.inz[nrow][j];
            }
          }
          break;
        } else {
          nrow++;
        }
      }
      // 655
      // Return from GOSUB 205.
    } else {    // GOTO 535
      int nrow = 0;
      for (int i = 1; i <= 13; i++) {
        if (dataset.getLatitude() < BASICSimulationModelConstants.rs[i]) {
          break;  // And go to 560, also when for() block ends.
        } else {
          nrow++;
        }
      }

      // 560

      if (nrow != 0) {
        // 605
        for (int i = 1; i <= 12; i++) {
          if (upe[i] <= 0) {
            // 650
            continue;
          } else if (nrow >= 13 || BASICSimulationModelConstants.fs[nrow][i]
                  == BASICSimulationModelConstants.fs[nrow + 1][i]) {
            // 640
            double cf = BASICSimulationModelConstants.fs[nrow][i];
            mpe[i] = upe[i] * cf;
          } else {
            // 625
            double cf = (BASICSimulationModelConstants.fs[nrow + 1][i] - BASICSimulationModelConstants.fs[nrow][i])
                    * ((dataset.getLatitudeMinutes() - BASICSimulationModelConstants.rs[nrow]) * 60)
                    / ((BASICSimulationModelConstants.rs[nrow + 1] - BASICSimulationModelConstants.rs[nrow]) * 60);
            cf += BASICSimulationModelConstants.fs[nrow][i];
            // 645
            mpe[i] = upe[i] * cf;
          }
          // Return from GOSUB 205.
        }
      } else {
        // 565
        nrow = 1;
        for (int i = 1; i <= 12; i++) {
          if (upe[i] <= 0) {
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
    }

    // 140

    double arf = 0;
    double aev = 0;
    for (int i = 1; i <= 12; i++) {
      arf += precip[i];
      aev += mpe[i];
    }

    // GOSUB 660

    double sumt = 0;
    for (int i = 1; i <= 12; i++) {
      sumt += temperature[i];
    }

    double tma = sumt / 12 + BASICSimulationModelConstants.fc;
    double at1 = (temperature[6] + temperature[7]
            + temperature[8]) / 3 + BASICSimulationModelConstants.fc;
    double at2 = (temperature[1] + temperature[2]
            + temperature[12]) / 3 + BASICSimulationModelConstants.fc;

    double st = 0;
    double wt = 0;
    if (dataset.getNsHemisphere() == 'N') {
      st = at1;
      wt = at2;
    } else {
      st = at2;
      wt = at1;
    }

    double dif = Math.abs(at1 - at2);
    double cs = dif * (1 - BASICSimulationModelConstants.fcd) / 2;

    // 680

    boolean[] cr = new boolean[13];
    boolean[] reg = new boolean[13];
    cr[1] = tma < 0;
    cr[2] = 0 <= tma && tma < 8;
    cr[3] = (st - cs) < 15;
    cr[7] = (dif * BASICSimulationModelConstants.fcd) > 5;
    cr[8] = (tma >= 8) && (tma < 15);
    cr[9] = (tma >= 15) && (tma < 22);
    cr[10] = tma >= 22;
    cr[11] = tma < 8;
    reg[1] = cr[1];
    reg[2] = cr[2] && cr[3];
    reg[3] = cr[11] && !cr[3] && cr[7];
    reg[4] = cr[8] && cr[7];
    reg[5] = cr[9] && cr[7];
    reg[6] = cr[10] && cr[7];
    reg[7] = cr[11] && !cr[7] && !cr[3];
    reg[8] = cr[8] && !cr[7];
    reg[9] = cr[9] && !cr[7];
    reg[10] = cr[10] && !cr[7];

    st -= cs;
    wt += cs;
    dif = st - wt;

    // Return.
    // 145

    String trr = "";
    for (int i = 1; i <= 10; i++) {
      if (reg[i]) {
        trr = BASICSimulationModelConstants.tempRegimes[i];
      }
    }

    // GOSUB 775 => GOSUB 3000 for printing.
    // Rendering, asking "Do you want to print this?"
    // Asking "Do you want to continue on?" => A
    // Return.

    // 155
    // Generate filename for input to STORVAR5, an
    // *.INT file.  Format is as follows:
    // ST, CO, RLA1, RLA2, H, RLO1, RLO2, EW, ELEV, ARF, AEV, TRR,
    // TMA, ST, WT  followed by:
    // for i = 1 to 12:  precip[i], temp[i], mpe[i]

    // 185 => GOSUB 725
    // Checks user response in A.  Returns if no, prints
    // CHR(12) + CHR(13 then chainloads STORVAR5.

    // 50 - STORVAR5

    double whc = 200;  // Water holding capacity, switch to param.
    double fsl = whc / 64;
    double[] sl = new double[65];
    for (int i = 0; i < sl.length; i++) {
      sl[i] = 0;
    }

    int k = 1;
    int swst = 0;
    int swfi = 0;

    double[] ntwi = new double[4];
    double[] ntsu = new double[4];
    double[] nsd = new double[4];
    double[] nzd = new double[4];
    double[] nd = new double[4];
    double[] cc = new double[4];
    for (int i = 1; i <= 3; i++) {
      ntwi[i] = 0.0;
      ntsu[i] = 0.0;
      nsd[i] = 0.0;
      nzd[i] = 0.0;
      nd[i] = 0.0;
      cc[i] = 0.0;
    }

    double[] cd = new double[6];
    for (int i = 1; i <= 5; i++) {
      cd[i] = 0.0;
    }

    // 300

    int msw = -1;
    int icon = 0;
    int lt5c = 0;
    int lt8c = 0;
    int ie = 0;
    int ib = 1;
    double prmo = 0;
    int nccd = 0;
    int nccm = 0;
    int id8c = 0;
    int id5c = 0;
    int swt = 0;
    int tc = 0;
    int np = 0;
    int np8 = 0;
    int ncsm = 0;
    int ncwm = 0;
    int ncsp = 0;
    int ncwp = 0;

    // 360

    double[] nbd = new double[7];
    double[] ned = new double[7];
    double[] nbd8 = new double[7];
    double[] ned8 = new double[7];
    for (int i = 1; i <= 6; i++) {
      nbd[i] = 0.0;
      ned[i] = 0.0;
      nbd8[i] = 0.0;
      ned8[i] = 0.0;
    }

    // 400

    int[] iday = new int[361];
    for (int i = 1; i <= 360; i++) {
      iday[i] = 0;
    }

    double fc = 2.5;
    double fcd = 0.66;
    int swp = -1;
    int gogr = 0;

    // 420

    boolean noMpeGreaterThanPrecip = true;
    for (int i = 1; i <= 12; i++) {
      if (mpe[i] > precip[i]) {
        noMpeGreaterThanPrecip = false;
        break;
      }
    }
    if (noMpeGreaterThanPrecip) {
      cd[5] = -1;
      swt = -1;
    }

    // 460 WTF, REP = "Want explaination?"

    for (int n = 1; n <= 10; n++) {
      for (int im = 1; im <= 12; im++) {
        // Print a period.
        // GOSUB 2750

        int zsw = 0;
        double lp = precip[im] / 2;
        double npe = (lp - mpe[im]) / 2;
        if (npe <= 0) {
          // 2760
          npe = 0 - npe;
        } else {
          zsw = -1;
        }

        // 2770

        for (int i3 = 1; i3 <= 64; i3++) {
          if (zsw == 0) {
            // 2820
            int nr = BASICSimulationModelConstants.dp[i3];
            if (sl[nr] <= 0) {
              // 2860
              continue;
            } else {
              // 2830
              double rpe = sl[nr] * BASICSimulationModelConstants.dr[i3];
              if (npe <= rpe) {
                // 2850
                sl[nr] = sl[nr] - (npe / BASICSimulationModelConstants.dr[i3]);
                npe = 0;
                // Return from GOSUB 2750;
                break;
              } else {
                // 2840
                sl[nr] = 0;
                npe = npe - rpe;
                // 2860
                continue;
              }
            }
          } else {
            // 2780
            if (sl[i3] >= fsl) {
              // 2860
              continue;
            } else {
              // 2790
              double esl = fsl - sl[i3];
              if (esl >= npe) {
                // 2810
                sl[i3] = sl[i3] + npe;
                // Return from GOSUB 2750.
                break;
              } else {
                // 2800
                sl[i3] = fsl;
                npe = npe - esl;
                // 2860
                continue;
              }
            }
          }
        }

        // Return from GOSUB 2750.

        // GOSUB 2070

        double hp = precip[im] / 2;
        for (int i3 = 1; i3 <= 64; i3++) {
          if (sl[i3] >= fsl) {
            // 2140
            continue;
          } else {
            // 2090
            double esl = fsl - sl[i3];
            if (esl >= hp) {
              // 2120
              sl[i3] = fsl;
              if (gogr != 0) {
                // GOSUB 3900
                // Print "ADDITION", draw, beep.
              }
              break;  // Return from GOSUB 2070.
            } else {
              // 2100
              sl[i3] = fsl;
              hp -= esl;
              if (gogr != 0) {
                // GOSUB 3900
                // Print "ADDITION", draw, beep.
              }
              continue;
            }
          }
        }

        // GOSUB 2750

        zsw = 0;
        lp = precip[im] / 2;
        npe = (lp - mpe[im]) / 2;
        if (npe <= 0) {
          // 2760
          npe = 0 - npe;
        } else {
          zsw = -1;
        }

        // 2770

        for (int i3 = 1; i3 <= 64; i3++) {
          if (zsw == 0) {
            // 2820
            int nr = BASICSimulationModelConstants.dp[i3];
            if (sl[nr] <= 0) {
              // 2860
              continue;
            } else {
              // 2830
              double rpe = sl[nr] * BASICSimulationModelConstants.dr[i3];
              if (npe <= rpe) {
                // 2850
                sl[nr] = sl[nr] - (npe / BASICSimulationModelConstants.dr[i3]);
                npe = 0;
                // Return from GOSUB 2750;
                break;
              } else {
                // 2840
                sl[nr] = 0;
                npe = npe - rpe;
                // 2860
                continue;
              }
            }
          } else {
            // 2780
            if (sl[i3] >= fsl) {
              // 2860
              continue;
            } else {
              // 2790
              double esl = fsl - sl[i3];
              if (esl >= npe) {
                // 2810
                sl[i3] = sl[i3] + npe;
                // Return from GOSUB 2750.
                break;
              } else {
                // 2800
                sl[i3] = fsl;
                npe = npe - esl;
                // 2860
                continue;
              }
            }
          }
        }

        // Return from GOSUB 2750.

        continue;

      }

      // 510

      double tmoi = 0;
      for (int it = 1; it <= 64; it++) {
        tmoi += sl[it];
      }

      // 520

      if (Math.abs(tmoi - prmo) < (prmo / 100)) {
        // 541
        break;        // From N loop.
      } else {
        // 530
        prmo = tmoi;
        continue;     // Next N.
      }

    }

    // 550

    gogr = -1;

    // GOSUB 2880



    // GOSUB 3760



    System.out.print("Temp: ");
    for (Double d : temperature) {
      System.out.print(d + " ");
    }
    System.out.println();

    System.out.print("UPE: ");
    for (Double d : upe) {
      System.out.print(d + " ");
    }
    System.out.println();

    System.out.print("MPE: ");
    for (Double d : mpe) {
      System.out.print(d + " ");
    }
    System.out.println();

    System.out.print("MWI: ");
    for (Double d : mwi) {
      System.out.print(d + " ");
    }
    System.out.println();

  }

  private BASICSimulationModel() {
    // Should not be instantiated.
  }
  
}
