/** 
 * CHANGELOG
 *
 * 29.11.2011 wendt - File created
 *
 */
package memorymgmt.enums;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 29.11.2011, 15:09:08
 * 
 */
public enum eContentType {
	//--- System ---//
	NULLOBJECT,
	NOTHING,
	EMPTYSPACE,
	
	//---Images---//
	PI,			//Perceived Image
	RI,			//Remembered Image
	PHI,		//Phantasized image
	RIREPRESSED,
	RILIBIDO,
	
	PIMATCH, 		//Content type of perception
	
	//--- Entities --- //
	ENTITY,
	
	AREASUBSET,
	AFFECTLEVEL,
	PREDICTION,
	PLAN,
	INTENTION,
	MOMENT,
	EXPECTATION,
	ENVIRONMENTALIMAGE,
	ENHANCEDENVIRONMENTALIMAGE,
	
	DRIVEGOALSUPPORT,
	PERCEPTIONSUPPORT,
	SUPPORTIVEDATASTRUCTURE,
	
	
	   //--- Speech Wording ---//
	KNOWN,
	INVITED,
	YES,
    EAT,
    SHARE,
    SPEAK,
    SPEAK_SHARE,
    SPEAK_YES,
    SPEAK_EAT,
    SPEAK_INVITED,
    
	//--- Goals ---//
	CONDITION,
	GOALSOURCE,
	GOALNAME,
	GOALOBJECT,
	GOALTYPENAME,
	
	// --- Goals->SelectableGoal --- //
	PREFERREDACTION,
	
	DRIVEDEMANDIMPORTANCE,
	DRIVEDEMANDCORRECTIONIMPORTANCE,
	FEELINGSIMPORTANCE,
	SOCIALRULESIMPORTANCE,
	EFFORTIMPACTIMPORTANCE,
	TOTALIMPORTANCE,
	POTENTIALDRIVEFULFILLMENTIMPORTANCE,
	DRIVEAIMIMPORTANCE,
	
	Expressions,
	
	
	
	// --- Feelings --- //
	FEELAGGRESSION,
	FEELLIBIDO,
	FEELPLEASURE,
	FEELUNPLEASURE,
	
	// --- Goals->AimOfDrive --- //
	QUOTAOFAFFECTASIMPORTANCE,
	
	//--- Actions ---//
	PRECONDITION,
	POSTCONDITION,
	ACTIONTYPE,
	NAME,
	
	//Acts
	MOMENTCONFIDENCE,
	ACTCONFIDENCE,
	MOVEMENTTIMEOUT,
	
	// --- Bodypart --- //
	BODYPART,
	ORIFICE,
	ORGAN,
	
	
	//--- Emotions
	BASICEMOTION,
	MEMORIZEDEMOTION, // kollmann: this is used to identify that this emotion, if associated to an activated image, will influence the current drive state
	ATTRIBUTEDEMOTION, // kollmann: this is used to identify that an emotion is attributed to the primaryinstance of an entity (currently used in intentions)
	COMPLEXEMOTION,
	
	// Feelings
	BASICFEELING,
	
	//--- Positioning
	DISTANCE,
	POSITION,
	DISTANCERELATION,
	POSITIONRELATION,
		
	//--- Associations ---//
	DISTANCEASSOCIATION,
	POSITIONASSOCIATION,
	MATCHASSOCIATION,
	PIASSOCIATION,
	GOALTYPEASSOCIATION,
	PARTOFASSOCIATION,
	ASSOCIATIONSECONDARY,
	SUPPORTDSASSOCIATION,
	PHANTASYFLAG,
	DRIVEOBJECTASSOCIATION,
	
