/**
 * @author deutsch
 * 25.02.2009, 15:22:23
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.factories;

import sim.physics2D.util.Angle;
import ARSsim.physics2D.physicalObject.clsMobileObject2D;
import bw.entities.clsEntity;
import bw.entities.clsMobile;
import bw.entities.clsStationary;


/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 25.02.2009, 15:22:23
 * 
 */
public final class clsRegisterEntity {

	public static void registerMobile(clsMobile poMobile) {
	}
	
	public static void registerStationary(clsStationary poStationary) {
	}
	
	public static void registerEntity(clsEntity poEntity) {
		if (poEntity instanceof clsMobile ) {
			registerMobile((clsMobile)poEntity);
		} else {
			registerStationary((clsStationary)poEntity);
		}
	}
}
