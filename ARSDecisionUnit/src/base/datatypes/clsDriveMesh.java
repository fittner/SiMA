
/**
 * CHANGELOG
 *
 * Jul 10, 2012 schaat - File created
 *
 */
package base.datatypes;

import java.text.DecimalFormat;
import java.util.ArrayList;


import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.enums.eDriveComponent;
import base.datatypes.enums.eOrgan;
import base.datatypes.enums.eOrifice;
import base.datatypes.enums.ePartialDrive;
import base.datatypes.helpstructures.clsTriple;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author schaat
 * Jul 10, 2012, 1:19:27 PM
 * 
 */
public class clsDriveMesh extends clsHomeostaticRepresentation implements itfInternalAssociatedDataStructure, itfExternalAssociatedDataStructure{
	
	public static final String moContent = null;
	private double mrQuotaOfAffect = 0.0;               //0-1
	private double mrQuotaOfAffect_lastStep = 0.0;               //0-1
    private double mrPleasureSum = 0.0;               //0-1
	private double mrPleasureSumMax = 0.0;               //0-1
    private int    mdLearningCnt = 0;               //0-1
    private double mrPsychicSatisfactionValue = 0.0;
	private eDriveComponent moDriveComponent ;			//Triebkomponente (agressiv/libidonoes)
	private ePartialDrive moPartialDrive  ;				//Partialtriebe (A/O/P/G)
	public String moBE = "NO";
    
	
	//private clsThingPresentationMesh moDriveObject;	//Triebobjekt contenttype entity
	//private clsThingPresentationMesh moDriveAim;		//Triebziel contenttype action
	//private clsThingPresentation moDriveSource;		//Triebquelle
	//private clsThingPresentation moBodyOrifice; 		//Koerperoeffnung
	private ArrayList<clsAssociation> moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
	private ArrayList<clsAssociation> moInternalAssociatedContent = new ArrayList<clsAssociation>();  //enthällt das aktuelle triebzie, objekt und quelle (ggf Körperöffung), also max 2 Einträge
	
	

	/**
	 * DOCUMENT (schaat)
	 * 
	 * Constructor of clsDriveMesh:
	 * 
	 * @param clsTriple poDataStructureIdentifier:
	 *            1. Integer (is always -1 for a new drive mesh) Braucht das irgenwer noch???
	 *            2. eDataType (is always: eDataType.DM, DM = drive mesh) Völliger Schwachsinn den eDataType zu erben weil ein drive mesh nur ein drive mesh sein kann!!!
	 *            3. eContentType ??? (e.g.: eContentType.AGGRESSION or eContentType.DEATH or eContentType.LIFE)
	 * @param ArrayList<clsAssociation> poInternalAssociatedContent (1st list element: drive aim, 2nd list element: drive object, 3rd list element: drive source, ...) These list elements must be set via: setActualDriveAim, setActualDriveSource, ...
	 * @param double prQuotaOfAffect (QuotaOfAffect)
	 * @param String poContent (Is only a debug information how the drive is called. E.g. nourisch - Word-presentations are not allowed in the primary process)
	 * @param eDriveComponent poDriveComponent (eDriveComponent.AGGRESSIVE or eDriveComponent.LIBIDINOUS)
	 * @param ePartialDrive poPartialDrive (Partial drives: ePartialDrive.ANAL, ePartialDrive.ORAL, ePartialDrive.PHALLIC, or ePartialDrive.GENITAL)
	 *
	 * @since Jul 10, 2012 1:21:34 PM
	 * 
	 */
	public clsDriveMesh(	clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, ArrayList<clsAssociation> poInternalAssociatedContent, double prQuotaOfAffect, String poContent, eDriveComponent poDriveComponent, ePartialDrive poPartialDrive) {
		super(poDataStructureIdentifier);
		// TODO (schaat) - Auto-generated constructor stub
		
		mrQuotaOfAffect = prQuotaOfAffect;
		moInternalAssociatedContent = poInternalAssociatedContent;
		
		moDebugInfo = poContent;
			
		moPartialDrive = poPartialDrive;
		
		moDriveComponent = poDriveComponent;
		mrPsychicSatisfactionValue = 0.0;
	}
	
