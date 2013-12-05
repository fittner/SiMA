package TestApps.Physics2D.BubbleRace;

import java.io.File;

import javax.swing.JFrame;

import bw.world.surface.*;

import sim.display.Console;
import sim.display.Controller;
import sim.display.Display2D;
import sim.display.GUIState;
import sim.engine.SimState;
import sim.portrayal.continuous.ContinuousPortrayal2D;
import sim.portrayal.grid.FastValueGridPortrayal2D;


/**
 * This class was adopted from the physicsTutorial3withUI class from the physics engine
 * @author kohlhauser
 *
 */
public class BubbleRaceWithUI extends GUIState
{
	public Display2D display;
	public JFrame displayFrame;
	
	//creating the protrayals
	ContinuousPortrayal2D entityPortrayal = new ContinuousPortrayal2D();
	FastValueGridPortrayal2D surfacePortrayal = new FastValueGridPortrayal2D(true);
	
	// S:\ARS\PA\BFG30\TestApps\src\TestApps\Physics2D\BubbleRace\BubbleRaceWorld.xml
	public static void main(String[] args)
	{
		BubbleRaceWithUI simBubbleRace = new BubbleRaceWithUI();
		Console c = new Console(simBubbleRace);
		c.setVisible(true);
		
		//checking for XML-file as a program argument
        if (args.length > 0)
        {
        	File xmlFile = new File(args[0]);
        	((BubbleRace) simBubbleRace.getSimulationInspectedObject()).xmlFile = xmlFile;
        }
	}
	
	public BubbleRaceWithUI()
	{
		super(new BubbleRace(System.currentTimeMillis()));
	}
	
	public BubbleRaceWithUI(SimState state)
	{
		super(state);
	}
	
	public static String getName()
	{
		return "Bot Race";
	}
	
	@Override
	public Object getSimulationInspectedObject()
	{
		return state;
	}
	
	@Override
	public void start()
	{
		super.start();
		setupPortrayals();
	}
	
	@Override
	public void load(SimState state)
	{
		super.load(state);
		setupPortrayals();
	}
	
	public void setupPortrayals()
	{
		//setting up the protrayals for the agents and the surfaces
		entityPortrayal.setField(((BubbleRace) state).fieldEnvironment);
		
        surfacePortrayal.setField(((BubbleRace)state).surfaceGrid);
        surfacePortrayal.setMap(itfSurface.soColorMap);
		
		display.reset();
		display.repaint();
	}
	
	
	@Override
	public void init(Controller c)
	{
		super.init(c);
		
		//creating the displays for the visual portrayal
		display = new Display2D(675, 900, this, 1);
		displayFrame = display.createFrame();
		c.registerFrame(displayFrame);
		displayFrame.setVisible(true);
		
		//attaching the protrayals to the display
		display.attach(surfacePortrayal,"SurfaceTrial");
		display.attach(entityPortrayal, "Bot Race");
	}
	
	@Override
	public void quit()
	{
		super.quit();
		
		if (displayFrame != null)
			displayFrame.dispose();
		
		displayFrame = null;
		display = null; 
	}
}
