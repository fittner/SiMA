/**
 * CHANGELOG
 *
 * Jun 27, 2012 schaat - File created
 *
 */
package base.datatypes;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;

import java.text.DecimalFormat;

import base.datatypes.helpstructures.clsTriple;

/** 
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jun 27, 2012, 10:47:02 AM
 * 
 */
public class clsAssociationEmotion  extends clsAssociation{

	/**
	 * DOCUMENT (schaat) - insert description 
	 *
	 * @since Jun 27, 2012 10:48:35 AM
	 *
	 * @param poDataStructureIdentifier
	 * @param poAssociationElementA
	 * @param poAssociationElementB
	 */
	public clsAssociationEmotion(
			clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
			clsEmotion poAssociationElementA,
			clsThingPresentationMesh poAssociationElementB) {
		
		super(poDataStructureIdentifier, poAssociationElementA, poAssociationElementB);
		// TODO (schaat) - Auto-generated constructor stub
	}
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 27.06.2012, 10:58:09
	 * 
	 * @see java.lang.Comparable#compareTo(java.lang.Object)
	 */
	@Override
	public double compareTo(clsDataStructurePA o) {
		// TODO (schaat) - Auto-generated method stub
		return 0;
	}
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 27.06.2012, 21:11:25
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#getLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public clsDataStructurePA getLeafElement() {
		return moAssociationElementA; 
	}
	
	@Override
	public clsDataStructurePA getRootElement() {
		return moAssociationElementB; 
	}
	
	/* (non-Javadoc)
	 *
	 * @since 27.06.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setLeafElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setLeafElement(clsDataStructurePA poDS) {
		moAssociationElementA = poDS;

		
	}

	/* (non-Javadoc)
	 *
	 * @since 27.06.2012 20:02:03
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsAssociation#setRootElement(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void setRootElement(clsDataStructurePA poDS) {
		moAssociationElementB = poDS;
		
	}
	
	public clsEmotion getEmotion(){
		//Element A is the emotion 
		return (clsEmotion)moAssociationElementA; 
	}
	
   @Override
    public String toString(){
        String oResult = "::"+this.moDataStructureType+"::";  
        oResult += this.moDS_ID + ":" + this.moContentType + "|";
        
        oResult += associationToString("elementA:", moAssociationElementA);
        if(moAssociationElementA instanceof clsEmotion)
        {
            ((clsEmotion)moAssociationElementA).getSourcePleasure();
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            
            oResult += " int: " + df.format(((clsEmotion)moAssociationElementA).getEmotionIntensity());
            oResult += " U: " + df.format(((clsEmotion)moAssociationElementA).getSourceUnpleasure());
            oResult += " A: "+ df.format(((clsEmotion)moAssociationElementA).getSourceAggr());
            oResult += " L: "+ df.format(((clsEmotion)moAssociationElementA).getSourceLibid());
            oResult += " P: "+ df.format(((clsEmotion)moAssociationElementA).getSourcePleasure());
        }
        oResult += ":"; 
        oResult += associationToString("elementB:", moAssociationElementB);
        if(moAssociationElementB instanceof clsEmotion)
        {
            ((clsEmotion)moAssociationElementB).getSourcePleasure();
            DecimalFormat df = new DecimalFormat();
            df.setMaximumFractionDigits(2);
            
            oResult += " int: " + df.format(((clsEmotion)moAssociationElementB).getEmotionIntensity());
            oResult += " U: " + df.format(((clsEmotion)moAssociationElementB).getSourceUnpleasure());
            oResult += " A: "+ df.format(((clsEmotion)moAssociationElementB).getSourceAggr());
            oResult += " L: "+ df.format(((clsEmotion)moAssociationElementB).getSourceLibid());
            oResult += " P: "+ df.format(((clsEmotion)moAssociationElementB).getSourcePleasure());
        }
        
        return oResult; 
    }
}



