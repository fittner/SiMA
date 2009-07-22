/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

import bw.exceptions.exContentColumnMaxContentExceeded;
import bw.exceptions.exContentColumnMinContentUnderrun;
import bw.exceptions.exValueNotWithinRange;
import bw.utils.config.clsBWProperties;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDecayColumn extends clsContentColumn {
	public static final String P_INCREASERATE = "increaserate";
	public static final String P_DECAYRATE = "decayrate";
	public static final String P_INJECTIONVALUE = "injectionvalue";

	private double mrIncreaseRate;
	private double mrDecayRate;
	private double mrInjectionValue;
	
	private boolean mnIsZero;
	private static final double mrZeroDelta = 0.0001f;
	
	/**
	 * @throws exContentColumnMinContentUnderrun 
	 * @throws exContentColumnMaxContentExceeded 
	 * @throws exValueNotWithinRange 
	 * 
	 */
	public clsDecayColumn() throws exContentColumnMaxContentExceeded, exContentColumnMinContentUnderrun, exValueNotWithinRange {
		super(0.0, 1.0);
		
		this.setIncreaseRate(0.1);
		this.setDecayRate(0.01);
		
		mrInjectionValue = 0.0;
		mnIsZero = false;
		
		checkZero();
	}
	
	
	public clsDecayColumn(String poPrefix, clsBWProperties poProp) throws exValueNotWithinRange {
		super(poPrefix, poProp);
		applyProperties(poPrefix, poProp);
		checkZero();
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.putAll( clsContentColumn.getDefaultProperties(pre) );
		
		oProp.setProperty(pre+P_INCREASERATE, 0.1);
		oProp.setProperty(pre+P_DECAYRATE,  0.1);		
		oProp.setProperty(pre+P_INJECTIONVALUE, 0);
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) throws exValueNotWithinRange {
		String pre = poPrefix;
		if (pre.length()>0) {
			pre = pre+".";
		}
		
		this.setIncreaseRate(poProp.getPropertyDouble(pre+P_INCREASERATE));
		this.setDecayRate(poProp.getPropertyDouble(pre+P_DECAYRATE));
		
		mrInjectionValue = poProp.getPropertyDouble(pre+P_INJECTIONVALUE);		
	}
	
	/**
	 * @param prIncreaseRate
	 * @param prDecayRate
	 * @throws exContentColumnMinContentUnderrun 
	 * @throws exContentColumnMaxContentExceeded 
	 * @throws exValueNotWithinRange 
	 */
	public clsDecayColumn(double prIncreaseRate, double prDecayRate) throws exContentColumnMaxContentExceeded, exContentColumnMinContentUnderrun, exValueNotWithinRange {
		super(0.0, 1.0);

		this.setIncreaseRate(prIncreaseRate);
		this.setDecayRate(prDecayRate);
		
		mrInjectionValue = 0.0;
		mnIsZero = false;
		
		checkZero();
	}

	/**
	 * @param prContent
	 * @param prMaxContent
	 * @param prIncreaseRate
	 * @param prDecayRate
	 * @throws exContentColumnMaxContentExceeded
	 * @throws exContentColumnMinContentUnderrun
	 * @throws exValueNotWithinRange
	 */
	public clsDecayColumn(double prContent, double prMaxContent, double prIncreaseRate, double prDecayRate) throws exContentColumnMaxContentExceeded, exContentColumnMinContentUnderrun, exValueNotWithinRange {
		super(prContent, prMaxContent);

		this.setIncreaseRate(prIncreaseRate);
		this.setDecayRate(prDecayRate);
		
		mrInjectionValue = 0.0;
		mnIsZero = false;		
		
		checkZero();
	}
	
	/**
	 * @return the mrZeroDelta
	 */
	public double getZeroDelta() {
		return mrZeroDelta;
	}

	/**
	 * @return the mnIsZero
	 */
	public boolean isZero() {
		return mnIsZero;
	}

	/**
	 * @return the mrIncreaseRate
	 */
	public double getIncreaseRate() {
		return mrIncreaseRate;
	}

	/**
	 * @param mrIncreaseRate the mrIncreaseRate to set
	 * @throws exValueNotWithinRange 
	 */
	public void setIncreaseRate(double prIncreaseRate) throws exValueNotWithinRange {		
		if (prIncreaseRate < 0.0f || prIncreaseRate > 1.0f) {
			throw new bw.exceptions.exValueNotWithinRange(0.0f, prIncreaseRate, 1.0f);			
		}
		
		this.mrIncreaseRate = prIncreaseRate;
	}

	/**
	 * @return the mrDecayRate
	 */
	public double getDecayRate() {
		return mrDecayRate;
	}

	/**
	 * @param mrDecayRate the mrDecayRate to set
	 * @throws exValueNotWithinRange 
	 */
	public void setDecayRate(double prDecayRate) throws exValueNotWithinRange {
		if (prDecayRate < 0.0f || prDecayRate > 1.0f) {
			throw new bw.exceptions.exValueNotWithinRange(0.0f, prDecayRate, 1.0f);			
		}
		this.mrDecayRate = prDecayRate;
	}


	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prAmount
	 * @throws exValueNotWithinRange
	 */
	public void inject(double prAmount) throws exValueNotWithinRange {
		if (prAmount < 0.0f || prAmount > this.getMaxContent()) {
			throw new bw.exceptions.exValueNotWithinRange(0.0f, prAmount, this.getMaxContent());
		}
		setInjectionValue(mrInjectionValue + prAmount);
		mnIsZero = false;
	}
	
	/**
	 * @return the mrInjectionValue
	 */
	public double getInjectionValue() {
		return mrInjectionValue;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prInjectionValue
	 * @throws exValueNotWithinRange
	 */
	public void setInjectionValue(double prInjectionValue) throws exValueNotWithinRange {
		if (prInjectionValue < 0.0f || prInjectionValue > this.getMaxContent()) {
			throw new bw.exceptions.exValueNotWithinRange(0.0f, prInjectionValue, this.getMaxContent());
		}

		this.mrInjectionValue = prInjectionValue;
		mnIsZero = false;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void decay() {
		double rContent = getContent();
		
		rContent = rContent - rContent * mrDecayRate;
		
		try {
			setContent( rContent );
		} catch (exContentColumnMaxContentExceeded e) {
			// this should never happen - rContent is always a positive number, mrDecayRate too
		} catch (exContentColumnMinContentUnderrun e) {
			// this can be ignored - this is an automatic decay converging towards zero ... 
		}
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void increase() {
		double rContent = getContent();
		double rChange = mrInjectionValue * mrIncreaseRate;
		
		rContent = rContent + rChange;
		
		try {
			setContent( rContent );
		} catch (exContentColumnMaxContentExceeded e) {
			// this can be ignored - per design!
		} catch (exContentColumnMinContentUnderrun e) {
			// this should never happen - rContent is always a positive number, mrInjectionValue and mrIncreaseRate too
		}
		
		try {
			setInjectionValue(mrInjectionValue - rChange);
		} catch (exValueNotWithinRange e) {
			// this should be ignoreable - rChange is always a fraction of mrInjectionValue - per design
		}
	}
	
	private void checkZero() {
		if (getContent() < mrZeroDelta && mrInjectionValue < mrZeroDelta) {
			mnIsZero = true;
			try {
				setContent(0.0f);
			} catch (exContentColumnMaxContentExceeded e) {
				// can be ignored - by design
			} catch (exContentColumnMinContentUnderrun e) {
				// can be ignored - by design				
			}
			mrInjectionValue = 0.0f;
		}
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	public void update() {
		if (!mnIsZero) {
			increase();
			decay();
			checkZero();
		}
	}
}
