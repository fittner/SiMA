/**
 * C14_PrimaryKnowledgeUtilizer.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:41:11
 */
package pa.modules;

import java.util.List;

import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I1_5;
import pa.interfaces.I2_6;
import pa.interfaces.I2_7;
import pa.interfaces.I6_3;
import pa.memory.clsMemory;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:41:11
 * 
 */
public class C13_PrimaryKnowledgeUtilizer extends clsModuleContainer implements
					I1_5,
					I2_6,
					I2_7,
					I6_3
					{

	public static final String P_E09 = "E09";
	public static final String P_E16 = "E16";
	
	public E09_KnowledgeAboutReality_unconscious moE09KnowledgeAboutReality_unconscious;
	public E16_ManagmentOfMemoryTraces moE16ManagmentOfMemoryTraces;

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
	public C13_PrimaryKnowledgeUtilizer(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E09_KnowledgeAboutReality_unconscious.getDefaultProperties(pre+P_E09) );
		oProp.putAll( E16_ManagmentOfMemoryTraces.getDefaultProperties(pre+P_E16) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE09KnowledgeAboutReality_unconscious = new E09_KnowledgeAboutReality_unconscious(pre+P_E09, poProp, this);
		moE16ManagmentOfMemoryTraces = new E16_ManagmentOfMemoryTraces(pre+P_E16, poProp, this);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:26:16
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(int pnData) {
		moE16ManagmentOfMemoryTraces.receive_I2_6(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:07:34
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
		moE09KnowledgeAboutReality_unconscious.receive_I1_5(poData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:07:34
	 * 
	 * @see pa.interfaces.I2_7#receive_I2_7(int)
	 */
	@Override
	public void receive_I2_7(int pnData) {
		((I2_7)moEnclosingContainer).receive_I2_7(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:07:34
	 * 
	 * @see pa.interfaces.I6_3#receive_I6_3(int)
	 */
	@Override
	public void receive_I6_3(int pnData) {
		((I6_3)moEnclosingContainer).receive_I6_3(pnData);
	}

}
