/**
 * CHANGELOG
 *
 * 17.05.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import datatypes.helpstructures.clsTriple;
import pa._v38.memorymgmt.enums.eCondition;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.eGoalType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.tools.ElementNotFoundException;
import secondaryprocess.datamanipulation.clsMeshTools;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 17.05.2013, 00:28:19
 * 
 */
public abstract class clsWordPresentationMeshGoal extends clsWordPresentationMesh {

    /**
     * DOCUMENT (wendt) - insert description 
     *
     * @since 17.05.2013 00:28:41
     *
     * @param poDataStructureIdentifier
     * @param poAssociatedStructures
     * @param poContent
     */
    public clsWordPresentationMeshGoal(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
            ArrayList<clsAssociation> poAssociatedStructures, Object poContent, 
            clsWordPresentationMesh poGoalObject,
            String poName,
            eGoalType oGoalType
            ) {
        super(poDataStructureIdentifier, poAssociatedStructures, poContent);
        
        setGoalObject(poGoalObject);
        setGoalName(poName);
        setGoalSource(oGoalType);
        
        
    }
    
    // === GETTERS AND SETTERS === //
    
    /**
     * Get the drive object from a goal mesh
     * 
     * (wendt)
     *
     * @since 26.03.2012 21:22:03
     *
     * @param poGoal
     * @return
     */
    public clsWordPresentationMesh getGoalObject() {
        return this.getUniquePropertyWPM(ePredicate.HASGOALOBJECT);
    }
    
    /**
     * Set goal object
     * 
     * (wendt)
     *
     * @since 17.05.2013 10:37:16
     *
     * @param poGoalObject
     */
    public void setGoalObject(clsWordPresentationMesh poGoalObject) {
        this.setUniqueProperty(poGoalObject, ePredicate.HASGOALOBJECT, true);
    }
    
    
    /**
     * Get goal identifier
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:39:14
     *
     * @return
     */
    public String getGoalContentIdentifier() {
        return this.getMoContent();
    }
    
    
    /**
     * Set task status or replace if it already exists
     * 
     * (wendt)
     *
     * @since 17.07.2012 22:00:32
     *
     * @param poGoal
     * @param poTask
     */
    public void setCondition(eCondition poTask) {
        this.addReplaceNonUniqueProperty(poTask.toString(), eContentType.CONDITION, ePredicate.HASCONDITION, true);        
    }
    
    
    /**
     * Get the current decision task
     * 
     * (wendt)
     *
     * @since 16.07.2012 16:42:03
     *
     * @param poGoal
     * @return
     */
    public ArrayList<eCondition> getCondition() {
        ArrayList<eCondition> oResult = new ArrayList<eCondition>();
        
        ArrayList<String> oFoundTaskStatusList = this.getNonUniqueProperty(ePredicate.HASCONDITION);
        
        for (String oTaskStatus : oFoundTaskStatusList) {
            oResult.add(eCondition.valueOf(oTaskStatus));
        }
        
        return oResult;
    }
    
    /**
     * Check if a certain taskstatus exists
     * 
     * (wendt)
     *
     * @since 23.07.2012 20:27:12
     *
     * @param poGoal
     * @param poTask
     * @return
     */
    public boolean checkIfConditionExists(eCondition poTask) {
        boolean bResult = false;
        
        ArrayList<eCondition> oResult = getCondition();
        if (oResult.contains(poTask)) {
            bResult=true;
        }
        
        return bResult;
    }
    
    /**
     * Remove a certain task status if it exists in the data structure
     * 
     * (wendt)
     *
     * @since 19.07.2012 11:25:29
     *
     * @param poGoal
     * @param poTask
     * @throws Exception 
     */
    public void removeCondition(eCondition poTask) throws ElementNotFoundException {
        this.removeProperty(poTask.toString(), ePredicate.HASCONDITION);
    }
    
    /**
     * Removes all task status in the goal
     * 
     * (wendt)
     *
     * @since 23.07.2012 17:24:37
     *
     * @param poGoal
     */
    public void removeAllConditions() {
        this.removeAllProperties(ePredicate.HASCONDITION);
    }
    
    
    /**
     * Set goal name
     * 
     * (wendt)
     *
     * @since 23.07.2012 16:04:54
     *
     * @param poGoal
     * @param poName
     */
    public void setGoalName(String poName) {
        this.setUniqueProperty(poName, eContentType.GOALNAME, ePredicate.HASGOALNAME, true);
    }
    
    /**
     * Get the goal content
     * 
     * (wendt)
     *
     * @since 26.03.2012 21:29:45
     *
     * @param poGoal
     * @return
     */
    public String getGoalName() {
        return this.getUniqueProperty(ePredicate.HASGOALNAME);
    }
    
