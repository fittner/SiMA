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
import du.enums.eEntityType;
import du.enums.pa.eContext;
import pa.clsInterfaceHandler;
import pa.datatypes.clsDriveContentCategories;
import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.receive.I2_5_receive;
import pa.interfaces.receive.I2_6_receive;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 07.10.2009, 11:16:58
 * 
 */
public class S_ManagementOfRepressedContents_1 extends clsModuleBase implements I2_5_receive {

	public ArrayList<clsPrimaryInformation> moEnvironmentalTP_Input_old;
	public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moAttachedRepressed_Output_old;
	
	public ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalTP_Input; 
	public ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>> moAttachedRepressed_Output; 
	
	private double mrContextSensitivity = 0.8;
	
	public static String P_CONTEXT_SENSTITIVITY = "CONTEXT_SENSITIVITY"; 
	
	
	public S_ManagementOfRepressedContents_1(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);	
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_CONTEXT_SENSTITIVITY, 0.8);
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());

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
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP_old, ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		moEnvironmentalTP_Input_old = (ArrayList<clsPrimaryInformation>)deepCopy( poEnvironmentalTP_old );
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 07.10.2009, 11:20:41
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		cathegorize( moEnvironmentalTP_Input_old );
		moAttachedRepressed_Output_old = matchWithRepressedContent(moEnvironmentalTP_Input_old);
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
					clsDriveContentCategories oCath = oSemanticWeb.get(oInfo.moTP.moContent).moTP.moDriveContentCategory.get(oContext);
					if(oCath != null) { //cathegory does not exist here
					clsDriveContentCategories oCathegory = new clsDriveContentCategories( oCath );
					oCathegory.adaptToContextRatio(oContextPrim.getValue().doubleValue());	//lower the category-ratio according to the match of the context
					oInfo.moTP.moDriveContentCategory.put(oContext, oCathegory);
					}

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
		((I2_6_receive)moEnclosingContainer).receive_I2_6(moAttachedRepressed_Output_old, moAttachedRepressed_Output);
		
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:52:35
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:52:35
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

}
