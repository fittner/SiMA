/**
 * E26_DecisionMaking.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:51:57
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;

import config.clsBWProperties;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsSecondaryInformationMesh;
import pa.interfaces.I1_7;
import pa.interfaces.I2_13;
import pa.interfaces.I3_3;
import pa.interfaces.I5_5;
import pa.interfaces.I7_1;
import pa.interfaces.I7_2;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:51:57
 * 
 */
public class E26_DecisionMaking extends clsModuleBase implements I1_7, I2_13, I3_3, I5_5 {

	private ArrayList<clsSecondaryInformation> moDriveList;
	private ArrayList<clsPair<clsSecondaryInformation, clsSecondaryInformationMesh>> moRealityPerception;
	
	HashMap<String, clsPair<clsSecondaryInformation, Double>> moTemplateImageResult;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:52:31
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E26_DecisionMaking(String poPrefix, clsBWProperties poProp,
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
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList) {
		moDriveList = (ArrayList<clsSecondaryInformation>)this.deepCopy(poDriveList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_13(ArrayList<clsPair<clsSecondaryInformation, clsSecondaryInformationMesh>> poRealityPerception) {
		moRealityPerception = (ArrayList<clsPair<clsSecondaryInformation, clsSecondaryInformationMesh>>)deepCopy(poRealityPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 */
	@Override
	public void receive_I5_5(int pnData) {
		mnTest += pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:33
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {

		ArrayList<clsSecondaryInformation> oCompletePerception = new ArrayList<clsSecondaryInformation>();
		
		oCompletePerception.addAll(moDriveList);
		for(clsPair<clsSecondaryInformation, clsSecondaryInformationMesh> oReal :moRealityPerception) {
			oCompletePerception.add(oReal.a);
		}
		
		moTemplateImageResult = moEnclosingContainer.moMemory.moTemplateImageStorage.compare(oCompletePerception);
		
		
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:33
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I7_1)moEnclosingContainer).receive_I7_1(mnTest);
		((I7_2)moEnclosingContainer).receive_I7_2(mnTest);		
	}
}

