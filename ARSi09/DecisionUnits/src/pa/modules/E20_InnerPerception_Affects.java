/**
 * E20_InnerPerception_Affects.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:40:29
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsSecondaryInformation;
import pa.interfaces.I5_1;
import pa.interfaces.I5_2;
import pa.interfaces.I5_3;
import pa.interfaces.I5_4;
import pa.interfaces.I5_5;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:40:29
 * 
 */
public class E20_InnerPerception_Affects extends clsModuleBase implements I5_1, I5_2, I5_3, I5_4 {

	private ArrayList<clsAffectTension> moAffectOnlyList;
	private ArrayList<clsAffectTension> moDeniedAffects_Input;
	private ArrayList<clsSecondaryInformation> moPerception;
	private ArrayList<clsSecondaryInformation> moDriveList_Input;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:41:04
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E20_InnerPerception_Affects(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);		
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
		mnProcessType = eProcessType.PRIMARY;
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
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_1(ArrayList<clsAffectTension> poAffectOnlyList) {
		moAffectOnlyList = (ArrayList<clsAffectTension>)this.deepCopy(poAffectOnlyList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_2(ArrayList<clsAffectTension> poDeniedAffects) {
		moDeniedAffects_Input = (ArrayList<clsAffectTension>)this.deepCopy(poDeniedAffects);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_3#receive_I5_3(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_3(ArrayList<clsSecondaryInformation> poDriveList) {
		moDriveList_Input = (ArrayList<clsSecondaryInformation>)this.deepCopy(poDriveList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_4#receive_I5_4(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_4(ArrayList<clsSecondaryInformation> poPerception) {
		moPerception = (ArrayList<clsSecondaryInformation>)this.deepCopy(poPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:08
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		mnTest++;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:08
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I5_5)moEnclosingContainer).receive_I5_5(mnTest);
		
	}

}
