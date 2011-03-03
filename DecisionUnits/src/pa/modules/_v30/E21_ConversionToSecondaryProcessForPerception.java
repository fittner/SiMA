/**
 * E21_ConversionToSecondaryProcess.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:38:29
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import config.clsBWProperties;
import pa.interfaces.knowledgebase.itfKnowledgeBaseAccess;
import pa.interfaces.receive._v30.I2_10_receive;
import pa.interfaces.receive._v30.I2_11_receive;
import pa.interfaces.receive._v30.I5_4_receive;
import pa.interfaces.send._v30.I2_11_send;
import pa.interfaces.send._v30.I5_4_send;
import pa.memorymgmt.clsKnowledgeBaseHandler;
import pa.memorymgmt.datahandler.clsDataStructureGenerator;
import pa.memorymgmt.datatypes.clsAffect;
import pa.memorymgmt.datatypes.clsAssociation;
import pa.memorymgmt.datatypes.clsAssociationAttribute;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDataStructureContainer;
import pa.memorymgmt.datatypes.clsDataStructurePA;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsTemplateImage;
import pa.memorymgmt.datatypes.clsWordPresentation;
import pa.memorymgmt.enums.eDataType;
import pa.tools.clsPair;
import pa.tools.clsTripple;

/**
 * DOCUMENT (KOHLHAUSER) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:38:29
 * 
 */
