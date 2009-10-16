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
public class clsPrimaryInformationMesh extends clsPrimaryInformation implements Cloneable {

	public  ArrayList<clsAssociationContext<clsPrimaryInformation>> moAssociations;
	

	public clsPrimaryInformationMesh(
			clsThingPresentationSingle poThingPresentationSingle) {
		super(poThingPresentationSingle);
		moAssociations = new ArrayList<clsAssociationContext<clsPrimaryInformation>>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsPrimaryInformationMesh oClone = (clsPrimaryInformationMesh)super.clone();
        	
        	oClone.moAssociations = new ArrayList<clsAssociationContext<clsPrimaryInformation>>();   	
        	for (clsAssociationContext<clsPrimaryInformation> oValue:moAssociations) {
        		oClone.moAssociations.add((clsAssociationContext<clsPrimaryInformation>) oValue.clone());
        	}
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}		
}
