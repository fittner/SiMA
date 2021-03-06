/**
 * CHANGELOG
 *
 * 27.07.2011 wendt - File created
 *
 */
package base.datatypes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.ePhiPosition;
import memorymgmt.enums.ePredicate;
import memorymgmt.enums.eRadius;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.helpstructures.clsPair;
import base.datatypes.helpstructures.clsTriple;
import base.tools.ElementNotFoundException;
import secondaryprocess.datamanipulation.clsActDataStructureTools;
import secondaryprocess.datamanipulation.clsActTools;
import secondaryprocess.datamanipulation.clsEntityTools;
import secondaryprocess.datamanipulation.clsMeshTools;
import testfunctions.clsTester;

/**
 * A mesh of >=1 word presentations. If a word presentation is a word, then the word presentation is a sentence 
 * 
 * @author wendt
 * 27.07.2011, 13:41:04
 * 
 */
public class clsWordPresentationMesh extends clsLogicalStructureComposition {

	//private String moContent = "UNDEFINED";
    private final static clsWordPresentationMesh moNullObject = new clsWordPresentationMesh(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.NULLOBJECT), new ArrayList<clsAssociation>(), eContentType.NULLOBJECT.toString()); 
	
	/**
	 * DOCUMENT (wendt) - insert description 
	 *
	 * @since 27.07.2011 20:59:03
	 *
	 * @param poDataStructureIdentifier
	 */
	public clsWordPresentationMesh(
			clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, 
			ArrayList<clsAssociation> poAssociatedStructures, Object poContent) {
		super(poDataStructureIdentifier);
		
		moContent = (String)poContent;
		setAssociations(poAssociatedStructures);
		
		// TODO (wendt) - Auto-generated constructor stub
	}
    
    
    /**
     * @since 05.07.2012 22:04:13
     * 
     * @return the moNullObjectWPM
     */
    public static clsWordPresentationMesh getNullObject() {
        return moNullObject;
    }
    
    /**
     * Create an instance of this type
     *
     * @author wendt
     * @since 03.10.2013 15:13:41
     *
     * @return
     */
    public static clsWordPresentationMesh createInstance(eContentType contentType, String content) {
        //Create identifiyer. All goals must have the content type "MENTALSITUATION"
        clsTriple<Integer, eDataType, eContentType> oDataStructureIdentifier = new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, contentType);

        //Create the basic goal structure
        clsWordPresentationMesh oRetVal = new clsWordPresentationMesh(oDataStructureIdentifier, new ArrayList<clsAssociation>(), content);    //Here the current step could be used

        return oRetVal;
    }
	
	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 16.08.2010, 22:12:06
	 *
	 * @param poAssociatedTemporalStructures
	 */
	private void setAssociations(
			ArrayList<clsAssociation> poAssociatedStructures) {
		moInternalAssociatedContent = poAssociatedStructures;
	}
	
	/* (non-Javadoc)
	 *
	 * @since 27.07.2011 20:59:16
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfComparable#compareTo(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
		// TODO (wendt) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since 27.07.2011 20:59:16
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsLogicalStructureComposition#contain(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	@Override
	public boolean contain(clsDataStructurePA poDataStructure) {
		// TODO (wendt) - Auto-generated method stub
		return false;
	}
	
	/**
	 * Find all data structures, which are connected with a certain predicate
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:06:20
	 *
	 * @param poPredicate
	 * @return
	 */
	public ArrayList<clsSecondaryDataStructure> findDataStructure(ePredicate poPredicate, boolean pbStopAtFirstMatch) {
		ArrayList<clsSecondaryDataStructure> oRetVal = new ArrayList<clsSecondaryDataStructure>();
		
		
		
		for (clsAssociation oAss : this.moInternalAssociatedContent) {
			if (oAss instanceof clsAssociationSecondary) {
				if (((clsAssociationSecondary)oAss).getPredicate().equals(poPredicate)==true) {
					oRetVal.add((clsSecondaryDataStructure) oAss.getTheOtherElement(this));
					if (pbStopAtFirstMatch==true) {
						break;
					}
				}
			}
 		}
		
		if (oRetVal.size()==0 || pbStopAtFirstMatch==false) {
			for (clsAssociation oAss : this.moExternalAssociatedContent) {
				if (oAss instanceof clsAssociationSecondary) {
					if (((clsAssociationSecondary)oAss).getPredicate().equals(poPredicate)==true) {
						oRetVal.add((clsSecondaryDataStructure) oAss.getTheOtherElement(this));
						
						if (pbStopAtFirstMatch==true) {
							break;
						}
					}
				}
	 		}
		}
		
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @since 27.07.2011 20:59:16
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsLogicalStructureComposition#assignDataStructure(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		// TODO (wendt) - Auto-generated method stub
		
	}
	
	@Override
