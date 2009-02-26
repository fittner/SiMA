package TestApps.src.BumpTest;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import sim.display.Controller;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.Double2D;



public class clsNewInspector extends Inspector{
	 
	public Inspector originalInspector;
	
    
    public clsNewInspector(Inspector originalInspector,
                                LocationWrapper wrapper,
                                GUIState guiState)
        {
        this.originalInspector = originalInspector;
       
        final AntBot antBot=(AntBot) wrapper.getObject();
        final SimState state=guiState.state;
        final Controller console=guiState.controller;
        
       
        // now let's add a Button
        Box box = new Box(BoxLayout.X_AXIS);
        JButton button = new JButton("Energy Inspector");
        box.add(button);
        box.add(Box.createGlue());

        // set up our inspector: keep the properties inspector around too
        setLayout(new BorderLayout());
        add(originalInspector, BorderLayout.CENTER);
        add(box, BorderLayout.NORTH);
        
        //Creating JTable for Internal Energy
        String[] columnNames = {"ID",
                "Sensor Name",
                "Value"};
        Object[][] data = {
  		    {"1", "Digestion",
  		    	antBot.getHashMapValue(1)},  //antBot.getHashMapValue(1)}
  		    {"2", "Acoustic",
  		    	antBot.getHashMapValue(2)},
  		    {"3", "BloodPresser",
  		    		antBot.getHashMapValue(3)}
  		    	
	  	};
        
        final JTable table = new JTable(data, columnNames);
    	//Importing table into the frame
    	JScrollPane scrollPane = new JScrollPane(table);
    	table.setFillsViewportHeight(true);
    	
    	//Creating JTable for group sensor and actuator inspector
    	 String[] GroupNames = {"ID",
                 "Group Name",
                 "Value"};
         Object[][] GroupData = {
   		    {"1", "InternalSensors",
   		    	antBot.getHashMapValue(1)},
   		    {"2", "InternalActuators",
   		    	antBot.getHashMapValue(2)},
   		    {"3", "ExternalSensor",
   		    		antBot.getHashMapValue(3)},
   		    {"4", "ExternalActuator",
   	   		    		antBot.getHashMapValue(3)}
   	   		    
   		    	
 	  	};
         
         final JTable Grouptable = new JTable(GroupData, GroupNames);
     	//Importing table into the frame
     	JScrollPane GroupscrollPane = new JScrollPane(Grouptable);
     	Grouptable.setFillsViewportHeight(true);
    	
    	
    	JPanel energyPanel=new JPanel();
		energyPanel.setBackground(Color.white);
		energyPanel.add(new JLabel("ABC"));
		  
		   
		final JFrame frame=new JFrame("Internal Energy Consumption");
		Container energyContainer=frame.getContentPane();
		energyContainer.add(energyPanel);
		  // frame.setLayout(new BorderLayout());
		  
		energyContainer.setLayout(new BorderLayout());
		energyContainer.add(table.getTableHeader(), BorderLayout.PAGE_START);
		energyContainer.add(table, BorderLayout.CENTER);

		
		energyContainer.add(Grouptable.getTableHeader(), BorderLayout.PAGE_END);
		energyContainer.add(Grouptable, BorderLayout.SOUTH);
		
		
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
	  
        // set what the button does
        button.addActionListener(new ActionListener()
            {
            public void actionPerformed(ActionEvent e)
                {
            	  synchronized(state.schedule)
            	  {
            	new JTableSample();	 
           		frame.setVisible(false);
                console.refresh();
                
                }
                }
            });
        }
        
    public void updateInspector()
        {
        originalInspector.updateInspector();
        }
    
    public boolean hitObject(Object object, DrawInfo2D range)
    {
		//TODO Clemens, hier gehört mehr rein als nur true!
    	return true; // (insert location algorithm and intersection here)
    } 
}
