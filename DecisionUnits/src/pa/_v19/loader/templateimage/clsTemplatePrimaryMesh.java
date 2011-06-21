/**
 * clsTemplatePrimaryMesh.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 23.10.2009, 12:12:24
 */
package pa._v19.loader.templateimage;

import java.util.ArrayList;

import du.enums.eTriState;

import bfg.utils.enums.eOptional;
import pa._v19.datatypes.clsAssociation;
import pa._v19.datatypes.clsPrimaryInformation;
import pa._v19.datatypes.clsPrimaryInformationMesh;
import pa._v19.datatypes.clsThingPresentationSingle;

/**
 * 
 * @author langr
 * 23.10.2009, 12:12:24
 * 
 */
@Deprecated
public class clsTemplatePrimaryMesh extends clsPrimaryInformationMesh implements itfPrimaryTemplateComparable{

	public eOptional meOptional;
	public eBooleanOperator meOperator;
	public eTriState meNegated;
	
	public clsCompareOperator moCompareOperator;
	
	/**
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
					((itfPrimaryTemplateComparable)oAssocTemp.moElementB).compareTemplateWith(oAssocComp.moElementB, poChildMatch);
				}
			}
			verifyNodeOperators(poChildMatch, poMatchList);
		}
	}

	/**
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
	 *
	 * @author langr
	 * 23.10.2009, 13:45:21
	 *
	 * @param poCurrentPrim
	 * @return
	 */
	@Override
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