public class E21_ConversionToSecondaryProcessForPerception extends clsModuleBase implements 
			I2_10_receive, I2_11_send, I5_4_send, itfKnowledgeBaseAccess {
	public static final String P_MODULENUMBER = "21";
	
	private clsKnowledgeBaseHandler moKnowledgeBaseHandler;
	
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
	public E21_ConversionToSecondaryProcessForPerception(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList);
		
		moKnowledgeBaseHandler = poKnowledgeBaseHandler;
		
		applyProperties(poPrefix, poProp);
	}

	private ArrayList<clsPrimaryDataStructureContainer> moGrantedPerception_Input; 
	//FIXME HZ: This would require a change in the interfaces!!! => different to the actual definition
	//private ArrayList<clsPair<clsSecondaryDataStructureContainer, clsPair<clsWordPresentation, clsWordPresentation>>> moPerception_Output; 
	private ArrayList<clsSecondaryDataStructureContainer> moPerception_Output; 
	
	private ArrayList<clsTripple<clsDataStructurePA, ArrayList<clsTemplateImage>, ArrayList<clsPair<clsDriveMesh, clsAffect>>>> moOrderedResult; 
	private HashMap<Integer, clsDriveMesh> moTemporaryDM; 

	
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
	public void receive_I2_10(ArrayList<clsPrimaryDataStructureContainer> poGrantedPerception) {
		moGrantedPerception_Input = (ArrayList<clsPrimaryDataStructureContainer>)this.deepCopy(poGrantedPerception);
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
		
		mergeInputToTI(); 
		convertToSecondary(); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 19.08.2010, 22:39:28
	 *
	 */
	private void mergeInputToTI() {
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
		
		for(clsPrimaryDataStructureContainer oContainer : moGrantedPerception_Input){
			HashMap<Integer,ArrayList<clsPair<Double,clsDataStructureContainer>>> oSearchResult = null;
			HashMap<Integer, clsDataStructurePA> oEvaluatedResult = null; 
			
			oSearchResult = getSearchResult(oContainer);
			oEvaluatedResult = evaluateSearchResult(oContainer.moDataStructure, oSearchResult);
			moOrderedResult.add(orderResult(oContainer.moDataStructure, oEvaluatedResult)); 
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
	private clsTripple<clsDataStructurePA, ArrayList<clsTemplateImage>, ArrayList<clsPair<clsDriveMesh, clsAffect>>> orderResult(
			clsDataStructurePA poDataStructure, 
			HashMap<Integer, clsDataStructurePA> poEvaluatedResult) {
		/*HZ: 21.08.2010: This method may be merged with the method convertToSecondary() in the future.
		 * It "orders" the evaluated result to a tripple of 
		 * - The data structure which the retrieved TIs are associated to
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
		
		for(Map.Entry<Integer, clsDataStructurePA> oEntry : poEvaluatedResult.entrySet()){
			clsDataStructurePA oDataStructurePA = oEntry.getValue(); 
			
			if(oDataStructurePA instanceof clsTemplateImage){
				oOrderedResult.b.add((clsTemplateImage)oDataStructurePA); 
			}
			else if(oDataStructurePA instanceof clsAffect){
				oOrderedResult.c.add(new clsPair<clsDriveMesh, clsAffect>(moTemporaryDM.get(oEntry.getKey()), (clsAffect)oDataStructurePA));
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
	private HashMap<Integer, ArrayList<clsPair<Double, clsDataStructureContainer>>> getSearchResult(clsPrimaryDataStructureContainer poContainer) {
		moTemporaryDM.clear();
		moSearchPattern.clear(); 
		
		HashMap <String, ArrayList<clsDataStructurePA>> oTIList = new HashMap <String, ArrayList<clsDataStructurePA>>(); 
		
		for(clsAssociation oAssociation : poContainer.moAssociatedDataStructures){
			if(oAssociation instanceof clsAssociationAttribute){
				String oCT = oAssociation.getLeafElement().moContentType; 
				
				if(oTIList.containsKey(oCT)){ oTIList.get(oCT).add(oAssociation.getLeafElement());}
				else {oTIList.put(oCT, new ArrayList<clsDataStructurePA>(Arrays.asList(oAssociation.getLeafElement()))); }

			}
			if(oAssociation instanceof clsAssociationDriveMesh){
				clsDriveMesh oDM = (clsDriveMesh) oAssociation.getLeafElement(); 
				clsDataStructurePA oAffect = clsDataStructureGenerator.generateDataStructure(eDataType.AFFECT, new clsPair<String, Object>(eDataType.AFFECT.name(), oDM.getPleasure())); 
				addToSearchPattern(eDataType.AFFECT,  oAffect);
				moTemporaryDM.put(moSearchPattern.size()-1, oDM); 
			}
		}
		
		for(Map.Entry<String,  ArrayList<clsDataStructurePA>> oEntry : oTIList.entrySet()){
			clsTripple <String, ArrayList<clsDataStructurePA>, Object> oTIcontent = 
				new clsTripple<String, ArrayList<clsDataStructurePA>, Object>(oEntry.getKey(), oEntry.getValue(), "undefined"); 
			
			clsDataStructurePA oTI = clsDataStructureGenerator.generateDataStructure(eDataType.TI, oTIcontent);
			addToSearchPattern(eDataType.UNDEFINED, oTI); 
		}
		
		return accessKnowledgeBase();	
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
	private HashMap<Integer, clsDataStructurePA> evaluateSearchResult( 
							clsDataStructurePA poDataStructure,
							HashMap<Integer, ArrayList<clsPair<Double, clsDataStructureContainer>>> poSearchResult) {
		
		HashMap<Integer, clsDataStructurePA> oEvaluatedResult = new HashMap<Integer, clsDataStructurePA>(); 
		
		for(Map.Entry<Integer, ArrayList<clsPair<Double, clsDataStructureContainer>>> oEntry : poSearchResult.entrySet()){
			clsDataStructurePA oBestMatch = getBestMatch(poDataStructure, oEntry.getValue()); 
			oEvaluatedResult.put(oEntry.getKey(), oBestMatch); 
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
			
			if(oPair.b.moDataStructure instanceof clsAffect){
					return oPair.b.moDataStructure; 
			}
			else{
				for(clsAssociation oAssociation : ((clsTemplateImage)oPair.b.moDataStructure).moAssociatedContent){
					
//					//Has to be discussed if this for loop and the if-statement are required: They
//					//should verify if the retrieved images can be mapped to the object. As there
//					//are different types of objects like bodyparts or entities I introduced this
//					//request => e.g. a TI is maybe only mapped to bodyparts but not to 
//					//entities 
					if(poDataStructure.moContentType.equals(oAssociation.getLeafElement().moContentType)){
						return oPair.b.moDataStructure;
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
			clsWordPresentation oNewWP = (clsWordPresentation)clsDataStructureGenerator.generateDataStructure(eDataType.WP, new clsPair<String, Object>(eDataType.WP.name(), "")); 
			
			try{
				oAssociatedWP.add(getWPforObject(oNewWP, oTripple.a));
				oAssociatedWP.addAll(getTItoWP(oNewWP, oTripple.b)); 
				oAssociatedWP.addAll(getWPforTP(oNewWP, oTripple.c));
				
				moPerception_Output.add(new clsSecondaryDataStructureContainer(oNewWP, oAssociatedWP)); 
			}catch(NullPointerException e1){/*tbd*/}
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
		
	    clsWordPresentation oWP = (clsWordPresentation)oAssWP.getLeafElement(); 
		poContentWP.moContent += oWP.moContentType + ":" + oWP.moContent + "|"; 
			
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
			oWP = (clsWordPresentation)oAssWP.getLeafElement(); 
			poContentWP.moContent += oWP.moContentType + ":" + oWP.moContent + "|"; 
			oTIWP.add(oAssWP); 
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
		
			oWP_dm = (clsWordPresentation)oAssWP_dm.getLeafElement();
			oWP_affect = (clsWordPresentation)getWP(oEntry.b).getLeafElement(); 
			oWP_dm.moContent += ":" + oWP_affect.moContent;  
			oDMWP.add( oAssWP_dm );
				
			poContentWP.moContent += oWP_dm.moContent + "|"; 
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
		moSearchPattern.clear(); 
		addToSearchPattern(eDataType.WP, poDataStructure); 
		
		clsAssociation oAssWP = null; 
		
		try{
			oAssWP = (clsAssociation)accessKnowledgeBase().get(0).get(0).b.moAssociatedDataStructures.get(0);
		} catch (IndexOutOfBoundsException ex1){/*required to catch if moAssociatedDS = null*/return null;}
			
		return oAssWP;  
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
		//HZ: null is a placeholder for the bjects of the type pa.memorymgmt.datatypes
		send_I2_11(moPerception_Output);
		send_I5_4(moPerception_Output);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I2_11_send#send_I2_11(java.util.ArrayList)
	 */
	@Override
	public void send_I2_11(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		((I2_11_receive)moModuleList.get(22)).receive_I2_11(moPerception_Output);
		((I2_11_receive)moModuleList.get(23)).receive_I2_11(moPerception_Output);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:48:18
	 * 
	 * @see pa.interfaces.send.I5_4_send#send_I5_4(java.util.ArrayList)
	 */
	@Override
	public void send_I5_4(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		((I5_4_receive)moModuleList.get(20)).receive_I5_4(moPerception_Output);
		
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
	 * @author zeilinger
	 * 12.08.2010, 20:58:22
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#accessKnowledgeBase(java.util.ArrayList)
	 */
	@Override
	public HashMap<Integer,ArrayList<clsPair<Double,clsDataStructureContainer>>> accessKnowledgeBase() {
		return moKnowledgeBaseHandler.initMemorySearch(moSearchPattern);
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 16.08.2010, 10:15:47
	 * 
	 * @see pa.interfaces.knowledgebase.itfKnowledgeBaseAccess#addToSearchPattern(pa.memorymgmt.enums.eDataType, pa.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void addToSearchPattern(eDataType oReturnType, clsDataStructurePA poSearchPattern) {
		moSearchPattern.add(new clsPair<Integer, clsDataStructurePA>(oReturnType.nBinaryValue, poSearchPattern));
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:44:44
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

}