	//Datatypes
	UNDEFINED,
	AFFECT,
	ASSOCIATIONTEMP,
	ASSOCIATIONATTRIBUTE,
	ASSOCIATIONWP,
	ASSOCIATIONDM,
	ASSOCIATIONPRI,
	ASSOCIATIONPRIDM,
	ASSOCIATIONSEC,
	ASSOCIATIONEMOTION,
	ASSOCIATIONFEELING,
	DRIVEDEMAND,
	DM,
	TI,
	TP,
	TPM,
	WP,
	WPM,			//What should be done here
	PRIINSTANCE, 	//This datatype is necessary in order to store actual experiences
	ACTIONINSTANCE,
	EMOTION, 
	LIBIDO,
	CONTEXT,
	FEELING,
	
	//Subdatatype
	ACT,
	GOAL,
	MENTALSITUATION,
	ACTION,
	
	
	//TPs: should be adapted
	COLOR,
	PERCEIVEDACTION,
	Color,
	ShapeType,
	INTENSITY,
	Brightness,
	Alive,
	TASTE,
	CONSUMEABLE,
	CONTENT,
	PositionChange,
	BUMP,
	RADIATION,
	ObjectPosition,
	Distance,
	POSITIONCHANGE,
	NumEntitiesPresent,
	AntennaPositionLeft,
	AntennaPositionRight,
	IsAlive,
	Intensity,
	IsConsumeable,
	ORIFICE_ORAL_LIBIDINOUS_MUCOSA,
	ORIFICE_ORAL_AGGRESSIV_MUCOSA,
	Odor,
	Name,
	HeartBeat,
	SweatIntensity,
	CryingIntensity,
	MuscleTensionArmsIntensity,
	MuscleTensionLegsIntensity,
	CHEEKS_REDNING,
	EYE_BROW_CENTER,
	EYE_BROW_CORNERS,
	MOUTH_OPEN,
	MOUTH_SIDES,
	MOUTH_STRECHINESS,
	SHAKE_INTENSITY,
	EYES_CRYING_INTENSITY,
	GENERAL_SWEAT,
	PARTIAL_SWEAT,
	
	
	//DM
	BITE,
	NOURISH,
	RELAX,
	SLEEP,
	DEPOSIT,
	REPRESS,
	DRIVECANDIDATE,
	DRIVECOMPONENT,
	DRIVEREPRESENTATION,
	MEMORIZEDDRIVEREPRESENTATION,
	MEMORIZEDDRIVEREPRESENTATIONIMAGE, 	//Add by AW. This shall only be used in PRI instances
	
	
	
	// TODO DELETE AFTER IMPLEMENTATION OF NEW DM STRUCTURE
	DEATH, LIFE, AGGRESSION,
	
	//Delete after implementation of secondary process
	//ACTIVATEREDUCEAFFECT,
	
	//???? OBSOLETE Delete ????
	RELATION,
	LOCATION,
	
	//??? REPLACE THIS SOME DAY...???
	DEFAULT,
	X,
	Y, 
	A,
	
	//Debug Values for Inspectors only, dont use or delete or Rename them - CM 25.09.2012
	ExactDebugX,
	ExactDebugY,
	DebugSensorArousal,
	
	//PREDICATES
	//General
    NONE,
    HASASSOCIATION,
    
    //Hierarchical
    ISA,
    PARTOF,
    HASSUPER,
    HASPART,
    
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
    HASIMPORTANCE,
    HASAFFECT,
    HASAFFECTLEVEL,
    HASEFFORTLEVEL,
    HASGOALTYPE,
    HASGOAL,
    HASGOALOBJECT,
    HASACTION,
    HASCONDITION,
    HASGOALNAME,
    HASPREFERREDACTION,
    HASASSOCIATEDACTION,
    HASEXCLUDEDGOAL,
    
    //Action
    HASPRECONDITION,
    HASPOSTCONDITION,
    HASACTIONTYPE,
    
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
    HASPHANTASYFLAG,
	
	//Expression
    Expression; //koller
	
	public static eContentType getContentType(String poContentType) {
        
        return eContentType.valueOf(poContentType);
        
    }
	
}
