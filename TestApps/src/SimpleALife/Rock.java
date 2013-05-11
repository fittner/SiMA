package SimpleALife;

import java.awt.*;
import sim.physics2D.physicalObject.*;
import sim.physics2D.util.*;

public class Rock extends StationaryObject2D
{
	private static final long serialVersionUID = 1L;

	private int width, height;
	private boolean isArenaLimit;

	public Rock(Double2D pos, int width, int height)
        {
		this.width = width;
		this.height = height;
        this.setCoefficientOfRestitution(1);
        this.setPose(pos, new Angle(0));
        this.setShape(new sim.physics2D.shape.Rectangle(width, height, Color.white));

        this.isArenaLimit = false;
        }

	public int getWidth()
	{
		return this.width;
	}

	public int getHeight()
	{
		return this.height;
	}

	public void setArenaLimit(boolean x)
	{
		if (x)
			this.isArenaLimit = true;
		else
			this.isArenaLimit = false;
	}

	public boolean getAreanaLimit()
	{
		return this.isArenaLimit;
	}
}
