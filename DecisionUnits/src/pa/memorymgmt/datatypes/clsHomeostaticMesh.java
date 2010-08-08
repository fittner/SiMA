/**
 * clsHomeostaticMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:26
 */
package pa.memorymgmt.datatypes;

import java.util.ArrayList;

import pa.memorymgmt.enums.eDataType;
import pa.tools.clsTripple;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:50:26
 * 
 */
public class clsHomeostaticMesh extends clsHomeostaticRepresentation{
	clsDriveDemand moDriveDemand = null;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:21:21
	 *
	 * @param poHomeostaticSource
	 */
	public clsHomeostaticMesh(clsTripple<String, eDataType, String> poDataStructureIdentifier, 
			ArrayList<clsAssociation> poAssociatedDriveSource, double pnDriveDemandIntensity) {
		super(poDataStructureIdentifier);
		
		moDriveDemand = new clsDriveDemand(null,pnDriveDemandIntensity); 
		moContent = poAssociatedDriveSource;
		//FIXME HZ Is clsDrieDemand required? 
		//moDriveDemand = new clsDriveDemand(pnDriveDemandIntensity, null, null); 
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 19.07.2010, 21:07:24
	 * 
	 * @see pa.memorymgmt.datatypes.itfComparable#compareTo(pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		// TODO (zeilinger) - Auto-generated method stub
		return 0;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsHomeostaticMesh oClone = (clsHomeostaticMesh)super.clone();
        	
        	if (moContent != null) {
        		oClone.moContent = new ArrayList<clsAssociation>(); 
        		
        		for(clsAssociation oAssociation : moContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone); 
    					oClone.moContent.add((clsAssociation)dupl); // unchecked warning
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
	 * @see pa.memorymgmt.datatypes.clsHomeostaticRepresentation#assignDataStructure(pa.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
}
