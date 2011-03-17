/**
 * clsConnection.java: DecisionUnitMasonInspectors - inspectors.mind.pa.functionalmodel
 * 
 * @author deutsch
 * 22.10.2009, 12:13:05
 */
package inspectors.mind.pa._v30.functionalmodel;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.10.2009, 12:13:05
 * 
 */
public class clsConnection {
	public final int mnMainId;
	public final int mnSubId;
	public final clsNode moTarget;
	
	public clsConnection(int pnMainId, int pnSubId, clsNode poTarget) {
		mnMainId = pnMainId;
		mnSubId = pnSubId;
		moTarget = poTarget;
	}
	
	@Override
	public String toString() {
		return "I"+mnMainId+"."+mnSubId;
	}
	
	public clsNode getTarget() {
		return moTarget;
	}
}
