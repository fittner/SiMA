/**
 * clsDriveMesh.java: DecisionUnits - pa._v38.memorymgmt.datatypes
 * 
 * @author zeilinger
 * 23.06.2010, 20:36:25
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - In ARSi10, DMs are only associated with TPMs that represent objects as their impact on the individual’s homeostatic state.
 * 					Class clsDriveMesh includes an array of double values that defines the categorizations of homeostatic demands (anal, oral, 
 * 					genital, phallic) as well as the level of “pleasure” representing the affect in ARSi10.
 * 
 * moContent (String):	
 * poDataStructureIdentifier (clsTripple):	Holds the data structure Id, the data type, as well as the content type. It is passed on to the super class
 * prPleasure (double):	Temporal change of a drive demand
 * poDriveCathegories (double []):	mrCathegoryAnal, mrCathegoryOral, mrCathegoryGenital, mrCathegoryPhalic
 * 
 * @author zeilinger
 * 23.06.2010, 20:36:25
 * 
 */
public class clsDriveMeshOLD extends clsHomeostaticRepresentation implements itfInternalAssociatedDataStructure{
	
	public boolean mbSexualDM = false;
	private String moContent = "UNDEFINED";
	private ArrayList<clsAssociation> moAssociatedContent = null; 
	private double mrQuotaOfAffect = 0.0; 
	private double mrCathegoryAnal = 0.0; 
	private double mrCathegoryGenital = 0.0;
	private double mrCathegoryOral = 0.0;
	private double mrCathegoryPhalic = 0.0;

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 23.06.2010, 20:37:07
	 *
	 * @param poHomeostaticSource
	 * @param poAssociationID
	 * @param peAssociationType
	 */	
	public clsDriveMeshOLD(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, 
												double prPleasure, double[] poDriveCathegories, 
												ArrayList<clsAssociation> poAssociatedDriveSources,
												String poContent) {
		super(poDataStructureIdentifier);
		moAssociatedContent =poAssociatedDriveSources; 
		setContent(poContent); 
		mrQuotaOfAffect = prPleasure; 
		mrCathegoryAnal = poDriveCathegories[0]; 
		mrCathegoryOral = poDriveCathegories[1]; 
		mrCathegoryGenital = poDriveCathegories[2];
		mrCathegoryPhalic = poDriveCathegories[3]; 
	}
	
	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @return the moContent
	 */
	public String getMoContent() {
		return moContent;
	}

	public clsThingPresentationMesh getActualDriveObject(){
		return null;
	}
	
