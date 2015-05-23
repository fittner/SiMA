/**
 * CHANGELOG
 *
 * 16.05.2013 wendt - File created
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
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 16.05.2013, 18:15:51
 * 
 */
public class clsWordPresentationMeshFeeling extends clsWordPresentationMesh {
    //kollmann: if we convert an emotion from memory into a feeling, the resulting feelings needs an instance_id. We will use the original emotions ID + this CONVERT_FEELING_BASE
    static final int CONVERTED_FEELING_BASE = 10000;
    
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
     * DOCUMENT - Constructor for creating clsWordPresentationMeshFeeling from clsEmotion (the clsEmotion will be stored via ASSOCIATIONWP)
     *
     * @author Kollmann
     * @since 08.04.2014 18:13:21
     *
     * @param poEmotion
     */
    public clsWordPresentationMeshFeeling(clsEmotion poEmotion) {
        super(new clsTriple<Integer, eDataType, eContentType>(-1, eDataType.WPM, eContentType.FEELING), new ArrayList<clsAssociation>(), poEmotion.getContent().toString());
        
        mapProperties(poEmotion);
        
        //store association to original clsEmotion object via ASSOCIATIONWP
        clsAssociationWordPresentation oWPAssEmotion = new clsAssociationWordPresentation(new clsTriple<Integer, eDataType, eContentType>
        (-1, eDataType.ASSOCIATIONWP, eContentType.ASSOCIATIONWP), this, poEmotion);
        this.addExternalAssociation(oWPAssEmotion);

        this.moDSInstance_ID = CONVERTED_FEELING_BASE + poEmotion.getDS_ID();
        
        //Kollmann: for some reason clsEmotion seems to be missing its externalAssociation array - for now we only anchor the associationWP in the WPMFeeling
//        poEmotion.addExternalAssociation(oWPAssEmotion);
    }
    
    /**
     * DOCUMENT - Map the properties of the poEmotion object to the properties of the clsWordPresentationMeshFeeling object
     *
     * @author Kollmann
     * @since 08.04.2014 18:13:35
     *
     * @param poEmotion
     */
    protected void mapProperties(clsEmotion poEmotion) {
        setIntensity(poEmotion.getEmotionIntensity());
        setLibido(poEmotion.getSourceLibid());
        setAggression(poEmotion.getSourceAggr());
        setPleasure(poEmotion.getSourcePleasure());
        setUnpleasure(poEmotion.getSourceUnpleasure());
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
    
    public double getDiff(final clsWordPresentationMeshFeeling poOther) {
        double rMatch = 1;
        
        if(this.getContent().contentEquals(poOther.getContent())) {
            rMatch = Math.abs(this.getIntensity() - poOther.getIntensity());
        }
        
        return rMatch;
    }
    
    public String debugString() {
        String oText = getContent() + ":";
        oText +=" Intensity=" + new DecimalFormat("0.00").format(getIntensity());
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