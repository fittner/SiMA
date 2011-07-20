/**
 * E13_NeuroSymbolsBody.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:24:29
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;
import pa._v38.interfaces.modules.I1_4_receive;
import pa._v38.interfaces.modules.I2_4_receive;
import pa._v38.interfaces.modules.I2_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.symbolization.clsSensorToSymbolConverter;
import pa._v38.symbolization.eSymbolExtType;
import pa._v38.symbolization.representationsymbol.itfSymbol;
import pa._v38.tools.toText;

/**
 * Conversion of raw data into neuro-symbols.
 * 
 * @author muchitsch
 * 11.08.2009, 14:24:29
 * 
 */
public class F13_NeuroSymbolizationBody extends clsModuleBase implements I1_4_receive, I2_4_send  {
	public static final String P_MODULENUMBER = "13";
	
	private HashMap<eSensorExtType, clsSensorExtern> moBodyData;
	private HashMap<eSymbolExtType, itfSymbol> moSymbolData;	
	/**
	 * basic CTOR
	 * 
	 * @author muchitsch
	 * 03.03.2011, 16:14:24
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F13_NeuroSymbolizationBody(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
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
		String text = "";
		
		text += toText.mapToTEXT("moBodyData", moBodyData);
		text += toText.mapToTEXT("moSymbolData", moSymbolData);

		return text;
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
	 * 11.08.2009, 14:25:15
	 * 
	 * @see pa.interfaces.I2_3#receive_I2_3(int)
	 */
	@Override
	public void receive_I1_4(HashMap<eSensorExtType, clsSensorExtern> poData) {
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
		((I2_4_receive)moModuleList.get(14)).receive_I2_4(poBodyData);
		putInterfaceData(I2_4_send.class, poBodyData);
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
		// TODO (muchitsch) - Auto-generated method stub
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
		// TODO (muchitsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:14:21
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
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
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Conversion of raw data into neuro-symbols.";
	}	
}
