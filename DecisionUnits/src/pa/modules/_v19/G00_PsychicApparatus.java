/**
 * C0_PsychicApparatus.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:06:02
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pa._v19.clsInterfaceHandler;
import pa.enums.eSymbolExtType;
import pa.interfaces.itfProcessHomeostases;
import pa.interfaces.itfProcessSensorBody;
import pa.interfaces.itfProcessSensorEnvironment;
import pa.interfaces.itfReturnActionCommands;
import pa.interfaces.receive._v19.I1_2_receive;
import pa.interfaces.receive._v19.I1_5_receive;
import pa.interfaces.receive._v19.I1_7_receive;
import pa.interfaces.receive._v19.I2_11_receive;
import pa.interfaces.receive._v19.I2_2_receive;
import pa.interfaces.receive._v19.I2_4_receive;
import pa.interfaces.receive._v19.I2_5_receive;
import pa.interfaces.receive._v19.I2_6_receive;
import pa.interfaces.receive._v19.I2_8_receive;
import pa.interfaces.receive._v19.I2_9_receive;
import pa.interfaces.receive._v19.I3_1_receive;
import pa.interfaces.receive._v19.I3_2_receive;
import pa.interfaces.receive._v19.I3_3_receive;
import pa.interfaces.receive._v19.I4_1_receive;
import pa.interfaces.receive._v19.I4_2_receive;
import pa.interfaces.receive._v19.I4_3_receive;
import pa.interfaces.receive._v19.I8_1_receive;
import pa.memory.clsMemory;
import pa.memorymgmt.clsKnowledgeBaseHandlerFactory;
import pa.memorymgmt.datatypes.clsAct;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.symbolization.representationsymbol.itfSymbol;
import pa.tools.clsPair;
import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.enums.eSensorIntType;
import du.itf.actions.itfActionProcessor;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsSensorExtern;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 15:06:02
 * 
 * 
 * 
 *       
                       wAQWHHHWWUUVOCtOwuXXzu
                      XHHHXZOwOv1+?+!+1OOZXwzX
                  wWHfyuzvOz1+!`^^^^^^^^?=wZWWWmk
                   HpfWXXOz+;!`^^^^^^^^^`+OwWWqHHX
                  gHbfXZOz++:^^^^^..^^^^::+dWqHmHku
                  @gkpkI??;;+?+:^^^^.^^^::+wWHHqkk
                  @gqHkXO+;+;?+;:^^^^^.?;+.jXHHHHk
                  HgmqpXwtz11+;+;:^:::++1++zXWHpWSV
                  H@gHWWXI=z+1+;++++.+1z=???zXyUCjQmX
                 |@MgqqWkkz+l=zlOusss&zrOOz+zwXwZTwWX
                  MM@HHHHWHkzXAQWgHmgdWWkwlzz=dWWkwwu
                 |qHHMNMMMNNS?WHMHHM8dVUUOtrOzZ+zw0u
                  qHMMM@gHHMI:zvXpWU01z++1zrvvOzI1O
                   gHHHmH@gHv:?1+?TUZzz++OwuZ=zzzIz
                   @@HHmmgHSo:??zo?1zzOvuwwOI?zXkXz
                    MM@ggHHky,?++zX++zzOOvzz1=zXWWX
                     @@@@MMNHkXH9z+vkz??z+111lOwXyXr
                     @@@MWHHHW0VC+;!?Uz?+???zwOruX+WkO
                     M@MWWkkkkku&oo+,JtI?z?1wZzOw:.Wkkyv
                     @HHHMMMMMHHWHW9Wz?1+1=jZOOv! dbkqqHkXz
                      MqHHWUZ6OZv1Olz1+1;+1wrI!  JbkkqqqqqHkkX
                        WWXz+++;!+?!?!+?+OzZC   .pkkqqqmmmmgg@Hk
                         Wkw=+;?^:^`.`.z=zv`   .bbqqmgmmmqgmm@@ggmm
                          kwzz+++^.?.+uuC`  `.dqkqqmgg@ggggggg@@gggggmmq
                          gHWkXwAagkHg9!``  .WHgqmg@@@@@@@gg@@@@@@ggggmm
                         @@HMMMMMM@@#^``  .jbH@gmg@@@@@@H@HH@M@@@@@gggmH
                        HMMMMMHVWkH3```  .dbkHMgg@@@@HM@HM@gMM@@@@@@@gH@
 */
