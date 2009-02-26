package MAV.src;
import sim.portrayal.*;
import sim.util.*;
import java.awt.geom.*;
import java.awt.*;
import java.io.*;
import java.awt.font.*;
public class Region extends SimplePortrayal2D{
	public static final Shape[] shapes = new Shape[] {
			new Ellipse2D.Double(0, 0, 100, 100)};

	// the location of the object's origin.
	public double originx;
	public double originy;
	int shapeNum;

	public static final Color[] surfacecolors = new Color[] { Color.white,
			Color.blue, Color.green, Color.red };
	public Shape shape;
	public Area area;
	public int surface;

	public Region(int num, int s, double x, double y) {
		shapeNum = num;
		shape = shapes[shapeNum];
		surface = s;
		area = new Area(shape);
		originx = x;
		originy = y;
	}

	// rule 1: don't fool around with graphics' own transforms because they
	// effect its clip, ARGH.
	// so we have to create our own transformed shape. To be more efficient, we
	// only transform
	// it if it's moved around.
	Shape oldShape;
	Rectangle2D.Double oldDraw = null;

	public void draw(Object object, Graphics2D graphics, DrawInfo2D info) {
		if (oldDraw == null || oldDraw.x != info.draw.x
				|| oldDraw.y != info.draw.y || oldDraw.width != info.draw.width
				|| oldDraw.height != info.draw.height) // new location or scale,
														// must create
		{
			oldDraw = info.draw;
			AffineTransform transform = new AffineTransform();
			transform.translate(oldDraw.x, oldDraw.y);
			transform.scale(oldDraw.width, oldDraw.height);
			oldShape = transform.createTransformedShape(shape);
		}

		// okay, now draw the shape, it's properly transformed
		graphics.setColor(surfacecolors[surface]);
		graphics.fill(oldShape);
	}

	

	 
}
