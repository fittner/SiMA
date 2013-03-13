/**
 * E4_FusionOfDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 13:40:06
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import pa._v38.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v38.interfaces.modules.I3_2_receive;
import pa._v38.interfaces.modules.I3_4_receive;
import pa._v38.interfaces.modules.I3_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandlertools.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.personality.parameter.clsPersonalityParameterContainer;
import config.clsProperties;
import du.enums.eFastMessengerSources;
import du.enums.eOrgan;
import du.enums.eOrifice;
import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;

/**
 * The libidinous and aggressive drives are combined to pair of opposites. For each bodily need, such a pair exists. 
 * 
 * @author muchitsch
 * 11.08.2009, 13:40:06
 * test
 */
public class F04_FusionOfSelfPreservationDrives extends clsModuleBase implements I3_2_receive, I3_4_send, itfInspectorGenericDynamicTimeChart {
	public static final String P_MODULENUMBER = "04";
	public static final String P_PERSONALITY_FACTOR ="PERSONALITY_CONTENT_FACTOR";
	public static final String P_RECTUM_PAIN_LIMIT ="RECTUM_PAIN_LIMIT";
	
	private double Personality_Content_Factor; //neg = shove it to agressive, pos value = shove it to libidoneus, value is in percent (0.1 = +10%)
	private double rectum_pain_limit; //is rectum tension reaches this limit the aggressiv part gets 100% of rectum tension
	
	private ArrayList<clsDriveMesh> moHomeostaticDriveCandidates_IN;
	private ArrayList <clsPair<clsDriveMesh,clsDriveMesh>> moHomeostaticDriveComponents_OUT;
	
	private ArrayList <clsPair<clsDriveMesh,clsDriveMesh>> moHomeostaticDriveComponents_lastStep;
	
	private HashMap<String, Double> moCandidatePartitionFactor;
	
	private boolean mnChartColumnsChanged = true;
	private HashMap<String, Double> moDriveChartData; 
	
