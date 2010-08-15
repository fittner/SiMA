/**
 * C05_DriveHandling.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:28:05
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;

import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.interfaces.receive.I1_2_receive;
import pa.interfaces.receive.I1_3_receive;
import pa.interfaces.receive.I1_4_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsDriveDemand;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.tools.clsPair;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:28:05
 * 
 */
public class G05_DriveHandling extends clsModuleContainer implements
							I1_2_receive,
							I1_3_receive,
							I1_4_receive
							{
	public static final String P_E03 = "E03";
	public static final String P_E04 = "E04";
	
	public E03_GenerationOfDrives moE03GenerationOfDrives;
	public E04_FusionOfDrives     moE04FusionOfDrives;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:36:39
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public G05_DriveHandling(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E03_GenerationOfDrives.getDefaultProperties(pre+P_E03) );
		oProp.putAll( E04_FusionOfDrives.getDefaultProperties(pre+P_E04) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE03GenerationOfDrives = new E03_GenerationOfDrives(pre+P_E03, poProp, this, moInterfaceHandler);
		moE04FusionOfDrives = new E04_FusionOfDrives(pre+P_E04, poProp, this, moInterfaceHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:20:42
	 * 
	 * @see pa.interfaces.I1_2#receive_I1_2(int)
	 */
	@Override
	public void receive_I1_2(HashMap<String, Double> poHomeostasisSymbols) {
		moE03GenerationOfDrives.receive_I1_2(poHomeostasisSymbols);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:35:25
	 * 
	 * @see pa.interfaces.I1_3#receive_I1_3(int)
	 */
	@Override
	public void receive_I1_3(ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> poDriveCandidate_old,
							 ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		moE04FusionOfDrives.receive_I1_3(poDriveCandidate_old, poDriveCandidate);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:38:37
	 * 
	 * @see pa.interfaces.I1_4#receive_I1_4(int)
	 */
	@Override
	public void receive_I1_4(ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>,clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> poDriveCandidate_old,
							 ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		((I1_4_receive)moEnclosingContainer).receive_I1_4(poDriveCandidate_old, poDriveCandidate);
		
	}

}
