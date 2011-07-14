/**
 * F7_SuperEgoReactive.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:53
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.modules.eProcessType;
import pa._v38.modules.ePsychicInstances;
import pa._v38.interfaces.modules.I5_10_receive;
import pa._v38.interfaces.modules.I5_11_receive;
import pa._v38.interfaces.modules.I5_11_send;
import pa._v38.interfaces.modules.I5_12_receive;
import pa._v38.interfaces.modules.I5_13_receive;
import pa._v38.interfaces.modules.I5_13_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import config.clsBWProperties;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:53
 * 
 */
public class F07_SuperEgoReactive extends clsModuleBase
	implements I5_12_receive, I5_10_receive, I5_11_send, I5_13_send{

	public static final String P_MODULENUMBER = "7";
	
	//AW 20110522: New inputs
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_Input;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_Input;
	
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_Output;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_Output;
	
	@SuppressWarnings("unused")
	private Object moMergedPrimaryInformation;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moDrives;
	private ArrayList<String> moForbiddenDrives;
	private ArrayList<clsPair<String, String>> moForbiddenPerceptions;
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 02.05.2011, 15:49:49
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poKnowledgeBaseHandler
	 * @throws Exception
	 */
	public F07_SuperEgoReactive(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		applyProperties(poPrefix, poProp); 
	}
	
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
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		text += toText.valueToTEXT("moAssociatedMemories_Input", moAssociatedMemories_Input);	
		text += toText.valueToTEXT("moAssociatedMemories_Output", moAssociatedMemories_Output);
		text += toText.valueToTEXT("moForbiddenDrives", moForbiddenDrives);		
		text += toText.valueToTEXT("moForbiddenPerceptions", moForbiddenPerceptions);		
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_10_receive#receive_I5_10(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_10(clsPrimaryDataStructureContainer poEnvironmentalPerception, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		moEnvironmentalPerception_Input = (clsPrimaryDataStructureContainer)poEnvironmentalPerception.clone();
		moAssociatedMemories_Input = (ArrayList<clsPrimaryDataStructureContainer>) deepCopy(poAssociatedMemories);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_12_receive#receive_I5_12(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_12(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poDrives) {
		
		moDrives = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>) deepCopy(poDrives); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		// TODO (zeilinger) - Auto-generated method stub
		
		//AW 20110522: Input from perception
		moEnvironmentalPerception_Output = moEnvironmentalPerception_Input;
		//AW 20110522: Input from associated memories
		moAssociatedMemories_Output = moAssociatedMemories_Input;
		
		// check perception and drives
		// apply internalized rules
		checkInternalizedRules();		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:12
	 * 
	 * Super-Ego checks perception and drives
	 * 
	 */
	private void checkInternalizedRules() {
		// ToDo FG: These are just samples for internalized rules.
		//          All the internalized rules must be stored in an (XML-)file and processed one after another
		
		// sample rule for repression of drives
		if (searchInDM ("NOURISH") &&
			searchInTP ("color", "Farbe eine feindlichen ARSin") &&
			searchInTPM("ENTITY", "BUBBLE") &&
			searchInTPM("ENTITY", "CAKE")) {
			// If all the conditions above are true then Super-Ego can fire.
			// An internalized rule was detected to be true.
			// So the Super-Ego conflicts now with Ego and Super-Ego requests from Ego to activate defense.
			
			
			// The following drive was found by Super-Ego as inappropriate or forbidden.
			// Therefore the Super-Ego marks the drive as forbidden and sends the mark to the Ego.
			moForbiddenDrives.add("NOURISH");
		}
		
		// sample rule for denial of perceptions
		if (searchInDM ("NOURISH") &&
			searchInTP ("color", "Farbe eine feindlichen ARSin") &&
			searchInTPM("ENTITY", "BUBBLE") &&
			searchInTPM("ENTITY", "CAKE")) {
			// If all the conditions above are true then Super-Ego can fire.
			// An internalized rule was detected to be true.
			// So the Super-Ego conflicts now with Ego and Super-Ego requests from Ego to activate defense.
		
			
			// The following perception was found by Super-Ego as inappropriate or forbidden.
			// Therefore the Super-Ego marks the perception as forbidden and sends the mark to the Ego.
			moForbiddenPerceptions.add(new clsPair<String, String> ("ENTITY", "CAKE"));
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:48
	 * 
	 * searches in the input-perception for example for a color red
	 * 
	 */
	private boolean searchInTP (String oContentType, String oContent) {
		// search in perceptions
		for(clsPrimaryDataStructureContainer oContainer : moAssociatedMemories_Output){
			
			// check a TP
			if(oContainer.getMoDataStructure() instanceof clsThingPresentation){
				
				// check the color
				if(oContainer.getMoDataStructure().getMoContentType() == oContentType){
					if(((clsThingPresentation)oContainer.getMoDataStructure()).getMoContent() == oContent){
						return true; 
					}	
				}	
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:49
	 * 
	 * searches in the input-perception for example for an ENTITY like a BUBBLE
	 * 
	 */
	private boolean searchInTPM (String oContentType, String oContent) {
		// search in perceptions
		for(clsPrimaryDataStructureContainer oContainer : moAssociatedMemories_Output){
			
			// check a TPM
			if(oContainer.getMoDataStructure() instanceof clsThingPresentationMesh){
				
				// check if it is for example an ARSin
				if(oContainer.getMoDataStructure().getMoContentType() == oContentType){
					if(((clsThingPresentationMesh)oContainer.getMoDataStructure()).getMoContent() == oContent){
						// ToDo FG: Man könnte jetzt auch noch die Assoziationen des TPM auf bestimmte Werte durchsuchen.
						return true;
					}	
				}					
			}
		}
		return false;
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:50
	 * 
	 * searches in the input-DriveMesh for example for NOURISH
	 * 
	 */
	private boolean searchInDM (String oContentType) {		
		// search in drives
		for(clsPair<clsPhysicalRepresentation, clsDriveMesh> oDrives : moDrives){
			// check DriveMesh
			if (oDrives.b.getMoContentType() == oContentType){
				return true;
			}
		}
		return false;
	}
	

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_13(moForbiddenDrives, moDrives); 
		send_I5_11(moForbiddenPerceptions, moEnvironmentalPerception_Output, moAssociatedMemories_Output); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
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
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.SUPEREGO;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:49:48
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
	 * 02.05.2011, 15:49:48
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		// TODO (zeilinger) - Auto-generated method stub
		moDescription = "Based on internalized rules, Super-Ego checks incoming perceptions and drives. If the internalized rules are violated Super-Ego requests from F06 and F19 to activate the defense mechanisms.";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_13_send#send_I5_13(java.util.ArrayList)
	 */
	@Override
	public void send_I5_13(ArrayList<String> poForbiddenDrives, ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		((I5_13_receive)moModuleList.get(6)).receive_I5_13(poForbiddenDrives, poData);
		
		putInterfaceData(I5_13_send.class, poForbiddenDrives, poData);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:42:26
	 * 
	 * @see pa._v38.interfaces.modules.I5_11_send#send_I5_11(java.util.ArrayList)
	 */
	@Override
	public void send_I5_11(ArrayList<clsPair<String, String>> poForbiddenPerceptions, clsPrimaryDataStructureContainer poEnvironmentalPerception, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		((I5_11_receive)moModuleList.get(19)).receive_I5_11(poForbiddenPerceptions, poEnvironmentalPerception, poAssociatedMemories);
		
		putInterfaceData(I5_13_send.class, poForbiddenPerceptions, poEnvironmentalPerception, poAssociatedMemories);
	}
}
