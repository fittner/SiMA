/** 
 * CHANGELOG
 *
 * 29.11.2011 wendt - File created
 *
 */
package pa._v38.memorymgmt.enums;

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
	EFFORTLEVEL,
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
	
	
	//--- Goals ---//
	CONDITION,
	GOALTYPE,
	GOALNAME,
	PREFERREDACTION,
	
	//--- Actions ---//
	PRECONDITION,
	POSTCONDITION,
	ACTIONTYPE,
	
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
	COMPLEXEMOTION,
	
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
	DRIVEDEMAND,
	DM,
	TI,
	TP,
	TPM,
	WP,
	WPM,			//What should be done here
	PRIINSTANCE, 	//This datatype is necessary in order to store actual experiences
	EMOTION, 
	LIBIDO,
	CONTEXT,
	
	//Subdatatype
	ACT,
	GOAL,
	MENTALSITUATION,
	ACTION,
	
	
	//TPs: should be adapted
	COLOR,
	Color,
	ShapeType,
	INTENSITY,
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
	IsConsumeable,
	
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
	DebugSensorArousal;
}
