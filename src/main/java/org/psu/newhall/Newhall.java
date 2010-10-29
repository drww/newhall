package org.psu.newhall;

import javax.swing.UIManager;
import org.psu.newhall.ui.DefaultNewhallFrame;

public class Newhall {

  public static void main(String[] args) {

    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      e.printStackTrace();
    }

    DefaultNewhallFrame dnf = new DefaultNewhallFrame();
    dnf.setVisible(true);

  }

}