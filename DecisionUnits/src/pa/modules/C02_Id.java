/**
 * C02_ID.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:10:40
 */
package pa.modules;

import pa.interfaces.I1_2;
import pa.interfaces.I1_4;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:10:40
 * 
 */
public class C02_Id extends clsModuleContainer implements
								I1_2,
								I1_4
								{
	public static final String P_E15 = "E15";
	public static final String P_C05 = "C05";
	public static final String P_C06 = "C06";
	
	public C05_DriveHandling moC05DriveHandling;
	public C06_AffectGeneration moC06AffectGeneration;
	public E15_ManagementOfRepressedContents moE15ManagementOfRepressedContents;

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 11.08.2009, 15:11:47
	 *
	 * @param poPrefix
	 * @param poProp
	 * @param poEnclosingContainer
	 */
	public C02_Id(String poPrefix, clsBWProperties poProp,
			clsModuleContainer poEnclosingContainer) {
		super(poPrefix, poProp, poEnclosingContainer);
		applyProperties(poPrefix, poProp);
	}
	
	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( C05_DriveHandling.getDefaultProperties(pre+P_C05) );
		oProp.putAll( C06_AffectGeneration.getDefaultProperties(pre+P_C06) );
		oProp.putAll( E15_ManagementOfRepressedContents.getDefaultProperties(pre+P_E15) );		
				
		return oProp;
	}	
	
	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
	
		moC05DriveHandling = new C05_DriveHandling(pre+P_C05, poProp, this);
		moC06AffectGeneration = new C06_AffectGeneration(pre+P_C06, poProp, this);
		moE15ManagementOfRepressedContents = new E15_ManagementOfRepressedContents(pre+P_E15, poProp, this);
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:19:49
	 * 
	 * @see pa.interfaces.I1_2#receive_I1_2(int)
	 */
	@Override
	public void receive_I1_2(int pnData) {
		moC05DriveHandling.receive_I1_2(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:39:00
	 * 
	 * @see pa.interfaces.I1_4#receive_I1_4(int)
	 */
	@Override
	public void receive_I1_4(int pnData) {
		moC06AffectGeneration.receive_I1_4(pnData);
		
	}

}
