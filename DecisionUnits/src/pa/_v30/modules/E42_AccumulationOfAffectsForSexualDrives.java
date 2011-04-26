/**
 * E42_AccumulationOfAffectsForSexualDrives.java: DecisionUnits - pa.modules._v30
 * 
 * @author deutsch
 * 03.03.2011, 15:20:18
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v30.tools.clsDriveValueSplitter;
import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import pa._v30.tools.eDriveValueSplitter;
import pa._v30.tools.toText;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInspectorGenericTimeChart;
import pa._v30.interfaces.modules.I2_17_receive;
import pa._v30.interfaces.modules.I2_18_receive;
import pa._v30.interfaces.modules.I2_18_send;
import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:20:18
 * 
 */
public class E42_AccumulationOfAffectsForSexualDrives extends clsModuleBase implements 
							I2_17_receive, I2_18_send, itfInspectorGenericTimeChart {
	public static final String P_MODULENUMBER = "42";
	
	private ArrayList< clsPair< clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double> > > moDriveCandidates;
	private ArrayList<clsDriveMesh> moDriveList; 
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 17:55:15
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E42_AccumulationOfAffectsForSexualDrives(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
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
		String text ="";
		
		text += toText.listToTEXT("moDriveCandidates", moDriveCandidates);
		text += toText.listToTEXT("moDriveList", moDriveList);
		
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
	
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
		moDriveList = new ArrayList<clsDriveMesh>(); 

		for (clsPair< clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double> > oEntry:moDriveCandidates) {
			clsPair<Double, Double> oSplitResult = clsDriveValueSplitter.calc(oEntry.a.b.getTension(), oEntry.b.b.getTension(), 
					eDriveValueSplitter.SIMPLE, null); 
			
			double oLifeAffect  = normalize( oSplitResult.a * oEntry.a.c );
			double oDeathAffect = normalize( oSplitResult.b * oEntry.b.c );
			
			oEntry.a.a.setPleasure(oLifeAffect); 
			oEntry.b.a.setPleasure(oDeathAffect); 
			moDriveList.add(oEntry.a.a); 
			moDriveList.add(oEntry.b.a); 			
		}
	}
	
	private double normalize(double r) {
		if (r>1) {return 1;}
		else if (r<-1) {return -1;}
		else {return r;}
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:20:18
	 * 
	 * @see pa.modules._v30.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_18(moDriveList);

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:50:26
	 * 
	 * @see pa.interfaces.send._v30.I2_18_send#receive_I2_18(java.util.ArrayList)
	 */
	@Override
	public void send_I2_18(ArrayList<clsDriveMesh> poDrives) {
		((I2_18_receive)moModuleList.get(44)).receive_I2_18(poDrives);
		
		putInterfaceData(I2_18_send.class, poDrives);
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:50:26
	 * 
	 * @see pa.interfaces.receive._v30.I2_17_receive#receive_I2_17(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_17(ArrayList< clsPair< clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double> > > poDriveCandidates) {
		moDriveCandidates = (ArrayList< clsPair< clsTripple<clsDriveMesh,clsDriveDemand,Double>, clsTripple<clsDriveMesh,clsDriveDemand,Double> > >)deepCopy(poDriveCandidates);

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
		moDescription = "The amount of total stored libido which equals the tension of the sexual drives is attached to the memory traces. Now, thing presentations consisting of drive aim, drive source, drive object, and quota of affects exist and can be processed by the next modules.";
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 17:41:33
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		
		for( clsDriveMesh oDM : moDriveList) {
			oCaptions.add(oDM.getMoContent());
		}
		
		return oCaptions;
	}	
	

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.11.2010, 23:00:57
	 * 
	 * @see pa.interfaces.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oTimingValues = new ArrayList<Double>();
		
		for( clsDriveMesh oDM : moDriveList) {
			oTimingValues.add(oDM.getPleasure());
		}
		return oTimingValues;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:31:35
	 * 
	 * @see pa.interfaces._v30.itfInspectorTimeChart#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Sexual Drives: Drive-Quota of Affect Chart";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:31:35
	 * 
	 * @see pa.interfaces._v30.itfInspectorTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1.05;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:31:35
	 * 
	 * @see pa.interfaces._v30.itfInspectorTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.05;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:34:35
	 * 
	 * @see pa.interfaces._v30.itfInspectorGenericTimeChart#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "Quota of Affects";
	}		
}
