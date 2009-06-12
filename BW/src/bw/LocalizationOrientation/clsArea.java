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
