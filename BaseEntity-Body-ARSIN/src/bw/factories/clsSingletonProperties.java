/**
 * @author deutsch
 * 15.07.2009, 17:36:36
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.factories;

import config.clsProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.07.2009, 17:36:36
 * 
 */
public class clsSingletonProperties {
	private clsProperties moProperties; 
	private clsProperties moSystemProperties;
	private boolean mnDrawImages;
	private boolean mnDrawSensors;
	private boolean mnUse3DPerception;
	private boolean mnUseLogger;
	private boolean mnShowArousalGridPortrayal;
	private boolean mnShowTPMNetworkGridPortrayal;
	private boolean mnShowFacialExpressionOverlay;
	private boolean mnShowSpeechExpressionOverlay;
	private boolean mnShowThoughtExpressionOverlay;
	
	protected clsSingletonProperties() {
		moProperties = new clsProperties();
		moSystemProperties = new clsProperties();
		mnDrawImages = false;
		mnDrawSensors = false;
		mnUse3DPerception = false;
		mnUseLogger = false;
		mnShowArousalGridPortrayal = false;
		mnShowTPMNetworkGridPortrayal = false;
		mnShowFacialExpressionOverlay = false;
		mnShowSpeechExpressionOverlay = false;
	}
	
	static private clsSingletonProperties _instance = null;
	
	
	static public clsSingletonProperties instance() {
		if (null == _instance) {
			_instance = new clsSingletonProperties();
		}
		return _instance;
	}
	
	static public clsProperties getProperties() {
		return (clsSingletonProperties.instance()).moProperties;
	}
	
	static public void setProperties(clsProperties poProperties) {
		(clsSingletonProperties.instance()).moProperties = poProperties;
	}

	static public clsProperties getSystemProperties() {
		return (clsSingletonProperties.instance()).moSystemProperties;
	}
	
	static public void setSystemProperties(clsProperties poProperties, String P_DRAWIMAGES, String P_DRAWSENSORS, String P_USE3DPERCEPTION, String P_USELOGGER) {
		(clsSingletonProperties.instance()).moSystemProperties = poProperties;
		(clsSingletonProperties.instance()).mnDrawImages = poProperties.getPropertyBoolean(P_DRAWIMAGES);
		(clsSingletonProperties.instance()).mnDrawSensors = poProperties.getPropertyBoolean(P_DRAWSENSORS);
		(clsSingletonProperties.instance()).mnUse3DPerception = poProperties.getPropertyBoolean(P_USE3DPERCEPTION);
		(clsSingletonProperties.instance()).mnUseLogger = poProperties.getPropertyBoolean(P_USELOGGER);

		//wenn wir die leute zwingen wollen das in ihren config zu haben.. wieder einkommentieren
	}	
	
	static public boolean drawImages() {
		return (clsSingletonProperties.instance()).mnDrawImages;
	}
	
	static public boolean drawSensors() {
		return (clsSingletonProperties.instance()).mnDrawSensors;
	}
	
	/** Use this boolean to prevent the 3D View of the perception to open, if this is set to true Java3D has to be properly installed
	 */
	static public boolean use3DPerception() {
		return (clsSingletonProperties.instance()).mnUse3DPerception;
	}
	
	static public boolean useLogger() {
		return (clsSingletonProperties.instance()).mnUseLogger;
	}
	
	static public boolean showArousalGridPortrayal() {
		return (clsSingletonProperties.instance()).mnShowArousalGridPortrayal;
	}
	static public void setShowArousalGridPortrayal(boolean value) {
		(clsSingletonProperties.instance()).mnShowArousalGridPortrayal = value;
		clsSingletonMasonGetter.getArousalGridEnvironment().setTo(0.0);
	}
	
	static public boolean showTPMNetworkGrid() {
		return (clsSingletonProperties.instance()).mnShowTPMNetworkGridPortrayal;
	}
	static public void setShowTPMNetworkGrid(boolean value) {
		(clsSingletonProperties.instance()).mnShowTPMNetworkGridPortrayal = value;
		clsSingletonMasonGetter.getTPMNetworkField().clear();
		clsSingletonMasonGetter.getTPMNodeField().clear();
	}
	
	static public boolean showFacialExpressionOverlay() {
		return (clsSingletonProperties.instance()).mnShowFacialExpressionOverlay;
	}
	
	static public boolean showSpeechExpressionOverlay() {
		return (clsSingletonProperties.instance()).mnShowSpeechExpressionOverlay;
	}
	
	static public boolean showThoughtExpressionOverlay() {
		return (clsSingletonProperties.instance()).mnShowSpeechExpressionOverlay;
	}
	
	
	static public void setShowFacialExpressionOverlay(boolean value) {
		(clsSingletonProperties.instance()).mnShowFacialExpressionOverlay = value;
	}
	
	static public void setShowSpeechExpressionOverlay(boolean value) {
		(clsSingletonProperties.instance()).mnShowSpeechExpressionOverlay = value;
	}

	static public void setShowThoughtExpressionOverlay(boolean value) {
		(clsSingletonProperties.instance()).mnShowThoughtExpressionOverlay = value;
	}
	
	

	public static void setShowCarriedItem(boolean value) {
		
	}
	
	
}
