/**
 * C05_DriveHandling.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:28:05
 */
package pa.modules;

import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:28:05
 * 
 */
public class C05_DriveHandling extends clsModuleContainer {
	public static final String P_E03 = "E03";
	public static final String P_E04 = "E04";
	
	public E03_GenerationOfDrives moE03GenerationOfDrives;
	public E04_FusionOfDrives moE04FusionOfDrives;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:36:39
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public C05_DriveHandling(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( E03_GenerationOfDrives.getDefaultProperties(pre+P_E03) );
		oProp.putAll( E04_FusionOfDrives.getDefaultProperties(pre+P_E04) );
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moE03GenerationOfDrives = new E03_GenerationOfDrives(pre+P_E03, poProp, this);
		moE04FusionOfDrives = new E04_FusionOfDrives(pre+P_E04, poProp, this);
	}

}
