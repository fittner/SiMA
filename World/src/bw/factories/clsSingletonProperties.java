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
	
	protected clsSingletonProperties() {
		moProperties = new clsProperties();
		moSystemProperties = new clsProperties();
		mnDrawImages = false;
		mnDrawSensors = false;
		mnUse3DPerception = false;
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
	
	static public void setSystemProperties(clsProperties poProperties, String P_DRAWIMAGES, String P_DRAWSENSORS, String P_USE3DPERCEPTION) {
		(clsSingletonProperties.instance()).moSystemProperties = poProperties;
		(clsSingletonProperties.instance()).mnDrawImages = poProperties.getPropertyBoolean(P_DRAWIMAGES);
		(clsSingletonProperties.instance()).mnDrawSensors = poProperties.getPropertyBoolean(P_DRAWSENSORS);
		(clsSingletonProperties.instance()).mnUse3DPerception = poProperties.getPropertyBoolean(P_USE3DPERCEPTION);
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
}
