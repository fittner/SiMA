/**
 * E2_NeurosymbolizationOfNeeds.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:12:02
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I1_1_receive;
import pa.interfaces.receive._v30.I1_2_receive;
import pa.interfaces.send._v30.I1_2_send;
import config.clsBWProperties;
import du.enums.eSensorIntType;
import du.enums.eSlowMessenger;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsFastMessenger;
import du.itf.sensors.clsFastMessengerEntry;
import du.itf.sensors.clsHealthSystem;
import du.itf.sensors.clsSlowMessenger;
import du.itf.sensors.clsStaminaSystem;
import du.itf.sensors.clsStomachTension;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:12:02
 * 
 */
public class E02_NeurosymbolizationOfNeeds extends clsModuleBase implements I1_1_receive, I1_2_send {
	public static final String P_MODULENUMBER = "02";
	
	private HashMap<eSensorIntType, clsDataBase> moHomeostasis;
	private HashMap<String, Double> moHomeostaticSymbol;
	/**
	 * DOCUMENT (muchitsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 15:55:32
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public E02_NeurosymbolizationOfNeeds(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
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
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToHTML()
	 */
	@Override
	public String stateToHTML() {
		String html ="";
		
		html += mapToHTML("moHomeostasis", moHomeostasis);
		html += mapToHTML("moHomeostaticSymbol", moHomeostaticSymbol);
		
		return html;
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
		mnProcessType = eProcessType.BODY;
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
		mnPsychicInstances = ePsychicInstances.BODY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 13:47:00
	 * 
	 * @see pa.interfaces.I1_1#receive_I1_1(int)
	 */
	@Override
	public void receive_I1_1(HashMap<eSensorIntType, clsDataBase> pnData) {
		moHomeostasis = pnData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:12:58
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		moHomeostaticSymbol = new HashMap<String, Double>();
		
		clsSlowMessenger oSlowMessengerSystem = (clsSlowMessenger)moHomeostasis.get(eSensorIntType.SLOWMESSENGER);
		if(oSlowMessengerSystem!=null)
		{
			for(  Map.Entry< eSlowMessenger, Double > oSlowMessenger : oSlowMessengerSystem.getSlowMessengerValues().entrySet() ) {
				moHomeostaticSymbol.put(oSlowMessenger.getKey().name(), oSlowMessenger.getValue());
			}
		}
		
		
		clsFastMessenger oFastMessengerSystem = (clsFastMessenger)moHomeostasis.get(eSensorIntType.FASTMESSENGER);
		if(oFastMessengerSystem!=null)
		{
			for(  clsFastMessengerEntry oFastMessenger : oFastMessengerSystem.getEntries() ) {
				moHomeostaticSymbol.put(oFastMessenger.getSource().name(), oFastMessenger.getIntensity());
			}
		}
	
		if(moHomeostasis.get(eSensorIntType.STOMACHTENSION)!=null)
			moHomeostaticSymbol.put(eSensorIntType.STOMACHTENSION.name(), ((clsStomachTension)moHomeostasis.get(eSensorIntType.STOMACHTENSION)).getTension() );
		
		if(moHomeostasis.get(eSensorIntType.HEALTH)!=null)
			moHomeostaticSymbol.put(eSensorIntType.HEALTH.name(), ((clsHealthSystem)moHomeostasis.get(eSensorIntType.HEALTH)).getHealthValue() );
		
		if(moHomeostasis.get(eSensorIntType.STAMINA)!=null)
			moHomeostaticSymbol.put(eSensorIntType.STAMINA.name(), ((clsStaminaSystem)moHomeostasis.get(eSensorIntType.STAMINA)).getStaminaValue() );
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:12:58
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I1_2(moHomeostaticSymbol);		
	}

	/**
	 * @author langr
	 * 13.08.2009, 02:14:56
	 * 
	 * @return the moHomeostasis
	 */
	public HashMap<eSensorIntType, clsDataBase> getHomeostasisData() {
		return moHomeostasis;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:43:46
	 * 
	 * @see pa.interfaces.send.I1_2_send#send_I1_2(java.util.HashMap)
	 */
	@Override
	public void send_I1_2(HashMap<String, Double> poHomeostasisSymbols) {
		((I1_2_receive)moModuleList.get(3)).receive_I1_2(poHomeostasisSymbols);
		
		putInterfaceData(I1_2_send.class, poHomeostasisSymbols);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:41:52
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (muchitsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:41:52
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (muchitsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:55:37
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
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
		moDescription = "Conversion of raw data into neuro-symbols.";
	}	
}
