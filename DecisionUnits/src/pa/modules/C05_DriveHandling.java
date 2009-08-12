/**
 * C05_DriveHandling.java: DecisionUnits - pa.modules
 * 
 * @author deutsch
 * 11.08.2009, 15:28:05
 */
package pa.modules;

import pa.interfaces.I1_2;
import pa.interfaces.I1_3;
import pa.interfaces.I1_4;
import pa.memory.clsMemory;
import config.clsBWProperties;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 11.08.2009, 15:28:05
 * 
 */
public class C05_DriveHandling extends clsModuleContainer implements
							I1_2,
							I1_3,
							I1_4
							{
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
			clsModuleContainer poEnclosingContainer, clsMemory poMemory) {
		super(poPrefix, poProp, poEnclosingContainer, poMemory);
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

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:20:42
	 * 
	 * @see pa.interfaces.I1_2#receive_I1_2(int)
	 */
	@Override
	public void receive_I1_2(int pnData) {
		moE03GenerationOfDrives.receive_I1_2(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:35:25
	 * 
	 * @see pa.interfaces.I1_3#receive_I1_3(int)
	 */
	@Override
	public void receive_I1_3(int pnData) {
		moE04FusionOfDrives.receive_I1_3(pnData);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 11.08.2009, 16:38:37
	 * 
	 * @see pa.interfaces.I1_4#receive_I1_4(int)
	 */
	@Override
	public void receive_I1_4(int pnData) {
		((I1_4)moEnclosingContainer).receive_I1_4(pnData);
		
	}

}
