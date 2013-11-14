/**
 * CHANGELOG
 *
 * 13.11.2013 Kollmann - File created
 *
 */
package primaryprocess.functionality.superegofunctionality;

import pa._v38.memorymgmt.datatypes.clsDriveMesh;
import datatypes.helpstructures.clsPair;
import du.enums.eOrgan;
import du.enums.pa.eDriveComponent;

/**
 * DOCUMENT (Kollmann) - This class represents a single conflict between super ego and self.
 * 
 * @author Kollmann
 * 13.11.2013, 09:54:40
 * 
 */
public class clsSuperEgoConflict {
    private eDriveComponent moConflictComponent;
    private eOrgan moConflictOrgan;
    private eDefenseType moPreferedDefense;

    public clsSuperEgoConflict(clsPair<eDriveComponent, eOrgan> poConflictIdentification) {
        this(poConflictIdentification.a, poConflictIdentification.b);
    }
    
    public clsSuperEgoConflict(eDriveComponent poConflictComponent, eOrgan poConflictOrgan) {
        this(poConflictComponent, poConflictOrgan, eDefenseType.UNSPECIFIED_DEFENSE);
    }
    
    public clsSuperEgoConflict(eDriveComponent poConflictComponent, eOrgan poConflictOrgan, eDefenseType poPreferedDefense) {
        setConflictComponent(poConflictComponent);
        setConflictOrgan(poConflictOrgan);
        setPreferedDefense(poPreferedDefense);
    }

    public boolean checkDrive(clsDriveMesh poDrive) {
        boolean bIsConflict = false; 
        
        if(poDrive != null) {
            eDriveComponent oComponent = poDrive.getDriveComponent();
            eOrgan oOrgan = poDrive.getActualDriveSourceAsENUM();
            if(oComponent != null && oOrgan != null) {
                return (poDrive.getDriveComponent().equals(moConflictComponent) &&
                        poDrive.getActualDriveSourceAsENUM().equals(moConflictOrgan));
            }
        }
        
        return bIsConflict;
    }
    
    @Override
    public String toString() {
        return "Super-ego conflict (" + moConflictComponent.toString() + "|" + moConflictOrgan.toString() + " -> " + moPreferedDefense.toString() + ")";
    }
    
    // Only Getters and Setter from here on
    public eDriveComponent getConflictComponent() {
        return moConflictComponent;
    }
    public void setConflictComponent(eDriveComponent poConflictComponent) {
        this.moConflictComponent = poConflictComponent;
    }
    public eOrgan getConflictOrgan() {
        return moConflictOrgan;
    }
    public void setConflictOrgan(eOrgan poConflictOrgan) {
        this.moConflictOrgan = poConflictOrgan;
    }
    public eDefenseType getPreferedDefense() {
        return moPreferedDefense;
    }
    public void setPreferedDefense(eDefenseType poPreferedDefense) {
        this.moPreferedDefense = poPreferedDefense;
    }
}
