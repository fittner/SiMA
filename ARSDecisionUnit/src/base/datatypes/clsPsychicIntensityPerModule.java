/**
 * CHANGELOG
 *
 * Nov 11, 2012 Snorry - File created
 *
 */
package base.datatypes;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericTimeChart;

import java.util.ArrayList;

/**
 * Psychic intensity per module to implement access for inspectors.
 * 
 * @author Kogelnig Philipp (e0225185)
 * @since 08.10.2012
 * @author Aldo Martinez Pinanez
 * @since 13.02.2014
 */

public class clsPsychicIntensityPerModule implements itfInspectorGenericTimeChart {
    
    
    private int mnModuleNr;

    public double mrAvailablePsychicIntensity;
    
    private double mrRequestedPsychicIntensity;
    private double[] moRequestedPsychicIntensityFollower;
    private int mnSizeIntensityFollower = 5;
    private double mrEstimationRequestedPsychicIntensity;
    
    private double mrModuleStrength;
    private double[] moModuleStrengthFollower;
    private int mnSizeStrengthFollower = 5;
    private double mrEstimatedModuleStrength;

    
    private double mrConsumedPsychicIntensity;
    private double[] moConsumedPsychicIntensityFollower;
    private int mnSizeConsumedPsychicIntensityFollower = 5;;
    private double mrEstimatedConsumedPsychicIntensity;
	
    public clsPsychicIntensityPerModule(int pnModuleNr, 
            double prInitialRequestedPsychicIntensity,
            double prInitialModuleStrength,
            double prInitialConsumedPsychicIntensity) {
        super();
        this.mnModuleNr = pnModuleNr;
        this.mrAvailablePsychicIntensity = 0.0;
        
        this.mrRequestedPsychicIntensity = prInitialRequestedPsychicIntensity;
        
        this.moRequestedPsychicIntensityFollower = new double[mnSizeIntensityFollower];
        double rEstimationIntensity = 0.0;
        for(int i = 0; i < mnSizeIntensityFollower; i++){
            moRequestedPsychicIntensityFollower[i] = prInitialRequestedPsychicIntensity;
            rEstimationIntensity += moRequestedPsychicIntensityFollower[i];
        }
        this.mrEstimationRequestedPsychicIntensity = rEstimationIntensity/mnSizeIntensityFollower;
        
        this.mrModuleStrength = prInitialModuleStrength;
        this.moModuleStrengthFollower = new double[mnSizeStrengthFollower];
        double rEstimationStrength = 0.0;
        for(int i=0; i < mnSizeStrengthFollower; i++){
            moModuleStrengthFollower[i] = prInitialModuleStrength;
            rEstimationStrength += moModuleStrengthFollower[i];
        }
        this.mrEstimatedModuleStrength = rEstimationStrength/mnSizeStrengthFollower;
        
        this.mrConsumedPsychicIntensity = prInitialConsumedPsychicIntensity;
        this.moConsumedPsychicIntensityFollower = new double[mnSizeConsumedPsychicIntensityFollower];
        double rEstimationConsumption = 0.0;
        for(int i=0; i< mnSizeConsumedPsychicIntensityFollower; i++){
            moConsumedPsychicIntensityFollower[i] = prInitialConsumedPsychicIntensity;
            rEstimationConsumption += moRequestedPsychicIntensityFollower[i];
        }
        this.mrEstimatedConsumedPsychicIntensity = rEstimationConsumption/mnSizeConsumedPsychicIntensityFollower;
    }
    
    
    public void updateEstimationRequestedPsychicIntensity(double prNewRequestedPsychicIntensity){
        for(int i = moRequestedPsychicIntensityFollower.length - 1; i >= 0; i--){
            
            if(i==0){
                moRequestedPsychicIntensityFollower[i] = prNewRequestedPsychicIntensity;
            }else{
                moRequestedPsychicIntensityFollower[i] = moRequestedPsychicIntensityFollower[i-1];
            }
        }
        
        setEstimationRequestedPsychicIntensity();
    }
    
    public void updateEstimationModuleStrength(double prNewModuleStrength){   
        for(int i = moModuleStrengthFollower.length - 1; i >= 0; i--){
            
            if(i==0){
                moModuleStrengthFollower[i] = prNewModuleStrength;
            }else{
                moModuleStrengthFollower[i] = moModuleStrengthFollower[i-1];
            }
        }
        setEstimatedModuleStrength();
    }
    
    public void updateEstimationConsumedPsychicIntensity(double prNewConsumedIntensity){
      
        for(int i = moConsumedPsychicIntensityFollower.length - 1; i >= 0; i--){
            
            if(i==0){
                moConsumedPsychicIntensityFollower[i] = prNewConsumedIntensity;
            }else{
                moConsumedPsychicIntensityFollower[i] = moConsumedPsychicIntensityFollower[i-1];
            }
        }
        
        setEstimatedConsumedPsychicIntensity();
    }
    
