/**
 * E47_ConversionToPrimaryProcess.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:22:59
 */
package secondaryprocess.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.SortedMap;

import properties.clsProperties;
import memorymgmt.enums.PsychicSpreadingActivationMode;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.shorttermmemory.clsShortTermMemory;
import modules.interfaces.I5_19_receive;
import modules.interfaces.I5_19_send;
import modules.interfaces.I6_11_receive;
import modules.interfaces.eInterfaces;
import secondaryprocess.datamanipulation.clsActionTools;
//import secondaryprocess.datamanipulation.clsDataStructureTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import testfunctions.clsTester;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationWordPresentation;
import base.datatypes.clsEmotion;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshFeeling;
import base.datatypes.clsWordPresentationMeshMentalSituation;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;


/**
 * From an incoming list of action plan together with a list of their associated data structures, the primary data structures
 * (in containers) are extracted and ranked according to the total quota of affect (the highest first). This list of primary data structure
 * images (clsTemplateImage) are returned and sent to F46.
 * 
 * Note: This module does not have any memory access. Therefore, it can only extract data from the available
 * lists. All necessary data is therefore loaded in F52
 *   
 * 
 * @author wendt
 * 03.03.2012, 15:22:59
 * 
 */
public class F47_ConversionToPrimaryProcess extends clsModuleBaseKB implements I6_11_receive, I5_19_send {    //Instead of I6_9_receive
	public static final String P_MODULENUMBER = "47";
	//FIXME AW: Extends ModulebaseKB is a hack until results from the planning can be used. Then it should be changed 
	//to clsModuleBase

	
	/** A list of primarty data structure containers, which form the input for phantsies in F46 */
	private ArrayList<clsThingPresentationMesh> returnedTPMemory_OUT;
	private ArrayList<clsThingPresentationMesh> returnedTPM_Action_OUT;
    private PsychicSpreadingActivationMode psychicSpreadingActivationMode;
	/** The list of generated actions */
	private clsWordPresentationMesh actionCommand_IN;
	private clsThingPresentationMesh moTPM_Action;
	private clsThingPresentationMesh moTPM_Object;
    /** The list of associated memories of the generated actions */
	private clsWordPresentationMesh moWordingToContext;
	//private ArrayList<clsWordPresentationMesh> moAssociatedMemories_IN;
	private List<clsEmotion> moCurrentEmotions;
	
	private clsShortTermMemory<clsWordPresentationMeshMentalSituation> moShortTimeMemory;
	
