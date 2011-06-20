/**
 * clsPrimaryInformation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:21
 */
package pa._v19.memorymgmt.datatypes;

import java.lang.reflect.Method;
import java.util.ArrayList;

/**
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
}
