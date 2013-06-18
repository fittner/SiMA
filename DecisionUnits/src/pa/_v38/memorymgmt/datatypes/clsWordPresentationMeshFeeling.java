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
        
        //this.moAssociationMapping.put(eContentType.IMPORTANCE, new ArrayList<clsSecondaryDataStructure>());
    }
    
    public double getIntensity(){
        
        double nResult = 0;
        
        String oFeelingParameter = this.getUniqueProperty(ePredicate.HASIMPORTANCE);
        
        if (oFeelingParameter.isEmpty()==false) {
            nResult = Double.valueOf(oFeelingParameter);
        }
        
        return nResult;
    }
    
    public void setIntensity(double poIntensity){
        this.setUniqueProperty(String.valueOf(poIntensity), eContentType.IMPORTANCE, ePredicate.HASIMPORTANCE, true);
    }
    
    public double getLibido(){
        
        double nResult = 0;
        
        String oFeelingParameter = this.getUniqueProperty(ePredicate.HASLIBIDO);
        
        if (oFeelingParameter.isEmpty()==false) {
            nResult = Double.valueOf(oFeelingParameter);
        }
        
        return nResult;
    }
    
    public void setLibido(double poLibido){
        this.setUniqueProperty(String.valueOf(poLibido), eContentType.FEELLIBIDO, ePredicate.HASLIBIDO, true);
    }
    
    public double getAggression(){
        
        double nResult = 0;
        
        String oFeelingParameter = this.getUniqueProperty(ePredicate.HASAGGRESSION);
        
        if (oFeelingParameter.isEmpty()==false) {
            nResult = Double.valueOf(oFeelingParameter);
        }
        
        return nResult;
    }
    
    public void setAggression(double poAggression){
        this.setUniqueProperty(String.valueOf(poAggression), eContentType.FEELAGGRESSION, ePredicate.HASAGGRESSION, true);
    }
    
    public double getPleasure(){
        
        double nResult = 0;
        
        String oFeelingParameter = this.getUniqueProperty(ePredicate.HASPLEASURE);
        
        if (oFeelingParameter.isEmpty()==false) {
            nResult = Double.valueOf(oFeelingParameter);
        }
        
        return nResult;
    }
    
    public void setPleasure(double poPleasure){
        this.setUniqueProperty(String.valueOf(poPleasure), eContentType.FEELPLEASURE, ePredicate.HASPLEASURE, true);
    }
    
    public double getUnpleasure(){
        
        double nResult = 0;
        
        String oFeelingParameter = this.getUniqueProperty(ePredicate.HASUNPLEASURE);
        
        if (oFeelingParameter.isEmpty()==false) {
            nResult = Double.valueOf(oFeelingParameter);
        }
        
        return nResult;
    }
    
    public void setUnpleasure(double poUnpleasure){
        this.setUniqueProperty(String.valueOf(poUnpleasure), eContentType.FEELUNPLEASURE, ePredicate.HASUNPLEASURE, true);
    }
}