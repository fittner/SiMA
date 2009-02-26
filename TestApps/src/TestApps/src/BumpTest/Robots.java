package TestApps.src.BumpTest;
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
	        
	    public int numCans = 1;
	    public int numBots = 2;
	    public int pID=1;
	    public int getNumCans() { return numCans; }
	    public int getNumBots() { return numBots; }
	    public void setNumCans(int val) { if (val > 0) numCans = val; }
	    public void setNumBots(int val) { if (val > 0) numBots = val; }
	        
	    public Continuous2D fieldEnvironment;
//	    public Continuous2D Pfield;
	    
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
//	        Pfield = new Continuous2D(25, (xMax - xMin), (yMax - yMin));
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
	        BotHome home;
	        AntBot abot;
	        Obstacles obstacle;
	        

	        
	       
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
	        
//	        BotHome
	        pos = new Double2D(10,10);
	        home = new BotHome(pos);
	        fieldEnvironment.setObjectLocation(home, new sim.util.Double2D(pos.x, pos.y));
	        objPE.register(home);
	        
	        
//	       Obstacles
	        pos = new Double2D(50,60);
	        obstacle = new Obstacles(pos, 5, 40);
	        fieldEnvironment.setObjectLocation(obstacle, new sim.util.Double2D(pos.x, pos.y));
	        objPE.register(obstacle);
	        
	        pos = new Double2D(100,100);
	        obstacle = new Obstacles(pos, 40, 5);
	        fieldEnvironment.setObjectLocation(obstacle, new sim.util.Double2D(pos.x, pos.y));
	        objPE.register(obstacle);
	        
	        
//	        Pheromone loading
	        double a=16;
	        double b=16;
	        Double2D position=new Double2D(a,b);
	        Pheromone pheromone=new Pheromone(1,position);
	        fieldEnvironment.setObjectLocation(pheromone, new sim.util.Double2D(pos.x, pos.y));
	        SpeadingPheromones(fieldEnvironment, pID, position);
	        
//	        
//	        pos = new Double2D(100,60);
//	        wall = new Wall(pos, 50, 5);
//	        fieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
//	        objPE.register(wall);
//	        
//	        pos = new Double2D(100,150);
//	        wall = new Wall(pos, 5, 50);
//	        fieldEnvironment.setObjectLocation(wall, new sim.util.Double2D(pos.x, pos.y));
//	        objPE.register(wall);
//	        
	                
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
	                
//	        for (int i = 0; i < numBots; i++)
//	            {
//	            double x = Math.max(Math.min(random.nextDouble() * xMax, xMax - 20), 20);
//	            double y = Math.max(Math.min(random.nextDouble() * yMax, yMax - 20), 50);
//	                        
//	            pos = new Double2D(x, y);
//	            bot = new Bot(pos, new Double2D(0, 0));
//	            objPE.register(bot);
//	            fieldEnvironment.setObjectLocation(bot, new sim.util.Double2D(pos.x, pos.y));
//	            schedule.scheduleRepeating(bot);
//	                        
//	            //FixedAngle fa = new FixedAngle();
//	            //NoPerpMotion npm = new NoPerpMotion();
//	                        
//	            Effector effector;
//	                        
//	            pos = new Double2D(x + 12, y + 6);
//	            effector = new Effector(pos, new Double2D(0, 0), 1, Color.gray);
//	            objPE.register(effector);
//	            fieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
//	            schedule.scheduleRepeating(effector);
//	            bot.e1 = effector;
//	                        
//	            objPE.setNoCollisions(bot, effector);
//	                        
//	            //fa.AddPhysicalObject(effector);
//	            //npm.AddPhysicalObject(bot);
//	                        
//	            PinJoint pj = new PinJoint(pos, effector, bot);
//	            //fa.AddPhysicalObject(bot);
//	                
//	            objPE.register(pj);
//	            //objPE.register(fa);
//	            //objPE.register(npm);
//	                        
//	            pos = new Double2D(x + 12, y - 6);
//	            effector = new Effector(pos, new Double2D(0, 0), 1, Color.gray);
//	            objPE.register(effector);
//	            fieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
//	            schedule.scheduleRepeating(effector);
//	            bot.e2 = effector;
//	                        
//	            objPE.setNoCollisions(bot, effector);
//	                        
//	            pj = new PinJoint(pos, effector, bot);
//	            //fa = new FixedAngle();
//	            //fa.AddPhysicalObject(effector);
//	            //fa.AddPhysicalObject(bot);
//	                
//	            objPE.register(pj);
//	            //objPE.register(fa);
//	            }
	        

       

	        //	        AntBot Load
	        
	        
	        for (int i = 0; i < numBots; i++){
	        
	        	double x = Math.max(Math.min(random.nextDouble() * xMax, xMax - 20), 20);
	            double y = Math.max(Math.min(random.nextDouble() * yMax, yMax - 20), 50);
                        
	        pos = new Double2D(x, y); 	
//	       double x=12;
//	       double y=12;
	       
	        
            abot = new AntBot(pos,new Double2D(0, 0));
            objPE.register(abot);
            fieldEnvironment.setObjectLocation(abot, new sim.util.Double2D(pos.x, pos.y));
            schedule.scheduleRepeating(abot);

            //	        Effector joint with AntBot
            Effector effector;
            pos = new Double2D(x + 6, y + 3);
            effector = new Effector(pos, new Double2D(0, 0), 1, Color.gray);
            objPE.register(effector);
            fieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
            schedule.scheduleRepeating(effector);
            abot.e1 = effector;
                        
            objPE.setNoCollisions(abot, effector);
                        
            //fa.AddPhysicalObject(effector);
            //npm.AddPhysicalObject(bot);
                        
            PinJoint pj = new PinJoint(pos, effector, abot);
            //fa.AddPhysicalObject(bot);
                
            objPE.register(pj);
          
                        
            pos = new Double2D(x + 6, y - 3);
            effector = new Effector(pos, new Double2D(0, 0), 1, Color.gray);
            objPE.register(effector);
            fieldEnvironment.setObjectLocation(effector, new sim.util.Double2D(pos.x, pos.y));
            schedule.scheduleRepeating(effector);
            abot.e2 = effector;
                        
            objPE.setNoCollisions(abot, effector);
                        
            pj = new PinJoint(pos, effector, abot);
            
            objPE.register(pj);
	        }
            
            
	        
            
        schedule.scheduleRepeating(objPE);
	        }
	    
	    public void SpeadingPheromones(Continuous2D fieldEnvironment,int id, Double2D pos){
	    	 Pheromone pheromone=new Pheromone(id,pos);
	    	 fieldEnvironment.setObjectLocation(pheromone, new sim.util.Double2D(pos.x, pos.y));
	    		 
	    	 
	    }
	    
	    public void SpeadingHomePheromones(Continuous2D fieldEnvironment, Double2D pos){
	    	 HomePheromone h_pheromone=new HomePheromone(pos);
	    	 fieldEnvironment.setObjectLocation(h_pheromone, new sim.util.Double2D(pos.x, pos.y));
	    		 
	    	 
	    }
	                        
	    public static void main(String[] args)
	        {
	    	System.out.println();
	        doLoop(Robots.class, args);
	        System.exit(0);
	        }    
}