	public clsThingPresentationMesh getActualDriveAim(){
		return null;
	}
	
	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @param moContent the moContent to set
	 */
	public void setMoContent(String moContent) {
		this.moContent = moContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @return the moAssociatedContent
	 */
	@Override
	public ArrayList<clsAssociation> getMoInternalAssociatedContent() {
		return moAssociatedContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @param moAssociatedContent the moAssociatedContent to set
	 */
	@Override
	public void setMoInternalAssociatedContent(ArrayList<clsAssociation> moAssociatedContent) {
		this.moAssociatedContent = moAssociatedContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @return the mrPleasure
	 */
	public double getMrQuotaOfAffect() {
		return mrQuotaOfAffect;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @param mrPleasure the mrPleasure to set
	 */
	public void setMrQuotaOfAffect(double mrPleasure) {
		this.mrQuotaOfAffect = mrPleasure;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @return the mrCathegoryAnal
	 */
	public double getMrCathegoryAnal() {
		return mrCathegoryAnal;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @param mrCathegoryAnal the mrCathegoryAnal to set
	 */
	public void setMrCathegoryAnal(double mrCathegoryAnal) {
		this.mrCathegoryAnal = mrCathegoryAnal;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @return the mrCathegoryGenital
	 */
	public double getMrCathegoryGenital() {
		return mrCathegoryGenital;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @param mrCathegoryGenital the mrCathegoryGenital to set
	 */
	public void setMrCathegoryGenital(double mrCathegoryGenital) {
		this.mrCathegoryGenital = mrCathegoryGenital;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @return the mrCathegoryOral
	 */
	public double getMrCathegoryOral() {
		return mrCathegoryOral;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @param mrCathegoryOral the mrCathegoryOral to set
	 */
	public void setMrCathegoryOral(double mrCathegoryOral) {
		this.mrCathegoryOral = mrCathegoryOral;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @return the mrCathegoryPhalic
	 */
	public double getMrCathegoryPhalic() {
		return mrCathegoryPhalic;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @param mrCathegoryPhalic the mrCathegoryPhalic to set
	 */
	public void setMrCathegoryPhalic(double mrCathegoryPhalic) {
		this.mrCathegoryPhalic = mrCathegoryPhalic;
	}


	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:15:00
	 *
	 * @param poContent
	 */
	private void setContent(String poContent) {
		if(poContent!=null){
			moContent = poContent; 
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:14:59
	 *
	 * @param poAssociatedDriveSources
	 */
	
	/*public void setAssociations(
			ArrayList<clsAssociation> poAssociatedDriveSources) {
		moAssociatedContent = poAssociatedDriveSources;
	}*/

	public double getPleasure(){
		return mrQuotaOfAffect; 
	}
	

	/**
	 * TODO CM maybe refactor the naming here. because it is actually quota of affect not pleasure
	 *
	 * @since 14.07.2011 11:42:35
	 *
	 * @param prPleasure
	 */
	public void setPleasure(double prPleasure){
		mrQuotaOfAffect = prPleasure; 
	}
	
	public double getOral() {
		return mrCathegoryOral;
	}
	
	public void setCategories(double prOral, double prAnal, double prGential, double prPhallic) {
		mrCathegoryOral = prOral;
		mrCathegoryAnal = prAnal;
		mrCathegoryGenital = prGential;
		mrCathegoryPhalic = prPhallic;
	}
	
	public void setOral(double prOral){
		mrCathegoryOral = prOral; 
	}
	
	public double getAnal() {
		return mrCathegoryAnal;
	}
	
	public void setAnal(double prAnal){
		mrCathegoryAnal = prAnal; 
	}
	
	public double getGenital() {
		return mrCathegoryGenital;
	}
	
	public void setGenital(double prGenital){
		mrCathegoryGenital = prGenital; 
	}
	
	public double getPhallic() {
		return mrCathegoryPhalic;
	}
	
	public void setPhallic(double prPhallic){
		mrCathegoryPhalic = prPhallic; 
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 23.06.2010, 22:00:22
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure#assignDataStructure(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		//TODO AW: Remove function
		
		//ArrayList <clsAssociation> oDataStructureList = new ArrayList<clsAssociation>();
		//oDataStructureList.add(poDataStructurePA); 
		
		//applyAssociations(oDataStructureList);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 13.07.2010, 20:58:39
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		double oRetVal = 0.0; 
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}

		clsDriveMeshOLD oDataStructure = (clsDriveMeshOLD)poDataStructure;
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
			else if (oDataStructure.moDS_ID > -1) {return oRetVal;}
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		//Drive Mesh content is represented by a list of attribute associations	
				
		if(this.moContent.intern() == oDataStructure.moContent.intern()){
				//oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
				oRetVal = 1.0;
		}
	
		return oRetVal; 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:38:20
	 *
	 * @return
	 */
	@Override
	public double getNumbInternalAssociations() {
		return moAssociatedContent.size();
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.05.2010, 14:40:45
	 *
	 * @param poAssociatedWordPresentations
	 */
	@Override
	public void addInternalAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moAssociatedContent.addAll(poAssociatedDataStructures);  
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsDriveMeshOLD oClone = (clsDriveMeshOLD)super.clone();
        	if (moAssociatedContent != null) {
        		oClone.moAssociatedContent = new ArrayList<clsAssociation>(); 
        		
        		for(clsAssociation oAssociation : moAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone, new ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>>()); 
    					oClone.moAssociatedContent.add((clsAssociation)dupl); // unchecked warning
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
	
	public Object clone(ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>> poClonedNodeList) throws CloneNotSupportedException {
		clsDriveMeshOLD oClone = null;
		
		try {
        	oClone = (clsDriveMeshOLD)super.clone();
        	poClonedNodeList.add(new clsPair<clsDataStructurePA, clsDataStructurePA>(this, oClone));
        	if (moAssociatedContent != null) {
        		oClone.moAssociatedContent = new ArrayList<clsAssociation>(); 
        		
        		for(clsAssociation oAssociation : moAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone, poClonedNodeList); 
    					oClone.moAssociatedContent.add((clsAssociation)dupl); // unchecked warning
    				} catch (Exception e) {
    					return e;
    				}
        		}
        	}
        	
        } catch (CloneNotSupportedException e) {
           return e;
        }
		
		return oClone;
	}
	

	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";  
		oResult += this.moDS_ID + ":";
		oResult += this.moContentType + ":";
		oResult += this.moContent + ":";
			
		for (clsAssociation oEntry : moAssociatedContent) {
			oResult += oEntry.toString() + ":"; 
		}
		oResult += " a: " + mrCathegoryAnal; 
		oResult += " o: " + mrCathegoryOral;
		oResult += " g: " + mrCathegoryGenital;
		oResult += " p: " + mrCathegoryPhalic;
		
		oResult += " QuotaOfAffect: " + mrQuotaOfAffect;
		
		
		return oResult; 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 21:10:20
	 *
	 * @param oDMInput
	 * @return
	 */
	public double matchCathegories(clsDriveMeshOLD poDMInput) {
		double rRetVal = 1.0-(distance(poDMInput) / 4.0);
		
		return rRetVal;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 21:11:10
	 *
	 * @param poDMInput
	 * @return
	 */
	private double distance(clsDriveMeshOLD poDMInput) {
		double rResult = 0.0;
		
		double rDiOral = Math.abs(mrCathegoryOral - poDMInput.mrCathegoryOral);
		double rDiAnal = Math.abs(mrCathegoryAnal - poDMInput.mrCathegoryAnal);
		double rDiGenital = Math.abs(mrCathegoryGenital - poDMInput.mrCathegoryGenital);
		double rDiPhallic = Math.abs(mrCathegoryPhalic - poDMInput.mrCathegoryPhalic);
		
		rResult = rDiOral+rDiAnal+rDiGenital+rDiPhallic;
		
		return rResult;
	}
	
	public clsThingPresentationMesh getBestTPM() {
		
		clsThingPresentationMesh oBestTPM= null;
		double rCurrentWeight = 0;
		double rMaxWeight = 0;
		
		// temporary solution
		for (clsAssociation oEntry : moAssociatedContent) {
			if (oEntry instanceof clsAssociationDriveMesh) {
				rCurrentWeight = oEntry.getMrWeight();
				if(rCurrentWeight > rMaxWeight) {
					rMaxWeight = rCurrentWeight;	
					if (oBestTPM != null) {
						oBestTPM = (clsThingPresentationMesh)oEntry.getMoAssociationElementB();
					}
					else {
						oBestTPM = (clsThingPresentationMesh) clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ENTITY,  new ArrayList<clsThingPresentation>(), "NULL"));
					}
				}
			}
			else {
				oBestTPM = (clsThingPresentationMesh) clsDataStructureGenerator.generateTPM(new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ENTITY,  new ArrayList<clsThingPresentation>(), "NULL"));
			}
					
		}
		
		return oBestTPM;
	}
	
	
}
