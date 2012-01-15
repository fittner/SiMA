/**
 * E19_DefenseMechanismsForPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:35:08
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.interfaces.modules.I5_14_receive;
import pa._v38.interfaces.modules.I5_15_receive;
import pa._v38.interfaces.modules.I5_15_send;
import pa._v38.interfaces.modules.I5_11_receive;
import pa._v38.interfaces.modules.I5_16_receive;
import pa._v38.interfaces.modules.I5_16_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.clsKnowledgeBaseHandler;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.storage.DT2_BlockedContentStorage;
import pa._v38.tools.clsDataStructureTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

/**
 * Defends forbidden perceptions. Super-Ego (F7, F55) sends a list with forbidden perceptions to F19. F19 decides whether to defend the forbidden perceptions or not.
 * If F19 decided to defend the forbidden perceptions F19 chooses the defense mechanism (denial, projection, depreciation, ...).
 * 
 * @author deutsch, gelbard
 * 11.08.2009, 14:35:08
 * 
 */
public class F19_DefenseMechanismsForPerception extends clsModuleBaseKB implements 
			I5_14_receive, I5_11_receive, I5_15_send, I5_16_send{
	public static final String P_MODULENUMBER = "19";
	
	//AW 20110522: New inputs
	//private clsPrimaryDataStructureContainer moEnvironmentalPerception_Input;
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_Input;
	private clsThingPresentationMesh moPerceptionalMesh_IN;
	
	//private clsPrimaryDataStructureContainer moEnvironmentalPerception_Output;
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_Output;
	private clsThingPresentationMesh moPerceptionalMesh_OUT;
	
	private ArrayList<clsPair<String, String>> moForbiddenPerceptions_Input;
	
	//private ArrayList<clsPrimaryDataStructureContainer> moSubjectivePerception_Input; 
	//private ArrayList<clsPrimaryDataStructureContainer> moFilteredPerception_Output; 
	private ArrayList<pa._v38.memorymgmt.datatypes.clsThingPresentation> moDeniedThingPresentations;
	private ArrayList<clsAssociationDriveMesh> moDeniedAffects;

	@SuppressWarnings("unused")
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moInput;
	
	private DT2_BlockedContentStorage moBlockedContentStorage; // only needed here in F19 to initialize the blocked content storage

	private String moBlockedContentTypeString = "RI:REPRESSED";
	
	/**
	 * DOCUMENT (GELBARD) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:41:41
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F19_DefenseMechanismsForPerception(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces,
			ArrayList<Object>> poInterfaceData, DT2_BlockedContentStorage poBlockedContentStorage,
			clsKnowledgeBaseHandler poKnowledgeBaseHandler)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData, poKnowledgeBaseHandler);
		moDeniedAffects = new ArrayList<clsAssociationDriveMesh>();  //TD 2011/07/20 - added initialization of member field
 		applyProperties(poPrefix, poProp);	
 		
 		//Get Blocked content storage
		moBlockedContentStorage = poBlockedContentStorage;
		
		//Fill the blocked content storage with initial data from protege
		moBlockedContentStorage.addAll(initialFillRepressedContent());
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
		
		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT", moPerceptionalMesh_OUT);
		//text += toText.valueToTEXT("moAssociatedMemories_Input", moAssociatedMemories_Input);
		//text += toText.valueToTEXT("moAssociatedMemories_Output", moAssociatedMemories_Output);
		text += toText.listToTEXT("moForbiddenPerceptions_Input", moForbiddenPerceptions_Input);
		text += toText.listToTEXT("moDeniedThingPresentations", moDeniedThingPresentations);
		text += toText.listToTEXT("moDeniedAffects", moDeniedAffects);

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
	 * 11.08.2009, 16:28:09
	 * 
	 * @see pa.interfaces.I3_2#receive_I3_2(int)
	 */
	@SuppressWarnings("unchecked")
	/* Comment TD 20110622: also deepCopy ist ganz ganz ganz ganz ganz … ganz böses voodoo. 
	 * In diesem fall ist das problem, dass du 2 cast in einem machst/machen mußt. 
	 * Und der ist so nicht checkbar (afaik). In diesem fall einfach suppresswarning machen 
	 * (ist bei deepcopy nicht schlimm – kommt innerhalb der funktion dauernd vor).
	 */
	@Override
	public void receive_I5_11(ArrayList<clsPair<String, String>> poForbiddenPerceptions, clsThingPresentationMesh poPerceptionalMesh) {
		try {
			moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.cloneGraph();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		//FIXME AW 20110522: Why is this warning present???
		//moAssociatedMemories_Input   = (ArrayList<clsPrimaryDataStructureContainer>) deepCopy(poAssociatedMemories);
		
		moForbiddenPerceptions_Input = poForbiddenPerceptions;
	}
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 03.05.2011, 17:36:10
	 * 
	 * @see pa._v38.interfaces.modules.I5_14_receive#receive_I5_14(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_14(
			ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poData) {
		
		moInput = (ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>>) deepCopy(poData);
	}		

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:03
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		moPerceptionalMesh_OUT = moPerceptionalMesh_IN;		
		//moAssociatedMemories_Output      = moAssociatedMemories_Input;
		
		deny_perception (moForbiddenPerceptions_Input);
		
	}
	
		
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 03.07.2011, 17:06:49
	 * 
	 * searches in the input-perception for example for an ENTITY like a ARSIN
	 * 
	 */
	private void deny_perception (ArrayList<clsPair<String, String>> oForbiddenPerceptions) {
		
    	// If nothing to deny return immediately (otherwise NullPointerException)
    	if (oForbiddenPerceptions == null) return;
		
		// check list of forbidden perceptions
		for(clsPair<String, String> oOneForbiddenPerception : oForbiddenPerceptions) {	    	
			String oContentType = oOneForbiddenPerception.a;
			String oContent     = oOneForbiddenPerception.b;
			
			int i = 0;
			clsDataStructurePA oFoundObject = null;
			
			// search in perceptions
			//Get all images and objects in the mesh
			//ArrayList<clsThingPresentationMesh> oTPMList = clsDataStructureTools.getTPMObjects(moPerceptionalMesh_OUT, oContentType, oContent, true, 1);
			ArrayList<clsPair<String, String>> oContentTypeAndContentList = new ArrayList<clsPair<String, String>>();
			oContentTypeAndContentList.add(new clsPair<String, String>(oContentType, oContent));
			ArrayList<clsDataStructurePA> oTPMList = clsDataStructureTools.getDataStructureInMesh(moPerceptionalMesh_OUT, eDataType.TPM, oContentTypeAndContentList, true, 1);
			if (oTPMList.isEmpty()==false) {
				oFoundObject = oTPMList.get(0);
			}
			
//			for(clsThingPresentationMesh oContainer : oImages){
//				
//				// check a TPM
//				if(oContainer instanceof clsThingPresentationMesh){
//					
//					// check if it is for example an ARSin
//					if(oContainer.getMoContentType().equalsIgnoreCase(oContentType)){
//						if(((clsThingPresentationMesh)oContainer.getMoDataStructure()).getMoContent().equalsIgnoreCase(oContent)){
//							
//							// perception found
//						    break;
//						}	
//					}					
//				}
				
				// or oContainer can contain a thing-presentation
				// in this case
				// check a TP
			
			//The attribute list is clsAssociationAttribute

			ArrayList<clsDataStructurePA> oAttributeList = clsDataStructureTools.getDataStructureInMesh(moPerceptionalMesh_OUT, eDataType.TP, oContentTypeAndContentList, true, 1);
			//ArrayList<clsAssociationAttribute> oAttributeList = clsDataStructureTools.getTPAssociations(moPerceptionalMesh_OUT, oContentType, oContent, 0, true, 1);
			if (oAttributeList.isEmpty()==false) {
				oFoundObject = oAttributeList.get(0);
			}
				
//			else if(oContainer.getMoDataStructure() instanceof clsThingPresentation){
//					
//					// check if it is for example an ARSin
//					if(oContainer.getMoDataStructure().getMoContentType().equalsIgnoreCase(oContentType)){
//						if(((String) ((clsThingPresentation)oContainer.getMoDataStructure()).getMoContent()).equalsIgnoreCase(oContent)){
//							
//							// perception found
//						    break;
//						}	
//					}					
//				}
				
				
				// or oContainer can contain a template image
				// in this case
				// check a TI
//				else if(oContainer.getMoDataStructure() instanceof clsTemplateImage){
//					
//					// check if it is for example an ARSin
//					if(oContainer.getMoDataStructure().getMoContentType().equalsIgnoreCase(oContentType)){
//						if(((String) ((clsTemplateImage)oContainer.getMoDataStructure()).getMoContent()).equalsIgnoreCase(oContent)){
//							
//							// perception found
//						    break;
//						}	
//					}					
//				}
				
//				i++;
				
		//	}	
		
		// if Perception found -->	
		if (oFoundObject!=null) {
			//Delete object from the perception
			
			//TODO AW: Implement this function. It is not implemented yet!!!!!!!
			clsDataStructureTools.deleteObjectInMesh(moPerceptionalMesh_OUT, oFoundObject);
		}
		
		
//			if (i < moAssociatedMemories_Output.size()) {				
//				// --> remove Perception i from output list
//				moAssociatedMemories_Output.remove(i);
//			}
		}
	}

	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 20.09.2011, 16:16:03
	 * 
	 * initializes the blocked content storage DT2_BlockedContentStorage with images
	 */
