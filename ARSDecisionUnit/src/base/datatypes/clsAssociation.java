/**
 * clsAssociation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 */
package base.datatypes;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import base.datatypes.clsDataStructurePA;
import base.datatypes.enums.eConnectionType;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (zeilinger) - clsAssociation represents the base class for the four association types � attribute association, temporal association, word presentation association, drive mesh association (see Figure 3). Every association contains two objects of type clsDataStructurePA (Element A and Element B). In addition it holds a weight of the association. 
 * There is only one weight defined for the association. Hence, �Element A� has the same level of dependency to �Element B� as the other way round. Keep in mind that the Prot�g� database defines a bi-directional connection; however the second weight is ignored during loading operation
 * The imperative factor indicates if the association is defined as class association or instance association. This differentiation simplifies the search for data structures as well as influences the matching level (level of correspondence between two data structures being compared). For v30, the complexity of the entities is low => hence, the differentiation between class associations and instance associations is crucial; otherwise a lot of data structures would have the same matching factor.  
 * Class association: It is differentiate between associations that define a specific object class (e.g. object �CAKE�) and those which are true for an instance of these objects (e.g. �red cake�). Class associations represent the first type of association. A higher weight is assigned to them by definition. 
 * Instance association: Defines an association that is not true for the whole object type but only for single instances. 
 * 
 * The imperative factor is set at the initialization of the data structure. This is done in package pa._v30.memorymgmt.informationrepresentation.searchspace in class clsOntology loader. There the method setImperativeFactor(int variable) is invoked. Hence, the imperative factor can also be changed during run time. The information about the belonging to the group of class or instance associations is defined within the knowledge base (Prot�g�). 
 * moAssociationElementA (clsDataStructurePA)
 * moAssociationElementB (clsDataStructurePA)	
 * mrWeight (double)	
 * mrImperativeFactor (double)	
 * poDataStructureIdentifier (clsTripple)	Holds the data structure Id, the data type, as well as the content type. It is passed on to the super class

 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 * 
 */
public abstract class clsAssociation extends clsDataStructurePA{
	//private static final long mrMaxStackDepth = 5000;
	
	//protected double mrImperativeFactor; 
	protected double mrWeight;
	protected double mrLearning;
	protected clsDataStructurePA moAssociationElementA;
	protected clsDataStructurePA moAssociationElementB;

//	/**
//	 * @author zeilinger
//	 * 17.03.2011, 00:48:52
//	 * 
//	 * @return the mrImperativeFactor
//	 */
//	public double getMrImperativeFactor() {
//		return mrImperativeFactor;
//	}
//
//	/**
//	 * @author zeilinger
//	 * 17.03.2011, 00:48:52
//	 * 
//	 * @param mrImperativeFactor the mrImperativeFactor to set
//	 */
//	public void setMrImperativeFactor(double mrImperativeFactor) {
//		this.mrImperativeFactor = mrImperativeFactor;
//	}
	   /**
     * @author fittner
     * 31.07.2018, 10:36:52
     * 
     * @return the mrLearning
     */
    public double getMrLearning() {
        return mrLearning;
    }
    
