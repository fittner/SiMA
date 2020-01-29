/**
 * F90_LearningQoA.java: DecisionUnits - pa._v38.modules
 * For generated drive candidates (vector affect values), drive objects and actions (drive aims) are remembered (for the satisfaction of needs 
 * 
 * @author fittner
 * 05.08.2011, 10:20:03
 */
package primaryprocess.modules;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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
import prementalapparatus.modules.F31_NeuroDeSymbolizationActionCommands;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationEmotion;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsShortTermMemoryEntry;
import base.datatypes.clsShortTermMemoryMF;
import base.datatypes.clsThingPresentation;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericDynamicTimeChart;

/**
 * DOCUMENT (fittner)  
 * 
 * @author fittner
 * 05.08.2011, 10:23:13
 *  
 */
public class F90_Learning extends clsModuleBaseKB implements itfInspectorGenericDynamicTimeChart {

	public static final String P_MODULENUMBER = "90";
	private ArrayList<clsDriveMesh> moLearningStorage_DM = new ArrayList<clsDriveMesh>();
	public static ArrayList<String> ArrayChanges = new ArrayList<String>();
	ArrayList<clsDriveMesh> moLearningLTM_DM = new ArrayList<clsDriveMesh>();
	ArrayList<clsDriveMesh> moLearningLTMred_DM = new ArrayList<clsDriveMesh>();
	private clsShortTermMemoryMF moSTM_Learning;
	private clsShortTermMemoryEntry moLTM_Learning;
	private clsShortTermMemoryEntry moSTM_LearningEntry;
	private boolean mnChartColumnsChanged = true;
	private HashMap<String, Double> moDriveChartData;
	private boolean written;
	private boolean adam;
    private int lastLearn=670;

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
		    moDriveChartData =  new HashMap<String, Double>(); //initialize charts
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
		String text = "";
		//text ="Step: "+moSTM_Learning.getActualStep()+"\n";
		//text += moLTM_Learning.getLTMLearningImages();
		text += moLTM_Learning.getLearningContent();
		//text += moSTM_Learning.toString();
		
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
	    int actualStep;
	    // STM to LTM
	    // STM new Step
	    //moSTM_Learning.setActualStep(moSTM_Learning.getActualStep()+1);
	    moSTM_LearningEntry = new clsShortTermMemoryEntry();


	    actualStep = moSTM_Learning.getActualStep();
        
	    if(moSTM_Learning.moShortTermMemoryMF.size()==0)
        {
            moSTM_Learning.moShortTermMemoryMF.add(0,new clsShortTermMemoryEntry());
        }
	    for(clsThingPresentationMesh STM_Image : moSTM_Learning.moShortTermMemoryMF.get(0).getLearningImage())
        {
            double MomentActivation = STM_Image.getCriterionActivationValue(eActivationType.MOMENT_ACTIVATION);
            if(MomentActivation>0 && STM_Image.getContent().contains("A12"))
            {
                lastLearn =400;
            }
        }
	    
