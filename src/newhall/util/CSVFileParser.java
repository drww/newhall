package newhall.util;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class CSVFileParser {

  String filePath;
  ArrayList<String> headers;
  ArrayList<ArrayList<String>> records;

  public CSVFileParser(String filePath, boolean hasHeader) throws FileNotFoundException, IOException {

    FileInputStream fis = new FileInputStream(filePath);
    DataInputStream dis = new DataInputStream(fis);
    InputStreamReader isr = new InputStreamReader(dis);
    BufferedReader br = new BufferedReader(isr);

    this.filePath = filePath;
    this.headers = new ArrayList<String>();
    this.records = new ArrayList<ArrayList<String>>();
    String line = "";

    if (hasHeader) {
      line = br.readLine();
      for (String str : line.split(",")) {
        headers.add(str);
      }
    }

    while((line = br.readLine()) != null) {
      ArrayList<String> holder = new ArrayList<String>();
      for(String str : line.split(",")) {
        holder.add(str);
      }
      records.add(holder);
    }

    fis.close();

  }

  public String getFilePath() {
    return filePath;
  }

  public ArrayList<String> getHeaders() {
    return headers;
  }

  public ArrayList<ArrayList<String>> getRecords() {
    return records;
  }

  @Override
  public String toString() {
    String result = this.getClass().toString();
    result += "\n  " + filePath;
    if (!headers.isEmpty()) {
      result += "\n  ";
      for (String str : headers) {
        result += str + " ";
      }
    } else {
      result += "\n  File has no headers.";
    }
    for(ArrayList<String> row : records) {
      result += "\n    ";
      for(String str : row) {
        result += str + " ";
      }
    }
    return result;
  }

}
