package newhall;

// Load source data file.
// Clean if dirty.
// Compute monthly PET values.
// Determine Temp Regime
// Produce results.

public class Newhall_Old {

    static String[] tempRegimes = {"pregelic", "cryic", "frigid", "mesic",
        "thermic", "hyperth.", "Isofrigid", "Isomesic", "Isothermic",
        "Isohyperthermic"};

    // 24
    static double[] zpe = {135.0, 139.5, 143.7, 147.8, 151.7, 155.4, 158.9, 162.1,
        165.2, 168.0, 170.7, 173.1, 175.3, 177.2, 179.0, 180.5, 181.8, 182.9,
        183.7, 184.3, 184.7, 184.9, 185.0, 185.0};

    // 24
    static double[] zt = {26.5, 27.0, 27.5, 28.0, 28.5, 29.0, 29.5, 30.0, 30.5 ,31.0,
        31.5, 32.0, 32.5, 33.0, 33.5, 34.0, 34.5, 35.0, 35.5, 36.0, 36.5,
        37.0, 37.5, 38.0};

    // 12 * 30
    static double[][] inz = {
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

    // 31
    static double[] rn = {0.0, 5.0, 10.0, 15.0, 20.0, 25.0, 26.0, 27.0, 28.0, 29.0,
        30.0, 31.0, 32.0, 33.0, 34.0, 35.0, 36.0, 37.0, 38.0, 39.0, 40.0, 41.0,
        42.0, 43.0, 44.0, 45.0, 46.0, 47.0, 48.0, 49.0, 50.0};

    // 12 * 13
    static double[][] fs = {{1.06, 1.08, 1.12, 1.14, 1.17, 1.2, 1.23, 1.27, 1.28, 1.3, 1.32, 1.34, 1.37},
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

    // 13
    static double[] rs = {5.0, 10.0, 15.0, 20.0, 25.0, 30.0, 35.0, 40.0, 42.0, 44.0, 46.0, 48.0, 50.0};

    public void notMain(String[] args) {

        // Load file data.

        String fileContents = "Testing,US,123,123,N,321,321,E,111," +
            "1,2,3,4,5,6,7,8,9,10,11,12," +
            "10,20,30,40,50,60,70,80,90,100,110,120,1999,1999,M";
        String csvFields[] = fileContents.split(",");
        
        String stationName = csvFields[0];                  // ST
        String stationCountry = csvFields[1];               // CO
        int latitudeDeg = Integer.valueOf(csvFields[2]);    // RLA1
        int latitudeMin = Integer.valueOf(csvFields[3]);    // RLA2
        char hemisphere = 
            Character.toUpperCase(csvFields[4].charAt(0));  // H
        int longitudeDeg = Integer.valueOf(csvFields[5]);   // RLO1
        int longitudeMin = Integer.valueOf(csvFields[6]);   // RLO2
        char longDirection = csvFields[7].charAt(0);        // EW
        int elevation = Integer.valueOf(csvFields[8]);      // ELEV

        double[] monthlyAverageTemp = new double[12];
        for(int i = 0, j = 8; i < 12; i++, j++) {
            monthlyAverageTemp[i] = Double.valueOf(csvFields[j]);
        }

        double[] monthlyAveragePrecip = new double[12];
        for(int i = 0, j = 20; i < 12; i++, j++) {
            monthlyAveragePrecip[i] = Double.valueOf(csvFields[j]);
        }

        int yearBegin = Integer.valueOf(csvFields[32]);
        int yearEnd = Integer.valueOf(csvFields[33]);
        char measurementSystem =
            Character.toUpperCase(csvFields[34].charAt(0));

        // Convert to metric system if measurements are not already in it.

        if(measurementSystem != 'M') {
            for(Double temp : monthlyAverageTemp) {
                temp = (temp - 32) * (5/9);
            }

            for (Double precip : monthlyAveragePrecip) {
                // Assuming inches to millimeters.
                precip *= 25.4;
            }
        }

        double rlag = latitudeDeg + (latitudeMin / 60);

        double[] mwi = new double[12];
        double swi = 0;
        for(int i = 0; i < 12; i++) {
            if(monthlyAverageTemp[i] > 0) {
                mwi[i] = Math.pow((monthlyAverageTemp[i] / 5), 1.514);
                swi += mwi[i];
            }
        }

        double a = (6.75E-07 * Math.pow(swi, 3)) - (7.71E-5 * Math.pow(swi, 2))
            + (0.01792 * swi) + 0.49239;

        double[] upe = new double[12];
        for(int i = 0; i < 12; i++) {
            if(monthlyAverageTemp[i] > 0) {
                if(monthlyAverageTemp[i] >= 26.5) {
                    if(monthlyAverageTemp[i] < 38) {
                        upe[i] = 185;
                    } else {
                        int kl = 0;
                        int kk = 0;
                        for(int ki = 1; ki <= 24; ki++) {
                            kl = ki + 1;
                            kk = ki;
                            if(monthlyAverageTemp[i] >= zt[ki] && monthlyAverageTemp[i] < zt[kl]) {
                                upe[i] = zpe[kk];
                                break;
                            }

                            if(ki == 24) {
                                upe[i] = zpe[kk];
                            }
                        }
                    }
                } else {
                    upe[i] = 16 * Math.pow((10*monthlyAverageTemp[i])/swi, a);
                }
            }
        }

        if(hemisphere == 'N') {
            
        } else {
            
        }

    }

}
