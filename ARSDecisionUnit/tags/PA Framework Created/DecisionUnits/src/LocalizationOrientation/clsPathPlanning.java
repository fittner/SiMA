//package LocalizationOrientation;
//
//import sim.util.Bag;
//
///**
// * here the actual path planing is done based on the Event stream
// * @author  monkfoodb
// */
//
//
//public class clsPathPlanning {
//
//	clsPath Path = new clsPath();
//	
//	
//	/**
//	 * Simple possibility. Starting at every point within the StepMemory with currentAreaID==Step.CurrentArea
//	 * Add to fw and bw path. For each go step forward / backward. 
//	 * if the next step equals the current backward step of the next possible path, or there is no next step then stop 
//	 * @param CurrentAreaID: the Area the robot is in currently
//	 * @param GoalAreaID: the Area the Path should lead to
//	 * @param StepMemory: the memory of all Steps taken 
//	 * @return
//	 */
//	public clsPath calculate(int CurrentAreaID, int GoalAreaID, Bag StepMemory, clsAreaSemanticMemory AreaMemory){
//		
//		if (CurrentAreaID==GoalAreaID)
//			return null;
//		
//		Bag fwPaths = new Bag();
//		Bag bwPaths = new Bag();
//		clsPath tempFwPath;
//		clsPath tempBwPath;
//		int  i=0,pathCount=0;
//		
//		
////		zuerst werden alle Stepeintr�ge gesucht deren CurrentArea == der jetzigen CurrentAreaID
//		for (i=0;i<StepMemory.numObjs;i++){
//			if (((clsStep)StepMemory.get(i)).getCurrentArea()==CurrentAreaID){
//				
//				clsPath Path= new clsPath();
//				Path.memposTemp=i;
//				//Path.addStep(StepMemory.get(i));
//				fwPaths.add(Path);
//				
//				Path= new clsPath();
//				Path.memposTemp=i;
//				//Path.addStep(StepMemory.get(i));
//				Path.setAsBwPath();
//				bwPaths.add(Path);
//				
//				pathCount+=2;
//			}
//			
//		}
//		while (pathCount>0){
//			for (i=0;i<fwPaths.numObjs;i++){
//				tempFwPath=(clsPath)fwPaths.get(i);
//				tempBwPath=(clsPath)bwPaths.get(i);
//			//sollte der weg noch ncht beendet sein
//				if ( 	!tempFwPath.isComplete 	){
////  TODO (borer) -  hier eing�gen das weg beendet fals area mit zu gro�er furcht oder mit zu wenig vertrauen; 
////alternativ k�nnen auch beim transitions extrahieren diese transitions und areas weggelassen werden
////das geht aber nur wenn dann auch die Dijkstra implementation steht. so mu� es ein durchg�ngiger strom sein.  
//					if((tempFwPath.memposTemp==(StepMemory.numObjs-1))||(((clsStep)StepMemory.get(tempFwPath.memposTemp)).getPathToNext()==null)){
//						tempFwPath.isComplete=true;
//						pathCount--;
//						
//					}else{
//					//pr�fen ob der n�chste schritt im FW Path zum ziel f�hrt
//						if (((clsStep)StepMemory.get(tempFwPath.memposTemp)).getNextarea()==GoalAreaID){
//							tempFwPath.isComplete=true;
//							((clsPath)fwPaths.get(i)).success=true;
//							pathCount--;
//						}else if ((i<(fwPaths.numObjs-1)) && (StepMemory.get(tempFwPath.memposTemp+1).equals(StepMemory.get( ((clsPath)bwPaths.get(i+1)).memposTemp) ))){
//							fwPaths.remove(i);
//							bwPaths.remove(i+1);
//							i--;
//							pathCount-=2;
//							continue;
//						}
//						
//						tempFwPath.addStep(StepMemory.get(tempFwPath.memposTemp));
//						if (!tempFwPath.isComplete)	//counting has to stop at the goal position other wise the ext bwpath might cross it
//							tempFwPath.memposTemp++;
//						
//					}
//					
//				}
//			//selbes f�r r�ckwertsschritt.
//				if ( 	!tempBwPath.isComplete 	){
//					
//					if((tempBwPath.memposTemp==0)||(((clsStep)StepMemory.get(tempBwPath.memposTemp)).getPathToPrev()==null)){
//						tempBwPath.isComplete=true;
//						pathCount--;
//						
//					}else{
//						//pr�fen ob der n�chste schritt im FW Path zum ziel f�hrt
//							if (((clsStep)StepMemory.get(tempBwPath.memposTemp)).getPrevArea()==GoalAreaID){
//								tempBwPath.isComplete=true;
//								((clsPath)bwPaths.get(i)).success=true;
//								pathCount--;
//							}else if ((i>0) && (StepMemory.get(tempBwPath.memposTemp-1).equals(StepMemory.get( ((clsPath)fwPaths.get(i-1)).memposTemp) ))){
//								fwPaths.remove(i-1);
//								bwPaths.remove(i);
//								i--;
//								pathCount-=2;
//								continue;
//							}
//							
//							tempBwPath.addStep(StepMemory.get(tempBwPath.memposTemp));
//							if (!tempBwPath.isComplete)	//counting has to stop at the goal position other wise the ext Fwpath might cross it
//								tempBwPath.memposTemp--;
//							
//					}
//				}
//			}
//		}
//		fwPaths.addAll(bwPaths);
//		int bestpath=-1;
//		double bestpathquality=-1;
//		for (i=0;i<fwPaths.numObjs;i++){
//			if (((clsPath)fwPaths.get(i)).success){
//				if ( (bestpath==-1) || (((clsPath)fwPaths.get(i)).getPathQuality(AreaMemory)<bestpathquality) ){
//					bestpath=i;
//					bestpathquality=((clsPath)fwPaths.get(bestpath)).getPathQuality(AreaMemory);
//				}
//			}
//		}
//		
//		if (bestpath==-1){
//			return null;
//		}
//		
//		((clsPath)fwPaths.get(bestpath)).resetReachedStatus();
//		
//		return (clsPath)fwPaths.get(bestpath);
//	}
//	
//}
