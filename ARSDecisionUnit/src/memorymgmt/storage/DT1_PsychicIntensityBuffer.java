/**
 * clsLibidoBuffer.java: DecisionUnits - pa.buffer
 * 
 * @author deutsch
 * 09.03.2011, 17:04:55
 */
package memorymgmt.storage;

import inspector.interfaces.clsTimeChartPropeties;
import inspector.interfaces.itfInspectorGenericTimeChart;
import inspector.interfaces.itfInspectorInternalState;
import inspector.interfaces.itfInterfaceDescription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map.Entry;

import memorymgmt.enums.eDrive;
import modules.interfaces.D1_1_receive;
import modules.interfaces.D1_2_receive;
import modules.interfaces.D1_3_receive;
import modules.interfaces.D1_4_send;
import modules.interfaces.D1_5_send;
import modules.interfaces.D1_6_receive;
import modules.interfaces.D1_7_send;
import modules.interfaces.eInterfaces;

import org.slf4j.Logger;

import properties.personality_parameter.clsPersonalityParameterContainer;

import base.datatypes.helpstructures.clsPair;
import base.tools.toText;

/**
 * Buffer for all drives
 * 
 * @author deutsch
 * 09.03.2011, 17:04:55
 * 
 */
public class DT1_PsychicIntensityBuffer implements itfInspectorInternalState, itfInterfaceDescription, itfInspectorGenericTimeChart, D1_4_send, D1_5_send, D1_6_receive, D1_1_receive, D1_2_receive, D1_3_receive, D1_7_send {
    private static final String P_LIBIDO_START_VALUE="LIBIDO_START_VALUE";
    private double mrBufferedLibido;
	private HashMap<eDrive,clsDriveBuffer> moLibidoBuffers;
    private double moLibidoInitValue=0.0;
	protected final Logger log;

	public DT1_PsychicIntensityBuffer(clsPersonalityParameterContainer poPersonalityParameterContainer) {
		mrBufferedLibido = 0;
		moLibidoInitValue= poPersonalityParameterContainer.getPersonalityParameter("DT1", P_LIBIDO_START_VALUE).getParameterDouble();
		moLibidoBuffers = initBuffers();
		log = logger.clsLogger.getLog("PsychicIntensityBuffer");
	}
	
	   public DT1_PsychicIntensityBuffer() {
	        mrBufferedLibido = 0;
	        moLibidoInitValue= 0.0;
	        moLibidoBuffers = initBuffers();
	        log = logger.clsLogger.getLog("PsychicIntensityBuffer");
	    }
	   
	   
	    /**
	     * @since 6 Dec 2018 15:55:58
	     * 
	     * @return the moLibidoBuffers
	     */
	    public HashMap<String, Double> getLibidoMetrics() {
	        HashMap<String, Double> oRetVal = new HashMap<String, Double>();        
	        for (Entry<eDrive, clsDriveBuffer> item : moLibidoBuffers.entrySet()) {
	            eDrive key = item.getKey();
	            clsDriveBuffer value = item.getValue();
	            oRetVal.put(key.toString()+"_Lib", value.getLib());
	            oRetVal.put(key.toString()+"_Aggr", value.getAggr());
	        }
	        return oRetVal;	        
	    }
	    
	    
	private HashMap<eDrive,clsDriveBuffer> initBuffers(){
	    HashMap<eDrive,clsDriveBuffer> oRetVal = new HashMap<eDrive,clsDriveBuffer>();
	    oRetVal.put(eDrive.STOMACH, new clsDriveBuffer());
	    oRetVal.put(eDrive.RECTUM, new clsDriveBuffer());
	    oRetVal.put(eDrive.STAMINA, new clsDriveBuffer());
	    oRetVal.put(eDrive.ORAL, new clsDriveBuffer(moLibidoInitValue/8,moLibidoInitValue/8));
	    oRetVal.put(eDrive.ANAL, new clsDriveBuffer(moLibidoInitValue/8,moLibidoInitValue/8));
	    oRetVal.put(eDrive.GENITAL, new clsDriveBuffer(moLibidoInitValue/8,moLibidoInitValue/8));
	    oRetVal.put(eDrive.PHALLIC, new clsDriveBuffer(moLibidoInitValue/8,moLibidoInitValue/8));
	    
	    return oRetVal;
	}
	



/*	private void normalizeBuffers() {
		//Max value = 1, min value = 0.
		for(Map.Entry<eDrive,clsPair<Double,Double>> oDrive: moLibidoBuffers.entrySet()){
			
			if(oDrive.getValue().a > 1.0){
			    oDrive.getValue().a = 1.0;
			    //if it is not possible to add it to the aggressive part add it to the libidinous
			    oDrive.getValue().b += oDrive.getValue().a -1.0;
			}
			else if(oDrive.getValue().a < 0.0) oDrive.getValue().a = 0.0;
			
	        if(oDrive.getValue().b > 1.0) oDrive.getValue().b = 1.0;
	        else if(oDrive.getValue().b < 0.0) oDrive.getValue().b = 0.0;
	        
	        double rSum =oDrive.getValue().a + oDrive.getValue().b;
	        if(rSum > 1.0){
	            oDrive.getValue().a -= (rSum - 1.0)/2;
	            oDrive.getValue().b -= (rSum - 1.0)/2;
	        }
		}
	}
	*/
	
