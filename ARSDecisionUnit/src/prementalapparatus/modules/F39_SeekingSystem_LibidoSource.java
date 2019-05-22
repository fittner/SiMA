/**
 * E39_SeekingSystem_LibidoSource.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:16:06
 */
package prementalapparatus.modules;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericTimeChart;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;
import properties.clsProperties;
import properties.personality_parameter.clsPersonalityParameterContainer;

import modules.interfaces.I0_1_receive;
import modules.interfaces.I0_2_receive;
import modules.interfaces.I1_1_receive;
import modules.interfaces.I1_1_send;
import modules.interfaces.eInterfaces;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;


/**
 * The seeking system is the basic motivational system. {E39} is collecting information on libido 
 * produced by various inner somatic sources as well as by erogenous zones. 
 * 
 * @author muchitsch
 * 07.05.2012, 15:16:06
 * 
 */
public class F39_SeekingSystem_LibidoSource extends clsModuleBase 
			implements I0_1_receive, I0_2_receive, I1_1_send, itfInspectorGenericTimeChart {
	public static final String P_MODULENUMBER = "39";
	   public static final String P_LIBIDO_IMPACT_FACTOR = "LIBIDO_IMPACT_FACTOR";
	
	private double mrIncomingLibido_I0_1;
	//private double mrIncomingLibido_I0_2;
	private double mrOutgoingLibido;
	private double libidoImpactFactor;
	

    private clsDataContainer moSensorSystems_IN;
    
    private clsDataContainer moSensorSystems_OUT;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	
	
	/**
	 * basic constructor
	 * 
	 * @author muchitsch
	 * 03.03.2011, 15:42:22
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public F39_SeekingSystem_LibidoSource(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsPersonalityParameterContainer poPersonalityParameterContainer, int pnUid) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
		libidoImpactFactor = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_LIBIDO_IMPACT_FACTOR).getParameterDouble();
		
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
		
		text += toText.valueToTEXT("mrIncomingLibido_I0_1", mrIncomingLibido_I0_1);		
		text += toText.valueToTEXT("mrOutgoingLibido", mrOutgoingLibido);		
		
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
	
//	private void updateTempLibido() {
//		mrOutgoingLibido = mrIncomingLibido_I0_1 + mrIncomingLibido_I0_2;
//	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:16:06
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		
		//calculate libido
	//	 updateTempLibido();
		 
		//clear the old list
	//	moErogenousZoneStimuliList.clear();
		
		//collect all zones together
	//	CollectErogenousZoneStimuliAndReduceLibido();

        for (clsDataPoint item : moSensorSystems_IN.getData()) {
            InfluxDB.sendInflux("F"+P_MODULENUMBER,item.getType(),item.getValue());
        }
        
		mrOutgoingLibido = mrIncomingLibido_I0_1*libidoImpactFactor;
		moSensorSystems_OUT =moSensorSystems_IN;

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:16:06
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
	
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:16:06
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:16:06
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_1(mrOutgoingLibido, moSensorSystems_OUT);
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
	 * @see pa.interfaces.send._v38.I1_8_send#send_I1_8(java.util.HashMap)
	 */
	@Override
	public void send_I1_1(double prData, clsDataContainer poData) {
		((I1_1_receive)moModuleList.get(40)).receive_I1_1(prData,poData);
		putInterfaceData(I1_1_send.class, prData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:38:54
	 * 
	 * @see pa.interfaces.receive._v38.I0_2_receive#receive_I0_2(java.util.List)
	 */
	@Override
	public void receive_I0_2(clsDataContainer poData) {
		moSensorSystems_IN = poData; 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:38:54
	 * 
	 * @see pa.interfaces.receive._v38.I0_1_receive#receive_I0_1(java.util.List)
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
	 * @see pa.modules._v38.clsModuleBase#setDescription()
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
	 * @see pa.interfaces._v38.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oValues = new ArrayList<Double>();
		
		oValues.add(mrIncomingLibido_I0_1);
		oValues.add(mrOutgoingLibido);
		
		return oValues;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 20:08:09
	 * 
	 * @see pa.interfaces._v38.itfTimeChartInformationContainer#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		
		oCaptions.add(eInterfaces.I0_1.toString());
		oCaptions.add(eInterfaces.I1_1.toString());
		
		return oCaptions;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:39:30
	 * 
	 * @see pa.interfaces._v38.itfInspectorGenericTimeChart#getTimeChartAxis()
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
	 * @see pa.interfaces._v38.itfInspectorGenericTimeChart#getTimeChartTitle()
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
	 * @see pa.interfaces._v38.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
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
	 * @see pa.interfaces._v38.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.05;
	}	
    /* (non-Javadoc)
    *
    * @since 14.05.2014 10:33:20
    * 
    * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
    */
   @Override
   public clsTimeChartPropeties getProperties() {
       return new clsTimeChartPropeties(true);
   }
}

