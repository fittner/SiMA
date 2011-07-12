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
import config.clsBWProperties;
import pa._v38.interfaces.modules.I5_14_receive;
import pa._v38.interfaces.modules.I5_15_receive;
import pa._v38.interfaces.modules.I5_15_send;
import pa._v38.interfaces.modules.I5_11_receive;
import pa._v38.interfaces.modules.I5_16_receive;
import pa._v38.interfaces.modules.I5_16_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPhysicalRepresentation;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;

/**
 * DOCUMENT (GELBARD) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:35:08
 * 
 */
public class F19_DefenseMechanismsForPerception extends clsModuleBase implements 
			I5_14_receive, I5_11_receive, I5_15_send, I5_16_send{
	public static final String P_MODULENUMBER = "19";
	
	//AW 20110522: New inputs
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_OUT;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;
	
	//private ArrayList<clsPrimaryDataStructureContainer> moSubjectivePerception_Input; 
	//private ArrayList<clsPrimaryDataStructureContainer> moFilteredPerception_Output; 
	private ArrayList<pa._v38.memorymgmt.datatypes.clsThingPresentation> moDeniedThingPresentations;
	private ArrayList<clsAssociationDriveMesh> moDeniedAffects;

	@SuppressWarnings("unused")
	private ArrayList<clsPair<clsPhysicalRepresentation, clsDriveMesh>> moInput; 
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
	public F19_DefenseMechanismsForPerception(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
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
		
		text += toText.valueToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.valueToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);
		text += toText.listToTEXT("moDeniedThingPresentations", moDeniedThingPresentations);
		text += toText.listToTEXT("moDeniedAffects", moDeniedAffects);

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
	 * 11.08.2009, 16:16:03
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		//moFilteredPerception_Output = new ArrayList<clsPrimaryDataStructureContainer>(); 
		//HZ 20.08.2010 All objects that do not have a drive evaluation attached are filtered in a first step =>
		//				This makes sense as it is a problem to evaluate objects by the defense mechanisms that do
		//			    not have drives attached (even this is essential for an evaluation)
		//	 			The question that has to be discussed is if this filtering takes place in E18 or here.
		filterInput();
		
		moAssociatedMemories_OUT = moAssociatedMemories_IN;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.08.2010, 12:01:38
	 *
	 * @return
	 */
	private void filterInput() {
		//FIXME (gelbard) - Input changed

		moEnvironmentalPerception_OUT = moEnvironmentalPerception_IN;
		
		//		for(clsPrimaryDataStructureContainer oContainer : moSubjectivePerception_Input){
//			for(clsAssociation oAssociation : oContainer.getMoAssociatedDataStructures()){
//				//HZ: if program steps into the if-statement it is known that 
//				//	  a drive mesh is associated with the data structure => it has an affective evaluation
//				if(oAssociation instanceof clsAssociationDriveMesh){
//					moFilteredPerception_Output.add(oContainer);
//					break; 
//				}
//			}
//		}
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
	public void receive_I5_11(clsPrimaryDataStructureContainer poEnvironmentalPerception, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		moEnvironmentalPerception_IN = (clsPrimaryDataStructureContainer) deepCopy(poEnvironmentalPerception);
		//FIXME AW 20110522: Why is this warning present???
		moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>) deepCopy(poAssociatedMemories);
		
		//AW 20110522 What is this?
		mnTest = 0;
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
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		//HZ: null is a placeholder for the bjects of the type pa._v38.memorymgmt.datatypes
		send_I5_15(moEnvironmentalPerception_OUT, moAssociatedMemories_OUT);
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
	public void send_I5_15(clsPrimaryDataStructureContainer poEnvironmentalPerception, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		((I5_15_receive)moModuleList.get(21)).receive_I5_15(poEnvironmentalPerception, poAssociatedMemories);
		putInterfaceData(I5_15_send.class, poEnvironmentalPerception, poAssociatedMemories);
		
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
