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

import pa.datatypes.clsAffectTension;
import pa.datatypes.clsAffectCandidate;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.I1_2;
import pa.interfaces.I1_4;
import pa.interfaces.I1_5;
import pa.interfaces.I2_5;
import pa.interfaces.I2_6;
import pa.interfaces.I2_8;
import pa.interfaces.I2_9;
import pa.interfaces.I4_1;
import pa.interfaces.I4_2;
import pa.interfaces.I4_3;
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
public class C02_Id extends clsModuleContainer implements
								I1_2,
								I1_4,
								I1_5,
								I2_5,
								I2_6,
								I2_8,
								I2_9,
								I4_1,
								I4_2,
								I4_3
								{
	public static final String P_E15 = "E15";
	public static final String P_C05 = "C05";
	public static final String P_C06 = "C06";
	
	public C05_DriveHandling moC05DriveHandling;
	public C06_AffectGeneration moC06AffectGeneration;
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
	public C02_Id(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( C05_DriveHandling.getDefaultProperties(pre+P_C05) );
		oProp.putAll( C06_AffectGeneration.getDefaultProperties(pre+P_C06) );
		oProp.putAll( E15_ManagementOfRepressedContents.getDefaultProperties(pre+P_E15) );		
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moC05DriveHandling = new C05_DriveHandling(pre+P_C05, poProp, this, moMemory);
		moC06AffectGeneration = new C06_AffectGeneration(pre+P_C06, poProp, this, moMemory);
		moE15ManagementOfRepressedContents = new E15_ManagementOfRepressedContents(pre+P_E15, poProp, this, moMemory);
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
		moC05DriveHandling.receive_I1_2(poHomeostasisSymbols);
		
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
		moC06AffectGeneration.receive_I1_4(poDriveCandidate);
		
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
		((I1_5)moEnclosingContainer).receive_I1_5(poData);
		
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
		moC06AffectGeneration.receive_I2_8(poMergedPrimaryInformationMesh);
		
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
		((I2_9)moEnclosingContainer).receive_I2_9(poMergedPrimaryInformation);
		
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
		((I2_6)moEnclosingContainer).receive_I2_6(poPerceptPlusRepressed);
		
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
		((I4_3)moEnclosingContainer).receive_I4_3(poPIs);
		
	}


}
