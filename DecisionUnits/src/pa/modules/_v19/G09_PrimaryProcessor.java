/**
 * C09_PreliminaryExternalPerception.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:35:28
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.List;

import pa._v19.clsInterfaceHandler;
import pa.interfaces.receive.I1_5_receive;
import pa.interfaces.receive.I1_6_receive;
import pa.interfaces.receive.I2_10_receive;
import pa.interfaces.receive.I2_5_receive;
import pa.interfaces.receive.I2_6_receive;
import pa.interfaces.receive.I2_7_receive;
import pa.interfaces.receive.I2_8_receive;
import pa.interfaces.receive.I2_9_receive;
import pa.interfaces.receive.I3_1_receive;
import pa.interfaces.receive.I3_2_receive;
import pa.interfaces.receive.I4_1_receive;
import pa.interfaces.receive.I4_2_receive;
import pa.interfaces.receive.I4_3_receive;
import pa.interfaces.receive.I5_1_receive;
import pa.interfaces.receive.I5_2_receive;
import pa.interfaces.receive.I6_3_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;
import pa.tools.clsTripple;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:35:28
 * 
 */
public class G09_PrimaryProcessor extends clsModuleContainer implements 
					I1_5_receive,
					I1_6_receive,
					I2_5_receive,
					I2_6_receive,
					I2_7_receive,
					I2_8_receive,
					I2_9_receive,
					I2_10_receive,
					I3_1_receive,
					I3_2_receive,
					I4_1_receive,
					I4_2_receive,
					I4_3_receive,
					I5_1_receive,
					I5_2_receive,
					I6_3_receive
					{

	public static final String P_E17 = "E17";
	public static final String P_G12 = "G12";
	public static final String P_G13 = "G13";
	
	public E17_FusionOfExternalPerceptionAndMemoryTraces moE17FusionOfExternalPerceptionAndMemoryTraces;
	public G12_PrimaryDecision                           moG12PrimaryDecision;
	public G13_PrimaryKnowledgeUtilizer                  moG13PrimaryKnowledgeUtilizer;

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
	public G09_PrimaryProcessor(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E17_FusionOfExternalPerceptionAndMemoryTraces.getDefaultProperties(pre+P_E17) );
		oProp.putAll( G12_PrimaryDecision.getDefaultProperties(pre+P_G12) );
		oProp.putAll( G13_PrimaryKnowledgeUtilizer.getDefaultProperties(pre+P_G13) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE17FusionOfExternalPerceptionAndMemoryTraces = new E17_FusionOfExternalPerceptionAndMemoryTraces(pre+P_E17, poProp, this, moInterfaceHandler);
		moG12PrimaryDecision = new G12_PrimaryDecision(pre+P_G12, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
		moG13PrimaryKnowledgeUtilizer = new G13_PrimaryKnowledgeUtilizer(pre+P_G13, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 16:52:58
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
	 * 11.08.2009, 17:16:30
	 * 
	 * @see pa.interfaces.I2_7#receive_I2_7(int)
	 */
	@Override
	public void receive_I2_7(ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>> poPerceptPlusMemories_Output) {
		moE17FusionOfExternalPerceptionAndMemoryTraces.receive_I2_7(poPerceptPlusMemories_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:20:13
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
	 * 11.08.2009, 17:23:38
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed) {
		moG13PrimaryKnowledgeUtilizer.receive_I2_6(poPerceptPlusRepressed);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(ArrayList<clsDriveMesh> poDriveList) {
		((I1_6_receive)moEnclosingContainer).receive_I1_6(poDriveList);	
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I2_10(ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		((I2_10_receive)moEnclosingContainer).receive_I2_10(poGrantedPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		((I4_1_receive)moEnclosingContainer).receive_I4_1(poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects) {
		((I4_2_receive)moEnclosingContainer).receive_I4_2(poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		((I5_1_receive)moEnclosingContainer).receive_I5_1(poAffectOnlyList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:18:20
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsDriveMesh> poData) {
		moG12PrimaryDecision.receive_I1_5(poData);
		moG13PrimaryKnowledgeUtilizer.receive_I1_5(poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:24:33
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		moG12PrimaryDecision.receive_I2_9(poMergedPrimaryInformation);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:25:53
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		moG12PrimaryDecision.receive_I3_2(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:27:51
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryDataStructureContainer> poPIs) {
		moG12PrimaryDecision.receive_I4_3(poPIs);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:29:58
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		moG12PrimaryDecision.receive_I3_1(pnData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:09:40
	 * 
	 * @see pa.interfaces.I6_3#receive_I6_3(int)
	 */
	@Override
	public void receive_I6_3(int pnData) {
		moG12PrimaryDecision.receive_I6_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 13:11:10
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(ArrayList<clsAssociationDriveMesh> poDeniedAffects) {
		((I5_2_receive)moEnclosingContainer).receive_I5_2(poDeniedAffects);
		
	}
}
