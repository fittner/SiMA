package nao.main;

import java.awt.*;
import java.awt.event.*;

public class clsExitListener extends WindowAdapter {
  public void windowClosing(WindowEvent event) {
    System.exit(0);
  }
}



