/**
 * CHANGELOG
 *
 * 24.02.2015 Kollmann - File created
 *
 */
package primaryprocess.functionality.superegofunctionality;

import memorymgmt.enums.eContentType;
import memorymgmt.enums.eEmotionType;
import base.datatypes.clsEmotion;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 24.02.2015, 12:52:25
 * 
 */
public class clsSuperEgoConflictEmotion {
    private eEmotionType moConflictEmotionType = null;
    private eDefenseType moPreferedDefense = eDefenseType.UNSPECIFIED_DEFENSE;
    private Double moConflictTension = 0.0;

    public clsSuperEgoConflictEmotion(eEmotionType poConflictEmotionType, eDefenseType poPreferedDefense, double rConflictTension) {
        moConflictEmotionType = poConflictEmotionType;
        setPreferedDefense(poPreferedDefense);
        setConflictTension(rConflictTension);
    }
    
    public clsSuperEgoConflictEmotion(eEmotionType poConflictEmotionType, double rConflictTension) {
        this(poConflictEmotionType, eDefenseType.UNSPECIFIED_DEFENSE, rConflictTension);
    }
    
    public clsSuperEgoConflictEmotion(String poConflictIdentification, double rConflictTension) {
        this(eEmotionType.valueOf(poConflictIdentification), rConflictTension);
    }
    
    public clsSuperEgoConflictEmotion(String poConflictIdentification, eDefenseType poPreferedDefense, double rConflictTension) {
        this(eEmotionType.valueOf(poConflictIdentification), poPreferedDefense, rConflictTension);
    }

    public boolean isConflict(clsEmotion poEmotion) {
        boolean bIsConflict = false; 
        
        //Kollmann: we can only compare BASICEMOTIONs
        if(poEmotion != null && poEmotion.getContentType().equals(eContentType.BASICEMOTION)) {
            bIsConflict = poEmotion.getContent().equals(moConflictEmotionType);
        }
        
        return bIsConflict;
    }
    
    @Override
    public String toString() {
        return "Super-ego conflict (" + moConflictEmotionType.toString() + "|" + moConflictTension.toString() + " -> " + moPreferedDefense.toString() + ")";
    }
    
    
    // Only Getters and Setter from here on
    public eEmotionType getConflictEmotionType() {
        return moConflictEmotionType;
    }
    public void getConflictEmotionType(eEmotionType poConflictEmotionType) {
        this.moConflictEmotionType = poConflictEmotionType;
    }
    public eDefenseType getPreferedDefense() {
        return moPreferedDefense;
    }
    public void setPreferedDefense(eDefenseType poPreferedDefense) {
        this.moPreferedDefense = poPreferedDefense;
    }
    public double getConflictTension() {
        return moConflictTension;
    }
    public void setConflictTension(double oConflictTension) {
        this.moConflictTension = oConflictTension;
    }
}
