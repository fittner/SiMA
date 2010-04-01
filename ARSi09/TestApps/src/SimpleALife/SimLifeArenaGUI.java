package SimpleALife;

import sim.engine.*;
import SimpleALife.SimLifeArena;
import SimpleALife.SimLifeArenaGUI;
import sim.display.*;
import sim.portrayal.continuous.*;
import sim.portrayal.grid.FastValueGridPortrayal2D;
import java.awt.*;
import java.io.File;

import javax.swing.*;

public class SimLifeArenaGUI extends GUIState
{
    public Display2D display;
    public JFrame displayFrame;

    ContinuousPortrayal2D entityPortrayal = new ContinuousPortrayal2D();
    FastValueGridPortrayal2D surfacePortrayal = new FastValueGridPortrayal2D(true);

    public static void main(String[] args)
    {
        SimLifeArenaGUI simRobotArena = new SimLifeArenaGUI();
        Console c = new Console(simRobotArena);
        c.setVisible(true);

      //checking for xmlFile as a program argument
        if (args.length > 0)
        {
        	File xmlFile = new File(args[0]);
        	((SimLifeArena) simRobotArena.getSimulationInspectedObject()).xmlFile = xmlFile;
        }
    }

    public SimLifeArenaGUI()
    {
        super(new SimLifeArena(System.currentTimeMillis()));
    }

    public SimLifeArenaGUI(SimState state)
    {
        super(state);
    }

    public static String getName()
    {
        return "SimLife Arena";
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
        // we now have new grids.  Set up the portrayals to reflect that
        setupPortrayals();
	}

    public void setupPortrayals()
	{
        // tell the portrayals what to portray and how to portray them
        entityPortrayal.setField(SimLifeArena.fieldEnvironment);

        surfacePortrayal.setField(((SimLifeArena)state).surfaceGrid);
        surfacePortrayal.setMap(bw.world.surface.itfSurface.soColorMap);

        // reschedule the displayer
        display.reset();

        // redraw the display
        display.repaint();
	}

    @Override
    public void init(Controller c)
	{
        super.init(c);

        // Make the Display2D.  We'll have it display stuff later.
        display = new Display2D(500,500,this,1); // the sim-display size
        displayFrame = display.createFrame();
        c.registerFrame(displayFrame);   // register the frame so it appears in the "Display" list
        displayFrame.setVisible(true);

        // attach the portrayals
        display.attach(surfacePortrayal,"SurfaceTrial");
        display.attach(entityPortrayal,"SimLife Arena");

        // specify the backdrop color  -- what gets painted behind the displays
        display.setBackdrop(Color.black);
	}

    @Override
    public void quit()
	{
        super.quit();

        if (displayFrame!=null)
            displayFrame.dispose();

        displayFrame = null;  // let gc
        display = null;       // let gc
	}
}





