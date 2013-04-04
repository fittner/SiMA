/**
 * F63_CompositionOfEmotions.java: DecisionUnits - pa._v38.modules
 * 
 * @author hinterleitner
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;

import pa._v38.interfaces.modules.I6_1_receive;
import pa._v38.memorymgmt.datatypes.clsConcept;
import pa._v38.memorymgmt.datatypes.clsSituation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.situationloader.clsConceptLoader;
import pa._v38.memorymgmt.situationloader.clsSituationLoader;
import pa._v38.memorymgmt.situationloader.itfConceptLoader;
import pa._v38.memorymgmt.situationloader.itfSituationLoader;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * 
 * @author hinterleitner
 */
public class F64_SpeechProduction extends clsModuleBase implements I6_1_receive {

    // Statics for the module
    public static final String P_MODULENUMBER = "64";

    private clsThingPresentationMesh moPerceptionalMesh_IN;
    private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;

    /** @author havlicek; Memory of the generated concepts. */
    //private clsShortTermMemory moConceptMemory;
    /** @author havlicek; Currently generated concept. */
    private final clsConcept moConcept;
    /** @author havlicek; Currently identified situation. */
    private final clsSituation moSituation;

    private clsProperties moProperties;

    public F64_SpeechProduction(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList) throws Exception {
        super(poPrefix, poProp, poModuleList, null);
        moProperties = poProp;
        // Prepare finals to ensure null safety.
        moConcept = new clsConcept();
        moSituation = new clsSituation();
    }

    public static clsProperties getDefaultProperties(String poPrefix) {
        String pre = clsProperties.addDot(poPrefix);

        clsProperties oProp = new clsProperties();
        oProp.setProperty(pre + P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());

        return oProp;
    }

    @SuppressWarnings("unused")
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
        text += toText.valueToTEXT("moSituation", moSituation.toString());

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

        // TODO (havlicek) generation of the situation and the concept.
        itfConceptLoader oConceptLoader = new clsConceptLoader();
        itfSituationLoader oSituationLoader = new clsSituationLoader();

        clsConcept oConcept = oConceptLoader.generate(moProperties,
                moAssociatedMemories_IN.toArray(new clsWordPresentationMesh[moAssociatedMemories_IN.size()]));
        clsSituation oSituation = oSituationLoader.generate("TODO the prefix for situations?", oConcept, moProperties);
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

    @SuppressWarnings("unchecked")
    public void send_I6_13() {

        // AW 20110602 Added Associtated memories
        moAssociatedMemories_IN = (ArrayList<clsWordPresentationMesh>) this.deepCopy(moAssociatedMemories_IN);
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
        moDescription = "F64: F64 Generation of Inner and Outer Speech for Speech and Language Production. ";
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
        // TODO (hinterleitner) - Auto-generated method stub

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
        // TODO (hinterleitner) - Auto-generated method stub

    }

}
