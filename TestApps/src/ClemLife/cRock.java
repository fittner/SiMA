package ClemLife;

import java.awt.*;

import ARSsim.physics2D.shape.clsRectangleImage;
import sim.physics2D.physicalObject.*;
import sim.physics2D.shape.Shape;
import sim.physics2D.util.*;

public class cRock extends StationaryObject2D
{
	private static final long serialVersionUID = 1L;

	private int width, height;


	public cRock(Double2D pos, int width, int height)
        {
		this.width = width;
		this.height = height;
        this.setCoefficientOfRestitution(1);
        this.setPose(pos, new Angle(0));
        Shape poShape = new clsRectangleImage(width, height, Color.GREEN,  "S:/ARS/PA/BWv1/BW/src/resources/images/wall1.jpg");
        this.setShape(poShape);
        
        //TEST


        }

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}
}

	
