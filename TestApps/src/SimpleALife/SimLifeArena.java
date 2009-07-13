package SimpleALife;

import java.io.*;
import java.awt.*;
import SimpleALife.CarT;
import SimpleALife.HerR;
import SimpleALife.PlaG;
import SimpleALife.Head;
import SimpleALife.SimLifeArena;
import SimpleALife.Rock;
import ec.util.*;
import sim.engine.*;
import sim.field.continuous.*;
import sim.field.grid.IntGrid2D;
import sim.physics2D.*;
import sim.physics2D.util.*;
import sim.physics2D.constraint.*;
import bw.world.surface.SurfaceHandler;

public class SimLifeArena extends SimState
{
	private static final long serialVersionUID = 1L;

	private static Double2D pos;
	private static CarT carT;
	private static HerR herR;
	private static PlaG plaG;
	private static Rock rock;

	public static Continuous2D fieldEnvironment;
	public static PhysicsEngine2D objPE;
	public IntGrid2D surfaceGrid;
	public File xmlFile = null;

	public static final int arenaWidth = 1000;			// must although be changed in xmlFile
	public static final int arenaHeight = 1000;
	public static final int arenaWallThickness = arenaWidth/100;
	public static final int maxDist = arenaWidth*3;	// must be greater then the greatest distance in the environment

    public SimLifeArena(long seed)
    {
        super(new MersenneTwisterFast(seed), new Schedule());
    }

