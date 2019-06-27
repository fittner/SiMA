/**
 * CHANGELOG
 *
 * 30.09.2013 wendt - File created
 *
 */
package base.datatypes;

import java.text.DecimalFormat;
import java.util.ArrayList;

import memorymgmt.enums.eCondition;
import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.eGoalType;
import memorymgmt.enums.ePredicate;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 30.09.2013, 11:26:37
 * 
 */
public class clsWordPresentationMeshPossibleGoal extends clsWordPresentationMeshGoal {
    private final static clsWordPresentationMeshPossibleGoal moNullObject = new clsWordPresentationMeshPossibleGoal(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.NULLOBJECT), new ArrayList<clsAssociation>(), eContentType.NULLOBJECT.toString(), null, "", eGoalType.NULLOBJECT, 0);


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
    public clsWordPresentationMeshPossibleGoal(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
            ArrayList<clsAssociation> poAssociatedStructures, Object poContent, clsWordPresentationMesh poGoalObject, String poName,
            eGoalType oGoalType, double driveDemandImportance) {
        super(poDataStructureIdentifier, poAssociatedStructures, poContent, poGoalObject, poName, oGoalType);

        setPotentialDriveFulfillmentImportance(driveDemandImportance);
    }
    
    /**
     * DOCUMENT - Alternate constructor for goals that have no potential drive fullfillment importance (could get their importance from other sources, like feelings)
     *
     * @author Kollmann
     * @since 10.09.2014 15:00:36
     *
     * @param poDataStructureIdentifier
     * @param poAssociatedStructures
     * @param poContent
     * @param poGoalObject
     * @param poName
     * @param oGoalType
     */
    public clsWordPresentationMeshPossibleGoal(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
            ArrayList<clsAssociation> poAssociatedStructures, Object poContent, clsWordPresentationMesh poGoalObject, String poName,
            eGoalType oGoalType) {
        super(poDataStructureIdentifier, poAssociatedStructures, poContent, poGoalObject, poName, oGoalType);
    }
    
    /**
     * @since 05.07.2012 22:04:13
     * 
     * @return the moNullObjectWPM
     */
    public static clsWordPresentationMeshPossibleGoal getNullObject() {
        return moNullObject;
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
        if(!Double.isNaN(pnImportance)) {
            this.setUniqueProperty(String.valueOf(pnImportance), eContentType.EFFORTIMPACTIMPORTANCE, ePredicate.HASEFFORTIMPACTIMPORTANCE, true);
        } else {
            log.error("Method setEffortImpactImportance tried to set NaN as dable value");
        }
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
        if(!Double.isNaN(pnImportance)) {
            this.setUniqueProperty(String.valueOf(this.getEffortImpactImportance() + pnImportance), eContentType.EFFORTIMPACTIMPORTANCE, ePredicate.HASEFFORTIMPACTIMPORTANCE, true);
        } else {
            log.error("Method addEffortImpactImportance tried to set NaN as dable value");
        }
    }
    
    public double getDriveDemandImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASDRIVEDEMANDIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
        
    
        return oRetVal;
    }
    
    private double nonProportionalAggregation (double rBaseValue, double rAddValue) {
        rBaseValue = rBaseValue + (1 - rBaseValue) * rAddValue;
    
        return rBaseValue;
    }
    
    public double getPPImportance() {
        double rImportance = getDriveDemandImportance();
        rImportance = nonProportionalAggregation(rImportance, getFeelingsMatchImportance());
        
        if(this.hasFeelingsExcpactationImportance()) {
            rImportance = nonProportionalAggregation(rImportance, getFeelingsExcpactationImportance());
        }
        
        return rImportance;
    }
    
    public void setDriveDemandImportance(double driveDemandImportance) {
        if(!Double.isNaN(driveDemandImportance)) {
            this.setUniqueProperty(String.valueOf(driveDemandImportance), eContentType.DRIVEDEMANDIMPORTANCE, ePredicate.HASDRIVEDEMANDIMPORTANCE, true);
        } else {
            log.error("Method setDriveDemandImportance tried to set NaN as dable value");
        }
    }
    
    public void setFeelingsMatchImportance(double feelingsImportance) {
        if(!Double.isNaN(feelingsImportance)) {
            this.setUniqueProperty(String.valueOf(feelingsImportance), eContentType.FEELINGSIMPORTANCE, ePredicate.HASFEELINGSMATCHIMPORTANCE, true);
        } else {
            log.error("Method setFeelingsExpactationImportance tried to set NaN as dable value");
        }
    }
    
    public void setFeelingsExpactationImportance(double feelingsImportance) {
        if(!Double.isNaN(feelingsImportance)) {
            this.setUniqueProperty(String.valueOf(feelingsImportance), eContentType.FEELINGSIMPORTANCE, ePredicate.HASEXPECTEDFEELINGSIMPORTANCE, true);
        } else {
            log.error("Method setFeelingsExpactationImportance tried to set NaN as dable value");
        }
    }
    
    public double getFeelingsMatchImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASFEELINGSMATCHIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
        
    
        return oRetVal;
    }
    
    public boolean hasFeelingsExcpactationImportance() {
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASEXPECTEDFEELINGSIMPORTANCE);
        return !oAffectLevel.isEmpty();
    }
    
    public double getFeelingsExcpactationImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASEXPECTEDFEELINGSIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
    
        return oRetVal;
    }
    
    public void setSocialRulesImportance(double socialRulesImportance) {
        if(!Double.isNaN(socialRulesImportance)) {
            this.setUniqueProperty(String.valueOf(socialRulesImportance), eContentType.SOCIALRULESIMPORTANCE, ePredicate.HASSOCIALRULESIMPORTANCE, true);
        } else {
            log.error("Method setSocialRulesImportance tried to set NaN as dable value");
        }
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
        if(!Double.isNaN(driveDemandCorrectionImportance)) {
            this.setUniqueProperty(String.valueOf(driveDemandCorrectionImportance), eContentType.DRIVEDEMANDCORRECTIONIMPORTANCE, ePredicate.HASDRIVEDEMANDCORRECTIONIMPORTANCE, true);
        } else {
            log.error("Method setDriveDemandCorrectionImportance tried to set NaN as dable value");
        }
    }
    
    public void setDriveAimImportance(double prDriveAimImportance) {
        if(!Double.isNaN(prDriveAimImportance)) {
            this.setUniqueProperty(String.valueOf(prDriveAimImportance), eContentType.DRIVEAIMIMPORTANCE, ePredicate.HASDRIVEAIMIMPORTANCE, true);
        } else {
            log.error("Method setDriveAimImportance tried to set NaN as dable value");
        }
    }
    
    public double getDriveDemandCorrectionImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASDRIVEDEMANDCORRECTIONIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
        
    
        return oRetVal;
    }
    
    public double getDriveAimImportance() {
        double oRetVal = 0;
        
        String oAimImportance = this.getUniqueProperty(ePredicate.HASDRIVEAIMIMPORTANCE);
        if (oAimImportance.isEmpty()==false) {
            oRetVal = Double.valueOf(oAimImportance);
        }
        
    
        return oRetVal;
    }
    
    public void setEntityValuationImportance(double prImportance) {
        if(!Double.isNaN(prImportance)) {
            this.setUniqueProperty(String.valueOf(prImportance), eContentType.ENTITYVALUATIONMATCHIMPORTANCE, ePredicate.HASENTITYVALUATIONMATCHIMPORTANCE, true);
        } else {
            log.error("Method setEntityValuationImportance tried to set NaN as dable value");
        }
    }
    
    public double getEntityValuationImportance() {
        double oRetVal = 0;
        
        String oImportance = this.getUniqueProperty(ePredicate.HASENTITYVALUATIONMATCHIMPORTANCE);
        if (oImportance.isEmpty()==false) {
            oRetVal = Double.valueOf(oImportance);
        }
        
    
        return oRetVal;
    }
    
    public void setEntityBodystateImportance(double prImportance) {
        if(!Double.isNaN(prImportance)) {
            this.setUniqueProperty(String.valueOf(prImportance), eContentType.ENTITYBODYSTATEMATCHIMPORTANCE, ePredicate.HASENTITYBODYSTATEMATCHIMPORTANCE, true);
        } else {
            log.error("Method setEntityBodystateImportance tried to set NaN as dable value");
        }
    }
    
    public double getEntityBodystateImportance() {
        double oRetVal = 0;
        
        String oImportance = this.getUniqueProperty(ePredicate.HASENTITYBODYSTATEMATCHIMPORTANCE);
        if (oImportance.isEmpty()==false) {
            oRetVal = Double.valueOf(oImportance);
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
        int nFactors = 0;
        double rTempImportance = 0;
        double DDCorr,Effort,SocialR,DriveA,PP,EntityV,EntityB;
        DDCorr  = this.getDriveDemandCorrectionImportance();
        Effort  = this.getEffortImpactImportance();
        SocialR = this.getSocialRulesImportance();
        DriveA  = this.getDriveAimImportance();
        PP      = this.getPPImportance();
        EntityV= this.getEntityValuationImportance();
        EntityB = this.getEntityBodystateImportance();
        
        
        double totalImportance = DDCorr+Effort+SocialR+DriveA+PP+EntityV+EntityB;
 
        
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
        if(!Double.isNaN(importance)) {
            this.setUniqueProperty(String.valueOf(importance), eContentType.POTENTIALDRIVEFULFILLMENTIMPORTANCE, ePredicate.HASPOTENTIALDRIVEFULFILLMENTIMPORTANCE, true);
        } else {
            log.error("Method setPotentialDriveFulfillmentImportance tried to set NaN as dable value");
        }
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
    
    public void setPotentialDriveAim(clsWordPresentationMesh poPotentialDriveAim) {
        this.setUniqueProperty(poPotentialDriveAim, ePredicate.HASPOTENTIALDRIVEAIM, true);
    }
    
    public clsWordPresentationMesh getPotentialDriveAim() {
        return this.getUniquePropertyWPM(ePredicate.HASPOTENTIALDRIVEAIM);
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
        this.removeAllProperties(ePredicate.HASFEELINGSMATCHIMPORTANCE);
        this.removeAllProperties(ePredicate.HASDRIVEDEMANDIMPORTANCE);
        this.removeAllProperties(ePredicate.HASEFFORTIMPACTIMPORTANCE);
        this.removeAllProperties(ePredicate.HASSOCIALRULESIMPORTANCE);
        this.removeAllProperties(ePredicate.HASDRIVEAIMIMPORTANCE);
        this.removeAllProperties(ePredicate.HASENTITYVALUATIONMATCHIMPORTANCE);
        this.removeAllProperties(ePredicate.HASENTITYBODYSTATEMATCHIMPORTANCE);
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
    public boolean isEquivalentDataStructure(clsWordPresentationMeshPossibleGoal ds) {
        boolean isEqual = false;
        
        if (ds.getClass().getName().equals(this.getClass().getName()) &&
            ds.getDS_ID()==this.moDS_ID &&
            ds.getContent().equals(this.getContent()) &&
            ds.getContentType().equals(this.getContentType()) && 
            ds.getSupportiveDataStructure().getContent().equals(this.getSupportiveDataStructure().getContent())) {
            isEqual=true;
        }
        
        return isEqual;
    }
    
    @Override
    public String toString() {
        String oResult = "";
        oResult += getSupportiveDataStructure().getContent() + ":";
        ArrayList<eCondition> oConditionList = getCondition();
        if (oConditionList.isEmpty()==false) {
           oResult += " " + oConditionList.toString();
        }
        
        oResult += this.getGoalContentIdentifier();
        //TODO SM: Goal, Tostring adaption to application of feelings together with the getter and setter
        //double rImportanceOfFeelings = clsImportanceTools.getConsequencesOfFeelingsOnGoalAsImportance(this, this.getFeelings());
        //double nTotalAffectLevel = getTotalImportance() + getEffortImpact() + rImportanceOfFeelings;
        
        oResult += ":" + new DecimalFormat("0.00").format(this.getTotalImportance());
        if (this.getTotalImportance()==0.0) {
            oResult += "(Pot.) " + this.getPotentialDriveFulfillmentImportance();
        }
        oResult += ":" + this.getFeelings().toString();
        oResult += ":" + this.getGoalObject().getContent();
        
        if (this.getAssociatedPlanAction().isNullObject()==false) {
            oResult += ":" + this.getAssociatedPlanAction().getContent();
        }
            
        oResult += "\n";
            
        return oResult;
    }
    
}
