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
import config.clsProperties;
import pa._v38.memorymgmt.datatypes.clsDataStructureContainer;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;
import pa._v38.interfaces.modules.I6_3_receive;
import pa._v38.interfaces.modules.I6_1_receive;
import pa._v38.interfaces.modules.I6_6_receive;
import pa._v38.interfaces.modules.I6_6_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsSecondaryDataStructureContainer;
import pa._v38.tools.clsAffectTools;
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
public class F23_ExternalPerception_focused extends clsModuleBase implements I6_1_receive, I6_3_receive, I6_6_send {
	public static final String P_MODULENUMBER = "23";
	
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:35 */
	private ArrayList<clsDataStructureContainer> moEnvironmentalPerception_IN;
	//AW 20110602 New input of the module
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:37 */
	private ArrayList<clsDataStructureContainer> moAssociatedMemoriesSecondary_IN;
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:39 */
	private ArrayList<clsSecondaryDataStructureContainer> moDriveList; 
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:55:40 */
	private ArrayList<clsDataStructureContainer> moEnvironmentalPerception_OUT; 
	/** DOCUMENT (wendt) - insert description; @since 04.08.2011 13:56:18 */
	private ArrayList<clsDataStructureContainer> moAssociatedMemoriesSecondary_OUT;
	
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
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
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
		
		text += toText.listToTEXT("moEnvironmentalPerception_IN", moEnvironmentalPerception_IN);
		text += toText.listToTEXT("moAssociatedMemoriesSecondary_IN", moAssociatedMemoriesSecondary_IN);
		text += toText.listToTEXT("moDriveList", moDriveList);
		text += toText.listToTEXT("moEnvironmentalPerception_OUT", moEnvironmentalPerception_OUT);
		text += toText.listToTEXT("moAssociatedMemoriesSecondary_OUT", moAssociatedMemoriesSecondary_OUT);
		
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
	public void receive_I6_1(ArrayList<clsDataStructureContainer> poPerception, ArrayList<clsDataStructureContainer> poAssociatedMemoriesSecondary) {
		moEnvironmentalPerception_IN = (ArrayList<clsDataStructureContainer>)this.deepCopy(poPerception);
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
		
		moEnvironmentalPerception_OUT = new ArrayList<clsDataStructureContainer>();
		
		for (clsDataStructureContainer oCont : moEnvironmentalPerception_IN) {
			if (oCont instanceof clsSecondaryDataStructureContainer) {
				clsSecondaryDataStructureContainer oFocusedPerception = focusPerception((clsSecondaryDataStructureContainer) oCont);
				moEnvironmentalPerception_OUT.add(oFocusedPerception);
			} else if (oCont instanceof clsPrimaryDataStructureContainer) {
				moEnvironmentalPerception_OUT.add(oCont);
			}
		}
		
		
		
		
		
		
		//TODO HZ 23.08.2010: Normally the perceived information has to be ordered by its priority
		//that depends on the evaluation of external and internal perception (moDriveList); 
		//
		//Actual state: no ordering! 
		
		/*boolean switched = false;
		clsSecondaryDataStructureContainer sdsc;
		
		if (!moPerception.isEmpty())
		{
			//bubblesort; if you want quicksort... have at it 
			do
			{
				switched = false;
				for (int i = 0; i < moPerception.size() - 1; i++)
				
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
		}*/
		
		
		//moEnvironmentalPerception_OUT = moEnvironmentalPerception_IN;
		moAssociatedMemoriesSecondary_OUT = moAssociatedMemoriesSecondary_IN;
	}
	
	/**
	 * All drives within the perceived images are extracted and sorted. Only the 20 (or another number) of objects are passed
	 * DOCUMENT (wendt) - insert description
	 *
	 * @since 07.08.2011 23:05:42
	 *
	 * @param poPerceptionSeondary
	 * @return
	 */
	private clsSecondaryDataStructureContainer focusPerception(clsSecondaryDataStructureContainer poPerceptionSecondary) {
		clsSecondaryDataStructureContainer oRetVal = null;
		
		try {
			ArrayList<clsSecondaryDataStructureContainer> oDriveGoals = clsAffectTools.getWPMDriveGoals(poPerceptionSecondary);
			//ArrayList<clsSecondaryDataStructureContainer> oSortedDriveGoals  = clsAffectTools.sortDriveDemands(oDriveGoals);
		} catch (Exception e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		oRetVal = poPerceptionSecondary;
				
		
		return oRetVal;
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
		send_I6_6(moEnvironmentalPerception_OUT, moDriveList, moAssociatedMemoriesSecondary_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author kohlhauser
	 * 18.05.2010, 17:50:35
	 * 
	 * @see pa.interfaces.send.I2_12_send#send_I2_12(java.util.ArrayList)
	 */
	@Override
	public void send_I6_6(ArrayList<clsDataStructureContainer> poFocusedPerception,
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
	
}