	   public clsDriveMesh(    clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, clsDriveMesh poTemplate, double prQuotaOfAffect, String poDebugInfo) {
	        super(poDataStructureIdentifier);
	        // TODO (schaat) - Auto-generated constructor stub
	        
	        mrQuotaOfAffect = prQuotaOfAffect;
	        //moInternalAssociatedContent = poTemplate.getInternalAssociatedContent();
	        
	        //set the right association element

	        for(clsAssociation oAssociation : poTemplate.getInternalAssociatedContent()){
                        // Create new identifier
                        clsTriple<Integer, eDataType, eContentType> oIdentifyer = new clsTriple<Integer, eDataType, eContentType>(-1,
                                eDataType.ASSOCIATIONDM, eContentType.ASSOCIATIONDM);
                        // Create new association drivemesh but with the new root element
                        clsAssociationDriveMesh oDriveAss = new clsAssociationDriveMesh(oIdentifyer, this,
                                (clsThingPresentationMesh) oAssociation.getAssociationElementB());
                        
                        moInternalAssociatedContent.add(oDriveAss);
	        }
	        moDebugInfo = poDebugInfo;
	            
	        moPartialDrive = poTemplate.getPartialDrive();
	        
	        moDriveComponent = poTemplate.getDriveComponent();
	        mrPsychicSatisfactionValue = poTemplate.getPsychicSatisfactionValue();
	    }
	
	   public clsDriveMesh(    clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, ArrayList<clsAssociation> poInternalAssociatedContent, double prQuotaOfAffect, String poContent, eDriveComponent poDriveComponent, ePartialDrive poPartialDrive, double poPsychicSatisfaction) {
	        super(poDataStructureIdentifier);
	        // TODO (schaat) - Auto-generated constructor stub
	        
	        mrQuotaOfAffect = prQuotaOfAffect;
	        moInternalAssociatedContent = poInternalAssociatedContent;
	        moDebugInfo = poContent;
	            
	        moPartialDrive = poPartialDrive;
	        
	        moDriveComponent = poDriveComponent;
	        mrPsychicSatisfactionValue = poPsychicSatisfaction;
	    }
	
	
	public clsThingPresentationMesh getActualDriveObject()
	{
		return getAssociatedObject(eContentType.ENTITY);
	}
	
	public clsThingPresentationMesh getActualDriveAim()
	{
		return getAssociatedObject(eContentType.ACTION);
	}
	
	public clsThingPresentationMesh getActualBodyOrifice()
	{
		return getAssociatedObject(eContentType.ORIFICE);
	}
	
