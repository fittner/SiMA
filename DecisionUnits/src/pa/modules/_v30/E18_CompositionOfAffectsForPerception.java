/**
 * E18_GenerationOfAffectsForPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import config.clsBWProperties;
import pa.interfaces._v30.eInterfaces;
import pa.interfaces.receive._v30.I2_16_receive;
import pa.interfaces.receive._v30.I2_8_receive;
import pa.interfaces.receive._v30.I2_9_receive;
import pa.interfaces.send._v30.I2_9_send;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsThingPresentationMesh;
import pa.tools.clsPair;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 * 
 */
public class E18_CompositionOfAffectsForPerception extends clsModuleBase implements I2_8_receive, I2_16_receive, I2_9_send {
	public static final String P_MODULENUMBER = "18";
	
	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moMergedPrimaryInformation_Input;
	private ArrayList<clsPrimaryDataStructureContainer> moNewPrimaryInformation; 
	
	/**
	 * DOCUMENT (wendt) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:33:27
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E18_CompositionOfAffectsForPerception(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, HashMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);
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
		String html = "";
		
		html += listToHTML("moMergedPrimaryInformation_Input", moMergedPrimaryInformation_Input);
		html += listToHTML("moNewPrimaryInformation", moNewPrimaryInformation);

		return html;
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
		mnPsychicInstances = ePsychicInstances.ID;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:34:27
	 * 
	 * @see pa.interfaces.I2_8#receive_I2_8(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		moMergedPrimaryInformation_Input = (ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>>)deepCopy(poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:59
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		adaptPleasureValue(); 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.08.2010, 12:44:36
	 *
	 */
	private void adaptPleasureValue() {
		moNewPrimaryInformation = new ArrayList<clsPrimaryDataStructureContainer>(); 
		
		//for each pair in the input list...
		for(clsPair <clsPrimaryDataStructureContainer, clsDriveMesh> oPair : moMergedPrimaryInformation_Input){
			//If there is an object e. g. CAKE, which is a TPM...
			if(oPair.a.getMoDataStructure() instanceof clsThingPresentationMesh){
				//For each associated content in moAssociatedContent...
				for(pa.memorymgmt.datatypes.clsAssociation oAssociation : oPair.a.getMoAssociatedDataStructures()){
					//If the Association is a DM...
					if(oAssociation instanceof clsAssociationDriveMesh){
						//Get the actual DM from the association
						clsDriveMesh oDMInput = ((clsAssociationDriveMesh)oAssociation).getDM(); 
						//Get the DM from the repressed content
						clsDriveMesh oDMRepressed = oPair.b; 
						
						/* If the moContentType is equal (at CAKE, NOURISH), then set new pleasure as the average of 
						 * the pleasure of repressed content and the object. Then, new moContent is set from the 
						 * Repressed content (NOURISH GREEDY statt NOURISH_CAKEBASIC).
						 */
						
						if (oDMInput.getMoContentType().intern() == oDMRepressed.getMoContentType().intern()) {
						//old Fix here if(oDMInput.getMoContent().intern() == oDMRepressed.getMoContent().intern()){
							oDMInput.setPleasure((oDMInput.getPleasure()+oDMRepressed.getPleasure())/2); 
							oDMInput.setMoContent(oDMRepressed.getMoContent()); 
						}
					}
				}
			}
			
			moNewPrimaryInformation.add(oPair.a); 
		}
	}

	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:59
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I2_9(moNewPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:38:33
	 * 
	 * @see pa.interfaces.send.I2_9_send#send_I2_9(java.util.ArrayList)
	 */
	@Override
	public void send_I2_9(ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		((I2_9_receive)moModuleList.get(7)).receive_I2_9(poMergedPrimaryInformation);
		((I2_9_receive)moModuleList.get(19)).receive_I2_9(poMergedPrimaryInformation);
		
		putInterfaceData(I2_9_send.class, poMergedPrimaryInformation);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:50
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (wendt) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:46:50
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (wendt) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:33:37
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
	 * 03.03.2011, 16:34:02
	 * 
	 * @see pa.interfaces.receive._v30.I2_16_receive#receive_I2_16(java.util.ArrayList)
	 */
	@Override
	public void receive_I2_16(
			ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> poMergedPrimaryInformation) {
		// TODO (wendt) - Auto-generated method stub
		
	}
}