    // Resets and starts a simulation
    @Override
    public void start()
    {
    	System.out.println("------ Start ------");	// debug

        super.start();  // clear out the schedule
        fieldEnvironment = new Continuous2D(25, (double)SimLifeArena.arenaWidth, (double)SimLifeArena.arenaHeight);

        // Add physics specific code here
        // Create and schedule the physics engine
		objPE = new PhysicsEngine2D();
		schedule.scheduleRepeating(objPE);

		//create world from xmlFile
	    if (xmlFile != null)
	    {
	    	if (xmlFile.exists())
	    	{
	    		surfaceGrid = SurfaceHandler.getInstance().createWorld(xmlFile);
	    	}
	    	else
	    	{
	    		System.out.println("xmlFile does not exist!");
	    		surfaceGrid = SurfaceHandler.getInstance().createWorld(arenaWidth, arenaHeight);	//mine
	    	}
	    }
	    else
	    {
	    	System.out.println("xmlFile not initialised");
	    	surfaceGrid = SurfaceHandler.getInstance().createWorld(arenaWidth, arenaHeight);	//mine
	    }

	    // TODO: make subfunctions for the register stuff to make it possible to create one from outside
		// Arena Walls
		// Horiz.
		pos = new Double2D(SimLifeArena.arenaWidth/2, SimLifeArena.arenaWallThickness/2);
		rock = new Rock(pos, SimLifeArena.arenaWidth, SimLifeArena.arenaWallThickness);
		rock.setArenaLimit(true);
		fieldEnvironment.setObjectLocation(rock, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(rock);

		pos = new Double2D(SimLifeArena.arenaWidth/2, SimLifeArena.arenaHeight-SimLifeArena.arenaWallThickness/2);
		rock = new Rock(pos, SimLifeArena.arenaWidth, SimLifeArena.arenaWallThickness);
		rock.setArenaLimit(true);
		fieldEnvironment.setObjectLocation(rock, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(rock);

		// Vert.
		pos = new Double2D(SimLifeArena.arenaWallThickness/2, SimLifeArena.arenaHeight/2);
		rock = new Rock(pos, SimLifeArena.arenaWallThickness, SimLifeArena.arenaHeight);
		rock.setArenaLimit(true);
		fieldEnvironment.setObjectLocation(rock, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(rock);

		pos = new Double2D(SimLifeArena.arenaWidth-SimLifeArena.arenaWallThickness/2, SimLifeArena.arenaHeight/2);
		rock = new Rock(pos, SimLifeArena.arenaWallThickness, SimLifeArena.arenaHeight);
		rock.setArenaLimit(true);
		fieldEnvironment.setObjectLocation(rock, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(rock);

		// Rocks (Obstacles)
		pos = new Double2D(SimLifeArena.arenaWidth*0.35,SimLifeArena.arenaHeight*0.55);  // block
		rock = new Rock(pos, arenaWidth/5, arenaHeight/5);
		fieldEnvironment.setObjectLocation(rock, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(rock);

		pos = new Double2D(SimLifeArena.arenaWidth*0.6,SimLifeArena.arenaHeight*0.25);  // horiz.
		rock = new Rock(pos, arenaWidth*2/5, arenaHeight/30);
		fieldEnvironment.setObjectLocation(rock, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(rock);

		pos = new Double2D(SimLifeArena.arenaWidth*0.75,SimLifeArena.arenaHeight*0.7);  // vert.
		rock = new Rock(pos, arenaWidth/30, arenaHeight/3);
		fieldEnvironment.setObjectLocation(rock, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(rock);

		// PlaG ("Grass")
		int startX, endX, startY, endY, plaIndex = 0;

		startX=40; endX=120;	// must although be changed in xmlFile
		startY=15; endY=75;
		pos = new Double2D((startX+endX)/2, (startY+endY)/2);
		plaG = new PlaG(pos, endX-startX, endY-startY);
		fieldEnvironment.setObjectLocation(plaG, new sim.util.Double2D(pos.x, pos.y));
		schedule.scheduleRepeating(plaG);

		startX=550; endX=700;	// must although be changed in xmlFile
		startY=150; endY=200;
		pos = new Double2D((startX+endX)/2, (startY+endY)/2);
		plaG = new PlaG(pos, endX-startX, endY-startY);
		fieldEnvironment.setObjectLocation(plaG, new sim.util.Double2D(pos.x, pos.y));
		schedule.scheduleRepeating(plaG);

		startX=650; endX=720;	// must although be changed in xmlFile
		startY=600; endY=700;
		pos = new Double2D((startX+endX)/2, (startY+endY)/2);
		plaG = new PlaG(pos, endX-startX, endY-startY);
		fieldEnvironment.setObjectLocation(plaG, new sim.util.Double2D(pos.x, pos.y));
		schedule.scheduleRepeating(plaG);

		startX=900; endX=970;	// must although be changed in xmlFile
		startY=400; endY=550;
		pos = new Double2D((startX+endX)/2, (startY+endY)/2);
		plaG = new PlaG(pos, endX-startX, endY-startY);
		fieldEnvironment.setObjectLocation(plaG, new sim.util.Double2D(pos.x, pos.y));
		schedule.scheduleRepeating(plaG);

		startX=20; endX=130;	// must although be changed in xmlFile
		startY=900; endY=980;
		pos = new Double2D((startX+endX)/2, (startY+endY)/2);
		plaG = new PlaG(pos, endX-startX, endY-startY);
		fieldEnvironment.setObjectLocation(plaG, new sim.util.Double2D(pos.x, pos.y));
		schedule.scheduleRepeating(plaG);

		// HerR ("Rabbits")
		// set some manually
		pos = new Double2D(arenaWidth*0.33, arenaHeight*0.25);
		herR = new HerR(pos, new Double2D(0, 0));
		fieldEnvironment.setObjectLocation(herR, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(herR);
		schedule.scheduleRepeating(herR);

		pos = new Double2D(arenaWidth*0.36, arenaHeight*0.26);
		herR = new HerR(pos, new Double2D(0, 0));
		fieldEnvironment.setObjectLocation(herR, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(herR);
		schedule.scheduleRepeating(herR);

		pos = new Double2D(arenaWidth*0.19, arenaHeight*0.48);
		herR = new HerR(pos, new Double2D(0, 0));
		fieldEnvironment.setObjectLocation(herR, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(herR);
		schedule.scheduleRepeating(herR);

		pos = new Double2D(arenaWidth*0.16, arenaHeight*0.55);
		herR = new HerR(pos, new Double2D(0, 0));
		fieldEnvironment.setObjectLocation(herR, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(herR);
		schedule.scheduleRepeating(herR);

		pos = new Double2D(arenaWidth*0.90, arenaHeight*0.28);
		herR = new HerR(pos, new Double2D(0, 0));
		fieldEnvironment.setObjectLocation(herR, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(herR);
		schedule.scheduleRepeating(herR);

		pos = new Double2D(arenaWidth*0.88, arenaHeight*0.29);
		herR = new HerR(pos, new Double2D(0, 0));
		fieldEnvironment.setObjectLocation(herR, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(herR);
		schedule.scheduleRepeating(herR);

		pos = new Double2D(arenaWidth*0.38, arenaHeight*0.70);
		herR = new HerR(pos, new Double2D(0, 0));
		fieldEnvironment.setObjectLocation(herR, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(herR);
		schedule.scheduleRepeating(herR);

		// set some randomly
		// TODO: check if the random set places are free
//		for (int i = 0; i < 6; i++)
//		{
//			double x = Math.max(Math.min(random.nextDouble() * arenaWidth, arenaWidth*0.9), arenaWidth*0.1);
//			double y = Math.max(Math.min(random.nextDouble() * arenaHeight, arenaHeight*0.9), arenaHeight*0.2);
//
//			pos = new Double2D(x, y);
//			herR = new HerR(pos, new Double2D(0, 0));
//			fieldEnvironment.setObjectLocation(herR, new sim.util.Double2D(pos.x, pos.y));
//			objPE.register(herR);
//			schedule.scheduleRepeating(herR);
//		}

		// CarT ("Tigers")
		// set some manually
		double x, y;

		x = arenaWidth*0.85;
		y = arenaHeight*0.85;
		pos = new Double2D(x, y);
		carT = new CarT(pos, new Double2D(0, 0));
		objPE.register(carT);
		fieldEnvironment.setObjectLocation(carT, new sim.util.Double2D(pos.x, pos.y));
		schedule.scheduleRepeating(carT);

		Head head;
		pos = new Double2D(x + 10, y);
		head = new Head(pos, new Double2D(0, 0), 5, Color.blue);
		objPE.register(head);
		fieldEnvironment.setObjectLocation(head, new sim.util.Double2D(pos.x, pos.y));
		schedule.scheduleRepeating(head);
		carT.head = head;
		objPE.setNoCollisions(carT, head);
		PinJoint pj = new PinJoint(pos, head, carT);
		objPE.register(pj);

		// set some randomly
//		for (int i = 0; i < 3; i++)
//		{
//			double x = Math.max(Math.min(random.nextDouble() * SimLifeArena.arenaWidth, SimLifeArena.arenaWidth*0.5), SimLifeArena.arenaWidth*0.1);
//			double y = Math.max(Math.min(random.nextDouble() * SimLifeArena.arenaHeight, SimLifeArena.arenaHeight*0.15), SimLifeArena.arenaHeight*0.05);
//
//			pos = new Double2D(x, y);
//			carT = new CarT(pos, new Double2D(0, 0));
//			objPE.register(carT);
//			fieldEnvironment.setObjectLocation(carT, new sim.util.Double2D(pos.x, pos.y));
//			schedule.scheduleRepeating(carT);
//
//			Head head;
//			pos = new Double2D(x + 10, y);
//			head = new Head(pos, new Double2D(0, 0), 5, Color.blue);
//			objPE.register(head);
//			fieldEnvironment.setObjectLocation(head, new sim.util.Double2D(pos.x, pos.y));
//			schedule.scheduleRepeating(head);
//			carT.head = head;
//			objPE.setNoCollisions(carT, head);
//			PinJoint pj = new PinJoint(pos, head, carT);
//			objPE.register(pj);
//		}
    }

    public static void main(String[] args)
    {
        doLoop(SimLifeArena.class, args);
        System.exit(0);
    }

    public static boolean removeHerR(HerR herR)
    {
    	if (fieldEnvironment.exists(herR))
    	{
//    		pos = herR.getHome();

    		// remove died HerR
    		objPE.unRegister(herR);
    		fieldEnvironment.allObjects.remove(herR);

    		// if one dies, create a new one
    		// TODO: schedule isn't possible outside start() ?? if it is so we can't born animals in runtime ??
//    		herR = new HerR(pos, new Double2D(0, 0));
//    		fieldEnvironment.setObjectLocation(herR, new sim.util.Double2D(pos.x, pos.y));
//    		objPE.register(herR);
//    		schedule.scheduleRepeating(herR);

    		return(true);
    	}
    	else
    		return(false);
    }
}
