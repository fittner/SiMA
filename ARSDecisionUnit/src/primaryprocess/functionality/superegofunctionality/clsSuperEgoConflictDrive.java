/**
 * CHANGELOG
 *
 * 13.11.2013 Kollmann - File created
 * 22.01.2014 Gelbard - Conflict tension added 
 *
 */
package primaryprocess.functionality.superegofunctionality;


import base.datatypes.clsDriveMesh;
import base.datatypes.enums.eDriveComponent;
import base.datatypes.enums.eOrgan;
import base.datatypes.helpstructures.clsPair;

/**
 * DOCUMENT (Kollmann) - This class represents a single conflict between super ego and self.
 * 
 * @author Kollmann
 * 13.11.2013, 09:54:40
 * 
 */
public class clsSuperEgoConflictDrive { 
    
    private String moAction;
    private eDriveComponent moConflictComponent;
    private eOrgan moConflictOrgan;
    private eDefenseType moPreferedDefense;
    private Double moConflictTension = 0.0; // FG 22.01.2014: initialized with 0.0 to avoid null-pointer exceptions

    boolean memorizedDrive = false;
    
    
    public clsSuperEgoConflictDrive(clsPair<eDriveComponent, eOrgan> poConflictIdentification) {
        this(poConflictIdentification.a, poConflictIdentification.b);
    }
    
    public clsSuperEgoConflictDrive(clsPair<eDriveComponent, eOrgan> poConflictIdentification, double oConflictTension) {
        setConflictComponent(poConflictIdentification.a);
        setConflictOrgan(poConflictIdentification.b);
        setPreferedDefense(eDefenseType.UNSPECIFIED_DEFENSE);
        setConflictTension(oConflictTension);
    }
    
    public clsSuperEgoConflictDrive(eDriveComponent poConflictComponent, eOrgan poConflictOrgan) {
        this(poConflictComponent, poConflictOrgan, eDefenseType.UNSPECIFIED_DEFENSE);
    }
    
    public clsSuperEgoConflictDrive(String poAction, double poConflictTension) { 
        setMoAction(poAction);
        setConflictTension(poConflictTension);
    }
    
    public clsSuperEgoConflictDrive(eDriveComponent poConflictComponent, eOrgan poConflictOrgan, eDefenseType poPreferedDefense) {
        setConflictComponent(poConflictComponent);
        setConflictOrgan(poConflictOrgan);
        setPreferedDefense(poPreferedDefense);
    }

    
    public clsSuperEgoConflictDrive(String poAction, double poConflictTension, eDriveComponent peDriveComponent, eOrgan peOrgan) { 
        setMoAction(poAction);
        setConflictTension(poConflictTension);
        setConflictComponent(peDriveComponent);
        setConflictOrgan(peOrgan);
        setPreferedDefense(eDefenseType.UNSPECIFIED_DEFENSE);
        memorizedDrive = true;
    }
    
    
    public boolean isConflict(clsDriveMesh poDrive) {
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
        return "Super-ego conflict (" + moConflictComponent.toString() + "|" + moConflictOrgan.toString() + "|" + moConflictTension.toString() + " -> " + moPreferedDefense.toString() + ")";
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
    public double getConflictTension() {
        return moConflictTension;
    }
    public void setConflictTension(double oConflictTension) {
        this.moConflictTension = oConflictTension;
    }

    /**
     * @since Apr 20, 2015 3:38:56 PM
     * 
     * @return the moAction
     */
    public String getMoAction() {
        return moAction;
    }

    /**
     * @since Apr 20, 2015 3:38:56 PM
     * 
     * @param moAction the moAction to set
     */
    public void setMoAction(String moAction) {
        this.moAction = moAction;
    }

    /**
     * @since Apr 23, 2015 6:22:00 PM
     * 
     * @return the moActionConflict
     */
    // if main conflict is the action conflict
    public boolean isActionConflict() {
        return (moAction!=null && moAction != "");
    }

    /**
     * @since Apr 23, 2015 6:22:00 PM
     * 
     * @param moActionConflict the moActionConflict to set
     */

}
