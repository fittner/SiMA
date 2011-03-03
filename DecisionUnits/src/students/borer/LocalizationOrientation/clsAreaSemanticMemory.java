/**
 * @author borer
 */
package students.borer.LocalizationOrientation;

import java.util.Comparator;

import students.borer.Mason.Bag;

/**
 * Semantic memory for the Area knowledge
 * Saves a list of Areas and provides search function for best matching area refered to the previously observed situation.
 * @author  borer
 */
public class clsAreaSemanticMemory {

	private Bag AreaEntries = new Bag();
	@SuppressWarnings("unused")
	private double similarObjectsTolerance;
	private double similarObjectBearingTolerance;
	public boolean imperfectMatch;
	public clsAreaSemanticMemory(){
		
	}
	
	public void addAreaEntry(Object moObject){
		this.AreaEntries.add(moObject);
	}
	
	public clsArea getAreaEntry(int i){
		return (clsArea)this.AreaEntries.get(i);
	}
	
	public int getSize(){
		return this.AreaEntries.numObjs;
	}
	
	
	/*
	*Compares the current objects in sight with the ones within the saved areas, depending on the used tolerances. The found maches are returned.
	*new areas are added to the list.
	*2 steps, similar objects check, and similar position check
	* @param tempArea: the current area that is to be compared with the memory
	* @param tmpsimilarObjectsTolerance: the tolerance for similar objects
	* @param tmpsimilarObjectBearingTolerance: the tolerance for similar object position
	* @param ObjcetbeeringTolerance: the angular discretization (see Master thesis section area encoding)
	* @return a bag of potential areas that fit with the similarity tolrance data
	*/
	public Bag rememberArea(clsArea tempArea,double tmpsimilarObjectsTolerance, double tmpsimilarObjectBearingTolerance,double ObjcetbeeringTolerance){
		Bag PotentialAreas;
		imperfectMatch=false;
		this.similarObjectsTolerance=tmpsimilarObjectsTolerance;
		this.similarObjectBearingTolerance=tmpsimilarObjectBearingTolerance;
		//areas are searched that have anough similar objects in sight as the current environment
		PotentialAreas = checkForSimilarObjects(tempArea,tmpsimilarObjectsTolerance);
		
		//if no results -> this is a new area
		if (PotentialAreas==null){
			PotentialAreas=new Bag();
			newAreaFound(tempArea);
			PotentialAreas.add(tempArea);
			return PotentialAreas;
		}else{	//if there are potential areas, sort them and compare the similar objects locations.
		
			PotentialAreas.sort(new Comparator<clsArea>() { @Override
			public int compare(clsArea A1, clsArea A2) {return A1.ObjectsSimilarity<=A2.ObjectsSimilarity?1:0;}});
			
			checkForSimilarPosition(PotentialAreas, tempArea,ObjcetbeeringTolerance);
			
			PotentialAreas.sort(new Comparator<clsArea>() { @Override
			public int compare(clsArea A1, clsArea A2) {return (A1.ObjectsSimilarity*A1.PositionSimilarity)<=(A2.ObjectsSimilarity*A2.PositionSimilarity)?1:0;}});
			
			if (PotentialAreas.numObjs>0){
				//If the best result from both comparison steps does not fit perfectly, the area is also saved as a new one because the result still has some tolerance and might not be the correct one.
				if (((clsArea)PotentialAreas.get(0)).ObjectsSimilarity*((clsArea)PotentialAreas.get(0)).PositionSimilarity !=100){
					imperfectMatch=true;
					newAreaFound(tempArea);
				}
				
				return PotentialAreas;
				
			}else{
				newAreaFound(tempArea);
				PotentialAreas.add(tempArea);
				return PotentialAreas;
			}
		}
	}
	
	
	/*
	*Saves a new area within the list.
	* @param newArea: the new area that is to be saved
	*/
	private void newAreaFound(clsArea newArea){
		newArea.setid(getSize()+1);
		
		//normalize again
		for ( int j=1;j<newArea.getNumObj();j++)
			newArea.getObjects().setBearing(j, (newArea.getObjects().getBearing(0)>newArea.getObjects().getBearing(j)) ? 
											(newArea.getObjects().getBearing(j)-newArea.getObjects().getBearing(0)+360) : 
											(newArea.getObjects().getBearing(j)-newArea.getObjects().getBearing(0))
											);
		newArea.getObjects().setBearing(0, 0);
		
		//add to list
		addAreaEntry(newArea);
		//For Debug
		System.out.printf("neue Area definiert: Nr %d ",newArea.getid());
		newArea.getObjects().String();
		if (newArea.hasFood())
			System.out.println("Has Food!!");
		
	}
	
	
	
