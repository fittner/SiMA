/**
 * E6_DefenseMechanismsForDriveContents.java: DecisionUnits - pa.modules
 * 
 * @author gelbard
 * 30.11.2011, 14:01:06
 */
package pa._v38.modules;

import java.util.ArrayList;


import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.D2_3_send;
import pa._v38.interfaces.modules.I5_18_receive;
import pa._v38.interfaces.modules.I5_18_send;
import pa._v38.interfaces.modules.I5_13_receive;
import pa._v38.interfaces.modules.I5_17_receive;
import pa._v38.interfaces.modules.I5_17_send;
import pa._v38.interfaces.modules.I5_5_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAffect;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.storage.DT2_BlockedContentStorage;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * Defends forbidden drives. Super-Ego (F7, F55) sends a list with forbidden drives to F06. F06 decides whether to defend the forbidden drives or not.
 * If F06 decided to defend the forbidden drives F06 chooses the defense mechanism (repression, sublimation, displacement, ...).
 * 
 * The following defense mechanisms for drives are implemented:
 * - repression (Verdr�ngung)
 * - reaction formation (Reaktionsbildung: new drive aim = opposite of old drive aim)
 * - displacement (Verschiebung: Drive object is changed) - TODO: muss noch fertig programmiert werden.
 * - sublimation (new drive aim is a social and cultural higher drive aim) - TODO: drive aims m�ssen noch definiert werden
 * - intellectualization (new drive aim is an intellectual drive aim) - TODO: drive aims m�ssen noch definiert werden
 * 
 * @author gelbard
 * 07.05.2012, 14:01:06
 * 
 */
