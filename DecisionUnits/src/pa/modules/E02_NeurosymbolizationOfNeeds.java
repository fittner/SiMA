/**
 * E2_NeurosymbolizationOfNeeds.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:12:02
 */
package pa.modules;

import java.util.HashMap;
import java.util.Map;

import pa.clsInterfaceHandler;
import pa.interfaces.receive.I1_1_receive;
import pa.interfaces.receive.I1_2_receive;
import pa.interfaces.send.I1_2_send;
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
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:12:02
 * 
 */
public class E02_NeurosymbolizationOfNeeds extends clsModuleBase implements I1_1_receive, I1_2_send {

	private HashMap<eSensorIntType, clsDataBase> moHomeostasis;
	private HashMap<String, Double> moHomeostaticSymbol;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 12:12:14
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E02_NeurosymbolizationOfNeeds(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
		//nothing to do
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
	protected void process() {
		
		moHomeostaticSymbol = new HashMap<String, Double>();
		
		clsSlowMessenger oSlowMessengerSystem = (clsSlowMessenger)moHomeostasis.get(eSensorIntType.SLOWMESSENGER);
		for(  Map.Entry< eSlowMessenger, Double > oSlowMessenger : oSlowMessengerSystem.getSlowMessengerValues().entrySet() ) {
			moHomeostaticSymbol.put(oSlowMessenger.getKey().name(), oSlowMessenger.getValue());
		}
		
		clsFastMessenger oFastMessengerSystem = (clsFastMessenger)moHomeostasis.get(eSensorIntType.FASTMESSENGER);
		for(  clsFastMessengerEntry oFastMessenger : oFastMessengerSystem.getEntries() ) {
			moHomeostaticSymbol.put(oFastMessenger.getSource().name(), oFastMessenger.getIntensity());
		}
	
		moHomeostaticSymbol.put(eSensorIntType.STOMACHTENSION.name(), ((clsStomachTension)moHomeostasis.get(eSensorIntType.STOMACHTENSION)).getTension() );
		moHomeostaticSymbol.put(eSensorIntType.HEALTH.name(), ((clsHealthSystem)moHomeostasis.get(eSensorIntType.HEALTH)).getHealthValue() );
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
		((I1_2_receive)moEnclosingContainer).receive_I1_2(moHomeostaticSymbol);
	}

}
