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
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;

import config.clsProperties;

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
	
	private ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > moLibidoDriveDemands;
	private ArrayList< clsPair< clsTriple<clsDriveMesh,clsDriveDemand,Double>, clsTriple<clsDriveMesh,clsDriveDemand,Double> > > moDriveCandidates;
	private ArrayList< clsPair<String,Double> > moPartialSexualDrives;
	
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
		text += toText.listToTEXT("moPartialSexualDrives", moPartialSexualDrives);
		
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
	
		moPartialSexualDrives = new ArrayList<clsPair<String,Double>>();
		
		moPartialSexualDrives.add( new clsPair<String, Double>("ORAL", poProp.getPropertyDouble(pre+P_PARTIAL_ORAL) ));
		moPartialSexualDrives.add( new clsPair<String, Double>("ANAL", poProp.getPropertyDouble(pre+P_PARTIAL_ANAL) ));
		moPartialSexualDrives.add( new clsPair<String, Double>("PHALLIC", poProp.getPropertyDouble(pre+P_PARTIAL_PHALLIC) ));
		moPartialSexualDrives.add( new clsPair<String, Double>("GENITAL", poProp.getPropertyDouble(pre+P_PARTIAL_GENITAL) ));	
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
		moDriveCandidates = new ArrayList<clsPair<clsTriple<clsDriveMesh,clsDriveDemand,Double>,clsTriple<clsDriveMesh,clsDriveDemand,Double>>>();
		for (clsPair<String, Double> oPSD:moPartialSexualDrives) {
			if (moLibidoDriveDemands.size() == 2) {
				clsPair< clsTriple<clsDriveMesh,clsDriveDemand,Double>, clsTriple<clsDriveMesh,clsDriveDemand,Double> > oTMPDriveCandidate = createDMT_Double(oPSD);
				
				//set DM libido categories
				oTMPDriveCandidate.a.a.setCategories(moPartialSexualDrives.get(0).b, moPartialSexualDrives.get(1).b, moPartialSexualDrives.get(3).b, moPartialSexualDrives.get(2).b);

				//set DM agressive categories
				oTMPDriveCandidate.b.a.setCategories(moPartialSexualDrives.get(0).b, moPartialSexualDrives.get(1).b, moPartialSexualDrives.get(3).b, moPartialSexualDrives.get(2).b);
				
				moDriveCandidates.add( oTMPDriveCandidate );
			} else {
				throw new java.lang.NoSuchMethodError();
				//E43_SeparationIntoPartialSexualDrives.process_basic(): don't know how to handle different number of entries for moHomeostaticDriveDemands.
			}
		}
	}
	
	/**
	 * generate pairs of opposites. should be only one life and one death instinct available -> straight forward
	 *
	 * @since 12.07.2011 10:49:22
	 *
	 * @param oPSD
	 * @return
	 */
	private clsPair< clsTriple<clsDriveMesh,clsDriveDemand,Double>, clsTriple<clsDriveMesh,clsDriveDemand,Double> > createDMT_Double(clsPair<String, Double> oPSD) {
		eContentType oContentType;
		String oContext;
		clsPair<clsDriveMesh, clsDriveDemand> oHDD;
		
		//(0) = libido
		oHDD = moLibidoDriveDemands.get(0);
		oContentType = oHDD.a.getMoContentType();
		oContext = oHDD.a.getMoContent()+"_"+oPSD.a;
		clsTriple<clsDriveMesh,clsDriveDemand,Double> oT_A =	createDriveMeshTripple(oContentType, oContext, oHDD.b, oPSD.b);
		//oPSD.b  for the c part of the Tripple is the factor read from the propety files. no calculation is done! just added 
		//to pass the factor down to module F54

		//(1) = agressive
		oHDD = moLibidoDriveDemands.get(1);
		oContentType = oHDD.a.getMoContentType();
		oContext = oHDD.a.getMoContent()+"_"+oPSD.a;
		clsTriple<clsDriveMesh,clsDriveDemand,Double> oT_B =	createDriveMeshTripple(oContentType, oContext, oHDD.b, oPSD.b);

		return new clsPair<clsTriple<clsDriveMesh,clsDriveDemand,Double>, clsTriple<clsDriveMesh,clsDriveDemand,Double>>(oT_A, oT_B);
	}
	
	private clsTriple<clsDriveMesh,clsDriveDemand,Double> createDriveMeshTripple(eContentType poContentType, String poContext, clsDriveDemand poDemand, Double prValue) {
		clsDriveMesh oDM = createSexualDriveMesh(poContentType, poContext);
		clsTriple<clsDriveMesh,clsDriveDemand,Double> oT = new clsTriple<clsDriveMesh, clsDriveDemand, Double>(oDM, poDemand, prValue);
		return oT;
	}
	
	private clsDriveMesh createSexualDriveMesh(eContentType poContentType, String poContext) {
		clsThingPresentation oDataStructure = (clsThingPresentation)clsDataStructureGenerator.generateDataStructure( eDataType.TP, new clsPair<eContentType, Object>(poContentType, poContext) );
		ArrayList<Object> oContent = new ArrayList<Object>( Arrays.asList(oDataStructure) );
		
		clsDriveMesh oRetVal = (pa._v38.memorymgmt.datatypes.clsDriveMesh)clsDataStructureGenerator.generateDataStructure( 
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
		send_I3_3(moDriveCandidates);

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:44
	 * 
	 * @see pa.interfaces.send._v38.I2_17_send#send_I2_17(java.util.ArrayList)
	 */
	@Override
	public void send_I3_3(ArrayList< clsPair< clsTriple<clsDriveMesh,clsDriveDemand,Double>, clsTriple<clsDriveMesh,clsDriveDemand,Double> > > poDriveCandidates) {
		
		((I3_3_receive)moModuleList.get(48)).receive_I3_3(poDriveCandidates);
		putInterfaceData(I3_3_send.class, poDriveCandidates);
		
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
	public void receive_I3_1(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > poHomeostaticDriveDemands) {
		moLibidoDriveDemands = (ArrayList< clsPair<clsDriveMesh, clsDriveDemand> >)deepCopy(poHomeostaticDriveDemands);
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
