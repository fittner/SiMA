/**
 * CHANGELOG
 *
 * Jun 26, 2012 schaat - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.memorymgmt.enums.clsEmotionType;
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
	
	private clsEmotionType moContent = null;
	private ArrayList<clsAssociation> moExternalAssociatedContent = null; 
	private double mrEmotionIntensity = 0.0; 
	
	/* Maybe later
	 * private double mrSystemPleasure = 0.0; 
	private double mrSystemUnpleasure = 0.0;
	private double mrSystemLibidUnpleasure = 0.0;
	private double mrSystemAggrUnpleasure = 0.0;
	*/
	public clsEmotion(clsTriple<Integer, eDataType, String> poDataStructureIdentifier, double prEmotionIntensity, clsEmotionType poContent) {
		super(poDataStructureIdentifier); 
		mrEmotionIntensity = prEmotionIntensity;
		moContent = poContent;
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
	public clsEmotionType getMoContent() {
		return moContent;
	}

	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @param moContent the moContent to set
	 */
	public void setMoContent(clsEmotionType moContent) {
		this.moContent = moContent;
	}
	
	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @return the mrPleasure
	 */
	public double getMrEmotionIntensity() {
		return mrEmotionIntensity;
	}

	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @param mrPleasure the mrPleasure to set
	 */
	public void setMrEmotionIntensity(double mrEmotionIntensity) {
		this.mrEmotionIntensity = mrEmotionIntensity;
	}

	
	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";  
		oResult += this.moDS_ID + ":";
		oResult += this.moContentType + ":";
		oResult += this.moContent + ":";
			
		for (clsAssociation oEntry : moExternalAssociatedContent) {
			oResult += oEntry.toString() + ":"; 
		}
		/*oResult += " unpl: " + mrSystemUnpleasure; 
		oResult += " pl: " + mrSystemPleasure;
		oResult += " libid: " + mrSystemLibidUnpleasure;
		oResult += " aggr: " + mrSystemAggrUnpleasure;
		*/
		oResult += " intensity: " + mrEmotionIntensity;
		
		
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
					oResult +=((clsThingPresentationMesh)((clsAssociation)oElement1).moAssociationElementB).getNumbAssociations(); 
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
}
