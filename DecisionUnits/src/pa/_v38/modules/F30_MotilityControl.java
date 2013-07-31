/**
 * E30_MotilityControl.java: DecisionUnits - pa.modules
 * 
 * @author brandstaetter
 * 11.08.2009, 14:58:20
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v38.interfaces.modules.I2_5_receive;
import pa._v38.interfaces.modules.I2_5_send;
import pa._v38.interfaces.modules.I6_11_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.interfaces.itfModuleMemoryAccess;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
//import pa._v38.tools.clsDumper;
import pa._v38.tools.toText;
import config.clsProperties;


/**
 * Pass ActionCommands to F31_NeuroDeSymbolizationActionCommands. When no action command are present, do some seeking. Motoric movement can be controlled by psychic functions up to some extend. Drive inhibitiona mechanism necessary for the defense mechanismsleads to the possibility to perform behavior in rehearsal. Module {E30} uses this concept to evaluate how the submitted action plan can be realized best. The resulting action commands are forwarded to {E31}.
 * 
 * @author brandstaetter
 * 11.08.2012, 14:58:20
 * 
 */
public class F30_MotilityControl extends clsModuleBaseKB 
    implements I6_11_receive, I2_5_send, itfInspectorGenericActivityTimeChart {
	public static final String P_MODULENUMBER = "30";
	
	private ArrayList<clsWordPresentationMesh> moActionCommands_Input;
	private clsWordPresentationMesh moEnvironmentalPerception_IN; // AP added environmental perception
	private ArrayList<clsWordPresentationMesh> moActionCommands_Output;
	//private int mnCounter, lastTurnDirection, mnTurns;
	
	private clsShortTermMemory moShortTermMemory;
	
	private clsShortTermMemory moEnvironmentalImageStorage;
	
	private final  DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
	/**
	 * Constructor of the NeuroDeSymbolization
	 * 
	 * @author brandstaetter
	 * 03.03.2011, 17:00:42
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F30_MotilityControl(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poLongTermMemory, clsShortTermMemory poShortTermMemory, clsShortTermMemory poTempLocalizationStorage,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory);
		
        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
        
		applyProperties(poPrefix, poProp);	
		
		moActionCommands_Output = new ArrayList<clsWordPresentationMesh>(); 
		
        this.moShortTermMemory = poShortTermMemory;
        this.moEnvironmentalImageStorage = poTempLocalizationStorage;

	}
	
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		String[] ignoreList = new String[] {"eDataType:UNDEFINED",
				                            "eDataType:AFFECT",
				                            "eDataType:ASSOCIATIONTEMP"};
		// simple toString output
		text += toText.listToTEXT("moActionCommands_Input", moActionCommands_Input);
		// complex clsDumper output
		//text += "moActionCommands_Input:" + clsDumper.dump(moActionCommands_Input,3,0,ignoreList) + "\n";		

		if(moEnvironmentalPerception_IN == null)
			text += toText.valueToTEXT("moEnvironmentalPerception_IN", "null");
		else {
			// simple toString output
			text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN.toString());
			// complex clsDumper output
			//text += "moEnvironmentalPerception_IN:" + clsDumper.dump(moEnvironmentalPerception_IN,3,0,ignoreList) + "\n";
		}
		// simple toString output
		text += toText.listToTEXT("moActionCommands_Output", moActionCommands_Output);
		// complex clsDumper output
		//text += "moActionCommands_Output:" + clsDumper.dump(moActionCommands_Output,3,0,ignoreList) + "\n";
		return text;
	}	
	


	/**
	 * @author brandstaetter
	 * 02.09.2010, 20:10:34
	 * 
	 * @return the moActionCommands_Output
	 */
	public ArrayList<clsWordPresentationMesh> getActionCommands_Output() {
		return moActionCommands_Output;
	}
	
	/**
	 * @author brandstaetter
	 * 02.09.2010, 20:10:34
	 * 
	 * @return the moActionCommands_Input
	 */
	public ArrayList<clsWordPresentationMesh> getActionCommands_Input() {
		return moActionCommands_Input;
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
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
	 * @author brandstaetter
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
	 * @author brandstaetter
	 * 11.08.2009, 14:58:46
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_11(ArrayList<clsWordPresentationMesh> poActionCommands) {
		moActionCommands_Input = (ArrayList<clsWordPresentationMesh>) deepCopy(poActionCommands); 
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 11.08.2009, 16:16:50
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
				
		this.moEnvironmentalPerception_IN = this.moEnvironmentalImageStorage.findCurrentSingleMemory();
		
		this.moActionCommands_Output = this.moActionCommands_Input;
		//if(moActionCommands_Input.size() >= 1) {
		//	moActionCommands_Output = getWordPresentations(moActionCommands_Input);
		//}
		//else{
			//when there are no actions, we generate a random seeking sequence 
			//moActionCommands_Output = GenerateSeekingSequence();
			
			//when there are no actions, we deliver the ActionCommand SEARCH1
			//moActionCommands_Output = new ArrayList<clsWordPresentation>();
			//moActionCommands_Output.add(new clsWordPresentation(new clsTriple<Integer, eDataType, String>(-1,eDataType.WP,"SEARCH1"), "SEARCH1"));
			
			//when there are no actions, we do nothing
		//}
	}
	
//    // AW 20110629 New function, which converts clsSecondaryDataStructureContainer to clsWordpresentation
//    /**
//     * convert the act to a word presentation, temp function!!! DOCUMENT (wendt) - insert description
//     * 
//     * @since 02.08.2011 09:50:37
//     * 
//     * @param poInput
//     * @return
//     */
//    private ArrayList<clsWordPresentation> getWordPresentations(ArrayList<clsWordPresentationMesh> poInput) {
//        ArrayList<clsWordPresentation> oRetVal = new ArrayList<clsWordPresentation>();
//
//        for (clsWordPresentationMesh oCont : poInput) {
//            clsWordPresentation oWP = clsDataStructureGenerator.generateWP(new clsPair<eContentType, Object>(oCont.getMoContentType(), oCont
//                    .getMoContent()));
//
//            oRetVal.add(oWP);
//        }
//
//        return oRetVal;
//    }
	
//	/**
//	 * This Method generates a simple random seeking sequence
//	 * @since 07.09.2011 14:02:14
//	 * @return
//	 */
	
	/*  // not need any more, CB 2011-11-14
	private ArrayList<clsWordPresentation> GenerateSeekingSequence(){
		double rRand1 = Math.random();
		double rRand2 = Math.random();
		ArrayList<clsWordPresentation> oActionCommands_Seeking = new ArrayList<clsWordPresentation>();
		oActionCommands_Seeking.clear();
		
		if (mnCounter == 5) {
		  if(rRand1<0.25) {
			  if(lastTurnDirection == 1) oActionCommands_Seeking.add(new clsWordPresentation(new clsTriple<Integer, eDataType, String>(-1,eDataType.WP,"Test"), "TURN_LEFT"));
			  else oActionCommands_Seeking.add(new clsWordPresentation(new clsTriple<Integer, eDataType, String>(-1,eDataType.WP,"Test"), "TURN_RIGHT"));
			  if(rRand2>Math.pow(0.999,mnTurns)) { // change turning direction
				lastTurnDirection=1-lastTurnDirection;
				mnTurns=0;
			  }
			  mnTurns++;
		  }
		  else oActionCommands_Seeking.add(new clsWordPresentation(new clsTriple<Integer, eDataType, String>(-1,eDataType.WP,"Test"), "MOVE_FORWARD"));
	      mnCounter = 0;
		}
		mnCounter++;
		
		return oActionCommands_Seeking;
	}
	*/

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 11.08.2009, 16:16:50
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_5(moActionCommands_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 18.05.2010, 17:59:05
	 * 
	 * @see pa.interfaces.send.I8_1_send#send_I8_1(java.util.ArrayList)
	 */
	@Override
	public void send_I2_5(ArrayList<clsWordPresentationMesh> poActionCommands) {
		((I2_5_receive)moModuleList.get(31)).receive_I2_5(poActionCommands);
		((I2_5_receive)moModuleList.get(52)).receive_I2_5(poActionCommands);
		
		putInterfaceData(I2_5_send.class, poActionCommands);
		
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 12.07.2010, 10:47:57
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 12.07.2010, 10:47:57
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}
	
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 29.11.2010, 11:00:00
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oRetVal = new ArrayList<Double>();
		double rNUM_INPUT_ACTIONS = moActionCommands_Input.size();
		oRetVal.add(rNUM_INPUT_ACTIONS); 
		return oRetVal; 
	}
	
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 29.11.2010, 11:00:00
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "Number of Incoming Action Commands";
	}

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 29.11.2010, 11:00:00
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Number of Incoming Action Commands";
	}	
	
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 29.11.2010, 11:00:00
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		oCaptions.add("NUM_INPUT_ACTIONS");
		return oCaptions;
	}	

	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 03.03.2011, 17:00:47
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author brandstaetter
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Motoric movement can be controlled by psychic functions up to some extend. Drive inhibitiona mechanism necessary for the defense mechanismsleads to the possibility to perform behavior in rehearsal. Module {E30} uses this concept to evaluate how the submitted action plan can be realized best. The resulting action commands are forwarded to {E31}. ";
	}		
}
