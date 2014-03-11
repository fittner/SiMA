/**
 * CHANGELOG
 *
 * 11.03.2014 Gelbard - file created 
 *
 */
package primaryprocess.functionality.superegofunctionality;

import memorymgmt.enums.eContentType;

/**
 * DOCUMENT (Gelbard) - This class represents a single conflict between super ego rules and perceptions.
 * 
 * @author Gelbard
 * 11.03.2014, 18:20:43
 * 
 */
public class clsSuperEgoConflictPerception {
    private eContentType moContentType;
    private String moContent;
    private eDefenseType moPreferedDefense;
    private Double moConflictTension = 0.0;
    
    public clsSuperEgoConflictPerception(eContentType poContentType, String poContent, eDefenseType poPreferedDefense, double poConflictTension) {
        setContentType(poContentType);
        setContent(poContent);
        setPreferedDefense(poPreferedDefense);
        setConflictTension(poConflictTension);
    }
    
    @Override
    public String toString() {
        return "Super-ego conflict perception (" + moContentType.toString() + "|" + moContent.toString() + "|" + moConflictTension.toString() + " -> " + moPreferedDefense.toString() + ")";
    }
    
    
    // Only Getters and Setter from here on
    public eContentType getContentType() {
        return moContentType;
    }
    public void setContentType(eContentType poContentType) {
        this.moContentType = poContentType;
    }
    public String getContent() {
        return moContent;
    }
    public void setContent(String poContent) {
        this.moContent = poContent;
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
