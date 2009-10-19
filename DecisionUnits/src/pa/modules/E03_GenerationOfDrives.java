/**
 * E3_GenerationOfDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:19:04
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsAssociationContext;
import pa.datatypes.clsDriveObject;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.datatypes.clsThingPresentationSingle;
import pa.interfaces.I1_2;
import pa.interfaces.I1_3;
import pa.loader.clsAffectCandidateDefinition;
import pa.loader.clsDriveLoader;
import pa.loader.clsTemplateDrive;
import pa.tools.clsPair;
import config.clsBWProperties;
import enums.pa.eDriveContent;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:19:04
 * 
 */
public class E03_GenerationOfDrives extends clsModuleBase implements I1_2 {

	public static String moDriveObjectType = "DriveObject";
	
	public HashMap<eDriveContent, clsTemplateDrive> moDriveDefinition = null;
	public HashMap<String, Double> moHomeostasisSymbols = null;
	
	ArrayList<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>> moEnvironmentalTP;
	
	/**
	 * @author langr
	 * 28.09.2009, 19:21:32
	 * 
	 * @return the moDriveDefinition
	 */
	public HashMap<eDriveContent, clsTemplateDrive> getDriveDefinition() {
		return moDriveDefinition;
	}

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 12:19:24
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E03_GenerationOfDrives(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		
		applyProperties(poPrefix, poProp);	
		loadDriveDefinition(poPrefix, poProp);
	}
	
	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 23.09.2009, 14:31:31
	 *
	 */
	private void loadDriveDefinition(String poPrefix, clsBWProperties poProp) {
	      
		//TODO - (langr): read team-name from property file!
		moDriveDefinition = clsDriveLoader.createDriveList("1", "PSY_10");
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
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
		mnPsychicInstances = ePsychicInstances.ID;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 13:46:56
	 * 
	 * @see pa.interfaces.I1_2#receive_I1_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_2(HashMap<String, Double> poHomeostasisSymbols) {
		moHomeostasisSymbols = (HashMap<String, Double>)deepCopy(poHomeostasisSymbols);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:48
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {

		moEnvironmentalTP = new ArrayList<clsPair<clsPrimaryInformationMesh,clsAffectCandidate>>();
		
		for( Map.Entry<eDriveContent, clsTemplateDrive> oDriveDef : moDriveDefinition.entrySet() ) {
			
			clsTemplateDrive oTPDrive = oDriveDef.getValue();
			
			clsPrimaryInformationMesh oDriveMesh = new clsPrimaryInformationMesh(new clsThingPresentationSingle());
			clsAffectCandidate oAffectCandidate = null;
			
			oDriveMesh.moTP.meContentName = oTPDrive.moName;
			oDriveMesh.moTP.meContentType = oTPDrive.meDriveContent.getClass().getName();
			oDriveMesh.moTP.moContent = oTPDrive.meDriveContent;
			oDriveMesh.moTP.moDriveContentCategory = oTPDrive.moDriveContentRatio;
			
			for( clsDriveObject oDriveObject : oTPDrive.moDriveObjects ) {
				clsThingPresentationSingle oTPSingle = new clsThingPresentationSingle();
				clsPrimaryInformation oPrimary = new clsPrimaryInformation(oTPSingle);
				oTPSingle.meContentName = moDriveObjectType;
				oTPSingle.meContentType = oDriveObject.meType.getClass().getName();
				oTPSingle.moContent = oDriveObject.meType;
				
				//creating the association between the mesh and the attribute
				clsAssociationContext<clsPrimaryInformation> oAssoc = new clsAssociationContext<clsPrimaryInformation>();
				oAssoc.moElementA = oDriveMesh;
				oAssoc.moElementB = oPrimary;
				oAssoc.moAssociationContext = new clsPrimaryInformation(new clsThingPresentationSingle(moDriveObjectType, oDriveObject.meContext.getClass().getName(), oDriveObject.meContext)); 
				//storing the association in the mesh
				oDriveMesh.moAssociations.add(oAssoc);
			}
			
			oAffectCandidate = createAffectCandidate( oDriveDef );
			
			moEnvironmentalTP.add(new clsPair<clsPrimaryInformationMesh, clsAffectCandidate>(oDriveMesh, oAffectCandidate));
		}
	}

	/**
	 * DOCUMENT (langr) - calculates the current value of the drive-tension for one drive. 
	 * can be originated in several 'organs' = internal-sensor values 
	 *
	 * @author langr
	 * 13.10.2009, 16:33:56
	 *
	 * @param driveDef
	 * @return
	 */
	private clsAffectCandidate createAffectCandidate(
			Entry<eDriveContent, clsTemplateDrive> driveDef) {

		clsAffectCandidate oRetVal = new clsAffectCandidate();

		for( clsAffectCandidateDefinition oCandidateDef : driveDef.getValue().moAffectCandidate ) {
			
			if( moHomeostasisSymbols.containsKey(oCandidateDef.moSensorType) ) {
				double rValue = moHomeostasisSymbols.get( oCandidateDef.moSensorType );
				
				if(oCandidateDef.mnInverse) {
					oRetVal.mrTensionValue += ((oCandidateDef.mrMaxValue-rValue) / oCandidateDef.mrMaxValue)*oCandidateDef.mrRatio;
				} 
				else {
					oRetVal.mrTensionValue += (rValue / oCandidateDef.mrMaxValue) * oCandidateDef.mrRatio;
				}
			}
		}

		return oRetVal;  //null;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:49
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I1_3)moEnclosingContainer).receive_I1_3(moEnvironmentalTP);
	}

}
