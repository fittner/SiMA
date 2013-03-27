/**
 * E3_GenerationOfDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:19:04
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.SortedMap;

import pa._v38.tools.clsPair;
import org.apache.log4j.Logger;

import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

import pa._v38.interfaces.itfInspectorGenericDynamicTimeChart;
import pa._v38.interfaces.modules.I2_2_receive;
import pa._v38.interfaces.modules.I3_2_receive;
import pa._v38.interfaces.modules.I3_2_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.itfModuleMemoryAccess;
import pa._v38.memorymgmt.datahandlertools.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.personality.parameter.clsPersonalityParameterContainer;
import config.clsProperties;
import du.enums.eFastMessengerSources;
import du.enums.eOrgan;
import du.enums.eOrifice;
import du.enums.eSensorIntType;
import du.enums.eSlowMessenger;
import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;

/**
 * The neurosymbolic representation of bodily needs are converted to memory 
 * traces representing the corresponding drives. At this stage, such a memory 
 * trace contains drive source, aim of drive, and drive object (cp Section ?). 
 * The quota of affect will be added later. For each bodily need, two drives 
 * are generated: a libidinous and an aggressive one.
 * 
 * @author muchitsch test3
 * 11.08.2009, 12:19:04
 * 
 */
public class F03_GenerationOfSelfPreservationDrives extends clsModuleBaseKB implements I2_2_receive, I3_2_send, itfInspectorGenericDynamicTimeChart {
	public static final String P_MODULENUMBER = "03";
	public static final String P_HOMEOSTASIS_STOMACH = "HOMEOSTASIS_IMPACT_FACTOR_STOMACH";
	public static final String P_HOMEOSTASIS_RECTUM = "HOMEOSTASIS_IMPACT_FACTOR_RECTUM";
	public static final String P_HOMEOSTASIS_STAMINA = "HOMEOSTASIS_IMPACT_FACTOR_STAMINA";
	public static String moDriveObjectType = "DriveObject";
	
	/** <source, tension> list of all symbols from the body */
	private HashMap<String, Double> moHomeostasisSymbols_IN; 
	
	private HashMap<eOrgan, eOrifice> moOrificeMap;
	

	
	private ArrayList <clsDriveMesh> moDriveCandidates_OUT;

	//einfluess auf die normalisierung von body -> psyche
	private HashMap<String, Double> moHomeostaisImpactFactors;
	
	private boolean mnChartColumnsChanged = true;
	private HashMap<String, Double> moTimeChartData; 
	
	private Logger log = Logger.getLogger(this.getClass().getName());
	
