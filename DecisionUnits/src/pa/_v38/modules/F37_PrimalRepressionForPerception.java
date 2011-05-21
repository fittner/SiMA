/**
 * E37_PrimalRepressionForPerception.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:22:10
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I5_7_receive;
import pa._v38.interfaces.modules.I5_7_send;
import pa._v38.interfaces.modules.I5_6_receive;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.tools.toText;

import config.clsBWProperties;

/**
 * DOCUMENT (HINTERLEITNER) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:22:10
 * 
 */
public class F37_PrimalRepressionForPerception extends clsModuleBase implements I5_6_receive, I5_7_send  {
	public static final String P_MODULENUMBER = "37";
	
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	private clsPrimaryDataStructureContainer moEvaluatedEnvironment_OUT;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;
		
	/**
	 * DOCUMENT (HINTERLEITNER) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:20:58
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F37_PrimalRepressionForPerception(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.valueToTEXT("moEvaluatedEnvironment_OUT", moEvaluatedEnvironment_OUT);
		
		return text;
	}	
	
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
	}	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		// TODO (HINTERLEITNER) - Auto-generated method stub
		//moEvaluatedEnvironment_OUT = clsDataStructureConverter.convertTIContToTPMCont(moEnvironmental_IN);
		moEvaluatedEnvironment_OUT = moEnvironmentalPerception_IN;
		
		//Indirect associated memories are "durchgeschliffen"
		moAssociatedMemories_OUT = moAssociatedMemories_IN;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (HINTERLEITNER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (HINTERLEITNER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_7(moEvaluatedEnvironment_OUT, moAssociatedMemories_OUT);

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:22:14
	 * 
	 * @see pa.interfaces.send._v38.I2_14_send#send_I2_14(java.util.ArrayList)
	 */
	@Override
	public void send_I5_7(clsPrimaryDataStructureContainer poEnvironmentalTP, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		
		((I5_7_receive)moModuleList.get(35)).receive_I5_7(poEnvironmentalTP, poAssociatedMemories);	//Associated memories only for perception
		((I5_7_receive)moModuleList.get(57)).receive_I5_7(poEnvironmentalTP, poAssociatedMemories);
		
		putInterfaceData(I5_7_send.class, poEnvironmentalTP, poAssociatedMemories);
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:22:14
	 * 
	 * @see pa.interfaces.receive._v38.I2_20_receive#receive_I2_20(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_6(clsPrimaryDataStructureContainer poEnvironmentalTP, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		moEnvironmentalPerception_IN = (clsPrimaryDataStructureContainer)deepCopy(poEnvironmentalTP);
		moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
		
	}

	/**
	 * @author zeilinger
	 * 18.03.2011, 15:59:16
	 * 
	 * @return the moEnvironmental_IN
	 */
	public clsPrimaryDataStructureContainer getMoEnvironmental_IN() {
		return moEnvironmentalPerception_IN;
	}
	/**
	 * @author zeilinger
	 * 18.03.2011, 15:59:16
	 * 
	 * @return the moEvaluatedEnvironment_OUT
	 */
	public clsPrimaryDataStructureContainer getMoEvaluatedEnvironment_OUT() {
		return moEvaluatedEnvironment_OUT;
	}
	
	public ArrayList<clsPrimaryDataStructureContainer> getMoAssociatedMemories_OUT() {
		return moAssociatedMemories_OUT;
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "This function categorizes the thing presentations according to the four primary drives. The result of it is that thing presentations have an additional value which can be used for further memory lookup to find similar entries.";
	}		
}
