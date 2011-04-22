/**
 * E41_Libidostasis.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:18:57
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInspectorGenericTimeChart;
import pa._v30.interfaces.modules.I1_10_receive;
import pa._v30.interfaces.modules.I1_10_send;
import pa._v30.interfaces.modules.I1_9_receive;
import pa._v30.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsThingPresentation;
import pa._v30.memorymgmt.enums.eDataType;
import pa._v30.storage.clsLibidoBuffer;
import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import pa._v30.tools.toHtml;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:18:57
 * 
 */
public class E41_Libidostasis extends clsModuleBase implements I1_9_receive, I1_10_send, itfInspectorGenericTimeChart {
	public static final String P_MODULENUMBER = "41";
	
	private clsLibidoBuffer moLibidoBuffer;
	private double mrIncomingLibido;
	private double mrTotalLibido;
	
	private ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > moDrives;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 15:47:47
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public E41_Libidostasis(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, 
			clsLibidoBuffer poLibidoBuffer) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		moLibidoBuffer = poLibidoBuffer;
		mrIncomingLibido = 0;
		mrTotalLibido = 0;
		
		applyProperties(poPrefix, poProp);	
	}
	
	private ArrayList< clsDriveMesh > createDriveMeshes() {
		ArrayList< clsDriveMesh > oDrives = new ArrayList< clsDriveMesh >();
		
		oDrives.add( createDriveMesh("LIFE",  "LIBIDINOUS") );
		oDrives.add( createDriveMesh("DEATH", "AGGRESSIVE") );
		
		return oDrives;
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
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToHTML()
	 */
	@Override
	public String stateToHTML() {
		String html ="";
		
		html += toHtml.listToHTML("moDrives", moDrives);
		html += toHtml.valueToHTML("moLibidoBuffer", moLibidoBuffer);
		html += toHtml.valueToHTML("mrIncomingLibido", mrIncomingLibido);		
		html += toHtml.valueToHTML("mrTotalLibido", mrTotalLibido);
		
		return html;
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
	
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

	private void updateTempLibido() {
		moLibidoBuffer.receive_D1_1(mrIncomingLibido);
		mrTotalLibido = moLibidoBuffer.send_D1_2();
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:22
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		moDrives = new ArrayList<clsPair<clsDriveMesh,clsDriveDemand>>();
		updateTempLibido();
		ArrayList<clsDriveMesh> oDriveMeshes = createDriveMeshes();
		clsDriveDemand oDemand = (clsDriveDemand)clsDataStructureGenerator.generateDataStructure(eDataType.DRIVEDEMAND, 
					new clsPair<String,Object>(eDataType.DRIVEDEMAND.toString(), mrTotalLibido));
		for (clsDriveMesh oDM:oDriveMeshes) {
			moDrives.add( new clsPair<clsDriveMesh, clsDriveDemand>(oDM, oDemand));
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:22
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		 updateTempLibido();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:22
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		 updateTempLibido();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:19:22
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_10(moDrives);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:01
	 * 
	 * @see pa.interfaces.send._v30.I1_10_send#receive_I1_10(java.util.HashMap)
	 */
	@Override
	public void send_I1_10(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > poHomeostaticDriveDemands) {
		((I1_10_receive)moModuleList.get(43)).receive_I1_10(poHomeostaticDriveDemands);
		putInterfaceData(I1_10_send.class, poHomeostaticDriveDemands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:49:01
	 * 
	 * @see pa.interfaces.receive._v30.I1_9_receive#receive_I1_9(java.util.HashMap)
	 */
	@Override
	public void receive_I1_9(Double poLibidoSymbol) {
		mrIncomingLibido= poLibidoSymbol;
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
		moDescription = "The constant flow of libido/psychic energy provided by the neurosymbols originating from {E40} has to be buffered until the tension generated by the seeking system can be released. Module {E41} adds the incoming libido to a libido buffer. The total amount of buffered libido is forwarded as total amount of tension. Further, the sexual drives are split into aggressive and libidinous components.";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 20:16:46
	 * 
	 * @see pa.interfaces._v30.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oValues = new ArrayList<Double>();
		
		oValues.add(mrIncomingLibido);
		oValues.add(mrTotalLibido);
		
		return oValues;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 20:16:46
	 * 
	 * @see pa.interfaces._v30.itfTimeChartInformationContainer#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		oCaptions.add("Incoming Libido");
		oCaptions.add("Total Libido");
		return oCaptions;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:42:50
	 * 
	 * @see pa.interfaces._v30.itfInspectorGenericTimeChart#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "Libido";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:42:50
	 * 
	 * @see pa.interfaces._v30.itfInspectorGenericTimeChart#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Libido Chart";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:42:50
	 * 
	 * @see pa.interfaces._v30.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1.05;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:42:50
	 * 
	 * @see pa.interfaces._v30.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.05;
	}	
}