package TestApps.src.BumpTest;
import java.awt.*;

import sim.physics2D.physicalObject.*;
import sim.physics2D.util.*;
public class Obstacles extends StationaryObject2D{
	public double radius;
    
	public Obstacles(Double2D pos, int width, int height)
        {
        this.setCoefficientOfRestitution(1);
        this.setPose(pos, new Angle(0));
        this.setShape(new sim.physics2D.shape.Rectangle(width, height, Color.red));
        } 
}
