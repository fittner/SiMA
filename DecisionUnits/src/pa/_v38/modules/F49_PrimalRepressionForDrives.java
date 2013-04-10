/**
 * F49_PrimalRepressionForDrives.java: DecisionUnits - pa._v38.modules
 * 
 * @author zeilinger
 * 02.05.2011, 15:47:30
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.SortedMap;

import pa._v38.modules.ePsychicInstances;
import pa._v38.interfaces.itfInspectorBarChart;
import pa._v38.interfaces.itfGraphCompareInterfaces;
import pa._v38.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v38.interfaces.modules.I5_1_receive;
import pa._v38.interfaces.modules.I5_2_receive;
import pa._v38.interfaces.modules.I5_2_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.personality.parameter.clsPersonalityParameterContainer;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;

import pa._v38.tools.toText;
import config.clsProperties;
import du.enums.eOrgan;
import du.enums.pa.eDriveComponent;

/**
 * Primal repressed content is associated to the remembered drive contents. If a assoc.
 * is possible we get remembered drive contents with assoc. primal repressions
 * 
 * @author muchitsch
 * 07.05.2012, 15:47:30
 * 
 */
public class F49_PrimalRepressionForDrives extends clsModuleBase 
			implements I5_1_receive, I5_2_send,itfInspectorGenericDynamicTimeChart, itfInspectorBarChart, itfGraphCompareInterfaces{

	public static final String P_MODULENUMBER = "49";
	
	public static final String P_SPLITFACTOR_STOMACH = "SPLITFACTOR_STOMACH";
	public static final String P_SPLITFACTOR_RECTUM = "SPLITFACTOR_RECTUM";
	public static final String P_SPLITFACTOR_STAMINA = "SPLITFACTOR_STAMINA";
	public static final String P_SPLITFACTOR_ORAL = "SPLITFACTOR_ORAL";
	public static final String P_SPLITFACTOR_ANAL = "SPLITFACTOR_ANAL";
	public static final String P_SPLITFACTOR_GENITAL = "SPLITFACTOR_GENITAL";
	public static final String P_SPLITFACTOR_PHALLIC = "SPLITFACTOR_PHALLIC";
	private HashMap<String, Double> moSplitterFactor;	
	

	private ArrayList<clsDriveMesh> moInput;
	private ArrayList<clsDriveMesh> moOutput;
	
	private HashMap<String,Double> moChartInputData;
	private HashMap<String,Double> moChartOutputData;
	
	
	/** DOCUMENT (muchitsch) - insert description; @since 19.07.2011 14:06:33 */
	private ArrayList< clsTriple<String, String, ArrayList<Double> >> moPrimalRepressionMemory;

	private boolean mnChartColumnsChanged =true;

	private Hashtable<Object, Object> moDriveChartData;
	/**
	 * Constructor, and filles the primal repression memory
	 * 
	 * @author muchitsch
	 * 02.05.2011, 15:51:29
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F49_PrimalRepressionForDrives(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsPersonalityParameterContainer poPersonalityParameterContainer)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		applyProperties(poPrefix, poProp); 
		fillPrimalRepressionMemory();
		
		moSplitterFactor = new HashMap<String, Double>();
		moSplitterFactor.put("STOMACH", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_STOMACH).getParameterDouble());
		moSplitterFactor.put("RECTUM", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_RECTUM).getParameterDouble());
		moSplitterFactor.put("STAMINA", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_STAMINA).getParameterDouble());
		moSplitterFactor.put("ORAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_ORAL).getParameterDouble());
		moSplitterFactor.put("ANAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_ANAL).getParameterDouble());
		moSplitterFactor.put("GENITAL", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_GENITAL).getParameterDouble());
		moSplitterFactor.put("PHALLIC", poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_SPLITFACTOR_PHALLIC).getParameterDouble());

		
		moChartInputData = new HashMap<String,Double>();
		moChartOutputData = new HashMap<String,Double>();
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
	
	/**
	 * This precreates the values for primal repression. Tha values are chosen randomly and should be loaded out of a 
	 * personality config file later.
	 *
	 * @since 19.07.2011 14:09:01
	 */
	private void fillPrimalRepressionMemory() {
		moPrimalRepressionMemory = new ArrayList<clsTriple<String,String,ArrayList<Double>>>();
		
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_ORAL", new ArrayList<Double>(Arrays.asList(0.1, 0.2, 0.3, 0.4)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_ANAL", new ArrayList<Double>(Arrays.asList(0.4, 0.3, 0.2, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_PHALLIC", new ArrayList<Double>(Arrays.asList(0.1, 0.1, 0.1, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_GENITAL", new ArrayList<Double>(Arrays.asList(0.1, 0.5, 0.1, 0.2)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_ORAL", new ArrayList<Double>(Arrays.asList(0.8, 0.01, 0.2, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_ANAL", new ArrayList<Double>(Arrays.asList(0.1, 0.4, 0.1, 0.2)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_PHALLIC", new ArrayList<Double>(Arrays.asList(0.01, 0.01, 0.01, 0.6)) ) );
		moPrimalRepressionMemory.add( new clsTriple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_GENITAL", new ArrayList<Double>(Arrays.asList(0.7, 0.7, 0.1, 0.9)) ) );
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moInput", moInput);	
		text += toText.listToTEXT("moOutput", moOutput);		
				
		return text;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:12:55
	 * 
	 * @see pa._v38.interfaces.modules.I5_1_receive#receive_I5_1(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_1(
			ArrayList<clsDriveMesh> poData) {
		
		moInput = (ArrayList<clsDriveMesh>) deepCopy(poData); 
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void process_basic() {
		//moOutput=  deepCopy(moInput);
		moOutput = new ArrayList<clsDriveMesh>();
		RedefineDevisionFactors(deepCopy(moInput));
			
		
		
		// TODO objekte einbeziehen, dafuer auf den clsPhysicalRepresentation die property isCandidateForRepression auf true setzen
		//go to every drive mesh in the list and calculate the partial things
		
		/*for (clsDriveMesh oDM:moOutput) {
			categorizeDriveMesh(oDM);
		}
		*/
		

		//chart data
		for( clsDriveMesh oDriveMeshEntry:moOutput){
			String oaKey = oDriveMeshEntry.getChartShortString();
			moChartOutputData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
		}
		for( clsDriveMesh oDriveMeshEntry:moInput){
			String oaKey = oDriveMeshEntry.getChartShortString();
			moChartInputData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
		}
		
		//mnChartColumnsChanged = false;

	}
	
	private void RedefineDevisionFactors(ArrayList<clsDriveMesh> oDrives){
		ArrayList<clsPair<clsDriveMesh,clsDriveMesh>> oDrivePairs = new ArrayList<clsPair<clsDriveMesh,clsDriveMesh>>();
		
		ArrayList<clsDriveMesh> used = new ArrayList<clsDriveMesh>();
		for(clsDriveMesh oDrive: oDrives){
			if(!used.contains(oDrive)){
				clsDriveMesh oCorresponingDrive=null;
				for(clsDriveMesh oSecDrive: oDrives){
					if(!oSecDrive.equals(oDrive)){
						if(oSecDrive.getActualBodyOrifice().toString().equals(oDrive.getActualBodyOrifice().toString()) && oSecDrive.getActualDriveSource().toString().equals(oDrive.getActualDriveSource().toString())){
							oCorresponingDrive = oSecDrive;
							break;
						}
					}
				}
				if(oCorresponingDrive != null){
					if(oDrive.getDriveComponent().equals(eDriveComponent.AGGRESSIVE)){
						oDrivePairs.add(new clsPair<clsDriveMesh,clsDriveMesh>(oDrive,oCorresponingDrive));
					}
					else{
						oDrivePairs.add(new clsPair<clsDriveMesh,clsDriveMesh>(oCorresponingDrive,oDrive));
					}
					
					used.add(oCorresponingDrive);
				}
				else{
					//if there is no corresponding drive
					moOutput.add(oDrive);
				}
				used.add(oDrive);
				
			}
			
		}
		
		for(clsPair<clsDriveMesh,clsDriveMesh> oDrivePair : oDrivePairs){
	//		if(true){
	//			ProcessSexualDriveCandidates(oDrivePair);
	//		}
	//		else{
				ProcessHomeostaticDriveCandidates(oDrivePair);
	//		}
		}
	}
	
	/**
	 * DOCUMENT (muchitsch) - insert description
	 *
	 * @since 18.07.2012 12:26:24
	 *
	 */
	private void ProcessSexualDriveCandidates(clsPair<clsDriveMesh,clsDriveMesh> oSexualDMEntry) {
			moOutput.add(oSexualDMEntry.a);
			moOutput.add(oSexualDMEntry.b);
	}

	/**
	 * Calculate the new values of the homeostatic drive pairs
	 *
	 * @since 18.07.2012 12:26:19
	 *
	 */
	private void ProcessHomeostaticDriveCandidates(clsPair<clsDriveMesh,clsDriveMesh> oHomeostaticDMPairEntry) {

			double rSlopeFactor = 0.5; //default value, the real values are taken from the personality config in the next loop
				try {
					
					//get the slope factor from the table/aka properties
					eOrgan oOrgan = ((clsDriveMesh)oHomeostaticDMPairEntry.a).getActualDriveSourceAsENUM();
					if(moSplitterFactor.containsKey( oOrgan.toString() ))
					{
						rSlopeFactor = moSplitterFactor.get(oOrgan.toString());
					}
					else
					{
					 //no slope factor found, use default
					}
				} catch (java.lang.Exception e) {
					System.out.print(e);
			}			

			//Calculate the tension values of the pairs, see Deutsch2011 p82 for details
			
			double oOldAgressiveQoA  = ((clsDriveMesh)oHomeostaticDMPairEntry.a).getQuotaOfAffect();
			double oOldLibidoneusQoA = ((clsDriveMesh)oHomeostaticDMPairEntry.b).getQuotaOfAffect();
			
			//calculate the tensions using a linear function
			//if != 0
			clsPair<Double, Double> oSplitResult;
			if(oOldAgressiveQoA + oOldLibidoneusQoA > 0){
			    oSplitResult = shiftQoA(new clsPair<Double,Double>(oOldAgressiveQoA,oOldLibidoneusQoA), rSlopeFactor);
			}
			else{
			    oSplitResult = new clsPair<Double,Double>(0.0,0.0);
			}
			
			/* old version
			 *clsPair<Double, Double> oSplitResult = clsDriveValueSplitter.calc(	oOldAgressiveQoA, 
											oOldLibidoneusQoA, 
											eDriveValueSplitter.ADVANCED, 
											rSlopeFactor); 
			*/
			
			//set the calculated affects into the entry set of the loop
			double oNewAgressiveQoA  = oSplitResult.a;
			double oNewLibidoneusQoA = oSplitResult.b;
			
			((clsDriveMesh)oHomeostaticDMPairEntry.a).setQuotaOfAffect(oNewAgressiveQoA); //set the new agr tension to pair A
			((clsDriveMesh)oHomeostaticDMPairEntry.b).setQuotaOfAffect(oNewLibidoneusQoA); //set the new lib tension to pair B
						
			//and add it to the outgoing list as two separate entries for lib/agr
			moOutput.add((clsDriveMesh)oHomeostaticDMPairEntry.a);
			moOutput.add((clsDriveMesh)oHomeostaticDMPairEntry.b);
	}
	
	
	private clsPair<Double,Double>shiftQoA(clsPair<Double,Double> prValues, double prRatio){
	    double rSum = prValues.a + prValues.b;
	    double rAggrFactor = prValues.a/rSum;
	    double d= 0.0;
	    double k = 0.0;
	    if(prRatio>=0.5){
	        k=(1-prRatio)/0.5;
	        d=1-k;
	    }
	    else{
	        k=prRatio/0.5;
	        d=0.0;
	    }
	    rAggrFactor = rAggrFactor*k + d;
	    
	    return new clsPair<Double,Double>(rSum*rAggrFactor,rSum*(1-rAggrFactor));
	}
	        
	
//	private void categorizeDriveMesh(clsDriveMesh poMD) {
//		for (clsTriple<String,String,ArrayList<Double>> oPRM:moPrimalRepressionMemory) {
//			String oContentType = oPRM.a; 
//			String oContext = oPRM.b;
//			
//			if ( poMD.getMoContent().equals(oContext) && poMD.getMoContentType().equals(oContentType)) {
//				ArrayList<Double> oC = oPRM.c;
//				
//				poMD.setCategories(oC.get(0), oC.get(1), oC.get(2), oC.get(3));
//				break;
//			}
//		}
//	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (zeilinger) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (zeilinger) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_2(moOutput);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.PRIMARY;
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#setPsychicInstances()
	 */
	@Override
	protected void setPsychicInstances() {
		mnPsychicInstances = ePsychicInstances.ID;		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:51:33
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		//FIXME CM english descriptiopn
		moDescription = "Anhaengen von urverdraengten Inhalte an die erinnerten Triebinhalte, wenn einen Assoziation moeglich ist => 'Erinnerte Triebinhalte mit assoziierten Urverdraengungen'";
		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 09:12:55
	 * 
	 * @see pa._v38.interfaces.modules.I5_2_send#send_I5_2(java.util.ArrayList)
	 */
	@Override
	public void send_I5_2(ArrayList<clsDriveMesh> poData) {
		
		((I5_2_receive)moModuleList.get(54)).receive_I5_2(poData);
		
		putInterfaceData(I5_2_send.class, poData);
	}
	
	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1.1;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.1;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "0 to 1";
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Pleasure and Drives";
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oResult = new ArrayList<Double>();
		oResult.addAll(moChartOutputData.values());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moChartOutputData.keySet());
		return oResult;
	}
	
	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsChanged()
	 */
	@Override
	public boolean chartColumnsChanged() {
		return mnChartColumnsChanged;
	}

	/* (non-Javadoc)
	 *
	 * @since 28.08.2012 13:00:17
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsUpdated()
	 */
	@Override
	public void chartColumnsUpdated() {
		mnChartColumnsChanged = false;	
		
	}
	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:38:56 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartTitle()
	 */
	@Override
	public String getBarChartTitle() {

		return "";
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:38:56 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartData()
	 */
	@Override
	public ArrayList<ArrayList<Double>> getBarChartData() {
		ArrayList<Double> oInput= new ArrayList<Double>();
		oInput.addAll(moChartInputData.values());
		
		ArrayList<Double> oOutput= new ArrayList<Double>();
		oOutput.addAll(moChartOutputData.values());
		
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		oResult.add(oInput);
		oResult.add(oOutput);
		
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:38:56 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartCategoryCaptions()
	 */
	@Override
	public ArrayList<String> getBarChartCategoryCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.add("input values");
		oResult.add("output values");
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 11:38:56 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorBarChart#getBarChartColumnCaptions()
	 */
	@Override
	public ArrayList<String> getBarChartColumnCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moChartOutputData.keySet());

		return oResult;

	}

	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2012 3:22:37 PM
	 * 
	 * @see pa._v38.interfaces.itfInterfaceCompare#getCompareInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getCompareInterfacesRecv() {
		
		return getInterfacesRecv();
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2012 3:22:37 PM
	 * 
	 * @see pa._v38.interfaces.itfInterfaceCompare#getCompareInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getCompareInterfacesSend() {
		
		return getInterfacesSend();
	}


}
