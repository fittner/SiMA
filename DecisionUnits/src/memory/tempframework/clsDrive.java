// File clsDrive.java
// May 02, 2006
//

// Belongs to package
package memory.tempframework;

// Imports
//import com.xj.anylogic.*;
//
import org.w3c.dom.Node;
import org.w3c.dom.NamedNodeMap;
import bw.memory.tempframework.clsCloneable;
import bw.memory.tempframework.enumTypeLevelDrive;
import bw.memory.tempframework.enumTypeDrive;

/**
 *
 * This is the class description ...
 *
 * $Revision: 768 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-19 23:12:25 +0200 (Do, 19 Jul 2007) $: Date of last commit
 *
 */
public class clsDrive extends clsCloneable {
  int meTypeId;
  int meLevel = enumTypeLevelDrive.TLEVELDRIVE_UNDEFINED;

  float moValue;

  public clsDrive(int pnTypeId) {
    meTypeId = pnTypeId;

    moValue = 0;
    setLevel();
  }
  public clsDrive(int pnTypeId, float prValue) {
    meTypeId = pnTypeId;

    moValue = prValue;
    setLevel();
  }

  public void setValue(float prValue) {
    moValue=prValue;
    setLevel();
  }
  public float getValue() {
    return moValue;
  }
  public int getType() {
    return meTypeId;
  }
  public int getLevel() {
    return meLevel;
  }
  public float getInternalValue() {
    return moValue;
  }

  public void setLevel(int level) {
    meLevel = level;
  }

  private void setLevel() {
    meLevel = enumTypeLevelDrive.getDriveLevel(moValue);
  }



  public static clsDrive createDrive( Node poDriveNode )
  {
    NamedNodeMap attribs = poDriveNode.getAttributes();
    clsDrive retVal = null;

    if (attribs.getLength() != 0) {
      String level="", type="";
      if( attribs.getNamedItem("Name") != null )
      {
        type  = attribs.getNamedItem("Name").getNodeValue();
      }
      if( attribs.getNamedItem("Level") != null )
      {
        level = attribs.getNamedItem("Level").getNodeValue();
      }
      int typeId = enumTypeDrive.getInteger(type);
      int levelValue = enumTypeLevelDrive.getInteger(level);
      retVal = new clsDrive(typeId);
      retVal.meLevel = levelValue;
    }
    return retVal;
  }



 
};
