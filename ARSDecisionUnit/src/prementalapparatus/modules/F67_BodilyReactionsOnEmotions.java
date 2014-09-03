/**
 * CHANGELOG
 *
 * Jul 5, 2013 schaat - File created
 *
 */
package pa._v38.modules;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I6_14_receive;
import pa._v38.interfaces.modules.eInterfaces;
//import pa._v38.memorymgmt.datahandlertools.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsEmotion;
//import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eEmotionType;
import pa._v38.tools.toText;
import config.clsProperties;
import du.itf.clsStepCounter;
//import datatypes.helpstructures.clsTriple;
//import du.enums.eInternalActionIntensity;
//import du.itf.actions.clsActionShare;
import du.itf.actions.clsInternalActionCommand;
import du.itf.actions.clsInternalActionEmotionalStressSweat;
import du.itf.actions.clsInternalActionFacialChangeEyeBrows;
import du.itf.actions.clsInternalActionFacialChangeEyes;
import du.itf.actions.clsInternalActionFacialChangeMouth;
import du.itf.actions.clsInternalActionFasterHeartPump;
import du.itf.actions.clsInternalActionTenseMuscles;
import du.itf.actions.itfInternalActionProcessor;
//import pa._v38.memorymgmt.datahandlertools.clsDataStructureGenerator;



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
        
        clsStepCounter.setCounter(0);
        isTimeStampPrinted = false;
        
        // TODO (schaat) - Auto-generated constructor stub
    }
    public static final String P_MODULENUMBER = "67";
    private ArrayList<clsEmotion> moEmotions_Input;
    //private ArrayList <clsEmotion> oEmotion = new ArrayList <clsEmotion> ();
    
    //list of internal actions, fill it with what you want to be shown
    private ArrayList<clsInternalActionCommand> moInternalActions = new ArrayList<clsInternalActionCommand>();
    
    
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
        text += toText.listToTEXT("moInternalActions", moInternalActions);
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
    /*/    //todo fill moInternalActions with the approriate poEmotions_Inputm see PSY document per mail for what, when, where
            
        //zB:
        //Angst
        //Magen Zusammenziehen, Zittern, Schwitzen, Herzrasen – passender Gesichtsausdruck
        //Wut
        //Blutdruckanstieg (Errötung), Muskelanspannung – passender Gesichtsausdruck
        
        //IH: for testing now:
        clsInternalActionSweat test = new clsInternalActionSweat(eInternalActionIntensity.HEAVY);       
        
        //IH: for testing now:
        clsActionShare testnew1 = new clsActionShare(eInternalActionIntensity.HEAVY);
        
        //Speech Trigger
        moInternalActions.add( test );
       //Thought Trigger 
        moInternalActions.add(testnew1 ); //*/
        
        
        ArrayList<String> moEmotionNames_Mouth = new ArrayList <String> ();
        ArrayList<String> moEmotionNames_Eyes = new ArrayList <String> ();
        ArrayList<String> moEmotionNames_EyeBrows = new ArrayList <String> ();
        ArrayList<String> moEmotionNames_Heart = new ArrayList <String> ();
        ArrayList<String> moEmotionNames_ArmsNLegs = new ArrayList <String> (); // maybe seperated into Arms and Legs later if needed
        ArrayList<String> moEmotionNames_StressSweat = new ArrayList <String> ();
        
        ArrayList<Double> moEmotionIntensities_Mouth = new ArrayList <Double> ();
        ArrayList<Double> moEmotionIntensities_Eyes = new ArrayList <Double> ();
        ArrayList<Double> moEmotionIntensities_EyeBrows = new ArrayList <Double> ();
        ArrayList<Double> moEmotionIntensities_Heart = new ArrayList <Double> ();
        ArrayList<Double> moEmotionIntensities_ArmsNLegs = new ArrayList <Double> ();
        ArrayList<Double> moEmotionIntensities_StressSweat = new ArrayList <Double> ();
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        
        // ---------- TEST v
        // Print the emotion values in a .txt file every round
        File file = new File("C:\\Users\\volkan\\Desktop\\Emotions Per Step.txt");
        roundCounter++;
        clsStepCounter.plusPlus();
        
        try {
        fw = new FileWriter(file.getAbsoluteFile(), true);
        
        BufferedWriter out = new BufferedWriter(fw);
        
       // if( 1 == clsStepCounter.getCounter() ){
        //if(roundCounter == 1){
        if( !isTimeStampPrinted ){
            String timeLog = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(Calendar.getInstance().getTime());
            
            out.append("---------------------");
            out.newLine();
            out.append(timeLog);
            out.newLine();
            out.append("---------------------");
            out.newLine();
            out.append("First code run. Check to see if ActionsList is empty: " + moInternalActions.isEmpty() + "\n");
            out.newLine();
            out.newLine();
            
            isTimeStampPrinted = true;
        }
        
//        out.append("Step " + roundCounter + "\n\nEmotions List\n" + poEmotions_Input.toString() + "\n");
        out.append("Step " + clsStepCounter.getCounter() + "\n\nEmotions List\n" + poEmotions_Input.toString() + "\n");
        out.newLine();
        
        if( !moInternalActions.isEmpty() )
        {
            out.append("List of InternalActions in the list:\n" + moInternalActions.toString() );
            out.newLine();
            
            moInternalActions.clear(); // this is VERY important. every round must have its own set of IA. IA's from last round must be deleted.
            out.append("Above contents are deleted." );
            out.newLine();
        }
        
        out.close();
        } catch (IOException e) {
            // TODO (volkan) - Auto-generated catch block
            e.printStackTrace();
        }
     // ---------- TEST ^
        
        /*
        // manual test inputs
        if( 2 == clsStepCounter.getCounter()){
            moEmotionNames_StressSweat.add( eEmotionType.ANGER.toString() );
            moEmotionIntensities_StressSweat.add( new Double( 0.6 ) );
            
     //       moEmotionNames_ArmsNLegs.add( eEmotionType.ANXIETY.toString() );
      //      moEmotionIntensities_ArmsNLegs.add( new Double( 0.0 ) );
        }
        else if(3 == clsStepCounter.getCounter()){
            moEmotionNames_StressSweat.add( eEmotionType.ANXIETY.toString() );
            moEmotionIntensities_StressSweat.add( new Double( 0.8 ) );
            
    //        moEmotionNames_ArmsNLegs.add( eEmotionType.ANXIETY.toString() );
    //        moEmotionIntensities_ArmsNLegs.add( new Double( 0.2 ) );
        }
        else if(4 == clsStepCounter.getCounter()){
            moEmotionNames_StressSweat.add( eEmotionType.ANGER.toString() );
            moEmotionIntensities_StressSweat.add( new Double( 0.5 ) );
            
    //        moEmotionNames_ArmsNLegs.add( eEmotionType.ANXIETY.toString() );
    //        moEmotionIntensities_ArmsNLegs.add( new Double( 0.4 ) );
        }
        else if(5 == clsStepCounter.getCounter()){
            moEmotionNames_StressSweat.add( eEmotionType.ANXIETY.toString() );
            moEmotionIntensities_StressSweat.add( new Double( 0.7 ) );
            
    //        moEmotionNames_ArmsNLegs.add( eEmotionType.ANXIETY.toString() );
    //        moEmotionIntensities_ArmsNLegs.add( new Double( 0.6 ) );
        }
        else if(6 == clsStepCounter.getCounter()){
            moEmotionNames_StressSweat.add( eEmotionType.ANGER.toString() );
            moEmotionIntensities_StressSweat.add( new Double( 1.0 ) );
            
     //       moEmotionNames_ArmsNLegs.add( eEmotionType.ANXIETY.toString() );
     //       moEmotionIntensities_ArmsNLegs.add( new Double( 0.8 ) );
        }
        else if(7 == clsStepCounter.getCounter()){
            moEmotionNames_StressSweat.add( eEmotionType.ANXIETY.toString() );
            moEmotionIntensities_StressSweat.add( new Double( 0.0 ) );
            
      //      moEmotionNames_ArmsNLegs.add( eEmotionType.ANXIETY.toString() );
      //      moEmotionIntensities_ArmsNLegs.add( new Double( 1.0 ) );
        }
        else if(8 == clsStepCounter.getCounter()){
            moEmotionNames_StressSweat.add( eEmotionType.ANGER.toString() );
            moEmotionIntensities_StressSweat.add( new Double( 1.0 ) );
        }   */
        
        // Catching emotions one after another and adding the gathered emotion informations to the InternalActionCommands' list
        for( clsEmotion eee: poEmotions_Input ){
            if( eee.getContent().equals( eEmotionType.JOY ) && (eee.getEmotionIntensity() >= 0.001) ) // 'Greater than 0.001' can be changed later
            { // emotion detected: Joy
                System.out.println("Incoming Emotion: " + eEmotionType.JOY.toString() + ", " + eee.getEmotionIntensity());
                if( (eee.getEmotionIntensity() > 0.001) && (eee.getEmotionIntensity() <= 1.0) ){ // this is important. it prevents emotions with 0.0 intensity to reduce affection.
                    /*
                    moEmotionNames_Mouth.add( eee.getContent().toString() );
                    moEmotionIntensities_Mouth.add( new Double( eee.getEmotionIntensity() ) );
                    
                    moEmotionNames_EyeBrows.add( eee.getContent().toString() );
                    moEmotionIntensities_EyeBrows.add( new Double( eee.getEmotionIntensity() ) );   */
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

                System.out.println("Incoming Emotion: " + eEmotionType.ANGER.toString() + ", " + eee.getEmotionIntensity());
                /*
                moEmotionNames_ArmsNLegs.add( eee.getContent().toString() );
                moEmotionIntensities_ArmsNLegs.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_EyeBrows.add( eee.getContent().toString() );
                moEmotionIntensities_EyeBrows.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_Mouth.add( eee.getContent().toString() );
                moEmotionIntensities_Mouth.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_Heart.add( eee.getContent().toString() );
                moEmotionIntensities_Heart.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_StressSweat.add( eee.getContent().toString() );
                moEmotionIntensities_StressSweat.add( new Double( eee.getEmotionIntensity() ) );    */
            } // end ANGER
            else if( eee.getContent().equals( eEmotionType.MOURNING ) && (eee.getEmotionIntensity() >= 0.0) )
            { // emotion detected: MOURNING
                System.out.println("Incoming Emotion: " + eEmotionType.MOURNING.toString() + ", " + eee.getEmotionIntensity());
                /*
                moEmotionNames_Eyes.add( eee.getContent().toString() );
                moEmotionIntensities_Eyes.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_Mouth.add( eee.getContent().toString() );
                moEmotionIntensities_Mouth.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_EyeBrows.add( eee.getContent().toString() );
                moEmotionIntensities_EyeBrows.add( new Double( eee.getEmotionIntensity() ) );   */
            } // end MOURNING
            else if( eee.getContent().equals( eEmotionType.ANXIETY ) && (eee.getEmotionIntensity() >= 0.0) )
            { // emotion detected: ANXIETY ( = FEAR)
                // Effects of anxiety on body:
                // - heart beat faster
                // - breathing faster
                // + sweat more
                // - hard to concentrate
                // - cant eat
                // + tense muscles

                System.out.println("Incoming Emotion: " + eEmotionType.ANXIETY.toString() + ", " + eee.getEmotionIntensity());
                /*
                moEmotionNames_ArmsNLegs.add( eee.getContent().toString() );
                moEmotionIntensities_ArmsNLegs.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_Heart.add( eee.getContent().toString() );
                moEmotionIntensities_Heart.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_StressSweat.add( eee.getContent().toString() );
                moEmotionIntensities_StressSweat.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_Mouth.add( eee.getContent().toString() );
                moEmotionIntensities_Mouth.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_EyeBrows.add( eee.getContent().toString() );
                moEmotionIntensities_EyeBrows.add( new Double( eee.getEmotionIntensity() ) );   */
            } // end ANXIETY
            else if( eee.getContent().equals( eEmotionType.SATURATION ) && (eee.getEmotionIntensity() >= 0.0) )
            { // emotion detected: SATURATION
                System.out.println("Incoming Emotion: " + eEmotionType.SATURATION.toString() + ", " + eee.getEmotionIntensity());
                /**/
                
            } // end SATURATION
            else if( eee.getContent().equals( eEmotionType.ELATION ) && (eee.getEmotionIntensity() >= 0.0) )
            { // emotion detected: ELATION
                // Effects of elation on body:
                // + lightly smile
                // + pull up the eye brows lightly
                // + muscle relaxation

                System.out.println("Incoming Emotion: " + eEmotionType.ELATION.toString() + ", " + eee.getEmotionIntensity());
                /*
                moEmotionNames_Mouth.add( eee.getContent().toString() );
                moEmotionIntensities_Mouth.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_EyeBrows.add( eee.getContent().toString() );
                moEmotionIntensities_EyeBrows.add( new Double( eee.getEmotionIntensity() ) );
                
                moEmotionNames_ArmsNLegs.add( eee.getContent().toString() );
                moEmotionIntensities_ArmsNLegs.add( new Double( eee.getEmotionIntensity() ) );   */
            } // end ELATION
        } // end for
        
        // Trigger internal actions
        moInternalActions.add( new clsInternalActionFasterHeartPump( moEmotionIntensities_Heart, moEmotionNames_Heart ) );
        moInternalActions.add( new clsInternalActionFacialChangeEyes( moEmotionIntensities_Eyes, moEmotionNames_Eyes ) );
        moInternalActions.add( new clsInternalActionFacialChangeEyeBrows( moEmotionIntensities_EyeBrows, moEmotionNames_EyeBrows ) );        
        moInternalActions.add( new clsInternalActionFacialChangeMouth( moEmotionIntensities_Mouth, moEmotionNames_Mouth ) );
        moInternalActions.add( new clsInternalActionEmotionalStressSweat( moEmotionIntensities_StressSweat, moEmotionNames_StressSweat ) );
        moInternalActions.add( new clsInternalActionTenseMuscles( moEmotionIntensities_ArmsNLegs, moEmotionNames_ArmsNLegs ) );

    } // end Fill IA
    
    /**
     * DOCUMENT (muchitsch) - insert description
     *
     * @since 31.10.2012 12:54:43
     *
     * @param poInternalActionContainer
     */
    public void getBodilyReactions( itfInternalActionProcessor poInternalActionContainer) {
      
        for( clsInternalActionCommand oCmd : moInternalActions ) {
            poInternalActionContainer.call(oCmd);
        }
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
