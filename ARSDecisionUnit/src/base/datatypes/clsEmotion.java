/**
 * CHANGELOG
 *
 * Jun 26, 2012 schaat - File created
 *
 */
package base.datatypes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.LogManager;
import org.slf4j.Logger;
import org.apache.log4j.Logger;


import logger.clsLogger;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eEmotionType;
import base.datahandlertools.clsDataStructureGenerator;
import base.datatypes.helpstructures.clsTriple;
import inspector.interfaces.Singleton;

/**
 * DOCUMENT (schaat) - insert description 
 * 
 * @author schaat
 * Jun 26, 2012, 12:21:05 PM
 * 
 */
public class clsEmotion extends clsPrimaryDataStructure implements itfExternalAssociatedDataStructure{
	private eEmotionType moContent = null;
	private ArrayList<clsAssociation> moExternalAssociatedContent = null; 
	private double mrEmotionIntensity = 0.0; 
	private double mrIntensityDeviation = 0.0;
	
	// save the values of those components that the emotion is based on (dependent on the emotion)
	private double mrSourcePleasure = 0.0; 
	private double mrSourceUnpleasure = 0.0;
	private double mrSourceLibid = 0.0;
	private double mrSourceAggr = 0.0;
	private double mrRelativeThreshold = 0.0;
	private double mrThresholdRange = 0.0;
	protected final static Logger logFim1 = logger.clsLogger.getLog("Fim");
	
	public clsEmotion(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, double prEmotionIntensity, double prIntensityDeviation, 
	        eEmotionType poContent, double prSourcePleasure, double prSourceUnpleasure, double prSourceLibid, double prSourceAggr) {
		super(poDataStructureIdentifier); 
		mrEmotionIntensity = prEmotionIntensity;
		mrIntensityDeviation = prIntensityDeviation;
		moContent = poContent;
		mrSourcePleasure = prSourcePleasure; 
		mrSourceUnpleasure = prSourceUnpleasure;
		mrSourceLibid = prSourceLibid;
		mrSourceAggr = prSourceAggr ;
		moExternalAssociatedContent = new ArrayList<>();
	} 

	//Copy constructor - THIS SHOULD ONLY CREATE A FLEET COPY - NO RECURSIONS!
	public clsEmotion(clsEmotion poToCopy) {
	    this(new clsTriple<Integer, eDataType, eContentType>(poToCopy.getDS_ID(), poToCopy.getMoDataStructureType(), poToCopy.getContentType()),
	            poToCopy.getEmotionIntensity(),
	            poToCopy.getIntensityDeviation(),
	            poToCopy.getContent(),
	            poToCopy.getSourcePleasure(),
	            poToCopy.getSourceUnpleasure(),
	            poToCopy.getSourceLibid(),
	            poToCopy.getSourceAggr());
	}
	
	public clsEmotion flatCopy() {
	    return new clsEmotion(this);
	}
	
	public static clsEmotion fromTPM(clsThingPresentationMesh poTPM) {
	    clsDataStructurePA oEmotion = null;
	    
	    for(clsAssociationEmotion oAssEmotion : clsAssociation.filterListByType(poTPM.getExternalAssociatedContent(), clsAssociationEmotion.class)) {
	        oEmotion = oAssEmotion.getTheOtherElement(poTPM);
	        if(oEmotion != null && oEmotion instanceof clsEmotion) {
	            return (clsEmotion) oEmotion;
	        }
	    }
	    
	    return null;
	}
	
	public static clsEmotion fromFeeling(clsWordPresentationMeshFeeling poFeeling) {
	    eEmotionType oEmotionType = eEmotionType.valueOf(poFeeling.getContent());
	    double rEmotionIntensity = poFeeling.getIntensity();
	    double rEmotionPleasure = poFeeling.getPleasure();
        double rEmotionUnpleasure = poFeeling.getUnpleasure();
        double rEmotionLibid = poFeeling.getLibido();
        double rEmotionAggr = poFeeling.getAggression();
                
        clsEmotion oEmotion = clsDataStructureGenerator.generateEMOTION(new clsTriple <eContentType, eEmotionType, Object>(eContentType.BASICEMOTION, oEmotionType, rEmotionIntensity),
                rEmotionPleasure, rEmotionUnpleasure, rEmotionLibid, rEmotionAggr);
        
        return oEmotion;
	}
	
