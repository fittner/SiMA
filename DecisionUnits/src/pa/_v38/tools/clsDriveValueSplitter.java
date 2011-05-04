/**
 * clsDriveValueSplitter.java: DecisionUnits - pa._v38.tools
 * 
 * @author deutsch
 * 21.04.2011, 00:00:25
 */
package pa._v38.tools;

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
		//TD 2011/05/01 - it should never happen that r is outside the range 0,1 ... but just in case. maybe add throw exception?
		if (r>1) {
			r=1;
		} else if (r<0) {
			r=0;
		}
		return r;
	}

	private static clsPair<Double, Double> calcSplitValueAdvanced(Double prA, Double prB, Double prFactor) {
		double rA = shiftInput(prA, prFactor);
		double rB = shiftInput(prB, prFactor);
				
		rA  = Math.sin(Math.PI*rA);
		rB = (2-(Math.cos(Math.PI*rB)+1))*0.5;		
		
		return new clsPair<Double, Double>(rA, rB);
	} 	
	
	private static double shiftInput(double r, double f) {
		//TD 2011/05/01 - this function shifts the input values. the original value 0.5 is shifted to f. the interval 0,0.5 is extended
		// and the interval 0.5,1 is compressed to fit into the new ares (in case f>0.5. for f<0.5 its the other way round).
		//it is not the nicest approach, but it works.
		if (r<=0.5) {		//x=r*f/0.5 = r*f*2
			r = r*f*2;
		} else {		//=2*(f*(1-r)+r)-1
			r = 2*(f*(1-r)+r)-1;
		}		
		
		return r;
	}
	
	private static clsPair<Double, Double> calcSplitValueSimple(Double prA, Double prB) {
		return calcSplitValueAdvanced(prA, prB, 0.5);
	}
}
