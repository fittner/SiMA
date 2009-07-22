/**
 * @author deutsch
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.utils.tools;

import bw.utils.config.clsBWProperties;


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
	public static final String P_MAXCONTENT = "maxcontent";
	public static final String P_CONTENT = "content";

	/**
	 * mgMaxContent is the maximum amount which the column can be filled with.
	 */
	private double mrMaxContent = java.lang.Double.MAX_VALUE;
	
	/**
	 * mgContent is the current fill level
	 */
	private double mrContent = 0.0;
	
	
	/**
	 * Constructor which uses the default values mrMaxContent = MAX_VALUE and mrContent = 0 
	 */
	public clsContentColumn() {
	}
	
	public clsContentColumn(String poPrefix, clsBWProperties poProp) {
		applyProperties(poPrefix, poProp);
	}

	public static clsBWProperties getDefaultProperties(String poPrefix) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		clsBWProperties oProp = new clsBWProperties();
		
		oProp.setProperty(pre+P_MAXCONTENT, new Double(java.lang.Double.MAX_VALUE).toString());
		oProp.setProperty(pre+P_CONTENT, "0");
		
		return oProp;
	}	

	private void applyProperties(String poPrefix, clsBWProperties poProp) {
		String pre = clsBWProperties.addDot(poPrefix);
		
		mrMaxContent = poProp.getPropertyDouble(pre+P_MAXCONTENT);
		mrContent = poProp.getPropertyDouble(pre+P_CONTENT);		
	}	
	
	/**
	 * Constructor which takes mrContent and mrMaxContent as params. Performs checkValue.
	 * 
	 * @param prContent
	 * @param prMaxContent
	 */
	public clsContentColumn(double prContent, double prMaxContent) throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun  {
		super();
		
		this.setMaxContent( prMaxContent );
		this.setContent( prContent );
	}	

	/**
	 * checkValue makes sure, that mrContent always fulfills 0 <= mgContent <= mrMaxContent. If
	 * this condition is not met, the value of mrContent is adapted to fulfill it. Should be executed
	 * whenever mrContent has changed.
	 */
	private void checkValue() throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun {
		double rTempContent = mrContent;
		
		if (this.mrContent < 0.0f) {
			this.mrContent = 0.0f;
			throw new bw.exceptions.exContentColumnMinContentUnderrun(rTempContent, 0.0);
		} else if (this.mrContent > this.mrMaxContent) {
			this.mrContent = this.mrMaxContent;
			throw new bw.exceptions.exContentColumnMaxContentExceeded(rTempContent, mrMaxContent);
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
		} else if (this.mrMaxContent > java.lang.Double.MAX_VALUE) {
			this.mrMaxContent = java.lang.Double.MAX_VALUE;
		}
	}
	
	/**
	 * Adds prContent to the current content of the column. 
	 *
	 * @param prContent how much should be added
	 * @return the resulting content after checkValue has been executed
	 */
	public double increase(double prContent) throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun  {
		return this.setContent( this.getContent() + prContent );
	}
	
	/**
	 * Alias for increase(prContent) 
	 *
	 * @param prContent how much should be added
	 * @return the resulting content after checkValue has been executed
	 */
	public double change(double prContent) throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun  {
		return increase( prContent );	
	}
	
	/**
	 * Lowers the current value of the column by prContent
	 *
	 * @param prContent how much should be removed
	 * @return the resulting content after checkValue has been executed
	 */
	public double decrease(double prContent) throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun  {
		return this.setContent( this.getContent() - prContent );
	}

	/**
	 * Getter function for mrContent
	 * 
	 * @return the mrContent
	 */
	public double getContent() {
		return this.mrContent;
	}

	/**
	 * Setter function for mrContent
	 * 
	 * @param pnContent the mrContent to set
	 * @return the resulting content after checkValue has been executed
	 */
	public double setContent(double prContent) throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun  {
		this.mrContent = prContent;
		
		this.checkValue();
		
		return this.getContent();
	}

	/**
	 * Getter function for mrMaxContent
	 * 
	 * @return the mrMaxContent
	 */
	public double getMaxContent() {
		return mrMaxContent;
	}

	/**
	 * Setter function for mrMaxContent
	 * 
	 * @param prMaxContent the mrMaxContent to set
	 * @return the resulting content after checkValue has been executed
	 */
	public double setMaxContent(double prMaxContent) throws bw.exceptions.exContentColumnMaxContentExceeded, bw.exceptions.exContentColumnMinContentUnderrun  {
		this.mrMaxContent = prMaxContent;	
		
		this.checkMaxValue();
		this.checkValue();
		
		return this.getMaxContent();
	}

	
}
