package org.psu.newhall.sim;

public class BASICSimulationModelConstants {

  public static final String[] tempRegimes = {"Pregelic", "Cryic", "Frigid", "Mesic",
    "Thermic", "Hyperthermic", "Isofrigid", "Isomesic", "Isothermic",
    "Isohyperthermic"};

  public static final double[] zpe = {135.0, 139.5, 143.7, 147.8, 151.7, 155.4, 158.9, 162.1,
    165.2, 168.0, 170.7, 173.1, 175.3, 177.2, 179.0, 180.5, 181.8, 182.9,
    183.7, 184.3, 184.7, 184.9, 185.0, 185.0};

  public static final double[] zt = {26.5, 27.0, 27.5, 28.0, 28.5, 29.0, 29.5, 30.0, 30.5, 31.0,
    31.5, 32.0, 32.5, 33.0, 33.5, 34.0, 34.5, 35.0, 35.5, 36.0, 36.5,
    37.0, 37.5, 38.0};

  public static final double[][] inz = {
    {1.04, 1.02, 1.0, 0.97, 0.95, 0.93, 0.92, 0.92, 0.91, 0.91, 0.9, 0.9, 0.89, 0.88, 0.88, 0.87, 0.87, 0.86, 0.85, 0.85, 0.84, 0.83, 0.82, 0.81, 0.81, 0.8, 0.79, 0.77, 0.76, 0.75, 0.74},
    {0.94, 0.93, 0.91, 0.91, 0.9, 0.89, 0.88, 0.88, 0.88, 0.87, 0.87, 0.87, 0.86, 0.86, 0.85, 0.85, 0.85, 0.84, 0.84, 0.84, 0.83, 0.83, 0.83, 0.82, 0.82, 0.81, 0.81, 0.8, 0.8, 0.79, 0.78},
    {1.04, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.02, 1.02, 1.02, 1.02, 1.02, 1.02, 1.02, 1.02},
    {1.01, 1.02, 1.03, 1.04, 1.05, 1.06, 1.06, 1.07, 1.07, 1.07, 1.08, 1.08, 1.08, 1.09, 1.09, 1.09, 1.1, 1.1, 1.1, 1.11, 1.11, 1.11, 1.12, 1.12, 1.13, 1.13, 1.13, 1.14, 1.14, 1.14, 1.15},
    {1.04, 1.06, 1.08, 1.11, 1.13, 1.15, 1.15, 1.16, 1.16, 1.17, 1.18, 1.18, 1.19, 1.19, 1.2, 1.21, 1.21, 1.22, 1.23, 1.23, 1.24, 1.25, 1.26, 1.26, 1.27, 1.28, 1.29, 1.3, 1.31, 1.32, 1.33},
    {1.01, 1.03, 1.06, 1.08, 1.11, 1.14, 1.15, 1.15, 1.16, 1.16, 1.17, 1.18, 1.19, 1.2, 1.2, 1.21, 1.22, 1.23, 1.24, 1.24, 1.25, 1.26, 1.27, 1.28, 1.29, 1.29, 1.31, 1.32, 1.33, 1.34, 1.36},
    {1.04, 1.06, 1.08, 1.12, 1.14, 1.17, 1.17, 1.18, 1.18, 1.19, 1.2, 1.2, 1.21, 1.22, 1.22, 1.23, 1.24, 1.25, 1.25, 1.26, 1.27, 1.27, 1.28, 1.29, 1.3, 1.31, 1.32, 1.33, 1.34, 1.35, 1.37},
    {1.04, 1.05, 1.07, 1.08, 1.11, 1.12, 1.12, 1.13, 1.13, 1.13, 1.14, 1.14, 1.15, 1.15, 1.16, 1.16, 1.16, 1.17, 1.17, 1.18, 1.18, 1.19, 1.19, 1.2, 1.2, 1.21, 1.22, 1.22, 1.23, 1.24, 1.25},
    {1.01, 1.01, 1.02, 1.02, 1.02, 1.02, 1.02, 1.02, 1.02, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.03, 1.04, 1.04, 1.04, 1.04, 1.04, 1.04, 1.04, 1.04, 1.04, 1.04, 1.05, 1.05, 1.06},
    {1.04, 1.03, 1.02, 1.01, 1.0, 0.99, 0.99, 0.99, 0.98, 0.98, 0.98, 0.98, 0.98, 0.97, 0.97, 0.97, 0.97, 0.97, 0.96, 0.96, 0.96, 0.96, 0.95, 0.95, 0.95, 0.94, 0.94, 0.93, 0.93, 0.93, 0.92},
    {1.01, 0.99, 0.98, 0.95, 0.93, 0.91, 0.91, 0.9, 0.9, 0.9, 0.89, 0.89, 0.88, 0.88, 0.87, 0.86, 0.86, 0.85, 0.84, 0.84, 0.83, 0.82, 0.82, 0.81, 0.8, 0.79, 0.79, 0.78, 0.77, 0.76, 0.76},
    {1.04, 1.02, 0.99, 0.97, 0.94, 0.91, 0.91, 0.9, 0.9, 0.89, 0.88, 0.88, 0.87, 0.86, 0.86, 0.85, 0.84, 0.83, 0.83, 0.82, 0.81, 0.8, 0.79, 0.77, 0.76, 0.75, 0.74, 0.73, 0.72, 0.71, 0.7}};

