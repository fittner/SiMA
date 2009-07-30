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
public class clsPropertiesGetter {
	private clsBWProperties moProperties; 
	private clsBWProperties moSystemProperties;
	private boolean mnDrawImages;
	
	protected clsPropertiesGetter() {
		moProperties = new clsBWProperties();
		moSystemProperties = new clsBWProperties();
		mnDrawImages = true;
	}
	
	static private clsPropertiesGetter _instance = null;
	
	static public clsPropertiesGetter instance() {
		if (null == _instance) {
			_instance = new clsPropertiesGetter();
		}
		return _instance;
	}
	
	static public clsBWProperties getProperties() {
		return (clsPropertiesGetter.instance()).moProperties;
	}
	
	static public void setProperties(clsBWProperties poProperties) {
		(clsPropertiesGetter.instance()).moProperties = poProperties;
	}

	static public clsBWProperties getSystemProperties() {
		return (clsPropertiesGetter.instance()).moSystemProperties;
	}
	
	static public void setSystemProperties(clsBWProperties poProperties) {
		(clsPropertiesGetter.instance()).moSystemProperties = poProperties;
		(clsPropertiesGetter.instance()).mnDrawImages = poProperties.getPropertyBoolean(clsBWMainWithUI.P_DRAWIMAGES);
	}	
	
	static public boolean drawImages() {
		return (clsPropertiesGetter.instance()).mnDrawImages;
	}
}
