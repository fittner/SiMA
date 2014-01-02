/**
 * clsSecondaryInformation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:31
 */
package base.datatypes;

import java.lang.reflect.Method;
import java.util.ArrayList;

import base.datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:42:31
 * @deprecated
 */
public class clsSecondaryDataStructureContainer extends clsDataStructureContainer {
		
	public clsSecondaryDataStructureContainer(String typ, String content) {
		super(typ, content);
	}
	
	public clsSecondaryDataStructureContainer(clsSecondaryDataStructure poDataStructure, ArrayList<clsAssociation>poAssociationList){
		super(poDataStructure, poAssociationList);  
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsSecondaryDataStructureContainer oClone = (clsSecondaryDataStructureContainer)super.clone();
        	
        	if (moAssociatedDataStructures != null) {
        		oClone.moAssociatedDataStructures = new ArrayList<clsAssociation>(); 
        		for(clsAssociation oAssociation : moAssociatedDataStructures){
        				try { 
        					Object dupl = oAssociation.clone(this, oClone, new ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>>()); 
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
