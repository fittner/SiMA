/**
 * E39_SeekingSystem_LibidoSource.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:16:06
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.interfaces.modules.I0_1_receive;
import pa._v38.interfaces.modules.I0_2_receive;
import pa._v38.interfaces.modules.I1_1_receive;
import pa._v38.interfaces.modules.I1_1_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.storage.DT1_LibidoBuffer;
import pa._v38.tools.toText;
import config.clsProperties;
import du.enums.eFastMessengerSources;
import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsFastMessenger;
import du.itf.sensors.clsFastMessengerEntry;

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
	
	private DT1_LibidoBuffer moLibidoBuffer;

	private double mrIncomingLibido_I0_1;
	private double mrIncomingLibido_I0_2;
	private double mrOutgoingLibido;
	
	private HashMap<String, Double> moErogenousZoneStimuliList;
	private HashMap<eSensorIntType, clsDataBase> moSensorSystems_IN;
	
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
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, DT1_LibidoBuffer poLibidoBuffer) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		moLibidoBuffer = poLibidoBuffer;
		moErogenousZoneStimuliList = new HashMap<String, Double>();
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
		
		text += toText.valueToTEXT("moLibidoBuffer", moLibidoBuffer);
		text += toText.valueToTEXT("mrIncomingLibido_I0_1", mrIncomingLibido_I0_1);		
		text += toText.valueToTEXT("mrIncomingLibido_I0_2", mrIncomingLibido_I0_2);		
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
	
	private void updateTempLibido() {
		mrOutgoingLibido = mrIncomingLibido_I0_1 + mrIncomingLibido_I0_2;
	}
	
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
		 updateTempLibido();
		 
		//clear the old list
		moErogenousZoneStimuliList.clear();
		
		//collect all zones together
		CollectErogenousZoneStimuliAndReduceLibido();


	}

	/**
	 * collects all the stimuli from the erogenous zones and combines them into one list for later reactin of libido
	 *
	 * @since 26.11.2012 14:17:32
	 *
	 */
	private void CollectErogenousZoneStimuliAndReduceLibido() {

		//FASTMESSENGER, the only sensor source we get to F39!
		clsFastMessenger oFastMessengerSystem = (clsFastMessenger)moSensorSystems_IN.get(eSensorIntType.FASTMESSENGER);
		if(oFastMessengerSystem!=null)
		{
			//loop through the fast messagers
			for(  clsFastMessengerEntry oFastMessenger : oFastMessengerSystem.getEntries() ) {
				
				eFastMessengerSources oFMSource = oFastMessenger.getSource();
				Double rIntensity = oFastMessenger.getIntensity();
				
				//wenn quelle X, dann Einfluß auf libido im Umfang von...
				if (oFMSource ==  eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA) {

					//TODO: calculate influence zones-> libido
					mrOutgoingLibido = mrOutgoingLibido - rIntensity;
					
					//Double stomachValue = moHomeostaticSymbol_OUT.get(eSensorIntType.STOMACH.name());
					//moHomeostaticSymbol_OUT.put(eSensorIntType.STOMACH.name(), stomachValue-rValue);

				}
				else if(oFMSource ==  eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA){
					//TODO: calculate influence zones-> libido
					mrOutgoingLibido = mrOutgoingLibido - rIntensity;
				}
				else if(oFMSource ==  eFastMessengerSources.ORIFICE_RECTAL_MUCOSA){
					//TODO: calculate influence zones-> libido
					mrOutgoingLibido = mrOutgoingLibido - rIntensity;
				}
				else if(oFMSource ==  eFastMessengerSources.ORIFICE_GENITAL_MUCOSA){
					//TODO: calculate influence zones-> libido
					mrOutgoingLibido = mrOutgoingLibido - rIntensity;
				}
				else if(oFMSource ==  eFastMessengerSources.ORIFICE_PHALLIC_MUCOSA){
					//phallic aka Schautrieb geht eigentlich über F45 auf die Libido, dazu gibt es keine erogene Zone (bisher! CM 27.11.2012)
				}
			}//end for
			
			//cannot be below zero
			if(mrOutgoingLibido < 0)
				mrOutgoingLibido=0;
			
		}//end null check
		
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
		 updateTempLibido();		

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
		 updateTempLibido();		
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
		send_I1_1(mrOutgoingLibido);
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
	public void send_I1_1(double prData) {
		((I1_1_receive)moModuleList.get(40)).receive_I1_1(prData);
		putInterfaceData(I1_1_send.class, prData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:38:54
	 * 
	 * @see pa.interfaces.receive._v38.I0_2_receive#receive_I0_2(java.util.List)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I0_2(HashMap<eSensorIntType, clsDataBase> poData) {
		moSensorSystems_IN = (HashMap<eSensorIntType, clsDataBase>) deepCopy(poData); 
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
		oValues.add(mrIncomingLibido_I0_2);
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
		oCaptions.add(eInterfaces.I0_2.toString());
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
}

