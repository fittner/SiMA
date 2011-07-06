/**
 * clsBlockedContentStorage.java: DecisionUnits - pa.storage
 * 
 * @author deutsch
 * 09.03.2011, 17:12:46
 */
package pa._v38.storage;

import java.util.ArrayList;
import java.util.Arrays;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;
import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.modules.D2_2_send;
import pa._v38.interfaces.modules.D2_3_receive;
import pa._v38.interfaces.modules.D2_4_receive;
import pa._v38.interfaces.modules.D2_4_send;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.03.2011, 17:12:46
 * 
 */
public class clsBlockedContentStorage implements itfInspectorInternalState, itfInterfaceDescription, D2_2_send, D2_4_send, D2_4_receive, D2_3_receive {
    //private ArrayList<clsDataStructurePA> moBlockedContent;
	private ArrayList<clsDriveMesh> moBlockedContent;
    private ArrayList<clsDataStructurePA> moContainerBlockedContent; 
    
    //Inputcontainer from F35
    private ArrayList<clsPrimaryDataStructureContainer> moInputPerception;
    
    //Outputcontainerlist to F35
    private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moOutputPerception;
    
    //AW 20110430: Static and TI
	//private static ArrayList<clsTemplateImage> moBlockedContent;
    
    public clsBlockedContentStorage() {
    	//The storage consists of an arraylist of clsDriveMesh
    	
    	moBlockedContent = new ArrayList<clsDriveMesh>();
    	//moContainerBlockedContent = new ArrayList<clsPrimaryDataStructureContainer>();
    	fillWithTestData();
    	
    	//AW 20110430: New Template Imagelist
    	//moBlockedContent = new ArrayList<clsTemplateImage>();
    }
    
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 30.06.2011, 14:40:39
	 * 
	 * This method is used by "F06: defense mechanisms for drives"
	 * 
	 */ 
    public void add(clsDriveMesh oDM){
    	moBlockedContent.add(oDM);
    }
	
	@SuppressWarnings("unchecked")
	private void fillWithTestData() {
    	ArrayList<ArrayList<Object>> oList = new ArrayList<ArrayList<Object>>();
    	oList.add( new ArrayList<Object>( Arrays.asList("PUNCH", "BITE", 0.0, 0.0, 0.8, 0.2, -0.5) ) );
    	oList.add( new ArrayList<Object>( Arrays.asList("GREEDY", "NOURISH", 0.8, 0.2, 0.0, 0.0, -0.3) ) );
    	oList.add( new ArrayList<Object>( Arrays.asList("DIRTY", "DEPOSIT", 0.0, 0.7, 0.3, 0.0, -0.7) ) );
    	// TI-TPM(CAKE)-AssociationDM(Nourisch, Value =-100)
			
    	for (ArrayList<Object> oData:oList) {
			clsThingPresentation oTP = clsDataStructureGenerator.generateTP(new clsPair<String, Object>((String)oData.get(0), oData.get(0))); 
			clsDriveMesh oDM = clsDataStructureGenerator.generateDM(new clsTripple<String, ArrayList<clsThingPresentation>, Object>((String)oData.get(1), 
																	   new ArrayList<clsThingPresentation>(Arrays.asList(oTP)),
																	   oData.get(0)));
			oDM.setCategories( (Double)oData.get(2), (Double)oData.get(3), (Double)oData.get(4), (Double)oData.get(5) );
			oDM.setPleasure( (Double)oData.get(6) ); 
	    	moBlockedContent.add(oDM);    	
    	}
    	
    }
    
	public clsDriveMesh getBestMatchCONVERTED(clsPrimaryDataStructureContainer poInput) {
		return getBestMatchCONVERTED(poInput, false);
	}
    
