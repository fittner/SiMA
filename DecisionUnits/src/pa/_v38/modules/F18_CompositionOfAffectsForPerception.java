/**
 * E18_GenerationOfAffectsForPerception.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 */
package pa._v38.modules;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.ListIterator;
import java.util.SortedMap;
import config.clsProperties;
import pa._v38.tools.clsDataStructureTools;
import pa._v38.tools.clsPair;
import pa._v38.tools.toText;
import pa._v38.interfaces.modules.I5_9_receive;
import pa._v38.interfaces.modules.I5_10_receive;
import pa._v38.interfaces.modules.I5_10_send;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 * 
 */
public class F18_CompositionOfAffectsForPerception extends clsModuleBase implements I5_9_receive, I5_10_send {
	public static final String P_MODULENUMBER = "18";
	
	private clsThingPresentationMesh moPerceptionalMesh_IN;
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	private clsThingPresentationMesh moPerceptionalMesh_OUT;
	//private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;

	/**
	 * F18_CompositionOfAffectsForPerception - merge all DMs by type comparison
	 * 
	 * @author wendt
	 * 03.03.2011, 16:33:27
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poModuleList
	 * @throws Exception
	 */
	public F18_CompositionOfAffectsForPerception(String poPrefix,
			clsProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
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
		
		text += toText.valueToTEXT("moPerceptionalMesh_IN", moPerceptionalMesh_IN);
		//text += toText.valueToTEXT("moAssociatedMemories_IN", moAssociatedMemories_IN);
		text += toText.valueToTEXT("moPerceptionalMesh_OUT", moPerceptionalMesh_OUT);
		//text += toText.valueToTEXT("moAssociatedMemories_OUT", moAssociatedMemories_OUT);
		
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
	 * 11.08.2009, 16:15:59
	 * 
	 * @see pa.modules.clsModuleBase#process()
	 */
	@Override
	protected void process_basic() {
		//Merge Drive meshes for environmental perception
		//moEnvironmentalPerception_OUT = mergeDriveMeshes(moEnvironmentalPerception_IN);
		
		//Merge Drive meshes for all associated content
		
		moPerceptionalMesh_OUT = mergeAssMemoryDriveMeshes(moPerceptionalMesh_IN);
		//moAssociatedMemories_OUT = moAssociatedMemories_IN;
	}
	
	/**
	 * Merge DMs are done for all associated memories too
	 *
	 * @since 12.07.2011 16:15:47
	 *
	 * @param oInput
	 * @return
	 */
	private clsThingPresentationMesh mergeAssMemoryDriveMeshes(clsThingPresentationMesh poInput) {
		clsThingPresentationMesh oRetVal = null;
		try {
			oRetVal = (clsThingPresentationMesh) poInput.cloneGraph();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		
		//RetVal is changed and returned. The images of oImages are for oRetVal
		ArrayList<clsThingPresentationMesh> oImages = clsDataStructureTools.getAllTPMImages(oRetVal, 2);
		
		for (clsThingPresentationMesh oImage : oImages) {
			mergeDriveMeshesImage(oImage);
		}
		return oRetVal;
	}
	
	/**
	 * Drives are merged if they are from the same type, i. e. if the content type is equal, then the mrPleasure is summarized
	 *
	 * @since 12.07.2011 16:16:34
	 *
	 * @param oInput
	 * @return
	 */
	private void mergeDriveMeshesForObject(clsThingPresentationMesh poInput) {
		/* DMs are added from F35 and F45 to the PDSC. In this function, all values, which are from the same
		 * type are summarized, e. g. moContentType is equal
		 */
		
		//FIXME someone: All parts of this function are on the same level
		
		//Create a new empty List of associations, in which the modified associations are added
		ArrayList<clsAssociation> oNewAss = new ArrayList<clsAssociation>();
		
		//For each association to a template image in the template image
				
		/* Create an Arraylist with booleans in order to only add the elements, which are set true
		 * This is used for avoiding to add an association twice
		 */
		ArrayList<clsPair<clsAssociation, Boolean>> oArrAssFirst = new ArrayList<clsPair<clsAssociation, Boolean>>();
		for (int i=0;i<poInput.getExternalMoAssociatedContent().size();i++) {
			oArrAssFirst.add(new clsPair<clsAssociation, Boolean>(poInput.getExternalMoAssociatedContent().get(i),false));
		}
		
		ListIterator<clsPair<clsAssociation, Boolean>> liMainList = oArrAssFirst.listIterator();
		ListIterator<clsPair<clsAssociation, Boolean>> liSubList;
		clsAssociation oFirstAss;
		//Go through each element in the first list
		while (liMainList.hasNext()) {
			clsPair<clsAssociation, Boolean> oFirstAssPair = liMainList.next();
			oFirstAss = oFirstAssPair.a;
			
			if ((oFirstAss instanceof clsAssociationDriveMesh) && (oFirstAssPair.b == false)) {
				//Get a DM from the associated content
				clsDriveMesh oFirstDM = (clsDriveMesh)oFirstAss.getLeafElement();
				
				/* Here the new content is set depending on the highest level of total quota of affect
				 * of all equal drive mesh types in the object. If another object has a higher
				 * mrPleasure, its content is taken.
				 */
				double rMaxTotalQuotaOfAffect = oFirstDM.getMrPleasure();	//FIXME Add Unpleasure too
				String sMaxContent = oFirstDM.getMoContent();
						
				//Go through all following associations of that object
				//Iterator<clsAssociation> oArrAssSecond = ;
				//Go to the position of the first element
				
				liSubList = oArrAssFirst.listIterator(liMainList.nextIndex());
				//for (int i=0;i<=iStartIndex;i++) {
				//	it2.next();
				//}
				
				while (liSubList.hasNext()) {
					clsPair<clsAssociation, Boolean> oSecondAssPair = liSubList.next();
					clsAssociation oSecondAss = oSecondAssPair.a;
					//Boolean oDelete = oSecondAssPair.b;
					
					//If the DM belongs to the same TPM oder TP AND it is a DM and it has not been used yet
					if ((oFirstAss.getRootElement().getMoDSInstance_ID() == oSecondAss.getRootElement().getMoDSInstance_ID()) && (oSecondAss instanceof clsAssociationDriveMesh)  
							&& (oSecondAssPair.b == false)) {	
						clsDriveMesh oSecondDM = (clsDriveMesh)oSecondAss.getLeafElement();
						//firstAssociation is compared with the secondAssociation
						//If the content type of the DM are equal then
						if (oFirstDM.getMoContentType().intern() == oSecondDM.getMoContentType().intern()) {
							//1. Add mrPleasure from the second to the first DM
							double mrNewPleasure = setNewQuotaOfAffectValue(oFirstDM.getMrPleasure(), oSecondDM.getMrPleasure());
							oFirstDM.setMrPleasure(mrNewPleasure);
							//Set second DM as used (true)
							oSecondAssPair.b = true;
							//2. Add Unpleasure from second to first DM
							//FIXME: AW 20110528: Add unpleasure too?
							//oFirstDM.setMrPleasure(mrNewUnpleasure);
							//3. Check if the quota of affect is higher for the second DM and exchange content
						
							if (java.lang.Math.abs(oSecondDM.getMrPleasure()) > java.lang.Math.abs(rMaxTotalQuotaOfAffect)) {
								//FIXME: Corrent the function to consider mrUnpleasure too
								sMaxContent = oSecondDM.getMoContent();
								rMaxTotalQuotaOfAffect = oSecondDM.getMrPleasure();
							}
						}
					}
				}
				//Set new content if different, in order to set the content to the one with the highest mrPleasure
				if (oFirstDM.getMoContent().equals(sMaxContent)==false) {
					oFirstDM.setMoContent(sMaxContent);
				}
			}
			//Add the association to the list if it has not been used yet
			if (oFirstAssPair.b == false) {
				oNewAss.add(oFirstAss);
			}
		}
		//clsPrimaryDataStructureContainer oMergedResult = new clsPrimaryDataStructureContainer(oInput.getMoDataStructure(), oNewAss);
		
		//Replace the old external associations with the smaller new list
		poInput.setMoExternalAssociatedContent(oNewAss);
		//return oMergedResult;
	}
	
	/**
	 * Merge drive meshes for a single image
	 * 
	 * (wendt)
	 *
	 * @since 27.12.2011 23:40:43
	 *
	 * @param poImage
	 */
	private void mergeDriveMeshesImage(clsThingPresentationMesh poImage) {
		ArrayList<clsThingPresentationMesh> oAllObjects = clsDataStructureTools.getAllTPMObjects(poImage, 1); 
		
		for (clsThingPresentationMesh oObject : oAllObjects) {
			mergeDriveMeshesForObject(oObject);
		}
	}
	
	
	/**
	 * This function was made in order to be able to set the calculation function of the total 
	 * quota of affect separately
	 *
	 * @since 12.07.2011 16:18:10
	 *
	 * @param rOriginal
	 * @param rAddValue
	 * @return
	 */
	private double setNewQuotaOfAffectValue(double rOriginal, double rAddValue) {
		// This function was made in order to be able to set the calculation function of the total 
		//  quota of affect separately 
		//
		
		//Averaging is used and normalization of quota of affect, as this quota of affect is only the "erinnerte Besetzung" and does not follow the law
		//of constanct quota of affect
		return (rOriginal + rAddValue);
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
		send_I5_10(moPerceptionalMesh_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:38:33
	 * 
	 * @see pa.interfaces.send.I2_9_send#send_I2_9(java.util.ArrayList)
	 */
	@Override
	public void send_I5_10(clsThingPresentationMesh poPerceptionalMesh) {
		((I5_10_receive)moModuleList.get(7)).receive_I5_10(poPerceptionalMesh);
		
		putInterfaceData(I5_10_send.class, poPerceptionalMesh);
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
	 * @see pa.modules._v38.clsModuleBase#setModuleNumber()
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
	 * @see pa.interfaces.receive._v38.I2_16_receive#receive_I2_16(java.util.ArrayList)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public void receive_I5_9(clsThingPresentationMesh poPerceptionalMesh) {
		try {
			moPerceptionalMesh_IN = (clsThingPresentationMesh) poPerceptionalMesh.cloneGraph();
		} catch (CloneNotSupportedException e) {
			// TODO (wendt) - Auto-generated catch block
			e.printStackTrace();
		}
		//moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
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
		moDescription = "The value for the quota of affects for perception thing presentations is calculating by looking up all associated unpleasure and pleasure values retrieved from memory in {F46}, {F37} and {F35}. Pleasure gained in {F45} is considered too. ";
	}		
}
