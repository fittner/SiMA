// File clsIdentity.java
// November 10, 2006
//
// Belongs to package
package pa._v19.bfg.symbolization.brainimages;

// Imports
//import com.xj.anylogic.*;

/**
 *
 * This is the class description ...
 *
 * $Revision: 681 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-07-11 16:39:54 +0200 (Mi, 11 Jul 2007) $: Date of last commit
 * @deprecated
 */
public class  clsIdentity
  {
    public boolean mnEnergyProducer = false;
    public boolean mnEnergyConsumer = false;
    public int mnTeamId   = -1;
    public int mnBubbleId = -1;
    public boolean mnFocusOfAttention = true;
    
    @Override
    public String toString() 
    {
      return "Identity TID:"+mnTeamId+" BID:"+mnBubbleId+" EP:"+mnEnergyProducer+" EC:"+mnEnergyConsumer+" Focus:"+mnFocusOfAttention; 
    };
  }
