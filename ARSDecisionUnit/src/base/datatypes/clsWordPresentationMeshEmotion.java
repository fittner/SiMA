/**
 * CHANGELOG
 *
 * 07.05.2015 Kollmann - File created
 *
 */
package base.datatypes;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eDataType;
import memorymgmt.enums.ePredicate;
import base.datatypes.helpstructures.clsTriple;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 07.05.2015, 16:38:14
 * 
 */
public class clsWordPresentationMeshEmotion extends clsWordPresentationMesh {
    static final int CONVERTED_FEELING_BASE = 20000;
    
    /**
     * DOCUMENT - insert description
     *
     * @author Kollmann
     * @since 07.05.2015 16:38:15
     *
     * @param poDataStructureIdentifier
     * @param poAssociatedStructures
     * @param poContent
     */
    public clsWordPresentationMeshEmotion(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier,
            ArrayList<clsAssociation> poAssociatedStructures, Object poContent) {
        super(poDataStructureIdentifier, poAssociatedStructures, poContent);
        // TODO (Kollmann) - Auto-generated constructor stub
    }

    public clsWordPresentationMeshEmotion(clsEmotion poEmotion) {
        super(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.EMOTION, eContentType.ATTRIBUTEDEMOTION),
                new ArrayList<clsAssociation>(), poEmotion.getContent().toString());
        
        //store association to original clsEmotion object via ASSOCIATIONWP
        clsAssociationWordPresentation oWPAssEmotion = new clsAssociationWordPresentation(new clsTriple<Integer, eDataType, eContentType>
        (-1, eDataType.ASSOCIATIONWP, eContentType.ASSOCIATIONWP), this, poEmotion);
        this.addExternalAssociation(oWPAssEmotion);
        
        this.moDSInstance_ID = CONVERTED_FEELING_BASE + poEmotion.getDS_ID();
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
    
    public String debugString() {
        String oText = getContent() + ":";
        oText +=" Libido=" + new DecimalFormat("0.00").format(getLibido());
        oText +=" Aggression=" + new DecimalFormat("0.00").format(getAggression());
        oText +=" Pleasure=" + new DecimalFormat("0.00").format(getPleasure());
        oText +=" Unpleasure=" + new DecimalFormat("0.00").format(getUnpleasure());
        
        return oText;
    }
    
    public clsEmotion getEmotion() {
        clsEmotion oEmotion = null;
        List<clsAssociationWordPresentation> oAssWPs = clsAssociationWordPresentation.getAllExternAssociationWordPresentation(this);
        
        if(!oAssWPs.isEmpty()) {
            for(clsAssociationWordPresentation oAssWP : oAssWPs) {
                if(oAssWP.getRootElement() instanceof clsEmotion) {
                    if(oEmotion != null) {
                        log.warn("Feeling " + getContent() + " seems to have more than one emotion associated - will only be using the first one");
                    } else {
                        oEmotion = (clsEmotion) oAssWP.getRootElement();
                    }
                }
            }
        }
        
        return oEmotion;
    }
    
    @Override
    public String toString() {
        return debugString();
    }
}
