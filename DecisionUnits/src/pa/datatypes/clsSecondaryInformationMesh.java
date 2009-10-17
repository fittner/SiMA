/**
 * clsSecondaryWithAssoziations.java: DecisionUnits - pa.datatypes
 * 
 * @author deutsch
 * 09.09.2009, 17:00:29
 */
package pa.datatypes;

import java.util.ArrayList;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.09.2009, 17:00:29
 * 
 */
public class clsSecondaryInformationMesh implements Cloneable {
	public clsSecondaryInformation moSecondaryInformation;
	public ArrayList<clsAssoziationSecondary> moAssoziations;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsSecondaryInformationMesh oClone = (clsSecondaryInformationMesh)super.clone();
        	oClone.moSecondaryInformation = (clsSecondaryInformation)moSecondaryInformation.clone();
        	
        	oClone.moAssoziations = new ArrayList<clsAssoziationSecondary>();   	
        	for (clsAssoziationSecondary oValue:moAssoziations) {
        		oClone.moAssoziations.add((clsAssoziationSecondary) oValue.clone());
        	}
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}		
}
