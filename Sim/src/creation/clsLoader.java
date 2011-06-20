/**
 * CHANGELOG
 * 
 * 2011/06/20 TD - added some javadoc
 */
package creation;

import config.clsBWProperties;
import bw.factories.clsSingletonProperties;
import bw.factories.clsSingletonMasonGetter;
import sim.engine.SimState;
import sim.field.continuous.Continuous2D;
import sim.physics2D.PhysicsEngine2D;
import statictools.clsGetARSPath;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 25.02.2009, 14:00:51
 * 
 */
public abstract class clsLoader {
	/** the width of the game field */
	public static final String P_FIELD_WIDTH = "field_width";
	/** the height of the game field */
	public static final String P_FIELD_HEIGHT = "field_height";
	/** the title of the setup */
	public static final String P_TITLE = "title";
	/** what is this setup all about */
	public static final String P_SHORTDESC = "short_description";
	/** a long version of what is this setup all about */
	public static final String P_DESCRIPTION = "description";
	/** url to a website. e.g. http://ars.ict.tuwien.ac.at */
	public static final String P_LINK = "url";
	/** icon/picture that represents the setup */
	public static final String P_IMAGE = "image";
	/** which loader to use (see eLoader) */
	public static final String P_LOADER_TYPE = "loader_type";
	/** the version of the loader */
	public static final String P_LOADER_VERSION = "loader_version";
	
	private clsBWProperties moProperties;
	private static final String moPrefix = "";
	private String moTitle;
	private String moDescription;
	private String moShortDesc;	
	private String moImageUrl;
	private String moLinkUrl;

	
    /**
     * Prepares the MASON simulation environment for the to be loaded entities.
     *
     * @since 20.06.2011 18:36:57
     *
     * @param poSimState
     * @param poProperties
     */
    public clsLoader(SimState poSimState, clsBWProperties poProperties) {
    	moProperties = poProperties;  	
    	clsSingletonProperties.setProperties(moProperties);    
    	
    	createPhysicsEngine2D();
		clsSingletonMasonGetter.setSimState(poSimState);
		clsSingletonMasonGetter.getSimState().schedule.scheduleRepeating(clsSingletonMasonGetter.getPhysicsEngine2D());		

		applyProperties(moPrefix, moProperties);
		
		checkVersionCompatibility(moPrefix, moProperties);
		verifyLoaderType(moPrefix, moProperties);
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
		
		moTitle = poProp.getPropertyString(pre+P_TITLE);
		moShortDesc = poProp.getPropertyString(pre+P_SHORTDESC);
		moDescription = poProp.getPropertyString(pre+P_DESCRIPTION);
		moImageUrl = poProp.getPropertyString(pre+P_IMAGE);
		
		try {
			moLinkUrl = poProp.getPropertyString(pre+P_LINK);
		} catch (java.lang.NullPointerException e) {
			moLinkUrl = "";
		}
		
		createGrids(pre, poProp);
	}	
	
	/**
	 * Checks if the version number of the implemented loader is compatible with the version number given in the
	 * property file.
	 *
	 * @since 20.06.2011 18:38:38
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	protected abstract void checkVersionCompatibility(String poPrefix, clsBWProperties poProp);
	
	/**
	 * Verifies that this is the correct loader for this property file.
	 *
	 * @since 20.06.2011 18:39:11
	 *
	 * @param poPrefix
	 * @param poProp
	 */
	protected abstract void verifyLoaderType(String poPrefix, clsBWProperties poProp);
	
    /**
     * Provides the default entries for this class. See config.clsBWProperties in project DecisionUnitInterface.
     */	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		clsBWProperties oProp = new clsBWProperties();

		oProp.setProperty(pre+P_TITLE, "default title");
		oProp.setProperty(pre+P_SHORTDESC, "loader which loads lots of entities");
		oProp.setProperty(pre+P_DESCRIPTION, "Lorem ipsum dolor sit amet, consectetuer sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet. Lorem ipsum dolor sit amet, consetetur sadipscing elitr, sed diam nonumy eirmod tempor invidunt ut labore et dolore magna aliquyam erat, sed diam voluptua. At vero eos et accusam et justo duo dolores et ea rebum. Stet clita kasd gubergren, no sea takimata sanctus est Lorem ipsum dolor sit amet.");
		oProp.setProperty(pre+P_IMAGE, "/BW/src/resources/images/cake.gif");
		
		oProp.setProperty(pre+P_FIELD_WIDTH, 200.0);
		oProp.setProperty(pre+P_FIELD_HEIGHT, 200.0);
		
