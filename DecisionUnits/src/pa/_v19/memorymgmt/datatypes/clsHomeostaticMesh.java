/**
 * clsHomeostaticMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:26
 */
package pa._v19.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v19.memorymgmt.enums.eDataType;
import pa._v19.tools.clsTripple;

/**
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:26
 * 
 */
public class clsHomeostaticMesh extends clsHomeostaticRepresentation{
	private clsDriveDemand moDriveDemand = null;
	@SuppressWarnings("unused")
	private String moContent = "UNDEFINED";
	private ArrayList<clsAssociation> moAssociatedContent = null; 
	/**
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:21:21
	 *
	 * @param poHomeostaticSource
	 */
	public clsHomeostaticMesh(clsTripple<Integer, eDataType, String> poDataStructureIdentifier, 
			ArrayList<clsAssociation> poAssociatedDriveSource, double pnDriveDemandIntensity) {
		super(poDataStructureIdentifier);
		
		moDriveDemand = new clsDriveDemand(null,pnDriveDemandIntensity); 
		moAssociatedContent = poAssociatedDriveSource;
		// HZ Is clsDrieDemand required? 
		//moDriveDemand = new clsDriveDemand(pnDriveDemandIntensity, null, null); 
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 19.07.2010, 21:07:24
	 * 
	 * @see pa._v19.memorymgmt.datatypes.itfComparable#compareTo(pa._v19.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		
		return 0;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsHomeostaticMesh oClone = (clsHomeostaticMesh)super.clone();
        	
        	if (moAssociatedContent != null) {
        		oClone.moAssociatedContent = new ArrayList<clsAssociation>(); 
        		
        		for(clsAssociation oAssociation : moAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone); 
    					oClone.moAssociatedContent.add((clsAssociation)dupl); // unchecked warning
    				} catch (Exception e) {
    					return e;
    				}
        		}
        	}
        	
        	oClone.moDriveDemand = (clsDriveDemand)this.moDriveDemand.clone(); 
        	
           	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 19.07.2010, 21:07:24
	 * 
	 * @see pa._v19.memorymgmt.datatypes.clsHomeostaticRepresentation#assignDataStructure(pa._v19.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		
		
	}
}
