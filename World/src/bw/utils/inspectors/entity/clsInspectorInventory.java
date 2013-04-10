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
	
	private clsInventory moInventory;
	private TextOutputPanel moText;
	private DefaultPieDataset moPieDataset;
	private int mnItemsCount;
	private ChartPanel moChartOfDeath;
	private clsARSIN moArsin;
	private clsContentColumn moStaminaContent;
	private ChartPanel moCakeChart;
	private XYSeries moXYSeriesWeight, moXYSeriesMaxWeight, moXYSeriesStamina;
	
	public clsInspectorInventory (clsARSIN poArsin)	{

		moInventory = poArsin.getInventory();
		moArsin = poArsin;
		moStaminaContent = ((clsComplexBody)moArsin.getBody()).getInternalSystem().getStaminaSystem().getStamina();
				
		moPieDataset = new DefaultPieDataset ();
		moCakeChart = new ChartPanel (createPieChart());
			
		mnItemsCount = -1;
				
		//Initializing and setting up the Layout
		JPanel oContent = new JPanel (); //Layout in Layout
		moText = new TextOutputPanel();
		
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
			oContent.add (moCakeChart);
			//oContent.add (moInspectorPie);
			add (oContent);
			add (moChartOfDeath);
		} catch (Exception e) { 
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
		
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
	
	private JFreeChart createPieChart () {
		
		JFreeChart oChart = ChartFactory.createPieChart("Inventory Weight Capacity", moPieDataset, true, false, false);
		
		return oChart;
	}
	
	// hier kommt ein Kommentar auf english :D
	
	public void fillChartOfDeath () {
				
		long nCurrentTime = clsSingletonMasonGetter.getSimState().schedule.getSteps();
		double rStamina = moStaminaContent.getContent() / moStaminaContent.getMaxContent() * moInventory.getMaxMass();
		
		moXYSeriesWeight.add(nCurrentTime, moInventory.getMass());
		moXYSeriesMaxWeight.add(nCurrentTime, moInventory.getMaxMass());
		moXYSeriesStamina.add(nCurrentTime, rStamina);
				
	}

	@Override
	public void updateInspector() {
		
		String oInventory;
		int i;
		clsMobile oItem;
		double rRemainingWeight = 0;
		
		fillChartOfDeath ();
		
		if (isVisible() && (mnItemsCount != moInventory.getItemCount())) {
			
			rRemainingWeight = moInventory.getMaxMass();
			oInventory = "MaxItems: " + moInventory.getItemCount() + " of " + moInventory.getMaxItems() + 
						"\nMaxMass: " + moInventory.getMass () + " of " + moInventory.getMaxMass() + "\n";
			
			if (moInventory.getCarriedEntity() != null) {
				oInventory += "Currently Carried Object: " + moInventory.getCarriedEntity().getId() + "Weight: " + 
						moInventory.getCarriedEntity().getStructuralWeight() + "\n";
			} else {
				oInventory += "currently carried Object: none\n";
			}
				
			mnItemsCount = moInventory.getItemCount();
			oInventory += "Inventory: \n";
			moPieDataset.insertValue(0, "Empty", rRemainingWeight); //damit die leere immer die selbe Farbe hat
			try {
				for (i = 0; i < mnItemsCount; i++) {
					oItem = moInventory.getInventoryItem (i); // weil zu oft ausgelesen vom oItem
					oInventory += "\t" + oItem.getId() + " Weight: " + oItem.getTotalWeight() + "\n";
												
					moPieDataset.insertValue(i+1, oItem.getId(), oItem.getTotalWeight());
					rRemainingWeight -= oItem.getTotalWeight();
				}
				moPieDataset.insertValue(0, "Empty", rRemainingWeight);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("InventoryItems: Index out of bound");
			}
			moText.setText(oInventory);
		}

	}

}
