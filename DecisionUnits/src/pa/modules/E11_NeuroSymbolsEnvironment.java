/**
 * E11_NeuroSymbolsEnvironment.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:19:23
 */
package pa.modules;

import java.util.HashMap;

import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;
import pa.clsInterfaceHandler;
import pa.enums.eSymbolExtType;
import pa.interfaces.receive.I2_1_receive;
import pa.interfaces.receive.I2_2_receive;
import pa.symbolization.clsSensorToSymbolConverter;
import pa.symbolization.representationsymbol.itfSymbol;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:19:23
 * 
 */
public class E11_NeuroSymbolsEnvironment extends clsModuleBase implements I2_1_receive {

	HashMap<eSensorExtType, clsSensorExtern> moEnvironmentalData;
	HashMap<eSymbolExtType, itfSymbol> moSymbolData;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:19:45
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E11_NeuroSymbolsEnvironment(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
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
	 * 11.08.2009, 14:20:05
	 * 
	 * @see pa.interfaces.I2_1#receive_I2_1(HashMap<eSensorExtType, clsDataBase>)
	 */
	@Override
	public void receive_I2_1(HashMap<eSensorExtType, clsSensorExtern> poData) {
		moEnvironmentalData = poData;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:28
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		moSymbolData = clsSensorToSymbolConverter.convertExtSensorToSymbol(moEnvironmentalData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:28
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I2_2_receive)moEnclosingContainer).receive_I2_2(moSymbolData);
		
	}
}
