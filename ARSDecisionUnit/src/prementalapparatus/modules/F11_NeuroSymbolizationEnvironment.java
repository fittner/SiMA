/**
 * E11_NeuroSymbolsEnvironment.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:19:23
 */
package prementalapparatus.modules;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericActivityTimeChart;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;

import communication.datatypes.clsDataContainer;

import prementalapparatus.symbolization.clsSensorToSymbolConverter;
import prementalapparatus.symbolization.eSymbolExtType;
import prementalapparatus.symbolization.representationsymbol.itfSymbol;
import properties.clsProperties;
import modules.interfaces.I1_3_receive;
import modules.interfaces.I2_3_receive;
import modules.interfaces.I2_3_send;
import modules.interfaces.eInterfaces;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;
/**
 * Conversion of raw data into neuro-symbols.
 * 
 * <br><br>
 * 
 * <b>INPUT:</b><br>
 * SPECIAL CASE<br>
 * <i>moEnvironmentalData</i> holds the sensor symbols of the external perception (IN I1.3)<br>
 * HashMap:<br>
 * <i>eSensorExtType</i> enum eSensorExtType<br>
 * <i>clsSensorExtern</i> Base class for all external sensors like vision, mouth area, nose, bump<br>
 * <br>
 * <b>OUTPUT:</b><br>
 * <i>moSymbolData</i> holds the sensortype and the sensor symbol (converted from the extSensor value) (OUT I2.3)
 * 
 * @author muchitsch
 * 07.05.2012, 14:19:23
 * 
 */
public class F11_NeuroSymbolizationEnvironment extends clsModuleBase 
			implements I1_3_receive, I2_3_send, itfInspectorGenericActivityTimeChart {
	public static final String P_MODULENUMBER = "11";
	
	/** holds the sensor symbols of the external perception (IN I1.3) @since 27.07.2011 13:58:58 */
	private clsDataContainer moEnvironmentalData;
	/** holds the sensortype and the sensor symbol (converted from the extSensor value) (OUT I2.3) @since 27.07.2011 14:00:18 */
	private HashMap<eSymbolExtType, itfSymbol> moSymbolData;

	private ArrayList<String> moCaptions = new ArrayList<String>(Arrays.asList(
			"V_ARSIN","V_MEAT","V_CAN","V_CARROT","V_EXCREMENT","V_PLANT","V_REMOTEBOT",
			"V_STONE","V_WALL","MAN_AREA","EAT_AREA","BUMP")); 

	/**
	 * basic constructor  
	 * 
	 * @author muchitsch
	 * 03.03.2011, 16:04:58
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public F11_NeuroSymbolizationEnvironment(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, int pnUid) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
		applyProperties(poPrefix, poProp);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {		
		String text = "";
		
        text += toText.valueToTEXT("moEnvironmentalData", moEnvironmentalData);
		text += toText.mapToTEXT("moSymbolData", moSymbolData);

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
	 * 11.08.2009, 14:20:05
	 * 
	 * @see pa.interfaces.I2_1#receive_I2_1(HashMap<eSensorExtType, clsDataBase>)
	 */
	@Override
	public void receive_I1_3(clsDataContainer poData) {
	    moEnvironmentalData = poData; 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:28
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moSymbolData = clsSensorToSymbolConverter.convertExtSensorToSymbol(moEnvironmentalData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:28
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_3(moSymbolData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:53:57
	 * 
	 * @see pa.interfaces.send.I2_2_send#send_I2_2(java.util.HashMap)
	 */
	@Override
	public void send_I2_3(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		((I2_3_receive)moModuleList.get(14)).receive_I2_3(poEnvironmentalData);
		putInterfaceData(I2_3_send.class, poEnvironmentalData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:14
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (muchitsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:14
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (muchitsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:05:03
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "Conversion of raw data into neuro-symbols.";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 24.04.2011, 00:33:50
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "Active";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 24.04.2011, 00:33:50
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "EXT.Sensors";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 24.04.2011, 00:33:50
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oData = new ArrayList<Double>();
		
		for (int i=0; i<moCaptions.size();i++) {oData.add(0.0);}
		
		ArrayList<eSymbolExtType> oKeys = new ArrayList<eSymbolExtType>(moSymbolData.keySet());
		
		for (int i=0; i<oKeys.size(); i++) {
			eSymbolExtType oKey = oKeys.get(i);
			itfSymbol oValue = moSymbolData.get(oKey);
			
			switch (oKey) {
				case BUMP: break;
				case EATABLE_AREA: break;
				case MANIPULATE_AREA: break; 
				case VISION_FAR:
				case VISION_MEDIUM:
				case VISION_NEAR:
						@SuppressWarnings("unused")
                        double rValue = 1;
						if (oKey == eSymbolExtType.VISION_FAR) {rValue = 0.33;}
						else if (oKey == eSymbolExtType.VISION_MEDIUM) {rValue = 0.66;}
				case ACOUSTIC_FAR:
                case ACOUSTIC_NEAR:
                case ACOUSTIC_MEDIUM:      
                    
   			
						
					break;
			}
		}
		
		return oData;
	}
	
	private double max(double a, double b) {
		if (a>b) {return a;} 
		return b;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 24.04.2011, 00:33:50
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		return moCaptions;
	}
    /* (non-Javadoc)
    *
    * @since 14.05.2014 10:33:20
    * 
    * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
    */
   @Override
   public clsTimeChartPropeties getProperties() {
       return new clsTimeChartPropeties(true);
   }
}
