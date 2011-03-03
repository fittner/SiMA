// File clsTeamConfig.java
// September 19, 2006
//

// Belongs to package
package pa.bfg.tools.xmltools;

// Imports
import org.w3c.dom.Node;

/**
 *
 * This is the class description ...
 *
 * $Revision: 572 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 * @deprecated
 */
public class clsTeamConfig
{
  public int mnTeamId = -1;
  public String moType = "";
  public String moXMLConfigPath = "";
  public int mnCount = 0;
  public Node moNode = null;

  public clsTeamConfig(int pnTeamId, String poTeamType, String poXMLConfigPath, Node poNode, int pnCount )
  {
    mnTeamId = pnTeamId;
    moType = poTeamType;
    moXMLConfigPath = poXMLConfigPath;
    mnCount = pnCount;  
    moNode = poNode;
  }
}
