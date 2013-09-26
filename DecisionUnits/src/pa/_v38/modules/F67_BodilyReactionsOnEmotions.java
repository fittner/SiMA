/**
 * CHANGELOG
 *
 * Jul 5, 2013 schaat - File created
 *
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import config.clsProperties;
import du.enums.eInternalActionIntensity;
import du.itf.actions.clsActionShare;
import du.itf.actions.clsInternalActionCommand;
import du.itf.actions.clsInternalActionSweat;
import du.itf.actions.itfInternalActionProcessor;

import pa._v38.interfaces.modules.I6_14_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsEmotion;
import pa._v38.tools.toText;


/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jul 5, 2013, 2:28:42 PM
 * 
 */
public class F67_BodilyReactionsOnEmotions extends clsModuleBase implements I6_14_receive{
    
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
        // TODO (schaat) - Auto-generated constructor stub
    }
    public static final String P_MODULENUMBER = "67";
    private ArrayList<clsEmotion> moEmotions_Input;
    
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
        //todo fill moInternalActions with the approriate poEmotions_Inputm see PSY document per mail for what, when, where
        
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
        moInternalActions.add(testnew1 );
    }
    
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
