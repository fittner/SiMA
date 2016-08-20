/**
 * CHANGELOG
 *
 * 12.10.2011 zottl					- first proper implementation according to current specification
 * 14.07.2011 deutsch 			- refactored
 * 09.07.2011 hinterleitner - File created
 *
 */
package memorymgmt.storage;


import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericTimeChart;
import inspector.interfaces.itfInspectorInternalState;
import inspector.interfaces.itfInspectorStackedBarChart;
import inspector.interfaces.itfInterfaceDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;

import properties.personality_parameter.clsPersonalityParameterContainer;
import modules.interfaces.D3_1_receive;
import modules.interfaces.D3_1_send;
import modules.interfaces.eInterfaces;
import base.datatypes.clsPsychicIntensityPerModule;
import base.tools.toText;

/**
 * Storage module for delivering intensity to F55, F7, F21, F20, F8, F23.
 * Module F56 sends freed psychic energy that is taken from the drives from {I5.3}.
 * A special storage containing this intensity energy is necessary in order to distribute it.
 * The stored data is simply the amount of intensity available to each connected module.
 * The modules are connected to this special type of storage with {D3_1_receive} and
 * {D3_1_send} interfaces.
 * 
 * @author Kogelnig Philipp (e0225185)
 * @since 08.10.2012
 * @author Aldo Martinez Pinanez
 * @since 13.02.2014
 * 
 */
