/**
 * clsPrimaryWithAssoziations.java: DecisionUnits - pa.datatypes
 * 
 * @author deutsch
 * 09.09.2009, 16:50:56
 */
package pa.datatypes;

import java.util.ArrayList;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.09.2009, 16:50:56
 * 
 */
public class clsPrimaryWithAssoziations implements Cloneable {
	public clsPrimaryInformation moPrimaryInformation;
	public ArrayList<clsAssoziationPrimary> moAssoziations;
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsPrimaryWithAssoziations oClone = (clsPrimaryWithAssoziations)super.clone();
        	oClone.moPrimaryInformation = (clsPrimaryInformation)moPrimaryInformation.clone();
        	
        	oClone.moAssoziations = new ArrayList<clsAssoziationPrimary>();   	
        	for (clsAssoziationPrimary oValue:moAssoziations) {
        		oClone.moAssoziations.add((clsAssoziationPrimary) oValue.clone());
        	}
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}		
}
