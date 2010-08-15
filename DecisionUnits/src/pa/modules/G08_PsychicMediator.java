/**
 * clsPsychicMediator.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:33:25
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.receive.I1_5_receive;
import pa.interfaces.receive.I1_6_receive;
import pa.interfaces.receive.I1_7_receive;
import pa.interfaces.receive.I2_10_receive;
import pa.interfaces.receive.I2_11_receive;
import pa.interfaces.receive.I2_6_receive;
import pa.interfaces.receive.I2_8_receive;
import pa.interfaces.receive.I2_9_receive;
import pa.interfaces.receive.I3_1_receive;
import pa.interfaces.receive.I3_2_receive;
import pa.interfaces.receive.I3_3_receive;
import pa.interfaces.receive.I4_1_receive;
import pa.interfaces.receive.I4_2_receive;
import pa.interfaces.receive.I4_3_receive;
import pa.interfaces.receive.I5_1_receive;
import pa.interfaces.receive.I5_2_receive;
import pa.interfaces.receive.I5_5_receive;
import pa.interfaces.receive.I6_3_receive;
import pa.interfaces.receive.I7_4_receive;
import pa.loader.plan.clsPlanAction;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
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
	 * DOCUMENT (deutsch) - insert description 
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
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed_old,
			  ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>> poPerceptPlusRepressed) {
		moG09PrimaryProcessor.receive_I2_6(poPerceptPlusRepressed_old, poPerceptPlusRepressed);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:22:04
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poMergedPrimaryInformation_old,
			  				ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>> poMergedPrimaryInformation) {
		((I2_8_receive)moEnclosingContainer).receive_I2_8(poMergedPrimaryInformation_old, poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:32:38
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(int pnData) {
		moG11SecondaryProcessor.receive_I3_3(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData_old, List<clsDriveMesh> poData) {
		moG09PrimaryProcessor.receive_I1_5(poData_old, poData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(ArrayList<clsPrimaryInformation> poDriveList_old, ArrayList<clsDriveMesh> poDriveList) {
		moG10PrimaryToSecondaryInterface1.receive_I1_6(poDriveList_old, poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation_old, ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		moG09PrimaryProcessor.receive_I2_9(poMergedPrimaryInformation_old, poMergedPrimaryInformation);
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
	public void receive_I4_1(List<clsPrimaryInformation> poPIs_old, List<clsThingPresentation> poTPs_old, List<clsAffectTension> poAffects_old,
			  				 List<clsPrimaryDataStructureContainer> poPIs, List<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		((I4_1_receive)moEnclosingContainer).receive_I4_1(poPIs_old, poTPs_old, poAffects_old, poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
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
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs_old, List<clsPrimaryDataStructureContainer> poPIs) {
		moG09PrimaryProcessor.receive_I4_3(poPIs_old, poPIs);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(ArrayList<clsAffectTension> poAffectOnlyList_old, ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		moG10PrimaryToSecondaryInterface1.receive_I5_1(poAffectOnlyList_old, poAffectOnlyList);
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
	public void receive_I5_2(ArrayList<clsAffectTension> poDeniedAffects_old, ArrayList<clsAssociationDriveMesh> poDeniedAffects) {
		moG10PrimaryToSecondaryInterface1.receive_I5_2(poDeniedAffects_old, poDeniedAffects);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:45:16
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList_old, ArrayList<clsPair<clsSecondaryDataStructureContainer, clsDriveMesh>> poDriveList) {
		moG11SecondaryProcessor.receive_I1_7(poDriveList_old, poDriveList); //e23&e26 (perc & delib)
		((I1_7_receive)moEnclosingContainer).receive_I1_7(poDriveList_old, poDriveList); //e22 (super ego)
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:45:16
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryInformation> poPerception_old, ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		((I2_11_receive)moEnclosingContainer).receive_I2_11(poPerception_old, poPerception); //to e22 (super ego)
		moG11SecondaryProcessor.receive_I2_11(poPerception_old, poPerception);		 //to e23
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
	public void receive_I7_4(ArrayList<clsPlanAction> poActionCommands) {
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
	public void receive_I2_10(ArrayList<clsPrimaryInformation> poGrantedPerception_old, ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		moG10PrimaryToSecondaryInterface1.receive_I2_10(poGrantedPerception_old, poGrantedPerception);
		
	}

}
