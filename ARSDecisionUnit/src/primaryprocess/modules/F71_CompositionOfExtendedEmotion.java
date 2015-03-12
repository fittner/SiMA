/**
 * CHANGELOG
 *
 * 23.02.2015 Kollmann - File created
 *
 */
package primaryprocess.modules;

import inspector.interfaces.itfInspectorCombinedTimeChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import primaryprocess.functionality.superegofunctionality.clsReadSuperEgoRules;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflict;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictEmotion;
import primaryprocess.functionality.superegofunctionality.clsSuperEgoConflictPerception;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eEmotionType;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I5_16_receive;
import modules.interfaces.I5_17_receive;
import modules.interfaces.I5_23_receive;
import modules.interfaces.I5_23_send;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsEmotion;
import base.datatypes.clsPrimaryDataStructure;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
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
public class F71_CompositionOfExtendedEmotion extends clsModuleBase implements I5_16_receive, I5_17_receive, I5_23_send, itfInspectorCombinedTimeChart {
    public static final String P_MODULENUMBER = "71";
    private static final String P_SUPER_EGO_STRENGTH = "SUPER_EGO_STRENGTH";
    private static final String P_SUPER_EGO_RULES_FILE = "SUPER_EGO_RULES_FILE";
    private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
    private static final String P_PSYCHIC_ENERGY_THESHOLD = "PSYCHIC_ENERGY_THESHOLD";
     
    private clsWordPresentationMesh moWordingToContext = null;
    private ArrayList<clsEmotion> moEmotions_Input = new ArrayList<>();
    private ArrayList<clsEmotion> moEmotions_Output = new ArrayList<>();
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
    private List<clsSuperEgoConflictEmotion> moForbiddenEmotions = new ArrayList<>();
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
        
