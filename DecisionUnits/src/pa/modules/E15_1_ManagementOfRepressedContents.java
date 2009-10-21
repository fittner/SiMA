/**
 * E15_1.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import bfg.tools.clsMutableDouble;

import config.clsBWProperties;
import enums.eEntityType;
import enums.pa.eContext;
import pa.datatypes.clsDriveContentCategories;
import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I2_5;
import pa.interfaces.I2_6;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 * 
 */
public class E15_1_ManagementOfRepressedContents extends clsModuleBase implements I2_5 {

	public ArrayList<clsPrimaryInformation> moEnvironmentalTP_Input;
	public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moAttachedRepressed_Output;
	private double mrContextSensitivity = 0.8;
	
	public static String P_CONTEXT_SENSTITIVITY = "CONTEXT_SENSITIVITY"; 
	
	
	public E15_1_ManagementOfRepressedContents(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);	
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_CONTEXT_SENSTITIVITY, 0.8);	
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		mrContextSensitivity = poProp.getPropertyDouble(pre+P_CONTEXT_SENSTITIVITY);
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:02
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP) {
		moEnvironmentalTP_Input = poEnvironmentalTP;//(ArrayList<clsPrimaryInformation>)deepCopy( poEnvironmentalTP );
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		
		cathegorize( moEnvironmentalTP_Input );
		moAttachedRepressed_Output = matchWithRepressedContent(moEnvironmentalTP_Input);
	}

	/**
	 * returns the corresponding driveContentCategories in the current context
	 *
	 * @author langr
	 * 17.10.2009, 18:52:32
	 *
	 * @param moEnvironmentalTP_Input2
	 * @return
	 */
	private void cathegorize(
			ArrayList<clsPrimaryInformation> poEnvironmentalTP) {

		HashMap<eEntityType, clsPrimaryInformation> oSemanticWeb = this.moEnclosingContainer.moMemory.moObjectSemanticsStorage.moObjectSemantics;
		
		//get current contexts and attach drvContCat to TP of incoming PrimInfo
		HashMap<clsPrimaryInformation, clsMutableDouble> oContextResult = moEnclosingContainer.moMemory.moCurrentContextStorage.getContextRatiosPrim(mrContextSensitivity); 
		for(clsPrimaryInformation oInfo : poEnvironmentalTP) {
			for( Map.Entry<clsPrimaryInformation, clsMutableDouble> oContextPrim : oContextResult.entrySet() ) {
				eContext oContext = (eContext)oContextPrim.getKey().moTP.moContent;
				if( oSemanticWeb.containsKey(oInfo.moTP.moContent) ) {
					clsDriveContentCategories oCathegory = new clsDriveContentCategories( oSemanticWeb.get(oInfo.moTP.moContent).moTP.moDriveContentCategory.get(oContext) );
					oCathegory.adaptToContextRatio(oContextPrim.getValue().doubleValue());	//lower the category-ratio according to the match of the context
					oInfo.moTP.moDriveContentCategory.put(oContext, oCathegory);
				}
			}
		}
		return;
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 17.10.2009, 18:54:27
	 *
	 * @param poCategorizedInput
	 * @return
	 */
	private ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> matchWithRepressedContent(
			ArrayList<clsPrimaryInformation> poCategorizedInput) {
		
		ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> oRetVal = new ArrayList<clsPair<clsPrimaryInformation,clsPrimaryInformation>>();

		for(clsPrimaryInformation oInput : poCategorizedInput) {
			
			clsPrimaryInformation oRep = moEnclosingContainer.moMemory.moRepressedContentsStore.getBestMatch(oInput.moTP.moDriveContentCategory);
			oRetVal.add(new clsPair<clsPrimaryInformation, clsPrimaryInformation>(oInput, oRep));
		}
		
		return oRetVal;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I2_6)moEnclosingContainer).receive_I2_6(moAttachedRepressed_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
		
	}

}
