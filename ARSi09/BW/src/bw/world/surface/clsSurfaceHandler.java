package bw.world.surface;


import config.clsBWProperties;

import sim.field.grid.IntGrid2D;
import sim.physics2D.util.Double2D;
import sim.util.gui.SimpleColorMap;

/**
 * Helper class to manage a surface. An IntGrid2D is used as the holder for a surface. 
 * This class implements the singleton pattern. 
 * The values are stored as an array. Therefore if the size of the world is given as width x height 
 * the indices for the squares range from 0 to (width - 1) and 0 to (height - 1).
 * @author kohlhauser
 *
 */
public class clsSurfaceHandler implements itfSurface
{
	private static final clsSurfaceHandler soInstance = new clsSurfaceHandler();
	protected IntGrid2D moSurfaceGrid;
	int mnWidth, mnHeight;
	
	//for properties
	public static final String P_NUMSURFACES = "numsurfaces";
	public static final String P_STATICFRICTION = "staticfriction";
	public static final String P_KINETICFRICTION = "kineticfriction";
	public static final String P_SURFACECOLOR = "surfacecolor";
	
	private clsSurfaceHandler()
	{

	}
	
	/**
	 * This method returns the only instance of the Surface Handler that is created. 
	 * @return
	 */
	public static clsSurfaceHandler getInstance()
	{
		return soInstance;
	}
	
	/**
	 * Creates a new world with the dimensions "width" and "height" and a standard surface.
	 * @param pnWidth
	 * @param pnHeight
	 * @return
	 */
	public IntGrid2D createWorld(int pnWidth, int pnHeight)
	{
		createWorld(pnWidth, pnHeight, MEDIUM);
		return moSurfaceGrid;
	}
	
	/**
	 * Creates a new world with the dimensions "width" and "height" and the surface index.
	 * @param pnWidth
	 * @param pnHeight
	 * @param pnSurface
	 * @return
	 */
	public IntGrid2D createWorld(int pnWidth, int pnHeight, int pnSurface)
	{
		this.mnWidth = pnWidth;
		this.mnHeight = pnHeight;
		moSurfaceGrid = new IntGrid2D(pnWidth, pnHeight);
		//width and height are reduced, otherwise the boundaries would be one square of and cause an exception
		this.mnWidth--;
		this.mnHeight--;
		moSurfaceGrid.setTo(pnSurface);		//setting all tiles to the same value.
		
		return moSurfaceGrid;
	}
	

	//TODO: Rewrite: Use properties
	public void createWorld(String poPrefix, clsBWProperties poProp)
	{
		String pre = clsBWProperties.addDot(poPrefix);
	}
	
	public IntGrid2D getGrid()
	{
		return moSurfaceGrid;
	}
	
	public SimpleColorMap getColorMap()
	{
		return soColorMap;
	}
	
	public int getSurface(int pnX, int pnY)
	{
		//check if the coordinates are within the boundaries of the world
		if (pnX > mnWidth)
			pnX = mnWidth;
		if (pnY > mnHeight)
			pnY = mnHeight;
		if (pnX < 0)
			pnX = 0;
		if (pnY < 0)
			pnY = 0;
		
		return moSurfaceGrid.get(pnX, pnY);
	}
	
	public int getSurface(Double2D poPosition)
	{
		int nX, nY;
		
		//don't make your world larger than (sizeof(int) x sizeof(int))
		nX = (int) Math.round(poPosition.x);
		nY = (int) Math.round(poPosition.y);
		
		//check if the coordinates are within the boundaries of the world
		if (nX > mnWidth)
			nX = mnWidth;
		if (nY > mnHeight)
			nY = mnHeight;
		if (nX < 0)
			nX = 0;
		if (nY < 0)
			nY = 0;
		
		return moSurfaceGrid.get(nX, nY);
	}
	
	public void setSurface(int pnX, int pnY, int pnSurface)
	{
		if(pnX >= 0 && pnX <= mnWidth && pnY >= 0 && pnY <= mnHeight)
			moSurfaceGrid.set(pnX, pnY, pnSurface);
	}
	
	public void setSurface(int pnStartX, int pnStartY, int pnEndX, int pnEndY, int pnSurface)
	{
		pnEndX--;
		pnEndY--;
		//check the boundaries of the world and set the surfaces
		if (pnStartX >= 0 && pnStartY >= 0 && pnEndX >= 0 && pnEndY >= 0 && 
				pnStartX < mnWidth && pnStartY < mnHeight && pnEndX <= mnWidth && 
				pnEndY <= mnHeight && pnSurface >= 0 && pnSurface < itfSurface.NUMBEROFSURFACES)
		{
			for (int i = pnStartX; i < pnEndX; i++)
				for (int j = pnStartY; j < pnEndY; j++)
					moSurfaceGrid.set(i, j, pnSurface);
		}
	}
	
	public double getStaticFriction(int pnX, int pnY)
	{
		//check if the coordinates are within the boundaries of the world
		if (pnX > mnWidth)
			pnX = mnWidth;
		if (pnY > mnHeight)
			pnY = mnHeight;
		if (pnX < 0)
			pnX = 0;
		if (pnY < 0)
			pnY = 0;
		
		return itfSurface.FRICTIONTABLE[moSurfaceGrid.get(pnX, pnY)][STATICFRICTION];
	}
	
	public double getStaticFriction(Double2D poPosition)
	{
		int nX, nY;
		
		 //dont make your world larger than (sizeof(int) x sizeof(int))
		nX = (int) Math.round(poPosition.x);
		nY = (int) Math.round(poPosition.y);
		
		//check if the coordinates are within the boundaries of the world
		if (nX > mnWidth)
			nX = mnWidth;
		if (nY > mnHeight)
			nY = mnHeight;
		if (nX < 0)
			nX = 0;
		if (nY < 0)
			nY = 0;
		
		return itfSurface.FRICTIONTABLE[moSurfaceGrid.get(nX, nY)][STATICFRICTION];
	}
	
	public double getKineticFriction(int pnX, int pnY)
	{
		//check if the coordinates are within the boundaries of the world
		if (pnX > mnWidth)
			pnX = mnWidth;
		if (pnY > mnHeight)
			pnY = mnHeight;
		if (pnX < 0)
			pnX = 0;
		if (pnY < 0)
			pnY = 0;
		
		return itfSurface.FRICTIONTABLE[moSurfaceGrid.get(pnX, pnY)][KINETICFRICTION];
	}
	
	public double getKineticFriction(Double2D position)
	{
		int nX, nY;
		
		//dont make your world larger than (sizeof(int) x sizeof(int))
		nX = (int) Math.round(position.x);
		nY = (int) Math.round(position.y);
		
		//check if the coordinates are within the boundaries of the world
		if (nX > mnWidth)
			nX = mnWidth;
		if (nY > mnHeight)
			nY = mnHeight;
		if (nX < 0)
			nX = 0;
		if (nY < 0)
			nY = 0;
		
		return itfSurface.FRICTIONTABLE[moSurfaceGrid.get(nX, nY)][KINETICFRICTION];
	}
	
	public int getHeight()
	{
		return mnHeight;
	}
}