        //Kollmann: this is a bit of a hack, I don't want to pass on the super ego rules so I load them again and for that I access F07s parameters
        //          for the filename and super-ego strength
        double oSuperEgoStrength  = poPersonalityParameterContainer.getPersonalityParameter("F07", P_SUPER_EGO_STRENGTH).getParameterDouble();
        String oFileName = poPersonalityParameterContainer.getPersonalityParameter("F07", P_SUPER_EGO_RULES_FILE).getParameter();
        
        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F" + P_MODULENUMBER, P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F" + P_MODULENUMBER, P_INITIAL_REQUEST_INTENSITY).getParameterDouble();
        mrPsychicEnergyThreshold = poPersonalityParameterContainer.getPersonalityParameter("F" + P_MODULENUMBER, P_PSYCHIC_ENERGY_THESHOLD).getParameterDouble();

        moPsychicEnergyStorage = poPsychicEnergyStorage;
        moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
        
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
    @Override
    public void receive_I5_16(ArrayList<clsPrimaryDataStructure> poAffectOnlyList, ArrayList<clsEmotion> poEmotions,
            clsWordPresentationMesh poWordingToContext2, clsThingPresentationMesh poPerceptionalMesh) {
        moWordingToContext = poWordingToContext2;
        moEmotions_Input = poEmotions;
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
        double rRemainingConflict = 0;
        clsEmotion oNewGuilt = null;
        
        log.debug("neutralized intensity F71: " + Double.toString(rReceivedPsychicEnergy));

        moEmotions_Output = moEmotions_Input.get(0).generateExtendedEmotions();
        
        if (rReceivedPsychicEnergy > mrPsychicEnergyThreshold) {
            rConsumedPsychicIntensity = checkInternalizedRules(rReceivedPsychicEnergy);   // check perceptions and drives, and apply internalized rules
            
            //TODO: create conflict base class and streamline this here 
            for(clsSuperEgoConflict oConflict : moForbiddenDrives) {
                rRemainingConflict += oConflict.getConflictTension();
            }
            for(clsSuperEgoConflictPerception oConflict : moForbiddenPerceptions) {
                rRemainingConflict += oConflict.getConflictTension();
            }
            for(clsSuperEgoConflictEmotion oConflict : moForbiddenEmotions) {
                rRemainingConflict += oConflict.getConflictTension();
            }
            
            if(rRemainingConflict > 0) {
                oNewGuilt = generateGuilt(rRemainingConflict);
                moEmotions_Output.add(oNewGuilt);
            }
        }
        moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, mrInitialRequestIntensity, rConsumedPsychicIntensity);
    }
    
    /* (non-Javadoc)
    *
    * @author gelbard
    * 27.08.2012, 17:54:00
    * 
    * clones an ArrayList<clsEmotions>
    */
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
    
    private clsEmotion generateGuilt(double prConflictIntensity) {
        //Kollmann: not sure how to generate guilt, but I think it should have some unpleasure component, I, for now, use the same pattern as with ANXIETY
        return clsDataStructureGenerator.generateEMOTION(new clsTriple <eContentType, eEmotionType, Object>(eContentType.BASICEMOTION, eEmotionType.GUILT, prConflictIntensity),  0,  prConflictIntensity,  0,  0);
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
                    double rIntensityOfConflictingEmotions = checkForEmotions (emotionsInOneRule, i);
                        
                    //wurden alle DM, TPM und Emotions von der aktuellen = i Regel gefunden
                    if (quotaOfAffect_of_ForbiddenDrives >= 0 && checkForPerception (perceptionInOneRule, i) && rIntensityOfConflictingEmotions >= 0) { 

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

                        
                        double rConflictTension;
                        
                        for (int fd = 0; fd < moRules.get(i).FDriveSize(); fd++) {
                            rConflictTension = quotaOfAffect_of_ForbiddenDrives + moRules.get(i).getSuperEgoRuleStrength();
                            
                            // hier ist noch ein Fehler drinnen: am 18.03.2014 war
                            // der Datentyp von moForbiddenDrives: ArrayList<clsSuperEgoConflict>
                            // aber
                            // der Datentyp von oRegeln.get(i).getForbiddenDrive(fd): clsPair <eDriveComponent, eOrgan>
                            // Das heißt, die folgende if-Bedingung kann niemals false werden - der Compiler wurde da ausgetrickst.
                            // Das Gleiche gilt auch für die übernächste if-Bedingung.
                            if (!moForbiddenDrives.contains(moRules.get(i).getForbiddenDrive(fd)))
                                moForbiddenDrives.add(new clsSuperEgoConflict(moRules.get(i).getForbiddenDrive(fd), rConflictTension));
                          
                            // if drive is already in list of forbidden drives: change conflict tension
                            else
                                moForbiddenDrives.get(moForbiddenDrives.lastIndexOf(moRules.get(i).getForbiddenDrive(fd))).setConflictTension(rConflictTension);
                        }
                        
                        for (int fp = 0; fp < moRules.get(i).FObjectSize(); fp++) {
                            // bei der folgenden Zeile könnte man noch schauen, ob es bei perceptions nicht soetwas gibt wie "Stärke einer Wahrnehmung" - analog dem quota of affect eines Triebes
                            rConflictTension = moRules.get(i).getSuperEgoRuleStrength();
                            
                            if (!moForbiddenPerceptions.contains(moRules.get(i).getForbiddenObject(fp))) 
                                moForbiddenPerceptions.add(new clsSuperEgoConflictPerception(moRules.get(i).getForbiddenObject(fp), rConflictTension));
                            
                            // if perception is already in list of forbidden perceptions: change conflict tension
                            else
                                moForbiddenPerceptions.get(moForbiddenPerceptions.lastIndexOf(moRules.get(i).getForbiddenDrive(fp))).setConflictTension(rConflictTension);
                        }
                        
                        for (int fe = 0; fe < moRules.get(i).FEmotionSize(); fe++) {
                            //Kollmann: according to KD, the intensity of the confclicting emotion should have less impact than the super ego rule strength
                            //          (0.5 is a completely arbitrary value)
                            //          also: according to KD, the difference between allowed intensity and actual intensity should be used.
                            rConflictTension = (moRules.get(i).getSuperEgoRuleStrength() * Math.min(prReceivedPsychicEnergy / mrInitialRequestIntensity, 1)) + (rIntensityOfConflictingEmotions * /*arbitrary->*/0.5);
                            
                            if (!moForbiddenEmotions.contains(moRules.get(i).getForbiddenEmotion(fe))) 
                                moForbiddenEmotions.add(new clsSuperEgoConflictEmotion(moRules.get(i).getForbiddenEmotion(fe), rConflictTension));
                        }
                        
                    } 
                }
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
    private double checkForEmotions(int emotionSize, int position) {
        double rIntensitySum = 0;
        double rEmotionIntensity = 0;
        double rLowerBound = 0;
        double rUpperBound = 0;
        boolean bFound = false;
        int e;

        for (e = 0; e < emotionSize; e++) {
            for(clsEmotion oOneEmotion : moEmotions_Input.get(0).generateExtendedEmotions()) {
                rEmotionIntensity = oOneEmotion.getEmotionIntensity();
                rLowerBound = moRules.get(position).getEmotionRule(e).b[0];
                rUpperBound = moRules.get(position).getEmotionRule(e).b[1];
                
                if (oOneEmotion.getContent().equals(moRules.get(position).getEmotionRule(e).a) &&
                        rLowerBound <= rEmotionIntensity && 
                        rUpperBound >= rEmotionIntensity) {
                    bFound = true;
                    //Kollmann: according to KD we should consider the difference between the allowed range and the actual intensity.
                    //          But currently the rule supplies the conflicting range - what if the rule says: must be nothing but moderately
                    //          angry (for example), I decided to use the minimal difference to the bound, e.g. ANGER between 0.5 and 1.0 with
                    //          actual intensity 0.6 --> rIntensitySum = 0.1 
                    rIntensitySum += Math.min(rUpperBound - rEmotionIntensity, rEmotionIntensity - rLowerBound);
                    break;
                }
            }
        }
        
        if(!bFound) {
            rIntensitySum = -1;
        }
        
        return rIntensitySum;
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
        send_I5_23(moAffectOnlyList_Input, moEmotions_Output, moWordingToContext);
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

    /*************************************************************/
    /***              COMBINED TIME CHART METHODS              ***/
    /*************************************************************/
    
    /* (non-Javadoc)
     *
     * @since Oct 2, 2012 1:31:29 PM
     * 
     * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getCombinedTimeChartAxis()
     */
    @Override
    public String getCombinedTimeChartAxis() {
        return "";
    }

    /* (non-Javadoc)
    *
    * @since Oct 2, 2012 1:31:29 PM
    * 
    * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getCombinedTimeChartData()
    */
   @Override
   public ArrayList<ArrayList<Double>> getCombinedTimeChartData() {
       ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
       //EMOTIONS
       ArrayList<Double> oAnger =new ArrayList<Double>();
       Double oAngerQoA= 0.0;
       
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.ANGER)){
               oAngerQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oAnger.add(oAngerQoA);
       oResult.add(oAnger);
       
       
       ArrayList<Double> oFear =new ArrayList<Double>();
       Double oFearQoA= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.ANXIETY)){
               oFearQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oFear.add(oFearQoA);
       oResult.add(oFear);
       
       ArrayList<Double> oGrief =new ArrayList<Double>();
       Double oGriefQoA= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.MOURNING)){
               oGriefQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oGrief.add(oGriefQoA);
       oResult.add(oGrief);
       
       ArrayList<Double> oLoveSa =new ArrayList<Double>();
       Double oLoveSaQoA= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.SATURATION)){
               oLoveSaQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oLoveSa.add(oLoveSaQoA);
       oResult.add(oLoveSa);
       
       ArrayList<Double> oLoveEx =new ArrayList<Double>();
       Double oLoveExQoA= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.ELATION)){
               oLoveExQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oLoveEx.add(oLoveExQoA);
       oResult.add(oLoveEx);
       
       ArrayList<Double> oPleasure =new ArrayList<Double>();
       Double oPleasureQoA= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.JOY)){
               oPleasureQoA = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oPleasure.add(oPleasureQoA);
       oResult.add(oPleasure);
       
       ArrayList<Double> oGuilt =new ArrayList<Double>();
       double rGuiltIntensity= 0.0;
       for(int i=0; i<moEmotions_Output.size();i++){
           if(moEmotions_Output.get(i).getContent().equals(eEmotionType.GUILT)){
               rGuiltIntensity = moEmotions_Output.get(i).getEmotionIntensity();

           }
       }
       oGuilt.add(rGuiltIntensity);
       oResult.add(oGuilt);
       
       return oResult;
   }


   /* (non-Javadoc)
    *
    * @since Oct 2, 2012 1:31:29 PM
    * 
    * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getChartTitles()
    */
   @Override
   public ArrayList<String> getChartTitles() {
       ArrayList<String> oResult = new ArrayList<String>();
       oResult.add(eEmotionType.ANGER.toString());
       oResult.add(eEmotionType.ANXIETY.toString());
       oResult.add(eEmotionType.MOURNING.toString());
       
       oResult.add(eEmotionType.SATURATION.toString());
       oResult.add(eEmotionType.ELATION.toString());
       oResult.add(eEmotionType.JOY.toString());
       
       oResult.add(eEmotionType.GUILT.toString());
       
       return oResult;
   }

   /* (non-Javadoc)
    *
    * @since Oct 2, 2012 1:31:29 PM
    * 
    * @see pa._v38.interfaces.itfInspectorCombinedTimeChart#getValueCaptions()
    */
   @Override
   public ArrayList<ArrayList<String>> getValueCaptions() {
       ArrayList<ArrayList<String>> oResult = new ArrayList<ArrayList<String>>();
       
       //Emotions
       
       //ChartAnger
       ArrayList<String> chartAnger = new ArrayList<String>();
       chartAnger.add("Emotion "+eEmotionType.ANGER.toString());
       oResult.add(chartAnger);
       
       //ChartFear
       ArrayList<String> chartFear = new ArrayList<String>();
       chartFear.add("Emotion "+eEmotionType.ANXIETY.toString());
       oResult.add(chartFear);
       
       //ChartGrief
       ArrayList<String> chartGrief = new ArrayList<String>();
       chartGrief.add("Emotion "+eEmotionType.MOURNING.toString());
       oResult.add(chartGrief);    
       
       //ChartLoveSaturation
       ArrayList<String> chartLoveSaturation = new ArrayList<String>();
       chartLoveSaturation.add("Emotion "+eEmotionType.SATURATION.toString());
       oResult.add(chartLoveSaturation);
       
       //ChartLoveexhilaration
       ArrayList<String> chartLoveExhilaration = new ArrayList<String>();
       chartLoveExhilaration.add("Emotion "+eEmotionType.ELATION.toString());
       oResult.add(chartLoveExhilaration); 
       
       //ChartPleasure
       ArrayList<String> chartPleasure= new ArrayList<String>();
       chartPleasure.add("Emotion "+eEmotionType.JOY.toString());
       oResult.add(chartPleasure); 
       
       //ChartGuilt
       ArrayList<String> chartGuilt= new ArrayList<String>();
       chartGuilt.add("Emotion "+eEmotionType.GUILT.toString());
       oResult.add(chartGuilt);
       
       return oResult;
   }
}
