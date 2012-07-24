/**
 * CHANGELOG
 *
 * Jul 10, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.text.DecimalFormat;
import java.util.ArrayList;

import du.enums.eOrgan;
import du.enums.eOrifice;
import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author schaat
 * Jul 10, 2012, 1:19:27 PM
 * 
 */
public class clsDriveMesh extends clsHomeostaticRepresentation implements itfInternalAssociatedDataStructure, itfExternalAssociatedDataStructure{
	
	private double mrQuotaOfAffect = 0.0;				//0-1
	private eDriveComponent moDriveComponent ;			//Triebkomponente (agressiv/libidonoes)
	private ePartialDrive moPartialDrive  ;				//Partialtriebe (A/O/P/G)
	
	//private clsThingPresentationMesh moDriveObject;		//Triebobjekt contenttype entity
	//private clsThingPresentationMesh moDriveAim;		//Triebziel contenttype action
	//private clsThingPresentation moDriveSource;			//Triebquelle
	//private clsThingPresentation moBodyOrifice; 		//Koerperoeffnung
	private ArrayList<clsAssociation> moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
	private ArrayList<clsAssociation> moInternalAssociatedContent = new ArrayList<clsAssociation>();  //enthällt das aktuelle triebzie, objekt und quelle (ggf Körperöffung), also max 2 Einträge
	
	

	/**
	 * DOCUMENT (schaat) - insert description 
	 *
	 * @since Jul 10, 2012 1:21:34 PM
	 *
	 * @param poDataStructureIdentifier
	 */
	public clsDriveMesh(	clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, ArrayList<clsAssociation> poInternalAssociatedContent, double prQuotaOfAffect, String poContent, eDriveComponent poDriveComponent, ePartialDrive poPartialDrive) {
		super(poDataStructureIdentifier);
		// TODO (schaat) - Auto-generated constructor stub
		
		mrQuotaOfAffect = prQuotaOfAffect;
		moInternalAssociatedContent = poInternalAssociatedContent;
		moDebugInfo = poContent;
			
		moPartialDrive = poPartialDrive;
		
		moDriveComponent = poDriveComponent;
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
		
		eOrifice retVal = eOrifice.UNDEFINED;
		
		try{
			clsThingPresentationMesh oDriveOrifice = getAssociatedObject(eContentType.ORIFICE);
			if(oDriveOrifice != null)
			{
				retVal = eOrifice.valueOf(oDriveOrifice.getMoContent());
			}
		}
		catch(Exception e){
			System.out.printf(e +"\n"+ e.getStackTrace().toString());
		}
		return retVal;
	}
	
	public clsThingPresentationMesh getActualDriveSource(){
		return getAssociatedObject(eContentType.ORGAN);
	}
	