    /**
     * Set goal name
     * 
     * (wendt)
     *
     * @since 23.07.2012 16:04:54
     *
     * @param poGoal
     * @param poName
     */
    public void setGoalTypeName(String poName) {
        this.setUniqueProperty(poName, eContentType.GOALTYPENAME, ePredicate.HASGOALTYPENAME, true);
    }
    
    /**
     * Get the goal content
     * 
     * (wendt)
     *
     * @since 26.03.2012 21:29:45
     *
     * @param poGoal
     * @return
     */
    public String getGoalTypeName() {
        return this.getUniqueProperty(ePredicate.HASGOALTYPENAME);
    } 
    
    /**
     * Get the supportive data structures from a goal mesh
     * 
     * (wendt)
     *
     * @since 26.03.2012 21:22:03
     *
     * @param poGoal
     * @return
     */
    public clsWordPresentationMesh getSupportiveDataStructure() {
        return this.getUniquePropertyWPM(ePredicate.HASSUPPORTIVEDATASTRUCTURE);
    }
    
    /**
     * Set the supportive data structure
     * 
     * (wendt)
     *
     * @since 16.07.2012 22:17:34
     *
     * @param poGoal
     * @param poDataStructure
     */
    public void setSupportiveDataStructure(clsWordPresentationMesh poDataStructure) {
        this.setUniqueProperty(poDataStructure, ePredicate.HASSUPPORTIVEDATASTRUCTURE, true);
    }
    
    /**
     * If there is no supportive datastructure, a data structure can be created from a single entity
     * 
     * (wendt)
     *
     * @since 10.07.2012 11:02:49
     *
     * @param poGoal: Goal
     * @param poEntity: Entity, which shall be added to an image
     */
    public void createSupportiveDataStructureFromEntity(clsWordPresentationMesh poEntity, eContentType poContentType) {
        //Create Image from entity
        clsWordPresentationMesh oImageFromEntity = clsMeshTools.createImageFromEntity(poEntity, poContentType);
        
        setSupportiveDataStructure(oImageFromEntity);
    }
    
    /**
     * Create the supportive datastructure from the drive object
     * 
     * (wendt)
     *
     * @since 16.07.2012 13:27:09
     *
     * @param poGoal
     * @param poContentType
     * @throws Exception 
     */
    public void createSupportiveDataStructureFromGoalObject(eContentType poContentType) throws Exception {
        try {
            clsWordPresentationMesh oGoalObject = getGoalObject();
            
            createSupportiveDataStructureFromEntity(oGoalObject, poContentType);
            
        } catch (NullPointerException e) {
            throw new Exception("Error: The goal does not have a valid goal object");
        }
    }
    
    /**
     * Get the goal type of a certain goal
     * 
     * (wendt)
     *
     * @since 24.06.2012 09:21:09
     *
     * @param poGoal
     * @return
     */
    public eGoalType getGoalSource() {
        eGoalType oRetVal = eGoalType.NULLOBJECT;
        
        String oGoalType = this.getUniqueProperty(ePredicate.HASGOALSOURCE);
        if (oGoalType.equals("")==false) {
            oRetVal = eGoalType.valueOf(oGoalType);
        }
        return oRetVal;
    }
    
    /**
     * Set goal type
     * 
     * (wendt)
     *
     * @since 17.05.2013 14:50:15
     *
     * @param oGoalType
     */
    public void setGoalSource(eGoalType oGoalType) {
        this.setUniqueProperty(oGoalType.toString(), eContentType.GOALSOURCE, ePredicate.HASGOALSOURCE, true);        
    }
    
    /**
     * Get the content type of the support data structure type of the goal
     * 
     * (wendt)
     *
     * @since 19.06.2012 22:07:50
     *
     * @param poGoal
     * @return
     */
    public eContentType getSupportiveDataStructureType() {
        eContentType oRetVal = null;
    
        if (getSupportiveDataStructure()!=null) {
            oRetVal = this.getSupportiveDataStructure().getMoContentType();
        }
        
        
        return oRetVal;
    }
    
    public abstract double getTotalImportance();
    

//    @Override
//    public String toString() {
//        String oResult = "";  
//        oResult += this.getGoalContentIdentifier();
//        //TODO SM: Goal, Tostring adaption to application of feelings together with the getter and setter
//        double rImportanceOfFeelings = clsImportanceTools.getConsequencesOfFeelingsOnGoalAsImportance(this, this.getFeelings());
//        double nTotalAffectLevel = getTotalImportance() + getEffortImpact() + rImportanceOfFeelings;
//        
//        oResult += ":" + new DecimalFormat("0.00").format(nTotalAffectLevel);
//        oResult += ":" + this.getFeelings().toString();
//        oResult += ":" + this.getGoalObject();
//        oResult += ":" + getSupportiveDataStructure().getMoContent();
//            
//        ArrayList<eCondition> oConditionList = getCondition();
//        if (oConditionList.isEmpty()==false) {
//           oResult += " " + oConditionList.toString();
//        }
//            
//        return oResult;
//    }
    
}
