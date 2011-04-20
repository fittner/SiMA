/**
 * C11_PrimaryToSecondaryInterface.java: DecisionUnits - pa.modules
 * 
 * @author langr
 * 11.08.2009, 15:37:27
 */
package pa.modules._v19;

import java.util.ArrayList;

import pa._v19.clsInterfaceHandler;
import pa.interfaces.receive._v19.I1_6_receive;
import pa.interfaces.receive._v19.I1_7_receive;
import pa.interfaces.receive._v19.I2_10_receive;
import pa.interfaces.receive._v19.I2_11_receive;
import pa.interfaces.receive._v19.I5_1_receive;
import pa.interfaces.receive._v19.I5_2_receive;
import pa.interfaces.receive._v19.I5_3_receive;
import pa.interfaces.receive._v19.I5_4_receive;
import pa.interfaces.receive._v19.I5_5_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import config.clsBWProperties;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 11.08.2009, 15:37:27
 * 
 */
@Deprecated
public class G10_PrimaryToSecondaryInterface extends clsModuleContainer implements
						I1_6_receive,
						I1_7_receive,
						I2_10_receive,
						I2_11_receive,
						I5_1_receive,
						I5_2_receive,
						I5_3_receive,
						I5_4_receive,
						I5_5_receive
						{

	public static final String P_E08 = "E08";
	public static final String P_E20 = "E20";
	public static final String P_E21 = "E21";
	
	public E08_ConversionToSecondaryProcess moE08ConversionToSecondaryProcess;
	public E20_InnerPerception_Affects      moE20InnerPerception_Affects;
	public E21_ConversionToSecondaryProcess moE21ConversionToSecondaryProcess;

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
	public G10_PrimaryToSecondaryInterface(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory, clsKnowledgeBaseHandler poKnowledgeBase) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory, poKnowledgeBase);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E08_ConversionToSecondaryProcess.getDefaultProperties(pre+P_E08) );
		oProp.putAll( E20_InnerPerception_Affects.getDefaultProperties(pre+P_E20) );
		oProp.putAll( E21_ConversionToSecondaryProcess.getDefaultProperties(pre+P_E21) );
				
		return oProp;
	}
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE08ConversionToSecondaryProcess = new E08_ConversionToSecondaryProcess(pre+P_E08, poProp, this, moInterfaceHandler);
		moE20InnerPerception_Affects = new E20_InnerPerception_Affects(pre+P_E20, poProp, this, moInterfaceHandler);
		moE21ConversionToSecondaryProcess = new E21_ConversionToSecondaryProcess(pre+P_E21, poProp, this, moInterfaceHandler);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:20:36
	 * 
	 * @see pa.interfaces.I1_6#receive_I1_6(int)
	 */
	@Override
	public void receive_I1_6(ArrayList<clsDriveMesh> poDriveList) {
		moE08ConversionToSecondaryProcess.receive_I1_6(poDriveList);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:35:40
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		moE20InnerPerception_Affects.receive_I5_1(poAffectOnlyList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:35:40
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(ArrayList<clsAssociationDriveMesh> poDeniedAffects) {
		moE20InnerPerception_Affects.receive_I5_2(poDeniedAffects);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		((I1_7_receive)moEnclosingContainer).receive_I1_7(poDriveList); //to e22 (super ego)
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@Override
	public void receive_I2_10(ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		moE21ConversionToSecondaryProcess.receive_I2_10(poGrantedPerception);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		((I2_11_receive)moEnclosingContainer).receive_I2_11(poPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I5_3#receive_I5_3(int)
	 */
	@Override
	public void receive_I5_3(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moE20InnerPerception_Affects.receive_I5_3(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:39:12
	 * 
	 * @see pa.interfaces.I5_5#receive_I5_5(int)
	 */
	@Override
	public void receive_I5_5(int pnData) {
		((I5_5_receive)moEnclosingContainer).receive_I5_5(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 24.10.2009, 20:12:57
	 * 
	 * @see pa.interfaces.I5_4#receive_I5_4(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_4(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		moE20InnerPerception_Affects.receive_I5_4(poPerception);
	}
}
