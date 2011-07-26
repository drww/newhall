package org.psu.newhall;

import javax.swing.UIManager;
import org.psu.newhall.ui.DefaultNewhallFrame;

public class Newhall {

  public static String NSM_VERSION = "1.4.5";

  public static void main(String[] args) {

    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      System.out.println(e);
    }

    DefaultNewhallFrame dnf = new DefaultNewhallFrame();
    dnf.setVisible(true);

    //ConnectorInvocationExample.example();

  }

}
