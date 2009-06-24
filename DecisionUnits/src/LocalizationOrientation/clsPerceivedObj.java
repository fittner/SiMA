package LocalizationOrientation;

import bw.body.io.sensors.external.visionAnalysis.clsObject;
import sim.util.Bag;

/**
 * @author  monkfoodb
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
	
	public void addObject(Object Object, double distance, double bearing){
		this.modistance[count]=distance;
		this.mobearing[count]=bearing;
		this.meObject.add(Object);
		count++;
	}
	
	public void removeObject(int i){
		this.meObject.remove(i);
		double[] tempmobearing = new double[this.count-1];
		double[] tempmodistance = new double[this.count-1];
		
		for (int k=0;k<i;k++){
			tempmodistance[k] = this.modistance[k];
			tempmobearing[k]  = this.mobearing[k];
		}
		for (int k=i;k<this.count-1;k++){
			tempmodistance[k] = this.modistance[k+1];
			tempmobearing[k]  = this.mobearing[k+1];
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
	
	public void String(){
		
		System.out.printf("Area: ");
		for (int i=0;i<count;i++){
			System.out.printf("ID:%2d ;",((clsObject)this.meObject.get(i)).ID);
			//System.out.printf("; %l m; %l ¡---", this.modistance[i],this.mobearing[i]);
			System.out.printf("%3.1f m; %3.1f ¡ ---",this.modistance[i], this.mobearing[i]);
			}
		System.out.printf("\n");
	}
	public clsPerceivedObj clone(){
		clsPerceivedObj newPerceivedObj=new clsPerceivedObj(count);
		for (int i=0;i<count;i++){
			newPerceivedObj.addObject(this.meObject.get(i), this.modistance[i], this.mobearing[i]);
		}
		
		return newPerceivedObj;
		
	}
}
