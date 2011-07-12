/**
 * E2_NeurosymbolizationOfNeeds.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:12:02
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.Map;
import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v38.interfaces.modules.I1_2_receive;
import pa._v38.interfaces.modules.I2_2_receive;
import pa._v38.interfaces.modules.I2_2_send;
import pa._v38.tools.toText;
import config.clsBWProperties;
import du.enums.eSensorIntType;
import du.enums.eSlowMessenger;
import du.itf.sensors.clsDataBase;
import du.itf.sensors.clsFastMessenger;
import du.itf.sensors.clsFastMessengerEntry;
import du.itf.sensors.clsHealthSystem;
import du.itf.sensors.clsIntestinePressure;
import du.itf.sensors.clsSlowMessenger;
import du.itf.sensors.clsStaminaSystem;
import du.itf.sensors.clsStomachTension;

/**
 * DOCUMENT (muchitsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:12:02
 * 
 */
public class F02_NeurosymbolizationOfNeeds extends clsModuleBase 
			implements itfInspectorGenericDynamicTimeChart, I1_2_receive, I2_2_send {
	public static final String P_MODULENUMBER = "02";
	
	private boolean mnChartColumnsChanged = true;
	private ArrayList<String> moChartColumnsCaptions;
	
	private HashMap<eSensorIntType, clsDataBase> moHomeostasis;
	private HashMap<String, Double> moHomeostaticSymbol;
	/**
	 * DOCUMENT (muchitsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 15:55:32
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public F02_NeurosymbolizationOfNeeds(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
		moChartColumnsCaptions = new ArrayList<String>();
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
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.mapToTEXT("moHomeostasis", moHomeostasis);
		text += toText.mapToTEXT("moHomeostaticSymbol", moHomeostaticSymbol);
		
		return text;
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
	 * 11.08.2009, 13:47:00
	 * 
	 * @see pa.interfaces.I1_1#receive_I1_1(int)
	 */
	@Override
	public void receive_I1_2(HashMap<eSensorIntType, clsDataBase> pnData) {
		
		moHomeostasis = (HashMap<eSensorIntType, clsDataBase>) deepCopy(pnData);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:12:58
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		//DOCUMENT CM process basic dokumentieren for F02
		
		moHomeostaticSymbol = new HashMap<String, Double>();
		
		clsSlowMessenger oSlowMessengerSystem = (clsSlowMessenger)moHomeostasis.get(eSensorIntType.SLOWMESSENGER);
		if(oSlowMessengerSystem!=null)
		{
			for(  Map.Entry< eSlowMessenger, Double > oSlowMessenger : oSlowMessengerSystem.getSlowMessengerValues().entrySet() ) {
				moHomeostaticSymbol.put(oSlowMessenger.getKey().name(), oSlowMessenger.getValue());
			}
		}
		
		
		clsFastMessenger oFastMessengerSystem = (clsFastMessenger)moHomeostasis.get(eSensorIntType.FASTMESSENGER);
		if(oFastMessengerSystem!=null)
		{
			for(  clsFastMessengerEntry oFastMessenger : oFastMessengerSystem.getEntries() ) {
				String oName = oFastMessenger.getSource().name();
				Double rValue = oFastMessenger.getIntensity();
				if (oName.equals("STOMACH")) {
					oName += "_PAIN";
					rValue /= 7;
				}
				moHomeostaticSymbol.put(oName, rValue);
			}
		}
	
		if(moHomeostasis.get(eSensorIntType.STOMACHTENSION)!=null)
			moHomeostaticSymbol.put(eSensorIntType.STOMACHTENSION.name(), ((clsStomachTension)moHomeostasis.get(eSensorIntType.STOMACHTENSION)).getTension() );

		if(moHomeostasis.get(eSensorIntType.INTESTINEPRESSURE)!=null)
			moHomeostaticSymbol.put(eSensorIntType.INTESTINEPRESSURE.name(), ((clsIntestinePressure)moHomeostasis.get(eSensorIntType.INTESTINEPRESSURE)).getPressure() );

		
		if(moHomeostasis.get(eSensorIntType.HEALTH)!=null)
			moHomeostaticSymbol.put(eSensorIntType.HEALTH.name(), ((clsHealthSystem)moHomeostasis.get(eSensorIntType.HEALTH)).getHealthValue() / 100 );
		
		if(moHomeostasis.get(eSensorIntType.STAMINA)!=null)
			moHomeostaticSymbol.put(eSensorIntType.STAMINA.name(), ((clsStaminaSystem)moHomeostasis.get(eSensorIntType.STAMINA)).getStaminaValue() );
		
		for (String oKey:moHomeostaticSymbol.keySet()) {
			if (!moChartColumnsCaptions.contains(oKey)) {
				mnChartColumnsChanged = true;
				moChartColumnsCaptions.add(oKey);
				
				Collections.sort(moChartColumnsCaptions);
			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:12:58
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_2(moHomeostaticSymbol);		
	}

	/**
	 * //FIXME CM delete this method is not used (2011.07.12)
	 * @author langr
	 * 13.08.2009, 02:14:56
	 * 
	 * @return the moHomeostasis
	 */
	@Deprecated
	private HashMap<eSensorIntType, clsDataBase> getHomeostasisData() {
		return moHomeostasis;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:43:46
	 * 
	 * @see pa.interfaces.send.I1_2_send#send_I1_2(java.util.HashMap)
	 */
	@Override
	public void send_I2_2(HashMap<String, Double> poHomeostasisSymbols) {
		((I2_2_receive)moModuleList.get(3)).receive_I2_2(poHomeostasisSymbols);
		
		putInterfaceData(I2_2_send.class, poHomeostasisSymbols);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:41:52
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
	 * 12.07.2010, 10:41:52
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
	 * 03.03.2011, 15:55:37
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
	 * 23.04.2011, 12:24:53
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "0 to 1";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 12:24:53
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Homeostasis Symbols";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 12:24:53
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oResult = new ArrayList<Double>();

		for (String oKey:moChartColumnsCaptions) {
			double rValue = 0;
			
			try {
				rValue = moHomeostaticSymbol.get(oKey);
			} catch (java.lang.Exception e)  {
				//do nothing
			}
			
			oResult.add(rValue);
		}
		
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 12:24:53
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		return moChartColumnsCaptions;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 12:24:53
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1.1;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 12:24:53
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.1;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 12:52:56
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartRowsChanged()
	 */
	@Override
	public boolean chartColumnsChanged() {
		return mnChartColumnsChanged;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 23.04.2011, 19:42:18
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartRowsUpdated()
	 */
	@Override
	public void chartColumnsUpdated() {
		mnChartColumnsChanged = false;
	}	
}
