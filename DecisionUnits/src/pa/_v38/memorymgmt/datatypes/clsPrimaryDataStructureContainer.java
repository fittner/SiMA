/**
 * clsPrimaryInformation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:21
 */
package pa._v38.memorymgmt.datatypes;

import java.lang.reflect.Method;
import java.util.ArrayList;

import pa._v38.tools.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:21
 * 
 */
public class clsPrimaryDataStructureContainer extends clsDataStructureContainer {
	
	public clsPrimaryDataStructureContainer(clsDataStructurePA poDataStructure, ArrayList<clsAssociation>poAssociationList){
		super(poDataStructure, poAssociationList); 
	}
	
	@Override
	public Object clone() {
		return clone(new ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>>());
	}
	
	public Object clone(ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>> poClonedNodeList) {
        try {
        	clsPrimaryDataStructureContainer oClone = (clsPrimaryDataStructureContainer)super.clone();
        	
        	if (this.moDataStructure != null) {
				try { 
					Class<?> clzz = this.moDataStructure.getClass();
	    			//Class[] argTypes = {Class.forName("java.util.ArrayList")};
	    			//Method cloneGraphExtended = clzz.getDeclaredMethod("clone", argTypes);
	    			//Object dupl = cloneGraphExtended.invoke(this.moDataStructure, poClonedNodeList);
					Method   meth = clzz.getMethod("clone", new Class[0]);
					Object   dupl = meth.invoke(this.moDataStructure, new Object[0]);
					oClone.moDataStructure =  (clsDataStructurePA)dupl; 
				} catch (Exception e) {
					return e;
				}
        	}
        	
        	poClonedNodeList.add(new clsPair<clsDataStructurePA, clsDataStructurePA>(this.getMoDataStructure(), oClone.getMoDataStructure()));
        	
        	if (moAssociatedDataStructures != null) {
        		oClone.moAssociatedDataStructures = new ArrayList<clsAssociation>(); 
        		for(clsAssociation oAssociation : moAssociatedDataStructures){
        			try { 
    					Object dupl = oAssociation.clone(); 
    					oClone.moAssociatedDataStructures.add((clsAssociation)dupl); 
    				} catch (Exception e) {
    					return e;
    				}
            	} 
        	}
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
        	System.err.println("clsPrimaryDataStructureContainer.clone() - CloneNotSupportedException:"+e);
        	return null;
        }
	}
	
	
//	/**
//	 * Find a certain data structure in the container. If the data structure is not found, null is returned, else the instance of that data structure
//	 * 
//	 * The compare criterium is the Type ID and not the instance ID
//	 *
//	 * @since 21.07.2011 09:27:59
//	 *
//	 * @param clsDataStructurePA
//	 * @return
//	 */
//	public clsDataStructurePA containsInstanceType(clsDataStructurePA poInput) {
//		clsDataStructurePA oRetVal = null;
//		
//		if (this.moDataStructure.moDS_ID == poInput.moDS_ID) {
//			oRetVal = this.moDataStructure;
//		} else {
//			if (poInput instanceof clsTemplateImage) {
//				for (clsAssociation oAss : ((clsTemplateImage)this.moDataStructure).moAssociatedContent) {
//					if (oAss.getLeafElement().moDS_ID == poInput.moDS_ID) {
//						oRetVal = oAss.getLeafElement();
//						break;
//					}
//				}
//			} else if (poInput instanceof clsThingPresentationMesh) {
//				for (clsAssociation oAss : ((clsTemplateImage)this.moDataStructure).moAssociatedContent) {
//					if (oAss.getLeafElement().moDS_ID == poInput.moDS_ID) {
//						oRetVal = oAss.getLeafElement();
//						break;
//					}
//				}
//			}
//		}
//		
//		return oRetVal;
//	}
}
