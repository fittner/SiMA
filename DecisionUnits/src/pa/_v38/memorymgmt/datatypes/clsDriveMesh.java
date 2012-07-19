/**
 * CHANGELOG
 *
 * Jul 10, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import du.enums.eOrgan;
import du.enums.eOrifice;
import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author schaat
 * Jul 10, 2012, 1:19:27 PM
 * 
 */
public class clsDriveMesh extends clsHomeostaticRepresentation implements itfInternalAssociatedDataStructure{
	
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
	public clsDriveMesh(	clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, ArrayList<clsAssociation> poInternalAssociatedContent, double prQuotaOfAffect, String poContent) {
		super(poDataStructureIdentifier);
		// TODO (schaat) - Auto-generated constructor stub
		
		mrQuotaOfAffect = prQuotaOfAffect;
		moInternalAssociatedContent = poInternalAssociatedContent;
		moDebugInfo = poContent;
	}
	
	
	public clsThingPresentationMesh getActualDriveObject(){
		return getAssociatedObject(eContentType.ENTITY);
	}
	
	public clsThingPresentationMesh getActualDriveAim(){
		return getAssociatedObject(eContentType.ACTION);
	}
	
	public clsThingPresentationMesh getActualBodyOrifice(){
		return getAssociatedObject(eContentType.ORIFICE);
	}
	
	//orifices are fixed for PA body, thus we can do this here
	public eOrifice getActualBodyOrificeAsENUM(){
		return eOrifice.valueOf(getAssociatedObject(eContentType.ORIFICE).getMoContent());
	}
	
	public clsThingPresentationMesh getActualDriveSource(){
		return getAssociatedObject(eContentType.ORGAN);
	}
	
	//organs are fixed for PA body, thus we can do this here
	public eOrgan getActualDriveSourceAsENUM(){
		return eOrgan.valueOf(getAssociatedObject(eContentType.ORGAN).getMoContent());
	}
	
	private clsAssociationDriveMesh getAssociation(eContentType oContentType){
		clsAssociationDriveMesh oRetVal = null;
		
		for(clsAssociation oAA : moInternalAssociatedContent)
		{
			if(oAA.getMoContentType() == oContentType) 
				oRetVal = (clsAssociationDriveMesh)oAA;
		}
		return oRetVal;
	}
	
	private clsThingPresentationMesh getAssociatedObject(eContentType oContentType){
		clsDataStructurePA oRetVal = null;
		
		for(clsAssociation oAA : moInternalAssociatedContent)
		{
			clsThingPresentationMesh oTPM = (clsThingPresentationMesh)oAA.getMoAssociationElementB();
			if(oTPM.getMoContentType() == oContentType)
				oRetVal = oTPM;
		}
		
		if( oRetVal != null )
		{
			return (clsThingPresentationMesh)oRetVal;
		}
		else
		{
			System.out.printf("No Object of type " + oContentType.toString() +" associated to DriveMesh");
			return null;
		}
	}
	
	public boolean ContainsAssociatedContentType(eContentType oContentType){
		boolean oRetVal = false;
		
		for(clsAssociation oAA : moInternalAssociatedContent)
		{
			clsThingPresentationMesh oTPM = (clsThingPresentationMesh)oAA.getMoAssociationElementB();
			if(oTPM.getMoContentType() == oContentType)
				oRetVal = true;
		}
		
		return oRetVal;
	}
	
