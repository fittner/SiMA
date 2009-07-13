/**
 * @author langr
 * 25.03.2009, 09:52:20
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.inspectors.body;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
//import javax.swing.JProgressBar;

import bw.body.internalSystems.clsStomachSystem;

import sim.display.Controller;
import sim.display.GUIState;
//import sim.engine.SimState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;

/**
 * Inspector for testing purpose to switch on/off intelligence-levels in 'AI brain'  
 * 
 * @author langr
 * 25.03.2009, 09:52:20
 * 
 */
public class clsFillLevelInspector extends Inspector implements ItemListener{

	/**
	 * TODO (langr) - insert description 
	 * 
	 * @author langr
	 * 25.03.2009, 10:36:38
	 */
	private static final long serialVersionUID = 1L;
	
	public Inspector moOriginalInspector;
	private clsStomachSystem moStomachSystem;
	private Controller moConsole;

	//content controls
	private JLabel moCaption;
	private JCheckBox moCheckBoxCD; //collision detection
	private JCheckBox moCheckBoxCA; //collision avoidance
	//private JProgressBar moEnergyProgress;
	private clsInspectorsAnalyse moAnalyse;
    
    public clsFillLevelInspector(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsStomachSystem poDU)
    {


    	moOriginalInspector = originalInspector;
    	moStomachSystem= poDU;
    	//final SimState state=guiState.state;
    	moConsole=guiState.controller;

    	moCaption = new JLabel("Inspector for clsFillLevel");

    	// creating the checkbox to sitch on/off the AI intelligence-levels.
    	Box oBox1 = new Box(BoxLayout.Y_AXIS);

    	//kilic
    	//moEnergyProgress= new JProgressBar(JProgressBar.VERTICAL, 0, 50);
    	//moEnergyProgress.setStringPainted(true);
    	//moAnalyse = new clsInspectorsAnalyse(true,0,50);
    	moAnalyse = new clsInspectorsAnalyse(true,0,50,moStomachSystem.getList());

    	oBox1.add(moCaption, BorderLayout.AFTER_LAST_LINE);
    	//kilic
    	oBox1.add(moAnalyse.getPanelOfAnalyse(), BorderLayout.AFTER_LAST_LINE);
    	//oBox1.add(moAnalyse.getProgressOfEnergy(), BorderLayout.AFTER_LAST_LINE);
    	oBox1.setBorder(BorderFactory.createTitledBorder("Inspector for clsFillLevel"));
    	oBox1.add(Box.createGlue());

    	// set up our inspector: keep the properties inspector around too
    	setLayout(new BorderLayout());
    	add(oBox1, BorderLayout.NORTH);
    	add(originalInspector, BorderLayout.CENTER);
    }

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.03.2009, 09:52:36
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		
		moCaption.setText(""+moStomachSystem.getEnergy());
		moOriginalInspector.updateInspector();
	
		this.repaint();
	}

	@Override
	public void paint (Graphics g)
	{
		super.paint(g);
		int i=0;
		i++;
		//kilic
		//aktualisiere den Progressbar
		moAnalyse.updatingValue((int)moStomachSystem.getEnergy());
		//aktualisiere die F�llk�stschen
		//moAnalyse.paintPanelOfAnalysisOfSeveralNutrition(g,moStomachSystem.getList(), 100, 150);
		moAnalyse.update(moStomachSystem.getList());
		
		
		
		
	}
	/********************************************************************************
	 * 
	 * TODO (kilic) - insert description
	 *
	 * @author kilic
	 * 14.05.2009, 14:05:30
	 *
	 * @param g
	 * @param x ist x-Koordinat f�r Ausgabe 
	 * @param y ist y-Koordinat f�r Ausgabe
	 * @param min ist Min. Wert
	 * @param max ist Max. Wert
	 * @param value ist aktueller Wert
	 ********************************************************************************/
	public void paintAnalysisOfNutriotion (Graphics g, int x, int y, double mino, double maxo, double valueorg) {
		int min=(int)mino*10;
		int max=(int)maxo*10;
		int value=(int)valueorg*10;
		g.setColor(Color.black);
		g.drawString("min", x-35, y-min);
		g.drawLine((x-10), (y-min), (x+20), (y-min));
		g.drawString("max", x-35, y-max);
		g.drawLine((x-10), (y-max), (x+20), (y-max));
		g.drawString(""+value, x+15, y-value);
		
		//g.fillRect(x, (yy-value), 10, value);
		
	    if(value<=min) {
	    	g.setColor(Color.red);
	    	//g.setColor(Color.yellow);
	    	g.fillRect(x, (y-value), 10, value);
	    }
	    else if(value>min&&value<max) {
	    	g.setColor(Color.red);
	    	g.fillRect(x, (y-min), 10, min);
	    	g.setColor(Color.orange);
	    	g.fillRect((x), (y-value), 10, (value-min));
	    }
	    else  if(value>=max) {
	    	g.setColor(Color.red);
	    	g.fillRect(x, (y-min), 10, min);
	    	g.setColor(Color.orange);
	    	g.fillRect((x), (y-max), 10, (max-min));
	    	g.setColor(Color.red);
	    	
	    	g.fillRect((x), (y-value), 10, (value-max));
	    }
	    
	  }
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 25.03.2009, 10:10:30
	 * 
	 * @see java.awt.event.ItemListener#itemStateChanged(java.awt.event.ItemEvent)
	 */
	public void itemStateChanged(ItemEvent e) {

		Object source = e.getItemSelectable();
		
			if( source == moCheckBoxCD)
			{
				//moStomachSystem.setRoombaIntelligence(moCheckBoxCD.isSelected());
			}
			else if(source == moCheckBoxCA)
			{
				//moStomachSystem.setCollisionAvoidance(moCheckBoxCA.isSelected());
			}
			
		moConsole.refresh();
	}
}
