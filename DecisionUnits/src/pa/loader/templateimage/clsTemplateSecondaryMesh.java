/**
 * clsTemplatePrimaryMesh.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 23.10.2009, 12:12:24
 */
package pa.loader.templateimage;

import java.util.ArrayList;

import du.enums.eTriState;

import bfg.utils.enums.eOptional;
import pa.datatypes.clsAssociation;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.datatypes.clsWordPresentation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 23.10.2009, 12:12:24
 * 
 */
public class clsTemplateSecondaryMesh extends clsSecondaryInformationMesh implements itfSecondaryTemplateCompare{

	public eOptional meOptional;
	public eBooleanOperator meOperator;
	public eTriState meNegated;
	
	public clsCompareOperator moCompareOperator;
	
	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 23.10.2009, 12:13:04
	 *
	 * @param poWordPresentation
	 */
	public clsTemplateSecondaryMesh(
			clsWordPresentation poWordPresentation) {
		super(poWordPresentation);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 23.10.2009, 13:17:35
	 * 
	 * @see pa.loader.templateimage.itfTemplateComparable#compareTemplateWith(pa.datatypes.clsPrimaryInformation, java.util.ArrayList)
	 */
	@Override
	public void compareTemplateWith(clsSecondaryInformation poCurrentSec,
			Integer[] pnMatches) {
		
		//check this TP
		if( checkType(poCurrentSec) ) {
			if( moCompareOperator.compare(moWP.moContent, poCurrentSec.moWP.moContent) ) {
				pnMatches[0]++;
			
		
				//check this Affect
				if(moAffect!=null && poCurrentSec.moAffect != null && moAffect instanceof clsTemplateAffect) {
					clsTemplateAffect oTempAff = (clsTemplateAffect)moAffect;
					if( oTempAff.moCompareOperator.compare(oTempAff.getValue(), poCurrentSec.moAffect.getValue())) {
						pnMatches[0]++;
					}
				}
		
				//check match of childs
	
				Integer[] nMatches = new Integer[]{0};
	
				for(clsAssociation<clsSecondaryInformation> oAssocTemp : moAssociations) {
					
					for( clsAssociation<clsSecondaryInformation> oAssocComp : ((clsSecondaryInformationMesh)poCurrentSec).moAssociations) {
						((itfSecondaryTemplateCompare)oAssocTemp.moElementB).compareTemplateWith(oAssocComp.moElementB, nMatches);
					}
				}
				if(moAssociations.size() > 0 && verifyNodeOperatorsOnChilds(nMatches[0])) {
					pnMatches[0] += nMatches[0]; //normaly only ONE value should be added - indicating wether this node has a match or not.
				}
			}
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
	private boolean verifyNodeOperatorsOnChilds(int pnMatches) {
		boolean match = false;		
		int nNodeCount = moAssociations.size();
		
		if(meOperator == eBooleanOperator.OR) { //at least one match
			if( pnMatches > 0 ) {
				match = true;
			}
		}
		else if(meOperator == eBooleanOperator.AND) {	//everything has to match
			if( pnMatches >= nNodeCount) {
				match = true;
			}
		}
		
		if(meNegated == eTriState.TRUE) {
			match = !match;
		}
		return match;
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
	public boolean checkType(clsSecondaryInformation poCurrentSec) {


		if( poCurrentSec instanceof clsSecondaryInformationMesh &&
			(moWP != null && poCurrentSec.moWP != null ) && 
			 moWP.moContentName.equals(poCurrentSec.moWP.moContentName) ) {
			//&& moWP.moContentType.equals(poCurrentSec.moWP.moContentType.toString()) ) {
			return true;
		}
		
		return false;
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 25.10.2009, 00:34:48
	 *
	 * @param poCompletePerception
	 * @return
	 */
	public double compareTo( ArrayList<clsSecondaryInformation> poCompletePerception) {

		Integer[] nMatches = new Integer[]{0};
		for(clsAssociation<clsSecondaryInformation> oAssoc : moAssociations) {

			if( oAssoc.moElementB instanceof clsTemplateSecondaryMesh ) {

				for( clsSecondaryInformation oSec : poCompletePerception ) {
					
					((clsTemplateSecondaryMesh)oAssoc.moElementB).compareTemplateWith(oSec, nMatches);

				}
			}
			if( oAssoc.moElementB instanceof clsTemplateSecondaryInfo ) {

				for( clsSecondaryInformation oSec : poCompletePerception ) {

					((clsTemplateSecondaryInfo)oAssoc.moElementB).compareTemplateWith(oSec, nMatches);					

				}
			}
	
		}
		return getMatchRatio(nMatches[0], this.meOperator);
	}

	public double getMatchRatio(int pnMatches, eBooleanOperator peOperator) {

		double oRetVal = 0.0;
		Integer[] oNodeCount = new Integer[]{-1};
		getNodeCountRecursive(oNodeCount);
		
		//add comapre operator logic here:
//		if(peOperator == eBooleanOperator.OR) { //at least one match
//			if( pnMatches > 0 && oNodeCount[0] > 0) {
//				oRetVal = (double)pnMatches/oNodeCount[0];
//			}
//		}
//		else if(peOperator == eBooleanOperator.AND) {	//everything has to match
//			if( pnMatches >= oNodeCount[0] ) {
//				oRetVal=1;
//			}
//		}


		if( oNodeCount[0] > 0) {
			oRetVal = (double)pnMatches/oNodeCount[0];
		}
		
		return oRetVal;
	}
	
	public int getNodeCount() {
		int nRetVal = 0;
		if(moWP!=null) { nRetVal++; }
		if(moAffect!=null) { nRetVal++; }
		nRetVal+=moAssociations.size();
		return nRetVal;
	}
	
	public void getNodeCountRecursive(Integer[] poNodeCount){
		if(moWP!=null) { poNodeCount[0]++; }
		if(moAffect!=null) { poNodeCount[0]++; }
		for(clsAssociation<clsSecondaryInformation> oSec : moAssociations) {
			if( oSec.moElementB instanceof clsTemplateSecondaryMesh ) {
				((clsTemplateSecondaryMesh)oSec.moElementB).getNodeCountRecursive(poNodeCount);
			}
			if( oSec.moElementB instanceof clsTemplateSecondaryInfo ) {
				((clsTemplateSecondaryInfo)oSec.moElementB).getNodeCountRecursive(poNodeCount);
			}
		}
	}
	
}
