package TestApps.src.BumpTest;

import java.awt.Color;

import sim.physics2D.physicalObject.StationaryObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;

public class Pheromones extends StationaryObject2D {
	public double radius;
	
	public Pheromones(Double2D pos) {
		
		this.setCoefficientOfRestitution(1);
        this.setPose(pos, new Angle(0));
        this.setShape(new sim.physics2D.shape.Circle(.1, Color.red));
       
		// TODO Auto-generated constructor stub
	}
}
