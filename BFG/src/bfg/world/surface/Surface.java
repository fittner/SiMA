package bfg.world.surface;


import java.awt.Color;

import sim.util.gui.SimpleColorMap;

/**
 * An interface for the friction values. The friction values are defined here.
 * @author kohlhauser
 *
 */
public interface Surface
{
	//indices for the friction table
	public static final int STATICFRICTION = 0, KINETICFRICTION = 1;
	public static final int VERYLOW = 0, LOW = 1, MEDIUM = 2, HIGH = 3, VERYHIGH = 4;
	public static final int NUMBEROFSURFACES = 5;
	
	public static final double[][] FRICTIONTABLE = 
		{{0.05, 0.04}, 	//VERYLOW friction
		{0.3, 0.26},	//LOW friction
		{0.5, 0.4},		//MEDIUM friction
		{0.7, 0.65},	//HARD friction
		{1.0, 0.95}};	//VERYHARD friction
	//public static final double DEFAULTCOEFFICIENT = 0.5;
	
	public static SimpleColorMap colorMap = new SimpleColorMap(new Color[] {
			Color.yellow, 
			Color.red, 
			Color.green, 
			Color.gray, 
			Color.white });
}
