package ClemLife;



import ARSsim.physics2D.util.clsPose;
import ec.util.*;
import sim.engine.*;
import sim.field.continuous.*;
import sim.field.grid.IntGrid2D;
import sim.physics2D.*;

import sim.physics2D.util.*;



public class ClemLifeMain extends SimState
{
	private static final long serialVersionUID = 1L;

	

	public static Continuous2D fieldEnvironment;
	public static PhysicsEngine2D objPE;
	public IntGrid2D surfaceGrid;


	public static final int arenaWidth = 1000;			// must although be changed in xmlFile
	public static final int arenaHeight = 1000;
	public static final int arenaWallThickness = arenaWidth/100;
	public static final int maxDist = arenaWidth*3;	// must be greater then the greatest distance in the environment

    public ClemLifeMain(long seed)
    {
        super(new MersenneTwisterFast(seed), new Schedule());
    }

    // Resets and starts a simulation
    @Override
    public void start()
    {
    	System.out.println("------ Start ------");	// debug

        super.start();  // clear out the schedule
        fieldEnvironment = new Continuous2D(25, (double)ClemLifeMain.arenaWidth, (double)ClemLifeMain.arenaHeight);

        // Add physics specific code here
        // Create and schedule the physics engine
		objPE = new PhysicsEngine2D();
		schedule.scheduleRepeating(objPE);

		//create world 
		Double2D pos = new Double2D(100, 100);
		clsPose oPose = new clsPose(100, 0, 0);
		
		
		
		pos = new Double2D(100, 100);
		cRock rock = new cRock(pos, 20, 300);
		fieldEnvironment.setObjectLocation(rock, new sim.util.Double2D(pos.x, pos.y));
		objPE.register(rock);

		
    }

    public static void main(String[] args)
    {
        doLoop(ClemLifeMain.class, args);
        System.exit(0);
    }

   
}
