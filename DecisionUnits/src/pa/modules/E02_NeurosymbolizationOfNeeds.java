/**
 * E2_NeurosymbolizationOfNeeds.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:12:02
 */
package pa.modules;

import java.util.HashMap;
import java.util.Map;

import pa.interfaces.I1_1;
import pa.interfaces.I1_2;
import config.clsBWProperties;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsFastMessenger;
import decisionunit.itf.sensors.clsFastMessengerEntry;
import decisionunit.itf.sensors.clsHealthSystem;
import decisionunit.itf.sensors.clsSlowMessenger;
import decisionunit.itf.sensors.clsStaminaSystem;
import decisionunit.itf.sensors.clsStomachTension;
import enums.eSensorIntType;
import enums.eSlowMessenger;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:12:02
 * 
 */
public class E02_NeurosymbolizationOfNeeds extends clsModuleBase implements I1_1 {

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
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
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
		for(  Map.Entry< eSlowMessenger, Double > oSlowMessenger : oSlowMessengerSystem.moSlowMessengerValues.entrySet() ) {
			moHomeostaticSymbol.put(oSlowMessenger.getKey().name(), oSlowMessenger.getValue());
		}
		
		clsFastMessenger oFastMessengerSystem = (clsFastMessenger)moHomeostasis.get(eSensorIntType.FASTMESSENGER);
		for(  clsFastMessengerEntry oFastMessenger : oFastMessengerSystem.moEntries ) {
			moHomeostaticSymbol.put(oFastMessenger.moSource.name(), oFastMessenger.mrIntensity);
		}
	
		moHomeostaticSymbol.put(eSensorIntType.STOMACHTENSION.name(), ((clsStomachTension)moHomeostasis.get(eSensorIntType.STOMACHTENSION)).mrTension );
		moHomeostaticSymbol.put(eSensorIntType.HEALTH.name(), ((clsHealthSystem)moHomeostasis.get(eSensorIntType.HEALTH)).mrHealthValue );
		moHomeostaticSymbol.put(eSensorIntType.STAMINA.name(), ((clsStaminaSystem)moHomeostasis.get(eSensorIntType.STAMINA)).mrStaminaValue );
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
		((I1_2)moEnclosingContainer).receive_I1_2(moHomeostaticSymbol);
		
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

}
