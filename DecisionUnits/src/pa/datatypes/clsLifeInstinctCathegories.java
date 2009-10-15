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
public class clsLifeInstinctCathegories implements Cloneable {
	private double mrOral;
	private double mrAnal;
	private double mrGenital;
	private double mrPhallic;
	
	public clsLifeInstinctCathegories(double prOral, double prAnal, double prGenital, double prPhallisch) {
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
	
	public double distance(clsLifeInstinctCathegories poOther) {
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
        	clsLifeInstinctCathegories oClone = (clsLifeInstinctCathegories)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
