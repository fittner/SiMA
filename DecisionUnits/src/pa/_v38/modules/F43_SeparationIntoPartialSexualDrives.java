/**
 * E43_SeparationIntoPartialSexualDrives.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:19:56
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I3_1_receive;
import pa._v38.interfaces.modules.I3_3_receive;
import pa._v38.interfaces.modules.I3_3_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsDriveDemand;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

import config.clsProperties;
import du.enums.eOrgan;
import du.enums.eOrifice;
import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;

/**
 * Each sexual drive is split apart into four drives representing the four partial drives. Module {F43} 
 * takes the aggressive and libidinous drives transmitted from {E41} and splits them according to 
 * predefined but individual templates. The result are eight sexual drives.
 * 
 * @author muchitsch
 * 07.05.2012, 15:19:56
 * 
 */
public class F43_SeparationIntoPartialSexualDrives extends clsModuleBase implements I3_1_receive, I3_3_send {
	public static final String P_MODULENUMBER = "43";
	
	public static final String P_PARTIAL_ORAL = "oral";
	public static final String P_PARTIAL_ANAL = "anal";
	public static final String P_PARTIAL_PHALLIC = "phallic";
	public static final String P_PARTIAL_GENITAL = "genital";
	
	private ArrayList< clsPair<clsDriveMeshOLD, clsDriveDemand> > moLibidoDriveDemands;
	private ArrayList< clsPair< clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>, clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> > > moDriveCandidates;
	private ArrayList< clsPair<ePartialDrive,Double> > moPartialSexualDrivesFactors;

	private ArrayList<clsPair<clsDriveMesh, clsDriveMesh>> moSexualDriveComponents_IN;
	
	private ArrayList<clsDriveMesh> moSexualDriveRepresentations_OUT;
	
	private HashMap<ePartialDrive, Double> moSexualDrivesImpactFactors = new HashMap<ePartialDrive, Double>();
	
