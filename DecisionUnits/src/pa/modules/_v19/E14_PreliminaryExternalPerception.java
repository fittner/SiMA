/**
 * E14_PreliminaryExternalPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:26:13
 */
package pa.modules._v19;

import java.util.ArrayList;
import java.util.HashMap;

import config.clsBWProperties;

import pa._v19.clsInterfaceHandler;
import pa.enums.eSymbolExtType;
import pa.interfaces.receive._v19.I2_2_receive;
import pa.interfaces.receive._v19.I2_4_receive;
import pa.interfaces.receive._v19.I2_5_receive;
import pa.interfaces.send._v19.I2_5_send;
import pa.memorymgmt.datahandler.clsDataStructureConverter;
import pa.memorymgmt.datatypes.clsPrimaryDataStructure;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.symbolization.representationsymbol.itfSymbol;

/**
 * 
 * 
 * @author deutsch
 * 11.08.2009, 14:26:13
 * 
 */
public class E14_PreliminaryExternalPerception extends clsModuleBase implements 
					I2_2_receive, 
					I2_4_receive,
					I2_5_send
					{
	
	HashMap<eSymbolExtType, itfSymbol> moEnvironmentalData;
	HashMap<eSymbolExtType, itfSymbol> moBodyData;
	
	public ArrayList<clsPrimaryDataStructureContainer> moEnvironmentalTP; 

	/**
	 * 
	 * 
	 * @author deutsch
	 * 11.08.2009, 14:26:43
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public E14_PreliminaryExternalPerception(String poPrefix,
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
		mnPsychicInstances = ePsychicInstances.EGO;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:27:13
	 * 
	 * @see pa.interfaces.I2_2#receive_I2_2(int)
	 */
	@Override
	public void receive_I2_2(HashMap<eSymbolExtType, itfSymbol> poEnvironmentalData) {
		moEnvironmentalData = poEnvironmentalData;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:27:13
	 * 
	 * @see pa.interfaces.I2_4#receive_I2_4(int)
	 */
	@Override
	public void receive_I2_4(HashMap<eSymbolExtType, itfSymbol> poBodyData) {
		moBodyData = poBodyData;
		
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
		moEnvironmentalTP = new ArrayList<clsPrimaryDataStructureContainer>(); 
		for(itfSymbol oSymbol : moEnvironmentalData.values()){
				if(oSymbol!=null){
					for(itfSymbol oSymbolObject : oSymbol.getSymbolObjects()) {
						clsPrimaryDataStructure oDataStructure = (clsPrimaryDataStructure)clsDataStructureConverter.convertExtSymbolsToPsychicDataStructures(oSymbolObject); 
						moEnvironmentalTP.add(new clsPrimaryDataStructureContainer(oDataStructure,null));
					}	
				}
		}
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
		send_I2_5(moEnvironmentalTP);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 16:55:55
	 * 
	 * @see pa.interfaces.send.I2_5_send#send_I2_5(java.util.ArrayList)
	 */
	@Override
	public void send_I2_5(ArrayList<clsPrimaryDataStructureContainer> poEnvironmentalTP) {
		((I2_5_receive)moEnclosingContainer).receive_I2_5(moEnvironmentalTP);
		
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
}
