/**
 * clsSecondaryKnowledgeUtilizer.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:43:00
 */
package pa._v19.modules;

import java.util.ArrayList;

import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I2_12_receive;
import pa._v19.interfaces.receive.I6_1_receive;
import pa._v19.interfaces.receive.I6_2_receive;
import pa._v19.interfaces.receive.I7_2_receive;
import pa._v19.interfaces.receive.I7_3_receive;
import pa._v19.interfaces.receive.I7_5_receive;
import pa._v19.memory.clsMemory;
import pa._v19.memorymgmt.clsKnowledgeBaseHandler;
import pa._v19.memorymgmt.datatypes.clsAct;
import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsWordPresentation;
import config.clsProperties;

/**
 *
 * 
 * @author langr
 * 11.08.2009, 15:43:00
 * 
 */
@Deprecated
public class G16_SecondaryKnowledgeUtilizer extends clsModuleContainer implements
					I2_12_receive,
					I6_1_receive,
					I6_2_receive,
					I7_2_receive,
					I7_3_receive,
					I7_5_receive
					{

	public static final String P_E25 = "E25";
	public static final String P_E28 = "E28";
	public static final String P_E34 = "E34";
	
	public E25_KnowledgeAboutReality         moE25KnowledgeAboutReality;
	public E28_KnowledgeBase_StoredScenarios moE28KnowledgeBase_StoredScenarios;
	public E34_KnowledgeAboutReality2        moE34KnowledgeAboutReality2;

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
	public G16_SecondaryKnowledgeUtilizer(String poPrefix, clsProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( E25_KnowledgeAboutReality.getDefaultProperties(pre+P_E25) );
		oProp.putAll( E28_KnowledgeBase_StoredScenarios.getDefaultProperties(pre+P_E28) );
		oProp.putAll( E34_KnowledgeAboutReality2.getDefaultProperties(pre+P_E34));
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	
		moE25KnowledgeAboutReality = new E25_KnowledgeAboutReality(pre+P_E25, poProp, this, moInterfaceHandler);
		moE28KnowledgeBase_StoredScenarios = new E28_KnowledgeBase_StoredScenarios(pre+P_E28, poProp, this, moInterfaceHandler);
		moE34KnowledgeAboutReality2 = new E34_KnowledgeAboutReality2(pre+P_E34, poProp, this, moInterfaceHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:19:57
	 * 
	 * @see pa.interfaces.I2_12#receive_I2_12(int)
	 */
	@Override
	public void receive_I2_12(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception,
			   ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moE25KnowledgeAboutReality.receive_I2_12(poFocusedPerception, poDriveList);
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
		((I6_1_receive)moEnclosingContainer).receive_I6_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:19:57
	 * 
	 * @see pa.interfaces.I6_2#receive_I6_2(int)
	 */
	@Override
	public void receive_I6_2(ArrayList<ArrayList<clsAct>> poPlanOutput) {
		((I6_2_receive)moEnclosingContainer).receive_I6_2(poPlanOutput);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:19:57
	 * 
	 * @see pa.interfaces.I7_2#receive_I7_2(int)
	 */
	@Override
	public void receive_I7_2(ArrayList<clsSecondaryDataStructureContainer> poGoal_Output) {
		moE28KnowledgeBase_StoredScenarios.receive_I7_2(poGoal_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:51:37
	 * 
	 * @see pa.interfaces.I7_3#receive_I7_3(java.util.ArrayList)
	 */
	@Override
	public void receive_I7_3(ArrayList<clsWordPresentation> poActionCommands) {
		moE34KnowledgeAboutReality2.receive_I7_3(poActionCommands);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 27.04.2010, 10:59:08
	 * 
	 * @see pa.interfaces.I7_5#receive_I7_5(int)
	 */
	@Override
	public void receive_I7_5(int pnData) {
		((I7_5_receive)moEnclosingContainer).receive_I7_5(pnData);
		
	}

}