	/* the memorized areas are compared with the current situation regarding the similarity of the objects in sight.
	 * the similarity is the saved. The ones with similarity higher that the silerance are saved for the next step
	 * 
	 * @param newArea: current environmental data within an clsArea container
	 * @param similarObjectsTolerance: tolerance for similar objects within the environment and the memorized area
	 * 
	 * Returns list of areas that have a high enough similarity
	 **/
		public Bag checkForSimilarObjects (clsArea newArea, double similarObjectsTolerance){
			int num=getSize();
			Bag similarEntries = new Bag();
			
			//every memory entry is checked
			for(int i=0;i<num;i++){
				clsArea currentArea = getAreaEntry(i);
				currentArea.SimilarObjectCount=0;
				currentArea.ObjectsSimilarity=0;
				currentArea.PositionSimilarity=0;
				//reset the used variable for each object in area. (used to make sure that an object is only once used for comparisson)
				for (int k=0 ; k<currentArea.getNumObj() ; k++)
					currentArea.getObjects().getObject(k).used=false;
				
				//compare every object within the current environment...
				for (int j=0;j<newArea.getObjects().getNum();j++){
					//...with every emelent within the memorized area
					for (int k=0 ; k<currentArea.getNumObj() ; k++){
						if ((newArea.getObjects().getObject(j).equals(currentArea.getObjects().getObject(k))) && !(currentArea.getObjects().getObject(k).used)){
							//set object as allready used for comparrison
							currentArea.getObjects().getObject(k).used=true;
							currentArea.SimilarObjectCount++;
							break;
						}
					}
				}
				
				if(currentArea.SimilarObjectCount>0){
					//calculate the similarity value
					currentArea.ObjectsSimilarity= (double)(100/ (double)Math.max(newArea.getNumObj(), currentArea.getNumObj()) ) * (double)currentArea.SimilarObjectCount;
					
					//is similarity is high enough, save the area in the potential list
					if (currentArea.ObjectsSimilarity >= (100*(1-similarObjectsTolerance)))
						similarEntries.add(currentArea);
				}
			}
			
			//if there where no areas found
			if(similarEntries.numObjs==0)
				return null;
			
			return similarEntries;
		}
		
	/* second comparison step. the locations of the similar objects (similar within current environment and memorized area) are compared
	 * the ones that have enough similar positioned objects are being kept.
	 *
	 * @param potentialAreas: the list of potential areas from the first comparison step
	 * @param newArea: current environmental data within an clsArea container
	 * @param similarObjcetbeeringTolerance: tolerance for similar objects position within the environment and the memorized area
	 * 
	 * Returns list of areas that have a high enough overall similarity
	 **/
		public void checkForSimilarPosition(Bag potentialAreas, clsArea newArea,double similarObjcetbeeringTolerance){
			int h,i,j=0, k,temp=0,bestSingleBearingDifference=0, AreaBearingDifference=0;
			double diffBearing=0;
			
		//	 check all potential areas
			do{
				clsArea currentArea = (clsArea)potentialAreas.get(j);
					currentArea.PositionSimilarity=-1;
			//		search for main object. similarity is calculated for every found possibility (this has to be done in case the main object has a twin in sight range)
					for (k=0;k<newArea.getNumObj();k++){
						if (newArea.getObjects().getObject(k).equals( currentArea.getObjects().getObject(0))){
							diffBearing= newArea.getObjects().getBearing(k);// das gegenstŸck in der vorhandenen Memory sollte bearing0 haben darum keine subtraktion "- ((clsArea)potentialAreas.get(k)).getObjects().getBearing(0);"
							AreaBearingDifference=0;
							
				//			reset the "used" variable for each object; it is used to ensure every object is only compared once
							for ( h=0 ; h<currentArea.getNumObj() ; h++)
								currentArea.getObjects().getObject(h).used=false;
							
				//			the angle is adjusted for every object within the new area. preparation for angular comparisson
							for (i=0;i<newArea.getNumObj();i++){
								if (diffBearing!=0)
									newArea.getObjects().setBearing(i,
											(newArea.getObjects().getBearing(i)-diffBearing)<0 ? 
													(newArea.getObjects().getBearing(i)-diffBearing+360) : 
													(newArea.getObjects().getBearing(i)-diffBearing) );
								
				//			Search for same objects and calc difference
								for (h=0;h<currentArea.getNumObj();h++){
				//					if object found, check if bearing is within tolerance, if there are several same objects, use the one with the best fit
									if ((newArea.getObjects().getObject(i).equals(currentArea.getObjects().getObject(h))) && !(currentArea.getObjects().getObject(h).used)){
										temp=(((int)(newArea.getObjects().getBearing(i)/similarObjcetbeeringTolerance)==(int)(currentArea.getObjects().getBearing(h)/similarObjcetbeeringTolerance)) ? 1 : 0);
										if (temp>bestSingleBearingDifference){
											bestSingleBearingDifference=temp;
											//make sure this object os not used for comparisson again
											currentArea.getObjects().getObject(h).used=true;
											break;
										}
									}
								}
				//				Add up the bearing differences
								AreaBearingDifference += bestSingleBearingDifference;
								bestSingleBearingDifference=0;
							}
							if ((currentArea.PositionSimilarity==-1) || (currentArea.PositionSimilarity<AreaBearingDifference))
								currentArea.PositionSimilarity=AreaBearingDifference;
							
						}
					}
					
			//		Calculate the average angular difference
					currentArea.PositionSimilarity=(currentArea.PositionSimilarity/  currentArea.SimilarObjectCount );
					if ((currentArea.PositionSimilarity)< ((1-this.similarObjectBearingTolerance))){
						potentialAreas.remove(j);
					}else{
						j++;
					}
			}while(j<potentialAreas.numObjs);
			
		}
	
	
	/* modify the up-to-dateness
	 *
	 * @param factor: factor that modifies the up-to-dateness
	 * @param entry: id of the entry that is to be modified
	 * 
	 * Returns true if mofied succesfully. false if not
	 **/
	public boolean updateUpToDateness(double factor, int entry){
		//check if entry is within range
		if (!((entry<=this.AreaEntries.numObjs)&&(entry>0)))
			return false;
		
//debug
		if (entry==-1)
			System.out.println("ERROR:Trying to modify up-to-dateness of entry Nr. -1");
		
		this.getAreaEntry(entry-1).updateUpToDateness(factor);
		return true;
	}
	
}
