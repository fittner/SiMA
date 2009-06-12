package bw.LocalizationOrientation;

import sim.util.Bag;

/**
 * @author  monkfoodb
 */
public class clsPath {
	
	private Bag Steps;
	private int step;
	public int Pathsize;
	public int memposTemp;
	public boolean isComplete;
	public boolean success;
	boolean isFwPath;
	
	
	
	
	public clsPath(){
		Steps=new Bag();
		step=0;
		Pathsize=0;
		isFwPath=true;
		success=false;
	}
	
	public clsStep getFirstStep(){
		clsStep tempObj;
		if (isFwPath){
			tempObj = (clsStep)Steps.get(0);
		}else{
			tempObj = (clsStep)Steps.get(Pathsize-1);
		}
		step=1;
		return tempObj;	
	}
	
	public clsStep getNextStep(){
		step++;
		if (step<Pathsize){
			clsStep tempObj; 
			tempObj = (clsStep)Steps.get(step);
			return tempObj;	
		}else{
			return null;
		}
	}
	
	public void addStep(Object Step){
		Steps.add(Step);
		Pathsize++;
	}
	
	public void setAsBwPath(){
		isFwPath=false;
	}
	
	/**
	 * @param SemAreaMemory the semantic memory of the areas
	 * @return the accumulated uptodateness. To make a less value of quality better (less steps are better) the uptodateness is 
	 * turned into a area-Badness ba calculating 100-UpToDateness
	 */
	//TODO (borer) - Robert Borer hier wŠre nich was zu tun. im moment Ÿberwiegt die wichtigkeit der up to dateness der lŠnge extrem. 1x schlecht bewertete direktverbindung ist so gut wie ca 30 transition langer weg 
	public double getPathQuality(clsAreaSemanticMemory SemAreaMemory){
		if (this.Steps.numObjs==0)
			return 0;
		
		double quality=0;
		
		for (int i=0; i < this.Steps.numObjs;i++)
			quality += 100-SemAreaMemory.getAreaEntry(((clsStep)this.Steps.get(i)).getCurrentArea() -1 ).getUpToDateness() + 1; //the +1 i done so that even if all areas are up to date, the amout o fthe areas is counted and used for finding the shortest/best
			
			return quality;
	}
	
	public Bag getMovements(){
		Bag movements=new Bag();
		if (isFwPath){
			for (int i=0;i<Pathsize;i++)
				movements.add(((clsStep)Steps.get(i)).getPathToNext());
		}else{
			for (int i=0;i<Pathsize;i++)
				movements.add(((clsStep)Steps.get(i)).getPathToPrev());
		}
		return movements;
	}
	
	/**
	 * 
	 * @return the ID of the area the agent should enter next when following the path
	 */
	public int getNextArea(){
		if (isFwPath){
			return ((clsStep)Steps.get(step)).getNextarea();
		}else{
			return ((clsStep)Steps.get(step)).getPrevArea();
		} 
	}
	
	
	public int getPrevArea(){
		if (isFwPath){
			return ((clsStep)Steps.get(step)).getPrevArea();
		}else{
			return ((clsStep)Steps.get(step)).getNextarea();
		} 
	}
	/**
	 * Function to get the current area in the path
	 * @return returns the ID of the area the agent should currently reside in
	 */
	public int getCurrentArea(){
		return ((clsStep)Steps.get(step)).getCurrentArea();
	}
	
	/**
	 * Depending on wether the PAth is a Forward or a Backward path the next Area link information is returned
	 * @return A clsMove
	 */
	public clsMove getNextMove(){
		if (isFwPath){
			return ((clsStep)Steps.get(step)).getPathToNext();
		}else{
			return ((clsStep)Steps.get(step)).getPathToPrev();
		} 
	}
	
	public void resetReachedStatus(){
		int i;
		for (i=0;i<Steps.numObjs;i++){
			((clsStep)Steps.get(i)).resetReachedStatus();
		}
	}
	public void String(){
		int i;
		System.out.printf("Path: ");
		if (isFwPath){
			for (i=0;i<Steps.numObjs;i++)
				System.out.printf("%d - ", ((clsStep)Steps.get(i)).getNextarea());
		}else{
			for (i=0;i<Steps.numObjs;i++)
				System.out.printf("%d - ", ((clsStep)Steps.get(i)).getPrevArea());
		} 
		System.out.printf("\n");
	}
}
