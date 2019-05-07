/**
 * F90_LearningQoA.java: DecisionUnits - pa._v38.modules
 * For generated drive candidates (vector affect values), drive objects and actions (drive aims) are remembered (for the satisfaction of needs 
 * 
 * @author fittner
 * 05.08.2011, 10:20:03
 */
package primaryprocess.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import memorymgmt.enums.eActivationType;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eEmotionType;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsShortTermMemoryEntry;
import base.datatypes.clsShortTermMemoryMF;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;

/**
 * DOCUMENT (fittner)  
 * 
 * @author fittner
 * 05.08.2011, 10:23:13
 *  
 */
public class F90_Learning extends clsModuleBaseKB {

	public static final String P_MODULENUMBER = "90";
	private ArrayList<clsDriveMesh> moLearningStorage_DM = new ArrayList<clsDriveMesh>();
	public static ArrayList<String> ArrayChanges = new ArrayList<String>();
	ArrayList<clsDriveMesh> moLearningLTM_DM = new ArrayList<clsDriveMesh>();
	ArrayList<clsDriveMesh> moLearningLTMred_DM = new ArrayList<clsDriveMesh>();
	private clsShortTermMemoryMF moSTM_Learning;
	private clsShortTermMemoryEntry moLTM_Learning;
	private clsShortTermMemoryEntry moSTM_LearningEntry;
	/**
	 * DOCUMENT (fittner) 
	 * 
	 * @author fittner
	 * 03.07.2018, 15:52:25
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poKnowledgeBaseHandler
	 * @throws Exception
	 */
	public F90_Learning(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			itfModuleMemoryAccess poLongTermMemory, 
			clsShortTermMemoryMF poSTM_Learning,
			clsPersonalityParameterContainer poPersonalityParameterContainer,
			int pnUid) throws Exception
	{
			super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory, pnUid);
		    applyProperties(poPrefix, poProp);
		    moSTM_Learning = poSTM_Learning;
		    moLTM_Learning = new clsShortTermMemoryEntry();
	}
	   
	private void applyProperties(String poPrefix, clsProperties poProp)
	{
	        //String pre = clsProperties.addDot(poPrefix);
	    
	        //nothing to do
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="moSTM_Learning.getActualStep():"+moSTM_Learning.getActualStep()+"\n";

		
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic()
	{
	    // STM to LTM
	    // STM new Step
	    //moSTM_Learning.setActualStep(moSTM_Learning.getActualStep()+1);
	    moSTM_LearningEntry = new clsShortTermMemoryEntry();
        
	    if(moSTM_Learning.moShortTermMemoryMF.size()==0)
        {
            moSTM_Learning.moShortTermMemoryMF.add(0,new clsShortTermMemoryEntry());
        }
	    if(moSTM_Learning.getChangedMoment())
	    {   
	        String moLTM_Learning1;
	        for(clsThingPresentationMesh TPM_Object : moSTM_Learning.moShortTermMemoryMF.get(0).getLearningImage())
	        {
	            double MomentActivation = TPM_Object.getCriterionActivationValue(eActivationType.MOMENT_ACTIVATION);
	            double LearningIntensity = 1.0;
	            double LearningWeight;
	            LearningWeight = MomentActivation * LearningIntensity;
	            
	            clsThingPresentationMesh TPM_Object_LTM = null;
	            try {
                    TPM_Object_LTM = (clsThingPresentationMesh) TPM_Object.clone();
                } catch (CloneNotSupportedException e) {
                    // TODO (nocks) - Auto-generated catch block
                    e.printStackTrace();
                }
	            if(LearningWeight > 0.1)
	            {
	                for(clsThingPresentationMesh Image:moLTM_Learning.getLearningImage())
	                {
	                    if(Image.compareTo(TPM_Object) == 1.0)
	                    {
	                        TPM_Object = Image;
	                    }
	                }
	                if(LearningWeight > 1.0)
	                {
	                    LearningWeight = 1.0;
	                }
	                TPM_Object.setLearningWeight(LearningWeight);
	                clsAssociation poAssociation;
                    
	                clsEmotion EmotionRI = null;
                    for(clsAssociation Ass:TPM_Object.getExternalAssociatedContent())
                    {
                        if (Ass instanceof clsAssociationEmotion)
                        {
                            EmotionRI = (clsEmotion) Ass.getTheOtherElement(TPM_Object);
                        }
                    }
	                
	                clsEmotion Emotion =  moSTM_Learning.moShortTermMemoryMF.get(0).getEmotions().get(0);
//	                TPM_Object.addExternalAssociation(new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONEMOTION, eContentType.ASSOCIATIONEMOTION), 
//	                        Emotion, 
//	                        TPM_Object));
	                //Emotion.merge(EmotionRI);
	                clsEmotion EmotionMerge;
	                EmotionMerge = clsDataStructureGenerator.generateEMOTION(
	                        new clsTriple <eContentType, eEmotionType, Object>(
	                                Emotion.getContentType(),
	                                Emotion.getContent(),
	                                Emotion.getEmotionIntensity()),
	                                (Emotion.getSourcePleasure() + EmotionRI.getSourcePleasure())/2,
	                                (Emotion.getSourceUnpleasure() + EmotionRI.getSourceUnpleasure())/2,
	                                (Emotion.getSourceLibid() + EmotionRI.getSourceLibid())/2,
	                                (Emotion.getSourceAggr() + EmotionRI.getSourceAggr())/2);
	                TPM_Object_LTM.addExternalAssociation(new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONEMOTION, eContentType.ASSOCIATIONEMOTION), 
                            EmotionMerge, 
                            TPM_Object_LTM));
	                
	                moLTM_Learning.setLearningImage(TPM_Object_LTM);
	             }
	        }
	        moSTM_Learning.moShortTermMemoryMF.add(0,moSTM_LearningEntry);
	    }
	    else
	    {
	        moSTM_Learning.moShortTermMemoryMF.set(0,moSTM_LearningEntry);
	    }

	    // LTM load + Change from STM (new assoziations)
	    //this.getLongTermMemory().searchCompleteMesh(poInput, pnLevel)
	    // Image + Emotions
	    
	    // IMAGE + Emotions new
	    //moSTM_Learning
	    

	    
	}
	
    /**
     * Creates a DM out of the entry, and adds necessary information, source, etc
     * @throws Exception 
     *
     * @since 16.07.2012 15:20:26
     *
     */
    private clsAssociation CreateAssoziation(clsDriveMesh poDM, clsThingPresentationMesh poTPM, double prWeight, int ID) throws Exception {
        
        clsAssociation oDriveAssociationCandidate  = null;

        //create the DM
        oDriveAssociationCandidate = (clsAssociation)clsDataStructureGenerator.generateASSOCIATIONDM(poDM, poTPM, prWeight,ID);
        oDriveAssociationCandidate.setMrLearning(0.0);

        return oDriveAssociationCandidate;
    }


	
	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (fittner) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (fittner) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
		
	}

	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author fittner
	 * 02.05.2011, 15:51:51
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Learning";
		// TODO (fittner) - give a en description
		
	}

    /* (non-Javadoc)
     *
     * @since 03.07.2018 14:22:43
     * 
     * @see base.modules.clsModuleBase#send()
     */
    @Override
    protected void send() {
        // TODO (noName) - Auto-generated method stub
        
    }

}
