package SurfaceDetectionTest;
import sim.engine.*;
import sim.field.continuous.*;
import ec.util.*;
import sim.physics2D.util.*;
import sim.physics2D.constraint.*;
import java.awt.*;
import sim.physics2D.*;

public class Robots extends SimState{
	public double xMin = 0;
    public double xMax = 100;
    public double yMin = 0;
    public double yMax = 100;
        
    public int numCans = 4;
    public int numBots = 2;
    public int getNumCans() { return numCans; }
    public int getNumBots() { return numBots; }
    public void setNumCans(int val) { if (val > 0) numCans = val; }
    public void setNumBots(int val) { if (val > 0) numBots = val; }
    public Region[] region = new Region[]
    {
    new Region(0, 1, 50,50),
    };    
    
    public Continuous2D fieldEnvironment;
    public Continuous2D ground;
    
    public Robots(long seed)
        {
        this(seed, 200, 200);
        }
        
    public Robots(long seed, int width, int height)
        {
        super(new MersenneTwisterFast(seed));
        xMax = width; 
        yMax = height;
        createGrids();
        }
                
    void createGrids()
        {       
        fieldEnvironment = new Continuous2D(25, (xMax - xMin), (yMax - yMin));
        ground = new Continuous2D((xMax - xMin) > (yMax - yMin) ? (xMax - xMin) : (yMax - yMin), (xMax - xMin), (yMax - yMin));
       
        }
                
    // Resets and starts a simulation
    public void start()
        {
        super.start();  // clear out the schedule
        createGrids();

        PhysicsEngine2D objPE = new PhysicsEngine2D();
                
        Double2D pos;
        Double2D vel;
                
        Bot bot;
        Can can;
        Wall wall;

        // WALLS
        // HORIZ
        pos = new Double2D(100,0);
        wall = new Wall(pos, 193, 6);
        fieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        objPE.register(wall);
 
        pos = new Double2D(100,200);
        wall = new Wall(pos, 193, 6);
        fieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        objPE.register(wall);
                
        // VERT
        pos = new Double2D(0,100);
        wall = new Wall(pos, 6, 200);
        fieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        objPE.register(wall);
        
        pos = new Double2D(200,100);
        wall = new Wall(pos, 6, 200);
        fieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
        objPE.register(wall);
        
        //Grounds 
//        pos = new Double2D(100,70);
//        wall = new Wall(pos, 10, 50);
//        ground.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
//        objPE.register(wall);
        
        
        ground.setObjectLocation(region[0], new Double2D(region[0].originx, region[0].originy));
           
        
                
        for (int i = 0; i < numCans; i++)
            {
            double x = Math.max(Math.min(random.nextDouble() * xMax, xMax - 10), 10);
            double y = Math.max(Math.min(random.nextDouble() * yMax, yMax - 10), 60);
                        
            pos = new Double2D(x, y);
                        
            can = new Can(pos, new Double2D(0, 0));
            fieldEnvironment.setObjectLocation(can, new sim.util.Double2D(pos.x, pos.y));
            objPE.register(can);
            schedule.scheduleRepeating(can);
            }
                
        for (int i = 0; i < numBots; i++)
            {
            double x = Math.max(Math.min(random.nextDouble() * xMax, xMax - 20), 20);
            double y = Math.max(Math.min(random.nextDouble() * yMax, yMax - 20), 50);
                        
            pos = new Double2D(x, y);
            bot = new Bot(pos, new Double2D(0, 0));
            objPE.register(bot);
            fieldEnvironment.setObjectLocation(bot, new sim.util.Double2D(pos.x, pos.y));
            schedule.scheduleRepeating(bot);
                        
                        
            //FixedAngle fa = new FixedAngle();
            //NoPerpMotion npm = new NoPerpMotion();
                        
            Effector effector;
                        
            pos = new Double2D(x + 12, y + 6);
            effector = new Effector(pos, new Double2D(0, 0), 1, Color.gray);
            objPE.register(effector);
            fieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
            schedule.scheduleRepeating(effector);
            bot.e1 = effector;
                        
            objPE.setNoCollisions(bot, effector);
                        
            //fa.AddPhysicalObject(effector);
            //npm.AddPhysicalObject(bot);
                        
            PinJoint pj = new PinJoint(pos, effector, bot);
            //fa.AddPhysicalObject(bot);
                
            objPE.register(pj);
            //objPE.register(fa);
            //objPE.register(npm);
                        
            pos = new Double2D(x + 12, y - 6);
            effector = new Effector(pos, new Double2D(0, 0), 1, Color.gray);
            objPE.register(effector);
            fieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
            schedule.scheduleRepeating(effector);
            bot.e2 = effector;
                        
            objPE.setNoCollisions(bot, effector);
                        
            pj = new PinJoint(pos, effector, bot);
            //fa = new FixedAngle();
            //fa.AddPhysicalObject(effector);
            //fa.AddPhysicalObject(bot);
                
            objPE.register(pj);
            //objPE.register(fa);
            }
                
        // schedule the physics engine
        schedule.scheduleRepeating(objPE);
        }
                        
    public static void main(String[] args)
        {
        doLoop(Robots.class, args);
        System.exit(0);
        } 
}
