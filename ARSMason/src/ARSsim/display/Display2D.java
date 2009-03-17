package ARSsim.display;

import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;

import sim.display.GUIState;
import sim.util.Bag;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Double2D;
import sim.portrayal.LocationWrapper;

/**
 * @author langr
 *
 * Using this ARSDisplay2D in your MainWithUI supports drag and drop of objects.
 *
 */
public class Display2D extends sim.display.Display2D {

	//members necessary for drag and drop
	private Bag[] moHitObjects;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Display2D(double width, double height, GUIState simulation,
			long interval) {
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
	        public void mousePressed(MouseEvent e)
	            {
	        		Point point = e.getPoint();
	        		Rectangle2D.Double rect =  new Rectangle2D.Double( point.x, point.y, 1, 1 );
	        		moHitObjects = objectsHitBy(rect);
	            }
	        
	        public void mouseReleased(MouseEvent e)
	            {
	        		Point oPoint = e.getPoint();
					moveSelectedObject(oPoint);
				    repaint();
	            }
	        });
	}
	
	
	/**
	 * Moves the selected object stored in moHitObjects to the given position.
	 * Requires a repaint-call afterwards!
	 * 
	 * @param poPoint
	 */
	public void moveSelectedObject(Point poPoint)
	{
	    for(int x=0;x<moHitObjects.length;x++)
	    {
	    	Object oHitObject = moHitObjects[x].objs[x];
	    	if( oHitObject instanceof LocationWrapper )
	    	{
	    		Object oLocation = ((LocationWrapper)oHitObject).getObject();
	    		if( oLocation instanceof MobileObject2D )
	        	{
	    			MobileObject2D oMobile = (MobileObject2D)oLocation;
	    			oMobile.setPose(transformToMasonCoord(poPoint), oMobile.getOrientation());
	        	}
	    	}
	    }
	}
	
	/**
	 * converting mouse coordinates to world coordinates - considering the zoom scale 
	 * 
	 * @param poP
	 * @return
	 */
	public Double2D transformToMasonCoord(Point poP)
	{
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