public class F06_DefenseMechanismsForDrives extends clsModuleBase implements 
					I5_5_receive, I5_13_receive, I5_18_send, I5_17_send, D2_3_send {
	public static final String P_MODULENUMBER = "06";
	
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>> moDriveList_Input;
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>> moDriveList_Output;
	private ArrayList<String> moForbiddenDrives_Input;
	private ArrayList<clsPrimaryDataStructureContainer> moRepressedRetry_Input;
	private ArrayList<clsDriveMeshOLD> moSexualDrives;
	private ArrayList<clsPrimaryDataStructure> moQuotasOfAffect_Output = new ArrayList<clsPrimaryDataStructure>();  // anxiety which is generated while repressing content
	
	// defense mechanisms must be activated by a psychoanalytic conflict
	// defense_active symbolizes an unpleasure value which is generated by the psychonanlytic conflict
	boolean defense_active = false;
	
	private DT2_BlockedContentStorage moBlockedContentStorage; // storage for repressed drives and denied perceptions

	/** 
	 * 
	 * @author gelbard
	 * 15.09.2011, 16:38:57
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @param poBlockedContentStorage
	 * @throws Exception
	 */
	public F06_DefenseMechanismsForDrives(String poPrefix, clsProperties poProp, HashMap<Integer,
			clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT2_BlockedContentStorage poBlockedContentStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		moBlockedContentStorage = poBlockedContentStorage;
		applyProperties(poPrefix, poProp);
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 15.09.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moDriveList_Input", moDriveList_Input);
		text += toText.listToTEXT("moDriveList_Output", moDriveList_Output);
		text += toText.listToTEXT("moRepressedRetry_Input", moRepressedRetry_Input);	
		text += toText.listToTEXT("moForbiddenDrives_Input", moForbiddenDrives_Input);
		text += toText.listToTEXT("moSexualDrives", moSexualDrives);
		text += toText.listToTEXT("moQuotasOfAffect_Output", moQuotasOfAffect_Output);
		text += toText.valueToTEXT("moBlockedContentStorage", moBlockedContentStorage);
		
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
	 * @author deutsch
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
	 * @author deutsch
	 * 11.08.2009, 13:46:50
	 * 
	 * @see pa.interfaces.I1_3#receive_I1_3(int)
	 */
	@Override
	public void receive_I5_5(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>> poData) {

	}

	/* Input from Super-Ego = E7
	 *
	 * @author deutsch
	 * 11.08.2009, 14:07:30
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_13(ArrayList<String> poForbiddenDrives, ArrayList<clsDriveMeshOLD> poData) {

		moDriveList_Input = new ArrayList <clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>>();
		// SSch: Temporary Solution until F06 and following modules consider new DM structure
		for (clsDriveMeshOLD oEntry : poData) {
			if (oEntry != null){
				moDriveList_Input.add(new clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>((clsPhysicalRepresentation) oEntry.getBestTPM(), oEntry ));
			}
		}
		
		//moDriveList_Input = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>)deepCopy( (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>)poData);
		
		moForbiddenDrives_Input = (ArrayList<String>) deepCopy (poForbiddenDrives);
	}

	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 15.09.2011, 16:15:00
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void process_basic() { 
		 moDriveList_Output = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>>) deepCopy (moDriveList_Input);
		 
		 
		 detect_conflict_and_activate_defense_machanisms();
	}

	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 28.03.2012, 17:30:00
	 * 
	 * This function detects a psychoanalytic conflict
	 * and activates the defense mechanisms
	 * and chooses a defense mechanism to resolve the conflict.
	 *
	 */
	private void detect_conflict_and_activate_defense_machanisms() {
		
		 // If nothing to defend return immediately (otherwise NullPointerException)
	   	 if (moForbiddenDrives_Input == null ) return;
		
		 // empty the list from last step otherwise list only grows
		 moQuotasOfAffect_Output.clear();
		
		 // check for a psychoanalytic conflict
		 // defense mechanisms are delayed by one cycle to produce a situation where conflict exists and no action plans are executed
		 if (!moForbiddenDrives_Input.isEmpty() && !defense_active)
		 {
			 // conflicting events exist -> activate conflict -> activate defense mechanisms but do not defend yet. (defense will work in the next cycle)
			 defense_active = true;
			 
			 // send quota of affect 999.9 via I5.17 to produce a "CONFLICT"-signal in F20
			 clsAffect oAffect = (clsAffect) clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<String, Object>("AFFECT", 999.9)); 
			 moQuotasOfAffect_Output.add(oAffect);
			 
			 return;
		 }
		 else if (moForbiddenDrives_Input.isEmpty())
		 {
			 // no conflicting events -> deactivate defense mechanisms
			 defense_active = false;
			 return;
		 }
		 // Defense mechanisms start to work.
		 
		 
		 // Super-Ego requests to defend the drives moForbiddenDrives_Input
		 // Ego decides now which defense mechanisms to apply (depending on the quota of affect of the forbidden drive)
		 
		 // get quota of affect of forbidden drive (for now only one forbidden drive is possible)
		 // ToDo FG: many forbidden drives possible
		 double oQoA = getQuotaOfAffect(moForbiddenDrives_Input);
		 
		 // select defense mechanism
		 if (oQoA <= 0.5)
			 defenseMechanism_Repression(moForbiddenDrives_Input);
		 else if (oQoA <= 0.9)
		 	 defenseMechanism_ReactionFormation(moForbiddenDrives_Input);
		 // else if (oQoA > 0.9)
		 // if the quota of affect of the forbidden drive is greater than 0.9, the drive can pass the defense (no defense mechanisms is activated)
	}

	
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 28.03.2012, 17:30:00
	 * 
	 * This is a function that represents the defense mechanism "displacement".
	 * Displacement means that the drive object is changed.
	 *
	 */
	protected void defenseMechanism_Displacement(ArrayList<String> oForbiddenDrives_Input) {
		
		// Iterate over all forbidden drives
		for (String oContent : oForbiddenDrives_Input) {
		
			int i = 0;
			// search in list of incoming drives
			for(clsPair<clsPhysicalRepresentation, clsDriveMeshOLD> oDrives : moDriveList_Input){
				// check DriveMesh
				if (oDrives.b.getMoContent().equalsIgnoreCase(oContent)){
					
					// remove DriveMesh i from output list
					moDriveList_Output.remove(i);
					// drive found
					moDriveList_Output.add(displacement(oDrives.a, oDrives.b));
									
				}
				
				i++;
			}
		
		}
		return;
	   	
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 28.03.2012, 17:30:00
	 * 
	 * This method represents the defense mechanism "displacement"
	 * The defense mechanism displacement replaces the original drive object by a new drive object.
	 *
	 */
	protected clsPair<clsPhysicalRepresentation, clsDriveMeshOLD> displacement(clsPhysicalRepresentation poOriginalTPM, clsDriveMeshOLD poOriginalDM) {
	    // Liste mit m�glichen Trieben und dazugeh�riges displaced drive object (und reserve drive object, falls displace drive object das original drive object ist.)
		// (eventuell k�nnte man auch noch das drive object des n�chsten Triebes nehmen, falls gar kein drive object passt)
		
		// Liste der Triebe ist NOURISH, BITE, DEPOSIT, usw. -> heraussuchen aus Inspektoren
		
		// Wo finde ich die m�glichen Triebobjekte (TPM)?
		
		//clsPhysicalRepresentation oDisplacedDriveObject = TPM(search(poOriginalDM -> displacedDriveObject));
		return new clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>(poOriginalTPM, poOriginalDM);
	}

	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 13.07.2012, 15:05:00
	 * 
	 * This is a function that represents the defense mechanism "reaction formation"
	 * reaction formation changes the drive aim
	 *
	 */
	private void defenseMechanism_ReactionFormation(ArrayList<String> oForbiddenDrives_Input) {
		
		// Testdata: Defining the opposite drive aim (DM's TP). TODO: Represent this information in the Ontology and implement a possibility to fetch this information from the ontology 
		// cathegories are only dependent on DM's TP (e.g. BITE) and independent from the associated TPM
		HashMap<String, ArrayList<Object>> oOppositeTP = new HashMap<String, ArrayList<Object>> ();
		oOppositeTP.put("NOURISH", new ArrayList<Object>( Arrays.asList("BITE", "DEATH", 0.3, 0.0, 0.7, 0.2) ));
		oOppositeTP.put("BITE", new ArrayList<Object>( Arrays.asList("NOURISH", "LIFE", 0.3, 0.0, 0.7, 0.0) ));
		oOppositeTP.put("REPRESS", new ArrayList<Object>( Arrays.asList("DEPOSIT", "DEATH", 1.0, 0.1, 0.0, 0.5) ));
		oOppositeTP.put("DEPOSIT", new ArrayList<Object>( Arrays.asList("REPRESS", "LIFE", 1.0, 0.1, 0.0, 0.0) ));
		//oOppositeTP.put("PLEASURE", new ArrayList<Object>( Arrays.asList("UNPLEASURE", "LIFE", 0.3, 0, 0.7, 0.2) ));

		
		changeDrive (oForbiddenDrives_Input, oOppositeTP);
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 13.07.2012, 15:05:00
	 * 
	 * This is the function which represents the defense mechanism "sublimation"
	 * sublimation changes the drive aim
	 *
	 */
	private void defenseMechanism_Sublimation(ArrayList<String> oForbiddenDrives_Input) {
		
		// Testdata: Defining the sublimated drive aim (DM's TP). TODO: Represent this information in the Ontology and implement a possibility to fetch this information from the ontology 
		// cathegories are only dependent on DM's TP (e.g. BITE) and independent from the associated TPM
		HashMap<String, ArrayList<Object>> oOppositeTP = new HashMap<String, ArrayList<Object>> ();
		oOppositeTP.put("NOURISH", new ArrayList<Object>( Arrays.asList("TASTE_FOOD_FOR_OTHERS", "LIFE", 0.3, 0.0, 0.7, 0.0) ));
		oOppositeTP.put("BITE", new ArrayList<Object>( Arrays.asList("DESTROY_DANGEROUS_ANIMALS", "DEATH", 0.3, 0.0, 0.7, 0.2) ));
		oOppositeTP.put("REPRESS", new ArrayList<Object>( Arrays.asList("GUARD_DOR", "LIFE", 1.0, 0.1, 0.0, 0.0) ));
		oOppositeTP.put("DEPOSIT", new ArrayList<Object>( Arrays.asList("THROW_OUT_GARBAGE", "DEATH", 1.0, 0.1, 0.0, 0.0) ));

		
		changeDrive (oForbiddenDrives_Input, oOppositeTP);
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 13.07.2012, 16:05:00
	 * 
	 * This is the function which represents the defense mechanism "intellectualization"
	 * intellectualisation changes the drive aim
	 *
	 */
	private void defenseMechanism_Intellectualization(ArrayList<String> oForbiddenDrives_Input) {
		
		// Testdata: Defining the intellectualisation drive aim (DM's TP). TODO: Represent this information in the Ontology and implement a possibility to fetch this information from the ontology 
		// cathegories are only dependent on DM's TP (e.g. BITE) and independent from the associated TPM
		HashMap<String, ArrayList<Object>> oOppositeTP = new HashMap<String, ArrayList<Object>> ();
		oOppositeTP.put("NOURISH", new ArrayList<Object>( Arrays.asList("TAKE_PART_IN_SOCIAL_ACTIVITY", "LIFE", 0.3, 0.0, 0.7, 0.0) ));
		oOppositeTP.put("BITE", new ArrayList<Object>( Arrays.asList("MOVE_JAW_MUSCLES", "DEATH", 0.3, 0.0, 0.7, 0.2) ));
		oOppositeTP.put("REPRESS", new ArrayList<Object>( Arrays.asList("TRAIN_PATIENCE", "LIFE", 1.0, 0.1, 0.0, 0.0) ));
		oOppositeTP.put("DEPOSIT", new ArrayList<Object>( Arrays.asList("GET_RID_OF_WASTE", "DEATH", 1.0, 0.1, 0.0, 0.0) ));

		
		changeDrive (oForbiddenDrives_Input, oOppositeTP);
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 13.07.2012, 15:31:00
	 * 
	 * This method changes the drive aim.
	 * The parameter poOppositeTP is a list which assigns the altered drive aim to the original drive aim (it is only passed to the next method)
	 *
	 *
	 */
	private void changeDrive(ArrayList<String> oForbiddenDrives_Input, HashMap<String, ArrayList<Object>> poOppositeTP) {
		
		// Iterate over all forbidden drives
		for (String oContent : oForbiddenDrives_Input) {
		
			int i = 0;
			// search in list of incoming drives
			for(clsPair<clsPhysicalRepresentation, clsDriveMeshOLD> oDrives : moDriveList_Input){
				// check DriveMesh
				if (oDrives.b.getMoContent().equalsIgnoreCase(oContent)){
					
					// remove DriveMesh i from output list
					moDriveList_Output.remove(i);
					// drive found
					moDriveList_Output.add(changeDriveAim(oDrives.a, oDrives.b, poOppositeTP));
									
				}
				
				i++;
			}
		
		}
		return;
	}
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 10.10.2011, 14:43:00
	 * 
	 * This method changes the drive aim.
	 * The third parameter poOppositeTP is a list which assigns the altered drive aim to the original drive aim 
	 *
	 */
	private clsPair<clsPhysicalRepresentation, clsDriveMeshOLD> changeDriveAim(clsPhysicalRepresentation poOriginalTPM, clsDriveMeshOLD poOriginalDM, HashMap<String, ArrayList<Object>> poOppositeTP) {
		
		// Helper
		String oOppositeTPContent = "";
		eContentType oOppositeTPContentType;   
		int j = 0;
		double rPleasure = 0;
		
		// opposite Drive
		clsPair<clsPhysicalRepresentation, clsDriveMeshOLD> oOppositeDrive= null;
		
		
		// What is the opposite TP?	
		String oOriginalTPContent = poOriginalDM.getMoContent();

		oOppositeTPContent = (String) poOppositeTP.get(oOriginalTPContent).get(0);
		oOppositeTPContentType = (eContentType) poOppositeTP.get(oOriginalTPContent).get(1);
		
		
		// Opposite drive goal found?
		if(oOppositeTPContent.isEmpty()) {
			// error
			return null;
		}
		
		// search in list of incoming drives if drive similar to opposite drive already exists
		for(clsPair<clsPhysicalRepresentation, clsDriveMeshOLD> oDrives : moDriveList_Output){
			// check DriveMesh (TODO: only one kind of TPM in drivelist, so no compare of TPM necessary?)
			if (oDrives.b.getMoContent().equalsIgnoreCase(oOppositeTPContent)){
				// drive found
				oOppositeDrive = oDrives;
				break;
			}
			
			j++;
											
		}
		
		// If a drive similar to opposite drive exists in drivelist -> merge quota of affect
		if(oOppositeDrive != null) {			
			rPleasure = moDriveList_Output.get(j).b.getMrQuotaOfAffect() + poOriginalDM.getMrQuotaOfAffect();
			
			// remove from drivelist, new merged drive will be added to drivelist anyway
			moDriveList_Output.remove(j);
			
			if(rPleasure <= 1.0){
				oOppositeDrive.b.setPleasure(rPleasure);				
			}
			else {
				poOriginalDM.setPleasure(rPleasure - 1.0);
				oOppositeDrive.b.setPleasure(1.0);
				
				// overflow quota of affect will be sent to F20  
				// add single quota of affect to affect only list
				clsAffect oAffect = (clsAffect) clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<String, Object>("AFFECT", poOriginalDM.getMrQuotaOfAffect())); 
				moQuotasOfAffect_Output.add(oAffect);
				
				
				//orignialDM will be repressed later with 0 quota of affect
			}
		}
		else {
			// form opposite drive
			
			clsDriveMeshOLD oNewDM = null;
			//clsDriveMesh oAssDM = null;


			// new DM needed
			clsThingPresentation oTP = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(oOppositeTPContentType,oOppositeTPContent)); 
			oNewDM = clsDataStructureGenerator.generateDM(new clsTriple <eContentType, ArrayList<clsThingPresentation>, Object>(oOppositeTPContentType, new ArrayList<clsThingPresentation>(Arrays.asList(oTP)) , oOppositeTPContent));
			oNewDM.setCategories((Double)poOppositeTP.get(oOriginalTPContent).get(4), (Double)poOppositeTP.get(oOriginalTPContent).get(2), (Double)poOppositeTP.get(oOriginalTPContent).get(3), (Double)poOppositeTP.get(oOriginalTPContent).get(5));
						
			
			oOppositeDrive = new clsPair<clsPhysicalRepresentation, clsDriveMeshOLD>(poOriginalTPM, oNewDM);	
			
			oOppositeDrive.b.setMrQuotaOfAffect(poOriginalDM.getMrQuotaOfAffect());	
		}
		
		
	    // repress old forbidden drive
		repress_single_drive((clsThingPresentationMesh) poOriginalTPM, poOriginalDM);
		
		return oOppositeDrive;
		
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 10.10.2011, 14:43:00
	 * 
	 * This method represses a single drive. Copied from FG
	 *
	 */
	protected void repress_single_drive(clsThingPresentationMesh poTPM, clsDriveMesh  poDM) {
		
	// Only store the drive in blocked content storage, if there are no similar drives in blocked content storage
			if (!moBlockedContentStorage.existsMatch(poTPM, poDM)) {		
				// insert DriveMesh i into BlockedContentStorage
				send_D2_3(poTPM, poDM);
			}
			
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 30.06.2011, 14:43:00
	 * 
	 * This method represents the defense mechanism "repression"
	 * 
	 * ToDo (FG): For now the whole DriveMesh and the PhysicalRepresentation are repressed.
	 *            Later it could be possible to only repress the DriveMesh or only repress the PhysicalRepresentation.
	 */
    protected void defenseMechanism_Repression(ArrayList<String> oForbiddenDrives_Input) {
		
		// Iterate over all forbidden drives
		for (String oContent : oForbiddenDrives_Input) {
				
			int i = 0;
			// search in list of incoming drives
			for(clsPair<clsPhysicalRepresentation, clsDriveMeshOLD> oDrives : moDriveList_Output){
				// check DriveMesh
				if (oDrives.b.getMoContent().equalsIgnoreCase(oContent)){

					// drive found
				    break;
				}
				
				i++;
			}
			
			
			// if drive found	
			if (i < moDriveList_Output.size()) {
				
				//FIXME FG: Added by AW. Exchange ALL physical representation with TPM. All objects are TPM
				repress_single_drive(((clsThingPresentationMesh) moDriveList_Output.get(i).a), moDriveList_Output.get(i).b);				
				
				// add single quotas of affect to affect only list
				clsAffect oAffect = (clsAffect) clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<String, Object>("AFFECT", moDriveList_Output.get(i).b.getMrQuotaOfAffect())); 
				moQuotasOfAffect_Output.add(oAffect);
				
				// remove DriveMesh i from output list
				moDriveList_Output.remove(i);
			}
		}
    }	
    

	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 30.11.2011, 14:43:00
	 * 
	 * This method searches in the list of drives and returns the quota of affect (if a matching drive was found)
	 * 
	 */
   private double getQuotaOfAffect(ArrayList<String> oForbiddenDrives_Input) {
   	
   	// If nothing to repress return immediately (otherwise NullPointerException)
   	if (oForbiddenDrives_Input == null ) return 0.0;
		
		// Iterate over all forbidden drives
		for (String oContent : oForbiddenDrives_Input) {
				
			// search in list of incoming drives
			for(clsPair<clsPhysicalRepresentation, clsDriveMeshOLD> oDrives : moDriveList_Output){
				// check DriveMesh
				if (oDrives.b.getMoContent().equalsIgnoreCase(oContent)){

					// drive found
					// return quota of affect
				    return oDrives.b.getMrQuotaOfAffect();
				}
			}
		}
		
		return 0.0;
   }	
   
    
    /* (non-Javadoc)
	 *
	 * @author gelbard
	 * 11.08.2009, 16:15:00
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() { 
		send_I5_18(moDriveList_Output);
		send_I5_17(moQuotasOfAffect_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I1_6_send#send_I1_6(java.util.ArrayList)
	 */
	@Override
	public void send_I5_18(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poDriveList) {
		((I5_18_receive)moModuleList.get(8)).receive_I5_18(poDriveList);
		putInterfaceData(I5_18_send.class, poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:48:13
	 * 
	 * @see pa.interfaces.send.I5_1_send#send_I5_1(java.util.ArrayList)
	 */
	@Override
	public void send_I5_17(ArrayList<clsPrimaryDataStructure> poAffectOnlyList) {
		((I5_17_receive)moModuleList.get(20)).receive_I5_17(poAffectOnlyList);	
		putInterfaceData(I5_17_send.class, poAffectOnlyList);		
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 15.09.2011, 16:48:13
	 * 
	 * Sends blocked drive aims (clsDriveMesh) and drive objects (clsPhysicalRepresentation) to DT2_BlockedContentStorage
	 */
	@Override
	public void send_D2_3 (clsThingPresentationMesh poDS, clsDriveMesh poDM) {
		moBlockedContentStorage.receive_D2_3(poDS, poDM);	
		putInterfaceData(D2_3_send.class, poDS, poDM);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:45:33
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (GELBARD) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:45:33
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (GELBARD) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:39:04
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
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Based on information provided by {E7} and {E9}, this module decides which drive representations are allowed to become (pre-)conscious and if not which defense mechanism is to be applied. These mechanisms can split the thing presentations from their quota of affect, change the thing presentations, repress the contents until later, attach them to other contents, and more. Examples for these mechanisms are repression, intellectualization, displacement, and sublimination ({Schuster1997}). Repressed content reappears in {F54}";
	}
}
