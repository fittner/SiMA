/**
 * E3_GenerationOfDrives.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:19:04
 */
package pa._v30.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;
import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import pa._v30.tools.toHtml;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.modules.I1_2_receive;
import pa._v30.interfaces.modules.I1_3_receive;
import pa._v30.interfaces.modules.I1_3_send;
import pa._v30.memorymgmt.clsKnowledgeBaseHandler;
import pa._v30.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsDriveDemand;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsThingPresentation;
import pa._v30.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v30.memorymgmt.enums.eDataType;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 12:19:04
 * 
 */
public class E03_GenerationOfSelfPreservationDrives extends clsModuleBaseKB implements I1_2_receive, I1_3_send {
	public static final String P_MODULENUMBER = "03";
	public static String moDriveObjectType = "DriveObject";
	
	private HashMap<String, Double> moHomeostasisSymbols; 
	
	private ArrayList< clsTripple<clsDriveMesh, String, ArrayList<String>> > moDriveTemplates;
	private ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > moDrives;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 15:56:22
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception 
	 */
	public E03_GenerationOfSelfPreservationDrives(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, 
			SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		applyProperties(poPrefix, poProp);	
		
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
	 * @see pa.modules._v30.clsModuleBase#stateToHTML()
	 */
	@Override
	public String stateToHTML() {
		String html ="";
		
		html += toHtml.mapToHTML("moHomeostasisSymbols",moHomeostasisSymbols);		
		html += toHtml.listToHTML("moDriveTemplates", moDriveTemplates);		
		html += toHtml.listToHTML("moDrives", moDrives);		
		html += toHtml.valueToHTML("moKnowledgeBaseHandler", moKnowledgeBaseHandler);		
		
		return html;
	}
		
	private ArrayList< clsTripple<clsDriveMesh, String, ArrayList<String>> > createDriveMeshes() {
		ArrayList< clsTripple<clsDriveMesh, String, ArrayList<String>> > oDrives = new ArrayList< clsTripple<clsDriveMesh, String, ArrayList<String>> >();
		
		oDrives.add( createDrives("LIFE", "NOURISH", "BLOODSUGAR") );
		oDrives.add( createDrives("DEATH", "BITE", "BLOODSUGAR") );
		
		oDrives.add( createDrives("LIFE", "RELAX", "STAMINA") );
		oDrives.add( createDrives("DEATH", "SLEEP", "STAMINA") );
		
		oDrives.add( createDrives("LIFE", "REPRESS", "STOMACHTENSION") );
		oDrives.add( createDrives("DEATH", "DEPOSIT", "STOMACHTENSION") );
		
		return oDrives;
	}
	
	private clsTripple<clsDriveMesh, String, ArrayList<String>> createDrives(String poContentType, String poContext, String poSource) {
		clsDriveMesh oDriveMesh = createDriveMesh(poContentType, poContext);
		ArrayList<String> oObjects = getDriveSources(poContext, oDriveMesh);
		
		return new clsTripple<clsDriveMesh, String, ArrayList<String>>(oDriveMesh, poSource, oObjects);
	}
/*	
	private ArrayList<String> getDriveSources(String poContext, clsDriveMesh poDriveMesh) {
		ArrayList<String> oRes = new ArrayList<String>();
		
		//TODO (ZEILINGER): make the damn search work!!!
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = 
			new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
		search(eDataType.UNDEFINED, new ArrayList<clsDriveMesh>(Arrays.asList(poDriveMesh)), oSearchResult ); 
		
		//TD 2011/04/20: workaround for now:
		if (poDriveMesh.getMoContent().equals("NOURISH")) {
			oRes.add("CAKE");
			oRes.add("CARROT");
		} else if (poDriveMesh.getMoContent().equals("BITE")) {
			oRes.add("CAKE");
			oRes.add("CARROT");			
		}
		
		return oRes;
	}
*/	
	private ArrayList<String> getDriveSources(String poContext, clsDriveMesh poDriveMesh) {
        
        double nIntensity = 0.0; 
        ArrayList<String> oRes = new ArrayList<String>();
        ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>(); 
        
        search(eDataType.TPM, new ArrayList<clsDriveMesh>(Arrays.asList(poDriveMesh)), oSearchResult ); 
        
        for(ArrayList<clsPair<Double,clsDataStructureContainer>> oPatternResults : oSearchResult ){
                 for(clsPair<Double, clsDataStructureContainer> oMatch : oPatternResults ){
                           for(clsAssociation oAssociation : oMatch.b.getMoAssociatedDataStructures()){
                                    
                                    nIntensity = ((clsDriveMesh)oAssociation.getMoAssociationElementA()).getPleasure(); 
                                    
                                    if(nIntensity > 0){
                                             oRes.add(((clsThingPresentationMesh) oAssociation.getMoAssociationElementB()).getMoContent()); 
                                    }
                           }
                 }
        }
                           
        return oRes;
	}
	
	private clsDriveMesh createDriveMesh(String poContentType, String poContext) {
		clsThingPresentation oDataStructure = (clsThingPresentation)clsDataStructureGenerator.generateDataStructure( eDataType.TP, new clsPair<String, Object>(poContentType, poContext) );
		ArrayList<Object> oContent = new ArrayList<Object>( Arrays.asList(oDataStructure) );
		
		clsDriveMesh oRetVal = (pa._v30.memorymgmt.datatypes.clsDriveMesh)clsDataStructureGenerator.generateDataStructure( 
				eDataType.DM, new clsTripple<String, Object, Object>(poContentType, oContent, poContext)
				);
		
		return oRetVal;
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
	public void receive_I1_2(HashMap<String, Double> poHomeostasisSymbols) {
		moHomeostasisSymbols = (HashMap<String, Double>)deepCopy(poHomeostasisSymbols);
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
		moDriveTemplates = createDriveMeshes();
		moDrives = new ArrayList< clsPair<clsDriveMesh,clsDriveDemand> >();
		
		for (clsTripple<clsDriveMesh, String, ArrayList<String>> oDT: moDriveTemplates) {
			clsDriveDemand oDD = getDriveDemand(oDT);
			moDrives.add( new clsPair<clsDriveMesh, clsDriveDemand>(oDT.a, oDD) );
		}
	}
	
	private double calculateNormalizedValue(double rValue, String poSource) {
		double rResult = rValue;
		double rMaxValue = 1;
		
		if (poSource.equals("BLOODSUGAR")) {
			rMaxValue = 1.0;
			rResult = (rMaxValue-rValue)/rMaxValue;
		} else if (poSource.equals("STAMINA")) {
			rMaxValue = 1.0;
			rResult = (rMaxValue-rValue)/rMaxValue;
		} else if (poSource.equals("STOMACHTENSION")) {
			//rValue *= 0.1;
			rResult = rValue;
		}
		
		
		
		return rResult;
	}
	
	private clsDriveDemand getDriveDemand(clsTripple<clsDriveMesh, String, ArrayList<String>> poDT) {
		double rDemand = 0.0;
		
		String oSource = poDT.b;

		if (moHomeostasisSymbols.containsKey(oSource)) {
			double rValue = moHomeostasisSymbols.get(oSource);
			rDemand = calculateNormalizedValue(rValue, oSource);
		}
		
		clsDriveDemand oDemand = (clsDriveDemand)clsDataStructureGenerator.generateDataStructure(eDataType.DRIVEDEMAND, 
				new clsPair<String,Object>(eDataType.DRIVEDEMAND.toString(), rDemand));
		
		return oDemand;
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
		send_I1_3(moDrives);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:44:46
	 * 
	 * @see pa.interfaces.send.I1_3_send#send_I1_3(java.util.ArrayList)
	 */
	@Override
	public void send_I1_3(ArrayList< clsPair<clsDriveMesh, clsDriveDemand> > poHomeostaticDriveDemands) {
		((I1_3_receive)moModuleList.get(4)).receive_I1_3(poHomeostaticDriveDemands);
		putInterfaceData(I1_3_send.class, poHomeostaticDriveDemands);
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
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
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
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
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
	 * @see pa.modules._v30.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "The neurosymbolic representation of bodily needs are converted to memory traces representing the corresponding drives. At this stage, such a memory trace contains drive source, aim of drive, and drive object (cp Section ?). The quota of affect will be added later. For each bodily need, two drives are generated: a libidinous and an aggressive one. ";
	}
}