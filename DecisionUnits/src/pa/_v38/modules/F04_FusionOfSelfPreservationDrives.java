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
import pa._v38.tools.clsQuadruppel;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I3_2_receive;
import pa._v38.interfaces.modules.I3_4_receive;
import pa._v38.interfaces.modules.I3_4_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsDriveDemand;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import config.clsProperties;

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
	
	private ArrayList< clsPair< clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand> > > moDriveCandidates; 
	private ArrayList< clsPair< clsDriveMesh, clsDriveDemand> > moHomeostaticDriveDemands;
	private ArrayList< clsPair< clsPair<String, String>, clsPair<String, String> > > moDriveOfOppositePairs;
	
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
		fillOppositePairs();
	}
	
	private void fillOppositePairs() {
		moDriveOfOppositePairs = new ArrayList<clsPair<clsPair<String,String>,clsPair<String,String>>>();
		
		ArrayList<clsQuadruppel<String, String, String, String>> oStaticList = new ArrayList<clsQuadruppel<String,String,String,String>>();
		oStaticList.add(new clsQuadruppel<String, String, String, String>("LIFE","NOURISH","DEATH","BITE"));
		oStaticList.add(new clsQuadruppel<String, String, String, String>("LIFE","RELAX","DEATH","SLEEP"));
		oStaticList.add(new clsQuadruppel<String, String, String, String>("LIFE","REPRESS","DEATH","DEPOSIT"));
		
		for (clsQuadruppel<String, String, String, String> oEntry:oStaticList) {
			clsPair<String, String> oLeft = new clsPair<String, String>(oEntry.a, oEntry.b);
			clsPair<String, String> oRight = new clsPair<String, String>(oEntry.c, oEntry.d);
			clsPair<clsPair<String,String>,clsPair<String,String>> oPair = new clsPair<clsPair<String,String>,clsPair<String,String>>(oLeft, oRight);
			moDriveOfOppositePairs.add(oPair);
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
		
		text += toText.listToTEXT("moDriveCandidate", moDriveCandidates);
		text += toText.listToTEXT("moHomeostaticDriveDemands",moHomeostaticDriveDemands);
		text += toText.listToTEXT("moDriveOfOppositePairs", moDriveOfOppositePairs);
		
		return text;
	}
	
	public static clsProperties getDefaultProperties(String poPrefix) {
		String pre = clsProperties.addDot(poPrefix);
		
		clsProperties oProp = new clsProperties();
		oProp.setProperty(pre+P_PROCESS_IMPLEMENTATION_STAGE, eImplementationStage.BASIC.toString());
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsProperties poProp) {
		//String pre = clsProperties.addDot(poPrefix);
		
		//test //neg = shove it to agressive, pos value = shove it to libidoneus, value is in percent (0.1 = +10%)
		//Personality_Content_Factor = -0.1;
	
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
	public void receive_I3_2(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > poHomeostaticDriveDemands) {
		moHomeostaticDriveDemands = (ArrayList< clsPair<clsDriveMesh, clsDriveDemand> >) deepCopy(poHomeostaticDriveDemands); 
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
		moDriveCandidates = new ArrayList<clsPair<clsPair<clsDriveMesh,clsDriveDemand>,clsPair<clsDriveMesh,clsDriveDemand>>>();
		
		for (clsPair< clsPair<String, String>, clsPair<String, String> > oDOOP:moDriveOfOppositePairs) {
			clsPair<clsDriveMesh, clsDriveDemand> oA = getEntry(oDOOP.a);
			clsPair<clsDriveMesh, clsDriveDemand> oB = getEntry(oDOOP.b);
			
			
			
			if (oA != null && oB != null) {
				clsPair< clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand> > oEntry = 
					new clsPair<clsPair<clsDriveMesh,clsDriveDemand>, clsPair<clsDriveMesh,clsDriveDemand>>(oA, oB);
					
					//chenge the agressive/lib content due to personaliyt
					if(Personality_Content_Factor != 0)
						oEntry = changeContentByFactor(oEntry);
				
					moDriveCandidates.add(oEntry); 
			}
		}
	}
	
	private clsPair< clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand> > changeContentByFactor(clsPair< clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand> > oEntry){
		
		if(Personality_Content_Factor <0) // more agressive
		{
			if(oEntry.a.a.getMoContentType() == "DEATH")
			{
				oEntry.b.b.setTension( oEntry.b.b.getTension()-(oEntry.b.b.getTension()*Personality_Content_Factor*-1) );
				oEntry.a.b.setTension( oEntry.a.b.getTension()+(oEntry.a.b.getTension()*Personality_Content_Factor*-1) );
			}
			else if (oEntry.a.a.getMoContentType() == "LIFE")
			{
				oEntry.b.b.setTension( oEntry.b.b.getTension()+(oEntry.b.b.getTension()*Personality_Content_Factor*-1) );
				oEntry.a.b.setTension( oEntry.a.b.getTension()-(oEntry.a.b.getTension()*Personality_Content_Factor*-1) );
			}
			else
			{
			 //new content type?
			}
		}
		else //more libido
		{
			if(oEntry.a.a.getMoContentType() == "DEATH")
			{
				oEntry.b.b.setTension( oEntry.b.b.getTension()+(oEntry.b.b.getTension()*Personality_Content_Factor) );
				oEntry.a.b.setTension( oEntry.a.b.getTension()-(oEntry.a.b.getTension()*Personality_Content_Factor) );
			}
			else if (oEntry.a.a.getMoContentType() == "LIFE")
			{
				oEntry.b.b.setTension( oEntry.b.b.getTension()-(oEntry.b.b.getTension()*Personality_Content_Factor) );
				oEntry.a.b.setTension( oEntry.a.b.getTension()+(oEntry.a.b.getTension()*Personality_Content_Factor) );
			}
			else
			{
			 //new content type?
			}
		}
		
		return oEntry;
	}
	
	private clsPair<clsDriveMesh, clsDriveDemand> getEntry(clsPair<String, String> poId) {
		clsPair<clsDriveMesh, clsDriveDemand> oResult =  null;
		String oContentType = poId.a; 
		String oContext = poId.b;
		
		for (clsPair<clsDriveMesh, clsDriveDemand> oHDD:moHomeostaticDriveDemands) {
			clsDriveMesh oTemp = oHDD.a;
			if ( oTemp.getMoContent().equals(oContext) && oTemp.getMoContentType().equals(oContentType)) {
				oResult = oHDD;
				break;
			}
		}
		
		return oResult;
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
		send_I3_4(moDriveCandidates);	
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
	public void send_I3_4(ArrayList<clsPair<clsPair<clsDriveMesh, clsDriveDemand>, clsPair<clsDriveMesh, clsDriveDemand>>> poDriveCandidate) {
		((I3_4_receive)moModuleList.get(48)).receive_I3_4(poDriveCandidate);
		putInterfaceData(I3_4_send.class, poDriveCandidate);
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
