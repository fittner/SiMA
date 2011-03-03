/**
 * E20_InnerPerception_Affects.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:40:29
 */
package pa.modules._v30;

import java.util.ArrayList;
import java.util.HashMap;
import config.clsBWProperties;
import pa.interfaces.receive._v30.I5_1_receive;
import pa.interfaces.receive._v30.I5_2_receive;
import pa.interfaces.receive._v30.I5_3_receive;
import pa.interfaces.receive._v30.I5_4_receive;
import pa.interfaces.receive._v30.I5_5_receive;
import pa.interfaces.send._v30.I5_5_send;
import pa.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa.memorymgmt.datatypes.clsSecondaryDataStructureContainer;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:40:29
 * 
 */
public class E20_InnerPerception_Affects extends clsModuleBase implements 
					I5_1_receive, I5_2_receive, I5_3_receive, I5_4_receive, I5_5_send {
	public static final String P_MODULENUMBER = "20";
	
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 03.03.2011, 16:45:56
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public E20_InnerPerception_Affects(String poPrefix, clsBWProperties poProp,
			HashMap<Integer, clsModuleBase> poModuleList) throws Exception {
		super(poPrefix, poProp, poModuleList);
		applyProperties(poPrefix, poProp);
	}

	//private ArrayList<clsPrimaryDataStructureContainer> moAffectOnlyList;
	//private ArrayList<clsAssociationDriveMesh> moDeniedAffects_Input;
	//private ArrayList<clsSecondaryDataStructureContainer> moPerception; 
	//private ArrayList<clsSecondaryDataStructureContainer> moDriveList_Input;


	
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
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_1#receive_I5_1(int)
	 */
	@Override
	public void receive_I5_1(ArrayList<clsPrimaryDataStructureContainer> poAffectOnlyList) {
		//moAffectOnlyList_old = (ArrayList<clsAffectTension>)this.deepCopy(poAffectOnlyList_old);
		//moAffectOnlyList = (ArrayList<clsPrimaryDataStructureContainer>)this.deepCopy(poAffectOnlyList);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_2#receive_I5_2(int)
	 */
	@Override
	public void receive_I5_2(ArrayList<clsAssociationDriveMesh> poDeniedAffects) {
		//moDeniedAffects_Input_old  = (ArrayList<clsAffectTension>)this.deepCopy(poDeniedAffects_old);
		//moDeniedAffects_Input  = (ArrayList<clsAssociationDriveMesh>)this.deepCopy(poDeniedAffects);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_3#receive_I5_3(int)
	 */
	@Override
	public void receive_I5_3(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		//moDriveList_Input_old = (ArrayList<clsSecondaryInformation>)this.deepCopy(poDriveList_old);
		//moDriveList_Input = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poDriveList);		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 14:41:15
	 * 
	 * @see pa.interfaces.I5_4#receive_I5_4(int)
	 */
	@Override
	public void receive_I5_4(ArrayList<clsSecondaryDataStructureContainer> poPerception) {
		//moPerception_old = (ArrayList<clsSecondaryInformation>)this.deepCopy(poPerception_old);
		//moPerception = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poPerception);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:08
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		mnTest++;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:16:08
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_5(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:46:11
	 * 
	 * @see pa.interfaces.send.I5_5_send#send_I5_5(int)
	 */
	@Override
	public void send_I5_5(int pnData) {
		((I5_5_receive)moModuleList.get(29)).receive_I5_5(mnTest);
		((I5_5_receive)moModuleList.get(26)).receive_I5_5(mnTest);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 12.07.2010, 10:47:00
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
	 * 12.07.2010, 10:47:00
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (deutsch) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 03.03.2011, 16:46:02
	 * 
	 * @see pa.modules._v30.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}

}
