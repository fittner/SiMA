/**
 * CHANGELOG
 *
 * 06.05.2019 delacruz - File created
 *
 */
package inspector.interfaces;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

//import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import memorymgmt.enums.ePhiPosition;
import memorymgmt.enums.eRadius;

/**
 * DOCUMENT (delacruz) - insert description 
 * 
 * @author delacruz
 * 06.05.2019, 14:39:05
 * 
 */
public class Singleton {
		
	private static Map<Integer, Singleton> piMatch_instance = new HashMap<>();
	private static int currentAgent = Integer.MIN_VALUE;
	public boolean clearPIMatchList = false;
	;	
	public ArrayList<HashMap<String, HashMap<String, Double>>> PIMatchList = new ArrayList<HashMap<String, HashMap<String, Double>>>();
	public ArrayList<HashMap<String, ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>>> RIPIMatchList= new ArrayList<>();
	
	public ArrayList<clsThingPresentationMesh> RIList = new ArrayList<>();
	public ArrayList<clsThingPresentationMesh> PIList = new ArrayList<>();
	public ArrayList<clsDataStructurePA> PIEmotionList = new ArrayList<>();
	

    public int stepPIMatch = -1;
    public int stepGlobalPIMatch = 0;
    public int numberImagesPIMatch = 0;
    
	private Singleton()
	{
	    
	    HashMap<String, ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>> oRIPIMatch= new HashMap<>();	    
	    oRIPIMatch.put("RIPIMatch", new ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>());
	    RIPIMatchList.add(oRIPIMatch);
	  	 
	}
	

	public void addToRIPIMatchList() {
	    
	    
	    HashMap<String, ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>> oRIPIMatch = new HashMap <>();
	    oRIPIMatch.put("RIPIMatch", new ArrayList <clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>>());
	    RIPIMatchList.add(oRIPIMatch);
	   
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
        
       
        EmotionMatches.put("TotalEmotionMatch", 0.0);
        EmotionMatches.put("AggressionMatch", 0.0);
        EmotionMatches.put("LibidoMatch", 0.0);
        EmotionMatches.put("PleasureMatch", 0.0);
        EmotionMatches.put("UnpleasureMatch", 0.0);
        SpatialMatches.put("SpatialMatch", 1.0);
            
        
        tmpPIMatch.put("EmotionMatches", EmotionMatches);
        tmpPIMatch.put("SpatialMatches", SpatialMatches);
       
	    PIMatchList.add(tmpPIMatch);
	    //update step
	    stepPIMatch++;
	   

	}
	
	public HashMap<String, Double> getPISpatialMatch(int step) {
	    	    
	    return PIMatchList.get(step).get("SpatialMatches");
	}
	
	public HashMap<String, Double> getPIEmotionMatch(int step) {
          
	   return PIMatchList.get(step).get("EmotionMatches");
	}
	   
    public static synchronized Singleton getInstance() 
    { 
        if (!piMatch_instance.containsKey(currentAgent)) 
            piMatch_instance.put(currentAgent, new Singleton()); 
  
        return piMatch_instance.get(currentAgent);
         
    } 
    
    public static void setCurrentAgent(int index) {
        currentAgent = index;
    }
    

    public void setRIPIMatch(ArrayList<clsPair<clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius>, clsPair<clsThingPresentationMesh, Double>>> oRIPIMatch) {
              
        RIPIMatchList.get(stepGlobalPIMatch).put("RIPIMatch",oRIPIMatch);
    }
    
    public void setEmotionMatch(double rEmMatch, double Aggr, double Lib, double Pleas, double Unpleas)
    {    

        
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("TotalEmotionMatch", rEmMatch);
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("AggressionMatch", Aggr);
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("LibidoMatch", Lib);
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("PleasureMatch", Pleas);
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("TotalEmotionMatch", rEmMatch);
        PIMatchList.get(stepPIMatch).get("EmotionMatches").put("UnpleasureMatch", Unpleas);

        
       
    } 
    public void setSpatialMatch(double rSpMatch)
    {

        PIMatchList.get(stepPIMatch).get("SpatialMatches").put("SpatialMatch", rSpMatch);

    }
}   
  


