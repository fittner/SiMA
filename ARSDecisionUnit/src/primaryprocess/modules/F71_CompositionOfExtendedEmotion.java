/**
 * CHANGELOG
 *
 * 23.02.2015 Kollmann - File created
 *
 */
package primaryprocess.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import primaryprocess.functionality.superegofunctionality.clsReadSuperEgoRules;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflict;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictPerception;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import memorymgmt.enums.eEmotionType;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I5_16_receive;
import modules.interfaces.I5_17_receive;
import modules.interfaces.I5_23_receive;
import modules.interfaces.I5_23_send;
import modules.interfaces.eInterfaces;
import base.datatypes.clsAssociation;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsThingPresentationMesh;
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
    private static final String P_SUPER_EGO_STRENGTH = "SUPER_EGO_STRENGTH";
    private static final String P_SUPER_EGO_RULES_FILE = "SUPER_EGO_RULES_FILE";
    private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
    private static final String P_PSYCHIC_ENERGY_THESHOLD = "PSYCHIC_ENERGY_THESHOLD";
     
    private clsWordPresentationMesh moWordingToContext = null;
    private ArrayList<clsEmotion> moEmotions_Input = new ArrayList<>();
    private ArrayList<clsPrimaryDataStructure> moAffectOnlyList_Input = new ArrayList<>();
    //Kollmann: I prefer using List as working type over ArrayList - I only use ArrayList as working type of an interface (or other demand)
    //          would force me to cast the List to ArrayList
    private List<clsDriveMesh> moDrives_Input = new ArrayList<>();
    private List<clsReadSuperEgoRules> moRules = new ArrayList<>();
    private clsThingPresentationMesh moPerceptionalMesh_Input = null;
    private double mrModuleStrength = 0;
    private double mrInitialRequestIntensity = 0;
    private double mrPsychicEnergyThreshold = 0;
    private DT3_PsychicIntensityStorage moPsychicEnergyStorage = null;
    
    private List<clsSuperEgoConflict> moForbiddenDrives = new ArrayList<>();
    private List<clsSuperEgoConflictPerception> moForbiddenPerceptions = new ArrayList<>();
    private List<eEmotionType> moForbiddenEmotions = new ArrayList<>();
    private List<String> moSuperEgoOutputRules = new ArrayList <>();  
    
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
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsPersonalityParameterContainer poPersonalityParameterContainer,
            DT3_PsychicIntensityStorage poPsychicEnergyStorage) throws Exception {
        super(poPrefix, poProp, poModuleList, poInterfaceData);
        moPsychicEnergyStorage = poPsychicEnergyStorage;
        
        //Kollmann: this is a bit of a hack, I don't want to pass on the super ego rules so I load them again and for that I access F07s parameters
        //          for the filename and super-ego strength
        double oSuperEgoStrength  = poPersonalityParameterContainer.getPersonalityParameter("F07", P_SUPER_EGO_STRENGTH).getParameterDouble();
        String oFileName = poPersonalityParameterContainer.getPersonalityParameter("F07", P_SUPER_EGO_RULES_FILE).getParameter();
        
        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F" + P_MODULENUMBER, P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F" + P_MODULENUMBER, P_INITIAL_REQUEST_INTENSITY).getParameterDouble();
        mrPsychicEnergyThreshold = poPersonalityParameterContainer.getPersonalityParameter("F" + P_MODULENUMBER, P_PSYCHIC_ENERGY_THESHOLD).getParameterInt();

        moRules = clsReadSuperEgoRules.fromFile(oSuperEgoStrength, oFileName);
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
    public void receive_I5_17(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsDriveMesh> poDriveList) {
        moAffectOnlyList_Input = (ArrayList<clsPrimaryDataStructure>)this.deepCopy(poAffectOnlyList);
        moDrives_Input = poDriveList;
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
            clsWordPresentationMesh poWordingToContext2, clsThingPresentationMesh poPerceptionalMesh) {
        moWordingToContext = poWordingToContext2;
        moEmotions_Input = (ArrayList<clsEmotion>) deepCopy(poEmotions);
        moPerceptionalMesh_Input = poPerceptionalMesh;
    }

    /* (non-Javadoc)
     *
     * @since 23.02.2015 18:33:30
     * 
     * @see base.modules.clsModuleBase#process_basic()
     */
    @Override
    protected void process_basic() {
        double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
        double rConsumedPsychicIntensity = 0;
        
        log.debug("neutralized intensity F71: " + Double.toString(rReceivedPsychicEnergy));
        
        if (rReceivedPsychicEnergy > mrPsychicEnergyThreshold
                /* for test purposes only: */ || true)
            rConsumedPsychicIntensity = checkInternalizedRules(rReceivedPsychicEnergy);   // check perceptions and drives, and apply internalized rules
        
        moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, mrInitialRequestIntensity, rConsumedPsychicIntensity);
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

    protected double checkInternalizedRules(double prReceivedPsychicEnergy) {
        // For now, its assumed that the consumed psychic intensity equals to the recevied psychic intensity in each period.
        double rConsumedPsychicIntensity = prReceivedPsychicEnergy;
        
        // If no Super-Ego rule fires, the lists with forbidden drives, forbidden perceptions, and forbidden emotions must be empty
        moForbiddenDrives     .clear();
        moForbiddenPerceptions.clear();
        moForbiddenEmotions   .clear();
        moSuperEgoOutputRules .clear();
                
        int nRules = moRules.size(); //number of rules
        
        if (nRules > 0) {
            
            try {
            
                for (int i = 0; i < nRules; i++) { //für alle Regel
                    
                    int drivesInOneRule = moRules.get(i).driveSize();
                    int perceptionInOneRule = moRules.get(i).perceptionSize();
                    int emotionsInOneRule = moRules.get(i).emotionSize();
                    
                    double quotaOfAffect_of_ForbiddenDrives = checkForDrives (drivesInOneRule, i);
                        
                    //wurden alle DM, TPM und Emotions von der aktuellen = i Regel gefunden
                    if (quotaOfAffect_of_ForbiddenDrives >= 0 && checkForPerception (perceptionInOneRule, i) && checkForEmotions (emotionsInOneRule, i)) { 

                        moSuperEgoOutputRules.add("FileRule: " + (i+1) + "\nDrives:");
                        for (int tmp=0; tmp < drivesInOneRule; tmp++) {
                            moSuperEgoOutputRules.add(moRules.get(i).getForbiddenDriveRule(tmp).a.toString() + " " +
                                    moRules.get(i).getForbiddenDriveRule(tmp).b.toString() + " " +
                                    moRules.get(i).getForbiddenDriveRule(tmp).c[0].toString() + " " +
                                    moRules.get(i).getForbiddenDriveRule(tmp).c[1].toString());
                                
                        }
                        moSuperEgoOutputRules.add("\nPerception:");
                        for (int tmp=0; tmp < perceptionInOneRule; tmp++) 
                            moSuperEgoOutputRules.add(moRules.get(i).getForbiddenPerceptionRule(tmp).toString());
                        moSuperEgoOutputRules.add("\nEmotion:");
                        for (int tmp=0; tmp < emotionsInOneRule; tmp++) 
                            moSuperEgoOutputRules.add(moRules.get(i).getForbiddenEmotinRule(tmp).a.toString() + " " +
                                    moRules.get(i).getForbiddenEmotinRule(tmp).b[0].toString() + " " +
                                    moRules.get(i).getForbiddenEmotinRule(tmp).b[1].toString());
                        moSuperEgoOutputRules.add("----------------------------------------------");

                        
                        double conflictTension;
                        
                        for (int fd = 0; fd < moRules.get(i).FDriveSize(); fd++) {
                            conflictTension = quotaOfAffect_of_ForbiddenDrives + moRules.get(i).getSuperEgoRuleStrength();
                            
                            // hier ist noch ein Fehler drinnen: am 18.03.2014 war
                            // der Datentyp von moForbiddenDrives: ArrayList<clsSuperEgoConflict>
                            // aber
                            // der Datentyp von oRegeln.get(i).getForbiddenDrive(fd): clsPair <eDriveComponent, eOrgan>
                            // Das heißt, die folgende if-Bedingung kann niemals false werden - der Compiler wurde da ausgetrickst.
                            // Das Gleiche gilt auch für die übernächste if-Bedingung.
                            if (!moForbiddenDrives.contains(moRules.get(i).getForbiddenDrive(fd)))
                                moForbiddenDrives.add(new clsSuperEgoConflict(moRules.get(i).getForbiddenDrive(fd), conflictTension));
                          
                            // if drive is already in list of forbidden drives: change conflict tension
                            else
                                moForbiddenDrives.get(moForbiddenDrives.lastIndexOf(moRules.get(i).getForbiddenDrive(fd))).setConflictTension(conflictTension);
                        }
                        
                        for (int fp = 0; fp < moRules.get(i).FObjectSize(); fp++) {
                            // bei der folgenden Zeile könnte man noch schauen, ob es bei perceptions nicht soetwas gibt wie "Stärke einer Wahrnehmung" - analog dem quota of affect eines Triebes
                            conflictTension = moRules.get(i).getSuperEgoRuleStrength();
                            
                            if (!moForbiddenPerceptions.contains(moRules.get(i).getForbiddenObject(fp))) 
                                moForbiddenPerceptions.add(new clsSuperEgoConflictPerception(moRules.get(i).getForbiddenObject(fp), conflictTension));
                            
                            // if perception is already in list of forbidden perceptions: change conflict tension
                            else
                                moForbiddenPerceptions.get(moForbiddenPerceptions.lastIndexOf(moRules.get(i).getForbiddenDrive(fp))).setConflictTension(conflictTension);
                        }
                        
                        for (int fe = 0; fe < moRules.get(i).FEmotionSize(); fe++) {
                            if (!moForbiddenEmotions.contains(moRules.get(i).getForbiddenEmotion(fe))) 
                                moForbiddenEmotions.add(moRules.get(i).getForbiddenEmotion(fe));
                        }
                        
                    } 
                } // Ende vom for -> "für jede Zeile" = für jede Regel
            } catch (IndexOutOfBoundsException e) {
                System.err.println("\nIndex nicht vorhanden\n");
            }
        }
        
        return rConsumedPsychicIntensity;
    }
    
    /**
     * DOCUMENT - insert description
     *
     * @author Jordakieva
     * @since 11.12.2013 16:14:28
     *
     * @param emotionSize
     * @param i
     * @return
     */
    private boolean checkForEmotions(int emotionSize, int position) {

        boolean beGefunden;
        int e;

        for (e = 0, beGefunden = true; e < emotionSize && beGefunden; e++) {
            
            beGefunden = false;
            for(clsEmotion oOneEmotion : moEmotions_Input) {
                
                double rEmotionItensity = oOneEmotion.getEmotionIntensity();
                
                if (oOneEmotion.getContent().equals(moRules.get(position).getEmotionRule(e).a) &&
                        moRules.get(position).getEmotionRule(e).b[0] <= rEmotionItensity && 
                                moRules.get(position).getEmotionRule(e).b[1] >= rEmotionItensity) {
                    beGefunden = true;
                    break;
                }
            }
        }
        
        
        if (e == emotionSize && beGefunden) return true;
        else return false;
    }

    /**
     * DOCUMENT - insert description
     *
     * @author Jordakieva
     * @since 11.12.2013 16:01:13
     *
     * @param perceptionSize
     * @param i
     * @return
     */
    private boolean checkForPerception(int perceptionSize, int position) {

        boolean bPerception;
        int p;
        
        ArrayList<clsAssociation> oInternalAssociations = ((clsThingPresentationMesh) moPerceptionalMesh_Input).getInternalAssociatedContent();
    
        for (p = 0, bPerception = true; p < perceptionSize && bPerception; p++) {
            
            bPerception = false;
            for (clsAssociation oAssociation : oInternalAssociations) {
                if (oAssociation.getAssociationElementB() instanceof clsThingPresentationMesh) {
                    if( ((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContentType().equals(moRules.get(position).getPerceptionRule(p).a) &&
                            ((clsThingPresentationMesh)oAssociation.getAssociationElementB()).getContent().equals(moRules.get(position).getPerceptionRule(p).b) ) {
                        //EINE TPM GEFUNDEN (von vlt vielen) JUHUU! und da gefunden ab zur nächsten in der Schleife
                        bPerception = true;
                        break;
                    }
                }
            }   
        }
        
        if ((bPerception && p == perceptionSize)) { //true wenn alle TPMs gefunden wurden = abspeichern
            return true;
        } else return false;
    }

    /* (non-Javadoc)
     * 
     * checks whether all conflicting drive meshes of one super-ego rule are contained in the array list of incoming drive meshes
     * 
     * returns the sum of quotas of affect of all forbidden drives (if all forbidden drives are in the array list of incoming drives)
     * returns -1 if not all forbidden drives are in the array list of incoming drives
     *
     * @author Jordakieva
     * @since 11.12.2013 15:17:59
     *
     * @param driveSize
     * @return
     */
    private double checkForDrives(int driveSize, int position) {

        boolean match;
        int d;
        double sum_of_QuoataOfAffect_of_MatchingDrives = 0;
        
        for (d = 0, match = true; d < driveSize && match; d++) { //für alle DM in einer Regel
            
            match = false;
            for (clsDriveMesh oDrive : moDrives_Input) { //moDrives ist received_I5_12 moDrives = (ArrayList<clsDriveMesh>) deepCopy(poDrives);
                
                if (oDrive.getDriveComponent().equals(moRules.get(position).getDriveRule(d).a) &&
                        oDrive.getActualDriveSourceAsENUM().equals(moRules.get(position).getDriveRule(d).b) &&
                        oDrive.getQuotaOfAffect() >= moRules.get(position).getDriveRule(d).c[0] && 
                        oDrive.getQuotaOfAffect() <= moRules.get(position).getDriveRule(d).c[1]) 
                {
                    match = true;
                    sum_of_QuoataOfAffect_of_MatchingDrives += oDrive.getQuotaOfAffect();
                    break;
                } 
            }

        }
        
        if (d == driveSize && match)        
            return sum_of_QuoataOfAffect_of_MatchingDrives;

        else return -1.0; // no match found or not all drive mashes of the super-ego-rule match
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
