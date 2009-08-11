/**
 * C0_PsychicApparatus.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:06:02
 */
package pa.modules;

import pa.interfaces.I1_2;
import pa.interfaces.I1_5;
import pa.interfaces.I2_2;
import pa.interfaces.I2_4;
import pa.interfaces.I2_5;
import pa.interfaces.I2_8;
import pa.interfaces.I2_9;
import pa.interfaces.I3_1;
import pa.interfaces.I3_2;
import pa.interfaces.I3_3;
import pa.interfaces.itfProcessHomeostases;
import pa.interfaces.itfProcessSensorBody;
import pa.interfaces.itfProcessSensorEnvironment;
import config.clsBWProperties;
import decisionunit.itf.sensors.clsSensorData;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:06:02
 * 
 */
public class C00_PsychicApparatus extends clsModuleContainer implements 
							itfProcessSensorEnvironment, 
							itfProcessHomeostases, 
							itfProcessSensorBody,
							I1_2,
							I1_5,
							I2_2,
							I2_4,
							I2_5,
							I2_8,
							I2_9,
							I3_1,
							I3_2,
							I3_3
							{
	public static final String P_C01 = "C01";
	public static final String P_C02 = "C02";
	public static final String P_C03 = "C03";
	public static final String P_C04 = "C04";
	
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
		super(poPrefix, poProp, null);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( C01_Body.getDefaultProperties(pre+P_C01) );
		oProp.putAll( C02_Id.getDefaultProperties(pre+P_C02) );
		oProp.putAll( C03_Ego.getDefaultProperties(pre+P_C03) );
		oProp.putAll( C04_SuperEgo.getDefaultProperties(pre+P_C04) );
		
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moC01Body = new C01_Body(pre+P_C01, poProp, this);
		moC02Id = new C02_Id(pre+P_C02, poProp, this);
		moC03Ego = new C03_Ego(pre+P_C03, poProp, this);
		moC04SuperEgo = new C04_SuperEgo(pre+P_C04, poProp, this);

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:53:01
	 * 
	 * @see pa.interfaces.itfProcessSensorEnvironment#processEnvironment(decisionunit.itf.sensors.clsSensorData)
	 */
	@Override
	public void receiveEnvironment(clsSensorData poData) {
		moC01Body.receiveEnvironment(poData);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:53:01
	 * 
	 * @see pa.interfaces.itfProcessHomeostases#processHomeostases(decisionunit.itf.sensors.clsSensorData)
	 */
	@Override
	public void receiveHomeostases(clsSensorData poData) {
		moC01Body.receiveHomeostases(poData);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 15:53:01
	 * 
	 * @see pa.interfaces.itfProcessSensorBody#processBody(decisionunit.itf.sensors.clsSensorData)
	 */
	@Override
	public void receiveBody(clsSensorData poData) {
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
	public void receive_I1_2(int pnData) {
		moC02Id.receive_I1_2(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:19:00
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@Override
	public void receive_I2_2(int pnData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:19:00
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(int pnData) {
		// TODO (deutsch) - Auto-generated method stub
		
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
		// TODO (deutsch) - Auto-generated method stub
		
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
		// TODO (deutsch) - Auto-generated method stub
		
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
		// TODO (deutsch) - Auto-generated method stub
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:50:44
	 * 
	 * @see pa.interfaces.I1_5#receive_I1_5(int)
	 */
	@Override
	public void receive_I1_5(int pnData) {
		moC03Ego.receive_I1_5(pnData);
		
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
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:56:24
	 * 
	 * @see pa.interfaces.I2_5#receive_I2_5(int)
	 */
	@Override
	public void receive_I2_5(int pnData) {
		moC02Id.receive_I2_5(pnData);
		
	}
}
