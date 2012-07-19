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
import pa._v38.interfaces.modules.I3_2_receive;
import pa._v38.interfaces.modules.I3_4_receive;
import pa._v38.interfaces.modules.I3_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContentType;
import config.clsProperties;
import du.enums.pa.eDriveComponent;
import du.enums.pa.ePartialDrive;

/**
 * The libidinous and aggressive drives are combined to pair of opposites. For each bodily need, such a pair exists. 
 * 
 * @author muchitsch
 * 11.08.2009, 13:40:06
 * 
 */
public class F04_FusionOfSelfPreservationDrives extends clsModuleBase implements I3_2_receive, I3_4_send {
	public static final String P_MODULENUMBER = "04";
	
	private double Personality_Content_Factor = 0; //neg = shove it to agressive, pos value = shove it to libidoneus, value is in percent (0.1 = +10%)
	
//	private ArrayList< clsPair< clsPair<clsDriveMeshOLD, clsDriveDemand>, clsPair<clsDriveMeshOLD, clsDriveDemand> > > moDriveCandidates; 
//	private ArrayList< clsPair< clsDriveMeshOLD, clsDriveDemand> > moHomeostaticDriveDemands;
//	private ArrayList< clsPair< clsPair<String, String>, clsPair<String, String> > > moDriveOfOppositePairs;
	
	//
	private ArrayList<clsDriveMesh> moHomeostaticDriveCandidates_IN;
	private ArrayList <clsPair<clsDriveMesh,clsDriveMesh>> moHomeostaticDriveComponents_OUT;
	
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
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
//		fillOppositePairs();
	}
	
//	private void fillOppositePairs() {
//		moDriveOfOppositePairs = new ArrayList<clsPair<clsPair<String,String>,clsPair<String,String>>>();
//		
//		ArrayList<clsQuadruppel<String, String, String, String>> oStaticList = new ArrayList<clsQuadruppel<String,String,String,String>>();
//		oStaticList.add(new clsQuadruppel<String, String, String, String>("LIFE","NOURISH","DEATH","BITE"));
//		oStaticList.add(new clsQuadruppel<String, String, String, String>("LIFE","RELAX","DEATH","SLEEP"));
//		oStaticList.add(new clsQuadruppel<String, String, String, String>("LIFE","REPRESS","DEATH","DEPOSIT"));
//		
//		for (clsQuadruppel<String, String, String, String> oEntry:oStaticList) {
//			clsPair<String, String> oLeft = new clsPair<String, String>(oEntry.a, oEntry.b);
//			clsPair<String, String> oRight = new clsPair<String, String>(oEntry.c, oEntry.d);
//			clsPair<clsPair<String,String>,clsPair<String,String>> oPair = new clsPair<clsPair<String,String>,clsPair<String,String>>(oLeft, oRight);
//			moDriveOfOppositePairs.add(oPair);
//		}
//	}
	
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
			rAgrTension = oEntry.getQuotaOfAffect() /2;
			rLibTension = oEntry.getQuotaOfAffect() /2;
			
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
			agressiveDM.setDriveComponent(eDriveComponent.LIBIDINOUS);
			libidoneusDM.setQuotaOfAffect(rLibTension);
			
			//add the components to the new list as PAIR(Agr,Lib)
			oTempPair = new clsPair<clsDriveMesh, clsDriveMesh>(agressiveDM, libidoneusDM); 
			moHomeostaticDriveComponents_OUT.add(oTempPair);

		}

	}
	
	//TODO CM this need a new implementation
