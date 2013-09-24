/**
 * E40_NeurosymbolizationOfLibido.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:18:01
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.modules.I1_1_receive;
import pa._v38.interfaces.modules.I2_1_receive;
import pa._v38.interfaces.modules.I2_1_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.tools.toText;
import config.clsProperties;
import du.enums.eFastMessengerSources;
import du.enums.eSensorIntType;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsFastMessenger;
import du.itf.sensors.clsFastMessengerEntry;

/**
 * Conversion of raw data into neuro-symbols
 * 
 * @author muchitsch
 * 07.05.2012, 15:18:01
 * 
 */
public class F40_NeurosymbolizationOfLibido extends clsModuleBase implements I1_1_receive, I2_1_send {
	public static final String P_MODULENUMBER = "40";
		
	private double mrLibido;
	
	private HashMap<eFastMessengerSources, Double> moErogenousZoneStimuliList_OUT;
	private HashMap<eSensorIntType, clsDataBase> moErogenousZones_IN;
	
	//private final Logger log = clsLogger.getLog(this.getClass().getName());
	/**
	 * Conversion of raw data into neuro-symbols.
	 * 
	 * @author muchitsch
	 * 03.03.2011, 17:52:37
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F40_NeurosymbolizationOfLibido(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		
		mrLibido = 0;
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
		
		text += toText.valueToTEXT("mrLibido", mrLibido);	
		
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
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.BODY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.BODY;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:18:01
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		CollectErogenousZones();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:18:01
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
	 * 03.03.2011, 15:18:01
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}
	
	/**
	 * collects all the stimuli from the erogenous zones
	 *
	 * @since 2.4.2013 14:17:32
	 *
	 */
	private void CollectErogenousZones() {

		moErogenousZoneStimuliList_OUT = new HashMap<eFastMessengerSources, Double>();
		//FASTMESSENGER, the only sensor source we get to F39!
		clsFastMessenger oFastMessengerSystem = (clsFastMessenger)moErogenousZones_IN.get(eSensorIntType.FASTMESSENGER);
		if(oFastMessengerSystem!=null)
		{
			//loop through the fast messagers
			for(  clsFastMessengerEntry oFastMessenger : oFastMessengerSystem.getEntries() ) {
				
				eFastMessengerSources oFMSource = oFastMessenger.getSource();
				Double rIntensity = oFastMessenger.getIntensity();
				
				moErogenousZoneStimuliList_OUT.put(oFMSource, rIntensity);
				
			}
			
			
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:18:01
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_1(mrLibido, moErogenousZoneStimuliList_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:47:14
	 * 
	 * @see pa.interfaces.send._v38.I1_9_send#send_I1_9(java.util.HashMap)
	 */
	@Override
	public void send_I2_1(Double poLibidoSymbol, HashMap<eFastMessengerSources,Double> poErogenousZones) {
		((I2_1_receive)moModuleList.get(64)).receive_I2_1(poLibidoSymbol, poErogenousZones);
		putInterfaceData(I2_1_send.class, poLibidoSymbol);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:47:14
	 * 
	 * @see pa.interfaces.receive._v38.I1_8_receive#receive_I1_8(java.util.HashMap)
	 */
	@Override
	public void receive_I1_1(double prData, HashMap<eSensorIntType, clsDataBase> poData) {
		mrLibido = prData;
		moErogenousZones_IN = poData;
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
		moDescription = "Conversion of raw data into neuro-symbols.";
	}		
}
