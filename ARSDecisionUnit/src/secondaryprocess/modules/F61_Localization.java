/**
 * F61_Localization.java: DecisionUnits - pa.modules
 * 
 * @author muchitsch
 * 07.05.2012, 14:46:53
 */
package secondaryprocess.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I6_12_receive;
import modules.interfaces.I6_1_receive;
import modules.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I6_12_send;
import pa._v38.interfaces.modules.I6_13_receive;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import base.datatypes.clsWordPresentationMesh;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.clsDumper;

/**
 *  Generates localization information for decision making from perceptual information and knowledge 
 * 
 * @author muchitsch
 * 07.05.2012, 14:46:53
 * 
 */
public class F61_Localization extends clsModuleBase implements I6_1_receive, I6_12_send, I6_13_receive {
	public static final String P_MODULENUMBER = "61";
	
    private static final String P_MODULE_STRENGTH ="MODULE_STRENGTH";
	private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
	        
	private double mrModuleStrength;
	private double mrInitialRequestIntensity;
	
	private ArrayList<clsWordPresentationMesh> moWording_IN;
    
    private clsWordPresentationMesh moWordingToContext;
  
	/** Perception IN */
	private clsWordPresentationMesh moPerceptionalMesh_IN;
	/** Associated Memories IN; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;
	/** Perception OUT */
	private clsWordPresentationMesh moPerceptionalMesh_OUT;
	/** Associated Memories OUT; @since 07.02.2012 15:54:51 */
	private ArrayList<clsWordPresentationMesh> moAssociatedMemories_OUT;
	
	private final  DT3_PsychicIntensityStorage moPsychicEnergyStorage;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	//TODO Localization information storage and datatype?

	
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:50:08
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F61_Localization(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT3_PsychicIntensityStorage poPsychicEnergyStorage, clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
		
        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F61", P_MODULE_STRENGTH).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F61", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

        this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber, mrInitialRequestIntensity, mrModuleStrength);
		
		applyProperties(poPrefix, poProp);	
		
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {		
		String text = "";

		String[] ignoreList = new String[] {
				                            "ArrayList:moInternalAssociatedContent",
//				                            "ArrayList:moExternalAssociatedContent",
				                            "eDataType:moDataStructureType"
				                            };
		// simple toString output
		//text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
        // complex clsDumper output
        text += "moPerceptionalMesh_IN:" + clsDumper.dump(moPerceptionalMesh_IN,0,0,ignoreList) + "\n";	
		
		return text;
	}	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I6_1(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
	    moPerceptionalMesh_IN = poPerception;

		//AW 20110602 Added Associtated memories
		moAssociatedMemories_IN = poAssociatedMemoriesSecondary;
	}


	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		//for now just forward the information
		//TODO implement localization
		moPerceptionalMesh_OUT = moPerceptionalMesh_IN;
		moAssociatedMemories_OUT = moAssociatedMemories_IN;
		
          
	   double rRequestedPsychicIntensity = 0.0;
	                
	   double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
	            
	   double rConsumedPsychicIntensity = rReceivedPsychicEnergy;
	            
	   moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, rRequestedPsychicIntensity, rConsumedPsychicIntensity);
		
	}

	
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I6_12(moPerceptionalMesh_OUT, moAssociatedMemories_OUT, moWordingToContext);
	}
	
//	@Override
//	public void send_I6_21(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemories) {
//		//AW 20110602: Attention, the associated memeories contain images and not objects like in the perception
//		//((I6_1_receive)moModuleList.get(23)).receive_I6_1(poPerception, poAssociatedMemories);
//		((I6_1_receive)moModuleList.get(61)).receive_I6_1(poPerception, poAssociatedMemories);
//		
//		putInterfaceData(I6_1_send.class, poPerception, poAssociatedMemories);
//		
//	}
	
	/* (non-Javadoc)
    *
    * @since 07.05.2012 13:59:47
    * 
    * @see pa._v38.interfaces.modules.I6_12_send#send_I6_12(pa._v38.memorymgmt.datatypes.clsWordPresentationMesh, java.util.ArrayList)
    */
   @Override
   public void send_I6_12(clsWordPresentationMesh poPerception, ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary, clsWordPresentationMesh moWordingToContext2) {
       ((I6_12_receive)moModuleList.get(23)).receive_I6_12(poPerception, poAssociatedMemoriesSecondary, moWordingToContext2);

       putInterfaceData(I6_12_send.class, poPerception, poAssociatedMemoriesSecondary, moWordingToContext2);
       
   }
   
	

	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:20
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:20
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 03.03.2011, 16:50:13
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "TODO";
	}

    /* (non-Javadoc)
     *
     * @since 27.12.2013 19:11:15
     * 
     * @see pa._v38.interfaces.modules.I6_13_receive#receive_I6_13(base.datatypes.clsWordPresentationMesh, base.datatypes.clsWordPresentationMesh, java.util.ArrayList)
     */
    @Override
    public void receive_I6_13(clsWordPresentationMesh moWordingToContext_OUT2, clsWordPresentationMesh poPerception,
            ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
        
        moPerceptionalMesh_IN = poPerception; 
        moAssociatedMemories_IN = poAssociatedMemoriesSecondary;
        moWordingToContext = moWordingToContext_OUT2;
        
    }
    
    
    /* (non-Javadoc)
    *
    * @since 25.12.2013 16:01:10
    * 
    * @see pa._v38.interfaces.modules.I6_13_receive#receive_I6_13(java.util.ArrayList, pa._v38.memorymgmt.datatypes.clsWordPresentationMesh, java.util.ArrayList)
    */
   @Override
   public void receive_I6_13(ArrayList<clsWordPresentationMesh> poWording, clsWordPresentationMesh poPerception,
           ArrayList<clsWordPresentationMesh> poAssociatedMemoriesSecondary) {
       
       poPerception = moPerceptionalMesh_IN;  
       poAssociatedMemoriesSecondary = moAssociatedMemories_IN;
       poWording = moWording_IN;
       
   }

	
	
}
