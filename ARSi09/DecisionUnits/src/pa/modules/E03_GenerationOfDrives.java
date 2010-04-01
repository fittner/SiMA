/**
 * E3_GenerationOfDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:19:04
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;

import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsAssociationContext;
import pa.datatypes.clsDriveMesh;
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

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:19:04
 * 
 */
public class E03_GenerationOfDrives extends clsModuleBase implements I1_2 {

	public static String moDriveObjectType = "DriveObject";
	
	public ArrayList<clsPair<clsTemplateDrive, clsTemplateDrive>> moDriveDefinition = null;
	public HashMap<String, Double> moHomeostasisSymbols = null;
	
	ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
			  		  clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> moHomeostaticTP;
	
	/**
	 * @author langr
	 * 28.09.2009, 19:21:32
	 * 
	 * @return the moDriveDefinition
	 */
	public ArrayList<clsPair<clsTemplateDrive, clsTemplateDrive>> getDriveDefinition() {
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
	@SuppressWarnings("unchecked")
	@Override
	protected void process() {

		moHomeostaticTP = new ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
		  clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>>();
		
		for( clsPair<clsTemplateDrive, clsTemplateDrive> oDriveDef : moDriveDefinition ) {
			
			clsPair<clsDriveMesh, clsAffectCandidate> oDrive1 = createDrive(oDriveDef.a);
			clsPair<clsDriveMesh, clsAffectCandidate> oDrive2 = createDrive(oDriveDef.b);
			oDrive1.a.moCounterDrive = oDrive2.a;	//marry drive couple
			oDrive2.a.moCounterDrive = oDrive1.a;
			
			moHomeostaticTP.add(new clsPair( oDrive1, oDrive2 ));
		}
	}

	public clsPair<clsDriveMesh, clsAffectCandidate> createDrive(clsTemplateDrive oTPDrive) {
		
		clsDriveMesh oDriveMesh = new clsDriveMesh(new clsThingPresentationSingle());
		clsAffectCandidate oAffectCandidate = null;
		
		oDriveMesh.moTP.meContentName = oTPDrive.moName;
		oDriveMesh.moTP.meContentType = oTPDrive.meDriveContent.getClass().getName();
		oDriveMesh.moTP.moContent = oTPDrive.meDriveContent;
		oDriveMesh.moTP.moDriveContentCategory = oTPDrive.moDriveContentRatio;
		
		oDriveMesh.meDriveType = oTPDrive.meDriveType;
		
		
		//create drive target
		clsThingPresentationSingle oDriveTarget = new clsThingPresentationSingle();
		oDriveTarget.meContentName = "Drivetarget";
		oDriveTarget.meContentType = "Drivetarget";
		oDriveTarget.moContent = oTPDrive.meDriveContent;
		clsPrimaryInformation oPrimaryTarget = new clsPrimaryInformation(oDriveTarget);
		clsAssociationContext<clsPrimaryInformation> oAssocTarget = new clsAssociationContext<clsPrimaryInformation>();
		oAssocTarget.moElementA = oDriveMesh;
		oAssocTarget.moElementB = oPrimaryTarget;
		oAssocTarget.moAssociationContext = new clsPrimaryInformation(new clsThingPresentationSingle(moDriveObjectType, "target", null)); 
		//storing the association in the mesh
		oDriveMesh.moAssociations.add(oAssocTarget);
		
		
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
		
		oAffectCandidate = createAffectCandidate( oTPDrive );
		
		return new clsPair<clsDriveMesh, clsAffectCandidate>(oDriveMesh, oAffectCandidate);
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
			clsTemplateDrive poDriveDef) {

		clsAffectCandidate oRetVal = new clsAffectCandidate();

		for( clsAffectCandidateDefinition oCandidateDef : poDriveDef.moAffectCandidate ) {
			
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
		((I1_3)moEnclosingContainer).receive_I1_3(moHomeostaticTP);
	}

}
