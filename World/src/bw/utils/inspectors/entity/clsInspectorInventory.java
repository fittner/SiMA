/**
 * CHANGELOG
 *
 * 29.03.2013 Jordakieva - File created
 *
 */
package bw.utils.inspectors.entity;

import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import panels.TextOutputPanel;
import bw.body.clsComplexBody;
import bw.entities.clsARSIN;
import bw.entities.clsMobile;
import bw.entities.tools.clsInventory;
import bw.factories.clsSingletonMasonGetter;
import bw.utils.inspectors.clsInspectorPieChart;
import bw.utils.tools.clsContentColumn;
import sim.portrayal.Inspector;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (Jordakieva) - insert description 
 * 
 * @author Jordakieva
 * 29.03.2013, 16:06:02
 * 
 */
public class clsInspectorInventory extends Inspector {
	
	//private static final TextOutputPanel TextOutputPanel () = null;
	private clsInventory moInventory;
	private TextOutputPanel moText, moText3;
	private DefaultPieDataset moPieDataset;
	private clsInspectorPieChart moInspectorPie;
	private int mnItemsCount;
	private ChartPanel moChartOfDeath;
	private clsARSIN moArsin;
	private clsContentColumn moStaminaContent;
	//private XYSeriesCollection moXYCollection1;
	private XYSeries moXYSeriesWeight, moXYSeriesMaxWeight, moXYSeriesStamina;
	
	public clsInspectorInventory (clsARSIN poArsin)	{

		moInventory = poArsin.getInventory();
		moArsin = poArsin;
		moStaminaContent = ((clsComplexBody)moArsin.getBody()).getInternalSystem().getStaminaSystem().getStamina();
				
		moPieDataset = new DefaultPieDataset ();
		moInspectorPie = new clsInspectorPieChart (moPieDataset);
		mnItemsCount = moInventory.getItemCount();
				
		//Initializing and setting up the Layout
		JPanel oContent = new JPanel (); //Layout in Layout
		moText = new TextOutputPanel();
		moText3 = new TextOutputPanel();
		
		moXYSeriesWeight = new XYSeries ("Weight");
		moXYSeriesMaxWeight = new XYSeries ("max Weight");
		moXYSeriesStamina = new XYSeries ("Stamina");
		
		moChartOfDeath = new ChartPanel(createXYChart());
		
		
		
		//set the layoutmanager to two lines and one row
		setLayout (new GridLayout(2,1));
		//inner-layoutmanager one line and two rows
		oContent.setLayout(new GridLayout(1,2));
		
		try {
			oContent.add (moText);
			oContent.add (moInspectorPie);
			add (oContent);
			add (moChartOfDeath);
		} catch (Exception e) { 
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
		buildDataset (); //to inizialize the empty Dataset
		
	}
	
	private JFreeChart createXYChart () {
		
		XYSeriesCollection oXYCollection1, oXYCollectionStamina; //das beinhaltet mehrere Serials. Serials beinhalten mehrere Wert-Paare
											// da wir verschiedene LineCharts haben werden, bräuchten wir später zwei Collections mit je einer Serials drinnen
		
		
		oXYCollection1 = new XYSeriesCollection ();
		oXYCollection1.addSeries(moXYSeriesMaxWeight);
		oXYCollection1.addSeries(moXYSeriesWeight);		
		
		oXYCollectionStamina = new XYSeriesCollection ();
		oXYCollectionStamina.addSeries(moXYSeriesStamina);
				
		//JFreeChart oXYChart = ChartFactory.createXYLineChart("Stamina Behaviour to Weight" , "Time", "Value", oXYCollection1, PlotOrientation.VERTICAL, true, false, false);
		JFreeChart oXYChart = ChartFactory.createXYStepChart("Stamina Behaviour to Weight", "Time", "Value", oXYCollection1, PlotOrientation.VERTICAL, true, false, false);
		
		XYPlot oPlot = (XYPlot) oXYChart.getPlot();
		
		{
			XYLineAndShapeRenderer oRenderer = new XYLineAndShapeRenderer(true, false);
			oPlot.setDataset(1, oXYCollectionStamina);
			oPlot.setRenderer(1, oRenderer);
		}
				
		return oXYChart;
	}
	
	// hier kommt ein Kommentar auf english :D
	
	public void inventoryItemsToChart () {
				
		long nCurrentTime = clsSingletonMasonGetter.getSimState().schedule.getSteps();
		double rStamina = moStaminaContent.getContent() / moStaminaContent.getMaxContent() * moInventory.getMaxMass();
		
		moXYSeriesWeight.add(nCurrentTime, moInventory.getMass());
		moXYSeriesMaxWeight.add(nCurrentTime, moInventory.getMaxMass());
		moXYSeriesStamina.add(nCurrentTime, rStamina);
		
		if (mnItemsCount != moInventory.getItemCount()) {
			buildDataset ();
		}
		
	}
	
	private void buildDataset () {
		
		clsMobile oItem;
		int i;
		double rRemainingWeight = 0;
		
		mnItemsCount = moInventory.getItemCount();
		rRemainingWeight = moInventory.getMaxMass();
		
		try {
			for (i = 0; i < mnItemsCount; i++) {
				oItem = moInventory.getInventoryItem (i); //zu oft ausgelesen vom oItem
				moPieDataset.insertValue(i, oItem.getId(), oItem.getTotalWeight());
				rRemainingWeight -= oItem.getTotalWeight();
			}
			moPieDataset.insertValue(i, "Empty", rRemainingWeight);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("InventoryItems: Index out of bound");
		}
	}

	@Override
	public void updateInspector() {
		// TODO (Jordakieva) - Auto-generated method stub
		
		try {
			if (isVisible()) {
				moText.setText(moInventory.toText());
				moText3.setText("ober");
				inventoryItemsToChart ();
			}
		} catch (Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}

	}

}
