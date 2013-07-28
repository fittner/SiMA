/**
 * CHANGELOG
 *
 * 29.08.2011 deutsch - File created
 *
 */
package statictools;

import com.sun.j3d.utils.universe.SimpleUniverse;
/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 29.08.2011, 17:21:38
 * 
 */
public class cls3DUniverse {
    private static cls3DUniverse instance = null;
    private SimpleUniverse moUniverse = null;
    
    private cls3DUniverse() {
    	moUniverse = new SimpleUniverse();
    }
    
    private static cls3DUniverse getInstance() {
       if (instance == null) {
            instance = new cls3DUniverse();
       }
       return instance;
    }

	public static SimpleUniverse getSimpleUniverse() {
		return cls3DUniverse.getInstance().moUniverse;
	}
}
