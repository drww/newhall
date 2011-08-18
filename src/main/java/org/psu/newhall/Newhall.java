package org.psu.newhall;

import javax.swing.UIManager;
import org.psu.newhall.ui.DefaultNewhallFrame;

public class Newhall {

  public static String NSM_VERSION = "1.4.6";

  public static void main(String[] args) {

    // Set the 
    System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Newhall");

    try {
      UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
    } catch (Exception e) {
      System.out.println(e);
    }

    DefaultNewhallFrame dnf = new DefaultNewhallFrame();
    dnf.setLocation(100, 100);
    dnf.setVisible(true);

    //ConnectorInvocationExample.example();

  }

}
