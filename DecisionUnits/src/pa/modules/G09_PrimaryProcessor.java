/**
 * C09_PreliminaryExternalPerception.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:35:28
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
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
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP_old, ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		((I2_5_receive)moEnclosingContainer).receive_I2_5(poEnvironmentalTP_old, poEnvironmentalTP);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:16:30
	 * 
	 * @see pa.interfaces.I2_7#receive_I2_7(int)
	 */
	@Override
	public void receive_I2_7(ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation,ArrayList<clsPrimaryInformation>>> poPerceptPlusMemories_Output_old,
			  				ArrayList<clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, ArrayList<clsDriveMesh>>> poPerceptPlusMemories_Output) {
		moE17FusionOfExternalPerceptionAndMemoryTraces.receive_I2_7(poPerceptPlusMemories_Output_old, poPerceptPlusMemories_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:20:13
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poMergedPrimaryInformation_old,
			  ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		((I2_8_receive)moEnclosingContainer).receive_I2_8(poMergedPrimaryInformation_old, poMergedPrimaryInformation);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:23:38
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed_old,
			  ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed) {
		moG13PrimaryKnowledgeUtilizer.receive_I2_6(poPerceptPlusRepressed_old, poPerceptPlusRepressed);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(ArrayList<clsPrimaryInformation> poDriveList_old, ArrayList<clsDriveMesh> poDriveList) {
		((I1_6_receive)moEnclosingContainer).receive_I1_6(poDriveList_old, poDriveList);	
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I2_10(ArrayList<clsPrimaryInformation> poGrantedPerception_old, ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		((I2_10_receive)moEnclosingContainer).receive_I2_10(poGrantedPerception_old, poGrantedPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(List<clsPrimaryInformation> poPIs_old, List<clsThingPresentation> poTPs_old, List<clsAffectTension> poAffects_old,
			  List<clsPrimaryDataStructureContainer> poPIs, List<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		((I4_1_receive)moEnclosingContainer).receive_I4_1(poPIs_old, poTPs_old, poAffects_old, poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(ArrayList<clsPrimaryInformation> poPIs_old, ArrayList<clsThingPresentation> poTPs_old, ArrayList<clsAffectTension> poAffects_old,
			  				ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects) {
		((I4_2_receive)moEnclosingContainer).receive_I4_2(poPIs_old, poTPs_old, poAffects_old, poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(ArrayList<clsAffectTension> poAffectOnlyList_old, ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		((I5_1_receive)moEnclosingContainer).receive_I5_1(poAffectOnlyList_old, poAffectOnlyList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:18:20
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData_old, List<clsDriveMesh> poData) {
		moG12PrimaryDecision.receive_I1_5(poData_old, poData);
		moG13PrimaryKnowledgeUtilizer.receive_I1_5(poData_old, poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:24:33
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation_old,
			  ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		moG12PrimaryDecision.receive_I2_9(poMergedPrimaryInformation_old, poMergedPrimaryInformation);		
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
	public void receive_I4_3(List<clsPrimaryInformation> poPIs_old, List<clsPrimaryDataStructureContainer> poPIs) {
		moG12PrimaryDecision.receive_I4_3(poPIs_old, poPIs);
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
	public void receive_I5_2(ArrayList<clsAffectTension> poDeniedAffects_old, ArrayList<clsAssociationDriveMesh> poDeniedAffects) {
		((I5_2_receive)moEnclosingContainer).receive_I5_2(poDeniedAffects_old, poDeniedAffects);
		
	}
}
