package datageneration.datatypes;

import java.util.HashMap;
import java.util.Map;

public class clsValuationMatrix <CONNECTION_DATA_TYPE> {
	private Map<clsValuation, Map<clsValuation, CONNECTION_DATA_TYPE>> moContributorMatrix = new HashMap<>();
	private Map<clsValuation, Map<clsValuation, CONNECTION_DATA_TYPE>> moContributedMatrix = new HashMap<>();
	
	public void contribution(clsValuation poContributor, clsValuation poContributed, CONNECTION_DATA_TYPE poConnectionData) {
		addContributor(poContributor, poContributed, poConnectionData);
		addContributed(poContributor, poContributed, poConnectionData);
	}
	
//	public Map<clsValuation, CONNECTION_DATA_TYPE> getContributors(clsValuation poValuation) {
//		if(isValidValuation(poValuation)) {
//			return moContributedMatrix.get(poValuation);
//		}
//	}
//	
//	public Map<clsValuation, CONNECTION_DATA_TYPE> getContributed(clsValuation poValuation) {
//		
//	}
	
	protected boolean isValidValuation(clsValuation poValuation) {
		return moContributorMatrix.containsKey(poValuation) && moContributedMatrix.containsKey(poValuation);
	}
	
	public void addContributor(clsValuation poContributor, clsValuation poContributed, CONNECTION_DATA_TYPE poConnectionData) {
		Map<clsValuation, CONNECTION_DATA_TYPE> oContributions = getRow(poContributor, moContributorMatrix);
		oContributions.put(poContributed, poConnectionData);
	}
	
	public void addContributed(clsValuation poContributor, clsValuation poContributed, CONNECTION_DATA_TYPE poConnectionData) {
		Map<clsValuation, CONNECTION_DATA_TYPE> oContributions = getRow(poContributed, moContributedMatrix);
		oContributions.put(poContributor, poConnectionData);
	}
	
	protected Map<clsValuation, CONNECTION_DATA_TYPE> getRow(clsValuation poKey, Map<clsValuation, Map<clsValuation, CONNECTION_DATA_TYPE>> poMap) {
		if(!poMap.containsKey(poKey)) {
			poMap.put(poKey, new HashMap<>());
		}
		
		return poMap.get(poKey);
	}
}
