/**
 * Poligon.java: BW - bw.world.surface
 * 
 * @author kohlhauser
 * 15.09.2009, 16:13:32
 */
package bw.world.surface;

//import java.awt.Point;
//import java.util.Vector;


/**
 * This class implements a Polygon which can output its Points. Only convex polygons are allowed.
 * DOCUMENT (kohlhauser) - insert description 
 * 
 * @author kohlhauser
 * 15.09.2009, 16:13:33
 * 
 */
public class Polygon 
{
	public static final int X = 0;
	public static final int Y = 1;
	
	protected int[][] vertices;
	
	protected int numVertex;
	protected int surface;
	
	protected clsSurfaceHandler surfaceHandler = clsSurfaceHandler.getInstance();
	
	protected EdgeList activelist;
	protected EdgeList[] edgelist;

	public Polygon (int[][] _vertices, int surface)
	{
		//super (_vertices, _color);
		vertices = _vertices;
		this.surface = surface;

		numVertex = vertices.length;
		
		for (int i = 0; i < numVertex; i++)
		{
			vertices[i][0] = vertices[i][X];
			vertices[i][1] = vertices[i][Y];
		}
		
		setSurfacePolygon();
	}

	/**
	 * Build edge list for this polygon.
	 */

	protected void buildEdgeList ()
	{
		int[] v1 = new int[2];
		int[] v2 = new int[2];
	
		v1[X] = vertices[numVertex - 1][X];
		v1[Y] = vertices[numVertex - 1][Y];

		for (int i=0; i<numVertex; i++)
		{
			v2[X] = vertices[i][X];
			v2[Y] = vertices[i][Y];

			if (v1[Y] != v2[Y])
			{
				if (v1[Y] < v2[Y])
				{
					// Our edges always end at yUpper-1. This makes sure that a monotonically increasing or decreasing pair
					// of edges shares no scanline, but produces a small error if yUpper is the highest scanline of both edges.

					edgelist[v1[Y]].insert (v2[Y] - 1, 
										   (double)v1[X], 
										   (double)(v2[X] - v1[X])/(double)(v2[Y] - v1[Y]));
				}
				else
				{
					edgelist[v2[Y]].insert (v1[Y] - 1,
										   (double)v2[X], 
										   (double)(v1[X] - v2[X])/(double)(v1[Y] - v2[Y]));
				}
			}

			v1[X] = v2[X];
			v1[Y] = v2[Y];
		}
	}

	/**
	 * Fill a scanline.
	 *
	 * @param scan scanline to fill
	 * @param canvas Canvas to draw to
	 */

	protected void fillScan (int scan)
	{
		Edge pStart, pStop;

		pStart = activelist.getHead();

		while (pStart != null)
		{
			pStop = pStart.next;

			for (int i = (int)pStart.xIntersect; i<(int)pStop.xIntersect; i++)
			{
				surfaceHandler.setSurface(i, scan, surface);
			}

			pStart = pStop.next;
		}
	}

	/**
	 * Add edges that are new for the current scanline to the active edge list.
	 */

	private void buildActiveList (int scan)
	{
		Edge p, q;

		p = edgelist[scan].getHead ();

		while (p != null)
		{
			q = p.next;
			activelist.insert (p);
			p = q;
		}
	}

	/**
	 * Draw filled polygon.
	 *
	 * @param canvas Canvas to draw the polygon to
	 */

	protected void setSurfacePolygon()
	{ 
		if (numVertex == 0) return;

		int height = surfaceHandler.getHeight ();

		edgelist = new EdgeList[height];
		for (int i=0; i<height; i++) edgelist[i] = new EdgeList();
		buildEdgeList();

		activelist = new EdgeList();

		for (int scan=0; scan<height; scan++)
		{
			buildActiveList (scan);

			if (!activelist.isEmpty())
			{
				fillScan (scan);
				activelist.update (scan);
				activelist.resort ();
			}
		}
	}

}
