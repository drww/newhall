package org.psu.bulksummarizer;

public class BulkSummarizer {

  static public final String BULK_SUMMARIZER_VERSION = "1.1.0";

  public static void main(String[] args) {

    System.out.println("Newhall BulkSummarizer " + BULK_SUMMARIZER_VERSION);

    final PrimaryFrame pf = new PrimaryFrame();
    pf.setTitle("Newhall BulkSummarizer " + BULK_SUMMARIZER_VERSION);
    pf.setLocation(300, 200);
    pf.setVisible(true);

    System.out.println("Main thread exiting.");

  }
}
