package TestApps.src.BumpTest;
import java.awt.*;
import javax.swing.*;

public class clsInspector extends javax.swing.JPanel {
	

		   public static void main(String[] args) {
			   
			   JPanel energyPanel=new JPanel();
			   energyPanel.setBackground(Color.white);
			   energyPanel.add(new JLabel("ABC"));
			   
			   
			   JFrame frame=new JFrame("Energy Inspector");
			   Container energyContainer=frame.getContentPane();
			   energyContainer.add(energyPanel);
			  // frame.setLayout(new BorderLayout());
			  
			   frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			   frame.pack();
			   frame.setVisible(true);
	   }
	}

