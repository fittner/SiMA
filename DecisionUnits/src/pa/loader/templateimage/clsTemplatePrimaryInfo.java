/**
 * clsTemplatePrimaryInfo.java: DecisionUnits - pa.loader.templateimage
 * 
 * @author langr
 * 23.10.2009, 12:12:56
 */
package pa.loader.templateimage;

import java.util.ArrayList;

import pa.datatypes.clsPrimaryInformation;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 23.10.2009, 12:12:56
 * 
 */
public class clsTemplatePrimaryInfo extends clsPrimaryInformation implements itfTemplateComparable {

	public clsCompareOperator moCompareOperator;

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 23.10.2009, 13:00:29
	 * 
	 * @see pa.loader.templateimage.itfTemplateComparable#compareTemplateWith(pa.datatypes.clsPrimaryInformation, java.util.ArrayList)
	 */
	@Override
	public void compareTemplateWith(clsPrimaryInformation poCurrentPrim,
			ArrayList<Boolean> poMatchList) {
		
		if(checkType(poCurrentPrim)) {
			poMatchList.add( moCompareOperator.compare(moTP.moContent, poCurrentPrim.moTP.moContent) );
		
			if(moAffect!=null && moAffect instanceof clsTemplateAffect) {
				clsTemplateAffect oTempAff = (clsTemplateAffect)moAffect;
				poMatchList.add( oTempAff.moCompareOperator.compare(oTempAff.getValue(), poCurrentPrim.moAffect.getValue()));
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
	public boolean checkType(clsPrimaryInformation poCurrentPrim) {

		if( (moTP != null && poCurrentPrim.moTP != null ) &&
			 moTP.meContentName == poCurrentPrim.moTP.meContentName &&
			 moTP.meContentType == poCurrentPrim.moTP.meContentType ) {
			return true;
		}

		return false;
	}
	
}