public class DT3_PsychicIntensityStorage 
implements itfInspectorInternalState, itfInterfaceDescription, itfInspectorGenericTimeChart, D3_1_receive, D3_1_send, itfInspectorStackedBarChart {
	
    private static final String P_PSYCHIC_INTENSITY_CONTAINER_LIMIT ="PSYCHIC_INTENSITY_CONTAINER_LIMIT";

    private Hashtable<Integer, clsPsychicIntensityPerModule> moPsychicIntensityPerModuleTable;
    
    private double mrTotalEstimatedPsychicIntensityDemand;
    
    private double mrEstimatedSumModulesPriorities;
    
    private double mrFreePsychicIntensityInStorage;
    
    private double mrEstimatedConsumedPsychicIntensity;
    
    private double mrSentPsychicIntensity;
    
    private double mrPsychicIntensityContainerLimit;
    
	
	public DT3_PsychicIntensityStorage(clsPersonalityParameterContainer poPersonalityParameterContainer) {
	    
	    this.mrPsychicIntensityContainerLimit =poPersonalityParameterContainer.getPersonalityParameter("DT3", P_PSYCHIC_INTENSITY_CONTAINER_LIMIT).getParameterDouble();
	    this.moPsychicIntensityPerModuleTable = new Hashtable<Integer, clsPsychicIntensityPerModule>();
	    this.mrFreePsychicIntensityInStorage = 0.0d;
	    this.mrEstimatedConsumedPsychicIntensity = 0.0d;
	    this.mrSentPsychicIntensity = 0.0d;
	    this.mrTotalEstimatedPsychicIntensityDemand = 0.0d;
	    this.mrEstimatedSumModulesPriorities = 0.0d;
	}


	
	/**
	 * Register modules which require psychic intensities
	 * 
	 * @since 13.02.2012 15:07:23
	 * 
	 * @param pnModuleNr - The module number of the calling module.
	 */
    public void registerModule(int pnModuleNr, double prInitialRequestedPsychicIntensity, double prInitalModuleStrength){
        
        moPsychicIntensityPerModuleTable.put(pnModuleNr, new clsPsychicIntensityPerModule(pnModuleNr, prInitialRequestedPsychicIntensity, prInitalModuleStrength, 0.0d));
    }
	

	public ArrayList<itfInspectorGenericTimeChart> getInspectorPsychicEnergyPerModule() {
		ArrayList<itfInspectorGenericTimeChart> ret = new ArrayList<itfInspectorGenericTimeChart>();
		for(int nKey:moPsychicIntensityPerModuleTable.keySet()) {
			ret.add(moPsychicIntensityPerModuleTable.get(nKey));
		}
		return ret;
	}
	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
	 */
	@Override
	public String getDescription() {
		return  "Storage module for delivering psychic intensity to F55, F7, F21, F20, F8, F23. " +
		"Module F56 sends free psychic intensity that is taken from the drives from {I5.3}. " +
		"A special storage containing this psychic intensity is necessary in order to distribute it. " +
		"The stored data is simply the amount of intensity available to each connected module. " +
		"The modules are connected to this special type of storage with {D3_1_receive}," +
		"and {D3_1_send} interfaces.";
	}

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D3_1) );
	}

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D3_1) );
	}

	/* (non-Javadoc)
	 *
	 * @since 14.07.2011 16:38:05
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToTEXT()
	 */
	@Override
	public String stateToTEXT() {
		String text = "";
		
		text += toText.h1("Psychic Intensity Storage");
		text += toText.mapToTEXT("moPsychicIntensityPerModule", moPsychicIntensityPerModuleTable);
		
		return text;
	}


	/* (non-Javadoc)
	 *
	 * @since 12.10.2011 15:35:43
	 * 
	 * @see pa._v38.interfaces.modules.D3_1_send#send_D3_1(int)
	 */
	@Override
	public double send_D3_1(int pnModuleNr) {
        
        double rFreeAvailablePsychicIntensity = 0.0d;
        
        clsPsychicIntensityPerModule oCurrentPsychicIntensityPerModule = moPsychicIntensityPerModuleTable.get(pnModuleNr);
        
        if(oCurrentPsychicIntensityPerModule != null){
            rFreeAvailablePsychicIntensity = oCurrentPsychicIntensityPerModule.getAvailablePsychicIntensity();
        }
        
        return rFreeAvailablePsychicIntensity;
	}


	/* (non-Javadoc)
	 *
	 * @since 12.10.2011 15:35:43
	 * 
	 * @see pa._v38.interfaces.modules.D3_1_receive#receive_D3_1(double)
	 */
	@Override
	public void receive_D3_1(double prFreePsychicIntensity) {
        mrFreePsychicIntensityInStorage += prFreePsychicIntensity;
        logger.clsLogger.getLog("NeutralizedIntensity").debug("Buffer fill level DT3: " + Double.toString(mrFreePsychicIntensityInStorage));
	}
	
	
	
	public double calculatePsychicIntensityDemand(){        
	   
	    double rTotalPsychicIntensityDemand = 0.0d;
	        
	    for(clsPsychicIntensityPerModule oPsychicIntensityPerModule:moPsychicIntensityPerModuleTable.values()){
	         rTotalPsychicIntensityDemand += oPsychicIntensityPerModule.getEstimationRequestedPsychicIntensity();
	    }
	        
	     mrTotalEstimatedPsychicIntensityDemand = rTotalPsychicIntensityDemand;        
	        
	     return mrTotalEstimatedPsychicIntensityDemand;
	 }
	    
	 public double calculateSumPriorities(){
	   	        
	     double rSumModulePriorities = 0.0;
	        
	     for(clsPsychicIntensityPerModule oPsychicIntensityPerModule:moPsychicIntensityPerModuleTable.values()){
	         rSumModulePriorities += oPsychicIntensityPerModule.getModuleStrength()*oPsychicIntensityPerModule.getEstimationRequestedPsychicIntensity();
	     }
	        
	     mrEstimatedSumModulesPriorities = rSumModulePriorities;
	       
	     return mrEstimatedSumModulesPriorities;
	    }
	

	
	/**
	 * Distributes the free Psychic Intensity relative to requested amount 
	 * and requested priority
	 * 
	 */
	 public void divideFreePsychicIntensity(double prTotalPsychicIntensityDemand, double prSumModulesPriority){
	     mrSentPsychicIntensity = 0;   
	     
	        if(moPsychicIntensityPerModuleTable.size() == 0)
	            //no active modules
	            return; 

	        if(prTotalPsychicIntensityDemand <=0 && mrFreePsychicIntensityInStorage >= mrPsychicIntensityContainerLimit){                
	                for(clsPsychicIntensityPerModule oPsychicIntensityPerModule:moPsychicIntensityPerModuleTable.values() ){

	                    oPsychicIntensityPerModule.setAvailablePsychicIntensity(mrFreePsychicIntensityInStorage/ moPsychicIntensityPerModuleTable.size());

	                    mrSentPsychicIntensity += oPsychicIntensityPerModule.getAvailablePsychicIntensity();
	                }
	                mrFreePsychicIntensityInStorage = 0.0d;
	                return;
	        }
	        
	        if(prTotalPsychicIntensityDemand <=0 && mrFreePsychicIntensityInStorage < mrPsychicIntensityContainerLimit){
	                
	                for(clsPsychicIntensityPerModule oPsychicIntensityPerModule:moPsychicIntensityPerModuleTable.values() ){
	                    oPsychicIntensityPerModule.setAvailablePsychicIntensity(0.0d);
	                }
	                return;
	        }
	        
	        
	        if(mrFreePsychicIntensityInStorage >= prTotalPsychicIntensityDemand && mrFreePsychicIntensityInStorage >= mrPsychicIntensityContainerLimit ){             
	                for(clsPsychicIntensityPerModule oPsychicIntensityPerModule:moPsychicIntensityPerModuleTable.values()){
	                    
	                    double rPriority = oPsychicIntensityPerModule.getEstimationRequestedPsychicIntensity()*oPsychicIntensityPerModule.getModuleStrength();
	                  
	                    oPsychicIntensityPerModule.setAvailablePsychicIntensity(oPsychicIntensityPerModule.getEstimationRequestedPsychicIntensity() + (rPriority / prSumModulesPriority));
	                    mrSentPsychicIntensity += oPsychicIntensityPerModule.getAvailablePsychicIntensity();
	                }   
	                mrFreePsychicIntensityInStorage = 0.0d;
	                return;
	        }
	            
	        if(mrFreePsychicIntensityInStorage >= prTotalPsychicIntensityDemand && mrFreePsychicIntensityInStorage < mrPsychicIntensityContainerLimit){
	                
	                double rRestFreePsychicIntensity = mrFreePsychicIntensityInStorage - prTotalPsychicIntensityDemand;
	                
//	                mrFreePsychicIntensityInStorage = rRestFreePsychicIntensity;
	                
	                for(clsPsychicIntensityPerModule oPsychicIntensityPerModule:moPsychicIntensityPerModuleTable.values()){
	                    oPsychicIntensityPerModule.setAvailablePsychicIntensity(oPsychicIntensityPerModule.getRequestedPsychicIntensity());
	                    mrSentPsychicIntensity += oPsychicIntensityPerModule.getAvailablePsychicIntensity();
	                }   
	            mrFreePsychicIntensityInStorage -= mrSentPsychicIntensity;
	            return;
	        }
	        
	        
	        if (mrFreePsychicIntensityInStorage < prTotalPsychicIntensityDemand) {
	            //not enough free PsychicIntensity to full fill all requests
	            //distribute PsychicIntensity depending on request and priority
	            
	            for(clsPsychicIntensityPerModule oPsychicIntensityPerModule:moPsychicIntensityPerModuleTable.values()){
	                double rPriority = oPsychicIntensityPerModule.getRequestedPsychicIntensity()*oPsychicIntensityPerModule.getModuleStrength();
	                
	                oPsychicIntensityPerModule.setAvailablePsychicIntensity(mrFreePsychicIntensityInStorage * (rPriority / prSumModulesPriority));
	                mrSentPsychicIntensity += oPsychicIntensityPerModule.getAvailablePsychicIntensity();
	            }
	            mrFreePsychicIntensityInStorage = 0.0d;
	            return;
	        }   
	    }
	 
	  
	    public void updateEstimations(){

	        //update estimations
	        for(clsPsychicIntensityPerModule oPsychicIntensityPerModule:getPsychicIntensityPerModuleTable().values()){
	            
	            double requestedIntensity = oPsychicIntensityPerModule.getRequestedPsychicIntensity();
	            double moduleStrength = oPsychicIntensityPerModule.getModuleStrength();
	            double consumedIntensity = oPsychicIntensityPerModule.getConsumedPsychicIntensity();
	            
	            oPsychicIntensityPerModule.updateEstimationRequestedPsychicIntensity(requestedIntensity);
	            oPsychicIntensityPerModule.updateEstimationModuleStrength(moduleStrength);
	            oPsychicIntensityPerModule.updateEstimationConsumedPsychicIntensity(consumedIntensity);
	        }
	    }
	    
	    public void updateToZero(){
	        
	        for(int j = 0; j < getPsychicIntensityPerModuleTable().size(); j++){
	            getPsychicIntensityPerModuleTable().get(j).setAvailablePsychicIntensity(0.0d);
	            getPsychicIntensityPerModuleTable().get(j).setConsumedPsychicIntensity(0);  
	        }
	    }
	    
	    

	    /**
	     * @return the psychicIntensityPerModule
	     */
	    public Hashtable<Integer, clsPsychicIntensityPerModule> getPsychicIntensityPerModuleTable() {
	        return moPsychicIntensityPerModuleTable;
	    }

	    /**
	     * @param psychicIntensityPerModule the psychicIntensityPerModule to set
	     */
	    public void setPsychicIntensityPerModuleTable(
	            Hashtable<Integer, clsPsychicIntensityPerModule> poPsychicIntensityPerModule) {
	        this.moPsychicIntensityPerModuleTable = poPsychicIntensityPerModule;
	    }

	    /**
	     * @return the freePsychicIntensityInStorage
	     */
	    public double getFreePsychicIntensityInStorage() {
	        return mrFreePsychicIntensityInStorage;
	    }

	    /**
	     * @param freePsychicIntensityInStorage the freePsychicIntensityInStorage to set
	     */
	    public void setFreePsychicIntensityInStorage(double prFreePsychicIntensityInStorage) {
	        this.mrFreePsychicIntensityInStorage = prFreePsychicIntensityInStorage;
	    }

	    /**
	     * @return the consumedPsychicIntensity
	     */
	    public double getEstimatedConsumedPsychicIntensity() {
	        return mrEstimatedConsumedPsychicIntensity;
	    }

	    /**
	     * @param consumedPsychicIntensity the consumedPsychicIntensity to set
	     */
	    public void setEstimatedConsumedPsychicIntensity() {
	        
	        double prConsumedPsychicIntensity = 0.0;
	        
	        for(clsPsychicIntensityPerModule oPsychicIntensityPerModule:moPsychicIntensityPerModuleTable.values()){
	            
	            prConsumedPsychicIntensity += oPsychicIntensityPerModule.getEstimatedConsumedPsychicIntensity();
	        }   
	        this.mrEstimatedConsumedPsychicIntensity = prConsumedPsychicIntensity;
	    }

	    /**
	     * @return the sentPsychicIntensity
	     */
	    public double getSentPsychicIntensity() {
	        return mrSentPsychicIntensity;
	    }

	    /**
	     * @param sentPsychicIntensity the sentPsychicIntensity to set
	     */
	    public void setSentPsychicIntensity(double prSentPsychicIntensity) {
	        this.mrSentPsychicIntensity = prSentPsychicIntensity;
	    }

	    /**
	     * @return the psychicIntensityThresholdLimit
	     */
	    public double getPsychicIntensityContainerLimit() {
	        return mrPsychicIntensityContainerLimit;
	    }

	    /**
	     * @param psychicIntensityThresholdLimit the psychicIntensityThresholdLimit to set
	     */
	    public void setPsychicIntensityContainerLimit(double prPsychicIntensityContainerLimit) {
	        this.mrPsychicIntensityContainerLimit = prPsychicIntensityContainerLimit;
	    }

	/* (non-Javadoc)
	 *
	 * @since Nov 10, 2012 8:11:13 PM
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
	 * @since Nov 10, 2012 8:11:13 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return null;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 10, 2012 8:11:13 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oValues = new ArrayList<Double>();
		oValues.add(mrTotalEstimatedPsychicIntensityDemand);
		oValues.add(mrEstimatedSumModulesPriorities);
		oValues.add(mrFreePsychicIntensityInStorage);
	    oValues.add(mrEstimatedConsumedPsychicIntensity);
	    oValues.add(mrSentPsychicIntensity);
	    oValues.add(mrPsychicIntensityContainerLimit);
		return oValues;
	}
	

	/* (non-Javadoc)
	 *
	 * @since Nov 10, 2012 8:11:13 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorTimeChartBase#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		oCaptions.add("Total Estimated Psychic Intensity Demand");
		oCaptions.add("Estimated Sum Modules Priorities");
		oCaptions.add("FreePsychic Intensity In Storage");
	    oCaptions.add("Estimated Consumed Psychic Intensity");
	    oCaptions.add("Sent Psychic Intensity");
	    oCaptions.add("Psychic Intensity Container Limit");
		return oCaptions;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 10, 2012 8:11:13 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 20;
	}

	/* (non-Javadoc)
	 *
	 * @since Nov 10, 2012 8:11:13 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.5;
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 12, 2012 5:47:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartTitle()
	 */
	@Override
	public String getStackedBarChartTitle() {
		return "Psychic Intensity Storage";
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 12, 2012 5:47:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartData()
	 */
	@Override
	public ArrayList<ArrayList<Double>> getStackedBarChartData() {
		ArrayList<ArrayList<Double>> oResult = new ArrayList<ArrayList<Double>>();
		ArrayList<Double> received = new ArrayList<Double>();
		ArrayList<Double> remaining = new ArrayList<Double>();
		ArrayList<Double> missing = new ArrayList<Double>();	

		for(clsPsychicIntensityPerModule oPsychicIntensityPerModule:moPsychicIntensityPerModuleTable.values()) {
			received.add(oPsychicIntensityPerModule.getConsumedPsychicIntensity());
			remaining.add(oPsychicIntensityPerModule.getAvailablePsychicIntensity());
			if(oPsychicIntensityPerModule.getConsumedPsychicIntensity() < oPsychicIntensityPerModule.getRequestedPsychicIntensity()) {
				missing.add(oPsychicIntensityPerModule.getRequestedPsychicIntensity() - oPsychicIntensityPerModule.getConsumedPsychicIntensity());	
			} else {
				missing.add(.0);
			}
		}
		
		oResult.add(received);
		oResult.add(remaining);
		oResult.add(missing);	
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 12, 2012 5:47:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartCategoryCaptions()
	 */
	@Override
	public ArrayList<String> getStackedBarChartCategoryCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		oResult.add("received");
		oResult.add("remaining");
		oResult.add("missing");
		return oResult;
	}

	/* (non-Javadoc)
	 *
	 * @since Dec 12, 2012 5:47:29 PM
	 * 
	 * @see pa._v38.interfaces.itfInspectorStackedBarChart#getStackedBarChartColumnCaptions()
	 */
	@Override
	public ArrayList<String> getStackedBarChartColumnCaptions() {
		ArrayList<String> oResult = new ArrayList<String>();
		
		for(clsPsychicIntensityPerModule oPsychicEnergyPerModule:moPsychicIntensityPerModuleTable.values()) {
			oResult.add(Integer.toString(oPsychicEnergyPerModule.getModuleNr()));
		}
		return oResult;
	}
	
	public void informIntensityValues(int rModuleNumber, double rModuleStrenght, double rRequestedPsychicIntensity, double rConsumedPsychicIntensity ){
	       
	    getPsychicIntensityPerModuleTable().get(rModuleNumber).setModuleStrength(rModuleStrenght);
	        
	    getPsychicIntensityPerModuleTable().get(rModuleNumber).setRequestedPsychicIntensity(rRequestedPsychicIntensity);
	        
	    getPsychicIntensityPerModuleTable().get(rModuleNumber).setConsumedPsychicIntensity(rConsumedPsychicIntensity);
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



/**
 * DOCUMENT - insert description
 *
 * @author kollmann
 * @since 30.09.2014 16:42:04
 *
 * @return
 */
public double calculatePleasureProduction() {
    
    double extraPleasureIntensityUseEfficiency = 0;
    
    if(mrTotalEstimatedPsychicIntensityDemand==0){
        extraPleasureIntensityUseEfficiency = 0;
    }else{
        extraPleasureIntensityUseEfficiency  = mrEstimatedConsumedPsychicIntensity/mrTotalEstimatedPsychicIntensityDemand;
    }
    
    return extraPleasureIntensityUseEfficiency;
}
	
}