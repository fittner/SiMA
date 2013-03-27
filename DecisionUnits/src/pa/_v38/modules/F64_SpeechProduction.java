/**
 * F63_CompositionOfEmotions.java: DecisionUnits - pa._v38.modules
 * 
 * @author hinterleitner
 */
package pa._v38.modules;

import java.util.HashMap;

import config.clsProperties;



/**
 * 
 * @author hinterleitner
 */
public class F64_SpeechProduction extends clsModuleBase {

	//Statics for the module
	public static final String P_MODULENUMBER = "64";
	
	
	
	public F64_SpeechProduction(String poPrefix,clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList)
			throws Exception {
		super(poPrefix, poProp, poModuleList, null);
		
	
		
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	
	}

	/* (non-Javadoc)
	 *
	 * @author hinterleitner
	 * 02.07.2012, 15:48:45
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
	
		//text += toText.listToTEXT("moPerceptions_IN", moPerceptions_IN.getMoInternalAssociatedContent());
		
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @author hinterleitner
	 * 02.07.2012, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
	}
		
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}

	
	@Override
	protected void send() {
	
	}

	
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
	}

	
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;
	}

	
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	
	@Override
	public void setDescription() {
		moDescription = "F64: F64 Generation of Inner and Outer Speech for Speech and Language Production. ";
	}

	



}