	static protected double doubleMatch(double rLHV, double rRHV) {
	    double rMatch;
	    rMatch = 1 - Math.abs(rLHV - rRHV);
//	    if (Math.abs(rLHV - rRHV)>0.9)
//	    {
//	        rMatch = 0.1;
//	    }
//        else if(Math.abs(rLHV - rRHV)>0.8)
//        {
//            rMatch = 0.2;
//        }
//        else if(Math.abs(rLHV - rRHV)>0.7)
//        {
//            rMatch = 0.3;
//        }
//        else if(Math.abs(rLHV - rRHV)>0.6)
//        {
//            rMatch = 0.4;
//        }
//        else if(Math.abs(rLHV - rRHV)>0.5)
//        {
//            rMatch = 0.5;
//        }
//        else if(Math.abs(rLHV - rRHV)>0.4)
//        {
//            rMatch = 0.6;
//        }
//        else if(Math.abs(rLHV - rRHV)>0.3)
//        {
//            rMatch = 0.7;
//        }
//        else if(Math.abs(rLHV - rRHV)>0.2)
//        {
//            rMatch = 0.8;
//        }
//        else if(Math.abs(rLHV - rRHV)>0.1)
//        {
//            rMatch = 0.9;
//        }
//        else
//        {
//            rMatch = 1;
//        }
	    
        return rMatch;
	}
	
	static protected double matchingFunction(clsEmotion oLHV, clsEmotion oRHV) {
	    //delacruz: division of Aggression, Libido, Pleasure and Unpleasure Emotion Matches 
	    Logger datalogger = LogManager.getLogger("GetImageMatch");
	    double rMatch = 0;
	    double AggrMatch = 0;
	    double LibMatch = 0;
	    double PleasMatch = 0;
	    double UnpleasMatch = 0;
	    double totalMatch = 0;
	    Singleton PIEmotionMatch = Singleton.getInstance();
	    
	    AggrMatch= doubleMatch(oLHV.getSourceAggr(), oRHV.getSourceAggr());
	    LibMatch = doubleMatch(oLHV.getSourceLibid(), oRHV.getSourceLibid());
	    PleasMatch= doubleMatch(oLHV.getSourcePleasure(), oRHV.getSourcePleasure());
	    UnpleasMatch= doubleMatch(oLHV.getSourceUnpleasure(), oRHV.getSourceUnpleasure());
	       
	    rMatch = AggrMatch + LibMatch + PleasMatch + UnpleasMatch;
	    totalMatch = rMatch / 4; 
	    //delacruz: add Emotion Matches to singleton
	    
	    try {
	        PIEmotionMatch.setEmotionMatch(totalMatch, AggrMatch, LibMatch, PleasMatch, UnpleasMatch);
	    }
	    catch(Exception e) {
	        datalogger.debug("Error while setting Emotion Match" + e + "\n");
	    }
	   
	   
	    //PIEmotionMatch.addToPIMatchList();
	    datalogger.debug("Aggression Match: " + AggrMatch);
	    datalogger.debug("Libido Match: " + LibMatch);
	    datalogger.debug("Pleasure Match: " + PleasMatch);
	    datalogger.debug("Unpleasure Match: " + UnpleasMatch);
	    
	    return totalMatch;
	}
	
