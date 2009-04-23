// File clsContainerAbstractImages.java
// May 15, 2006
//

// Belongs to package
package bfg.symbolization.brainimages;

// Imports
//import com.xj.anylogic.*;

import java.util.TreeMap;



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

 /* public clsContainerCompareResults associate(clsImagePerception iPerception,                         
                                              clsScenarioContainer poBrainsScenarioList,
                                              clsDesireContainer poBrainsDesireList,
                                              clsContainerComplexEmotion poBrainsComplexEmotions, 
                                              clsContainerPerceptions poBrainsPerceptions, 
                                              clsIdentity poBrainsIdentity
                                              ) {
    clsContainerCompareResults oResult = new clsContainerCompareResults();

    Set oKeySet = keySet();
    Iterator i = oKeySet.iterator();
    while (i.hasNext()) {
      Integer oKey = (Integer)i.next();

      clsImageAbstract oAI = (clsImageAbstract)moContainer.get(oKey);
      oResult.add(oAI.getCompareResult(iPerception, poBrainsScenarioList, 
                                       poBrainsDesireList, poBrainsComplexEmotions, poBrainsPerceptions, poBrainsIdentity ));    
    }
    return oResult;
  }*/

  protected String gettoString(Object poObject) {
    return ((clsImageAbstract)poObject).toString();
  }

};
