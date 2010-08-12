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

    for(int i = 1; i <= 3; i++) {
      cc[i] = false;
    }

    boolean[] pc = new boolean[7];
    for(int i = 1; i <= 6; i++) {
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

    for(int i = 1; i <= 3; i++) {
      if(!cc[i]) {
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

    for(int im = 1; im < 12; im++) {
      
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
      double npe = (lp - mpe[im])/2;
      double cnpe = 0;
      boolean skipi3Loop = false;

      if(npe < 0) {
        npe = -npe;
        cnpe = npe;
      } else if(npe == 0) {
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
            if(npe <= 0) {
              // 1920
              break;
            } else {
              // 1850
              int nr = BASICSimulationModelConstants.dp[i3];
              if(sl[nr] <= 0) {
                // 1910
                continue;
              } else {
                // 1870
                double rpd = sl[nr] * BASICSimulationModelConstants.dr[i3];
                if(npe <= rpd) {
                  // 1890
                  sl[nr] -= npe / BASICSimulationModelConstants.dr[i3];
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
                for(int i = ib; i <= ie; i++) {
                  iday[i] = k;
                  // Screen rendering.
                  if(i > 30) {
                    ii = (i % 30) - 1;
                  } else {
                    ii = i;
                  }
                  // 1990
                  mm = i / 30;
                  if(i % 30 == 0) {
                    mm = mm - 1;
                  }

                  if(ii == -1) {
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
                for(int i = ib; i <= ie; i++) {
                  iday[i] = k;
                  // Screen rendering.
                  if(i > 30) {
                    ii = (i % 30) - 1;
                  } else {
                    ii = i;
                  }
                  // 1990
                  mm = i / 30;
                  if(i % 30 == 0) {
                    mm = mm - 1;
                  }

                  if(ii == -1) {
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
              int nr = BASICSimulationModelConstants.dp[i3];
              if (sl[nr] <= 0) {
                // 1910
                continue;
              } else {
                // 1870
                double rpd = sl[nr] * BASICSimulationModelConstants.dr[i3];
                if (npe <= rpd) {
                  // 1890
                  sl[nr] -= npe / BASICSimulationModelConstants.dr[i3];
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

    for(int i = 1; i <= 12; i++) {
      // Start examination of calendar.
      if(temperature[i] < 5) {
        // 670 - To determine season > 5C.
        tempUnderFive = true;
        break;
      }
      // 870 if finish without breaking.
    }

    boolean skipTo890 = false;
    double t13 = 0;

    if(tempUnderFive) {
      // 670
      t13 = temperature[1];
      for(int it = 1; it <= 11; it++) {
        if(temperature[it] == 5 && temperature[it+1] == 5) {
          temperature[it] = 5.01;
        }
      }
      if(temperature[12] == 5 && t13 == 5) {
        temperature[12] = 5.01;
      }
      crr = 5;

      // 720 - GOSUB 2410

      for(int i = 1; i <= 12; i++) {
        nj[i] = 0;
      }

      zwt = false;
      kj = 1;
      ia = 30;
      iz = 30;

      if(crr != 8) {
        ia = 36;
        iz = 25;
      }

      for(int i = 1; i <= 12; i++) {
        for(int j = 1; j <= 14; j++) {
          c[j] = false;
        }

        // 2470

        int m0 = i - 1;
        int m1 = i;
        int m2 = i + 1;
        if(m2 > 12) {
          m2 = m2 - 12;
        }
        if(m0 < 1) {
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

        if(c[11]) {
          // 2590
          nj[kj] = (m0 * 30) + ia +
            (int)((crr - temperature[m1])/(temperature[m2] - temperature[m1]));
          if(nj[kj] > 360) {
            nj[kj] -= 360;
          }
          kj++;
          zwt = true;
          // 2650
          continue;
        } else if(c[14]) {
          // 2620
          nj[kj] = (m0 * 30) + iz +
            (int)((30*(temperature[m1]-crr))/(temperature[m1] - temperature[m2]));
          if(nj[kj] > 360) {
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

      if(zwt) {
        // 2670
        int le = kj - 2;
        int npro = nj[1];
        for(int i = 1; i <= le; i++) {
          nj[i] = nj[i+1];
        }
        nj[le+1] = npro;
      }

      // 2700

      int npj = (kj - 1) / 2;
      int nbj[] = new int[npj+2];
      int nej[] = new int[npj+2];
      for(int i = 1; i <= npj; npj++) {
        ib = 2*i - 1;
        ie = 2*i;
        nbj[i] = nj[ib];
        nej[i] = nj[ie];
      }

      // 720 - Return from GOSUB 2410.

      for(int i = 1; i <= 6; i++) {
        nbd[i] = nbj[i];
        ned[i] = nej[i];
      }

      np = npj;
      tc = -1;
      int jb = 0;
      double ir = 0.0;
      double jr = 0.0;
      double je = 0.0;

      if(np == 0) {
        // 840 - WHen no temp is above 5 degrees C.
        if(wt > 5) {
          // 870 - Break outside of IF block.
        } else {
          // 850
          lt5c = 0;
          nbd[1] = -1;
          id5c = 0;
          for(int ik = 1; ik <= 3; ik++) {
            nsd[ik] = 0;
          }
          tc = 0;
          skipTo890 = true;
          // 890 - Jump over block after IF block.
        }
      } else {
        // 760
        for(int i = 1; i <= np; i++) {
          ib = (int)nbd[i];  // Possible casting issue.
          if(nbd[i] < ned[i]) {
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

          for(int ij = 1; ij <= 3; ij++) {
            nzd[ij] = 0;
          }

          je = jb + jr - 1;
          
          for(int l = jb; l <= je; l++) {
            int j = l;
            if(j > 360) {
              j = j - 360;
            }
            int ik = iday[j];
            nzd[ik] = nzd[ik] + 1;
          }

          // 810 - Return from GOSUB 2380.

          for(int ic = 1; ic <- 3; ic++) {
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

    if(!skipTo890) {
      tc = 0;
      for(int ic = 1; ic <= 3; ic++) {
        nsd[ic] = nd[ic];
      }
      lt5c = 360;
      id5c = 0;
    }

    // 890

    int[] ncpm = new int[4];
    for(int ic = 1; ic <= 2; ic++) {
      ncpm[ic] = 0;
    }

    boolean tempUnder8C = false;
    for(int ic = 1; ic <= 12; ic++) {
      if(temperature[ic] < 8) {
        // 930
        tempUnder8C = true;
        break;
      }
    }
    
    if(tempUnder8C) {
      // 930
      t13 = temperature[1];
      for(int it = 1; it <= 11; it++) {
        if(temperature[it] == 8 && temperature[it+1] == 8) {
          temperature[it] = 8.01;
        }
      }

      crr = 8;
      if(temperature[12] == 8 && t13 == 8) {
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
                  + (int) ((crr - temperature[m1]) / (temperature[m2] - temperature[m1]));
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
      int nbj[] = new int[npj + 2];
      int nej[] = new int[npj + 2];
      for (int i = 1; i <= npj; npj++) {
        ib = 2 * i - 1;
        ie = 2 * i;
        nbj[i] = nj[ib];
        nej[i] = nj[ie];
      }

      // 990 - Return from GOSUB 2410.

      for(int i = 1; i <= 6; i++) {
        nbd8[i] = nbj[i];
        ned8[i] = nej[i];
      }

      np8 = npj;
      tc = -1;

      // 1020

      if(np8 == 0) {
        // 1150 - When no temp is above 8 degrees C.
      } else {
        // 1030
        for(int i = 1; i <= np8; i++) {
          ib = (int) nbd8[i];
          double ir = 0.0;
          if(nbd8[i] < ned8[i]) {
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
          
          for(int n = sib; n <= siz; n++) {
            int n1 = n+1;
            if(n1 > 360) {
              n1 = n1 - 360;
            }

            if(n > 360) {
              si = n - 360;
            } else {
              si = n;
            }

            if(swm) {
              // 2250
              if(iday[si] == x) {
                // 2270
                if(iday[si] != iday[n1]) {
                  // 2300
                  if(sw != 0) {
                    // 2320
                    ns[x] = ns[x] + 1;
                    if(ns[x] > max) {
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
              if(iday[si] != x) {
                // 2230
                if(iday[n1] == x) {
                  // 2300
                  if(sw != 0) {
                    // 2320
                    ns[x] = ns[x] + 1;
                    if(ns[x] > max) {
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

          if(sw != 0) {
            ifin = ns[x];
          }

          if(ifin > max) {
            max = ifin;
          }

          // Return from GOSUB 2160. -> 1100

          icon = max;
          if(ncpm[2] > icon) {
            // 1130
            continue;
          } else {
            // 1110
            ncpm[2] = icon;
            lt8c = (int)ir;
            id8c = (int)nbd8[i];
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
      tc = 0;
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

    // Return from GOSUB 2160 -> 1190

    icon = max;
    if(icon > 360) {
      icon = 360;
    }
    ncpm[1] = icon;
    if(sn == 0) {
      // 1230
      ncpm[2] = ncpm[1];
    }

    // 1240

    sn = 0;   // Almost done now, Xeric criteria.
    msw = -1;
    int ic = 0;

    if(dataset.getNsHemisphere() == 'N') {
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

    if(nd[3] == 360) {
      // 1390
      ncsm = 180;
      ncwm = 180;
      ncsp = 180;
      ncwp = 180;
      ntsu[3] = 180;
      ntwi[3] = 180;
      // 1420
      skipTo1420 = true;
    } else if(nd[1] == 360) {
      // 1420
      skipTo1420 = true;
    } else if(nd[1] == 0) {
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
    if(!skipTo1420) {
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
      for(int i = 1; i <= 3; i++) {
        ntsu[i] = nzd[i];
      }
      for(int i = 1; i <= 3; i++) {
        ntwi[i] = nd[i] = ntsu[i];
      }
      // 1420
    }

    // 1420


    if(tc != 0 || tu != 0) {
      // GOSUB 2990 - Calculate calendar.

      char[] kl = new char[25];    // Dimensions unknown.
      double[] kr = new double[25];
      int[] m = new int[25];

      kl[0] = '$';
      kl[1] = '-';
      kl[2] = '5';
      kl[3] = '8';

      for(int i = 1; i <= 24; i++) {
        kr[i] = 0;
      }

      if(nbd[1] < 0 && nbd8[1] < 0) {
        // 3350
      } else if(nbd[1] == 0 && nbd8[1] < 0) {
        // 3370
      } else {
        // 3020
        if(nbd8[1] < 0) {
          nbd8[1] = 0;
        }

        for(int i = 1; i <= 6; i++) {
          kr[i] = nbd[i];
          m[i] = 2;
        }

        for(int i = 7; i <= 12; i++) {
          // 3040
        }
      }

      // Return from GOSUB 2990.
    }

    // End of simulation model run.

  }

  private BASICSimulationModel() {
    // Should not be instantiated.
  }
  
}
