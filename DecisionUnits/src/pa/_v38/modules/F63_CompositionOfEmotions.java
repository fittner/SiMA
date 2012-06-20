/**
 * F48_AccumulationOfAffectsForDrives.java: DecisionUnits - pa._v38.modules
 * 
 * @author muchitsch
 * 02.05.2011, 15:47:11
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import pa._v38.modules.eImplementationStage;
import pa._v38.interfaces.modules.I5_10_receive;
import pa._v38.interfaces.modules.I5_21_send;
import pa._v38.interfaces.modules.I5_21_receive;
import pa._v38.interfaces.modules.I5_3_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * F48 combines Libido and homeostatic drive candidates, calculates the first quota of effect based 
 * on a splitter mechanism and outputs the result to a list of drive candidates.
 * 
 * @author muchitsch
 * 07.05.2012, 15:47:11
 */
public class F63_CompositionOfEmotions extends clsModuleBase 
					implements I5_3_receive, I5_10_receive, I5_21_send {

	//Statics for the module
	public static final String P_MODULENUMBER = "63";

	//Private members for send and recieve
	private ArrayList<String> moEmotions_OUT; //TODO implement emotions datatype
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives_IN;
	private clsThingPresentationMesh moPerceptions_IN;
	

	public F63_CompositionOfEmotions(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		applyProperties(poPrefix, poProp);	
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moEmotions_OUT", moEmotions_OUT);	
				
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
		//TODO: implement F63 here
	
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_21(moEmotions_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "F63: TODO";
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:23:00
	 * 
	 * @see pa._v38.interfaces.modules.I5_10_receive#receive_I5_10(pa._v38.memorymgmt.datatypes.clsThingPresentationMesh)
	 */
	@Override
	public void receive_I5_10(clsThingPresentationMesh poPerceptionalMesh) {
		try {
			moPerceptions_IN = (clsThingPresentationMesh) poPerceptionalMesh.clone();
		} catch (CloneNotSupportedException e) {
			// TODO (muchitsch) - Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:23:00
	 * 
	 * @see pa._v38.interfaces.modules.I5_3_receive#receive_I5_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_3(
			ArrayList<clsDriveMesh> poDrives) {
		moDrives_IN = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>) deepCopy(poDrives); 
		
	}

	/* (non-Javadoc)
	 *
	 * @since 07.05.2012 12:33:59
	 * 
	 * @see pa._v38.interfaces.modules.I5_21_send#send_I5_21(java.util.ArrayList)
	 */
	@Override
	public void send_I5_21(ArrayList<String> poEmotions) {
		((I5_21_receive)moModuleList.get(55)).receive_I5_21(poEmotions);
		
	}

}
