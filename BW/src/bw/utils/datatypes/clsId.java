// File clsId.java
// December 02, 2005
//

// Belongs to package
package bw.utils.datatypes;

// Imports
import java.awt.Color;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 *
 */
public class clsId implements java.lang.Comparable {
  public int mnTeamId;
  public int mnEntityId;

  public clsId() {
    mnTeamId = -1;
    mnEntityId = -1;
  }
  public clsId(int pnTeamId, int pnEntityId) {
    mnTeamId = pnTeamId;
    mnEntityId = pnEntityId;
  }
  public clsId(clsId poId) {
    mnTeamId = poId.mnTeamId;
    mnEntityId = poId.mnEntityId;
  }

  public String toString() {
    return mnTeamId+"/"+mnEntityId;
  }

  public void set(int pnTeamId, int pnEntityId) {
    mnTeamId = pnTeamId;
    mnEntityId = pnEntityId;
  }

  public void set(clsId oId) {
    mnTeamId = oId.mnTeamId;
    mnEntityId = oId.mnEntityId;
  }

  public boolean itsMe(clsId poId, boolean pnExact) {
    return itsMe(poId.mnTeamId, poId.mnEntityId, pnExact);
  }

  public boolean itsMe(int pnTeamId, int pnEntityId, boolean pnExact) {
    boolean nResult = false;

    if (pnExact) {
      if (pnTeamId==mnTeamId && pnEntityId==mnEntityId) {
        nResult = true;
      }
    } else {
      if ((pnTeamId == -1 || pnTeamId==mnTeamId) && (pnEntityId ==-1 || pnEntityId==mnEntityId)) {
        nResult = true;
      }
    }

    return nResult;
  }

  public Color getTeamColor() {
    if (mnTeamId == 1) {
      return Color.white;
    } else if (mnTeamId == 2) {
      return Color.blue;
    } else if (mnTeamId == 3) {
      return Color.green;
    } 

    return Color.black;
  }

  public final int compareTo( Object poId )
  { 
    int nResult = -1;

    clsId oOther = (clsId)poId;

    if (this.mnTeamId == oOther.mnTeamId) {
      if (this.mnEntityId > oOther.mnEntityId) {
        nResult = 1;
      } else if (this.mnEntityId == oOther.mnEntityId) {
        nResult = 0;
      }
    } else if (this.mnTeamId > oOther.mnTeamId) {
      nResult = 1;
    }

    return nResult;
  }
};
