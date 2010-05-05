/**
 * C03_Ego.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:11:09
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pa.datatypes.clsAffectTension;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsSecondaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.I1_5;
import pa.interfaces.I1_7;
import pa.interfaces.I2_11;
import pa.interfaces.I2_2;
import pa.interfaces.I2_4;
import pa.interfaces.I2_5;
import pa.interfaces.I2_6;
import pa.interfaces.I2_8;
import pa.interfaces.I2_9;
import pa.interfaces.I3_1;
import pa.interfaces.I3_2;
import pa.interfaces.I3_3;
import pa.interfaces.I4_1;
import pa.interfaces.I4_2;
import pa.interfaces.I4_3;
import pa.interfaces.I7_4;
import pa.loader.plan.clsPlanAction;
import pa.memory.clsMemory;
import pa.symbolization.representationsymbol.itfSymbol;
import pa.tools.clsPair;
import pa.interfaces.I8_1;
import config.clsBWProperties;
import pa.enums.eSymbolExtType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:11:09
 * 
 */
public class G03_Ego extends clsModuleContainer implements
				I1_7,
				I2_2,
				I2_4,
				I2_5,
				I2_6,
				I2_11,
				I1_5,
				I2_8,
				I2_9,
				I3_1,
				I3_2,
				I3_3,
				I4_1,
				I4_2,
				I4_3,
				I7_4,
				I8_1
				{

	public static final String P_G07 = "G07";
	public static final String P_G08 = "G08";
	
	public G07_EnvironmentalInterfaceFunctions moG07EnvironmentalInterfaceFunctions;
	public G08_PsychicMediator 				   moG08PsychicMediator;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:11:44
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public G03_Ego(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( G07_EnvironmentalInterfaceFunctions.getDefaultProperties(pre+P_G07) );
		oProp.putAll( G08_PsychicMediator.getDefaultProperties(pre+P_G08) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moG07EnvironmentalInterfaceFunctions = new G07_EnvironmentalInterfaceFunctions(pre+P_G07, poProp, this, moMemory);
		moG08PsychicMediator = new G08_PsychicMediator(pre+P_G07, poProp, this, moMemory);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@Override
	public void receive_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		moG07EnvironmentalInterfaceFunctions.receive_I2_2(poEnvironmentalData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData) {
		moG07EnvironmentalInterfaceFunctions.receive_I2_4(poBodyData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
		moG08PsychicMediator.receive_I1_5(poData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryInformation> poMergedPrimaryInformation) {
		moG08PsychicMediator.receive_I2_9(poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		moG08PsychicMediator.receive_I3_1(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:44:52
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		moG08PsychicMediator.receive_I3_2(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:45:19
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed) {
		moG08PsychicMediator.receive_I2_6(poPerceptPlusRepressed);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:45:19
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs) {
		moG08PsychicMediator.receive_I4_3(poPIs);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 16:55:05
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP) {
		((I2_5)moEnclosingContainer).receive_I2_5(poEnvironmentalTP);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:30:05
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryInformation,clsPrimaryInformation>> poMergedPrimaryInformationMesh) {
		((I2_8)moEnclosingContainer).receive_I2_8(poMergedPrimaryInformationMesh);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 11.08.2009, 17:31:07
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(int pnData) {
		moG08PsychicMediator.receive_I3_3(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 09:49:53
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
	 * 12.08.2009, 09:49:53
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
	 * 12.08.2009, 10:56:38
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryInformation> poDriveList) {
		((I1_7)moEnclosingContainer).receive_I1_7(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 11:24:59
	 * 
	 * @see pa.interfaces.I7_4#receive_I7_4(int)
	 */
	@Override
	public void receive_I7_4(ArrayList<clsPlanAction> poActionCommands) {
		moG07EnvironmentalInterfaceFunctions.receive_I7_4(poActionCommands);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:45:33
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryInformation> poPerception) {
		((I2_11)moEnclosingContainer).receive_I2_11(poPerception);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:49:00
	 * 
	 * @see pa.interfaces.I8_1#receive_I8_1(int)
	 */
	@Override
	public void receive_I8_1(ArrayList<clsPlanAction> poActionCommands) {
		((I8_1)moEnclosingContainer).receive_I8_1(poActionCommands);
		
	}

}