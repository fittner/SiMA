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
	//General
	HASASSOCIATION,
	
	//Hierarchical
	ISA,
	PARTOF,
	HASSUPER,
	HASPART,
	
	//has factors
	HASCONFIRMFACTOR,
	HASBEENCONFIRMED,
	HASTEMPORALPROGRESSFACTOR,
	HASTEMPORALPROGRESS,
	HASCONFIRMPROGRESS,
	ACTIVATEAFFECTREDUCE,
	
	//Goals and motivations
	HASREALITYAFFECT,
	HASDRIVEOBJECT,
	HASSUPPORTIVEDATASTRUCTURE,
	HASAFFECT,
	HASAFFECTLEVEL,
	HASGOALTYPE,
	HASGOAL,
	HASACTION,
	HASDECISIONTASK,
	
	//PI and RI
	HASNEXT,
	HASINTENTION,
	HASMOMENT,
	HASEXPECTATION,
	HASPIMATCH,
	HASDISTANCE,
	HASPOSITION,
	HASCONTEXT,
	HASFEELING,
	HASTEMPORALCONTEXT,
	HASSPATIALCONTEXT,
	
	//Relations
	GENERAL, 
	LEFTBEHINDOF,
	LEFTOF,
	LEFTINFRONTOF,
	INFRONTOF,
	RIGHTINFRONTOF,
	RIGHTOF,
	RIGHTBEHINDOF,
	BEHINDOF,	
	ONPOSITION,
	NEAROF,
	MEDIUMOF,
	FAROF,
	OUT_OF_SIGHT_OF,
	
	
	//Control
	HASPHANTASYFLAG;
	
	/*public int mnPredicate;
	
	ePredicate(int pnPredicate)
	{
		this.mnPredicate = pnPredicate;
	}*/
}
