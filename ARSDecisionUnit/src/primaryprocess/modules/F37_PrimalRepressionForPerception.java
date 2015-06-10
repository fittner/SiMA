/**
 * E37_PrimalRepressionForPerception.java: DecisionUnits - pa.modules._v38
 * This function attaches the thing presentations to the repressed content and makes an association. The result is a constructed perception with associated repressed content.
 * 
 * @author hinterleitner
 * 05.08.2011, 10:22:10
 */
package primaryprocess.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;

import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.storage.DT2_BlockedContentStorage;
import modules.interfaces.I5_6_receive;
import modules.interfaces.I5_7_receive;
import modules.interfaces.I5_7_send;
import modules.interfaces.eInterfaces;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.clsAssociation;
import base.datatypes.clsAssociationDriveMesh;
import base.datatypes.clsDataStructurePA;
import base.datatypes.clsDriveMesh;
import base.datatypes.clsThingPresentationMesh;
import base.datatypes.clsWordPresentationMesh;
import base.datatypes.enums.eDriveComponent;
import base.datatypes.enums.ePartialDrive;
import base.datatypes.helpstructures.clsTriple;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
import testfunctions.clsTester;

/**
 * 
 * 
 * @author hinterleitner
 * 07.05.2012, 10:22:10
 * 
 */