	/* (non-Javadoc) Kollmann: calculates match between two emotions as single value
	 * 
	 * @since 27.02.2015 17:50:53
	 * 
	 * @see base.datatypes.itfComparable#compareTo(base.datatypes.clsDataStructurePA)
	 */
	@Override
	public double compareTo(clsDataStructurePA poDataStructure) {
	    double rMatch = 0;
	    clsEmotion oOtherEmotion = null;
	    
		//if the other data structure is no cleEmotion -> no match
	    if(poDataStructure instanceof clsEmotion) {
	        oOtherEmotion = (clsEmotion)poDataStructure;
	        //kollmann: memorizedemotions can only be compared with other memorizedemotions - basic and attributedemotions can be compared to any other emotion type
            switch(getContentType()) {
            case MEMORIZEDEMOTION:
                if(oOtherEmotion.getContentType().equals(eContentType.MEMORIZEDEMOTION)) {
                    rMatch = clsEmotion.matchingFunction(this, oOtherEmotion);
                }
                break;
            case BASICEMOTION:
            case ATTRIBUTEDEMOTION:
                rMatch = clsEmotion.matchingFunction(this, oOtherEmotion);
                break;
            }
	    }
	    
		return rMatch;
	}
	
	public static clsEmotion zeroEmotion(eContentType oContentType, eEmotionType oEmotionType) {
	    return clsDataStructureGenerator.generateEMOTION(new clsTriple<eContentType, eEmotionType, Object>(oContentType, oEmotionType, 0.0), 0.0, 0.0, 0.0, 0.0);
	}
	
	private double stepChange(double prOldValue, double prNewValue, double prMaxChange) {
	    //double rDiff = prNewValue - prOldValue;
	    //return Math.min(rDiff, Math.signum(rDiff) * prMaxChange);
	    
	    return (prNewValue - prOldValue) * prMaxChange;
	    
//	    double rDiff = prNewValue - prOldValue;
//	    if(Math.abs(rDiff) < prMaxChange) {
//	        return rDiff;
//	    } else {
//	        return rDiff * prMaxChange;
//	    }
	}
	
	public void gradualChange(clsEmotion poTargetState, double prMaxChange) {
	    mrEmotionIntensity += stepChange(mrEmotionIntensity, poTargetState.getEmotionIntensity(), prMaxChange);
	    mrSourcePleasure += stepChange(mrSourcePleasure, poTargetState.getSourcePleasure(), prMaxChange);
	    mrSourceUnpleasure += stepChange(mrSourceUnpleasure, poTargetState.getSourceUnpleasure(), prMaxChange);
	    mrSourceLibid += stepChange(mrSourceLibid, poTargetState.getSourceLibid(), prMaxChange);
	    mrSourceAggr += stepChange(mrSourceAggr, poTargetState.getSourceAggr(), prMaxChange);
	}
	
	public void sub(clsEmotion poEmotion) {
        //kollmann: emotions that are not of the same type (e.g. BASICEMOTION) and content (e.g. ANXIETY) always produce a match of 0
        if(getContentType().equals(poEmotion.getContentType()) && getContent().equals(poEmotion.getContent())) {
            switch(getContentType()) {
            case MEMORIZEDEMOTION:
            case BASICEMOTION:
                mrEmotionIntensity -= poEmotion.getEmotionIntensity();
                mrSourcePleasure -= poEmotion.getSourcePleasure();
                mrSourceUnpleasure -= poEmotion.getSourceUnpleasure();
                mrSourceLibid -= poEmotion.getSourceLibid();
                mrSourceAggr -= poEmotion.getSourceAggr();
                break;
            }
        } else {
            throw new RuntimeException("Can not subtract two emotions with different content types");
        }
	}
	
	/**
	 * DOCUMENT (Kollmann) - calculates the difference of two emotions as resulting emotion 
	 * 
     * @author Kollmann
     * 
     * 27.02.2015, 00:00:00
     * 
     */
	public clsEmotion diff(clsEmotion poEmotion) {
        clsEmotion oEmotionDiff = null;
        
        //kollmann: emotions that are not of the same type (e.g. BASICEMOTION) and content (e.g. ANXIETY) always produce a math of 0
        if(getContentType().equals(poEmotion.getContentType()) && getContent().equals(poEmotion.getContent())) {
            switch(getContentType()) {
            case MEMORIZEDEMOTION:
            case BASICEMOTION:
                oEmotionDiff = clsDataStructureGenerator.generateEMOTION(
                    new clsTriple <eContentType, eEmotionType, Object>(
                            getContentType(),
                            getContent(),
                            getEmotionIntensity() - poEmotion.getEmotionIntensity()),
                            getSourcePleasure() - poEmotion.getSourcePleasure(),
                            getSourceUnpleasure() - poEmotion.getSourceUnpleasure(),
                            getSourceLibid() - poEmotion.getSourceLibid(),
                            getSourceAggr() - poEmotion.getSourceAggr());
            }
        }
        
        return oEmotionDiff;
    }
	
