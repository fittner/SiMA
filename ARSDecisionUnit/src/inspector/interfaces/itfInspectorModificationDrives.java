/**
 * CHANGELOG
 *
 * 13.11.2013 Jordakieva - File created
 *
 */
package inspector.interfaces;

import java.util.ArrayList;

import primaryprocess.modules.F06_DefenseMechanismsForDrives.clsChangedDrives;


/**
 * DOCUMENT (Jordakieva) - If this interface is implemented by a module, an inspector tab displays the rule-changes after the defense 
 * 
 * @author Jordakieva
 * 13.11.2013, 17:11:54
 * 
 */
public interface itfInspectorModificationDrives {
    
    //DriveAim, DriveObject, ChartsShortString, QoA
    ArrayList <clsChangedDrives> processList ();

}