	 /** check if buffer available 
     * if not initialize new buffer
     */
    private boolean checkBuffer(eDrive peType){
        if(moLibidoBuffers.containsKey(peType)){
            return true;
        }
        else{
            moLibidoBuffers.put(peType, new clsDriveBuffer());
            return false;
        }
    }
    private clsPair<Double,Double> shiftQoA (clsPair<Double,Double> prValues, double prRatio){
        double rSum = prValues.a + prValues.b;
        double rAggrFactor = prValues.a/rSum;
        double d = 0.0;
        double k = 0.0;
        if(prRatio>=0.5){
            k=(1-prRatio)/0.5;
            d=1-k;
        }
        else{
            k=prRatio/0.5;
            d=0.0;
        }
        rAggrFactor = rAggrFactor*k + d;
        
        return new clsPair<Double,Double>(rSum*rAggrFactor,rSum*(1-rAggrFactor));
    }
    
    public clsPair<Double,Double> shiftQoA_V2 (double poAggrOld, double poLibOld, double prRatio){
        double rSum = poAggrOld + poLibOld;
        double rAggrFactor = poAggrOld/rSum;
        double rFactorChange = prRatio -0.5;
        
        double rAggrfactorNew =rAggrFactor+  prRatio;
        
        return new clsPair<Double,Double>(rSum*rAggrfactorNew,rSum*(1-rAggrfactorNew));
    }
	
    /**
     * Write access to libido storage
     * Sets both drive components
     * 1. aggressiv
     * 2. libidinous
     * 
     * @author herret
     * 15.5.2013
     * 
     */
   @Override
   public void receive_D1_1(eDrive peType, clsPair<Double,Double> oValues) {
       checkBuffer(peType);
       moLibidoBuffers.get(peType).setAggr(oValues.a);
       moLibidoBuffers.get(peType).setLib(oValues.b);

       //normalizeBuffers();
       log.debug(moLibidoBuffers.toString());
   }
	
   
   /**
    * Write access to libido storage
    * increases both drive components
    * 1. aggressiv
    * 2. libidinous
    * 
    * @author herret
    * 15.5.2013
    * 
    */
  @Override
  public void receive_D1_2(eDrive peType, clsPair<Double,Double> oValues) {
      checkBuffer(peType);
      moLibidoBuffers.get(peType).incAggr(oValues.a);
      moLibidoBuffers.get(peType).incLib(oValues.b);
      log.debug(moLibidoBuffers.toString());
  }
  
  /**
   * Write access to libido storage
   * decreases both drive components
   * 1. aggressiv
   * 2. libidinous
   * 
   * @author herret
   * 15.5.2013
   * 
   */
 @Override
 public void receive_D1_3(eDrive peType, clsPair<Double,Double> oValues) {
     
     
     checkBuffer(peType);
     
     moLibidoBuffers.get(peType).decAggr(oValues.a);
     moLibidoBuffers.get(peType).decLib(oValues.b);
     log.debug(moLibidoBuffers.toString());
 }