	/**
	 * basic constructor 
	 * 
	 * @author muchitsch
	 * 03.03.2011, 15:56:22
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public F03_GenerationOfSelfPreservationDrives(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, 
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			itfModuleMemoryAccess poMemory, clsPersonalityParameterContainer poPersonalityParameterContainer) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poMemory);
		applyProperties(poPrefix, poProp);
		fillOrificeMapping();
		moHomeostaisImpactFactors = new HashMap<String, Double>();
		moHomeostaisImpactFactors.put("STOMACH",poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_HOMEOSTASIS_STOMACH).getParameterDouble());
		moHomeostaisImpactFactors.put("RECTUM",poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_HOMEOSTASIS_RECTUM).getParameterDouble());
		moHomeostaisImpactFactors.put("STAMINA",poPersonalityParameterContainer.getPersonalityParameter("F"+P_MODULENUMBER,P_HOMEOSTASIS_STAMINA).getParameterDouble());
		moTimeChartData =  new HashMap<String, Double>(); //initialize charts
	}
	
	
	private void fillOrificeMapping() {
		//this mapping is fixed for the PA body, no changes! (cm 18.07.2012)
		moOrificeMap = new HashMap<eOrgan, eOrifice>();
		moOrificeMap.put(eOrgan.RECTUM, eOrifice.RECTAL_MUCOSA);
		moOrificeMap.put(eOrgan.STAMINA, eOrifice.TRACHEA);
		moOrificeMap.put(eOrgan.BLADDER, eOrifice.URETHRAL_MUCOSA);
		moOrificeMap.put(eOrgan.STOMACH, eOrifice.ORAL_MUCOSA);
	}

	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
						
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
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
		
		text += toText.mapToTEXT("IN",moHomeostasisSymbols_IN);		
		text += toText.listToTEXT("OUT",moDriveCandidates_OUT);		
		
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
	 * 11.08.2009, 13:46:56
	 * 
	 * @see pa.interfaces.I1_2#receive_I1_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_2(HashMap<String, Double> poHomeostasisSymbols) {
		moHomeostasisSymbols_IN = (HashMap<String, Double>)deepCopy(poHomeostasisSymbols);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:48
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		moDriveCandidates_OUT = new ArrayList<clsDriveMesh>();
		
		//OVERVIEW: from the body-symbol-tension list, create a set of psychic datastructures that represent the demands+sources+tensions
		HashMap<String, Double> oNormalizedHomeostatsisSymbols = null;
		
		// 1- Normalization of bodily demands according to table
		oNormalizedHomeostatsisSymbols = NormalizeHomeostaticSymbols(moHomeostasisSymbols_IN);
		
		// 2- create a drivecandidate for every entry in the list, set the tension, organ orifice
		for( Entry<String, Double> oEntry : oNormalizedHomeostatsisSymbols.entrySet())
		{
						
			if(oEntry.getKey().toString() == eSlowMessenger.BLOODSUGAR.name())
			{
				//bloodsugar is special, make it to a stomach drive
				try {
					clsDriveMesh oMesh = CreateDriveCandidateStomach(eOrgan.STOMACH, oEntry.getValue(), oNormalizedHomeostatsisSymbols);
					moDriveCandidates_OUT.add( oMesh );
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			else if(oEntry.getKey().toString() == (eOrgan.STOMACH.name()))
			{
				//do nothing
			}
			else{
				boolean createDrive = true;
				//test if the information comming from a body is in the eOrgan list, if not... only show a warning
				try {
					String oSource = eOrgan.valueOf(oEntry.getKey()).toString();
				
				} catch (Exception e) {
					createDrive = false;
					if(oEntry.getKey() == "ADREANLIN" || oEntry.getKey() == "HEALTH")
					{}
					else if (oEntry.getKey() == eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString() || oEntry.getKey() == eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString())
					{}
					else{
						log.error("Error in "  + this.getClass().getSimpleName() + ": Homeostatic value " + oEntry.getKey() + " not found in eOrgan, something missing?", e);
					}
					
				}
			
				try {
					
					if(createDrive){
						moDriveCandidates_OUT.add( CreateDriveCandidate(oEntry) );
					}
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}
		
		//generate Time Chart Data
		for( clsDriveMesh oDriveMeshEntry:moDriveCandidates_OUT){
			String oaKey = oDriveMeshEntry.getChartShortString();
			if ( !moTimeChartData.containsKey(oaKey) ) {
				mnChartColumnsChanged = true;
			}
			moTimeChartData.put(oaKey, oDriveMeshEntry.getQuotaOfAffect());	
		}
		
		
	}
	


	/**
	 * DOCUMENT (herret) - insert description
	 * @param oNormalizedHomeostatsisSymbols 
	 *
	 * @since Jan 30, 2013 2:18:39 PM
	 *
	 * @param stomach
	 * @param value
	 * @return
	 */
	private clsDriveMesh CreateDriveCandidateStomach(eOrgan poOrgan, double rTension, HashMap<String,Double> oNormalizedHomeostatsisSymbols) throws Exception  {
		clsDriveMesh oDriveCandidate  = null;
		eOrifice oOrifice = moOrificeMap.get(poOrgan);
		
		//create a TPM for the organ
		clsThingPresentationMesh oOrganTPM = 
			(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
					eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORGAN, new ArrayList<clsThingPresentation>(), poOrgan.toString()) );
		
