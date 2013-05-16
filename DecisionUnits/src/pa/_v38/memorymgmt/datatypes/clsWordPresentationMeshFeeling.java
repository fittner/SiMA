/**
 * CHANGELOG
 *
 * 16.05.2013 wendt - File created
 *
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

import pa._v38.memorymgmt.enums.eAffectLevel;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.enums.ePredicate;
import pa._v38.tools.clsMeshTools;
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
        
        this.moAssociationMapping.put(eContentType.AFFECTLEVEL, new ArrayList<clsSecondaryDataStructure>());
    }
    
    public eAffectLevel getAffect() {
        eAffectLevel oResult = null;
        
        ArrayList<clsSecondaryDataStructure> oS = this.moAssociationMapping.get(eContentType.AFFECTLEVEL);
        
        if (oS.isEmpty()==false) {
            oResult = eAffectLevel.valueOf(((clsWordPresentation)oS.get(0)).getMoContent().toString());
        }
        
        return oResult;
    }
    
    public void setAffect(eAffectLevel poAffectLevel) {
        clsMeshTools.setUniquePredicateWP(this, eContentType.ASSOCIATIONSECONDARY, ePredicate.HASAFFECTLEVEL, eContentType.AFFECTLEVEL, poAffectLevel.toString(), false);
        
        //Get all of them
        ArrayList<clsSecondaryDataStructure> oS = this.moAssociationMapping.get(eContentType.AFFECTLEVEL);
        oS.add(clsMeshTools.getUniquePredicateWP(this, ePredicate.HASAFFECTLEVEL));
        
        this.moAssociationMapping.put(eContentType.AFFECTLEVEL, oS);
    }

}
