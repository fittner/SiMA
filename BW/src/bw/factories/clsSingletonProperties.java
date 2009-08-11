/**
 * @author deutsch
 * 15.07.2009, 17:36:36
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.factories;

import sim.clsBWMainWithUI;
import bw.utils.config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 15.07.2009, 17:36:36
 * 
 */
public class clsSingletonProperties {
	private clsBWProperties moProperties; 
	private clsBWProperties moSystemProperties;
	private boolean mnDrawImages;
	private boolean mnDrawSensors;
	
	protected clsSingletonProperties() {
		moProperties = new clsBWProperties();
		moSystemProperties = new clsBWProperties();
		mnDrawImages = false;
		mnDrawSensors = false;
	}
	
	static private clsSingletonProperties _instance = null;
	
	static public clsSingletonProperties instance() {
		if (null == _instance) {
			_instance = new clsSingletonProperties();
		}
		return _instance;
	}
	
	static public clsBWProperties getProperties() {
		return (clsSingletonProperties.instance()).moProperties;
	}
	
	static public void setProperties(clsBWProperties poProperties) {
		(clsSingletonProperties.instance()).moProperties = poProperties;
	}

	static public clsBWProperties getSystemProperties() {
		return (clsSingletonProperties.instance()).moSystemProperties;
	}
	
	static public void setSystemProperties(clsBWProperties poProperties) {
		(clsSingletonProperties.instance()).moSystemProperties = poProperties;
		(clsSingletonProperties.instance()).mnDrawImages = poProperties.getPropertyBoolean(clsBWMainWithUI.P_DRAWIMAGES);
		(clsSingletonProperties.instance()).mnDrawSensors = poProperties.getPropertyBoolean(clsBWMainWithUI.P_DRAWSENSORS);
	}	
	
	static public boolean drawImages() {
		return (clsSingletonProperties.instance()).mnDrawImages;
	}
	
	static public boolean drawSensors() {
		return (clsSingletonProperties.instance()).mnDrawSensors;
	}
}
