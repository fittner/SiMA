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
public class clsSecondaryInformationMesh extends clsSecondaryInformation implements Cloneable {
	public clsSecondaryInformation moSecondaryInformation;
	public ArrayList<clsAssociation<clsSecondaryInformation>> moAssociations;
		
	//transforms a primary information into a secondary information - stupid copy of content from Thin->WordPresentation
	public clsSecondaryInformationMesh(clsPrimaryInformation poPrim) {
		super(poPrim);
		moAssociations = new ArrayList<clsAssociation<clsSecondaryInformation>>();
		
		if(poPrim instanceof clsPrimaryInformationMesh) {
		
			for( clsAssociation<clsPrimaryInformation> oPrimAssoc : ((clsPrimaryInformationMesh)poPrim).moAssociations ) {
			
				moAssociations.add( oPrimAssoc.convertToSecondary(this) );
			}
		}
	}
	
	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 24.10.2009, 23:48:18
	 *
	 * @param poWordPresentation
	 */
	public clsSecondaryInformationMesh(clsWordPresentation poWordPresentation) {
		super(poWordPresentation);
		moAssociations = new ArrayList<clsAssociation<clsSecondaryInformation>>();
	}

	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsSecondaryInformationMesh oClone = (clsSecondaryInformationMesh)super.clone();
        	oClone.moSecondaryInformation = (clsSecondaryInformation)moSecondaryInformation.clone();
        	
        	oClone.moAssociations = new ArrayList<clsAssociation<clsSecondaryInformation>>();   	
        	for (clsAssociation<clsSecondaryInformation> oValue:moAssociations) {
        		oClone.moAssociations.add((clsAssociation<clsSecondaryInformation>) oValue.clone());
        	}
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}
		
}
