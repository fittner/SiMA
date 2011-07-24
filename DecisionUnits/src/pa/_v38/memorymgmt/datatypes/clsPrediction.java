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
 * 
 */
public class clsPrediction {
	private clsDataStructureContainerPair moIntention;
	private ArrayList<clsDataStructureContainerPair> moExpectations;
	private clsDataStructureContainerPair moMoment;
	
	public clsPrediction (clsDataStructureContainerPair poIntention, 
			ArrayList<clsDataStructureContainerPair> poExpectations, 
			clsDataStructureContainerPair poMoment) {
		
		moIntention = poIntention;
		moExpectations = poExpectations;
		moMoment = poMoment;
		
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
}
