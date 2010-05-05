/**
 * @author borer
 */
package students.borer.simpleObjectRecognition;

import sim.util.Bag;

/**
 * A semantic memory for objects. includes a remember function for retrieval.
 * @author  borer
 */
public class clsObjectsSemanticMemory {

	private Bag ObjectEntries = new Bag();
	public clsObjectsSemanticMemory(){
		
	}
	
	public void addObjectEntry(clsObject moObject){
		this.ObjectEntries.add(moObject);
	}
	
	public clsObject getObjectEntry(int i){
		return (clsObject)this.ObjectEntries.get(i);
	}
	
	public int getSize(){
		return this.ObjectEntries.numObjs;
	}
	
	
	/**
	 *scans the memorized objects for a similar one
	 *
	 * @param temoObject: the new object that is to be memorized
	 * @param similarObjectsTolerance: the tolerance value for the comparisson
	 *
	 * @return the id of the remembered object.
	 */
	public int rememberObject(clsObject tempObject,double similarObjectsTolerance){
		Bag PotentialObjects;
		
		
		//Potential similar objects are beeing searched
		PotentialObjects = checkForSimilarObject(tempObject,similarObjectsTolerance);
		
		//if there are no similar objects
		if (PotentialObjects==null){
			newObjectFound(tempObject);
			return tempObject.getid();
		}else{		
				/*	If there are several potential objects, perform more intense check using the episodic memory data, not yet implemented
				so the first one is returned.*/
					return ((clsObject)PotentialObjects.get(0)).getid();
		}
	}
	
	
	/**
	 *Adds a new object to the memory
	 *
	 * @param newObject: the new Object that is to be added
	 */
	private void newObjectFound(clsObject newObject){
		//we can use the size of the bag as new id, this way the id is the same as the position within the semantic memory
		newObject.setid(getSize());
		addObjectEntry(newObject);
		System.out.printf("neue Area definiert");
		newObject.toString();
		System.out.printf("\n");
	}
	
	
	
	/* Here all the objects are compared for their similarity with the new object
	 * 
	 * @param newArea: the new object that is to be memorized
	 * @param similarObjectsTolerance: the tolerance value for the comparisson
	 * 
	 * @return a list of objects that fit the tolerance value
	 **/
		public Bag checkForSimilarObject (clsObject newObject, double similarObjectsTolerance){
			int num=getSize(), i, bestfit=-1;
			double bestequality=0;
			Bag similarEntries = new Bag();
			clsObject currentObject=null;
		//prüfen auf gleiche objekte in sicht
			//jeden memory eintrag prüfen, best passensten speichern
			for(i=0;i<num;i++){
				currentObject = getObjectEntry(i);
				currentObject.calcEquality(newObject);
				if (currentObject.ObjectSimilarity >bestequality){
					bestequality = currentObject.ObjectSimilarity;
					bestfit=i;
				}
	
			}
			
//			jene objekte deren übereinstimmung innerhald der toleranz sind werden zur liste potenzieleler objects hinzugefügt
			if ((bestfit!=-1) && (getObjectEntry(bestfit).ObjectSimilarity >= (100*(1-similarObjectsTolerance))))
				similarEntries.add(getObjectEntry(bestfit));
			
			//wenn keine gleichen objects gefunden werden
			if(similarEntries.numObjs==0)
				return null;
			
			return similarEntries;
		}	
}
