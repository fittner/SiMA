package SimpleALife;

import sim.engine.*;
import sim.physics2D.util.*;

public class PlaG implements Steppable
{
	private static final long serialVersionUID = 1L;

	private Double2D position;
	private int width, height;
	private int growCount, growCountStart;

	public int index;
	public double mass, currentMass;
	//private int steps = 0;

	public PlaG(Double2D pos, int width, int height)
	{
		this.position = pos;
		this.width = width;
    	this.height = height;

    	this.mass = width*height/1000;
    	this.currentMass = mass;
    	this.growCountStart = 2100;
    	this.growCount = growCountStart;
	}

	@Override
	public void step(SimState state)
	{
		if (currentMass <= 0)
		{
			if (growCount == 0)
			{
				currentMass = mass;
				growCount = growCountStart;
			}
			else
				growCount--;
		}
	}

	public int getWidth()
	{
    	return width;
	}

	public int getHeight()
	{
    	return height;
	}

	public Double2D getPosition()
	{
    	return position;
	}
}
