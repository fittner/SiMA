package ClemLife;

import sim.engine.*;

import sim.display.*;
import sim.portrayal.continuous.*;

import java.awt.*;


import javax.swing.*;

public class ClemLifeUI extends GUIState
{
    public Display2D display;
    public JFrame displayFrame;

    ContinuousPortrayal2D entityPortrayal = new ContinuousPortrayal2D();
    //FastValueGridPortrayal2D surfacePortrayal = new FastValueGridPortrayal2D(true);

    public static void main(String[] args)
    {
        ClemLifeUI simRobotArena = new ClemLifeUI();
        Console c = new Console(simRobotArena);
        c.setVisible(true);

      //checking for xmlFile as a program argument
        
    }

    public ClemLifeUI()
    {
        super(new ClemLifeMain(System.currentTimeMillis()));
    }

    public ClemLifeUI(SimState state)
    {
        super(state);
    }

    public static String getName()
    {
        return "ClemLife Arena";
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
        entityPortrayal.setField(ClemLifeMain.fieldEnvironment);

        //surfacePortrayal.setField(((ClemLifeMain)state).surfaceGrid);
        //surfacePortrayal.setMap(bw.world.surface.itfSurface.soColorMap);

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
        //display.attach(surfacePortrayal,"SurfaceTrial");
        display.attach(entityPortrayal,"ClemLife Arena");

        // specify the backdrop color  -- what gets painted behind the displays
        display.setBackdrop(Color.white);
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





