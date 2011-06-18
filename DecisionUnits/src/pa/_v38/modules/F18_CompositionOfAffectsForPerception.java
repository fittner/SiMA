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
import config.clsBWProperties;
import pa._v38.tools.clsPair;
import pa._v38.tools.clsTripple;
import pa._v38.tools.toText;
import pa._v38.interfaces.eInterfaces;
import pa._v38.interfaces.modules.I5_9_receive;
import pa._v38.interfaces.modules.I5_10_receive;
import pa._v38.interfaces.modules.I5_10_send;
import pa._v38.memorymgmt.datatypes.clsAssociation;
import pa._v38.memorymgmt.datatypes.clsAssociationDriveMesh;
import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import pa._v38.memorymgmt.datatypes.clsPrimaryDataStructureContainer;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 14:33:54
 * 
 */
public class F18_CompositionOfAffectsForPerception extends clsModuleBase implements I5_9_receive, I5_10_send {
	public static final String P_MODULENUMBER = "18";
	
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_IN;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_IN;
	
	private clsPrimaryDataStructureContainer moEnvironmentalPerception_OUT;
	private ArrayList<clsPrimaryDataStructureContainer> moAssociatedMemories_OUT;
	
	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moLibidoPleasureCandidates_IN;
	private ArrayList<clsPair<clsPrimaryDataStructureContainer, clsDriveMesh>> moPerception_IN;
	private ArrayList<clsTripple<clsPrimaryDataStructureContainer,clsDriveMesh,clsDriveMesh>> moMergedPrimaryInformation_Input;
	
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
	public F18_CompositionOfAffectsForPerception(String poPrefix,
			clsBWProperties poProp, HashMap<Integer, clsModuleBase> poModuleList, SortedMap<eInterfaces, ArrayList<Object>> poInterfaceData)
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
		
		text += toText.listToTEXT("moMergedPrimaryInformation_Input", moMergedPrimaryInformation_Input);
		text += toText.listToTEXT("moNewPrimaryInformation", moNewPrimaryInformation);
		text += toText.listToTEXT("moLibidoPleasureCandidates_IN", moLibidoPleasureCandidates_IN);
		text += toText.listToTEXT("moPerception_IN", moPerception_IN);
		
		
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
		//Merge inputs
		//mergeLists();
		//Merge quota of affect values original
		//adaptPleasureValue();
		//moNewPrimaryInformation = clsDataStructureConverter.convertTIContToTPMCont(moEnvironmentalPerception_IN);
		
		//Merge Drive meshes for environmental perception
		moEnvironmentalPerception_OUT = mergeDriveMeshes(moEnvironmentalPerception_IN);
		
