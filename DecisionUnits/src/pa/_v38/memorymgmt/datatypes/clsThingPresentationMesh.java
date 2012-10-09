/**
 * clsThingPresentationMesh.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;
import java.util.HashMap;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsPrimarySpatialTools;
import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eActivationType;
import pa._v38.memorymgmt.enums.ePhiPosition;
import pa._v38.memorymgmt.enums.eRadius;

/**
 * DOCUMENT (zeilinger) - The term Thing Presentation Mesh (TPM) describes a mesh of TPs which are connected via attribute associations. 
 * 	The term TPM is introduced to the technical model only and does not occur in psychoanalytic theory. 
 * 
 * moContent (String) 
 * poDataStructureIdentifier (clsTripple):	Holds the data structure Id, the data type, as well as the content type. It is passed on to the super class
 * 				poAssociatedPhysicalRepresentations (ArrayList)	@@@Bug – ArrayList must be parameterized with objects of type clsAssociationAttributes
 * 
 * @author zeilinger
 * 24.05.2010, 12:51:07
 * 
 */
public class clsThingPresentationMesh extends clsPhysicalStructureComposition{
	
	private String moContent = "UNDEFINED";
	
	// 
	private double mrAggregatedActivationValue ;
	
	// TPMs in memory may get activation from different sources. (memory retrieval)
	private HashMap<eActivationType, Double> moActivations = new HashMap<eActivationType, Double>();
	private HashMap<eActivationType, Double> moCriterionWeights = new HashMap<eActivationType, Double>();
	private HashMap<eActivationType, Double> moCriterionMaxValues = new HashMap<eActivationType, Double>();
	
