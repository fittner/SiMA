
package body.attributes;


public abstract class clsBodyOrgan {
	
	/*
	// this organ is affected this much by this emotion
	// 0.0 -> this organ is poorly affected by this emotion
	// 0.5 -> this organ is normally (directly) affected by this emotion
	// 1.0 -> this organ is overly affected by this emotion
	*/
	private double mrAngerFactor;
	private double mrMourningFactor;
	private double mrAnxietyFactor;
	private double mrJoyFactor;
	private double mrSaturationFactor;
	private double mrElationFactor;
	
	private int mnCurrentStep;
	
	public clsBodyOrgan(){
		mrAngerFactor = 1.0;
		mrMourningFactor = 1.0;
		mrAnxietyFactor = 1.0;
		mrJoyFactor = 1.0;
		mrSaturationFactor = 1.0;
		mrElationFactor = 1.0;
	}


	/**
	 * 
	 * @return the mrAngerFactor
	 */
	public double getAngerFactor() {
		return mrAngerFactor;
	}
	/**
	 * 
	 * @param prAngerFactor the mrAngerFactor to set
	 */
	protected void setAngerFactor(double prAngerFactor) {
		this.mrAngerFactor = prAngerFactor;
	}
	
	/**
	 * 
	 * @return the mrMourningFactor
	 */
	public double getMourningFactor() {
		return mrMourningFactor;
	}
	/**
	 * 
	 * @param prMourningFactor the mrMourningFactor to set
	 */
	protected void setMourningFactor(double prMourningFactor) {
		this.mrMourningFactor = prMourningFactor;
	}
	
	/**
	 * 
	 * @return the mrAnxietyFactor
	 */
	public double getAnxietyFactor() {
		return mrAnxietyFactor;
	}
	/**
	 * 
	 * @param prAnxietyFactor the prAnxietyFactor to set
	 */
	protected void setAnxietyFactor(double prAnxietyFactor) {
		this.mrAnxietyFactor = prAnxietyFactor;
	}
	
	/**
	 * 
	 * @return the prJoyFactor
	 */
	public double getJoyFactor() {
		return mrJoyFactor;
	}
	/**
	 * 
	 * @param prJoyFactor the mrJoyFactor to set
	 */
	protected void setJoyFactor(double prJoyFactor) {
		this.mrJoyFactor = prJoyFactor;
	}
	
	/**
	 * 
	 * @return the mrSaturationFactor
	 */
	public double getSaturationFactor() {
		return mrSaturationFactor;
	}
	/**
	 * 
	 * @param prSaturationFactor the mrSaturationFactor to set
	 */
	protected void setSaturationFactor(double prSaturationFactor) {
		this.mrSaturationFactor = prSaturationFactor;
	}
	
	/**
	 * 
	 * @return the meElationFactor
	 */
	public double getElationFactor() {
		return mrElationFactor;
	}
	/**
	 * 
	 * @param prElationFactor the mrElationFactor to set
	 */
	protected void setElationFactor(double prElationFactor) {
		this.mrElationFactor = prElationFactor;
	}
	
	public double getCurrentStep() {
		return mnCurrentStep;
	}
	
	protected void registerStepNo(){
	/*	if( clsStepCounter.getCounter() == mnCurrentStep ){
			// in the same step
			
		}
		else{
			// step changed!
			
			// update mnCurrentStep
			mnCurrentStep = clsStepCounter.getCounter();
			
			// reset Organ for next step
			this.resetforNextStep();
		}
		
		/////// DELETE ME !!!!!
		 
		 */
	}
	
	protected void resetforNextStep() { //// DELETE THIS FUNKTION TOO !!!
		// each sub class must override this method and implement specifically how to reset their own organ
		
	}
	
	

}
