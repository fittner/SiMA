/**
 * C0_PsychicApparatus.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:06:02
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import pa.datatypes.clsAffect;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsThingPresentation;
import pa.interfaces.I1_2;
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
import pa.interfaces.I8_1;
import pa.interfaces.itfProcessHomeostases;
import pa.interfaces.itfProcessSensorBody;
import pa.interfaces.itfProcessSensorEnvironment;
import pa.interfaces.itfReturnActionCommands;
import pa.memory.clsMemory;
import config.clsBWProperties;
import decisionunit.itf.actions.itfActionProcessor;
import decisionunit.itf.sensors.clsDataBase;
import decisionunit.itf.sensors.clsSensorExtern;
import enums.eSensorExtType;
import enums.eSensorIntType;

/**
 * DOCUMENT (deutsch) - insert description 
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
public class C00_PsychicApparatus extends clsModuleContainer implements 
							itfProcessSensorEnvironment, 
							itfProcessHomeostases, 
							itfProcessSensorBody,
							itfReturnActionCommands,
							I1_2,
							I1_5,
							I1_7,
							I2_2,
							I2_4,
							I2_5,
							I2_6,
							I2_8,
							I2_9,
							I2_11,
							I3_1,
							I3_2,
							I3_3,
							I4_1,
							I4_2,
							I4_3,
							I8_1
							{
	public static final String P_C01 = "C01";
	public static final String P_C02 = "C02";
	public static final String P_C03 = "C03";
	public static final String P_C04 = "C04";
	public static final String P_MEMORY = "MEMORY";
	
	public C01_Body moC01Body;
	public C02_Id moC02Id;
	public C03_Ego moC03Ego;
	public C04_SuperEgo moC04SuperEgo;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:06:42
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public C00_PsychicApparatus(String poPrefix, clsBWProperties poProp) {
		super(poPrefix, poProp, null, null);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( C01_Body.getDefaultProperties(pre+P_C01) );
		oProp.putAll( C02_Id.getDefaultProperties(pre+P_C02) );
		oProp.putAll( C03_Ego.getDefaultProperties(pre+P_C03) );
		oProp.putAll( C04_SuperEgo.getDefaultProperties(pre+P_C04) );
		oProp.putAll( clsMemory.getDefaultProperties(pre+P_MEMORY) );
		
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moMemory = new clsMemory(pre+P_MEMORY, poProp);
		
		moC01Body = new C01_Body(pre+P_C01, poProp, this, moMemory);
		moC02Id = new C02_Id(pre+P_C02, poProp, this, moMemory);
		moC03Ego = new C03_Ego(pre+P_C03, poProp, this, moMemory);
		moC04SuperEgo = new C04_SuperEgo(pre+P_C04, poProp, this, moMemory);

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
		moC01Body.receiveHomeostases(poData);		
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
		moC01Body.receiveEnvironment(poData);		
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
		moC01Body.receiveBody(poData);
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
		moC02Id.receive_I1_2(poHomeostasisSymbols);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:19:00
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@Override
	public void receive_I2_2(HashMap<eSensorExtType, clsSensorExtern> poEnvironmentalData) {
		moC03Ego.receive_I2_2(poEnvironmentalData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:19:00
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(HashMap<eSensorExtType, clsSensorExtern> poBodyData ) {
		moC03Ego.receive_I2_4(poBodyData);
		
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
		moC03Ego.receive_I3_1(pnData);
		
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
		moC03Ego.receive_I3_2(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:46:45
	 * 
	 * @see pa.interfaces.I3_3#receive_I3_3(int)
	 */
	@Override
	public void receive_I3_3(int pnData) {
		moC03Ego.receive_I3_3(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:50:44
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(List<clsPrimaryInformation> poData) {
		moC03Ego.receive_I1_5(poData);
		moC04SuperEgo.receive_I1_5(poData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:54:35
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@Override
	public void receive_I2_8(int pnData) {
		moC02Id.receive_I2_8(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:54:35
	 * 
	 * @see pa.interfaces.I2_9#receive_I2_9(int)
	 */
	@Override
	public void receive_I2_9(int pnData) {
		moC03Ego.receive_I2_9(pnData);
		moC04SuperEgo.receive_I2_9(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:56:24
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(ArrayList<clsPrimaryInformation> poEnvironmentalTP) {
		moC02Id.receive_I2_5(poEnvironmentalTP);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:02:39
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@Override
	public void receive_I2_6(int pnData) {
		moC03Ego.receive_I2_6(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:02:39
	 * 
	 * @see pa.interfaces.I4_3#receive_I4_3(int)
	 */
	@Override
	public void receive_I4_3(List<clsPrimaryInformation> poPIs) {
		moC03Ego.receive_I4_3(poPIs);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 17:04:33
	 * 
	 * @see pa.interfaces.I8_1#receive_I8_1(int)
	 */
	@Override
	public void receive_I8_1(int pnData) {
		moC01Body.receive_I8_1(pnData);
		
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
		moC01Body.getActionCommands(poActionContainer);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:12:19
	 * 
	 * @see pa.interfaces.I4_1#receive_I4_1(int)
	 */
	@Override
	public void receive_I4_1(List<clsPrimaryInformation> poPIs, List<clsThingPresentation> poTPs, List<clsAffect> poAffects) {
		moC02Id.receive_I4_1(poPIs, poTPs, poAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:12:19
	 * 
	 * @see pa.interfaces.I4_2#receive_I4_2(int)
	 */
	@Override
	public void receive_I4_2(int pnData) {
		moC02Id.receive_I4_2(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 12.08.2009, 10:57:13
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@Override
	public void receive_I1_7(int pnData) {
		moC04SuperEgo.receive_I1_7(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.08.2009, 11:46:47
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@Override
	public void receive_I2_11(int pnData) {
		moC04SuperEgo.receive_I2_11(pnData);
		
	}
}
