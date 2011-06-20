/**
 * eDataStructureMatch.java: DecisionUnits - pa._v19.memorymgmt.informationrepresentation.enums
 * 
 * @author zeilinger
 * 15.07.2010, 13:09:32
 */
package pa._v19.memorymgmt.informationrepresentation.enums;

/**
 *
 * 
 * @author zeilinger
 * 15.07.2010, 13:09:32
 * 
 */
public enum eDataStructureMatch {
	MANDATORYMATCH(1.0),
	OPTIONALMATCH(0.1),
	IDENTITYMATCH(9999.0),
	THRESHOLDMATCH(0.0);
	
	double mrMatchFactor; 
	private eDataStructureMatch(double prMatchFactor){
		mrMatchFactor = prMatchFactor; 
	}
	
	public double getMatchFactor(){
		return mrMatchFactor; 
	}
}
