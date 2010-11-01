package org.psu.newhall.util;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import javax.swing.JOptionPane;
import org.psu.newhall.sim.NewhallResults;

public class CSVResultsExporter {

  private NewhallResults nr;
  private File outputFile;

  public CSVResultsExporter(NewhallResults nr, File outputFile) {
    this.nr = nr;
    this.outputFile = outputFile;
  }

  public void save() {

    FileWriter fw;
    PrintWriter pw;

    try {
      fw = new FileWriter(outputFile);
      pw = new PrintWriter(fw);
    } catch(Exception e) {
      JOptionPane.showMessageDialog(null, "Error occured while saving file: \n\n" + e.getMessage());
      return;
    }

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

    pw.print(nr.getFlxFile());
    pw.close();

  }
}
