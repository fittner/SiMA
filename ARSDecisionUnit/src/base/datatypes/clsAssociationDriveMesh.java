/**
 * clsAssociationDriveMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:54:11
 */
package base.datatypes;


import java.util.ArrayList;
import java.util.List;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.helpstructures.clsTriple;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:54:11
 * 
 */
public class clsAssociationDriveMesh extends clsAssociation{
	//never usedprivate Object moContent = null; 
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 13:36:37
	 *
	 * @param clsDataStructurePA
	 * @param poDriveMesh
	 */
	public clsAssociationDriveMesh(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, 
			clsDriveMesh poAssociationElementA, 
			clsThingPresentationMesh poAssociationElementB){
		
		super(poDataStructureIdentifier, poAssociationElementA, poAssociationElementB);		
		//HZ moContent defines the association weight => also defines the affect!
		//It has to be defined if an affect is required and how the affect's minus values
		//should be represented in case the affect is the same as the clsAssociation's weight 
		//moContent = 1.0; 
	}
	
	protected static List<clsAssociationDriveMesh> getAllAssociationDriveMesh(List<clsAssociation> poAssociations) {
	    List<clsAssociationDriveMesh> oDriveMeshes = new ArrayList<>();
	    
	    for(clsAssociation oAssociation : poAssociations) {
            if(oAssociation instanceof clsAssociationDriveMesh) {
                oDriveMeshes.add((clsAssociationDriveMesh)oAssociation);
            }
        }
	    
	    return oDriveMeshes;
	}
	
	/**
	 * DOCUMENT - Return list of all external associations of type clsAssociationDriveMesh
	 *
	 * @author Kollmann
	 * @since 15.07.2014 17:12:00
	 *
	 * @param poContainingDataStructure
	 * @return List of all clsAssociationDriveMesh instances found in poContainingDataStructures extern associations
	 */
	public static List<clsAssociationDriveMesh> getAllExternAssociationDriveMesh(itfExternalAssociatedDataStructure poContainingDataStructure) {
	    return getAllAssociationDriveMesh(poContainingDataStructure.getExternalAssociatedContent());
	}
	
	/**
     * DOCUMENT - Return list of all internal associations of type clsAssociationDriveMesh
     *
     * @author Kollmann
     * @since 15.07.2014 17:12:00
     *
     * @param poContainingDataStructure
     * @return List of all clsAssociationDriveMesh instances found in poContainingDataStructures internal associations
     */
    public static List<clsAssociationDriveMesh> getAllInternalAssociationDriveMesh(itfInternalAssociatedDataStructure poContainingDataStructure) {
        return getAllAssociationDriveMesh(poContainingDataStructure.getInternalAssociatedContent());
    }
    
    /**
     * DOCUMENT - Remove all external associations of type clsAssociationDriveMesh from data structure
     *
     * @author Kollmann
     * @since 15.07.2014 17:12:00
     *
     * @param poContainingDataStructure
     * @return true if items where removed, false otherwise
     */
    public static boolean removeAllExternAssociationDriveMesh(itfExternalAssociatedDataStructure poContainingDataStructure) {
        boolean bRemoved = false;
        
        List<clsAssociationDriveMesh> oDriveMeshAssociations = getAllExternAssociationDriveMesh(poContainingDataStructure);
        for(clsAssociationDriveMesh oAssociationDriveMesh : oDriveMeshAssociations) {
            bRemoved |= clsAssociation.removeAssociationCompletely(oAssociationDriveMesh);
        }
        
        return bRemoved;
    }
    
    /**
     * DOCUMENT - Remove all internal associations of type clsAssociationDriveMesh from data structure
     *
     * @author Kollmann
     * @since 15.07.2014 17:12:00
     *
     * @param poContainingDataStructure
     * @return true if items where removed, false otherwise
     */
    public static boolean removeAllInternalAssociationDriveMesh(itfInternalAssociatedDataStructure poContainingDataStructure) {
        boolean bRemoved = false;
        
        List<clsAssociationDriveMesh> oDriveMeshAssociations = getAllInternalAssociationDriveMesh(poContainingDataStructure);
        for(clsAssociationDriveMesh oAssociationDriveMesh : oDriveMeshAssociations) {
            bRemoved |= clsAssociation.removeAssociationCompletely(oAssociationDriveMesh);
        }
        
        return bRemoved;
    }
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 18.07.2012, 20:58:09
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		
		/* compare drive source, -object and -aim
		double oRetVal = 0.0; 
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}
		
		clsAssociationDriveMesh oDataStructure = (clsAssociationDriveMesh)poDataStructure;
		
		clsDataStructurePA oAssociationElementIntern = this.moAssociationElementB;
		clsDataStructurePA oAssociationElementExtern = oDataStructure.moAssociationElementB;
		
				
		if(this.moDS_ID == oDataStructure.moDS_ID){
			
			oRetVal = 1.0;
		}
		
		if(oAssociationElementIntern.moContentType == oAssociationElementExtern.moContentType){
			
		}
			
		return oRetVal; 
		*/
		return 0;
	}
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 17.08.2010, 21:11:25
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#getLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement() {
		//FIXME HZ: The DM has to be marked as leaf element! Bad term 
		return moAssociationElementA; 
	}
	
	@Override
	public clsDataStructurePA getRootElement() {
		return moAssociationElementB; 
	}
	
	/* (non-Javadoc)
	 *
	 * @since 03.01.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setLeafElement(clsDataStructurePA poDS) {
		moAssociationElementA = poDS;
		// TODO (wendt) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since 03.01.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setRootElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setRootElement(clsDataStructurePA poDS) {
		moAssociationElementB = poDS;
		// TODO (wendt) - Auto-generated method stub
		
	}
	
	public clsDriveMesh getDM(){
		//Element A is always the Drive Mesh 
		return (clsDriveMesh)moAssociationElementA; 
	}
	
	@Override
	public String toString() {
		String oResult = "::"+this.moDataStructureType+"|";  
		//oResult +=  this.moContentType + "|";
		
		if (this.getRootElement() instanceof clsThingPresentationMesh) {
			oResult += ":" + ((clsThingPresentationMesh)this.getRootElement()).getContent();
		} else {
			oResult += ":" + moAssociationElementA;
		}
		
		//oResult += ":"; 
		if(getDM() != null) {
		    oResult += ":" + this.getDM().getDriveIdentifier() + ", " + this.getDM().getQuotaOfAffect();
		}
		
		return oResult;
	}
}
