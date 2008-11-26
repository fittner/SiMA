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
 * @author deutsch
 * 
 */

public class clsContentColumn {

	/**
	 * mgMaxContent is the maximum amount which the column can be filled with.
	 */
	private int mnMaxContent = java.lang.Integer.MAX_VALUE;
	
	/**
	 * mgContent is the current fill level
	 */
	private int mnContent = 0;
	
	
	
	/**
	 * Constructor which uses the default values mnMaxContent = MAX_VALUE and mnContent = 0 
	 */
	public clsContentColumn() {
		super();
	}
	
	/**
	 * Constructor which takes mnContent and mnMaxContent as params. Performs checkValue.
	 * 
	 * @param mnContent
	 * @param mnMaxContent
	 */
	public clsContentColumn(int mnContent, int mnMaxContent) {
		super();
		this.mnContent = mnContent;
		this.mnMaxContent = mnMaxContent;
		
		this.checkMaxValue();
		this.checkValue();		
	}

	/**
	 * checkValue makes sure, that mnContent always fulfills 0 <= mgContent <= mnMaxContent. If
	 * this condition is not met, the value of mnContent is adapted to fulfill it. Should be executed
	 * whenever mnContent has changed.
	 */
	private void checkValue() {
		if (this.mnContent < 0) {
			this.mnContent = 0;
		} else if (this.mnContent > this.mnMaxContent) {
			this.mnContent = this.mnMaxContent;
		}
	}
	
	/**
	 * checkMaxValue makes sure, that mnMaxContent always fulfills 0 <= mnMaxContent <= MAX_VALUE. If
	 * this condition is not met, the value of mnMaxContent is adapted to fulfill it. Should be executed 
	 * whenever mnMaxValue has been changed. 
	 */
	private void checkMaxValue() {
		if (this.mnMaxContent < 0) {
			this.mnMaxContent = 0;
		} else if (this.mnMaxContent > java.lang.Integer.MAX_VALUE) {
			this.mnMaxContent = java.lang.Integer.MAX_VALUE;
		}
	}
	
	/**
	 * Adds pnContent to the current content of the column. 
	 *
	 * @param pnContent how much should be added
	 * @return the resulting content after checkValue has been executed
	 */
	public int increase(int pnContent) {
		this.mnContent += pnContent;
		
		this.checkValue();
		
		return this.mnContent;
	}
	
	/**
	 * Lowers the current value of the column by pnContent
	 *
	 * @param pnContent how much should be removed
	 * @return the resulting content after checkValue has been executed
	 */
	public int decrease(int pnContent) {
		this.mnContent -= pnContent;
		
		this.checkValue();
		
		return this.mnContent;
	}

	/**
	 * Getter function for mnContent
	 * 
	 * @return the mnContent
	 */
	public int getContent() {
		return this.mnContent;
	}

	/**
	 * Setter function for mnContent
	 * 
	 * @param mnContent the mnContent to set
	 * @return the resulting content after checkValue has been executed
	 */
	public int setContent(int mnContent) {
		this.mnContent = mnContent;
		
		this.checkValue();
		
		return this.mnContent;
	}

	/**
	 * Getter function for mnMaxContent
	 * 
	 * @return the mnMaxContent
	 */
	public int getMaxContent() {
		return mnMaxContent;
	}

	/**
	 * Setter function for mnMaxContent
	 * 
	 * @param mnMaxContent the mnMaxContent to set
	 * @return the resulting content after checkValue has been executed
	 */
	public int setMaxContent(int mnMaxContent) {
		this.mnMaxContent = mnMaxContent;	
		
		this.checkMaxValue();
		this.checkValue();
		
		return this.mnContent;
	}
	
	
}
