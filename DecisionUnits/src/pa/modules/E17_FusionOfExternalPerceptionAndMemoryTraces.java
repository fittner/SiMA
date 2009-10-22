/**
 * E17_FusionOfExternalPerceptionAndMemoryTraces.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:32:41
 */
package pa.modules;

import java.util.ArrayList;

import config.clsBWProperties;
import pa.datatypes.clsAssociationContent;
import pa.datatypes.clsPrimaryInformation;
import pa.datatypes.clsPrimaryInformationMesh;
import pa.interfaces.I2_7;
import pa.interfaces.I2_8;
import pa.tools.clsPair;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:32:41
 * 
 */
public class E17_FusionOfExternalPerceptionAndMemoryTraces extends clsModuleBase implements 
						I2_7
						{

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:33:08
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	ArrayList<clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> moPerceptPlusAwareContent_Input;
	ArrayList<clsPrimaryInformationMesh> moMergedPrimaryInformation_Output; 
		
	public E17_FusionOfExternalPerceptionAndMemoryTraces(String poPrefix,
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
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:33:13
	 * 
	 * @see pa.interfaces.I2_7#receive_I2_7(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_7(ArrayList<clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> poPerceptPlusAwareContent_Input) {
		moPerceptPlusAwareContent_Input = (ArrayList<clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>>>)deepCopy(poPerceptPlusAwareContent_Input); 
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:54
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		moMergedPrimaryInformation_Output = new ArrayList<clsPrimaryInformationMesh>(); 
		mergePrimaryInformation(); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 21.10.2009, 12:23:40
	 *
	 */
	private void mergePrimaryInformation() {
		for(clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>> oElement : moPerceptPlusAwareContent_Input){
			moMergedPrimaryInformation_Output.add(getMergedMesh(oElement)); 
		}
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 21.10.2009, 14:32:20
	 *
	 * @param element
	 * @return
	 */
	private clsPrimaryInformationMesh getMergedMesh(clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>> poElement) {
			
		clsPrimaryInformationMesh oMergedMesh = getNewMesh(poElement.a); 
			
	    for(clsPrimaryInformation oAwareContent : poElement.b){
	    	oMergedMesh.moTP.meContentName = oMergedMesh.moTP.meContentName + "_" + oAwareContent.moTP.meContentName;
	    	oMergedMesh.moTP.moContent = oMergedMesh.moTP.moContent +"_" + oAwareContent.moTP.moContent; 
			clsAssociationContent<clsPrimaryInformation> oAssociationContent_AC = new clsAssociationContent<clsPrimaryInformation>(); 
			oAssociationContent_AC.moElementA = oMergedMesh; 
			oAssociationContent_AC.moElementB = oAwareContent; 
			//FIXME HZ Define Weight and Context, respectively a new type of association
			//oAssociationContext.moWeight =
			//oAssociationContent.moAssociationContent;  
			oMergedMesh.moAssociations.add(oAssociationContent_AC);
		}
		return oMergedMesh;
	}

	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 21.10.2009, 15:43:17
	 *
	 * @param a
	 * @return
	 */
	private clsPrimaryInformationMesh getNewMesh(clsPrimaryInformation poPerceivedObject) {
		clsPrimaryInformationMesh oMergedMesh; 
		
		if(poPerceivedObject instanceof clsPrimaryInformationMesh){
			oMergedMesh =(clsPrimaryInformationMesh)poPerceivedObject; 
		}
		else{
			oMergedMesh = new clsPrimaryInformationMesh(poPerceivedObject.moTP); 
			oMergedMesh.moAffect = poPerceivedObject.moAffect; 
		}
		return oMergedMesh;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:54
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I2_8)moEnclosingContainer).receive_I2_8(moMergedPrimaryInformation_Output);
	}

}
