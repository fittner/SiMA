package jnao;

import java.util.Random;


public class CommandGenerator {
	public static Command move(double force, boolean forward) throws Exception {
		if (force > 1.0 || force < 0.0) {
			throw new java.lang.Exception("move force out of bounds. 0.0<"+force+"<1.0 violated");
		}
		
		Command c = new Command(eCommands.MOVE);
		
		int speed = (int)(force*255.0);
		
		c.params.add(""+speed);
		c.params.add(""+forward);
		
		return c;
	}
	
	public static Command halt() {
		Command c = new Command(eCommands.HALT);
		return c;
	}
	
	public static Command turn(double force) throws Exception {
		// force > 0 turn left
		// force < 0 turn right (or vice versa)
		if (force > 1.0 || force < -1.0) {
			throw new java.lang.Exception("move force out of bounds. -1.0<"+force+"<1.0 violated");
		}
		
		Command c = new Command(eCommands.TURN);
		
		int speed = (int)(force*255.0);
		
		c.params.add(""+speed);
		
		return c;
	}	
	
	public static Command initpose() {
		Command c = new Command(eCommands.INITPOSE);
		return c;
	}
	
	public static Command stiffness(boolean stiffness) {
		Command c = new Command(eCommands.STIFFNESS);
		c.params.add(""+stiffness);
		
		return c;
	}	
	
	public static Command getRandom() throws Exception {
		Command result;
		double force;
		boolean forward;
		boolean stiffness;
		Random rand = new Random();
		
		int r = rand.nextInt(3);
	   
		switch (r) {
			case 0:
				force = rand.nextDouble();
				if (rand.nextInt(2)==0) {
					forward = true;
				} else {
					forward = false;
				}
				result = CommandGenerator.move(force, forward);
				break;
			case 1:
				force = rand.nextDouble() * 2.0 - 1.0;
				result = CommandGenerator.turn(force);
				break;
			case 2: result = CommandGenerator.halt(); break;
/* INITPOSE and STIFFNESS are not suitable for a random test run ...
			case 3: result = CommandGenerator.initpose(); break;
			case 4:
				if (rand.nextInt(2)==0) {
					stiffness = true;
				} else {
					stiffness = false;
				}
				result = CommandGenerator.stiffness(stiffness); break;
*/				
			default:
				throw new java.lang.Exception("unkown value "+r+" in switch statement");
		}
		
		return result;
	}

}
