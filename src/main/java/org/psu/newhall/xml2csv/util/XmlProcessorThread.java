package org.psu.newhall.xml2csv.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JLabel;

public class XmlProcessorThread extends Thread {

  FileWriter fw;
  List<File> xmlFiles;
  JLabel statusText;
  JButton summarizeButton;

  public XmlProcessorThread(FileWriter fw, List<File> xmlFiles, JLabel statusText, JButton summarizeButton) {
    super("XmlProcessorThread");
    this.fw = fw;
    this.xmlFiles = xmlFiles;
    this.statusText = statusText;
    this.summarizeButton = summarizeButton;
  }

  @Override
  public void run() {
    int numberXmlFiles = xmlFiles.size();
    int numberProcessedXmlFiles = 0;

    // Settings for how aggressive to make the Garbage Collector.
    int filesSinceLastGarbageCollection = 0;
    final int filesPerGarbageCollection = 100;

    // Write headers.
    try {
      fw.write(CsvFormatProperties.CSV_HEADERS_SINGLE_LINE + "\n");
    } catch (IOException ex) {
      statusText.setText("Cannot create output file as specified, correct it.");
      summarizeButton.setEnabled(true);
      return;
    }

    // Process all the XML files.
    for (File xmlFile : xmlFiles) {
      filesSinceLastGarbageCollection++;
      numberProcessedXmlFiles++;
      statusText.setText("Processing: " + numberProcessedXmlFiles + "/" + numberXmlFiles + " (" + (numberProcessedXmlFiles * 100) / numberXmlFiles + "%)");

      try {
        fw.write(XmlFileParser.parseXmlFile(xmlFile).toString() + "\n");
        fw.flush();
      } catch (InvalidFileFormatException ex) {
        numberProcessedXmlFiles--;
        System.out.println("Invalid XML file: " + xmlFile.getName() + " (Skipping.)");
        continue;
      } catch (NullPointerException ex) {
        numberProcessedXmlFiles--;
        System.out.println("Nulls in XML file: " + xmlFile.getName() + " (Skipping.)");
        //ex.printStackTrace();
        continue;
      } catch (IOException ex) {
        statusText.setText("Cannot create output file as specified, correct it.");
        summarizeButton.setEnabled(true);
        return;
      }

      if (filesSinceLastGarbageCollection >= filesPerGarbageCollection) {
        filesSinceLastGarbageCollection = 0;
        System.out.println("== RUNNING GARBAGE COLLECTION MANUALLY ==");      
        System.gc();
      }
    }

    // Cleanup.
    try {
      fw.close();
    } catch (IOException ex) {
      statusText.setText("Cannot create output file as specified, correct it.");
      summarizeButton.setEnabled(true);
      return;
    }

    System.out.println("Finished exporting data to CSV.");
    statusText.setText("Processed " + numberProcessedXmlFiles + " out of " + numberXmlFiles + " XML files.");
    summarizeButton.setEnabled(true);
  }
}
