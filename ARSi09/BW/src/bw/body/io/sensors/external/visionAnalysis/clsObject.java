package bw.body.io.sensors.external.visionAnalysis;

import java.awt.Color;

import sim.physics2D.physicalObject.PhysicalObject2D;

import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import ARSsim.physics2D.shape.clsCircleImage;

public class clsObject {
	public Object moObject;
	public int ID;
	public double ObjectSimilarity;
	public boolean used;
	
	public clsObject(Object newObject){
		this.moObject = newObject;
	}
	
	
	public void setid(int newID){
		this.ID=newID;
	}
	
	public int getid(){
		return ID;
	}
	
	public void calcEquality(clsObject newObject){
		this.ObjectSimilarity=100;
		
		//if the objects have different shapes, they can not be the same
		if( !((PhysicalObject2D)this.moObject).getShape().getClass().equals(((PhysicalObject2D)newObject.moObject).getShape().getClass()) ){
			this.ObjectSimilarity=0;
			return;
		}
		
		ObjectSimilarity -= (100 / Math.abs( ((Color)((PhysicalObject2D)this.moObject).getShape().getPaint()).getRGB()) ) * Math.abs(((Color)((PhysicalObject2D)this.moObject).getShape().getPaint()).getRGB()-((Color)((PhysicalObject2D)newObject.moObject).getShape().getPaint()).getRGB());
		
		//if shape is a circle, compare mass and radius
		if(((PhysicalObject2D)this.moObject).getShape() instanceof clsCircleImage){
			ObjectSimilarity -= (100 / ((clsMobileObject2D)this.moObject).getMass()) * Math.abs(((clsMobileObject2D)this.moObject).getMass()-((clsMobileObject2D)newObject.moObject).getMass());
			ObjectSimilarity -= (100 / ((clsCircleImage)((clsMobileObject2D)this.moObject).getShape()).getRadius()  )   * Math.abs(((clsCircleImage)((clsMobileObject2D)this.moObject).getShape()).getRadius() - ((clsCircleImage)((clsMobileObject2D)newObject.moObject).getShape()).getRadius() );
		}
		
		/* for testing
		if (this.moObject.equals(newObject.moObject))
			this.ObjectSimilarity=100;
			*/
		
	}
	
	@Override
	public boolean equals(Object tmpObject){
		if (this.ID==((clsObject)tmpObject).getid()){
			return true;
		}else{
			return false;
		}
	}

}
