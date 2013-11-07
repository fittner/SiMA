/**
 * CHANGELOG
 *
 * 01.11.2013 Jordakieva - File created
 *
 */
package pa._v38.interfaces;

import java.util.ArrayList;

import pa._v38.modules.F07_SuperEgoReactive.clsReadSuperEgoRules;

/**
 * DOCUMENT (Jordakieva) - If this interface is implemented by a module, an inspector tab that displays the rules returned by getDriverlules () is added.
 * 
 * @author Jordakieva
 * 01.11.2013, 16:15:30
 * 
 */
public interface itfInspectorForRules {
    
    //das ist die Funktion des Moduls welche der Inspektor aufrufen wird
    public ArrayList <clsReadSuperEgoRules> getDriverules ();

}