	   /**
     * DOCUMENT (Kollmann) - calculates the difference of two emotions as resulting emotion 
     * 
     * @author Kollmann
     * 
     * 27.02.2015, 00:00:00
     * 
     */
    public clsEmotion merge(clsEmotion poEmotion) {
        clsEmotion oEmotionDiff = null;
        
        //kollmann: emotions that are not of the same type (e.g. BASICEMOTION) and content (e.g. ANXIETY) always produce a math of 0
        if(getContentType().equals(poEmotion.getContentType()) && getContent().equals(poEmotion.getContent())) {
            switch(getContentType()) {
            case MEMORIZEDEMOTION:
            case BASICEMOTION:
                oEmotionDiff = clsDataStructureGenerator.generateEMOTION(
                    new clsTriple <eContentType, eEmotionType, Object>(
                            getContentType(),
                            getContent(),
                            (getEmotionIntensity() + poEmotion.getEmotionIntensity())/2),
                            (getSourcePleasure() + poEmotion.getSourcePleasure())/2,
                            (getSourceUnpleasure() + poEmotion.getSourceUnpleasure())/2,
                            (getSourceLibid() + poEmotion.getSourceLibid())/2,
                            (getSourceAggr() + poEmotion.getSourceAggr())/2);
            }
        }
        return oEmotionDiff;
    }
	
	public void add(clsEmotion poEmotion) {
	  //kollmann: emotions that are not of the same type (e.g. BASICEMOTION) and content (e.g. ANXIETY) always produce a math of 0
        if(getContentType().equals(poEmotion.getContentType()) && getContent().equals(poEmotion.getContent())) {
            switch(getContentType()) {
            case MEMORIZEDEMOTION:
            case BASICEMOTION:
                mrEmotionIntensity += poEmotion.getEmotionIntensity();
                mrSourcePleasure += poEmotion.getSourcePleasure();
                mrSourceUnpleasure += poEmotion.getSourceUnpleasure();
                mrSourceLibid += poEmotion.getSourceLibid();
                mrSourceAggr += poEmotion.getSourceAggr();
                break;
            }
        } else {
            throw new RuntimeException("Can not add two emotions with different content types");
        }
	}
	
	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @return the moContent
	 */
	public eEmotionType getContent() {
		return moContent;
	}

	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @param moContent the moContent to set
	 */
	public void setContent(eEmotionType moContent) {
		this.moContent = moContent;
	}
	
	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @return the mrEmotionIntensity
	 */
	public double getEmotionIntensity() {
		return mrEmotionIntensity;
	}

	/**
	 * @author schaat
	 * 17.06.2012, 00:50:38
	 * 
	 * @param mrEmotionIntensity the mrEmotionIntensity to set
	 */
	public void setEmotionIntensity(double mrEmotionIntensity) {
		this.mrEmotionIntensity = mrEmotionIntensity;
	}
	
	public double getIntensityDeviation() {
	    return mrIntensityDeviation;
	}
	
	public void setIntensityDeviation(double prIntensityDeviation) {
	    this.mrIntensityDeviation = prIntensityDeviation;
	}
	
	/**
	 * @author schaat
	 * 5.07.2012, 00:50:38
	 * 
	 * @return the mrSourcePleasure
	 */
	public double getSourcePleasure() {
		return mrSourcePleasure;
	}
	
	public void setSourcePleasure(double mrSourcePleasure) {//koller
        this.mrSourcePleasure = mrSourcePleasure;
    }
    
    public double getSourceUnpleasure() {
        return mrSourceUnpleasure;
    }
    
