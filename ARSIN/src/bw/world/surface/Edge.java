/**
 * Edge.java: BW - bw.world.surface
 * 
 * @author kohlhauser
 * 01.10.2009, 15:24:02
 */
package bw.world.surface;

/**
 * DOCUMENT (kohlhauser) - insert description 
 * 
 * @author kohlhauser
 * 01.10.2009, 15:24:02
 * 
 */
public class Edge 
{
	public int yUpper;
	public double xIntersect, dxPerScan;
	public Edge prev, next;

	/**
	 * Create an edge including only basic geometry information.
	 * 
	 * @param _yUpper geometry information
	 * @param _xIntersect geometry information
	 * @param _dxPerScan geometry information
	 */

	public Edge (int _yUpper, double _xIntersect, double _dxPerScan)
	{
		yUpper		= _yUpper;
		xIntersect	= _xIntersect;
		dxPerScan	= _dxPerScan;
		prev		= null;
		next		= null;
	}
}