		//Merge Drive meshes for all associated content
		moAssociatedMemories_OUT = mergeAssMemoryDriveMeshes(moAssociatedMemories_IN);
		//moAssociatedMemories_OUT = moAssociatedMemories_IN;
	}
	
	//AW 20110618 new function
	private ArrayList<clsPrimaryDataStructureContainer> mergeAssMemoryDriveMeshes(ArrayList<clsPrimaryDataStructureContainer> oInput) {
		ArrayList<clsPrimaryDataStructureContainer> oRetVal = new ArrayList<clsPrimaryDataStructureContainer>();	//The merged structures
		
		for (clsPrimaryDataStructureContainer oMemoryContainer : oInput) {
			oRetVal.add(mergeDriveMeshes(oMemoryContainer));
		}
		return oRetVal;
	}
	
	//AW 20110528 new function
	private clsPrimaryDataStructureContainer mergeDriveMeshes(clsPrimaryDataStructureContainer oInput) {
		/* DMs are added from F35 and F45 to the PDSC. In this function, all values, which are from the same
		 * type are summarized, e. g. moContentType is equal
		 */
		
		//Create a new empty List of associations, in which the modified associations are added
		ArrayList<clsAssociation> oNewAss = new ArrayList<clsAssociation>();
		
		//For each association to a template image in the template image
				
		/* Create an Arraylist with booleans in order to only add the elements, which are set true
		 * This is used for avoiding to add an association twice
		 */
		ArrayList<clsPair<clsAssociation, Boolean>> oArrAssFirst = new ArrayList<clsPair<clsAssociation, Boolean>>();
		for (int i=0;i<oInput.getMoAssociatedDataStructures().size();i++) {
			oArrAssFirst.add(new clsPair<clsAssociation, Boolean>(oInput.getMoAssociatedDataStructures().get(i),false));
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
					if ((oFirstAss.getRootElement().getMoDS_ID() == oSecondAss.getRootElement().getMoDS_ID()) && (oSecondAss instanceof clsAssociationDriveMesh)  
							&& (oSecondAssPair.b == false)) {	
						clsDriveMesh oSecondDM = (clsDriveMesh)oSecondAss.getLeafElement();
						//firstAssociation is compared with the secondAssociation
						//If the content type of the DM are equal then
						if (oFirstDM.getMoContentType().intern() == oSecondDM.getMoContentType().intern()) {
							//1. Add mrPleasure from the second to the first DM
							double mrNewPleasure = setNewQuotaOfAffectValue(oFirstDM.getMrPleasure(), oSecondDM.getMrPleasure());
							//double mrNewPleasure = oFirstDM.getMrPleasure() + oSecondDM.getMrPleasure(); //No averaging was made here
							oFirstDM.setMrPleasure(mrNewPleasure);
							//Set second DM as used (true)
							oSecondAssPair.b = true;
							//2. Add Unpleasure from second to first DM
							//FIXME: AW 20110528: Add unpleasure too?
							//double mrNewUnpleasure = oFirstDM.getMrUnpleasure() + oSecondDM.getMrUnpleasure(); //No averaging was made here
							//oFirstDM.setMrPleasure(mrNewUnpleasure);
							//3. Check if the quota of affect is higher for the second DM and exchange content
							
							//FIXME: Why does the import nicht work of java.lang.Math
							if (java.lang.Math.abs(oSecondDM.getMrPleasure()) > java.lang.Math.abs(rMaxTotalQuotaOfAffect)) {
								//FIXME: Corrent the function to consider mrUnpleasure too
								sMaxContent = oSecondDM.getMoContent();
								rMaxTotalQuotaOfAffect = oSecondDM.getMrPleasure();
							}
							//4. Delete oSeconDM
							//ListIterator<clsAssociation> itr = oArrAssFirst.listIterator(liSubList.previousIndex());
							//oSecondAss = liSubList.previous();
							//liSubList.remove();
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
		clsPrimaryDataStructureContainer oMergedResult = new clsPrimaryDataStructureContainer(oInput.getMoDataStructure(), oNewAss);
		
		return oMergedResult;
	}
	
	//AW 20110618 new function
	private double setNewQuotaOfAffectValue(double rOriginal, double rAddValue) {
		/** This function was made in order to be able to set the calculation function of the total 
		 *  quota of affect separately 
		 */
		
		return (rOriginal + rAddValue);
	}
	
	//TD 2011/04/22
	/*private void mergeLists() {
		//merge the two lists which are incoming from the two receive interfaces. the clsPrimaryDataStructureContainers entries
		//are the same. moLibidoPleasureCandidates_IN is a subgroup of moPerception_IN
		moMergedPrimaryInformation_Input = new ArrayList<clsTripple<clsPrimaryDataStructureContainer,clsDriveMesh,clsDriveMesh>>();

		for (clsPair<clsPrimaryDataStructureContainer,clsDriveMesh> oP:moPerception_IN) {
			clsPrimaryDataStructureContainer oPDSC = oP.a;
			clsDriveMesh oDMRepressed = oP.b;
			clsDriveMesh oDMLibido = null;
			
			//FIXME (wendt) - Input changed
//			for (clsPair<clsPrimaryDataStructureContainer,clsDriveMesh> oL:moLibidoPleasureCandidates_IN) {
//				if (oL.a.toString().equals(oPDSC.toString())) { //FIXME (Zeilinger): untested! not sure if this operation can identify identical clsPrimaryDataStructureContainer (TD 2011/04/22)
//					oDMLibido = oL.b;
//					break;
//				}
//			}			
			
			clsTripple<clsPrimaryDataStructureContainer,clsDriveMesh,clsDriveMesh> oEntry = 
				new clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, clsDriveMesh>(oPDSC, oDMRepressed, oDMLibido);
			moMergedPrimaryInformation_Input.add(oEntry);
		}
	}*/
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.08.2010, 12:44:36
	 *
	 */
	/*private void adaptPleasureValue() {
		moNewPrimaryInformation = new ArrayList<clsPrimaryDataStructureContainer>(); 
		
		//for each pair in the input list...
		for(clsTripple<clsPrimaryDataStructureContainer,clsDriveMesh,clsDriveMesh> oData : moMergedPrimaryInformation_Input){
			clsPrimaryDataStructureContainer oPDSC = oData.a;
			
			clsDriveMesh oDMRepressed = oData.b;
			clsDriveMesh oDMLibido = oData.c;
			
			//If there is an object e. g. CAKE, which is a TPM...
			if(oPDSC.getMoDataStructure() instanceof clsThingPresentationMesh){
				//For each associated content in moAssociatedContent...
				for(clsAssociation oAssociation : oPDSC.getMoAssociatedDataStructures()){
					//If the Association is a DM...
					if(oAssociation instanceof clsAssociationDriveMesh){
						//Get the actual DM from the association
						clsDriveMesh oDMInput = ((clsAssociationDriveMesh)oAssociation).getDM(); 
						//Get the DM from the repressed content		
						
						processLibidoContent(oDMInput, oDMLibido);
						processRepressedContent(oDMInput, oDMRepressed);

					}
				}
			}	
			moNewPrimaryInformation.add(oPDSC); 
		}
	}*/
	
    //TD 2011/04/22 - refactored adaptPleasureValue function
	/*private void processLibidoContent(clsDriveMesh oDMInput, clsDriveMesh oDMLibido) {
		//this function processes data received by interface I2.16
		
		if (oDMLibido != null) {
			//this incoming perception has been identified by E45 to qualify for reduction of libido tension
			//->tension reduction results in pleasure gain->the perceptions pleasure value is increased.
			double rLibido = oDMLibido.getPleasure();
			double rPleasure = oDMInput.getPleasure();
			
			double rResult = rPleasure+rLibido;
			if (rResult > 1) {rResult = 1;}
			if (rResult < -1) {rResult = -1;}
			
			oDMInput.setPleasure(rResult);
		}
	}*/
	
    //TD 2011/04/22 - refactored adaptPleasureValue function
	/*private void processRepressedContent(clsDriveMesh oDMInput, clsDriveMesh oDMRepressed) {
		//this function processes data received by interface I2.8
		
		if (oDMRepressed != null) { //in the minimal model version, no drive meshes are attached! see E35.
			/* If the moContentType is equal (at CAKE, NOURISH), then set new pleasure as the average of 
			 * the pleasure of repressed content and the object. Then, new moContent is set from the 
			 * Repressed content (NOURISH GREEDY statt NOURISH_CAKEBASIC).
			 */
			/*if (oDMInput.getMoContentType().intern() == oDMRepressed.getMoContentType().intern()) {
			//old Fix here if(oDMInput.getMoContent().intern() == oDMRepressed.getMoContent().intern()){
				oDMInput.setPleasure((oDMInput.getPleasure()+oDMRepressed.getPleasure())/2); 
				oDMInput.setMoContent(oDMRepressed.getMoContent()); 
			}
		}
	}*/
	
	//AW 2011-04-18, new adaptpleasurefunction
	/*private void adaptObjectQuotaOfAffect(clsTemplateImage oObjectUnmerged) {
		// oDirectTI is directly manipulated
		/* structure of oDirectTI: DM from repressed content are added as associations in the
		 * complete template image, i. e. to the extrinsic properties. From E35, DM are 
		 * added with positive mrPleasure and mrUnpleasure. From E45, mrPleasure is added.
		 */
		/*
		//Create a new empty List of associations, in which the modified associations are added
		ArrayList<clsAssociation> oNewAss = new ArrayList<clsAssociation>();
		
		//For each association to a template image in the template image
				
		//As long as there are any extrinsic associations bound to this object
		//Create an Arraylist with booleans in order to only add the elements, which are set true
		ArrayList<clsPair<clsAssociation, Boolean>> oArrAssFirst = new ArrayList<clsPair<clsAssociation, Boolean>>();
		for (int i=0;i<oObjectUnmerged.getMoAssociatedContent().size();i++) {
			oArrAssFirst.add(new clsPair<clsAssociation, Boolean>(oObjectUnmerged.getMoAssociatedContent().get(i),false));
		}
		
		ListIterator<clsPair<clsAssociation, Boolean>> liMainList = oArrAssFirst.listIterator();
		ListIterator<clsPair<clsAssociation, Boolean>> liSubList;
		clsAssociation oFirstAss;
		while (liMainList.hasNext()) {
		//for (Iterator<clsAssociation> it = oArrAssFirst.iterator(); it.hasNext();) {
		//while (oArrAssFirst.iterator().hasNext()) {
			clsPair<clsAssociation, Boolean> oFirstAssPair = liMainList.next();
			oFirstAss = oFirstAssPair.a;
			
			if ((oFirstAss instanceof clsAssociationDriveMesh) && (oFirstAssPair.b == false)) {
				//Set the quota of affect for the content
				clsDriveMesh oFirstDM = (clsDriveMesh)oFirstAss.getLeafElement();
						
				/* Here the new content is set depending on the highest level of total quota of affect
				 * of all equal drive meshes in the object. If another object has a higher
				 * Quota of affect, its content is taken.
				 */
	/*
				double mrMaxTotalQuotaOfAffect = oFirstDM.getMrPleasure();	//FIXME Add Unpleasure too
				String msMaxContent = oFirstDM.getMoContent();
						
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
							
					if ((oSecondAss instanceof clsAssociationDriveMesh)  && (oSecondAssPair.b == false)) {	
						clsDriveMesh oSecondDM = (clsDriveMesh)oSecondAss.getLeafElement();
						//firstAssociation is compared with the secondAssociation
						//If the content type of the DM are equal then
						if (oFirstDM.getMoContentType().intern() == oSecondDM.getMoContentType().intern()) {
							//1. Add Pleasure from second to first DM
							double mrNewPleasure = oFirstDM.getMrPleasure() + oSecondDM.getMrPleasure(); //No averaging was made here
							oFirstDM.setMrPleasure(mrNewPleasure);
							oSecondAssPair.b = true;
							//2. Add Unpleasure from second to first DM
							//double mrNewUnpleasure = oFirstDM.getMrUnpleasure() + oSecondDM.getMrUnpleasure(); //No averaging was made here
							//oFirstDM.setMrPleasure(mrNewUnpleasure);
							//3. Check if the quota of affect is higher for the second DM and exchange content
							//FIXME: Why does the import nicht work of java.lang.Math
							if (java.lang.Math.abs(oSecondDM.getMrPleasure()) > java.lang.Math.abs(mrMaxTotalQuotaOfAffect)) {
								//FIXME: Corrent the function to consider mrUnpleasure too
								msMaxContent = oSecondDM.getMoContent();
								mrMaxTotalQuotaOfAffect = oSecondDM.getMrPleasure();
							}
							//4. Delete oSeconDM
							//ListIterator<clsAssociation> itr = oArrAssFirst.listIterator(liSubList.previousIndex());
							//oSecondAss = liSubList.previous();
							//liSubList.remove();
							//liSubList.remove();
							
						}
					}
				}
				//Set new content if different
				if (oFirstDM.getMoContent().equals(msMaxContent)==false) {
					oFirstDM.setMoContent(msMaxContent);
				}
			}
			//Add the association to the list
			if (oFirstAssPair.b == false) {
				oNewAss.add(oFirstAss);
			}
		}
		oObjectUnmerged.setMoAssociatedContent(oNewAss);
	}
	
	//AW 2011-04-18, new adaptpleasurefunction
	private clsTemplateImage tempMergeDMs(ArrayList<clsTripple<clsPrimaryDataStructureContainer,clsDriveMesh,clsDriveMesh>> oInput) {
		//Inputformat: Template Image with all DMs as associated to a certain TPM
		ArrayList<clsPrimaryDataStructureContainer> oModifiedInputContainerList = new ArrayList<clsPrimaryDataStructureContainer>();
		
		for (clsTripple<clsPrimaryDataStructureContainer, clsDriveMesh, clsDriveMesh> oTripple : oInput) {
			clsPrimaryDataStructureContainer oNewMerge = oTripple.a;
			
			ArrayList<clsAssociation> oNewAss = oNewMerge.getMoAssociatedDataStructures();
			clsPrimaryDataStructure oDS = (clsPrimaryDataStructure) oNewMerge.getMoDataStructure();
			clsDriveMesh oDM1 = oTripple.b;
			clsDriveMesh oDM2 = oTripple.c;
			clsTripple<Integer, eDataType, String> oID = new clsTripple<Integer, eDataType, String> (-1,eDataType.ASSOCIATIONDM,"Test");
			
			if (oDM1!=null) {
				clsAssociation oDMAss1 = new clsAssociationDriveMesh(oID, oDM1, oDS);
				oNewAss.add(oDMAss1);
			}
			
			if (oDM2!=null) {
				clsAssociation oDMAss2 = new clsAssociationDriveMesh(oID, oDM2, oDS);
				oNewAss.add(oDMAss2);
			}
			
			oNewMerge.setMoAssociatedDataStructures(oNewAss);
			oModifiedInputContainerList.add(oNewMerge);
		}
		
		clsTemplateImage oRetVal = clsDataStructureConverter.convertMultiplePDSCtoTI(oModifiedInputContainerList);
		
		return oRetVal;
	}*/



	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:15:59
	 * 
	 * @see pa.modules.clsModuleBase#send()
	 */
	@Override
	protected void send() {
		send_I5_10(moEnvironmentalPerception_OUT, moAssociatedMemories_OUT);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 18.05.2010, 17:38:33
	 * 
	 * @see pa.interfaces.send.I2_9_send#send_I2_9(java.util.ArrayList)
	 */
	@Override
	public void send_I5_10(clsPrimaryDataStructureContainer poMergedPrimaryInformation, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		((I5_10_receive)moModuleList.get(7)).receive_I5_10(poMergedPrimaryInformation, poAssociatedMemories);
		
		putInterfaceData(I5_10_send.class, poMergedPrimaryInformation, poAssociatedMemories);
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
	public void receive_I5_9(clsPrimaryDataStructureContainer poMergedPrimaryInformation, ArrayList<clsPrimaryDataStructureContainer> poAssociatedMemories) {
		moEnvironmentalPerception_IN = (clsPrimaryDataStructureContainer)deepCopy(poMergedPrimaryInformation);
		moAssociatedMemories_IN = (ArrayList<clsPrimaryDataStructureContainer>)deepCopy(poAssociatedMemories);
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
