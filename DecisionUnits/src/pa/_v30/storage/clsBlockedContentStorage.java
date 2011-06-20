/**
 * clsBlockedContentStorage.java: DecisionUnits - pa.storage
 * 
 * @author deutsch
 * 09.03.2011, 17:12:46
 */
package pa._v30.storage;

import java.util.ArrayList;
import java.util.Arrays;

import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import pa._v30.tools.toText;
import pa._v30.interfaces.eInterfaces;
import pa._v30.interfaces.itfInspectorInternalState;
import pa._v30.interfaces.itfInterfaceDescription;
import pa._v30.interfaces.modules.D2_1_receive;
import pa._v30.interfaces.modules.D2_2_send;
import pa._v30.interfaces.modules.D2_3_receive;
import pa._v30.interfaces.modules.D2_4_send;
import pa._v30.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v30.memorymgmt.datatypes.clsAssociation;
import pa._v30.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;
import pa._v30.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v30.memorymgmt.datatypes.clsThingPresentation;
import pa._v30.memorymgmt.enums.eDataType;

/**
 *
 * 
 * @author deutsch
 * 09.03.2011, 17:12:46
 * 
 */
public class clsBlockedContentStorage implements itfInspectorInternalState, itfInterfaceDescription, D2_2_send, D2_4_send, D2_1_receive, D2_3_receive {
    private ArrayList<clsDriveMesh> moBlockedContent;
    
    public clsBlockedContentStorage() {
    	moBlockedContent = new ArrayList<clsDriveMesh>();
    	fillWithTestData();
    }
	
	@SuppressWarnings("unchecked")
	private void fillWithTestData() {
    	ArrayList<ArrayList<Object>> oList = new ArrayList<ArrayList<Object>>();
    	oList.add( new ArrayList<Object>( Arrays.asList("PUNCH", "BITE", 0.0, 0.0, 0.8, 0.2, -0.5) ) );
    	oList.add( new ArrayList<Object>( Arrays.asList("GREEDY", "NOURISH", 0.8, 0.2, 0.0, 0.0, -0.3) ) );
    	oList.add( new ArrayList<Object>( Arrays.asList("DIRTY", "DEPOSIT", 0.0, 0.7, 0.3, 0.0, -0.7) ) );
			
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
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.receive._v30.D2_3_receive#receive_D2_3(java.util.ArrayList)
	 */
	@Override
	public void receive_D2_3(ArrayList<Object> poData) {
		
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.receive._v30.D2_1_receive#receive_D2_1(java.util.ArrayList)
	 */
	@Override
	public void receive_D2_1(ArrayList<Object> poData) {
		
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.send._v30.D2_4_send#send_D2_4(java.util.ArrayList)
	 */
	@Override
	public void send_D2_4(ArrayList<Object> poData) {
		
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:15:13
	 * 
	 * @see pa.interfaces.send._v30.D2_2_send#send_D2_2(java.util.ArrayList)
	 */
	@Override
	public void send_D2_2(ArrayList<Object> poData) {
		
		
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
	 * @see pa._v30.interfaces.itfInspectorInternalState#stateToHTML()
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
	 * @see pa._v30.interfaces.itfInterfaceDescription#getDescription()
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
	 * @see pa._v30.interfaces.itfInterfaceDescription#getInterfacesRecv()
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
	 * @see pa._v30.interfaces.itfInterfaceDescription#getInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D2_2, eInterfaces.D2_4) );
	}
}
