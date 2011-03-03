/**
 * E26_DecisionMaking.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:51:57
 */
package pa.modules._v19;

import java.util.ArrayList;

import config.clsBWProperties;
import pa._v19.clsInterfaceHandler;
import pa.interfaces.receive._v19.I1_7_receive;
import pa.interfaces.receive._v19.I2_13_receive;
import pa.interfaces.receive._v19.I3_3_receive;
import pa.interfaces.receive._v19.I5_5_receive;
import pa.interfaces.receive._v19.I7_1_receive;
import pa.interfaces.receive._v19.I7_2_receive;
import pa.interfaces.send._v19.I7_1_send;
import pa.interfaces.send._v19.I7_2_send;
import pa.memorymgmt.datahandler.clsDataStructureGenerator;
import pa.memorymgmt.datatypes.clsAct;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eAffectLevel;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:51:57
 * 
 */
public class E26_DecisionMaking extends clsModuleBase implements I1_7_receive, I2_13_receive, I3_3_receive, I5_5_receive, I7_1_send, I7_2_send {

	private ArrayList<clsSecondaryDataStructureContainer> moDriveList;
	private ArrayList<clsAct> moRuleList; 
	private ArrayList<clsSecondaryDataStructureContainer> moRealityPerception;
	private ArrayList<clsSecondaryDataStructureContainer> moGoal_Output; 
	
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
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);	
				
		moGoal_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
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
	 * 
	 * by this interface a list of drives, which represent the current wishes
	 * fills moDriveList
	 *   
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moDriveList = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poDriveList); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I2_13#receive_I2_13(int)
	 * 
	 * by this interface a set of reality information, filtered by E24 (reality check), is received
	 * fills moRealityPerception
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_13(ArrayList<clsSecondaryDataStructureContainer> poRealityPerception) {
		moRealityPerception = (ArrayList<clsSecondaryDataStructureContainer>)deepCopy(poRealityPerception); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 * 
	 * TODO cua implement
	 * 
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I3_3(ArrayList<clsAct> poRuleList) {
		moRuleList = (ArrayList<clsAct>)deepCopy(poRuleList); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 * 
	 * TODO cua implement
	 * 
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
	 * 
	 * this module sends the perception input to module E27, E28 just bypasses the information and sends an additional counter which is not used
	 *  
	 * 
	 */
	@Override
	protected void process_basic() {
		//HZ Up to now it is possible to define the goal by a clsWordPresentation only; it has to be 
		//verified if a clsSecondaryDataStructureContainer is required.
		moGoal_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
		selectGoal(); 
	}
	
	/**
	 * DOCUMENT (zeilinger) -
	 *
	 * @author zeilinger
	 * 27.08.2010, 15:28:53
	 * @return 
	 *
	 */
	private void selectGoal() {
		// HZ 2010.08.27: This method selects a goal on the base of input parameters. Up to now
		// these inputs are restricted to I_2.13 and I_1.7. As I_2.13 gives an image about the
		// actual situation and I_1.7 retrieves an ordered list of actual "needs" in the form of 
		// drives. Here it is iterated through the drive list and the need is compared with the 
		// list of focused external perception. In case a match is found between an external perception
		// and a drive (both of highest priority), the goal is formed to decrease the drive
		// by the use of the externally perceived e.g. object. If no match turns up in the 
		// highest priority, the iteration is going on until a match has been found. 
		// However if there is no match between the externally perceived objects and the 
		// received drives, the drive is buffered. In case its priority is the highest one, 
		// the decision can be done, that exactly this drive has to be satisfied even there
		// is no object in the area right now that can be used to do this. Hence the goal 
		// would be to roam around and find an object that can be used to satisfy the drive. 
		String oGoalContent = ""; 
		String oDriveContent = ""; 
		clsSecondaryDataStructureContainer oDriveContainer = null; 
		clsWordPresentation oGoal = null; 
		ArrayList<clsAssociation> oAssociatedDS = new ArrayList<clsAssociation>();

		//FIXME HZ Actually the highest rated drive content is taken => this is sloppy and has to be evaluated in a later version! 
		oDriveContainer = getHighestDriveDemand(moDriveList);
		oDriveContent = ((clsWordPresentation)oDriveContainer.moDataStructure).moContent; 
		
		for (clsSecondaryDataStructureContainer oExtPerception : moRealityPerception ){
				// dirty hack -> moRealityPerception only contains the "a" part of the clsPair - look at E24
				String oExtContent   = ((clsWordPresentation)oExtPerception.moDataStructure).moContent; 
				
				//FIXME HZ: Here an evaluation of the drive's intensity (very low, low, medium, high, very high) has to be done (like)
				// in E23. It cannot be matched directly as it has to be compared that e.g. very high is not equal to high
				// but is bigger than high. E.g. in case eating a cake satisfies the drive NOURISH high but the need Nourish
				// is very high now, it has to be identified that the cake gets still eaten even high and very high do not match.
				// actually this is done very sloppy. For now it is controlled if the drive intensity is high enough
				// to get satisfied and if the object is able to satisfy it.
				// Has to be changed when it is possible to order Strings by their "intensity"
			//	int nFirstIndex = oExtContent.indexOf(oDriveContent.substring(0, oDriveContent.indexOf(":"))); 
			//	String oIntensity = oExtContent.substring(oExtContent.indexOf(":", nFirstIndex) + 1, oExtContent.indexOf("|", nFirstIndex));
				
				if (oExtContent.contains(oDriveContent.substring(0, oDriveContent.indexOf(":")))
						&& (oDriveContent.contains(eAffectLevel.MEDIUM.name())
							||oDriveContent.contains(eAffectLevel.HIGH.name())
							||oDriveContent.contains(eAffectLevel.VERYHIGH.name()))){
//						&& ((oIntensity.equals("HIGH"))
//						|| (oIntensity.equals("VERYHIGH")))){ 
					//TODO HZ: Here the first match is taken and added as goal to the output list; Actually
					// only one goal is selected!
					//Attention: the first part of the string (index 0 until the first string sequence "||" ) defines the drive that has to be
					// satisfied by the object outside; in case there is no adequate object perceived, the variable oContent is defined
					// only by the first part.
					oGoalContent = oDriveContent.substring(0,oDriveContent.indexOf(":")) + "||" + oExtContent; 
					oGoal = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("GOAL", oGoalContent)); 
					oAssociatedDS.addAll(oExtPerception.moAssociatedDataStructures); 
					oAssociatedDS.addAll(oDriveContainer.moAssociatedDataStructures); 
					
//					//HZ - MAGIC happens 
//					if(!(moRuleList.size() > 0 && (oGoal.moContent.contains("ARSINO") || oGoal.moContent.contains("BUBBLE")))){
					moGoal_Output.add(new clsSecondaryDataStructureContainer(oGoal, oAssociatedDS));
//					}
				}
		}
		
		
//HZ 25.09.2010 - Here, the evaluation of received SUPER-Ego rules must be incorporated to the goal decision
		for(clsAct oRule : moRuleList){
			//the rule set has to be introduced
			oRule.moContent = "TEST"; 
		}
//		for(clsAct oRule : moRuleList){
//			String oDelimiter = "|"; 
//			String oRuleContent =  oRule.moContent; 
//			String [] oPrecondition = oRuleContent.substring(oRuleContent.indexOf(oDelimiter, oRuleContent.indexOf(eActState.PRECONDITION.name())), 
//					                                         oRuleContent.indexOf(eActState.ACTION.name())).split("[" + oDelimiter + "]");
//			//String oConsequence = oRuleContent.substring(oRuleContent.indexOf(oDelimiter, oRuleContent.indexOf(eActState.CONSEQUENCE.name()))); 
//			ArrayList<clsSecondaryDataStructureContainer> oRemovedCon = new ArrayList<clsSecondaryDataStructureContainer>(); 
//			
//			for(clsSecondaryDataStructureContainer oCon : moGoal_Output){
//				int oIndex = 0; 
//				
//				for (String oCondition : oPrecondition){
//					if(((clsWordPresentation)oCon.moDataStructure).moContent.contains(oCondition)){
//						oIndex++; 
//					}
//				}
//				
//				// -1 is the placeholder for UNPLEASURE - has to be rethought 
//				if(oIndex == oPrecondition.length - 1){
//					oRemovedCon.add(oCon); 
//				}
//			}
//			
//			moGoal_Output.removeAll(oRemovedCon); 
//		}
		
		// In case moGoal_output was not filled, the drive with the highest priority used as output
		if(moGoal_Output.size() == 0){
			oGoalContent = oDriveContent.substring(0,oDriveContent.indexOf(":")) + "||"; 
			oGoal = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("GOAL", oGoalContent));
			oAssociatedDS.addAll(moDriveList.get(0).moAssociatedDataStructures); 
			moGoal_Output.add(new clsSecondaryDataStructureContainer(oGoal, oAssociatedDS));
		}
	}

	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 02.11.2010, 17:04:15
	 *
	 * @param moDriveList2
	 * @return
	 */
	private clsSecondaryDataStructureContainer getHighestDriveDemand(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		int oHighestPriorityIndex = 0; 
		eAffectLevel oAffectLevel = eAffectLevel.VERYLOW; 
		 
		for(clsSecondaryDataStructureContainer oContainer : poDriveList){
			String [] oContent = ((clsWordPresentation)oContainer.moDataStructure).moContent.split(":"); 
			
			if(eAffectLevel.compare(eAffectLevel.valueOf(oContent[1]), oAffectLevel)){
				oHighestPriorityIndex = poDriveList.indexOf(oContainer); 
				oAffectLevel = eAffectLevel.valueOf(oContent[1]); 
			}
		}
		 
		return poDriveList.get(oHighestPriorityIndex);
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
		send_I7_1(moGoal_Output);
		send_I7_2(moGoal_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:55:10
	 * 
	 * @see pa.interfaces.send.I7_1_send#send_I7_1(java.util.HashMap)
	 */
	@Override
	public void send_I7_1(ArrayList<clsSecondaryDataStructureContainer> poGoal_Output) {
		((I7_1_receive)moEnclosingContainer).receive_I7_1(moGoal_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:55:10
	 * 
	 * @see pa.interfaces.send.I7_2_send#send_I7_2(int)
	 */
	@Override
	public void send_I7_2(ArrayList<clsSecondaryDataStructureContainer> poGoal_Output) {
		((I7_2_receive)moEnclosingContainer).receive_I7_2(moGoal_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:36
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:36
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
}

