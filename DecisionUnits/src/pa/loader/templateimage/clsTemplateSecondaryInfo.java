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
			ArrayList<Boolean> poMatchList) {
		
		if(checkType(poCurrentSec)) {
			poMatchList.add( moCompareOperator.compare(moWP.moContent, poCurrentSec.moWP.moContent) );
		
			if(moAffect!=null && moAffect instanceof clsTemplateAffect) {
				clsTemplateAffect oTempAff = (clsTemplateAffect)moAffect;
				poMatchList.add( oTempAff.moCompareOperator.compare(oTempAff.getValue(), poCurrentSec.moAffect.getValue()));
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
			 moWP.moContentName == poCurrentSec.moWP.moContentName &&
			 moWP.moContentType == poCurrentSec.moWP.moContentType ) {
			return true;
		}

		return false;
	}
	
}
