// File clsXMLReaderTools.java
// February 20, 2007
//

// Belongs to package
package pa._v19.bfg.tools.xmltools;

// Imports
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;

import bfg.utils.enumsOld.enumTypeTrippleState;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 * @deprecated
 */
public class clsXMLReaderTools {

  public static String getNodeValue(Node poNode) {
    return poNode.getNodeValue();
  }

  public static String getNamedItemString(NamedNodeMap poAttributes, String poName) throws java.lang.NullPointerException {
    String oResult = "";

    try {
      oResult = getNodeValue( poAttributes.getNamedItem(poName) );

    } catch (java.lang.NullPointerException ex) {
      //Engine.log.println("clsXMLReaderTools.getNamedItemString - possibly attribute '"+poName+"' not defined or misspelled in xml. "+ex);

      throw new java.lang.NullPointerException();
    }

    return oResult;
  }
   
  public static int getNamedItemInteger(NamedNodeMap poAttributes, String poName) throws java.lang.NullPointerException {
    int nResult = 0;

    try {
      nResult = Integer.parseInt( getNamedItemString(poAttributes, poName) );

    } catch (java.lang.NumberFormatException ex) {
      //Engine.log.println("clsXMLReaderTools.getNamedItemInteger NumberFormatException while fetching attribute '"+poName+"'. "+ex);

    } catch (java.lang.NullPointerException ex) {
      //Engine.log.println("clsXMLReaderTools.getNamedItemInteger - possibly attribute '"+poName+"' not defined or misspelled in xml. "+ex);

      throw new java.lang.NullPointerException();
    }

    return nResult;
  }

   
  public static float getNamedItemFloat(NamedNodeMap poAttributes, String poName) throws java.lang.NullPointerException {
    float rResult = 0;

    try {
      rResult = Float.parseFloat( getNamedItemString(poAttributes, poName) );

    } catch (java.lang.NumberFormatException ex) {
      //Engine.log.println("clsXMLReaderTools.getNamedItemFloat NumberFormatException while fetching attribute '"+poName+"'. "+ex);

    } catch (java.lang.NullPointerException ex) {
      //Engine.log.println("clsXMLReaderTools.getNamedItemFloat - possibly attribute '"+poName+"' not defined or misspelled in xml. "+ex);

      throw new java.lang.NullPointerException();
    }

    return rResult;
  }

  public static boolean getNamedItemTrippleStateBoolean(NamedNodeMap poAttributes, String poName) throws java.lang.NullPointerException {
    boolean nResult = false;

    try {
      nResult = enumTypeTrippleState.getBoolean( getNamedItemString(poAttributes, poName) );

    } catch (java.lang.NullPointerException ex) {
      //Engine.log.println("clsXMLReaderTools.getNamedItemTrippleStateBoolean - possibly attribute '"+poName+"' not defined or misspelled in xml. "+ex);

      throw new java.lang.NullPointerException();
    }

    return nResult;
  }
};
