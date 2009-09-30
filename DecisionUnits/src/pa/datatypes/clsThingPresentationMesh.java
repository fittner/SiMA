/**
 * clsThingPresentationMesh.java: DecisionUnits - pa.datatypes
 * 
 * @author zeilinger
 * 15.09.2009, 09:10:22
 */
package pa.datatypes;

import java.util.ArrayList;

import pa.memory.clsAssociationContext;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 15.09.2009, 09:10:22
 * 
 */
public class clsThingPresentationMesh extends clsThingPresentation {
	
	public clsThingPresentationMesh() {
		moAssociations = new ArrayList<clsAssociationContext>();
	}
	
	public String meContentName;
	public String meContentType = "";
	public Object moContent = null;
	
	public ArrayList<clsAssociationContext> moAssociations;

}
