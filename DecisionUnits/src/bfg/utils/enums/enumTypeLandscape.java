/**
 * @author deutsch
 * 
 * Copied from the old bubble family game
 * 
 * CHKME keep this file?
 * @deprecated taken from old BFG
 * 
 * $Rev:: 1825                 $: Revision of last commit
 * $Author:: deutsch           $: Author of last commit
 * $Date:: 2009-01-27 19:59:35#$: Date of last commit
 */

// File enumTypeLandscape.java
// May 21, 2007
//

// Belongs to package
package bfg.utils.enums;

// Imports

/**
 *
 * This is the class description ...
 *
 * @deprecated
 * 
 * $Revision: 1825 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2009-01-27 19:59:35 +0100 (Di, 27 Jän 2009) $: Date of last commit
 *
 */
public class enumTypeLandscape extends enumClass {
  public final static int TLANDSCAPE_UNDEFINED    = -1;
  public final static int TLANDSCAPE_ANY          = -2;

  public final static int TLANDSCAPE_DESERT       = 0;
  public final static int TLANDSCAPE_SWAMP        = 1;
  public final static int TLANDSCAPE_GRAS         = 2;
  public final static int TLANDSCAPE_WATER        = 3;
  public final static int TLANDSCAPE_STONE        = 4;
  public final static int TLANDSCAPE_BRIDGE       = 5;
  public final static int TLANDSCAPE_TREE         = 6;
  public final static int TLANDSCAPE_WOOD         = 7;
  public final static int TLANDSCAPE_WALL         = 8;
  public final static int TLANDSCAPE_BUILDING     = 9;
  public final static int TLANDSCAPE_PATH         = 10;


  /** 
    * converts the enum value to a human readable string
    */
  public static String getString(int pnEnum) {
    String oResult = enumClass.getString(pnEnum);

    switch(pnEnum) {
      case  TLANDSCAPE_UNDEFINED:oResult = "[undefined]";break;

      case  TLANDSCAPE_ANY:oResult = "[any]";break;

      case  TLANDSCAPE_DESERT:oResult   = "[desert]";break;
      case  TLANDSCAPE_SWAMP:oResult    = "[swamp]";break;
      case  TLANDSCAPE_GRAS:oResult     = "[gras]";break;
      case  TLANDSCAPE_WATER:oResult    = "[water]";break;
      case  TLANDSCAPE_STONE:oResult    = "[stone]";break;
      case  TLANDSCAPE_BRIDGE:oResult   = "[bridge]";break;
      case  TLANDSCAPE_TREE:oResult     = "[tree]";break;
      case  TLANDSCAPE_WOOD:oResult     = "[wood]";break;
      case  TLANDSCAPE_WALL:oResult     = "[wall]";break;
      case  TLANDSCAPE_BUILDING:oResult = "[building]";break;
      case  TLANDSCAPE_PATH:oResult     = "[path]";break;

      default:oResult = "_unkown_"+pnEnum+"_";
    }

    return oResult;
  }
  /**
    * converts a string read from an XML-file into its corresponding enum value
    */
  public static int getInteger(String poValue)
  {
     int nValue = TLANDSCAPE_UNDEFINED;

     if( poValue.toLowerCase().equals("desert"))
     {
      nValue = TLANDSCAPE_DESERT;
     }
     else if( poValue.toLowerCase().equals("any") )
     {
      nValue = TLANDSCAPE_ANY;
     }
     else if( poValue.toLowerCase().equals("swamp") )
     {
      nValue = TLANDSCAPE_SWAMP;
     }
     else if( poValue.toLowerCase().equals("gras") )
     {
      nValue = TLANDSCAPE_GRAS;
     }
     else if( poValue.toLowerCase().equals("water") )
     {
      nValue = TLANDSCAPE_WATER;
     }
     else if( poValue.toLowerCase().equals("stone") )
     {
      nValue = TLANDSCAPE_STONE;
     }
     else if( poValue.toLowerCase().equals("bridge") )
     {
      nValue = TLANDSCAPE_BRIDGE;
     }
     else if( poValue.toLowerCase().equals("tree") )
     {
      nValue = TLANDSCAPE_TREE;
     }
     else if( poValue.toLowerCase().equals("wood") )
     {
      nValue = TLANDSCAPE_WOOD;
     }
     else if( poValue.toLowerCase().equals("wall") )
     {
      nValue = TLANDSCAPE_WALL;
     }
     else if( poValue.toLowerCase().equals("building") )
     {
      nValue = TLANDSCAPE_BUILDING;
     }
     else if( poValue.toLowerCase().equals("path") )
     {
      nValue = TLANDSCAPE_PATH;
     }
     return nValue;
  }
  
};
