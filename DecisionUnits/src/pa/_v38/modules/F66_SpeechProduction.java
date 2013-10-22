/**
 * F63_CompositionOfEmotions.java: DecisionUnits - pa._v38.modules
 * 
 * @author hinterleitner
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I6_12_receive;
import pa._v38.interfaces.modules.I6_13_send;
import pa._v38.interfaces.modules.I6_1_receive;
import pa._v38.interfaces.modules.I6_2_receive;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsSituation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshAimOfDrive;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshFeeling;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal;
import pa._v38.memorymgmt.shorttermmemory.clsShortTermMemory;
import pa._v38.memorymgmt.situationloader.clsConceptLoader;
import pa._v38.memorymgmt.situationloader.clsSituationLoader;
import pa._v38.memorymgmt.situationloader.itfConceptLoader;
import pa._v38.memorymgmt.situationloader.itfSituationLoader;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;
import pa._v38.tools.toText;
import testfunctions.clsTester;
import config.clsProperties;
import config.personality_parameter.clsPersonalityParameterContainer;

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
    private clsWordPresentationMesh poWording;
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

    public F66_SpeechProduction(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, DT3_PsychicEnergyStorage poPsychicEnergyStorage,
            clsPersonalityParameterContainer poPersonalityParameterContainer, clsShortTermMemory poShortTermMemory) throws Exception {
        super(poPrefix, poProp, poModuleList, poInterfaceData);

        moProperties = poProp;
        // Prepare finals to ensure null safety.
        moConcept = new clsConcept();
        moSituation = new clsSituation();
        //Extract wording
        poWording = new clsConcept().moWording;
        
        applyProperties(poPrefix, poProp);
        this.moShortTermMemory = poShortTermMemory;

       // moWording = new clsWording().getWording();
        // mnSpeechThresold = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER, SPEECH_THRESHOLD).getParameterInt();
        

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

        text += toText.h3("Concept and Situation");
        text += toText.valueToTEXT("moConcept", moConcept.toString());
        text += toText.valueToTEXT("poWording", poWording.toString());
        text += toText.valueToTEXT("moShortTermMemory", moShortTermMemory.stateToTEXT());
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

        moSituation = oSituationLoader.generate("TODO the prefix for situations?", moConcept, moProperties);

        this.moShortTermMemory.saveToShortTimeMemory(moConcept.returnContent());
        
        
        
        
        //TODO
        //Konzepte in WPMs umwandeln, die mit dem CONCEPT-WPMs in Protege korrespondieren
        //Protoge: Die Kontexts wie Images aufbauen. Was soll im Image vorhanden sein, damit man es als "House" erkennt
        //
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
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

    public void send_I6_13(clsWordPresentationMesh moPerceptionalMesh_OUT2, ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT2, clsConcept moConcept2) {

        // AW 20110602 Added Associtated memories
        // moAssociatedMemories_IN = (ArrayList<clsWordPresentationMesh>) this.deepCopy(moAssociatedMemories_IN);
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
            send_I6_13(moPerceptionalMesh_OUT, moAssociatedMemories_OUT);
        }

  

    /**
     * DOCUMENT (hinterleitner) - insert description
     *
     * @since 23.08.2013 20:01:05
     *
     * @param moPerceptionalMesh_OUT2
     * @param moAssociatedMemories_OUT2
     */
    @Override
    public void send_I6_13(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
        ((I6_12_receive)moModuleList.get(23)).receive_I6_12(poPerception, poAssociatedMemoriesSecondary);

        putInterfaceData(I6_13_send.class, poPerception, poAssociatedMemoriesSecondary);
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


  


}
