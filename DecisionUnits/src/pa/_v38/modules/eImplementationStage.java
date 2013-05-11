/**
 * eModuleProcessLevel.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 12.07.2010, 10:08:36
 */
package pa._v38.modules;


/**
 * The three different implementation stages. The three stages are: basic, draft, and final. Basic is a first 
 * implementation that provides minimum functionality. Especially it guarantees the modules that are called after this module have at least some
 * data to process. Draft is the implementation stage where alpha and beta stage implementations should be done. If everything is done and fits the 
 * specs, the implementation is moved to final.
 * in impstage file this should look like this:
 * processor.psychicapparatus.01.IMP_STAGE=BASIC
*	processor.psychicapparatus.02.IMP_STAGE=BASIC
*	processor.psychicapparatus.03.IMP_STAGE=BASIC ...
 * 
 * @see clsModuleBase
 * 
 * @author deutsch
 * 14.07.2011, 11:28:17
 * 
 */
public enum eImplementationStage {
	/** Basic is a first implementation that provides minimum functionality.; @since 14.07.2011 11:29:41 */
	BASIC,
	/** Draft is the implementation stage where alpha and beta stage implementations should be done.; @since 14.07.2011 11:30:02 */
	DRAFT, 
	/** If everything is done and fits the specs, the implementation is moved to final.; @since 14.07.2011 11:30:07 */
	FINAL,
}
