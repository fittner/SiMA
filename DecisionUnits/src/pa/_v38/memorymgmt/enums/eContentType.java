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
	
	
	// --- Entities --- //
	ENTITY,
	PI,
	PIMATCH, 		//Content type of perception
	GOALTYPE,
	RI,
	LIBIDO,
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
	SUPPORTIVEDATASTRUCTURE,
	
	// --- Bodypart --- //
	BODYPART,
	
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
	WPM,		//What should be done here
	PRIINSTANCE, 	//This datatype is necessary in order to store actual experiences
	EMOTION;
}
