/**
 * E26_DecisionMaking.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:51:57
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.NavigableSet;
import java.util.SortedMap;
import java.util.TreeMap;
import config.clsBWProperties;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I6_1_receive;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_7_receive;
import pa._v38.interfaces.modules.I6_2_receive;
import pa._v38.interfaces.modules.I6_8_receive;
import pa._v38.interfaces.modules.I6_8_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsPrediction;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * Demands provided by reality, drives, and Superego are merged. The result is evaluated regarding which resulting wish can be used as motive for an action tendency. The list of produced motives is ordered according to their satisability. 
 * 
 * @author kohlhauser
 * 11.08.2009, 14:51:57
 * 
 */
public class F26_DecisionMaking extends clsModuleBase implements 
			I6_1_receive, I6_2_receive, I6_3_receive, I6_7_receive, I6_8_send {
	public static final String P_MODULENUMBER = "26";
	
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList;
	private ArrayList<clsAct> moRuleList; 
	private ArrayList<clsSecondaryDataStructureContainer> moRealityPerception;
	//AW 20110602 Added expectations, intentions and the current situation
	private ArrayList<clsPrediction> moExtractedPrediction_IN;
	
	private ArrayList<clsSecondaryDataStructureContainer> moGoal_Output;
	
	private static String _Delimiter01 = ":"; 
	private static String _Delimiter02 = "||";
	private static String _Delimiter03 = "|";
	/**
	 * DOCUMENT (kohlhauser) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:51:33
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F26_DecisionMaking(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
		
		moGoal_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
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
	 * @author kohlhauser
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
	 * @author kohlhauser
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
	 * @author kohlhauser
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
	public void receive_I6_3(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moDriveList = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poDriveList); 
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
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
	public void receive_I6_7(ArrayList<clsSecondaryDataStructureContainer> poRealityPerception, 
			ArrayList<clsPrediction> poExtractedPrediction) {
		moRealityPerception = (ArrayList<clsSecondaryDataStructureContainer>)deepCopy(poRealityPerception); 
		moExtractedPrediction_IN = (ArrayList<clsPrediction>)deepCopy(poExtractedPrediction); 
	}
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:52:37
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 * 
	 * TODO cua implement
	 * 
	 */
	@Override
	public void receive_I6_2(int pnData) {
		mnTest += pnData;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
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
		
		//Add AW 20110723
		moGoal_Output.addAll(comprisePrediction(moExtractedPrediction_IN));
		
		compriseRuleList(); 
		compriseDrives();
		
		try {
			sortGoalOutput();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void sortGoalOutput() throws Exception {
		//TD this function sorts moGoal_Output such that the moset pleasureful goals are listed first. this
		//is a feature-fix. none of the later modules make a selection of the best plan currently. they just
		//select what is first in list.
		
		if (moGoal_Output.size()<=1) {
			return; //nothing to do. either list is empty, or it consists of one lement only
		}
		
		//TD everything in here is a bad hack - problem is, i don't understand clsSecondaryDataStructureContainer ...
		HashMap<String, Double> oValues = new HashMap<String, Double>();
		oValues.put("VERYLOW", 0.0);oValues.put("LOW", 0.25);oValues.put("MEDIUM", 0.5);oValues.put("HIGH", 0.55);oValues.put("VERYHIGH", 1.0);
		ArrayList<String> oKeyWords = new ArrayList<String>(Arrays.asList("NOURISH", "BITE", "REPRESS", "SLEEP", "RELAX", "DEPOSIT"));
		
		
		TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer> > oSortedList = new TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer>>();
		for (int i=0; i<moGoal_Output.size(); i++) {
			
			clsSecondaryDataStructureContainer oSDSC = moGoal_Output.get(i);
			String oDesc = oSDSC.toString();
			String[] oEntries = oDesc.split("\\|");

			double rPleasure = 0;
			int found = 0;
			for (String oE:oEntries) {
				String[] oParts = oE.split(":");
				int len = oParts.length;
				if (len != 2) {
					continue;
				}
				
				if (oKeyWords.contains(oParts[0])) {
					double rTemp = oValues.get(oParts[1]);
					rPleasure += rTemp;
					found++;
				}
			}
			
			if (found != 2 && found != 1) {
				throw new java.lang.Exception("could not find one or two drive demand validations in '"+oDesc+"'. candidates are: "+oKeyWords);
			}
			
			rPleasure /= found;
			
			
			
			ArrayList<clsSecondaryDataStructureContainer> oAL;
			if (oSortedList.containsKey(rPleasure)) {
				oAL = oSortedList.get(rPleasure);
			} else {
				oAL = new ArrayList<clsSecondaryDataStructureContainer>();
				oSortedList.put(rPleasure, oAL);
			}
			oAL.add(oSDSC);
		}
		
	//	badVoodoo(oSortedList); //FIXME (kohlhauser): TD 2011/05/01 - bad voodoo!!!
		
		moGoal_Output.clear();
		NavigableSet<Double> oSLdKS = oSortedList.descendingKeySet();
		Iterator<Double> it = oSLdKS.iterator();
		while (it.hasNext()) {
			Double oKey = it.next();
			ArrayList<clsSecondaryDataStructureContainer> oList = oSortedList.get(oKey);
			for (clsSecondaryDataStructureContainer oTemp:oList) {
				moGoal_Output.add(oTemp);
			}
		}
	}
	
	private void badVoodoo(TreeMap<Double, ArrayList<clsSecondaryDataStructureContainer> > poSortedList) {
		//FIXME : remove this method!!!
		//TD 2011/05/01 - remove nourish or bit if sleep, repress, deposit, relax is at the same importance level
		//but nothing is visible - very bad voodoo! the problem is that if the agent is very hungry he is doing nothing
		//else any more than search for food!!!!
		
		//check first if nothing is in the reality perception list - precondition for this bad voodoo!!!
		if (moRealityPerception.size() > 0) {
			return; //nothing to do!
		}
		
		ArrayList<clsSecondaryDataStructureContainer> oList = poSortedList.get( poSortedList.lastKey() );
		ArrayList<clsSecondaryDataStructureContainer> oDeleteCandidates = new ArrayList<clsSecondaryDataStructureContainer>();
		
		if (oList.size() > 1) { // if only one entry present, the other drives are not as important!
			for (int i=0; i<oList.size(); i++) {
				clsSecondaryDataStructureContainer oSDSC = oList.get(i);
				clsWordPresentation oWP = (clsWordPresentation)oSDSC.getMoDataStructure();
				String oText = oWP.toString();
				if (oText.contains("GOAL:BITE") || oText.contains("GOAL:NOURISH")) {
					oDeleteCandidates.add(oSDSC);
				}
			}
		}
		
	}
	
	
	
	/**
	 * DOCUMENT (kohlhauser) -
	 *
	 * @author kohlhauser
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
		
		if(oMaxDemand != null){
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
	}

	/**
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
	 * 18.04.2011, 23:12:13
	 *
	 */
	private void compriseRuleList() {
		//HZ 16.04.2011 - Here, the evaluation of received SUPER-Ego rules must be incorporated to the goal decision
		//obsolete for v38
//		for(clsAct oRule : moRuleList){
//			
//			int nUnpleasureIntensity = getUnpleasureIntensity (oRule); 
//			ArrayList<clsSecondaryDataStructureContainer> oObsoleteGoals = getObsoleteGoals (nUnpleasureIntensity, oRule); 
//			ArrayList<clsSecondaryDataStructureContainer> oObsoleteDrives = getObsoleteDrives(oRule); 
//			deleteObsoleteGoals(oObsoleteGoals); 
//			deleteObsoleteDrives(oObsoleteDrives); 
//		}
	}
	
	/**
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
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
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
	 * 25.04.2011, 14:54:15
	 *
	 * @param oRule
	 * @return
	 */
	private ArrayList<clsSecondaryDataStructureContainer> getObsoleteDrives(clsAct poRule) {

		ArrayList <clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		String oRuleContent = poRule.getMoContent().substring(0, poRule.getMoContent().indexOf(_Delimiter03));
		
		//FIXME (kohlhauser): TD 2011/04/30 bad hack - it seems that a drive demand is removed as soon as there
		//exists a superego rule for that drive. deposit has been removed regardless of the current drive tension
		//and the entered unpleasure in the superego rule. 
		//bugfix: extract unpleasure intesity from string and get nRuleIntensisty. the same is done for each drive
		//from drivelist. as soon as drive names match AND the drives intensity is larger than the unpleasure of
		//the superego rule, the drive is NOT added to the obsolete drives list!
		int pos = poRule.getMoContent().indexOf("CONTENT:UNPLEASURE|INTENSITY:")+"CONTENT:UNPLEASURE|INTENSITY:".length();
		String oRuleUnpleasure = poRule.getMoContent().substring(pos);
		oRuleUnpleasure = oRuleUnpleasure.substring(0, oRuleUnpleasure.indexOf("|"));
		int nRuleIntensity = eAffectLevel.valueOf(oRuleUnpleasure).ordinal();
		
		for(clsSecondaryDataStructureContainer oDrive : moDriveList){
			String[] oTemp = ((clsWordPresentation)oDrive.getMoDataStructure()).getMoContent().split(":");
			String oDriveContent = oTemp[0];
			String oDriveUnpleasure = oTemp[1];
			int nDriveIntensity = eAffectLevel.valueOf(oDriveUnpleasure).ordinal();
						
			if(oDriveContent.contains(oRuleContent) && nDriveIntensity<=nRuleIntensity){ 
//			if(oDriveContent.contains(oRuleContent)){
				//TD 2011/04/30: remove drive from list iff the drives instensity is eauql or lower than the punishment of the superego rule
				oRetVal.add(oDrive); 
			}
		}
		
		return oRetVal; 
	}

	/**
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
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
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
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
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
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
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
	 * 19.04.2011, 07:17:23
	 *
	 */
	private void compriseDrives() {
	   // In case moGoal_output was not filled, the drive with the highest priority used as output
		ArrayList<clsAssociation> oAssociatedDS = new ArrayList<clsAssociation>();
		clsPair<String, clsSecondaryDataStructureContainer> oMaxDemand = getDriveMaxDemand(); 
		
		if(oMaxDemand != null) { //HZ if-statment should be obsolete in case input parameters are adapted
			String oDriveContent = oMaxDemand.a; 
			clsSecondaryDataStructureContainer oDriveContainer = oMaxDemand.b;
			
			if( moGoal_Output.size() == 0 ){
				String oGoalContent = oDriveContent.substring(0,oDriveContent.indexOf(_Delimiter01)) + _Delimiter02; 
				clsWordPresentation oGoal = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("GOAL", oGoalContent));
				oAssociatedDS.addAll(oDriveContainer.getMoAssociatedDataStructures()); 
				moGoal_Output.add(new clsSecondaryDataStructureContainer(oGoal, oAssociatedDS));
			}
		}
	}
	

	/**
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
	 * 02.11.2010, 17:04:15
	 * @param moDriveList2
	 * @return
	 */
	private clsPair<String, clsSecondaryDataStructureContainer> getDriveMaxDemand() {
		clsPair <String, clsSecondaryDataStructureContainer> oRetVal = null;
		
		TreeMap<Integer, ArrayList< clsPair<String, clsSecondaryDataStructureContainer>>> oSortedDrives = new TreeMap<Integer, ArrayList<clsPair<String,clsSecondaryDataStructureContainer>>>();	
		
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
		ArrayList< clsPair<String, clsSecondaryDataStructureContainer> > oList = new ArrayList<clsPair<String,clsSecondaryDataStructureContainer>>(); 
		
		if(oSortedDrives.size() > 0){
			oList = oSortedDrives.lastEntry().getValue();
		}
		
		if (oList.size() == 1) { //case oList.size() == 0 is dealt with throw statement at the end of the method
			//trivial case
			oRetVal = oList.get(0);
		} else if (oList.size() > 1){
			//priorize sleep. currently, the agent cannot die -> sleeping is more important than eating.
			//TD 2011/05/01 - added deposit and repress
			//FIXME (kohlhauser)- this function is bad voodoo
			ArrayList<String> oPriorityDrives = new ArrayList<String>( Arrays.asList("SLEEP", "RELAX", "DEPOSIT", "REPRESS") );
			
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
			//HZ - should be reactivated in case input parameters are adapted
			//throw new NullPointerException("no drive demand found -- impossible"); 
		}
		
		return oRetVal; 
	}
	
	/**
	 * Use expectations as goals. They have the correct acts in their associated memories.
	 * (wendt)
	 *
	 * @since 23.07.2011 14:01:12
	 *
	 * @param poExtractedPrediction_IN
	 * @return
	 */
	private ArrayList<clsSecondaryDataStructureContainer> comprisePrediction(ArrayList<clsPrediction> 
			poExtractedPrediction_IN) {
		ArrayList<clsSecondaryDataStructureContainer> oRetVal = new ArrayList<clsSecondaryDataStructureContainer>();
		//Get the expectations of the acts, and make them to goals
		
		ArrayList<clsAssociation> oAssociatedDS = new ArrayList<clsAssociation>();
		String oGoalContent; 
		clsWordPresentation oGoal = null; 
		
		//Get the drive with the higest value
		clsPair<String, clsSecondaryDataStructureContainer> oMaxDemand = getDriveMaxDemand(); 
		
		//find this drive
		
		if(oMaxDemand != null){
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
		
		
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:33
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_8(moGoal_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:55:10
	 * 
	 * @see pa.interfaces.send.I7_1_send#send_I7_1(java.util.HashMap)
	 */
	@Override
	public void send_I6_8(ArrayList<clsSecondaryDataStructureContainer> poGoal_Output) {
		((I6_8_receive)moModuleList.get(52)).receive_I6_8(poGoal_Output);
		
		putInterfaceData(I6_8_send.class, poGoal_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:36
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (kohlhauser) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:36
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (kohlhauser) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 03.03.2011, 16:51:39
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 20.04.2011, 08:46:04
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Demands provided by reality, drives, and Superego are merged. The result is evaluated regarding which resulting wish can be used as motive for an action tendency. The list of produced motives is ordered according to their satisability.";
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 03.05.2011, 17:46:45
	 * 
	 * @see pa._v38.interfaces.modules.I6_1_receive#receive_I6_1(java.util.ArrayList)
	 */
	@Override
	public void receive_I6_1(
			ArrayList<clsSecondaryDataStructureContainer> poPerception, ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary) {
		// TODO (kohlhauser) - Auto-generated method stub
		
	}
	
}

