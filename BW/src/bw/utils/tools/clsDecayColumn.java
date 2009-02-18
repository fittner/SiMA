/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

import bw.exceptions.ContentColumnMaxContentExceeded;
import bw.exceptions.ContentColumnMinContentUnderrun;
import bw.exceptions.ValueNotWithinRange;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsDecayColumn extends clsContentColumn {
	/**
	 * 
	 */
	private float mrIncreaseRate;
	/**
	 * 
	 */
	private float mrDecayRate;
	/**
	 * 
	 */
	private float mrInjectionValue;	
	/**
	 * 
	 */
	private boolean mnIsZero;
	/**
	 * 
	 */
	private float mrZeroDelta;
	
	/**
	 * @throws ContentColumnMinContentUnderrun 
	 * @throws ContentColumnMaxContentExceeded 
	 * @throws ValueNotWithinRange 
	 * 
	 */
	public clsDecayColumn() throws ContentColumnMaxContentExceeded, ContentColumnMinContentUnderrun, ValueNotWithinRange {
		super(0.0f, 1.0f);
		
		this.setIncreaseRate(0.1f);
		this.setDecayRate(0.01f);
		
		mrInjectionValue = 0.0f;
		mnIsZero = false;
		mrZeroDelta = 0.0001f;
	}
	
	/**
	 * @param prIncreaseRate
	 * @param prDecayRate
	 * @throws ContentColumnMinContentUnderrun 
	 * @throws ContentColumnMaxContentExceeded 
	 * @throws ValueNotWithinRange 
	 */
	public clsDecayColumn(float prIncreaseRate, float prDecayRate) throws ContentColumnMaxContentExceeded, ContentColumnMinContentUnderrun, ValueNotWithinRange {
		super(0.0f, 1.0f);

		this.setIncreaseRate(prIncreaseRate);
		this.setDecayRate(prDecayRate);
		
		mrInjectionValue = 0.0f;
		mnIsZero = false;
		mrZeroDelta = 0.0001f;
	}

	/**
	 * @param prContent
	 * @param prMaxContent
	 * @param prIncreaseRate
	 * @param prDecayRate
	 * @throws ContentColumnMaxContentExceeded
	 * @throws ContentColumnMinContentUnderrun
	 * @throws ValueNotWithinRange
	 */
	public clsDecayColumn(float prContent, float prMaxContent, float prIncreaseRate, float prDecayRate) throws ContentColumnMaxContentExceeded, ContentColumnMinContentUnderrun, ValueNotWithinRange {
		super(prContent, prMaxContent);

		this.setIncreaseRate(prIncreaseRate);
		this.setDecayRate(prDecayRate);
		
		mrInjectionValue = 0.0f;
		mnIsZero = false;		
		mrZeroDelta = 0.0001f;
	}
	
	/**
	 * @return the mrZeroDelta
	 */
	public float getZeroDelta() {
		return mrZeroDelta;
	}
	

	/**
	 * @param mrZeroDelta the mrZeroDelta to set
	 * @throws ValueNotWithinRange 
	 */
	public void setZeroDelta(float prZeroDelta) throws ValueNotWithinRange {
		if (prZeroDelta < 0.0f || prZeroDelta > 0.1f) {
			throw new bw.exceptions.ValueNotWithinRange(0.0f, prZeroDelta, 1.0f);			
		}		
		this.mrZeroDelta = prZeroDelta;
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
	public float getIncreaseRate() {
		return mrIncreaseRate;
	}

	/**
	 * @param mrIncreaseRate the mrIncreaseRate to set
	 * @throws ValueNotWithinRange 
	 */
	public void setIncreaseRate(float prIncreaseRate) throws ValueNotWithinRange {		
		if (prIncreaseRate < 0.0f || prIncreaseRate > 1.0f) {
			throw new bw.exceptions.ValueNotWithinRange(0.0f, prIncreaseRate, 1.0f);			
		}
		
		this.mrIncreaseRate = prIncreaseRate;
	}

	/**
	 * @return the mrDecayRate
	 */
	public float getDecayRate() {
		return mrDecayRate;
	}

	/**
	 * @param mrDecayRate the mrDecayRate to set
	 * @throws ValueNotWithinRange 
	 */
	public void setDecayRate(float prDecayRate) throws ValueNotWithinRange {
		if (prDecayRate < 0.0f || prDecayRate > 1.0f) {
			throw new bw.exceptions.ValueNotWithinRange(0.0f, prDecayRate, 1.0f);			
		}
		this.mrDecayRate = prDecayRate;
	}


	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prAmount
	 * @throws ValueNotWithinRange
	 */
	public void inject(float prAmount) throws ValueNotWithinRange {
		if (prAmount < 0.0f || prAmount > this.getMaxContent()) {
			throw new bw.exceptions.ValueNotWithinRange(0.0f, prAmount, this.getMaxContent());
		}
		setInjectionValue(mrInjectionValue + prAmount);
		mnIsZero = false;
	}
	
	/**
	 * @return the mrInjectionValue
	 */
	public float getInjectionValue() {
		return mrInjectionValue;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prInjectionValue
	 * @throws ValueNotWithinRange
	 */
	public void setInjectionValue(float prInjectionValue) throws ValueNotWithinRange {
		if (prInjectionValue < 0.0f || prInjectionValue > this.getMaxContent()) {
			throw new bw.exceptions.ValueNotWithinRange(0.0f, prInjectionValue, this.getMaxContent());
		}

		this.mrInjectionValue = prInjectionValue;
		mnIsZero = false;
	}

	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void decay() {
		float rContent = getContent();
		
		rContent = rContent - rContent * mrDecayRate;
		
		try {
			setContent( rContent );
		} catch (ContentColumnMaxContentExceeded e) {
			// this should never happen - rContent is always a positive number, mrDecayRate too
		} catch (ContentColumnMinContentUnderrun e) {
			// this can be ignored - this is an automatic decay converging towards zero ... 
		}
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void increase() {
		float rContent = getContent();
		float rChange = mrInjectionValue * mrIncreaseRate;
		
		rContent = rContent + rChange;
		
		try {
			setContent( rContent );
		} catch (ContentColumnMaxContentExceeded e) {
			// this can be ignored - per design!
		} catch (ContentColumnMinContentUnderrun e) {
			// this should never happen - rContent is always a positive number, mrInjectionValue and mrIncreaseRate too
		}
		
		try {
			setInjectionValue(mrInjectionValue - rChange);
		} catch (ValueNotWithinRange e) {
			// this should be ignoreable - rChange is always a fraction of mrInjectionValue - per design
		}
	}
	
	private void checkZero() {
		if (getContent() < mrZeroDelta && mrInjectionValue < mrZeroDelta) {
			mnIsZero = true;
			try {
				setContent(0.0f);
			} catch (ContentColumnMaxContentExceeded e) {
				// can be ignored - by design
			} catch (ContentColumnMinContentUnderrun e) {
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
