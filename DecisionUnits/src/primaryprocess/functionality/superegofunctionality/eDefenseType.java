/**
 * CHANGELOG
 *
 * 13.11.2013 Kollmann - File created
 *
 */
package primaryprocess.functionality.superegofunctionality;

/**
 * DOCUMENT (Kollmann) - enum containing the available defense mechanisms 
 * 
 * @author Kollmann
 * 13.11.2013, 09:56:06
 * 
 */
public enum eDefenseType {
    // Special values //
    NULL_DEFENSE, //this value should represent problems/error in defense assignment - check NO_DEFENSE and UNSPECIFIED_DEFENSE for other special defense types
    UNSPECIFIED_DEFENSE, //use this if the defense module should choose the defense itself 
    NO_DEFENSE, //this means that the defense module should NOT defend against the drive representation that with this

    // Now for the real defense mechanisms //
    SUBLIMATION
    //....//
}