	public double getPsychicSatisfactionValue()
	{
	    return mrPsychicSatisfactionValue;
	}
	//orifices are fixed for PA body, thus we can do this here
	public eOrifice getActualBodyOrificeAsENUM()
	{
		
		eOrifice retVal = eOrifice.UNDEFINED;
		
		try{
			clsThingPresentationMesh oDriveOrifice = getAssociatedObject(eContentType.ORIFICE);
			if(oDriveOrifice != null)
			{
				retVal = eOrifice.valueOf(oDriveOrifice.getContent());
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
				retVal = eOrgan.valueOf(oDriveSource.getContent());
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
			if(oAA.getContentType() == oContentType) 
				oRetVal = (clsAssociationDriveMesh)oAA;
		}
		return oRetVal;
	}
	
	private clsThingPresentationMesh getAssociatedObject(eContentType oContentType){
		clsDataStructurePA oRetVal = null;
		
		for(clsAssociation oAA : moInternalAssociatedContent)
		{
			clsThingPresentationMesh oTPM = (clsThingPresentationMesh)oAA.getAssociationElementB();
			if(oTPM.getContentType() == oContentType)
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
		
		if (moInternalAssociatedContent != null) {
			for(clsAssociation oAA : moInternalAssociatedContent)
			{
				clsThingPresentationMesh oTPM = (clsThingPresentationMesh)oAA.getAssociationElementB();
				if(oTPM.getContentType() == oContentType)
					oRetVal = true;
			}
		}
		
		return oRetVal;
	}
	
	/*
	 * @param poExceptionMessage
	 * @param poContentType: eContentType.ORGAN, eContentType.ACTION, eContentType.ENTITY, or eContentType.ORIFICE
	 * @param poDriveTPM: DriveSource, DriveAim, DriveObject, or BodyOrifice
	 * @param prWeight: Importance of the DriveSource, DriveAim, DriveObject, or BodyOrifice 
	 * 
	 */
	private void setAssociatedContent(String poExceptionMessage, eContentType poContentType, clsThingPresentationMesh poDriveTPM, double prWeight) throws Exception{
		
		int i = 0;
 
		if(poDriveTPM == null) {
			throw new Exception(poExceptionMessage);
		}

		if(ContainsAssociatedContentType(poContentType))
			for(clsAssociation oAA : moInternalAssociatedContent)
			{
				if(oAA.getAssociationElementB().getContentType() == poContentType) {
					moInternalAssociatedContent.get(i).setAssociationElementB(poDriveTPM);
					moInternalAssociatedContent.get(i).setMrWeight(prWeight);
				}
				
				i++;
			}
		else
			if (moInternalAssociatedContent != null)
				moInternalAssociatedContent.add(
					clsDataStructureGenerator.generateASSOCIATIONDM(this, (clsThingPresentationMesh)poDriveTPM, prWeight));

	}

	public void setActualDriveSource(clsThingPresentationMesh poDriveSource, double prWeight) throws Exception{
		
		setAssociatedContent("Drivesource must not be null", eContentType.ORGAN, poDriveSource, prWeight);
	}
	
	public void setActualDriveAim(clsThingPresentationMesh poDriveAim, double prWeight) throws Exception{
		
		setAssociatedContent("Driveaim must not be null", eContentType.ACTION, poDriveAim, prWeight);
	}
	
	public void setActualDriveObject(clsThingPresentationMesh poDriveObject, double prWeight) throws Exception{
		
		setAssociatedContent("Driveobject must not be null", eContentType.ENTITY, poDriveObject, prWeight);
	}
	
	public void setActualBodyOrifice(clsThingPresentationMesh poBodyOrifice, double prWeight) throws Exception{
		
		setAssociatedContent("Bodyorifice must not be null", eContentType.ORIFICE, poBodyOrifice, prWeight);
	}

	
	/*
	// Wenn Clemens einverstanden ist, kann man die folgenden 4 Methoden loeschen.
	// Wenn es niemand bis Dezember 2012 geloescht hat und es auch niemendem abgegangen ist und irgendjemand diese Zeilen liest,
	// dann kann er die folgenden 4 Methoden loeschen.   
	  
	public void associateActualDriveSource(clsThingPresentationMesh poDriveSource, double prWeight) throws Exception{

		if(ContainsAssociatedContentType(eContentType.ORGAN))
			throw new Exception("Cannot associate type " +eContentType.ORGAN+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONDM(this, (clsThingPresentationMesh)poDriveSource, prWeight));
	}
	
	public void associateActualDriveAim(clsThingPresentationMesh poDriveAim, double prWeight) throws Exception{
		
		if(poDriveAim == null) {
			throw new Exception("Driveaim must not be null");
		}
		
		if(ContainsAssociatedContentType(eContentType.ACTION))
			throw new Exception("Cannot associate type " +eContentType.ACTION+ " to DM, already associated.");
		
		moInternalAssociatedContent.add(
				clsDataStructureGenerator.generateASSOCIATIONDM(this, (clsThingPresentationMesh)poDriveAim, prWeight));
	}
	
	public void associateActualDriveObject(clsThingPresentationMesh poDriveObject, double prWeight) throws Exception{
		
		if(poDriveObject == null) {
			throw new Exception("Driveobject must not be null");
		}
		
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
*/
	
	@Override
	
	
	public String toString(){
	    String oRetval = "|DM:";
	    oRetval += ":Action="+(this.getActualDriveAim()!=null?this.getActualDriveAim().getContent():"no action");
	    oRetval += ":Object="+(this.getActualDriveObject()!=null?this.getActualDriveObject().getContent():"no object");
	    oRetval += ":BE="+ this.getBE();
	    oRetval += ":QoA="+GetQuotaOfAffectAsMyString(this.mrQuotaOfAffect);
        oRetval += ":QoASum="+GetQuotaOfAffectAsMyString(this.mrQuotaOfAffect);
        oRetval += ":PleSum= " + GetQuotaOfAffectAsMyString(this.getPleasureSum());
        oRetval += ":PleSumMax= " + GetQuotaOfAffectAsMyString(this.getPleasureSumMax());
        oRetval += ":LeaCnt= " + GetQuotaOfAffectAsMyString(this.getLearningCnt());
        oRetval += ":DComponent="+this.moDriveComponent.toString();
		oRetval += ":PartialD="+this.moPartialDrive.toString();
		oRetval += ":Organ="+this.getActualDriveSourceAsENUM();
		oRetval += ":Orifice="+this.getActualBodyOrificeAsENUM();
		oRetval += ":Aim=" + (this.getActualDriveAim()!=null?this.getActualDriveAim().getContent():"no action");
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
		DecimalFormat threeDec = new DecimalFormat("0.000");
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
		String oRetVal = "-"; //aka UNDEFINED,
		
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
	public int getLearningCnt() {
		return mdLearningCnt;
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
     * @return the mrQuotaOfAffect
     */
    public double getQuotaOfAffect_lastStep() {
        return mrQuotaOfAffect_lastStep;
    }   
    

    /**
     * @since 11.07.2012 14:10:00
     * 
     * @param mrQuotaOfAffect the mrQuotaOfAffect to set
     */
    public void setLearningCnt(int mdLearningCnt) {
        this.mdLearningCnt = mdLearningCnt;
    }
    
    /**
     * @since 11.07.2012 14:10:00
     * 
     * @return the mrQuotaOfAffect
     */
    public double getPleasureSum() {
        return mrPleasureSum;
    }
    
    /**
     * @since 11.07.2012 14:10:00
     * 
     * @param mrQuotaOfAffect the mrQuotaOfAffect to set
     */
    public void setPleasureSum(double mrPleasureSum) {
        this.mrPleasureSum = mrPleasureSum;
    }    
    
    /**
     * @since 11.07.2012 14:10:00
     * 
     * @return the mrQuotaOfAffect
     */
    public double getPleasureSumMax() {
        return mrPleasureSumMax;
    }
    
    /**
     * @since 11.07.2012 14:10:00
     * 
     * @param mrQuotaOfAffect the mrQuotaOfAffect to set
     */
    public void setPleasureSumMax(double mrPleasureSumMax) {
        this.mrPleasureSumMax = mrPleasureSumMax;
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
     * @return the mrQuotaOfAffect
     */
    public double setQuotaOfAffect_lastStep(double mrQuotaOfAffect) {
        return mrQuotaOfAffect_lastStep = mrQuotaOfAffect;
    }   

	/**
	 * @since 28.01.2013 14:10:00
	 * 
	 * @param mrQuotaOfAffect the mrQuotaOfAffect to set
	 * 
	 * "besetzen des triebobjekts"
	 */
	public void cathexis(double prCathexis) {
		 if (this.getActualDriveObject() != null) {
			 this.getActualDriveObject().cathexisAndCondensation(prCathexis);
		 }
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
		
		if(this.moDataStructureType != poDataStructure.moDataStructureType)
		{
		    return oRetVal;
		}

		clsDriveMesh oDataStructure = (clsDriveMesh)poDataStructure;
				
		//This if statement proofs if the compared datastructure does already have an ID =>
		//the ID sepcifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
	
		if(oDataStructure.moDS_ID >= 0 && this.moDS_ID == oDataStructure.moDS_ID){
			/*In case the DataStructureIDs are equal, the return value is the number 
			 * of associated data structures and their number of associations. The idendityMatch number
			 * is not used here as it would distort the result.   
			 */
			//oRetVal = oDataStructure.getNumbAssociations();
			oRetVal = 1.0;
		}
		else if (oDataStructure.moDS_ID >= -1) 
		{
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		
			//Comparision of DMs makes only sense, if they have the same component, source (and partialdrive). Driveaim and -object are comparable
				
			double rDiffQoA = 0 ;
			if(this.moDriveComponent == oDataStructure.moDriveComponent){
				if(this.moPartialDrive == oDataStructure.moPartialDrive){
					if(this.getActualDriveSourceAsENUM() == oDataStructure.getActualDriveSourceAsENUM()){
						oRetVal = getMatchScore(this, oDataStructure);
						// also consider QoA. if the two DMs have the same QoA -> higher matchingfactor
						
						rDiffQoA = (this.mrQuotaOfAffect/oDataStructure.getQuotaOfAffect());
						
						if(rDiffQoA>1){
							rDiffQoA =1;
						}
						oRetVal = ((oRetVal + 1 +rDiffQoA)/3); // drivecomponent (+ partialdrive) (at this stage always "1") have the same weight as driveobject + driveaim
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
	 * @see pa._v38.memorymgmt.datatypes.itfComparable#compareTo(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 * 
	 * how good would this drive's driveobject  would satisfy poDriveCandidate?
	 */

	public double compareToDriveCandidate(clsDriveMesh poDriveCandidate) {
		// TODO (schaat) - Auto-generated method stub
		double oRetVal = 0.0; 
		
				
				if(this.moDriveComponent == poDriveCandidate.moDriveComponent){
					if(this.moPartialDrive == poDriveCandidate.moPartialDrive){
						if(this.getActualDriveSourceAsENUM() == poDriveCandidate.getActualDriveSourceAsENUM()){
							
							
							oRetVal = (this.mrQuotaOfAffect/poDriveCandidate.getQuotaOfAffect());
							
							if(oRetVal>1){
								oRetVal =1;
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
	public ArrayList<clsAssociation> getInternalAssociatedContent() {
		
		return moInternalAssociatedContent;
	}

	/* (non-Javadoc)
	 *
	 * @since Jul 10, 2012 1:21:47 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#setMoAssociatedContent(java.util.ArrayList)
	 */
	@Override
	public void setInternalAssociatedContent(
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
    * @see pa._v38.memorymgmt.datatypes.itfAssociatedDataStructure#getNumbAssociations()
    */
   public String getBE() {
       
       if (this.mrPleasureSumMax >= 0.2)
       {
           moBE = "LARGE";
       }
       else if (this.mrPleasureSumMax >= 0.1)
       {
           moBE = "MEDIUM";
       }
       else if (this.mrPleasureSumMax >= 0.05)
       {
           moBE = "SMALL";
       }
       else
       {
           moBE = "NO";
       }
       return moBE;
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
	public ArrayList<clsAssociation> getExternalAssociatedContent() {
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
	public void setExternalAssociatedContent(
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
	
	 /**
     * Alternative clone for cloning directed graphs
     * 
     * (wendt)
     *
     * @since 01.12.2011 16:29:38
     *
     * @return
     * @throws CloneNotSupportedException
     */
	@Override
	public Object clone() throws CloneNotSupportedException {
        return clone(new HashMap<clsDataStructurePA, clsDataStructurePA>());
    }
	
	
	public Object clone(HashMap<clsDataStructurePA, clsDataStructurePA> poClonedNodeMap) throws CloneNotSupportedException {
	    clsDriveMesh  oClone = null;
	    
        try {
            if(!poClonedNodeMap.containsKey(this)) {
            	oClone = (clsDriveMesh)super.clone();
            	oClone.mrQuotaOfAffect = this.mrQuotaOfAffect;
            	if (moInternalAssociatedContent != null) {
            		oClone.moInternalAssociatedContent = new ArrayList<clsAssociation>(); 
            		
            		for(clsAssociation oAssociation : moInternalAssociatedContent){
            			try { 
        					Object dupl = oAssociation.clone(this, oClone, poClonedNodeMap); 
        					if(dupl!= null) oClone.moInternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
        				} catch (Exception e) {
        					return e;
        				}
            		}
            	}
            	
            	if (moExternalAssociatedContent != null) {
            		oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
            		
            		for(clsAssociation oAssociation : moExternalAssociatedContent){
            			try { 
        					Object dupl = oAssociation.clone(this, oClone, poClonedNodeMap); 
        					if(dupl!= null) oClone.moExternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
        				} catch (Exception e) {
        					return e;
        				}
            		}
            	}
            } else {
                clsLogger.getLog("Cloning").info("Object already in list");
                oClone = (clsDriveMesh) poClonedNodeMap.get(this);
            }
        } catch (CloneNotSupportedException e) {
            throw e;
        }
        
        return oClone;
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 13, 2012 1:27:17 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfExternalAssociatedDataStructure#addExternalAssociation(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void addExternalAssociation(clsAssociation poAssociatedDataStructure) {
		// TODO (schaat) - Auto-generated method stub
		this.moExternalAssociatedContent.add(poAssociatedDataStructure);
	}
	
	/**
	 * This is a function to create a string that identifies the drive mesh, equivalent to bite and nourish. 
	 * Es war mir zu deppart jedes mal den Drive-String zu konstruieren, wenn man es eh holen kann. 
	 *
	 * 
	 * (wendt)
	 *
	 * @since 04.03.2013 10:17:07
	 *
	 * @return
	 */
	public String getDriveIdentifier() {
		//Get the Drive component
		eDriveComponent oDriveComponent = this.getDriveComponent();
		
		//Get partial drive
		ePartialDrive partialDrive = this.getPartialDrive();
		
		//Get the bodily part
		//eOrifice oOrifice = poDM.getActualBodyOrificeAsENUM();
		eOrgan oOrgan = this.getActualDriveSourceAsENUM();
		
		String aim = "";
		if (this.getActualDriveAim()!=null) {
		    aim=this.getActualDriveAim().getContent();
		}
		
		//Create the drive string from Drive component, orifice and organ
		return oDriveComponent.toString() + oOrgan.toString() + partialDrive.toString() + aim;
	}


    /**
     * DOCUMENT (hinterleitner) - insert description
     *
     * @since 11.05.2013 12:16:19
     *
     * @return
     */
    public boolean isNullObject() {
        // TODO (hinterleitner) - Auto-generated method stub
        return false;
    }

    /**
     * DOCUMENT - Utility comparator that only compares:
     *               - DRIVE COMPONENT
     *               - DRIVE SOURCE
     *               - PARTIAL DRIVE
     *
     * @author Kollmann
     * @since 20.01.2015 18:32:00
     *
     * @param clsDriveMesh Drive mesh to compare
     * @return
     */
    public static class StructureComparatorClass implements Comparator<clsDriveMesh> {
        @Override
        public int compare(clsDriveMesh poDM1, clsDriveMesh poDM2) {
            int nDiff = 0;
                    
            if(poDM1 == poDM2){ return 0; }
            
            nDiff += Math.abs(poDM1.getDriveComponent().compareTo(poDM2.getDriveComponent()));
            nDiff += Math.abs(poDM1.getActualDriveSourceAsENUM().compareTo(poDM2.getActualDriveSourceAsENUM()));
            nDiff += Math.abs(poDM1.getPartialDrive().compareTo(poDM2.getPartialDrive()));
            
            return nDiff;
        }
    }
    
    public static StructureComparatorClass StructureComparator = new StructureComparatorClass();
    
    /**
     * DOCUMENT - Goes through a provided list of drive meshes and returns a new list, containing only drive meshes that are equal to the comparison
     *            DM, using the provided comparator (Rem
     *
     * @author Kollmann
     * @since 24.09.2014 11:54:09
     *
     * @param poAssociations
     * @return
     */
    public static List<clsDriveMesh> filterListByDM(List<clsDriveMesh> poMeshes, clsDriveMesh poDM, Comparator<clsDriveMesh> poComparator) {
        List<clsDriveMesh> oDriveMeshes = new ArrayList<clsDriveMesh>();
        
        for(clsDriveMesh oDM : poMeshes) {
            if(poComparator.compare(oDM, poDM) == 0) {
                oDriveMeshes.add(oDM);
            }
        }
        
        return oDriveMeshes;
    }
    /*
     * @since  25.03.2015
     * (Zhukova)
     * set the quota of affect for the drive mesh associated with the current one
     * 
     */
    public boolean changeExpectedQuotaOfAffectOfDrive(String poDriveMeshAim, String poDriveMeshObject,   double poCoefficient, boolean poPairSearch ) {
        
        ArrayList<clsDriveMesh> associatedDriveMeshes = getAssociatedDriveMeshes();
        
        for(clsDriveMesh oTmpDriveMesh : associatedDriveMeshes) {
    // either we are checking the matching of both drive aim and drive object or we are checking mapping just of drive aim
            if(oTmpDriveMesh.getActualDriveAim().getContent() == poDriveMeshAim &&((poPairSearch && oTmpDriveMesh.getActualDriveObject().getContent() == poDriveMeshObject) || !poPairSearch) ) {    
                    // can't be more than 1
                    oTmpDriveMesh.setQuotaOfAffect(Math.min(oTmpDriveMesh.getQuotaOfAffect() +  poCoefficient, 1)); 
                    return true;
             }
          }
        
        return false;
    }
    
    /*
     * Zhukova Olga, 25.03.2015
     * Calculate new drive aim for the current drive mesh and memorize new drive object, drive subject, drive aim 
     */
     public void updateDriveAim(){ 
          
         double nMaxQuotaOfAffect =  0;
         
         String oUpdDriveAim = ""; 
         
          for(clsAssociation oAssociation :  moExternalAssociatedContent) { 
              if(oAssociation.getContentType() == eContentType.ASSOCIATIONPRIDM) {
                 clsDriveMesh oDriveMesh = (clsDriveMesh)oAssociation.getAssociationElementB();
                 if(oDriveMesh.getQuotaOfAffect() > nMaxQuotaOfAffect) { 
                                 
                     nMaxQuotaOfAffect = oDriveMesh.getQuotaOfAffect();
                     oUpdDriveAim = oDriveMesh.getActualDriveAim().getContent();
                  
                }
              }
          }
          
          clsThingPresentationMesh oNewDriveAim = (clsThingPresentationMesh) clsDataStructureGenerator.generateDataStructure(
          eDataType.TPM,
          new clsTriple<eContentType, Object, Object> (eContentType.ACTION, new ArrayList<clsThingPresentation>(), oUpdDriveAim));
          
          try {
              setActualDriveAim(oNewDriveAim, 1.0);
           } 
          catch (Exception e) {
            e.printStackTrace();
        }
     }
     
     /* 
      * Zhukova 25.03.2015
      * return the list of associated drive meshes with the current one from the external associated content*/
     
     private ArrayList<clsDriveMesh> getAssociatedDriveMeshes() {
         ArrayList<clsDriveMesh> oAssociatedDriveMeshList = new ArrayList<clsDriveMesh>();
         
         for(clsAssociation oAssociation :  moExternalAssociatedContent) { 
             if(oAssociation.getContentType() == eContentType.ASSOCIATIONPRIDM) {
                 clsDriveMesh oAssociatedElement = (clsDriveMesh)oAssociation.getAssociationElementB() ;
                 oAssociatedDriveMeshList.add(oAssociatedElement);
             }
         }
         return oAssociatedDriveMeshList; 
         
     }

    /* (non-Javadoc)
     *
     * @since 09.06.2015 16:08:55
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object arg0) {
        // TODO (Kollmann) - Auto-generated method stub
        return super.equals(arg0);
    }

    /* (non-Javadoc)
     *
     * @since 09.06.2015 16:08:55
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        // TODO (Kollmann) - Auto-generated method stub
        return moDS_ID;
    }
}