	public clsDriveMesh getBestMatchCONVERTED(clsPrimaryDataStructureContainer poInput, boolean boRemoveAfterActivate) {
		clsDriveMesh oRetVal = null;
		
		double rHighestMatch = 0.0;

		for( clsDriveMesh oDMRepressedContent : moBlockedContent ) {
			for(clsAssociation oAssociation : poInput.getMoAssociatedDataStructures()){
				//HZ 17.08.2010: The method getLeafElement cannot be used here as the search patterns actually
				// do not have a data structure ID => in a later version when E16 will be placed in front 
				// of E15, the patterns already have an ID. 
				if(oAssociation instanceof clsAssociationDriveMesh){
					clsDriveMesh oData = ((clsAssociationDriveMesh)oAssociation).getDM(); 
					
					if(oDMRepressedContent.getMoContentType().equals(oData.getMoContentType())){
						double rMatchValue = oDMRepressedContent.matchCathegories(oData); 
							
						if(rMatchValue > rHighestMatch) {
								rHighestMatch = rMatchValue;
								oRetVal = oDMRepressedContent;
						}
					}
					if(rHighestMatch >= 1) { break;	} //do the doublebreak to abort search --> first come first serve
				}
			}
		}
		
		//*************************************************************************************
		//AW 20110430: Add option to remove the original object from the repressed content list
		if (boRemoveAfterActivate==true) {
			moBlockedContent.remove(oRetVal);	//FIXME: Test this one
		}
		//*************************************************************************************

		if (oRetVal == null) {
			//TD 2011/04/20: safety - create empty drive mesh to be returned. should never happen ... but safety first!
			clsThingPresentation oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair<String, Object>("NULL", "NULL")); 
			clsTripple <String, ArrayList<clsThingPresentation>, Object> oContent = 
				new clsTripple<String, ArrayList<clsThingPresentation>, Object>("REPRESSED", new ArrayList<clsThingPresentation>(Arrays.asList(oTP)), "DEFAULT"); 
			oRetVal = (clsDriveMesh) clsDataStructureGenerator.generateDataStructure(eDataType.DM, oContent);
			
//			throw new java.lang.NullPointerException();
		
		}
		
