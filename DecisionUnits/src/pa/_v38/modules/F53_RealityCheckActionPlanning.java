/**
 * E33_RealityCheck2.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 27.04.2010, 10:18:11
 */
package pa._v38.modules;

import general.datamanipulation.PrintTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I6_10_receive;
import pa._v38.interfaces.modules.I6_10_send;
import pa._v38.interfaces.modules.I6_13_receive;
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshSelectableGoal;
import pa._v38.memorymgmt.datatypes.clsWording;
import pa._v38.memorymgmt.interfaces.itfModuleMemoryAccess;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import secondaryprocess.functionality.EffortFunctionality;
import config.clsProperties;

/**
 * DOCUMENT (KOHLHAUSER) - insert description 
 * 
 * @author deutsch
 * 27.04.2012, 10:18:11
 * 
 */
public class F53_RealityCheckActionPlanning extends clsModuleBaseKB implements I6_9_receive, I6_10_send, I6_13_receive {
	
	public static final String P_MODULENUMBER = "53";
	
	
	private final  DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	private clsWordPresentationMesh moWording_IN;
	
	private ArrayList<clsWordPresentationMeshSelectableGoal> selectableGoals;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	/**
	 * DOCUMENT (Kohlhauser) - insert description 
	 * 
	 * @author zeilinger
	 * 02.05.2011, 16:20:28
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poKnowledgeBaseHandler
	 * @throws Exception
	 */
	public F53_RealityCheckActionPlanning(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			itfModuleMemoryAccess poLongTermMemory,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);
		// TODO (zeilinger) - Auto-generated constructor stub
		
		 this.moPsychicEnergyStorage = poPsychicEnergyStorage;
		 this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
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
		
		return text;
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	@SuppressWarnings("unused")
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
		//nothing to do
	}	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:18:11
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
	    log.debug("=== module {} start ===", this.getClass().getName());
	    
	    //TEMP Apply effort on goal
        EffortFunctionality.applyEffortOfGoal(selectableGoals);
        log.info("Applied efforts on selectable goals: {}", PrintTools.printArrayListWithLineBreaks(selectableGoals));
        
        //Apply effort of wording
        
        //Apply effort/bonus of drive aim action
        
        
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:18:11
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_10(selectableGoals);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:18:11
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
	 * 27.04.2010, 10:18:11
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
	 * 27.04.2010, 10:36:01
	 * 
	 * @see pa.interfaces.I7_3#receive_I7_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I6_9(ArrayList<clsWordPresentationMeshSelectableGoal> poSelectableGoals) {
	    selectableGoals = poSelectableGoals;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 18:00:41
	 * 
	 * @see pa.interfaces.send.I7_6_send#send_I7_6(int)
	 */
	@Override
	public void send_I6_10(ArrayList<clsWordPresentationMeshSelectableGoal> poSelectableGoals) {
		((I6_10_receive)moModuleList.get(29)).receive_I6_10(poSelectableGoals);
		
		putInterfaceData(I6_10_send.class, poSelectableGoals);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:48:18
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:48:18
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:59:26
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
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
		moDescription = "This module operates similarly to {E24}. The imaginary actions generated by {E27} are evaluated regarding which action plan is possible and the resulting requirements. {E34} provides the semantic knowledge necessary for this task. The result influences the final decision which action to choose in module {E28}.";
	}

    /* (non-Javadoc)
     *
     * @since 06.09.2013 21:37:49
     * 
     * @see pa._v38.interfaces.modules.I6_13_receive#receive_I6_13(pa._v38.memorymgmt.datatypes.clsWording)
     */
    @Override
    public void receive_I6_13(clsWordPresentationMesh moWording) {
        moWording_IN = moWording; 
        
    }

    /* (non-Javadoc)
     *
     * @since 06.09.2013 21:37:49
     * 
     * @see pa._v38.interfaces.modules.I6_13_receive#receive_I6_13(pa._v38.memorymgmt.datatypes.clsWordPresentationMesh, java.util.ArrayList)
     */
    @Override
    public void receive_I6_13(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
        // TODO (hinterleitner) - Auto-generated method stub
        
    }

    /* (non-Javadoc)
     *
     * @since 12.09.2013 22:25:09
     * 
     * @see pa._v38.interfaces.modules.I6_13_receive#receive_I6_13(pa._v38.memorymgmt.datatypes.clsWording)
     */
    @Override
    public void receive_I6_13(clsWording moWording) {
        // TODO (hinterleitner) - Auto-generated method stub
        
    }	
	
}
