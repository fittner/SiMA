/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

/**
 * TODO (deutsch) - insert description 
 * 
 * @author deutsch
 * 
 */
public class clsFillLevel extends clsContentColumn {

	private float mrLowerBound;
	private float mrUpperBound;
	
	/**
	 * 
	 */
	public clsFillLevel() {
		super();
		mrLowerBound = this.getMaxContent() / 3.0f;
		mrUpperBound = mrLowerBound * 2.0f;
		
		checkBounds();		
	}
	
	/**
	 * @param prContent
	 * @param prMaxContent
	 */
	public clsFillLevel(float prContent, float prMaxContent) {
		super(prContent, prMaxContent);
		
		mrLowerBound = this.getMaxContent() / 3.0f;
		mrUpperBound = mrLowerBound * 2.0f;	
		
		checkBounds();
	}
	
	/**
	 * @param prContent
	 * @param prMaxContent
	 * @param prLowerBound
	 * @param prUpperBound
	 */
	public clsFillLevel(float prContent, float prMaxContent, float prLowerBound, float prUpperBound) {
		super(prContent, prMaxContent);
		
		setBounds(prLowerBound, prUpperBound);
	}	
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @param prLowerBound
	 * @param prUpperBound
	 */
	public void setBounds(float prLowerBound, float prUpperBound) {
		mrLowerBound = prLowerBound;
		mrUpperBound = prUpperBound;
		
		checkBounds();
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public boolean isLow() {
		return (this.getContent() < mrLowerBound);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @return
	 */
	public boolean isHigh() {
		return (this.getContent() > mrUpperBound);
	}	
	
	/**
	 * @return the mrLowerBound
	 */
	public float getLowerBound() {
		return mrLowerBound;
	}

	/**
	 * Sets the lower bound which can be checked using isLow(). If the set value is higher than the upper bound 
	 * value - the latter one is changed to the new value. e.g. upper: 3, new lower: 4 => new upper: 4
	 * 
	 * @param mrLowerBound the mrLowerBound to set
	 */
	public float setLowerBound(float prBound) {
		this.mrLowerBound = prBound;
		
		checkLowerBound();
		
		return this.mrLowerBound;
	}

	/**
	 * @return the mrUpperBound
	 */
	public float getUpperBound() {
		return mrUpperBound;
	}

	/**
 	 * Sets the upper bound which can be checked using isHigh(). If the set value is lower than the lower bound 
	 * value - the latter one is changed to the new value. e.g. lower: 1, new upper: 0.5 => new lower: 0.5
	 * 
	 * @param mrUpperBound the mrUpperBound to set
	 */
	public float setUpperBound(float prBound) {
		this.mrUpperBound = prBound;
		
		checkUpperBound();
		
		return this.mrUpperBound;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void checkLowerBound() {
		if (mrLowerBound < 0.0f) {
			mrLowerBound = 0.0f;
		} else if (mrLowerBound > this.getMaxContent()) {
			mrLowerBound = this.getMaxContent();
		}
			
		if (mrLowerBound > mrUpperBound) {
			mrUpperBound = mrLowerBound;
		} 
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 */
	private void checkUpperBound() {
		if (mrUpperBound > this.getMaxContent()) {
			mrUpperBound = this.getMaxContent();
		} else if (mrUpperBound < 0.0f) {
			mrUpperBound = 0.0f;
		}
		
		if (mrUpperBound < mrLowerBound) {
			mrLowerBound = mrUpperBound;
		} 
	}

	/**
	 * TODO (deutsch) - insert description
	 * TODO optimize function
	 *
	 */
	private void checkBounds() {
		checkLowerBound();
		checkUpperBound();
	}
}
