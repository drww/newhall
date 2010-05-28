package newhall;

import java.util.logging.Level;
import java.util.logging.Logger;
import newhall.sim.BASICSimulationModel;
import newhall.sim.NewhallDataset;

public class Newhall {

  public static void main(String[] args) {

    NewhallDataset nd = null;

    try {
      nd = new NewhallDataset(args[0]);
    } catch (Exception e) {
      Logger.getLogger(Newhall.class.getName()).log(Level.SEVERE, null, e);
    }

    BASICSimulationModel.runSimulation(nd);

  }

}
