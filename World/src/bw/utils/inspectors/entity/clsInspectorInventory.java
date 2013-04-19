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
 * DOCUMENT (Jordakieva) - Visualizes the inventory of a selected agent in three forms of representation. 
 * Textually, as a pie chart and as a XY chart. In the XY chart you can also see 
 * the developement of the stamina in relationship to the weight of the inventory. 
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

	
	/**
	 * DOCUMENT (Jordakieva) - specifies the layout of the panels and generates them
	 * @since 19.04.2013 13:16:02
	 * @param poArsin - the ARSIN which has been selected to be visualized
	 */
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
		
		//set the layout-manager to two lines and one row
		setLayout (new GridLayout(2,1));
		//inner-layout-manager one line and two rows
		oContent.setLayout(new GridLayout(1,2));
		
		//assemble layout (putting it all together)
		try {
			oContent.add (moText);
			oContent.add (moCakeChart);
			add (oContent);
			add (moChartOfDeath);
		} catch (Exception e) { 
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
		
	}
	
	/**
	 * DOCUMENT (Jordakieva) - creates a new XY-Step-Chart (inventory) combined with an XY-Line-Chart (stamina) and adjusts the display settings
	 *
	 * @since 19.04.2013 13:21:13
	 *
	 * @return - the chart
	 */
	private JFreeChart createXYChart () {
		
		XYSeriesCollection oXYCollection1, oXYCollectionStamina; //XYSeriesCollection contains Serials. The Serials contains the Values
											//bec we are using different Charts, we need two Collections
		
		
		oXYCollection1 = new XYSeriesCollection ();
		oXYCollection1.addSeries(moXYSeriesMaxWeight);
		oXYCollection1.addSeries(moXYSeriesWeight);		
		
		oXYCollectionStamina = new XYSeriesCollection ();
		oXYCollectionStamina.addSeries(moXYSeriesStamina);
				
		//JFreeChart oXYChart = ChartFactory.createXYLineChart("Stamina Behaviour to Weight" , "Time", "Value", oXYCollection1, PlotOrientation.VERTICAL, true, false, false);
		JFreeChart oXYChart = ChartFactory.createXYStepChart("Stamina Behaviour to Weight", "Time", "Value", oXYCollection1, PlotOrientation.VERTICAL, true, false, false);
		
		XYPlot oPlot = (XYPlot) oXYChart.getPlot();
		
		{ //sets line-renderer for the stamina
			XYLineAndShapeRenderer oRenderer = new XYLineAndShapeRenderer(true, false);
			oPlot.setDataset(1, oXYCollectionStamina);
			oPlot.setRenderer(1, oRenderer);
		}
				
		return oXYChart;
	}
	
	/**
	 * DOCUMENT (Jordakieva) - creates a new PieChart
	 *
	 * @since 19.04.2013 13:22:28
	 *
	 * @return - the chart
	 */
	private JFreeChart createPieChart () {
		
		JFreeChart oChart = ChartFactory.createPieChart("Inventory Weight Capacity", moPieDataset, true, false, false);
		
		return oChart;
	}
		
	/**
	 * DOCUMENT (Jordakieva) - Filling the variables for the chart output.
	 * MaxStamina is adjusted to MaxWeight for the output (so that you can see the relation in the XYPlot ;))
	 *
	 * @since 19.04.2013 13:28:29
	 *
	 */
	public void fillChartOfDeath () {
				
		long nCurrentTime = clsSingletonMasonGetter.getSimState().schedule.getSteps();
		double rStamina = moStaminaContent.getContent() / moStaminaContent.getMaxContent() * moInventory.getMaxMass(); //stamina adjusted to MaxMass Arsin can carry
		
		moXYSeriesWeight.add(nCurrentTime, moInventory.getMass());
		moXYSeriesMaxWeight.add(nCurrentTime, moInventory.getMaxMass());
		moXYSeriesStamina.add(nCurrentTime, rStamina);
				
	}

	/**
	 * DOCUMENT (Jordakieva) - updates the dataset of the charts
	 *
	 * @since 19.04.2013 13:29:29
	 *
	 */
	@Override
	public void updateInspector() {
		
		String oInventory;
		clsMobile oItem;
		double rRemainingWeight = 0;
		int i;
		
		fillChartOfDeath ();
		
		if (moInventory.getCarriedEntity() != null) {
			oInventory = "Currently carried object: " + moInventory.getCarriedEntity().getId() + "Weight: " + 
					moInventory.getCarriedEntity().getTotalWeight() + "\n";
		} else {
			oInventory = "Currently in the hand carried Object: none\n";
		}
		
		rRemainingWeight = moInventory.getMaxMass();
		oInventory += "\nMaxItems: " + moInventory.getItemCount() + " of " + moInventory.getMaxItems() + 
					"\nMaxMass: " + moInventory.getMass () + " of " + moInventory.getMaxMass() + "\n";
		 
			
		mnItemsCount = moInventory.getItemCount();
		oInventory += "Inventory: \n";
		moPieDataset.insertValue(0, "Empty", rRemainingWeight); //has to be first, so that the empty space keeps 
																//the same color after reopening the inventory inspector 
		try {
			for (i = 0; i < mnItemsCount; i++) {
				oItem = moInventory.getInventoryItem (i);
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
