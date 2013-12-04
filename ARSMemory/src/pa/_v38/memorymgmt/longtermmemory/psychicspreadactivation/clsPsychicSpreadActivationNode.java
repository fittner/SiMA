/**
 * CHANGELOG
 *
 * 29.03.2012 wendt - File created
 *
 */
package pa._v38.memorymgmt.longtermmemory.psychicspreadactivation;

import org.apache.log4j.Logger;

import pa._v38.memorymgmt.datatypes.clsThingPresentationMesh;

/**
 * DOCUMENT (wendt) - insert description 
 * 
 * @author wendt
 * 29.03.2012, 21:57:03
 * 
 */
public class clsPsychicSpreadActivationNode {

	private clsThingPresentationMesh moBaseImage;
	private double mrPsychicPotential = 0.0;
	private double mrP = 0.0;
	private double mrDivider = 0.0;
	private double mrAccumulatedSum = 0.0;
	//private boolean mbActivateable = false;
	//private boolean mbSortedActivation = false;
	//private double mrAverageAffect = 0.0;
	private double mrConsumptionValue = 0.0;
	private double mrEnergyQuote = 0.0;
	private double mrAssignedPsychicEnergy = 0.0;
	
	Logger logger = Logger.getLogger("pa._v38.memorymgmt.psychicspreadactivation");

	/**
	 * @since 30.03.2012 21:52:56
	 * 
	 * @return the moBaseImage
	 */
	public clsThingPresentationMesh getMoBaseImage() {
		return moBaseImage;
	}
	/**
	 * @since 30.03.2012 21:52:56
	 * 
	 * @param moBaseImage the moBaseImage to set
	 */
	public void setBaseImage(clsThingPresentationMesh moBaseImage) {
		this.moBaseImage = moBaseImage;
	}

	/**
	 * @since 30.03.2012 21:52:56
	 * 
	 * @return the mrP
	 */
	public double getP() {
		return mrP;
	}
	/**
	 * @since 30.03.2012 21:52:56
	 * 
	 * @param mrP the mrP to set
	 */
	public void setP(double mrP) {
		this.mrP = mrP;
	}
	/**
	 * @since 30.03.2012 21:52:56
	 * 
	 * @return the mrAccumulatedSum
	 */
	public double getMrAccumulatedSum() {
		return mrAccumulatedSum;
	}
	/**
	 * @since 30.03.2012 21:52:56
	 * 
	 * @param mrAccumulatedSum the mrAccumulatedSum to set
	 */
	public void setMrAccumulatedSum(double mrAccumulatedSum) {
		this.mrAccumulatedSum = mrAccumulatedSum;
	}

	/**
	 * @since 30.03.2012 21:52:56
	 * 
	 * @return the mrAssignedPsychicEnergy
	 */
	public double getMrAssignedPsychicEnergy() {
		return mrAssignedPsychicEnergy;
	}
	/**
	 * @since 30.03.2012 21:52:56
	 * 
	 * @param mrAssignedPsychicEnergy the mrAssignedPsychicEnergy to set
	 */
	public void setMrAssignedPsychicEnergy(double mrAssignedPsychicEnergy) {
		this.mrAssignedPsychicEnergy = mrAssignedPsychicEnergy;
	}
	/**
	 * @since 30.03.2012 21:56:20
	 * 
	 * @param mrDivider the mrDivider to set
	 */
	public void setDivider(double mrDivider) {
		this.mrDivider = mrDivider;
	}
	/**
	 * @since 30.03.2012 21:56:20
	 * 
	 * @return the mrDivider
	 */
	public double getMrDivider() {
		return mrDivider;
	}
	/**
	 * @since 30.03.2012 22:01:26
	 * 
	 * @param mrPsychicPotential the mrPsychicPotential to set
	 */
	public void setPsychicPotential(double mrPsychicPotential) {
		this.mrPsychicPotential = mrPsychicPotential;
	}
	/**
	 * @since 30.03.2012 22:01:26
	 * 
	 * @return the mrPsychicPotential
	 */
	public double getPsychicPotential() {
		return mrPsychicPotential;
	}
	/**
	 * @since 30.03.2012 22:15:36
	 * 
	 * @param mrEnergyQuote the mrEnergyQuote to set
	 */
	public void setMrEnergyQuote(double mrEnergyQuote) {
		this.mrEnergyQuote = mrEnergyQuote;
	}
	/**
	 * @since 30.03.2012 22:15:36
	 * 
	 * @return the mrEnergyQuote
	 */
	public double getMrEnergyQuote() {
		return mrEnergyQuote;
	}
	
	public double getConsumptionValue() {
		return mrConsumptionValue;
	}
	public void setConsumptionValue(double mrConsumptionValue) {
		this.mrConsumptionValue = mrConsumptionValue;
	}
	
	@Override
	public String toString() {
		String oResult = "";
		
		oResult += this.moBaseImage.getContent() + ", ass pe=" + this.mrAssignedPsychicEnergy + ", psy pot=" + this.mrPsychicPotential + ", ";
		
		return oResult;
	}
	
	
}
