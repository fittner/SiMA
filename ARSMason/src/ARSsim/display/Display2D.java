package ARSsim.display;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Dimension2D;
import java.awt.geom.Rectangle2D;

import sim.display.GUIState;
import sim.util.Bag;
import sim.physics2D.physicalObject.MobileObject2D;
import sim.physics2D.util.Angle;
import sim.physics2D.util.Double2D;
import sim.portrayal.DrawInfo2D;
import sim.portrayal.LocationWrapper;
import sim.portrayal.continuous.ContinuousPortrayal2D;

public class Display2D extends sim.display.Display2D {

	//members necessary for drag and drop
	
	private Point moPoint;
	private Bag[] moHitObjects;
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	
	public Display2D(double width, double height, GUIState simulation,
			long interval) {
		super(width, height, simulation, interval);


		insideDisplay.addMouseMotionListener(new MouseMotionListener() 
		{

			@Override
			public void mouseDragged(MouseEvent e) {
				
//				System.out.println(arg0.getPoint().toString() + " --> " + transformed);
				
        		Point oPoint = e.getPoint();
        		
                for(int x=0;x<moHitObjects.length;x++)
                {
                	Bag obj = moHitObjects[x];
                	Object hitObject = obj.objs[x];
                	
                	if( hitObject instanceof LocationWrapper )
                	{
                		
                		LocationWrapper portrayal = ((LocationWrapper)hitObject);
                		//portrayal.move(obj, new Dimension(1,1));
                		
                		Object porty = portrayal.getObject();
                		if( porty instanceof MobileObject2D )
	                	{
                			MobileObject2D mobi = (MobileObject2D)porty;
                			mobi.setPose(transformToMasonCoord(oPoint), mobi.getOrientation());
	                	}
                		
                		//((MobileObject2D)hitObject).setPose(new Double2D(10, 10), new Angle(0));
                		oPoint = moPoint;
                	}
                }
                repaint();
				
				
			}

			@Override
			public void mouseMoved(MouseEvent arg0) {
			
			}
			
		});
		
	    //Add listener to components that can bring up popup menus.
		insideDisplay.addMouseListener(new MouseAdapter()
	        {
	        public void mousePressed(MouseEvent e)
	            {
	        		Point point = e.getPoint();
	        		
	        		Rectangle2D.Double rect =  new Rectangle2D.Double( point.x, point.y, 1, 1 );
	        		moHitObjects = objectsHitBy(rect);
	        		int ix = 0;
	        		ix++;
	        		
	            }
	        
	        public void mouseReleased(MouseEvent e)
	            {
	        		Point point = e.getPoint();
	        		
	                for(int x=0;x<moHitObjects.length;x++)
	                {
	                	Bag obj = moHitObjects[x];
	                	Object hitObject = obj.objs[x];
	                	
	                	if( hitObject instanceof LocationWrapper )
	                	{
	                		
	                		LocationWrapper portrayal = ((LocationWrapper)hitObject);
	                		//portrayal.move(obj, new Dimension(1,1));
	                		
	                		Object porty = portrayal.getObject();
	                		if( porty instanceof MobileObject2D )
		                	{
	                			MobileObject2D mobi = (MobileObject2D)porty;
	                			mobi.setPose(transformToMasonCoord(point), mobi.getOrientation());
		                	}
	                		
	                		//((MobileObject2D)hitObject).setPose(new Double2D(10, 10), new Angle(0));
	                		point = moPoint;
	                	}
	                }
	                repaint();
	            }
	        });
	}
	
	
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
