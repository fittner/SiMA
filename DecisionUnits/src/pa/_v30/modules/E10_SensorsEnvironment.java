/**
 * E10_SensorsEnvironment.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:13:27
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import du.enums.eEntityType;
import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;
import du.itf.sensors.clsSensorRingSegment;
import du.itf.sensors.clsSensorRingSegmentEntry;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I0_4_receive;
import pa._v30.interfaces.modules.I2_1_receive;
import pa._v30.interfaces.modules.I2_1_send;
import pa._v30.tools.toText;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:13:27
 * 
 */
public class E10_SensorsEnvironment extends clsModuleBase implements I0_4_receive, I2_1_send {
	public static final String P_MODULENUMBER = "10";
	
	private HashMap<eSensorExtType, clsSensorExtern> moEnvironmentalData;
	private final int uid;
	
	/**
	 * 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:03:16
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public E10_SensorsEnvironment(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, 
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, int uid) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		this.uid = uid;
		applyProperties(poPrefix, poProp);		
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v30.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {		
		String text = "";
		
		text += toText.mapToTEXT("moEnvironmentalData", moEnvironmentalData);

		return text;
	}	

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
	
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.BODY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.BODY;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:23
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		removeSelfVision();
		
	}
	
	private void removeSelfVision() {
		// TD 2011/05/01 - due to some reason, the ARSIN sees himself in vision near. 
		//remove this entry manually. should be dealt with in vision sensor in project BW.
		//it seems that this happens after the ARSIN has been moved manually. but not always!
		
		clsSensorRingSegment oVisionNear = (clsSensorRingSegment)moEnvironmentalData.get(eSensorExtType.VISION_NEAR);
		ArrayList<clsSensorExtern> oDataObjects = oVisionNear.getDataObjects();
		ArrayList<clsSensorExtern> oDeleteCandidates = new ArrayList<clsSensorExtern>();
		for (clsSensorExtern oTemp:oDataObjects) {
			clsSensorRingSegmentEntry oSRSE = (clsSensorRingSegmentEntry)oTemp;
			
			if (oSRSE.getEntityType() == eEntityType.ARSIN) {
				//ok its a ARSIN - now check if this ARSIN is us!
				if (oSRSE.getEntityId().endsWith("(#"+uid+")")) {
					oDeleteCandidates.add(oSRSE);
				}
			}
		}
		
		for (int i=0; i<oDeleteCandidates.size();i++) {
			clsSensorRingSegmentEntry oSRSE = (clsSensorRingSegmentEntry)oDeleteCandidates.get(i);
			oDataObjects.remove( (clsSensorExtern)oSRSE );
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:23
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_1(moEnvironmentalData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:53:14
	 * 
	 * @see pa.interfaces.send.I2_1_send#send_I2_1(java.util.HashMap)
	 */
	@Override
	public void send_I2_1(HashMap<eSensorExtType, clsSensorExtern> poData) {
		((I2_1_receive)moModuleList.get(11)).receive_I2_1(poData);
		putInterfaceData(I2_1_send.class, poData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:09
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:09
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:03:14
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:03:32
	 * 
	 * @see pa.interfaces.receive._v30.I0_4_receive#receive_I0_4(java.util.List)
	 */
	@Override
	public void receive_I0_4(HashMap<eSensorExtType, clsSensorExtern> poData) {
		moEnvironmentalData = poData;
		
		putInterfaceData(I0_4_receive.class, poData);
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "These sensors collect data of the environment. Typical sensors are the five senses: sight, hearing, smell, touch, and taste. Also non-humanoid sensors like radar are part of this module.";
	}		
}
