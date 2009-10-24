/**
 * E18_GenerationOfAffectsForPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.datatypes.clsAffectMemory;
import pa.datatypes.clsAssociation;
import pa.datatypes.clsAssociationContent;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.interfaces.I2_9;
import pa.interfaces.I2_8;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 * 
 */
public class E18_GenerationOfAffectsForPerception extends clsModuleBase implements I2_8 {

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
	
	public ArrayList<clsPair<clsPrimaryInformation,clsPrimaryInformation>> moMergedPrimaryInformation_Input; 
	public ArrayList<clsPrimaryInformation> moNewPrimaryInformation; 
	
	public E18_GenerationOfAffectsForPerception(String poPrefix,
			clsBWProperties poProp, clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);		
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		// String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		//nothing to do
				
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
	public void receive_I2_8(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poMergedPrimaryInformationMesh) {
		moMergedPrimaryInformation_Input = (ArrayList<clsPair<clsPrimaryInformation,clsPrimaryInformation>>)deepCopy(poMergedPrimaryInformationMesh);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:59
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
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
		moNewPrimaryInformation = new ArrayList<clsPrimaryInformation>(); 
		
		for(clsPair<clsPrimaryInformation, clsPrimaryInformation> oElement : moMergedPrimaryInformation_Input){
			if(oElement.b != null){
				moNewPrimaryInformation.add(calculateAffect(oElement));
			}
			else{
				moNewPrimaryInformation.add(oElement.a); 
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
		((I2_9)moEnclosingContainer).receive_I2_9(moNewPrimaryInformation);
		
	}
}
