/**
 * C03_Ego.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:11:09
 */
package pa._v19.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import config.clsProperties;
import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I1_5_receive;
import pa._v19.interfaces.receive.I1_7_receive;
import pa._v19.interfaces.receive.I2_11_receive;
import pa._v19.interfaces.receive.I2_2_receive;
import pa._v19.interfaces.receive.I2_4_receive;
import pa._v19.interfaces.receive.I2_5_receive;
import pa._v19.interfaces.receive.I2_6_receive;
import pa._v19.interfaces.receive.I2_8_receive;
import pa._v19.interfaces.receive.I2_9_receive;
import pa._v19.interfaces.receive.I3_1_receive;
import pa._v19.interfaces.receive.I3_2_receive;
import pa._v19.interfaces.receive.I3_3_receive;
import pa._v19.interfaces.receive.I4_1_receive;
import pa._v19.interfaces.receive.I4_2_receive;
import pa._v19.interfaces.receive.I4_3_receive;
import pa._v19.interfaces.receive.I7_4_receive;
import pa._v19.interfaces.receive.I8_1_receive;
import pa._v19.memory.clsMemory;
import pa._v19.symbolization.representationsymbol.itfSymbol;
import pa._v19.tools.clsPair;
import pa._v19.memorymgmt.clsKnowledgeBaseHandler;
import pa._v19.memorymgmt.datatypes.clsAct;
import pa._v19.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v19.memorymgmt.datatypes.clsWordPresentation;
import pa._v19.enums.eSymbolExtType;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 15:11:09
 * 
 */
@Deprecated
public class G03_Ego extends clsModuleContainer implements
				I1_7_receive,
				I2_2_receive,
				I2_4_receive,
				I2_5_receive,
				I2_6_receive,
				I2_11_receive,
				I1_5_receive,
				I2_8_receive,
				I2_9_receive,
				I3_1_receive,
				I3_2_receive,
				I3_3_receive,
				I4_1_receive,
				I4_2_receive,
				I4_3_receive,
				I7_4_receive,
				I8_1_receive
				{

	public static final String P_G07 = "G07";
	public static final String P_G08 = "G08";
	
	public G07_EnvironmentalInterfaceFunctions moG07EnvironmentalInterfaceFunctions;
	public G08_PsychicMediator 				   moG08PsychicMediator;
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:11:44
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public G03_Ego(String poPrefix, clsProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		
		oProp.putAll( G07_EnvironmentalInterfaceFunctions.getDefaultProperties(pre+P_G07) );
		oProp.putAll( G08_PsychicMediator.getDefaultProperties(pre+P_G08) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
	
		moG07EnvironmentalInterfaceFunctions = new G07_EnvironmentalInterfaceFunctions(pre+P_G07, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
		moG08PsychicMediator = new G08_PsychicMediator(pre+P_G08, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@Override
	public void receive_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		moG07EnvironmentalInterfaceFunctions.receive_I2_2(poEnvironmentalData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData) {
		moG07EnvironmentalInterfaceFunctions.receive_I2_4(poBodyData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsDriveMesh> poData) {
		moG08PsychicMediator.receive_I1_5(poData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		moG08PsychicMediator.receive_I2_9(poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		moG08PsychicMediator.receive_I3_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		moG08PsychicMediator.receive_I3_2(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:45:19
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed) {
		moG08PsychicMediator.receive_I2_6(poPerceptPlusRepressed);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:45:19
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryDataStructureContainer> poPIs) {
		moG08PsychicMediator.receive_I4_3(poPIs);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 16:55:05
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		((I2_5_receive)moEnclosingContainer).receive_I2_5(poEnvironmentalTP);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:30:05
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		((I2_8_receive)moEnclosingContainer).receive_I2_8(poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:31:07
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(ArrayList<clsAct> poRuleList) {
		moG08PsychicMediator.receive_I3_3(poRuleList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:49:53
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa._v19.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		((I4_1_receive)moEnclosingContainer).receive_I4_1(poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:49:53
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa._v19.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects) {
		((I4_2_receive)moEnclosingContainer).receive_I4_2(poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:56:38
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		((I1_7_receive)moEnclosingContainer).receive_I1_7(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:24:59
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(ArrayList<clsWordPresentation> poActionCommands) {
		moG07EnvironmentalInterfaceFunctions.receive_I7_4(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:45:33
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		((I2_11_receive)moEnclosingContainer).receive_I2_11(poPerception);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:49:00
	 * 
	 * @see pa.interfaces.I8_1#receive_I8_1(int)
	 */
	@Override
	public void receive_I8_1(ArrayList<clsWordPresentation> poActionCommands) {
		((I8_1_receive)moEnclosingContainer).receive_I8_1(poActionCommands);
		
	}

}
