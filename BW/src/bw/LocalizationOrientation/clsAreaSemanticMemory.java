<<<<<<< .mine
/**
 * @author borer
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.LocalizationOrientation;

import sim.util.Bag;

/**
 * Saves a list of Areas and provides search function for best matching  area for the previously observed situation.
 * @author  borer
 */
public class clsAreaSemanticMemory {

	private Bag AreaEntries = new Bag();
	private double similarObjectsTolerance;
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
	
	
	public int rememberArea(clsArea tempArea,double tmpsimilarObjectsTolerance, double similarObjcetbeeringTolerance){
		Bag PotentialAreas;
		
		this.similarObjectsTolerance=tmpsimilarObjectsTolerance;
		
		//werden Potentielle similaritäten im gedächtnis gesucht
		PotentialAreas = checkForSimilarObjects(tempArea,tmpsimilarObjectsTolerance);
		
		//wenn keine gefunden wurden -> neue area
		if (PotentialAreas==null){
			newAreaFound(tempArea);
			return tempArea.getid();
		}else{	//wenn  potentielle Areas
			checkForSimilarPosition(PotentialAreas, tempArea,similarObjcetbeeringTolerance);
			if (PotentialAreas.numObjs>0){
	//				Area gefunden
					return ((clsArea)PotentialAreas.get(0)).getid();
				
			}else{
				newAreaFound(tempArea);
				return tempArea.getid();
			}
		}
	}
	
	
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
	}
	
	
	
	/* Hier werden alle elemente in der Semantic Area Memory auf übereinstimmende 
	 * objekte geprüft und die % der übereinstimmung gespeichert 
	 * 
	 * Übergabewert is die Area in der sich der Bot gerade befindet
	 * 
	 * Rückgabewert ist die liste von areas aus der Memory die eine 
	 * anzahl von gleichen objekten haben die innerhalb der tolleranz liegt
	 **/
		public Bag checkForSimilarObjects (clsArea newArea, double similarObjectsTolerance){
			int num=getSize();
			Bag similarEntries = new Bag();
			
		//prüfen auf gleiche objekte in sicht
			//jeden memory eintrag prüfen 
			
			for(int i=0;i<num;i++){
				clsArea currentArea = getAreaEntry(i);
				currentArea.SimilarObjectCount=0;
				currentArea.ObjectsSimilarity=0;
				currentArea.PositionSimilarity=0;
				//reset the used variable for each object in area. (used to make sure that an object is only once used for comparisson)
				for (int k=0 ; k<currentArea.getNumObj() ; k++)
					currentArea.getObjects().getObject(k).used=false;
				
				//in dem memory eintrag für jedes element in der derzeitigen Area
				for (int j=0;j<newArea.getObjects().getNum();j++){
					//vergleichen mit jedem element aus den memoryeintrag auf übereinstimmung
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
					//errechnen der Prozentmäßigen übereinstimmung der objekte in sicht
					currentArea.ObjectsSimilarity= (double)(100/ (double)Math.max(newArea.getNumObj(), currentArea.getNumObj()) ) * (double)currentArea.SimilarObjectCount;
					
					//jene objekte deren übereinstimmung innerhald der toleranz sind werden zur liste potenzieleler Areas hinzugefügt
					if (currentArea.ObjectsSimilarity >= (100*(1-similarObjectsTolerance)))
						similarEntries.add(currentArea);
				}
			}
			
			//wenn keine gleichen Areas gefunden werden
			if(similarEntries.numObjs==0)
				return null;
			
			return similarEntries;
		}
		
		
	/*funktion um die gleichheit der der position der einzelnen objekte in den potentiellen areas zu checken
	 * dafür werden erst die winkel vereinheitlicht auf das referenz objekt und dann alle verglichen
	 * 
	 * Übergabewert: die Potentiellen Areas
	 * 
	 * Rückgabewert: die Potentiellen Areas bei denen auch die Objekt position stimmt. 
	 * 				 Liste ist in geordneter reihenfolge. die best passensten zuerst*/
		public void checkForSimilarPosition(Bag potentialAreas, clsArea newArea,double similarObjcetbeeringTolerance){
			int h,i,j=0, k,temp=0,bestSingleBearingDifference=0, AreaBearingDifference=0;
			double diffBearing=0;
			
		//	 alle potentiellen memoryAreas berechnen
			do{
				clsArea currentArea = (clsArea)potentialAreas.get(j);
					currentArea.PositionSimilarity=-1;
			//		search for main object. similarity is calculated for every found possibility (this has to be done in case the main object has a twin in sight range)
					for (k=0;k<newArea.getNumObj();k++){
						if (newArea.getObjects().getObject(k).equals( currentArea.getObjects().getObject(0))){
							diffBearing= newArea.getObjects().getBearing(k);// das gegenstück in der vorhandenen Memory sollte bearing0 haben darum keine subtraktion "- ((clsArea)potentialAreas.get(k)).getObjects().getBearing(0);"
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
					if ((currentArea.PositionSimilarity)< ((1-this.similarObjectsTolerance))){
						potentialAreas.remove(j);
					}else{
						j++;
					}
			}while(j<potentialAreas.numObjs);
			
		}

		/*		//sortieren
		Object temp;
		for (int i=0;i<num;i++)
			for (int j=0;j<i;j++)
				if (Memory.getAreaEntry(j).ObjectsSimilarity < Memory.getAreaEntry(i).ObjectsSimilarity){
					temp=Memory.getAreaEntry(i);
					for(int k=j;k<i;k++)
						Memory.getAreaEntry(k+1)=similarEntries[k];
					similarEntries[j]=temp;
					break;
				}
		*/

	
	public boolean updateUpToDateness(double factor, int entry){
		//check if entry is within range
		if (!((entry<=this.AreaEntries.numObjs)&&(entry>0)))
			return false;
		
//debug
		if (entry==-1)
			System.out.println("FUCK");
		
		this.getAreaEntry(entry-1).updateUpToDateness(factor);
		return true;
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

import sim.util.Bag;

/**
 * Saves a list of Areas and provides search function for best matching  area for the previously observed situation.
 * @author  borer
 */
public class clsAreaSemanticMemory {

	private Bag AreaEntries = new Bag();
	private double similarObjectsTolerance;
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
	
	
	public int rememberArea(clsArea tempArea,double tmpsimilarObjectsTolerance, double similarObjcetbeeringTolerance){
		Bag PotentialAreas;
		
		this.similarObjectsTolerance=tmpsimilarObjectsTolerance;
		
		//werden Potentielle similaritäten im gedächtnis gesucht
		PotentialAreas = checkForSimilarObjects(tempArea,tmpsimilarObjectsTolerance);
		
		//wenn keine gefunden wurden -> neue area
		if (PotentialAreas==null){
			newAreaFound(tempArea);
			return tempArea.getid();
		}else{	//wenn  potentielle Areas
			checkForSimilarPosition(PotentialAreas, tempArea,similarObjcetbeeringTolerance);
			if (PotentialAreas.numObjs>0){
	//				Area gefunden
					return ((clsArea)PotentialAreas.get(0)).getid();
				
			}else{
				newAreaFound(tempArea);
				return tempArea.getid();
			}
		}
	}
	
	
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
		System.out.printf("neue Area definiert");
		newArea.getObjects().String();
		System.out.printf("\n");
	}
	
	
	
	/* Hier werden alle elemente in der Semantic Area Memory auf übereinstimmende 
	 * objekte geprüft und die % der übereinstimmung gespeichert 
	 * 
	 * Übergabewert is die Area in der sich der Bot gerade befindet
	 * 
	 * Rückgabewert ist die liste von areas aus der Memory die eine 
	 * anzahl von gleichen objekten haben die innerhalb der tolleranz liegt
	 **/
		public Bag checkForSimilarObjects (clsArea newArea, double similarObjectsTolerance){
			int num=getSize();
			Bag similarEntries = new Bag();
			
		//prüfen auf gleiche objekte in sicht
			//jeden memory eintrag prüfen 
			for(int i=0;i<num;i++){
				clsArea currentArea = getAreaEntry(i);
				currentArea.SimilarObjectCount=0;
				currentArea.ObjectsSimilarity=0;
				currentArea.PositionSimilarity=0;
				//in dem memory eintrag für jedes element in der derzeitigen Area
				for (int j=0;j<newArea.getObjects().getNum();j++){
					
					//vergleichen mit jedem element aus den memoryeintrag auf übereinstimmung
					for (int k=0 ; k<currentArea.getNumObj() ; k++){
						if (newArea.getObjects().getObject(j).equals(currentArea.getObjects().getObject(k))){
							currentArea.SimilarObjectCount++;
							break;
						}
					}
				}
				
				if(currentArea.SimilarObjectCount>0){
					//errechnen der Prozentmäßigen übereinstimmung der objekte in sicht
					currentArea.ObjectsSimilarity= (double)(100/ (double)Math.max(newArea.getNumObj(), currentArea.getNumObj()) ) * (double)currentArea.SimilarObjectCount;
					
					//jene objekte deren übereinstimmung innerhald der toleranz sind werden zur liste potenzieleler Areas hinzugefügt
					if (currentArea.ObjectsSimilarity >= (100*(1-similarObjectsTolerance)))
						similarEntries.add(currentArea);
				}
			}
			
			//wenn keine gleichen Areas gefunden werden
			if(similarEntries.numObjs==0)
				return null;
			
			return similarEntries;
		}
		
		
	/*funktion um die gleichheit der der position der einzelnen objekte in den potentiellen areas zu checken
	 * dafür werden erst die winkel vereinheitlicht auf das referenz objekt und dann alle verglichen
	 * 
	 * Übergabewert: die Potentiellen Areas
	 * 
	 * Rückgabewert: die Potentiellen Areas bei denen auch die Objekt position stimmt. 
	 * 				 Liste ist in geordneter reihenfolge. die best passensten zuerst*/
		public void checkForSimilarPosition(Bag potentialAreas, clsArea newArea,double similarObjcetbeeringTolerance){
			int h,i,j=0, k,temp=0,bestSingleBearingDifference=0, AreaBearingDifference=0;
			double diffBearing=0;
			
		//	 alle potentiellen memoryAreas berechnen
			do{
				clsArea currentArea = (clsArea)potentialAreas.get(j);
					currentArea.PositionSimilarity=-1;
			//		search for main object. similarity is calculated for every found possibility (this has to be done in case the main object has a twin in sight range)
					for (k=0;k<newArea.getNumObj();k++){
						if (newArea.getObjects().getObject(k).equals( currentArea.getObjects().getObject(0))){
							diffBearing= newArea.getObjects().getBearing(k);// das gegenstück in der vorhandenen Memory sollte bearing0 haben darum keine subtraktion "- ((clsArea)potentialAreas.get(k)).getObjects().getBearing(0);"
							AreaBearingDifference=0;
							
				//			the angle is adjusted for every object within the new area. preparation for angular comparisson
							for (i=0;i<newArea.getNumObj();i++){
								newArea.getObjects().setBearing(i,
										(newArea.getObjects().getBearing(i)-diffBearing)<0 ? 
												(newArea.getObjects().getBearing(i)-diffBearing+360) : 
												(newArea.getObjects().getBearing(i)-diffBearing) );
								
				//			Search for same objects and calc difference
								for (h=0;h<currentArea.getNumObj();h++){
				//					if object found, check if bearing is within tolerance, if there are several same objects, use the one with the best fit
									if (newArea.getObjects().getObject(i).equals(currentArea.getObjects().getObject(h))){
										temp=(((int)(newArea.getObjects().getBearing(i)/similarObjcetbeeringTolerance)==(int)(currentArea.getObjects().getBearing(h)/similarObjcetbeeringTolerance)) ? 1 : 0);
										if (temp>bestSingleBearingDifference)
											bestSingleBearingDifference=temp;
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
					if ((currentArea.PositionSimilarity)< ((1-this.similarObjectsTolerance))){
						potentialAreas.remove(j);
					}else{
						j++;
					}
			}while(j<potentialAreas.numObjs);
			
		}

		/*		//sortieren
		Object temp;
		for (int i=0;i<num;i++)
			for (int j=0;j<i;j++)
				if (Memory.getAreaEntry(j).ObjectsSimilarity < Memory.getAreaEntry(i).ObjectsSimilarity){
					temp=Memory.getAreaEntry(i);
					for(int k=j;k<i;k++)
						Memory.getAreaEntry(k+1)=similarEntries[k];
					similarEntries[j]=temp;
					break;
				}
		*/

	
	public boolean updateUpToDateness(double factor, int entry){
		//check if entry is within range
		if ((this.AreaEntries.numObjs>=entry)&&(entry>0))
			return false;
		
//debug
		if (entry==-1)
			System.out.println("FUCK");
		
		this.getAreaEntry(entry).updateUpToDateness(factor);
		return true;
	}
	
}
>>>>>>> .r2653
