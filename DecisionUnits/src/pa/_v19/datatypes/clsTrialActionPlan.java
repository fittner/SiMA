/**
 * clsTrialActionPlan.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 08.10.2009, 14:59:55
 */
package pa._v19.datatypes;


/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 08.10.2009, 14:59:55
 * 
 */
@Deprecated
public class clsTrialActionPlan extends clsSecondaryInformation implements Cloneable {

	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 08.10.2009, 14:59:59
	 *
	 * @param poWP
	 * @param poTP
	 * @param poAffect
	 */
	public clsTrialActionPlan(clsWordPresentation poWP,
			clsThingPresentationSingle poTP, clsAffectTension poAffect) {
		super(poWP, poTP, poAffect);
		// TODO (langr) - Auto-generated constructor stub
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsTrialActionPlan oClone = (clsTrialActionPlan)super.clone();
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	

}