  public static final double[] rn = {0.0, 5.0, 10.0, 15.0, 20.0, 25.0, 26.0, 27.0, 28.0, 29.0,
    30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0, 41.0,
    42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0, 50.0};

  public static final double[][] fs = {{1.06, 1.08, 1.12, 1.14, 1.17, 1.2, 1.23, 1.27, 1.28, 1.3, 1.32, 1.34, 1.37},
    {0.95, 0.97, 0.98, 1.0, 1.01, 1.03, 1.04, 1.06, 1.07, 1.08, 1.1, 1.11, 1.12},
    {1.04, 1.05, 1.05, 1.05, 1.05, 1.06, 1.06, 1.07, 1.07, 1.07, 1.07, 1.08, 1.08},
    {1.0, 0.99, 0.98, 0.97, 0.96, 0.95, 0.94, 0.93, 0.92, 0.92, 0.91, 0.9, 0.89},
    {1.02, 1.01, 0.98, 0.96, 0.94, 0.92, 0.89, 0.86, 0.85, 0.83, 0.82, 0.8, 0.77},
    {0.99, 0.96, 0.94, 0.91, 0.88, 0.85, 0.82, 0.78, 0.76, 0.74, 0.72, 0.7, 0.67},
    {1.02, 1.0, 0.97, 0.95, 0.93, 0.9, 0.87, 0.84, 0.82, 0.81, 0.79, 0.76, 0.74},
    {1.03, 1.01, 1.0, 0.99, 0.98, 0.96, 0.94, 0.92, 0.92, 0.91, 0.9, 0.89, 0.88},
    {1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.99, 0.99, 0.99, 0.99},
    {1.05, 1.06, 1.07, 1.08, 1.1, 1.12, 1.13, 1.15, 1.16, 1.17, 1.17, 1.18, 1.19},
    {1.03, 1.05, 1.07, 1.09, 1.11, 1.14, 1.17, 1.2, 1.22, 1.23, 1.25, 1.27, 1.29},
    {1.06, 1.1, 1.12, 1.15, 1.18, 1.21, 1.25, 1.29, 1.31, 1.33, 1.35, 1.37, 1.41}};

  public static final double[] rs = {5.0, 10.0, 15.0, 20.0, 25.0, 30.0, 35.0, 40.0, 42.0, 44.0, 46.0, 48.0, 50.0};

  /** Degree offset between soil and air temperature in Celsius. **/
  public static final double fc = 2.5;

  /** Soil-Air Relationship Amplitude **/
  public static final double fcd = 0.66;

  public static final double cv = 5.0/9.0;

  public static final int[] dp = {
    8, 7, 16, 6, 15, 24, 5, 14, 23, 32, 4, 13, 22, 31, 40, 3, 12, 21, 30,
    39, 48, 2, 11, 20, 29, 38, 47, 56, 1, 10, 19, 28, 37, 46, 55, 64, 9, 18,
    27, 36, 45, 54, 63, 17, 26, 35, 44, 53, 62, 25, 34, 43, 52, 61, 33, 42,
    51, 60, 41, 50, 59, 49, 58, 57, 160};

  public static final double[] dr = {
    1., 1., 1., 1., 1.02, 1.03, 1.05, 1.07, 1.09, 1.11, 1.13, 1.15, 1.17, 1.19, 1.21, 1.23, 1.26,
    1.28, 1.31, 1.34, 1.37, 1.40, 1.43, 1.46, 1.49, 1.53, 1.57, 1.61, 1.65, 1.69, 1.74, 1.78,
    1.84, 1.89, 1.95, 2.01, 2.07, 2.14, 2.22, 2.30, 2.38, 2.47, 2.57, 2.68, 2.80, 2.93, 3.07,
    3.22, 3.39, 3.58, 3.80, 4.03, 4.31, 4.62, 4.98, 5., 5., 5., 5., 5., 5., 5., 5., 5.};


  /**
   * Lag phases.  21 days is used for when the soil is warming
   * up, and 10 days for when the soil is cooling off.
   */
  public static final int lagPhaseSummer = 21;
  public static final int lagPhaseWinter = 10;

  private BASICSimulationModelConstants() {
    // Do not instantiate.
  }
  
}
