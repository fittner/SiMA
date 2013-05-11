/**
 * CHANGELOG
 *
 * 24.07.2011 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 24.07.2011, 14:26:38
 * @deprecated
 */
public class clsPrediction implements Cloneable {
	private clsDataStructureContainerPair moIntention;
	private ArrayList<clsDataStructureContainerPair> moExpectations;
	private clsDataStructureContainerPair moMoment;
	
	public clsPrediction (clsDataStructureContainerPair poIntention, 
			ArrayList<clsDataStructureContainerPair> poExpectations, 
			clsDataStructureContainerPair poMoment) {
		
		//Create empty instances if the input is null
		if (poIntention==null) {
			moIntention = new clsDataStructureContainerPair(null, null);
		} else {
			moIntention = poIntention;
		}
		
		if (poExpectations==null) {
			moExpectations = new ArrayList<clsDataStructureContainerPair>();
		} else {
			moExpectations = poExpectations;
		}
		
		if (poMoment==null) {
			moMoment = new clsDataStructureContainerPair(null, null);
		} else {
			moMoment = poMoment;
		}
		
	}
	
	public clsDataStructureContainerPair getIntention() {
		return moIntention;
	}
	
	public void setIntention(clsDataStructureContainerPair poIntention) {
		moIntention = poIntention;
	}
	
	public ArrayList<clsDataStructureContainerPair> getExpectations() {
		return moExpectations;
	}
	
	public void setExpectations(ArrayList<clsDataStructureContainerPair> poExpectations) {
		moExpectations = poExpectations;
	}
	
	public clsDataStructureContainerPair getMoment() {
		return moMoment;
	}
	
	public void setMoment(clsDataStructureContainerPair poMoment) {
		moMoment = poMoment;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	//Clone intention
        	clsDataStructureContainerPair oCloneIntention = (clsDataStructureContainerPair) moIntention.clone();
        	//Clone moment
        	clsDataStructureContainerPair oCloneMoment = (clsDataStructureContainerPair) moMoment.clone();
        	
        	//Clone expectations
        	ArrayList<clsDataStructureContainerPair> oCloneExpectations = null;
        	if (moExpectations != null) {
        		oCloneExpectations = new ArrayList<clsDataStructureContainerPair>();
        		for(clsDataStructureContainerPair oP : moExpectations){
        			try { 
        				oCloneExpectations.add((clsDataStructureContainerPair) oP.clone());
        			} catch (Exception e) {
        				return e;
        			}
        		}
        	}
        	
        	clsPrediction oClone = new clsPrediction(oCloneIntention, oCloneExpectations, oCloneMoment);
   
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}
	
	@Override
	public String toString(){
		String oRetVal = ""; 
		
		oRetVal += "I:";
		if (moIntention.getSecondaryComponent()!=null) {
			oRetVal += ((clsSecondaryDataStructure)moIntention.getSecondaryComponent().getMoDataStructure()).moContent.toString();
		}
		oRetVal += "::M:";
		if (moMoment.getSecondaryComponent()!=null) {
			oRetVal += ((clsSecondaryDataStructure)moMoment.getSecondaryComponent().getMoDataStructure()).moContent.toString();
		}
		for (clsDataStructureContainerPair oP : moExpectations) {
			oRetVal += ":";
			oRetVal += "E:";
			oRetVal += ((clsSecondaryDataStructure)oP.getSecondaryComponent().getMoDataStructure()).moContent.toString();
		}
	
		return oRetVal;
	}
}
