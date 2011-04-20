/**
 * C04_SuperEgo.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:11:20
 */
package pa._v19.modules;

import java.util.ArrayList;
import java.util.List;

import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I1_5_receive;
import pa._v19.interfaces.receive.I1_7_receive;
import pa._v19.interfaces.receive.I2_11_receive;
import pa._v19.interfaces.receive.I2_9_receive;
import pa._v19.interfaces.receive.I3_1_receive;
import pa._v19.interfaces.receive.I3_2_receive;
import pa._v19.interfaces.receive.I3_3_receive;
import pa._v19.memory.clsMemory;
import pa._v19.memorymgmt.clsKnowledgeBaseHandler;
import pa._v19.memorymgmt.datatypes.clsAct;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import config.clsBWProperties;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 15:11:20
 * 
 */
@Deprecated
public class G04_SuperEgo extends clsModuleContainer implements
						I1_5_receive,
						I1_7_receive,
						I3_1_receive,
						I3_2_receive,
						I2_9_receive,
						I2_11_receive,
						I3_3_receive
						{
	public static final String P_E07 = "E07";
	public static final String P_E22 = "E22";
	
	public E07_SuperEgo_unconscious  moE07SuperEgoUnconscious;
	public E22_SuperEgo_preconscious moE22SuperEgoPreconscious;

	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:11:51
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public G04_SuperEgo(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();

		oProp.putAll( E07_SuperEgo_unconscious.getDefaultProperties(pre+P_E07) );
		oProp.putAll( E22_SuperEgo_preconscious.getDefaultProperties(pre+P_E22) );
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE07SuperEgoUnconscious = new E07_SuperEgo_unconscious(pre+P_E07, poProp, this, moInterfaceHandler);
		moE22SuperEgoPreconscious = new E22_SuperEgo_preconscious(pre+P_E22, poProp, this, moInterfaceHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsDriveMesh> poData) {
		moE07SuperEgoUnconscious.receive_I1_5(poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moE22SuperEgoPreconscious.receive_I1_7(poDriveList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		((I3_1_receive)moEnclosingContainer).receive_I3_1(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		moE07SuperEgoUnconscious.receive_I2_9(poMergedPrimaryInformation);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		moE22SuperEgoPreconscious.receive_I2_11(poPerception);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:23:33
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(ArrayList<clsAct> poRuleList) {
		((I3_3_receive)moEnclosingContainer).receive_I3_3(poRuleList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:28:01
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		((I3_2_receive)moEnclosingContainer).receive_I3_2(pnData);
		
	}

}
