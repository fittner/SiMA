/**
 * E23_ExternalPerception_focused.java: DecisionUnits - pa.modules
 * 
 * @author kohlhauser
 * 11.08.2009, 14:46:53
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedMap;
import config.clsBWProperties;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.interfaces.itfMinimalModelMode;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_1_receive;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_6_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.tools.toText;

/**
 * The task of this module is to focus the external perception on ``important'' things. Thus, the word presentations originating from perception are ordered according to their importance to existing drive wishes. This could mean for example that an object is qualified to satisfy a bodily need. The resulting listthe package of word presentation, thing presentation, and drive whishes for each perception ordered descending by their importanceis forwarded by the interface {I2.12} to {E24} and {E25}. These two modules are part of reality check. 
 * 
 * TODO (kohlhauser) - freie energie irgendwie einarbeiten
 * 
 * @author kohlhauser
 * 11.08.2009, 14:46:53
 * 
 */
public class F23_ExternalPerception_focused extends clsModuleBase implements itfMinimalModelMode, I6_1_receive, I6_3_receive, I6_6_send {
	public static final String P_MODULENUMBER = "23";
	
	private ArrayList<clsSecondaryDataStructureContainer> moPerception;
	//AW 20110602 New input of the module
	private ArrayList<clsDataStructureContainer> moAssociatedMemoriesSecondary_IN;
	
	
	
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList; 
	private ArrayList<clsSecondaryDataStructureContainer> moFocusedPerception_Output; 
	
	//AW 20110602 New output of the module
	private ArrayList<clsDataStructureContainer> moAssociatedMemoriesSecondary_OUT;
	
	private boolean mnMinimalModel;
	/**
	 * DOCUMENT (KOHLHAUSER) - insert description 
	 * 
	 * @author kohlhauser
	 * 03.03.2011, 16:50:08
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F23_ExternalPerception_focused(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
			throws Exception {
		super(poPrefix, poProp, poModuleList, poInterfaceData);
		applyProperties(poPrefix, poProp);		
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 14.04.2011, 17:36:19
	 * 
	 * @see pa.modules._v38.clsModuleBase#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {		
		String text = "";
		
		text += toText.valueToTEXT("mnMinimalModel", mnMinimalModel);
		text += toText.listToTEXT("moPerception", moPerception);
		text += toText.listToTEXT("moDriveList", moDriveList);
		text += toText.listToTEXT("moFocusedPerception_Output", moFocusedPerception_Output);

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
		mnMinimalModel = false;
		//nothing to do
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 12:09:34
	 * 
	 * @see pa.modules.clsModuleBase#setProcessType()
	 */
	@Override
	protected void setProcessType() {
		mnProcessType = eProcessType.SECONDARY;
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
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
	 * @author kohlhauser
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I2_11#receive_I2_11(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_1(ArrayList<clsSecondaryDataStructureContainer> poPerception, ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary) {
		moPerception = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poPerception);
		//AW 20110602 Added Associtated memories
		moAssociatedMemoriesSecondary_IN = (ArrayList<clsDataStructureContainer>)this.deepCopy(poAssociatedMemoriesSecondary);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 14:47:49
	 * 
	 * @see pa.interfaces.I1_7#receive_I1_7(int)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I6_3(ArrayList<clsSecondaryDataStructureContainer> poDriveList) {
		moDriveList = (ArrayList<clsSecondaryDataStructureContainer>)this.deepCopy(poDriveList);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		if (!mnMinimalModel) {				
			//TODO HZ 23.08.2010: Normally the perceived information has to be ordered by its priority
			//that depends on the evaluation of external and internal perception (moDriveList); 
			//
			//Actual state: no ordering! 
			
			boolean switched = false;
			clsSecondaryDataStructureContainer sdsc;
			
			if (!moPerception.isEmpty())
			{
				//bubblesort; if you want quicksort... have at it 
				do
				{
					switched = false;
					for (int i = 0; i < moPerception.size() - 1; i++)
					{
						//AW 20110618 FIXME: Sometimes it crashes on get(i+1) and get(2). i+1 should be checked first and why is get(2) used?
						//Correct this START
						if (i+1 < moPerception.size()) {
							if ((moPerception.get(i).getMoAssociatedDataStructures().size()>2) && (moPerception.get(i + 1).getMoAssociatedDataStructures().size()>2)){
								if (((clsDriveMesh) moPerception.get(i).getMoAssociatedDataStructures().get(2).getMoAssociationElementB()).getMrPleasure() <
										((clsDriveMesh) moPerception.get(i + 1).getMoAssociatedDataStructures().get(2).getMoAssociationElementB()).getMrPleasure())
										{
											sdsc = moPerception.get(i);
											moPerception.remove(i);
											moPerception.add(i + 1, sdsc);
											switched = true;
										}
							}
						}
						//Correct this END
					}
				} while (switched == true);
			}
			
			moFocusedPerception_Output = moPerception;
			moAssociatedMemoriesSecondary_OUT = moAssociatedMemoriesSecondary_IN;
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 11.08.2009, 16:16:20
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		if (mnMinimalModel) {		
			send_I6_6(moPerception, new ArrayList<clsSecondaryDataStructureContainer>(), new ArrayList<clsDataStructureContainer>());
		} else {
			send_I6_6(moFocusedPerception_Output, moDriveList, moAssociatedMemoriesSecondary_OUT);
		}
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:50:35
	 * 
	 * @see pa.interfaces.send.I2_12_send#send_I2_12(java.util.ArrayList)
	 */
	@Override
	public void send_I6_6(ArrayList<clsSecondaryDataStructureContainer> poFocusedPerception,
			   				ArrayList<clsSecondaryDataStructureContainer> poDriveList,
			   				ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary_OUT) {
		((I6_6_receive)moModuleList.get(51)).receive_I6_6(poFocusedPerception, poDriveList, poAssociatedMemoriesSecondary_OUT);
		
		putInterfaceData(I6_6_send.class, poFocusedPerception, poDriveList, poAssociatedMemoriesSecondary_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:20
	 * 
	 * @see pa.modules.clsModuleBase#process_draft()
	 */
	@Override
	protected void process_draft() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 12.07.2010, 10:47:20
	 * 
	 * @see pa.modules.clsModuleBase#process_final()
	 */
	@Override
	protected void process_final() {
		// TODO (KOHLHAUSER) - Auto-generated method stub
		throw new java.lang.NoSuchMethodError();
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 03.03.2011, 16:50:13
	 * 
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
	 */
	@Override
	protected void setModuleNumber() {
		mnModuleNumber = Integer.parseInt(P_MODULENUMBER);
		
	}
	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 15.04.2011, 13:52:57
	 * 
	 * @see pa.modules._v38.clsModuleBase#setDescription()
	 */
	@Override
	public void setDescription() {
		moDescription = "The task of this module is to focus the external perception on ``important'' things. Thus, the word presentations originating from perception are ordered according to their importance to existing drive wishes. This could mean for example that an object is qualified to satisfy a bodily need. The resulting listthe package of word presentation, thing presentation, and drive whishes for each perception ordered descending by their importanceis forwarded by the interface {I2.12} to {E24} and {E25}. These two modules are part of reality check.";
	}	
	
	@Override
	public void setMinimalModelMode(boolean pnMinial) {
		mnMinimalModel = pnMinial;
	}

	@Override
	public boolean getMinimalModelMode() {
		return mnMinimalModel;
	}	
	
}
