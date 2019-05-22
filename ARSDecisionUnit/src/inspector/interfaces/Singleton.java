/**
 * CHANGELOG
 *
 * 06.05.2019 delacruz - File created
 *
 */
package inspector.interfaces;
import java.util.HashMap;

//import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructurePA;
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
	public static boolean clearPIMatchList = false;
	//public HashMap<String, HashMap<String, Double>> PIMatch = new HashMap<String, HashMap<String, Double>>();	
	public static ArrayList<HashMap<String, HashMap<String, Double>>> PIMatchList = new ArrayList<HashMap<String, HashMap<String, Double>>>();
	public static ArrayList<HashMap<String, ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>>> RIPIMatchList= new ArrayList<>();
	
	public static ArrayList<clsThingPresentationMesh> RIList = new ArrayList<>();
	public static ArrayList<clsThingPresentationMesh> PIList = new ArrayList<>();
	public static ArrayList<clsDataStructurePA> PIEmotionList = new ArrayList<>();
	

    public static int stepPIMatch = 0;
    public static int stepGlobalPIMatch = 0;
    public static int numberImagesPIMatch = 0;
    
    //private constructor restricted to this class itself
	private Singleton()
	{
	    
	    HashMap<String, ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>> oRIPIMatch= new HashMap<>();
	    HashMap<String, HashMap<String, Double>> PIMatch = new HashMap<String, HashMap<String, Double>>();   
	    HashMap <String, Double> EmotionMatches = new HashMap<>();
	    HashMap <String, Double>  SpatialMatches = new HashMap<>();
	    
	    oRIPIMatch.put("RIPIMatch", new ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>());
	    EmotionMatches.put("TotalEmotionMatch", 0.0);
	    EmotionMatches.put("AggressionMatch", 0.0);
	    EmotionMatches.put("LibidoMatch", 0.0);
	    EmotionMatches.put("PleasureMatch", 0.0);
	    EmotionMatches.put("UnpleasureMatch", 0.0);
	    SpatialMatches.put("SpatialMatch", 0.0);
	   	    
	    PIMatch.put("EmotionMatches", EmotionMatches);
	    PIMatch.put("SpatialMatches", SpatialMatches);
	   
	    RIPIMatchList.add(oRIPIMatch);
	    PIMatchList.add(PIMatch);
	     
	 
	}
	
	//public HashMap<String, Double> getEmotionMatchMap()
	//{
	   // return PIMatchList.
	//}
	
    
	public void addToRIPIMatchList() {
	    
	    
	    HashMap<String, ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>> oRIPIMatch = new HashMap <>();
	    oRIPIMatch.put("RIPIMatch", new ArrayList <clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>());
	    RIPIMatchList.add(oRIPIMatch);
	    
	            
	  //"RIPIMatch", new clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>(null, null));
	}
	public void addToPIMatchList() {
	        
	    //clear list if new getImage()
        if(clearPIMatchList==true) {
            PIMatchList.clear();
        }
	    //create temporary node
	    HashMap<String, HashMap<String, Double>> tmpPIMatch = new HashMap<>();
        HashMap <String, Double> EmotionMatches = new HashMap<>();
        HashMap <String, Double>  SpatialMatches = new HashMap<>();
        
        
        
        //RIPIMatchList.put("RIPIList", new ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>());
        
        //EmotionMatches.put("TotalEmotionMatch", PIMatchList.get(stepPIMatch).get("EmotionMatches").get("TotalEmotionMatch"));
        EmotionMatches.put("TotalEmotionMatch", 0.0);
        EmotionMatches.put("AggressionMatch", 0.0);
        EmotionMatches.put("LibidoMatch", 0.0);
        EmotionMatches.put("PleasureMatch", 0.0);
        EmotionMatches.put("UnpleasureMatch", 0.0);
        SpatialMatches.put("SpatialMatch", 0.0);
            
        
        tmpPIMatch.put("EmotionMatches", EmotionMatches);
        tmpPIMatch.put("SpatialMatches", SpatialMatches);
       
	    //tmpPIMatch.put("PIMatch", new HashMap<>());
	    PIMatchList.add(tmpPIMatch);
	    //update step
	    Singleton.stepPIMatch++;
	   
	    //clear RIPIArray for update
	    //oRIPIMatchList.clear();
	}
	
	public static ArrayList<HashMap<String, HashMap<String, Double>>> getPIMatchList() {
	    
	    return PIMatchList;
	}
	
	public static HashMap<String, Double> getPISpatialMatch(int step) {
	    	    
	    return PIMatchList.get(step).get("SpatialMatches");
	}
	
	public static HashMap<String, Double> getPIEmotionMatch(int step) {
          
	   return PIMatchList.get(step).get("EmotionMatches");
	}
	   
    // static method to create instance of Singleton class 
    public static synchronized Singleton getInstance() 
    { 
    	
        if (piMatch_instance == null) 
        	piMatch_instance = new Singleton(); 
  
        return piMatch_instance; 
    } 
    
    //public HashMap<String, HashMap<String, Double>> getPIMatchNode(){
        
      //  return PIMatch;
    //}
    
    
    //public String getEmotionMatch()
    //{
       // return PIMatch.get("EmotionMatch");
    //}
    
    public void setRIPIMatch(ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>> oRIPIMatch) {
              
        RIPIMatchList.get(stepGlobalPIMatch).put("RIPIMatch",oRIPIMatch);
    }
    
    public void setEmotionMatch(double rEmMatch, double Aggr, double Lib, double Pleas, double Unpleas)
    {    
        //if (this.PIMatch == null)
        //{
             //PIMatch = new HashMap<String, HashMap<String, Double>>(); 
             
        //}
        //if(this.EmotionMatches == null) {
           // EmotionMatches = new HashMap<String, Double>();
        //}
        
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("TotalEmotionMatch", rEmMatch);
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("AggressionMatch", Aggr);
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("LibidoMatch", Lib);
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("PleasureMatch", Pleas);
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("TotalEmotionMatch", rEmMatch);
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("UnpleasureMatch", Unpleas);
        
        //this.PIMatch.get("EmotionMatches").put("AggressionMatch", Aggr);
        //this.PIMatch.get("EmotionMatches").put("LibidoMatch", Lib);
        //this.PIMatch.get("EmotionMatches").put("PleasureMatch", Pleas);
        //this.PIMatch.get("EmotionMatches").put("UnpleasureMatch", Unpleas);
        
       
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
        PIMatchList.get(stepPIMatch).get("SpatialMatches").put("SpatialMatch", rSpMatch);
        //this.PIMatch.get("SpatialMatches").put("SpatialMatch", rSpMatch);
    }
}   
  


