/**
 * E8_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:11:38
 */
package primaryprocess.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import base.datatypes.clsAssociation;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsSecondaryDataStructure;
import base.datatypes.clsWordPresentation;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.clsWordPresentationMeshAimOfDrive;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.clsModuleBaseKB;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.interfaces.itfModuleMemoryAccess;
import memorymgmt.storage.DT3_PsychicIntensityStorage;
import modules.interfaces.I5_18_receive;
import modules.interfaces.I6_3_receive;
import modules.interfaces.I6_3_send;
import modules.interfaces.I6_5_receive;
import modules.interfaces.I6_5_send;
import modules.interfaces.eInterfaces;
import properties.clsImplementationVariant;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;
import secondaryprocess.datamanipulation.clsGoalManipulationTools;

/**
 * Conversion of drive demands in the form of thing-presentations into drive-wishes in the form of word presentations associated with incoming thing-presentations. For the incoming thing presentations fitting word presentations are selected from memory. The whole packagething presentations, word presentations, and quota of affectsare now converted into a form which can be used by secondary process modules. The drive contents are now drive wishes.  
 * 
 * @author kohlhauser
 * 07.05.2012, 14:11:38
 * 
 */
public class F08_ConversionToSecondaryProcessForDriveWishes extends clsModuleBaseKB implements 
                 I5_18_receive, I6_3_send, I6_5_send {
	
	public static final String P_MODULENUMBER = "08";
	
    private static final String P_MODULE_STRENGHT ="MODULE_STRENGTH";
    private static final String P_INITIAL_REQUEST_INTENSITY ="INITIAL_REQUEST_INTENSITY";
    private static final String P_ERROR_NO_WP_OR_WPM = "The data structure is neither WP nor WPM!";
    
    private double mrModuleStrength;
    private double mrInitialRequestIntensity;
	
	/** Specialized Logger for this class */
	//private Logger log = Logger.getLogger(this.getClass());
	
	private ArrayList<clsDriveMesh> moDriveList_Input;
	
	private ArrayList<clsWordPresentationMeshAimOfDrive> moDriveList_Output = new ArrayList<clsWordPresentationMeshAimOfDrive>();

	private ArrayList<clsWordPresentationMesh> moWishList_Output = new ArrayList<clsWordPresentationMesh>(); // MJ: Output list of atomic propositions

	private final DT3_PsychicIntensityStorage moPsychicEnergyStorage;
	
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:42:48
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F08_ConversionToSecondaryProcessForDriveWishes(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, itfModuleMemoryAccess poMemory,
			DT3_PsychicIntensityStorage poPsychicEnergyStorage, clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poMemory, pnUid);
		
        mrModuleStrength = poPersonalityParameterContainer.getPersonalityParameter("F08", P_MODULE_STRENGHT).getParameterDouble();
        mrInitialRequestIntensity =poPersonalityParameterContainer.getPersonalityParameter("F08", P_INITIAL_REQUEST_INTENSITY).getParameterDouble();

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
		String text ="";
		
		text += toText.listToTEXT("moDriveList_Input", moDriveList_Input);
		text += toText.listToTEXT("moDriveList_Output", moDriveList_Output);		
		//text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		
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
	 * @author kohlhauser
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
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
	 * 11.08.2009, 14:12:33
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I5_18(ArrayList<clsDriveMesh> poDriveList) {
		//TODO (Kohlhauser) adapt Module to new Input 
		moDriveList_Input = poDriveList;
		//moDriveList_Input = new ArrayList<clsDriveMesh>(); 
	}


	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:15:14
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {

		//Fixme: Remove this hack
	    //moDriveList_Input = HackMethods.JACKBAUERHASHACKEDHERETOGETTHENOURISHCAKEDRIVEASASINGLEDRIVE(moDriveList_Input, this.getLongTermMemory());
		//log.warn("HACK IMPLEMENTED: All drives except Aggressive Stomach are deactivaed");
		
        if (clsImplementationVariant.permittedByImplementationVariant(clsImplementationVariant.implementationVariantMJ)) {
            clsDataStructurePA oWPorWPMforDriveAim;
            int nCounterPerceivedObjects = 1;
            clsWordPresentationMesh oAtomicPropositionNew;
            for (clsDriveMesh poDMElement : moDriveList_Input) {
//                System.out.printf(poDMElement.getDriveIdentifier() + "\n");
                try { // For the drive aim the associated or WP or WPM is taken from the memory. 
                    oWPorWPMforDriveAim = this.getLongTermMemory().getSecondaryDataStructure(poDMElement.getActualDriveAim(), -1).getAssociationElementA();
                    if (!(oWPorWPMforDriveAim instanceof clsWordPresentation || oWPorWPMforDriveAim instanceof clsWordPresentationMesh)) {
                        // Drive Aims have to be WPs or WPMs.
                        throw new java.lang.Error(P_ERROR_NO_WP_OR_WPM);
                    }
                }
                catch (Exception e) { // No WP or WPM was found for a certain attribute, so a dummy WPM is created with the content "SOMETHING") 
                        oWPorWPMforDriveAim = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.WPM), new ArrayList<clsAssociation>(), "SOMETHING");                                
                }
                clsWordPresentationMesh oObjectvariableLocal = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.WPM), new ArrayList<clsAssociation>(), "X" + nCounterPerceivedObjects++); // A variable WPM is created for this perceived TPM.
                oAtomicPropositionNew = clsWordPresentationMesh.createWPSfromTwo((clsSecondaryDataStructure)oWPorWPMforDriveAim, oObjectvariableLocal); // An atomic proposition is created with the TPM as predicate.
                System.out.printf("(" + oAtomicPropositionNew.getContent() + ")\n");
                moWishList_Output.add(oAtomicPropositionNew);
            }
        }
        

	    
		try {
            moDriveList_Output = clsGoalManipulationTools.createGoalFromDriveMesh(moDriveList_Input, this);
        } catch (Exception e) {
            this.log.error("",e);
        } 
		
	        
	    double rRequestedPsychicIntensity = 0.0;
	        
	    double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
	    
	    double rConsumedPsychicIntensity = rReceivedPsychicEnergy;
        
        moPsychicEnergyStorage.informIntensityValues(mnModuleNumber, mrModuleStrength, rRequestedPsychicIntensity, rConsumedPsychicIntensity);
		
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:15:14
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the homeostatic information formed out of objects of the type pa._v38.memorymgmt.datatypes 
		send_I6_3(moDriveList_Output);
		send_I6_5(moDriveList_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 16:51:16
	 * 
	 * @see pa.interfaces.send.I1_7_send#send_I1_7(java.util.ArrayList)
	 */
	@Override
	public void send_I6_3(ArrayList<clsWordPresentationMeshAimOfDrive> poDriveList) {
		((I6_3_receive)moModuleList.get(23)).receive_I6_3(poDriveList);
		//((I6_3_receive)moModuleList.get(51)).receive_I6_3(poDriveList);
		((I6_3_receive)moModuleList.get(26)).receive_I6_3(poDriveList);
		
		putInterfaceData(I6_3_send.class, poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 16:51:16
	 * 
	 * @see pa.interfaces.send.I5_3_send#send_I5_3(java.util.ArrayList)
	 */
	@Override
	public void send_I6_5(ArrayList<clsWordPresentationMeshAimOfDrive> poDriveList) {
		((I6_5_receive)moModuleList.get(20)).receive_I6_5(poDriveList);	
		
		putInterfaceData(I6_5_send.class, poDriveList);		
	}
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:45:47
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		try {
            moDriveList_Output = clsGoalManipulationTools.createGoalFromDriveMesh(moDriveList_Input, this);
        } catch (Exception e) {
            // TODO (wendt) - Auto-generated catch block
            e.printStackTrace();
        } 

		double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:45:47
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
	 * 03.03.2011, 16:42:55
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
		moDescription = "For the incoming thing presentations fitting word presentations are selected from memory. The whole packagething presentations, word presentations, and quota of affectsare now converted into a form which can be used by secondary process modules. The drive contents are now drive wishes. ";
	}
}
