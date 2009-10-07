/**
 * C09_PreliminaryExternalPerception.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:35:28
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import pa.datatypes.clsAffect;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.datatypes.clsThingPresentationMesh;
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
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:35:28
 * 
 */
public class C09_PrimaryProcessor extends clsModuleContainer implements 
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
	public static final String P_C13 = "C13";
	public static final String P_C14 = "C14";
	
	public E17_FusionOfExternalPerceptionAndMemoryTraces moE17FusionOfExternalPerceptionAndMemoryTraces;
	public C12_PrimaryDecision moC13PrimaryDecision;
	public C13_PrimaryKnowledgeUtilizer moC14PrimaryKnowledgeUtilizer;

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
	public C09_PrimaryProcessor(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E17_FusionOfExternalPerceptionAndMemoryTraces.getDefaultProperties(pre+P_E17) );
		oProp.putAll( C12_PrimaryDecision.getDefaultProperties(pre+P_C13) );
		oProp.putAll( C13_PrimaryKnowledgeUtilizer.getDefaultProperties(pre+P_C14) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE17FusionOfExternalPerceptionAndMemoryTraces = new E17_FusionOfExternalPerceptionAndMemoryTraces(pre+P_E17, poProp, this);
		moC13PrimaryDecision = new C12_PrimaryDecision(pre+P_C13, poProp, this, moMemory);
		moC14PrimaryKnowledgeUtilizer = new C13_PrimaryKnowledgeUtilizer(pre+P_C14, poProp, this, moMemory);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 16:52:58
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsThingPresentationMesh> poEnvironmentalTP) {
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
	public void receive_I2_7(int pnData) {
		moE17FusionOfExternalPerceptionAndMemoryTraces.receive_I2_7(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:20:13
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(int pnData) {
		((I2_8)moEnclosingContainer).receive_I2_8(pnData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:23:38
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(int pnData) {
		moC14PrimaryKnowledgeUtilizer.receive_I2_6(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(int pnData) {
		((I1_6)moEnclosingContainer).receive_I1_6(pnData);	
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I2_10(int pnData) {
		((I2_10)moEnclosingContainer).receive_I2_10(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(List<clsPrimaryInformation> poPIs, List<clsThingPresentation> poTPs, List<clsAffect> poAffects) {
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
	public void receive_I4_2(int pnData) {
		((I4_2)moEnclosingContainer).receive_I4_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:03:54
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(int pnData) {
		((I5_1)moEnclosingContainer).receive_I5_1(pnData);
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
		moC13PrimaryDecision.receive_I1_5(poData);
		moC14PrimaryKnowledgeUtilizer.receive_I1_5(poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:24:33
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(int pnData) {
		moC13PrimaryDecision.receive_I2_9(pnData);		
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
		moC13PrimaryDecision.receive_I3_2(pnData);
		
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
		moC13PrimaryDecision.receive_I4_3(poPIs);
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
		moC13PrimaryDecision.receive_I3_1(pnData);		
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
		moC13PrimaryDecision.receive_I6_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 13:11:10
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(int pnData) {
		((I5_2)moEnclosingContainer).receive_I5_2(pnData);
		
	}
}
