/**
 * eAffectLevel.java: DecisionUnits - pa._v38.memorymgmt.enums
 * 
 * @author kohlhauser
 * 31.08.2010, 13:38:25
 */
package memorymgmt.enums;


/**
 * enum list with all used predicates in clsAssociationSecondary
 * (wendt) 
 * 
 * @author wendt
 * 12.09.2011, 09:28:44
 * @deprecated
 * 
 * CONTENT TYPE SHOULD BE USED INSTEAD OF PREDICATE - for all new functions, try to avoid predicates
 */
public enum ePredicate {
	//General
	NONE,
	HASASSOCIATION,
	
	//Hierarchical
	ISA,
	PARTOF,
	HASSUPER,
	HASPART,
	
	//has factors
	//HASCONFIRMFACTOR,
	//HASBEENCONFIRMED,
	//HASTEMPORALPROGRESSFACTOR,
	//HASTEMPORALPROGRESS,
	//HASCONFIRMPROGRESS,
	//ACTIVATEAFFECTREDUCE,
	
	//Acts
	HASMOMENTCONFIDENCE,
	HASACTCONFIDENCE,
	HASMOVEMENTTIMEOUT,
	HASINDIVIDUALMOVEMENTTIMEOUT,
	
	//Goals and motivations
	HASREALITYAFFECT,
	HASDRIVEOBJECT,
	HASSUPPORTIVEDATASTRUCTURE,
	HASSUPPORTIVEDATASTRUCTUREFORACTION,
	HASDRIVEDEMANDIMPORTANCE,
	HASTOTALIMPORTANCE,
	HASAGGRESSION,
	HASLIBIDO,
	HASPLEASURE,
	HASUNPLEASURE,
	HASAFFECT,
	HASAFFECTLEVEL,
	HASEFFORTIMPACTIMPORTANCE,
	HASGOALSOURCE,
	HASGOAL,
	HASGOALOBJECT,
	HASACTION,
	HASACTIONOBJECT,
	HASCONDITION,
	HASGOALNAME,
	HASGOALTYPENAME,
	HASPREFERREDACTION,
	HASASSOCIATEDPLANACTION,
	HASPOTENTIALDRIVEAIM,
	HASASSOCIATEDDRIVEAIMACTION,
	HASEXCLUDEDGOAL,
	HASSOCIALRULESIMPORTANCE, 
	HASFEELINGSMATCHIMPORTANCE,
	HASEXPECTEDFEELINGSIMPORTANCE,
	HASINTENSITY,
	HASQUOTAOFAFFECTASIMPORTANCE,
	HASPOTENTIALDRIVEFULFILLMENTIMPORTANCE,
	HASDRIVEDEMANDCORRECTIONIMPORTANCE,
	HASDRIVEAIMIMPORTANCE,
	HASBODYSTATE,
	HASENTITYVALUATIONMATCHIMPORTANCE,
	HASENTITYBODYSTATEMATCHIMPORTANCE,
	
	//MentalSituation
	HASPLANGOAL,
	HASSELECTABLEGOAL,
	HASAIMOFDRIVE,
	
	//Action
	HASPRECONDITION,
	HASPOSTCONDITION,
	HASACTIONTYPE,
	HASACTIONREFINEMENT,
	
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
	
	//Entities
	HASATTRIBUTEDEMOTION,
	
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