	    if (actualStep < lastLearn)
	    {
	        written = false;
    
    	    if(moSTM_Learning.getChangedMoment())
    	    {   
    	        String moLTM_Learning1;
    	        boolean replace=false;
    	        for(clsThingPresentationMesh STM_Image : moSTM_Learning.moShortTermMemoryMF.get(0).getLearningImage())
    	        {
    	            double MomentActivation = STM_Image.getCriterionActivationValue(eActivationType.MOMENT_ACTIVATION);
    	            double LearningIntensity = 1.0;
    	            double LearningIntensity1 = 1.0;
    	            double LearningIntensity2 = 1.0;
                    double TimeIntensity = 1.0;
                    
    	            double LearningWeight;
    	            LearningWeight = MomentActivation * LearningIntensity;
    	            
    	            clsThingPresentationMesh LTM_Image = null;
    	            try {
                        LTM_Image = (clsThingPresentationMesh) STM_Image.clone();
                    } catch (CloneNotSupportedException e) {
                        // TODO (nocks) - Auto-generated catch block
                        e.printStackTrace();
                    }
    	            if(LearningWeight > 0.1)
    	            {   replace = false;
    	                for(clsThingPresentationMesh Image:moLTM_Learning.getLearningImage())
    	                {
    	                    if(Image.compareTo(STM_Image) == 1.0)
    	                    {
    	                        STM_Image = Image;
    	                        replace = true;
    	                    }
    	                }
    	                if(LearningWeight > 1.0)
    	                {
    	                    LearningWeight = 1.0;
    	                }
    	                
    	                clsEmotion EmotionRI = null;
                        for(clsAssociation Ass:STM_Image.getExternalAssociatedContent())
                        {
                            if (Ass instanceof clsAssociationEmotion)
                            {
                                EmotionRI = (clsEmotion) Ass.getTheOtherElement(STM_Image);
                            }    
                        }
    	                
    	                clsEmotion Emotion =  moSTM_Learning.moShortTermMemoryMF.get(0).getEmotions().get(0);
    //	                TPM_Object.addExternalAssociation(new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONEMOTION, eContentType.ASSOCIATIONEMOTION), 
    //	                        Emotion, 
    //	                        TPM_Object));
    	                //Emotion.merge(EmotionRI);
    	                LTM_Image.addExternalAssociation(new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONEMOTION, eContentType.ASSOCIATIONEMOTION), 
                                Emotion, 
                                LTM_Image));
    	                clsEmotion EmotionMerge;
    	                double merdgePleasure;
    	                double merdgeUnlpeasure;
    	                double merdgeLib;
    	                double merdgeAggr;
    	                double merdgeIntensity;
    	                double EmotionSum;
    	                double EmotionRISum;
                        
    //	                merdgePleasure= (EmotionRI.getSourcePleasure() + Emotion.getSourcePleasure()*LearningIntensity*TimeIntensity)/(1+1*LearningIntensity*TimeIntensity);
    //	                merdgeUnlpeasure= (EmotionRI.getSourceUnpleasure() + Emotion.getSourceUnpleasure()*LearningIntensity*TimeIntensity)/(1+1*LearningIntensity*TimeIntensity);
    //	                merdgeLib= (EmotionRI.getSourceLibid() + Emotion.getSourceLibid()*LearningIntensity*TimeIntensity)/(1+1*LearningIntensity*TimeIntensity);
    //	                merdgeAggr=  (EmotionRI.getSourceAggr() + Emotion.getSourceAggr()*LearningIntensity*TimeIntensity)/(1+1*LearningIntensity*TimeIntensity);
    //	                merdgeIntensity = merdgeUnlpeasure;
    	                EmotionSum = Emotion.getSourcePleasure() + Emotion.getSourceUnpleasure() + Emotion.getSourceLibid() + Emotion.getSourceAggr();
    	                EmotionRISum = EmotionRI.getSourcePleasure() + EmotionRI.getSourceUnpleasure() + EmotionRI.getSourceLibid() + EmotionRI.getSourceAggr();
    	                
    	                double PleRI,UnpRI,LibRI,AggRI;
    	                double PleEm,UnpEm,AggEm,LibEm;
    	                PleRI = EmotionRI.getSourcePleasure();
                        UnpRI = EmotionRI.getSourceUnpleasure();
                        LibRI = EmotionRI.getSourceLibid();
                        AggRI = EmotionRI.getSourceAggr();
                        PleEm = Emotion.getSourcePleasure();
                        UnpEm = Emotion.getSourceUnpleasure();
                        LibEm = Emotion.getSourceLibid();
                        AggEm = Emotion.getSourceAggr();
                        //double Anpassungsfaktor=0.4;
                        LearningIntensity = (PleEm*PleEm+UnpEm*UnpEm)/((UnpEm+PleEm));
                        LearningIntensity1 = PleEm + UnpEm + LibEm + AggEm;
                        LearningIntensity2 = PleEm + UnpEm;
                        //LearningIntensity = LearningIntensity2;
                        EmotionMerge = clsDataStructureGenerator.generateEMOTION(
    	                        new clsTriple <eContentType, eEmotionType, Object>(
    	                                Emotion.getContentType(),
    	                                Emotion.getContent(),
    	                                Emotion.getEmotionIntensity()),
    	                                (PleRI*PleRI*EmotionRISum + PleEm*PleEm*LearningIntensity*TimeIntensity*EmotionSum)/(EmotionRISum*PleRI+LearningIntensity*TimeIntensity*EmotionSum*PleEm),
    	                                (UnpRI*UnpRI*EmotionRISum + UnpEm*UnpEm*LearningIntensity*TimeIntensity*EmotionSum)/(EmotionRISum*UnpRI+LearningIntensity*TimeIntensity*EmotionSum*UnpEm),
    	                                (LibRI*LibRI*EmotionRISum + LibEm*LibEm*LearningIntensity*TimeIntensity*EmotionSum)/(EmotionRISum*LibRI+LearningIntensity*TimeIntensity*EmotionSum*LibEm),
    	                                (AggRI*AggRI*EmotionRISum + AggEm*AggEm*LearningIntensity*TimeIntensity*EmotionSum)/(EmotionRISum*AggRI+LearningIntensity*TimeIntensity*EmotionSum*AggEm));
    	                LTM_Image.addExternalAssociation(new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONEMOTION, eContentType.ASSOCIATIONEMOTION), 
                                EmotionMerge, 
                                LTM_Image));
    	                LTM_Image.setLearningWeight(LearningIntensity);
    	                LTM_Image.setLearningWeight1(LearningIntensity1);
    	                LTM_Image.setLearningWeight2(LearningIntensity2);
                        if(replace)
    	                {
    	                    moLTM_Learning.setLearningImage(LTM_Image); 
    	                }
    	                else
    	                {
    	                    moLTM_Learning.setLearningImage(LTM_Image);   
    	                }
    	                moLTM_Learning.setLearningLTMStorage(LTM_Image);
    	                String olKey = "Learning intensity";
    	                if ( !moDriveChartData.containsKey(olKey) ) {
    	                    mnChartColumnsChanged = true;
    	                }
    	                moDriveChartData.put(olKey, LearningIntensity);
    	             }
    	        }
    	            
                for(clsThingPresentationMesh TPM_Object : moSTM_Learning.moShortTermMemoryMF.get(0).getLearningObjects())
                {
                
                    double LearningWeight;
                    double LearningIntensity = 1.0;
                    double TimeIntensity = 0.1;
                    
                    double ObjectFocus = TPM_Object.getCriterionActivationValue(eActivationType.FOCUS_ACTIVATION);
                    
                    LearningWeight = ObjectFocus * LearningIntensity;
                    
                    
                    clsThingPresentationMesh TPM_Object_LTM = null;
                    try {
                        TPM_Object_LTM = (clsThingPresentationMesh) TPM_Object.clone();
                    } catch (CloneNotSupportedException e) {
                        // TODO (nocks) - Auto-generated catch block
                        e.printStackTrace();
                    }
                    if(LearningWeight > 0.1)
                    {
                        for(clsThingPresentationMesh Object:moLTM_Learning.getLearningObjects())
                        {
                            if(Object.compareTo(TPM_Object) == 1.0)
                            {
                                TPM_Object = Object;
                            }
                        }
                        
                        //TPM_Object.setLearningWeight(LearningWeight);
                            
                        clsEmotion Emotion =  moSTM_Learning.moShortTermMemoryMF.get(0).getEmotions().get(0);
        //	                  TPM_Object.addExternalAssociation(new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONEMOTION, eContentType.ASSOCIATIONEMOTION), 
        //	                          Emotion, 
        //	                          TPM_Object));
                            //Emotion.merge(EmotionRI);
                        clsEmotion EmotionRI = null;
                        for(clsAssociation Ass:TPM_Object.getExternalAssociatedContent())
                        {
                            if (Ass instanceof clsAssociationEmotion)
                            {
                                EmotionRI = (clsEmotion) Ass.getTheOtherElement(TPM_Object);
                            }    
                        }
                        
                        Emotion =  moSTM_Learning.moShortTermMemoryMF.get(0).getEmotions().get(0);
        //              TPM_Object.addExternalAssociation(new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONEMOTION, eContentType.ASSOCIATIONEMOTION), 
        //                      Emotion, 
        //                      TPM_Object));
                        //Emotion.merge(EmotionRI);
                        TPM_Object_LTM.addExternalAssociation(new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONEMOTION, eContentType.ASSOCIATIONEMOTION), 
                                Emotion, 
                                TPM_Object_LTM));
                        clsEmotion EmotionMerge;
                        
                        
                        if(EmotionRI != null)
                        {
                        EmotionMerge = clsDataStructureGenerator.generateEMOTION(
                                new clsTriple <eContentType, eEmotionType, Object>(
                                        Emotion.getContentType(),
                                        Emotion.getContent(),
                                        Emotion.getEmotionIntensity()),
                                        (EmotionRI.getSourcePleasure() + Emotion.getSourcePleasure()*LearningIntensity*TimeIntensity)/(1+1*LearningIntensity*TimeIntensity),
                                        (EmotionRI.getSourceUnpleasure() + Emotion.getSourceUnpleasure()*LearningIntensity*TimeIntensity)/(1+1*LearningIntensity*TimeIntensity),
                                        (EmotionRI.getSourceLibid() + Emotion.getSourceLibid()*LearningIntensity*TimeIntensity)/(1+1*LearningIntensity*TimeIntensity),
                                        (EmotionRI.getSourceAggr() + Emotion.getSourceAggr()*LearningIntensity*TimeIntensity)/(1+1*LearningIntensity*TimeIntensity));
                        TPM_Object_LTM.addExternalAssociation(new clsAssociationEmotion(new clsTriple<Integer, eDataType, eContentType> (-1, eDataType.ASSOCIATIONEMOTION, eContentType.ASSOCIATIONEMOTION), 
                                EmotionMerge, 
                                TPM_Object_LTM));
                        }
                        
                        moLTM_Learning.setLearningObjects(TPM_Object_LTM);
                        //moLTM_Learning.setLearningLTMStorage(TPM_Object_LTM);
        
                    }
                    
                    // Trieb speichern
                    // Agenten mag ich oder nicht
                    // Situation von Agent beeinflusst --> Intention des Agenten
                    // Emotion des anderen Ageneten
                }
    	        moSTM_Learning.moShortTermMemoryMF.add(0,moSTM_LearningEntry);
    	        log.error(moLTM_Learning.getLearningContent());
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
    	    int size = moSTM_Learning.moShortTermMemoryMF.size()-1;
    	    for(int i=0; i<size; i++)
            {
                clsShortTermMemoryEntry STM_Entry = moSTM_Learning.moShortTermMemoryMF.get(i);
                if(i>6)
    	        {
        	            moSTM_Learning.moShortTermMemoryMF.remove(i);
        	            size--;
                }
            }
	    }
	    else
	    {   
	        for(clsThingPresentationMesh TPM_Object : moSTM_Learning.moShortTermMemoryMF.get(0).getLearningObjects())
	        {
	            if(TPM_Object.getContent().equals("SELF"))
	            {
	                for(clsAssociation intAss : TPM_Object.getInternalAssociatedContent())
	                {
	                    if(intAss.getTheOtherElement(TPM_Object) instanceof clsThingPresentation)
	                    {
	                        clsThingPresentation TPM = (clsThingPresentation)intAss.getTheOtherElement(TPM_Object);
	                        if(TPM.getContentType().equals(eContentType.Color))
	                        {
	                            if(TPM.getContent().equals("#33FF33"))
                                {
	                                adam=true;
                                }
	                            
	                        }
	                    }
	                }
	            }
	        }
    	    if(!written && adam && lastLearn==400 && F31_NeuroDeSymbolizationActionCommands.share == false)
    	    {
    	        try
        	    {
        	        String line = null;
        	        File f1 = new File("C:/Users/nocks/Dropbox/workspace/ARSIN_V02/ARSMemory/config/_v38/bw/pa.memory/ADAM_FIM_LEARN_EMOTION/ADAM_FIM_LEARN_EMOTION.pins");
                    //File f2 = new File("C:/Users/noName/Dropbox/workspace/ARSIN_V02/ARSMemory/config/_v38/bw/pa.memory/ADAM_EC2SC2_BEAT/ADAM_EC2SC2_BEAT.pins");
                    FileReader fr = new FileReader(f1);
                    BufferedReader br = new BufferedReader(fr);
                    ArrayList<String> lines = new ArrayList<String>();
                    while ((line = br.readLine()) != null) {
                        lines.add(line+"\n");
                    }
                    lines.add("\n");
                    lines.add("([ASSOCIATIONEMOTION%3ANEW_ASSOCIATION_EMOTION%3AANXIETY] of  ASSOCIATIONEMOTION\n");
                    lines.add("\n");
                    lines.add("\t(element\n");
                    lines.add("\t\t[TPM%3AIMAGE%3AA12_EAT_MEAT_L01_I04]\n");
                    lines.add("\t\t[EMOTION%3ANEW_EMOTION%3AANXIETY])\n");
                    lines.add("\t(weight 1.0 1.0))\n");

                    fr.close();
                    br.close();
        
                    FileWriter fw = new FileWriter(f1);
                    BufferedWriter out = new BufferedWriter(fw);
                    for(String s : lines)
                         out.write(s);
                    out.flush();
                    out.close();
                    written = true;
                }
    	        catch (Exception ex)
        	    {
    	            ex.printStackTrace();
                }
    	    }
	    }
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

    /* (non-Javadoc)
     *
     * @since 24.06.2019 16:48:23
     * 
     * @see inspector.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
     */
    @Override
    public double getTimeChartUpperLimit() {
        // TODO (nocks) - Auto-generated method stub
        return 1.1;
    }

    /* (non-Javadoc)
     *
     * @since 24.06.2019 16:48:23
     * 
     * @see inspector.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
     */
    @Override
    public double getTimeChartLowerLimit() {
        // TODO (nocks) - Auto-generated method stub
        return -0.1;
    }

    /* (non-Javadoc)
     *
     * @since 24.06.2019 16:48:23
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
     */
    @Override
    public String getTimeChartAxis() {
        // TODO (nocks) - Auto-generated method stub
        return "0 to 1";
    }

    /* (non-Javadoc)
     *
     * @since 24.06.2019 16:48:23
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
     */
    @Override
    public String getTimeChartTitle() {
        // TODO (nocks) - Auto-generated method stub
        return "Learning intensity";
    }

    /* (non-Javadoc)
     *
     * @since 24.06.2019 16:48:23
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getTimeChartData()
     */
    @Override
    public ArrayList<Double> getTimeChartData() {
        // TODO (nocks) - Auto-generated method stub
		ArrayList<Double> oResult = new ArrayList<Double>();
		oResult.addAll(moDriveChartData.values());
		return oResult;

    }

    /* (non-Javadoc)
     *
     * @since 24.06.2019 16:48:23
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
     */
    @Override
    public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moDriveChartData.keySet());
		return oResult;
    }

    /* (non-Javadoc)
     *
     * @since 24.06.2019 16:48:23
     * 
     * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
     */
    @Override
    public clsTimeChartPropeties getProperties() {
        // TODO (nocks) - Auto-generated method stub
        return new clsTimeChartPropeties(true);
    }

    /* (non-Javadoc)
     *
     * @since 24.06.2019 16:48:23
     * 
     * @see inspector.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsChanged()
     */
    @Override
    public boolean chartColumnsChanged() {
        // TODO (nocks) - Auto-generated method stub
        return mnChartColumnsChanged;
    }

    /* (non-Javadoc)
     *
     * @since 24.06.2019 16:48:23
     * 
     * @see inspector.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsUpdated()
     */
    @Override
    public void chartColumnsUpdated() {
        // TODO (nocks) - Auto-generated method stub
        mnChartColumnsChanged = false;
    }

}
