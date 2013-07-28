/**
 * Position.java: BW - bw.entities.logger
 * 
 * @author deutsch
 * 30.04.2011, 13:47:29
 */
package bw.entities.logger;

import statictools.clsSimState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 30.04.2011, 13:47:29
 * 
 */
public class Position {
	public final long step;
	public final double x;
	public final double y;
	public final double a;
	
	public Position(double x, double y, double a) {
		this.x=x;
		this.y=y;
		this.a=a;
		
		this.step = clsSimState.getSteps();
	}
	
	public String toCSV() {
		return step+";"+x+";"+y+";"+a;
	}
}
