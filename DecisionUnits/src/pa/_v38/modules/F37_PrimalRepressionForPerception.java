/**
 * E37_PrimalRepressionForPerception.java: DecisionUnits - pa.modules._v38
 * 
 * @author deutsch
 * 03.03.2011, 15:22:10
 */
package pa._v38.modules;

import java.util.ArrayList;

import java.util.Arrays;
import java.util.HashMap;
import java.util.SortedMap;

import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I5_7_receive;
import pa._v38.interfaces.modules.I5_7_send;
import pa._v38.interfaces.modules.I5_6_receive;

import pa._v38.memorymgmt.datahandler.clsDataStructureConverter;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.enums.eDataType;


import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;
import pa._v38.storage.clsBlockedContentStorage;
import config.clsBWProperties;

/**
 * DOCUMENT (HINTERLEITNER) - insert description 
 * 
 * @author deutsch
 * 03.03.2011, 15:22:10
 * 
 */
public class F37_PrimalRepressionForPerception extends clsModuleBase implements I5_6_receive, I5_7_send  {
	public static final String P_MODULENUMBER = "37";
	
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	private clsPrimaryDataStructureContainer moEvaluatedEnvironment_OUT;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;


	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moAttachedRepressed_Output;
	private ArrayList< clsTripple<String, String, ArrayList<Double> >> moPrimalRepressionMemory;
	
	 
		
	/**
	 * DOCUMENT (HINTERLEITNER) - This function attaches the thing presentations to the repressed content and makes an association. The result is a constructed perception with associated repressed content.
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:20:58
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F37_PrimalRepressionForPerception(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, 
			ArrayList<Object>> poInterfaceData, clsBlockedContentStorage poBlockedContentStorage)
		throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);	
		fillPrimalRepressionMemory();
	}
	
	private void fillPrimalRepressionMemory() {
		moPrimalRepressionMemory = new ArrayList<clsTripple<String,String,ArrayList<Double>>>();
		
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_ORAL", new ArrayList<Double>(Arrays.asList(0.1, 0.2, 0.3, 0.4)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_ANAL", new ArrayList<Double>(Arrays.asList(0.4, 0.3, 0.2, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_PHALLIC", new ArrayList<Double>(Arrays.asList(0.1, 0.1, 0.1, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"LIFE", "LIBIDINOUS_GENITAL", new ArrayList<Double>(Arrays.asList(0.1, 0.5, 0.1, 0.2)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_ORAL", new ArrayList<Double>(Arrays.asList(0.8, 0.01, 0.2, 0.1)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_ANAL", new ArrayList<Double>(Arrays.asList(0.1, 0.4, 0.1, 0.2)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_PHALLIC", new ArrayList<Double>(Arrays.asList(0.01, 0.01, 0.01, 0.6)) ) );
		moPrimalRepressionMemory.add( new clsTripple<String,String,ArrayList<Double>>(
				"DEATH", "AGGRESSIVE_GENITAL", new ArrayList<Double>(Arrays.asList(0.7, 0.7, 0.1, 0.9)) ) );
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
		
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.valueToTEXT("moEvaluatedEnvironment_OUT", moEvaluatedEnvironment_OUT);
		text += toText.listToTEXT("moPrimalRepressionMemory", moPrimalRepressionMemory);
		
		return text;
	}	
	
	@Override
	protected void setProcessType() {mnProcessType = eProcessType.PRIMARY;}
	@Override
	protected void setPsychicInstances() {mnPsychicInstances = ePsychicInstances.ID;}
	@Override
	protected void setModuleNumber() {mnModuleNumber = Integer.parseInt(P_MODULENUMBER);}

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
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_basic()
	 */
	@Override
	protected void process_basic() {
	
	moEvaluatedEnvironment_OUT = moEnvironmentalPerception_IN;
   
	matchRepressedContent(moEnvironmentalPerception_IN, moAttachedRepressed_Output);

	//Pass memories forward
	moAssociatedMemories_OUT = moAssociatedMemories_IN;
	
}

/**
 * DOCUMENT (hinterleitner) - function adds a triple of primal repressions (oEntryPrimRep) to each incoming environmental repression 
 * 
 * @since 25.06.2011 14:57:36
 *
 */
private void matchRepressedContent(clsPrimaryDataStructureContainer moConstrPerc, ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moAttachedRepressed_Output) 
{
    moConstrPerc = moEnvironmentalPerception_IN; //Constructed Perception enters from the Interface
    
    if (moConstrPerc != null)  
    {
       
	//System.out.println(moPrimalRepressionMemory);  // Urverdrängtes
    //moEvaluatedEnvironment_OUT; //:TI::-1:TI:CONSTRUCTED_PERCEPTION
    	
		moAttachedRepressed_Output = new ArrayList<clsPair<clsPrimaryDataStructureContainer,clsDriveMesh>>();
		
		for (clsPrimaryDataStructureContainer oPDSC:clsDataStructureConverter.convertTIContToTPMCont(moEnvironmentalPerception_IN)) 
		{
	
			clsPair<clsPrimaryDataStructureContainer,clsDriveMesh> oEntryPrimRep = 
				new clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>(
						oPDSC, 
						new clsDriveMesh(
								new clsTripple<Integer, eDataType, String>(0, eDataType.UNDEFINED, "c"), 
								0, 
								new double[]{0.1,0.5,0.6,0.3}, 
								null, null)
						);

			moAttachedRepressed_Output.add(oEntryPrimRep);
			//System.out.println(moAttachedRepressed_Output);
			//moEvaluatedEnvironment_OUT = ConvertToTIContainer(moAttachedRepressed_Output);
		
		}    			
	
    }		
}	
	/**
 * DOCUMENT (hinterleitner) - insert description
 *
 * @since 29.06.2011 14:26:30
 *
 * @param moAttachedRepressed_Output2
 * @return
 */
private clsPrimaryDataStructureContainer ConvertToTIContainer(
		ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moAttachedRepressed_Output2) {
	// TODO (hinterleitner) - Auto-generated method stub
	return null;
}

