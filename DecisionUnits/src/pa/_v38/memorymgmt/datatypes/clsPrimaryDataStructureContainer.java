/**
 * clsPrimaryInformation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:21
 */
package pa._v38.memorymgmt.datatypes;

import java.lang.reflect.Method;
import java.util.ArrayList;

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
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsPrimaryDataStructureContainer oClone = (clsPrimaryDataStructureContainer)super.clone();
        	if (moAssociatedDataStructures != null) {
        		oClone.moAssociatedDataStructures = new ArrayList<clsAssociation>(); 
        		for(clsAssociation oAssociation : moAssociatedDataStructures){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone); 
    					oClone.moAssociatedDataStructures.add((clsAssociation)dupl); 
    				} catch (Exception e) {
    					return e;
    				}
            	} 
        	}
        	
        	if (this.moDataStructure != null) {
				try { 
					Class<?> clzz = this.moDataStructure.getClass();
					Method   meth = clzz.getMethod("clone", new Class[0]);
					Object   dupl = meth.invoke(this.moDataStructure, new Object[0]);
					oClone.moDataStructure =  (clsDataStructurePA)dupl; 
				} catch (Exception e) {
					return e;
				}
        	}
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}
	
	/**
	 * This variant of getMoAssociatedDataStructures is only useful for containers with a "combined"
	 * DataStructure like a TemplateImage for example. Unlike the variant without an argument this
	 * version does not return all AssociatedDataStructures, but only the ones that are associated
	 * with the provided argument.If the DataStructure in the container is a single Item, like
	 * a ThingPresentationMesh, then the result will be the whole list of AssociatedDataStructures,
	 * just like the variant without an argument.<br>
	 * <br>
	 * <b>NOTE</b><br>
	 * Comparison between the argument and the RootElements of the AssociatedDataStructures is done
	 * on the basis of the moDS_ID. While this is sufficient for the problem that made this method
	 * necessary in the first place, this is in general <b>NOT</b> a suitable way of identifying
	 * individual objects! Therefore this method should either be made obsolete by replacing the use
	 * of containers with composite DataStructures by a better suited type or a new way of identifying
	 * individual DataStructures has to be introduced.  
	 *
	 * @author Zottl Marcus (e0226304),
	 * 28.06.2011, 18:47:52
	 *
	 * @param poAssociationRoot - an element of the DataStructure in the container for which you need
	 * the AssociatedDataStructures.
	 * @return an ArrayList of <i>clsAssociation</i>s that belong to the provided argument.
	 */
	public ArrayList<clsAssociation> getMoAssociatedDataStructures(clsDataStructurePA poAssociationRoot) {
		ArrayList<clsAssociation> oReturnList = new ArrayList<clsAssociation>();
		
		for (clsAssociation oEntry : moAssociatedDataStructures) {
			if (oEntry.getRootElement().getMoDSInstance_ID() == poAssociationRoot.getMoDSInstance_ID()) {
				oReturnList.add(oEntry);
			}
		}
		return oReturnList;
	}
}