//	private void fillBlockedContentStorageWithTestData (){
//		//Search for matches for the input image
//		double mrMatchThreshold = 1.0;
//		
//		// contains images for initialization of DT2_BlockedContentStorage
//		ArrayList<clsPair<Double,clsDataStructureContainer>> oSearchResultContainer = new ArrayList<clsPair<Double,clsDataStructureContainer>>();
//		
//		// String for searching for content type from the storage of images to libido
//		final String oBlockedContentImageString = "IMAGE:BLOCKEDCONTENT";
//		
//		//Find matching images for the input image
//		searchContainer(null, oSearchResultContainer, oBlockedContentImageString, mrMatchThreshold);
//
//		for (clsPair<Double,clsDataStructureContainer> oInitImage : oSearchResultContainer) {
//			moBlockedContentStorage.add((clsPrimaryDataStructureContainer) oInitImage.b);
//		}
//	}
	
	/**
	 * This function load all images with the content type "IMAGE:REPRESSED" from the knowledgebase. Those images are defined in
	 * Protege
	 * 
	 * (wendt)
	 *
	 * @since 24.10.2011 09:40:43
	 *
	 * @return
	 */
	private ArrayList<clsThingPresentationMesh> initialFillRepressedContent() {
		ArrayList<clsThingPresentationMesh> oRetVal = new ArrayList<clsThingPresentationMesh>();
		
		ArrayList<clsPair<Double, clsDataStructurePA>> oSearchResult = new ArrayList<clsPair<Double, clsDataStructurePA>>();
		
		clsThingPresentationMesh newTPMImage = new clsThingPresentationMesh(new clsTriple<Integer, eDataType, String>(-1, eDataType.TPM, moBlockedContentTypeString), new ArrayList<clsAssociation>(), "EMPTY");
		//clsPrimaryDataStructureContainer oPattern = new clsPrimaryDataStructureContainer(newTI, new ArrayList<clsAssociation>());
		
		searchMesh(newTPMImage, oSearchResult, moBlockedContentTypeString, 0.0, 2);	//Set pnLevel=2, in order to add direct matches
		
		for (clsPair<Double, clsDataStructurePA> oPair : oSearchResult) {
			oRetVal.add((clsThingPresentationMesh) oPair.b);
		}
		
		return oRetVal;
	}
	
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:03
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the bjects of the type pa._v38.memorymgmt.datatypes
		send_I5_15(moPerceptionalMesh_OUT);
		send_I5_16(moDeniedAffects);
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:40:12
	 * 
	 * @see pa.interfaces.send.I2_10_send#send_I2_10(java.util.ArrayList)
	 */
	@Override
	public void send_I5_15(clsThingPresentationMesh poPerceptionalMesh) {
		((I5_15_receive)moModuleList.get(21)).receive_I5_15(poPerceptionalMesh);
		putInterfaceData(I5_15_send.class, poPerceptionalMesh);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:40:12
	 * 
	 * @see pa.interfaces.send.I5_16_send#send_I5_16(java.util.ArrayList)
	 */
	@Override
	public void send_I5_16(ArrayList<clsAssociationDriveMesh> poDeniedAffects) {
		((I5_16_receive)moModuleList.get(20)).receive_I5_16(poDeniedAffects);
		putInterfaceData(I5_16_send.class, poDeniedAffects);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:55
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (GELBARD) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:55
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (GELBARD) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:41:48
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
		moDescription = "Analogous to {E6}, {E19} evaluates incoming perceptions if they are allowed to become (pre-)conscious contents. Here, focus is on whether this ``thought'' is allowed or not. This is in opposition to defense mechanisms for drives where the focus is on the acceptability of satisfying a drive demand with a certain object. The same set of mechanisms can be used for {E6} and {E19}. They differ by the available data. {E6} has drive demands, internalized rules, and knowledge about its drives at hand; {E19} has only internalized rules and the perception.";
	}
}