    /**
     * @author fittner
     * 31.07.2018, 10:36:52
     * 
     * @param mrLearning the mrLearning to set
     */
    public void setMrLearning(double mrLearning) {
        this.mrLearning = mrLearning;
    }


	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @return the mrWeight
	 */
	public double getMrWeight() {
		return mrWeight;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @param mrWeight the mrWeight to set
	 */
	public void setMrWeight(double mrWeight) {
		this.mrWeight = mrWeight;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @return the moAssociationElementA
	 */
	public clsDataStructurePA getAssociationElementA() {
		return moAssociationElementA;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @param moAssociationElementA the moAssociationElementA to set
	 */
	public void setAssociationElementA(clsDataStructurePA moAssociationElementA) {
		this.moAssociationElementA = moAssociationElementA;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @return the moAssociationElementB
	 */
	public clsDataStructurePA getAssociationElementB() {
		return moAssociationElementB;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @param moAssociationElementB the moAssociationElementB to set
	 */
	public void setAssociationElementB(clsDataStructurePA moAssociationElementB) {
		this.moAssociationElementB = moAssociationElementB;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 15:50:41
	 *
	 */
	public clsAssociation(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, clsDataStructurePA poAssociationElementA, clsDataStructurePA poAssociationElementB) {
		super(poDataStructureIdentifier);
		//mrImperativeFactor = 1.0; 
		mrWeight = 1.0;
		moAssociationElementA = poAssociationElementA; 
		moAssociationElementB = poAssociationElementB; 
	}
	
	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 15:50:41
	 *
	 */
	public clsAssociation(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, clsDataStructurePA poAssociationElementA, clsDataStructurePA poAssociationElementB, double prAssociationWeight) {
		super(poDataStructureIdentifier);
		//mrImperativeFactor = 1.0; 
		mrWeight = prAssociationWeight;
		moAssociationElementA = poAssociationElementA; 
		moAssociationElementB = poAssociationElementB; 
	}
	
	//Abstract method that has to be implemented by every Association object - however
	//the recall of the leaf element is different for every Association Type
	//FIXME HZ 17.08.2010: Refactor this method as it is different for every 
	//					   Association type. 
	public abstract clsDataStructurePA getLeafElement();
	
	public abstract clsDataStructurePA getRootElement();
	
	public abstract void setLeafElement(clsDataStructurePA poDS);
	
	public abstract void setRootElement(clsDataStructurePA poDS);
		
	/**
	 * Put "this" into this function to get the other element of the association
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:12:23
	 *
	 * @param poSubjectElement
	 * @return
	 */
	public clsDataStructurePA getTheOtherElement(clsDataStructurePA poSubjectElement) {
		clsDataStructurePA oRetVal = null;
		
		if (this.moAssociationElementA.equals(poSubjectElement)) {
			oRetVal=this.moAssociationElementB;
		} else if (this.moAssociationElementB.equals(poSubjectElement)) {
			oRetVal=this.moAssociationElementA;
		}
		
		return oRetVal;
	}
	
	/**
	 * Set the other element of the association
	 * 
	 * (wendt)
	 *
	 * @since 08.09.2012 11:43:13
	 *
	 * @param poSubjectElement
	 * @param poSetOtherElement
	 */
	public void setTheOtherElement(clsDataStructurePA poSubjectElement, clsDataStructurePA poSetOtherElement) {
		
		if (this.moAssociationElementA.equals(poSubjectElement)) {
			this.moAssociationElementB=poSetOtherElement;
		} else if (this.moAssociationElementB.equals(poSubjectElement)) {
			this.moAssociationElementA = poSetOtherElement;
		}
		
	}
	
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		try {
			clsAssociation oClone = (clsAssociation)super.clone();
           	try {
    	    		Class<?> clzz = this.moAssociationElementA.getClass();
    	    		Method   meth = clzz.getMethod("clone", new Class[0]);
    				Object   dupl = meth.invoke(this.moAssociationElementA, new Object[0]);
    				oClone.moAssociationElementA = (clsDataStructurePA) dupl;
       	    } catch (Exception e) {
    	    	 //...
    	    }
    	    try {
    	    		Class<?> clzz = this.moAssociationElementB.getClass();
    	    		Method   meth = clzz.getMethod("clone", new Class[0]);
    				Object   dupl = meth.invoke(this.moAssociationElementB, new Object[0]);
    				oClone.moAssociationElementB = (clsDataStructurePA) dupl; 
    	    } catch (Exception e) {
    	    	 //...
    	    }
         	        	
         	return oClone;
		 } catch (CloneNotSupportedException e) {
			 return e;
		 }
	}
			
			
	public Object clone(Object poOriginalObject, Object poClonedObject, HashMap<clsDataStructurePA, clsDataStructurePA> poClonedNodeMap) throws CloneNotSupportedException {
		clsAssociation oClone = null;
		clsDataStructurePA oOldClone = null; 
		
		//Clone the association itself
    	
		try { 
	    	//Clone the clsDataStructurePA for this association
	    	oClone = (clsAssociation) super.clone(); 
	    	//poClonedNodeList.add(new clsPair<clsDataStructurePA, clsDataStructurePA>(this, oClone));
	    	
	    	//Clone elementA
            try {
                if (this.moAssociationElementA.equals(poOriginalObject)) {
                    //If the element A is the origin element, set the clone as association for A
                    oClone.moAssociationElementA = (clsDataStructurePA) poClonedObject;
                } else if (this.moAssociationElementB.equals(poOriginalObject)) {
                    //Check if element B exists in the list
                    oOldClone = poClonedNodeMap.get(moAssociationElementA);
                    
                    if(oOldClone != null) {
                        oClone.moAssociationElementA = oOldClone;
                    } else {
                        if (moAssociationElementA instanceof clsThingPresentationMesh) {
                            oClone.moAssociationElementA = (clsDataStructurePA) ((clsThingPresentationMesh)this.moAssociationElementA).clone(poClonedNodeMap);                     
                        } else if (moAssociationElementA instanceof clsThingPresentation) {
                            oClone.moAssociationElementA = (clsDataStructurePA) ((clsThingPresentation)this.moAssociationElementA).clone(poClonedNodeMap);
                        } else if (moAssociationElementA instanceof clsDriveMesh) {
                            oClone.moAssociationElementA = (clsDataStructurePA) ((clsDriveMesh)this.moAssociationElementA).clone(poClonedNodeMap);
                        } else if (moAssociationElementA instanceof clsEmotion) {
                            oClone.moAssociationElementA = (clsDataStructurePA) ((clsEmotion)this.moAssociationElementA).clone(poClonedNodeMap);
                        } else if (moAssociationElementA instanceof clsWordPresentationMesh) {
                            oClone.moAssociationElementA = (clsDataStructurePA) ((clsWordPresentationMesh)this.moAssociationElementA).clone(poClonedNodeMap);
                        } else if (moAssociationElementA instanceof clsWordPresentation) {
                            oClone.moAssociationElementA = (clsDataStructurePA) ((clsWordPresentation)this.moAssociationElementA).clone();
                        } else {
                            throw new Exception("Datatype not found or is clonaeble. Data structure " + this.moAssociationElementA);
                        }
                    }
                }
            } catch (Exception e) {
                log.error("Error in the cloning", e);
            }
            
            //Clone ElementB
            try {
                if (this.moAssociationElementB.equals(poOriginalObject)) {
                    //If the element A is the origin element, set the clone as association for A
                    oClone.moAssociationElementB = (clsDataStructurePA) poClonedObject;
                } else {
                    //Check if element B exists in the list
                    oOldClone = poClonedNodeMap.get(moAssociationElementB);
                    
                    if(oOldClone != null) {
                        oClone.moAssociationElementB = oOldClone;
                    } else {
                         if (moAssociationElementB instanceof clsThingPresentationMesh) {
                            oClone.moAssociationElementB = (clsDataStructurePA) ((clsThingPresentationMesh)this.moAssociationElementB).clone(poClonedNodeMap);                     
                         } else if (moAssociationElementB instanceof clsThingPresentation) {
                             oClone.moAssociationElementB = (clsDataStructurePA) ((clsThingPresentation)this.moAssociationElementB).clone();
                         } else if (moAssociationElementB instanceof clsDriveMesh) {
                             oClone.moAssociationElementB = (clsDataStructurePA) ((clsDriveMesh)this.moAssociationElementB).clone(poClonedNodeMap);
                         } else if (moAssociationElementB instanceof clsEmotion) {
                             oClone.moAssociationElementB = (clsDataStructurePA) ((clsEmotion)this.moAssociationElementB).clone(poClonedNodeMap);
                         } else if (moAssociationElementB instanceof clsWordPresentationMesh) {
                             oClone.moAssociationElementB = (clsDataStructurePA) ((clsWordPresentationMesh)this.moAssociationElementB).clone(poClonedNodeMap);
                         } else if (moAssociationElementB instanceof clsWordPresentation) {
                             oClone.moAssociationElementB = (clsDataStructurePA) ((clsWordPresentation)this.moAssociationElementB).clone();
                         } else {
                             throw new Exception("Datatype not found or is clonaeble. Data structure " + this.moAssociationElementB);
                         }
                    }
                }
            } catch (Exception e) {
                log.error("Error in the cloning", e);
            }
	    } catch (CloneNotSupportedException e) { 
	        log.error("Clone error", e);
	        throw e; 
	    } catch (Exception e) {
	    	log.error("Clone error", e);
	    }
		
		return oClone;

	}
	
	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";  
		oResult += this.moDS_ID + ":" + this.moContentType + "|";
		
		oResult += associationToString("elementA:", moAssociationElementA);
		oResult += ":"; 
		oResult += associationToString("elementB:", moAssociationElementB);
		
		return oResult; 
	}
	
	 /**
     * Check if two instances, which are not the same instance are the same
     *
     * @author wendt
     * @since 08.10.2013 10:14:28
     *
     * @param ds
     * @return
     */
    public <E extends clsAssociation> boolean isEquivalentDataStructure(E ds) {
        boolean isEqual = false;
        
        if (ds.getClass().getName().equals(this.getClass().getName()) &&
            ds.getDS_ID()==this.moDS_ID &&
            ds.getContentType()==this.getContentType() &&
            ds.getLeafElement().isEquivalentDataStructure(this.getLeafElement()) &&
            ds.getRootElement().isEquivalentDataStructure(this.getRootElement())) {
            isEqual=true;
        }
        
        return isEqual;
    }
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 14.09.2011, 10:32:48
	 * 
	 * converts the moContentType and moContent of an association to a String
	 */ 
	protected String associationToString (String element, clsDataStructurePA moAssociationElement)
	{
		String oResult = "";
		if(moAssociationElement != null){
			oResult += element;
			//oResult += moAssociationElement.moDataStructureType.toString() + ":";
			oResult += moAssociationElement.moDS_ID + ":";
			oResult += moAssociationElement.moContentType;
			if (moAssociationElement instanceof clsThingPresentationMesh) {
				oResult += ":" + ((clsThingPresentationMesh)moAssociationElement).getContent().toString();
			} else if (moAssociationElement instanceof clsThingPresentation) {
				oResult += ":" + ((clsThingPresentation)moAssociationElement).getContent().toString();
			} else if (moAssociationElement instanceof clsSecondaryDataStructure) {
				oResult += ":" + ((clsSecondaryDataStructure)moAssociationElement).getContent().toString();
			} else if (moAssociationElement instanceof clsEmotion) {
			    oResult += ":" + ((clsEmotion)moAssociationElement).getContent().toString();
			}

//			// find moContent
//			if(moAssociationElement instanceof clsThingPresentationMesh){
//				// check if it is for example an ARSin
//				if (((clsThingPresentationMesh)moAssociationElement).getMoContent() != null)
//					oResult += ":" + ((clsThingPresentationMesh)moAssociationElement).getMoContent();
//				else
//					oResult += ":-null-";
//					
//			}
		}
		else
			oResult = ":-null-";
		return oResult;
	}
	
	/**
	 * DOCUMENT - Tries to find the association in a given list of associations, considering clones and shadow nodes (this means all kind of fauly nodes,
	 *            like old clones that should not be used anymore or similar)
	 *            
	 *            Evaluation criteria is the result of clsAssociation.isCloneOf(...).
	 *            
	 *            If there is more than one candidate for which clsAssociation.isCloneOf(...) returns true, the first one is used and an error message is
	 *            logged.
	 *
	 * @author Kollmann
	 * @since 24.09.2014 11:28:31
	 *
	 * @param poAssociation
	 * @param poAssList
	 * @return
	 */
	static public clsAssociation findAssociationInList(clsAssociation poAssociation, List<clsAssociation> poAssList) {
	    clsAssociation oFoundAssociation = null;
	    
	    if(poAssList.contains(poAssociation)) {
	        oFoundAssociation = poAssociation;
	    } else {
	        for(clsAssociation oCandidate : poAssList) {
	            if(oCandidate.isEquivalentOrClone(poAssociation)) {
	                if(oFoundAssociation == null) {
	                    oFoundAssociation = oCandidate;
	                } else {
	                    log.error("Two or more associations are so equal they might be clones.");
	                    log.error("List: " + poAssList.toString());
	                    log.error("Association: " + oCandidate.toString());
	                }
	            }
	        }
	    }
	    
	    return oFoundAssociation;
	}
	
	/**
     * DOCUMENT - Convenience function that only checks which association interfaces the data structure supports
     *            and attempts to disconnect the association from the data structure (on a trial error basis).
     *            
     *            Disconnecting affects both directions, meaning that disconnecting will remove the association
     *            from the collection of associations AND set the reference from the association to the data
     *            structure to null
     *
     * @author Kollmann
     * @since 16.07.2014 17:11:11
     *
     * @param poAssociation
     * @param poDataStructure
     * @return
     */
    protected static boolean disconnectAssociation(clsAssociation poAssociation, clsDataStructurePA poDataStructure) {
        boolean bDisconnected = false;
        
        //Kollmann 22.07.2014: It happens that the associations are not properly connected (possibly due to cloning errors)
        //                     Example:
        //                      ASS1.A -> TMP1                 // OK
        //                      TMP1.externalAss -> ASS1       // OK
        //                      ASS1.B -> TMP2                 // OK
        //                      TMP2.internalAss -> ASS2       // ERROR - TPM2 should also point to ASS 1
        //
        //                     Since I can not solve that problem quickly, I do not use poAssociation directly, but instead
        //                     I first find the corresponding association (even if it is not EXACTLY the right object) and
        //                     use that instead.
        clsAssociation oAssociationObject = null;
        
        //try to remove the association from the list of internal associations (removes the link from the clsDataStructurePA to the clsAssociation)
        if(poDataStructure instanceof itfInternalAssociatedDataStructure) {
            oAssociationObject = findAssociationInList(poAssociation, ((itfInternalAssociatedDataStructure)poDataStructure).getInternalAssociatedContent());
            if(oAssociationObject != null) {
                bDisconnected |= ((itfInternalAssociatedDataStructure)poDataStructure).getInternalAssociatedContent().remove(oAssociationObject);
            }
        }
        
        //try to remove the association from the list of external associations (removes the link from the clsDataStructurePA to the clsAssociation)
        if(poDataStructure instanceof itfExternalAssociatedDataStructure) {
            oAssociationObject = findAssociationInList(poAssociation, ((itfExternalAssociatedDataStructure)poDataStructure).getExternalAssociatedContent());
            if(oAssociationObject != null) {
                bDisconnected |= ((itfExternalAssociatedDataStructure)poDataStructure).getExternalAssociatedContent().remove(oAssociationObject);
            }
        }
        
        if(!bDisconnected) {
            log.warn("While trying to disconnect a data structure from an association, neither the association, nor a clone of the association could be founde.");
            log.warn("Datastructure: " + poDataStructure.toString());
            log.warn("Association: " + poAssociation.toString());
        }
        
        //complete the disconnection by removing the data structure reference from the association
        //   (removes the link from the clsAssociation to the clsDataStructurePA)
        bDisconnected |= removeAssociationElement(poAssociation, poDataStructure);
        
        return bDisconnected;
    }
    
    /**
     * DOCUMENT - Removes the link from the clsAssociation to the clsDataStructurePA. The method tries to find out if the poDataStructurePA is root
     *            or leaf in the clsAssociation and sets the apropriate end to null.
     *            
     *            The check is done using the comparison operator (==). If the clsDataStructurePA has neither been found in to be Root nor Leaf, the
     *            method does not remove anything, logs an error message and returns false
     *
     * @author Kollmann
     * @since 24.09.2014 11:36:50
     *
     * @param poAssociation
     * @param poDataStructure
     * @return
     */
    protected static boolean removeAssociationElement(clsAssociation poAssociation, clsDataStructurePA poDataStructure) {
        boolean bRemoved = true;
        
        if(bRemoved) {
            if(poAssociation.getAssociationElementA() == poDataStructure) {
                poAssociation.setAssociationElementA(null);
            } else if(poAssociation.getAssociationElementB() == poDataStructure) {
                poAssociation.setAssociationElementB(null);
            } else {
                bRemoved = false;
                log.error("Could not remove association from clsDataStructurePA since the "
                        + "association does not point to the clsDataStructurePA."
                        + "\nclsPhysicalStructureComposition: " + poDataStructure.toString() 
                        + "\nAssociation: " + poAssociation.toString());
            }
        }
        
        return bRemoved;
    }
    
    /**
     * DOCUMENT - This convenience method removes the provided clsAssociation, by first calling disconnectAssociation(...) for the clsAssociation and its
     *            association element A and then calling disconnectAssociation(...) for the clsAssociation and its association element B.
     *            
     *            The association elements are obtained via getAssociationElementA() and getAssociationElementB().
     *            
     *            The method returns only TRUE if the association has been successfully disconnected from both elements. The used method disconnectAssociation(...)
     *            normally only returns true if the disconnection was successful on both ends, meaning the link from clsAssociation to clsDataStructurePA AND the 
     *            link from clsDataStructurePA to clsAssociation, where both removed. Therefore, the method should (normally) only return TRUE if the provided
     *            clsAssociation has been removed completely and is not referencing anything, anymore.
     *            
     *            The method is only able to remove the association from clsDataStructurePAs that are referenced by the association.
     *            Cloning errors and faulty removal attempts might have created shadow nodes that still reference the clsAssociation,
     *            possibly keeping it from being removed by garbage collection.
     *
     * @author Kollmann
     * @since 24.09.2014 11:39:48
     *
     * @param poAssociation
     * @return
     */
    public static boolean removeAssociationCompletely(clsAssociation poAssociation) {
        boolean bRemoved = true;
    
        bRemoved = disconnectAssociation(poAssociation, poAssociation.getAssociationElementA()) && 
                disconnectAssociation(poAssociation, poAssociation.getAssociationElementB());
        
        return bRemoved;
    }
    
    @Override
    public boolean isEquivalentOrClone(clsDataStructurePA poOther) {
        boolean bIsClone = false; 
        
        if(poOther instanceof clsAssociation && this.getClass().equals(poOther.getClass()) && this.getDS_ID() == poOther.getDS_ID()) {
            clsAssociation oOther = (clsAssociation)poOther;
            
            bIsClone = getAssociationElementA().isEquivalentOrClone(oOther.getAssociationElementA()) && 
                    getAssociationElementB().isEquivalentOrClone(oOther.getAssociationElementB());
        }
        
        return bIsClone;
    }
    
    /**
     * DOCUMENT - Goes through a provided list of associations and returns a new list, containing only associations of a certain type
     *
     * @author Kollmann
     * @since 24.09.2014 11:54:09
     *
     * @param poAssociations
     * @return
     */
    public static <T extends clsAssociation> List<T> filterListByType(List<clsAssociation> poAssociations, Class<T> poAssocationType) {
        List<T> oDriveMeshes = new ArrayList<>();
        
        for(clsAssociation oAssociation : poAssociations) {
            if(poAssocationType.isInstance(oAssociation)) {
                oDriveMeshes.add(poAssocationType.cast(oAssociation));
            }
        }
        
        return oDriveMeshes;
    }
    
    protected static boolean performSafeConnect(clsAssociation poAssociation, ArrayList<clsAssociation> poAssociations) {
        boolean bPerformed = false;
        
        clsAssociation oFoundAssociation = clsAssociation.findAssociationInList(poAssociation, poAssociations);
        
        if(oFoundAssociation != null) {
            if(oFoundAssociation != poAssociation) {
                log.error("Trying to connect an association to a target that already contains a clone of that association - "
                        + "this is a sign for a serious flaw in graph handling.\nIt is unclear how to solve this situation, so "
                        + "fix this problem immediately to avoid shadow graphs or similar phenomena");
                //throw new MalformedParametersException("Association shadows existing association");
                poAssociations.remove(clsAssociation.findAssociationInList(poAssociation, poAssociations));
                poAssociations.add(poAssociation);
                bPerformed = true;
            } else {
                log.info("clsAssociation::connect() called to a target that already contains the specified association - now change performed");
            }
        } else {
            poAssociations.add(poAssociation);
            bPerformed = true;    
        }
        
        return bPerformed;
    }
    
    public static boolean connect(clsAssociation poAssociation, clsDataStructurePA poTarget, eConnectionType poConnectionType) {
        boolean bPerformed = false;
        ArrayList<clsAssociation> poAssociations = new ArrayList<>();
        
        switch(poConnectionType) {
        case INTERNAL:
            if(poTarget instanceof itfInternalAssociatedDataStructure) {
                poAssociations = ((itfInternalAssociatedDataStructure)poTarget).getInternalAssociatedContent();
                
                bPerformed = performSafeConnect(poAssociation, poAssociations);
            }
            break;
        case EXTERNAL:
            if(poTarget instanceof itfExternalAssociatedDataStructure) {
                poAssociations = ((itfExternalAssociatedDataStructure)poTarget).getExternalAssociatedContent();
                
                bPerformed = performSafeConnect(poAssociation, poAssociations);
            }
            break;
        default:
            log.error("Unknown root connection type '{}' provided for connection association {} to target {}", poConnectionType, poAssociation, poTarget);
            break;
        }
        
        return bPerformed;
    }
    
    public clsPair<clsDataStructurePA, clsDataStructurePA> activate(eConnectionType poRootConnectionType, eConnectionType poLeafConnectionType) {
        clsPair<clsDataStructurePA, clsDataStructurePA> oEnds = new clsPair<clsDataStructurePA, clsDataStructurePA>(null, null);
        
        oEnds.a = clsAssociation.connect(this, getRootElement(), poRootConnectionType) ? getRootElement() : null;
        oEnds.b = clsAssociation.connect(this, getLeafElement(), poLeafConnectionType) ? getLeafElement() : null;
        
        return oEnds;
    }

    
//    /* (non-Javadoc)
//     *
//     * @since 16.07.2014 17:11:31
//     * 
//     * @see base.datatypes.itfExternalAssociatedDataStructure#removeExternalAssociationCompletely(base.datatypes.clsAssociation)
//     */
//    @Override
//    public boolean removeExternalAssociationCompletely(clsAssociation poAssociation) {
//        boolean bRemoved = false;
//    
//        if(moExternalAssociatedContent.contains(poAssociation)) {
//            //Disconnect from element A
//            bRemoved |= disconnectAssociation(poAssociation, poAssociation.getAssociationElementA());
//            
//            //Disconnect from element B
//            bRemoved |= disconnectAssociation(poAssociation, poAssociation.getAssociationElementB());
//        }
//        
//        return bRemoved;
//    }
//    
//    /* (non-Javadoc)
//     *
//     * @since 16.07.2014 17:11:33
//     * 
//     * @see base.datatypes.itfInternalAssociatedDataStructure#removeInternalAssociationFromThis(base.datatypes.clsAssociation)
//     */
//    @Override
//    public boolean removeInternalAssociationFromThis(clsAssociation poAssociation) {
//        boolean bRemoved = false;
//        
//        if(poAssociation.getAssociationElementA() == this) {
//            //Disconnect from element A
//            bRemoved |= disconnectAssociation(poAssociation, poAssociation.getAssociationElementA());
//        } else {
//            //Disconnect from element B
//            bRemoved |= disconnectAssociation(poAssociation, poAssociation.getAssociationElementA());
//        } 
//        
//        return bRemoved;
//    }
//    
//    /* (non-Javadoc)
//     *
//     * @since 16.07.2014 17:11:36
//     * 
//     * @see base.datatypes.itfInternalAssociatedDataStructure#removeInternalAssociationCompletely(base.datatypes.clsAssociation)
//     */
//    @Override
//    public boolean removeInternalAssociationCompletely(clsAssociation poAssociation) {
//        boolean bRemoved = false;
//        
//        if(moExternalAssociatedContent.contains(poAssociation)) {
//            //Disconnect from element A
//            bRemoved |= disconnectAssociation(poAssociation, poAssociation.getAssociationElementA());
//            
//            //Disconnect from element B
//            bRemoved |= disconnectAssociation(poAssociation, poAssociation.getAssociationElementB());
//        }
//        
//        return bRemoved;
//    }
}