    /**
     * @return the mnModuleNr
     */
    public int getModuleNr() {
        return mnModuleNr;
    }
    /**
     * @param mnModuleNr the mnModuleNr to set
     */
    public void setModuleNr(int mnModuleNr) {
        this.mnModuleNr = mnModuleNr;
    }
    /**
     * @return the mrAvailablePsychicIntensity
     */
    public double getAvailablePsychicIntensity() {
        return mrAvailablePsychicIntensity;
    }
    /**
     * @param mrAvailablePsychicIntensity the mrAvailablePsychicIntensity to set
     */
    public void setAvailablePsychicIntensity(double mrAvailablePsychicIntensity) {
        this.mrAvailablePsychicIntensity = mrAvailablePsychicIntensity;
    }
    /**
     * @return the mrRequestedPsychicIntensity
     */
    public double getRequestedPsychicIntensity() {
        return mrRequestedPsychicIntensity;
    }
    /**
     * @param mrRequestedPsychicIntensity the mrRequestedPsychicIntensity to set
     */
    public void setRequestedPsychicIntensity(double mrRequestedPsychicIntensity) {
        this.mrRequestedPsychicIntensity = mrRequestedPsychicIntensity;
    }
    /**
     * @return the moRequestedPsychicIntensityFollower
     */
    public double[] getRequestedPsychicIntensityFollower() {
        return moRequestedPsychicIntensityFollower;
    }
    /**
     * @param moRequestedPsychicIntensityFollower the moRequestedPsychicIntensityFollower to set
     */
    public void setRequestedPsychicIntensityFollower(
            double[] moRequestedPsychicIntensityFollower) {
        this.moRequestedPsychicIntensityFollower = moRequestedPsychicIntensityFollower;
    }
    /**
     * @return the mnSizeIntensityFollower
     */
    public int getSizeIntensityFollower() {
        return mnSizeIntensityFollower;
    }
    /**
     * @param mnSizeIntensityFollower the mnSizeIntensityFollower to set
     */
    public void setSizeIntensityFollower(int mnSizeIntensityFollower) {
        this.mnSizeIntensityFollower = mnSizeIntensityFollower;
    }
    /**
     * @return the mrEstimationRequestedPsychicIntensity
     */
    public double getEstimationRequestedPsychicIntensity() {
        return mrEstimationRequestedPsychicIntensity;
    }
    /**
     * @param mrEstimationRequestedPsychicIntensity the mrEstimationRequestedPsychicIntensity to set
     */
    public void setEstimationRequestedPsychicIntensity() {       
        double rEstimation = 0.0;
        
        for(int i=0; i < moRequestedPsychicIntensityFollower.length; i++){
            rEstimation += moRequestedPsychicIntensityFollower[i];
        }       
        this.mrEstimationRequestedPsychicIntensity = rEstimation/moRequestedPsychicIntensityFollower.length;
    }
    /**
     * @return the mrModuleStrength
     */
    public double getModuleStrength() {
        return mrModuleStrength;
    }
    /**
     * @param mrModuleStrength the mrModuleStrength to set
     */
    public void setModuleStrength(double mrModuleStrength) {
        this.mrModuleStrength = mrModuleStrength;
    }
    /**
     * @return the mrConsumedPsychicIntensity
     */
    public double getConsumedPsychicIntensity() {
        return mrConsumedPsychicIntensity;
    }
    /**
     * @param mrConsumedPsychicIntensity the mrConsumedPsychicIntensity to set
     */
    public void setConsumedPsychicIntensity(double mrConsumedPsychicIntensity) {
        this.mrConsumedPsychicIntensity = mrConsumedPsychicIntensity;
    }


    /**
     * @return the mrEstimatedModuleStrength
     */
    public double getEstimatedModuleStrength() {
        return mrEstimatedModuleStrength;
    }

    /**
     * @param mrEstimatedModuleStrength the mrEstimatedModuleStrength to set
     */
    public void setEstimatedModuleStrength() {    
        double rEstimation = 0.0;
        for(int i=0; i < moModuleStrengthFollower.length; i++){
            rEstimation += moModuleStrengthFollower[i];
        }       
        this.mrEstimatedModuleStrength = rEstimation/moModuleStrengthFollower.length;
    }


    /**
     * @return the mrEstimatedConsumedPsychicIntensity
     */
    public double getEstimatedConsumedPsychicIntensity() {
        return mrEstimatedConsumedPsychicIntensity;
    }

    /**
     * @param mrEstimatedConsumedPsychicIntensity the mrEstimatedConsumedPsychicIntensity to set
     */
    public void setEstimatedConsumedPsychicIntensity() {
        double rEstimation = 0.0;
        
        for(int i=0; i < moConsumedPsychicIntensityFollower.length; i++){
            rEstimation += moConsumedPsychicIntensityFollower[i];
        }
        
        this.mrEstimatedConsumedPsychicIntensity = rEstimation/moConsumedPsychicIntensityFollower.length;
    }   
	
    

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		// TODO (Snorry) - Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return Integer.toString(mnModuleNr);
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oValues = new ArrayList<Double>();
		oValues.add(mrAvailablePsychicIntensity);
		oValues.add(mrRequestedPsychicIntensity);
		oValues.add(mrEstimationRequestedPsychicIntensity);
	    oValues.add(mrModuleStrength);
	    oValues.add(mrEstimatedModuleStrength);
	    oValues.add(mrConsumedPsychicIntensity);
	    oValues.add(mrEstimatedConsumedPsychicIntensity);
		return oValues;
	}
	

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oValues = new ArrayList<String>();
		oValues.add("Available Psychic Intensity");
		oValues.add("Requested Psychic Intensity");
		oValues.add("Estimated Requested Psychic Intensity");
	    oValues.add("Module Strength");
	    oValues.add("Estimated  Module Strength");
	    oValues.add("Consumed Psychic Intensity");
	    oValues.add("Estimated Consumed Psychic Intensity");
		return oValues;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		// TODO (Snorry) - Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 11, 2012 3:58:48 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		// TODO (Snorry) - Auto-generated method stub
		return 0;
	}
    /* (non-Javadoc)
    *
    * @since 14.05.2014 10:33:20
    * 
    * @see inspector.interfaces.itfInspectorTimeChartBase#getProperties()
    */
   @Override
   public clsTimeChartPropeties getProperties() {
       return new clsTimeChartPropeties(true);
   }

}
