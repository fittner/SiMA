/**
 * CHANGELOG
 *
 * Jun 26, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jun 26, 2012, 12:21:05 PM
 * 
 */
public class clsEmotion extends clsPrimaryDataStructure implements itfExternalAssociatedDataStructure{
	
	private eEmotionType moContent = null;
	private ArrayList<clsAssociation> moExternalAssociatedContent = null; 
	private double mrEmotionIntensity = 0.0; 
	
	// save the values of those components that the emotion is based on (dependent on the emotion)
	private double mrSourcePleasure = 0.0; 
	private double mrSourceUnpleasure = 0.0;
	private double mrSourceLibid = 0.0;
	private double mrSourceAggr = 0.0;
	
	public clsEmotion(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, double prEmotionIntensity, eEmotionType poContent, 
			double prSourcePleasure, double prSourceUnpleasure, double prSourceLibid, double prSourceAggr) {
		super(poDataStructureIdentifier); 
		mrEmotionIntensity = prEmotionIntensity;
		moContent = poContent;
		mrSourcePleasure = prSourcePleasure; 
		mrSourceUnpleasure = prSourceUnpleasure;
		mrSourceLibid = prSourceLibid;
		mrSourceAggr = prSourceAggr ;
	} 

	/* (non-Javadoc)
	 *
	 * @since Jun 27, 2012 10:10:01 AM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfComparable#compareTo(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		// TODO (schaat) - Auto-generated method stub
		return 0;
	}
	
	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @return the moContent
	 */
	public eEmotionType getMoContent() {
		return moContent;
	}

	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @param moContent the moContent to set
	 */
	public void setMoContent(eEmotionType moContent) {
		this.moContent = moContent;
	}
	
	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @return the mrEmotionIntensity
	 */
	public double getMrEmotionIntensity() {
		return mrEmotionIntensity;
	}

	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @param mrEmotionIntensity the mrEmotionIntensity to set
	 */
	public void setMrEmotionIntensity(double mrEmotionIntensity) {
		this.mrEmotionIntensity = mrEmotionIntensity;
	}
	
	/**
	 * @author schaat
	 * 5.07.2012, 00:50:38
	 * 
	 * @return the mrSourcePleasure
	 */
	public double getMrSourcePleasure() {
		return mrSourcePleasure;
	}
	
	public double getMrSourceUnpleasure() {
		return mrSourceUnpleasure;
	}
	
	public double getMrSourceLibid() {
		return mrSourceLibid;
	}
	
	public double getMrSourceAggr() {
		return mrSourceAggr;
	}
	
	

	
	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";
		oResult += this.moDS_ID + ":";
		try{
		oResult += this.moContentType + ":";
		oResult += this.moContent + ":";
		
		if(moExternalAssociatedContent != null)
		{
			for (clsAssociation oEntry : moExternalAssociatedContent) {
				oResult += oEntry.toString() + ":"; 
			}
		}
			
		
		oResult += " intensity: " + mrEmotionIntensity;
		}
		catch(Exception e){
			System.out.printf(e + "\n" + e.getStackTrace().toString());
		}
		
		return oResult; 
	}
	
	
	public Object clone(ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>> poClonedNodeList) throws CloneNotSupportedException {
		clsEmotion oClone = null;
		
		try {
        	oClone = (clsEmotion)super.clone();
        	poClonedNodeList.add(new clsPair<clsDataStructurePA, clsDataStructurePA>(this, oClone));
        	if (moExternalAssociatedContent != null) {
        		oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
        		
        		for(clsAssociation oAssociation : moExternalAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone, poClonedNodeList); 
    					oClone.moExternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
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
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 26.06.2012, 16:12:00
	 *
	 * @return
	 */
	@Override
	public double getNumbExternalAssociations() {
		double oResult = 0.0;
			for(clsDataStructurePA oElement1 : moExternalAssociatedContent){
				if(((clsAssociation)oElement1).moAssociationElementB.moDataStructureType == eDataType.TPM){
					oResult +=((clsThingPresentationMesh)((clsAssociation)oElement1).moAssociationElementB).getNumbInternalAssociations(); 
				}
				else {
					oResult += 1.0; 
				}
			}
		return oResult;
	}
	
	/**
	 * @author schaat
	 * 26.06.2012, 08:57:27
	 * 
	 * @return the moAssociatedContent
	 */
	@Override
	public ArrayList<clsAssociation> getExternalMoAssociatedContent() {
		return moExternalAssociatedContent;
	}
	
	/**
	 * Set external associated content
	 * 
	 * (schaat)
	 *
	 * @since 26.06.2012 11:16:52
	 *
	 * @param poExternalAssociatedContent
	 */
	@Override
	public void setMoExternalAssociatedContent(ArrayList<clsAssociation> poExternalAssociatedContent) {
		this.moExternalAssociatedContent = poExternalAssociatedContent;
	}
	
	/**
	 * Add external associations
	 * 
	 * (schaat)
	 *
	 * @since 26.06.2012 11:21:45
	 *
	 * @param poAssociatedDataStructures
	 */
	@Override
	public void addExternalAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moExternalAssociatedContent.addAll(poAssociatedDataStructures);  
	}
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 05.07.2012, 17:46:07
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsDataStructurePA#assignDataStructure(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		ArrayList <clsAssociation> oDataStructureList = new ArrayList<clsAssociation>();
		oDataStructureList.add(poDataStructurePA); 
		
		addExternalAssociations(oDataStructureList);
	}
}