public class F37_PrimalRepressionForPerception extends clsModuleBase 
implements I5_6_receive, I5_7_send  {
	public static final String P_MODULENUMBER = "37";
	
	/** Specialized Logger for this class */
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	public static final String P_ACTIVATION_THRESHOLD = "ACTIVATION_THRESHOLD";
	public static final String P_ACTIVATION_LIMIT = "ACTIVATION_LIMIT";

		/** Input perceived image (type template image) */
	private clsThingPresentationMesh moPerceptionalMesh_IN;
	/** Input associated activated memories */
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;

	/** Output evaluated image (type template image) */
	private clsThingPresentationMesh moPerceptionalMesh_OUT;
	/** Output associated activated memories */
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;

	/** The storage for primal repressed drives*/
	private ArrayList<clsDriveMesh> moPrimalRepressionMemory;
	
	/**
	 * Personality parameter:
	 * Minimum match factor a repressed drive must achieve to be activated. 
	 * 
	 * @author Marcus Zottl (e0226304)
	 * @since 19.09.2011 18:20:22
	 * */
	private double mrActivationThreshold;
	
	private clsWordPresentationMesh moWordingToContext;
	/**
	 * Personality parameter:
	 * Limit to adjust the maximum number of activated repressed drives.
	 * NOTE: unclear if such a limitation should be done at all?  
	 * 
	 * @author Marcus Zottl (e0226304)
	 * @since 19.09.2011 18:21:02
	 * */
	private int mnActivationLimit;


	/**
	 * Constructor
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:20:58
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F37_PrimalRepressionForPerception(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, 
			ArrayList<Object>> poInterfaceData, DT2_BlockedContentStorage poBlockedContentStorage,
			clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid)
	throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
		applyProperties(poPrefix, poProp);
		mrActivationThreshold = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_ACTIVATION_THRESHOLD).getParameterDouble();
		mnActivationLimit = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_ACTIVATION_LIMIT).getParameterInt();

		
		moPrimalRepressionMemory = new ArrayList<clsDriveMesh>();
		fillPrimalRepressionMemory();
	}

	/**
	 * Creates a few DriveMeshes and puts them in the PrimalRepressionMemory
	 *
	 * @author Marcus Zottl (e0226304)
	 * @since 19.09.2011 17:02:40
	 *
	 */
	private void fillPrimalRepressionMemory() {
		
	//TODO: check due to new DM-structure	
	ArrayList<ArrayList<Object>> oList = new ArrayList<ArrayList<Object>>();
  	oList.add( new ArrayList<Object>( Arrays.asList(eDriveComponent.AGGRESSIVE, ePartialDrive.UNDEFINED, "BITE", 0.5) ) );
  	oList.add( new ArrayList<Object>( Arrays.asList(eDriveComponent.LIBIDINOUS, ePartialDrive.UNDEFINED, "NOURISH",  0.3) ) );
  	oList.add( new ArrayList<Object>( Arrays.asList(eDriveComponent.AGGRESSIVE, ePartialDrive.UNDEFINED, "DEPOSIT",  0.7) ) );
  	oList.add( new ArrayList<Object>( Arrays.asList(eDriveComponent.LIBIDINOUS, ePartialDrive.UNDEFINED, "NOURISH", 0.1) ) );

  	for (ArrayList<Object> oData:oList) {
		clsDriveMesh oDM = clsDataStructureGenerator.generateDM(new clsTriple<eContentType, ArrayList<clsThingPresentationMesh>, Object>(eContentType.DRIVECOMPONENT, 
																   new ArrayList<clsThingPresentationMesh>(),	  oData.get(2)), (eDriveComponent)oData.get(0), (ePartialDrive)oData.get(1));
		oDM.setQuotaOfAffect( (Double)oData.get(3) ); 
		moPrimalRepressionMemory.add(oDM);    	
  	}
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

		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT", moPerceptionalMesh_OUT);
		text += toText.listToTEXT("moPrimalRepressionMemory", moPrimalRepressionMemory);

		return text;
	}	

	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

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
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		// clone input to allow for comparison before/after processing
		moPerceptionalMesh_OUT = moPerceptionalMesh_IN;
//		try {
//			//moPerceptionalMesh_OUT = (clsThingPresentationMesh)moPerceptionalMesh_IN.cloneGraph();
//			moPerceptionalMesh_OUT = (clsThingPresentationMesh)moPerceptionalMesh_IN.clone();
//		} catch (CloneNotSupportedException e) {
//			// TODO (wendt) - Auto-generated catch block
//			e.printStackTrace();
//		}

		evaluatePerception(moPerceptionalMesh_OUT);
		
		//=== Perform system tests ===//
		if (clsTester.getTester().isActivated()) {
			try {
				clsTester.getTester().exeTestAssociationAssignment(moPerceptionalMesh_OUT);
			} catch (Exception e) {
				log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
			}
		}
	}

	/**
	 * Acquires a list of matching drives from the primal repression storage and 
	 * enriches the incoming perception by adding some (mnActivationLimit) of 
	 * them to the output - which is now an evaluated version of the input.
	 *
	 * @since 19.09.2011 18:55:56
	 *
	 * @param poPerception_IN
	 */
	private void evaluatePerception(clsThingPresentationMesh poPerception_OUT) {
		ArrayList<clsTriple<clsThingPresentationMesh, clsAssociationDriveMesh, Double>> oMatchedDrives;

		// look up matching drives
		oMatchedDrives = matchRepressedDrives(poPerception_OUT);
		// now pick the topmost matches and process them accordingly
		int i = 0;
		for (clsTriple<clsThingPresentationMesh, clsAssociationDriveMesh, Double> matchedItem : oMatchedDrives) {
			i++;
			if (i >= mnActivationLimit) break;

			// attach DMs in result to the perception
			//moPerceptionalMesh_OUT.getExternalMoAssociatedContent().add(matchedItem.a);
			matchedItem.a.getExternalAssociatedContent().add(matchedItem.b);
		}
	}

	/**
	 * Compares each drive from the primal repression with the drives present in 
	 * the perception and returns those that achieve a sufficient matching (above 
	 * <i>mrActivationThreshold</i>) as a list of new associations (between them 
	 * and the corresponding object in the perception), sorted by the quality of 
	 * the match (descending).
	 *
	 * @author Marcus Zottl (e0226304)
	 * @since 19.09.2011 16:33:32
	 *
	 * @param poPerception_IN
	 * @param moAttachedRepressed_Output
	 */
	private ArrayList<clsTriple<clsThingPresentationMesh, clsAssociationDriveMesh, Double>> matchRepressedDrives(clsThingPresentationMesh poPerception_IN) {
		ArrayList<clsTriple<clsThingPresentationMesh, clsAssociationDriveMesh, Double>> oMatchValues = new ArrayList<clsTriple<clsThingPresentationMesh, clsAssociationDriveMesh, Double>>();
		
		// compare each element from moPrimalRepressionMemory with the input
		for (clsDriveMesh oEntry : moPrimalRepressionMemory) {
			for(clsAssociation oInputAssociation : poPerception_IN.getInternalAssociatedContent()) {
				clsDataStructurePA oObject = oInputAssociation.getLeafElement();
				if (oObject instanceof clsThingPresentationMesh) {
					
					for (clsAssociation oSubAss : ((clsThingPresentationMesh)oObject).getExternalAssociatedContent()) {
						if(oSubAss instanceof clsAssociationDriveMesh){
							clsDriveMesh oData = ((clsAssociationDriveMesh)oSubAss).getDM(); 
							if(oEntry.getContentType().equals(oData.getContentType())) {
								// calculate match between drives
								double rMatchValue = oEntry.compareTo(oData);
								// ignore matches below threshold
								if (rMatchValue < mrActivationThreshold)
									continue;

								// add the association with the matching element to the output 
								clsThingPresentationMesh newRoot = (clsThingPresentationMesh) ((clsAssociationDriveMesh)oSubAss).getRootElement();
								clsAssociationDriveMesh oNewAssociation =	new clsAssociationDriveMesh(
												new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONDM, eContentType.ASSOCIATIONDM),
												oEntry,
												newRoot);

								// ensure that the list of results is sorted by the matchValues, with the highest matchValues on top of the list.
								int i = 0;
								while ((i + 1 < oMatchValues.size()) && rMatchValue < oMatchValues.get(i).c) {
									i++;
								}
								// add to results
								oMatchValues.add(i, new clsTriple<clsThingPresentationMesh, clsAssociationDriveMesh, Double>((clsThingPresentationMesh) oObject, oNewAssociation, rMatchValue));
							}
						}
					}
				}
				
			}
		}
		return oMatchValues;
	}