	/**
	 * @author zeilinger
	 * 17.03.2011, 00:52:29
	 * 
	 * @return the moContent
	 */
	public String getMoContent() {
		return moContent;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:52:29
	 * 
	 * @param moContent the moContent to set
	 */
	public void setMoContent(String moContent) {
		this.moContent = moContent;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilingere
	 * 24.05.2010, 12:51:22
	 *
	 * @param poWordPresentationAssociation
	 * @param poDriveMeshAssociation
	 */
	public clsThingPresentationMesh(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
									ArrayList<clsAssociation> poAssociatedPhysicalRepresentations,
									String poContent) {
		
		super(poDataStructureIdentifier);
		moInternalAssociatedContent = poAssociatedPhysicalRepresentations; 
		setContent(poContent); 
		mrAggregatedActivationValue = 0;
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:10:28
	 *
	 * @param poContent
	 */
	private void setContent(String poContent) {
		if(poContent != null){
			moContent = poContent;
		}
	}
	

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:10:26
	 *
	 * @param poAssociatedPhysicalRepresentations
	 */
	
	/*public void setAssociations(ArrayList<clsAssociation> poAssociatedPhysicalRepresentations) {
		moAssociatedContent = poAssociatedPhysicalRepresentations; 
	}*/

	/**
	
	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 22.06.2010, 17:46:07
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsDataStructurePA#assignDataStructure(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		ArrayList <clsAssociation> oDataStructureList = new ArrayList<clsAssociation>();
		oDataStructureList.add(poDataStructurePA); 
		
		addInternalAssociations(oDataStructureList);
	}

	/* (non-Javadoc)
	*
	* @author zeilinger
	* 13.07.2010, 20:59:01
	* 
	* @see java.lang.Comparable#compareTo(java.lang.Object)
	*/

	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		double oRetVal = 0.0; 
		if(this.moDataStructureType != poDataStructure.moDataStructureType){return oRetVal;}
		
		clsThingPresentationMesh oDataStructure = (clsThingPresentationMesh)poDataStructure;
		ArrayList <clsAssociation> oContentListTemplate = this.moInternalAssociatedContent; 
		ArrayList <clsAssociation> oContentListUnknown = oDataStructure.moInternalAssociatedContent;
		
		// a TPM-entity may have attributes of different importance. e.g. color and shape should be more important than position and distance (which should not be internal attributes, since they do not identify the TPM)
				
    	//This if statement proofs if the compared data structure does already have an ID =>
		//the ID specifies that the data structure has been already compared with a stored
		//data structure and replaced by it. Hence they can be compared by their IDs.
		if(this.moDS_ID == oDataStructure.moDS_ID){
				/*In case the DataStructureIDs are equal, the return value is the number 
				 * of associated data structures and their number of associations. The idendityMatch number
				 * is not used here as it would distort the result. getNumbAssociations has to be introduced
				 * as TPMs can be associated to different types of data structures that can consist of associated
				 * data structures too (TPMs can consist out of TPMs).  
				 */
				oRetVal = 1.0; //oDataStructure.getNumbInternalAssociations();
				return oRetVal;
		}
		/*Each saved CAKE or other individual shall have an own ID. Here, the ID is treated as a type ID, which makes it
		 *impossible to compare individuals */
		else if (oDataStructure.moDS_ID > -1){	return oRetVal;	}
		
		//In case the data structure does not have an ID, it has to be compared to a stored 
		//data structure and replaced by it (the processes base on information that is already
		//defined.
		//TPM content is represented by a list of attribute associations
		//TODO HZ Define why there is no if statement regarding the content!
		if(this.moContent.intern() == oDataStructure.moContent.intern()){
			//added by AW: If there are no associations in the TPM, then only content type and content makes a 1.0 equality
			//FIXME AW: Also the EMPTYSPACE shall have som properties
			if ((oContentListTemplate.isEmpty()==true) && (oContentListUnknown.isEmpty()==true)) {
				oRetVal=1.0;
			} else {
				oRetVal = getMatchScore(this, oDataStructure);
			}
			
		}
		else if (this.moContentType  == poDataStructure.moContentType ){
			oRetVal = getMatchScore(this, oDataStructure);
		}
		
		//Special case, if the TPM is empty	
		
			
		return oRetVal; 
	}
	
//	public double compareToMethod2(clsDataStructurePA poDataStructure) {
//		double rRetVal = 0;
//		//1. Types has to be equal
//		if(this.moDataStructureType != poDataStructure.moDataStructureType){return rRetVal;}
//		//2. If the TYPE-ID is the same, then there is a full match 1.0. If the ID matches, the intrinsic properties are automatically equal
//		if(this.moDS_ID == poDataStructure.moDS_ID){rRetVal = 1.0;}
//		//3. Compare similarity
//		
//		return rRetVal;
//	}
	
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 8.08.2012, 16:12:00
	 *
	 * @return
	 */
//	public ArrayList<clsThingPresentation> getAssociatedThingPresentations() {
//		ArrayList<clsThingPresentation> oResult = new ArrayList<clsThingPresentation>();
//			for(clsAssociation oIntAss: this.moInternalAssociatedContent) {
//				try {
//					oResult.add((clsThingPresentation)oIntAss.getMoAssociationElementB());
//				}
//				catch (Exception e) {
//					
//				}
//			}
//		return oResult;
//	}
	
	
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:12:00
	 *
	 * @return
	 */
	@Override
	public double getNumbInternalAssociations() {
		double oResult = 0.0;
			for(clsDataStructurePA oElement1 : moInternalAssociatedContent){
				if(((clsAssociation)oElement1).moAssociationElementB.moDataStructureType == eDataType.TPM){
					oResult +=((clsThingPresentationMesh)((clsAssociation)oElement1).moAssociationElementB).getNumbInternalAssociations(); 
				}
				else {
					oResult += 1.0; 
				}
			}
		return oResult;
	}
	
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 18.07.2010, 16:12:00
	 *
	 * @return
	 */
	@Override
	public double getNumbExternalAssociations() {
		double oResult = 0.0;
			for(clsDataStructurePA oElement1 : moExternalAssociatedContent){
				if(((clsAssociation)oElement1).moAssociationElementB.moDataStructureType == eDataType.TPM){
					oResult +=((clsThingPresentationMesh)((clsAssociation)oElement1).moAssociationElementB).getNumbInternalAssociations(); 
				}
				else {
					oResult += 1.0; 
				}
			}
		return oResult;
	}
	
	@Override
	public boolean contain(clsDataStructurePA poDataStructure){
		
		for(clsAssociation oAssociation : this.moInternalAssociatedContent){
			if(oAssociation.moAssociationElementB.moContentType == poDataStructure.moContentType ){
				return true;
			}
		}
		
		for(clsAssociation oAssociation : this.moExternalAssociatedContent){
			if(oAssociation.moAssociationElementB.moContentType == poDataStructure.moContentType ){
				return true;
			}
		}
		
		return false; 
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 28.08.2012, 12:10:28
	 *
	 * @param poActivationKind, prSourceActivation
	 */
	public void applySourceActivation(eActivationType poActivationKind, double prSourceActivation, double prWeight){
		
		double rPreviousActivation = 0;
		
		if(moActivations.containsKey(poActivationKind)) {
			rPreviousActivation = moActivations.get(poActivationKind);
		}
		moActivations.put(poActivationKind, rPreviousActivation+(prSourceActivation*prWeight));
		
		
		
//		System.out.println(poActivationKind);
//		System.out.println(rPreviousActivation+(prSourceActivation*rWeight));
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 28.08.2012, 12:10:28
	 *
	 * @param prWeight
	 */
	public void extendCriterionWeight(eActivationType poActivationKind,  double prWeight){
		// calculate criterion impact factor
				if(poActivationKind == eActivationType.EMBODIMENT_ACTIVATION) {
					double currentWeight = 0;
					if(moCriterionWeights.containsKey(poActivationKind)){
						 currentWeight = moCriterionWeights.get(poActivationKind);
					}
					else {
						 currentWeight = 0;
					}
					moCriterionWeights.put(poActivationKind, currentWeight + (1-currentWeight)*prWeight);
					
					
				}
	}	
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 28.08.2012, 12:10:28
	 *
	 * @param prWeight
	 */
	public void extendCriterionMaxValue(eActivationType poActivationKind,  double prWeight){
		// calculate criterion impact factor
				if(poActivationKind == eActivationType.EMBODIMENT_ACTIVATION) {
					
					double currentMax = 0;
					if(moCriterionMaxValues.containsKey(poActivationKind)){
						currentMax = moCriterionMaxValues.get(poActivationKind);
					}
					moCriterionMaxValues.put(poActivationKind, currentMax + prWeight);
				}
	}
	
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 28.08.2012, 12:10:28
	 *
	 * @param poActivationKind, poNorm
	 */
	public void applyCriterionActivation(eActivationType poActivationKind) {
		
//		System.out.println(poActivationKind);
//		System.out.println("act" + moActivations.get(poActivationKind)); 
//		System.out.println("max" +poCriterionMaxValue);
//		
		double rCriterionMaxValue = moCriterionMaxValues.get(poActivationKind);
		
		moActivations.put(poActivationKind, moActivations.get(poActivationKind) / rCriterionMaxValue);
			
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 28.08.2012, 12:10:28
	 *
	 * @param poActivationKind, poNorm
	 */
	public void applyCriterionActivation(eActivationType poActivationKind, double prCriterionMaxValue) {
		
//		System.out.println(poActivationKind);
//		System.out.println("act" + moActivations.get(poActivationKind)); 
//		System.out.println("max" +poCriterionMaxValue);
//		 
		
		moActivations.put(poActivationKind, moActivations.get(poActivationKind) / prCriterionMaxValue);
			
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 28.08.2012, 12:10:28
	 *
	 */
	/*public void applyEmbodimentActivation(ArrayList<clsDriveMesh> poActualDrives) {
		

		// diff between QoA of associated DM an actual DM --> how good would this memory DM satisfy the according actual DM
		double rMatchDMs = 0;;
		double rSatisfactionOfActualDM = 0;
		double rTotSatisfactionOfActualDMs = 0;
		double rCriterionActivation = 0;
		
		if(moActivations.containsKey(eActivationType.EMBODIMENT_ACTIVATION)) {
			// exemplar has already this criterion activation
			double rPreviousActivation = moActivations.get(eActivationType.EMBODIMENT_ACTIVATION);
		}
		else {
					
			// check for every associated memory-DM how this DM would satisfy the agent's actual drives
			for (clsDriveMesh oActualDM : poActualDrives) {
				rSatisfactionOfActualDM = 0;
				for (clsAssociation oExtAss : this.moExternalAssociatedContent) {
					if (oExtAss instanceof clsAssociationDriveMesh) {
						// if a drive is the same (has the same aim, object and source) and has the same QoA -> the matchingfactor is 1. hence the driveobject with this drive would satisfy the actual drive in the best possible way
						rMatchDMs = oActualDM.compareTo(((clsDriveMesh)oExtAss.getMoAssociationElementA()));
						// take the best match
						if(rMatchDMs>rSatisfactionOfActualDM) {
							rSatisfactionOfActualDM = rMatchDMs;
						}
					}
					
				}
				rTotSatisfactionOfActualDMs += rSatisfactionOfActualDM;
			}
			
			// Normalization. The Max possible satisfaction of all actual drives correspond to the number of them
			rCriterionActivation = rTotSatisfactionOfActualDMs/poActualDrives.size();
			moActivations.put(eActivationType.EMBODIMENT_ACTIVATION, rCriterionActivation * this.getPleasurePotential()); //PleasurePotential for consideration of the pleasure principle --> one high DM is better as two middle DMs, even if they have the same (accumulated) total QoA
			
			
		}
	}*/
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 28.08.2012, 12:10:28
	 *
	 * @param poActivationKind
	 */
	public double getCriterionActivationValue(eActivationType poActivationKind) {
			double rActivationLevel = moActivations.get(poActivationKind);
			
			return rActivationLevel;
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 28.08.2012, 12:10:28
	 *
	 * @param poActivation
	 */
	public double getAggregatedActivationValue() {
		double oOverallActivation = 0;
		double oOverallWeights = 0;
		
		if(mrAggregatedActivationValue == 0) {
			for (eActivationType oActivationType : moActivations.keySet()) {
				//TEST
				if(oActivationType == eActivationType.SIMILARITY_ACTIVATION) {
					oOverallActivation += moActivations.get(oActivationType) *1;
					oOverallWeights += 1;
				}
				else {
					oOverallActivation += moActivations.get(oActivationType) * moCriterionWeights.get(oActivationType);	
					oOverallWeights += moCriterionWeights.get(oActivationType);
				}				
			}
			mrAggregatedActivationValue = oOverallActivation/oOverallWeights;
		}
		
		
		
		// 
		return  mrAggregatedActivationValue; // TEST moActivations.get(eActivationType.SIMILARITY_ACTIVATION);
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 28.08.2012, 12:10:28
	 *
	 * @param poActivation
	 */
	public void setCriterionActivationValue(eActivationType poActivationKind, double poActivationLevel) {
		if(moActivations.containsKey(poActivationKind)) {
			System.out.println(poActivationKind + " already exist");
		}
		moActivations.put(poActivationKind, poActivationLevel);
	}
	

	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 03.10.2012, 12:10:28
	 *
	 * @param poTPM
	 *
	 */
	public void takeActivationsFromTPM(clsThingPresentationMesh poTPM) {
		for (eActivationType oActivationType : poTPM.moActivations.keySet()) {
			if(this.moActivations.containsKey(oActivationType) == false) {
				this.moActivations.put(oActivationType, poTPM.moActivations.get(oActivationType));
				this.moCriterionMaxValues.put(oActivationType, poTPM.moCriterionMaxValues.get(oActivationType));
				this.moCriterionWeights.put(oActivationType, poTPM.moCriterionWeights.get(oActivationType));
			}
		}
		
	}
//	/**
//	 * DOCUMENT (schaat) - insert description
//	 *
//	 * @author schaat
//	 * 28.08.2012, 12:10:28
//	 *
//	 */
//	public void embodimentCriterionWeighting() {
//		// a TPM uses the highest possible activation
//		// but consider synergies, i.e. if a object gets  multiple actications --> increase activation
//		// always use the highest activation for the basis-activation. the other activations are only considered as rest-activation. 
//		// That is, the rest until the max activation is considered as the maximal possible activation for the rest activation. E.g. if the basic activation is
//		// 0.7, then the max rest activation is 0.3. Next, the concerned activation is normed with respect to the maximal possible 
//		// value, i.e. the max rest activation. E.g. if the basic activation is 0.7 and another activation  is 0,4, then 
//		// the total activation of the TPM is 0.7+0.3*0.4
//		
//		//pleasure potential
//		double rQoA = 0;
//		
//		for (clsAssociation oExtAss : this.moExternalAssociatedContent) {
//			if (oExtAss instanceof clsAssociationDriveMesh) {
//				
//				rQoA = ((clsDriveMesh)oExtAss.getMoAssociationElementA()).getQuotaOfAffect();
//		
//				if(this.mrPleasurePotential != 0) {					
//					mrPleasurePotential += (1-mrPleasurePotential)*rQoA;
//				}
//				else {
//					mrPleasurePotential = rQoA;
//				}
//			}
//			
//		}
//	
//	}
	
	
	
	
	/**
	 * Check if this object is a null object
	 * 
	 * (wendt)
	 *
	 * @since 21.07.2012 20:48:57
	 *
	 * @return
	 */
	public boolean isNullObject() {
		boolean bResult = false;
		
		if (this.getMoContentType()==eContentType.NULLOBJECT) {
			bResult=true;
		}
		
		return bResult;
	}
	
		
	@Override
//	public Object clone() throws CloneNotSupportedException {
//        try {
//        	clsThingPresentationMesh oClone = (clsThingPresentationMesh)super.clone();
//        	if (moAssociatedContent != null) {
//        		oClone.moAssociatedContent = new ArrayList<clsAssociation>(); 
//        		for(clsAssociation oAssociation : moAssociatedContent){
//        			try { 
//    					Object dupl = oAssociation.clone(this, oClone); 
//    					oClone.moAssociatedContent.add((clsAssociation)dupl); // unchecked warning
//    				} catch (Exception e) {
//    					return e;
//    				}
//        		}
//        	}
//        	
//        	if (moAssociatedContent != null) {
//        		oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
//        		for(clsAssociation oAssociation : moExternalAssociatedContent){
//        			try { 
//    					Object dupl = oAssociation.clone(this, oClone); 
//    					oClone.moExternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
//    				} catch (Exception e) {
//    					return e;
//    				}
//        		}
//        	}
//        	
//          	return oClone;
//        } catch (CloneNotSupportedException e) {
//           return e;
//        }
//	}
	
	/**
	 * Alternative clone for cloning directed graphs
	 * 
	 * (wendt)
	 *
	 * @since 01.12.2011 16:29:38
	 *
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public Object clone() throws CloneNotSupportedException {
	//public Object cloneGraph() throws CloneNotSupportedException {
		return clone(new ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>>());
	}
	
	/**
	 * Alternative clone for cloning directed graphs. This function adds cloned objects to a list and considers
	 * that loops may occur
	 * 
	 * (wendt)
	 *
	 * @since 01.12.2011 16:29:58
	 *
	 * @param poNodeList
	 * @return
	 * @throws CloneNotSupportedException
	 */
	public Object clone(ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>> poClonedNodeList) throws CloneNotSupportedException {
		
		clsThingPresentationMesh oClone = null;
		
		try {
			//Clone the data structure without associated content. They only exists as empty lists
			
			oClone = (clsThingPresentationMesh)super.clone();
			oClone.moActivations = (HashMap<eActivationType, Double>) this.moActivations.clone();
			oClone.moCriterionMaxValues = (HashMap<eActivationType, Double>) this.moCriterionMaxValues.clone();
			oClone.moCriterionWeights = (HashMap<eActivationType, Double>) this.moCriterionWeights.clone();
			oClone.moInternalAssociatedContent = new ArrayList<clsAssociation>();
			oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>();
			//Add this structure and the new clone to the list of cloned structures
			poClonedNodeList.add(new clsPair<clsDataStructurePA, clsDataStructurePA>(this, oClone));
			
			//Go through all associations
			if (moInternalAssociatedContent != null) {
				//Add internal associations to oClone 
        		for(clsAssociation oAssociation : this.moInternalAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone, poClonedNodeList); 
    					oClone.moInternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
    				} catch (Exception e) {
    					return e;
    				}
        		}
        	}
						
			//Go through all associations
			if (moExternalAssociatedContent != null) {
				//Add internal associations to oClone 
        		for(clsAssociation oAssociation : this.moExternalAssociatedContent){
        			try { 
    					Object dupl = oAssociation.clone(this, oClone, poClonedNodeList); 
    					oClone.moExternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
    				} catch (Exception e) {
    					return e;
    				}
        		}
        	}
			
		} catch (CloneNotSupportedException e) {
           return e;
        }
		
		return oClone;
	}
	
	
	
	@Override
	public String toString(){
		//Add by AW
		String oResult = "";
		if (this.moContentType.equals(eContentType.RI) || this.moContentType.equals(eContentType.PI)) {
			//"::"+this.moDataStructureType+"::";  
			oResult += this.moContentType + ":" + this.moContent;
			
			oResult += "\nINTERNAL ASSOCIATED CONTENT\n";
			for (clsAssociation oEntry : moInternalAssociatedContent) {
				oResult += oEntry.getLeafElement().toString() + ","; 
			}
			
			oResult += "\nEXTERNAL ASSOCIATED CONTENT\n";
			for (clsAssociation oEntry : moExternalAssociatedContent) {
				oResult += oEntry.toString() + ","; 
			}
		} else if (this.moContentType.equals(eContentType.ENTITY)) {
			oResult += this.moContentType + ":" + this.moContent;
			
			clsTriple<clsThingPresentationMesh, ePhiPosition, eRadius> oPosition = clsPrimarySpatialTools.getPosition(this);
			
			String oPhiPos = "null";
			String oRadPos = "null";
			
			if (oPosition.b!=null) {
				oPhiPos = oPosition.b.toString();
			}
			if (oPosition.c!=null) {
				oRadPos = oPosition.c.toString();
			}
			
			oResult += "(" + oPhiPos + ":" + oRadPos + ")";
			
		} else {
			oResult += "::"+this.moDataStructureType+"::";  
			oResult += this.moContentType + ":" + this.moContent;
		}
		
		return oResult; 
	}
}
