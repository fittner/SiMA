<<<<<<< .mine
/**
 * @author borer
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.LocalizationOrientation;

import bw.body.io.sensors.external.visionAnalysis.clsObject;
import bw.entities.clsCake;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import sim.util.Bag;


/**
 * properties of one single area, obstacles in sight,
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
	
	
	
	public int getid(){
		return area_id;
	}
	
	public void setid(int id){
		area_id=id;
	}
	
	public clsPerceivedObj getObjects(){
		return meObjectsInSight;
	}
	
	public void setObjects(clsPerceivedObj mePerceived){
		this.meObjectsInSight=mePerceived;
	}
	
	public int getNumObj(){
		return this.meObjectsInSight.getNum();
	}

	public void updateUpToDateness(double factor){
		upToDateness*=factor;
		if (upToDateness>100){
			upToDateness=100;
		}else if (upToDateness<0){
			upToDateness=0;
		}
	}
	
	public double getUpToDateness(){
		return this.upToDateness;
	}
	
	public void addNonLandmarkObject(Object NonLandmarkObject){
		this.nonLandmarkObjects.add(NonLandmarkObject);
	}
	
	public boolean hasFood(){
		int i;
		for (i=0;i<nonLandmarkObjects.numObjs;i++){
			if ( ((clsMobileObject2D)((clsObject)nonLandmarkObjects.get(i)).moObject).getEntity() instanceof clsCake)
				return true;
		}
		return false;
	}
	
}
=======
/**
 * @author borer
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.LocalizationOrientation;


/**
 * properties of one single area, obstacles in sight,
 * @author  borer
 */


public class clsArea {

	public double ObjectsSimilarity;
	public int SimilarObjectCount;
	public double PositionSimilarity;
	private int area_id;
	private clsPerceivedObj  meObjectsInSight;
	private int upToDateness;
	
	public clsArea(int num){
		meObjectsInSight = new clsPerceivedObj(num);
	}
	
	
	public clsArea(int id, final clsPerceivedObj mePerceived){
		area_id = id;
		this.meObjectsInSight=mePerceived;
		upToDateness=100;
	}
	
	
	
	public int getid(){
		return area_id;
	}
	
	public void setid(int id){
		area_id=id;
	}
	
	public clsPerceivedObj getObjects(){
		return meObjectsInSight;
	}
	
	public void setObjects(clsPerceivedObj mePerceived){
		this.meObjectsInSight=mePerceived;
	}
	
	public int getNumObj(){
		return this.meObjectsInSight.getNum();
	}

	public void updateUpToDateness(double factor){
		upToDateness*=factor;
		if (upToDateness>100){
			upToDateness=100;
		}else if (upToDateness<0){
			upToDateness=0;
		}
	}
	
	public double getUpToDateness(){
		return this.upToDateness;
	}
	
}
>>>>>>> .r2653
