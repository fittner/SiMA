/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package utils;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsUniqueIdGenerator {
    /**
     * 
     */
    private static clsUniqueIdGenerator instance = null;
    
    /**
     * 
     */
    private int mnCounter = 0;
 
    /**
     * 
     */
    private clsUniqueIdGenerator() {}

    /**
     * DOCUMENT (deutsch) - insert description
     *
     * @return
     */
    private static clsUniqueIdGenerator getInstance() {
       if (instance == null) {
            instance = new clsUniqueIdGenerator();
       }
       return instance;
    }

	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public static int getUniqueId() {
		return clsUniqueIdGenerator.getInstance().mnCounter++;
	}
	
	/**
	 * DOCUMENT (deutsch) - insert description
	 *
	 * @return
	 */
	public static int getCurrentUniqueId() {
		return clsUniqueIdGenerator.getInstance().mnCounter;
	}
	
}
