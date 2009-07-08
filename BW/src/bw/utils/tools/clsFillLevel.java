/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

/**
 * Extends the content column by two concepts: 1. dividing the column into three parts (low, normal, high);  
 * 2. it allows to predefine a value (mrChange) which will be added to the content each call of update()
 * 
 * @author deutsch
 * 
 */
public class clsFillLevel extends clsContentColumn {

	private double mrLowerBound;
	private double mrUpperBound;
	private double mrChange;
	
	/**
	 * The column (0.0 to mrMaxContent) is divided into three equal parts. mrChange is set to 0.0f
	 */
	public clsFillLevel() {
		super();
		mrLowerBound = this.getMaxContent() / 3.0f;
		mrUpperBound = mrLowerBound * 2.0f;
		mrChange = 0.0f;
		
		checkBounds();		
	}
	
	/**
	 * The column (0.0 to mrMaxContent) is divided into three equal parts. mrChange is set to 0.0f
	 * 
	 * @param prContent the mrContent to set
	 * @param prMaxContent the mrMaxContent to set
	 */
	public clsFillLevel(double prContent, double prMaxContent) throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun {
		super(prContent, prMaxContent);
		
		mrLowerBound = this.getMaxContent() / 3.0f;
		mrUpperBound = mrLowerBound * 2.0f;	
		mrChange = 0.0f;
		
		checkBounds();
	}
	
	/**
	 * The column (0.0 to mrMaxContent) is divided into three equal parts.
	 * 
	 * @param prContent the mrContent to set
	 * @param prMaxContent the mrMaxContent to set
	 * @param prChange  the mrChange to set
	 */
	public clsFillLevel(double prContent, double prMaxContent, double prChange) throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun {
		super(prContent, prMaxContent);
		
		mrLowerBound = this.getMaxContent() / 3.0f;
		mrUpperBound = mrLowerBound * 2.0f;	
		mrChange = prChange;
		
		checkBounds();
	}
	
	/**
	 * @param prContent the mrContent to set
	 * @param prMaxContent the mrMaxContent to set
	 * @param prChange  the mrChange to set
	 * @param prLowerBound the mrLowerBound to set
	 * @param prUpperBound the mrUpperBound to set
	 */
	public clsFillLevel(double prContent, double prMaxContent, double prChange, double prLowerBound, double prUpperBound) throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun {
		super(prContent, prMaxContent);
		mrChange = prChange;		
		setBounds(prLowerBound, prUpperBound);
	}	
	
	
	
	/**
	 * @return the mrChange
	 */
	public double getChange() {
		return mrChange;
	}

	/**
	 * @param mrChange the mrChange to set
	 */
	public void setChange(double mrChange) {
		this.mrChange = mrChange;
	}

	/**
	 * Applies the mrChange value to mrContent. See also {@link clsContentColumn#change(float)}
	 * 
	 * @see clsContentColumn.change(float)
	 */
	public void update() throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun {
		change(mrChange);
	}

	/**
	 * Sets the lower and the upper bound and performs validation checks ({@link #checkBounds()}).
	 * 
	 * @see checkBounds 
	 *
	 * @param prLowerBound the mrLowerBound to set
	 * @param prUpperBound the mrLowerBound to set
	 */
	public void setBounds(double prLowerBound, double prUpperBound) {
		mrLowerBound = prLowerBound;
		mrUpperBound = prUpperBound;
		
		checkBounds();
	}
	
	/**
	 * Checks if the content is within the lower part  (mrContent < mrLowerBound)
	 *
	 * @return true if mrContent is within the lower part
	 */
	public boolean isLow() {
		return (this.getContent() < mrLowerBound);
	}
	
	/**
	 * Checks if the content is within the upper part  (mrContent > mrUpperBound)
	 *
	 * @return true if mrContent is within the upper part
	 */
	public boolean isHigh() {
		return (this.getContent() > mrUpperBound);
	}
	
	/**
	 * TODO (deutsch) - insert description
	 *
	 * @author deutsch
	 * 20.02.2009, 11:08:28
	 *
	 * @return
	 */
	public double percentageLow() {
		double rPercentage = 0.0f;
		
		if (isLow()) {
			double rRange = getLowerBound();
			double rValue = rRange - getContent();
			
			rPercentage = rValue / rRange;
			
		}
		
		return rPercentage;
	}
	
	/**
	 * TODO (deutsch) - insert description
	 * 
	 * @author deutsch
	 * 20.02.2009, 11:08:30
	 *
	 * @return
	 */
	public double percentageHigh() {
		double rPercentage = 0.0f;
		
		if (isHigh()) {
			double rRange = getMaxContent() - getUpperBound();
			double rValue = getMaxContent() - getContent();
				
			rPercentage = rValue / rRange;
		}
		
		return rPercentage;
	}
	
	/**
	 * @return the mrLowerBound
	 */
	public double getLowerBound() {
		return mrLowerBound;
	}

	/**
	 * Sets the lower bound which can be checked using {@link #isLow()}.
	 * 
	 * @param mrLowerBound the mrLowerBound to set
	 */
	public double setLowerBound(float prBound) {
		this.mrLowerBound = prBound;
		
		checkLowerBound();
		
		return this.mrLowerBound;
	}

	/**
	 * @return the mrUpperBound
	 */
	public double getUpperBound() {
		return mrUpperBound;
	}

	/**
 	 * Sets the upper bound which can be checked using {@link #isHigh()}. 
	 * 
	 * @param mrUpperBound the mrUpperBound to set
	 */
	public double setUpperBound(float prBound) {
		this.mrUpperBound = prBound;
		
		checkUpperBound();
		
		return this.mrUpperBound;
	}
	
	/**
	 * Checks the lower bound value for validity. If necessary the bounds are corrected automatically. If the 
	 * set value is higher than the upper bound value - the latter one is changed to the new value. 
	 * e.g. upper: 3, new lower: 4 => new upper: 4
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
	 * Checks the upper bound value for validity. If necessary the bounds are corrected automatically. If the 
	 * set value is lower than the lower bound value - the latter one is changed to the new value. 
	 * e.g. lower: 1, new upper: 0.5 => new lower: 0.5
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
	 * Checks if the lower and the upper bound are valid. 
	 * First it calls {@link #checkLowerBound()}, afterwards {@link #checkUpperBound()}. 
	 * 
	 */
	private void checkBounds() {
		checkLowerBound();
		checkUpperBound();
	}
}
