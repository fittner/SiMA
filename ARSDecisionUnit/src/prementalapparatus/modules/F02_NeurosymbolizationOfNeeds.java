/**
 * E2_NeurosymbolizationOfNeeds.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:12:02
 */
package prementalapparatus.modules;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericDynamicTimeChart;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.SortedMap;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;

import properties.clsProperties;

import modules.interfaces.I1_2_receive;
import modules.interfaces.I2_2_receive;
import modules.interfaces.I2_2_send;
import modules.interfaces.eInterfaces;
import base.modules.clsModuleBase;
import base.modules.eImplementationStage;
import base.modules.eProcessType;
import base.modules.ePsychicInstances;
import base.tools.toText;


/**
 * Conversion of raw homoestatic data into neuro-symbols. Also slow and fast messengers are created here.<br><br>
 * 
 * <b>INPUT:</b><br>
 * moHomeostasis holds a map of all homoestatic values sorted by eSensorIntType as key (IN I1.2)<br>
 * <br>
 * <b>OUTPUT:</b><br>
 * moHomeostaticSymbol holds the symbolized list of homoestatic values (OUT I2.2)<br>
 * <i>String</i> is the name eSensorIntType.<br>
 * <i>Double</i> is the tension of the specific symbol .<br>
 * 
 * @author muchitsch
 * 11.08.2009, 12:12:02
 * 
 */
public class F02_NeurosymbolizationOfNeeds extends clsModuleBase 
			implements itfInspectorGenericDynamicTimeChart, I1_2_receive, I2_2_send {
	public static final String P_MODULENUMBER = "02";
	
	private boolean mnChartColumnsChanged = true;
	private ArrayList<String> moChartColumnsCaptions;
	
	/** holds a map of all homoestatic values sorted by eSensorIntType as key (IN I1.2) @since 27.07.2011 13:15:08 */
	private clsDataContainer moBodilyDemands_IN;
	
	/** holds the symbolized list of homoestatic values (OUT I2.2)
	 * String is the name eSensorIntType
	 * Double is the tension of the specific symbol   @since 27.07.2011 13:15:08 */
	private HashMap<String, Double> moHomeostaticSymbol_OUT;
	/**
	 * basic constructor
	 * 
	 * @author muchitsch
	 * 03.03.2011, 15:55:32
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public F02_NeurosymbolizationOfNeeds(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, int pnUid) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, pnUid);
		applyProperties(poPrefix, poProp);	
		moChartColumnsCaptions = new ArrayList<String>();
		//add data that should be displayed
		moChartColumnsCaptions.add("STAMINA");
		moChartColumnsCaptions.add("STOMACH");
		moChartColumnsCaptions.add("RECTUM");
		moChartColumnsCaptions.add("ADRENALIN");
		moChartColumnsCaptions.add("BLOODSUGAR");
		Collections.sort(moChartColumnsCaptions);
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
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
        text += toText.valueToTEXT("moHomeostasis", moBodilyDemands_IN);
		text += toText.mapToTEXT("moHomeostaticSymbol", moHomeostaticSymbol_OUT);
		
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
	public void receive_I1_2(clsDataContainer pnData) {
		
	    moBodilyDemands_IN = pnData;		
		//System.out.printf("\n F03 in ="+ moHomeostasis);
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
		
		//OVERVIEW: go through the IN list and create a symbol-tension pair for all of them, add the other body-sources for drives
		
		// create a empty map for all the tension the body can create.
		// here we collect all the information from the body relevant for homeostatic drives in one list <symbol, tendion>
	      
		moHomeostaticSymbol_OUT = new HashMap<String, Double>();
		
		CollectBodilyDemandsInOneList();
		
        for (HashMap.Entry<String, Double> item : moHomeostaticSymbol_OUT.entrySet()) {
            InfluxDB.sendInflux("F"+P_MODULENUMBER,item.getKey(),item.getValue());
        }
        
		//create some chart of them
/*		for (String oKey:moHomeostaticSymbol_OUT.keySet()) {
			if(oKey != eSensorIntType.HEALTH.toString()){ //health can be very large, dotn add it to chart
				if (!moChartColumnsCaptions.contains(oKey)) {
					mnChartColumnsChanged = true;
					moChartColumnsCaptions.add(oKey);
					
					Collections.sort(moChartColumnsCaptions);
				}
			}
		}
		*/
	}

    /**
     * here we collect all the information from the body relevant for homeostatic drives in one list <symbol, tendion>
     * Normalize them if necessary
     *
     * @since 16.07.2012 13:37:23
     *
     */
    private void CollectBodilyDemandsInOneList() {
        //TODO: remove this ampping and use the receieved values
        for (clsDataPoint oDataPoint : moBodilyDemands_IN.getData()) {
            if (oDataPoint.getType().equals("STOMACH_TENSION")) {
                moHomeostaticSymbol_OUT.put("STOMACH", Double.parseDouble(oDataPoint.getValue()));
            } else if (oDataPoint.getType().equals("STOMACH_INTESTINE_TENSION")) {
                moHomeostaticSymbol_OUT.put("RECTUM", Double.parseDouble(oDataPoint.getValue()));
            } else if (oDataPoint.getType().equals("HEALTH")) {
                moHomeostaticSymbol_OUT.put("HEALTH", Double.parseDouble(oDataPoint.getValue()));
            } else if (oDataPoint.getType().equals("STAMINA")) {
                moHomeostaticSymbol_OUT.put("STAMINA", Double.parseDouble(oDataPoint.getValue()));
            } else if (oDataPoint.getType().equals("SLOW_MESSENGER_SYSTEM")) {
                for(clsDataPoint oEntry: oDataPoint.getAssociatedDataPoints()){
                    moHomeostaticSymbol_OUT.put(oEntry.getType(), Double.parseDouble(oEntry.getValue()));
                }
            }
            else if (oDataPoint.getType().equals("FAST_MESSENGER_SYSTEM")) {
                for(clsDataPoint oEntry: oDataPoint.getAssociatedDataPoints()){
                    moHomeostaticSymbol_OUT.put(oEntry.getType(), Double.parseDouble(oEntry.getValue()));
                }
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
		send_I2_2(moHomeostaticSymbol_OUT);	
		//System.out.printf("\n F03 out ="+ moHomeostaticSymbol);
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
		((I2_2_receive)moModuleList.get(65)).receive_I2_2(poHomeostasisSymbols);
		
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
		moDescription = "F02: Conversion of raw data into neuro-symbols.";
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
		int x=0;
		for (String oKey:moChartColumnsCaptions) {
			double rValue = 0;
			
			try {
				rValue = moHomeostaticSymbol_OUT.get(oKey);
				
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
	 * test
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
