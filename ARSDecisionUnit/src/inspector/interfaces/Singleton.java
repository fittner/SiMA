/**
 * CHANGELOG
 *
 * 06.05.2019 delacruz - File created
 *
 */
package inspector.interfaces;
import java.util.HashMap;

import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import memorymgmt.enums.ePhiPosition;
import memorymgmt.enums.eRadius;

import java.util.ArrayList;

/**
 * DOCUMENT (delacruz) - insert description 
 * 
 * @author delacruz
 * 06.05.2019, 14:39:05
 * 
 */
public class Singleton {
		
	private static Singleton piMatch_instance = null;
		
	public HashMap<String, HashMap<String, Double>> PIMatch = new HashMap<String, HashMap<String, Double>>();	
	public static ArrayList<HashMap<String, HashMap<String, Double>>> PIMatchList = new ArrayList<HashMap<String, HashMap<String, Double>>>();
	public static ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>> oRIPIMatch= new ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>();
	
	public double rEmotionMatch;
    public double rSpatialMatch;
    public static int stepPIMatch = 1;
    
    //private constructor restricted to this class itself
	private Singleton()
	{
	    
	    PIMatch = new HashMap<>();
	    HashMap <String, Double> EmotionMatches = new HashMap<>();
	    HashMap <String, Double>  SpatialMatches = new HashMap<>();
	    
	    EmotionMatches.put("TotalEmotionMatch", 0.0);
	    EmotionMatches.put("AggressionMatch", 0.0);
	    EmotionMatches.put("LibidoMatch", 0.0);
	    EmotionMatches.put("PleasureMatch", 0.0);
	    EmotionMatches.put("UnpleasureMatch", 0.0);
	    SpatialMatches.put("SpatialMatch", 0.0);
	   	    
	    PIMatch.put("EmotionMatches", EmotionMatches);
	    PIMatch.put("SpatialMatches", SpatialMatches);
	   
	    PIMatchList.add(PIMatch);
	     
	 
	}
	
	//public HashMap<String, Double> getEmotionMatchMap()
	//{
	   // return PIMatchList.
	//}
	public static int getStepPIMatch()
	{
	    return stepPIMatch;
	    
	}
	public static ArrayList<HashMap<String, HashMap<String, Double>>> getPIMatchList() {
	    
	    return PIMatchList;
	}
    // static method to create instance of Singleton class 
    public static synchronized Singleton getInstance() 
    { 
    	
        if (piMatch_instance == null) 
        	piMatch_instance = new Singleton(); 
  
        return piMatch_instance; 
    } 
    
    //public String getEmotionMatch()
    //{
       // return PIMatch.get("EmotionMatch");
    //}
    public void setEmotionMatch(double rEmMatch, double Aggr, double Lib, double Pleas, double Unpleas)
    {    
        //if (this.PIMatch == null)
        //{
             //PIMatch = new HashMap<String, HashMap<String, Double>>(); 
             
        //}
        //if(this.EmotionMatches == null) {
           // EmotionMatches = new HashMap<String, Double>();
        //}
        this.PIMatch.get("EmotionMatches").put("TotalEmotionMatch", rEmMatch);
        this.PIMatch.get("EmotionMatches").put("AggressionMatch", Aggr);
        this.PIMatch.get("EmotionMatches").put("LibidoMatch", Lib);
        this.PIMatch.get("EmotionMatches").put("PleasureMatch", Pleas);
        this.PIMatch.get("EmotionMatches").put("UnpleasureMatch", Unpleas);
        
        
       
   
        
       
    } 
    public void setSpatialMatch(double rSpMatch)
    {
    	//this.rSpatialMatch = rSpMatch;
        //if (this.PIMatch == null)
        //{
             //PIMatch = new HashMap<String, HashMap<String, Double>>(); 
        //}
        //if(this.SpatialMatches == null) {
           // SpatialMatches = new HashMap<String, Double>();
        //}
        this.PIMatch.get("SpatialMatches").put("SpatialMatch", rSpMatch);
    }
}   
  


