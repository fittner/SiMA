/**
 * E18_GenerationOfAffectsForPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.clsInterfaceHandler;
import pa.datatypes.clsAffectMemory;
import pa.datatypes.clsAssociation;
import pa.datatypes.clsAssociationContent;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.interfaces.receive.I2_8_receive;
import pa.interfaces.receive.I2_9_receive;
import pa.interfaces.send.I2_9_send;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 * 
 */
public class E18_GenerationOfAffectsForPerception extends clsModuleBase implements I2_8_receive, I2_9_send {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:34:15
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public ArrayList<clsPair<clsPrimaryInformation,clsPrimaryInformation>> moMergedPrimaryInformation_Input_old; 
	public ArrayList<clsPrimaryInformation> moNewPrimaryInformation_old; 
	
	public ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>> moMergedPrimaryInformation_Input;
	public ArrayList<clsPrimaryDataStructureContainer> moNewPrimaryInformation; 
	
	public E18_GenerationOfAffectsForPerception(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer, clsInterfaceHandler poInterfaceHandler) {
		super(poPrefix, poProp, poEnclosingContainer, poInterfaceHandler);
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
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poMergedPrimaryInformation_old,
			  ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>> poMergedPrimaryInformation) {
		moMergedPrimaryInformation_Input_old = (ArrayList<clsPair<clsPrimaryInformation,clsPrimaryInformation>>)deepCopy(poMergedPrimaryInformation_old);
		moMergedPrimaryInformation_Input = (ArrayList<clsPair<clsPrimaryDataStructureContainer, clsPrimaryDataStructureContainer>>)deepCopy(poMergedPrimaryInformation);
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
		defineOutput(); 
		//moMergedPrimaryInformation_Output = moMergedPrimaryInformation_Input; 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.10.2009, 10:10:37
	 *
	 * @return
	 */
	private void defineOutput() {
		moNewPrimaryInformation_old = new ArrayList<clsPrimaryInformation>(); 
		
		for(clsPair<clsPrimaryInformation, clsPrimaryInformation> oElement : moMergedPrimaryInformation_Input_old){
			if(oElement.b != null){
				moNewPrimaryInformation_old.add(calculateAffect(oElement));
			}
			else{
				moNewPrimaryInformation_old.add(oElement.a); 
			}
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 24.10.2009, 16:20:48
	 * @param oPrimaryInfoPlusAffect 
	 * @param poElement 
	 *
	 * @param associationElement
	 */
	private clsPrimaryInformationMesh calculateAffect(clsPair<clsPrimaryInformation, clsPrimaryInformation> poElement) {
		clsPrimaryInformationMesh oPrimaryInfoPlusAffect = (clsPrimaryInformationMesh)poElement.a; 
		for(clsAssociation<clsPrimaryInformation> oAssociationElement : oPrimaryInfoPlusAffect.moAssociations){
			if(oAssociationElement instanceof clsAssociationContent){
				//FIXME HZ - this mechanism has to be reconsidered
				double nAffectValue = (oAssociationElement.moElementB.moAffect.getValue()+poElement.b.moAffect.getValue())/2; 
				oPrimaryInfoPlusAffect.moAffect = new clsAffectMemory(nAffectValue); 
			}		
		}
		
		return oPrimaryInfoPlusAffect; 
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
		//HZ: null is a placeholder for the bjects of the type pa.memorymgmt.datatypes
		send_I2_9(moNewPrimaryInformation_old,moNewPrimaryInformation);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:38:33
	 * 
	 * @see pa.interfaces.send.I2_9_send#send_I2_9(java.util.ArrayList)
	 */
	@Override
	public void send_I2_9(
				ArrayList<clsPrimaryInformation> poMergedPrimaryInformation_old,
				ArrayList<clsPrimaryDataStructureContainer> poMergedPrimaryInformation) {
		((I2_9_receive)moEnclosingContainer).receive_I2_9(moNewPrimaryInformation_old, moNewPrimaryInformation);
		
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
		// TODO (deutsch) - Auto-generated method stub
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
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}
}
