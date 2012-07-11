/**
 * CHANGELOG
 *
 * Jul 10, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author schaat
 * Jul 10, 2012, 1:19:27 PM
 * 
 */
public class clsDriveMeshNew extends clsHomeostaticRepresentation implements itfAssociatedDataStructure{
	
	private double mrQuotaOfAffect = 0.0;				//0-1
	private eDriveComponent moDriveComponent;			//Triebkomponente (agressiv/libidonoes)
	private ePartialDrive moPartialDrive;				//Partialtriebe (A/O/P/G)
	
	//private clsThingPresentationMesh moDriveObject;		//Triebobjekt contenttype entity
	//private clsThingPresentationMesh moDriveAim;		//Triebziel contenttype action
	//private clsThingPresentation moDriveSource;			//Triebquelle
	//private clsThingPresentation moBodyOrifice; 		//Koerperoeffnung
	private ArrayList<clsAssociation> moExternalAssociatedContent = null; 
	private ArrayList<clsAssociation> moInternalAssociatedContent = null;  //enthällt das aktuelle triebzie, objekt und quelle (ggf Körperöffung), also max 2 Einträge
	

	/**
	 * DOCUMENT (schaat) - insert description 
	 *
	 * @since Jul 10, 2012 1:21:34 PM
	 *
	 * @param poDataStructureIdentifier
	 */
	public clsDriveMeshNew(	clsTriple<Integer, eDataType, String> poDataStructureIdentifier) {
		super(poDataStructureIdentifier);
		// TODO (schaat) - Auto-generated constructor stub
	}
	
	public clsThingPresentationMesh getActualDriveObject(){
		return null;
	}
	
	public clsThingPresentationMesh getActualDriveAim(){
		return null;
	}
	
	public clsThingPresentationMesh getActualBodyOrifice(){
		return null;
	}
	
	public clsThingPresentationMesh getActualDriveSource(){
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfComparable#compareTo(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		// TODO (schaat) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#getMoAssociatedContent()
	 */
	@Override
	public ArrayList<clsAssociation> getMoAssociatedContent() {
		// TODO (schaat) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#setMoAssociatedContent(java.util.ArrayList)
	 */
	@Override
	public void setMoAssociatedContent(
			ArrayList<clsAssociation> moAssociatedContent) {
		// TODO (schaat) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#getNumbAssociations()
	 */
	@Override
	public double getNumbAssociations() {
		// TODO (schaat) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#addAssociations(java.util.ArrayList)
	 */
	@Override
	public void addAssociations(
			ArrayList<clsAssociation> poAssociatedDataStructures) {
		// TODO (schaat) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#assignDataStructure(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		// TODO (schaat) - Auto-generated method stub
		
	}

}
