/**
 * E21_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:38:29
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.SortedMap;
import java.util.Map;
import config.clsBWProperties;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I5_15_receive;
import pa._v38.interfaces.modules.I6_1_receive;
import pa._v38.interfaces.modules.I6_1_send;
import pa._v38.interfaces.modules.I6_4_receive;
import pa._v38.interfaces.modules.I6_4_send;
import pa._v38.interfaces.modules.I6_9_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datahandler.clsDataStructureConverter;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAffect;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsTemplateImage;
import pa._v38.memorymgmt.datatypes.clsWordPresentation;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (KOHLHAUSER) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:38:29
 * 
 */
public class F21_ConversionToSecondaryProcessForPerception extends clsModuleBaseKB implements 
			I5_15_receive, I6_9_receive, I6_1_send, I6_4_send {
	public static final String P_MODULENUMBER = "21";
	
	//AW 20110522: New inputs
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;
	//AW 20110522: The input below will be used soon, in order to extract expectations.
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	//AW 20110602 New output of the module
	private ArrayList<clsDataStructureContainer> moAssociatedMemoriesSecondary_OUT;
	
	//private ArrayList<clsPrimaryDataStructureContainer> moGrantedPerception_Input; 
	//FIXME HZ: This would require a change in the interfaces!!! => different to the actual definition
	//private ArrayList<clsPair<clsSecondaryDataStructureContainer, clsPair<clsWordPresentation, clsWordPresentation>>> moPerception_Output; 
	private ArrayList<clsSecondaryDataStructureContainer> moPerception_Output; 
	private ArrayList<clsTripple<clsDataStructurePA, ArrayList<clsTemplateImage>, ArrayList<clsPair<clsDriveMesh, clsAffect>>>> moOrderedResult; 
	private HashMap<Integer, clsDriveMesh> moTemporaryDM; 
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:44:38
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F21_ConversionToSecondaryProcessForPerception(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData, clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		
		applyProperties(poPrefix, poProp);
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
		String text = "";
		
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.listToTEXT("moPerception_Output", moPerception_Output);
		text += toText.listToTEXT("moOrderedResult", moOrderedResult);
		text += toText.mapToTEXT("moTemporaryDM", moTemporaryDM);
		text += toText.valueToTEXT("moKnowledgeBaseHandler", moKnowledgeBaseHandler);

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
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:39:18
	 * 
	 * @see pa.interfaces.I2_10#receive_I2_10(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_15(clsPrimaryDataStructureContainer poEnvironmentalPerception, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		moEnvironmentalPerception_IN = (clsPrimaryDataStructureContainer)poEnvironmentalPerception.clone();
		moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:12
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		moTemporaryDM = new HashMap<Integer, clsDriveMesh>(); 
		moPerception_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
		
		defineTemplateImage(); 
		convertToSecondary(); 
		
		//AW 20110602: Added function
		//Processing of associated images
		moAssociatedMemoriesSecondary_OUT = assignWPtoImages(moAssociatedMemories_IN);
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.08.2010, 22:39:28
	 *
	 */
	private void defineTemplateImage() {
		/*HZ 20.08.2010: There are two possibilities of receiving the matching Template images.
		 * 	1. create a TI out of received Information and search in the memory for matching 
		 * 	   TIs
		 * 	   advantage: one TI is created and gets a best match; this match can be further processed 
		 * 				  and mapped to word presentations in E21.
		 * 	   
		 * 	   disadvantage: There exist several disadvantages
		 * 	   				 a. TIs are abstract definitions of "situations"/ they are not defined for 
		 * 					    every object; e. g. the image "entity far right " is only defined for
		 * 					    objects that are of the type entity but not explicitly for the 
		 * 					    object "cake" or "can"
		 * 					 b. Even abstract TIs are defined, in order to match an object and its 
		 * 					    associations (additional TPs, TPMs, or DMs ) additional variations 
		 * 					    would have to be added to the TI.
		 * 					    e. g. if the search parameter is an object that associates a position (far right) and
		 * 					          a drive mesh (extremely nourish), than the TI has to be defined (object at far right is
		 * 							  extremely nourish). This variation would have to be done for every nourish object in any 
		 * 							  position... this would be quiet an overkill
		 * 
		 * 2. Hence the second possibility is to get TIs that abstract certain situations (but not the whole situation); e. g. 
		 *    there exist TIs for the position of objects and there exist separated TIs for the drive evaluation of the object
		 *    and so on. 
		 *    The data structures that are included in the received primary data structure container are used to retrieve
		 *    their associated TIs. The retrieved TIs form a new TI. However the new TI is not merged to a data structure 
		 *    called TI because a. it is an image that is not a real template but more an abstraction of the situation formed
		 *    out of templates and b. will not be present in one of the acts => would be a problem for the planning part. 
		 *    I am sure that the last part is not explained in the way that anyone will understand it... for clarification please
		 *    talk to the programmer or read the code... read the code first.     
		 */
		moOrderedResult = new ArrayList<clsTripple<clsDataStructurePA, ArrayList<clsTemplateImage>, ArrayList<clsPair<clsDriveMesh, clsAffect>>>>(); 
		
		//AW 20110522: Convert from new input
		ArrayList<clsPrimaryDataStructureContainer> moGrantedPerception_Input = clsDataStructureConverter.convertTIContToTPMCont(moEnvironmentalPerception_IN);
		
		for(clsPrimaryDataStructureContainer oContainer : moGrantedPerception_Input){
			ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult 
							= new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
			ArrayList<clsDataStructurePA> oEvaluatedResult = new ArrayList<clsDataStructurePA>(); 
					
			extractPattern(oContainer, oSearchResult);
			oEvaluatedResult = evaluateSearchResult(oContainer.getMoDataStructure(), oSearchResult);
			moOrderedResult.add(orderResult(oContainer.getMoDataStructure(), oEvaluatedResult)); 
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.08.2010, 17:44:55
	 *
	 * @param oEvaluatedResult
	 * @return
	 */
	private clsTripple<clsDataStructurePA, ArrayList<clsTemplateImage>, ArrayList<clsPair<clsDriveMesh, clsAffect>>> orderResult( clsDataStructurePA poDataStructure, ArrayList<clsDataStructurePA> poEvaluatedResult) {
		/*HZ: 21.08.2010: This method may be merged with the method convertToSecondary() in the future.
		 * It "orders" the evaluated result to a tripple of 
		 * - The data structure to which the retrieved TIs are associated
		 * - a list of associated TIs
		 * - and a List of pairs that consist out of the associated drive mesh and its 
		 * 	 affective evaluation (clsAffect)
		 * 
		 * This leads to tripples that can be simplified in the future (maybe?)
		 * In addition the calculation effort is rather high => hence it can be discussed if the ordering
		 * will directly lead to a match with word presentations 
		 * */ 
		clsTripple<clsDataStructurePA, ArrayList<clsTemplateImage>, ArrayList<clsPair<clsDriveMesh, clsAffect>>> oOrderedResult = 
				new clsTripple<clsDataStructurePA, ArrayList<clsTemplateImage>, ArrayList<clsPair<clsDriveMesh, clsAffect>>>(
																							poDataStructure, 
																							new ArrayList<clsTemplateImage>(), 
																							new ArrayList<clsPair<clsDriveMesh, clsAffect>>()); 
		
		for(clsDataStructurePA oEntry : poEvaluatedResult){
						
			if(oEntry instanceof clsTemplateImage){
				oOrderedResult.b.add((clsTemplateImage)oEntry); 
			}
			else if(oEntry instanceof clsAffect){
				oOrderedResult.c.add(new clsPair<clsDriveMesh, clsAffect>(moTemporaryDM.get(poEvaluatedResult.indexOf(oEntry)),(clsAffect)oEntry));
			}
		}
		
		return oOrderedResult;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.08.2010, 13:05:45
	 *
	 * @return
	 */
	private void extractPattern(clsPrimaryDataStructureContainer poContainer, 
			ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> poSearchResult) {
		
		moTemporaryDM.clear();
				
		HashMap <String, ArrayList<clsDataStructurePA>> oTIList = new HashMap <String, ArrayList<clsDataStructurePA>>(); 
		ArrayList<clsPair<eDataType, clsDataStructurePA>> oPattern = new ArrayList<clsPair<eDataType,clsDataStructurePA>>(); 
		
		for(clsAssociation oAssociation : poContainer.getMoAssociatedDataStructures()){
			
			if(oAssociation instanceof clsAssociationAttribute){
				String oCT = oAssociation.getLeafElement().getMoContentType(); 
				
				if(oTIList.containsKey(oCT)){ oTIList.get(oCT).add(oAssociation.getLeafElement());}
				else {oTIList.put(oCT, new ArrayList<clsDataStructurePA>(Arrays.asList(oAssociation.getLeafElement()))); }

			}
			
			if(oAssociation instanceof clsAssociationDriveMesh){
				clsDriveMesh oDM = (clsDriveMesh) oAssociation.getLeafElement(); 
				clsDataStructurePA oAffect = clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<String, Object>(eDataType.AFFECT.name(), oDM.getPleasure())); 
				oPattern.add(new clsPair<eDataType, clsDataStructurePA> (eDataType.AFFECT,  oAffect));
				moTemporaryDM.put(oPattern.size()-1, oDM);
			}
		}
		
		for(Map.Entry<String,  ArrayList<clsDataStructurePA>> oEntry : oTIList.entrySet()){
			clsTripple <String, ArrayList<clsDataStructurePA>, Object> oTIcontent = 
				new clsTripple<String, ArrayList<clsDataStructurePA>, Object>(oEntry.getKey(), oEntry.getValue(), "undefined"); 
			
			clsDataStructurePA oTI = clsDataStructureGenerator.generateDataStructure(eDataType.TI, oTIcontent);
			oPattern.add(new clsPair<eDataType, clsDataStructurePA>(eDataType.UNDEFINED, oTI)); 
		}
		
		for(clsPair<eDataType, clsDataStructurePA> oEntry : oPattern){
			search(oEntry.a, new ArrayList<clsDataStructurePA>(Arrays.asList(oEntry.b)),poSearchResult); 
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.08.2010, 13:15:06
	 *
	 * @param poSearchResult
	 * @param poDataStructure
	 * @return
	 */
	private ArrayList<clsDataStructurePA> evaluateSearchResult( clsDataStructurePA poDataStructure,
									ArrayList<ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult) {
		
		ArrayList<clsDataStructurePA> oEvaluatedResult = new ArrayList<clsDataStructurePA>(); 
		
		for(ArrayList<clsPair<Double, clsDataStructureContainer>> oEntry : poSearchResult){
			clsDataStructurePA oBestMatch = getBestMatch(poDataStructure, oEntry); 
			oEvaluatedResult.add(oBestMatch); 
		}
		
		return oEvaluatedResult;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.08.2010, 16:04:05
	 *
	 * @param poDataStructure
	 * @param value
	 * @return
	 */
	private clsDataStructurePA getBestMatch(clsDataStructurePA poDataStructure,
											ArrayList<clsPair<Double, clsDataStructureContainer>> poValue) {
		//HZ For now: the element with the best match THAT INCLUDES the content type of the object, the search 
		//	 patterns are associated to, is selected => selected TI has to include an object that has the same 
		//   content type as the root element (poDataStructure in this case)

		for(clsPair<Double, clsDataStructureContainer> oPair : poValue){
			
			if(oPair.b.getMoDataStructure() instanceof clsAffect){
					return oPair.b.getMoDataStructure(); 
			}
			else{
				for(clsAssociation oAssociation : ((clsTemplateImage)oPair.b.getMoDataStructure()).getMoAssociatedContent()){
					
//					//Has to be discussed if this for loop and the if-statement are required: They
//					//should verify if the retrieved images can be mapped to the object. As there
//					//are different types of objects like bodyparts or entities I introduced this
//					//request => e.g. a TI is maybe only mapped to bodyparts but not to 
//					//entities 
					if(poDataStructure.getMoContentType().equals(oAssociation.getLeafElement().getMoContentType())){
						return oPair.b.getMoDataStructure();
					}
				}
			}
		}
		//FIXME HZ: never return null
		return null;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.08.2010, 22:39:32
	 *
	 */
	private void convertToSecondary() {
		moPerception_Output = new ArrayList<clsSecondaryDataStructureContainer>(); 
		
		for(clsTripple<clsDataStructurePA, ArrayList<clsTemplateImage>, ArrayList<clsPair<clsDriveMesh, clsAffect>>> oTripple : moOrderedResult){
			ArrayList <clsAssociation> oAssociatedWP = new ArrayList<clsAssociation>();
			clsWordPresentation oNewWP = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>(eDataType.WP.name(), "DEFAULT")); 
			
			oAssociatedWP.add(getWPforObject(oNewWP, oTripple.a));
			oAssociatedWP.addAll(getTItoWP(oNewWP, oTripple.b)); 
			oAssociatedWP.addAll(getWPforTP(oNewWP, oTripple.c));
						
			if(!oNewWP.getMoContent().contains("DEFAULT")){
				moPerception_Output.add(new clsSecondaryDataStructureContainer(oNewWP, oAssociatedWP)); 
			}
			
		}
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.08.2010, 19:53:01
	 *
	 * @param oContentWP
	 * @param a
	 * @return
	 */
	private clsAssociation getWPforObject(clsWordPresentation poContentWP, clsDataStructurePA poDS) {
		clsAssociation oAssWP = getWP(poDS);
		
		if(oAssWP != null){
			clsWordPresentation oWP = (clsWordPresentation)oAssWP.getLeafElement(); 
			poContentWP.setMoContent(oWP.getMoContentType() + ":" + oWP.getMoContent() + "|"); 
		}
			
		return oAssWP;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.08.2010, 19:53:10
	 *
	 * @param oContentWP
	 * @param b
	 * @return
	 */
	private Collection<? extends clsAssociation> getTItoWP(clsWordPresentation poContentWP, ArrayList<clsTemplateImage> poListTI) {
		
		clsAssociation oAssWP = null; 
		clsWordPresentation oWP = null; 
		ArrayList<clsAssociation> oTIWP = new ArrayList<clsAssociation>(); 
				
		for(clsTemplateImage oEntry : poListTI){
			oAssWP = getWP(oEntry); 
			
			if(oAssWP != null){
				oWP = (clsWordPresentation)oAssWP.getLeafElement(); 
				poContentWP.setMoContent(poContentWP.getMoContent() + oWP.getMoContentType() + ":" + oWP.getMoContent() + "|"); 
				oTIWP.add(oAssWP); 
			}
		}
		
		return oTIWP;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 22.08.2010, 19:53:07
	 *
	 * @param oContentWP
	 * @param c
	 * @return
	 */
	private Collection<? extends clsAssociation> getWPforTP(clsWordPresentation poContentWP, ArrayList<clsPair<clsDriveMesh, clsAffect>> poListDM) {
		clsWordPresentation oWP_dm; 
		clsWordPresentation oWP_affect;
		ArrayList<clsAssociation> oDMWP = new ArrayList<clsAssociation>(); 
		
		for(clsPair<clsDriveMesh, clsAffect> oEntry : poListDM){
			clsAssociation oAssWP_dm = getWP(oEntry.a);
		
			if(oAssWP_dm != null){
				oWP_dm = (clsWordPresentation)oAssWP_dm.getLeafElement();
				oWP_affect = (clsWordPresentation)getWP(oEntry.b).getLeafElement(); 
				oWP_dm.setMoContent(oWP_dm.getMoContent() + ":" + oWP_affect.getMoContent());  
				oDMWP.add( oAssWP_dm );
				poContentWP.setMoContent( poContentWP.getMoContent() + oWP_dm.getMoContent() + "|"); 
			}
				
		}
		return oDMWP;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 23.08.2010, 21:24:28
	 *
	 * @param poDataStructure
	 * @return
	 */
	private clsAssociation getWP(clsDataStructurePA poDataStructure){
		ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = new ArrayList<ArrayList<clsPair<Double,clsDataStructureContainer>>>();
		clsAssociation oRetVal = null; 
		
		search(eDataType.WP, new ArrayList<clsDataStructurePA>(Arrays.asList(poDataStructure)), oSearchResult); 
		
		if(oSearchResult.get(0).size() > 0 && oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures().size() > 0){
			oRetVal = (clsAssociation)oSearchResult.get(0).get(0).b.getMoAssociatedDataStructures().get(0);
		}
		
		return oRetVal;  
	}
	
	private ArrayList<clsDataStructureContainer> assignWPtoImages(ArrayList<clsPrimaryDataStructureContainer> oInput) {
		ArrayList<clsDataStructureContainer> oRetVal = new ArrayList<clsDataStructureContainer>();
		
		return oRetVal;
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:12
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the bjects of the type pa._v38.memorymgmt.datatypes
		send_I6_1(moPerception_Output, moAssociatedMemoriesSecondary_OUT);
		send_I6_4(moPerception_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I2_11_send#send_I2_11(java.util.ArrayList)
	 */
	@Override
	public void send_I6_1(ArrayList<clsSecondaryDataStructureContainer> poPerception, ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary) {
		//AW 20110602: Attention, the associated memeories contain images and not objects like in the perception
		((I6_1_receive)moModuleList.get(23)).receive_I6_1(poPerception, poAssociatedMemoriesSecondary);
		((I6_1_receive)moModuleList.get(26)).receive_I6_1(poPerception, poAssociatedMemoriesSecondary);
		
		putInterfaceData(I6_1_send.class, poPerception, poAssociatedMemoriesSecondary);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I5_4_send#send_I5_4(java.util.ArrayList)
	 */
	@Override
	public void send_I6_4(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		((I6_4_receive)moModuleList.get(20)).receive_I6_4(poPerception);
		putInterfaceData(I6_4_send.class, poPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:07
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:07
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
	

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:44:44
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
		moDescription = "This module does the same as {E8} but with perceptions instead of drive representations. The thing presentations and quota of affects generated by incoming perceived neurosymbols are associated with the most fitting word presentations found in memory. ";
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 03.05.2011, 17:40:36
	 * 
	 * @see pa._v38.interfaces.modules.I6_9_receive#receive_I6_9(java.util.ArrayList)
	 */
	@Override
	public void receive_I6_9(ArrayList<clsSecondaryDataStructureContainer> poActionCommands, ArrayList<clsDataStructureContainer> poAssociatedMemories) {
		// TODO (zeilinger) - Auto-generated method stub
		
	}	
}
