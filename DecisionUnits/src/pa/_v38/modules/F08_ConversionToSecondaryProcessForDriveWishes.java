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
import config.clsProperties;
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
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationPrimaryDM;
import pa._v38.memorymgmt.datatypes.clsAssociationWordPresentation;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.datatypes.clsWordPresentationMesh;
import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eGoalType;

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
	
	private ArrayList<clsDriveMesh> moDriveList_Input;
	

	// TODO: change after adaption to new DM
	private ArrayList<clsDriveMesh> moDriveList_InputTEMPORARY = new ArrayList<clsDriveMesh>();
	
	//private ArrayList<clsDriveMesh> moDriveList_InputTEMPORARY;
	
	private ArrayList<clsWordPresentationMesh> moDriveList_Output = new ArrayList<clsWordPresentationMesh>();
	//private ArrayList<clsTriple<String, eAffectLevel, clsWordPresentationMesh>> moDriveList_Output; 

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
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
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
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);
		
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
		JACKBAUERHASHACKEDHERETOGETTHENOURISHCAKEDRIVEASASINGLEDRIVE();
		
		moDriveList_Output = getWPAssociations(moDriveList_Input); 
	}
	
	private void JACKBAUERHASHACKEDHERETOGETTHENOURISHCAKEDRIVEASASINGLEDRIVE() {
		//FIXME AW .::::::: FAKE Prepare Drive input
				clsDriveMesh oOnlyDriveMesh = null;
				for (clsDriveMesh oDM : moDriveList_Input) {
					if (oDM.getActualDriveObject().getMoContent().equals("CARROT")) {
						//Change to cake
						ArrayList<clsAssociation> oAssList = oDM.getExternalMoAssociatedContent();
						for (clsAssociation oAss : oAssList) {
							clsDriveMesh oOtherDM = (clsDriveMesh) ((clsAssociationPrimaryDM)oAss).getTheOtherElement(oDM);
							if (oOtherDM.getActualDriveObject().getMoContent().equals("CAKE")) {
								//Get the association with the carrot
								for(clsAssociation oAA : oDM.getMoInternalAssociatedContent())
								{
									clsThingPresentationMesh oTPM = (clsThingPresentationMesh)oAA.getMoAssociationElementB();
									if(oTPM.getMoContentType() == eContentType.ENTITY) {
										oAA.setMoAssociationElementB(oOtherDM.getActualDriveObject());
									}
								}
							}
						}
						
						//Set mrPleasure to max
						oDM.setQuotaOfAffect(1.0);
						
						oOnlyDriveMesh = oDM;
						
						break;
					}
				}
				
				moDriveList_Input.clear();
				moDriveList_Input.add(oOnlyDriveMesh);
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
			//Convert drive to affect
			clsWordPresentation oAffect = convertDriveMeshToWP(oPair);
			
			//Get the drive content
			String oDriveContent = clsImportanceTools.getDriveType(oAffect.getMoContent());
			
			//Get the affect level
			eAffectLevel oAffectLevel = clsImportanceTools.getDriveIntensityAsAffectLevel(oAffect.getMoContent());
			
			//Convert the object to a WPM
			clsWordPresentationMesh oDriveObject = null;
			clsAssociationWordPresentation oWPforObject = getWPMesh(oPair.getActualDriveObject(), 1.0);
			if (oWPforObject!=null) {
				if (oWPforObject.getLeafElement() instanceof clsWordPresentationMesh) {
					oDriveObject = (clsWordPresentationMesh) oWPforObject.getLeafElement();
					oDriveObject.getExternalAssociatedContent().add(oWPforObject);
				}
			}
			
			if ((oDriveContent!=null) && (oDriveObject!=null) && (oAffectLevel!=null)) {
				//If these values exist, create a new container with the word presentation
				//oRetVal.add(new clsTriple<String, eAffectLevel, clsWordPresentationMesh>(oDriveContent, oAffectLevel, oDriveObject));
				oRetVal.add(clsGoalTools.createGoal(oDriveContent, eGoalType.DRIVESOURCE, oAffectLevel, oDriveObject, clsMeshTools.getNullObjectWPM()));
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
		throw new java.lang.NoSuchMethodError();
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
