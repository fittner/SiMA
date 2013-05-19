/**
 * CHANGELOG
 *
 * 16.05.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.tools.clsTriple;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 16.05.2013, 18:15:51
 * 
 */
public class clsWordPresentationMeshFeeling extends clsWordPresentationMesh {

    /**
     * DOCUMENT (wendt) - insert description 
     *
     * @since 16.05.2013 18:16:27
     *
     * @param poDataStructureIdentifier
     * @param poAssociatedStructures
     * @param poContent
     */
    public clsWordPresentationMeshFeeling(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
            ArrayList<clsAssociation> poAssociatedStructures, Object poContent) {
        super(poDataStructureIdentifier, poAssociatedStructures, poContent);
        // TODO (wendt) - Auto-generated constructor stub
        
        this.moAssociationMapping.put(eContentType.IMPORTANCE, new ArrayList<clsSecondaryDataStructure>());
    }
    
    /**
     * Get Importance
     * 
     * (wendt)
     *
     * @since 17.05.2013 15:03:11
     *
     * @return
     */
    public double getImportance() {
        double nResult = 0;
        
        String oImportance = this.getUniqueProperty(eContentType.IMPORTANCE);
        
        if (oImportance.isEmpty()==false) {
            nResult = Double.valueOf(oImportance);
        }
        
        return nResult;
    }
    
    /**
     * Set importance/intensity of the feeling
     * 
     * (wendt)
     *
     * @since 17.05.2013 15:06:18
     *
     * @param poImportance
     */
    public void setImportance(double poImportance) {
        this.setUniqueProperty(String.valueOf(poImportance), eContentType.IMPORTANCE, ePredicate.HASIMPORTANCE, true); 
    }
 
}