	/** partial crive categories for the homeostatic drives */
	//private ArrayList< clsTriple<String, String, ArrayList<Double> >> moPartialDriveCategories;
	/**
	 * basic constructor, fills oposite pairs 
	 * 
	 * @author muchitsch
	 * 03.03.2011, 15:57:33
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public F04_FusionOfSelfPreservationDrives(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsPersonalityParameterContainer poPersonalityParameterContainer) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);
		Personality_Content_Factor =poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_PERSONALITY_FACTOR).getParameterDouble();
		rectum_pain_limit = poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_RECTUM_PAIN_LIMIT).getParameterDouble();
		moCandidatePartitionFactor = new HashMap<String, Double>();
		moDriveChartData =  new HashMap<String, Double>(); //initialize charts
//		fillOppositePairs();
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
		
		text += toText.listToTEXT("IN", moHomeostaticDriveCandidates_IN);
		text += toText.listToTEXT("OUT",moHomeostaticDriveComponents_OUT);

		return text;
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
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
		mnProcessType = eProcessType.PRIMARY;
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
		mnPsychicInstances = ePsychicInstances.ID;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 13:46:50
	 * 
	 * @see pa.interfaces.I1_3#receive_I1_3(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I3_2(ArrayList<clsDriveMesh> poHomeostaticDriveCandidates){
		moHomeostaticDriveCandidates_IN = (ArrayList<clsDriveMesh>) deepCopy(poHomeostaticDriveCandidates); 
	}

	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:52
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		moHomeostaticDriveComponents_OUT = new ArrayList<clsPair<clsDriveMesh, clsDriveMesh>>();
		//Loop through the drives and duplicate the drive mesh
		//one becomes the lib. one the agr. part of the drive
		for( clsDriveMesh oEntry :  moHomeostaticDriveCandidates_IN)
		{
			clsPair<clsDriveMesh, clsDriveMesh> oTempPair = null;
			clsDriveMesh agressiveDM = null;
			clsDriveMesh libidoneusDM = null;
			double rAgrTension = 0;
			double rLibTension = 0;
			
			
			//calculate the tension for both parts from personality split 50/50
			rAgrTension = oEntry.getQuotaOfAffect()/2; 
			rLibTension = oEntry.getQuotaOfAffect()/2;
			

			if(oEntry.getActualDriveSource().getMoContent().equals(eOrgan.STOMACH.toString()) && moHomeostaticDriveComponents_lastStep != null){
				ArrayList<Double> oDevisioFactorsStomach = generateDivisionFactorsStomach(oEntry);
				rAgrTension = oDevisioFactorsStomach.get(0);
				rLibTension = oDevisioFactorsStomach.get(1);
			}
			else if (oEntry.getActualDriveSource().getMoContent().equals(eOrgan.RECTUM.toString()) && moHomeostaticDriveComponents_lastStep != null){
				ArrayList<Double> oDevisioFactorsRectum = generateDivisionFactorsRectum(oEntry);
				rAgrTension = oDevisioFactorsRectum.get(0);
				rLibTension = oDevisioFactorsRectum.get(1);
			}

			//change the agressive/lib content due to personality
			if(Personality_Content_Factor != 0){
				//oEntry = changeContentByFactor(oEntry);
			}
			
			//1 - create agressive component DM
			agressiveDM = CreateDriveComponentFromCandidate(oEntry);
			agressiveDM.setDriveComponent(eDriveComponent.AGGRESSIVE);
			agressiveDM.setQuotaOfAffect(rAgrTension);
			
			//2- create libidoneus component DM
			libidoneusDM = CreateDriveComponentFromCandidate(oEntry);
			libidoneusDM.setDriveComponent(eDriveComponent.LIBIDINOUS);
			libidoneusDM.setQuotaOfAffect(rLibTension);
			
			//add the components to the new list as PAIR(Agr,Lib)
			oTempPair = new clsPair<clsDriveMesh, clsDriveMesh>(agressiveDM, libidoneusDM); 
			moHomeostaticDriveComponents_OUT.add(oTempPair);
			
			moHomeostaticDriveComponents_lastStep = new ArrayList<clsPair<clsDriveMesh, clsDriveMesh>>();
			
			//save drives for next step
			for(clsPair<clsDriveMesh,clsDriveMesh> oMesh : moHomeostaticDriveComponents_OUT){
				moHomeostaticDriveComponents_lastStep.add(oMesh);
			}
			
			//add some time chart data
			String oaKey = agressiveDM.getChartShortString();
			if ( !moDriveChartData.containsKey(oaKey) ) {
				mnChartColumnsChanged = true;
				
			}
			moDriveChartData.put(oaKey, agressiveDM.getQuotaOfAffect());	
			
			String olKey = libidoneusDM.getChartShortString();
			if ( !moDriveChartData.containsKey(olKey) ) {
				mnChartColumnsChanged = true;
				
			}
			moDriveChartData.put(olKey, libidoneusDM.getQuotaOfAffect());

		}
	}
	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:52
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I3_4(moHomeostaticDriveComponents_OUT);	
		//System.out.printf("\n F04 out ="+ moDriveCandidates);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:45:32
	 * 
	 * @see pa.interfaces.send.I1_4_send#send_I1_4(java.util.ArrayList)
	 */
	@Override
	public void send_I3_4(ArrayList <clsPair<clsDriveMesh,clsDriveMesh>> poDriveComponents) {
		((I3_4_receive)moModuleList.get(48)).receive_I3_4(poDriveComponents);
		putInterfaceData(I3_4_send.class, poDriveComponents);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:42:10
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		
		
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
	
	private clsDriveMesh CreateDriveComponentFromCandidate(clsDriveMesh poDriveCandidate) {
		clsDriveMesh oDriveComponent  = null;
		try {		
			//create the DM
			oDriveComponent = (clsDriveMesh)clsDataStructureGenerator.generateDM(new clsTriple<eContentType, ArrayList<clsThingPresentationMesh>, Object>(eContentType.DRIVECOMPONENT, new ArrayList<clsThingPresentationMesh>(), "") 
					,eDriveComponent.UNDEFINED, ePartialDrive.UNDEFINED );
			
			//copy the information from the drive candidate
			oDriveComponent.setActualDriveSource(poDriveCandidate.getActualDriveSource(), 1.0);

			oDriveComponent.setActualBodyOrifice(poDriveCandidate.getActualBodyOrifice(), 1.0);
			
		} catch (Exception e) {
			e.printStackTrace();
		}

		return oDriveComponent;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:42:10
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
	/**
	 * 
	 * DOCUMENT (herret) - Methode to calculate the QoA of the aggressiv and libidinous parts of the Stomach drive
	 * if QoA of hole stomach drive is rising: rise the aggressiv and libidinous 50/50
	 * if QoA of hole stomach drive is falling: rise the aggressiv and libidinous parts corresponding to the oral erogenous zone
	 * if QoA of hole stomach drive isn't rising or falling: aggressiv and libidinous parts stay constant
	 *
	 * @since Feb 14, 2013 1:20:08 PM
	 *
	 * @param oEntry
	 * @return
	 */
	
	private ArrayList<Double> generateDivisionFactorsStomach(clsDriveMesh oEntry){
		double rAgrTensionOld = 0.0;
		double rLibTensionOld = 0.0;
		double rAgrTension = 0.0;
		double rLibTension = 0.0;
		//get External associated Content
		
		double rLibFactor = 0.5;
		double rAgrFactor = 0.5;
		

		for (clsAssociation oAssoc : oEntry.getMoInternalAssociatedContent()){
			clsDataStructurePA oDataTPM = null;
			if(oAssoc.getMoAssociationElementA().equals(oEntry)) oDataTPM= oAssoc.getMoAssociationElementB();
			else if(oAssoc.getMoAssociationElementB().equals(oEntry))oDataTPM = oAssoc.getMoAssociationElementA();
			if(oDataTPM instanceof clsThingPresentationMesh){
				if(((clsThingPresentationMesh)oDataTPM).getMoContent().equals(eOrifice.ORAL_MUCOSA.toString())){
					for( clsAssociation oAssocIntern : ((clsThingPresentationMesh) oDataTPM).getMoInternalAssociatedContent()){
						clsDataStructurePA oData = null;
						if(oAssocIntern.getMoAssociationElementA().equals(oDataTPM)) oData= oAssocIntern.getMoAssociationElementB();
						else if(oAssocIntern.getMoAssociationElementB().equals(oDataTPM)) oData = oAssocIntern.getMoAssociationElementA();
						if(oData!=null){
							if(oData instanceof clsThingPresentation){
								if(((clsThingPresentation)oData).getMoContentType().toString().equals(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString())){
									rLibFactor = (Double)((clsThingPresentation)oData).getMoContent();
								}
								else if(((clsThingPresentation)oData).getMoContentType().toString().equals(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString())){
									rAgrFactor = (Double)((clsThingPresentation)oData).getMoContent();
								}
							}
						}
					}
				}
			}
		}


		for(clsPair<clsDriveMesh,clsDriveMesh> oMesh: moHomeostaticDriveComponents_lastStep){
			if(oMesh.a.getActualDriveSource().getMoContent().equals(oEntry.getActualDriveSource().getMoContent())){
				rAgrTensionOld = oMesh.a.getQuotaOfAffect();
				rLibTensionOld = oMesh.b.getQuotaOfAffect();
			}
		}
		double rTensionChange = (rAgrTensionOld+rLibTensionOld)-oEntry.getQuotaOfAffect();
		if(rTensionChange > 0){
			double rRelativAgrFactor=0.0;
			if(rLibFactor != 0.0 || rAgrFactor != 0.0){
				rRelativAgrFactor=rAgrFactor/(rLibFactor + rAgrFactor);
				moCandidatePartitionFactor.put("STOMACH", rRelativAgrFactor);
			}
			else{
				if(moCandidatePartitionFactor.containsKey("STOMACH"))rRelativAgrFactor= moCandidatePartitionFactor.get("STOMACH");
				else rRelativAgrFactor=0.5;
			}
			
			//falling stomach tension
			rAgrTension = rAgrTensionOld - rTensionChange * (rRelativAgrFactor);
			//rLibTension = rLibTensionOld - rTensionChange *(0.7);
			
			if(rAgrTension < 0 ) rAgrTension = 0;
			
			if(rAgrTension > oEntry.getQuotaOfAffect()) rAgrTension = oEntry.getQuotaOfAffect();
			rLibTension = oEntry.getQuotaOfAffect()-rAgrTension;
		}
		else if (rTensionChange < 0){
			//rising stomach tension
			rAgrTension = (rAgrTensionOld - (rTensionChange / 2)) ;
			
			if(rAgrTension > oEntry.getQuotaOfAffect()) rAgrTension = oEntry.getQuotaOfAffect();
			rLibTension = oEntry.getQuotaOfAffect() - rAgrTension; 
		}
		else {
			//Stomach Tension doesn't change
			rAgrTension = rAgrTensionOld;
			rLibTension = rLibTensionOld;
		}
		ArrayList<Double> oRetVal = new ArrayList<Double>();
		oRetVal.add(rAgrTension);
		oRetVal.add(rLibTension);
		
		return oRetVal;
	}
	
	ArrayList<Double> generateDivisionFactorsRectum(clsDriveMesh oEntry){
		
		//TODO: Change Methode to calculate aggr and lib value
		double rAgrTension = 0.0;
		double rLibTension = 0.0;
		//get External associated Content
		double rRectumPartitionFactor;
		
		double rRectumTension = oEntry.getQuotaOfAffect();
		rRectumPartitionFactor =rRectumTension*(1/rectum_pain_limit);
		
		if(rRectumPartitionFactor > 1.0) rRectumPartitionFactor = 1.0;
		if(rRectumPartitionFactor < 0.0) rRectumPartitionFactor = 0.0;
		
		
		
		rAgrTension = rRectumTension*rRectumPartitionFactor;
		rLibTension = rRectumTension-rAgrTension;

		ArrayList<Double> oRetVal = new ArrayList<Double>();
		oRetVal.add(rAgrTension);
		oRetVal.add(rLibTension);
		
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:57:39
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
		moDescription = "F04: The libidinous and aggressive drives are combined to pair of opposites. For each bodily need, such a pair exists.";
	}
	
	
	

	/*************************************************************/
	/***                   TIME CHART METHODS                  ***/
	/*************************************************************/
	
	/* (non-Javadoc)
	 *
	 * @since 24.07.2012 15:51:16
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1.1;
	}

	/* (non-Javadoc)
	 *
	 * @since 24.07.2012 15:51:16
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.1;
	}

	/* (non-Javadoc)
	 *
	 * @since 24.07.2012 15:51:16
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "0 to 1";
	}

	/* (non-Javadoc)
	 *
	 * @since 24.07.2012 15:51:16
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "self-preservation drives";
	}

	/* (non-Javadoc)
	 *
	 * @since 24.07.2012 15:51:16
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oResult = new ArrayList<Double>();
		//oResult = (ArrayList<Double>) moDriveChartData.values().toArray();
		oResult.addAll(moDriveChartData.values());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since 24.07.2012 15:51:16
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moDriveChartData.keySet());
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since 24.07.2012 15:51:16
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsChanged()
	 */
	@Override
	public boolean chartColumnsChanged() {
		return mnChartColumnsChanged;
	}

	/* (non-Javadoc)
	 *
	 * @since 24.07.2012 15:51:16
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsUpdated()
	 */
	@Override
	public void chartColumnsUpdated() {
		mnChartColumnsChanged = false;		
	}	
}
