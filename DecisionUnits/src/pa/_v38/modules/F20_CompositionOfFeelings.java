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
import du.enums.eInternalActionIntensity;
import du.itf.actions.clsInternalActionCommand;
import du.itf.actions.clsInternalActionSweat;
import du.itf.actions.itfInternalActionProcessor;
import pa._v38.interfaces.modules.I5_17_receive;
import pa._v38.interfaces.modules.I5_16_receive;
import pa._v38.interfaces.modules.I6_5_receive;
import pa._v38.interfaces.modules.I6_4_receive;
import pa._v38.interfaces.modules.I6_2_receive;
import pa._v38.interfaces.modules.I6_2_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.itfModuleMemoryAccess;
//import pa._v38.memorymgmt.datahandlertools.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAffect;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
//import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
//import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
//import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
//import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
//import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
//import pa._v38.memorymgmt.datatypes.clsAssociationSecondary;
//import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
//import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsEmotion;
//import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;

//import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructure;
//import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
//import pa._v38.memorymgmt.enums.eAction;
//import pa._v38.memorymgmt.enums.eAffectLevel;
//import pa._v38.memorymgmt.enums.eContent;
//import pa._v38.memorymgmt.enums.eAction;
//import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
//import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePredicate;
//import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
//import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
//import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

/**
 * - Converts separated quota of affect into affects for the secondary process.
 * - Converts Emotions to Word presentation for the secondary process
 * More precisely, it converts the separated quota of affect for drives 
 * from the primary process into the affect anxiety in the secondary process by increasing the intensity of Anxiety.
 * 
 * <br />  		
 * Input: - clsPrimaryDataStructureContainer which contains quota of affect for drives 
 * 		  - clsEmotion which contains Emotions
 * <br />           
 * Output: clsSecondaryDataStructureContainer which contains word presentations with the following content:
 * 			MOURNING, ANXIETY, ANGER, JOY
 * <br />               
 * * 
 * @author gelbard  and Lotfi
 * 07.05.2012, 14:40:29 and  23.04.2013 18:14:45
 * * 
 */
