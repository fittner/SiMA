/**
 * E39_SeekingSystem_LibidoSource.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:16:06
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInspectorGenericTimeChart;
import pa._v30.interfaces.receive.I0_1_receive;
import pa._v30.interfaces.receive.I0_2_receive;
import pa._v30.interfaces.receive.I1_8_receive;
import pa._v30.interfaces.send.I1_8_send;
import pa._v30.storage.clsLibidoBuffer;
import config.clsBWProperties;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:16:06
 * 
 */
public class E39_SeekingSystem_LibidoSource extends clsModuleBase 
			implements I0_1_receive, I0_2_receive, I1_8_send, itfInspectorGenericTimeChart {
	public static final String P_MODULENUMBER = "39";
	
	private clsLibidoBuffer moLibidoBuffer;

	private double mrIncomingLibido_I0_1;
	private double mrIncomingLibido_I0_2;
	private double mrOutgoingLibido;
	
	/**
	 * DOCUMENT (wendt) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 15:42:22
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public E39_SeekingSystem_LibidoSource(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsLibidoBuffer poLibidoBuffer) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		moLibidoBuffer = poLibidoBuffer;
		
		applyProperties(poPrefix, poProp);	
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
		
		html += valueToHTML("moLibidoBuffer", moLibidoBuffer);
		html += valueToHTML("mrIncomingLibido_I0_1", mrIncomingLibido_I0_1);		
		html += valueToHTML("mrIncomingLibido_I0_2", mrIncomingLibido_I0_2);		
		html += valueToHTML("mrOutgoingLibido", mrOutgoingLibido);		
		
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
	
	private void updateTempLibido() {
		mrOutgoingLibido = mrIncomingLibido_I0_1 + mrIncomingLibido_I0_2;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:16:06
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		 updateTempLibido();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:16:06
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
	 * 03.03.2011, 15:16:06
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
	 * 03.03.2011, 15:16:06
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_8(mrOutgoingLibido);
	}

	@Override
	protected void setProcessType() {mnProcessType = eProcessType.BODY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.BODY;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}
	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:38:54
	 * 
	 * @see pa.interfaces.send._v30.I1_8_send#send_I1_8(java.util.HashMap)
	 */
	@Override
	public void send_I1_8(double prData) {
		((I1_8_receive)moModuleList.get(40)).receive_I1_8(prData);
		putInterfaceData(I1_8_send.class, prData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:38:54
	 * 
	 * @see pa.interfaces.receive._v30.I0_2_receive#receive_I0_2(java.util.List)
	 */
	@Override
	public void receive_I0_2(Double prLibido) {
		mrIncomingLibido_I0_2 = prLibido;
		
		putInterfaceData(I0_2_receive.class, prLibido);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:38:54
	 * 
	 * @see pa.interfaces.receive._v30.I0_1_receive#receive_I0_1(java.util.List)
	 */
	@Override
	public void receive_I0_1(Double prLibido) {
		mrIncomingLibido_I0_1 = prLibido;
		
		putInterfaceData(I0_1_receive.class, prLibido);
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
		moDescription = "The seeking system is the basic motivational system. {E39} is collecting information on libido produced by various inner somatic sources as well as by erogenous zones.";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 20:08:09
	 * 
	 * @see pa.interfaces._v30.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oValues = new ArrayList<Double>();
		
		oValues.add(mrIncomingLibido_I0_1);
		oValues.add(mrIncomingLibido_I0_2);
		oValues.add(mrOutgoingLibido);
		
		return oValues;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 20:08:09
	 * 
	 * @see pa.interfaces._v30.itfTimeChartInformationContainer#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		
		oCaptions.add(eInterfaces.I0_1.toString());
		oCaptions.add(eInterfaces.I0_2.toString());
		oCaptions.add(eInterfaces.I1_8.toString());
		
		return oCaptions;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:39:30
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
	 * 19.04.2011, 10:39:30
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
	 * 19.04.2011, 10:39:30
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
	 * 19.04.2011, 10:39:30
	 * 
	 * @see pa.interfaces._v30.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.05;
	}	
}

