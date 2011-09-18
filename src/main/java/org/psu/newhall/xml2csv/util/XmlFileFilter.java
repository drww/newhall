package org.psu.newhall.xml2csv.util;

import java.io.File;
import java.io.FileFilter;

public class XmlFileFilter implements FileFilter {

  public XmlFileFilter() {
  }

  public boolean accept(File file) {
    String fileName = file.getName();
    
    fileName = fileName.toLowerCase();

    return fileName.endsWith(".xml");
  }

}
