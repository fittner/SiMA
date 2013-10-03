/**
 * CHANGELOG
 *
 * 03.10.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;
import datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 03.10.2013, 15:07:01
 * 
 */
public class clsWordPresentationMeshMentalSituation extends clsWordPresentationMesh {

    /**
     * WordPresentationMesh Mental Situation
     *
     * @author wendt
     * @since 03.10.2013 15:09:26
     *
     * @param poDataStructureIdentifier
     * @param poAssociatedStructures
     * @param poContent
     */
    public clsWordPresentationMeshMentalSituation(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, ArrayList<clsAssociation> poAssociatedStructures, Object poContent) {
        super(poDataStructureIdentifier, poAssociatedStructures, poContent);
    }
    
    public static clsWordPresentationMesh createInstance() {
        return clsWordPresentationMesh.createInstance(eContentType.MENTALSITUATION, "MENTALSITUATION");
    }
    
    //=== Getters and Setters ===//
    
    /**
     * Set plan goal
     *
     * @author wendt
     * @since 03.10.2013 15:23:39
     *
     * @param poDataStructure
     */
    public void setPlanGoal(clsWordPresentationMeshSelectableGoal poDataStructure) {
        this.setUniqueProperty(poDataStructure, ePredicate.HASPLANGOAL, true);       
    }
    
    /**
     * get plan goal
     *
     * @author wendt
     * @since 03.10.2013 15:23:47
     *
     * @return
     */
    public clsWordPresentationMeshSelectableGoal getPlanGoal() {
        return (clsWordPresentationMeshSelectableGoal) this.getUniquePropertyWPM(ePredicate.HASPLANGOAL);
    }
    
    /**
     * add selectable goal
     *
     * @author wendt
     * @since 03.10.2013 15:26:37
     *
     * @param poDataStructure
     */
    public void addSelectableGoal(clsWordPresentationMeshSelectableGoal poDataStructure) {
        this.addReplaceNonUniqueProperty(poDataStructure, ePredicate.HASSELECTABLEGOAL, true);       
    }
    
    /**
     * get all selectable goals
     *
     * @author wendt
     * @since 03.10.2013 15:26:40
     *
     */
    public ArrayList<clsWordPresentationMeshSelectableGoal> getSelectableGoals() {
        ArrayList<clsWordPresentationMesh> wordPresentationMeshList = this.getNonUniquePropertyWPM(ePredicate.HASSELECTABLEGOAL);
        ArrayList<clsWordPresentationMeshSelectableGoal> result = new ArrayList<clsWordPresentationMeshSelectableGoal>();
        
        for (clsWordPresentationMesh wpm : wordPresentationMeshList) {
            if (wpm instanceof clsWordPresentationMeshSelectableGoal) {
                result.add((clsWordPresentationMeshSelectableGoal) wpm);
            } else {
                throw new ClassCastException("This structure is no valid class for this association " + wpm);
            }
        }
        
        return result;    
    }
    
    /**
     * add excluded selectable goal
     *
     * @author wendt
     * @since 03.10.2013 15:26:37
     *
     * @param poDataStructure
     */
    public void addExcludedSelectableGoal(clsWordPresentationMeshSelectableGoal poDataStructure) {
        this.addReplaceNonUniqueProperty(poDataStructure, ePredicate.HASEXCLUDEDGOAL, true);       
    }
    
    /**
     * get all excluded selectable goals
     *
     * @author wendt
     * @since 03.10.2013 15:26:40
     *
     */
    public ArrayList<clsWordPresentationMeshSelectableGoal> getExcludedSelectableGoals() {
        ArrayList<clsWordPresentationMesh> wordPresentationMeshList = this.getNonUniquePropertyWPM(ePredicate.HASEXCLUDEDGOAL);
        ArrayList<clsWordPresentationMeshSelectableGoal> result = new ArrayList<clsWordPresentationMeshSelectableGoal>();
        
        for (clsWordPresentationMesh wpm : wordPresentationMeshList) {
            if (wpm instanceof clsWordPresentationMeshSelectableGoal) {
                result.add((clsWordPresentationMeshSelectableGoal) wpm);
            } else {
                throw new ClassCastException("This structure is no valid class for this association " + wpm);
            }
        }
        
        return result;    
    }
    
    /**
     * add selectable goal
     *
     * @author wendt
     * @since 03.10.2013 15:26:37
     *
     * @param poDataStructure
     */
    public void addAimOfDrive(clsWordPresentationMeshSelectableGoal poDataStructure) {
        this.addReplaceNonUniqueProperty(poDataStructure, ePredicate.HASAIMOFDRIVE, true);       
    }
    
    /**
     * get all selectable goals
     *
     * @author wendt
     * @since 03.10.2013 15:26:40
     *
     */
    public ArrayList<clsWordPresentationMeshAimOfDrive> getAimOfDrives() {
        ArrayList<clsWordPresentationMesh> wordPresentationMeshList = this.getNonUniquePropertyWPM(ePredicate.HASAIMOFDRIVE); 
        ArrayList<clsWordPresentationMeshAimOfDrive> result = new ArrayList<clsWordPresentationMeshAimOfDrive>();
        
        for (clsWordPresentationMesh wpm : wordPresentationMeshList) {
            if (wpm instanceof clsWordPresentationMeshSelectableGoal) {
                result.add((clsWordPresentationMeshAimOfDrive) wpm);
            } else {
                throw new ClassCastException("This structure is no valid class for this association " + wpm);
            }
        }
        
        return result;
    }
    

}
