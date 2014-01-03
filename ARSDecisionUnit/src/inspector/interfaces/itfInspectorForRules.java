/**
 * CHANGELOG
 *
 * 01.11.2013 Jordakieva - File created
 *
 */
package inspector.interfaces;

import java.util.ArrayList;

import primaryprocess.functionality.superegofunctionality.clsReadSuperEgoRules;

/**
 * DOCUMENT (Jordakieva) - If this interface is implemented by a module, an inspector tab that displays the rules returned by getDriverlules () is added.
 * 
 * @author Jordakieva
 * 01.11.2013, 16:15:30
 * 
 */
public interface itfInspectorForRules {
    
    public ArrayList <clsReadSuperEgoRules> getDriverules ();

}
