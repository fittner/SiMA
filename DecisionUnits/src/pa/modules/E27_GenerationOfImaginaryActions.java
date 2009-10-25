/**
 * E27_GenerationOfImaginaryActions.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:55:01
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;

import config.clsBWProperties;
import pa.datatypes.clsSecondaryInformation;
import pa.interfaces.I6_2;
import pa.interfaces.I7_1;
import pa.interfaces.I7_3;
import pa.loader.plan.clsPlanAction;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:55:01
 * 
 */
public class E27_GenerationOfImaginaryActions extends clsModuleBase implements I6_2, I7_1{

	ArrayList<clsSecondaryInformation> moEnvironmentalPerception;
	private HashMap<String, clsPair<clsSecondaryInformation, Double>> moTemplateResult_Input;
	
	ArrayList<clsPlanAction> moActions_Output;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:55:40
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E27_GenerationOfImaginaryActions(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);	
		
		ArrayList<clsPlanAction> moActions_Output = new ArrayList<clsPlanAction>();
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
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
	 * 11.08.2009, 12:09:34
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
	 * 11.08.2009, 14:55:51
	 * 
	 * @see pa.interfaces.I6_2#receive_I6_2(int)
	 */
	@Override
	public void receive_I6_2(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:55:51
	 * 
	 * @see pa.interfaces.I7_1#receive_I7_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I7_1(HashMap<String, clsPair<clsSecondaryInformation, Double>> poTemplateResult) {
		moTemplateResult_Input = ( HashMap<String, clsPair<clsSecondaryInformation, Double>>) deepCopy( poTemplateResult );
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:38
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {

		moActions_Output = this.moEnclosingContainer.moMemory.moTemplatePlanStorage.getReognitionUpdate(moTemplateResult_Input);

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:38
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I7_3)moEnclosingContainer).receive_I7_3(moActions_Output);
		
	}
}
