package org.psu.newhall;

import javax.swing.UIManager;
import org.psu.newhall.ui.NewerNewhallFrame;

public class Newhall {

  public static void main(String[] args) {

    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      System.out.println(e);
    }

    NewerNewhallFrame dnf = new NewerNewhallFrame();
    dnf.setVisible(true);

  }

}
