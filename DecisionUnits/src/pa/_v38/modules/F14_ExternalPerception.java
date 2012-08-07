/**
 * E14_PreliminaryExternalPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:26:13
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.interfaces.modules.I2_3_receive;
import pa._v38.interfaces.modules.I2_4_receive;
import pa._v38.interfaces.modules.I2_6_receive;
import pa._v38.interfaces.modules.I2_6_send;
import pa._v38.interfaces.modules.I5_1_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datahandler.clsDataStructureConverter;
import pa._v38.memorymgmt.datahandler.clsDataStructureGenerator;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationAttribute;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsThingPresentation;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;
import pa._v38.memorymgmt.enums.eContent;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;
import pa._v38.symbolization.eSymbolExtType;
import pa._v38.symbolization.representationsymbol.itfSymbol;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.tools.toText;

/**
 * In this module neurosymbolic contents are transformed into thing presentations. Now, sensor sensations originating in body and 
 * environment sensors can be processed by the mental functions. The generated thing presentations are associated among each others 
 * according to their temporal and spatial vicinity and likeness.<br><br>
 * 
 * <b>INPUT:</b><br>
 * <i>moEnvironmentalData</i> this holds the symbols from the environmental perception (IN I2.3)<br>
 * <i>moBodyData</i> this holds the symbols from the bodily perception (IN I2.4) <br>
 * 
 * <br>
 * <b>OUTPUT:</b><br>
 * <i>moEnvironmentalTP</i> OUT member of F14, this holds the to TP converted symbols of the two perception paths (OUT 2.6)<br>
 * 
 * @author muchitsch
 * 07.05.2012, 14:26:13
 * 
 */