//	/**
//	 * DOCUMENT (hinterleitner) - function adds a triple of primal repressions (oEntryPrimRep) to each incoming environmental repression 
//	 * 
//	 * @since 25.06.2011 14:57:36
//	 *
//	 */
//	private void matchRepressedDrives(clsPrimaryDataStructureContainer moConstrPerc, ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moAttachedRepressed_Output) 
//	{
//		moConstrPerc = moEnvironmentalPerception_IN; //Constructed Perception enters from the Interface
//
//		if (moConstrPerc != null)  
//		{
//			moAttachedRepressed_Output = new ArrayList<clsPair<clsPrimaryDataStructureContainer,clsDriveMesh>>();
//
//			for (clsPrimaryDataStructureContainer oPDSC:clsDataStructureConverter.convertTIContToTPMCont(moEnvironmentalPerception_IN)) 
//			{	
//				clsPair<clsPrimaryDataStructureContainer,clsDriveMesh> oEntryPrimRep = 
//					new clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>(
//							oPDSC, 
//							new clsDriveMesh(
//									new clsTriple<Integer, eDataType, String>(0, eDataType.UNDEFINED, "c"), 
//									0, 
//									new double[]{0.1,0.5,0.6,0.3}, 
//									null, null)
//					);
//
//					moAttachedRepressed_Output.add(oEntryPrimRep);
//					//System.out.println(moAttachedRepressed_Output);
//			}   			
//		}
//	}

	//check whether the format is equal to output format moEvaluatedEnvironment_OUT





	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (HINTERLEITNER) - Auto-generated method stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (HINTERLEITNER) - Auto-generated method stub
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_7(moPerceptionalMesh_OUT, moWordingToContext);
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:22:14
	 * 
	 * @see pa.interfaces.send._v38.I2_14_send#send_I2_14(java.util.ArrayList)
	 */
	@Override
	public void send_I5_7(clsThingPresentationMesh poPerceptionalMesh, clsWordPresentationMesh moWordingToContext2) {

		((I5_7_receive)moModuleList.get(35)).receive_I5_7(poPerceptionalMesh, moWordingToContext2);	//Associated memories only for perception
		
		putInterfaceData(I5_7_send.class, poPerceptionalMesh, moWordingToContext2);
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:22:14
	 * 
	 * @see pa.interfaces.receive._v38.I2_20_receive#receive_I2_20(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_6(clsThingPresentationMesh poPerceptionalMesh, clsWordPresentationMesh moWordingToContext2) {
	    moWordingToContext = moWordingToContext2;
	    moPerceptionalMesh_IN = poPerceptionalMesh;
//		try {
//			//moPerceptionalMesh_IN = (clsThingPresentationMesh)poPerceptionalMesh.cloneGraph();
//			moPerceptionalMesh_IN = (clsThingPresentationMesh)poPerceptionalMesh.clone();
//		} catch (CloneNotSupportedException e) {
//			// TODO (wendt) - Auto-generated catch block
//			e.printStackTrace();
//		}
		//moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
	}

	/**
	 * @author zeilinger
	 * 18.03.2011, 15:59:16
	 * 
	 * @return the moEnvironmental_IN
	 */
	//public clsPrimaryDataStructureContainer getMoEnvironmental_IN() {
	//	return moEnvironmentalPerception_IN;
	//}
	
	/**
	 * @author zeilinger
	 * 18.03.2011, 15:59:16
	 * 
	 * @return the moEvaluatedEnvironment_OUT
	 */
	//public clsPrimaryDataStructureContainer getMoEvaluatedEnvironment_OUT() {
	//	return moEvaluatedEnvironment_OUT;
	//}

	//public ArrayList<clsPrimaryDataStructureContainer> getMoAssociatedMemories_OUT() {
	//	return moAssociatedMemories_OUT;
	//}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "This function attaches the thing presentations to the repressed content and makes an association. The result is a constructed perception with associated repressed content.";
	}		
}
