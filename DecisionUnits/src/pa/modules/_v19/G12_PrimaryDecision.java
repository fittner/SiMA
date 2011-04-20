/**
 * C13_PrimaryDecision.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:40:41
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.List;

import pa._v19.clsInterfaceHandler;
import pa.interfaces.receive._v19.I1_5_receive;
import pa.interfaces.receive._v19.I1_6_receive;
import pa.interfaces.receive._v19.I2_10_receive;
import pa.interfaces.receive._v19.I2_9_receive;
import pa.interfaces.receive._v19.I3_1_receive;
import pa.interfaces.receive._v19.I3_2_receive;
import pa.interfaces.receive._v19.I4_1_receive;
import pa.interfaces.receive._v19.I4_2_receive;
import pa.interfaces.receive._v19.I4_3_receive;
import pa.interfaces.receive._v19.I5_1_receive;
import pa.interfaces.receive._v19.I5_2_receive;
import pa.interfaces.receive._v19.I6_3_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDriveMesh; 
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:40:41
 * 
 */
@Deprecated
public class G12_PrimaryDecision extends clsModuleContainer implements 
					I1_5_receive,
					I1_6_receive,
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

	public static final String P_E06 = "E06";
	public static final String P_E19 = "E19";
	
	public E06_DefenseMechanismsForDriveContents moE06DefenseMechanismsForDriveContents;
	public E19_DefenseMechanismsForPerception    moE19DefenseMechanismsForPerception;

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
	public G12_PrimaryDecision(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
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
	
		moE06DefenseMechanismsForDriveContents = new E06_DefenseMechanismsForDriveContents(pre+P_E06, poProp, this, moInterfaceHandler);
		moE19DefenseMechanismsForPerception = new E19_DefenseMechanismsForPerception(pre+P_E19, poProp, this, moInterfaceHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsDriveMesh> poData) {
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
	public void receive_I1_6(ArrayList<clsDriveMesh> poDriveList) {
		((I1_6_receive)moEnclosingContainer).receive_I1_6(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
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
	public void receive_I2_10(ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		((I2_10_receive)moEnclosingContainer).receive_I2_10(poGrantedPerception);
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
	public void receive_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		((I4_1_receive)moEnclosingContainer).receive_I4_1(poPIs, poTPs, poAffects);	
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
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
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryDataStructureContainer> poPIs) {
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
	public void receive_I5_1(ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		((I5_1_receive)moEnclosingContainer).receive_I5_1(poAffectOnlyList);	
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:42:37
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(ArrayList<clsAssociationDriveMesh> poDeniedAffects) {
		((I5_2_receive)moEnclosingContainer).receive_I5_2(poDeniedAffects);	
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
