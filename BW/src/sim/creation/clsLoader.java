/**
 * @author deutsch
 * 25.02.2009, 14:00:51
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package sim.creation;

import bw.factories.clsPropertiesGetter;
import bw.factories.clsSingletonMasonGetter;
import bw.utils.config.clsBWProperties;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;
import statictools.clsGetARSPath;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 25.02.2009, 14:00:51
 * 
 */
public abstract class clsLoader {
	public static final String P_FIELD_WIDTH = "field_width";
	public static final String P_FIELD_HEIGHT = "field_height";
	
	protected clsBWProperties moProperties;
	protected static final String moPrefix = "";
	
    public clsLoader(SimState poSimState, clsBWProperties poProperties) {
    	moProperties = poProperties;  	
    	clsPropertiesGetter.setProperties(moProperties);    
    	
    	createPhysicsEngine2D();
		clsSingletonMasonGetter.setSimState(poSimState);
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(clsSingletonMasonGetter.getPhysicsEngine2D());		

		applyProperties(moPrefix, moProperties);
    }

    @Deprecated
    public clsLoader(SimState poSimState, String poPropertiesFilename) {
    	moProperties = loadProperties(poPropertiesFilename);
    	    	
    	createPhysicsEngine2D();
		clsSingletonMasonGetter.setSimState(poSimState);
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(clsSingletonMasonGetter.getPhysicsEngine2D());		
    }    
	
	private void applyProperties(String poPrefix, clsBWProperties poProp){		
		String pre = clsBWProperties.addDot(poPrefix);
		createGrids(pre, poProp);
	}	
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_FIELD_WIDTH, 200.0);
		oProp.setProperty(pre+P_FIELD_HEIGHT, 200.0);
		
		return oProp;
	}    
    
	@Deprecated
    private clsBWProperties loadProperties(String poPropertiesFilename) {
    	clsBWProperties oProp = clsBWProperties.readProperties( clsGetARSPath.getConfigPath(), poPropertiesFilename);
    	clsPropertiesGetter.setProperties(oProp);    
    	return oProp;
    }
    
	public abstract void loadObjects();
	
	private void createPhysicsEngine2D() {
		clsSingletonMasonGetter.setPhysicsEngine2D(new PhysicsEngine2D());
	}


    private void createGrids(String poPrefix, clsBWProperties poProp)
    {
    	String pre = clsBWProperties.addDot(poPrefix);
    	/**
    	 * Continuous2D is a Field: a representation of space. In particular, Continuous2D 
    	 * represents continuous 2-dimensional space it is actually infinite: the width 
    	 * and height are just for GUI guidelines (starting size of the window). */
    	int nWidth = poProp.getPropertyInt(pre+P_FIELD_WIDTH);
    	int nHeight = poProp.getPropertyInt(pre+P_FIELD_HEIGHT);
    	clsSingletonMasonGetter.setFieldEnvironment(new Continuous2D(25, nWidth, nHeight));
    }	
    
    @Deprecated
    protected void createGrids(int pnWidth, int pnHeight)
    {
    	/**
    	 * Continuous2D is a Field: a representation of space. In particular, Continuous2D 
    	 * represents continuous 2-dimensional space it is actually infinite: the width 
    	 * and height are just for GUI guidelines (starting size of the window). */
    	
    	clsSingletonMasonGetter.setFieldEnvironment(new Continuous2D(25, pnWidth, pnHeight));
    }	
    
	public static clsBWProperties generateRandomPose(String Prefix, String P_POS_X, String P_POS_Y, String P_POS_ANGLE) {
		String pre = clsBWProperties.addDot(Prefix);
		
		double xMax = clsSingletonMasonGetter.getFieldEnvironment().getWidth();
		double yMax = clsSingletonMasonGetter.getFieldEnvironment().getHeight();
		
        double xStartPos = Math.max(Math.min(clsSingletonMasonGetter.getSimState().random.nextDouble() * xMax, xMax - 20), 20);
        double yStartPos = Math.max(Math.min(clsSingletonMasonGetter.getSimState().random.nextDouble() * yMax, yMax - 20), 50);
        double rAngle = clsSingletonMasonGetter.getSimState().random.nextDouble() * Math.PI * 2;
        
    	clsBWProperties oProp = new clsBWProperties();
        oProp.setProperty(pre+P_POS_X, xStartPos);
        oProp.setProperty(pre+P_POS_Y, yStartPos);
        oProp.setProperty(pre+P_POS_ANGLE, rAngle);
        
        return oProp;		
	}    
}
