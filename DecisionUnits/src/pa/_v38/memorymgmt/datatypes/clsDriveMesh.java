/**
 * clsDriveMesh.java: DecisionUnits - pa._v38.memorymgmt.datatypes
 * 
 * @author zeilinger
 * 23.06.2010, 20:36:25
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.tools.clsTripple;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.06.2010, 20:36:25
 * 
 */
public class clsDriveMesh extends clsHomeostaticRepresentation{
	
	private String moContent = "UNDEFINED";
	private ArrayList<clsAssociation> moAssociatedContent = null; 
	private double mrPleasure = 0.0; 
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
	public clsDriveMesh(clsTripple<Integer, eDataType, String> poDataStructureIdentifier, 
												double prPleasure, double[] poDriveCathegories, 
												ArrayList<clsAssociation> poAssociatedDriveSources,
												String poContent) {
		super(poDataStructureIdentifier);
		setAssociations(poAssociatedDriveSources); 
		setContent(poContent); 
		mrPleasure = prPleasure; 
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
	public ArrayList<clsAssociation> getMoAssociatedContent() {
		return moAssociatedContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @param moAssociatedContent the moAssociatedContent to set
	 */
	public void setMoAssociatedContent(ArrayList<clsAssociation> moAssociatedContent) {
		this.moAssociatedContent = moAssociatedContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @return the mrPleasure
	 */
	public double getMrPleasure() {
		return mrPleasure;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:50:38
	 * 
	 * @param mrPleasure the mrPleasure to set
	 */
	public void setMrPleasure(double mrPleasure) {
		this.mrPleasure = mrPleasure;
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
	private void setAssociations(
			ArrayList<clsAssociation> poAssociatedDriveSources) {
		moAssociatedContent = poAssociatedDriveSources;
	}

	public double getPleasure(){
		return mrPleasure; 
	}
	
	public void setPleasure(double prPleasure){
		mrPleasure = prPleasure; 
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
		ArrayList <clsAssociation> oDataStructureList = new ArrayList<clsAssociation>();
		oDataStructureList.add(poDataStructurePA); 
		
		applyAssociations(oDataStructureList);
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
				oRetVal = oDataStructure.getNumbAssociations();
			}
			else if (oDataStructure.moDS_ID > -1) {return oRetVal;}
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined
		//Drive Mesh content is represented by a list of attribute associations	
				
		if(this.moContent.intern() == oDataStructure.moContent.intern()){
				oRetVal = getMatchScore(oContentListTemplate, oContentListUnknown);
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
	private double getNumbAssociations() {
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
		
	protected void applyAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moAssociatedContent.addAll(poAssociatedDataStructures);  
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsDriveMesh oClone = (clsDriveMesh)super.clone();
        	if (moAssociatedContent != null) {
        		oClone.moAssociatedContent = new ArrayList<clsAssociation>(); 
        		
        		for(clsAssociation oAssociation : moAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone); 
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

	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";  
		oResult += this.moDS_ID + ":";
		oResult += this.moContentType + ":";
		oResult += this.moContent + ":";
			
//		for (clsAssociation oEntry : moAssociatedContent) {
//			oResult += oEntry.toString() + ":"; 
//		}
		oResult += " a: " + mrCathegoryAnal; 
		oResult += " o: " + mrCathegoryOral;
		oResult += " g: " + mrCathegoryGenital;
		oResult += " p: " + mrCathegoryPhalic;
		
		oResult += " pleasure: " + mrPleasure;
		
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
	public double matchCathegories(clsDriveMesh poDMInput) {
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
	private double distance(clsDriveMesh poDMInput) {
		double rResult = 0.0;
		
		double rDiOral = Math.abs(mrCathegoryOral - poDMInput.mrCathegoryOral);
		double rDiAnal = Math.abs(mrCathegoryAnal - poDMInput.mrCathegoryAnal);
		double rDiGenital = Math.abs(mrCathegoryGenital - poDMInput.mrCathegoryGenital);
		double rDiPhallic = Math.abs(mrCathegoryPhalic - poDMInput.mrCathegoryPhalic);
		
		rResult = rDiOral+rDiAnal+rDiGenital+rDiPhallic;
		
		return rResult;
	}
}
