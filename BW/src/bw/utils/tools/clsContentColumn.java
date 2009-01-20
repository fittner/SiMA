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
 * TODO refactor this class into two - a datatype and a contentcolumn - the datatype could be used for e.g. internal energy consumption
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
	 * mnPrecision says how many decimal positions the internal fixed width value has. this property is maybe needed by
	 * derived specializations. the value has to be 0 or larger.  
	 */
	private int mnPrecision = 0;
	
	/**
	 * the resulting precision multiplicator. values are 1, 10, 100, etc.
	 */
	private int mnPrecisionMultiplicator = 1;
	
	
	
	/**
	 * Constructor which uses the default values mnMaxContent = MAX_VALUE and mnContent = 0 
	 */
	public clsContentColumn() {
		super();
	}
	
	/**
	 * Constructor which takes mnContent and mnMaxContent as params. Performs checkValue.
	 * 
	 * @param pnContent
	 * @param pnMaxContent
	 */
	public clsContentColumn(int pnContent, int pnMaxContent) {
		super();
		
		this.setMaxContent( pnMaxContent );
		this.setContent( pnContent );
		
		this.checkMaxValue();
		this.checkValue();		
	}	
	
	/**
	 * Constructor which takes mnContent, mnMaxContent, and mnPrecision as params. Performs checkValue.
	 * 
	 * @param pnContent
	 * @param pnMaxContent
	 * @param pnPrecision
	 */
	public clsContentColumn(int pnContent, int pnMaxContent, int pnPrecision) {
		super();
		
		this.setPrecision( pnPrecision );
		
		this.setMaxContent( pnMaxContent );
		this.setContent( pnContent );
		
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
		return this.setContent( this.getContent() + pnContent );
	}
	
	/**
	 * Lowers the current value of the column by pnContent
	 *
	 * @param pnContent how much should be removed
	 * @return the resulting content after checkValue has been executed
	 */
	public int decrease(int pnContent) {
		return this.setContent( this.getContent() - pnContent );
	}

	/**
	 * Getter function for mnContent
	 * 
	 * @return the mnContent
	 */
	public int getContent() {
		if (this.mnPrecision == 0) {
			return this.mnContent;
		} else {
			return (int)(this.mnContent / this.mnPrecisionMultiplicator);
		}
	}

	/**
	 * Setter function for mnContent
	 * 
	 * @param pnContent the mnContent to set
	 * @return the resulting content after checkValue has been executed
	 */
	public int setContent(int pnContent) {
		this.mnContent = pnContent * this.mnPrecisionMultiplicator;
		
		this.checkValue();
		
		return this.getContent();
	}

	/**
	 * Getter function for mnMaxContent
	 * 
	 * @return the mnMaxContent
	 */
	public int getMaxContent() {
		if (this.mnPrecision == 0) {		
			return mnMaxContent;
		} else {
			return (int)(this.mnMaxContent / this.mnPrecisionMultiplicator);
		}
	}

	/**
	 * Setter function for mnMaxContent
	 * 
	 * @param pnMaxContent the mnMaxContent to set
	 * @return the resulting content after checkValue has been executed
	 */
	public int setMaxContent(int pnMaxContent) {
		this.mnMaxContent = pnMaxContent * this.mnPrecisionMultiplicator;	
		
		this.checkMaxValue();
		this.checkValue();
		
		return this.getMaxContent();
	}
	
	/**
	 * sets the precision multiplicator according to the current value of mnPrecision
	 *
	 */
	private void setPrecisionMultiplicator() {
		this.mnPrecisionMultiplicator = (int) Math.pow(10, this.mnPrecision);
	}
	
	/**
	 * Getter function for mnPrecision
	 * 
	 * @return the mnPrecision
	 */
	public int getPrecision() {
		return this.mnPrecision;
	}

	/**
	 * Setter function for mnPrecision. updates mnContent and mnMaxContent to fit the new precision
	 * 
	 * @param pnPrecision the mnPrecision to set
	 */
	public void setPrecision(int pnPrecision) {
		//store old value of precision multiplicator - used to update the values.
		int oldPrecisionMultiplicator = this.mnPrecisionMultiplicator;

		if (pnPrecision < 0) {
			this.mnPrecision = 0;
		}
				
		this.mnPrecision = pnPrecision;
		this.setPrecisionMultiplicator();
		
		// update the values of content and maxcontent to the new precision
		this.mnContent *= this.mnPrecisionMultiplicator;
		this.mnMaxContent *= this.mnPrecisionMultiplicator;
		
		this.mnContent = this.mnContent / oldPrecisionMultiplicator;
		this.mnMaxContent = this.mnMaxContent / oldPrecisionMultiplicator;
		
		// check if everything is still in range - should be fine
		this.checkMaxValue();
		this.checkValue();
	}	
	
}
