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
public class clsLifeInstinctRatio {
	private double mrOral;
	private double mrAnal;
	private double mrGenital;
	private double mrPhallic;
	
	public clsLifeInstinctRatio(double prOral, double prAnal, double prGenital, double prPhallisch) {
		setRatio(prOral, prAnal, prGenital, prPhallisch);
	}
	
	public void setRatio(double prOral, double prAnal, double prGenital, double prPhallisch) {
		mrOral = low_cut_filter(prOral);
		mrAnal = low_cut_filter(prAnal);
		mrGenital = low_cut_filter(prGenital);
		mrPhallic = low_cut_filter(prPhallisch);
		
		double rSum = mrAnal + mrOral + mrGenital + mrPhallic;
		
		if (rSum == 0) {
			throw new java.lang.IllegalArgumentException("sum of all for values has to be larger than 0");
		}
		
		mrOral = normalize(prOral, rSum);
		mrAnal = normalize(prAnal, rSum);
		mrGenital = normalize(prGenital, rSum);
		mrPhallic = normalize(prPhallisch, rSum);		
	}
	
	private double normalize(double prValue, double prNorm) {
		return prValue / prNorm;
	}
	
	private double low_cut_filter(double prValue) {
		if (prValue < 0) {
			prValue = 0;
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
	
	public double distance(clsLifeInstinctRatio poOther) {
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
		
		oResult = "oral: "+mrOral+" / anal: "+mrAnal+" / genital: "+mrGenital+" / phallic: "+mrPhallic;
		
		return oResult;
	}
}
