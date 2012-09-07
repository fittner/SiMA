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
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I2_2_receive;
import pa._v38.interfaces.modules.I3_2_receive;
import pa._v38.interfaces.modules.I3_2_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import config.clsProperties;
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
public class F03_GenerationOfSelfPreservationDrives extends clsModuleBaseKB implements I2_2_receive, I3_2_send {
	public static final String P_MODULENUMBER = "03";
	public static final String P_HOMEOSTASISLABEL = "label";
	public static final String P_HOMEOSTASISFACTOR = "factor";
	public static final String P_NUM_HOMEOSTASIS = "num";
	public static String moDriveObjectType = "DriveObject";
	
	/** <source, tension> list of all symbols from the body */
	private HashMap<String, Double> moHomeostasisSymbols_IN; 
	
	private HashMap<eOrgan, eOrifice> moOrificeMap;
	
	private ArrayList <clsDriveMesh> moDriveCandidates_OUT;

	//einfluess auf die normalisierung von body -> psyche
	private HashMap<String, Double> moHomeostaisImpactFactors;
	
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
			clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		applyProperties(poPrefix, poProp);
		fillOrificeMapping();
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
				
		int i=0;
		
		oProp.setProperty(pre+i+"."+P_HOMEOSTASISLABEL, "STOMACH");
		oProp.setProperty(pre+i+"."+P_HOMEOSTASISFACTOR, 1.0);
		i++;
		oProp.setProperty(pre+i+"."+P_HOMEOSTASISLABEL, "RECTUM");
		oProp.setProperty(pre+i+"."+P_HOMEOSTASISFACTOR, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_HOMEOSTASISLABEL, "STAMINA");
		oProp.setProperty(pre+i+"."+P_HOMEOSTASISFACTOR, 1.0);
		i++;
		
		oProp.setProperty(pre+P_NUM_HOMEOSTASIS, i);
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		
		moHomeostaisImpactFactors = new HashMap<String, Double>();
		
		int num = poProp.getPropertyInt(pre+P_NUM_HOMEOSTASIS);
		for (int i=0; i<num; i++) {
			String oKey = poProp.getProperty(pre+i+"."+P_HOMEOSTASISLABEL);
			Double oValue = poProp.getPropertyDouble(pre+i+"."+P_HOMEOSTASISFACTOR);
			moHomeostaisImpactFactors.put(oKey, oValue);
		}
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
					moDriveCandidates_OUT.add( CreateDriveCandidate(eOrgan.STOMACH, oEntry.getValue()) );
				} catch (Exception e) {
					e.printStackTrace();
				}
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
					else{
						System.out.printf("WARNING: Homeostatic value " + oEntry.getKey() + " not found in eOrgan, something missing?\n");
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
					System.out.print(e);
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



}
