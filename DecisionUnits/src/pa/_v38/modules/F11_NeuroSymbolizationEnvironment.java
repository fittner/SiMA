/**
 * E11_NeuroSymbolsEnvironment.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:19:23
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsBWProperties;
import du.enums.eSensorExtType;
import du.itf.sensors.clsSensorExtern;
import pa._v38.symbolization.representationsymbol.clsSymbolVision;
import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.itfInspectorGenericActivityTimeChart;
import pa._v38.interfaces.modules.I1_3_receive;
import pa._v38.interfaces.modules.I2_3_receive;
import pa._v38.interfaces.modules.I2_3_send;
import pa._v38.symbolization.clsSensorToSymbolConverter;
import pa._v38.symbolization.representationsymbol.clsSymbolBump;
import pa._v38.symbolization.representationsymbol.clsSymbolEatableArea;
import pa._v38.symbolization.representationsymbol.clsSymbolManipulateArea;
import pa._v38.symbolization.representationsymbol.clsSymbolVisionEntry;
import pa._v38.symbolization.representationsymbol.itfSymbol;
import pa._v38.symbolization.representationsymbol.itfSymbolVisionEntry;
import pa._v38.tools.toText;
import pa._v38.enums.eSymbolExtType;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:19:23
 * 
 */
public class F11_NeuroSymbolizationEnvironment extends clsModuleBase 
			implements I1_3_receive, I2_3_send, itfInspectorGenericActivityTimeChart {
	public static final String P_MODULENUMBER = "11";
	
	private HashMap<eSensorExtType, clsSensorExtern> moEnvironmentalData;
	private HashMap<eSymbolExtType, itfSymbol> moSymbolData;

	private ArrayList<String> moCaptions = new ArrayList<String>(Arrays.asList(
			"V_BUBBLE","V_CAKE","V_CAN","V_CARROT","V_EXCREMENT","V_PLANT","V_REMOTEBOT",
			"V_STONE","V_WALL","MAN_AREA","EAT_AREA","BUMP")); 

	/**
	 * DOCUMENT (muchitsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:04:58
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public F11_NeuroSymbolizationEnvironment(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
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
		
		text += toText.mapToTEXT("moEnvironmentalData", moEnvironmentalData);
		text += toText.mapToTEXT("moSymbolData", moSymbolData);

		return text;
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		//String pre = clsBWProperties.addDot(poPrefix);
	
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
	public void receive_I1_3(HashMap<eSensorExtType, clsSensorExtern> poData) {
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
				case BUMP: if (((clsSymbolBump)oValue).getBumped()) {
							oData.set(10, 1.0);
						} break;
				case EATABLE_AREA: if (((clsSymbolEatableArea)oValue).getEntries().size() > 1) {
							oData.set(9, 0.5);
						} else if (((clsSymbolEatableArea)oValue).getEntries().size() == 1) {
							oData.set(9, 1.0);
						} break;
				case MANIPULATE_AREA: if (((clsSymbolManipulateArea)oValue).getEntries().size() > 1) {
							oData.set(8, 0.5);
						} else if (((clsSymbolManipulateArea)oValue).getEntries().size() == 1) {
							oData.set(8, 1.0);
						} break; 
				case VISION_FAR:
				case VISION_MEDIUM:
				case VISION_NEAR:
						double rValue = 1;
						if (oKey == eSymbolExtType.VISION_FAR) {rValue = 0.33;}
						else if (oKey == eSymbolExtType.VISION_MEDIUM) {rValue = 0.66;}
						
						ArrayList<itfSymbolVisionEntry> oEntries = ((clsSymbolVision)oValue).getEntries();
						for (int j=0; j<oEntries.size(); j++) {
							clsSymbolVisionEntry oE = (clsSymbolVisionEntry) oEntries.get(j);
							int x = -1;
							switch (oE.getEntityType()) {
								case BUBBLE: x=0; break;
								case CAKE:   x=1; break;
								case CAN:    x=2; break;
								case CARROT: x=3; break;
								case EXCREMENT: x=4; break;
								case PLANT: x=5; break;
								case REMOTEBOT: x=6; break;
								case STONE:  x=7; break;
								case WALL:   x=8; break;
								
							}
							if (x>=0) {
								oData.set(x, max(rValue, oData.get(x)));
							}
						}
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
}
