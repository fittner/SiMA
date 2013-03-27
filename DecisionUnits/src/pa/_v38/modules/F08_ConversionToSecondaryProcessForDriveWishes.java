/**
 * E8_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:11:38
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import org.apache.log4j.Logger;

import config.clsProperties;
import du.enums.eShapeType;
import pa._v38.tools.clsImportanceTools;
import pa._v38.tools.clsGoalTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I5_18_receive;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_3_send;
import pa._v38.interfaces.modules.I6_5_receive;
import pa._v38.interfaces.modules.I6_5_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.itfModuleMemoryAccess;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAction;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.storage.DT3_PsychicEnergyStorage;

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
	
	/** Specialized Logger for this class */
	private Logger log = Logger.getLogger(this.getClass());
	
	private ArrayList<clsDriveMesh> moDriveList_Input;
	
	private ArrayList<clsWordPresentationMesh> moDriveList_Output = new ArrayList<clsWordPresentationMesh>();

	private final DT3_PsychicEnergyStorage moPsychicEnergyStorage;
	
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
			DT3_PsychicEnergyStorage poPsychicEnergyStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poMemory);
		
		this.moPsychicEnergyStorage = poPsychicEnergyStorage;
        this.moPsychicEnergyStorage.registerModule(mnModuleNumber);
		
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
		moDriveList_Input = (ArrayList<clsDriveMesh>)deepCopy(poDriveList);
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
		//JACKBAUERHASHACKEDHERETOGETTHENOURISHCAKEDRIVEASASINGLEDRIVE();
		//log.warn("HACK IMPLEMENTED: All drives except Aggressive Stomach are deactivaed");
		
		
		moDriveList_Output = getWPAssociations(moDriveList_Input); 

		double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber, 3, 1);
	}
	
	private void JACKBAUERHASHACKEDHERETOGETTHENOURISHCAKEDRIVEASASINGLEDRIVE() {
		//FIXME AW .::::::: FAKE Prepare Drive input
				//ArrayList<clsDriveMesh> oOnlyDriveMesh = new ArrayList<clsDriveMesh>();
				for (clsDriveMesh oDM : moDriveList_Input) {
					//if (oDM.getActualDriveObject().getMoContent().equals("BODO")) {
						//Change to cake
						
						clsThingPresentationMesh oTPM = this.getLongTermMemory().searchExactEntityFromInternalAttributes("CAKE", eShapeType.CIRCLE.toString(), "FFAFAF");
						//clsThingPresentationMesh oTPM = this.debugGetThingPresentationMeshEntity("CARROT", eShapeType.CIRCLE.toString(), "FFC800");
						
						
						try {
							if (oDM.getDebugInfo().equals("nourish")) {
								oDM.setActualDriveObject(oTPM, 1.0);
								oDM.setQuotaOfAffect(1.0);
							}
							
						} catch (Exception e) {
							// TODO (wendt) - Auto-generated catch block
							e.printStackTrace();
						}
//						ArrayList<clsAssociation> oAssList = oDM.getExternalMoAssociatedContent();
//						for (clsAssociation oAss : oAssList) {
//							clsDriveMesh oOtherDM = (clsDriveMesh) ((clsAssociationPrimaryDM)oAss).getTheOtherElement(oDM);
//							if (oOtherDM.getActualDriveObject().getMoContent().equals("CAKE")) {
//								//Get the association with the carrot
//								for(clsAssociation oAA : oDM.getMoInternalAssociatedContent())
//								{
//									clsThingPresentationMesh oTPM = (clsThingPresentationMesh)oAA.getMoAssociationElementB();
//									if(oTPM.getMoContentType() == eContentType.ENTITY) {
//										oAA.setMoAssociationElementB(oOtherDM.getActualDriveObject());
//									}
//								}
//							}
//						}
						
						//Set mrPleasure to max
						//oDM.setQuotaOfAffect(1.0);
						
						//oOnlyDriveMesh = oDM;
						
						//break;
					//} 

					
				}
				
				//moDriveList_Input.clear();
				//moDriveList_Input.add(oOnlyDriveMesh);
	}
	
	/**
	 * DOCUMENT (kohlhauser) - insert description
	 *
	 * @author kohlhauser
	 * 19.03.2011, 09:17:07
	 *
	 * @return
	 */
	private ArrayList<clsWordPresentationMesh> getWPAssociations(ArrayList<clsDriveMesh> poDriveList_Input) {
		ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
		
		for (clsDriveMesh oPair : poDriveList_Input) {			
			if (oPair.getDriveComponent()==null) {
				//Break as there is an error
				break;
			}
			
			//Convert drive to affect
			clsWordPresentation oAffect = clsGoalTools.convertDriveMeshToWP(oPair);
			
			//Get the drive content
			String oDriveContent = clsImportanceTools.getDriveType(oAffect.getMoContent());
			
			//Get the affect level
			eAffectLevel oAffectLevel = clsImportanceTools.getDriveIntensityAsAffectLevel(oAffect.getMoContent());
			
			//Get the preferred action name
			String oActionString = oPair.getActualDriveAim().getMoContent();
			eAction oAction = eAction.NULLOBJECT;
			try {
				oAction =  eAction.getAction(oActionString);

			} catch (Exception e) {
				e.printStackTrace();
			}
			
			//Convert the object to a WPM
			clsWordPresentationMesh oDriveObject = null;
			clsAssociationWordPresentation oWPforObject = this.getLongTermMemory().getSecondaryDataStructure(oPair.getActualDriveObject(), 1.0);
			if (oWPforObject!=null) {
				if (oWPforObject.getLeafElement() instanceof clsWordPresentationMesh) {
					oDriveObject = (clsWordPresentationMesh) oWPforObject.getLeafElement();
					oDriveObject.getExternalAssociatedContent().add(oWPforObject);
				}
			}
			
			if ((oDriveContent!=null) && (oDriveObject!=null) && (oAffectLevel!=null)) {
				//If these values exist, create a new container with the word presentation
				//oRetVal.add(new clsTriple<String, eAffectLevel, clsWordPresentationMesh>(oDriveContent, oAffectLevel, oDriveObject));
				oRetVal.add(clsGoalTools.createGoal(oDriveContent, eGoalType.DRIVESOURCE, oAffectLevel, oAction, oDriveObject, clsMeshTools.getNullObjectWPM()));
			}
		}
		
		return oRetVal;
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
	public void send_I6_3(ArrayList<clsWordPresentationMesh> poDriveList) {
		((I6_3_receive)moModuleList.get(23)).receive_I6_3(poDriveList);
		((I6_3_receive)moModuleList.get(51)).receive_I6_3(poDriveList);
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
	public void send_I6_5(ArrayList<clsWordPresentationMesh> poDriveList) {
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
		moDriveList_Output = getWPAssociations(moDriveList_Input); 

		double rReceivedPsychicEnergy = moPsychicEnergyStorage.send_D3_1(mnModuleNumber, 3, 1);
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
