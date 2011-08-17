/**
 * C07_EnvironmentalInterfaceFunctions.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:32:50
 */
package pa._v19.modules;

import java.util.ArrayList;
import java.util.HashMap;

import config.clsProperties;
import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I2_2_receive;
import pa._v19.interfaces.receive.I2_4_receive;
import pa._v19.interfaces.receive.I2_5_receive;
import pa._v19.interfaces.receive.I7_4_receive;
import pa._v19.interfaces.receive.I8_1_receive;
import pa._v19.memory.clsMemory;
import pa._v19.symbolization.representationsymbol.itfSymbol;
import pa._v19.memorymgmt.clsKnowledgeBaseHandler;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsWordPresentation;
import pa._v19.enums.eSymbolExtType;

/**
 *
 * 
 * @author langr
 * 11.08.2009, 15:32:50
 * 
 */
@Deprecated
public class G07_EnvironmentalInterfaceFunctions extends clsModuleContainer implements
						I2_2_receive,
						I2_4_receive,
						I2_5_receive,
						I7_4_receive,
						I8_1_receive
						{

	public static final String P_E14 = "E14";
	public static final String P_E30 = "E30";
	
	public E14_PreliminaryExternalPerception moE14PreliminaryExternalPerception;
	public E30_MotilityControl               moE30MotilityControl;

	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:36:51
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public G07_EnvironmentalInterfaceFunctions(String poPrefix, clsProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( E14_PreliminaryExternalPerception.getDefaultProperties(pre+P_E14) );
		oProp.putAll( E30_MotilityControl.getDefaultProperties(pre+P_E30) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	
		moE14PreliminaryExternalPerception = new E14_PreliminaryExternalPerception(pre+P_E14, poProp, this, moInterfaceHandler);
		moE30MotilityControl = new E30_MotilityControl(pre+P_E30, poProp, this, moInterfaceHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:01:47
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@Override
	public void receive_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		moE14PreliminaryExternalPerception.receive_I2_2(poEnvironmentalData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:03:26
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData) {
		moE14PreliminaryExternalPerception.receive_I2_4(poBodyData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:26:23
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(ArrayList<clsWordPresentation> poActionCommands) {
		moE30MotilityControl.receive_I7_4(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:31:08
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		((I2_5_receive)moEnclosingContainer).receive_I2_5(poEnvironmentalTP);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:48:21
	 * 
	 * @see pa.interfaces.I8_1#receive_I8_1(int)
	 */
	@Override
	public void receive_I8_1(ArrayList<clsWordPresentation> poActionCommands) {
		((I8_1_receive)moEnclosingContainer).receive_I8_1(poActionCommands);
		
	}
}
