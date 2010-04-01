/**
 * clsSecondaryKnowledgeUtilizer.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:43:00
 */
package pa.modules;

import java.util.ArrayList;

import pa.datatypes.clsSecondaryInformation;
import pa.interfaces.I2_12;
import pa.interfaces.I6_1;
import pa.interfaces.I6_2;
import pa.interfaces.I7_2;
import pa.memory.clsMemory;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:43:00
 * 
 */
public class C16_SecondaryKnowledgeUtilizer extends clsModuleContainer implements
					I2_12,
					I6_1,
					I6_2,
					I7_2
					{

	public static final String P_E25 = "E25";
	public static final String P_E28 = "E28";
	
	public E25_KnowledgeAboutReality moE25KnowledgeAboutReality;
	public E28_KnowledgeBase_StoredScenarios moE28KnowledgeBase_StoredScenarios;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:36:51
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public C16_SecondaryKnowledgeUtilizer(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E25_KnowledgeAboutReality.getDefaultProperties(pre+P_E25) );
		oProp.putAll( E28_KnowledgeBase_StoredScenarios.getDefaultProperties(pre+P_E28) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE25KnowledgeAboutReality = new E25_KnowledgeAboutReality(pre+P_E25, poProp, this);
		moE28KnowledgeBase_StoredScenarios = new E28_KnowledgeBase_StoredScenarios(pre+P_E28, poProp, this);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:19:57
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@Override
	public void receive_I2_12(ArrayList<clsSecondaryInformation> poFocusedPerception) {
		moE25KnowledgeAboutReality.receive_I2_12(poFocusedPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:19:57
	 * 
	 * @see pa.interfaces.I6_1#receive_I6_1(int)
	 */
	@Override
	public void receive_I6_1(int pnData) {
		((I6_1)moEnclosingContainer).receive_I6_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:19:57
	 * 
	 * @see pa.interfaces.I6_2#receive_I6_2(int)
	 */
	@Override
	public void receive_I6_2(int pnData) {
		((I6_2)moEnclosingContainer).receive_I6_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:19:57
	 * 
	 * @see pa.interfaces.I7_2#receive_I7_2(int)
	 */
	@Override
	public void receive_I7_2(int pnData) {
		moE28KnowledgeBase_StoredScenarios.receive_I7_2(pnData);
	}

}
