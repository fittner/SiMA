/**
 * E11_NeuroSymbolsEnvironment.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:19:23
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;
import pa.enums.eSymbolExtType;
import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I2_1_receive;
import pa.interfaces.receive._v30.I2_2_receive;
import pa.interfaces.send._v30.I2_2_send;
import pa.symbolization.clsSensorToSymbolConverter;
import pa.symbolization.representationsymbol.itfSymbol;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:19:23
 * 
 */
public class E11_NeuroSymbolizationEnvironment extends clsModuleBase implements I2_1_receive, I2_2_send {
	public static final String P_MODULENUMBER = "11";
	
	private HashMap<eSensorExtType, clsSensorExtern> moEnvironmentalData;
	private HashMap<eSymbolExtType, itfSymbol> moSymbolData;

	/**
	 * DOCUMENT (muchitsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:04:58
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public E11_NeuroSymbolizationEnvironment(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
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
		String html = "";
		
		html += mapToHTML("moEnvironmentalData", moEnvironmentalData);
		html += mapToHTML("moSymbolData", moSymbolData);

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
	protected void process_basic() {
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
		send_I2_2(moSymbolData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:53:57
	 * 
	 * @see pa.interfaces.send.I2_2_send#send_I2_2(java.util.HashMap)
	 */
	@Override
	public void send_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		((I2_2_receive)moModuleList.get(14)).receive_I2_2(poEnvironmentalData);
		putInterfaceData(I2_2_send.class, poEnvironmentalData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:14
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
	 * 12.07.2010, 10:46:14
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
	 * 03.03.2011, 16:05:03
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
}
