/**
 * C06_AffectGeneration.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:28:15
 */
package pa._v19.modules;

import java.util.ArrayList;
import java.util.List;

import pa._v19.clsInterfaceHandler;
import pa._v19.interfaces.receive.I1_4_receive;
import pa._v19.interfaces.receive.I1_5_receive;
import pa._v19.interfaces.receive.I2_8_receive;
import pa._v19.interfaces.receive.I2_9_receive;
import pa._v19.memory.clsMemory;
import pa._v19.memorymgmt.clsKnowledgeBaseHandler;
import pa._v19.memorymgmt.datatypes.clsDriveDemand;
import pa._v19.memorymgmt.datatypes.clsDriveMesh;
import pa._v19.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v19.tools.clsPair;
import config.clsBWProperties;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 15:28:15
 * 
 */
@Deprecated
public class G06_AffectGeneration extends clsModuleContainer implements
                           I1_4_receive,
                           I1_5_receive,
                           I2_8_receive,
                           I2_9_receive
                           {
	public static final String P_E05 = "E05";
	public static final String P_E18 = "E18";
	
	public E05_GenerationOfAffectsForDrives     moE05GenerationOfAffectsForDrives;
	public E18_GenerationOfAffectsForPerception moE18GenerationOfAffectsForPerception;

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
	public G06_AffectGeneration(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E05_GenerationOfAffectsForDrives.getDefaultProperties(pre+P_E05) );
		oProp.putAll( E18_GenerationOfAffectsForPerception.getDefaultProperties(pre+P_E18) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE05GenerationOfAffectsForDrives = new E05_GenerationOfAffectsForDrives(pre+P_E05, poProp, this, moInterfaceHandler);
		moE18GenerationOfAffectsForPerception = new E18_GenerationOfAffectsForPerception(pre+P_E18, poProp, this, moInterfaceHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:39:25
	 * 
	 * @see pa.interfaces.I1_4#receive_I1_4(int)
	 */
	@Override
	public void receive_I1_4(ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		moE05GenerationOfAffectsForDrives.receive_I1_4(poDriveCandidate);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:49:41
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsDriveMesh> poData) {
		((I1_5_receive)moEnclosingContainer).receive_I1_5(poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:51:45
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		moE18GenerationOfAffectsForPerception.receive_I2_8(poMergedPrimaryInformation);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:52:58
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		((I2_9_receive)moEnclosingContainer).receive_I2_9(poMergedPrimaryInformation);
		
	}
}
