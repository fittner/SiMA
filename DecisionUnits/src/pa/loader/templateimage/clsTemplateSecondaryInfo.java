/**
 * clsTemplatePrimaryInfo.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 23.10.2009, 12:12:56
 */
package pa.loader.templateimage;

import java.util.ArrayList;

import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsWordPresentation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 23.10.2009, 12:12:56
 * 
 */
public class clsTemplateSecondaryInfo extends clsSecondaryInformation implements itfSecondaryTemplateCompare {

	public clsCompareOperator moCompareOperator;

	public clsTemplateSecondaryInfo(
			clsWordPresentation poWordPresentation) {
		super(poWordPresentation);
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 23.10.2009, 13:00:29
	 * 
	 * @see pa.loader.templateimage.itfTemplateComparable#compareTemplateWith(pa.datatypes.clsPrimaryInformation, java.util.ArrayList)
	 */
	@Override
	public void compareTemplateWith(clsSecondaryInformation poCurrentSec,
			Integer[] pnMatches) {
		
		if(checkType(poCurrentSec)) {
			if( moCompareOperator.compare(moWP.moContent, poCurrentSec.moWP.moContent) ) {
				pnMatches[0]++;
		
				if(moAffect!=null && moAffect instanceof clsTemplateAffect) {
					clsTemplateAffect oTempAff = (clsTemplateAffect)moAffect;
					if( oTempAff.moCompareOperator.compare(oTempAff.getValue(), poCurrentSec.moAffect.getValue())) {
						pnMatches[0]++;
					}
				}
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 23.10.2009, 13:49:49
	 * 
	 * @see pa.loader.templateimage.itfTemplateComparable#checkType(pa.datatypes.clsPrimaryInformation)
	 */
	@Override
	public boolean checkType(clsSecondaryInformation poCurrentSec) {

		if( (moWP != null && poCurrentSec.moWP != null ) &&
			//&& moWP.moContentType.equals(poCurrentSec.moWP.moContentType.toString())
			moWP.moContentName.equals(poCurrentSec.moWP.moContentName) ) { 
			return true;
		}

		return false;
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 25.10.2009, 00:27:03
	 *
	 * @param poCompletePerception
	 * @return
	 */
	public double compareTo(
		ArrayList<clsSecondaryInformation> poCompletePerception) {

		double oRetVal = 0;
		Integer[] nMatches = new Integer[]{0};
		for(clsSecondaryInformation oSecPerc : poCompletePerception) {
			
			compareTemplateWith(oSecPerc, nMatches);
			oRetVal += getMatchRatio(nMatches[0]);
		}
		
		return oRetVal;
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 25.10.2009, 00:31:51
	 *
	 * @param matchList
	 * @return
	 */
	public double getMatchRatio(int pnMatches) {

		double oRetVal = 0;

		if(getNodeCount() > 0) {
			oRetVal = (double)pnMatches/getNodeCount();
		}
		
		return oRetVal;
	}
	
	@Override
	public int getNodeCount() {
		int nRetVal = 0;
		if(moWP!=null) { nRetVal++; }
		if(moAffect!=null) { nRetVal++; }
		return nRetVal;
	}
	@Override
	public void getNodeCountRecursive(Integer[] poNodeCount){
		poNodeCount[0]+=getNodeCount();
	}
	
}