	private clsWordPresentationMesh moAction;
	private clsWordPresentationMesh moObject;
    
    
	
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	/**
	 * Constructor of F47. Apply properties
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:56:27
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F47_ConversionToPrimaryProcess(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsShortTermMemory<clsWordPresentationMeshMentalSituation> poShortTimeMemory, int pnUid,
			itfModuleMemoryAccess poLongTermMemory)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poLongTermMemory, pnUid);
		
		moShortTimeMemory = poShortTimeMemory;
		
		applyProperties(poPrefix, poProp);	
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moReturnedTPMemory_OUT", returnedTPMemory_OUT);
		//text += toText.listToTEXT("moAssociatedMemories_IN", moAssociatedMemories_IN);
		text += toText.valueToTEXT("moActionCommands_IN", actionCommand_IN);
		text += toText.valueToTEXT("moTPM_Action", moTPM_Action);
		text += toText.valueToTEXT("moTPM_Object", moTPM_Object);
		return text;
	}		
	
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
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		this.psychicSpreadingActivationMode = PsychicSpreadingActivationMode.NONE;
	    
		try {
			returnedTPMemory_OUT = getTPMImagesFromAction(actionCommand_IN);
			this.psychicSpreadingActivationMode = getPhantasyCommandFromAction(actionCommand_IN);
			returnedTPM_Action_OUT = null;
			log.debug("Phantasy image: " + returnedTPMemory_OUT.toString());
		} catch (Exception e) {
			log.error("", e);
		}
		clsThingPresentationMesh Action;
		clsThingPresentationMesh Object;
        
		moTPM_Action = clsMeshTools.getPrimaryDataStructureOfWPM(moAction);
		moTPM_Object = clsMeshTools.getPrimaryDataStructureOfWPM(moObject);
        
		Action = moTPM_Action;
		Object = moTPM_Object;
        
	    
		//=== Perform system tests ===//
        if (clsTester.getTester().isActivated()) {
            log.warn("Systemtester is activated");
            try {
                
                if (returnedTPMemory_OUT.isEmpty()==false) {
                    clsTester.getTester().exeTestAssociationAssignment(returnedTPMemory_OUT.get(0));
                }
                
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
        
        //extract the current feelings from the current mental situation
        moCurrentEmotions = new ArrayList<>();
        for(clsWordPresentationMeshFeeling oFeeling : moShortTimeMemory.getNewestMemory().b.getFeelings()) {
            moCurrentEmotions.add(clsEmotion.fromFeeling(oFeeling));
        }
        
        //debug output
        log.debug("Emotions to send back: ");
        for(clsEmotion oEmotion : moCurrentEmotions) {
            log.debug("  {}", oEmotion.toString());
        }
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		
	}
	
	private PsychicSpreadingActivationMode getPhantasyCommandFromAction(clsWordPresentationMesh poActionCommands) {
	    PsychicSpreadingActivationMode result = clsActionTools.getPhantasyFlag(poActionCommands);
	    return result;
	}
	
	/**
	 * Get the first action, Check if the action has a certain phantasy flag, Check if the associated structure, Get the associated structure, Get the TPM part of that structure
	 * 
	 * 
	 * (wendt)
	 * @throws Exception 
	 * 
	 * @since 20110625
	 *
	 * ${tags}
	 * 
	 */
	private ArrayList<clsThingPresentationMesh> getTPMImagesFromAction(clsWordPresentationMesh poActionCommands) throws Exception {
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		if (poActionCommands!=null && poActionCommands.isNullObject()==false) {		
			ArrayList<clsWordPresentationMesh> oSupportiveDataStructure = clsActionTools.getSupportiveDataStructureForAction(poActionCommands);
			
			for (clsWordPresentationMesh wpm : oSupportiveDataStructure) {
			    clsThingPresentationMesh oTPM = clsMeshTools.getPrimaryDataStructureOfWPM(wpm);
			    
                if (oTPM!=null && oTPM.isNullObject()==false) {
                    //At this stage, there should be no associationWP in the external associations of the TPM
                    try {
                        if (checkIfAssociationWPExists(oTPM)==true) {
                            throw new Exception("No AssociationWP are allowed here");
                        } else {
                            //oTPM.setMoContentType(eContentType.PHI);
                            oRetVal.add(oTPM);
                        }
                    } catch (Exception e) {
                        log.error("", e);
                        throw new Exception(e.getMessage());
                    }
                } else {
                    log.warn("This image should be used in spreading activation but does not have a primary process part {}", wpm);
                }
			}	
		}
			
		return oRetVal;
	}
	
	/**
	 * Check if there exists any WP-associations in the TPM 
	 * 
	 * (wendt)
	 *
	 * @since 16.07.2012 14:55:38
	 *
	 * @param poInput
	 * @return
	 */
	private boolean checkIfAssociationWPExists(clsThingPresentationMesh poInput) {
		boolean bResult = false;
		
		for (clsAssociation oAss : poInput.getExternalAssociatedContent()) {
			if (oAss instanceof clsAssociationWordPresentation) {
				bResult = true;
				break;
			}
		}
		
		return bResult;
	}
		
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_19(returnedTPMemory_OUT, this.psychicSpreadingActivationMode, moWordingToContext, moCurrentEmotions, moTPM_Action, moTPM_Object);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:59
	 * 
	 * @see pa.modules._v38.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.EGO;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:56:32
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 18:03:53
	 * 
	 * @see pa.interfaces.send._v38.I7_7_send#send_I7_7(java.util.ArrayList)
	 */
	@Override
	public void send_I5_19(ArrayList<clsThingPresentationMesh> poReturnedMemory, PsychicSpreadingActivationMode psychicSpreadingActivationMode, clsWordPresentationMesh moWordingToContext2, List<clsEmotion> poCurrentEmotions, clsThingPresentationMesh moTPM_Action, clsThingPresentationMesh moTPM_Object) {
		((I5_19_receive)moModuleList.get(14)).receive_I5_19(poReturnedMemory, psychicSpreadingActivationMode, moWordingToContext2, poCurrentEmotions, moTPM_Action, moTPM_Object);
		putInterfaceData(I5_19_send.class, poReturnedMemory, moWordingToContext2);
	}

    /* (non-Javadoc)
    *
    * @since 02.10.2013 13:04:49
    * 
    * @see pa._v38.interfaces.modules.I6_11_receive#receive_I6_11(java.util.ArrayList)
    */
   @Override
   public void receive_I6_11(clsWordPresentationMesh poActionCommands, clsWordPresentationMesh moWordingToContext2, clsWordPresentationMesh moAction2, clsWordPresentationMesh moObject2) {
       actionCommand_IN = poActionCommands;
       moWordingToContext = moWordingToContext2;
       moAction = moAction2;
       moObject = moObject2;
   }   
	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Contents of various action plans can be used to reduce libido tension in E45. Before they can be processed by primary process functions, they have to be converted back again. The preconscious parts of the contents - the word presentations - are removed by this module.";
	}  
}
