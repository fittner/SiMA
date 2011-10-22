/**
 * eAffectLevel.java: DecisionUnits - pa._v38.memorymgmt.enums
 * 
 * @author kohlhauser
 * 31.08.2010, 13:38:25
 */
package pa._v38.memorymgmt.enums;


/**
 * enum list with all used predicates in clsAssociationSecondary
 * (wendt) 
 * 
 * @author wendt
 * 12.09.2011, 09:28:44
 * 
 */
public enum ePredicate {
	ISA,
	HASNEXT,
	HASCONFIRMFACTOR,
	HASBEENCONFIRMED,
	HASTEMPORALPROGRESSFACTOR,
	HASTEMPORALPROGRESS,
	HASCONFIRMPROGRESS,
	HASINTENTION,
	HASREALITYAFFECT,
	HASASSOCIATION,
	ACTIVATESPHANTASY,
	ACTIVATEAFFECTREDUCE;
	
	/*public int mnPredicate;
	
	ePredicate(int pnPredicate)
	{
		this.mnPredicate = pnPredicate;
	}*/
}
