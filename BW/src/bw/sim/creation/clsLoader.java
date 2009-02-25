/**
 * @author deutsch
 * 25.02.2009, 14:00:51
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.sim.creation;

import bw.factories.clsSingletonMasonGetter;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 25.02.2009, 14:00:51
 * 
 */
public abstract class clsLoader {
	
    public clsLoader(SimState poSimState, int pnWidth, int pnHeight) {
    	createGrids(pnWidth, pnHeight);
    	
    	createPhysicsEngine2D();
    	
		clsSingletonMasonGetter.setSimState(poSimState);
		
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(clsSingletonMasonGetter.getPhysicsEngine2D());		
    }
    
    
    
	public abstract void loadObjects();
	
	private void createPhysicsEngine2D() {
		clsSingletonMasonGetter.setPhysicsEngine2D(new PhysicsEngine2D());
	}

    private void createGrids(int pnWidth, int pnHeight)
    {
    	/**
    	 * Continuous2D is a Field: a representation of space. In particular, Continuous2D 
    	 * represents continuous 2-dimensional space it is actually infinite: the width 
    	 * and height are just for GUI guidelines (starting size of the window). */
    	
    	clsSingletonMasonGetter.setFieldEnvironment(new Continuous2D(25, pnWidth, pnHeight));
    }	
}