	/**
	 * basic constructor
	 * 
	 * @author muchitsch
	 * 03.03.2011, 17:56:43
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F43_SeparationIntoPartialSexualDrives(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
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
		
		text += toText.listToTEXT("moLibidoDriveDemands", moLibidoDriveDemands);
		text += toText.listToTEXT("moDriveCandidates", moDriveCandidates);
		text += toText.listToTEXT("moPartialSexualDrives", moPartialSexualDrivesFactors);
		
		return text;
	}	
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		
		oProp.setProperty(pre+P_PARTIAL_ORAL, 0.1);
		oProp.setProperty(pre+P_PARTIAL_ANAL, 0.05);
		oProp.setProperty(pre+P_PARTIAL_PHALLIC, 0.2);
		oProp.setProperty(pre+P_PARTIAL_GENITAL, 0.6);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);

		moSexualDrivesImpactFactors.put(ePartialDrive.ORAL, poProp.getPropertyDouble(pre+P_PARTIAL_ORAL));
		moSexualDrivesImpactFactors.put(ePartialDrive.ANAL, poProp.getPropertyDouble(pre+P_PARTIAL_ANAL));
		moSexualDrivesImpactFactors.put(ePartialDrive.PHALLIC, poProp.getPropertyDouble(pre+P_PARTIAL_PHALLIC));
		moSexualDrivesImpactFactors.put(ePartialDrive.GENITAL, poProp.getPropertyDouble(pre+P_PARTIAL_GENITAL));
	}	
	
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic()  {
		
		moSexualDriveRepresentations_OUT  = new ArrayList<clsDriveMesh>();
		
		if(moSexualDriveComponents_IN.size() > 1)
			System.out.printf("ERROR: There can only be 2 sexual drives components commin to F43, thus Arrayhas only one entry, always");
		
		//even if it only can have one entry, safety first...
		for( clsPair<clsDriveMesh, clsDriveMesh> oEntry :  moSexualDriveComponents_IN)
		{
			//create A,O,P,G for the agressive component
			CreateAgressiveDriveRepresentations((clsDriveMesh)oEntry.a);
			
			//create A,O,P,G for the libidoneus component
			CreateLibidoneusDriveRepresentations((clsDriveMesh)oEntry.a);
		}
		

		
//		moDriveCandidates = new ArrayList<clsPair<clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>,clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>>>();
//		for (clsPair<String, Double> oPSD:moPartialSexualDrivesFactors) {
//			if (moLibidoDriveDemands.size() == 2) {
//				clsPair< clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>, clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> > oTMPDriveCandidate = createDMT_Double(oPSD);
//				
//				//set DM libido categories
//				oTMPDriveCandidate.a.a.setCategories(moPartialSexualDrivesFactors.get(0).b, moPartialSexualDrivesFactors.get(1).b, moPartialSexualDrivesFactors.get(3).b, moPartialSexualDrivesFactors.get(2).b);
//
//				//set DM agressive categories
//				oTMPDriveCandidate.b.a.setCategories(moPartialSexualDrivesFactors.get(0).b, moPartialSexualDrivesFactors.get(1).b, moPartialSexualDrivesFactors.get(3).b, moPartialSexualDrivesFactors.get(2).b);
//				
//				moDriveCandidates.add( oTMPDriveCandidate );
//			} else {
//				throw new java.lang.NoSuchMethodError();
//				//E43_SeparationIntoPartialSexualDrives.process_basic(): don't know how to handle different number of entries for moHomeostaticDriveDemands.
//			}
//		}
	}
	
	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 19.07.2012 11:25:00
	 *
	 * @param a
	 */
	private void CreateAgressiveDriveRepresentations(clsDriveMesh a) {
		double rAgressiveTension = a.getQuotaOfAffect();
		
		clsDriveMesh oAADM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.RECTAL_MUCOSA, eDriveComponent.AGGRESSIVE);
		clsDriveMesh oAODM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.ORAL_MUCOSA, eDriveComponent.AGGRESSIVE);
		//TODO, create according to sex femal/male
		clsDriveMesh oAPDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.PHALLUS, eDriveComponent.AGGRESSIVE);
		clsDriveMesh oAGDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.MALE_GENITAL, eDriveComponent.AGGRESSIVE);
		
		//calculate tension according to personality
		oAADM.setQuotaOfAffect( CalculateNewPartialTension(oAADM));
		oAODM.setQuotaOfAffect( CalculateNewPartialTension(oAODM));
		oAPDM.setQuotaOfAffect( CalculateNewPartialTension(oAPDM));
		oAGDM.setQuotaOfAffect( CalculateNewPartialTension(oAGDM));
		
		//add the 4 to the out list
		moSexualDriveRepresentations_OUT.add(oAADM);
		moSexualDriveRepresentations_OUT.add(oAODM);
		moSexualDriveRepresentations_OUT.add(oAPDM);
		moSexualDriveRepresentations_OUT.add(oAGDM);
	}
	
	private double CalculateNewPartialTension(clsDriveMesh poSexualDrive)
	{
		double rNewTension = 0.0;
		if(moSexualDrivesImpactFactors.containsKey( poSexualDrive.getDriveComponent() ) )
		{
			try 
			{
				double rImpactFactor = moSexualDrivesImpactFactors.get(poSexualDrive.getDriveComponent());
				rNewTension = poSexualDrive.getQuotaOfAffect() * rImpactFactor;
			} catch (java.lang.Exception e) {
				System.out.print(e);
			}
		}
		return rNewTension;
	}
	
	private clsDriveMesh CreateDriveRepresentations(eOrgan poOrgan, eOrifice poOrifice, eDriveComponent oComponent) {
		clsDriveMesh oDriveCandidate  = null;
		
		String oOrgan = poOrgan.toString();
		String oOrifice = poOrifice.toString();
		
		try {
		
		
		//create a TPM for the organ
			clsThingPresentationMesh oOrganTPM = 
				(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
						eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORGAN, new ArrayList<clsThingPresentation>(), oOrgan) );

		
		//create a TPM for the orifice
			clsThingPresentationMesh oOrificeTPM = 
				(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
						eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORIFICE, new ArrayList<clsThingPresentation>(), oOrifice) );

		
		//create the DM
		oDriveCandidate = (clsDriveMesh)clsDataStructureGenerator.generateDM(new clsTriple<eContentType, ArrayList<clsThingPresentationMesh>, 
				Object>(eContentType.DRIVEREPRESENTATION, new ArrayList<clsThingPresentationMesh>(), "") 
				,eDriveComponent.UNDEFINED, ePartialDrive.UNDEFINED );
		
		//supplement the information
		
		oDriveCandidate.setDriveComponent(oComponent);
		
		oDriveCandidate.associateActualDriveSource(oOrganTPM, 1.0);
		
		
		oDriveCandidate.associateActualBodyOrifice(oOrificeTPM, 1.0);
		
		} catch (Exception e) {
			// TODO (muchitsch) - Auto-generated catch block
			e.printStackTrace();
		}
		
	return oDriveCandidate;
	}
	
	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 19.07.2012 11:25:00
	 *
	 * @param a
	 */
	private void CreateLibidoneusDriveRepresentations(clsDriveMesh a) {
		double rAgressiveTension = a.getQuotaOfAffect();
		
		clsDriveMesh oLADM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.RECTAL_MUCOSA, eDriveComponent.LIBIDINOUS);
		clsDriveMesh oLODM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.ORAL_MUCOSA, eDriveComponent.LIBIDINOUS);
		//TODO, create according to sex femal/male
		clsDriveMesh oLPDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.PHALLUS, eDriveComponent.LIBIDINOUS);
		clsDriveMesh oLGDM = CreateDriveRepresentations(eOrgan.LIBIDO, eOrifice.MALE_GENITAL, eDriveComponent.LIBIDINOUS);
		
		//calculate tension according to personality
		oLADM.setQuotaOfAffect( CalculateNewPartialTension(oLADM));
		oLODM.setQuotaOfAffect( CalculateNewPartialTension(oLODM));
		oLPDM.setQuotaOfAffect( CalculateNewPartialTension(oLPDM));
		oLGDM.setQuotaOfAffect( CalculateNewPartialTension(oLGDM));
		
		//add the 4 to the out list
		moSexualDriveRepresentations_OUT.add(oLADM);
		moSexualDriveRepresentations_OUT.add(oLODM);
		moSexualDriveRepresentations_OUT.add(oLPDM);
		moSexualDriveRepresentations_OUT.add(oLGDM);
		
	}

	/**
	 * generate pairs of opposites. should be only one life and one death instinct available -> straight forward
	 *
	 * @since 12.07.2011 10:49:22
	 *
	 * @param oPSD
	 * @return
	 */
	private clsPair< clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>, clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> > createDMT_Double(clsPair<String, Double> oPSD) {
		eContentType oContentType;
		String oContext;
		clsPair<clsDriveMeshOLD, clsDriveDemand> oHDD;
		
		//(0) = libido
		oHDD = moLibidoDriveDemands.get(0);
		oContentType = oHDD.a.getMoContentType();
		oContext = oHDD.a.getMoContent()+"_"+oPSD.a;
		clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> oT_A =	createDriveMeshTripple(oContentType, oContext, oHDD.b, oPSD.b);
		//oPSD.b  for the c part of the Tripple is the factor read from the propety files. no calculation is done! just added 
		//to pass the factor down to module F54

		//(1) = agressive
		oHDD = moLibidoDriveDemands.get(1);
		oContentType = oHDD.a.getMoContentType();
		oContext = oHDD.a.getMoContent()+"_"+oPSD.a;
		clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> oT_B =	createDriveMeshTripple(oContentType, oContext, oHDD.b, oPSD.b);

		return new clsPair<clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>, clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>>(oT_A, oT_B);
	}
	
	private clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> createDriveMeshTripple(eContentType poContentType, String poContext, clsDriveDemand poDemand, Double prValue) {
		clsDriveMeshOLD oDM = createSexualDriveMesh(poContentType, poContext);
		clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> oT = new clsTriple<clsDriveMeshOLD, clsDriveDemand, Double>(oDM, poDemand, prValue);
		return oT;
	}
	
	private clsDriveMeshOLD createSexualDriveMesh(eContentType poContentType, String poContext) {
		clsThingPresentation oDataStructure = (clsThingPresentation)clsDataStructureGenerator.generateDataStructure( eDataType.TP, new clsPair<eContentType, Object>(poContentType, poContext) );
		ArrayList<Object> oContent = new ArrayList<Object>( Arrays.asList(oDataStructure) );
		
		clsDriveMeshOLD oRetVal = (pa._v38.memorymgmt.datatypes.clsDriveMeshOLD)clsDataStructureGenerator.generateDataStructure( 
				eDataType.DM, new clsTriple<eContentType, Object, Object>(poContentType, oContent, poContext)
				);
		
		oRetVal.mbSexualDM = true; // temporary solution to dinstinguish sexual drives from self-preservation drives
		return oRetVal;
	}	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	/* (non-Javadoc)
	 *
	 * @since 12.07.2011 10:49:10
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I3_3(moSexualDriveRepresentations_OUT);

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:44
	 * 
	 * @see pa.interfaces.send._v38.I2_17_send#send_I2_17(java.util.ArrayList)
	 */
	@Override
	public void send_I3_3(ArrayList<clsDriveMesh> poSexualDriveRepresentations) {
		
		((I3_3_receive)moModuleList.get(48)).receive_I3_3(poSexualDriveRepresentations);
		putInterfaceData(I3_3_send.class, poSexualDriveRepresentations);
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:44
	 * 
	 * @see pa.interfaces.receive._v38.I1_10_receive#receive_I1_10(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I3_1(ArrayList< clsPair<clsDriveMesh, clsDriveMesh> > poSexualDriveComponents) {
		moSexualDriveComponents_IN = (ArrayList< clsPair<clsDriveMesh, clsDriveMesh> >)deepCopy(poSexualDriveComponents);
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
		moDescription = "Each sexual drive is split apart into four drives representing the four partial drives. Module {E43} takes the aggressive and libidinous drives transmitted from {E41} and splits them according to predefined but individual templates. The result are eight sexual drives.  ";
	}	
}
