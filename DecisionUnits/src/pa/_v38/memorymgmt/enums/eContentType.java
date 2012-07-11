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
	// --- Entities --- //
	ENTITY,
	PI,
	PIMATCH, 		//Content type of perception
	GOALTYPE,
	RI,
	AREASUBSET,
	AFFECT,
	AFFECTLEVEL,
	ACTION,
	GOAL,
	PREDICTION,
	PLAN,
	INTENTION,
	MOMENT,
	EXPECTATION,
	MENTALSITUATION,
	NULLOBJECT,
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
	SUPPORTDSASSOCIATION;
}
