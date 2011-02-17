package nao.main;

/**
 * clsNAOMainWithUI.java: nao.main
 * 
 * @author muchitsch
 * 15.02.2011, 12:50:50
 */

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import config.clsBWProperties;
import decisionunit.clsDecisionUnitFactory;
import du.itf.itfDecisionUnit;
import du.itf.actions.itfActionProcessor;

import pa.clsPsychoAnalysis;
import statictools.clsGetARSPath;

import nao.body.clsNAOBody;
import nao.body.io.actuators.clsActionProcessor;




//import statictools.clsGetARSPath;

/**
 * This is the main function to start the NAO connection between nao 
 * simulator or real nao and the ARS-PA decision unit.
 * 
 * @author muchitsch
 * 15.02.2011, 12:50:50
 * 
 */
public class clsNAOMainWithUI extends javax.swing.JFrame implements ActionListener{
	
	private static final long serialVersionUID = 7626986341592606788L;
	private static JButton moCancelButton = null;
	private static JButton moRunButton = null;
	private static JFrame moMainFrame = null;
	
	public static void main(String[] args){
		new clsNAOMainWithUI();
	}
	
	 public clsNAOMainWithUI(){
		 
	 	//make me a Frame
	 	clsWindowUtilities.setNativeLookAndFeel();
	 	moMainFrame = new JFrame("ARS vs. NAO");
	 	moMainFrame.setSize(400, 150);
	    Container content = moMainFrame.getContentPane();
	    content.setBackground(Color.LIGHT_GRAY);
	    content.setLayout(new FlowLayout()); 
	    
	    //add the freakin buttons
	    moCancelButton =  new JButton("CANCEL");
	    moCancelButton.setActionCommand("cancel");
	    moCancelButton.addActionListener(this);
	    content.add(moCancelButton);
	    
	    moRunButton = new JButton("RUN");
	    moRunButton.setActionCommand("run");
	    moRunButton.addActionListener(this);
	    content.add(moRunButton);
	    
	    moMainFrame.addWindowListener(new clsExitListener());
	    moMainFrame.setVisible(true);
	 }
	
	 public void actionPerformed(ActionEvent event) {
		 
		 if ("cancel".equals(event.getActionCommand())) {
			 	moMainFrame.setName("closing...");
			 	System.exit(0);
			}
			else if ("run".equals(event.getActionCommand())) {
				moMainFrame.setName("ARS vs. NAO - running...");
				run();
			}
			else {
				//noop
			}
			this.repaint();
		  }

	

	//fuer spaeter wegen den args	
	 static String argumentForKey(String key, String[] args, int startingAt)
	    {
	    	for(int x=0;x<args.length-1;x++)  // key can't be the last string
	    		if (args[x].equalsIgnoreCase(key))
	    			return args[x + 1];
	    	return null;
	    }
	    
	    static boolean keyExists(String key, String[] args, int startingAt)
	    {
	    	for(int x=0;x<args.length;x++)  // key can't be the last string
	        	if (args[x].equalsIgnoreCase(key))
	        		return true;
	    	return false;
	    }
	    
	    private void run() {
	    	try{
	    		
	    		String oFilename = "";
	    		oFilename = "testsetup.main.properties"; // no parameters given - used default config			
	
				String oPath = "";
				
				oPath = clsGetARSPath.getConfigPath();
		    		
		    	
		    	
		    	clsBWProperties oProp = pa.clsPsychoAnalysis.getDefaultProperties("");
		    	//clsBWProperties oProp = clsBWProperties.readProperties(oPath, oFilename);
		    	itfDecisionUnit oDU = new pa.clsPsychoAnalysis("", oProp);
		    	
		    	clsActionProcessor oActionProcessor = new clsActionProcessor();
		    	oDU.setActionProcessor(oActionProcessor);
		    		
				clsNAOBody nao = new clsNAOBody();
				
				nao.getBrain().setDecisionUnit(oDU);
				
				 while(true)
		            {
					 nao.stepProcessing();
		            }

				 
	    	} catch(Exception e) {
	    		getCustomStackTrace(e);
	    	      //System.out.println("Error : " + e + " " +  e.getStackTrace());
	    	      System.exit(0);	    	      
	    	    }
		}
	    
	    public static String getCustomStackTrace(Throwable aThrowable) {
	        //add the class name and any message passed to constructor
	        final StringBuilder result = new StringBuilder( "BOO-BOO: " );
	        result.append(aThrowable.toString());
	        final String NEW_LINE = System.getProperty("line.separator");
	        result.append(NEW_LINE);

	        //add each element of the stack trace
	        for (StackTraceElement element : aThrowable.getStackTrace() ){
	          result.append( element );
	          result.append( NEW_LINE );
	        }
	        return result.toString();
	      }

		
}//end class clsNAOMainWithUI



