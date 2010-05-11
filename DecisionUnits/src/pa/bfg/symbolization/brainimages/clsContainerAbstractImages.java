// File clsContainerAbstractImages.java
// May 15, 2006
//

// Belongs to package
package pa.bfg.symbolization.brainimages;

// Imports
//import com.xj.anylogic.*;

import java.util.ArrayList;
import java.util.TreeMap;

import pa.bfg.symbolization.ruletree.clsRuleCompareResult;


import du.itf.sensors.clsSensorData;



// REMOVED from langr during import --> necessary for associate-functionality
//import java.util.Set;
//import java.util.Iterator;
//import java.util.Vector;
//import pkgBrain.clsIdentity;
//import pkgBrainScenario.clsScenarioContainer;
//import pkgBrainDesire.clsDesireContainer;
//import pkgBrainComplexEmotion.clsContainerComplexEmotion;


/**
 *
 * This is the class description ...
 *
 * $Revision: 627 $:  Revision of last commit
 * $Author: deutsch $: Author of last commit
 * $Date: 2007-06-25 12:20:22 +0200 (Mo, 25 Jun 2007) $: Date of last commit
 *
 */
public class clsContainerAbstractImages {

	public TreeMap<Integer, clsImageAbstract> moAbstractImageList;	
	
	public clsContainerAbstractImages() {
		moAbstractImageList = new TreeMap<Integer, clsImageAbstract>();
	}

	
  public clsImageAbstract get(int pnPos) {
    return moAbstractImageList.get(pnPos);
  }

  public ArrayList<clsRuleCompareResult> associate(clsSensorData poSensorData,
                                              clsIdentity poBrainsIdentity
                                              ) {
    ArrayList<clsRuleCompareResult> oResult = new ArrayList<clsRuleCompareResult>();

    for(clsImageAbstract oAI : moAbstractImageList.values() ) {
      oResult.add( oAI.getCompareResult(poSensorData, poBrainsIdentity  ));    
    }
    return oResult;
  }

  protected String gettoString(Object poObject) {
    return ((clsImageAbstract)poObject).toString();
  }

};
