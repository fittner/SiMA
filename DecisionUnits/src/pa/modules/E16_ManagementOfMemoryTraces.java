/**
 * E16_ManagmentOfMemoryTraces.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:31:19
 */
package pa.modules;

import java.util.ArrayList;
import java.util.HashMap;

import bfg.tools.clsMutableDouble;

import config.clsBWProperties;
import enums.eEntityType;
import enums.pa.eRepressedContentType;
import pa.datatypes.clsPrimaryInformation;
import pa.interfaces.I2_6;
import pa.interfaces.I2_7;
import pa.tools.clsPair;
import pa.tools.clsTripple;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:31:19
 * 
 */
public class E16_ManagementOfMemoryTraces extends clsModuleBase implements I2_6 {

	public ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> moPerceptPlusRepressed_Input;
	public ArrayList<clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> moPerceptPlusMemories_Output;
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:31:37
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E16_ManagementOfMemoryTraces(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);	
		
		moPerceptPlusMemories_Output = new ArrayList<clsPair<clsPrimaryInformation,ArrayList<clsPrimaryInformation>>>();
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
	 * 11.08.2009, 14:31:53
	 * 
	 * @see pa.interfaces.I2_6#receive_I2_6(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I2_6(ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed) {
		moPerceptPlusRepressed_Input = (ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>>)deepCopy(poPerceptPlusRepressed);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:49
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process() {
		moPerceptPlusMemories_Output = getOutput(moPerceptPlusRepressed_Input); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.10.2009, 14:23:35
	 *
	 * @param moPerceptPlusRepressed_Input2
	 * @return
	 */
	private ArrayList<clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> getOutput(
			         ArrayList<clsPair<clsPrimaryInformation, clsPrimaryInformation>> poPerceptPlusRepressed_Input) {
		
		ArrayList<clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>>> oRetVal
								= new ArrayList<clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>>>(); 
		
		for(clsPair<clsPrimaryInformation, clsPrimaryInformation>element : poPerceptPlusRepressed_Input){
			oRetVal.add(changeRepressedContent(element)); 
		}
		return oRetVal;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.10.2009, 14:48:40
	 *
	 * @param element
	 * @return
	 */
	private clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>> changeRepressedContent(
					clsPair<clsPrimaryInformation, clsPrimaryInformation> poInputElement) {
		
		ArrayList<clsPrimaryInformation> oAwareContentList = new ArrayList<clsPrimaryInformation>(); 
		
		if(poInputElement.a != null && poInputElement.b != null){
			oAwareContentList = getAwareContentList(poInputElement); 
			return new clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>>(poInputElement.a, oAwareContentList); 
		}
		return new clsPair<clsPrimaryInformation, ArrayList<clsPrimaryInformation>>(poInputElement.a, oAwareContentList); 
	}

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 20.10.2009, 15:18:01
	 *
	 * @param poInputElement
	 * @return
	 */
	
	//HashMap<clsPrimaryInformation, clsMutableDouble> oContextResult = moEnclosingContainer.moMemory.moCurrentContextStorage.getContextRatiosPrim(mrContextSensitivity);
	
	private ArrayList<clsPrimaryInformation> getAwareContentList(clsPair<clsPrimaryInformation, clsPrimaryInformation> poInputElement) {
		ArrayList <clsPrimaryInformation> oAwareContent = new ArrayList<clsPrimaryInformation>(); 
		eEntityType oEntityType = eEntityType.valueOf(poInputElement.a.moTP.moContent.toString()); 
		HashMap<clsPrimaryInformation, clsMutableDouble> oCurrentContextMap = this.moEnclosingContainer.moMemory.moCurrentContextStorage.getContextRatiosPrim(1); 
		eRepressedContentType oRepressedContentType = eRepressedContentType.valueOf(poInputElement.b.moTP.moContent.toString()); 
		
		for(clsPrimaryInformation oContextName : oCurrentContextMap.keySet()){
			String oCurrentContextType = oContextName.moTP.moContent.toString();
			
			oAwareContent.add(this.moEnclosingContainer.moMemory.moAwareContentsStore
					    			 .getMappedContent(new clsTripple<String, String, String>(oEntityType.toString(), 
							         oCurrentContextType.toString(),oRepressedContentType.toString()))); 
		}
		
		
		return oAwareContent; 
	 }
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:49
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		((I2_7)moEnclosingContainer).receive_I2_7(moPerceptPlusMemories_Output);
	}
}
