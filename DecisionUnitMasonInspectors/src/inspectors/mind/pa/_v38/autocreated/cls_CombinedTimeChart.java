/**
 * CHANGELOG
 *
 * Sep 27, 2012 herret - File created
 *
 */
package inspectors.mind.pa._v38.autocreated;

import java.awt.GridLayout;
import java.util.ArrayList;

import pa._v38.interfaces.itfInspectorCombinedTimeChart;
import pa._v38.interfaces.itfInspectorGenericTimeChart;
import sim.portrayal.Inspector;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Sep 27, 2012, 11:33:16 AM
 * 
 */
public class cls_CombinedTimeChart extends Inspector{

	private static final long serialVersionUID = 1836759324746797113L;
	

	
	private itfInspectorCombinedTimeChart moContainer;
	private ArrayList<Inspector> moCharts;
	private ArrayList<DataContainer> moData;
	
	private boolean showRangeLabel =true;
	
	
	
	/**
	 * @since Oct 10, 2012 10:43:38 AM
	 * 
	 * @param showRangeLabel the showRangeLabel to set
	 */
	public void setShowRangeLabel(boolean showRangeLabel) {
		this.showRangeLabel = showRangeLabel;
	}

	public class DataContainer implements itfInspectorGenericTimeChart{
		protected String moTitle;
		protected String moAxis;
		protected ArrayList<Double> moData;
		protected ArrayList<String> moCaptions;
		
		
		public DataContainer(String poTitle, String poAxis, ArrayList<Double> poData, ArrayList<String> poCaptions){
			moTitle = poTitle;
			moAxis = poAxis;
			moData = poData;
			moCaptions = poCaptions;	
		}
		@Override
		public String getTimeChartAxis() {
			return moAxis;
		}
		@Override
		public String getTimeChartTitle() {
			return moTitle;
		}
		@Override
		public ArrayList<Double> getTimeChartData() {
			return moData;
		}
		@Override
		public ArrayList<String> getTimeChartCaptions() {
			return moCaptions;
		}
		@Override
		public double getTimeChartUpperLimit() {
			return 1.0;
		}
		@Override
		public double getTimeChartLowerLimit() {
			return 0;
		}	
	}
		
	public cls_CombinedTimeChart(itfInspectorCombinedTimeChart poContainer){
		moContainer = poContainer;
		moCharts = new ArrayList<Inspector>();
		moData = new ArrayList<DataContainer>();
		int iColumns=(int) Math.ceil(Math.sqrt(moContainer.getCombinedTimeChartData().size()));
		this.setLayout(new GridLayout(moContainer.getCombinedTimeChartData().size()/iColumns,iColumns));
		for(int i=0; i <  moContainer.getCombinedTimeChartData().size(); i++){
			DataContainer iData = new DataContainer(moContainer.getChartTitles().get(i),moContainer.getCombinedTimeChartAxis(),moContainer.getCombinedTimeChartData().get(i),moContainer.getValueCaptions().get(i));
			cls_GenericTimeChartInspector iContainer = new cls_GenericTimeChartInspector(iData,200,750/iColumns,550/iColumns);
			iContainer.setShowRangeLabel(false);
			moCharts.add(iContainer);
			moData.add(iData);
			add(iContainer);
		}
	}

	
	@Override
	public void updateInspector() {
		updateData();
		for (Inspector i : moCharts){
			i.updateInspector();
		}
	}
	
	private void updateData(){
		for(int i=0; i <  moContainer.getCombinedTimeChartData().size(); i++){
			moData.get(i).moData = moContainer.getCombinedTimeChartData().get(i);
		}
	}
}