@Deprecated
public class G00_PsychicApparatus extends clsModuleContainer implements 
							itfProcessSensorEnvironment, 
							itfProcessHomeostases, 
							itfProcessSensorBody,
							itfReturnActionCommands,
							I1_2_receive,
							I1_5_receive,
							I1_7_receive,
							I2_2_receive,
							I2_4_receive,
							I2_5_receive,
							I2_6_receive,
							I2_8_receive,
							I2_9_receive,
							I2_11_receive,
							I3_1_receive,
							I3_2_receive,
							I3_3_receive,
							I4_1_receive,
							I4_2_receive,
							I4_3_receive,
							I8_1_receive
							{
	public static final String P_G01 = "G01";
	public static final String P_G02 = "G02";
	public static final String P_G03 = "G03";
	public static final String P_G04 = "G04";
	public static final String P_MEMORY = "MEMORY";
	public static final String P_INFORMATIONREPRESENTATIONMGMT = "INFORMATIONREPRESENTATIONMGMT";
	
	public G01_Body     moG01Body;
	public G02_Id       moG02Id;
	public G03_Ego      moG03Ego;
	public G04_SuperEgo moG04SuperEgo;

	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:06:42
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public G00_PsychicApparatus(String poPrefix, clsBWProperties poProp, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, null, poInterfaceHandler, null, null);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( G01_Body.getDefaultProperties(pre+P_G01) );
		oProp.putAll( G02_Id.getDefaultProperties(pre+P_G02) );
		oProp.putAll( G03_Ego.getDefaultProperties(pre+P_G03) );
		oProp.putAll( G04_SuperEgo.getDefaultProperties(pre+P_G04) );
		oProp.putAll( clsMemory.getDefaultProperties(pre+P_MEMORY) );
		
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moMemory = new clsMemory(pre+P_MEMORY, poProp);
		moKnowledgeBaseHandler = clsKnowledgeBaseHandlerFactory.createInformationRepresentationManagement("ARSI10_MGMT", pre+P_INFORMATIONREPRESENTATIONMGMT, poProp);
		
		moG01Body = new G01_Body(pre+P_G01, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
		moG02Id = new G02_Id(pre+P_G02, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
		moG03Ego = new G03_Ego(pre+P_G03, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);
		moG04SuperEgo = new G04_SuperEgo(pre+P_G04, poProp, this, moInterfaceHandler, moMemory, moKnowledgeBaseHandler);

	}
	
	public clsMemory getMemoryForInspector() {
		return moMemory;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:53:01
	 * 
	 * @see pa.interfaces.itfProcessHomeostases#processHomeostases(decisionunit.itf.sensors.clsSensorData)
	 */
	@Override
	public void receiveHomeostases(HashMap<eSensorIntType, clsDataBase> poData) {
		moG01Body.receiveHomeostases(poData);		
	}
	
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 20:58:11
	 * 
	 * @see pa.interfaces.itfProcessSensorEnvironment#receiveEnvironment(java.util.HashMap)
	 */
	@Override
	public void receiveEnvironment(HashMap<eSensorExtType, clsSensorExtern> poData) {
		moG01Body.receiveEnvironment(poData);		
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 20:58:11
	 * 
	 * @see pa.interfaces.itfProcessSensorBody#receiveBody(java.util.HashMap)
	 */
	@Override
	public void receiveBody(HashMap<eSensorExtType, clsSensorExtern> poData) {
		moG01Body.receiveBody(poData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:19:00
	 * 
	 * @see pa.interfaces.I1_2#receive_I1_2(int)
	 */
	@Override
	public void receive_I1_2(HashMap<String, Double> poHomeostasisSymbols) {
		moG02Id.receive_I1_2(poHomeostasisSymbols);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:19:00
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@Override
	public void receive_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		moG03Ego.receive_I2_2(poEnvironmentalData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:19:00
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData ) {
		moG03Ego.receive_I2_4(poBodyData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:46:45
	 * 
	 * @see pa.interfaces.I3_1#receive_I3_1(int)
	 */
	@Override
	public void receive_I3_1(int pnData) {
		moG03Ego.receive_I3_1(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:46:45
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@Override
	public void receive_I3_2(int pnData) {
		moG03Ego.receive_I3_2(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:46:45
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(ArrayList<clsAct> poRuleList) {
		moG03Ego.receive_I3_3(poRuleList);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:50:44
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsDriveMesh> poData) {
		moG03Ego.receive_I1_5(poData);
		moG04SuperEgo.receive_I1_5(poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:54:35
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		moG02Id.receive_I2_8(poMergedPrimaryInformation);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:54:35
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		moG03Ego.receive_I2_9(poMergedPrimaryInformation);
		moG04SuperEgo.receive_I2_9(poMergedPrimaryInformation);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:56:24
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		moG02Id.receive_I2_5(poEnvironmentalTP);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:02:39
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poPerceptPlusRepressed) {
		moG03Ego.receive_I2_6(poPerceptPlusRepressed);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:02:39
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryDataStructureContainer> poPIs) {
		moG03Ego.receive_I4_3(poPIs);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:04:33
	 * 
	 * @see pa.interfaces.I8_1#receive_I8_1(int)
	 */
	@Override
	public void receive_I8_1(ArrayList<clsWordPresentation> poActionCommands) {
		moG01Body.receive_I8_1(poActionCommands);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:09:31
	 * 
	 * @see pa.interfaces.itfReturnActionCommands#getActionCommands()
	 */
	@Override
	public void getActionCommands(itfActionProcessor poActionContainer) {
		moG01Body.getActionCommands(poActionContainer);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:12:19
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(List<clsPrimaryDataStructureContainer> poPIs, List<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, List<clsAssociationDriveMesh> poAffects) {
		moG02Id.receive_I4_1(poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:12:19
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(ArrayList<clsPrimaryDataStructureContainer> poPIs, ArrayList<pa.memorymgmt.datatypes.clsThingPresentation> poTPs, ArrayList<clsAssociationDriveMesh> poAffects) {
		moG02Id.receive_I4_2(poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:57:13
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moG04SuperEgo.receive_I1_7(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:46:47
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		moG04SuperEgo.receive_I2_11(poPerception);
		
	}
}
