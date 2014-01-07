/**
 * F63_CompositionOfEmotions.java: DecisionUnits - pa._v38.modules
 * 
 * @author hinterleitner
 */
package secondaryprocess.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import memorymgmt.shorttermmemory.clsShortTermMemory;
import memorymgmt.situationloader.clsConceptLoader;
import memorymgmt.situationloader.clsSituationLoader;
import memorymgmt.situationloader.itfConceptLoader;
import memorymgmt.situationloader.itfSituationLoader;
import memorymgmt.storage.DT3_PsychicEnergyStorage;
import modules.interfaces.I6_1_receive;
import modules.interfaces.I6_2_receive;
import modules.interfaces.I6_3_receive;
import modules.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I6_13_receive;
import pa._v38.interfaces.modules.I6_13_send;
import testfunctions.clsTester;
import base.datatypes.clsAssociation;
import base.datatypes.clsConcept;
import base.datatypes.clsEmotion;
import base.datatypes.clsSituation;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshAimOfDrive;
import base.datatypes.clsWordPresentationMeshFeeling;
import base.datatypes.clsWordPresentationMeshGoal;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import config.clsProperties;
import config.personality_parameter.clsPersonalityParameterContainer;
import du.itf.actions.clsInternalActionCommand;
import du.itf.actions.itfInternalActionProcessor;

/**
 * 
 * @author hinterleitner
 */
public class F66_SpeechProduction extends clsModuleBase implements I6_1_receive, I6_2_receive, I6_3_receive, I6_13_send {

    // Statics for the module
    public static final String P_MODULENUMBER = "66";

    // public static final String SPEECH_THRESHOLD = "SPEECH_THRESHOLD";

    private clsWordPresentationMesh moPerceptionalMesh_IN;
    private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;
    private clsWordPresentationMesh moPerceptionalMesh_OUT;
  
    private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
    private ArrayList<clsWordPresentationMeshAimOfDrive> moDriveList_IN;
    private ArrayList<clsWordPresentationMeshFeeling> moSecondaryDataStructureContainer_IN;
    /** @author havlicek; Currently generated concept. */
    private clsConcept moConcept;
    /** @author havlicek; Currently identified situation. */
    private clsSituation moSituation;
    private clsProperties moProperties;
//    private String moWording;
    private float mnSpeechThresold;
    DT3_PsychicEnergyStorage poPsychicEnergyStorage;
    clsPersonalityParameterContainer poPersonalityParameterContainer;
    private clsShortTermMemory moShortTermMemory;
    
    private ArrayList<clsAssociation> moExternalAssociatedContent;
    private clsWordPresentationMesh moContext;
    private clsWordPresentationMesh moWording_Invited;
    private clsWordPresentationMesh moWording_Share;
    private clsWordPresentationMesh moWording_Yes;
    private clsWordPresentationMesh moWordingToContext_OUT;
    private ArrayList<clsEmotion> moEmotions_Input;
    private ArrayList<clsInternalActionCommand> moInternalActions = new ArrayList<clsInternalActionCommand>();
    
    public F66_SpeechProduction(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, DT3_PsychicEnergyStorage poPsychicEnergyStorage,
            clsPersonalityParameterContainer poPersonalityParameterContainer, clsShortTermMemory poShortTermMemory) throws Exception {
        super(poPrefix, poProp, poModuleList, poInterfaceData);

        moProperties = poProp;
        // Prepare finals to ensure null safety.
        moConcept = new clsConcept();
        moSituation = new clsSituation();
        
        applyProperties(poPrefix, poProp);
        this.moShortTermMemory = poShortTermMemory;

    }

    public static clsProperties getDefaultProperties(String poPrefix) {
        String pre = clsProperties.addDot(poPrefix);

        clsProperties oProp = new clsProperties();
        oProp.setProperty(pre + P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());

        return oProp;
    }

    private void applyProperties(String poPrefix, clsProperties poProp) {
        // String pre = clsProperties.addDot(poPrefix);

        // TODO (havlicek) fetch properties for clsSituationLoader

    }

