/**
 * CHANGELOG
 *
 * 30.09.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.text.DecimalFormat;
import java.util.ArrayList;

import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePredicate;
import datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 30.09.2013, 11:24:22
 * 
 */
public class clsWordPresentationMeshAimOfDrive extends clsWordPresentationMeshGoal {

    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 30.09.2013 12:40:10
     *
     * @param poDataStructureIdentifier
     * @param poAssociatedStructures
     * @param poContent
     * @param poGoalObject
     * @param poName
     * @param oGoalType
     */
    
    public clsWordPresentationMeshAimOfDrive(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
            ArrayList<clsAssociation> poAssociatedStructures, Object poContent, clsWordPresentationMesh poGoalObject, String poName,
            eGoalType oGoalType,
            double qoutaOfAffectAsImportance,
            clsWordPresentationMesh poAssociatedAction) {
        super(poDataStructureIdentifier, poAssociatedStructures, poContent, poGoalObject, poName, oGoalType);

        setQuotaOfAffectAsImportance(qoutaOfAffectAsImportance);
        setAssociatedDriveAimAction(poAssociatedAction);
        
    }

    /**
     * DOCUMENT - insert description
     *
     * @author wendt
     * @since 30.09.2013 11:29:43
     *
     * @param poDataStructureIdentifier
     * @param poAssociatedStructures
     * @param poContent
     */

    
    
    public void setQuotaOfAffectAsImportance(double qoutaOfAffectAsImportance) {
        this.setUniqueProperty(String.valueOf(qoutaOfAffectAsImportance), eContentType.QUOTAOFAFFECTASIMPORTANCE, ePredicate.HASQUOTAOFAFFECTASIMPORTANCE, true);
    }
    
    public double getQuotaOfAffectAsImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASQUOTAOFAFFECTASIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
        
    
        return oRetVal;
    }
    
    /* (non-Javadoc)
    *
    * @since 30.09.2013 12:14:12
    * 
    * @see pa._v38.memorymgmt.datatypes.clsWordPresentationMeshGoal#getTotalImportance()
    */
   @Override
   public double getTotalImportance() {
       return this.getQuotaOfAffectAsImportance();
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
    public clsWordPresentationMesh getAssociatedDriveAimAction() {
        return this.getUniquePropertyWPM(ePredicate.HASASSOCIATEDDRIVEAIMACTION);
    } 
    
    /**
     * Set associated aim action
     * 
     * (wendt)
     *
     * @since 26.09.2012 12:20:16
     *
     * @param poGoal
     * @param poAssociatedAction
     */
    public void setAssociatedDriveAimAction(clsWordPresentationMesh poAssociatedAction) {
        this.setUniqueProperty(poAssociatedAction, ePredicate.HASASSOCIATEDDRIVEAIMACTION, true);
    }
    
    @Override
    public String toString() {
        String oResult = "";  
        oResult += this.getGoalContentIdentifier();
        
        oResult += ":" + new DecimalFormat("0.00").format(this.getTotalImportance());
        oResult += ":" + this.getGoalObject();
        oResult += ":" + getSupportiveDataStructure().getMoContent();
            
        ArrayList<eCondition> oConditionList = getCondition();
        if (oConditionList.isEmpty()==false) {
           oResult += " " + oConditionList.toString();
        }
            
        return oResult;
    }

}