//	private clsPair< clsPair<clsDriveMeshOLD, clsDriveDemand>, clsPair<clsDriveMeshOLD, clsDriveDemand> > changeContentByFactor(clsPair< clsPair<clsDriveMeshOLD, clsDriveDemand>, clsPair<clsDriveMeshOLD, clsDriveDemand> > oEntry){
//		
//		if(Personality_Content_Factor <0) // more agressive
//		{
//			if(oEntry.a.a.getMoContentType() == eContentType.DEATH)
//			{
//				oEntry.b.b.setTension( oEntry.b.b.getTension()-(oEntry.b.b.getTension()*Personality_Content_Factor*-1) );
//				oEntry.a.b.setTension( oEntry.a.b.getTension()+(oEntry.a.b.getTension()*Personality_Content_Factor*-1) );
//			}
//			else if (oEntry.a.a.getMoContentType() == eContentType.LIFE)
//			{
//				oEntry.b.b.setTension( oEntry.b.b.getTension()+(oEntry.b.b.getTension()*Personality_Content_Factor*-1) );
//				oEntry.a.b.setTension( oEntry.a.b.getTension()-(oEntry.a.b.getTension()*Personality_Content_Factor*-1) );
//			}
//			else
//			{
//			 //new content type?
//			}
//		}
//		else //more libido
//		{
//			if(oEntry.a.a.getMoContentType() == eContentType.DEATH)
//			{
//				oEntry.b.b.setTension( oEntry.b.b.getTension()+(oEntry.b.b.getTension()*Personality_Content_Factor) );
//				oEntry.a.b.setTension( oEntry.a.b.getTension()-(oEntry.a.b.getTension()*Personality_Content_Factor) );
//			}
//			else if (oEntry.a.a.getMoContentType() == eContentType.LIFE)
//			{
//				oEntry.b.b.setTension( oEntry.b.b.getTension()-(oEntry.b.b.getTension()*Personality_Content_Factor) );
//				oEntry.a.b.setTension( oEntry.a.b.getTension()+(oEntry.a.b.getTension()*Personality_Content_Factor) );
//			}
//			else
//			{
//			 //new content type?
//			}
//		}
//		
//		return oEntry;
//	}
	
//	private clsPair<clsDriveMeshOLD, clsDriveDemand> getEntry(clsPair<String, String> poId) {
//		clsPair<clsDriveMeshOLD, clsDriveDemand> oResult =  null;
//		String oContentType = poId.a; 
//		String oContext = poId.b;
//		
//		for (clsPair<clsDriveMeshOLD, clsDriveDemand> oHDD:moHomeostaticDriveDemands) {
//			clsDriveMeshOLD oTemp = oHDD.a;
//			if ( oTemp.getMoContent().equals(oContext) && oTemp.getMoContentType().equals(oContentType)) {
//				oResult = oHDD;
//				break;
//			}
//		}
//		
//		return oResult;
//	}
	

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
		
		//the old code:

//		moDriveCandidates = new ArrayList<clsPair<clsPair<clsDriveMeshOLD,clsDriveDemand>,clsPair<clsDriveMeshOLD,clsDriveDemand>>>();
//		
//		for (clsPair< clsPair<String, String>, clsPair<String, String> > oDOOP:moDriveOfOppositePairs) {
//			clsPair<clsDriveMeshOLD, clsDriveDemand> oA = getEntry(oDOOP.a);
//			clsPair<clsDriveMeshOLD, clsDriveDemand> oB = getEntry(oDOOP.b);
//			
//			
//			
//			if (oA != null && oB != null) {
//				clsPair< clsPair<clsDriveMeshOLD, clsDriveDemand>, clsPair<clsDriveMeshOLD, clsDriveDemand> > oEntry = 
//					new clsPair<clsPair<clsDriveMeshOLD,clsDriveDemand>, clsPair<clsDriveMeshOLD,clsDriveDemand>>(oA, oB);
//					
//					//chenge the agressive/lib content due to personaliyt
//					if(Personality_Content_Factor != 0)
//						oEntry = changeContentByFactor(oEntry);
//				
//					moDriveCandidates.add(oEntry); 
//			}
//		}
		
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
			oDriveComponent.associateActualDriveSource(poDriveCandidate.getActualDriveSource(), 1.0);

			oDriveComponent.associateActualBodyOrifice(poDriveCandidate.getActualBodyOrifice(), 1.0);
			
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
}