    /*
     * (non-Javadoc)
     * 
     * @author hinterleitner 02.07.2012, 15:48:45
     * 
     * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
     */
    @Override
    public String stateToTEXT() {
        String text = "";

        // text += toText.listToTEXT("moPerceptions_IN", moPerceptions_IN.getMoInternalAssociatedContent());

        text += toText.h3("Concept");
        text += toText.valueToTEXT("moConcept", moConcept.toString());
      
        text += toText.valueToTEXT("moShortTermMemory", moShortTermMemory.stateToTEXT());
        
        text += toText.h3("Context and Wording");
        text += toText.valueToTEXT("moExternalAssociatedContent", moExternalAssociatedContent.toString());
        text += toText.valueToTEXT("moWordingToContext_OUT", moWordingToContext_OUT.toString());
        return text;
    }

    /*
     * (non-Javadoc)
     * 
     * @author hinterleitner 02.07.2012, 15:48:45
     * 
     * @see pa._v38.modules.clsModuleBase#process_basic()
     */
    @Override
    protected void process_basic() {
        
        //=== Perform system tests ===//
        clsTester.getTester().setActivated(false);
        if (clsTester.getTester().isActivated()) {
            try {
                log.warn("System tests activated");
                for (clsWordPresentationMesh mesh :moAssociatedMemories_IN) {
                    
                    clsTester.getTester().exeTestCheckLooseAssociations(mesh); 
                }
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }

        this.moShortTermMemory.updateTimeSteps();
        // generation of the situation and the concept.
        itfConceptLoader oConceptLoader = new clsConceptLoader();
        itfSituationLoader oSituationLoader = new clsSituationLoader();

        moConcept = new clsConcept();
        if (null != moAssociatedMemories_IN) {
            moConcept = oConceptLoader.generate(moProperties,
                    moAssociatedMemories_IN.toArray(new clsWordPresentationMesh[moAssociatedMemories_IN.size()]));
        }
        if (null != moPerceptionalMesh_IN) {
            moConcept = oConceptLoader.extend(moConcept, moProperties, moPerceptionalMesh_IN);
        }
        if (null != moSecondaryDataStructureContainer_IN) {
            moConcept = oConceptLoader.extend(moConcept, moProperties,
                    moSecondaryDataStructureContainer_IN.toArray(new clsWordPresentationMesh[moSecondaryDataStructureContainer_IN.size()]));
        }
        if (null != moDriveList_IN) {
            moConcept = oConceptLoader
                    .extend(moConcept, moProperties, moDriveList_IN.toArray(new clsWordPresentationMeshGoal[moDriveList_IN.size()]));
        }

       // moSituation = oSituationLoader.generate("TODO the prefix for situations?", moConcept, moProperties);

       // this.moShortTermMemory.saveToShortTimeMemory(moConcept.returnContent());
        
        moExternalAssociatedContent = moPerceptionalMesh_IN.getExternalAssociatedContent();

        moPerceptionalMesh_IN.getExternalAssociatedContent();

        clsAssociation element;
        String elementnew;

        element = moExternalAssociatedContent.get(0);
        element.getAssociationElementB();
        elementnew = element.getAssociationElementB().toString();

        moContext = new clsConcept().moWording;
        
        if (moWordingToContext_OUT == null) {
            moWordingToContext_OUT = moContext;
        }
                
        
        

        /*if (elementnew.contains("PLANT(LEFT:MEDIUM)")) {
           // triggerSpeech_eat(moEmotions_Input); // Yes ?
            moWording_Yes = new clsConcept().moWording_Yes;
            moWordingToContext_OUT = moWording_Yes;
        }*/
        
        // Scenario 1 Step 15
        if (elementnew.contains("BODO(RIGHT:MEDIUM)")) {
           // triggerSpeech_schnitzel(moEmotions_Input); // share?
            moWording_Yes = new clsConcept().moWording_Yes;
            moWordingToContext_OUT = moWording_Yes;
          }
        
        // Scenario 1 Step 7
        if (elementnew.contains("WALL(RIGHT:NEAR)")) {
            
            //triggerSpeech_schnitzel(moEmotions_Input); // share?
            moWording_Share = new clsConcept().moWording_Share;
            moWordingToContext_OUT = moWording_Share;
        }
        
       /* if (elementnew.contains("CAKE")) {
            // triggerSpeech_schnitzel(moEmotions_Input); // Share?
            moWording_Yes = new clsConcept().moWording_Yes;
            moWordingToContext_OUT = moWording_Yes;
         }*/
       
        
        
        moPerceptionalMesh_OUT = moPerceptionalMesh_IN;
        moAssociatedMemories_OUT = moAssociatedMemories_IN;
       
      
        //=== Perform system tests ===//
        clsTester.getTester().setActivated(false);
        if (clsTester.getTester().isActivated()) {
            try {
                log.warn("System tests activated");
                for (clsWordPresentationMesh mesh :moAssociatedMemories_IN) {
                    
                    clsTester.getTester().exeTestCheckLooseAssociations(mesh); 
                }
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
    }

  
    @Override
    protected void process_draft() {
        // TODO (zeilinger) - Auto-generated method stub
        throw new java.lang.NoSuchMethodError();
    }

    @Override
    protected void process_final() {
        throw new java.lang.NoSuchMethodError();
    }


    @Override
    protected void setProcessType() {
        mnProcessType = eProcessType.SECONDARY;
    }

    @Override
    protected void setPsychicInstances() {
        mnPsychicInstances = ePsychicInstances.ID;
    }

    @Override
    protected void setModuleNumber() {
        mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
    }

    @Override
    public void setDescription() {
        moDescription = "F66: F66 Generation of Inner and Outer Speech for Speech and Language Production. ";
    }

    /*
     * (non-Javadoc)
     * 
     * @since 25.03.2013 20:06:09
     * 
     * @see pa._v38.modules.clsModuleBase#send()
     */
    @Override
    protected void send() {
        send_I6_13(moWordingToContext_OUT, moPerceptionalMesh_OUT, moAssociatedMemories_OUT);
       
    }
    
    
    /*
     * (non-Javadoc)
     * 
     * @since 26.03.2013 19:00:11
     * 
     * @see pa._v38.interfaces.modules.I6_1_receive#receive_I6_1(pa._v38.memorymgmt.datatypes.clsWordPresentationMesh, java.util.ArrayList)
     */
    @Override
    public void receive_I6_1(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
        moPerceptionalMesh_IN = poPerception;

        // AW 20110602 Added Associtated memories
        moAssociatedMemories_IN = poAssociatedMemoriesSecondary;
    }

    /*
     * (non-Javadoc)
     * 
     * @since 09.05.2013 19:28:39
     * 
     * @see pa._v38.interfaces.modules.I6_3_receive#receive_I6_3(java.util.ArrayList)
     */
    @Override
    public void receive_I6_3(ArrayList<clsWordPresentationMeshAimOfDrive> poDriveList) {
        // TODO (hinterleitner) - Auto-generated method stub
        moDriveList_IN = poDriveList;
    }

    /*
     * (non-Javadoc)
     * 
     * @since 09.05.2013 19:28:39
     * 
     * @see pa._v38.interfaces.modules.I6_2_receive#receive_I6_2(java.util.ArrayList)
     */
    @Override
    public void receive_I6_2(ArrayList<clsWordPresentationMeshFeeling> poSecondaryDataStructureContainer_Output) {
        // TODO (hinterleitner) - Auto-generated method stub
        moSecondaryDataStructureContainer_IN = poSecondaryDataStructureContainer_Output;
    }

    

    /* (non-Javadoc)
    *
    * @since 25.12.2013 16:04:42
    * 
    * @see pa._v38.interfaces.modules.I6_13_send#send_I6_13(pa._v38.memorymgmt.datatypes.clsWordPresentationMesh, pa._v38.memorymgmt.datatypes.clsWordPresentationMesh, java.util.ArrayList)
    */
   @Override
   public void send_I6_13(clsWordPresentationMesh moWordingToContext_OUT, clsWordPresentationMesh poPerception,
           ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
       
       ((I6_13_receive) moModuleList.get(61)).receive_I6_13(moWordingToContext_OUT, poPerception, poAssociatedMemoriesSecondary);

       putInterfaceData(I6_13_send.class, moWordingToContext_OUT, poPerception, poAssociatedMemoriesSecondary);
       
   }

/**
 * DOCUMENT - insert description
 *
 * @author hinterleitner
 * @since 27.12.2013 19:21:09
 *
 * @param poInternalActionContainer
 */
public void getBodilyReactions(itfInternalActionProcessor poInternalActionContainer) {
    for (clsInternalActionCommand oCmd : moInternalActions) {
        poInternalActionContainer.call(oCmd);
    }
}

  


}