		oProp.setProperty(pre+P_LOADER_TYPE, eLoader.UNDEFINED.name());
		oProp.setProperty(pre+P_LOADER_VERSION, -1);
		
		oProp.setProperty(pre+P_LINK, "http://ars.ict.tuwien.ac.at");
		
		return oProp;
	}    
    
	@Deprecated
    private clsBWProperties loadProperties(String poPropertiesFilename) {
    	clsBWProperties oProp = clsBWProperties.readProperties( clsGetARSPath.getConfigPath(), poPropertiesFilename);
    	clsSingletonProperties.setProperties(oProp);    
    	return oProp;
    }
    
	/**
	 * Executes the load and entity creation.
	 *
	 * @since 20.06.2011 18:39:49
	 *
	 */
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
    	double nWidth = poProp.getPropertyDouble(pre+P_FIELD_WIDTH);
    	double nHeight = poProp.getPropertyDouble(pre+P_FIELD_HEIGHT);
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
    
	/**
	 * Creates a random pose (x,y,direction) and sets the value in the property class instance at the given position (P_*).
	 *
	 * @since 20.06.2011 18:40:16
	 *
	 * @param Prefix
	 * @param P_POS_X
	 * @param P_POS_Y
	 * @param P_POS_ANGLE
	 * @return
	 */
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
	
	/**
	 * Returns the title of the configuration. (see P_TITLE)
	 *
	 * @since 20.06.2011 18:41:27
	 *
	 * @return
	 */
	public String getTitle() {
		return moTitle;
	}
	
	/**
	 * Returns the description of the configuration. (see P_DESCRITPION)
	 *
	 * @since 20.06.2011 18:41:53
	 *
	 * @return
	 */
	public String getDescription() {
		return moDescription;
	}
	
	/**
	 * Returns the image path+filename. (see P_IMAGE)
	 *
	 * @since 20.06.2011 18:42:28
	 *
	 * @return
	 */
	public String getImageUrl() {
		return moImageUrl;
	}
	
	/**
	 * Returns the short description of the configuration. (see P_SHORTDESC)
	 *
	 * @since 20.06.2011 18:42:50
	 *
	 * @return
	 */
	public String getShortDesc() {
		return moShortDesc;
	}
	
	/**
	 * Returns the external website url. (see P_LINK)
	 *
	 * @since 20.06.2011 18:43:15
	 *
	 * @return
	 */
	public String getLinkUrl() {
		return moLinkUrl;
	}
	
	/**
	 * Creates an html-page with the title, image, shortdesc, description, and url provided in the config file. 
	 *
	 * @since 20.06.2011 18:43:33
	 *
	 * @return
	 */
	public String getIndexHtml() {
		String html = "";
		
		html += "<html>";
		html += "<head><title>"+getTitle()+"</title></head>";
		html += "<body>";
		html += "<table border=0 cellspacing=0 cellpadding=6>";
		html += "<tr>";
		html += "	<td valign=top>";
		html += "		<img src=\""+clsGetARSPath.getArsPath()+getImageUrl()+"\">";
		html += "	<td valign=top>";
		html += "	<td>";
		html += "		<h2>"+getTitle()+"</h2>"; 
		html += "		<p>"+getShortDesc()+"</p>";
		html += "	</td>";
		html += "</tr>";
		html += "</table>";
		html += "<p>";
		html += getDescription();
		html += "</p>";		
		if (getLinkUrl().length() > 0) {
			html += "<a href=\""+getLinkUrl()+"\" target=_blank>"+getLinkUrl()+"</a>";
		}
		html += "</body>";
		html += "</html>	";
		
		return html;
	}
	
	@Deprecated
	protected void setTitle(String poTitle) {
		moTitle = poTitle;
	}
	@Deprecated
	protected void setShortDesc(String poShortDesc) {
		moShortDesc = poShortDesc;
	}
	@Deprecated
	protected void setDescription(String poDescription) {
		moDescription = poDescription;
	}
	@Deprecated
	protected void setImageUrl(String poImageUrl) {
		moImageUrl = poImageUrl;
	}
	@Deprecated
	protected void setLinkUrl(String poLinkUrl) {
		moLinkUrl = poLinkUrl;
	}
	
	
	/**
	 * Getter for moProperteries (instance of clsBWProperties).
	 *
	 * @since 20.06.2011 18:44:29
	 *
	 * @return
	 */
	protected clsBWProperties getProperties() {
		return moProperties;
	}
	
	/**
	 * Getter for the prefix within the properties.
	 *
	 * @since 20.06.2011 18:44:49
	 *
	 * @return
	 */
	protected String getPrefix() {
		return moPrefix;
	}	
}
