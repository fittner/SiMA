package JNAOProxy;

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
	
	public static Command say(String msg) {
		Command c = new Command(eCommands.SAY);
		c.params.add(msg);
		
		return c;
	}
	
	public static Command reset() {
		Command c = new Command(eCommands.RESET);
		return c;
	}
	
	public static Command consume(int id) {
		Command c = new Command(eCommands.CONSUME);
		c.params.add(""+id);
		
		return c;
	}
	
	private static Command headmove(double yaw, double pitch, double speed) throws Exception {
		// yaw -- head up and down
		// pitch -- head left and right
		// speed -- speed of head movement
		// either yaw == 0 or pitch == 0
		
		Command c = new Command(eCommands.HEADMOVE);
		
		if (yaw > 1.0 || yaw < -1.0) {
			throw new java.lang.Exception("head yaw out of bounds. -1.0<"+yaw+"<1.0 violated");
		}
		if (pitch > 1.0 || pitch < -1.0) {
			throw new java.lang.Exception("head pitch out of bounds. -1.0<"+pitch+"<1.0 violated");
		}
		if (speed > 1.0 || speed < 0.0) {
			throw new java.lang.Exception("head speed out of bounds. -1.0<"+speed+"<1.0 violated");
		}
		if (yaw != 0.0 && pitch != 0.0) {
			throw new java.lang.Exception("simultaneous head yaw and head pitch movement is forbidden due to safety reasons");
		}
		
		c.params.add( ""+(int)(yaw*255.0) );
		c.params.add( ""+(int)(pitch*255.0) );
		c.params.add( ""+(int)(speed*255.0) );
		
		return c;
	}
	
	public static Command headyaw(double yaw, double speed) throws Exception {
		return headmove(yaw, 0.0, speed);
	}
	
	public static Command headpitch(double pitch, double speed) throws Exception {
		return headmove(0.0, pitch, speed);
	}
	
	public static Command headreset() {
		Command c = new Command(eCommands.HEADRESET);
		
		return c;
	}
	
	public static Command cower() {
		Command c = new Command(eCommands.COWER);
		
		return c;
	}
	
	public static Command getRandom() throws Exception {
		Command result;
		double force;
		double yaw;
		double pitch;
		boolean forward;
		boolean stiffness;
		Random rand = new Random();
		
		int r = rand.nextInt(8);
	   
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
			case 3: result = CommandGenerator.say("test"); break;
			case 4:  
				yaw = rand.nextDouble() * 2.0 - 1.0;
				force = rand.nextDouble();
				result = CommandGenerator.headyaw(yaw, force);
				break;
			case 5:  
				pitch = rand.nextDouble() * 2.0 - 1.0;
				force = rand.nextDouble();
				result = CommandGenerator.headpitch(pitch, force);
				break;
			case 6: result = CommandGenerator.headreset(); break;
			case 7: result = CommandGenerator.consume( rand.nextInt(10) ); break;
/* COWER, INITPOSE and STIFFNESS are not suitable for a random test run ...
			case ?: result = CommandGenerator.initpose(); break;
			case ?:
				if (rand.nextInt(2)==0) {
					stiffness = true;
				} else {
					stiffness = false;
				}
				result = CommandGenerator.stiffness(stiffness); break;
			case ?: result = CommandGenerator.cower(); break;
			case ?: result = CommandGenerator.reset(); break;
*/				
			default:
				throw new java.lang.Exception("unkown value "+r+" in switch statement");
		}
		
		return result;
	}

}
