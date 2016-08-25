package properties;

import java.util.BitSet;

/**
 * clsImplementationVariant is a specialization of the java.util.BitSet class. 
 * 
 * It is a property which defines in a bitmap the code-parts which should be executed. A certain implementation variant
 * might be set or not as a certain bit in the BitSet and thus indicates, whether a certain part of code has to be executed
 * or not.
 * 
 * The Implementation Variant can be set as property IMP_VARIANT in the file pa.implementationstate.<username>.
 * 
 * Currently specified variants:
 * Bit 1:	Decision Making as designed by Alexander Wendt
 * Bit 2:	WPM processing by Matthias Jakubec
 * 
 * Default value is 1. This is executed, if no implementation variant is specified.
 * 
 * @author jakubec
 * 2015-12-17
 * 
 */
public final class clsImplementationVariant extends BitSet
{

	private static final long serialVersionUID = 1L;
	
	/** Contains the property key for the implementation variant to be set in the file pa.implementationstate.<user>. */
	public static final String P_IMPLEMENTATIONVARIANT  = "IMP_VARIANT";
	public static final int implementationVariantTotal = 255;
	public static final int implementationVariantAW = 1; // Code of Alexander Wendt to be executed.
	public static final int implementationVariantMJ = 2; // Code of Matthias Jakubec to be executed.
	public static final int implementationVariantDefault = implementationVariantAW;
	
	private static clsImplementationVariant soGlobalImplementationVariant = null;
	

	/**
	 * Creates an object of type ImplementationVariant.
	 * It may be used under access level private only.
	 */
	private clsImplementationVariant() {
				
	}
	

	/**
	 * Creates an object of type ImplementationVariant if such an object doesn't exist already.
	 * The object is created with the default bit setting
	 * and stored in the class static variable soGlobalImplementationVariant.
	 */
	public static void initiate() {
		
		if (soGlobalImplementationVariant == null) {
			soGlobalImplementationVariant = new clsImplementationVariant();
			soGlobalImplementationVariant.setPattern(implementationVariantDefault);
		}
		
	}
	

	/**
	 * Creates an object of type ImplementationVariant if such an object doesn't exist already.
	 * The object is created with the bits of the input parameter pnBitPattern set
	 * and stored in the class static variable soGlobalImplementationVariant.
	 * 
	 * @param pnBitPattern bit pattern to be set.
	 */
	public static void initiate(int pnBitPattern) {
		
		if (soGlobalImplementationVariant == null) {
			soGlobalImplementationVariant = new clsImplementationVariant();
			soGlobalImplementationVariant.setPattern(pnBitPattern);
		}
		
	}
	

	/**
	 * Sets in this ImplementationVariant object the bit pattern indicated by the int pnBitPattern.
	 * 
	 * @param pnBitPattern bit pattern to be set.
	 */
	private void setPattern(int pnBitPattern) {
		
		clear();
		int nCounter = 0;
		for (int nMask = 1; nMask <= pnBitPattern; nMask = nMask << 1) {
			if ((pnBitPattern & nMask) != 0)
				this.set(nCounter);
			nCounter++;
		}
		
	}
	

	/**
	 * Compares whether the bit pattern given in pnBitPattern contains a bit also contained in the property ImplementationVariant.
	 * ImplementationVariant has to be initiated before.
	 * 
	 * @param pnBitPattern bit pattern to be compared.
	 * @return true if the bit pattern given in pnBitPattern contains a bit also contained in the property ImplementationVariant.
	 */
	public static boolean permittedByImplementationVariant(int pnBitPattern) {
		
		clsImplementationVariant oPatternToBeCompared = null;
		
		oPatternToBeCompared = new clsImplementationVariant();
		oPatternToBeCompared.setPattern(pnBitPattern);
		oPatternToBeCompared.and (soGlobalImplementationVariant);
		return !oPatternToBeCompared.isEmpty();
		
	}
}
