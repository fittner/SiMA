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

import pa._v38.interfaces.modules.I2_5_receive;
import pa._v38.interfaces.modules.I2_5_send;
import pa._v38.interfaces.modules.I6_11_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainerPair;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * Pass ActionCommands to F31_NeuroDeSymbolizationActionCommands. When no action command are present, do some seeking. Motoric movement can be controlled by psychic functions up to some extend. Drive inhibitiona mechanism necessary for the defense mechanismsleads to the possibility to perform behavior in rehearsal. Module {E30} uses this concept to evaluate how the submitted action plan can be realized best. The resulting action commands are forwarded to {E31}.
 * 
 * @author brandstaetter
 * 11.08.2009, 14:58:20
 * 
 */
public class F30_MotilityControl extends clsModuleBase implements I6_11_receive, I2_5_send {
	public static final String P_MODULENUMBER = "30";
	
	private ArrayList<clsWordPresentation> moActionCommands_Input;
	private clsDataStructureContainerPair moEnvironmentalPerception_IN; // AP added environmental perception
	private ArrayList<clsWordPresentation> moActionCommands_Output;
	private int mnCounter, lastTurnDirection, mnTurns;
	
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
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
		
		moActionCommands_Output = new ArrayList<clsWordPresentation>(); 

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
		
		text += toText.listToTEXT("moActionCommands_Input", moActionCommands_Input);
		if(moEnvironmentalPerception_IN == null)
			text += toText.valueToTEXT("moEnvironmentalPerception", "null");
		else {
			text += toText.valueToTEXT("moEnvironmentalPerception_IN-PrimaryComponent", moEnvironmentalPerception_IN.getPrimaryComponent());		
			text += toText.valueToTEXT("moEnvironmentalPerception_IN-SecondaryComponent", moEnvironmentalPerception_IN.getSecondaryComponent());
			//text += toText.valueToTEXT("moEnvironmentalPerception", moEnvironmentalPerception_IN.toString());
		}
		text += toText.listToTEXT("moActionCommands_Output", moActionCommands_Output);
		
		return text;
	}	

	/**
	 * @author brandstaetter
	 * 02.09.2010, 20:10:34
	 * 
	 * @return the moActionCommands_Output
	 */
	public ArrayList<clsWordPresentation> getActionCommands_Output() {
		return moActionCommands_Output;
	}
	
	/**
	 * @author brandstaetter
	 * 02.09.2010, 20:10:34
	 * 
	 * @return the moActionCommands_Input
	 */
	public ArrayList<clsWordPresentation> getActionCommands_Input() {
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
	public void receive_I6_11(ArrayList<clsWordPresentation> poActionCommands, clsDataStructureContainerPair poEnvironmentalPerception) {
		moActionCommands_Input = (ArrayList<clsWordPresentation>) deepCopy(poActionCommands); 
		moEnvironmentalPerception_IN = poEnvironmentalPerception;
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
		
		if(moActionCommands_Input.size() < 1) {
			//when there are no actions, we generate a random seeking sequence 
			moActionCommands_Output = GenerateSeekingSequence();
		}
		else{
			//TODO implement sub-functionality, until now forward the action commands
			moActionCommands_Output = moActionCommands_Input;
		}
	}
	
	/**
	 * This Method generates a simple random seeking sequence
	 * @since 07.09.2011 14:02:14
	 * @return
	 */
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
	public void send_I2_5(ArrayList<clsWordPresentation> poActionCommands) {
		((I2_5_receive)moModuleList.get(31)).receive_I2_5(poActionCommands);
		
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
