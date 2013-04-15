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

import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.modules.D1_1_receive;
import pa._v38.interfaces.modules.D1_2_send;
import pa._v38.interfaces.modules.D1_3_receive;
import pa._v38.interfaces.modules.D1_4_send;
import pa._v38.interfaces.modules.D1_5_receive;
import pa._v38.interfaces.modules.eInterfaces;
import pa._v38.memorymgmt.enums.eSexualDrives;

import pa._v38.tools.toText;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.03.2011, 17:04:55
 * 
 */
public class DT1_LibidoBuffer implements itfInspectorInternalState, itfInterfaceDescription, itfInspectorGenericTimeChart, D1_2_send, D1_4_send, D1_1_receive, D1_3_receive, D1_5_receive {
	private double mrBufferedLibido;
	private HashMap<eSexualDrives,Double> moLibidoBuffers;
	
	public DT1_LibidoBuffer() {
		mrBufferedLibido = 0;
		moLibidoBuffers = new HashMap<eSexualDrives,Double>();
		moLibidoBuffers.put(eSexualDrives.ANAL, 0.0);
		moLibidoBuffers.put(eSexualDrives.ORAL, 0.0);
		moLibidoBuffers.put(eSexualDrives.GENITAL, 0.0);
		moLibidoBuffers.put(eSexualDrives.PHALLIC, 0.0);
		
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:16:00
	 * 
	 * @see pa.interfaces.receive._v38.D1_3_receive#receive_D1_3(double)
	 */
	@Override
	public void receive_D1_3(double prValue) {

		//all partial libido storages get reduced by the same amount
		double rReduceValue = prValue / moLibidoBuffers.size();
		for(eSexualDrives eType: moLibidoBuffers.keySet()){
			moLibidoBuffers.put(eType , moLibidoBuffers.get(eType)-rReduceValue);
			
		}		
		normalizeBuffers();
	}
	
	private void normalizeBuffers() {
		//Max value = 1, min value = 0.
		for(eSexualDrives eType: moLibidoBuffers.keySet()){
			if (moLibidoBuffers.get(eType) > 1) {
				moLibidoBuffers.put(eType , 1.0);
			} else if (moLibidoBuffers.get(eType) < 0) {
				moLibidoBuffers.put(eType , 0.0);
			}
		}
	}
	
	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:16:00
	 * 
	 * @see pa.interfaces.receive._v38.D1_1_receive#receive_D1_1(double)
	 */
	@Override
	public void receive_D1_1(eSexualDrives peType, double prValue) {
		
		double rBufferValue;
		if(moLibidoBuffers.containsKey(peType)){
			rBufferValue = moLibidoBuffers.get(peType);
		}
		else{
			rBufferValue=0.0;
		}
		
		//Only values < are allowed. Total value > 1 is cut of
		if (rBufferValue+prValue <= 1) { 
			rBufferValue += prValue;
		} else {
			rBufferValue = 1;
		}
		
		moLibidoBuffers.put(peType, rBufferValue);
		
		normalizeBuffers();
	}
	
	/* (non-Javadoc)
	 *
	 * @since Apr 2, 2013 4:05:05 PM
	 * 
	 * @see pa._v38.interfaces.modules.D1_5_receive#receive_D1_5(pa._v38.memorymgmt.enums.eSexualDrives)
	 */
	@Override
	public void receive_D1_5(double prValue, eSexualDrives peType) {
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
	@Override
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
	@Override
	public double send_D1_2(eSexualDrives peType) {
		return moLibidoBuffers.get(peType);
	}

	@Override
	public String toString() {
		String oRetVal ="ORAL: "+moLibidoBuffers.get(eSexualDrives.ORAL) + "\n"
		+ "ANAL: "+moLibidoBuffers.get(eSexualDrives.ANAL) + "\n"
		+ "PHALLIC: "+moLibidoBuffers.get(eSexualDrives.PHALLIC) + "\n"
		+ "GENITAL: "+moLibidoBuffers.get(eSexualDrives.GENITAL);
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
		
	      for(double driveVal : moLibidoBuffers.values()){
	          oValues.add(driveVal);
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
		for(eSexualDrives drive : moLibidoBuffers.keySet()){
		    oCaptions.add(drive.toString());
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