	public void associateActualDriveSource(clsThingPresentationMesh poDriveSource, double prWeight) throws Exception{

		if(ContainsAssociatedContentType(eContentType.ORGAN))
			throw new Exception("Cannot associate type " +eContentType.ORGAN+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.ORGAN, 
																		(clsPrimaryDataStructure)this, 
																		(clsPrimaryDataStructure)poDriveSource, 
																		prWeight));
	}
	
	public void associateActualDriveAim(clsThingPresentationMesh poDriveAim, double prWeight) throws Exception{
		
		if(ContainsAssociatedContentType(eContentType.ACTION))
			throw new Exception("Cannot associate type " +eContentType.ACTION+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.ACTION, 
																		(clsPrimaryDataStructure)this, 
																		(clsPrimaryDataStructure)poDriveAim, 
																		prWeight));
	}
	
	public void associateActualDriveObject(clsThingPresentationMesh poDriveObject, double prWeight) throws Exception{
		
		if(ContainsAssociatedContentType(eContentType.ENTITY))
			throw new Exception("Cannot associate type " +eContentType.ENTITY+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.ENTITY, 
																		(clsPrimaryDataStructure)this, 
																		(clsPrimaryDataStructure)poDriveObject, 
																		prWeight));
	}
	
	public void associateActualBodyOrifice(clsThingPresentationMesh poDriveOrifice, double prWeight) throws Exception{
		
		if(ContainsAssociatedContentType(eContentType.ORIFICE))
			throw new Exception("Cannot associate type " +eContentType.ORIFICE+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.ORIFICE, 
																		(clsPrimaryDataStructure)this, 
																		(clsPrimaryDataStructure)poDriveOrifice, 
																		prWeight));
	}

	
	@Override
	public String toString(){
		String oRetval = "|DM:";
		oRetval += ":QoA="+this.mrQuotaOfAffect;
		oRetval += ":DComponent="+this.moDriveComponent;
		oRetval += ":PartialD="+this.moPartialDrive;
		oRetval += ":Internal="+this.moInternalAssociatedContent.toString();
		oRetval += ":External="+this.moExternalAssociatedContent.toString();
		oRetval += "|";
		return oRetval;
	}
	
	
	/**
	 * @since 11.07.2012 14:10:00
	 * 
	 * @return the mrQuotaOfAffect
	 */
	public double getQuotaOfAffect() {
		return mrQuotaOfAffect;
	}

	/**
	 * @since 11.07.2012 14:10:00
	 * 
	 * @param mrQuotaOfAffect the mrQuotaOfAffect to set
	 */
	public void setQuotaOfAffect(double mrQuotaOfAffect) {
		this.mrQuotaOfAffect = mrQuotaOfAffect;
	}

	/**
	 * @since 11.07.2012 14:10:00
	 * 
	 * @return the moDriveComponent
	 */
	public eDriveComponent getDriveComponent() {
		return moDriveComponent;
	}

	/**
	 * @since 11.07.2012 14:10:00
	 * 
	 * @param moDriveComponent the moDriveComponent to set
	 */
	public void setDriveComponent(eDriveComponent moDriveComponent) {
		this.moDriveComponent = moDriveComponent;
	}

	/**
	 * @since 11.07.2012 14:10:00
	 * 
	 * @return the moPartialDrive
	 */
	public ePartialDrive getPartialDrive() {
		return moPartialDrive;
	}

	/**
	 * @since 11.07.2012 14:10:00
	 * 
	 * @param moPartialDrive the moPartialDrive to set
	 */
	public void setPartialDrive(ePartialDrive moPartialDrive) {
		this.moPartialDrive = moPartialDrive;
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
		double oRetVal = 0.0; 
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}

		clsDriveMesh oDataStructure = (clsDriveMesh)poDataStructure;
		ArrayList <clsAssociation> oContentListTemplate = this.moAssociatedContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moAssociatedContent;
				
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		
			if(this.moDS_ID == oDataStructure.moDS_ID){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result.   
				 */
				//oRetVal = oDataStructure.getNumbAssociations();
				oRetVal = 1.0;
			}
			else if (oDataStructure.moDS_ID > -1) {
			//In case the data structure does not have an ID, it has to be compared to a stored 
			//data structure and replaced by it (the processes base on information that is already
			//defined
			//Drive Mesh content is represented by a list of attribute associations	
					
				if(this.moDriveComponent == oDataStructure.moDriveComponent){
					if(this.moPartialDrive == oDataStructure.moPartialDrive){
						if(this.getActualDriveSource().getMoContent() == oDataStructure.getActualDriveSource().getMoContent()){
							oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
						}
					}
					
				}
			}
		return oRetVal; 
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#getMoAssociatedContent()
	 */
	@Override
	public ArrayList<clsAssociation> getMoInternalAssociatedContent() {
		
		return moInternalAssociatedContent;
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#setMoAssociatedContent(java.util.ArrayList)
	 */
	@Override
	public void setMoInternalAssociatedContent(
			ArrayList<clsAssociation> moAssociatedContent) {
		// TODO (schaat) - Auto-generated method stub
		throw new NotImplementedException();
		
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#getNumbAssociations()
	 */
	@Override
	public double getNumbInternalAssociations() {
		return moAssociatedContent.size();
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#addAssociations(java.util.ArrayList)
	 */
	@Override
	public void addInternalAssociations(
			ArrayList<clsAssociation> poAssociatedDataStructures) {
		// TODO (schaat) - Auto-generated method stub
		moInternalAssociatedContent.addAll(poAssociatedDataStructures); 
		
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
		throw new NotImplementedException();
		
	}

}
