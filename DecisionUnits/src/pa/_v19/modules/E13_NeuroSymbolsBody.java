/**
 * E13_NeuroSymbolsBody.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:24:29
 */
package pa._v19.modules;

import java.util.HashMap;

import config.clsProperties;
import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;
import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I2_3_receive;
import pa._v19.interfaces.receive.I2_4_receive;
import pa._v19.interfaces.send.I2_4_send;
import pa._v19.symbolization.clsSensorToSymbolConverter;
import pa._v19.symbolization.representationsymbol.itfSymbol;
import pa._v19.enums.eSymbolExtType;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:24:29
 * 
 */
@Deprecated
public class E13_NeuroSymbolsBody extends clsModuleBase implements I2_3_receive, I2_4_send  {

	private HashMap<eSensorExtType, clsSensorExtern> moBodyData;
	private HashMap<eSymbolExtType, itfSymbol> moSymbolData;	
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:24:59
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E13_NeuroSymbolsBody(String poPrefix, clsProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
		applyProperties(poPrefix, poProp);		
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
	 * 11.08.2009, 14:25:15
	 * 
	 * @see pa.interfaces.I2_3#receive_I2_3(int)
	 */
	@Override
	public void receive_I2_3(HashMap<eSensorExtType, clsSensorExtern> poData) {
		moBodyData = poData;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:36
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moSymbolData = clsSensorToSymbolConverter.convertExtSensorToSymbol(moBodyData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:36
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_4(moSymbolData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:55:22
	 * 
	 * @see pa.interfaces.send.I2_4_send#send_I2_4(java.util.HashMap)
	 */
	@Override
	public void send_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData) {
		((I2_4_receive)moEnclosingContainer).receive_I2_4(moSymbolData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:24
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:24
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();
	}

}
