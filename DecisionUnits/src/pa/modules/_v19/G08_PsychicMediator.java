/**
 * clsPsychicMediator.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:33:25
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.List;

import pa._v19.clsInterfaceHandler;
import pa.interfaces.receive._v19.I1_5_receive;
import pa.interfaces.receive._v19.I1_6_receive;
import pa.interfaces.receive._v19.I1_7_receive;
import pa.interfaces.receive._v19.I2_10_receive;
import pa.interfaces.receive._v19.I2_11_receive;
import pa.interfaces.receive._v19.I2_6_receive;
import pa.interfaces.receive._v19.I2_8_receive;
import pa.interfaces.receive._v19.I2_9_receive;
import pa.interfaces.receive._v19.I3_1_receive;
import pa.interfaces.receive._v19.I3_2_receive;
import pa.interfaces.receive._v19.I3_3_receive;
import pa.interfaces.receive._v19.I4_1_receive;
import pa.interfaces.receive._v19.I4_2_receive;
import pa.interfaces.receive._v19.I4_3_receive;
import pa.interfaces.receive._v19.I5_1_receive;
import pa.interfaces.receive._v19.I5_2_receive;
import pa.interfaces.receive._v19.I5_5_receive;
import pa.interfaces.receive._v19.I6_3_receive;
import pa.interfaces.receive._v19.I7_4_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAct;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:33:25
 * 
 */
public class G08_PsychicMediator extends clsModuleContainer implements 
					I1_5_receive,
					I1_6_receive,
					I1_7_receive,
					I2_6_receive,
					I2_8_receive,
					I2_9_receive,
					I2_10_receive,
					I2_11_receive,
					I3_1_receive,
					I3_2_receive,
					I3_3_receive,
					I4_1_receive,
					I4_2_receive,
					I4_3_receive,
					I5_1_receive,
					I5_2_receive,
					I5_5_receive,
					I6_3_receive,
					I7_4_receive
					{

	public static final String P_G09 = "G09";
	public static final String P_G10 = "G10";
	public static final String P_G11 = "G11";
	
	public G09_PrimaryProcessor 		   moG09PrimaryProcessor;
	public G10_PrimaryToSecondaryInterface moG10PrimaryToSecondaryInterface1;
	public G11_SecondaryProcessor          moG11SecondaryProcessor;

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
	public G08_PsychicMediator(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( G09_PrimaryProcessor.getDefaultProperties(pre+P_G09) );
		oProp.putAll( G10_PrimaryToSecondaryInterface.getDefaultProperties(pre+P_G10) );
		oProp.putAll( G11_SecondaryProcessor.getDefaultProperties(pre+P_G11) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moG09PrimaryProcessor = new G09_PrimaryProcessor(pre+P_G09, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
		moG10PrimaryToSecondaryInterface1 = new G10_PrimaryToSecondaryInterface(pre+P_G10, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
		moG11SecondaryProcessor = new G11_SecondaryProcessor(pre+P_G11, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:21:52
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed) {
		moG09PrimaryProcessor.receive_I2_6(poPerceptPlusRepressed);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:22:04
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
	 * 11.08.2009, 17:32:38
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(ArrayList<clsAct> poRuleList) {
		moG11SecondaryProcessor.receive_I3_3(poRuleList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsDriveMesh> poData) {
		moG09PrimaryProcessor.receive_I1_5(poData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(ArrayList<clsDriveMesh> poDriveList) {
		moG10PrimaryToSecondaryInterface1.receive_I1_6(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		moG09PrimaryProcessor.receive_I2_9(poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		moG09PrimaryProcessor.receive_I3_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
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
	 * 12.08.2009, 09:17:01
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
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryDataStructureContainer> poPIs) {
		moG09PrimaryProcessor.receive_I4_3(poPIs);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		moG10PrimaryToSecondaryInterface1.receive_I5_1(poAffectOnlyList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I6_3#receive_I6_3(int)
	 */
	@Override
	public void receive_I6_3(int pnData) {
		((I6_3_receive)moEnclosingContainer).receive_I6_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:25
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		moG09PrimaryProcessor.receive_I3_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:35:06
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(ArrayList<clsAssociationDriveMesh> poDeniedAffects) {
		moG10PrimaryToSecondaryInterface1.receive_I5_2(poDeniedAffects);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:45:16
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moG11SecondaryProcessor.receive_I1_7(poDriveList); //e23&e26 (perc & delib)
		((I1_7_receive)moEnclosingContainer).receive_I1_7(poDriveList); //e22 (super ego)
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:45:16
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		((I2_11_receive)moEnclosingContainer).receive_I2_11(poPerception); //to e22 (super ego)
		moG11SecondaryProcessor.receive_I2_11(poPerception);		 //to e23
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:45:16
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 */
	@Override
	public void receive_I5_5(int pnData) {
		moG11SecondaryProcessor.receive_I5_5(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:24:22
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(ArrayList<clsWordPresentation> poActionCommands) {
		((I7_4_receive)moEnclosingContainer).receive_I7_4(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 13:09:48
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I2_10(ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		moG10PrimaryToSecondaryInterface1.receive_I2_10(poGrantedPerception);
		
	}

}
