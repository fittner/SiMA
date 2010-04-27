/**
 * clsPsychicMediator.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:33:25
 */
package pa.modules;

import java.util.ArrayList;
import java.util.List;

import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.I1_5;
import pa.interfaces.I1_6;
import pa.interfaces.I1_7;
import pa.interfaces.I2_10;
import pa.interfaces.I2_11;
import pa.interfaces.I2_6;
import pa.interfaces.I2_8;
import pa.interfaces.I2_9;
import pa.interfaces.I3_1;
import pa.interfaces.I3_2;
import pa.interfaces.I3_3;
import pa.interfaces.I4_1;
import pa.interfaces.I4_2;
import pa.interfaces.I4_3;
import pa.interfaces.I5_1;
import pa.interfaces.I5_2;
import pa.interfaces.I5_5;
import pa.interfaces.I6_3;
import pa.interfaces.I7_4;
import pa.loader.plan.clsPlanAction;
import pa.memory.clsMemory;
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
					I1_5,
					I1_6,
					I1_7,
					I2_6,
					I2_8,
					I2_9,
					I2_10,
					I2_11,
					I3_1,
					I3_2,
					I3_3,
					I4_1,
					I4_2,
					I4_3,
					I5_1,
					I5_2,
					I5_5,
					I6_3,
					I7_4
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
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
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
	
		moG09PrimaryProcessor = new G09_PrimaryProcessor(pre+P_G09, poProp, this, moMemory);
		moG10PrimaryToSecondaryInterface1 = new G10_PrimaryToSecondaryInterface(pre+P_G10, poProp, this, moMemory);
		moG11SecondaryProcessor = new G11_SecondaryProcessor(pre+P_G11, poProp, this, moMemory);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:21:52
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed) {
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
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryInformation,clsPrimaryInformation>>poMergedPrimaryInformationMesh) {
		((I2_8)moEnclosingContainer).receive_I2_8(poMergedPrimaryInformationMesh);
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
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
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
	public void receive_I1_6(ArrayList<clsPrimaryInformation> poDriveList) {
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
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation) {
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
	public void receive_I4_1(List<clsPrimaryInformation> poPIs, List<clsThingPresentation> poTPs, List<clsAffectTension> poAffects) {
		((I4_1)moEnclosingContainer).receive_I4_1(poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:17:01
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
	 * 12.08.2009, 09:17:01
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs) {
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
	public void receive_I5_1(ArrayList<clsAffectTension> poAffectOnlyList) {
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
		((I6_3)moEnclosingContainer).receive_I6_3(pnData);
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
	public void receive_I5_2(ArrayList<clsAffectTension> poDeniedAffects) {
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
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList) {
		moG11SecondaryProcessor.receive_I1_7(poDriveList); //e23&e26 (perc & delib)
		((I1_7)moEnclosingContainer).receive_I1_7(poDriveList); //e22 (super ego)
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:45:16
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryInformation> poPerception) {
		((I2_11)moEnclosingContainer).receive_I2_11(poPerception); //to e22 (super ego)
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
	public void receive_I7_4(ArrayList<clsPlanAction> poActionCommands) {
		((I7_4)moEnclosingContainer).receive_I7_4(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 13:09:48
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I2_10(ArrayList<clsPrimaryInformation> poGrantedPerception) {
		moG10PrimaryToSecondaryInterface1.receive_I2_10(poGrantedPerception);
		
	}

}
