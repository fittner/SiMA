/**
 * E3_GenerationOfDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:19:04
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.HashMap;

import pa._v19.clsInterfaceHandler;
import pa.interfaces.receive.I1_2_receive;
import pa.interfaces.receive.I1_3_receive;
import pa.interfaces.send.I1_3_send;
import pa.loader.clsAffectCandidateDefinition;
import pa.loader.clsDriveLoader;
import pa.loader.clsTemplateDrive;
import pa.memorymgmt.datahandler.clsDataStructureGenerator;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsThingPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;
import pa.tools.clsTripple;
import config.clsBWProperties;
import du.enums.pa.eContext;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:19:04
 * 
 */
public class E03_GenerationOfDrives extends clsModuleBase implements I1_2_receive, I1_3_send {

	public static String moDriveObjectType = "DriveObject";
	
	public ArrayList<clsPair<clsTemplateDrive, clsTemplateDrive>> moDriveDefinition = null;
	public HashMap<String, Double> moHomeostasisSymbols = null;
	
	ArrayList<clsPair<clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand>, 
	                  clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand>>> moHomeostaticTP; 
	
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
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		
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
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
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
	protected void process_basic() {
		moHomeostaticTP = new ArrayList<clsPair<clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand>, 
		                                clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand>>>();
		
		for( clsPair<clsTemplateDrive, clsTemplateDrive> oDriveDef : moDriveDefinition ) {
			clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand> oMeshA = createDrive(oDriveDef.a); 
			clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand> oMeshB = createDrive(oDriveDef.b); 
			moHomeostaticTP.add(new clsPair<clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand>, 
									clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand>>(oMeshA, oMeshB)); 
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 14.08.2010, 16:39:10
	 *
	 * @param oDriveDef
	 * @return
	 */
	private clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand> createDrive(clsTemplateDrive poDriveDef) {
		pa.memorymgmt.datatypes.clsDriveMesh oDriveMesh = null; 
		clsDriveDemand oDemand = null; 
		
		oDriveMesh = createDriveMesh(poDriveDef); 
		oDemand = createDemand(poDriveDef); 
		return new clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand>(oDriveMesh, oDemand);
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 14.08.2010, 17:57:27
	 *
	 * @param poDriveDef
	 * @return
	 */
	private pa.memorymgmt.datatypes.clsDriveMesh createDriveMesh(clsTemplateDrive poDriveDef) {
		pa.memorymgmt.datatypes.clsDriveMesh oRetVal = null;
		
		//String oContentName = poDriveDef.moName; 
		String oContentType = poDriveDef.meDriveType.toString(); 
		String oContext = poDriveDef.meDriveContent.toString(); 
		clsThingPresentation oDataStructure = (clsThingPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair<String, Object>(oContentType,oContext));

		//HZ 15.08.2010: In the actual design the ArrayList oContent is not required. Actually every drive mesh has only one associated drive context; This makes 
		// sense but is not approved completely and may be changed. Maybe a drive mesh has to store the counter drive too; Hence this ArrayList remains here
		// even it is actually filled with only one entry.  
		ArrayList<Object> oContent = new ArrayList<Object>(); 
		oContent.add(oDataStructure); 
		
		oRetVal = (pa.memorymgmt.datatypes.clsDriveMesh)clsDataStructureGenerator.generateDataStructure(eDataType.DM, new clsTripple<String,Object, Object>(oContentType, oContent, oContext));
		
		oRetVal.setAnal(poDriveDef.moDriveContentRatio.get(eContext.DEFAULT).getAnal());
		oRetVal.setGenital(poDriveDef.moDriveContentRatio.get(eContext.DEFAULT).getGenital());
		oRetVal.setOral(poDriveDef.moDriveContentRatio.get(eContext.DEFAULT).getOral());
		oRetVal.setPhallic(poDriveDef.moDriveContentRatio.get(eContext.DEFAULT).getPhallic());
		
		return oRetVal;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 14.08.2010, 17:45:46
	 *
	 * @param poDriveDef
	 * @return
	 */
	private clsDriveDemand createDemand(clsTemplateDrive poDriveDef) {
		double rDemand = 0.0; 
		
		for(clsAffectCandidateDefinition oCandidateDef : poDriveDef.moAffectCandidate) {
			if( moHomeostasisSymbols.containsKey(oCandidateDef.moSensorType)) {
				double rValue = moHomeostasisSymbols.get(oCandidateDef.moSensorType);
				
				if(oCandidateDef.mnInverse) {rDemand += ((oCandidateDef.mrMaxValue-rValue)/oCandidateDef.mrMaxValue)*oCandidateDef.mrRatio;} 
				else 						{rDemand += (rValue/oCandidateDef.mrMaxValue)*oCandidateDef.mrRatio;}
			}
		}
		return (clsDriveDemand)clsDataStructureGenerator.generateDataStructure(eDataType.DRIVEDEMAND, new clsPair<String,Object>(eDataType.DRIVEDEMAND.toString(),rDemand));  
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
		send_I1_3(moHomeostaticTP);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:44:46
	 * 
	 * @see pa.interfaces.send.I1_3_send#send_I1_3(java.util.ArrayList)
	 */
	@Override
	public void send_I1_3(ArrayList<clsPair<clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand>, clsPair<pa.memorymgmt.datatypes.clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		((I1_3_receive)moEnclosingContainer).receive_I1_3(moHomeostaticTP); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:42:01
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:42:01
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

}
