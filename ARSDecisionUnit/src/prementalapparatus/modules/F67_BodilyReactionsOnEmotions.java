/**
 * CHANGELOG
 *
 * Jul 5, 2013 schaat - File created
 *
 */
package prementalapparatus.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;

import properties.clsProperties;

import memorymgmt.enums.eEmotionType;
import modules.interfaces.I6_14_receive;
import modules.interfaces.eInterfaces;

import base.datatypes.clsEmotion;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;




/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jul 5, 2013, 2:28:42 PM
 * 
 */
public class F67_BodilyReactionsOnEmotions extends clsModuleBase implements I6_14_receive{

    // Intensity variables for basic emotions
    private double mrAngerIntensity;
    private double mrAnxietyIntensity;
    private double mrElationIntensity;
    private double mrJoyIntensity;
    private double mrMourningIntensity;
    private double mrSaturationIntensity;

    // emotion body factors
    private double FullAffectionFactor = 1.0;

    // EB
    private double JoyEyeBrowsFactor = 1.0;
    private double ElationEyeBrowsFactor = 1.0;
    private double MourningEyeBrowsFactor = 1.0;
    private double AngerEyeBrowsFactor = 1.0;
    private double AnxietyEyeBrowsFactor = 1.0;

    // H
    private double AnxietyHeartFactor = 1.0;

    // M
    private double ElationMouthFactor = 1.0;

    // MT
    private double AngerTenseMusclesFactor = 1.0;

    // SG
    private double AngerSweatGlandsFactor = 1.0;

    // direction multipliers
    private int MinusDirection = -1;
    private int PlusDirection = +1;
    
    /**
     * DOCUMENT (schaat) - insert description 
     *
     * @since Jul 5, 2013 2:40:21 PM
     *
     * @param poPrefix
     * @param poProp
     * @param poModuleList
     * @param poInterfaceData
     * @throws Exception
     */
    public F67_BodilyReactionsOnEmotions(String poPrefix, clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList,
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, int pnUid) throws Exception {
        super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
        
        
        // TODO (schaat) - Auto-generated constructor stub
    }
    public static final String P_MODULENUMBER = "67";
    private ArrayList<clsEmotion> moEmotions_Input;
    //private ArrayList <clsEmotion> oEmotion = new ArrayList <clsEmotion> ();
    
    //list of internal actions, fill it with what you want to be shown
    private clsDataContainer moInternalActions = new clsDataContainer();
    
    
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
     * @since Jul 5, 2013 2:40:41 PM
     * 
     * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
     */
    @Override
    public String stateToTEXT() {
        // TODO (schaat) - Auto-generated method stub
        
        String text ="";
        text += toText.listToTEXT("moEmotions_Input", moEmotions_Input);
        text += toText.valueToTEXT("moInternalActions", moInternalActions);
        return text;
           
    }
    /* (non-Javadoc)
     *
     * @since Jul 5, 2013 2:40:41 PM
     * 
     * @see pa._v38.modules.clsModuleBase#process_basic()
     */
    @Override
    protected void process_basic() {
        // TODO (schaat) - Auto-generated method stub
        FillInternalActions(moEmotions_Input);
    }
    