//	public Object clone() throws CloneNotSupportedException {
//        try {
//        	clsWordPresentationMesh oClone = (clsWordPresentationMesh)super.clone();
//        	if (moInternalAssociatedContent != null) {
//        		oClone.moInternalAssociatedContent = new ArrayList<clsAssociation>(); 
//        		for(clsAssociation oAssociation : moInternalAssociatedContent){
//        			try { 
//    					Object dupl = oAssociation.clone(this, oClone); 
//    					oClone.moInternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
//    				} catch (Exception e) {
//    					return e;
//    				}
//        		}
//        	}
//        	
//        	if (moExternalAssociatedContent != null) {
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
		return clone(new HashMap<clsDataStructurePA, clsDataStructurePA>());
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
	public Object clone(HashMap<clsDataStructurePA, clsDataStructurePA> poClonedNodeMap) throws CloneNotSupportedException {
		clsWordPresentationMesh oClone = null;
		
		try {
			//Clone the data structure without associated content. They only exists as empty lists
		    
		    //Check if structure already exists in the list
		    if(!poClonedNodeMap.containsKey(this)) {
    			oClone = (clsWordPresentationMesh)super.clone();
    			oClone.moInternalAssociatedContent = new ArrayList<clsAssociation>();
    			oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>();
    			
    			oClone.moAssociationMapping = new HashMap<ePredicate, ArrayList<clsSecondaryDataStructure>>();
    			
    			//Add this structure and the new clone to the list of cloned structures
    			poClonedNodeMap.put(this, oClone);
    			
    			//Go through all associations
    			if (moInternalAssociatedContent != null) {
    				//Add internal associations to oClone 
            		for(clsAssociation oAssociation : moInternalAssociatedContent){
            			try { 
        					Object dupl = oAssociation.clone(this, oClone, poClonedNodeMap); 
        					if(dupl!= null) {
        					    oClone.moInternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
        					
            					clsDataStructurePA oOtherElement = ((clsAssociation)dupl).getTheOtherElement(oClone);
            					if (dupl instanceof clsAssociationSecondary) {
            					    ePredicate oCT = ((clsAssociationSecondary)dupl).getPredicate();
                                    
                                    ArrayList<clsSecondaryDataStructure> oS = oClone.moAssociationMapping.get(oCT);
                                    if (oS==null) {
                                        oS = new ArrayList<clsSecondaryDataStructure>();
                                    }
                                    oS.add((clsSecondaryDataStructure) oOtherElement);
                                    oClone.moAssociationMapping.put(oCT, oS);
            					}
        					}
        					
        				} catch (Exception e) {
        					return e;
        				}
            		}
            	}
    						
    			//Go through all associations
    			if (moExternalAssociatedContent != null) {
    				//Add internal associations to oClone 
            		for(clsAssociation oAssociation : moExternalAssociatedContent){
            			try {
        					Object dupl = oAssociation.clone(this, oClone, poClonedNodeMap); 
        					if(dupl!= null) {
            					oClone.moExternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
            					
                                clsDataStructurePA oOtherElement = ((clsAssociation)dupl).getTheOtherElement(oClone);
                                if (dupl instanceof clsAssociationSecondary) {
                                    ePredicate oCT = ((clsAssociationSecondary)dupl).getPredicate();
                                    
                                    ArrayList<clsSecondaryDataStructure> oS = oClone.moAssociationMapping.get(oCT);
                                    if (oS==null) {
                                        oS = new ArrayList<clsSecondaryDataStructure>();
                                    }
                                    oS.add((clsSecondaryDataStructure) oOtherElement);
                                    oClone.moAssociationMapping.put(oCT, oS);
                                }
        					}
        				} catch (Exception e) {
        					return e;
        				}
            		}
            	}
		    } else {
		        clsLogger.getLog("Cloning").info("Object already in list");
                oClone = (clsWordPresentationMesh) poClonedNodeMap.get(this);
		    }
			
		} catch (CloneNotSupportedException e) {
           return e;
        }
		
        //=== Perform system tests ===//
        clsTester.getTester().setActivated(false);
        if (clsTester.getTester().isActivated()) {
            try {
                log.warn("System tester active");
                log.debug("Testing original {}", this.getContent());
                clsTester.getTester().exeTestCheckLooseAssociations(this);
                clsTester.getTester().exeTestAssociationAssignment(this);
                log.debug("Testing clone {}", oClone.getContent());
                clsTester.getTester().exeTestCheckLooseAssociations(oClone);
                clsTester.getTester().exeTestAssociationAssignment(oClone);
            } catch (Exception e) {
                log.error("Systemtester has an error in " + this.getClass().getSimpleName(), e);
            }
        }
		
		return oClone;
	}
	
	@Override
	public String toString(){
			String oResult = "";
			
			//Add by AW
			if (this.moContentType.equals(eContentType.RI)) {
				if (clsActTools.isIntention(this)==true) {
					//oResult += "::"+this.moDataStructureType+"::";  
					oResult += this.moContentType + ":" + this.moContent;
					//List PI-Match
					double rPIMatch = clsActTools.getPIMatchFlag(this);
					oResult += "(PIMatch=" + new DecimalFormat("0.00").format(rPIMatch) + ")(ActConf=" + new DecimalFormat("0.00").format(clsActTools.getActConfidenceLevel(this)) + ");";
					
					//Get all subimages
					ArrayList<clsWordPresentationMesh> oSubImages = clsActTools.getAllSubImages(this);
					oResult += "\nSUBIMAGES: ";
					for (clsWordPresentationMesh oSubImage : oSubImages) {
						oResult += oSubImage.getContent() + 
								"(PIMatch=" + new DecimalFormat("0.00").format(clsActTools.getPIMatchFlag(oSubImage)) + 
								")(MomConf=" + new DecimalFormat("0.00").format(clsActTools.getMomentConfidenceLevel(oSubImage)) + 
								")(Timeout=" + clsActTools.getMovementTimeoutValue(oSubImage) + ");"; 
					}
				} else if (clsActTools.isEvent(this)==true) {
					//oResult += "::"+this.moDataStructureType+"::";  
					oResult += this.moContentType + ":" + this.moContent;
					//List PI-Match
					double rPIMatch = clsActTools.getPIMatchFlag(this);
					oResult += "(PIMatch=" + new DecimalFormat("0.00").format(rPIMatch) + 
					")(MomConf=" + new DecimalFormat("0.00").format(clsActTools.getMomentConfidenceLevel(this)) + 
					")(Timeout=" + clsActTools.getMovementTimeoutValue(this) + ");";
					oResult += "\nACTION: " + clsActTools.getRecommendedAction(this);
				} else {  
					oResult += this.moContentType + ":" + this.moContent;
					if (this.moInternalAssociatedContent.isEmpty()==false) {
						oResult += "\nINT ASS: ";
						for (clsAssociation oEntry : this.moInternalAssociatedContent) {
							if (oEntry instanceof clsAssociationWordPresentation) {
							    oResult += "\nTPM part:";
							}
						    oResult += oEntry.getLeafElement().toString() + ";";
						}
					}
					
					if (moExternalAssociatedContent.isEmpty()==false) {
						oResult += "\nEXT ASS: ";
						for (clsAssociation oEntry : moExternalAssociatedContent) {
							clsDataStructurePA oDS = oEntry.getTheOtherElement(this);
							if (oDS == null) {
								oResult += "ERRONEOUS ASSOCIATION:" + oEntry;
							} else if (oDS instanceof clsWordPresentationMesh) {
								oResult += ((clsWordPresentationMesh)oDS).moContentType + ":" + ((clsWordPresentationMesh)oDS).moContent + ", "; 
							} else {
								oResult += oDS.toString()  + ","; 
							}
						}
					}
				}
				
			} else if (this.moContentType.equals(eContentType.PI) || this.moContentType.equals(eContentType.MENTALSITUATION) || this.moContentType.equals(eContentType.PHI)) {
				//oResult += "::"+this.moDataStructureType+"::";  
				oResult += this.moContentType + ":" + this.moContent;
				if (this.moInternalAssociatedContent.isEmpty()==false) {
					oResult += "\nINT ASS: ";
					for (clsAssociation oEntry : this.moInternalAssociatedContent) {
						oResult += oEntry.getLeafElement().toString() + ";";
					}
				}
				
				if (moExternalAssociatedContent.isEmpty()==false) {
					oResult += "\nEXT ASS: ";
					for (clsAssociation oEntry : moExternalAssociatedContent) {
						clsDataStructurePA oDS = oEntry.getTheOtherElement(this);
						if (oDS instanceof clsWordPresentationMesh) {
							oResult += ((clsWordPresentationMesh)oDS).moContentType + ":" + ((clsWordPresentationMesh)oDS).moContent + ", "; 
						} else {
							oResult += oDS.toString()  + ","; 
						}
					}
				}
			} else if (this.moContentType.equals(eContentType.ACT)) {
				//oResult += "::"+this.moDataStructureType+"::";  
				oResult += this.moContentType + ":" + this.moContent;
				
				clsWordPresentationMesh oIntention = clsActDataStructureTools.getIntention(this);
				if (oIntention.isNullObject()==false) {
					oResult += "\nINTENTION: " + oIntention.toString();
				}
				clsWordPresentationMesh oMoment = clsActDataStructureTools.getMoment(this);
				if (oMoment.isNullObject()==false) {
					oResult += "\nMOMENT: " + oMoment.toString();
				}
				clsWordPresentationMesh oExpectation = clsActDataStructureTools.getExpectation(this);
				if (oExpectation.isNullObject()==false) {
					oResult += "\nEXPECTATION: " + oExpectation.toString();
				}
				oResult += "\n";
				
			} else if (this.moContentType.equals(eContentType.ENHANCEDENVIRONMENTALIMAGE) || this.moContentType.equals(eContentType.ENVIRONMENTALIMAGE)) {
				//oResult += "::"+this.moDataStructureType+"::";  
				oResult += this.moContent;
				if (this.moInternalAssociatedContent.isEmpty()==false) {
					oResult += "\nINT ASS: ";
					for (clsAssociation oEntry : this.moInternalAssociatedContent) {
						oResult += oEntry.getLeafElement().toString() + ";";
					}
				}
				
			} else if (this.moContentType.equals(eContentType.ENTITY)) {
				oResult += this.moContent;
				
				clsTriple<clsWordPresentationMesh, ePhiPosition, eRadius> oPosition = clsEntityTools.getPosition(this);
				String oPhiPos = "null";
				String oRadPos = "null";
				
				if (oPosition.b!=null) {
					oPhiPos = oPosition.b.toString();
				}
				if (oPosition.c!=null) {
					oRadPos = oPosition.c.toString();
				}
				
				oResult += "(" + oPhiPos + ":" + oRadPos + ")";
			}
			else {
				oResult += "::"+this.moDataStructureType+"::";  
				oResult += this.moDS_ID + ":" + this.moContentType + ":" + this.moContent;
			}

			return oResult; 
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 18.06.2012, 16:38:20
	 *
	 * @return
	 */
	@Override
	public double getNumbInternalAssociations() {
		return moInternalAssociatedContent.size();
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 18.06.2012, 16:38:20
	 *
	 * @return
	 */
	@Override
	public double getNumbExternalAssociations() {
		return moExternalAssociatedContent.size();
	}
	
	/**
	 * Check if the WPM is a null object
	 * 
	 * (wendt)
	 *
	 * @since 21.07.2012 20:49:30
	 *
	 * @return
	 */
	public boolean isNullObject() {
		boolean bResult = false;
		
		if (this.getContentType()==eContentType.NULLOBJECT) {
			bResult=true;
		}
		
		return bResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 13, 2012 1:28:53 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfExternalAssociatedDataStructure#addExternalAssociation(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void addExternalAssociation(clsAssociation poAssociatedDataStructure) {
		// TODO (schaat) - Auto-generated method stub
		this.moExternalAssociatedContent.add(poAssociatedDataStructure);
	}
	
    /**
   * Get the primary component of a WPM
   * 
   * (wendt)
   *
   * @since 22.05.2012 22:01:39
   *
   * @param poInput
   * @return
   */
  public clsAssociationWordPresentation getAssociationWPOfWPM() {
      clsAssociationWordPresentation oRetVal = null;
      
      for (clsAssociation oAss : this.getExternalAssociatedContent()) {
          if (oAss instanceof clsAssociationWordPresentation) {
              //Add the TPM to the output
              oRetVal = (clsAssociationWordPresentation) oAss;
              break;
          }
      }
      
      return oRetVal;
  }
	
	   /**
     * Create a new association secondary between 2 existing objects and add the association to the objects association lists (depending on add state)
     * 
     * (wendt)
     *
     * @since 25.01.2012 16:09:04
     *
     * @param <E> WPM or WP
     * @param poElementOrigin Always a WPM
     * @param nOriginAddAssociationState 0: Do not add, 1: Add to internal associations, 2: Add to external associations
     * @param poElementTarget WPM or WP
     * @param nTargetAddAssociationState 0: Do not add, 1: Add to internal associations, 2: Add to external associations
     * @param prWeight
     * @param poContenType
     * @param poPredicate
     * @param pbSwapDirectionAB
     */
    public <E extends clsSecondaryDataStructure> clsAssociationSecondary createAssociationSecondary(int nOriginAddAssociationState, E poElementTarget, int nTargetAddAssociationState, double prWeight, eContentType poContentType, ePredicate poPredicate, boolean pbSwapDirectionAB) {
        clsAssociationSecondary oResult = null;
        
        //Create association
        clsAssociationSecondary oNewAss;
        if (pbSwapDirectionAB==false) {
            oNewAss = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC(poContentType, this, poElementTarget, poPredicate, prWeight);
        } else {
            oNewAss = (clsAssociationSecondary) clsDataStructureGenerator.generateASSOCIATIONSEC(poContentType, poElementTarget, this, poPredicate, prWeight);
        }
        
        //Process the original Element 
        if (nOriginAddAssociationState==1) {
            this.getInternalAssociatedContent().add(oNewAss);
        } else if (nOriginAddAssociationState==2) {
            this.getExternalAssociatedContent().add(oNewAss);
        }
        //If Associationstate=0, then do nothing
        
        //Add association to the target structure if it is a WPM
        if ((poElementTarget instanceof clsWordPresentationMesh) && (nOriginAddAssociationState!=0)) {
            if (nTargetAddAssociationState==1) {
                ((clsWordPresentationMesh)poElementTarget).getInternalAssociatedContent().add(oNewAss);
            } else if (nTargetAddAssociationState==2) {
                ((clsWordPresentationMesh)poElementTarget).getExternalAssociatedContent().add(oNewAss);
            }
        }
        
        oResult = oNewAss;
        
        return oResult;
    }
	
	
	   /**
     * Set any word presentation to a certain WPM
     * 
     * (wendt)
     *
     * @since 23.05.2012 17:04:04
     *
     * @param poWPM
     * @param poAssContentType
     * @param poAssPredicate
     * @param poWPContentType
     * @param poWPContent
     */
    private void setUniquePredicateWP(eContentType poAssContentType, ePredicate poAssPredicate, eContentType poWPContentType, String poWPContent, boolean pbAddToInternalAssociations) {
        //Get association if exists
        clsAssociation oAss = (clsAssociation) clsMeshTools.searchFirstDataStructureOverAssociationWPM(this, poAssPredicate, 0, true);
        
        if (oAss==null) {
            //Create new WP
            clsWordPresentation oNewPresentation = clsDataStructureGenerator.generateWP(new clsPair<eContentType, Object>(poWPContentType, poWPContent));
            
            //Create and add association
            if (pbAddToInternalAssociations==false) {
                createAssociationSecondary(2, oNewPresentation, 0, 1.0, poAssContentType, poAssPredicate, false);
            } else {
                createAssociationSecondary(1, oNewPresentation, 0, 1.0, poAssContentType, poAssPredicate, false);
            }
            
            
        } else {
            ((clsSecondaryDataStructure)oAss.getTheOtherElement(this)).setContent(poWPContent);
        }
        
    }
    
    /**
     * Set any word presentation to a certain WPM
     * 
     * Non unique = one predicate can have several content
     * 
     * (wendt)
     *
     * @since 17.07.2012 22:11:51
     *
     * @param poWPM
     * @param poAssContentType
     * @param poAssPredicate
     * @param poWPContentType
     * @param poWPContent
     */
    private void setNonUniquePredicateWP(ePredicate poAssPredicate, eContentType poWPContentType, String poWPContent, boolean pbAddToInternalAssociations) {
        //Get association if exists
        ArrayList<clsDataStructurePA> oAssList = clsMeshTools.searchDataStructureOverAssociation(this, poAssPredicate, 0, true, false);
        
        boolean bWPFound = false;
        
        for (clsDataStructurePA oAss : oAssList) {
            //Get WP
            clsWordPresentation oWP = (clsWordPresentation) ((clsAssociation)oAss).getLeafElement();
            if (oWP.getContent().equals(poWPContent) && oWP.getContentType().equals(poWPContentType)) {
                bWPFound = true;    //Do nothing as it is already set
                break;
            }
        }
        
        if (bWPFound==false) {
            //Create new WP
            clsWordPresentation oNewPresentation = clsDataStructureGenerator.generateWP(new clsPair<eContentType, Object>(poWPContentType, poWPContent));
            
            //Create and add association
            if (pbAddToInternalAssociations==false) {
                createAssociationSecondary(2, oNewPresentation, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, poAssPredicate, false);
            } else {
                createAssociationSecondary(1, oNewPresentation, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, poAssPredicate, false);
            }
            
            
        }
    }
    
    /**
     * Add a new WPM with predicate asspredicate to a certain other wpm. This is used to add an action to a goal.
     * 
     * (wendt)
     *
     * @since 26.09.2012 12:31:58
     *
     * @param poOriginWPM
     * @param poAssPredicate
     * @param poAddWPM
     * @param pbAddToInternalAssociations
     */
    private void setNonUniquePredicateWPM(ePredicate poAssPredicate, clsWordPresentationMesh poAddWPM, boolean pbAddToInternalAssociations) {
        //Get association if exists
        ArrayList<clsDataStructurePA> oAssList = clsMeshTools.searchDataStructureOverAssociation(this, poAssPredicate, 0, true, false);
        
        boolean bWPFound = false;
        
        for (clsDataStructurePA oAss : oAssList) {
            clsDataStructurePA oDS = ((clsAssociation)oAss).getLeafElement();
            if (oDS instanceof clsWordPresentationMesh) {
                clsWordPresentationMesh oWPM = (clsWordPresentationMesh) oDS;
                
                if (oWPM.equals(poAddWPM)==true) {
                    bWPFound = true;    //Do nothing as it is already set
                    break;
                }
            }
        }
        
        if (bWPFound==false) {
            
            //Create and add association
            if (pbAddToInternalAssociations==false) {
                createAssociationSecondary(2, poAddWPM, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, poAssPredicate, false);
            } else {
                createAssociationSecondary(1, poAddWPM, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, poAssPredicate, false);
            }
        }
    }
    
    /**
     * Add a new WPM with predicate asspredicate to a certain other wpm. This is used to add an action to a goal.
     * 
     * (wendt)
     *
     * @since 26.09.2012 12:31:58
     *
     * @param poOriginWPM
     * @param poAssPredicate
     * @param poAddWPM
     * @param pbAddToInternalAssociations
     */
    private void setUniquePredicateWPM(ePredicate poAssPredicate, clsWordPresentationMesh poAddWPM, boolean pbAddToInternalAssociations) {
        //Get association if exists
        ArrayList<clsDataStructurePA> oAssList = clsMeshTools.searchDataStructureOverAssociation(this, poAssPredicate, 0, true, false);
        
        boolean bWPFound = false;
        //clsAssociation oFoundAss = null;
        
        if (oAssList.isEmpty()==false) {
            bWPFound = true;
        }
        
//        for (clsDataStructurePA oAss : oAssList) {
//            clsDataStructurePA oDS = ((clsAssociation)oAss).getLeafElement();
//            if (oDS instanceof clsWordPresentationMesh) {
//                clsWordPresentationMesh oWPM = (clsWordPresentationMesh) oDS;
//                
//                if (oWPM.getMoContent().equals(poAddWPM.getMoContent()) && oWPM.getMoContentType().equals(poAddWPM.getMoContentType())) {
//                    bWPFound = true;    //Do nothing as it is already set
//                    oFoundAss = (clsAssociation) oAss;
//                    break;
//                }
//            }
//        }
        
        if (bWPFound==false) {
            
            //Create and add association
            if (pbAddToInternalAssociations==false) {
                createAssociationSecondary(2, poAddWPM, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, poAssPredicate, false);
            } else {
                createAssociationSecondary(1, poAddWPM, 0, 1.0, eContentType.ASSOCIATIONSECONDARY, poAssPredicate, false);
            }
        } else {
            //Replace
            ((clsAssociation)oAssList.get(0)).setLeafElement(poAddWPM);
        }
    }
    
    /**
     * Get the first WP for a certain predicate in a certian mesh
     * 
     * (wendt)
     *
     * @since 12.07.2012 17:26:47
     *
     * @param poWPM
     * @param poAssPredicate
     * @return
     */
    private clsWordPresentation getUniquePredicateWP(ePredicate poAssPredicate) {
        //clsWordPresentation oResult = null;
        
        //clsAssociation oAss = (clsAssociation) clsMeshTools.searchFirstDataStructureOverAssociationWPM(poWPM, poAssPredicate, 0, true);
        clsWordPresentation oResult = (clsWordPresentation) clsMeshTools.searchFirstDataStructureOverAssociationWPM(this, poAssPredicate, 0, false);
//      if (oAss!=null) {
//          oResult = (clsWordPresentation)oAss.getTheOtherElement(poWPM);
//      }
        
        return oResult;
    }
    
    /**
     * Get a unique predicate from the association list
     * 
     * (wendt)
     *
     * @since 17.05.2013 10:41:28
     *
     * @param poAssPredicate
     * @return
     */
    private clsWordPresentationMesh getUniquePredicateWPM(ePredicate poAssPredicate) {
        clsWordPresentationMesh oResult = clsMeshTools.getNullObjectWPM();
        
        clsDataStructurePA oResultPrel = clsMeshTools.searchFirstDataStructureOverAssociationWPM(this, poAssPredicate, 0, false);
        
        if (oResultPrel instanceof clsWordPresentationMesh) {
            oResult = (clsWordPresentationMesh) oResultPrel;
        }
        
        return oResult;
    }
    
    /**
     * Get all WP of a certain predicate
     * 
     * (wendt)
     *
     * @since 16.07.2012 20:56:24
     *
     * @param poWPM
     * @param poAssPredicate
     * @return
     */
    private ArrayList<clsSecondaryDataStructure> getNonUniquePredicateSecondaryDataStructure(ePredicate poAssPredicate) {
        ArrayList<clsSecondaryDataStructure> oResult = new ArrayList<clsSecondaryDataStructure>();
            
        ArrayList<clsDataStructurePA> oDSList = clsMeshTools.searchDataStructureOverAssociation(this, poAssPredicate, 0, false, false);

        for (clsDataStructurePA oDS : oDSList) {
            if (oDS instanceof clsSecondaryDataStructure) {
                oResult.add((clsSecondaryDataStructure) oDS);
            }
        }
            
        return oResult;
    }
    
    /**
     * Get all WP of a certain predicate
     * 
     * (wendt)
     *
     * @since 16.07.2012 20:56:24
     *
     * @param poWPM
     * @param poAssPredicate
     * @return
     */
    private ArrayList<clsWordPresentation> getNonUniquePredicateWP(ePredicate poAssPredicate) {
        ArrayList<clsWordPresentation> oResult = new ArrayList<clsWordPresentation>();
        
        ArrayList<clsSecondaryDataStructure> oSecondaryList = getNonUniquePredicateSecondaryDataStructure(poAssPredicate);
        
        //ArrayList<clsDataStructurePA> oDSList = clsMeshTools.searchDataStructureOverAssociation(poWPM, poAssPredicate, 0, false, false);

        for (clsSecondaryDataStructure oDS : oSecondaryList) {
            if (oDS instanceof clsWordPresentation)
            oResult.add((clsWordPresentation) oDS);
        }
            
        return oResult;
    }
    
    /**
     * Get all WP of a certain predicate
     * 
     * (wendt)
     *
     * @since 16.07.2012 20:56:24
     *
     * @param poWPM
     * @param poAssPredicate
     * @return
     */
    private ArrayList<clsWordPresentationMesh> getNonUniquePredicateWPM(ePredicate poAssPredicate) {
        ArrayList<clsWordPresentationMesh> oResult = new ArrayList<clsWordPresentationMesh>();
        
        ArrayList<clsSecondaryDataStructure> oSecondaryList = getNonUniquePredicateSecondaryDataStructure(poAssPredicate);
        
        //ArrayList<clsDataStructurePA> oDSList = clsMeshTools.searchDataStructureOverAssociation(poWPM, poAssPredicate, 0, false, false);
        //if (oSecondaryList!=null) {
            for (clsSecondaryDataStructure oDS : oSecondaryList) {
                if (oDS instanceof clsWordPresentationMesh)
                oResult.add((clsWordPresentationMesh) oDS);
            }
        //}
        
            
        return oResult;
    }
    
    /**
     * Add or replace the assciation shortcut mapping in the hastable of this structure
     * 
     * (wendt) - insert description
     *
     * @since 17.05.2013 10:29:08
     *
     * @param oContentType
     * @param oPredicate
     * @param pbUniqueProperty
     */
    private void addToAssociationMapping(ePredicate oPredicate, boolean pbUniqueProperty, eDataType poDataType) {
        ArrayList<clsSecondaryDataStructure> oAddStructureList = new ArrayList<clsSecondaryDataStructure>();
        if (pbUniqueProperty==true) {
            
            if (poDataType.equals(eDataType.WPM)) {
                clsWordPresentationMesh oUniqueWPM = getUniquePredicateWPM(oPredicate);
                if (((clsWordPresentationMesh)oUniqueWPM).isNullObject()==false) {
                    oAddStructureList.add(oUniqueWPM);
                    this.moAssociationMapping.put(oPredicate, oAddStructureList);
                }
            } else if (poDataType.equals(eDataType.WP)) {
                clsWordPresentation oUniqueWP = this.getUniquePredicateWP(oPredicate);
                if (oUniqueWP!=null) {
                    oAddStructureList.add(oUniqueWP);
                    this.moAssociationMapping.put(oPredicate, oAddStructureList);
                }
            }
           
        } else {
            //Get all of them
            //ArrayList<clsSecondaryDataStructure> oNonUniqueStructure = this.moAssociationMapping.get(oContentType);
            if (poDataType.equals(eDataType.WPM)) {
                ArrayList<clsWordPresentationMesh> oUniqueWPMList = getNonUniquePredicateWPM(oPredicate);
                oAddStructureList.addAll(oUniqueWPMList);
                //oNonUniqueStructure.addAll(this.getNonUniquePredicateWPM(oPredicate));
                this.moAssociationMapping.put(oPredicate, oAddStructureList);
            } else if (poDataType.equals(eDataType.WP)) {
                ArrayList<clsWordPresentation> oUniqueWPList = getNonUniquePredicateWP(oPredicate);
                oAddStructureList.addAll(oUniqueWPList);
                //oNonUniqueStructure.addAll(this.getNonUniquePredicateWPM(oPredicate));
                this.moAssociationMapping.put(oPredicate, oAddStructureList);
            }
        }
    }
    
    /**
     * (wendt)
     *
     * @since 17.05.2013 11:48:11
     *
     * @param oContentType
     * @param poRemoveStructure
     * @return
     * @throws Exception 
     */
    private void removeAssociationMapping(ePredicate oPredicate, clsSecondaryDataStructure poRemoveStructure) throws ElementNotFoundException{
        boolean bSuccessfulRemoval = false;
        ArrayList<clsSecondaryDataStructure> oDS = this.moAssociationMapping.get(oPredicate);
        if (oDS!=null) {
            bSuccessfulRemoval = oDS.remove(poRemoveStructure);
        }
        
        
        if (bSuccessfulRemoval==false) {
            throw new ElementNotFoundException("The target structure " + poRemoveStructure + " was not found in the list " + this.moAssociationMapping.get(oPredicate));
        }
        
    }
    
    /**
     * Set unique property of a WPM, which is a WPM
     * 
     * (wendt)
     *
     * @since 17.05.2013 10:29:53
     *
     * @param poProperty
     * @param oContentType
     * @param oPredicate
     */
    protected void setUniqueProperty(clsWordPresentationMesh poProperty, ePredicate oPredicate, boolean pbInternalAssociations) {
        setUniquePredicateWPM(oPredicate, (clsWordPresentationMesh)poProperty, pbInternalAssociations);
        addToAssociationMapping(oPredicate, true, eDataType.WPM);
    }
    
    /**
     * Add a non unique property, which is a WPM to the WPM
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:15:10
     *
     * @param poProperty
     * @param oContentType
     * @param oPredicate
     * @param pbInternalAssociations
     */
    protected void addReplaceNonUniqueProperty(clsWordPresentationMesh poProperty, ePredicate oPredicate, boolean pbInternalAssociations) {
        setNonUniquePredicateWPM(oPredicate, poProperty, pbInternalAssociations);
        addToAssociationMapping(oPredicate, false, eDataType.WPM);
    }
    
    /**
     * Create a new WP and connect it to a WPM for a property. Add the new structure to the hashmap
     * 
     * (wendt)
     *
     * @since 17.05.2013 10:30:21
     *
     * @param poProperty
     * @param oContentType
     * @param oPredicate
     */
    protected void setUniqueProperty(String poProperty, eContentType oContentType, ePredicate oPredicate, boolean pbInternalAssociations) {
        setUniquePredicateWP(eContentType.ASSOCIATIONSECONDARY, oPredicate, oContentType, poProperty, pbInternalAssociations);
        addToAssociationMapping(oPredicate, true, eDataType.WP);
    }
    
    /**
     * Set a non unique property, which is a string. WPs are created and added/replaced in the WPM 
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:17:23
     *
     * @param poProperty
     * @param oContentType
     * @param oPredicate
     * @param pbInternalAssociations
     */
    protected void addReplaceNonUniqueProperty(String poProperty, eContentType oContentType, ePredicate oPredicate, boolean pbInternalAssociations) {
        setNonUniquePredicateWP(oPredicate, oContentType, poProperty, pbInternalAssociations);
        addToAssociationMapping(oPredicate, false, eDataType.WP);
    }
    
    /**
     * Get unique property, which is a WPM
     * 
     * (wendt)
     *
     * @since 17.05.2013 10:35:00
     *
     * @param oContentType
     * @return
     */
    protected clsWordPresentationMesh getUniquePropertyWPM(ePredicate oPredicate) {
        clsWordPresentationMesh oRetVal = clsMeshTools.getNullObjectWPM();
        
        ArrayList<clsSecondaryDataStructure> oS = this.moAssociationMapping.get(oPredicate);
        
        if (oS!=null && oS.isEmpty()==false) {
            oRetVal = (clsWordPresentationMesh) oS.get(0);
        }
        
        return oRetVal;
    }
    
    /**
     * Get all non unique properties, which are of type WPM
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:20:20
     *
     * @param oContentType
     * @return
     */
    protected ArrayList<clsWordPresentationMesh> getNonUniquePropertyWPM(ePredicate oPredicate) {
        ArrayList<clsWordPresentationMesh> oRetVal = new ArrayList<clsWordPresentationMesh>();
    
        ArrayList<clsSecondaryDataStructure> oDS = this.moAssociationMapping.get(oPredicate);
        
        if (oDS!=null) {
            for (clsSecondaryDataStructure oF : oDS) {
                oRetVal.add((clsWordPresentationMesh) oF);
            }
        }
        
    
        return oRetVal;
    }
    
    /**
     * DOCUMENT - Gets all non unique properties of a specified sub-class of WPM in a List (this is the more generic version of getNonUniquePropertyWPM)
     *            Only properties with the specified predicate AND the specified type (provided via Class<T>) are returned
     *
     * @author Kollmann
     * @since 19.08.2014 13:35:39
     *
     * @param poPredicate: the predicate the sought after properties have
     * @param poPropertyType: the class of the sought after properties
     * @return
     */
    protected <PROPERTY_TYPE extends clsWordPresentationMesh> List<PROPERTY_TYPE> getNonUniquePropertyWPM (ePredicate poPredicate, Class<PROPERTY_TYPE> poPropertyType) {
        List<PROPERTY_TYPE> oRetVal = new ArrayList<PROPERTY_TYPE>();
        
        //List<clsSecondaryDataStructure> oDS = this.moAssociationMapping.get(poPredicate);
        
        //The association mapping was not implemented consistently (it seems) - get association if exists
        List<clsDataStructurePA> oAssList = clsMeshTools.searchDataStructureOverAssociation(this, poPredicate, 0, true, false);
        
        if (oAssList != null) {
            for (clsDataStructurePA oPropertyAss : oAssList) {
                if(oPropertyAss instanceof clsAssociationSecondary) {
                    clsDataStructurePA oProperty = ((clsAssociationSecondary) oPropertyAss).getTheOtherElement(this);
                    if(poPropertyType.isInstance(oProperty)) {
                        oRetVal.add(poPropertyType.cast(oProperty));
                    }
                }
            }
        }
        
        return oRetVal;
    }
    
    /**
     * DOCUMENT (wendt)
     *
     * @since 17.05.2013 11:24:13
     *
     * @param oContentType
     * @return
     */
    protected String getUniqueProperty(ePredicate oPredicate) {
        //TODO AW: Create a nullobject for WPs too
        String oRetVal = "";
    
        ArrayList<clsSecondaryDataStructure> oWP = this.moAssociationMapping.get(oPredicate);
        
        if (oWP!=null && oWP.isEmpty()==false) {
            oRetVal = ((clsWordPresentation) oWP.get(0)).getContent();
        }
    
        return oRetVal;
    }
    
    /**
     * Get non unique properties
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:24:13
     *
     * @param oContentType
     * @return
     */
    protected ArrayList<String> getNonUniqueProperty(ePredicate oPredicate) {
        //TODO AW: Create a nullobject for WPs too
        ArrayList<String> oRetVal = new ArrayList<String>();
    
        ArrayList<clsSecondaryDataStructure> oWP = this.moAssociationMapping.get(oPredicate);
        
        if (oWP!=null) {
            for (clsSecondaryDataStructure oC : oWP) {
                oRetVal.add(oC.getContent());
            }
        }

        return oRetVal;
    }
    
    /**
     * Remove a property
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:55:05
     *
     * @param poRemoveStructure
     * @param oContentType
     * @param oPredicate
     * @throws Exception 
     */
    protected void removeProperty(String poRemoveContent, ePredicate oPredicate) throws ElementNotFoundException {
        ArrayList<clsWordPresentation> oFoundStructureList = this.getNonUniquePredicateWP(oPredicate);
        
        for (clsWordPresentation oListElement : oFoundStructureList) {
            if (oListElement.getContent().equals(poRemoveContent)) {
                clsMeshTools.removeAssociationInObject(this, oListElement);
                this.removeAssociationMapping(oPredicate, oListElement);
                break;
            }
        }
    }
    
    /**
     * Remove all properties of a certain type
     * 
     * (wendt)
     *
     * @since 17.05.2013 12:01:36
     *
     * @param oContentType
     * @param oPredicate
     */
    protected void removeAllProperties(ePredicate oPredicate) {
        clsMeshTools.removeAssociationInObject(this, oPredicate);
        this.moAssociationMapping.remove(oPredicate);
    }

    /**
     * Get the Feelings from an Image.
     * 
     * (wendt) (moved and modified by kollmann)
     *
     * @since 26.03.2012 21:25:11
     *
     * @param poGoal
     * @return
     */
    public ArrayList<clsWordPresentationMeshFeeling> getFeelings() {
        return (ArrayList<clsWordPresentationMeshFeeling>) getNonUniquePropertyWPM(ePredicate.HASFEELING, clsWordPresentationMeshFeeling.class);
        
        //if the image had no feelings, try to get the 
 
//        ArrayList<clsWordPresentationMesh> oRetVal = this.getNonUniquePropertyWPM(ePredicate.HASFEELING);;
//    
//        ArrayList<clsWordPresentationMeshFeeling> result = new ArrayList<clsWordPresentationMeshFeeling>();
//        
//        for (clsWordPresentationMesh wpm : oRetVal) {
//            if (wpm instanceof clsWordPresentationMeshFeeling) {
//                result.add((clsWordPresentationMeshFeeling) wpm);
//            } else {
//                throw new ClassCastException("This structure is no valid class for this association " + wpm);
//            }
//        }
//    
//        return result;
    }
    
    /**
     * Add a Feeling to the WPM, which is not already present. If present, then replace
     * 
     * (wendt) (moved by kollmann)
     *
     * @since 17.05.2013 10:37:48
     *
     * @param poFeeling
     */
    public void addFeeling(clsWordPresentationMeshFeeling poFeeling) {
        this.addReplaceNonUniqueProperty(poFeeling, ePredicate.HASFEELING, true);
    }
    
    /**
     * Add an attributed emotion to the WPM, which is not already present. If present, then replace
     * 
     * (kollmann)
     *
     * @since 17.05.2013 10:37:48
     *
     * @param poEmotion
     */
    public void addAttributedEmotion(clsWordPresentationMeshEmotion poEmotion) {
        this.addReplaceNonUniqueProperty(poEmotion, ePredicate.HASATTRIBUTEDEMOTION, false);
    }
    
    /**
     * Add an attributed emotion to the WPM, which is not already present. If present, then replace
     * 
     * (kollmann)
     *
     * @since 17.05.2013 10:37:48
     *
     * @param poEmotion
     */
    public ArrayList<clsWordPresentationMeshEmotion> getAttributedEmotions() {
        return (ArrayList<clsWordPresentationMeshEmotion>) getNonUniquePropertyWPM(ePredicate.HASATTRIBUTEDEMOTION, clsWordPresentationMeshEmotion.class);
    }
    
    /**
     * Add a list of feelings
     * 
     * (wendt) (moved by kollmann)
     *
     * @since 17.05.2013 11:33:11
     *
     * @param poFeeling
     */
    public void addFeelings(ArrayList<clsWordPresentationMeshFeeling> poFeeling) {
        for (clsWordPresentationMeshFeeling oF : poFeeling) {
            addFeeling(oF);
        }
    }
}
