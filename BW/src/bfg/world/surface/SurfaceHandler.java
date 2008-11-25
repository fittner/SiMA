package bfg.world.surface;


import java.io.File;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import sim.field.grid.IntGrid2D;
import sim.physics2D.util.Double2D;
import sim.util.gui.SimpleColorMap;

/**
 * Helper class to manage a surface. An IntGrid2D is used as the holder for a surface. 
 * This class implements the singleton pattern. 
 * @author kohlhauser
 *
 */
public class SurfaceHandler implements Surface
{
	private static final SurfaceHandler instance = new SurfaceHandler();
	protected IntGrid2D surfaceGrid;
	int width, height;
	
	private SurfaceHandler()
	{

	}
	
	/**
	 * This method returns the only instace of the Surface Handler that is created. 
	 * @return
	 */
	public static SurfaceHandler getInstance()
	{
		return instance;
	}
	
	/**
	 * Creates a new world with the dimensions "width" and "height" and a standard surface.
	 * @param width
	 * @param height
	 * @return
	 */
	public IntGrid2D createWorld(int width, int height)
	{
		createWorld(width, height, MEDIUM);
		return surfaceGrid;
	}
	
	/**
	 * Creates a new world with the dimensions "width" and "height" and the surface index.
	 * @param width
	 * @param height
	 * @param surface
	 * @return
	 */
	public IntGrid2D createWorld(int width, int height, int surface)
	{
		this.width = width;
		this.height = height;
		surfaceGrid = new IntGrid2D(width, height);
		surfaceGrid.setTo(surface);		//setting all tiles to the same value.
		
		return surfaceGrid;
	}
	
	/**
	 * Creates a new world from an XML-File.
	 * @param xmlFile
	 * @return
	 */
	public IntGrid2D createWorld(File xmlFile)
	{
		SAXParserFactory spf = SAXParserFactory.newInstance();
		SAXParser parser = null;
		
		System.out.println("Creating parser...");
		try
		{
			parser = spf.newSAXParser();
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
			System.exit(1);
		}
		System.out.println("Parser created.");
		
		SAXHandler handler = new SAXHandler();
		
		System.out.println("Calling parser...");
		try
		{
			parser.parse(xmlFile, handler);
		}
		catch (Exception e)
		{
			e.printStackTrace(System.err);
		}
		System.out.println("Done parsing.");
		
		System.out.println("Receiving the world...");
		surfaceGrid = handler.getSurfaceGrid();
		width = surfaceGrid.getWidth();
		height = surfaceGrid.getHeight();
		if (surfaceGrid != null)
			System.out.println("Got it!");
		
		return surfaceGrid;
	}
	
	public IntGrid2D getGrid()
	{
		return surfaceGrid;
	}
	
	public SimpleColorMap getColorMap()
	{
		return colorMap;
	}
	
	public int getSurface(int x, int y)
	{
		//check if the coordinates are within the boundaries of the world
		if (x > width)
			x = width;
		if (y > height)
			y = height;
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		
		return surfaceGrid.get(x, y);
	}
	
	public int getSurface(Double2D position)
	{
		int x, y;
		
		 //dont make your world larger than (int x int)
		x = (int) Math.round(position.x);
		y = (int) Math.round(position.y);
		
		//check if the coordinates are within the boundaries of the world
		if (x > width)
			x = width;
		if (y > height)
			y = height;
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		
		return surfaceGrid.get(x, y);
	}
	
	public void setSurface(int x, int y, int surface)
	{
		surfaceGrid.set(x, y, surface);
	}
	
	public double getStaticFriction(int x, int y)
	{
		//check if the coordinates are within the boundaries of the world
		if (x > width)
			x = width;
		if (y > height)
			y = height;
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		
		return Surface.FRICTIONTABLE[surfaceGrid.get(x, y)][STATICFRICTION];
	}
	
	public double getStaticFriction(Double2D position)
	{
		int x, y;
		
		 //dont make your world larger than (int x int)
		x = (int) Math.round(position.x);
		y = (int) Math.round(position.y);
		
		//check if the coordinates are within the boundaries of the world
		if (x > width)
			x = width;
		if (y > height)
			y = height;
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		
		return Surface.FRICTIONTABLE[surfaceGrid.get(x, y)][STATICFRICTION];
	}
	
	public double getKineticFriction(int x, int y)
	{
		//check if the coordinates are within the boundaries of the world
		if (x > width)
			x = width;
		if (y > height)
			y = height;
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		
		return Surface.FRICTIONTABLE[surfaceGrid.get(x, y)][KINETICFRICTION];
	}
	
	public double getKineticFriction(Double2D position)
	{
		int x, y;
		
		 //dont make your world larger than (int x int)
		x = (int) Math.round(position.x);
		y = (int) Math.round(position.y);
		
		//check if the coordinates are within the boundaries of the world
		if (x > width)
			x = width;
		if (y > height)
			y = height;
		if (x < 0)
			x = 0;
		if (y < 0)
			y = 0;
		
		return Surface.FRICTIONTABLE[surfaceGrid.get(x, y)][KINETICFRICTION];
	}
}