		return oRetVal;
	}
	
	/*
	//Tempfunktion - löschen wenn nicht verwendet
	public clsTemplateImage getBestMatchCONVERTED(clsTemplateImage poInput) {
	// Dummy funktion, die auf template image konvertiert werden soll. Später soll es möglich sein, mehrere 
	// Images zu aktivieren
		return null;
	}
	
	//AW 20110430: New Function Add TemplateImage
	public void AddTemplateImage(clsTemplateImage oInput) {
	//Content, which is repressed is added with this function
		//moBlockedContent.add(oInput);
	}
	
	//AW 20110430: New Function ActivateImage, Load in E35 and remove from memory
	public ArrayList<clsTemplateImage> activateTemplateImages(clsTemplateImage oInput, boolean boRemoveActivated) {
		ArrayList<clsTemplateImage> oRetVal = new ArrayList<clsTemplateImage>();
		
		
		
		
		return oRetVal;
		
	}
	*/
	
	

	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 24.06.2011, 12:39:15
	 * 
	 * This method is used by "F54: Emersion of blocked drive content"
	 * 
	 */
	public clsDriveMesh getBestMatchCONVERTED(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poInput) {
		return getBestMatchCONVERTED(poInput, false);
	}

	// (FG) This method was copied from AW's method: public clsDriveMesh getBestMatchCONVERTED(clsPrimaryDataStructureContainer poInput, boolean boRemoveAfterActivate)	
	/*
	 * finds best match in list of clsDriveMeshes of repressed content
	 */
	public clsDriveMesh getBestMatchCONVERTED(ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> poInput, boolean boRemoveAfterActivate) {
		clsDriveMesh oRetVal = null;
		
		double rHighestMatch = 0.0;

		for( clsDriveMesh oDMRepressedContent : moBlockedContent ) {
			for(clsPair<clsPhysicalRepresentation, clsDriveMesh> oDrivePair : poInput){ 
				clsDriveMesh oData = oDrivePair.b; 

					
					if(oDMRepressedContent.getMoContentType().equals(oData.getMoContentType())){
						double rMatchValue = oDMRepressedContent.matchCathegories(oData); 
							
						if(rMatchValue > rHighestMatch) {
								rHighestMatch = rMatchValue;
								oRetVal = oDMRepressedContent;
						}
					}
					if(rHighestMatch >= 1) { break;	} //do the doublebreak to abort search --> first come first serve
				
			}
		}
		
		//*************************************************************************************
		//AW 20110430: Add option to remove the original object from the repressed content list
		if (boRemoveAfterActivate==true) {
			moBlockedContent.remove(oRetVal);	//FIXME: Test this one
		}
		//*************************************************************************************

		if (oRetVal == null) {
			//TD 2011/04/20: safety - create empty drive mesh to be returned. should never happen ... but safety first!
			clsThingPresentation oTP = (clsThingPresentation) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair<String, Object>("NULL", "NULL")); 
			clsTripple <String, ArrayList<clsThingPresentation>, Object> oContent = 
				new clsTripple<String, ArrayList<clsThingPresentation>, Object>("REPRESSED", new ArrayList<clsThingPresentation>(Arrays.asList(oTP)), "DEFAULT"); 
			oRetVal = (clsDriveMesh) clsDataStructureGenerator.generateDataStructure(eDataType.DM, oContent);
			
//			throw new java.lang.NullPointerException();
		
		}
		
		return oRetVal;
	}
	
	//This function is taken from F35
	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> matchRepressedContent(ArrayList<clsPrimaryDataStructureContainer> poCathegorizedInputContainer) {
		
		//For each object (e. g. CAKE) with adapted categories...
		//oInput is a clsPrimaryDataStructureContainer
		ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> oRetVal = new ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>>();
		
		for(clsPrimaryDataStructureContainer oInput : poCathegorizedInputContainer){
				/* A DM is loaded, which matches a drive, which is Repressed.
				 * In the storage of Repressed Content, DM are stored. If the ContentType of the DM attached to
				 * an object is exactly matched to a content type of a DM in the repressed Content Store, 
				 * the categories are compared. For each matching DM, the equality of the values in the categories
				 * are compared and a number <= 1.0 is generated. The DM with the highest category match is returned
				 * In the case of CAKE, there exists 2 DM: BITE (associated with DEATH) and NOURISH. In the Repressed Content Store, there
				 * exists 2 Repressed Content DM: BITE (PUNCH) and NOURISH (GREEDY), both with negative mrPleasure
				 * The match of the BITE DM is 0.5 and the match of the NOURISH DM is 0.9. Therefore, the DM of 
				 * NOURISH is selected
				 */
				//FIXME: mrPleasure = -0.3. This is not allowed. Add mrUnpleasure instead
//				clsDriveMesh oRep = moMemory.moRepressedContentsStore.getBestMatchCONVERTED(oInput);
//				moAttachedRepressed_Output.add(new clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>(oInput, oRep));
// TD 2011/04/20: removed above two line due to removal of rolands clsMemory. has to be reimplemented by other means
// TODO (Wendt): reimplement method matchRepressedContent
			clsDriveMesh oRep = getBestMatchCONVERTED(oInput);
			oRetVal.add(new clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>(oInput, oRep));
		}
		
		return oRetVal;
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.receive._v38.D2_3_receive#receive_D2_3(java.util.ArrayList)
	 */
	@Override
	public void receive_D2_3(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub

	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.receive._v38.D2_1_receive#receive_D2_1(java.util.ArrayList)
	 */
	/*@Override
	public void receive_D2_1(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub
		
	}*/

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.send._v38.D2_4_send#send_D2_4(java.util.ArrayList)
	 */
	@Override
	public ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> send_D2_4() {
		//AW: This IF goes to F35
		return moOutputPerception;
		
	}
	
	@Override
	public void receive_D2_4(ArrayList<clsPrimaryDataStructureContainer> poData) {
		//AW: This IF goes to F35
		//Here, an input image is received from F35, where matching is performed
		
		moInputPerception = poData;
		moOutputPerception = matchRepressedContent(moInputPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.send._v38.D2_2_send#send_D2_2(java.util.ArrayList)
	 */
	@Override
	public void send_D2_2(ArrayList<Object> poData) {
		// TODO (deutsch) - Auto-generated method stub
		
		
	}

	@Override
	public String toString() {
		return moBlockedContent.toString();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 15:02:51
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToHTML()
	 */
	@Override
	public String stateToTEXT() {
		String text = "";	
		text += toText.h1("Blocked Content Storage");
		text += toText.listToTEXT("moBlockedContent", moBlockedContent);
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 15:47:22
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
	 */
	@Override
	public String getDescription() {
		return "Module {E36} retrieves blocked content from the defense mechanisms. This content tries to be become unblocked again by emerging from {E36} and {E35} into the flow of the functional model. A special storage containing these blocked contents is necessary. The stored data is of type thing presentations with attached quota. Figure \ref{fig:model:functional:repressed_content} shows that the two modules are connected to this special type of storage with a read ({D2.2} and {D2.4}) and a write ({D2.1} and {D2.3}) interface. The two incoming interfaces into module {E36} are {I4.1} and {I4.2}. The first one transports blocked drives in the form of thing presentations plus attached quota of affects. The other one transports blocked thing presentations representing incoming perceptions in the same format. Both incoming information are stored into the memory via interface {D2.3}. Depending on future results of the functions of the module {E36}, drives pushed into this storage try to pass the defense mechanisms. Thus, drives in the form of thing presentations and attached quota of affects are sent via interface {I4.3} back to {E6}. The alternative possibility to reappear for blocked contents is module {E35}. Incoming perceptions in the form of thing presentations (transfered through interface {I2.14}) are compared with stored blocked content. If matching content is found it is attached to the incoming perception. The stored thing presentation plus attached quota of affects can be used as a whole or it can be split and only the thing presentation or the quota of affect are attached. The enriched thing presentation of the perception is forwarded via interface {I2.15} to the next module.";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 15:47:22
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D2_1, eInterfaces.D2_3) );
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 15:47:22
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D2_2, eInterfaces.D2_4) );
	}
}
