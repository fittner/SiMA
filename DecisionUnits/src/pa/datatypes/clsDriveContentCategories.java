/**
 * clsLifeInstinctRatio.java: DecisionUnits - pa.datatypes
 * 
 * @author langr
 * 28.09.2009, 16:52:49
 */
package pa.datatypes;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 28.09.2009, 16:52:49
 * 
 */
@Deprecated
public class clsDriveContentCategories implements Cloneable {
	//TODO: (ALL) probably better to change to cls0to1() !!!
	private double mrOral;
	private double mrAnal;
	private double mrGenital;
	private double mrPhallic;
	
	public clsDriveContentCategories(double prOral, double prAnal, double prGenital, double prPhallic) {
		setRatio(prOral, prAnal, prGenital, prPhallic);
	}
	
	/**
	 * DOCUMENT (langr) - insert description 
	 * 
	 * @author langr
	 * 18.10.2009, 11:48:11
	 *
	 * @param clsDriveContentCategories
	 */
	public clsDriveContentCategories(
			clsDriveContentCategories poDrvContCat) {
		setRatio(poDrvContCat.mrOral, poDrvContCat.mrAnal, poDrvContCat.mrGenital, poDrvContCat.mrPhallic);
	}

	public void setRatio(double prOral, double prAnal, double prGenital, double prPhallic) {
		mrOral = low_cut_filter(prOral);
		mrAnal = low_cut_filter(prAnal);
		mrGenital = low_cut_filter(prGenital);
		mrPhallic = low_cut_filter(prPhallic);
		
		//we want objects with e.g. 100% oral AND 100% Phallic!
//		double rSum = mrAnal + mrOral + mrGenital + mrPhallic;
//		
//		if (rSum == 0) {
//			throw new java.lang.IllegalArgumentException("sum of all four values has to be larger than 0");
//		}
//		
//		mrOral = normalize(prOral, rSum);
//		mrAnal = normalize(prAnal, rSum);
//		mrGenital = normalize(prGenital, rSum);
//		mrPhallic = normalize(prPhallisch, rSum);
		
		mrOral = up_cut_filter(prOral);
		mrAnal = up_cut_filter(prAnal);
		mrGenital = up_cut_filter(prGenital);
		mrPhallic = up_cut_filter(prPhallic);
	}
	
	public void adaptToContextRatio(double poContextRatio) {
		setRatio(mrOral*poContextRatio, mrAnal*poContextRatio, mrGenital*poContextRatio, mrPhallic*poContextRatio);
	}
	
//	private double normalize(double prValue, double prNorm) {
//		return prValue / prNorm;
//	}
	
	private double low_cut_filter(double prValue) {
		if (prValue < 0) {
			prValue = 0;
		}
		
		return prValue;
	}
	
	private double up_cut_filter(double prValue) {
		if (prValue > 1) {
			prValue = 1;
		}
		
		return prValue;
	}
	
	public double getOral() {
		return mrOral;
	}
	
	public double getAnal() {
		return mrAnal;
	}
	
	public double getGenital() {
		return mrGenital;
	}
	
	public double getPhallic() {
		return mrPhallic;
	}
	
	public double distance(clsDriveContentCategories poOther) {
		double rResult = 0;
		
		double rDiOral = Math.abs(mrOral - poOther.mrOral);
		double rDiAnal = Math.abs(mrAnal - poOther.mrAnal);
		double rDiGenital = Math.abs(mrGenital - poOther.mrGenital);
		double rDiPhallic = Math.abs(mrPhallic - poOther.mrPhallic);
		
		rResult = rDiOral+rDiAnal+rDiGenital+rDiPhallic;
		
		return rResult;
	}
	
	@Override
	public String toString() {
		String oResult = "";
		
		oResult = "o:"+mrOral+"; a:"+mrAnal+"; g:"+mrGenital+"; p:"+mrPhallic;
		
		return oResult;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsDriveContentCategories oClone = (clsDriveContentCategories)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}

	/**
	 * gives the match in percent (0 to 1)
	 *
	 * @author langr
	 * 18.10.2009, 12:35:37
	 *
	 * @param value
	 * @return
	 */
	public double match(clsDriveContentCategories poValue) {
		double rRetVal = 1.0-(distance(poValue) / 4.0);
		return rRetVal;
	}
}
