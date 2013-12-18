/**
 * CHANGELOG
 *
 * 07.11.2012 wendt - File created
 *
 */
package base.datatypes;

import java.util.ArrayList;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.ePhiPosition;
import base.datatypes.helpstructures.clsTriple;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 07.11.2012, 09:46:59
 * 
 */
public class clsThingPresentationMeshEntity extends clsThingPresentationMesh {

	private clsAssociationAttribute oPositionAttribute;
	private clsAssociationAttribute oRadiusAttribute;
	
	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 07.11.2012 09:47:15
	 *
	 * @param poDataStructureIdentifier
	 * @param poAssociatedPhysicalRepresentations
	 * @param poContent
	 */
	public clsThingPresentationMeshEntity(
			clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
			ArrayList<clsAssociation> poAssociatedPhysicalRepresentations,
			String poContent) {
		super(poDataStructureIdentifier, poAssociatedPhysicalRepresentations, poContent);
		// TODO (wendt) - Auto-generated constructor stub
	}
	
	public ePhiPosition getPosition() {
		ePhiPosition oResult = null;
		
		if (oPositionAttribute!=null) {
			String oContent = (String) ((clsThingPresentation)oPositionAttribute.getLeafElement()).getContent();
			oResult = ePhiPosition.elementAt(oContent);
		}
		
		return oResult;
	}
	
	public void setPosition(clsAssociationAttribute poPositionAttribute) {
		//Check if position already exists in attribute
		
		//If yes, replace
			//Remove old one from external ass list
			//replace local variable
			//Add to ext. list
		
		//else add
			//1. Add to external associations
			//2. set oPositionAttribute
		//
	}	
	
	public void removePosition() {
		//clsMeshTools.removeUniqueTP(this, eContentType.DISTANCE);
		this.getExternalAssociatedContent().remove(oPositionAttribute);
		oRadiusAttribute=null;
		clsMeshTools.removeUniqueTP(this, eContentType.POSITION);
		oPositionAttribute=null;
	}
	

}
