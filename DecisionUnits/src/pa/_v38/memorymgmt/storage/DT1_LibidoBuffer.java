/**
 * clsLibidoBuffer.java: DecisionUnits - pa.buffer
 * 
 * @author deutsch
 * 09.03.2011, 17:04:55
 */
package pa._v38.memorymgmt.storage;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.modules.D1_1_receive;
import pa._v38.interfaces.modules.D1_2_receive;
import pa._v38.interfaces.modules.D1_3_receive;
import pa._v38.interfaces.modules.D1_4_send;
import pa._v38.interfaces.modules.D1_5_send;
import pa._v38.interfaces.modules.D1_6_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.enums.eDrive;

import pa._v38.tools.clsPair;
import pa._v38.tools.toText;

/**
 * Buffer for all drives
 * 
 * @author deutsch
 * 09.03.2011, 17:04:55
 * 
 */
public class DT1_LibidoBuffer implements itfInspectorInternalState, itfInterfaceDescription, itfInspectorGenericTimeChart, D1_4_send, D1_5_send, D1_6_receive, D1_1_receive, D1_2_receive, D1_3_receive {
	private double mrBufferedLibido;
	private HashMap<eDrive,clsPair<Double,Double>> moLibidoBuffers;
	
	public DT1_LibidoBuffer() {
		mrBufferedLibido = 0;
		moLibidoBuffers = initBuffers();

	}
	private HashMap<eDrive,clsPair<Double,Double>> initBuffers(){
	    HashMap<eDrive,clsPair<Double,Double>> oRetVal = new HashMap<eDrive,clsPair<Double,Double>>();
	    oRetVal.put(eDrive.STOMACH, new clsPair<Double,Double>(0.0,0.0));
	    oRetVal.put(eDrive.RECTUM, new clsPair<Double,Double>(0.0,0.0));
	    oRetVal.put(eDrive.STAMINA, new clsPair<Double,Double>(0.0,0.0));
	    oRetVal.put(eDrive.ORAL, new clsPair<Double,Double>(0.0,0.0));
	    oRetVal.put(eDrive.ANAL, new clsPair<Double,Double>(0.0,0.0));
	    oRetVal.put(eDrive.GENITAL, new clsPair<Double,Double>(0.0,0.0));
	    oRetVal.put(eDrive.PHALLIC, new clsPair<Double,Double>(0.0,0.0));
	    
	    return oRetVal;
	}

	

	private void normalizeBuffers() {
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
	
	
	 /** check if buffer available 
     * if not initialize new buffer
     */
    private boolean checkBuffer(eDrive peType){
        if(moLibidoBuffers.containsKey(peType)){
            return true;
        }
        else{
            moLibidoBuffers.put(peType, new clsPair<Double,Double>(0.0,0.0));
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
    
    public clsPair<Double,Double> shiftQoA_V2 (clsPair<Double,Double> prValues, double prRatio){
        double rSum = prValues.a + prValues.b;
        double rAggrFactor = prValues.a/rSum;
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
       
       moLibidoBuffers.put(peType, oValues);

       normalizeBuffers();
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
      
      clsPair<Double,Double> oDriveValues = moLibidoBuffers.get(peType);
      oDriveValues.a += oValues.a;
      if(oDriveValues.a > 1.0) oDriveValues.b += oDriveValues.a-1.0;
      oDriveValues.b += oValues.b;
      if(oDriveValues.b > 1.0) oDriveValues.a += oDriveValues.b-1.0;
      moLibidoBuffers.put(peType, oDriveValues);

      normalizeBuffers();
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
     
     clsPair<Double,Double> oDriveValues = moLibidoBuffers.get(peType);
     oDriveValues.a -= oValues.a;
     if(oDriveValues.a < 0.0) oDriveValues.b += oDriveValues.a;
     oDriveValues.b -= oValues.b;
     if(oDriveValues.b < 0.0) oDriveValues.a += oDriveValues.b;
     moLibidoBuffers.put(peType, oDriveValues);

     normalizeBuffers();
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
     return moLibidoBuffers.get(peType);
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
    return moLibidoBuffers;
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
    clsPair<Double,Double> newValues =shiftQoA_V2(moLibidoBuffers.get(oDrive),oShiftFactor);
    moLibidoBuffers.put(oDrive, newValues);
    
    return;
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
		
	      for(clsPair<Double,Double> driveVal : moLibidoBuffers.values()){
	          oValues.add(driveVal.a);
	          oValues.add(driveVal.b);
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

	
}
