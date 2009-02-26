package TestApps.src.BumpTest;
import sim.physics2D.util.Double2D;
import sim.portrayal.*;
import sim.util.*;
import java.awt.geom.*;
import java.awt.*;
import java.io.*;

public class Pheromone extends SimplePortrayal2D{
	
	Shape pShape=new Ellipse2D.Double(0,0,1,1);
	Color pColor=Color.red;
	double origonX,origonY;
	int pID;
	public Pheromone(int id,Double2D position) {
		
		origonX=position.x;
		origonY=position.y;
		pID=id;
		// TODO Auto-generated constructor stub
	}
	Shape oldShape;
    Rectangle2D.Double oldDraw = null;
    
	public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
    {
    if (oldDraw == null ||
        oldDraw.x != info.draw.x ||
        oldDraw.y != info.draw.y ||
        oldDraw.width != info.draw.width ||
        oldDraw.height != info.draw.height) // new location or scale, must create
        {
        oldDraw = info.draw;
        AffineTransform transform = new AffineTransform();
        transform.translate(oldDraw.x, oldDraw.y);
        transform.scale(oldDraw.width, oldDraw.height);
        oldShape = transform.createTransformedShape(pShape);
        }
    
    // okay, now draw the shape, it's properly transformed
    graphics.setColor(pColor);
    graphics.fill(oldShape);
    }
	
}