    public void setSourceUnpleasure(double mrSourceUnpleasure) {//koller
        this.mrSourceUnpleasure = mrSourceUnpleasure;
    }
    
    public double getSourceLibid() {
        return mrSourceLibid;
    }
    
    public void setSourceLibid(double mrSourceLibid) {//koller
        this.mrSourceLibid = mrSourceLibid;
    }
    
    public double getSourceAggr() {
        return mrSourceAggr;
    }
    
    public void setSourceAggr(double mrSourceAggr) {//koller
        this.mrSourceAggr = mrSourceAggr;
    }  
	
	public double getRelativeThreshold() {
	    return mrRelativeThreshold;
	}
	
	public void setRelativeThreshold(double prRelativeThreshold) {
	    mrRelativeThreshold = prRelativeThreshold;
	}

	public double getThresholdRange() {
	    return mrThresholdRange;
	}
	
	public void setThresholdRange(double prThresholdRange) {
	    mrThresholdRange = prThresholdRange;
	}
	
	@Override
	public String toString(){
		String oResult = "\n::"+this.moDataStructureType+"::";
		oResult += this.moDS_ID + ":";
		try{
		//oResult += this.moContentType + ":";
		oResult += this.moContent + ":";
		
		if(moExternalAssociatedContent != null)
		{
			for (clsAssociation oEntry : moExternalAssociatedContent) {
				oResult += oEntry.toString() + ":"; 
			}
		}
			
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(2);
		
		oResult += " intensity: " + df.format(mrEmotionIntensity);
		oResult += " U: " + df.format(mrSourceUnpleasure);
		oResult += " A: "+ df.format(mrSourceAggr);
		oResult += " L: "+ df.format(mrSourceLibid);
		oResult += " P: "+ df.format(mrSourcePleasure);
		
		}
		catch(Exception e){
			log.error(e + "\n" + e.getStackTrace().toString());
		}
		
//		if(getContent().equals(eEmotionType.UNDEFINED))
//		{
//    		oResult += "\nEmotions with undefined type are normally used to create extended emotions.\nCurrent extended emotions:";
//    		for(clsEmotion oEmotion : generateExtendedEmotions()) {
//    		    if(!oEmotion.getContent().equals(eEmotionType.UNDEFINED)) {
//    		        oResult += "\n" + oEmotion.toString();
//    		    }
//    		}
//		}
		
		return oResult; 
	}
	
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
    @Override
    public Object clone() throws CloneNotSupportedException {
        return clone(new HashMap<clsDataStructurePA, clsDataStructurePA>());
    }
	
	
	public Object clone(HashMap<clsDataStructurePA, clsDataStructurePA> poClonedNodeMap) throws CloneNotSupportedException {
		clsEmotion oClone = null;
		
		try {
        	oClone = (clsEmotion)super.clone();
        	
        	if(!poClonedNodeMap.containsKey(this)) {
            	poClonedNodeMap.put(this, oClone);
            	
            	if (moExternalAssociatedContent != null) {
            		oClone.moExternalAssociatedContent = new ArrayList<clsAssociation>(); 
            		
            		for(clsAssociation oAssociation : moExternalAssociatedContent){
            			try { 
        					Object dupl = oAssociation.clone(this, oClone, poClonedNodeMap); 
        					if(dupl!= null) oClone.moExternalAssociatedContent.add((clsAssociation)dupl); // unchecked warning
        				} catch (Exception e) {
        					return e;
        				}
            		}
            	}
        	} else {
        	    clsLogger.getLog("Cloning").info("Object already in list");
        	    oClone = (clsEmotion) poClonedNodeMap.get(this);
        	}
        	
        } catch (CloneNotSupportedException e) {
           return e;
        }
		
		return oClone;
	}
	
	/**
	 * DOCUMENT (schaat) - insert description
	 *
	 * @author schaat
	 * 26.06.2012, 16:12:00
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
	
	/**
	 * @author schaat
	 * 26.06.2012, 08:57:27
	 * 
	 * @return the moAssociatedContent
	 */
	@Override
	public ArrayList<clsAssociation> getExternalAssociatedContent() {
		return moExternalAssociatedContent;
	}
	
