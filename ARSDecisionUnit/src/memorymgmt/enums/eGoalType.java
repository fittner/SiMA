/**
 * CHANGELOG
 *
 * 24.06.2012 wendt - File created
 *
 */
package memorymgmt.enums;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 24.06.2012, 09:03:13
 * 
 */
public enum eGoalType {
	NULLOBJECT,
	SPEECHSOURCE,
	DRIVESOURCE,			//Drives, which origins from the body
	PERCEPTIONALEMOTION,	//Emotions, which origins from perception
	MEMORYFEELING,			//Emotions, which origins from memories e. g. bad bodo
	MEMORYDRIVE,
	PERCEPTIONALDRIVE,		//Normal potential drive satisfier, which are found in the perception 
	EMOTIONSOURCE;
}
