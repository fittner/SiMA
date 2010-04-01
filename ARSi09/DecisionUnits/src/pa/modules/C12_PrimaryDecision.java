/**
 * C13_PrimaryDecision.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:40:41
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
 * 11.08.2009, 15:40:41
 * 
 */
public class C12_PrimaryDecision extends clsModuleContainer implements 
					I1_5,
					I1_6,
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

	public static final String P_E06 = "E06";
	public static final String P_E19 = "E19";
	
	public E06_DefenseMechanismsForDriveContents moE06DefenseMechanismsForDriveContents;
	public E19_DefenseMechanismsForPerception moE19DefenseMechanismsForPerception;

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
	public C12_PrimaryDecision(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E06_DefenseMechanismsForDriveContents.getDefaultProperties(pre+P_E06) );
		oProp.putAll( E19_DefenseMechanismsForPerception.getDefaultProperties(pre+P_E19) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE06DefenseMechanismsForDriveContents = new E06_DefenseMechanismsForDriveContents(pre+P_E06, poProp, this);
		moE19DefenseMechanismsForPerception = new E19_DefenseMechanismsForPerception(pre+P_E19, poProp, this);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
		moE06DefenseMechanismsForDriveContents.receive_I1_5(poData);		
		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
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
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation) {
		moE19DefenseMechanismsForPerception.receive_I2_9(poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
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
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		moE06DefenseMechanismsForDriveContents.receive_I3_1(pnData);	
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
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
	 * 11.08.2009, 17:42:37
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
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs) {
		moE06DefenseMechanismsForDriveContents.receive_I4_3(poPIs);	
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
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
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(ArrayList<clsAffectTension> poDeniedAffects) {
		((I5_2)moEnclosingContainer).receive_I5_2(poDeniedAffects);	
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 08:50:01
	 * 
	 * @see pa.interfaces.I6_3#receive_I6_3(int)
	 */
	@Override
	public void receive_I6_3(int pnData) {
		moE06DefenseMechanismsForDriveContents.receive_I6_3(pnData);	
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 08:53:15
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		moE19DefenseMechanismsForPerception.receive_I3_2(pnData);	
	}

}