	/**
	 * Set external associated content
	 * 
	 * (schaat)
	 *
	 * @since 26.06.2012 11:16:52
	 *
	 * @param poExternalAssociatedContent
	 */
	@Override
	public void setExternalAssociatedContent(ArrayList<clsAssociation> poExternalAssociatedContent) {
		this.moExternalAssociatedContent = poExternalAssociatedContent;
	}
	
	/**
	 * Add external associations
	 * 
	 * (schaat)
	 *
	 * @since 26.06.2012 11:21:45
	 *
	 * @param poAssociatedDataStructures
	 */
	@Override
	public void addExternalAssociations(ArrayList<clsAssociation> poAssociatedDataStructures) {
		moExternalAssociatedContent.addAll(poAssociatedDataStructures);  
	}
	
	/* (non-Javadoc)
	 *
	 * @author schaat
	 * 05.07.2012, 17:46:07
	 * 
	 * @see pa._v38.memorymgmt.datatypes.clsDataStructurePA#assignDataStructure(pa._v38.memorymgmt.datatypes.clsDataStructurePA)
	 */
	public void assignDataStructure(clsAssociation poDataStructurePA) {
		ArrayList <clsAssociation> oDataStructureList = new ArrayList<clsAssociation>();
		oDataStructureList.add(poDataStructurePA); 
		
		addExternalAssociations(oDataStructureList);
	}

	/* (non-Javadoc)
	 *
	 * @since Sep 13, 2012 1:28:06 PM
	 * 
	 * @see pa._v38.memorymgmt.datatypes.itfExternalAssociatedDataStructure#addExternalAssociation(pa._v38.memorymgmt.datatypes.clsAssociation)
	 */
	@Override
	public void addExternalAssociation(clsAssociation poAssociatedDataStructure) {
		// TODO (schaat) - Auto-generated method stub
		this.moExternalAssociatedContent.add(poAssociatedDataStructure);
	}
	
	protected clsEmotion generateEmotion(eEmotionType poEmotionType, double prIntensity) {
	    return clsDataStructureGenerator.generateEMOTION(new clsTriple <eContentType, eEmotionType, Object>(eContentType.BASICEMOTION, poEmotionType, prIntensity),  mrSourcePleasure,  mrSourceUnpleasure,  mrSourceLibid,  mrSourceAggr);
	}
	
	public ArrayList<clsEmotion> generateExtendedEmotions() {
	    return generateExtendedEmotions(mrRelativeThreshold);
	}
	
