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
	
	
	// --- Entities --- //
	ENTITY,
	PI,
	PIMATCH, 		//Content type of perception
	GOALTYPE,
	RI,
	RIREPRESSED,
	RILIBIDO,
	AREASUBSET,
	AFFECTLEVEL,
	ACTION,
	GOAL,
	PREDICTION,
	PLAN,
	INTENTION,
	MOMENT,
	EXPECTATION,
	MENTALSITUATION,
	
	DRIVEGOALSUPPORT,
	PERCEPTIONSUPPORT,
	SUPPORTIVEDATASTRUCTURE,
	
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
	DRIVEOBJECTASSOCIATION,
	DRIVESOURCEASSOCIATION,
	DRIVEAIMASSOCIATION,
	SUPPORTDSASSOCIATION,
	PHANTASYFLAG,
	
	//Datatypes
	UNDEFINED,
	ACT,
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
	
	//TPs
	COLOR,
	Color,
	ShapeType,
	ANTENNA_LEFT,
	HANDS,
	INTENSITY,
	Alive,
	TASTE,
	CONSUMEABLE,
	CONTENT,
	PositionChange,
	BUMP,
	ANTENNA_RIGHT,
	RADIATION,
	ObjectPosition,
	Distance,
	POSITIONCHANGE,
	
	//DM
	BITE,
	NOURISH,
	RELAX,
	SLEEP,
	DEPOSIT,
	REPRESS,
	
	
	// TODO DELETE AFTER IMPLEMENTATION OF NEW DM STRUCTURE
	DEATH, LIFE, AGGRESSION,
	
	//Delete after implementation of secondary process
	ACTIVATEREDUCEAFFECT,
	
	//???? OBSOLETE Delete ????
	RELATION,
	LOCATION,
	
	//??? REPLACE THIS SOME DAY...???
	DEFAULT,
	X,
	Y, 
	A;
}
