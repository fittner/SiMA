/**
 * CHANGELOG
 *
 * 29.08.2011 deutsch - File created
 *
 */
package bw.entities.tools;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.media.j3d.Appearance;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Vector3f;


import bw.utils.enums.eShapeType;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import config.clsProperties;


/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 29.08.2011, 17:40:26
 * 
 */
public class clsShape3DCreator {
	private static BufferedImage readTiledImage(String url, int w, int h) throws IOException {
		BufferedImage res = null;
		
		BufferedImage img = null;
	    img = ImageIO.read(new File("S:\\ARSIN_V01\\BaseEntity-Body-ARSIN\\src\\resources\\images\\arsin_red.png"));
	    int width = img.getWidth();
	    int height = img.getHeight();
	    int[] RGB = img.getRGB(0, 0, width, height, null, 0, width);
	    
	    res = new BufferedImage(width*w, height*h, BufferedImage.TYPE_INT_RGB);
	    		    
	    for (int i=0; i<w; i++) {
	    	for (int j=0; j<h; j++) {
	    		res.setRGB(width*i, height*j, width, height, RGB, 0, width);
	    	}
	    }
		
		return res;
	}
	
	private static Appearance getAppearance(Color c) {
		   Color3f c3f = new Color3f(c);
	   
		   Appearance ap = new Appearance();

		   //set up the material
		   ap.setMaterial(new Material(c3f, c3f, c3f, c3f, 1.0f));
	
		   return ap;
	}
	
	private static Appearance getAppearance(String url, Color c) {
		   Color3f c3f = new Color3f(c);
		   Color4f c4f = new Color4f(c); c4f.w = 0.0f;
		   
		   Appearance ap = new Appearance();

		   // Set up the texture map
		   try {
			   TextureLoader loader = new TextureLoader( readTiledImage(url , 3, 3) );
			   Texture texture = loader.getTexture();
			   texture.setBoundaryModeS(Texture.WRAP);
			   texture.setBoundaryModeT(Texture.WRAP);
			   texture.setBoundaryColor(c4f);
			   ap.setTexture(texture);
			   
			   TextureAttributes texAttr = new TextureAttributes();
			   texAttr.setTextureMode(TextureAttributes.REPLACE);
			   ap.setTextureAttributes(texAttr);
		   } catch (IOException e) {
			   System.err.println("Error reading texture '"+url+"'.");
		   }
		   
		   //set up the material
		   ap.setMaterial(new Material(c3f, c3f, c3f, c3f, 1.0f));
	
		   return ap;
	}
	
	public static TransformGroup createShape(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		TransformGroup oShape = null; 

		try {
			if (poProp.getPropertyDouble(pre+clsShape2DCreator.P_ANGLE) != 0) {
				//TODO implement rotation of shapes and images
				throw new java.lang.IllegalArgumentException();
			}
		} catch (java.lang.NullPointerException e) {
			// can be ignored - null equals 0 in this case
		}
		
		eShapeType oShapeType = eShapeType.valueOf( poProp.getPropertyString(pre +clsShape2DCreator.P_TYPE) );
		
		switch( oShapeType ) {
			case CIRCLE:	oShape = createSphere(pre, poProp); break;
			case RECTANGLE: oShape = createBox(pre, poProp); break;
//			case POLYGON: 	oShape = createPolygon(pre, poProp); break;
			default: 		throw new java.lang.IllegalArgumentException();
		}
	
		return oShape;
	}

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 29.08.2011 17:48:20
	 *
	 * @param pre
	 * @param poProp
	 * @return
	 */
	private static TransformGroup createBox(String pre, clsProperties poProp) {
		int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
		float height = 1.0f;
		
		Appearance ap = null;
		try {
			ap = getAppearance(poProp.getPropertyString(pre +clsShape2DCreator.P_IMAGE_PATH), poProp.getPropertyColor(pre+clsShape2DCreator.P_COLOR));
		} catch (Exception e) {
			ap = getAppearance(poProp.getPropertyColor(pre+clsShape2DCreator.P_COLOR));
		}
		
		Box b = new Box( (float) poProp.getPropertyDouble(pre +clsShape2DCreator.P_WIDTH), 
							(float) poProp.getPropertyDouble(pre +clsShape2DCreator.P_LENGTH),
							height,
							primflags, 
							ap);
		
		TransformGroup tg = new TransformGroup();
		tg.addChild(b);
		   
		Transform3D tr = new Transform3D();
		Vector3f v = new Vector3f(0, 0, height/2.0f);
		tr.setTranslation(v);
		tr.setRotation(new AxisAngle4f(1,0,0, (float) (Math.PI/2.0)));
		tg.setTransform(tr);
		   
		return tg;	
	}

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @since 29.08.2011 17:48:18
	 *
	 * @param pre
	 * @param poProp
	 * @return
	 */
	private static TransformGroup createSphere(String pre, clsProperties poProp) {
		int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
		float r = (float) poProp.getPropertyDouble(pre +clsShape2DCreator.P_RADIUS);
		
		Appearance ap = null;
		try {
			ap = getAppearance(poProp.getPropertyString(pre +clsShape2DCreator.P_IMAGE_PATH), poProp.getPropertyColor(pre+clsShape2DCreator.P_COLOR));
		} catch (Exception ex) {
			ap = getAppearance(poProp.getPropertyColor(pre+clsShape2DCreator.P_COLOR));
		}
		Sphere s = new Sphere(r, primflags, ap);
		
		TransformGroup tg = new TransformGroup();
		tg.addChild(s);
		   
		Transform3D tr = new Transform3D();
		Vector3f v = new Vector3f(0, 0, r);
		tr.setTranslation(v);
		tr.setRotation(new AxisAngle4f(1,0,0, (float) (Math.PI/2.0)));
		tg.setTransform(tr);
		   
		return tg;		
	}	
}
