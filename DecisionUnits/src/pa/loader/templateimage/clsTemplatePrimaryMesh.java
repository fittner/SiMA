/**
 * clsTemplatePrimaryMesh.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 23.10.2009, 12:12:24
 */
package pa.loader.templateimage;

import java.util.ArrayList;

import enums.eTriState;
import bfg.utils.enums.eOptional;
import pa.datatypes.clsAssociation;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.datatypes.clsThingPresentationSingle;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 23.10.2009, 12:12:24
 * 
 */
public class clsTemplatePrimaryMesh extends clsPrimaryInformationMesh implements itfTemplateComparable{

	public eOptional meOptional;
	public enumBooleanOperator meOperator;
	public eTriState meNegated;
	
	public clsCompareOperator moCompareOperator;
	
	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 23.10.2009, 12:13:04
	 *
	 * @param poThingPresentationSingle
	 */
	public clsTemplatePrimaryMesh(
			clsThingPresentationSingle poThingPresentationSingle) {
		super(poThingPresentationSingle);
		
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 23.10.2009, 13:17:35
	 * 
	 * @see pa.loader.templateimage.itfTemplateComparable#compareTemplateWith(pa.datatypes.clsPrimaryInformation, java.util.ArrayList)
	 */
	@Override
	public void compareTemplateWith(clsPrimaryInformation poCurrentPrim,
			ArrayList<Boolean> poMatchList) {

		//check this TP
		if( checkType(poCurrentPrim) ) {
			poMatchList.add( moCompareOperator.compare(moTP.moContent, poCurrentPrim.moTP.moContent) );
		
			//check this Affect
			if(moAffect!=null && moAffect instanceof clsTemplateAffect) {
				clsTemplateAffect oTempAff = (clsTemplateAffect)moAffect;
				poMatchList.add( oTempAff.moCompareOperator.compare(oTempAff.getValue(), poCurrentPrim.moAffect.getValue()));
			}
	
			//check match of childs
			ArrayList<Boolean> poChildMatch = new ArrayList<Boolean>();
			for(clsAssociation<clsPrimaryInformation> oAssocTemp : moAssociations) {
				
				for( clsAssociation<clsPrimaryInformation> oAssocComp : ((clsTemplatePrimaryMesh)poCurrentPrim).moAssociations) {
					((itfTemplateComparable)oAssocTemp.moElementB).compareTemplateWith(oAssocComp.moElementB, poChildMatch);
				}
			}
			verifyNodeOperators(poChildMatch, poMatchList);
		}
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 23.10.2009, 14:12:45
	 *
	 * @param poChildMatch
	 * @return
	 */
	private void verifyNodeOperators(ArrayList<Boolean> poChildMatch, ArrayList<Boolean> poMatchList) {
		boolean match = false;
		int nTrueCount = 0;
		int nFalseCount = 0;
		
		for(Boolean oVal : poChildMatch) {
			if(oVal) {
				nTrueCount++;
			}
			else {
				nFalseCount++;
			}
		}
		
		
		if(meOptional == eOptional.OPTIONAL) { //at least one match
			if( nTrueCount > 0 ) {
				match = true;
			}
		}
		else if(meOptional == eOptional.MANDATORY) {	//everything has to match
			if( nFalseCount == 0 && nTrueCount > 0) {
				match = true;
			}
		}
		
		if(meNegated == eTriState.TRUE) {
			match = !match;
		}
		poMatchList.add(match);
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 23.10.2009, 13:45:21
	 *
	 * @param poCurrentPrim
	 * @return
	 */
	public boolean checkType(clsPrimaryInformation poCurrentPrim) {


		if( poCurrentPrim instanceof clsTemplatePrimaryMesh &&
			(moTP != null && poCurrentPrim.moTP != null ) && 
			 moTP.meContentName == poCurrentPrim.moTP.meContentName &&
			 moTP.meContentType == poCurrentPrim.moTP.meContentType ) {
			return true;
		}
		
		return false;
	}

}
