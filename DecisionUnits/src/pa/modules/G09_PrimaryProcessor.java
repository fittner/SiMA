/**
 * C09_PreliminaryExternalPerception.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:35:28
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.I1_5;
import pa.interfaces.I1_6;
import pa.interfaces.I2_10;
import pa.interfaces.I2_5;
import pa.interfaces.I2_6;
import pa.interfaces.I2_7;
import pa.interfaces.I2_8;
import pa.interfaces.I2_9;
import pa.interfaces.I3_1;
import pa.interfaces.I3_2;
import pa.interfaces.I4_1;
import pa.interfaces.I4_2;
import pa.interfaces.I4_3;
import pa.interfaces.I5_1;
import pa.interfaces.I5_2;
import pa.interfaces.I6_3;
import pa.memory.clsMemory;
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
					I1_5,
					I1_6,
					I2_5,
					I2_6,
					I2_7,
					I2_8,
					I2_9,
					I2_10,
					I3_1,
					I3_2,
					I4_1,
					I4_2,
					I4_3,
					I5_1,
					I5_2,
					I6_3
					{

	public static final String P_E17 = "E17";
	public static final String P_G13 = "G13";
	public static final String P_G14 = "G14";
	
	public E17_FusionOfExternalPerceptionAndMemoryTraces moE17FusionOfExternalPerceptionAndMemoryTraces;
	public G12_PrimaryDecision                           moG13PrimaryDecision;
	public G13_PrimaryKnowledgeUtilizer                  moG14PrimaryKnowledgeUtilizer;

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
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E17_FusionOfExternalPerceptionAndMemoryTraces.getDefaultProperties(pre+P_E17) );
		oProp.putAll( G12_PrimaryDecision.getDefaultProperties(pre+P_G13) );
		oProp.putAll( G13_PrimaryKnowledgeUtilizer.getDefaultProperties(pre+P_G14) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE17FusionOfExternalPerceptionAndMemoryTraces = new E17_FusionOfExternalPerceptionAndMemoryTraces(pre+P_E17, poProp, this);
		moG13PrimaryDecision = new G12_PrimaryDecision(pre+P_G13, poProp, this, moMemory);
		moG14PrimaryKnowledgeUtilizer = new G13_PrimaryKnowledgeUtilizer(pre+P_G14, poProp, this, moMemory);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 16:52:58
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP) {
		((I2_5)moEnclosingContainer).receive_I2_5(poEnvironmentalTP);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:16:30
	 * 
	 * @see pa.interfaces.I2_7#receive_I2_7(int)
	 */
	@Override
	public void receive_I2_7(ArrayList<clsTripple<clsPrimaryInformation, clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> poPerceptPlusMemories_Output) {
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
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryInformation,clsPrimaryInformation>> poMergedPrimaryInformationMesh) {
		((I2_8)moEnclosingContainer).receive_I2_8(poMergedPrimaryInformationMesh);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:23:38
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed) {
		moG14PrimaryKnowledgeUtilizer.receive_I2_6(poPerceptPlusRepressed);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(ArrayList<clsPrimaryInformation> poDriveList) {
		((I1_6)moEnclosingContainer).receive_I1_6(poDriveList);	
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I2_10(ArrayList<clsPrimaryInformation> poGrantedPerception) {
		((I2_10)moEnclosingContainer).receive_I2_10(poGrantedPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(List<clsPrimaryInformation> poPIs, List<clsThingPresentation> poTPs, List<clsAffectTension> poAffects) {
		((I4_1)moEnclosingContainer).receive_I4_1(poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(ArrayList<clsPrimaryInformation> poPIs, ArrayList<clsThingPresentation> poTPs, ArrayList<clsAffectTension> poAffects) {
		((I4_2)moEnclosingContainer).receive_I4_2(poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(ArrayList<clsAffectTension> poAffectOnlyList) {
		((I5_1)moEnclosingContainer).receive_I5_1(poAffectOnlyList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:18:20
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
		moG13PrimaryDecision.receive_I1_5(poData);
		moG14PrimaryKnowledgeUtilizer.receive_I1_5(poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:24:33
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation) {
		moG13PrimaryDecision.receive_I2_9(poMergedPrimaryInformation);		
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
		moG13PrimaryDecision.receive_I3_2(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:27:51
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs) {
		moG13PrimaryDecision.receive_I4_3(poPIs);
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
		moG13PrimaryDecision.receive_I3_1(pnData);		
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
		moG13PrimaryDecision.receive_I6_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 13:11:10
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(ArrayList<clsAffectTension> poDeniedAffects) {
		((I5_2)moEnclosingContainer).receive_I5_2(poDeniedAffects);
		
	}
}
