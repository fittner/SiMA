/**
 * 2011/06/15 TD - added javadoc comments and sanitized class.
 */
package display;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import properties.clsProperties;



import sim.display.GUIState;
import sim.util.Bag;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Double2D;
import sim.portrayal.LocationWrapper;

/**
 * Using this ARSDisplay2D in your MainWithUI supports drag and drop of objects.
 * 
 * @author langr
 **/
public class Display2D extends sim.display.Display2D {
	/** property entry identifier */
	public static final String P_WIDTH = "width";
	/** property entry identifier */
	public static final String P_HEIGHT = "height";
	/** property entry identifier */
	public static final String P_INTERVAL = "interval";

	private static final long serialVersionUID = -5129774859180110482L;

	/** necessary for drag and drop */
	private Bag[] moHitObjects;
	
	/**
	 * Constructor that adds mouse listeners to the created Display2D. Two mouse listeners fetch information on dragging and moving. 
	 * Two mouse listeners fetch information on the state of the left mouse button.
	 * 
	 * @author deutsch
	 * 15.06.2011, 15:54:59
	 *
	 * @param width Width of the simulation display
	 * @param height Height of the simulation display
	 * @param simulation GUIState
	 * @param interval Interval duration for a step
	 */
	public Display2D(double width, double height, GUIState simulation, long interval) {
		super(width, height, simulation, interval);

		// Adds the mouse listener for the mouseDragged event 
		insideDisplay.addMouseMotionListener(new MouseMotionListener() 
		{
			@Override
			public void mouseDragged(MouseEvent e) {

				Point oPoint = e.getPoint();
				moveSelectedObject(oPoint);
			    repaint();
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
			}
		});
		
	    //Add listener for mouse-buttons to start and stop drag'n drop
		insideDisplay.addMouseListener(new MouseAdapter()
	        {
	        @Override
			public void mousePressed(MouseEvent e)
	            {
	        		Point point = e.getPoint();
	        		Rectangle2D.Double rect =  new Rectangle2D.Double( point.x, point.y, 1, 1 );
	        		moHitObjects = objectsHitBy(rect);
	            }
	        @Override
	        public void mouseReleased(MouseEvent e)
	            {
	        		Point oPoint = e.getPoint();
					//moveSelectedObject(oPoint);
				    repaint();
	            }
	        });
	}
	
	/**
	 * Static factory method that produces a Display2D instance according to the provided parameters
	 *
	 * @param poPrefix identifier prefix for the properites
	 * @param poProp properties in the form of clsProperties
	 * @param simulation GUIState
	 * @return a new instance of Display2D
	 */
	public static Display2D createDisplay2d(String poPrefix, clsProperties poProp, GUIState simulation) {
		return applyProperties(poPrefix, poProp, simulation);
	}
	
	/**
	 * Creates an instance of Display2D according to the provided properties.
	 * 
	 * @param poPrefix identifier prefix for the properites
	 * @param poProp properties in the form of clsProperties
	 * @param simulation GUIState
	 * @return a new instance of Display2D
	 */
	private static Display2D applyProperties(String poPrefix, clsProperties poProp, GUIState simulation) {
		String pre = clsProperties.addDot(poPrefix);
		
		double width = poProp.getPropertyDouble(pre+P_WIDTH);
		double height = poProp.getPropertyDouble(pre+P_HEIGHT);
		int interval = poProp.getPropertyInt(pre+P_INTERVAL);
		
		return new Display2D(width, height, simulation, interval);
	}
	
	/**
	 * Provides the default parameters for this class. 
	 */
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();
		
		oProp.setProperty(pre+P_HEIGHT, 600.0);
		oProp.setProperty(pre+P_WIDTH, 600.0);
		oProp.setProperty(pre+P_INTERVAL, 1);
		
		return oProp;
	}
	
	/**
	 * Moves the selected object stored in moHitObjects to the given position. Requires a repaint-call afterwards!
	 * 
	 * @param poPoint
	 */
	public void moveSelectedObject(Point poPoint)
	{
		
		//moHitObjects.length = count ob bags
	    for(int bag=0;bag<moHitObjects.length;bag++)
	    {
	    	int dragObjectCounter = 0;
	    	//number of objects in bag
	    	for(int numObj=0;numObj<moHitObjects[bag].size();numObj++)
		    {
	    		if(dragObjectCounter==0) //only drag the first object in the bag
	    		{
			    	Object oHitObject = moHitObjects[bag].objs[numObj];
			    	if( oHitObject instanceof LocationWrapper )
			    	{
			    		Object oLocation = ((LocationWrapper)oHitObject).getObject();
			    		if( oLocation instanceof MobileObject2D )
			        	{
			    			MobileObject2D oMobile = (MobileObject2D)oLocation;
			    			oMobile.setPose(transformToMasonCoord(poPoint), oMobile.getOrientation());
			    			dragObjectCounter++;
			        	}
			    	}
	    		}
		    }
	    }
	}
	
	/**
	 * converting mouse coordinates to world coordinates - considering the zoom scale 
	 */
	public Double2D transformToMasonCoord(Point poP) {
        double scale = getScale();
        // compute WHERE we need to draw
        int origindx = 0;
        int origindy = 0;

        // offset according to user's specification
        origindx += (int)(insideDisplay.xOffset*scale);
        origindy += (int)(insideDisplay.yOffset*scale);

        // for information on why we use getViewRect, see computeClip()

        Rectangle2D fullComponent = insideDisplay.getVisibleRect();//.getViewRect();
        
        if (fullComponent.getWidth() > (insideDisplay.width * scale))
            origindx = (int)((fullComponent.getWidth() - insideDisplay.width*scale)/2);
        if (fullComponent.getHeight() > (insideDisplay.height*scale))
            origindy = (int)((fullComponent.getHeight() - insideDisplay.height*scale)/2);

        return new Double2D(((poP.x-origindx)/scale)/3,((poP.y-origindy)/scale)/3);
	}
}
