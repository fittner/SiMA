/**
 * E43_SeparationIntoPartialSexualDrives.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:19:56
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import pa._v30.tools.toText;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I1_10_receive;
import pa._v30.interfaces.modules.I2_17_receive;
import pa._v30.interfaces.modules.I2_17_send;
import pa._v30.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsThingPresentation;
import pa._v30.memorymgmt.enums.eDataType;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:19:56
 * 
 */
public class E43_SeparationIntoPartialSexualDrives extends clsModuleBase implements I1_10_receive, I2_17_send {
	public static final String P_MODULENUMBER = "43";
	
	public static final String P_PARTIAL_ORAL = "oral";
	public static final String P_PARTIAL_ANAL = "anal";
	public static final String P_PARTIAL_PHALLIC = "phallic";
	public static final String P_PARTIAL_GENITAL = "genital";
	
	private ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > moHomeostaticDriveDemands;
	private ArrayList< clsPair< clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double> > > moDriveCandidates;
	private ArrayList< clsPair<String,Double> > moPartialSexualDrives;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:56:43
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E43_SeparationIntoPartialSexualDrives(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moHomeostaticDriveDemands", moHomeostaticDriveDemands);
		text += toText.listToTEXT("moDriveCandidates", moDriveCandidates);
		text += toText.listToTEXT("moPartialSexualDrives", moPartialSexualDrives);
		
		return text;
	}	
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		
		oProp.setProperty(pre+P_PARTIAL_ORAL, 0.1);
		oProp.setProperty(pre+P_PARTIAL_ANAL, 0.05);
		oProp.setProperty(pre+P_PARTIAL_PHALLIC, 0.2);
		oProp.setProperty(pre+P_PARTIAL_GENITAL, 0.6);
		
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
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
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic()  {
		moDriveCandidates = new ArrayList<clsPair<clsTripple<clsDriveMesh,clsDriveDemand,Double>,clsTripple<clsDriveMesh,clsDriveDemand,Double>>>();
		for (clsPair<String, Double> oPSD:moPartialSexualDrives) {
			if (moHomeostaticDriveDemands.size() == 2) {
				moDriveCandidates.add( createDMT_Double(oPSD) );
			} else {
				throw new java.lang.NoSuchMethodError();
				//E43_SeparationIntoPartialSexualDrives.process_basic(): don't know how to handle different number of entries for moHomeostaticDriveDemands.
			}
		}
	}
	
	//generate pairs of opposites. should be only one life and one death instinct available -> straight forward
	private clsPair< clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double> > createDMT_Double(clsPair<String, Double> oPSD) {
		String oContentType;
		String oContext;
		clsPair<clsDriveMesh, clsDriveDemand> oHDD;
		
		oHDD = moHomeostaticDriveDemands.get(0);
		oContentType = oHDD.a.getMoContentType();
		oContext = oHDD.a.getMoContent()+"_"+oPSD.a;
		clsTripple<clsDriveMesh,clsDriveDemand,Double> oT_A =	createDriveMeshTripple(oContentType, oContext, oHDD.b, oPSD.b);

		oHDD = moHomeostaticDriveDemands.get(1);
		oContentType = oHDD.a.getMoContentType();
		oContext = oHDD.a.getMoContent()+"_"+oPSD.a;
		clsTripple<clsDriveMesh,clsDriveDemand,Double> oT_B =	createDriveMeshTripple(oContentType, oContext, oHDD.b, oPSD.b);

		return new clsPair<clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double>>(oT_A, oT_B);
	}
	
	private clsTripple<clsDriveMesh,clsDriveDemand,Double> createDriveMeshTripple(String poContentType, String poContext, clsDriveDemand poDemand, Double prValue) {
		clsDriveMesh oDM = createDriveMesh(poContentType, poContext);
		clsTripple<clsDriveMesh,clsDriveDemand,Double> oT = new clsTripple<clsDriveMesh, clsDriveDemand, Double>(oDM, poDemand, prValue);
		return oT;
	}
	
	private clsDriveMesh createDriveMesh(String poContentType, String poContext) {
		clsThingPresentation oDataStructure = (clsThingPresentation)clsDataStructureGenerator.generateDataStructure( eDataType.TP, new clsPair<String, Object>(poContentType, poContext) );
		ArrayList<Object> oContent = new ArrayList<Object>( Arrays.asList(oDataStructure) );
		
		clsDriveMesh oRetVal = (pa._v30.memorymgmt.datatypes.clsDriveMesh)clsDataStructureGenerator.generateDataStructure( 
				eDataType.DM, new clsTripple<String, Object, Object>(poContentType, oContent, poContext)
				);
		
		return oRetVal;
	}	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:56
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_17(moDriveCandidates);

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:44
	 * 
	 * @see pa.interfaces.send._v30.I2_17_send#send_I2_17(java.util.ArrayList)
	 */
	@Override
	public void send_I2_17(ArrayList< clsPair< clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double> > > poDriveCandidates) {
		
		((I2_17_receive)moModuleList.get(42)).receive_I2_17(poDriveCandidates);
		putInterfaceData(I2_17_send.class, poDriveCandidates);
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:44
	 * 
	 * @see pa.interfaces.receive._v30.I1_10_receive#receive_I1_10(java.util.HashMap)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I1_10(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > poHomeostaticDriveDemands) {
		moHomeostaticDriveDemands = (ArrayList< clsPair<clsDriveMesh, clsDriveDemand> >)deepCopy(poHomeostaticDriveDemands);
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Each sexual drive is split apart into four drives representing the four partial drives. Module {E43} takes the aggressive and libidinous drives transmitted from {E41} and splits them according to predefined but individual templates. The result are eight sexual drives.  ";
	}	
}
