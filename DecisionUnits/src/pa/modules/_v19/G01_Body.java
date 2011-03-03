/**
 * C1_Body.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:09:50
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.HashMap;

import pa._v19.clsInterfaceHandler;
import pa.enums.eSymbolExtType;
import pa.interfaces.itfProcessHomeostases;
import pa.interfaces.itfProcessSensorBody;
import pa.interfaces.itfProcessSensorEnvironment;
import pa.interfaces.itfReturnActionCommands;
import pa.interfaces.receive.I1_1_receive;
import pa.interfaces.receive.I1_2_receive;
import pa.interfaces.receive.I2_1_receive;
import pa.interfaces.receive.I2_2_receive;
import pa.interfaces.receive.I2_3_receive;
import pa.interfaces.receive.I2_4_receive;
import pa.interfaces.receive.I8_1_receive;
import pa.interfaces.receive.I8_2_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.symbolization.representationsymbol.itfSymbol;
import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.itf.actions.clsActionCommand;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorExtern;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:09:50
 * 
 */
public class G01_Body extends clsModuleContainer implements 
						itfProcessSensorEnvironment, 
						itfProcessHomeostases, 
						itfProcessSensorBody,
						itfReturnActionCommands,
						I1_1_receive,
						I1_2_receive,
						I2_1_receive,
						I2_2_receive,
						I2_3_receive,
						I2_4_receive,
						I8_1_receive,
						I8_2_receive
						{
	public static final String P_E01 = "E01";
	public static final String P_E02 = "E02";
	public static final String P_E10 = "E10";
	public static final String P_E11 = "E11";
	public static final String P_E12 = "E12";
	public static final String P_E13 = "E13";
	public static final String P_E31 = "E31";
	public static final String P_E32 = "E32";
	
	public E01_Homeostases 					moE01Homeostases;
	public E02_NeurosymbolizationOfNeeds 	moE02NeurosymbolizationOfNeeds;
	public E10_SensorsEnvironment 			moE10SensorsEnvironment;
	public E11_NeuroSymbolsEnvironment		moE11NeuroSymbolsEnvironment;
	public E12_SensorsBody					moE12SensorsBody;
	public E13_NeuroSymbolsBody 			moE13NeuroSymbolsBody;
	public E31_NeuroDeSymbolization 		moE31NeuroDeSymbolization;
	public E32_Actuators 					moE32Actuators;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:10:04
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public G01_Body(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E01_Homeostases.getDefaultProperties(pre+P_E01) );
		oProp.putAll( E02_NeurosymbolizationOfNeeds.getDefaultProperties(pre+P_E02) );
		oProp.putAll( E10_SensorsEnvironment.getDefaultProperties(pre+P_E10) );
		oProp.putAll( E11_NeuroSymbolsEnvironment.getDefaultProperties(pre+P_E11) );
		oProp.putAll( E12_SensorsBody.getDefaultProperties(pre+P_E12) );
		oProp.putAll( E13_NeuroSymbolsBody.getDefaultProperties(pre+P_E13) );
		oProp.putAll( E31_NeuroDeSymbolization.getDefaultProperties(pre+P_E31) );
		oProp.putAll( E32_Actuators.getDefaultProperties(pre+P_E32) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE01Homeostases = new E01_Homeostases(pre+P_E01, poProp, this, moInterfaceHandler);
		moE02NeurosymbolizationOfNeeds = new E02_NeurosymbolizationOfNeeds(pre+P_E02, poProp, this, moInterfaceHandler);
		moE10SensorsEnvironment = new E10_SensorsEnvironment(pre+P_E10, poProp, this, moInterfaceHandler);
		moE11NeuroSymbolsEnvironment = new E11_NeuroSymbolsEnvironment(pre+P_E11, poProp, this, moInterfaceHandler);
		moE12SensorsBody = new E12_SensorsBody(pre+P_E12, poProp, this, moInterfaceHandler);
		moE13NeuroSymbolsBody = new E13_NeuroSymbolsBody(pre+P_E13, poProp, this, moInterfaceHandler);
		moE31NeuroDeSymbolization = new E31_NeuroDeSymbolization(pre+P_E31, poProp, this, moInterfaceHandler);
		moE32Actuators = new E32_Actuators(pre+P_E32, poProp, this, moInterfaceHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 20:58:11
	 * 
	 * @see pa.interfaces.itfProcessSensorEnvironment#receiveEnvironment(java.util.HashMap)
	 */
	@Override
	public void receiveEnvironment(HashMap<eSensorExtType, clsSensorExtern> poData) {
		moE10SensorsEnvironment.receiveEnvironment(poData);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:53:01
	 * 
	 * @see pa.interfaces.itfProcessHomeostases#processHomeostases(decisionunit.itf.sensors.clsSensorData)
	 */
	@Override
	public void receiveHomeostases(HashMap<eSensorIntType, clsDataBase> poData) {
		moE01Homeostases.receiveHomeostases(poData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 20:58:11
	 * 
	 * @see pa.interfaces.itfProcessSensorBody#receiveBody(java.util.HashMap)
	 */
	@Override
	public void receiveBody(HashMap<eSensorExtType, clsSensorExtern> poData) {
		moE12SensorsBody.receiveBody(poData);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:54:35
	 * 
	 * @see pa.interfaces.I1_1#receive_I1_1(int)
	 */
	@Override
	public void receive_I1_1(HashMap<eSensorIntType, clsDataBase> pnData) {
		moE02NeurosymbolizationOfNeeds.receive_I1_1(pnData);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:54:35
	 * 
	 * @see pa.interfaces.I2_1#receive_I2_1(int)
	 */
	@Override
	public void receive_I2_1(HashMap<eSensorExtType, clsSensorExtern> pnData) {
		moE11NeuroSymbolsEnvironment.receive_I2_1(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:54:35
	 * 
	 * @see pa.interfaces.I2_3#receive_I2_3(int)
	 */
	@Override
	public void receive_I2_3(HashMap<eSensorExtType, clsSensorExtern> pnData) {
		moE13NeuroSymbolsBody.receive_I2_3(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:54:35
	 * 
	 * @see pa.interfaces.I8_1#receive_I8_1(int)
	 */
	@Override
	public void receive_I8_1(ArrayList<clsWordPresentation> poActionCommands) {
		moE31NeuroDeSymbolization.receive_I8_1(poActionCommands);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:54:35
	 * 
	 * @see pa.interfaces.I8_2#receive_I8_2(int)
	 */
	@Override
	public void receive_I8_2(ArrayList<clsActionCommand> poActionCommandList) {
		moE32Actuators.receive_I8_2(poActionCommandList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:18:21
	 * 
	 * @see pa.interfaces.I1_2#receive_I1_2(int)
	 */
	@Override
	public void receive_I1_2(HashMap<String, Double> poHomeostasisSymbols) {
		((I1_2_receive)moEnclosingContainer).receive_I1_2(poHomeostasisSymbols);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:08:53
	 * 
	 * @see pa.interfaces.itfReturnActionCommands#getActionCommands()
	 */
	@Override
	public void getActionCommands(itfActionProcessor poActionContainer) {
		moE32Actuators.getActionCommands(poActionContainer);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:13:39
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@Override
	public void receive_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		((I2_2_receive)moEnclosingContainer).receive_I2_2(poEnvironmentalData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:29:26
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData) {
		((I2_4_receive)moEnclosingContainer).receive_I2_4(poBodyData);
		
	}
}
