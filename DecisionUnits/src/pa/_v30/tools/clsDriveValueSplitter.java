/**
 * clsDriveValueSplitter.java: DecisionUnits - pa._v30.tools
 * 
 * @author deutsch
 * 21.04.2011, 00:00:25
 */
package pa._v30.tools;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 21.04.2011, 00:00:25
 * 
 */
public class clsDriveValueSplitter {
	public static clsPair<Double, Double> calc(Double prA, Double prB, eDriveValueSplitter peDVS, Object... poParams) {
		clsPair<Double, Double> oRes = null;
		
		switch (peDVS) {
			case SIMPLE:   oRes = calcSplitValueSimple(prA, prB);
				break;
			case ADVANCED: oRes = calcSplitValueAdvanced(prA, prB, (Double) poParams[0]);
				break;
		}
		
		oRes.a = normalize(oRes.a);
		oRes.b = normalize(oRes.b);
				
		return oRes;
	}
	
	private static double normalize(double r) {
		if (r>1) {
			r=1;
		} else if (r<0) {
			r=0;
		}
		return r;
	}

	private static clsPair<Double, Double> calcSplitValueAdvanced(Double prA, Double prB, Double prFactor) {
		double rA = 0.0;
		double rB = 0.0;
		
		rA  = Math.sin(Math.PI*prA);
		rB = (2-(Math.cos(Math.PI*prB)+1))*0.5;		
		
		return new clsPair<Double, Double>(rA, rB);
	} 	
	
	private static clsPair<Double, Double> calcSplitValueSimple(Double prA, Double prB) {
		return calcSplitValueAdvanced(prA, prB, 0.5);
	}
}
