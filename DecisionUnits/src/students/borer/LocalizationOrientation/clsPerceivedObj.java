/**
 * @author borer
 */
 package students.borer.LocalizationOrientation;

import sim.util.Bag;
import students.borer.simpleObjectRecognition.clsObject;

/**
 * holds the environmental data. identified objects in sight, their distance and bearing realive to traveling direction
 */
 
public class clsPerceivedObj {
	private Bag meObject;
	private double[] mobearing;
	private double[] modistance;
	private int count=0;
	
	
	public clsPerceivedObj(){
		meObject = new Bag();
	}
	public clsPerceivedObj(int numObj){
		meObject = new Bag();
		mobearing= new double[numObj];
		modistance= new double[numObj];
	}
	
	/**
	 * adds an alement to the list.
	 * @param Obejct: the object to add 
	 * @param distance: the objects distance data
	 * @param bearing: the objects bearing relative to the agents traveling direction
	 */
	public void addObject(Object Object, double distance, double bearing){
		this.modistance[count]=distance;
		this.mobearing[count]=bearing;
		this.meObject.add(Object);
		count++;
	}
	
	/**
	 * removes an alement from the list.
	 * @param i: the element index that should be removed
	 */
	public void removeObject(int i){
		this.meObject.remove(i);
		
		double[] tempmobearing = new double[this.count-1];
		double[] tempmodistance = new double[this.count-1];
		
		for (int k=0;k<this.count-1;k++){
			tempmodistance[k] = this.modistance[k];
			tempmobearing[k]  = this.mobearing[k];
		}
		
		if (i!=this.count-1){
			tempmodistance[i] = this.modistance[this.count-1];
			tempmobearing[i]  = this.mobearing[this.count-1];
		}
		
		this.mobearing	= tempmobearing;
		this.modistance	= tempmodistance;
		this.count--;
		
	}
	
	public clsObject getObject(int i){
		return (clsObject)this.meObject.get(i);
	}
	
	public Bag getObjects(){
		return this.meObject;
	}
	
	public double getDistance(int i){
		return this.modistance[i];
	}
	
	public void setDistance(int i,double distance){
		this.modistance[i]=distance;
	}
	
	public double getBearing(int i){
		return this.mobearing[i];
	}
	
	public void setBearing(int i,double bearing){
		this.mobearing[i]=bearing;
	}
	
	public int getNum(){
		return count;
	}
	
	/**
	 * Prints a string with all the objects in sight and their position data
	 */
	public void String(){
		System.out.printf("Area: ");
		for (int i=0;i<count;i++){
			System.out.printf("ID:%2d ;",((clsObject)this.meObject.get(i)).ID);
			//System.out.printf("; %l m; %l ¡---", this.modistance[i],this.mobearing[i]);
			System.out.printf("%3.1f m; %3.1f ¡ ---",this.modistance[i], this.mobearing[i]);
			}
		System.out.printf("\n");
	}
	
	@Override
	public clsPerceivedObj clone(){
		clsPerceivedObj newPerceivedObj=new clsPerceivedObj(count);
		for (int i=0;i<count;i++){
			newPerceivedObj.addObject(this.meObject.get(i), this.modistance[i], this.mobearing[i]);
		}
		
		return newPerceivedObj;
		
	}
}
