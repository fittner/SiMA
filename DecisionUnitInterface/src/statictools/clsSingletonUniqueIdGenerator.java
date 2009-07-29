/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package statictools;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsSingletonUniqueIdGenerator {
    /**
     * 
     */
    private static clsSingletonUniqueIdGenerator instance = null;
    
    /**
     * 
     */
    private int mnCounter = 0;
 
    /**
     * 
     */
    private clsSingletonUniqueIdGenerator() {}

    /**
     * DOCUMENT (deutsch) - insert description
     *
     * @return
     */
    private static clsSingletonUniqueIdGenerator getInstance() {
       if (instance == null) {
            instance = new clsSingletonUniqueIdGenerator();
       }
       return instance;
    }

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public static int getUniqueId() {
		return clsSingletonUniqueIdGenerator.getInstance().mnCounter++;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public static int getCurrentUniqueId() {
		return clsSingletonUniqueIdGenerator.getInstance().mnCounter;
	}
	
}
