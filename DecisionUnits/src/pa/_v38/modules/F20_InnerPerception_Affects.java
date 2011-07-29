/**
 * E20_InnerPerception_Affects.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:40:29
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.interfaces.modules.I5_17_receive;
import pa._v38.interfaces.modules.I5_16_receive;
import pa._v38.interfaces.modules.I6_5_receive;
import pa._v38.interfaces.modules.I6_4_receive;
import pa._v38.interfaces.modules.I6_2_receive;
import pa._v38.interfaces.modules.I6_2_send;
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAffect;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;

/**
 * DOCUMENT (gelbard)
 *   		
 * input:
 * clsPrimaryDataStructureContainer
 *   will contain quota of affect for drives
 *            and quota of affect for perceptions
 *            
 * output:
 * output data structure will be clsSecondaryDataStructureContainer
 *        will contain word presentations with the following content:  
 * output = f(quota of affect for drives, quota of affect for perceptions)
 *                      
 * according to 2 thresholds the output will be on of 3 possible affects:
 * moContent = "ANXIETY" (Angst)
 * or
 * moContent = "WORRIEDNESS" (Ängstlichkeit = leichte Angst)
 * or
 * moContent = "PRICKLE" (Kribbeln = ganz, ganz leichte Angst)	 
 * 
 * @author deutsch, gelbard
 * 11.08.2009, 14:40:29
 * 
 */
public class F20_InnerPerception_Affects extends clsModuleBase implements 
					I5_17_receive, I5_16_receive, I6_5_receive, I6_4_receive,  I6_9_receive, I6_2_send {
	public static final String P_MODULENUMBER = "20";
	
	private ArrayList<clsPrimaryDataStructureContainer> moAffectOnlyList_Input;
	//private ArrayList<clsAssociationDriveMesh> moDeniedAffects_Input;
	//private ArrayList<clsSecondaryDataStructureContainer> moPerception; 
	//private ArrayList<clsSecondaryDataStructureContainer> moDriveList_Input;
	
	private ArrayList<clsSecondaryDataStructureContainer> moSecondaryDataStructureContainer_Output = new ArrayList<clsSecondaryDataStructureContainer>();

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:45:56
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F20_InnerPerception_Affects(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
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
		String text = "";
		
		text += toText.listToTEXT("moAffectOnlyList_Input", moAffectOnlyList_Input);
		text += toText.listToTEXT("moSecondaryDataStructureContainer_Output", moSecondaryDataStructureContainer_Output);

		return text;
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
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
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
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_17(ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		//moAffectOnlyList_old = (ArrayList<clsAffectTension>)this.deepCopy(poAffectOnlyList_old);
		moAffectOnlyList_Input = (ArrayList<clsPrimaryDataStructureContainer>)this.deepCopy(poAffectOnlyList);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_16(ArrayList<clsAssociationDriveMesh> poDeniedAffects) {
		//moDeniedAffects_Input_old  = (ArrayList<clsAffectTension>)this.deepCopy(poDeniedAffects_old);
		//moDeniedAffects_Input  = (ArrayList<clsAssociationDriveMesh>)this.deepCopy(poDeniedAffects);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_3#receive_I5_3(int)
	 */
	@Override
	public void receive_I6_5(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		//moDriveList_Input_old = (ArrayList<clsSecondaryInformation>)this.deepCopy(poDriveList_old);
		//moDriveList_Input = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poDriveList);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_4#receive_I5_4(int)
	 */
	@Override
	public void receive_I6_4(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		//moPerception_old = (ArrayList<clsSecondaryInformation>)this.deepCopy(poPerception_old);
		//moPerception = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:08
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		// calculate average of separated quota of affect for drives and perceptions
		double poAverageQuotaOfAffect_Input = calculateQuotaOfAffect(moAffectOnlyList_Input);
		
		// convert quota of affect of the primary process into affect of the secondary process according to 2 thresholds
		clsWordPresentation poAffect = calculateAffect(poAverageQuotaOfAffect_Input);
		
		// add the calculated word-presentation with empty associations
		// TODO FG: Which associations can be generated for ANXIETY, WORRIEDNESS, or PRICKL (for now the associations are empty)
		moSecondaryDataStructureContainer_Output.add(new clsSecondaryDataStructureContainer(poAffect, new ArrayList<clsAssociation>()));

	    // TODO FG: Hand over moSecondaryDataStructureContainer_Output to F26 and F29
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 21.07.2011, 16:16:08
	 * 
	 * calculates the sum of the separated quotas of affect
	 * 
	 */
	private double calculateQuotaOfAffect(ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList_Input) {
		
		double poAverageQuotaOfAffect = 0;
		
		for(clsPrimaryDataStructureContainer oContainer : poAffectOnlyList_Input){
			
			// if oContainer (element of moAffectOnlyList_Input) is an affect
			// add pleasure-values of the affect
			// TODO FG: The formula to calculate ANXIETY must be improved.
			if(oContainer.getMoDataStructure() instanceof clsAffect){
				poAverageQuotaOfAffect += ((clsAffect) oContainer.getMoDataStructure()).getPleasure();
			}					
		}
		
		return poAverageQuotaOfAffect;
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 21.07.2011, 18:18:08
	 * 
	 */
	private clsWordPresentation calculateAffect(double oAverageQuotaOfAffect) {
		clsWordPresentation oAffect;
			
		if (oAverageQuotaOfAffect < 0.3) {
			oAffect = (clsWordPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("AFFECT", "PRICKLE")); 
		}
		else if (oAverageQuotaOfAffect < 0.7) {
			oAffect = (clsWordPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("AFFECT", "WORRIEDNESS")); 
		}
		else {
			oAffect = (clsWordPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>("AFFECT", "ANXIETY")); 
		}
		
		return oAffect;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:08
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_2(moSecondaryDataStructureContainer_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:46:11
	 * 
	 * @see pa.interfaces.send.I5_5_send#send_I5_5(int)
	 */
	@Override
	public void send_I6_2(ArrayList<clsSecondaryDataStructureContainer> moSecondaryDataStructureContainer_Output) {
		((I6_2_receive)moModuleList.get(29)).receive_I6_2(moSecondaryDataStructureContainer_Output);
		((I6_2_receive)moModuleList.get(26)).receive_I6_2(moSecondaryDataStructureContainer_Output);
		
		putInterfaceData(I6_2_send.class, moSecondaryDataStructureContainer_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:00
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
	 * 12.07.2010, 10:47:00
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:46:02
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
		moDescription = "Until now, only quota of affects attached to thing presentations were available. Although the value of these quota of affects has immediate and strong influence on decision making they cannot become conscious. The qualitative counterpart of the quota of affects in the primary processes is the affect in the secondary processes. The affect is represented by a word presentation and thus can become conscious. Two different groups of affects are generated. Based on the output of the defense mechanisms, a set of affects is built. For these no explanation on their origin is available; they cannot be grasped. The other set uses the output of {E8} and {E21}. With the addition of word presentations ``explaining'' the contents attached to the quota of affects, the origin of the affect can be understood up to some extent. This results in more differentiated moods like unlust, fear, joy, sadness.";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 03.05.2011, 17:38:53
	 * 
	 * @see pa._v38.interfaces.modules.I6_9_receive#receive_I6_9(java.util.ArrayList)
	 */
	@Override
	public void receive_I6_9(ArrayList<clsSecondaryDataStructureContainer> poActionCommands, ArrayList<clsDataStructureContainer> poAssociatedMemories) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}	
}
