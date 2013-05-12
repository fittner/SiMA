/**
 * @author borer
 */
package students.borer.LocalizationOrientation;

import du.enums.eEntityType;
import students.borer.Mason.Bag;
import students.borer.simpleObjectRecognition.clsObject;


/**
 * properties of one single area, obstacles in sight, up-to-dateness, non landmark objects…..
 * @author  borer
 */


public class clsArea {

	public double ObjectsSimilarity;
	public int SimilarObjectCount;
	public double PositionSimilarity;
	private int area_id;
	private clsPerceivedObj  meObjectsInSight;
	private Bag nonLandmarkObjects;
	private int upToDateness;
	public double reserved;
	
	public clsArea(int num){
		meObjectsInSight = new clsPerceivedObj(num);
		nonLandmarkObjects = new Bag();
		upToDateness=100;
	}
	
	
	public clsArea(int id, final clsPerceivedObj mePerceived){
		area_id = id;
		this.meObjectsInSight=mePerceived;
		upToDateness=100;
	}
	
	
	/*
	*Returns the Area ID as interger
	*/
	public int getid(){
		return area_id;
	}
	
	/*
	*Sets the Area ID
	*/
	public void setid(int id){
		area_id=id;
	}
	
	/*
	*Returns the Objects in sight from within this area
	*/
	public clsPerceivedObj getObjects(){
		return meObjectsInSight;
	}
	
	/*
	*Sets the Objects in sight from within this area
	* @param mePerceived: container of environmental objects and their location data 
	*/
	public void setObjects(clsPerceivedObj mePerceived){
		this.meObjectsInSight=mePerceived;
	}
	
	/*
	*Returns the amount of Objects in sight from within this area
	*/
	public int getNumObj(){
		return this.meObjectsInSight.getNum();
	}


	/*
	*modifies the up-to-dateness. up-to-dateness x factor
	*maximum is set to 100, minimum to 0
	* @param factor: the factor that modifies the up-to-dateness
	*/
	public void updateUpToDateness(double factor){
		upToDateness*=factor;
		if (upToDateness>100){
			upToDateness=100;
		}else if (upToDateness<0){
			upToDateness=0;
		}
	}
	
	/*
	*returns the up-to-dateness
	*/
	public double getUpToDateness(){
		return this.upToDateness;
	}
	
	/*
	*Adds the object NonLandmarkObject to the list of non landmark objects
	* @param NonLandmarkObject: the object to add
	*/
	public void addNonLandmarkObject(Object NonLandmarkObject){
		this.nonLandmarkObjects.add(NonLandmarkObject);
	}
	
	/*
	*function searches the list of non landmark objects for an object eitn entityType CAKE.
	*Returns true if found, false if not
	*/
	public boolean hasFood(){
		int i;
		for (i=0;i<nonLandmarkObjects.numObjs;i++){
			if ( ((clsObject)nonLandmarkObjects.get(i)).getEntityType()==eEntityType.CAKE)
				return true;
		}
		return false;
	}
	
}
