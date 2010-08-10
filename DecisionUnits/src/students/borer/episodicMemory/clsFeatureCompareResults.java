package students.borer.episodicMemory;

import students.borer.episodicMemory.tempframework.clsContainerCompareResults;
import students.borer.episodicMemory.tempframework.clsRuleCompareResult;

/**
 * The class clsFeatureActions represents the feature TI-matches. It inherites the basic functionalities which are equal for each feature and implements specific functionalities for the feature TI-matches. $Revision: 572 $:  Revision of last commit $Author: deutsch $: Author of last commit $Date: 2007-05-31 10:56:07 +0200 (Do, 31 Mai 2007) $: Date of last commit
 */
public class clsFeatureCompareResults extends clsFeature  {
	private clsContainerCompareResults moContainerCompareResults;
	
	/**
	 * Instantiates a new feature TI-matches with the respective elements indicated in the container in the parameter. If
	 * triggering encoding or spontaneous retrieval for this feature should be disabled, mnTriggerEncodingEnabled respectively
	 * mnSpontaneousRetrievalEnabled have to be set false.
	 * @param poImageCompareResults The container with the individual TI-matches of this situation
	 */
	@SuppressWarnings("unchecked")
	public clsFeatureCompareResults(clsContainerCompareResults poImageCompareResults) {
		super();
		moContainerCompareResults = poImageCompareResults;
		for(int i=0; i<poImageCompareResults.size(); i++){
			clsRuleCompareResult oCompResult = (clsRuleCompareResult)poImageCompareResults.get(i);
			moFeatureElements.add( new clsElementCompareResult( oCompResult ));
		}
	}
	/**
	 * Returns the initially delivered actions in a container (clsContainerCompareResults)
	 */
	@Override
	public Object getContainer() {
		return moContainerCompareResults;
	}
	@Override
	public boolean checkIfSameType(clsFeature poFeature) {
		if(poFeature instanceof clsFeatureCompareResults) {
			return true;
		}
		return false;
	}
	@Override
	public String toString() {
		return moContainerCompareResults.toString();
		//return "Template Images";
	}
}