    /**
     * DOCUMENT (muchitsch) - insert description
     *
     * @since 31.10.2012 12:57:55
     *
     * @param moEmotions_Input2
     */
    private void FillInternalActions(ArrayList<clsEmotion> poEmotions_Input) {

        // create Values for Internal Action variables
        double raiseEyeBrowsCorners = 0.0;
        double raiseEyeBrowsCenter = 0.0;
        double affectMouthOpen = 0.0;
        double affectMouthStretchiness = 0.0;
        double affectMouthSidesUpOrDown = 0.0;
        double tenseMuscles = 0.0;
        double sweatEmotionalStress = 0.0;
        double affectHeartRate = 0.0;
        double affectHeartBloodPressureSystolic = 0.0;
        double affectHeartBloodPressureDiastolic = 0.0;
        double affectEyesForCrying = 0.0;
        
        // catch emotions
        for( clsEmotion eee: poEmotions_Input ){
            if( eee.getContent().equals( eEmotionType.JOY ) )
            {   // emotion detected: Joy
                if(eee.getEmotionIntensity() == this.mrJoyIntensity){
                    // no change of the emotion intensity. do nothing.
                }
                else{
                    // change of emotion intensity!
                    // Effects of joy on body:

                    // + Facial Expression: Mouth
                    // no effect on the mouth opening
                    // no effect on mouth stretchiness
                    affectMouthSidesUpOrDown += ( eee.getEmotionIntensity() - this.mrJoyIntensity ) * FullAffectionFactor * PlusDirection; // plus (+) direction curves the lip corners upwards

                    // + Facial Expression: Eye Brows
                    raiseEyeBrowsCorners += (eee.getEmotionIntensity() - this.mrJoyIntensity) * JoyEyeBrowsFactor;
                    raiseEyeBrowsCenter += (eee.getEmotionIntensity() - this.mrJoyIntensity) * FullAffectionFactor;
                }
                this.mrJoyIntensity = eee.getEmotionIntensity();
            } // end JOY
            else if( eee.getContent().equals( eEmotionType.ANGER ) )
            { // emotion detected: Anger
                if(eee.getEmotionIntensity() == this.mrAngerIntensity){
                    // no change of this emotion intensity. do nothing.
                }
                else{
                    // change of emotion intensity!
                    // Effects of anger on body:
                    // - Triggers Fight or Flight
                    // - Adrenal glands flood the blood with stress hormones: adrenaline & cortisol
                    // - blood goes from guts to muscles
                    // - temperature rises
                    // - fist clenching

                    // + muscle tension
                    tenseMuscles += (eee.getEmotionIntensity() - this.mrAngerIntensity) * AngerTenseMusclesFactor * PlusDirection; // (+) plus, adds tension

                    // + Facial Expression: Eye Brows
                    raiseEyeBrowsCorners += (eee.getEmotionIntensity() - this.mrAngerIntensity) * AngerEyeBrowsFactor * PlusDirection; // (+) plus, up
                    raiseEyeBrowsCenter += (eee.getEmotionIntensity() - this.mrAngerIntensity) * FullAffectionFactor * MinusDirection; // (-) minus, down

                    // + teeth grinding (close mouth)
                    // + Facial Expression: Mouth
                    affectMouthOpen += ( eee.getEmotionIntensity() - this.mrAngerIntensity ) * FullAffectionFactor * MinusDirection; // minus (-) direction means closing the mouth
                    // no effect on mouth stretchiness
                    // no effect on the curves of the lip corners

                    // + skin sweats
                    sweatEmotionalStress += (eee.getEmotionIntensity() - this.mrAngerIntensity) * AngerSweatGlandsFactor * PlusDirection; // adds up

                    // + heart rate increase
                    affectHeartRate += (eee.getEmotionIntensity() - this.mrAngerIntensity) * FullAffectionFactor * PlusDirection; // adds up
                    // + flushing / paling
                    affectHeartBloodPressureSystolic += (eee.getEmotionIntensity() - this.mrAngerIntensity) * FullAffectionFactor * PlusDirection; // adds up
                    affectHeartBloodPressureDiastolic += (eee.getEmotionIntensity() - this.mrAngerIntensity) * FullAffectionFactor * PlusDirection; // adds up
                }
                this.mrAngerIntensity = eee.getEmotionIntensity();
            } // end ANGER
            else if( eee.getContent().equals( eEmotionType.MOURNING ) )
            {   // emotion detected: Mourning
                if(eee.getEmotionIntensity() == this.mrMourningIntensity){
                    // no change of the emotion intensity. do nothing.
                }
                else{
                    // change of emotion intensity!
                    // Effects of mourning on body:

                    // + cry if intensity is greater than a treshhold
                    affectEyesForCrying += ( eee.getEmotionIntensity() - this.mrMourningIntensity ) * FullAffectionFactor * PlusDirection;

                    // + Facial Expression: Mouth
                    // no effect on the mouth opening
                    // no effect on stretchiness
                    affectMouthSidesUpOrDown += ( eee.getEmotionIntensity() - this.mrMourningIntensity ) * FullAffectionFactor * MinusDirection; // minus (-) direction curves the lip corners downwards

                    // + Facial Expression: Eye Brows
                    raiseEyeBrowsCorners += (eee.getEmotionIntensity() - this.mrMourningIntensity) * MourningEyeBrowsFactor * MinusDirection;
                    raiseEyeBrowsCenter += (eee.getEmotionIntensity() - this.mrMourningIntensity) * FullAffectionFactor * PlusDirection;
                }
                this.mrMourningIntensity = eee.getEmotionIntensity();
            } // end MOURNING
            else if( eee.getContent().equals( eEmotionType.ANXIETY ) )
            {   // emotion detected: Anxiety ( = FEAR)
                if(eee.getEmotionIntensity() == this.mrAnxietyIntensity){
                    // no change of the emotion intensity. do nothing.
                }
                else{
                    // change of emotion intensity!
                    // Effects of anxiety on body:
                    // - breathing faster
                    // - hard to concentrate
                    // - cant eat

                    // + sweat more
                    sweatEmotionalStress += (eee.getEmotionIntensity() - this.mrAnxietyIntensity) * FullAffectionFactor * PlusDirection; // adds up

                    // + heart beat faster
                    affectHeartRate += (eee.getEmotionIntensity() - this.mrAnxietyIntensity) * AnxietyHeartFactor * PlusDirection; // adds up

                    // + tense muscles
                    tenseMuscles += (eee.getEmotionIntensity() - this.mrAnxietyIntensity) * FullAffectionFactor * PlusDirection; // (+) plus, adds tension

                    // + Facial Expression: Eye Brows
                    raiseEyeBrowsCorners += (eee.getEmotionIntensity() - this.mrAnxietyIntensity) * AnxietyEyeBrowsFactor * MinusDirection;
                    raiseEyeBrowsCenter += (eee.getEmotionIntensity() - this.mrAnxietyIntensity) * FullAffectionFactor * PlusDirection;

                    // + Facial Expression: Mouth
                    affectMouthOpen += ( eee.getEmotionIntensity() - this.mrAngerIntensity ) * FullAffectionFactor * PlusDirection; // plus (+) direction means opening the mouth
                    affectMouthStretchiness += ( eee.getEmotionIntensity() - this.mrAnxietyIntensity ) * FullAffectionFactor * PlusDirection;
                    affectMouthSidesUpOrDown += ( eee.getEmotionIntensity() - this.mrAnxietyIntensity ) * FullAffectionFactor * MinusDirection; // minus (-) direction curves the lip corners downwards

                }
                this.mrAnxietyIntensity = eee.getEmotionIntensity();
            } // end ANXIETY
            else if( eee.getContent().equals( eEmotionType.SATURATION ) )
            {   // emotion detected: Saturation
                if(eee.getEmotionIntensity() == this.mrSaturationIntensity){
                    // no change of the emotion intensity. do nothing.
                }
                else{
                    // change of emotion intensity!
                    // Effects of anxiety on body:

                }
                this.mrSaturationIntensity = eee.getEmotionIntensity();
            } // end SATURATION
            else if( eee.getContent().equals( eEmotionType.ELATION ) )
            {   // emotion detected: Elation
                if(eee.getEmotionIntensity() == this.mrElationIntensity){
                    // no change of the emotion intensity. do nothing.
                }
                else{
                    // change of emotion intensity!
                    // Effects of anxiety on body:

                    // + muscle relaxation
                    tenseMuscles += (eee.getEmotionIntensity() - this.mrElationIntensity) * FullAffectionFactor * MinusDirection; // (-) minus, relieves tension

                    // + lightly smile
                    affectMouthSidesUpOrDown += ( eee.getEmotionIntensity() - this.mrElationIntensity ) * ElationMouthFactor * PlusDirection; // plus (+) direction curves the lip corners upwards

                    // + pull up the eye brows lightly
                    raiseEyeBrowsCorners += (eee.getEmotionIntensity() - this.mrElationIntensity) * ElationEyeBrowsFactor * PlusDirection;
                    raiseEyeBrowsCenter += (eee.getEmotionIntensity() - this.mrElationIntensity) * ElationEyeBrowsFactor * PlusDirection;
                }
                this.mrElationIntensity = eee.getEmotionIntensity();
            } // end ELATION

        } // end for // catching emotions per step
        
        // set values for Internal Action Commands
        if(  0 != raiseEyeBrowsCorners ){
            moInternalActions.addDataPoint(createActionCommand("RAISE_EYE_BROWS_CORNERS", raiseEyeBrowsCorners ));
        }
        if(  0 != raiseEyeBrowsCenter ){
            moInternalActions.addDataPoint(createActionCommand("RAISE_EYE_BROWS_CENTER", raiseEyeBrowsCenter ));
        }
        if(  0 != affectMouthSidesUpOrDown ){
            moInternalActions.addDataPoint(createActionCommand("AFFECT_MOUTH_SIDES_UP", affectMouthSidesUpOrDown ));
        }
        if(  0 != affectMouthOpen ){
            moInternalActions.addDataPoint(createActionCommand("AFFECT_MOUTH_OPEN", affectMouthOpen ));
        }
        if(  0 != affectMouthStretchiness ){
            moInternalActions.addDataPoint(createActionCommand("AFFECT_MOUTH_STRETCHINESS", affectMouthStretchiness ));
        }
        if(  0 != tenseMuscles ){
            moInternalActions.addDataPoint(createActionCommand("TENSE_MUSCLES", tenseMuscles ));
        }
        if(  0 != sweatEmotionalStress ){
            moInternalActions.addDataPoint(createActionCommand("STRESS_SWEAT", sweatEmotionalStress ));
        }
        if(  0 != affectHeartRate ){
            moInternalActions.addDataPoint(createActionCommand("AFFECT_HEART_RATE", affectHeartRate ));
        }
        if(  0 != affectHeartBloodPressureSystolic ){
            moInternalActions.addDataPoint(createActionCommand("AFFECT_BLOOD_PRESSURE_SYSTOLIC", affectHeartBloodPressureSystolic ));
        }
        if(  0 != affectHeartBloodPressureDiastolic ){
            moInternalActions.addDataPoint(createActionCommand("AFFECT_BLOOD_PRESSURE_DIASTOLIC", affectHeartBloodPressureDiastolic ));
        }
        if(  0 != affectEyesForCrying ){
            moInternalActions.addDataPoint(createActionCommand("EFFECT_EYEY_CRYING", affectEyesForCrying ));

        }

        
    }
    