	/*private clsPrimaryDataStructureContainer ConvertToTIContainer(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> oInput) {
	ArrayList<clsPrimaryDataStructureContainer> oMergedArray = new ArrayList<clsPrimaryDataStructureContainer>();
	for (clsPair<clsPrimaryDataStructureContainer, clsDriveMesh> oPair : oInput) {
		clsPrimaryDataStructureContainer oNewSingleContainer = new clsPrimaryDataStructureContainer(oPair.a.getMoDataStructure(),oPair.a.getMoAssociatedDataStructures());
		clsTripple<Integer, eDataType, String> oIdentifyer = new clsTripple<Integer, eDataType, String>(-1, eDataType.ASSOCIATIONDM, eDataType.ASSOCIATIONDM.toString());
		clsAssociationDriveMesh oDriveAss = new clsAssociationDriveMesh(oIdentifyer, oPair.b, (clsPrimaryDataStructure)oPair.a.getMoDataStructure());
		
		ArrayList<clsAssociation> oNewAssList = oNewSingleContainer.getMoAssociatedDataStructures();
		oNewAssList.add(oDriveAss);
		
		oNewSingleContainer.setMoAssociatedDataStructures(oNewAssList);
		oMergedArray.add(oNewSingleContainer);
	}
	
	clsPrimaryDataStructureContainer oRetVal = clsDataStructureConverter.convertTPMContToTICont(oMergedArray);
	
	return oRetVal;
}*/

	//check whether the format is equal to output format moEvaluatedEnvironment_OUT





	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (HINTERLEITNER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (HINTERLEITNER) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 15:22:10
	 * 
	 * @see pa.modules._v38.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_7(moEvaluatedEnvironment_OUT, moAssociatedMemories_OUT);

	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:22:14
	 * 
	 * @see pa.interfaces.send._v38.I2_14_send#send_I2_14(java.util.ArrayList)
	 */
	@Override
	public void send_I5_7(clsPrimaryDataStructureContainer poEnvironmentalTP, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		
		((I5_7_receive)moModuleList.get(35)).receive_I5_7(poEnvironmentalTP, poAssociatedMemories);	//Associated memories only for perception
		((I5_7_receive)moModuleList.get(57)).receive_I5_7(poEnvironmentalTP, poAssociatedMemories);
		
		putInterfaceData(I5_7_send.class, poEnvironmentalTP, poAssociatedMemories);
	}
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:22:14
	 * 
	 * @see pa.interfaces.receive._v38.I2_20_receive#receive_I2_20(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_6(clsPrimaryDataStructureContainer poEnvironmentalTP, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		moEnvironmentalPerception_IN = (clsPrimaryDataStructureContainer)deepCopy(poEnvironmentalTP);
		moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
		
	}

	/**
	 * @author zeilinger
	 * 18.03.2011, 15:59:16
	 * 
	 * @return the moEnvironmental_IN
	 */
	public clsPrimaryDataStructureContainer getMoEnvironmental_IN() {
		return moEnvironmentalPerception_IN;
	}
	/**
	 * @author zeilinger
	 * 18.03.2011, 15:59:16
	 * 
	 * @return the moEvaluatedEnvironment_OUT
	 */
	public clsPrimaryDataStructureContainer getMoEvaluatedEnvironment_OUT() {
		return moEvaluatedEnvironment_OUT;
	}
	
	public ArrayList<clsPrimaryDataStructureContainer> getMoAssociatedMemories_OUT() {
		return moAssociatedMemories_OUT;
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
		moDescription = "This function attaches the thing presentations to the repressed content and makes an association. The result is a constructed perception with associated repressed content.";
	}		
}
