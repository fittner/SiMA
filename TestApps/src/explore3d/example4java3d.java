package explore3d;

import javax.imageio.ImageIO;
import javax.media.j3d.AmbientLight;
import javax.media.j3d.Appearance;
import javax.media.j3d.BoundingSphere;
import javax.media.j3d.BranchGroup;
import javax.media.j3d.Material;
import javax.media.j3d.Texture;
import javax.media.j3d.TextureAttributes;
import javax.media.j3d.Transform3D;
import javax.media.j3d.TransformGroup;
import javax.vecmath.AxisAngle4f;
import javax.vecmath.Color3f;
import javax.vecmath.Color4f;
import javax.vecmath.Point3d;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import statictools.clsGetARSPath;

import com.sun.j3d.utils.geometry.Box;
import com.sun.j3d.utils.geometry.Primitive;
import com.sun.j3d.utils.geometry.Sphere;
import com.sun.j3d.utils.image.TextureLoader;
import com.sun.j3d.utils.universe.SimpleUniverse;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.awt.Color;

public class example4java3d {
	
	private BufferedImage readTiledImage(String url, int w, int h) throws IOException {
		BufferedImage res = null;
		
		BufferedImage img = null;
	    img = ImageIO.read(new File(clsGetARSPath.getArsPath()+"\\ARSIN\\src\\resources\\images\\arsin_red.png"));
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
	
	private Appearance getAppearance(String url, Color c) {
		   Color3f c3f = new Color3f(c);
		   Color4f c4f = new Color4f(c); c4f.w = 0.0f;
		   
		   Appearance ap = new Appearance();

		   // Set up the texture map
		   try {
			   TextureLoader loader = new TextureLoader( readTiledImage(url, 3, 3) );
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

	private Sphere createSphere(float size) {
	   // Create a ball to demonstrate textures
	   int primflags = Primitive.GENERATE_NORMALS + Primitive.GENERATE_TEXTURE_COORDS;
	
	   Sphere s = new Sphere(size, primflags, getAppearance("S:\\ARSIN_V01\\ARSIN\\src\\resources\\images\\arsin_red.png", Color.blue));
	   return s;
	}
	
	private TransformGroup getSphere(float size, float x, float y) {
	   TransformGroup tg = new TransformGroup();
	   tg.addChild(createSphere(size));
	   
	   
	   Vector3f v = new Vector3f(x, y, size);
	   
	   Transform3D tr = new Transform3D();
	   tr.setTranslation(v);
	   tr.setRotation(new AxisAngle4f(1,0,0, (float) (Math.PI/2.0)));
	   
	   tg.setTransform(tr);
	   
	   return tg;
	}
	
	private Box getFloorPane() {
		Color3f c3f = new Color3f(Color.white);
		Appearance ap = new Appearance();
        ap.setMaterial(new Material(c3f, c3f, c3f, c3f, 1.0f));
		
		Box b = new Box(1000, 1000, 0, ap);
		return b;
	}
	
	private AmbientLight getLighting() {
		   BoundingSphere bounds = new BoundingSphere(new Point3d(0.0,0.0,0.0), 10000.0);
		   AmbientLight ambientLight = new AmbientLight(new Color3f(1.0f,1.0f,1.0f));
		   ambientLight.setInfluencingBounds(bounds);
		   return ambientLight;
	}
	
	private void setViewPane(SimpleUniverse universe) {
		   float x=0;
		   float y=-5;
		   float z=0.5f;
		    Transform3D t3d = new Transform3D();
		    t3d.setTranslation(new Vector3d(x, y, z));
		    t3d.setRotation(new AxisAngle4f(1,0,0, (float) (Math.PI/2.0)));
		    universe.getViewingPlatform().getViewPlatformTransform().setTransform(t3d);
		  // universe.getViewingPlatform().setNominalViewingTransform();
	}
	
	public example4java3d() {
	   // Create the universe
	   SimpleUniverse universe = new SimpleUniverse();
	   // set looking direction
	   setViewPane(universe);

	   // Create a structure to contain objects
	   BranchGroup groupBasics = new BranchGroup();
	   // Create lights
	   groupBasics.addChild(getLighting());
	   // create the floor
	   groupBasics.addChild(getFloorPane());
	   // add the group of objects to the Universe
	   universe.addBranchGraph(groupBasics);
	   
	   // create the objects
	   BranchGroup groupEntities = new BranchGroup();
	   groupEntities.addChild(getSphere(0.5f, 0.2f, 0));
	   groupEntities.addChild(getSphere(0.4f, -0.7f, 0.3f));
	   groupEntities.addChild(getSphere(0.3f, 1.2f, -1));

	   universe.addBranchGraph(groupEntities);
	}

	public static void main(String[] args) {
	    new example4java3d();
	}
}