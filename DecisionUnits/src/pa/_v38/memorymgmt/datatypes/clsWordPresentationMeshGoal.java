/**
 * CHANGELOG
 *
 * 17.05.2013 wendt - File created
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
import pa._v38.tools.ElementNotFoundException;
import pa._v38.tools.clsImportanceTools;
import pa._v38.tools.clsMeshTools;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 17.05.2013, 00:28:19
 * 
 */
public class clsWordPresentationMeshGoal extends clsWordPresentationMesh {

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
            ArrayList<clsAssociation> poAssociatedStructures, Object poContent) {
        super(poDataStructureIdentifier, poAssociatedStructures, poContent);
        // TODO (wendt) - Auto-generated constructor stub
        
        //Init hashmap
        //this.moAssociationMapping.put(eContentType.GOALOBJECT, new ArrayList<clsSecondaryDataStructure>());
        //this.moAssociationMapping.put(eContentType.FEELING, new ArrayList<clsSecondaryDataStructure>());
        //this.moAssociationMapping.put(eContentType.GOALTYPE, new ArrayList<clsSecondaryDataStructure>());
        //this.moAssociationMapping.put(eContentType.IMPORTANCE, new ArrayList<clsSecondaryDataStructure>());
        //this.moAssociationMapping.put(eContentType.EFFORTLEVEL, new ArrayList<clsSecondaryDataStructure>());
        //this.moAssociationMapping.put(eContentType.CONDITION, new ArrayList<clsSecondaryDataStructure>());
        //this.moAssociationMapping.put(eContentType.GOALNAME, new ArrayList<clsSecondaryDataStructure>());
        //this.moAssociationMapping.put(eContentType.SUPPORTIVEDATASTRUCTURE, new ArrayList<clsSecondaryDataStructure>());
        //this.moAssociationMapping.put(eContentType.GOALTYPE, new ArrayList<clsSecondaryDataStructure>());
        //this.moAssociationMapping.put(eContentType.ACTION, new ArrayList<clsSecondaryDataStructure>());
        
        
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
        ArrayList<clsWordPresentationMeshFeeling> oRetVal = new ArrayList<clsWordPresentationMeshFeeling>();
    
        oRetVal = this.getNonUniquePropertyWPM(ePredicate.HASFEELING);
        
//        for (clsSecondaryDataStructure oF : oFeelings) {
//            oRetVal.add((clsWordPresentationMeshFeeling) oF);
//        }
    
        return oRetVal;
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
    public double getImportance() {
        double oRetVal = 0;
        
        String oAffectLevel = this.getUniqueProperty(ePredicate.HASIMPORTANCE);
        if (oAffectLevel.isEmpty()==false) {
            oRetVal = Double.valueOf(oAffectLevel);
        }
        
    
        return oRetVal;
    }
    
    
    /**
     * Set affectlevel
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:36:43
     *
     * @param pnImportance
     */
    public void setImportance(double prImportance) {
        this.setUniqueProperty(String.valueOf(prImportance), eContentType.IMPORTANCE, ePredicate.HASIMPORTANCE, true);
    }
    
    /**
     * Get effort level
     * 
     * (wendt)
     *
     * @since 17.05.2013 11:38:03
     *
     * @return
     */
    public double getEffortLevel() {
        double rResult = 0;
        
        String oEffort = this.getUniqueProperty(ePredicate.HASEFFORTLEVEL);
        
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
    public void setEffortLevel(double pnImportance) {
        this.setUniqueProperty(String.valueOf(pnImportance), eContentType.EFFORTLEVEL, ePredicate.HASEFFORTLEVEL, true);
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
//        
//        ArrayList<clsWordPresentation> oFoundStructureList = getConditionDataStructure();
//        
//        for (clsWordPresentation oTaskStatus : oFoundStructureList) {
//            if (oTaskStatus.getMoContent().equals(poTask.toString())) {
//                clsMeshTools.removeAssociationInObject(this, oTaskStatus);
//            }
//        }
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
    
//    /**
//     * Get the goal name
//     * 
//     * "" if there is no goal name
//     * 
//     * (wendt)
//     *
//     * @since 16.07.2012 16:54:50
//     *
//     * @param poGoal
//     * @return
//     */
//    private ArrayList<clsWordPresentation> getConditionDataStructure() {
//        ArrayList<clsWordPresentation> oResult = new ArrayList<clsWordPresentation>();
//        
//        ArrayList<clsSecondaryDataStructure> oFoundTaskStatusList = this.moAssociationMapping.get(eContentType.CONDITION);//clsGoalTools.getConditionDataStructure(poGoal);
//                
//        for (clsSecondaryDataStructure oTaskStatus : oFoundTaskStatusList) {
//            oResult.add((clsWordPresentation) oTaskStatus);
//        }
//        
//        return oResult;
//        
//        //return getNonUniquePredicateWP(ePredicate.HASCONDITION);
//    }
    
    
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
//        
//        clsWordPresentationMesh oRetVal = clsGoalTools.getNullObjectWPM();
//        
//        ArrayList<clsSecondaryDataStructure> oFoundStructures = this.moAssociationMapping.get(eContentType.SUPPORTIVEDATASTRUCTURE);//poGoal.findDataStructure(ePredicate.HASSUPPORTIVEDATASTRUCTURE, true);
//        
//        if (oFoundStructures.isEmpty()==false) {
//            //The drive object is always a WPM
//            oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
//        }
//        
//        return oRetVal;
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
    
    
//    /**
//     * Get the supportive data structure for actions
//     * 
//     * (wendt)
//     *
//     * @since 16.07.2012 22:21:26
//     *
//     * @param poGoal
//     * @return
//     */
//    public static clsWordPresentationMesh getSupportiveDataStructureForAction(clsWordPresentationMesh poGoal) {
//        clsWordPresentationMesh oRetVal = clsGoalTools.getNullObjectWPM();
//        
//        ArrayList<clsSecondaryDataStructure> oFoundStructures = poGoal.findDataStructure(ePredicate.HASSUPPORTIVEDATASTRUCTUREFORACTION, true);
//        
//        if (oFoundStructures.isEmpty()==false) {
//            //The drive object is always a WPM
//            oRetVal = (clsWordPresentationMesh) oFoundStructures.get(0);
//        }
//        
//        return oRetVal;
//    }
    
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
    public eGoalType getGoalType() {
        eGoalType oRetVal = eGoalType.NULLOBJECT;
        
        String oGoalType = this.getUniqueProperty(ePredicate.HASGOALTYPE);
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
    public void setGoalType(eGoalType oGoalType) {
        this.setUniqueProperty(oGoalType.toString(), eContentType.GOALTYPE, ePredicate.HASGOALTYPE, true);        
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
    public clsWordPresentationMesh getAssociatedAction() {
        return this.getUniquePropertyWPM(ePredicate.HASASSOCIATEDACTION);
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
    public void setAssociatedAction(clsWordPresentationMesh poAssociatedAction) {
        this.setUniqueProperty(poAssociatedAction, ePredicate.HASASSOCIATEDACTION, true);
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
    public clsWordPresentationMesh getAssociatedAimAction() {
        return this.getUniquePropertyWPM(ePredicate.HASASSOCIATEDAIMACTION);
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
    public void setAssociatedAimAction(clsWordPresentationMesh poAssociatedAction) {
        this.setUniqueProperty(poAssociatedAction, ePredicate.HASASSOCIATEDAIMACTION, true);
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
        this.removeAllProperties(ePredicate.HASASSOCIATEDACTION);
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
    

    @Override
    public String toString() {
        String oResult = "";  
        oResult += this.getGoalContentIdentifier();
        double rImportanceOfFeelings = clsImportanceTools.getConsequencesOfFeelingsOnGoalAsImportance(this);
        double nTotalAffectLevel = getImportance() + getEffortLevel() + rImportanceOfFeelings;
        
        oResult += ":" + new DecimalFormat("0.00").format(nTotalAffectLevel);
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
