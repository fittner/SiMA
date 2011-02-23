package nao.main;

import java.awt.*;
import java.awt.event.*;

/**
 * Listens to window events, mainly to make sure the app gets closed when the window is closed
 * @author muchitsch
 *
 */
public class clsWindowListener extends WindowAdapter {
	
  public void windowClosing(WindowEvent event) {
    System.exit(0);
  }
  
}