public class F14_ExternalPerception extends clsModuleBase implements 
					I2_3_receive, 
					I2_4_receive,
					I2_6_send,
					I5_1_receive
					{
	public static final String P_MODULENUMBER = "14";
	
	/** this holds the symbols from the environmental perception (IN I2.3) @since 21.07.2011 11:37:01 */
	private HashMap<eSymbolExtType, itfSymbol> moEnvironmentalData;
	/** this holds the symbols from the bodily perception (IN I2.4)  @since 21.07.2011 11:37:06 */
	private HashMap<eSymbolExtType, itfSymbol> moBodyData;
	/** OUT member of F14, this holds the to TP converted symbols of the two perception paths (OUT I2.6) @since 20.07.2011 10:26:23 */
	private ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalTP; 
	/** Input from Drive System */
	private ArrayList<clsDriveMesh> moDrives_IN;

	
	
	/**
	 * Constructor of F14, nothing unusual
	 * 
	 * @author muchitsch
	 * 03.03.2011, 16:15:27
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F14_ExternalPerception(String poPrefix, clsProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData) throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
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
		
		text += toText.mapToTEXT("moEnvironmentalData", moEnvironmentalData);
		text += toText.mapToTEXT("moBodyData", moBodyData);
		text += toText.listToTEXT("moEnvironmentalTP", moEnvironmentalTP);

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
	 * 11.08.2009, 14:27:13
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_3(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		moEnvironmentalData = (HashMap<eSymbolExtType, itfSymbol>) deepCopy(poEnvironmentalData); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:27:13
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData) {
		moBodyData = (HashMap<eSymbolExtType, itfSymbol>) deepCopy(poBodyData); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:41
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		
		//here also the body data should be processed, but nothing is coming from this path until now.
		
		moEnvironmentalTP = new ArrayList<clsPrimaryDataStructureContainer>(); 
		for(itfSymbol oSymbol : moEnvironmentalData.values()){
			if(oSymbol!=null){
				for(itfSymbol oSymbolObject : oSymbol.getSymbolObjects()) {
					//convert the symbol to a PDSC/TP
					clsPrimaryDataStructure oDataStructure = (clsPrimaryDataStructure)clsDataStructureConverter.convertExtSymbolsToPsychicDataStructures(oSymbolObject); 
					moEnvironmentalTP.add(new clsPrimaryDataStructureContainer(oDataStructure,null));
				}	
			}
		}
		//add the perception of the floor, as we dont have a sensor detecting the floor
		
		//AW 20120522: Outcommented this part as it is never used later
		//clsPrimaryDataStructure oFloorDataStructure = (clsPrimaryDataStructure) clsDataStructureGenerator.generateDataStructure(eDataType.TP, new clsPair<String, Object>("FLOOR", "EMPTYSPACE"));
		//moEnvironmentalTP.add(new clsPrimaryDataStructureContainer(oFloorDataStructure,null));
		
		//prepared, but nothing is coming through so not much to do
		for(itfSymbol oSymbol : moBodyData.values()){
			if(oSymbol!=null){
				for(itfSymbol oSymbolObject : oSymbol.getSymbolObjects()) {
					//convert the symbol to a PDSC/TP
					clsPrimaryDataStructure oDataStructure = (clsPrimaryDataStructure)clsDataStructureConverter.convertExtSymbolsToPsychicDataStructures(oSymbolObject); 
					moEnvironmentalTP.add(new clsPrimaryDataStructureContainer(oDataStructure,null));
				}	
			}
		}
		
		//AW 20120522: Add the SELF to the perception. Actually it should be added before and origin from the body
		//TODO @CM: Please adapt the SELF for your needs. 
		clsPrimaryDataStructure oSelfDataStructure = (clsThingPresentationMesh)clsDataStructureGenerator.generateDataStructure(eDataType.TPM, new clsTriple<eContentType, Object, Object>(eContentType.ENTITY, new ArrayList<clsPhysicalRepresentation>(), eContent.SELF.toString())); 
		clsPrimaryDataStructureContainer oSelfContainer = new clsPrimaryDataStructureContainer(oSelfDataStructure,new ArrayList<clsAssociation>());
		//Add Position to SELF
		clsThingPresentation oPos = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.POSITION, ePhiPosition.CENTER.toString()));
		clsAssociationAttribute oPosAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.POSITIONASSOCIATION), oSelfDataStructure, oPos);
		oSelfContainer.addMoAssociatedDataStructure(oPosAss);
		
		//Add Distance to SELF
		clsThingPresentation oDist = clsDataStructureGenerator.generateTP(new clsPair<eContentType, Object>(eContentType.DISTANCE, eRadius.NODISTANCE.toString()));
		clsAssociationAttribute oDistAss = new clsAssociationAttribute(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.ASSOCIATIONATTRIBUTE, eContentType.DISTANCEASSOCIATION), oSelfDataStructure, oDist);
		oSelfContainer.addMoAssociatedDataStructure(oDistAss);
		
		moEnvironmentalTP.add(oSelfContainer);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:41
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_6(moEnvironmentalTP);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:55:55
	 * 
	 * @see pa.interfaces.send.I2_5_send#send_I2_5(java.util.ArrayList)
	 */
	@Override
	public void send_I2_6(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		((I2_6_receive)moModuleList.get(46)).receive_I2_6(poEnvironmentalTP);
		putInterfaceData(I2_6_send.class, poEnvironmentalTP);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:34
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:34
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:15:33
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
		moDescription = "Neurosymbolic contents are transformed into thing presentations. Now, sensor sensations originating in body and environment sensors can be processed by the mental functions. The generated thing presentations are associated among each others according to their temporal and spacial vicinity and likeness.";
	}

	/* (non-Javadoc)
	 *
	 * @since 15.05.2012 10:58:38
	 * 
	 * @see pa._v38.interfaces.modules.I5_1_receive#receive_I5_1(java.util.ArrayList)
	 */
	@Override
	public void receive_I5_1(
			ArrayList<clsDriveMesh> poDrives) {
		moDrives_IN = (ArrayList<clsDriveMesh>)deepCopy(poDrives);
		
	}	
}