public class F20_CompositionOfFeelings extends clsModuleBaseKB implements 
					I5_17_receive, I5_16_receive, I6_5_receive, I6_4_receive, I6_2_send {
	public static final String P_MODULENUMBER = "20";
	
	//private enum affect {CONFLICT, ANXIETY, WORRIEDNESS, PRICKLE}; // These affects can be sent to secondary process by F20
	private ArrayList<clsPrimaryDataStructure> moAffectOnlyList_Input;
	//private ArrayList<clsAssociationDriveMesh> moDeniedAffects_Input;
	//
	//private ArrayList<clsSecondaryDataStructureContainer> moPerception; 
	//private ArrayList<clsSecondaryDataStructureContainer> moDriveList_Input;
	
	private ArrayList<clsWordPresentationMesh> moSecondaryDataStructureContainer_Output = new ArrayList<clsWordPresentationMesh>();

	private ArrayList<clsEmotion> moEmotions_Input;
	private ArrayList<clsEmotion> moEmotions_Output;
	private ArrayList<clsWordPresentationMesh> moFeelings = new ArrayList<clsWordPresentationMesh> ();
	
	//list of internal actions, fill it with what you want to be shown
	private ArrayList<clsInternalActionCommand> moInternalActions = new ArrayList<clsInternalActionCommand>();
	ArrayList <clsEmotion> oEmotion =new ArrayList <clsEmotion> ();
	ArrayList<clsWordPresentationMesh> oWPMEmotion = new ArrayList<clsWordPresentationMesh>();
	
	ArrayList<String> Test= new ArrayList<String>();
	ArrayList<String> Test1= new ArrayList<String>();
	private ArrayList<clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentation>>> moFeelingsAssociatedMemories_OUT = new ArrayList<clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentation>>>(); 
	private clsShortTermMemory moShortTimeMemory;
	private final DT3_PsychicEnergyStorage moPsychicEnergyStorage;

	private boolean add;
	/**
	 * DOCUMENT (gelbard) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:45:56
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F20_CompositionOfFeelings(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poMemory,
			DT3_PsychicEnergyStorage poPsychicEnergyStorage) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poMemory);

		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
        
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
		text += toText.listToTEXT("moEmotions_Input", moEmotions_Input);
		text += toText.listToTEXT("moSecondaryDataStructureContainer_Output", moSecondaryDataStructureContainer_Output);
		//text += toText.listToTEXT("moFeelings", moFeelings);
		text += toText.listToTEXT("Emotion", oEmotion);
		text += toText.listToTEXT("moFeelingsAssociatedMemories_OUT", moFeelingsAssociatedMemories_OUT);

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
	public void receive_I5_17(ArrayList<clsPrimaryDataStructure> poAffectOnlyList) {
		moAffectOnlyList_Input = (ArrayList<clsPrimaryDataStructure>)this.deepCopy(poAffectOnlyList);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_16(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions) {
		//moDeniedAffects_Input  = (ArrayList<clsAssociationDriveMesh>)this.deepCopy(poDeniedAffects);	
		moEmotions_Input = (ArrayList<clsEmotion>) deepCopy(poEmotions);

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_3#receive_I5_3(int)
	 */
	@Override
	public void receive_I6_5(ArrayList<clsWordPresentationMesh> poDriveList) {
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
	public void receive_I6_4(clsWordPresentationMesh poPerception) {
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
		
		FillInternalActions(moEmotions_Input);
		moEmotions_Output = clone(moEmotions_Input);
		// calculate average of separated quota of affect for drives and perceptions
		double poAverageQuotaOfAffect_Input = calculateQuotaOfAffect(moAffectOnlyList_Input);
		
		
		
		// clear the list first
		// then create one element in the list.
		
		moSecondaryDataStructureContainer_Output.clear();
//		if (poAffect != null) {
//			
//			
//			
//			for(clsWordPresentationMesh oWPM : moSecondaryDataStructureContainer_Output) {
//				if(oWPM.getMoContent().equals(poAffect.getMoContent()))
//					
//					// if affect is already in list, do nothing.
//					return;
//			}
		if(!moAffectOnlyList_Input.isEmpty()){
			
			//convert the quota of affect for drives from the primary process into the affect anxiety 
			//in the secondary process by increasing the intensity of Anxiety
			moSecondaryDataStructureContainer_Output = calculateAffect(poAverageQuotaOfAffect_Input, moEmotions_Output);
			// This function is called to show on the Simulator the WPM's and their WP's with association parameters 
			moFeelingsAssociatedMemories_OUT = WPMFeelingsAssociassions(moFeelings);
			
		}else{
			
			// This function is called to convert the Emotions to WPM, each Emotion has own WPM 
			moSecondaryDataStructureContainer_Output = CreateWPMForEmotions(moEmotions_Input);
			
			// This function is called to show on the Simulator the WPM's and their WP's with association parameters 
			moFeelingsAssociatedMemories_OUT = WPMFeelingsAssociassions(moFeelings);
		}
		
		
		//}
	}
	//clone  Emotions -
	private ArrayList<clsEmotion> clone(ArrayList<clsEmotion> oEmotions) {
		// deep clone: oEmotions --> oClonedEmotions
				ArrayList<clsEmotion> oClonedEmotions = new ArrayList<clsEmotion>();
				ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>> poClonedNodeList = new ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>>();
				for (clsEmotion oOneEmotion : oEmotions) {
					try {
						oClonedEmotions.add( (clsEmotion) oOneEmotion.clone(poClonedNodeList));
					} catch (CloneNotSupportedException e) {
						// Auto-generated catch block
						e.printStackTrace();
					}
				}
				
				return oClonedEmotions;
	}
	
	/* (non-Javadoc)
	 *
	 * @author Lotfi
	 * 23.04.2013, 16:16:08
	 * 
	 * Create WordPresentationMesh "WPM" for Emotions, each emotion has WPM where 
	 * Emotion types --> Association WordPresentaion 
	 * prSourcePleasure, Intensity, prSourceUnpleasure, prSourceLibid, and prSourceAggr ---> WordPresentaion
	 * and their values are related to WPM over AssociationSecondary
	 * 
	 *   
	 */
	private ArrayList<clsWordPresentationMesh> CreateWPMForEmotions(ArrayList<clsEmotion> poEmotions){
		
		
		clsWordPresentationMesh oFeeling = null;
		clsWordPresentation oWPIntensity = null;
		clsWordPresentation oWPSourcePleasure = null;
		clsWordPresentation oWPSourceUnpleasure = null;
		clsWordPresentation oWPSourceLibid = null;
		clsWordPresentation oWPSourceAggr = null;
		
		ArrayList<clsAssociation> oWPAssEmotionList = new ArrayList<clsAssociation>();
				
		for (clsEmotion oEmotion:  poEmotions){
			
			
			
				// Relate the Emotion to the Feeling which have Data Structure clsWordPresentationMesh, each Emotion has own Feeling
			
				oFeeling = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>
						  (-1, eDataType.WPM, eContentType.ASSOCIATIONWP),oWPAssEmotionList, oEmotion.getMoContent().toString());
				
				// Create Association WordPresentaion from clsEmotion
				clsAssociationWordPresentation oWPAssEmotion = new clsAssociationWordPresentation(new clsTriple<Integer, eDataType, eContentType>
																(-1, eDataType.ASSOCIATIONWP, eContentType.ASSOCIATIONWP), oFeeling, oEmotion);
				
				
				oWPAssEmotionList.add((clsAssociation) oWPAssEmotion);
				
				
				// ALL the Parameters of Emotion (prSourcePleasure, Intensity, prSourceUnpleasure, prSourceLibid, prSourceAggr) are converted to WordPresentaion 
				// and they are related with WPM over AssociationSecondary
				
				oWPIntensity = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>
								(-1,eDataType.WP,eContentType.ASSOCIATIONEMOTION), "Intensity= "+String.valueOf(oEmotion.getMrEmotionIntensity()));
				
				
				clsMeshTools.createAssociationSecondary(oFeeling, 2,oWPIntensity, 0, 1.0,eContentType.ASSOCIATIONSECONDARY,ePredicate.HASPART, false);
				
				
				oWPSourcePleasure = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>
									(-1,eDataType.WP,eContentType.ASSOCIATIONEMOTION), "SourcePleasure= "+String.valueOf(oEmotion.getMrSourcePleasure())); 
				
				clsMeshTools.createAssociationSecondary(oFeeling, 2,oWPSourcePleasure, 0, 1.0,eContentType.ASSOCIATIONSECONDARY,ePredicate.HASPART, false);
				
				
				oWPSourceUnpleasure = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>
									(-1,eDataType.WP,eContentType.ASSOCIATIONEMOTION), "SourceUnpleasure= "+String.valueOf(oEmotion.getMrSourceUnpleasure())); 
				
				clsMeshTools.createAssociationSecondary(oFeeling, 2,oWPSourceUnpleasure, 0, 1.0,eContentType.ASSOCIATIONSECONDARY,ePredicate.HASPART, false);
								
				oWPSourceLibid = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>
									(-1,eDataType.WP,eContentType.ASSOCIATIONEMOTION), "SourceLibid= "+String.valueOf(oEmotion.getMrSourceLibid()));
				
				clsMeshTools.createAssociationSecondary(oFeeling, 2,oWPSourceLibid, 0, 1.0,eContentType.ASSOCIATIONSECONDARY,ePredicate.HASPART, false);
								
				oWPSourceAggr = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>
									(-1,eDataType.WP,eContentType.ASSOCIATIONEMOTION), "SourceAggr= "+String.valueOf(oEmotion.getMrSourceAggr()));
				
				clsMeshTools.createAssociationSecondary(oFeeling, 2,oWPSourceAggr, 0, 1.0,eContentType.ASSOCIATIONSECONDARY,ePredicate.HASPART, false);
				
			
				
				
				moFeelings.add(oFeeling);
		}
		
		return moFeelings;
	}
	
	/* (non-Javadoc)
	 *
	 * @author Lotfi
	 * 23.04.2013, 16:18:28
	 * 
	 * View the WPM (Feeling) of each Emotion and its WP Associations 
	 * prSourcePleasure, Intensity, prSourceUnpleasure, prSourceLibid, prSourceAggr
	 * on the Simulator ---> State 
	 *   
	 */
	
	
	private ArrayList<clsPair<clsWordPresentationMesh,ArrayList<clsWordPresentation>>> WPMFeelingsAssociassions(ArrayList<clsWordPresentationMesh> poFeelings){
		
		ArrayList<clsPair<clsWordPresentationMesh,ArrayList<clsWordPresentation>>> poFeelingsAssociatedMemories_OUT = new ArrayList<clsPair<clsWordPresentationMesh,ArrayList<clsWordPresentation>>> ();
		clsWordPresentationMesh oWPMforFeeling = null;
		clsPair<clsWordPresentationMesh,ArrayList<clsWordPresentation>> oWPMASS = null;
		clsWordPresentation oAssLeafElement = null;
		
		for(clsWordPresentationMesh oWPM: poFeelings){
			
			if (oWPM instanceof clsWordPresentationMesh) {
				
				ArrayList<clsWordPresentation> oWPListforFeeling  = new ArrayList<clsWordPresentation>();
			
				for (clsAssociation oAss : oWPM.getExternalMoAssociatedContent()){
				
					oWPMforFeeling  =  (clsWordPresentationMesh) oAss.getRootElement();
					
					oAssLeafElement= (clsWordPresentation)oAss.getLeafElement();
					
					oWPListforFeeling.add(oAssLeafElement);
				
					
				}
			
			
				oWPMASS = new clsPair<clsWordPresentationMesh,ArrayList<clsWordPresentation>>(oWPMforFeeling,oWPListforFeeling);
			
				poFeelingsAssociatedMemories_OUT.add(oWPMASS);
			}
		}
			
			
	 return poFeelingsAssociatedMemories_OUT;
	}
	
	

	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 21.07.2011, 16:16:08
	 * 
	 * calculates the sum of the separated quotas of affect
	 * 
	 */
	private double calculateQuotaOfAffect(ArrayList<clsPrimaryDataStructure> poAffectOnlyList_Input) {
		
		double poAverageQuotaOfAffect = 0;
		
		if (poAffectOnlyList_Input.isEmpty()) return 0;
		
		for(clsPrimaryDataStructure oContainer : poAffectOnlyList_Input){
			
			// if oContainer (element of moAffectOnlyList_Input) is an affect
			// add pleasure-values of the affect
			// TODO FG: The formula to calculate ANXIETY must be improved.
			if(oContainer instanceof clsAffect){
				poAverageQuotaOfAffect += ((clsAffect) oContainer).getPleasure();
			}					
		}
		
		// calculate average quota of affect
		return poAverageQuotaOfAffect / poAffectOnlyList_Input.size();
	}
	
	/* (non-Javadoc)
	 *
	 *  @author Lotfi
	 * 23.04.2013, 20:00:28
	 * 
	 * convert the quota of affect for drives from the primary process into the affect anxiety 
	 * in the secondary process by increasing the intensity of Anxiety
	 * 
	 */
	private ArrayList<clsWordPresentationMesh> calculateAffect(double oAverageQuotaOfAffect,ArrayList<clsEmotion> poEmotions) {
		ArrayList <clsEmotion> oEmotions = new ArrayList <clsEmotion>(); 
		double oIntensityOfAnxiety = 0.0;
		
		
		if (oAverageQuotaOfAffect > 0.7) {
			
			oIntensityOfAnxiety = GetEmotionIntensity(eEmotionType.ANXIETY)+ 0.3;
		}
		else if (oAverageQuotaOfAffect > 0.3) {
			oIntensityOfAnxiety = GetEmotionIntensity(eEmotionType.ANXIETY) + 0.2;
		}
		else if (oAverageQuotaOfAffect > 0){
			oIntensityOfAnxiety = GetEmotionIntensity(eEmotionType.ANXIETY) + 0.1;
		}
		else{
			oIntensityOfAnxiety = GetEmotionIntensity(eEmotionType.ANXIETY);
		}
		for(clsEmotion oOneEmotion : poEmotions) {
			if(searchInEmotions (eEmotionType.ANXIETY)){
				if ((oOneEmotion.getMoContent()== eEmotionType.ANXIETY)){
					oOneEmotion.setMrEmotionIntensity(oIntensityOfAnxiety);
				}
				
			}
			
		}
		return CreateWPMForEmotions(poEmotions);
		
	}
	// Search of the emotion types if they exist
		private boolean searchInEmotions (eEmotionType oEmotionType) {	
			
		   	for(clsEmotion oOneEmotion : moEmotions_Output) {
		   		if(oOneEmotion.getMoContent() == oEmotionType) {
		   			return true;
		   		}
		   	}
		   	
		   	return false;
		}
	// get the intensity of the emotions MOURNING, ANXIETY and ANGER 
		private double GetEmotionIntensity(eEmotionType moEmotionType){
			double oEmotionIntensity =0.0;
			for(clsEmotion oOneEmotion : moEmotions_Output) {
				
					if(searchInEmotions (eEmotionType.MOURNING)){
						if ((moEmotionType == eEmotionType.MOURNING) && (oOneEmotion.getMoContent() == eEmotionType.MOURNING)){
							oEmotionIntensity = oOneEmotion.getMrEmotionIntensity();
						}
				    }
					if(searchInEmotions (eEmotionType.ANXIETY)){
						if ((moEmotionType == eEmotionType.ANXIETY)&&(oOneEmotion.getMoContent() == eEmotionType.ANXIETY)){
							oEmotionIntensity = oOneEmotion.getMrEmotionIntensity();
						}
					}
				     
				    if(searchInEmotions(eEmotionType.ANGER)){
				    	if ((moEmotionType == eEmotionType.ANGER)&&(oOneEmotion.getMoContent() == eEmotionType.ANGER)){
				    		 oEmotionIntensity = oOneEmotion.getMrEmotionIntensity();
				    	}
				    }
		
				
			}
			return oEmotionIntensity;
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
	public void send_I6_2(ArrayList<clsWordPresentationMesh> moSecondaryDataStructureContainer_Output) {
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
	
	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 31.10.2012 12:57:55
	 *
	 * @param moEmotions_Input2
	 */
	private void FillInternalActions(ArrayList<clsEmotion> poEmotions_Input) {
		//todo fill moInternalActions with the approriate poEmotions_Inputm see PSY document per mail for what, when, where
		
		//zB:
		//Angst
		//Magen Zusammenziehen, Zittern, Schwitzen, Herzrasen – passender Gesichtsausdruck
		//Wut
		//Blutdruckanstieg (Errötung), Muskelanspannung – passender Gesichtsausdruck
		
		//CM: for testing now:
		clsInternalActionSweat test = new clsInternalActionSweat(eInternalActionIntensity.HEAVY);
		//moInternalActions.add( test );
		
	}

	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 31.10.2012 12:54:43
	 *
	 * @param poInternalActionContainer
	 */
	public void getBodilyReactions(	itfInternalActionProcessor poInternalActionContainer) {
		
		for( clsInternalActionCommand oCmd : moInternalActions ) {
			poInternalActionContainer.call(oCmd);
		}
	}

}
