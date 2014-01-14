/**
 * @author deutsch
 * Jul 24, 2009, 8:39:41 PM
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities.tools;

import java.awt.Color;

import java.util.ArrayList;


import config.clsProperties;

import sim.physics2D.shape.Shape;
import singeltons.clsSingletonProperties;
import statictools.clsGetARSPath;
import tools.eImagePositioning;
import bfg.tools.shapes.clsPoint;
import bw.utils.enums.eShapeType;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * Jul 24, 2009, 8:39:41 PM
 * 
 */
public class clsShape2DCreator {
	public static final String P_DEFAULT_SHAPE = "default_shape";
	
	public static final String P_IMAGE_PATH = "image_path";	
	public static final String P_IMAGE_POSITIONING = "image_positioning";
	public static final String P_TYPE   	= "type";
	public static final String P_RADIUS 	= "radius";
	public static final String P_WIDTH  	= "width";
	public static final String P_LENGTH 	= "length";
	public static final String P_COLOR 		= "color";
	public static final String P_NUMPOINTS  = "numpoints"; //points of a polygon
	public static final String P_X			= "x";
	public static final String P_Y			= "y";
	public static final String P_ANGLE      = "angle"; //rotate image and shape by this angle
	

	public static Shape createShape(String poPrefix, clsProperties poProp) {
		return createShape( poPrefix,  poProp, false,false);
	}
	public static Shape createShapeWithOverlays(String poPrefix, clsProperties poProp, boolean showOrientation){
		return createShape( poPrefix,  poProp, showOrientation,true);
	}
	private static Shape createShape(String poPrefix, clsProperties poProp, boolean showOrientation, boolean poWithOverlays) {
		String pre = clsProperties.addDot(poPrefix);
		
		Shape oShape = null; 

		try {
			if (poProp.getPropertyDouble(pre+P_ANGLE) != 0) {
				//TODO implement rotation of shapes and images
				throw new java.lang.IllegalArgumentException();
			}
		} catch (java.lang.NullPointerException e) {
			// can be ignored - null equals 0 in this case
		}
		
		eShapeType oShapeType = eShapeType.valueOf( poProp.getPropertyString(pre +P_TYPE) );
		
		switch( oShapeType ) {
			case CIRCLE:	
				if(poWithOverlays){
					oShape = createAnimatedCircle(pre, poProp, showOrientation);
				}
				else{
					oShape = createCircle(pre, poProp);
				}
				 break;
			
			case RECTANGLE: oShape = createRectangle(pre, poProp); break;
			case POLYGON: 	oShape = createPolygon(pre, poProp); break;
			default: 		throw new java.lang.IllegalArgumentException();
		}
	
		return oShape;
	}
	
	private static Shape createCircle(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		Shape oShape = null; 
		String oImagePath = "";
		
		try {
			oImagePath = poProp.getPropertyString(pre+P_IMAGE_PATH);
		} catch (java.lang.NullPointerException e) {
			// everything is fine ...
		}
		
		if (oImagePath.length() == 0 || !clsSingletonProperties.drawImages()) {
			oShape = new sim.physics2D.shape.Circle(poProp.getPropertyDouble(pre +P_RADIUS), 
				 poProp.getPropertyColor(pre +P_COLOR));
		} else {
			eImagePositioning nImagePositioning = eImagePositioning.valueOf(poProp.getPropertyString(pre+P_IMAGE_POSITIONING));
			if (nImagePositioning != eImagePositioning.DEFAULT) {
				throw new java.lang.NoSuchMethodError();
				// TODO (everyone) - image positioning not implemented yet
			}			
			oShape = new physics2D.shape.clsCircleImage(
					poProp.getPropertyDouble(pre+ P_RADIUS),							                     
					poProp.getPropertyColor(pre+P_COLOR),
					clsGetARSPath.getArsPath()+poProp.getPropertyString(pre +P_IMAGE_PATH)
					);
		}
		
		return oShape;
	}
	
	private static Shape createAnimatedCircle(String poPrefix, clsProperties poProp, boolean showOrientation) {
		String pre = clsProperties.addDot(poPrefix);
		Shape oShape = null; 
		String oImagePath = "";
		
		try {
			oImagePath = poProp.getPropertyString(pre+P_IMAGE_PATH);
		} catch (java.lang.NullPointerException e) {
			// everything is fine ...
		}
		
		if (oImagePath.length() == 0 || !clsSingletonProperties.drawImages()) {
			oShape = new sim.physics2D.shape.Circle(poProp.getPropertyDouble(pre +P_RADIUS), 
				 poProp.getPropertyColor(pre +P_COLOR));
		} else {
			eImagePositioning nImagePositioning = eImagePositioning.valueOf(poProp.getPropertyString(pre+P_IMAGE_POSITIONING));
			if (nImagePositioning != eImagePositioning.DEFAULT) {
				throw new java.lang.NoSuchMethodError();
				// TODO (everyone) - image positioning not implemented yet
			}			
			oShape = new physics2D.shape.clsAnimatedCircleImage(
					poProp.getPropertyDouble(pre+ P_RADIUS),							                     
					poProp.getPropertyColor(pre+P_COLOR),
					clsGetARSPath.getArsPath()+poProp.getPropertyString(pre +P_IMAGE_PATH),
					showOrientation
					);
		}
		
		return oShape;
	}
	
