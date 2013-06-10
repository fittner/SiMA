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
    
    /**
     * Gets the given "type" of feeling parameter's current value.
     * The currently valid types are: INTENSITY, AGGRESSION, LIBIDO, PLEASURE, UNPLEASURE.
     * Throws an IllegalArgumentException in case of an invalid type.
     * This method is not case sensitive. 
     * (mahmody)
     *
     * @since 10.06.2013 13:09:52
     *
     * @param type
     * @return
     * @throws IllegalArgumentException
     */
    public double getFeelingParam(String type) throws IllegalArgumentException{
        
        ePredicate pred = null;
        
        FeelingParams ftype = FeelingParams.valueOf(type.toUpperCase());
        
        switch(ftype){
            case INTENSITY:
                pred = ePredicate.HASIMPORTANCE;
                break;
            case AGGRESSION:
                pred = ePredicate.HASAGGRESSION;
                break;
            case LIBIDO:
                pred = ePredicate.HASLIBIDO;
                break;
            case PLEASURE:
                pred = ePredicate.HASPLEASURE;
                break;
            case UNPLEASURE:
                pred = ePredicate.HASUNPLEASURE;
                break;
            default:
                throw new IllegalArgumentException(("The type "+ type +" does not exist"));
        }
        
        double nResult = 0;
        
        String oFeelingParameter = this.getUniqueProperty(pred);
        
        if (oFeelingParameter.isEmpty()==false) {
            nResult = Double.valueOf(oFeelingParameter);
        }
        
        return nResult;
    }
    
    /**
     * Sets the given "type" of feeling parameter to the given "paramValue".
     * The currently valid types are: INTENSITY, AGGRESSION, LIBIDO, PLEASURE, UNPLEASURE.
     * Throws an IllegalArgumentException in case of an invalid type.
     * This method is not case sensitive. 
     * 
     * 
     * (mahmody)
     *
     * @since 10.06.2013 13:02:14
     *
     * @param type
     * @param paramValue
     * @throws IllegalArgumentException 
     */
    public void setFeelingParam(String type, double paramValue) throws IllegalArgumentException{
        
        eContentType cType = null;
        ePredicate pred = null;
        
        FeelingParams ftype = FeelingParams.valueOf(type.toUpperCase());
        
        switch(ftype){
            case INTENSITY:
                cType = eContentType.IMPORTANCE;
                pred = ePredicate.HASIMPORTANCE;
                break;
            case AGGRESSION:
                cType = eContentType.FEELAGGRESSION;
                pred = ePredicate.HASAGGRESSION;
                break;
            case LIBIDO:
                cType = eContentType.FEELLIBIDO;
                pred = ePredicate.HASLIBIDO;
                break;
            case PLEASURE:
                cType = eContentType.FEELPLEASURE;
                pred = ePredicate.HASPLEASURE;
                break;
            case UNPLEASURE:
                cType = eContentType.FEELUNPLEASURE;
                pred = ePredicate.HASUNPLEASURE;
                break;
            default:
                throw new IllegalArgumentException(("The type "+ type +" does not exist"));
        }
        
        this.setUniqueProperty(String.valueOf(paramValue), cType, pred, true);
    }
 
    public enum FeelingParams{
        INTENSITY,
        AGGRESSION,
        LIBIDO,
        PLEASURE,
        UNPLEASURE
    }
}