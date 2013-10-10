/**
 * CHANGELOG
 *
 * 30.09.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.text.DecimalFormat;
import java.util.ArrayList;

import datatypes.helpstructures.clsTriple;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePredicate;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 30.09.2013, 11:26:37
 * 
 */
public class clsWordPresentationMeshSelectableGoal extends clsWordPresentationMeshGoal {
    private final static clsWordPresentationMeshSelectableGoal moNullObject = new clsWordPresentationMeshSelectableGoal(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.NULLOBJECT), new ArrayList<clsAssociation>(), eContentType.NULLOBJECT.toString(), null, "", eGoalType.NULLOBJECT, 0);


    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 30.09.2013 12:41:30
     *
     * @param poDataStructureIdentifier
     * @param poAssociatedStructures
     * @param poContent
     * @param poGoalObject
     * @param poName
     * @param oGoalType
     */
    public clsWordPresentationMeshSelectableGoal(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
            ArrayList<clsAssociation> poAssociatedStructures, Object poContent, clsWordPresentationMesh poGoalObject, String poName,
            eGoalType oGoalType, double driveDemandImportance) {
        super(poDataStructureIdentifier, poAssociatedStructures, poContent, poGoalObject, poName, oGoalType);

        setPotentialDriveFulfillmentImportance(driveDemandImportance);
    }
    
    /**
     * @since 05.07.2012 22:04:13
     * 
     * @return the moNullObjectWPM
     */
    public static clsWordPresentationMeshSelectableGoal getNullObject() {
        return moNullObject;
    }

    /**
     * Get the Feelings from a goal
     * 
     * (wendt)
     *
     * @since 26.03.2012 21:25:11
     *
     * @param poGoal
     * @return
     */
    public ArrayList<clsWordPresentationMeshFeeling> getFeelings() {
        ArrayList<clsWordPresentationMesh> oRetVal = this.getNonUniquePropertyWPM(ePredicate.HASFEELING);;
    
        ArrayList<clsWordPresentationMeshFeeling> result = new ArrayList<clsWordPresentationMeshFeeling>();
        
        for (clsWordPresentationMesh wpm : oRetVal) {
            if (wpm instanceof clsWordPresentationMeshFeeling) {
                result.add((clsWordPresentationMeshFeeling) wpm);
            } else {
                throw new ClassCastException("This structure is no valid class for this association " + wpm);
            }
        }
    
        return result;
    }
    
    /**
     * Add a Feeling to the goal, which is not already present. If present, then replace
     * 
     * (wendt)
     *
     * @since 17.05.2013 10:37:48
     *
     * @param poFeeling
     */
    public void addFeeling(clsWordPresentationMeshFeeling poFeeling) {
        this.addReplaceNonUniqueProperty(poFeeling, ePredicate.HASFEELING, true);
    }
    
    /**
     * Add a list of feelings
     * 
     * (wendt)
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
    
    //==== Importance ====//
    
    /**
     * Get effort level
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:38:03
     *
     * @return
     */
    public double getEffortImpactImportance() {
        double rResult = 0;
        
        String oEffort = this.getUniqueProperty(ePredicate.HASEFFORTIMPACTIMPORTANCE);
        
        if (oEffort.isEmpty()==false) {
            rResult = Double.valueOf(oEffort);
        }
        
        return rResult;
    }
    
    /**
     * Set effort level
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:38:59
     *
     * @param pnImportance
     */
    public void setEffortImpactImportance(double pnImportance) {
        this.setUniqueProperty(String.valueOf(pnImportance), eContentType.EFFORTIMPACTIMPORTANCE, ePredicate.HASEFFORTIMPACTIMPORTANCE, true);
    }
    
    /**
     * Add effort level
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:38:59
     *
     * @param pnImportance
     */
    public void addEffortImpactImportance(double pnImportance) {
        this.setUniqueProperty(String.valueOf(this.getEffortImpactImportance() + pnImportance), eContentType.EFFORTIMPACTIMPORTANCE, ePredicate.HASEFFORTIMPACTIMPORTANCE, true);
    }
    
    
    
    public double getDriveDemandImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASDRIVEDEMANDIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
        
    
        return oRetVal;
    }
    
    public void setDriveDemandImportance(double driveDemandImportance) {
        this.setUniqueProperty(String.valueOf(driveDemandImportance), eContentType.DRIVEDEMANDIMPORTANCE, ePredicate.HASDRIVEDEMANDIMPORTANCE, true);
    }
    
    public void setFeelingsImportance(double feelingsImportance) {
        this.setUniqueProperty(String.valueOf(feelingsImportance), eContentType.FEELINGSIMPORTANCE, ePredicate.HASFEELINGSIMPORTANCE, true);
    }
    
    public double getFeelingsImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASFEELINGSIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
        
    
        return oRetVal;
    }
    
    public void setSocialRulesImportance(double socialRulesImportance) {
        this.setUniqueProperty(String.valueOf(socialRulesImportance), eContentType.SOCIALRULESIMPORTANCE, ePredicate.HASSOCIALRULESIMPORTANCE, true);
    }
    
    public double getSocialRulesImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASSOCIALRULESIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
        
    
        return oRetVal;
    }
    
    public void setDriveDemandCorrectionImportance(double driveDemandCorrectionImportance) {
        this.setUniqueProperty(String.valueOf(driveDemandCorrectionImportance), eContentType.DRIVEDEMANDCORRECTIONIMPORTANCE, ePredicate.HASDRIVEDEMANDCORRECTIONIMPORTANCE, true);
    }
    
    public double getDriveDemandCorrectionImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASDRIVEDEMANDCORRECTIONIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
        
    
        return oRetVal;
    }
    
    /**
     * Get the affectlevel from a goal
     * 
     * (wendt)
     *
     * @since 26.03.2012 21:25:11
     *
     * @param poGoal
     * @return
     */
    @Override
    public double getTotalImportance() {
        double totalImportance = this.getDriveDemandImportance() + this.getDriveDemandCorrectionImportance() + getEffortImpactImportance() + getFeelingsImportance() + this.getSocialRulesImportance();
        return totalImportance;
        
        //double oRetVal = 0;
        
//        String oAffectLevel = this.getUniqueProperty(ePredicate.HASTOTALIMPORTANCE);
//        if (oAffectLevel.isEmpty()==false) {
//            oRetVal = Double.valueOf(oAffectLevel);
//        }
//        
//    
//        return oRetVal;
    }
    
    public void setPotentialDriveFulfillmentImportance(double importance) {
        this.setUniqueProperty(String.valueOf(importance), eContentType.POTENTIALDRIVEFULFILLMENTIMPORTANCE, ePredicate.HASPOTENTIALDRIVEFULFILLMENTIMPORTANCE, true);
    }
    
    public double getPotentialDriveFulfillmentImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASPOTENTIALDRIVEFULFILLMENTIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
        
    
        return oRetVal;
    }
    
    /**
     * In the action codelets, actions are associated with the goals. In that way a new action can be attached to a goal and extracted
     * 
     * (wendt)
     *
     * @since 26.09.2012 12:17:57
     *
     * @param poGoal
     * @return
     */
    public clsWordPresentationMesh getAssociatedPlanAction() {
        return this.getUniquePropertyWPM(ePredicate.HASASSOCIATEDPLANACTION);
    } 
    
    /**
     * Set associated action
     * 
     * (wendt)
     *
     * @since 26.09.2012 12:20:16
     *
     * @param poGoal
     * @param poAssociatedAction
     */
    public void setAssociatedPlanAction(clsWordPresentationMesh poAssociatedAction) {
        this.setUniqueProperty(poAssociatedAction, ePredicate.HASASSOCIATEDPLANACTION, true);
    }
    
    /**
     * Remove the associated action
     * 
     * (wendt)
     *
     * @since 17.05.2013 14:54:21
     *
     */
    public void removeAllAssociatedAction() {
        this.removeAllProperties(ePredicate.HASASSOCIATEDPLANACTION);
    }
    
    /**
     * Remove all importances
     *
     * @author wendt
     * @since 04.10.2013 10:44:08
     *
     */
    public void removeAllImportance() {
        this.removeAllProperties(ePredicate.HASFEELINGSIMPORTANCE);
        this.removeAllProperties(ePredicate.HASDRIVEDEMANDIMPORTANCE);
        this.removeAllProperties(ePredicate.HASEFFORTIMPACTIMPORTANCE);
        this.removeAllProperties(ePredicate.HASSOCIALRULESIMPORTANCE);
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
    public boolean isEquivalentDataStructure(clsWordPresentationMeshSelectableGoal ds) {
        boolean isEqual = false;
        
        if (ds.getClass().getName().equals(this.getClass().getName()) &&
            ds.getMoDS_ID()==this.moDS_ID &&
            ds.getMoContent()==this.getMoContent() &&
            ds.getMoContentType()==this.getMoContentType() && 
            ds.getSupportiveDataStructure().getMoContent().equals(this.getSupportiveDataStructure().getMoContent())) {
            isEqual=true;
        }
        
        return isEqual;
    }
    
    @Override
    public String toString() {
        String oResult = "";  
        oResult += this.getGoalContentIdentifier();
        //TODO SM: Goal, Tostring adaption to application of feelings together with the getter and setter
        //double rImportanceOfFeelings = clsImportanceTools.getConsequencesOfFeelingsOnGoalAsImportance(this, this.getFeelings());
        //double nTotalAffectLevel = getTotalImportance() + getEffortImpact() + rImportanceOfFeelings;
        
        oResult += ":" + new DecimalFormat("0.00").format(this.getTotalImportance());
        oResult += ":" + this.getFeelings().toString();
        oResult += ":" + this.getGoalObject();
        oResult += ":" + getSupportiveDataStructure().getMoContent();
            
        ArrayList<eCondition> oConditionList = getCondition();
        if (oConditionList.isEmpty()==false) {
           oResult += " " + oConditionList.toString();
        }
            
        return oResult;
    }
    
}
