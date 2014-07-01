/**
 * @author deutsch
 * 15.07.2009, 17:36:36
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package singeltons;

import properties.clsProperties;

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
	private boolean mnUseLogger;
	private boolean mnShowArousalGridPortrayal;
	private boolean mnShowTPMNetworkGridPortrayal;
	private boolean mnShowFacialExpressionOverlay;
	private boolean mnShowSpeechQuestionOverlay;
	private boolean mnShowSpeechAnswerOverlay;
	private boolean mnAntiAliasing;

	private boolean mnShowLifeIndicator;

	
	protected clsSingletonProperties() {
		moProperties = new clsProperties();
		moSystemProperties = new clsProperties();
		mnDrawImages = false;
		mnDrawSensors = false;
		mnUseLogger = false;
		mnShowArousalGridPortrayal = false;
		mnShowTPMNetworkGridPortrayal = false;
		mnShowFacialExpressionOverlay = false;
		mnShowSpeechQuestionOverlay = false;
		mnShowLifeIndicator =false;
		mnAntiAliasing=true;
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
	
	static public void setSystemProperties(clsProperties poProperties, String P_DRAWIMAGES, String P_DRAWSENSORS, String P_USELOGGER) {
		(clsSingletonProperties.instance()).moSystemProperties = poProperties;
		(clsSingletonProperties.instance()).mnDrawImages = poProperties.getPropertyBoolean(P_DRAWIMAGES);
		(clsSingletonProperties.instance()).mnDrawSensors = poProperties.getPropertyBoolean(P_DRAWSENSORS);
		(clsSingletonProperties.instance()).mnUseLogger = poProperties.getPropertyBoolean(P_USELOGGER);

		//wenn wir die leute zwingen wollen das in ihren config zu haben.. wieder einkommentieren
	}	
	
	static public boolean drawImages() {
		return (clsSingletonProperties.instance()).mnDrawImages;
	}
	
	static public boolean drawSensors() {
		return (clsSingletonProperties.instance()).mnDrawSensors;
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
	
	static public boolean showSpeechAnswerOverlay() {
		return (clsSingletonProperties.instance()).mnShowSpeechAnswerOverlay;
	}
	
	static public void setShowSpeechAnswerOverlay(boolean value) {
		(clsSingletonProperties.instance()).mnShowSpeechAnswerOverlay = value;
	}
	
	static public boolean showSpeechQuestionOverlay() {
		return (clsSingletonProperties.instance()).mnShowSpeechQuestionOverlay;
	}
	static public void setShowSpeechQuestionOverlay(boolean value) {
		(clsSingletonProperties.instance()).mnShowSpeechQuestionOverlay = value;
	}
	
	
	

	
	static public void setShowLifeIndicator(boolean value) {
		(clsSingletonProperties.instance()).mnShowLifeIndicator = value;
	}
	
	static public boolean showLifeIndicator() {
		return (clsSingletonProperties.instance()).mnShowLifeIndicator;
	}
	
	static public boolean showFacialExpressionOverlay() {
		return (clsSingletonProperties.instance()).mnShowFacialExpressionOverlay;
	}
	
	static public void setShowFacialExpressionOverlay(boolean value) {
		(clsSingletonProperties.instance()).mnShowFacialExpressionOverlay = value;
	}
	
	static public void setAntiAliasing(boolean value) {
		(clsSingletonProperties.instance()).mnAntiAliasing = value;
	}
	
	static public boolean isAntiAliasing() {
		return (clsSingletonProperties.instance()).mnAntiAliasing;
	}
	
	
	
}
