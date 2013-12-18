/**
 * eProcessType.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 12:03:37
 */
package base.modules;

/**
 * Defines how the data of the functional module is organized. 
 * 
 * @author deutsch
 * 14.07.2011, 11:34:45
 * 
 */
public enum eProcessType {
	/** Symbols defined according to the primary process - unorderd thing presentations.; @since 14.07.2011 11:35:16 */
	PRIMARY,
	/** Symbolds defined according to the secondary process - word presentations with associations to thing presentations.; @since 14.07.2011 11:35:41 */
	SECONDARY,
	/** Scalar values that represent electro/chemical values from within the body.; @since 14.07.2011 11:36:04 */
	BODY
}
