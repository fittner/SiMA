/**
 * F48_AccumulationOfAffectsForDrives.java: DecisionUnits - pa._v38.modules
 * 
 * @author muchitsch
 * 02.05.2011, 15:47:11
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedMap;
import pa._v38.modules.eImplementationStage;
import pa._v38.storage.DT4_PleasureStorage;
import pa._v38.tools.clsDriveValueSplitter;
import pa._v38.tools.eDriveValueSplitter;
import pa._v38.interfaces.modules.I3_3_receive;
import pa._v38.interfaces.modules.I3_4_receive;
import pa._v38.interfaces.modules.I4_1_receive;
import pa._v38.interfaces.modules.I4_1_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveDemand;
import pa._v38.memorymgmt.datatypes.clsDriveMeshOLD;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;
import config.clsProperties;

/**
 * F48 combines Libido and homeostatic drive candidates, calculates the first quota of effect based 
 * on a splitter mechanism and outputs the result to a list of drive candidates.
 * 
 * @author muchitsch
 * 07.05.2012, 15:47:11
 */
public class F48_AccumulationOfQuotaOfAffectsForDrives extends clsModuleBase 
					implements I3_3_receive, I3_4_receive, I4_1_send {

	public static final String P_MODULENUMBER = "48";

	/** c part of the Tripple is the factor read from the propety files. no calculation is done! 
	 * just added to pass the factor down to module F54 @since 13.07.2011 14:05:14 */
	private ArrayList<clsPair<clsTriple<clsDriveMeshOLD, clsDriveDemand, Double>, clsTriple<clsDriveMeshOLD, clsDriveDemand, Double>>> moLibidoCandidates_IN;
	private ArrayList<clsPair<clsPair<clsDriveMeshOLD, clsDriveDemand>, clsPair<clsDriveMeshOLD, clsDriveDemand>>> moHomoestasisCandidates_IN;

	/** This private member is the output of the module, it contains the combined list of homeostatic and libidonues drives @since 14.07.2011 11:23:16 */
	private ArrayList<clsDriveMeshOLD> moDriveCandidates_OUT;
	
	public static final String P_SPLITFACTORLABEL = "label";
	public static final String P_SPLITFACTORVALUE = "value";
	public static final String P_NUM_SPLIFACTOR = "num";
	private HashMap<String, Double> moSplitterFactor;	
	
	private boolean rJACKBAUERWASHERE = false;
	
	private DT4_PleasureStorage moPleasureStorage;
	
	/**
	 *F48 combines Libido and homeostatic drive candidates, calculates the first quota of effect based 
	 * on a splitter mechanism and outputs the result to a list of drive candidates.
	 * 
	 * @author muchitsch
	 * 02.05.2011, 15:48:57
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @param poInterfaceData
	 * @throws Exception
	 */
	public F48_AccumulationOfQuotaOfAffectsForDrives(String poPrefix,
			clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList,
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			DT4_PleasureStorage poPleasureStorage)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);

		moPleasureStorage = poPleasureStorage;
		applyProperties(poPrefix, poProp);	
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
		
		// see PhD Deutsch2011 p82 for what this is used for		
		int i=0;
		
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "NOURISH");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "BITE");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "RELAX");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "DEPOSIT");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "REPRESS");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;
		oProp.setProperty(pre+i+"."+P_SPLITFACTORLABEL, "SLEEP");
		oProp.setProperty(pre+i+"."+P_SPLITFACTORVALUE, 0.5);
		i++;

		oProp.setProperty(pre+P_NUM_SPLIFACTOR, i);
		
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		String pre = clsProperties.addDot(poPrefix);
		moSplitterFactor = new HashMap<String, Double>();
		
		int num = poProp.getPropertyInt(pre+P_NUM_SPLIFACTOR);
		for (int i=0; i<num; i++) {
			String oKey = poProp.getProperty(pre+i+"."+P_SPLITFACTORLABEL);
			Double oValue = poProp.getPropertyDouble(pre+i+"."+P_SPLITFACTORVALUE);
			moSplitterFactor.put(oKey, oValue);
		}		
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text ="";
		
		text += toText.listToTEXT("moLibidoCandidates_IN", moLibidoCandidates_IN);	
		text += toText.listToTEXT("moHomoestasisCandidates_IN", moHomoestasisCandidates_IN);	
		text += toText.listToTEXT("moDriveCandidates_OUT", moDriveCandidates_OUT);
				
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {

		moDriveCandidates_OUT = new ArrayList<clsDriveMeshOLD>(); 

		// first we do some magic to the homeostatic drives
		for(clsPair<clsPair<clsDriveMeshOLD, clsDriveDemand>, clsPair<clsDriveMeshOLD, clsDriveDemand>> oEntry : moHomoestasisCandidates_IN){
			double rFactor = 0.5; //default value, the real values are taken from the config in the next loop
			try {
				for (Map.Entry<String, Double> oSF:moSplitterFactor.entrySet()) {
					if (oEntry.a.a.toString().contains(oSF.getKey())) {
						rFactor = oSF.getValue();
						break;
					}
				}
			} catch (java.lang.Exception e) {
				//do nothing
			}			
			//RL:
			//for a constant increase of the affect values, the following function is implemented:
			//1.: life-instinct increases faster than death-instinct
			//2.: life-instinct reaches maximum (death-instinct at 50%) and decreases
			//3.: death-instinct reaches maximum (--> should result in deatch)
			clsPair<Double, Double> oSplitResult = clsDriveValueSplitter.calc(oEntry.a.b.getTension(), oEntry.b.b.getTension(), 
					eDriveValueSplitter.ADVANCED, rFactor); 
			
			//set the calculated affects into the entry set of the loop, actually pleasure is not right here, this value is quota of affect not pleasure
			double oLifeAffect  = oSplitResult.a;
			double oDeathAffect = oSplitResult.b;
			oEntry.a.a.setPleasure(oLifeAffect); 
			oEntry.b.a.setPleasure(oDeathAffect); 
			
			//and add it to the outgoing list as two separate entries for life/death
			moDriveCandidates_OUT.add(oEntry.a.a); 
			moDriveCandidates_OUT.add(oEntry.b.a); 
		}
		
		//now some love for the libido drives
		for (clsPair< clsTriple<clsDriveMeshOLD,clsDriveDemand,Double>, clsTriple<clsDriveMeshOLD,clsDriveDemand,Double> > oEntry:moLibidoCandidates_IN) {
			double rFactor = 0.5; //default value, the real values are taken from the config
			try {
				for (Map.Entry<String, Double> oSF:moSplitterFactor.entrySet()) {
					if (oEntry.a.a.toString().contains(oSF.getKey())) {
						rFactor = oSF.getValue();
						break;
					}
				}
			} catch (java.lang.Exception e) {
				//do nothing
			}
			
			//do the splitting math
			clsPair<Double, Double> oSplitResult = clsDriveValueSplitter.calc(oEntry.a.b.getTension(), oEntry.b.b.getTension(), 
					eDriveValueSplitter.ADVANCED, rFactor); 
			
			//normalize the resultign values
			double oLifeAffect  = normalize( oSplitResult.a * oEntry.a.c );
			double oDeathAffect = normalize( oSplitResult.b * oEntry.b.c );
			
			oEntry.a.a.setPleasure(oLifeAffect); 
			oEntry.b.a.setPleasure(oDeathAffect); 
			
			//add the two pairs to the final list, this mixes the libido to the homeostatic ones
			moDriveCandidates_OUT.add(oEntry.a.a); 
			moDriveCandidates_OUT.add(oEntry.b.a);

		}
		
		//FIXME CM: This is a hack function by AW. Please remove it as soon as there is some meaningful SLEEP
		//tempJACKBAUERWASHERE(moDriveCandidates_OUT);
		
		//TODO here the actual pleasure gained has to be stored in Dt4
		//moPleasureStorage.receive_D4_1(xxx);
	}
	
	/**
	 * Guarantees that the provided scalar value r is within the range -1<r<1.
	 *
	 * @since 14.07.2011 11:53:36
	 *
	 * @param r
	 * @return r
	 */
	private double normalize(double r) {
		if (r>1) {return 1;}
		else if (r<-1) {return -1;}
		else {return r;}
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
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
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I4_1(moDriveCandidates_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 02.05.2011, 15:48:45
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
	 * 02.05.2011, 15:48:45
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
	 * 02.05.2011, 15:48:45
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
	 * 02.05.2011, 15:48:45
	 * 
	 * @see pa._v38.modules.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "F48 combines Libido and homeostatic drive candidates, calculates the first quota of effect based on a splitter mechanism and outputs the result to a list of drive candidates.";
	}
	
	/**
	 * This is a temporary function to correct the problem with the short sleeps.
	 * 
	 * (wendt)
	 *
	 * @since 13.02.2012 20:18:12
	 *
	 * @param poDriveCandidatesOut
	 */
	private void tempJACKBAUERWASHERE(ArrayList<clsDriveMeshOLD> poDriveCandidatesOut) {
		double rUpperThreshold = 0.9;
		double rLowerThreshold = 0.1;
		
		for (clsDriveMeshOLD oDM : poDriveCandidatesOut) {
			if (oDM.getMoContent().equals("SLEEP")) {
				if (oDM.getPleasure()>=rUpperThreshold) {
					rJACKBAUERWASHERE = true;
				} else if (oDM.getPleasure()< rLowerThreshold) {
						rJACKBAUERWASHERE=false;
				} else if (rJACKBAUERWASHERE==true) {
					oDM.setPleasure(1.0);
				} 
				
				break;
			}
			
		}
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:57:33
	 * 
	 * @see pa._v38.interfaces.modules.I4_1_send#send_I4_1(java.util.ArrayList)
	 */
	@Override
	public void send_I4_1(ArrayList<clsDriveMeshOLD> poDriveCandidates) {
		((I4_1_receive)moModuleList.get(57)).receive_I4_1(poDriveCandidates);
		putInterfaceData(I4_1_send.class, poDriveCandidates);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:57:33
	 * 
	 * @see pa._v38.interfaces.modules.I3_4_receive#receive_I3_4(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I3_4(
			ArrayList<clsPair<clsPair<clsDriveMeshOLD, clsDriveDemand>, clsPair<clsDriveMeshOLD, clsDriveDemand>>> poDriveCandidates) {
		moHomoestasisCandidates_IN = (ArrayList<clsPair<clsPair<clsDriveMeshOLD, clsDriveDemand>, clsPair<clsDriveMeshOLD, clsDriveDemand>>>) deepCopy(poDriveCandidates);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 04.05.2011, 08:57:33
	 * 
	 * @see pa._v38.interfaces.modules.I3_3_receive#receive_I3_3(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I3_3(
			ArrayList<clsPair<clsTriple<clsDriveMeshOLD, clsDriveDemand, Double>, clsTriple<clsDriveMeshOLD, clsDriveDemand, Double>>> poDriveCandidates) {
		moLibidoCandidates_IN = (ArrayList<clsPair<clsTriple<clsDriveMeshOLD, clsDriveDemand, Double>, clsTriple<clsDriveMeshOLD, clsDriveDemand, Double>>>) deepCopy(poDriveCandidates);
	}

}