	public ArrayList<clsEmotion> generateExtendedEmotions(double prRelativeThreshold) {
	    ArrayList<clsEmotion> oExtEmotions = new ArrayList<>();
	    
	    // Normalize to be able to decide which basic category prevails/dominates
        double rSumValuesPlUnPl = mrSourceUnpleasure + mrSourcePleasure;
        double rSumValuesLibidAggr =  mrSourceAggr + mrSourceLibid;        
        double rRelativeSystemPleasure = mrSourcePleasure/rSumValuesPlUnPl; 
        double rRelativeSystemUnpleasure = mrSourceUnpleasure/rSumValuesPlUnPl;
        double rRelativeSystemLibid = mrSourceLibid/rSumValuesLibidAggr;
        double rRelativeSystemAggr = mrSourceAggr/rSumValuesLibidAggr;
        
        /*
         * Generate Emotions
         * if unpleasure prevails --> only generate unpleasure-based  emotions (always-> anxiety. if agg prevails -> ANGER. if libid prevails -> mourning. if no one prevails -> both)
         * if pleasure prevails --> only generate pleasure-based emotions (always->> PLEASURE. if agg prevails ->ELATION if libid prevails -> SATURATION)
         * 
         * the intensity of generated emotions is dependent on the relative amount of the basic category (Pleasuer, aggr, ... = from which the emotion is derived), particularly relative to the amount of pleasure+unpleasure
         * E.g. As Grief is based on aggr. unpleasure, its intensity is derived from the amount of aggr. unpleasure relative to the total amount of the ground truth (pleasure+unpleasure) 
         * 
         * Aggr and libid components/categories are just another form of unpleasure. This is considered in the further
         * procedure to avoid duplicating the ground truth(=the values emotions are based on).
         * 
         * variable "rGrade": To avoid "leaps" in emotion-intensity, a threshold-range is considered and a gradual change of intensity (e.g. if the domination of pleasure changes very fast (e.g. in every step), the intensity of the according emotions should not "jump" up and down   
        */
                
        // just generate Unpleasure--based Emotions
        if(rRelativeSystemUnpleasure > prRelativeThreshold){
            oExtEmotions.add(generateEmotion(eEmotionType.ANXIETY, mrSourceUnpleasure));
            oExtEmotions.add(generateEmotion(eEmotionType.JOY, mrSourcePleasure));
            
            if(rRelativeSystemAggr > prRelativeThreshold) {
                oExtEmotions.add(generateEmotion(eEmotionType.ANGER, mrSourceAggr));
            }
            else if (rRelativeSystemLibid > prRelativeThreshold) {
                oExtEmotions.add(generateEmotion(eEmotionType.MOURNING, mrSourceLibid));
            }
            else {
                oExtEmotions.add(generateEmotion(eEmotionType.ANGER, mrSourceAggr));
                oExtEmotions.add(generateEmotion(eEmotionType.MOURNING,  mrSourceLibid));
            }
        }
        // just generate Pleasure-based Emotions
        else if (rRelativeSystemPleasure > prRelativeThreshold) {
            oExtEmotions.add(generateEmotion(eEmotionType.ANXIETY, mrSourceUnpleasure));
            oExtEmotions.add(generateEmotion(eEmotionType.JOY, mrSourcePleasure));
            
            if (rRelativeSystemLibid > prRelativeThreshold) {
                oExtEmotions.add(generateEmotion(eEmotionType.SATURATION,  mrSourceLibid));
            }
            else if (rRelativeSystemAggr > prRelativeThreshold) {
                oExtEmotions.add(generateEmotion(eEmotionType.ELATION, mrSourceAggr));
            }
            else {
                oExtEmotions.add(generateEmotion(eEmotionType.SATURATION,  mrSourceLibid));
                oExtEmotions.add(generateEmotion(eEmotionType.ELATION, mrSourceAggr));
            } 
        }
        // generate both
        else {
            // pleasure-based emotions
            oExtEmotions.add(generateEmotion(eEmotionType.JOY, mrSourcePleasure));
            if (rRelativeSystemLibid > prRelativeThreshold) {
                oExtEmotions.add(generateEmotion(eEmotionType.SATURATION,  mrSourceLibid));
            }
            else if (rRelativeSystemAggr > prRelativeThreshold) {
                oExtEmotions.add(generateEmotion(eEmotionType.ELATION, mrSourceAggr));
            }
            else {
                oExtEmotions.add(generateEmotion(eEmotionType.SATURATION,  mrSourceLibid));
                oExtEmotions.add(generateEmotion(eEmotionType.ELATION, mrSourceAggr));
            }
            
            //unpleasure-based emotions         
            oExtEmotions.add(generateEmotion(eEmotionType.ANXIETY, mrSourceUnpleasure));
            if(rRelativeSystemAggr > prRelativeThreshold) {
                oExtEmotions.add(generateEmotion(eEmotionType.ANGER, mrSourceAggr));
            }
            else if (rRelativeSystemLibid > prRelativeThreshold) {
                oExtEmotions.add(generateEmotion(eEmotionType.MOURNING, mrSourceLibid));
            }
            else {
                oExtEmotions.add(generateEmotion(eEmotionType.ANGER, mrSourceAggr));
                oExtEmotions.add(generateEmotion(eEmotionType.MOURNING,  mrSourceLibid));
            }
        }
        //logFim1.info("ExtEmotion"+oExtEmotions);
	    return oExtEmotions;
	}
	
	
}