	//organs are fixed for PA body, thus we can do this- here
	public eOrgan getActualDriveSourceAsENUM(){
		eOrgan retVal = eOrgan.UNDEFINED;
		try{
			clsThingPresentationMesh oDriveSource = getAssociatedObject(eContentType.ORGAN);
			if(oDriveSource != null)
			{
				retVal = eOrgan.valueOf(oDriveSource.getMoContent());
			}
		}
		catch(Exception e){
			System.out.printf(e +"\n"+ e.getStackTrace().toString());
		}
		return retVal;
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
			//TODO: deep debug info, if needed System.out.printf("No Object of type " + oContentType.toString() +" associated to DriveMesh");
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
				clsDataStructureGenerator.generateASSOCIATIONDM(this, (clsThingPresentationMesh)poDriveSource, prWeight));
	}
	
	public void associateActualDriveAim(clsThingPresentationMesh poDriveAim, double prWeight) throws Exception{
		
		if(ContainsAssociatedContentType(eContentType.ACTION))
			throw new Exception("Cannot associate type " +eContentType.ACTION+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONDM(this, (clsThingPresentationMesh)poDriveAim, prWeight));
	}
	
	public void associateActualDriveObject(clsThingPresentationMesh poDriveObject, double prWeight) throws Exception{
		
		if(ContainsAssociatedContentType(eContentType.ENTITY))
			throw new Exception("Cannot associate type " +eContentType.ENTITY+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONDM(this, (clsThingPresentationMesh)poDriveObject, prWeight));
	}
	
	public void associateActualBodyOrifice(clsThingPresentationMesh poDriveOrifice, double prWeight) throws Exception{
		
		if(ContainsAssociatedContentType(eContentType.ORIFICE))
			throw new Exception("Cannot associate type " +eContentType.ORIFICE+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONDM(this, (clsThingPresentationMesh)poDriveOrifice, prWeight));
	}

	
	@Override
	public String toString(){
		String oRetval = "|DM:";
		oRetval += ":QoA="+GetQuotaOfAffectAsMyString(this.mrQuotaOfAffect);
		oRetval += ":DComponent="+this.moDriveComponent.toString();
		oRetval += ":PartialD="+this.moPartialDrive.toString();
		oRetval += ":Organ="+this.getActualDriveSourceAsENUM();
		oRetval += ":Orifice="+this.getActualBodyOrificeAsENUM();
		//if(this.moInternalAssociatedContent!=null){
			oRetval += ": :Internal="+this.moInternalAssociatedContent.toString();
		//}
		//else
		//	oRetval += ": :Internal=NULL";
		//if(this.moExternalAssociatedContent!=null){
			oRetval += ": :External="+this.moExternalAssociatedContent.toString();
		//}
		//else
		//	oRetval += ": :External=NULL";
			if(this.moDebugInfo!=null && this.moDebugInfo!="")
				oRetval += ":DBG='"+this.moDebugInfo+"'";
		oRetval += "|\n";
		return oRetval;
	}
	
	private String GetQuotaOfAffectAsMyString(double rQoA){
		DecimalFormat threeDec = new DecimalFormat("0.00000");
		String shortString = (threeDec.format(rQoA));
		return shortString;
	}
	
	//this is used for chart data short lables only!
	public String getChartShortString(){
		String oRetval = "";
		
		if(this.moDriveComponent==eDriveComponent.AGGRESSIVE){
			oRetval += "A.";
		}
		else if(this.moDriveComponent==eDriveComponent.LIBIDINOUS){
			oRetval += "L.";
		}
		
		oRetval +=getPartialShortString(this.moPartialDrive)+".";
		oRetval += this.getActualDriveSourceAsENUM();

		return oRetval;
	}
	
	private String getPartialShortString(ePartialDrive oPartialDrive){
		String oRetVal = "N"; //aka UNDEFINED,
		
		switch (oPartialDrive){
		case ANAL:
			oRetVal = "A";
			break;
		case ORAL:
			oRetVal = "O";
			break;
		case PHALLIC:
			oRetVal = "P";
			break;
		case GENITAL:
			oRetVal = "G";
			break;
		}
		return oRetVal;
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
		ArrayList <clsAssociation> oContentListTemplate = this.moInternalAssociatedContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moInternalAssociatedContent;
				
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
			else if (oDataStructure.moDS_ID >= -1) {
			//In case the data structure does not have an ID, it has to be compared to a stored 
			//data structure and replaced by it (the processes base on information that is already
			//defined
			
				//Comparision of DMs makes only sense, if they have the same component, source (and partialdrive). Driveaim and -object are comparable
					
				if(this.moDriveComponent == oDataStructure.moDriveComponent){
					if(this.moPartialDrive == oDataStructure.moPartialDrive){
						if(this.getActualDriveSourceAsENUM() == oDataStructure.getActualDriveSourceAsENUM()){
							oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
							oRetVal = (oRetVal + 1)/2; // drivecomponent (+ partialdrive) (at this stage always "1") have the same weight as driveobject + driveaim
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
			ArrayList<clsAssociation> poInternalAssociatedContent) {
		// TODO (schaat) - Auto-generated method stub
		this.moInternalAssociatedContent = poInternalAssociatedContent;		
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#getNumbAssociations()
	 */
	@Override
	public double getNumbInternalAssociations() {
		return moInternalAssociatedContent.size();
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


	/* (non-Javadoc)
	 *
	 * @since Jul 19, 2012 10:12:43 AM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfExternalAssociatedDataStructure#getExternalMoAssociatedContent()
	 */
	@Override
	public ArrayList<clsAssociation> getExternalMoAssociatedContent() {
		// TODO (schaat) - Auto-generated method stub
		return moExternalAssociatedContent;
	}


	/* (non-Javadoc)
	 *
	 * @since Jul 19, 2012 10:12:43 AM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfExternalAssociatedDataStructure#setMoExternalAssociatedContent(java.util.ArrayList)
	 */
	@Override
	public void setMoExternalAssociatedContent(
			ArrayList<clsAssociation> poExternalAssociatedContent) {
		// TODO (schaat) - Auto-generated method stub
		this.moExternalAssociatedContent = poExternalAssociatedContent; 
	}


	/* (non-Javadoc)
	 *
	 * @since Jul 19, 2012 10:12:43 AM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfExternalAssociatedDataStructure#getNumbExternalAssociations()
	 */
	@Override
	public double getNumbExternalAssociations() {
		// TODO (schaat) - Auto-generated method stub
		return moExternalAssociatedContent.size();
	}


	/* (non-Javadoc)
	 *
	 * @since Jul 19, 2012 10:12:43 AM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfExternalAssociatedDataStructure#addExternalAssociations(java.util.ArrayList)
	 */
	@Override
	public void addExternalAssociations(
			ArrayList<clsAssociation> poAssociatedDataStructures) {
		// TODO (schaat) - Auto-generated method stub
		moExternalAssociatedContent.addAll(poAssociatedDataStructures); 
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsDriveMesh  oClone = (clsDriveMesh)super.clone();
        	if (moInternalAssociatedContent != null) {
        		oClone.moInternalAssociatedContent = new ArrayList<clsAssociation>(); 
        		
        		for(clsAssociation oAssociation : moInternalAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone, new ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>>()); 
    					oClone.moInternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
    				} catch (Exception e) {
    					return e;
    				}
        		}
        	}
        	
        	if (moExternalAssociatedContent != null) {
        		oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
        		
        		for(clsAssociation oAssociation : moExternalAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone, new ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>>()); 
    					oClone.moExternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
    				} catch (Exception e) {
    					return e;
    				}
        		}
        	}
        	
          	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}

}
