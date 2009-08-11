/**
 * C13_PrimaryDecision.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:40:41
 */
package pa.modules;

import pa.interfaces.I1_5;
import pa.interfaces.I1_6;
import pa.interfaces.I2_10;
import pa.interfaces.I2_9;
import pa.interfaces.I3_1;
import pa.interfaces.I4_1;
import pa.interfaces.I4_2;
import pa.interfaces.I4_3;
import pa.interfaces.I5_1;
import pa.interfaces.I5_2;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:40:41
 * 
 */
public class C12_PrimaryDecision extends clsModuleContainer implements 
					I1_5,
					I1_6,
					I2_9,
					I2_10,
					I3_1,
					I4_1,
					I4_2,
					I4_3,
					I5_1,
					I5_2
					{

	public static final String P_E06 = "E06";
	public static final String P_E19 = "E19";
	
	public E06_DefenseMechanismsForDriveContents moE06DefenseMechanismsForDriveContents;
	public E19_DefenseMechanismsForPerception moE19DefenseMechanismsForPerception;

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
	public C12_PrimaryDecision(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E06_DefenseMechanismsForDriveContents.getDefaultProperties(pre+P_E06) );
		oProp.putAll( E19_DefenseMechanismsForPerception.getDefaultProperties(pre+P_E19) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE06DefenseMechanismsForDriveContents = new E06_DefenseMechanismsForDriveContents(pre+P_E06, poProp, this);
		moE19DefenseMechanismsForPerception = new E19_DefenseMechanismsForPerception(pre+P_E19, poProp, this);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(int pnData) {
		// TODO (langr) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(int pnData) {
		// TODO (langr) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(int pnData) {
		// TODO (langr) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I2_10(int pnData) {
		// TODO (langr) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		// TODO (langr) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(int pnData) {
		// TODO (langr) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(int pnData) {
		// TODO (langr) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(int pnData) {
		// TODO (langr) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(int pnData) {
		// TODO (langr) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(int pnData) {
		// TODO (langr) - Auto-generated method stub
		
	}

}
