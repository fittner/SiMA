/**
 * E26_DecisionMaking.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:51:57
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.TreeMap;

import config.clsBWProperties;
import pa._v30.tools.clsPair;
import pa._v30.tools.toText;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I1_7_receive;
import pa._v30.interfaces.modules.I2_13_receive;
import pa._v30.interfaces.modules.I3_3_receive;
import pa._v30.interfaces.modules.I5_5_receive;
import pa._v30.interfaces.modules.I7_1_receive;
import pa._v30.interfaces.modules.I7_1_send;
import pa._v30.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v30.memorymgmt.datatypes.clsAct;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v30.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsWordPresentation;
import pa._v30.memorymgmt.enums.eAffectLevel;
import pa._v30.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (perner) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:51:57
 * 
 */
public class E26_DecisionMaking extends clsModuleBase implements 
					I1_7_receive, I2_13_receive, I3_3_receive, I5_5_receive, I7_1_send {
	public static final String P_MODULENUMBER = "26";
	
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList;
	private ArrayList<clsAct> moRuleList; 
	private ArrayList<clsSecondaryDataStructureContainer> moRealityPerception;
	private ArrayList<clsSecondaryDataStructureContainer> moGoal_Output;
	
	private static String _Delimiter01 = ":"; 
	private static String _Delimiter02 = "||";
	private static String _Delimiter03 = "|";
	/**
	 * DOCUMENT (perner) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:51:33
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E26_DecisionMaking(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
		
		moGoal_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moDriveList", moDriveList);
		text += toText.listToTEXT("moRuleList", moRuleList);
		text += toText.listToTEXT("moRealityPerception", moRealityPerception);
		text += toText.listToTEXT("moGoal_Output", moGoal_Output);
		
		return text;
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
		
		compriseExternalPerception();
		compriseRuleList(); 
		compriseDrives();
	}
	
	
	/**
	 * DOCUMENT (zeilinger) -
	 *
	 * @author zeilinger
	 * 27.08.2010, 15:28:53
	 * @return 
	 *
	 */
	private void compriseExternalPerception() {
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
		ArrayList<clsAssociation> oAssociatedDS = new ArrayList<clsAssociation>();
		String oGoalContent; 
		clsWordPresentation oGoal = null; 
		
		//FIXME HZ Actually the highest rated drive content is taken => this is sloppy and has to be evaluated in a later version! 
		clsPair<String, clsSecondaryDataStructureContainer> oMaxDemand = getDriveMaxDemand(); 
		String oDriveContent = oMaxDemand.a; 
		clsSecondaryDataStructureContainer oDriveContainer = oMaxDemand.b; 
				
		for (clsSecondaryDataStructureContainer oExternalPerception : moRealityPerception ){
				String oExternalContent = ((clsWordPresentation)oExternalPerception.getMoDataStructure()).getMoContent(); 
							
				//TODO HZ: Here the first match is taken and added as goal to the output list; Actually
				// only one goal is selected!
				//Attention: the first part of the string (index 0 until the first string sequence "||" ) defines the drive that has to be
				// satisfied by the object outside; in case there is no adequate object perceived, the variable oContent is defined
				// only by the first part.
				if(oExternalContent.contains(oDriveContent.substring(0,oDriveContent.indexOf(_Delimiter01)))){
					oGoalContent = oDriveContent.substring(0,oDriveContent.indexOf(_Delimiter01)) + _Delimiter02 + oExternalContent; 
					oGoal = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("GOAL", oGoalContent)); 
					oAssociatedDS.addAll(oExternalPerception.getMoAssociatedDataStructures()); 
					oAssociatedDS.addAll(oDriveContainer.getMoAssociatedDataStructures()); 
					
					moGoal_Output.add(new clsSecondaryDataStructureContainer(oGoal, oAssociatedDS));
				}
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.04.2011, 23:12:13
	 *
	 */
	private void compriseRuleList() {
		//HZ 16.04.2011 - Here, the evaluation of received SUPER-Ego rules must be incorporated to the goal decision
		for(clsAct oRule : moRuleList){
			
			int nUnpleasureIntensity = getUnpleasureIntensity (oRule); 
			ArrayList<clsSecondaryDataStructureContainer> oObsoleteGoals = getObsoleteGoals (nUnpleasureIntensity, oRule); 
			ArrayList<clsSecondaryDataStructureContainer> oObsoleteDrives = getObsoleteDrives(oRule); 
			deleteObsoleteGoals(oObsoleteGoals); 
			deleteObsoleteDrives(oObsoleteDrives); 
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.04.2011, 15:02:31
	 *
	 * @param oObsoleteDrives
	 */
	private void deleteObsoleteDrives(
			ArrayList<clsSecondaryDataStructureContainer> poObsoleteDrives) {
		
		for(clsSecondaryDataStructureContainer oObsoleteDrive : poObsoleteDrives){
			moDriveList.remove(oObsoleteDrive); 
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 25.04.2011, 14:54:15
	 *
	 * @param oRule
	 * @return
	 */
	private ArrayList<clsSecondaryDataStructureContainer> getObsoleteDrives(clsAct poRule) {

		ArrayList <clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		String oRuleContent = poRule.getMoContent().substring(0, poRule.getMoContent().indexOf(_Delimiter03)); 
		
//		if(oRuleContent.contains("DEPOSIT")){
//			int i = 0; 
//		}
		
		for(clsSecondaryDataStructureContainer oDrive : moDriveList){
			String oDriveContent = ((clsWordPresentation)oDrive.getMoDataStructure()).getMoContent();
						
			if(oDriveContent.contains(oRuleContent)){
				oRetVal.add(oDrive); 
			}
		}
		
		return oRetVal; 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.04.2011, 14:40:54
	 * @param poRule 
	 *
	 * @param nUnpleasureIntensity
	 * @return
	 */
	private ArrayList<clsSecondaryDataStructureContainer> getObsoleteGoals(int pnUnpleasureIntensity, clsAct poRule) {
		ArrayList <clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		
		for(clsSecondaryDataStructureContainer oEntry : moGoal_Output){
			String oGoal = ((clsWordPresentation)oEntry.getMoDataStructure()).getMoContent();
			String oGoalContent = (String)oGoal.subSequence(0, oGoal.indexOf(_Delimiter02)); 
			String oRuleContent = poRule.getMoContent().substring(0, poRule.getMoContent().indexOf(_Delimiter03)); 
						
			for(clsSecondaryDataStructureContainer oDrive : moDriveList){
				String oDriveContent = ((clsWordPresentation)oDrive.getMoDataStructure()).getMoContent();
				String oDriveDemand = oDriveContent.substring(oDriveContent.indexOf(_Delimiter01) + 1); 
				
				if(oDriveContent.contains(oGoalContent) && oGoal.contains(oRuleContent)){
					if(eAffectLevel.valueOf(oDriveDemand).ordinal() - pnUnpleasureIntensity <= 0){
						oRetVal.add(oEntry); 
					}
				}
			}
		}
		
		return oRetVal; 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.04.2011, 14:38:48
	 *
	 * @param oRule
	 * @return
	 */
	private int getUnpleasureIntensity(clsAct poRule) {
		ArrayList <clsSecondaryDataStructure> oContentList = poRule.getMoAssociatedContent(); 
		int oRetVal = 0; 
		
		//Read out the Unpleasure intensity
		for(clsSecondaryDataStructure oContent : oContentList){
			if(oContent instanceof clsWordPresentation && ((clsWordPresentation)oContent).getMoContent().contains("UNPLEASURE")){
				String oUnpleasure = ((clsWordPresentation)oContentList.get(oContentList.indexOf(oContent) + 1)).getMoContent();
				oRetVal = eAffectLevel.valueOf(oUnpleasure).ordinal(); 
			}
		}
		
		return oRetVal;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.04.2011, 14:44:00
	 *
	 * @param oObsoleteGoals
	 */
	private void deleteObsoleteGoals(ArrayList<clsSecondaryDataStructureContainer> poObsoleteGoals) {
		
		for(clsSecondaryDataStructureContainer oObsoleteGoal : poObsoleteGoals){
			moGoal_Output.remove(oObsoleteGoal); 
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.04.2011, 07:17:23
	 *
	 */
	private void compriseDrives() {
	   // In case moGoal_output was not filled, the drive with the highest priority used as output
		ArrayList<clsAssociation> oAssociatedDS = new ArrayList<clsAssociation>();
		clsPair<String, clsSecondaryDataStructureContainer> oMaxDemand = getDriveMaxDemand(); 
		String oDriveContent = oMaxDemand.a; 
		clsSecondaryDataStructureContainer oDriveContainer = oMaxDemand.b;
		
		if( moGoal_Output.size() == 0 ){
			String oGoalContent = oDriveContent.substring(0,oDriveContent.indexOf(_Delimiter01)) + _Delimiter02; 
			clsWordPresentation oGoal = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("GOAL", oGoalContent));
			oAssociatedDS.addAll(oDriveContainer.getMoAssociatedDataStructures()); 
			moGoal_Output.add(new clsSecondaryDataStructureContainer(oGoal, oAssociatedDS));
		}
	}
	

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 02.11.2010, 17:04:15
	 * @param moDriveList2
	 * @return
	 */
	private clsPair<String, clsSecondaryDataStructureContainer> getDriveMaxDemand() {
		clsPair <String, clsSecondaryDataStructureContainer> oRetVal = null;
		
		TreeMap<Integer, ArrayList< clsPair<String, clsSecondaryDataStructureContainer> > > oSortedDrives = new TreeMap<Integer, ArrayList<clsPair<String,clsSecondaryDataStructureContainer>>>();	
		
		//fill oSortedDrives. the result is the drives grouped by their intensity
		for(clsSecondaryDataStructureContainer oContainer : moDriveList){
			String oContent = ((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent(); 
			clsPair<String, clsSecondaryDataStructureContainer> oVal = new clsPair<String, clsSecondaryDataStructureContainer>(oContent, oContainer);
			
			String oDriveIntensity = ((clsWordPresentation)oContainer.getMoDataStructure()).getMoContent().split(_Delimiter01)[1];
			int nIntensity = eAffectLevel.valueOf(oDriveIntensity).ordinal();
			
			ArrayList< clsPair<String, clsSecondaryDataStructureContainer> > oList;
			if (oSortedDrives.containsKey(nIntensity)) {
				oList = oSortedDrives.get(nIntensity);
			} else {
				oList = new ArrayList<clsPair<String,clsSecondaryDataStructureContainer>>();
				oSortedDrives.put(nIntensity, oList);
			}
			
			oList.add(oVal);
		}
		
		//select set with highest intensity - treemap is sorted ascending -> the last entry == highest intensity
		ArrayList< clsPair<String, clsSecondaryDataStructureContainer> > oList = oSortedDrives.lastEntry().getValue();
		
		if (oList.size() == 1) { //case oList.size() == 0 is dealt with throw statement at the end of the method
			//trivial case
			oRetVal = oList.get(0);
		} else if (oList.size() > 1){
			//priorize sleep. currently, the agent cannot die -> sleeping is more important than eating.
			ArrayList<String> oPriorityDrives = new ArrayList<String>( Arrays.asList("SLEEP", "RELAX") );
			
			oRetVal = null;
			for (clsPair<String, clsSecondaryDataStructureContainer> oEntry:oList) {
				String oContent = oEntry.a;

				for (String oP:oPriorityDrives) {
					if (oContent.contains(oP)) {
						oRetVal = oEntry;
						break;
					}
				}
				if (oRetVal != null) {
					break; //can't break through two loops with a single break statement.
				}
			}
			if (oRetVal == null) {
				//no entries matching one of the values of oPriorityDrives could be found -> select first entry of list (as good as random) 
				oRetVal = oList.get(0);
			}
		}
		
		if(oRetVal == null){
			throw new NullPointerException("no drive demand found -- impossible"); 
		}
		
		return oRetVal; 
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
		((I7_1_receive)moModuleList.get(27)).receive_I7_1(poGoal_Output);
		((I7_1_receive)moModuleList.get(28)).receive_I7_1(poGoal_Output);	
		
		putInterfaceData(I7_1_send.class, poGoal_Output);
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
		// TODO (perner) - Auto-generated method stub
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
		// TODO (perner) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:51:39
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 20.04.2011, 08:46:04
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "";
	}
	
}

