package org.psu.newhall.xml2csv;

public class XML2CSV {

  static public final String BULK_SUMMARIZER_VERSION = "1.1.1";

  public static void main(String[] args) {

    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Newhall XML2CSV Tool");
      
    System.out.println("Newhall XML2CSV Tool " + BULK_SUMMARIZER_VERSION);

    final PrimaryFrame pf = new PrimaryFrame();
    pf.setTitle("Newhall XML2CSV Tool " + BULK_SUMMARIZER_VERSION);
    pf.setLocation(300, 200);
    pf.setVisible(true);

    System.out.println("Main thread exiting.");

  }
}
