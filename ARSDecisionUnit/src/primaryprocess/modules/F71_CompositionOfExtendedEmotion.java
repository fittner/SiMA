/**
 * CHANGELOG
 *
 * 23.02.2015 Kollmann - File created
 *
 */
package primaryprocess.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import properties.clsProperties;
import modules.interfaces.I5_16_receive;
import modules.interfaces.I5_17_receive;
import modules.interfaces.I5_23_receive;
import modules.interfaces.I5_23_send;
import modules.interfaces.eInterfaces;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsWordPresentationMesh;
import base.modules.clsModuleBase;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 23.02.2015, 18:33:30
 * 
 */
public class F71_CompositionOfExtendedEmotion extends clsModuleBase implements I5_16_receive, I5_17_receive, I5_23_send {
    public static final String P_MODULENUMBER = "71";
    private clsWordPresentationMesh moWordingToContext = null;
    private ArrayList<clsEmotion> moEmotions_Input = new ArrayList<>();
    private ArrayList<clsPrimaryDataStructure> moAffectOnlyList_Input = new ArrayList<>();
    
    /**
     * DOCUMENT - In F71 (Zusammenstellung von erweiterter Emotion) führen Abwehrmechanismen eine Färbung/Akzentuierung der Basisemotionen
     * durch oder assoziieren ein Zusatzattribut. Die konkrete Verarbeitung der Basisemotionen ist bei den spezifischen erweiterten Emotionen
     * unterschiedlich und abhängig von der Situation, der Über-Ich Regeln und der Basisemotionen.
     * Beispiele für erweiterte Emotionen sind Scham, Mitleid, Neid, Schuld.
     *
     * @author Kollmann
     * @since 23.02.2015 18:33:41
     *
     * @param poPrefix
     * @param poProp
     * @param poModuleList
     * @param poInterfaceData
     * @throws Exception
     */
    public F71_CompositionOfExtendedEmotion(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
        super(poPrefix, poProp, poModuleList, poInterfaceData);
        // TODO (Kollmann) - Auto-generated constructor stub
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see inspector.interfaces.itfInspectorInternalState#stateToTEXT()
     */
    @Override
    public String stateToTEXT() {
        // TODO (Kollmann) - Auto-generated method stub
        return null;
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see modules.interfaces.I5_23_send#send_I5_23(java.util.ArrayList, java.util.ArrayList, base.datatypes.clsWordPresentationMesh)
     */
    @Override
    public void send_I5_23(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions,
            clsWordPresentationMesh moWordingToContext2) {
        ((I5_23_receive)moModuleList.get(20)).receive_I5_23(poAffectOnlyList, poEmotions, moWordingToContext2);
        putInterfaceData(I5_23_send.class, poAffectOnlyList, poEmotions, moWordingToContext2);
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see modules.interfaces.I5_17_receive#receive_I5_17(java.util.ArrayList)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void receive_I5_17(ArrayList<clsPrimaryDataStructure> poAffectOnlyList) {
        moAffectOnlyList_Input = (ArrayList<clsPrimaryDataStructure>)this.deepCopy(poAffectOnlyList);
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see modules.interfaces.I5_16_receive#receive_I5_16(java.util.ArrayList, java.util.ArrayList, base.datatypes.clsWordPresentationMesh)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void receive_I5_16(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions,
            clsWordPresentationMesh poWordingToContext2) {
        moWordingToContext = poWordingToContext2;
        
        moEmotions_Input = (ArrayList<clsEmotion>) deepCopy(poEmotions);
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#process_basic()
     */
    @Override
    protected void process_basic() {
        // TODO (Kollmann) - Auto-generated method stub

    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#process_draft()
     */
    @Override
    protected void process_draft() {
        // TODO (Kollmann) - Auto-generated method stub

    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#process_final()
     */
    @Override
    protected void process_final() {
        // TODO (Kollmann) - Auto-generated method stub

    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#send()
     */
    @Override
    protected void send() {
        send_I5_23(moAffectOnlyList_Input, moEmotions_Input, moWordingToContext);
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#setProcessType()
     */
    @Override
    protected void setProcessType() {
        mnProcessType = eProcessType.PRIMARY;
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#setPsychicInstances()
     */
    @Override
    protected void setPsychicInstances() {
        mnPsychicInstances = ePsychicInstances.ID;
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#setModuleNumber()
     */
    @Override
    protected void setModuleNumber() {
        mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#setDescription()
     */
    @Override
    public void setDescription() {
        moDescription = "In F71 (Zusammenstellung von erweiterter Emotion) führen Abwehrmechanismen eine Färbung/Akzentuierung der Basisemotionen "
                + "durch oder assoziieren ein Zusatzattribut. Die konkrete Verarbeitung der Basisemotionen ist bei den spezifischen erweiterten Emotionen "
                + "unterschiedlich und abhängig von der Situation, der Über-Ich Regeln und der Basisemotionen.\n"
                + "Beispiele für erweiterte Emotionen sind Scham, Mitleid, Neid, Schuld.";
    }

}
