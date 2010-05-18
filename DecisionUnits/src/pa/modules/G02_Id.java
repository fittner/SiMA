/**
 * C02_ID.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:10:40
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectTension;
import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.receive.I1_2_receive;
import pa.interfaces.receive.I1_4_receive;
import pa.interfaces.receive.I1_5_receive;
import pa.interfaces.receive.I2_5_receive;
import pa.interfaces.receive.I2_6_receive;
import pa.interfaces.receive.I2_8_receive;
import pa.interfaces.receive.I2_9_receive;
import pa.interfaces.receive.I4_1_receive;
import pa.interfaces.receive.I4_2_receive;
import pa.interfaces.receive.I4_3_receive;
import pa.memory.clsMemory;
import pa.tools.clsPair;
import config.clsBWProperties;
/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:10:40
 * 
 */
public class G02_Id extends clsModuleContainer implements
								I1_2_receive,
								I1_4_receive,
								I1_5_receive,
								I2_5_receive,
								I2_6_receive,
								I2_8_receive,
								I2_9_receive,
								I4_1_receive,
								I4_2_receive,
								I4_3_receive
								{
	public static final String P_E15 = "E15";
	public static final String P_G05 = "G05";
	public static final String P_G06 = "G06";
	
	public G05_DriveHandling 				 moG05DriveHandling;
	public G06_AffectGeneration 			 moG06AffectGeneration;
	public E15_ManagementOfRepressedContents moE15ManagementOfRepressedContents;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:11:47
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public G02_Id(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( G05_DriveHandling.getDefaultProperties(pre+P_G05) );
		oProp.putAll( G06_AffectGeneration.getDefaultProperties(pre+P_G06) );
		oProp.putAll( E15_ManagementOfRepressedContents.getDefaultProperties(pre+P_E15) );		
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moG05DriveHandling = new G05_DriveHandling(pre+P_G05, poProp, this, moInterfaceHandler, moMemory);
		moG06AffectGeneration = new G06_AffectGeneration(pre+P_G06, poProp, this, moInterfaceHandler, moMemory);
		moE15ManagementOfRepressedContents = new E15_ManagementOfRepressedContents(pre+P_E15, poProp, this, moInterfaceHandler, moMemory);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:19:49
	 * 
	 * @see pa.interfaces.I1_2#receive_I1_2(int)
	 */
	@Override
	public void receive_I1_2(HashMap<String, Double> poHomeostasisSymbols) {
		moG05DriveHandling.receive_I1_2(poHomeostasisSymbols);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:39:00
	 * 
	 * @see pa.interfaces.I1_4#receive_I1_4(int)
	 */
	@Override
	public void receive_I1_4(ArrayList<clsPair<clsPair<clsPrimaryInformationMesh, clsAffectCandidate>, 
	  		  clsPair<clsPrimaryInformationMesh, clsAffectCandidate>>> poDriveCandidate) {
		moG06AffectGeneration.receive_I1_4(poDriveCandidate);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:50:12
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
		((I1_5_receive)moEnclosingContainer).receive_I1_5(poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:53:37
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryInformation,clsPrimaryInformation>> poMergedPrimaryInformationMesh) {
		moG06AffectGeneration.receive_I2_8(poMergedPrimaryInformationMesh);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:53:37
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation) {
		((I2_9_receive)moEnclosingContainer).receive_I2_9(poMergedPrimaryInformation);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:56:51
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP) {
		moE15ManagementOfRepressedContents.receive_I2_5(poEnvironmentalTP);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:58:59
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(List<clsPrimaryInformation> poPIs, List<clsThingPresentation> poTPs, List<clsAffectTension> poAffects) {
		moE15ManagementOfRepressedContents.receive_I4_1(poPIs, poTPs, poAffects);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:58:59
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(ArrayList<clsPrimaryInformation> poPIs, ArrayList<clsThingPresentation> poTPs, ArrayList<clsAffectTension> poAffects) {
		moE15ManagementOfRepressedContents.receive_I4_2(poPIs, poTPs, poAffects);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:01:47
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed) {
		((I2_6_receive)moEnclosingContainer).receive_I2_6(poPerceptPlusRepressed);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:01:47
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs) {
		((I4_3_receive)moEnclosingContainer).receive_I4_3(poPIs);
		
	}


}
