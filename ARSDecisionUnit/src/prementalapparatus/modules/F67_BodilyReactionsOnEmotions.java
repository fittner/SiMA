/**
 * CHANGELOG
 *
 * Jul 5, 2013 schaat - File created
 *
 */
package prementalapparatus.modules;

import java.io.FileWriter;
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
    
    
    static int roundCounter = 0;
    FileWriter fw = null;
    boolean isTimeStampPrinted;
    
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
            SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
        super(poPrefix, poProp, poModuleList, poInterfaceData);
        
  //      clsStepCounter.setCounter(0);
        isTimeStampPrinted = false;
        
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
    @SuppressWarnings("unchecked")
    private void FillInternalActions(ArrayList<clsEmotion> poEmotions_Input) {

       
        ArrayList<String> moEmotionNames_Mouth = new ArrayList <String> ();
        ArrayList<String> moEmotionNames_Eyes = new ArrayList <String> ();
        ArrayList<String> moEmotionNames_EyeBrows = new ArrayList <String> ();
        ArrayList<String> moEmotionNames_Heart = new ArrayList <String> ();
        ArrayList<String> moEmotionNames_ArmsNLegs = new ArrayList <String> (); // maybe separated into Arms and Legs later if needed
        ArrayList<String> moEmotionNames_StressSweat = new ArrayList <String> ();

        ArrayList<Double> moEmotionIntensities_Mouth = new ArrayList <Double> ();
        ArrayList<Double> moEmotionIntensities_Eyes = new ArrayList <Double> ();
        ArrayList<Double> moEmotionIntensities_EyeBrows = new ArrayList <Double> ();
        ArrayList<Double> moEmotionIntensities_Heart = new ArrayList <Double> ();
        ArrayList<Double> moEmotionIntensities_ArmsNLegs = new ArrayList <Double> ();
        ArrayList<Double> moEmotionIntensities_StressSweat = new ArrayList <Double> ();

        // Catching emotions one after another and adding the gathered emotion informations to the InternalActionCommands' list
        for( clsEmotion eee: poEmotions_Input ){
            if( eee.getContent().equals( eEmotionType.JOY ) && (eee.getEmotionIntensity() >= 0.0) ) // 'Greater than 0.0' can be changed later
            { // emotion detected: Joy
                if( (eee.getEmotionIntensity() > 0.001) && (eee.getEmotionIntensity() <= 1.0) ){ // this is important. it prevents emotions with 0.0 intensity to reduce affection.
                    /**/
                    moEmotionNames_Mouth.add( eee.getContent().toString() );
                    moEmotionIntensities_Mouth.add( new Double( eee.getEmotionIntensity() ) );

                    moEmotionNames_EyeBrows.add( eee.getContent().toString() );
                    moEmotionIntensities_EyeBrows.add( new Double( eee.getEmotionIntensity() ) );
                }
            } // end JOY
            else if( eee.getContent().equals( eEmotionType.ANGER ) && (eee.getEmotionIntensity() >= 0.0) )
            { // emotion detected: Anger
                // Effects of anger on body:
                // - Triggers Fight or Flight
                // - Adrenal glands flood the blood with stress hormones: adrenaline & cortisol
                // - blood goes from guts to muscles
                // + skin sweats
                // - temperature rises
                // + heart rate increase
                // + teeth grinding (close mouth)
                // - fist clenching
                // + muscle tension
                // + flushing / paling

                /**/
                moEmotionNames_ArmsNLegs.add( eee.getContent().toString() );
                moEmotionIntensities_ArmsNLegs.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_EyeBrows.add( eee.getContent().toString() );
                moEmotionIntensities_EyeBrows.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_Mouth.add( eee.getContent().toString() );
                moEmotionIntensities_Mouth.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_Heart.add( eee.getContent().toString() );
                moEmotionIntensities_Heart.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_StressSweat.add( eee.getContent().toString() );
                moEmotionIntensities_StressSweat.add( new Double( eee.getEmotionIntensity() ) );
            } // end ANGER
            else if( eee.getContent().equals( eEmotionType.MOURNING ) && (eee.getEmotionIntensity() >= 0.0) )
            { // emotion detected: MOURNING
                /**/
                moEmotionNames_Eyes.add( eee.getContent().toString() );
                moEmotionIntensities_Eyes.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_Mouth.add( eee.getContent().toString() );
                moEmotionIntensities_Mouth.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_EyeBrows.add( eee.getContent().toString() );
                moEmotionIntensities_EyeBrows.add( new Double( eee.getEmotionIntensity() ) );
            } // end MOURNING
            else if( eee.getContent().equals( eEmotionType.ANXIETY ) && (eee.getEmotionIntensity() >= 0.0) )
            { // emotion detected: ANXIETY ( = FEAR)
                // Effects of anxiety on body:
                // - contract stomach
                // - heart beat faster
                // - breathing faster
                // + sweat more
                // - hard to concentrate
                // - cant eat
                // + tense muscles

                /**/
                moEmotionNames_ArmsNLegs.add( eee.getContent().toString() );
                moEmotionIntensities_ArmsNLegs.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_Heart.add( eee.getContent().toString() );
                moEmotionIntensities_Heart.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_StressSweat.add( eee.getContent().toString() );
                moEmotionIntensities_StressSweat.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_Mouth.add( eee.getContent().toString() );
                moEmotionIntensities_Mouth.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_EyeBrows.add( eee.getContent().toString() );
                moEmotionIntensities_EyeBrows.add( new Double( eee.getEmotionIntensity() ) );
            } // end ANXIETY
            else if( eee.getContent().equals( eEmotionType.SATURATION ) && (eee.getEmotionIntensity() >= 0.0) )
            { // emotion detected: SATURATION
                /**/

            } // end SATURATION
            else if( eee.getContent().equals( eEmotionType.ELATION ) && (eee.getEmotionIntensity() >= 0.0) )
            { // emotion detected: ELATION
                // Effects of elation on body:
                // + lightly smile
                // + pull up the eye brows lightly
                // + muscle relaxation

                /**/
                moEmotionNames_Mouth.add( eee.getContent().toString() );
                moEmotionIntensities_Mouth.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_EyeBrows.add( eee.getContent().toString() );
                moEmotionIntensities_EyeBrows.add( new Double( eee.getEmotionIntensity() ) );

                moEmotionNames_ArmsNLegs.add( eee.getContent().toString() );
                moEmotionIntensities_ArmsNLegs.add( new Double( eee.getEmotionIntensity() ) );
            } // end ELATION
        } // end for

        // adding internal Actions
        moInternalActions.addDataPoint(createActionCommand("HEART_INTENSITY", moEmotionNames_Heart,moEmotionIntensities_Heart ));
        moInternalActions.addDataPoint(createActionCommand("EYES_INTENSITY", moEmotionNames_Eyes,moEmotionIntensities_Eyes ));
        moInternalActions.addDataPoint(createActionCommand("EYE_BROWNS_INTENSITY", moEmotionNames_EyeBrows,moEmotionIntensities_EyeBrows ));
        moInternalActions.addDataPoint(createActionCommand("MOUTH_INTENSITY", moEmotionNames_Mouth,moEmotionIntensities_Mouth ));
        moInternalActions.addDataPoint(createActionCommand("SWEAT_INTENSITY", moEmotionNames_StressSweat,moEmotionIntensities_StressSweat ));
        moInternalActions.addDataPoint(createActionCommand("ARM_INTENSITY", moEmotionNames_ArmsNLegs,moEmotionIntensities_ArmsNLegs ));

        
    }
    
    public clsDataPoint createActionCommand(String commandName,ArrayList<String> labels, ArrayList<Double> values){
        clsDataPoint oRetVal = new clsDataPoint("ACTION_COMMAND",commandName);
        
        for(int i =0; i< labels.size();i++){
            clsDataPoint child = new clsDataPoint(labels.get(i),""+values.get(i));
            oRetVal.addAssociation(child);
        }
        
        
        
        
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
