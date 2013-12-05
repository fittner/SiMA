/**
 * @author borer
 */
package students.borer.simpleObjectRecognition;

import java.awt.Color;

import du.enums.eEntityType;
import du.enums.eShapeType;
import du.itf.sensors.clsVisionEntry;



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
	
	
	/**
	 * calculates the equality the 
	 *
	 * @param: the new object that is to be compared with this one
	 */
	public void calcEquality(clsObject newObject){
		this.ObjectSimilarity=100;
		//if the objects have different shapes, they can not be the same
		if(newObject.getShape()!=this.getShape()){
			this.ObjectSimilarity=0;
			return;
		}
	
		ObjectSimilarity -= (100 / Math.abs((this.getPaint()).getRGB()) * Math.abs((this.getPaint()).getRGB()-(newObject.getPaint()).getRGB()));
//		ObjectSimilarity -= (100 / this.getMass()) * Math.abs(this.getMass()-newObject.getMass());	
	}
	
	@Override
	public boolean equals(Object tmpObject){
		if (this.ID==((clsObject)tmpObject).getid()){
			return true;
		}else{
			return false;
		}
	}

	public eShapeType getShape(){
		
		return ((clsVisionEntry)moObject).getShapeType();
	}
	
	public Color getPaint(){
		return ((clsVisionEntry)moObject).getColor();
	}
	
	public eEntityType getEntityType(){
		return ((clsVisionEntry)moObject).getEntityType();
	}
//	public double getMass(){
//		return ((clsVisionEntry)moObject).moMass;
//	}
}
