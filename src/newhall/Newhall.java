package newhall;

import javax.swing.UIManager;
import newhall.ui.DefaultNewhallFrame;

public class Newhall {

  public static void main(String[] args) {

    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    DefaultNewhallFrame dnf = new DefaultNewhallFrame();
    dnf.setVisible(true);

    /**NewhallDataset nd = null;

    try {
      nd = new NewhallDataset(args[0]);
    } catch (Exception e) {
      Logger.getLogger(Newhall.class.getName()).log(Level.SEVERE, null, e);
    }

    NewhallResults nr = BASICSimulationModel.runSimulation(nd);
    System.out.println(nr);**/

  }

}
