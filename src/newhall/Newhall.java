package newhall;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import newhall.util.CSVFileParser;

public class Newhall {

  public static void main(String[] args) {

    CSVFileParser cfp = null;

    try {
      cfp = new CSVFileParser(args[0], true);
    } catch (FileNotFoundException ex) {
      Logger.getLogger(Newhall.class.getName()).log(Level.SEVERE, null, ex);
    } catch (IOException ex) {
      Logger.getLogger(Newhall.class.getName()).log(Level.SEVERE, null, ex);
    }

    System.out.println(cfp);

  }

}
