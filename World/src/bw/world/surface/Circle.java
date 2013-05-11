/**
 * Circle.java: BW - bw.world.surface
 * 
 * @author kohlhauser
 * 01.10.2009, 13:47:11
 */
package bw.world.surface;



/**
 * Draws a circle shaped surface
 * 
 * @author kohlhauser
 * 01.10.2009, 13:47:11
 * 
 */
public class Circle 
{
	protected int centerX, centerY;
	protected int radius;
	protected int surface;
	
	public clsSurfaceHandler surfaceHandler = clsSurfaceHandler.getInstance();
	
	public Circle(int centerX, int centerY, int radius, int surface)
	{
		this.centerX = centerX;
		this.centerY = centerY;
		this.radius = radius;
		this.surface = surface;
		
		circleMidpoint();
	}
	
	protected void circleMidpoint()
	{
		SurfacePoint circlePoint = new SurfacePoint();
		
		int p = 1 - radius;
		
		circlePoint.setCoords(0, radius);
		
		circlePlotPoints(centerX, centerY, circlePoint);
		
		while (circlePoint.getX() < circlePoint.getY())
		{
			circlePoint.incrementX();
			if (p < 0)
				p += 2 * circlePoint.getX() + 1;
			else
			{
				circlePoint.decrementY();
				p += 2 * (circlePoint.getX() - circlePoint.getY()) + 1;
			}
			circlePlotPoints(centerX, centerY, circlePoint);
		}	
	}

	protected void circlePlotPoints(int xc, int yc, SurfacePoint circlePoint)
	{
		fillCircle(xc + circlePoint.getX(), yc + circlePoint.getY(), circlePoint);
		fillCircle(xc - circlePoint.getX(), yc + circlePoint.getY(), circlePoint);
		fillCircle(xc + circlePoint.getX(), yc - circlePoint.getY(), circlePoint);
		fillCircle(xc - circlePoint.getX(), yc - circlePoint.getY(), circlePoint);
		fillCircle(xc + circlePoint.getY(), yc + circlePoint.getX(), circlePoint);
		fillCircle(xc - circlePoint.getY(), yc + circlePoint.getX(), circlePoint);
		fillCircle(xc + circlePoint.getY(), yc - circlePoint.getX(), circlePoint);
		fillCircle(xc - circlePoint.getY(), yc - circlePoint.getX(), circlePoint);
	}
	
	
	//@TODO: Correct this method
	protected void fillCircle(int xc, int yc, SurfacePoint circlePoint)
	{
		if (yc <= centerY)
		{
			while (yc <= centerY)
			{
				surfaceHandler.setSurface(xc, yc, surface);
				yc++;
			}
		}
		else
		{
			while (yc > centerY)
			{
				surfaceHandler.setSurface(xc, yc, surface);
				yc--;
			}
		}
	}
	
	class SurfacePoint
	{
		private int x = 0, y = 0;

		protected void setCoords(int x, int y)
		{
			this.x = x;
			this.y = y;
		}
		
		protected int getX()
		{
			return x;
		}
		
		protected int getY()
		{
			return y;
		}
		
		protected void incrementX()
		{
			x++;
		}
		
		protected void decrementY()
		{
			y--;
		}
	}
}