 /**
  * returns drive
  * 1. aggressiv
  * 2. libidinous
  * 
  * @author herret
  * 15.5.2013
  * 
  */
 @Override
 public clsPair<Double,Double> send_D1_4(eDrive peType) {
     return new clsPair<Double,Double> (moLibidoBuffers.get(peType).getAggr(),moLibidoBuffers.get(peType).getLib());
 }


/**
 * returns drive
 * 1. aggressiv
 * 2. libidinous
 * 
 * @author herret
 * 15.5.2013
 * 
 */
@Override
public HashMap<eDrive,clsPair<Double,Double>> send_D1_5() {
    HashMap<eDrive,clsPair<Double,Double>> oRetVal = new HashMap<eDrive,clsPair<Double,Double>> ();
    for(eDrive oDrive: moLibidoBuffers.keySet()){
        oRetVal.put(oDrive, new clsPair<Double,Double>(moLibidoBuffers.get(oDrive).getAggr(),moLibidoBuffers.get(oDrive).getLib()));
    }
    return oRetVal;
}


/**
 * shift drive components 
 * 
 * @author herret
 * 15.5.2013
 * 
 */
@Override
public void receive_D1_6(eDrive oDrive, Double oShiftFactor) {
    clsPair<Double,Double> newValues =shiftQoA_V2(moLibidoBuffers.get(oDrive).getAggr(),moLibidoBuffers.get(oDrive).getAggr(),oShiftFactor);
    
    checkBuffer(oDrive);
    moLibidoBuffers.get(oDrive).setAggr(newValues.a);
    moLibidoBuffers.get(oDrive).setLib(newValues.b);
    
    log.debug(moLibidoBuffers.toString());
    
    return;
}

/**
 * returns drive
 * 1. aggressiv
 * 2. libidinous
 * 
 * @author herret
 * 15.5.2013
 * 
 */
@Override
public clsPair<Boolean,Boolean> send_D1_7(eDrive peType) {
    checkBuffer(peType);
    clsPair<Boolean,Boolean> oRetVal = new clsPair<Boolean,Boolean>(moLibidoBuffers.get(peType).aggressivDecreased(),moLibidoBuffers.get(peType).libidinousDecreased());
    
    return oRetVal;
}

   
   /* (non-Javadoc)
   *
   * @author deutsch
   * 09.03.2011, 17:16:00
   * 
   * @see pa.interfaces.receive._v38.D1_3_receive#receive_D1_3(double)
   */
/*  @Override
  public void receive_D1_3(double prValue) {

      //all storages are reduced by the same amount
      double rReduceValue = prValue / getNumberOfBuffers();
      for(Map.Entry<eDrive,HashMap<eDriveComponent,Double>> oDrive: moLibidoBuffers.entrySet()){
          for(Map.Entry<eDriveComponent, Double> oDriveComponent : oDrive.getValue().entrySet()){
                  oDriveComponent.setValue(oDriveComponent.getValue() - rReduceValue);
          }
      }
      normalizeBuffers();
  }
	
	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 4:05:05 PM
	 * 
	 * @see pa._v38.interfaces.modules.D1_5_receive#receive_D1_5(pa._v38.memorymgmt.enums.eSexualDrives)
	 */
//	@Override
/*	public void receive_D1_5(double prValue, eDrive peType) {
		moLibidoBuffers.put(peType , moLibidoBuffers.get(peType)-prValue);
		normalizeBuffers();
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:21:00
	 * 
	 * @see pa.interfaces.send._v38.D1_4_send#send_D1_4()
	 */
/*	@Override
	public Double send_D1_4() {
		Double rRetValue=0.0;
		for(Double rValue : moLibidoBuffers.values()){
			rRetValue += rValue;
			
		}
		return rRetValue;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:21:00
	 * 
	 * @see pa.interfaces.send._v38.D1_2_send#send_D1_2()
	 */
/*	@Override
	public double send_D1_2(eDrive peType) {
		return moLibidoBuffers.get(peType);
	}
*/
	@Override
	public String toString() {
		String oRetVal ="ORAL: "+moLibidoBuffers.get(eDrive.ORAL) + "\n"
		+ "ANAL: "+moLibidoBuffers.get(eDrive.ANAL) + "\n"
		+ "PHALLIC: "+moLibidoBuffers.get(eDrive.PHALLIC) + "\n"
		+ "GENITAL: "+moLibidoBuffers.get(eDrive.GENITAL);
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 15:02:51
	 * 
	 * @see pa._v38.interfaces.itfInspectorInternalState#stateToHTML()
	 */
	@Override
	public String stateToTEXT() {
		String text = "";
		
		text += toText.h1("Libido Buffer");
		text += toText.valueToTEXT("mrBufferedLibido", mrBufferedLibido);
		
		return text;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 20:16:46
	 * 
	 * @see pa.interfaces._v38.itfTimeChartInformationContainer#getTimeChartData()
	 */
	@Override
	public ArrayList<Double> getTimeChartData() {
		ArrayList<Double> oValues = new ArrayList<Double>();
		
	      for(clsDriveBuffer driveVal : moLibidoBuffers.values()){
	          oValues.add(driveVal.getAggr());
	          oValues.add(driveVal.getLib());
	        }
		
		return oValues;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 15.04.2011, 20:16:46
	 * 
	 * @see pa.interfaces._v38.itfTimeChartInformationContainer#getTimeChartCaptions()
	 */
	@Override
	public ArrayList<String> getTimeChartCaptions() {
		ArrayList<String> oCaptions = new ArrayList<String>();
		for(eDrive drive : moLibidoBuffers.keySet()){
		    oCaptions.add(drive.toString()+" Agressiv");
		    oCaptions.add(drive.toString()+" Libidinous");
		}
		return oCaptions;
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:42:50
	 * 
	 * @see pa.interfaces._v38.itfInspectorGenericTimeChart#getTimeChartAxis()
	 */
	@Override
	public String getTimeChartAxis() {
		return "Libido";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:42:50
	 * 
	 * @see pa.interfaces._v38.itfInspectorGenericTimeChart#getTimeChartTitle()
	 */
	@Override
	public String getTimeChartTitle() {
		return "Libido Chart";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:42:50
	 * 
	 * @see pa.interfaces._v38.itfInspectorGenericTimeChart#getTimeChartUpperLimit()
	 */
	@Override
	public double getTimeChartUpperLimit() {
		return 1.05;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 10:42:50
	 * 
	 * @see pa.interfaces._v38.itfInspectorGenericTimeChart#getTimeChartLowerLimit()
	 */
	@Override
	public double getTimeChartLowerLimit() {
		return -0.05;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 15:50:51
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getDescription()
	 */
	@Override
	public String getDescription() {
		return "The two modules which are communicating via the libido buffer are {E41} and {E45}.  {E41} is responsible of adding incoming libido to the buffer while {E45} removes libido whenever possible. This results in a reduction of the libido tension and thus in pleasure gain. For this purpose a libido buffer is introduced. It is independent from the rest of the memory system. Figure X shows how this buffer is connected to the two modules. Each module is connected with two interfaces - read access ({D1.2}, {D1.4}) and write access ({D1.1}, {D1.3}). The libido buffer stores a single quantitative value denoting the accumulated libido and its corresponding tension. This concludes the description of the final functional model. The implementation of it and its memory is described in Section X. This chapter is concluded with a technical view on the model and its critical implementation path.";
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 15:50:51
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesRecv()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesRecv() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D1_1, eInterfaces.D1_3) );
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 21.04.2011, 15:50:51
	 * 
	 * @see pa._v38.interfaces.itfInterfaceDescription#getInterfacesSend()
	 */
	@Override
	public ArrayList<eInterfaces> getInterfacesSend() {
		return new ArrayList<eInterfaces>( Arrays.asList(eInterfaces.D1_2, eInterfaces.D1_4) );
	}
	
	public void saveOld() {
	    for(clsDriveBuffer oDrive :moLibidoBuffers.values()){
	        oDrive.newStep();
	    }
	}

    class clsDriveBuffer{
        private double moAggrValue=0.0;
        private double moLibValue=0.0;
        private double moAggrValueOld=0.0;
        private double moLibValueOld=0.0;
        
        
        public clsDriveBuffer(){
            
        }
        public clsDriveBuffer(double poAggrInit, double poLibInit){
            moAggrValue = poAggrInit;
            moAggrValueOld = poAggrInit;
            moLibValue =poLibInit;
            moLibValueOld =  poLibInit;
        }
        public void newStep(){
            moAggrValueOld=moAggrValue;
            moLibValueOld=moLibValue;
        }
        
        
        public boolean aggressivDecreased(){
            if(moAggrValueOld>moAggrValue) return true;
            else return false;
        }
        public boolean libidinousDecreased(){
            if(moLibValueOld>moLibValue) return true;
            else return false;
        }
        
        public void incAggr(double poValue){
            moAggrValue += poValue;
            if(moAggrValue> 1.0){
                double tmp = moAggrValue -1.0;
                moAggrValue = 1.0;
                moLibValue += tmp;
                if(moLibValue> 1.0)moLibValue =1.0; 
            }
        }
        
        public void decAggr(double poValue){
            moAggrValue -= poValue;
            if(moAggrValue< 0.0){
                double tmp = moAggrValue*-1;
                moAggrValue = 0.0;
                moLibValue -= tmp;
                if(moLibValue< 0.0)moLibValue =0.0; 
            }
        }
        
        public void setAggr(double poValue){
            if(poValue > 1.0) moAggrValue=1.0;
            else if(poValue < 0.0) moAggrValue=0.0;
            else moAggrValue=poValue;
        }
        
        
        public void incLib(double poValue){
            moLibValue += poValue;
            if(moLibValue> 1.0){
                double tmp = moLibValue -1.0;
                moLibValue = 1.0;
                moAggrValue += tmp;
                if(moAggrValue> 1.0)moAggrValue =1.0; 
            }
        }
        
        public void decLib(double poValue){
            moLibValue -= poValue;
            if(moLibValue< 0.0){
                double tmp = moLibValue*-1;
                moLibValue = 0.0;
                moAggrValue -= tmp;
                if(moAggrValue< 0.0)moAggrValue =0.0; 
            }
        }
        
        public void setLib(double poValue){
            if(poValue > 1.0) moLibValue=1.0;
            else if(poValue < 0.0) moLibValue=0.0;
            else moLibValue=poValue;
        }
        
        public double getAggr(){
            return moAggrValue;
        }
        public double getLib(){
            return moLibValue;
        }
        
        @Override
        public String toString(){
            return "Aggr: "+moAggrValue+" ("+moAggrValueOld+"); Lib: "+ moLibValue+" ("+moLibValueOld+")";
        }
        
        
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

