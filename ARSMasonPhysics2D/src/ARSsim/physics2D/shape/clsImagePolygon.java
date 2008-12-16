package ARSsim.physics2D.shape;

import sim.physics2D.shape.Circle;
import sim.physics2D.shape.Polygon;
import sim.physics2D.util.Double2D;
import sim.portrayal.*;
import sim.util.matrix.DenseMatrix;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class clsImagePolygon extends Polygon{

	 public void draw(Object object, Graphics2D graphics, DrawInfo2D info)
     {
     graphics.setPaint(paint);

     // set the scaling DenseMatrix
     scale.vals[0][0] = info.draw.width;
     scale.vals[1][1] = info.draw.height;
             
     DenseMatrix scaledMat = scale.times(this.vertices);
     DenseMatrix rottedMat = Polygon.rotationTranslationMatrix2D(getOrientation().radians, new Double2D(info.draw.x, info.draw.y)).times(scaledMat);
     //graphics.fillPolygon(Polygon.getRow(0, rottedMat),Polygon.getRow(1, rottedMat), this.vertices.n);
     
     
     String nImagePath = "S:/ARS/PA/BWv1/BW/src/resources/images/rock2.jpg";
 	 double nScale = 20;
 	File oFile = new File( nImagePath ); 
	
	BufferedImage oImage = null;
	try
	{
		oImage = ImageIO.read( oFile );
	} catch (IOException e)
	{
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	BufferedImageOp op = null;
     graphics.drawImage(oImage, op, 55, 55);
     //graphics.fillPolygon(Polygon.getRow(0, rottedMat),Polygon.getRow(1, rottedMat), this.vertices.n);
     }

	

	
}