	private static Shape createRectangle(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		Shape oShape = null; 
		String oImagePath = "";
		
		try {
			oImagePath = poProp.getPropertyString(pre+P_IMAGE_PATH);
		} catch (java.lang.NullPointerException e) {
			// everything is fine ...
		}
		
		if (oImagePath.length() == 0 || !clsSingletonProperties.drawImages()) {			
			oShape = new sim.physics2D.shape.Rectangle(	poProp.getPropertyDouble(pre +P_WIDTH),
													poProp.getPropertyDouble(pre +P_LENGTH), 
													 poProp.getPropertyColor(pre +P_COLOR));
		} else {
			eImagePositioning nImagePositioning = eImagePositioning.valueOf(poProp.getPropertyString(pre+P_IMAGE_POSITIONING));
			if (nImagePositioning != eImagePositioning.DEFAULT) {
				throw new java.lang.NoSuchMethodError();
				// TODO (everyone) - image positioning not implemented yet
			}
			oShape = new physics2D.shape.clsRectangleImage(
					poProp.getPropertyDouble(pre +P_WIDTH),
					poProp.getPropertyDouble(pre +P_LENGTH),
					poProp.getPropertyColor(pre +P_COLOR),
					clsGetARSPath.getArsPath()+oImagePath
					);
		}			
		
		return oShape;
	}
	
	private static Shape createPolygon(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		ArrayList<clsPoint> oPoints = new ArrayList<clsPoint>();
		
		int num = poProp.getPropertyInt(pre+P_NUMPOINTS);
		for (int i=0; i<num; i++) {
			double x = poProp.getPropertyDouble(pre+i+"."+P_X);
			double y = poProp.getPropertyDouble(pre+i+"."+P_Y);
			oPoints.add(new clsPoint(x,y));
		}
		
		//TODO: (everyone) - create clsPolygon - Polygon is an abstract class!!!
		throw new java.lang.NoSuchMethodError();
//		return null;
	}
	
	public static clsProperties getDefaultProperties(String poPrefix, eShapeType pnShape) {
		String pre = clsProperties.addDot(poPrefix);
		clsProperties oProp = new clsProperties();

		switch(pnShape) {
			case CIRCLE: 
				oProp.setProperty(pre+P_TYPE, eShapeType.CIRCLE.toString());
				oProp.setProperty(pre+P_ANGLE, Math.PI/2);
				oProp.setProperty(pre+P_RADIUS, 5);
				oProp.setProperty(pre+P_COLOR, Color.DARK_GRAY);
				oProp.setProperty(pre+P_IMAGE_PATH, "/BW/src/resources/images/fungus.png");
				oProp.setProperty(pre+P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
				break;
			case RECTANGLE:
				oProp.setProperty(pre+P_TYPE, eShapeType.RECTANGLE.toString());
				oProp.setProperty(pre+P_WIDTH, 10);
				oProp.setProperty(pre+P_LENGTH, 1);
				oProp.setProperty(pre+P_COLOR, Color.GREEN);
				oProp.setProperty(pre+P_IMAGE_PATH, "/src/resources/images/wall1.png");
				oProp.setProperty(pre+P_IMAGE_POSITIONING, eImagePositioning.DEFAULT.name());
				break;
			case POLYGON:
				//note only convex polygons are allowed!
				oProp.setProperty(pre+P_TYPE, eShapeType.POLYGON.toString());
				oProp.setProperty(pre+P_ANGLE, Math.PI/2);
				oProp.setProperty(pre+P_NUMPOINTS, 5);
				oProp.setProperty(pre+"0."+P_X, 1);
				oProp.setProperty(pre+"0."+P_Y, 1);
				oProp.setProperty(pre+"1."+P_X, -1);
				oProp.setProperty(pre+"1."+P_Y, 1);
				oProp.setProperty(pre+"2."+P_X, -1);
				oProp.setProperty(pre+"2."+P_Y, -1);
				oProp.setProperty(pre+"3."+P_X, 1);
				oProp.setProperty(pre+"3."+P_Y, -1);
				oProp.setProperty(pre+"4."+P_X, 2);
				oProp.setProperty(pre+"4."+P_Y, 0);
		}
		
		
		return oProp;
	}
}
