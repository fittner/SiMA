/**
 * clsLibidoBuffer.java: DecisionUnits - pa.buffer
 * 
 * @author deutsch
 * 09.03.2011, 17:04:55
 */
package pa._v38.storage;

import java.util.ArrayList;
import java.util.Arrays;

import pa._v38.interfaces.itfInspectorGenericTimeChart;
import pa._v38.interfaces.itfInspectorInternalState;
import pa._v38.interfaces.itfInterfaceDescription;
import pa._v38.interfaces.modules.D1_1_receive;
import pa._v38.interfaces.modules.D1_2_send;
import pa._v38.interfaces.modules.D1_3_receive;
import pa._v38.interfaces.modules.D1_4_send;
import pa._v38.interfaces.modules.eInterfaces;

import pa._v38.tools.toText;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 09.03.2011, 17:04:55
 * 
 */
public class DT1_LibidoBuffer implements itfInspectorInternalState, itfInterfaceDescription, itfInspectorGenericTimeChart, D1_2_send, D1_4_send, D1_1_receive, D1_3_receive  {
	private double mrBufferedLibido;
	
	public DT1_LibidoBuffer() {
		mrBufferedLibido = 0;
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
		//Min value = 0. A bigger value causes = 0
		if (mrBufferedLibido-prValue >= 0) { 
			mrBufferedLibido -= prValue;
		} else {
			mrBufferedLibido = 0;
		}
		
		normalizeBuffer();
	}
	
	private void normalizeBuffer() {
		//Max value = 1, min value = 0.
		if (mrBufferedLibido > 1) {
			mrBufferedLibido = 1;
		} else if (mrBufferedLibido < 0) {
			mrBufferedLibido = 0;
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
	public void receive_D1_1(double prValue) {
		//Only values < are allowed. Total value > 1 is cut of
		if (mrBufferedLibido+prValue <= 1) {
			mrBufferedLibido += prValue;
		} else {
			mrBufferedLibido = 1;	
		}		
		
		normalizeBuffer();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:21:00
	 * 
	 * @see pa.interfaces.send._v38.D1_4_send#send_D1_4()
	 */
	@Override
	public double send_D1_4() {
		return mrBufferedLibido;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 09.03.2011, 17:21:00
	 * 
	 * @see pa.interfaces.send._v38.D1_2_send#send_D1_2()
	 */
	@Override
	public double send_D1_2() {
		return mrBufferedLibido;
	}

	@Override
	public String toString() {
		return "libido: "+mrBufferedLibido;
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
		
		oValues.add(mrBufferedLibido);
		
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
		oCaptions.add("Total Libido");
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
