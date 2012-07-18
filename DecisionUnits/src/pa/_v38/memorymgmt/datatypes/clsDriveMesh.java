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
	public clsDriveMesh(	clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, double prQuotaOfAffect) {
		super(poDataStructureIdentifier);
		// TODO (schaat) - Auto-generated constructor stub
		
		mrQuotaOfAffect = prQuotaOfAffect;
	}
	
	
	public clsThingPresentationMesh getActualDriveObject(){
		return getAssociatedObject(eContentType.DRIVEOBJECTASSOCIATION);
	}
	
	public clsThingPresentationMesh getActualDriveAim(){
		return getAssociatedObject(eContentType.DRIVEAIMASSOCIATION);
	}
	
	public clsThingPresentationMesh getActualBodyOrifice(){
		return getAssociatedObject(eContentType.ORIFICE);
	}
	
	public clsThingPresentationMesh getActualDriveSource(){
		return getAssociatedObject(eContentType.ORGAN);
	}
	
	private clsAssociationAttribute getAssociation(eContentType oContentType){
		clsAssociationAttribute oRetVal = null;
		
		for(clsAssociation oAA : moInternalAssociatedContent)
		{
			if(oAA.getMoContentType() == oContentType) //TODO no more strings
				oRetVal = (clsAssociationAttribute)oAA;
		}
		return oRetVal;
	}
	
	private clsThingPresentationMesh getAssociatedObject(eContentType oContentType){
		clsDataStructurePA oRetVal = null;
		
		for(clsAssociation oAA : moInternalAssociatedContent)
		{
			if(oAA.getMoContentType() == oContentType)
				oRetVal = (clsAssociationAttribute)oAA.moAssociationElementB;
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
			if(oAA.getMoContentType() == oContentType)
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
		
		if(ContainsAssociatedContentType(eContentType.DRIVEAIMASSOCIATION))
			throw new Exception("Cannot associate type " +eContentType.DRIVEAIMASSOCIATION+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.DRIVEAIMASSOCIATION, 
																		(clsPrimaryDataStructure)this, 
																		(clsPrimaryDataStructure)poDriveAim, 
																		prWeight));
	}
	
	public void associateActualDriveObject(clsThingPresentationMesh poDriveObject, double prWeight) throws Exception{
		
		if(ContainsAssociatedContentType(eContentType.DRIVEOBJECTASSOCIATION))
			throw new Exception("Cannot associate type " +eContentType.DRIVEOBJECTASSOCIATION+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.DRIVEOBJECTASSOCIATION, 
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
	public String getDebugInfo() 	{
		String oRetval = "|DM:";
		oRetval += ":QoA="+this.mrQuotaOfAffect;
		oRetval += ":DComponent="+this.moDriveComponent;
		oRetval += ":PartialD="+this.moPartialDrive;
		oRetval += ":Internal="+this.moInternalAssociatedContent.toString();
		oRetval += ":External="+this.moExternalAssociatedContent.toString();
		oRetval += "|";
		return oRetval;
	}
	
	@Override
	public String toString(){
		return getDebugInfo();
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
		throw new NotImplementedException();
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