		//create a TPM for the orifice
		clsThingPresentationMesh oOrificeTPM = 
			(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
					eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORIFICE, new ArrayList<clsThingPresentation>(), oOrifice.toString()) );
		
		//if available create TP for libidinous oral mucosa and add to TPM orifice
		if(oNormalizedHomeostatsisSymbols.containsKey(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString())){
			clsThingPresentation oOralLibidinousMucosaTP = 
					(clsThingPresentation)clsDataStructureGenerator.generateDataStructure( 
							eDataType.TP, new clsPair<eContentType,Object>(eContentType.ORIFICE_ORAL_LIBIDINOUS_MUCOSA, oNormalizedHomeostatsisSymbols.get(eFastMessengerSources.ORIFICE_ORAL_LIBIDINOUS_MUCOSA.toString())) );
			oOrificeTPM.assignDataStructure(clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.ORIFICE_ORAL_LIBIDINOUS_MUCOSA, oOrificeTPM, oOralLibidinousMucosaTP, 1.0));
		}
		//if available create TP for aggressiv oral mucosa and add to TPM orifice
		if(oNormalizedHomeostatsisSymbols.containsKey(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString())){
			clsThingPresentation oOralLibidinousMucosaTP = 
					(clsThingPresentation)clsDataStructureGenerator.generateDataStructure( 
							eDataType.TP, new clsPair<eContentType,Object>(eContentType.ORIFICE_ORAL_AGGRESSIV_MUCOSA, oNormalizedHomeostatsisSymbols.get(eFastMessengerSources.ORIFICE_ORAL_AGGRESSIV_MUCOSA.toString())) );
			oOrificeTPM.assignDataStructure(clsDataStructureGenerator.generateASSOCIATIONATTRIBUTE(eContentType.ORIFICE_ORAL_AGGRESSIV_MUCOSA, oOrificeTPM, oOralLibidinousMucosaTP, 1.0));
		}	
		
		
		
		
		//create the DM
		oDriveCandidate = (clsDriveMesh)clsDataStructureGenerator.generateDM(new clsTriple<eContentType, ArrayList<clsThingPresentationMesh>, Object>(eContentType.DRIVECANDIDATE, new ArrayList<clsThingPresentationMesh>(), "") 
				,eDriveComponent.UNDEFINED, ePartialDrive.UNDEFINED );
		
		//supplement the information
		oDriveCandidate.setActualDriveSource(oOrganTPM, 1.0);
		
		oDriveCandidate.setActualBodyOrifice(oOrificeTPM, 1.0);
		
		oDriveCandidate.setQuotaOfAffect(rTension);
		
		return oDriveCandidate;
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:14:49
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I3_2(moDriveCandidates_OUT);
	}
	
	/* (non-Javadoc)
	 *
	 * @since 17.07.2012 14:20:20
	 * 
	 * @see pa._v38.interfaces.modules.I3_2_send#send_I3_2(java.util.ArrayList)
	 */
	@Override
	public void send_I3_2(ArrayList< clsDriveMesh> poHomeostaticDriveCandidates) {
		((I3_2_receive)moModuleList.get(4)).receive_I3_2(poHomeostaticDriveCandidates);
		putInterfaceData(I3_2_send.class, poHomeostaticDriveCandidates);
		
	}


	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:42:01
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {


	}
	
	/**
	 * Creates a DM out of the entry, and adds necessary information, source, etc
	 * @throws Exception 
	 *
	 * @since 16.07.2012 15:20:26
	 *
	 */
	private clsDriveMesh CreateDriveCandidate(eOrgan poOrgan, double rTension) throws Exception {
		clsDriveMesh oDriveCandidate  = null;
		eOrifice oOrifice = moOrificeMap.get(poOrgan);
		
		//create a TPM for the organ
		clsThingPresentationMesh oOrganTPM = 
			(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
					eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORGAN, new ArrayList<clsThingPresentation>(), poOrgan.toString()) );
		
		//create a TPM for the orifice
		clsThingPresentationMesh oOrificeTPM = 
			(clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure( 
					eDataType.TPM, new clsTriple<eContentType, ArrayList<clsThingPresentation>, Object>(eContentType.ORIFICE, new ArrayList<clsThingPresentation>(), oOrifice.toString()) );
		
		//create the DM
		oDriveCandidate = (clsDriveMesh)clsDataStructureGenerator.generateDM(new clsTriple<eContentType, ArrayList<clsThingPresentationMesh>, Object>(eContentType.DRIVECANDIDATE, new ArrayList<clsThingPresentationMesh>(), "") 
				,eDriveComponent.UNDEFINED, ePartialDrive.UNDEFINED );
		
		//supplement the information
		oDriveCandidate.setActualDriveSource(oOrganTPM, 1.0);
		
		oDriveCandidate.setActualBodyOrifice(oOrificeTPM, 1.0);
		
		oDriveCandidate.setQuotaOfAffect(rTension);
		
		return oDriveCandidate;
	}

	/**
	 * Creates a DM out of the entry, and adds necessary information, source, etc
	 * @throws Exception 
	 *
	 * @since 16.07.2012 15:20:26
	 *
	 */
	private clsDriveMesh CreateDriveCandidate(Entry<String, Double> pEntry) throws Exception {
		
		double rTension = pEntry.getValue();
		eOrgan oOrgan = eOrgan.valueOf(pEntry.getKey());

	return CreateDriveCandidate(oOrgan, rTension);
		
	}

	/**
	 * Take the normalization map and produces values ready for translation to psy
	 *
	 * @since 16.07.2012 14:44:30
	 *
	 * @param moHomeostasisSymbols_IN2
	 * @return
	 */
	private HashMap<String, Double> NormalizeHomeostaticSymbols( HashMap<String, Double> poHomeostasisSymbols) {
		
		// look at every source of a demand, and normalize it according to the normalization map
		for( Entry<String, Double> oEntry : poHomeostasisSymbols.entrySet())
		{
			double rEntryTension = oEntry.getValue();
			
			//any special normalization needed for special types? do it here:
			//Special STOMACH_PAIN
			if(oEntry.getKey() == "STOMACH_PAIN" )
			{
				rEntryTension /= 7;
			}
			//Special HEALTH
			if(oEntry.getKey() == eSensorIntType.HEALTH.name())
			{
				rEntryTension /= 100;
			}
			
			//Special STOMACH
			if(oEntry.getKey() == eSensorIntType.STOMACH.name())
			{
				rEntryTension = 1-rEntryTension ;
			}
			

			//Special STOMACH
			if(oEntry.getKey() == eSlowMessenger.BLOODSUGAR.name())
			{
				rEntryTension = 1-rEntryTension ;
			}
			
			//Special STAMINA
			if(oEntry.getKey() == eSensorIntType.STAMINA.name())
			{
				rEntryTension = 1-rEntryTension ;
			}
			
			
			
			
			//if we have a normalization factor, use it
			if(moHomeostaisImpactFactors.containsKey( oEntry.getKey() ) )
			{
				try 
				{
					double rImpactFactor = moHomeostaisImpactFactors.get(oEntry.getKey());
					rEntryTension = rEntryTension * rImpactFactor;
				} catch (java.lang.Exception e) {
					log.error("Error in "  + this.getClass().getSimpleName() , e);

				}
			}
			
			
			
			// they can never be above 1 or below zero
			if (rEntryTension > 1.0) {
				rEntryTension = 1.0;
			} else if (rEntryTension < 0.0) {
				rEntryTension = 0.0;
			}
			
			oEntry.setValue(rEntryTension);
		}
	
		return poHomeostasisSymbols;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:42:01
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:56:30
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
		moDescription = "F03: The neurosymbolic representation of bodily needs are converted to memory traces representing the corresponding drives. At this stage, such a memory trace contains drive source, aim of drive, and drive object (cp Section ?). The quota of affect will be added later. For each bodily need, two drives are generated: a libidinous and an aggressive one. ";
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 10:34:02 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1.0;
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 10:34:02 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return 0;
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 10:34:02 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "0 to 1";
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 10:34:02 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "";
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 10:34:02 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oResult = new ArrayList<Double>();
		oResult.addAll(moTimeChartData.values());
		return oResult;
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 10:34:02 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.addAll(moTimeChartData.keySet());
		return oResult;
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 10:34:02 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsChanged()
	 */
	@Override
	public boolean chartColumnsChanged() {
		return mnChartColumnsChanged;
	}


	/* (non-Javadoc)
	 *
	 * @since Sep 5, 2012 10:34:02 AM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericDynamicTimeChart#chartColumnsUpdated()
	 */
	@Override
	public void chartColumnsUpdated() {
		mnChartColumnsChanged = false;
	}





}
