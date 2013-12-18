/**
 * CHANGELOG
 *
 * 16.05.2013 wendt - File created
 *
 */
package base.datatypes;

import java.util.ArrayList;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.ePredicate;
import base.datatypes.helpstructures.clsTriple;

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
        
        String oFeelingParameter = this.getUniqueProperty(ePredicate.HASINTENSITY);
        
        if (oFeelingParameter.isEmpty()==false) {
            nResult = Double.valueOf(oFeelingParameter);
        }
        
        return nResult;
    }
    
    public void setIntensity(double poIntensity){
        this.setUniqueProperty(String.valueOf(poIntensity), eContentType.INTENSITY, ePredicate.HASINTENSITY, true);
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