/**
 * E20_InnerPerception_Affects.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:40:29
 */
package secondaryprocess.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import base.datatypes.clsAffect;
import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsWordPresentation;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshAimOfDrive;
import base.datatypes.clsWordPresentationMeshFeeling;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.datatypes.helpstructures.clsPair;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import memorymgmt.enums.eEmotionType;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I5_23_receive;
import modules.interfaces.I6_14_receive;
import modules.interfaces.I6_14_send;
import modules.interfaces.I6_2_receive;
import modules.interfaces.I6_2_send;
import modules.interfaces.I6_4_receive;
import modules.interfaces.I6_5_receive;
import modules.interfaces.eInterfaces;

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
					I5_23_receive, I6_5_receive, I6_4_receive, I6_2_send, I6_14_send {
	public static final String P_MODULENUMBER = "20";
	
    private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
    
    private double mrModuleStrength;
    private double mrInitialRequestIntensity;
	
	//private enum affect {CONFLICT, ANXIETY, WORRIEDNESS, PRICKLE}; // These affects can be sent to secondary process by F20
	private ArrayList<clsPrimaryDataStructure> moAffectOnlyList_Input;
	//private ArrayList<clsAssociationDriveMesh> moDeniedAffects_Input;
	//
	//private ArrayList<clsSecondaryDataStructureContainer> moPerception; 
	//private ArrayList<clsSecondaryDataStructureContainer> moDriveList_Input;
	
	private ArrayList<clsWordPresentationMeshFeeling> moSecondaryDataStructureContainer_Output = new ArrayList<clsWordPresentationMeshFeeling>();

	private ArrayList<clsEmotion> moEmotions_Input;
	private ArrayList<clsEmotion> moEmotions_Output;
	private ArrayList<clsWordPresentationMeshFeeling> moFeelings = new ArrayList<clsWordPresentationMeshFeeling> ();
	
	private clsWordPresentationMesh moWordingToContext;
    
	ArrayList <clsEmotion> oEmotion =new ArrayList <clsEmotion> ();
	ArrayList<clsWordPresentationMesh> oWPMEmotion = new ArrayList<clsWordPresentationMesh>();
	
	ArrayList<String> Test= new ArrayList<String>();
	ArrayList<String> Test1= new ArrayList<String>();
	private ArrayList<clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentation>>> moFeelingsAssociatedMemories_OUT = new ArrayList<clsPair<clsWordPresentationMesh, ArrayList<clsWordPresentation>>>(); 
	private clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTimeMemory;
	private final DT3_PsychicIntensityStorage moPsychicEnergyStorage;

	private boolean add;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	/**
	 * DOCUMENT (gelbard) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:45:56
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poPersonalityParameterContainer 
	 * @throws Exception
	 */
	public F20_CompositionOfFeelings(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poMemory,
			DT3_PsychicIntensityStorage poPsychicEnergyStorage, clsPersonalityParameterContainer poPersonalityParameterContainer,
			clsShortTermMemory<clsWordPresentationMeshMentalSituation> poShortTimeMemory, int pnUid) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poMemory, pnUid);
		
	    mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F20", P_MODULE_STRENGTH).getParameterDouble();
	    mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F20", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
        
        moShortTimeMemory = poShortTimeMemory;
        
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
		text += toText.listToTEXT("moFeelings", moFeelings);

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
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_23(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions, clsWordPresentationMesh moWordingToContext2) {
		//moDeniedAffects_Input  = (ArrayList<clsAssociationDriveMesh>)this.deepCopy(poDeniedAffects);
	    moAffectOnlyList_Input = (ArrayList<clsPrimaryDataStructure>)this.deepCopy(poAffectOnlyList);
	    
	    moWordingToContext = moWordingToContext2;
	    
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
	public void receive_I6_5(ArrayList<clsWordPresentationMeshAimOfDrive> poDriveList) {
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
		}else{
			
			// This function is called to convert the Emotions to WPM, each Emotion has own WPM 
			moSecondaryDataStructureContainer_Output = CreateWPMForEmotions(moEmotions_Input);
		}
		
		//After all is said and done, store copies of the newly created feelings in short term memory, so they can be accessed by F47 to send to the next cycle
		for(clsWordPresentationMeshFeeling oFeeling : moSecondaryDataStructureContainer_Output) {
		    moShortTimeMemory.getNewestMemory().b.addFeeling(oFeeling);
		}
		
        double rRequestedPsychicIntensity = 0.0;
            
        double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
        
        double rConsumedPsychicIntensity = rReceivedPsychicEnergy;
        
        moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, rRequestedPsychicIntensity, rConsumedPsychicIntensity);
		
		
		//}
	}
	//clone  Emotions -
	private ArrayList<clsEmotion> clone(ArrayList<clsEmotion> oEmotions) {
		// deep clone: oEmotions --> oClonedEmotions
		ArrayList<clsEmotion> oClonedEmotions = new ArrayList<clsEmotion>();

	    for (clsEmotion oOneEmotion : oEmotions) {
			try {
				oClonedEmotions.add( (clsEmotion) oOneEmotion.clone(new HashMap<clsDataStructurePA, clsDataStructurePA>()));
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
	private ArrayList<clsWordPresentationMeshFeeling> CreateWPMForEmotions(ArrayList<clsEmotion> poEmotions){
		
		
		clsWordPresentationMeshFeeling oFeeling = null;
				
		for (clsEmotion oEmotion:  poEmotions){
			//create clsWordPresentationMeshFeeling from clsEmotion
		    oFeeling = new clsWordPresentationMeshFeeling(oEmotion);
		    
		    //if successfully created, store the new feeling
		    if(oFeeling != null && !oFeeling.isNullObject()) {
		        moFeelings.add(oFeeling);
		    }
			
			
//				// Relate the Emotion to the Feeling which have Data Structure clsWordPresentationMesh, each Emotion has own Feeling
//			
//				oFeeling = new clsWordPresentationMeshFeeling(new clsTriple<Integer, eDataType, eContentType>
//						  (-1, eDataType.WPM, eContentType.ASSOCIATIONWP),new ArrayList<clsAssociation>(), oEmotion.getContent().toString());
//				
//				// Create Association WordPresentaion from clsEmotion
//				clsAssociationWordPresentation oWPAssEmotion = new clsAssociationWordPresentation(new clsTriple<Integer, eDataType, eContentType>
//																(-1, eDataType.ASSOCIATIONWP, eContentType.ASSOCIATIONWP), oFeeling, oEmotion);
//				
//				
//				oWPAssEmotionList.add((clsAssociation) oWPAssEmotion);
//				
//				
//				// ALL the Parameters of Emotion (prSourcePleasure, Intensity, prSourceUnpleasure, prSourceLibid, prSourceAggr) are converted to WordPresentaion 
//				// and they are related with WPM over AssociationSecondary
//				
//				oWPIntensity = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>
//								(-1,eDataType.WP,eContentType.ASSOCIATIONEMOTION), "Intensity= "+String.valueOf(oEmotion.getEmotionIntensity()));
//				
//				
//				clsMeshTools.createAssociationSecondary(oFeeling, 2,oWPIntensity, 0, 1.0,eContentType.ASSOCIATIONSECONDARY,ePredicate.HASPART, false);
//				
//				
//				oWPSourcePleasure = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>
//									(-1,eDataType.WP,eContentType.ASSOCIATIONEMOTION), "SourcePleasure= "+String.valueOf(oEmotion.getSourcePleasure())); 
//				
//				clsMeshTools.createAssociationSecondary(oFeeling, 2,oWPSourcePleasure, 0, 1.0,eContentType.ASSOCIATIONSECONDARY,ePredicate.HASPART, false);
//				
//				
//				oWPSourceUnpleasure = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>
//									(-1,eDataType.WP,eContentType.ASSOCIATIONEMOTION), "SourceUnpleasure= "+String.valueOf(oEmotion.getSourceUnpleasure())); 
//				
//				clsMeshTools.createAssociationSecondary(oFeeling, 2,oWPSourceUnpleasure, 0, 1.0,eContentType.ASSOCIATIONSECONDARY,ePredicate.HASPART, false);
//								
//				oWPSourceLibid = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>
//									(-1,eDataType.WP,eContentType.ASSOCIATIONEMOTION), "SourceLibid= "+String.valueOf(oEmotion.getSourceLibid()));
//				
//				clsMeshTools.createAssociationSecondary(oFeeling, 2,oWPSourceLibid, 0, 1.0,eContentType.ASSOCIATIONSECONDARY,ePredicate.HASPART, false);
//								
//				oWPSourceAggr = new clsWordPresentation(new clsTriple<Integer, eDataType, eContentType>
//									(-1,eDataType.WP,eContentType.ASSOCIATIONEMOTION), "SourceAggr= "+String.valueOf(oEmotion.getSourceAggr()));
//				
//				clsMeshTools.createAssociationSecondary(oFeeling, 2,oWPSourceAggr, 0, 1.0,eContentType.ASSOCIATIONSECONDARY,ePredicate.HASPART, false);
//				
//			
//				
//				
//				moFeelings.add(oFeeling);
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
	
	
	private ArrayList<clsPair<clsWordPresentationMesh,ArrayList<clsWordPresentation>>> WPMFeelingsAssociations(ArrayList<clsWordPresentationMeshFeeling> poFeelings){
		
		ArrayList<clsPair<clsWordPresentationMesh,ArrayList<clsWordPresentation>>> poFeelingsAssociatedMemories_OUT = new ArrayList<clsPair<clsWordPresentationMesh,ArrayList<clsWordPresentation>>> ();
		clsWordPresentationMesh oWPMforFeeling = null;
		clsPair<clsWordPresentationMesh,ArrayList<clsWordPresentation>> oWPMASS = null;
		clsWordPresentation oAssLeafElement = null;
		
		for(clsWordPresentationMesh oWPM: poFeelings){
			
			if (oWPM instanceof clsWordPresentationMesh) {
				
				ArrayList<clsWordPresentation> oWPListforFeeling  = new ArrayList<clsWordPresentation>();
			
				for (clsAssociation oAss : oWPM.getExternalAssociatedContent()){
				
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
	private ArrayList<clsWordPresentationMeshFeeling> calculateAffect(double oAverageQuotaOfAffect,ArrayList<clsEmotion> poEmotions) {
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
				if ((oOneEmotion.getContent()== eEmotionType.ANXIETY)){
					oOneEmotion.setEmotionIntensity(oIntensityOfAnxiety);
				}
				
			}
			
		}
		return CreateWPMForEmotions(poEmotions);
		
	}
	// Search of the emotion types if they exist
	private boolean searchInEmotions (eEmotionType oEmotionType) {	
		
	   	for(clsEmotion oOneEmotion : moEmotions_Output) {
	   		if(oOneEmotion.getContent() == oEmotionType) {
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
					if ((moEmotionType == eEmotionType.MOURNING) && (oOneEmotion.getContent() == eEmotionType.MOURNING)){
						oEmotionIntensity = oOneEmotion.getEmotionIntensity();
					}
			    }
				if(searchInEmotions (eEmotionType.ANXIETY)){
					if ((moEmotionType == eEmotionType.ANXIETY)&&(oOneEmotion.getContent() == eEmotionType.ANXIETY)){
						oEmotionIntensity = oOneEmotion.getEmotionIntensity();
					}
				}
			     
			    if(searchInEmotions(eEmotionType.ANGER)){
			    	if ((moEmotionType == eEmotionType.ANGER)&&(oOneEmotion.getContent() == eEmotionType.ANGER)){
			    		 oEmotionIntensity = oOneEmotion.getEmotionIntensity();
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
		send_I6_14(moEmotions_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:46:11
	 * 
	 * @see pa.interfaces.send.I5_5_send#send_I5_5(int)
	 */
	@Override
	public void send_I6_2(ArrayList<clsWordPresentationMeshFeeling> moSecondaryDataStructureContainer_Output) {
		((I6_2_receive)moModuleList.get(29)).receive_I6_2(moSecondaryDataStructureContainer_Output);
		((I6_2_receive)moModuleList.get(26)).receive_I6_2(moSecondaryDataStructureContainer_Output);
		
		putInterfaceData(I6_2_send.class, moSecondaryDataStructureContainer_Output);
	}
	
	 /* (non-Javadoc)
    *
    * @since Jul 5, 2013 2:46:56 PM
    * 
    * @see pa._v38.interfaces.modules.I6_14_send#send_I6_14(java.util.ArrayList)
    */
   @Override
   public void send_I6_14(ArrayList<clsEmotion> poEmotions_Input) {
       // TODO (schaat) - Auto-generated method stub
       ((I6_14_receive)moModuleList.get(67)).receive_I6_14(poEmotions_Input);
       //IH
       ((I6_14_receive)moModuleList.get(30)).receive_I6_14(poEmotions_Input);
       
       putInterfaceData(I6_14_send.class, poEmotions_Input);
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
}
