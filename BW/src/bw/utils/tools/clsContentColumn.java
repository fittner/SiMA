/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

/**
 * Content column is a general purpose value container. It is
 * intended to be used similarly to a silo. it has a maximum 
 * capacity, it can be filled or emptied. the minimum amount it 
 * can hold is 0. no negative values are allowed.
 * 
 * TODO use float instead of fixed comma
 * 
 * @author deutsch
 * 
 */

public class clsContentColumn {

	/**
	 * mgMaxContent is the maximum amount which the column can be filled with.
	 */
	private float mrMaxContent = java.lang.Float.MAX_VALUE;
	
	/**
	 * mgContent is the current fill level
	 */
	private float mrContent = 0.0f;
	
	
	/**
	 * Constructor which uses the default values mrMaxContent = MAX_VALUE and mrContent = 0 
	 */
	public clsContentColumn() {
		super();
	}
	
	/**
	 * Constructor which takes mrContent and mrMaxContent as params. Performs checkValue.
	 * 
	 * @param prContent
	 * @param prMaxContent
	 */
	public clsContentColumn(float prContent, float prMaxContent) {
		super();
		
		this.setMaxContent( prMaxContent );
		this.setContent( prContent );
	}	

	/**
	 * checkValue makes sure, that mrContent always fulfills 0 <= mgContent <= mrMaxContent. If
	 * this condition is not met, the value of mrContent is adapted to fulfill it. Should be executed
	 * whenever mrContent has changed.
	 */
	private void checkValue() {
		if (this.mrContent < 0.0f) {
			this.mrContent = 0.0f;
		} else if (this.mrContent > this.mrMaxContent) {
			this.mrContent = this.mrMaxContent;
		}
	}
	
	/**
	 * checkMaxValue makes sure, that mrMaxContent always fulfills 0 <= mrMaxContent <= MAX_VALUE. If
	 * this condition is not met, the value of mrMaxContent is adapted to fulfill it. Should be executed 
	 * whenever MaxValue has been changed. 
	 */
	private void checkMaxValue() {
		if (this.mrMaxContent < 0) {
			this.mrMaxContent = 0;
		} else if (this.mrMaxContent > java.lang.Float.MAX_VALUE) {
			this.mrMaxContent = java.lang.Float.MAX_VALUE;
		}
	}
	
	/**
	 * Adds prContent to the current content of the column. 
	 *
	 * @param prContent how much should be added
	 * @return the resulting content after checkValue has been executed
	 */
	public float increase(float prContent) {
		return this.setContent( this.getContent() + prContent );
	}
	
	/**
	 * Alias for increase(prContent) 
	 *
	 * @param prContent how much should be added
	 * @return the resulting content after checkValue has been executed
	 */
	public float change(float prContent) {
		return increase( prContent );	
	}
	
	/**
	 * Lowers the current value of the column by prContent
	 *
	 * @param prContent how much should be removed
	 * @return the resulting content after checkValue has been executed
	 */
	public float decrease(float prContent) {
		return this.setContent( this.getContent() - prContent );
	}

	/**
	 * Getter function for mrContent
	 * 
	 * @return the mrContent
	 */
	public float getContent() {
		return this.mrContent;
	}

	/**
	 * Setter function for mrContent
	 * 
	 * @param pnContent the mrContent to set
	 * @return the resulting content after checkValue has been executed
	 */
	public float setContent(float prContent) {
		this.mrContent = prContent;
		
		this.checkValue();
		
		return this.getContent();
	}

	/**
	 * Getter function for mrMaxContent
	 * 
	 * @return the mrMaxContent
	 */
	public float getMaxContent() {
		return mrMaxContent;
	}

	/**
	 * Setter function for mrMaxContent
	 * 
	 * @param prMaxContent the mrMaxContent to set
	 * @return the resulting content after checkValue has been executed
	 */
	public float setMaxContent(float prMaxContent) {
		this.mrMaxContent = prMaxContent;	
		
		this.checkMaxValue();
		this.checkValue();
		
		return this.getMaxContent();
	}

	
}