    public clsDataPoint createActionCommand(String commandName,Double value){
        clsDataPoint oRetVal = new clsDataPoint("ACTION_COMMAND",commandName);
        oRetVal.addAssociation(new clsDataPoint("INTENSITY",""+value));
        return oRetVal;
    }
        
    /**
     * DOCUMENT (muchitsch) - insert description
     *
     * @since 31.10.2012 12:54:43
     *
     * @param poInternalActionContainer
     */
    public clsDataContainer getBodilyReactions() {
      
      return   moInternalActions;
        //TODO: convert internal Actions to clsDataContainer
    }

    
    /* (non-Javadoc)
     *
     * @since Jul 5, 2013 2:40:41 PM
     * 
     * @see pa._v38.modules.clsModuleBase#process_draft()
     */
    @Override
    protected void process_draft() {
        // TODO (schaat) - Auto-generated method stub
        
    }
    /* (non-Javadoc)
     *
     * @since Jul 5, 2013 2:40:41 PM
     * 
     * @see pa._v38.modules.clsModuleBase#process_final()
     */
    @Override
    protected void process_final() {
        // TODO (schaat) - Auto-generated method stub
        
    }
    /* (non-Javadoc)
     *
     * @since Jul 5, 2013 2:40:41 PM
     * 
     * @see pa._v38.modules.clsModuleBase#send()
     */
    @Override
    protected void send() {
        // TODO (schaat) - Auto-generated method stub
        
    }
    /* (non-Javadoc)
     *
     * @since Jul 5, 2013 2:40:41 PM
     * 
     * @see pa._v38.modules.clsModuleBase#setProcessType()
     */
    @Override
    protected void setProcessType() {
        // TODO (schaat) - Auto-generated method stub
        mnProcessType = eProcessType.BODY;
    }
    /* (non-Javadoc)
     *
     * @since Jul 5, 2013 2:40:41 PM
     * 
     * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
     */
    @Override
    protected void setPsychicInstances() {
        // TODO (schaat) - Auto-generated method stub
        mnPsychicInstances = ePsychicInstances.BODY;

    }
    /* (non-Javadoc)
     *
     * @since Jul 5, 2013 2:40:41 PM
     * 
     * @see pa._v38.modules.clsModuleBase#setModuleNumber()
     */
    @Override
    protected void setModuleNumber() {
        // TODO (schaat) - Auto-generated method stub
        mnModuleNumber = Integer.parseInt(P_MODULENUMBER);

    }
    /* (non-Javadoc)
     *
     * @since Jul 5, 2013 2:40:41 PM
     * 
     * @see pa._v38.modules.clsModuleBase#setDescription()
     */
    @Override
    public void setDescription() {
        // TODO (schaat) - Auto-generated method stub
        moDescription = "Bodily Reactions on Emotions";

    }
    /* (non-Javadoc)
     *
     * @since Jul 5, 2013 2:44:20 PM
     * 
     * @see pa._v38.interfaces.modules.I6_14_receive#receive_I6_14(java.util.ArrayList)
     */
    @SuppressWarnings("unchecked")
    @Override
    public void receive_I6_14(ArrayList<clsEmotion> poEmotions_Input) {
        // TODO (schaat) - Auto-generated method stub
        moEmotions_Input =  (ArrayList<clsEmotion>) deepCopy(poEmotions_Input);
    }
    
   
}
