/**
 * C06_AffectGeneration.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:28:15
 */
package pa.modules;

import pa.interfaces.I1_4;
import pa.interfaces.I1_5;
import pa.interfaces.I2_8;
import pa.interfaces.I2_9;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:28:15
 * 
 */
public class C06_AffectGeneration extends clsModuleContainer implements
                           I1_4,
                           I1_5,
                           I2_8,
                           I2_9
                           {
	public static final String P_E05 = "E05";
	public static final String P_E18 = "E18";
	
	public E05_GenerationOfAffectsForDrives moE05GenerationOfAffectsForDrives;
	public E18_GenerationOfAffectsForPerception moE18GenerationOfAffectsForPerception;

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
	public C06_AffectGeneration(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E05_GenerationOfAffectsForDrives.getDefaultProperties(pre+P_E05) );
		oProp.putAll( E18_GenerationOfAffectsForPerception.getDefaultProperties(pre+P_E18) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE05GenerationOfAffectsForDrives = new E05_GenerationOfAffectsForDrives(pre+P_E05, poProp, this);
		moE18GenerationOfAffectsForPerception = new E18_GenerationOfAffectsForPerception(pre+P_E18, poProp, this);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:39:25
	 * 
	 * @see pa.interfaces.I1_4#receive_I1_4(int)
	 */
	@Override
	public void receive_I1_4(int pnData) {
		moE05GenerationOfAffectsForDrives.receive_I1_4(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:49:41
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(int pnData) {
		((I1_5)moEnclosingContainer).receive_I1_5(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:51:45
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(int pnData) {
		moE18GenerationOfAffectsForPerception.receive_I2_8(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:52:58
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(int pnData) {
		((I2_9)moEnclosingContainer).receive_I2_9(pnData);
		
	}
}
