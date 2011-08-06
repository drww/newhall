package org.psu.newhall.sim;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the 1:1 translation of the original Newhall BASIC source code.  Variable names have
 * been preserved (and unfortunately ambiguous as a result).  Array indexes do not start at zero,
 * among other BASIC quirkiness.  This should be modernized in the future, but this version should
 * remain to assure consistency with the original version.
 */
public class BASICSimulationModel {

  public static NewhallResults runSimulation(NewhallDataset dataset, double waterHoldingCapacity) {
    // Calls simulation model with the BASIC version's default constants for FC and FCD.
    return runSimulation(dataset, waterHoldingCapacity, BASICSimulationModelConstants.fc, BASICSimulationModelConstants.fcd);
  }

  public static NewhallResults runSimulation(NewhallDataset dataset, double waterHoldingCapacity, double fc, double fcd) {

    // Convert elevation into meters.
    double elevation = dataset.getElevation();
    if (!dataset.isMetric()) {
      // Feet to meters.  1 foot = 0.3048 m
      elevation *= 0.305;  // 0.3048, technically.  Using coefficient BASIC version uses.
    }

    // Convert temperatures into celsius.
    double[] temperature = new double[13];  // T()
    double[] precip = new double[13];       // MP()
    for (int i = 1; i <= 12; i++) {
      temperature[i] = dataset.getTemperature().get(i - 1);
      precip[i] = dataset.getPrecipitation().get(i - 1);
    }
    if (!dataset.isMetric()) {
      double cv = 5.0 / 9.0;
      for (int i = 1; i <= 12; i++) {
        // Farenheit to Celsius.
        temperature[i] = cv * (temperature[i] - 32);
        // Inches to Millimeters.  1 inch = 25.4 mm
        precip[i] = dataset.getPrecipitation().get(i - 1) * 25.4;
      }
    }

    double[] upe = new double[13];
    double[] mpe = new double[13];
    double[] mwi = new double[13];

    // 405

    for (int i = 1; i <= 12; i++) {
      if (temperature[i] > 0) {
        double mwiValue = Math.pow((temperature[i] / 5), 1.514);
        mwi[i] = mwiValue;
      }
    }

    double swi = 0.0;
    for (Double mwiElement : mwi) {
      swi += mwiElement;
    }

    // 425

    double a = (0.000000675 * swi * swi * swi) - (0.0000771 * swi * swi) + (0.01792 * swi) + 0.49239;

    // 430

    for (int i = 1; i <= 12; i++) {
      if (temperature[i] > 0) {
        if (temperature[i] < 26.5) {
          double aBase = 10 * (temperature[i] / swi);
          upe[i] = 16 * Math.pow(aBase, a);
        } else if (temperature[i] >= 38) {
          upe[i] = 185.0;
        } else {
          double[] zt = BASICSimulationModelConstants.zt;
          double[] zpe = BASICSimulationModelConstants.zpe;
          int kl = 0;
          int kk = 0;
          for (int ki = 1; ki <= 24; ki++) {
            kl = ki + 1;
            kk = ki;
            if (temperature[i] >= zt[ki - 1] && temperature[i] < zt[kl - 1]) {
              upe[i] = zpe[kk - 1];
              break;
            }
          }
        }
      }
    }

    // 495

    if (dataset.getNsHemisphere() == 'N') {
      // 505
      int nrow = 0;
      for (int i = 1; i <= 31; i++) {
        if (dataset.getLatitude() < BASICSimulationModelConstants.rn[i - 1]) {
          // 515 - Breaks outside the loop.
          break;
        } else {
          // 510
          nrow++;
        }
      }

      // 515
      for (int i = 1; i <= 12; i++) {
        if (upe[i] <= 0) {
          // 525
          continue;
        } else {
          // 520
          mpe[i] = upe[i] * BASICSimulationModelConstants.inz[i - 1][nrow - 1];
          continue;
        }
      }

      // 530 -> GOTO 655 -> Return from GOSUB 205.
    } else {    // GOTO 535
      int nrow = 0;
      for (int i = 1; i <= 13; i++) {
        if (dataset.getLatitude() < BASICSimulationModelConstants.rs[i - 1]) {
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
          } else if (nrow >= 13 || BASICSimulationModelConstants.fs[i - 1][nrow - 1]
                  == BASICSimulationModelConstants.fs[i - 1][nrow]) {
            // 640
            double cf = BASICSimulationModelConstants.fs[i - 1][nrow - 1];
            mpe[i] = upe[i] * cf;
          } else {
            // 625
            double cf = (BASICSimulationModelConstants.fs[i - 1][nrow] - BASICSimulationModelConstants.fs[i - 1][nrow - 1])
                    * ((dataset.getLatitudeMinutes() - BASICSimulationModelConstants.rs[nrow - 1]) * 60)
                    / ((BASICSimulationModelConstants.rs[nrow] - BASICSimulationModelConstants.rs[nrow - 1]) * 60);
            cf += BASICSimulationModelConstants.fs[i - 1][nrow - 1];
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
            double cf = (BASICSimulationModelConstants.fs[i - 1][0]
                    - BASICSimulationModelConstants.inz[i - 1][0])
                    * (dataset.getLatitudeDegrees() * 60 + dataset.getLatitudeMinutes())
                    / 300;
            cf += BASICSimulationModelConstants.inz[i - 1][0];
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

    double tma = sumt / 12 + fc;
    double at1 = (temperature[6] + temperature[7]
            + temperature[8]) / 3 + fc;
    double at2 = (temperature[1] + temperature[2]
            + temperature[12]) / 3 + fc;

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
    double cs = dif * (1 - fcd) / 2;

    // 680

    /**
     * The cr[] arrays hold critera to determine temperature regime.  Then
     * the reg[] array holds the flags for each regime.  The last-most flag
     * that is true indicates the temp regime.
     */

    boolean[] cr = new boolean[13];
    boolean[] reg = new boolean[13];
    cr[1] = tma < 0;                      // Mean annual air temp (MAAT) < 0C.
    cr[2] = 0 <= tma && tma < 8;          // 0C <= MAAT <= 8C.
    cr[3] = (st - cs) < 15;               // Summer temp ave minus (summer/winter difference * (1 - SOIL_AIR_REL) * 0.5) < 15C.
    cr[7] = (dif * fcd) > 5;              // Summer/winter difference * SOIL_AIR_REL > 5.
    cr[8] = (tma >= 8) && (tma < 15);     // 8C <= MAAT < 15C.
    cr[9] = (tma >= 15) && (tma < 22);    // 15C <= MAAT < 22C.
    cr[10] = tma >= 22;                   // 22C <= MAAT.
    cr[11] = tma < 8;                     // MAAT < 8C.
    reg[1] = cr[1];                       // PERGELIC
    reg[2] = cr[2] && cr[3];              // CRYIC
    reg[3] = cr[11] && !cr[3] && cr[7];   // FRIGID
    reg[4] = cr[8] && cr[7];              // MESIC
    reg[5] = cr[9] && cr[7];              // THERMIC
    reg[6] = cr[10] && cr[7];             // HYPERTHERMIC
    reg[7] = cr[11] && !cr[7] && !cr[3];  // ISOFRIGID
    reg[8] = cr[8] && !cr[7];             // ISOMESIC
    reg[9] = cr[9] && !cr[7];             // ISOTHERMIC
    reg[10] = cr[10] && !cr[7];           // ISOHYPERTHERMIC

    st -= cs;
    wt += cs;
    dif = st - wt;

    // Return.
    // 145

    String trr = "";
    for (int i = 1; i <= 10; i++) {
      if (reg[i]) {
        trr = BASICSimulationModelConstants.tempRegimes[i - 1];
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

    double whc = waterHoldingCapacity;  // Water holding capacity, switch to param.
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
    boolean[] cc = new boolean[4];
    for (int i = 1; i <= 3; i++) {
      ntwi[i] = 0.0;
      ntsu[i] = 0.0;
      nsd[i] = 0.0;
      nzd[i] = 0.0;
      nd[i] = 0.0;
      cc[i] = false;
    }

    double[] cd = new double[6];
    for (int i = 1; i <= 5; i++) {
      cd[i] = 0.0;
    }

    // 300

    int msw = -1;
    int sib = 0;
    double sir = 0.0;
    int x = 0;
    boolean swm = false;
    int max = 0;
    int icon = 0;
    double lt5c = 0;
    int lt8c = 0;
    int ie = 0;
    int ib = 1;
    double prmo = 0;
    int nccd = 0;
    int nccm = 0;
    int id8c = 0;
    double id5c = 0;
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

    //double fc = 2.5;
    //double fcd = 0.66;
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
            int nr = BASICSimulationModelConstants.dp[i3 - 1];
            if (sl[nr] <= 0) {
              // 2860
              continue;
            } else {
              // 2830
              double rpe = sl[nr] * BASICSimulationModelConstants.dr[i3 - 1];
              if (npe <= rpe) {
                // 2850
                sl[nr] = sl[nr] - (npe / BASICSimulationModelConstants.dr[i3 - 1]);
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
              sl[i3] += hp;
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
            int nr = BASICSimulationModelConstants.dp[i3 - 1];
            if (sl[nr] <= 0) {
              // 2860
              continue;
            } else {
              // 2830
              double rpe = sl[nr] * BASICSimulationModelConstants.dr[i3 - 1];
              if (npe <= rpe) {
                // 2850
                sl[nr] = sl[nr] - (npe / BASICSimulationModelConstants.dr[i3 - 1]);
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

    for (int i = 1; i <= 3; i++) {
      cc[i] = false;
    }

    boolean[] pc = new boolean[7];
    for (int i = 1; i <= 6; i++) {
      pc[i] = false;
    }

    pc[1] = sl[9] <= 0;
    pc[2] = sl[17] <= 0;
    pc[3] = sl[25] <= 0;
    cc[1] = pc[1] && pc[2] && pc[3];
    cc[2] = !cc[1] && (pc[1] || pc[2] || pc[3]);
    pc[4] = sl[9] > 0;
    pc[5] = sl[17] > 0;
    pc[6] = sl[25] > 0;
    cc[3] = pc[4] && pc[5] && pc[6];

    for (int i = 1; i <= 3; i++) {
      if (!cc[i]) {
        continue;
      } else {
        k = i;
        break;
      }
    }

    // Return from GOSUB 2880.

    // GOSUB 3760
    //   GOSUB 4330
    //     Draws Dry,M/D,Moist calendar.

    // 570

    for (int im = 1; im <= 12; im++) {

      // 590 - GOSUB 1630

      int[] dmc = new int[4];

      for (int i = 1; i <= 3; i++) {
        dmc[i] = 0;
        cc[i] = false;
      }

      int zsw = 0;
      int dpmc = 0;
      int pmc = k;
      int igmc = 0;

      double lp = precip[im] / 2;
      double npe = (lp - mpe[im]) / 2;
      double cnpe = 0;
      boolean skipi3Loop = false;

      if (npe < 0) {
        npe = -npe;
        cnpe = npe;
      } else if (npe == 0) {
        // 1920 
        skipi3Loop = true;
      } else {
        zsw = -1;
        cnpe = npe;
      }

      // 1670
      if (!skipi3Loop) {
        for (int i3 = 1; i3 <= 64; i3++) {
          if (zsw == 0) {
            // 1840
            if (npe <= 0) {
              // 1920
              break;
            } else {
              // 1850
              int nr = BASICSimulationModelConstants.dp[i3 - 1];
              if (sl[nr] <= 0) {
                // 1910
                continue;
              } else {
                // 1870
                double rpd = sl[nr] * BASICSimulationModelConstants.dr[i3 - 1];
                if (npe <= rpd) {
                  // 1890
                  sl[nr] -= npe / BASICSimulationModelConstants.dr[i3 - 1];
                  npe = 0;
                  // 1750
                } else {
                  // 1880
                  sl[nr] = 0;
                  npe = npe - rpd;
                  // 1750
                }
                // 1750

                for (int i = 1; i <= 3; i++) {
                  cc[i] = false;
                }

                for (int i = 1; i <= 6; i++) {
                  pc[i] = false;
                }

                pc[1] = sl[9] <= 0;
                pc[2] = sl[17] <= 0;
                pc[3] = sl[25] <= 0;
                cc[1] = pc[1] && pc[2] && pc[3];
                cc[2] = !cc[1] && (pc[1] || pc[2] || pc[3]);
                pc[4] = sl[9] > 0;
                pc[5] = sl[17] > 0;
                pc[6] = sl[25] > 0;
                cc[3] = pc[4] && pc[5] && pc[6];

                for (int i = 1; i <= 3; i++) {
                  if (!cc[i]) {
                    continue;
                  } else {
                    k = i;
                    break;
                  }
                }

                // 1750 - Return from GOSUB 2880.

                if (zsw != 0) {
                  // GOSUB 3900
                  //   Print "ADDITION", render stuff, beep.
                } else {
                  // GOSUB 3990
                  //   Print "DEPLETION", render stuff, beep.
                }

                // 1760

                int kk = k;
                k = pmc;
                if (kk == pmc) {
                  // 1910
                  continue;
                }

                if (npe <= 0) {
                  // 1920
                  break;
                }

                // 1790

                double rpe = cnpe - npe;
                dmc[k] = (int) ((15 * rpe) / cnpe) - dpmc;
                igmc = dmc[k];
                dpmc = dmc[k] + dpmc;
                dmc[k] = 0;

                // 1820 - GOSUB 1960

                int ii = 0;
                int mm = 0;
                ie += igmc;
                for (int i = ib; i <= ie; i++) {
                  iday[i] = k;
                  // Screen rendering.
                  if (i > 30) {
                    ii = (i % 30) - 1;
                  } else {
                    ii = i;
                  }
                  // 1990
                  mm = i / 30;
                  if (i % 30 == 0) {
                    mm = mm - 1;
                  }

                  if (ii == -1) {
                    ii = 29;
                  }

                  if (mm < 0) {
                    mm = 0;
                  }
                  // More screen rendering with
                  // a GOSUB 4460 call.
                }
                ib = ie + 1;
                nd[k] += igmc;

                // Return from GOSUB 1960, 1820
                pmc = kk;
                k = kk;
                continue;

              }
            }
          } else {
            // 1680
            if (npe <= 0) {
              // 1920
              break;
            } else {
              // 1690
              if (sl[i3] >= fsl) {
                // 1910
                continue;
              } else {
                // 1700

                double esl = fsl - sl[i3];
                if (esl >= npe) {
                  // 1740
                  sl[i3] = sl[i3] + npe;
                  npe = 0;
                  // 1750
                } else {
                  sl[i3] = fsl;
                  // 1720
                  npe = npe - esl;
                  // 1750
                }

                // 1750 - GOSUB 2880

                for (int i = 1; i <= 3; i++) {
                  cc[i] = false;
                }

                for (int i = 1; i <= 6; i++) {
                  pc[i] = false;
                }

                pc[1] = sl[9] <= 0;
                pc[2] = sl[17] <= 0;
                pc[3] = sl[25] <= 0;
                cc[1] = pc[1] && pc[2] && pc[3];
                cc[2] = !cc[1] && (pc[1] || pc[2] || pc[3]);
                pc[4] = sl[9] > 0;
                pc[5] = sl[17] > 0;
                pc[6] = sl[25] > 0;
                cc[3] = pc[4] && pc[5] && pc[6];

                for (int i = 1; i <= 3; i++) {
                  if (!cc[i]) {
                    continue;
                  } else {
                    k = i;
                    break;
                  }
                }

                // 1750 - Return from GOSUB 2880.

                if (zsw != 0) {
                  // GOSUB 3900
                  //   Print "ADDITION", render stuff, beep.
                } else {
                  // GOSUB 3990
                  //   Print "DEPLETION", render stuff, beep.
                }

                // 1760

                int kk = k;
                k = pmc;
                if (kk == pmc) {
                  // 1910
                  continue;
                }

                if (npe <= 0) {
                  // 1920
                  break;
                }

                // 1790

                double rpe = cnpe - npe;
                dmc[k] = (int) ((15 * rpe) / cnpe) - dpmc;
                igmc = dmc[k];
                dpmc = dmc[k] + dpmc;
                dmc[k] = 0;

                // 1820 - GOSUB 1960

                int ii = 0;
                int mm = 0;
                ie += igmc;
                for (int i = ib; i <= ie; i++) {
                  iday[i] = k;
                  // Screen rendering.
                  if (i > 30) {
                    ii = (i % 30) - 1;
                  } else {
                    ii = i;
                  }
                  // 1990
                  mm = i / 30;
                  if (i % 30 == 0) {
                    mm = mm - 1;
                  }

                  if (ii == -1) {
                    ii = 29;
                  }

                  if (mm < 0) {
                    mm = 0;
                  }
                  // More screen rendering with
                  // a GOSUB 4460 call.   
                }
                ib = ie + 1;
                nd[k] += igmc;

                // Return from GOSUB 1960, 1820
                pmc = kk;
                k = kk;
                continue;
              }
            }
          }
        }
      }

      // 1920

      dmc[k] = 15 - dpmc;
      igmc = dmc[k];
      dmc[k] = 0;

      // GOSUB 1960

      int ii = 0;
      int mm = 0;
      ie += igmc;
      for (int i = ib; i <= ie; i++) {
        iday[i] = k;
        // Screen rendering.
        if (i > 30) {
          ii = (i % 30) - 1;
        } else {
          ii = i;
        }
        // 1990
        mm = i / 30;
        if (i % 30 == 0) {
          mm = mm - 1;
        }

        if (ii == -1) {
          ii = 29;
        }

        if (mm < 0) {
          mm = 0;
        }
        // More screen rendering with
        // a GOSUB 4460 call.
      }
      ib = ie + 1;
      nd[k] += igmc;

      // Return from GOSUB 1630, still in IM loop.
      // 600 - Print "STORM" - GOSUB 2070

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
            sl[i3] += hp;
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

      // 610 - "Second Half" - GOSUB 2880, 1630

      // GOSUB 2880

      for (int i = 1; i <= 3; i++) {
        cc[i] = false;
      }

      for (int i = 1; i <= 6; i++) {
        pc[i] = false;
      }

      pc[1] = sl[9] <= 0;
      pc[2] = sl[17] <= 0;
      pc[3] = sl[25] <= 0;
      cc[1] = pc[1] && pc[2] && pc[3];
      cc[2] = !cc[1] && (pc[1] || pc[2] || pc[3]);
      pc[4] = sl[9] > 0;
      pc[5] = sl[17] > 0;
      pc[6] = sl[25] > 0;
      cc[3] = pc[4] && pc[5] && pc[6];

      for (int i = 1; i <= 3; i++) {
        if (!cc[i]) {
          continue;
        } else {
          k = i;
          break;
        }
      }

      // GOSUB 1630

      for (int i = 1; i <= 3; i++) {
        dmc[i] = 0;
        cc[i] = false;
      }

      zsw = 0;
      dpmc = 0;
      pmc = k;

      lp = precip[im] / 2;
      npe = (lp - mpe[im]) / 2;
      cnpe = 0;
      skipi3Loop = false;

      if (npe < 0) {
        npe = -npe;
        cnpe = npe;
      } else if (npe == 0) {
        // 1920
        skipi3Loop = true;
      } else {
        zsw = -1;
        cnpe = npe;
      }

      // 1670
      if (!skipi3Loop) {
        for (int i3 = 1; i3 <= 64; i3++) {
          if (zsw == 0) {
            // 1840
            if (npe <= 0) {
              // 1920
              break;
            } else {
              // 1850
              int nr = BASICSimulationModelConstants.dp[i3 - 1];
              if (sl[nr] <= 0) {
                // 1910
                continue;
              } else {
                // 1870
                double rpd = sl[nr] * BASICSimulationModelConstants.dr[i3 - 1];
                if (npe <= rpd) {
                  // 1890
                  sl[nr] -= npe / BASICSimulationModelConstants.dr[i3 - 1];
                  npe = 0;
                  // 1750
                } else {
                  // 1880
                  sl[nr] = 0;
                  npe = npe - rpd;
                  // 1750
                }
                // 1750

                for (int i = 1; i <= 3; i++) {
                  cc[i] = false;
                }

                for (int i = 1; i <= 6; i++) {
                  pc[i] = false;
                }

                pc[1] = sl[9] <= 0;
                pc[2] = sl[17] <= 0;
                pc[3] = sl[25] <= 0;
                cc[1] = pc[1] && pc[2] && pc[3];
                cc[2] = !cc[1] && (pc[1] || pc[2] || pc[3]);
                pc[4] = sl[9] > 0;
                pc[5] = sl[17] > 0;
                pc[6] = sl[25] > 0;
                cc[3] = pc[4] && pc[5] && pc[6];

                for (int i = 1; i <= 3; i++) {
                  if (!cc[i]) {
                    continue;
                  } else {
                    k = i;
                    break;
                  }
                }

                // 1750 - Return from GOSUB 2880.

                if (zsw != 0) {
                  // GOSUB 3900
                  //   Print "ADDITION", render stuff, beep.
                } else {
                  // GOSUB 3990
                  //   Print "DEPLETION", render stuff, beep.
                }

                // 1760

                int kk = k;
                k = pmc;
                if (kk == pmc) {
                  // 1910
                  continue;
                }

                if (npe <= 0) {
                  // 1920
                  break;
                }

                // 1790

                double rpe = cnpe - npe;
                dmc[k] = (int) ((15 * rpe) / cnpe) - dpmc;
                igmc = dmc[k];
                dpmc = dmc[k] + dpmc;
                dmc[k] = 0;

                // 1820 - GOSUB 1960

                ii = 0;
                mm = 0;
                ie += igmc;
                for (int i = ib; i <= ie; i++) {
                  iday[i] = k;
                  // Screen rendering.
                  if (i > 30) {
                    ii = (i % 30) - 1;
                  } else {
                    ii = i;
                  }
                  // 1990
                  mm = i / 30;
                  if (i % 30 == 0) {
                    mm = mm - 1;
                  }

                  if (ii == -1) {
                    ii = 29;
                  }

                  if (mm < 0) {
                    mm = 0;
                  }
                  // More screen rendering with
                  // a GOSUB 4460 call.
                }
                ib = ie + 1;
                nd[k] += igmc;

                // Return from GOSUB 1960, 1820
                pmc = kk;
                k = kk;
                continue;

              }
            }
          } else {
            // 1680
            if (npe <= 0) {
              // 1920
              break;
            } else {
              // 1690
              if (sl[i3] >= fsl) {
                // 1910
                continue;
              } else {
                // 1700

                double esl = fsl - sl[i3];
                if (esl >= npe) {
                  // 1740
                  sl[i3] = sl[i3] + npe;
                  npe = 0;
                  // 1750
                } else {
                  sl[i3] = fsl;
                  // 1720
                  npe = npe - esl;
                  // 1750
                }

                // 1750 - GOSUB 2880

                for (int i = 1; i <= 3; i++) {
                  cc[i] = false;
                }

                for (int i = 1; i <= 6; i++) {
                  pc[i] = false;
                }

                pc[1] = sl[9] <= 0;
                pc[2] = sl[17] <= 0;
                pc[3] = sl[25] <= 0;
                cc[1] = pc[1] && pc[2] && pc[3];
                cc[2] = !cc[1] && (pc[1] || pc[2] || pc[3]);
                pc[4] = sl[9] > 0;
                pc[5] = sl[17] > 0;
                pc[6] = sl[25] > 0;
                cc[3] = pc[4] && pc[5] && pc[6];

                for (int i = 1; i <= 3; i++) {
                  if (!cc[i]) {
                    continue;
                  } else {
                    k = i;
                    break;
                  }
                }

                // 1750 - Return from GOSUB 2880.

                if (zsw != 0) {
                  // GOSUB 3900
                  //   Print "ADDITION", render stuff, beep.
                } else {
                  // GOSUB 3990
                  //   Print "DEPLETION", render stuff, beep.
                }

                // 1760

                int kk = k;
                k = pmc;
                if (kk == pmc) {
                  // 1910
                  continue;
                }

                if (npe <= 0) {
                  // 1920
                  break;
                }

                // 1790

                double rpe = cnpe - npe;
                dmc[k] = (int) ((15 * rpe) / cnpe) - dpmc;
                igmc = dmc[k];
                dpmc = dmc[k] + dpmc;
                dmc[k] = 0;

                // 1820 - GOSUB 1960

                ii = 0;
                mm = 0;
                ie += igmc;
                for (int i = ib; i <= ie; i++) {
                  iday[i] = k;
                  // Screen rendering.
                  if (i > 30) {
                    ii = (i % 30) - 1;
                  } else {
                    ii = i;
                  }
                  // 1990
                  mm = i / 30;
                  if (i % 30 == 0) {
                    mm = mm - 1;
                  }

                  if (ii == -1) {
                    ii = 29;
                  }

                  if (mm < 0) {
                    mm = 0;
                  }
                  // More screen rendering with
                  // a GOSUB 4460 call.
                }
                ib = ie + 1;
                nd[k] += igmc;

                // Return from GOSUB 1960, 1820
                pmc = kk;
                k = kk;
                continue;
              }
            }
          }
        }
      }

      // 1920

      dmc[k] = 15 - dpmc;
      igmc = dmc[k];
      dmc[k] = 0;

      // GOSUB 1960

      ii = 0;
      mm = 0;
      ie += igmc;
      for (int i = ib; i <= ie; i++) {
        iday[i] = k;
        // Screen rendering.
        if (i > 30) {
          ii = (i % 30) - 1;
        } else {
          ii = i;
        }
        // 1990
        mm = i / 30;
        if (i % 30 == 0) {
          mm = mm - 1;
        }

        if (ii == -1) {
          ii = 29;
        }

        if (mm < 0) {
          mm = 0;
        }
        // More screen rendering with
        // a GOSUB 4460 call.
      }
      ib = ie + 1;
      nd[k] += igmc;

      // Return from GOSUB 1630

      // 610 - NEXT IM
      continue;
    }

    // 620 - Just some DATA.

    int sn = 0;
    int kj = 0;
    int ia = 0;
    int iz = 0;
    int nj[] = new int[14];
    double crr = 0;
    boolean c[] = new boolean[15];
    boolean tempUnderFive = false;
    boolean zwt = false;

    for (int i = 1; i <= 12; i++) {
      // Start examination of calendar.
      if (temperature[i] < 5) {
        // 670 - To determine season > 5C.
        tempUnderFive = true;
        break;
      }
      // 870 if finish without breaking.
    }

    boolean skipTo890 = false;
    double t13 = 0;

    if (tempUnderFive) {
      // 670
      t13 = temperature[1];
      for (int it = 1; it <= 11; it++) {
        if (temperature[it] == 5 && temperature[it + 1] == 5) {
          temperature[it] = 5.01;
        }
      }
      if (temperature[12] == 5 && t13 == 5) {
        temperature[12] = 5.01;
      }
      crr = 5;

      // 720 - GOSUB 2410

      for (int i = 1; i <= 12; i++) {
        nj[i] = 0;
      }

      zwt = false;
      kj = 1;
      ia = 30;
      iz = 30;

      if (crr != 8) {
        ia = 36;
        iz = 25;
      }

      for (int i = 1; i <= 12; i++) {
        for (int j = 1; j <= 14; j++) {
          c[j] = false;
        }

        // 2470

        int m0 = i - 1;
        int m1 = i;
        int m2 = i + 1;
        if (m2 > 12) {
          m2 = m2 - 12;
        }
        if (m0 < 1) {
          m0 = m0 + 12;
        }

        c[1] = temperature[m1] < crr;
        c[2] = temperature[m2] > crr;
        c[3] = temperature[m0] < crr;
        c[4] = temperature[m1] == crr;
        c[5] = temperature[m2] > crr;
        c[6] = temperature[m2] < crr;
        c[7] = temperature[m1] > crr;
        c[8] = temperature[m0] > crr;
        c[9] = c[1] && c[2];
        c[10] = c[3] && c[4] && c[5];
        c[11] = c[9] || c[10];
        c[12] = c[6] && c[7];
        c[13] = c[8] && c[6] && c[4];
        c[14] = c[12] || c[13];

        if (c[11]) {
          // 2590
          nj[kj] = (m0 * 30) + ia
                  + (int) (30 * (crr - temperature[m1]) / (temperature[m2] - temperature[m1]));
          if (nj[kj] > 360) {
            nj[kj] -= 360;
          }
          kj++;
          zwt = true;
          // 2650
          continue;
        } else if (c[14]) {
          // 2620
          nj[kj] = (m0 * 30) + iz
                  + (int) ((30 * (temperature[m1] - crr)) / (temperature[m1] - temperature[m2]));
          if (nj[kj] > 360) {
            nj[kj] -= 360;
          }
          kj++;
          zwt = false;
          // 2650
          continue;
        } else {
          // 2650
          continue;
        }
      }

      // 2660

      if (zwt) {
        // 2670
        int le = kj - 2;
        int npro = nj[1];
        for (int i = 1; i <= le; i++) {
          nj[i] = nj[i + 1];
        }
        nj[le + 1] = npro;
      }

      // 2700

      int npj = (kj - 1) / 2;
      int nbj[] = new int[7];
      int nej[] = new int[7];
      for (int i = 1; i <= npj; i++) {
        ib = 2 * i - 1;
        ie = 2 * i;
        nbj[i] = nj[ib];
        nej[i] = nj[ie];
      }

      // 720 - Return from GOSUB 2410.

      for (int i = 1; i <= 6; i++) {
        nbd[i] = nbj[i];
        ned[i] = nej[i];
      }

      np = npj;
      tc = -1;
      int jb = 0;
      double ir = 0.0;
      double jr = 0.0;
      double je = 0.0;

      if (np == 0) {
        // 840 - WHen no temp is above 5 degrees C.
        if (wt > 5) {
          // 870 - Break outside of IF block.
        } else {
          // 850
          lt5c = 0;
          nbd[1] = -1;
          id5c = 0;
          for (int ik = 1; ik <= 3; ik++) {
            nsd[ik] = 0;
          }
          tc = 0;
          skipTo890 = true;
          // 890 - Jump over block after IF block.
        }
      } else {
        // 760
        for (int i = 1; i <= np; i++) {
          ib = (int) nbd[i];  // Possible casting issue.
          if (nbd[i] < ned[i]) {
            // 790
            ir = ned[i] - nbd[i] + 1;
            jb = ib;
            jr = ir;
            // 800
          } else {
            // 770
            ir = 361 - nbd[i] + ned[i];
            jb = ib;
            jr = ir;
            // 800
          }
          // 800 - GOSUB 2380 - To calculate NSD.

          for (int ij = 1; ij <= 3; ij++) {
            nzd[ij] = 0;
          }

          je = jb + jr - 1;

          for (int l = jb; l <= je; l++) {
            int j = l;
            if (j > 360) {
              j = j - 360;
            }
            int ik = iday[j];
            nzd[ik] = nzd[ik] + 1;
          }

          // 810 - Return from GOSUB 2380.

          for (int ic = 1; ic <= 3; ic++) {
            nsd[ic] = nsd[ic] + nzd[ic];
          }

          lt5c = lt5c + ir;
          // Next i, then 830.
        }

        // 830

        id5c = nbd[1];
        skipTo890 = true;

        // 890, jump over region after IF block.

      }

    } else {
      // 870 continues below.
    }

    // 870

    if (!skipTo890) {
      tc = 0;
      for (int ic = 1; ic <= 3; ic++) {
        nsd[ic] = nd[ic];
      }
      lt5c = 360;
      id5c = 0;
    }

    // 890

    int[] ncpm = new int[4];
    for (int ic = 1; ic <= 2; ic++) {
      ncpm[ic] = 0;
    }

    boolean tempUnder8C = false;
    for (int ic = 1; ic <= 12; ic++) {
      if (temperature[ic] < 8) {
        // 930
        tempUnder8C = true;
        break;
      }
    }

    if (tempUnder8C) {
      // 930
      t13 = temperature[1];
      for (int it = 1; it <= 11; it++) {
        if (temperature[it] == 8 && temperature[it + 1] == 8) {
          temperature[it] = 8.01;
        }
      }

      crr = 8;
      if (temperature[12] == 8 && t13 == 8) {
        temperature[12] = 8.01;
      }

      // 980 - GOSUB 2410

      for (int i = 1; i <= 12; i++) {
        nj[i] = 0;
      }

      zwt = false;
      kj = 1;
      ia = 30;
      iz = 30;

      if (crr != 8) {
        ia = 36;
        iz = 25;
      }

      for (int i = 1; i <= 12; i++) {
        for (int j = 1; j <= 14; j++) {
          c[j] = false;
        }

        // 2470

        int m0 = i - 1;
        int m1 = i;
        int m2 = i + 1;
        if (m2 > 12) {
          m2 = m2 - 12;
        }
        if (m0 < 1) {
          m0 = m0 + 12;
        }

        c[1] = temperature[m1] < crr;
        c[2] = temperature[m2] > crr;
        c[3] = temperature[m0] < crr;
        c[4] = temperature[m1] == crr;
        c[5] = temperature[m2] > crr;
        c[6] = temperature[m2] < crr;
        c[7] = temperature[m1] > crr;
        c[8] = temperature[m0] > crr;
        c[9] = c[1] && c[2];
        c[10] = c[3] && c[4] && c[5];
        c[11] = c[9] || c[10];
        c[12] = c[6] && c[7];
        c[13] = c[8] && c[6] && c[4];
        c[14] = c[12] || c[13];

        if (c[11]) {
          // 2590
          nj[kj] = (m0 * 30) + ia
                  + (int) (30 * (crr - temperature[m1]) / (temperature[m2] - temperature[m1]));
          if (nj[kj] > 360) {
            nj[kj] -= 360;
          }
          kj++;
          zwt = true;
          // 2650
          continue;
        } else if (c[14]) {
          // 2620
          nj[kj] = (m0 * 30) + iz
                  + (int) ((30 * (temperature[m1] - crr)) / (temperature[m1] - temperature[m2]));
          if (nj[kj] > 360) {
            nj[kj] -= 360;
          }
          kj++;
          zwt = false;
          // 2650
          continue;
        } else {
          // 2650
          continue;
        }
      }

      // 2660

      if (zwt) {
        // 2670
        int le = kj - 2;
        int npro = nj[1];
        for (int i = 1; i <= le; i++) {
          nj[i] = nj[i + 1];
        }
        nj[le + 1] = npro;
      }

      // 2700

      int npj = (kj - 1) / 2;
      int nbj[] = new int[7];
      int nej[] = new int[7];
      for (int i = 1; i <= npj; i++) {
        ib = 2 * i - 1;
        ie = 2 * i;
        nbj[i] = nj[ib];
        nej[i] = nj[ie];
      }

      // 990 - Return from GOSUB 2410.

      for (int i = 1; i <= 6; i++) {
        nbd8[i] = nbj[i];
        ned8[i] = nej[i];
      }

      np8 = npj;
      tc = -1;

      // 1020

      if (np8 == 0) {
        // 1150 - When no temp is above 8 degrees C.
      } else {
        // 1030
        for (int i = 1; i <= np8; i++) {
          ib = (int) nbd8[i];
          double ir = 0.0;
          if (nbd8[i] < ned8[i]) {
            // 1070
            ir = ned8[i] - nbd8[i] + 1;
            // 1080
          } else {
            // 1060
            ir = 361 - nbd8[i] + ned8[i];
            // 1080
          }
          // 1080

          msw = 0;
          sib = ib;
          sir = ir;
          x = 1;
          swm = (msw != 0);

          // 1090 - GOSUB 2160

          int ns[] = new int[5];
          ns[x] = 0;
          int ifin = 0;
          int sw = 0;
          int si = 0;
          max = 0;
          double siz = sib + sir - 1;

          for (int n = sib; n <= siz; n++) {
            int n1 = n + 1;
            if (n1 > 360) {
              n1 = n1 - 360;
            }

            if (n > 360) {
              si = n - 360;
            } else {
              si = n;
            }

            if (swm) {
              // 2250
              if (iday[si] == x) {
                // 2270
                if (iday[si] != iday[n1]) {
                  // 2300
                  if (sw != 0) {
                    // 2320
                    ns[x] = ns[x] + 1;
                    if (ns[x] > max) {
                      // 2340
                      max = ns[x];
                      // 2330
                      ns[x] = 0;
                      sw = 0;
                      // 2350
                      continue;
                    } else {
                      // 2330
                      ns[x] = 0;
                      sw = 0;
                      // 2350
                      continue;
                    }
                  } else {
                    // 2310 -> 2350
                    continue;
                  }
                } else {
                  // 2280
                  ns[x] = ns[x] + 1;
                  sw = -1;
                  continue;
                }
              } else {
                // 2350
                continue;
              }
            } else {
              // 2210
              if (iday[si] != x) {
                // 2230
                if (iday[n1] == x) {
                  // 2300
                  if (sw != 0) {
                    // 2320
                    ns[x] = ns[x] + 1;
                    if (ns[x] > max) {
                      // 2340
                      max = ns[x];
                      // 2330
                      ns[x] = 0;
                      sw = 0;
                      // 2350
                      continue;
                    } else {
                      // 2330
                      ns[x] = 0;
                      sw = 0;
                      // 2350
                      continue;
                    }
                  } else {
                    // 2310 -> 2350
                    continue;
                  }
                } else {
                  // 2280
                  ns[x] = ns[x] + 1;
                  sw = -1;
                  continue;
                }
              } else {
                // 2350
                continue;
              }
            }

          }

          if (sw != 0) {
            ifin = ns[x];
          }

          if (ifin > max) {
            max = ifin;
          }

          // Return from GOSUB 2160. -> 1100

          icon = max;
          if (ncpm[2] > icon) {
            // 1130
            continue;
          } else {
            // 1110
            ncpm[2] = icon;
            lt8c = (int) ir;
            id8c = (int) nbd8[i];
            continue;
          }
        }

        // 'I' loop done, 1140.
        sn = -1;
        // 1180
      }
    } else {
      // 920 -> GOTO 1170
      msw = 0;
      //tc = 0;
      tc = -1;
      lt8c = 360;
      id8c = 0;
      // 1180
    }

    // 1180

    sib = 1;
    sir = 720;
    x = 1;
    swm = (msw != 0);

    // 1180 -> GOSUB 2160.

    int ns[] = new int[5];
    ns[x] = 0;
    int ifin = 0;
    int sw = 0;
    int si = 0;
    max = 0;
    double siz = sib + sir - 1;

    for (int n = sib; n <= siz; n++) {
      int n1 = n + 1;
      while (n1 > 360) {   // LOGIC CHANGE HERE, BEWARE.
        n1 = n1 - 360;
      }

      if (n > 360) {
        si = n - 360;
      } else {
        si = n;
      }

      if (swm) {
        // 2250
        if (iday[si] == x) {
          // 2270
          if (iday[si] != iday[n1]) {
            // 2300
            if (sw != 0) {
              // 2320
              ns[x] = ns[x] + 1;
              if (ns[x] > max) {
                // 2340
                max = ns[x];
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              } else {
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              }
            } else {
              // 2310 -> 2350
              continue;
            }
          } else {
            // 2280
            ns[x] = ns[x] + 1;
            sw = -1;
            continue;
          }
        } else {
          // 2350
          continue;
        }
      } else {
        // 2210
        if (iday[si] != x) {
          // 2230
          if (iday[n1] == x) {
            // 2300
            if (sw != 0) {
              // 2320
              ns[x] = ns[x] + 1;
              if (ns[x] > max) {
                // 2340
                max = ns[x];
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              } else {
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              }
            } else {
              // 2310 -> 2350
              continue;
            }
          } else {
            // 2280
            ns[x] = ns[x] + 1;
            sw = -1;
            continue;
          }
        } else {
          // 2350
          continue;
        }
      }

    }

    if (sw != 0) {
      ifin = ns[x];
    }

    if (ifin > max) {
      max = ifin;
    }

    // Return from GOSUB 2160 -> 1190

    icon = max;
    if (icon > 360) {
      icon = 360;
    }
    ncpm[1] = icon;
    if (sn == 0) {
      // 1230
      ncpm[2] = ncpm[1];
    }

    // 1240

    sn = 0;   // Almost done now, Xeric criteria.
    msw = -1;
    int ic = 0;

    if (dataset.getNsHemisphere() == 'N') {
      ib = 181;
      ic = 1;
    } else {
      ib = 1;
      ic = 181;
    }

    sib = ib;
    sir = 120;
    x = 1;
    swm = (msw != 0);

    // 1260 -> GOSUB 2160.

    ns = new int[5];
    ns[x] = 0;
    ifin = 0;
    sw = 0;
    si = 0;
    max = 0;
    siz = sib + sir - 1;

    for (int n = sib; n <= siz; n++) {
      int n1 = n + 1;
      if (n1 > 360) {
        n1 = n1 - 360;
      }

      if (n > 360) {
        si = n - 360;
      } else {
        si = n;
      }

      if (swm) {
        // 2250
        if (iday[si] == x) {
          // 2270
          if (iday[si] != iday[n1]) {
            // 2300
            if (sw != 0) {
              // 2320
              ns[x] = ns[x] + 1;
              if (ns[x] > max) {
                // 2340
                max = ns[x];
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              } else {
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              }
            } else {
              // 2310 -> 2350
              continue;
            }
          } else {
            // 2280
            ns[x] = ns[x] + 1;
            sw = -1;
            continue;
          }
        } else {
          // 2350
          continue;
        }
      } else {
        // 2210
        if (iday[si] != x) {
          // 2230
          if (iday[n1] == x) {
            // 2300
            if (sw != 0) {
              // 2320
              ns[x] = ns[x] + 1;
              if (ns[x] > max) {
                // 2340
                max = ns[x];
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              } else {
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              }
            } else {
              // 2310 -> 2350
              continue;
            }
          } else {
            // 2280
            ns[x] = ns[x] + 1;
            sw = -1;
            continue;
          }
        } else {
          // 2350
          continue;
        }
      }

    }

    if (sw != 0) {
      ifin = ns[x];
    }

    if (ifin > max) {
      max = ifin;
    }

    // Return from GOSUB 2160 -> 1270

    nccd = max;
    sib = ic;
    sir = 120;
    x = 3;
    swm = (msw != 0);

    // 1290 -> GOSUB 2160

    ns = new int[5];
    ns[x] = 0;
    ifin = 0;
    sw = 0;
    si = 0;
    max = 0;
    siz = sib + sir - 1;

    for (int n = sib; n <= siz; n++) {
      int n1 = n + 1;
      if (n1 > 360) {
        n1 = n1 - 360;
      }

      if (n > 360) {
        si = n - 360;
      } else {
        si = n;
      }

      if (swm) {
        // 2250
        if (iday[si] == x) {
          // 2270
          if (iday[si] != iday[n1]) {
            // 2300
            if (sw != 0) {
              // 2320
              ns[x] = ns[x] + 1;
              if (ns[x] > max) {
                // 2340
                max = ns[x];
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              } else {
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              }
            } else {
              // 2310 -> 2350
              continue;
            }
          } else {
            // 2280
            ns[x] = ns[x] + 1;
            sw = -1;
            continue;
          }
        } else {
          // 2350
          continue;
        }
      } else {
        // 2210
        if (iday[si] != x) {
          // 2230
          if (iday[n1] == x) {
            // 2300
            if (sw != 0) {
              // 2320
              ns[x] = ns[x] + 1;
              if (ns[x] > max) {
                // 2340
                max = ns[x];
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              } else {
                // 2330
                ns[x] = 0;
                sw = 0;
                // 2350
                continue;
              }
            } else {
              // 2310 -> 2350
              continue;
            }
          } else {
            // 2280
            ns[x] = ns[x] + 1;
            sw = -1;
            continue;
          }
        } else {
          // 2350
          continue;
        }
      }

    }

    if (sw != 0) {
      ifin = ns[x];
    }

    if (ifin > max) {
      max = ifin;
    }

    // Return from GOSUB 2160 -> 1290

    nccm = max;
    int tu = 0;
    boolean skipTo1420 = false;

    if (nd[3] == 360) {
      // 1390
      ncsm = 180;
      ncwm = 180;
      ncsp = 180;
      ncwp = 180;
      ntsu[3] = 180;
      ntwi[3] = 180;
      // 1420
      skipTo1420 = true;
    } else if (nd[1] == 360) {
      // 1420
      skipTo1420 = true;
    } else if (nd[1] == 0) {
      // 1360
      tu = -1;
      sib = ib;
      x = 3;
      swm = (tu != 0);

      // 1370 -> GOSUB 2160.

      ns = new int[5];
      ns[x] = 0;
      ifin = 0;
      sw = 0;
      si = 0;
      max = 0;
      siz = sib + sir - 1;

      for (int n = sib; n <= siz; n++) {
        int n1 = n + 1;
        if (n1 > 360) {
          n1 = n1 - 360;
        }

        if (n > 360) {
          si = n - 360;
        } else {
          si = n;
        }

        if (swm) {
          // 2250
          if (iday[si] == x) {
            // 2270
            if (iday[si] != iday[n1]) {
              // 2300
              if (sw != 0) {
                // 2320
                ns[x] = ns[x] + 1;
                if (ns[x] > max) {
                  // 2340
                  max = ns[x];
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                } else {
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                }
              } else {
                // 2310 -> 2350
                continue;
              }
            } else {
              // 2280
              ns[x] = ns[x] + 1;
              sw = -1;
              continue;
            }
          } else {
            // 2350
            continue;
          }
        } else {
          // 2210
          if (iday[si] != x) {
            // 2230
            if (iday[n1] == x) {
              // 2300
              if (sw != 0) {
                // 2320
                ns[x] = ns[x] + 1;
                if (ns[x] > max) {
                  // 2340
                  max = ns[x];
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                } else {
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                }
              } else {
                // 2310 -> 2350
                continue;
              }
            } else {
              // 2280
              ns[x] = ns[x] + 1;
              sw = -1;
              continue;
            }
          } else {
            // 2350
            continue;
          }
        }

      }

      if (sw != 0) {
        ifin = ns[x];
      }

      if (ifin > max) {
        max = ifin;
      }

      // Return from GOSUB 2160.

      ncsp = max;
      sib = ic;
      sir = 180;
      swm = (tu != 0);

      // 1380 -> GOSUB 2160.

      ns = new int[5];
      ns[x] = 0;
      ifin = 0;
      sw = 0;
      si = 0;
      max = 0;
      siz = sib + sir - 1;

      for (int n = sib; n <= siz; n++) {
        int n1 = n + 1;
        if (n1 > 360) {
          n1 = n1 - 360;
        }

        if (n > 360) {
          si = n - 360;
        } else {
          si = n;
        }

        if (swm) {
          // 2250
          if (iday[si] == x) {
            // 2270
            if (iday[si] != iday[n1]) {
              // 2300
              if (sw != 0) {
                // 2320
                ns[x] = ns[x] + 1;
                if (ns[x] > max) {
                  // 2340
                  max = ns[x];
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                } else {
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                }
              } else {
                // 2310 -> 2350
                continue;
              }
            } else {
              // 2280
              ns[x] = ns[x] + 1;
              sw = -1;
              continue;
            }
          } else {
            // 2350
            continue;
          }
        } else {
          // 2210
          if (iday[si] != x) {
            // 2230
            if (iday[n1] == x) {
              // 2300
              if (sw != 0) {
                // 2320
                ns[x] = ns[x] + 1;
                if (ns[x] > max) {
                  // 2340
                  max = ns[x];
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                } else {
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                }
              } else {
                // 2310 -> 2350
                continue;
              }
            } else {
              // 2280
              ns[x] = ns[x] + 1;
              sw = -1;
              continue;
            }
          } else {
            // 2350
            continue;
          }
        }

      }

      if (sw != 0) {
        ifin = ns[x];
      }

      if (ifin > max) {
        max = ifin;
      }

      // Return from GOSUB 2160.

      ncwp = max;

      // 1400

    } else {
      // 1330
      tu = 0;
      sib = ib;
      sir = 180;
      x = 1;
      swm = (tu != 0);
      // 1340 -> GOSUB 2160.

      ns = new int[5];
      ns[x] = 0;
      ifin = 0;
      sw = 0;
      si = 0;
      max = 0;
      siz = sib + sir - 1;

      for (int n = sib; n <= siz; n++) {
        int n1 = n + 1;
        if (n1 > 360) {
          n1 = n1 - 360;
        }

        if (n > 360) {
          si = n - 360;
        } else {
          si = n;
        }

        if (swm) {
          // 2250
          if (iday[si] == x) {
            // 2270
            if (iday[si] != iday[n1]) {
              // 2300
              if (sw != 0) {
                // 2320
                ns[x] = ns[x] + 1;
                if (ns[x] > max) {
                  // 2340
                  max = ns[x];
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                } else {
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                }
              } else {
                // 2310 -> 2350
                continue;
              }
            } else {
              // 2280
              ns[x] = ns[x] + 1;
              sw = -1;
              continue;
            }
          } else {
            // 2350
            continue;
          }
        } else {
          // 2210
          if (iday[si] != x) {
            // 2230
            if (iday[n1] == x) {
              // 2300
              if (sw != 0) {
                // 2320
                ns[x] = ns[x] + 1;
                if (ns[x] > max) {
                  // 2340
                  max = ns[x];
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                } else {
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                }
              } else {
                // 2310 -> 2350
                continue;
              }
            } else {
              // 2280
              ns[x] = ns[x] + 1;
              sw = -1;
              continue;
            }
          } else {
            // 2350
            continue;
          }
        }

      }

      if (sw != 0) {
        ifin = ns[x];
      }

      if (ifin > max) {
        max = ifin;
      }

      // Return from GOSUB 2160.
      ncsm = max;
      sib = ic;
      // 1350 -> GOSUB 2160.

      ns = new int[5];
      ns[x] = 0;
      ifin = 0;
      sw = 0;
      si = 0;
      max = 0;
      siz = sib + sir - 1;

      for (int n = sib; n <= siz; n++) {
        int n1 = n + 1;
        if (n1 > 360) {
          n1 = n1 - 360;
        }

        if (n > 360) {
          si = n - 360;
        } else {
          si = n;
        }

        if (swm) {
          // 2250
          if (iday[si] == x) {
            // 2270
            if (iday[si] != iday[n1]) {
              // 2300
              if (sw != 0) {
                // 2320
                ns[x] = ns[x] + 1;
                if (ns[x] > max) {
                  // 2340
                  max = ns[x];
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                } else {
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                }
              } else {
                // 2310 -> 2350
                continue;
              }
            } else {
              // 2280
              ns[x] = ns[x] + 1;
              sw = -1;
              continue;
            }
          } else {
            // 2350
            continue;
          }
        } else {
          // 2210
          if (iday[si] != x) {
            // 2230
            if (iday[n1] == x) {
              // 2300
              if (sw != 0) {
                // 2320
                ns[x] = ns[x] + 1;
                if (ns[x] > max) {
                  // 2340
                  max = ns[x];
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                } else {
                  // 2330
                  ns[x] = 0;
                  sw = 0;
                  // 2350
                  continue;
                }
              } else {
                // 2310 -> 2350
                continue;
              }
            } else {
              // 2280
              ns[x] = ns[x] + 1;
              sw = -1;
              continue;
            }
          } else {
            // 2350
            continue;
          }
        }

      }

      if (sw != 0) {
        ifin = ns[x];
      }

      if (ifin > max) {
        max = ifin;
      }

      // Return from GOSUB 2160.
      ncwm = max;
      // 1400
    }

    int jb = 0;
    int jr = 0;
    int je = 0;
    if (!skipTo1420) {
      // 1400
      jb = ib;
      jr = 180;
      // GOSUB 2380.

      for (int ij = 1; ij <= 3; ij++) {
        nzd[ij] = 0;
      }

      je = jb + jr - 1;

      for (int l = jb; l <= je; l++) {
        int j = l;
        if (j > 360) {
          j = j - 360;
        }
        int ik = iday[j];
        nzd[ik] = nzd[ik] + 1;
      }

      // Return from GOSUB 2380.
      for (int i = 1; i <= 3; i++) {
        ntsu[i] = nzd[i];
      }
      for (int i = 1; i <= 3; i++) {
        ntwi[i] = nd[i] - ntsu[i];
      }
      // 1420
    }

    // 1420
    int ntd[] = new int[365];

    if(!tempUnder8C) {
      // Fill with 8's.  Previous detection nullified
      // the following if() block, so we need to populate
      // for this edge case here.
      for(int i = 1; i <= 360; i++) ntd[i] = '8';
    }

    if (tc != 0 || tu != 0) {
      // GOSUB 2990 - Calculate calendar.

      char[] kl = new char[4];    // Dimensions unknown.
      double[] kr = new double[25];
      int[] m = new int[25];
      int kt = 0;

      kl[0] = '$';
      kl[1] = '-';
      kl[2] = '5';
      kl[3] = '8';

      for (int i = 1; i <= 24; i++) {
        kr[i] = 0;
      }

      if (nbd[1] < 0 && nbd8[1] < 0) {
        // 3350
        for (int i = 1; i <= 360; i++) {
          ntd[i] = kl[1];
        }
        // 3380 -> Return from GOSUB 2990.
      } else if (nbd[1] == 0 && nbd8[1] < 0) {
        // 3370
        for (int i = 1; i <= 360; i++) {
          ntd[i] = kl[2];
        }
        // 3380 -> Return from GOSUB 2990.
      } else {
        // 3020
        if (nbd8[1] < 0) {
          nbd8[1] = 0;
        }

        for (int i = 1; i <= 6; i++) {
          kr[i] = nbd[i];
          m[i] = 2;
        }

        for (int i = 7; i <= 12; i++) {
          kt = i - 6;
          if (ned[kt] == 0) {
            // 3070
            continue;
          } else {
            // 3050
            kr[i] = ned[kt] + 1;
            if (kr[i] > 360) {
              kr[i] = 1;
            }
            m[i] = 1;
            continue;
          }
        }

        // 3080

        for (int i = 13; i <= 18; i++) {
          kt = i - 12;
          kr[i] = nbd8[kt];
          m[i] = 3;
        }

        for (int i = 19; i <= 24; i++) {
          kt = i - 18;
          if (ned8[kt] == 0) {
            // 3130
            continue;
          } else {
            // 3100
            kr[i] = ned8[kt] + 1;
            if (kr[i] > 360) {
              kr[i] = 1;
            }
            m[i] = 2;
            continue;
          }
        }

        // 3140

        int nt = 23;
        int stt = 0;
        double itemp = 0;
        int itm = 0;

        for (int i = 1; i <= 23; i++) {
          stt = 0;

          for (int j = 1; j <= nt; j++) {
            if (kr[j] <= kr[j + 1]) {
              // 3180
              continue;
            } else {
              // 3160
              itemp = kr[j];
              itm = m[j];
              kr[j] = kr[j + 1];
              m[j] = m[j + 1];
              kr[j + 1] = itemp;
              m[j + 1] = itm;
              stt = -1;
              continue;
            }
          }

          // 3180

          if (stt == 0) {
            // 3210
            break;
          } else {
            // 3190
            nt = nt - 1;
            continue;
          }
        }

        // 3210

        int ima = m[24];
        int nul = 0;

        for (int i = 1; i <= 24; i++) {
          if (kr[i] == 0) {
            nul++;
          }
        }

        int kk = 0;
        for (int i = 1; i <= 24; i++) {
          kk = i;
          int ipl = i + nul;
          if (ipl > 24) {
            // 3250
            break;
          } else {
            // 3240
            kr[i] = kr[ipl];
            m[i] = m[ipl];
            continue;
          }
        }

        // 3250

        for (int i = kk; i <= 24; i++) {
          kr[i] = 0;
        }

        if (kr[1] == 1) {
          // 3290
        } else {
          // 3270
          ie = (int) (kr[1] - 1);
          for (int i = 1; i <= ie; i++) {
            ntd[i] = kl[ima];
          }
        }

        // 3290

        int ns2 = 0;        // Original NS is int[], now used as int?
        int nn = 24 - nul;
        for (int i = 1; i <= nn; i++) {
          ns2 = m[i];
          ib = (int) kr[i];
          ie = (int) (kr[i + 1] - 1);
          if (kr[i + 1] == 0) {
            ie = 360;
          }

          for (int j = ib; j <= ie; j++) {
            ntd[j] = kl[ns2];
          }
        }

        // 3380 -> RETURN
      }

      // Return from GOSUB 2990 if invoked at 1420.
    }

    // 1430 -> GOSUB 3390

    String ans = " ";
    String div = " ";
    String q = " ";
    if (swt != 0) {
      ans = "Perudic";
      // 3570
      ncsm = 180;
      ncwm = 180;
      ncsp = 180;
      ncwp = 180;
      ntsu[3] = 180;
      ntwi[3] = 180;
      // Return from GOSUB 3390.
    } else if (nsd[1] > (lt5c / 2) && ncpm[2] < 90) {
      ans = "Aridic";
      // 3450
      if (nd[1] == 360) {
        q = "Extreme";
      }
      // 3560
      div = new String(ans);  // Deep copy.
      // Return from GOSUB 3390.
    } else if (tma < 22 && dif >= 5 && nccd >= 45 && nccm >= 45) {
      ans = "Xeric";
      // 3470
      ncsm = 180;
      ncwm = 180;
      ncsp = 180;
      ncwp = 180;
      ntsu[3] = 180;
      ntwi[3] = 180;
      // Return from GOSUB 3390.
    } else if ((nd[1] + nd[2]) < 90) {
      ans = "Udic";
      // 3480
      if((nd[1] + nd[2]) < 30) {
        q = "Typic";
        // 3560
        div = new String(ans);
        // 3580 - Return from GOSUB 3390.
      } else {
        // 3490
        if(dif < 5) {
          q = "Dry";
          div = "Tropudic";
          // 3580 - Return from GOSUB 3390.
        } else {
          // 3500
          div = "Tempudic";
          q = "Dry";
          // 3680 - Return from GOSUB 3390.
        }
      }
    } else if (!trr.equalsIgnoreCase("pergelic") && !trr.equalsIgnoreCase("cryic")) {
      ans = "Ustic";
      // 3510
      if (dif >= 5) {
        div = "Tempustic";
        // 3520
        if (nccm <= 45) {
          q = "Typic";
        } else if (nccd > 45) {
          q = "Xeric";
        } else {
          q = "Wet";
        }
        // Return from GOSUB 3390.
      } else {
        // 3540
        if (ncpm[2] < 180) {
          q = "Aridic";
        } else if (ncpm[2] < 270) {
          q = "Typic";
        } else {
          q = "Udic";
        }
        // 3550
        div = "Tropustic";
        // Return from GOSUB 3390.
      }
    } else {
      ans = "Undefined";
      div = new String(ans);
      // Return from GOSUB 3390.
    }

    // Return from GOSUB 3390 -> 1430

    // Original CC is boolean, cc2 is String?  Also c is boolean?
    byte[] cc2Bytes = {7, 0, 7, 0, 7};
    String cc2 = new String(cc2Bytes);
    int c2 = 0;  // Seems to be index of '.' char in filename.

    // Open for FLX file export.
    // Write:
    //    ans, id5c, lt5c, id8c, lt8c, nccd, nccm, ncsm, ncwm, div, q
    //    ncsp, ncwp, tc, tu, swt, swp
    //    for i from 1 to 360:
    //      iday[i],
    //    whc
    //    for i from 1 to 6:
    //      nbd[i], ned[i], nbd8[i], ned8[i]
    //    for i from 1 to 3:
    //      nd[i], nzd[i], nsd[i], ntsu[i], ntwi[i]
    //    ncpm[1], ncpm[2]
    //    for i from 1 to 5:
    //      cd[i]
    //    if(!tc && !tu) skip the following, otherwise:
    //      for i from 1 to 360:
    //        ntd[i],
    // Close file.

    // "Proceed to next step?" -> STORVAR6

    String flxFile = "\"" + dataset.getName() + "\",\"" + dataset.getCountry() + "\"," + dataset.getLatitudeDegrees() + ",";
    flxFile += dataset.getLatitudeMinutes() + "," + dataset.getNsHemisphere() + "," + dataset.getLongitudeDegrees();
    flxFile += "," + dataset.getLongitudeMinutes() + "," + dataset.getEwHemisphere() + "," + dataset.getElevation();
    flxFile += "," + arf + "," + aev + ",\"" + trr + "\"," + tma + "," + st + "," + wt + "," + dif + ",";
    flxFile += dataset.getStartYear() + "," + dataset.getEndYear() + "\n";

    for (int i = 1; i <= 12; i++) {
      flxFile += precip[i] + "," + temperature[i];
      flxFile += "," + mpe[i] + "\n";
    }

    flxFile += "\"" + ans + "\"," + id5c + "," + lt5c + "," + id8c + "," + lt8c + ",";
    flxFile += nccd + "," + nccm + "," + ncsm + "," + ncwm + ",\"" + div + "\",\"" + q + "\"\n";
    flxFile += ncsp + "," + ncwp + "," + tc + "," + tu + "," + swt + "," + swp + "\n";

    for (int i = 1; i < 360; i++) {
      flxFile += iday[i] + ",";
    }
    flxFile += iday[360] + "," + whc + "\n";

    for (int i = 1; i <= 6; i++) {
      flxFile += nbd[i] + "," + ned[i] + "," + nbd8[i] + "," + ned8[i] + "\n";
    }

    for (int i = 1; i <= 3; i++) {
      flxFile += nd[i] + "," + nzd[i] + "," + nsd[i] + "," + ntsu[i] + "," + ntwi[i] + "\n";
    }

    flxFile += ncpm[1] + "," + ncpm[2] + "\n";

    for (int i = 1; i <= 5; i++) {
      flxFile += cd[i] + "\n";
    }

    for (int i = 1; i < 360; i++) {
      flxFile += (char) ntd[i] + ",";
    }

    // End of simulation model run.

    List<Double> soilTempCalendar = computeSoilCalendar(temperature, BASICSimulationModelConstants.lagPhaseSummer,
            BASICSimulationModelConstants.lagPhaseWinter, (dataset.getNsHemisphere() == 'N'), fcd);

    // Water balance calculations.

    double awb = computeWaterBalance(precip, mpe, false, (dataset.getNsHemisphere() == 'N'));
    double swb = computeWaterBalance(precip, mpe, true, (dataset.getNsHemisphere() == 'N'));

    return new NewhallResults(arf, whc, mpe, nccd, nccm, ntd, iday, nd, nsd,
            ncpm, trr, ans, q, div, awb, swb, soilTempCalendar, flxFile);

  }

  private static double computeWaterBalance(double[] precip, double[] evap, boolean onlySummer, boolean northernHemisphere) {
    double runningBalance = 0.0;

    if (onlySummer) {
      if (northernHemisphere) {
        for (int i = 6; i <= 8; i++) {
          runningBalance += precip[i];
          runningBalance -= evap[i];
        }
      } else {
        runningBalance += precip[12];
        runningBalance -= evap[12];
        runningBalance += precip[1];
        runningBalance -= evap[1];
        runningBalance += precip[2];
        runningBalance -= evap[2];
      }
    } else {
      for (int i = 1; i <= 12; i++) {
        runningBalance += precip[i];
        runningBalance -= evap[i];
      }
    }

    return runningBalance;
  }

  private static List<Double> computeSoilCalendar(double[] airTemps, int summerLagPhase, int fallLagPhase, boolean northernHemisphere, double amplitude) {

    /**
     * Compute soil temperatures using BASIC version's lag phases with C++ version's
     * method to compute soil temperatures by utilizing lag phases.  BASIC version assumes
     * 21 days in the summer and 10 days in the winter for lag phases elsewhere, so to
     * continue to use that assumption here seems reasonable.
     * 
     * Lag phases are dependant on the structure of the soil in the area, and these
     * assumptions should be recognized for their limitations in any results.
     */
    double yearAverage = 0.0;
    for (double month : airTemps) {
      yearAverage += month;
    }
    yearAverage /= 12.0;

    double summerAverage = airTemps[5] + airTemps[6] + airTemps[7];
    summerAverage /= 3.0;

    double winterAverage = airTemps[11] + airTemps[0] + airTemps[1];
    winterAverage /= 3.0;

    double a = Math.abs(summerAverage - winterAverage) / 2.0 * amplitude;
    double w = 2.0 * Math.PI / 360;

    ArrayList<Double> soilTempCalendarUnshifted = new ArrayList<Double>(360);

    for (int i = 0; i < 360; i++) {
      if (northernHemisphere) {

        if (i >= 90 && i < 270) {
          soilTempCalendarUnshifted.add(i, yearAverage + a * Math.sin(w * (i + summerLagPhase)));
        } else {
          soilTempCalendarUnshifted.add(i, yearAverage + a * Math.sin(w * (i + winterAverage)));
        }

      } else {

        if (i >= 90 && i < 270) {
          soilTempCalendarUnshifted.add(i, yearAverage + a * Math.cos(w * (i + summerLagPhase)));
        } else {
          soilTempCalendarUnshifted.add(i, yearAverage + a * Math.cos(w * (i + winterAverage)));
        }

      }
    }

    ArrayList<Double> soilTempCalendar = new ArrayList<Double>(360);

    for (int i = 0; i < 134; i++) {
      soilTempCalendar.add(i, soilTempCalendarUnshifted.get(i + 226));
    }

    for (int i = 134; i < 360; i++) {
      soilTempCalendar.add(i, soilTempCalendarUnshifted.get(i - 134));
    }

    return soilTempCalendar;
  }

  private BASICSimulationModel() {
    // Should not be instantiated.
  }
}
