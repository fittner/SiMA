/**
 * CHANGELOG
 *
 * 29.03.2013 Jordakieva - File created
 *
 */
package bw.utils.inspectors.entity;

import java.awt.GridLayout;

import javax.swing.JPanel;

import org.jfree.data.general.DefaultPieDataset;

import panels.TextOutputPanel;
import bw.entities.clsMobile;
import bw.entities.tools.clsInventory;
import bw.utils.inspectors.clsInspectorPieChart;
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
	private TextOutputPanel moText, moText2, moText3;
	private DefaultPieDataset moPieDataset;
	private clsInspectorPieChart moInspectorPie;
	private int mnItemsCount;
	
	public clsInspectorInventory (clsInventory poInventory)	{
		JPanel oContent = new JPanel ();
		
		moInventory = poInventory;
		moText = new TextOutputPanel();
		moText2 = new TextOutputPanel();
		moText3 = new TextOutputPanel();
		setLayout (new GridLayout(2,1));
		oContent.setLayout(new GridLayout(1,2));
		
		moPieDataset = new DefaultPieDataset ();
		moInspectorPie = new clsInspectorPieChart (moPieDataset);
		mnItemsCount = moInventory.getItemCount();
		
		try {
			oContent.add (moText);
			oContent.add (moText2);
			add (oContent);
			add (moText3);
		} catch (Exception e) { 
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
		
	}
	
	// hier kommt ein Kommentar auf english :D
	
	public void inventoryItemsToChart () {
		
		clsMobile oItem;
		int i;
		double rRemainingWeight = 0;
		
		//in der späteren Version anchgucken ob sich etwas verändert hat und es dann übernehmen, für jetzt wird die schleife immer aufgerufen
		if (mnItemsCount != moInventory.getItemCount()) {
			mnItemsCount = moInventory.getItemCount();
			rRemainingWeight = moInventory.getMaxMass();
			
			try {
				for (i = 0; i <= mnItemsCount; i++) { //gucken was an der letzten Stelle ist bzw an der ersten = 0ten
					oItem = moInventory.getInventoryItem (i);
					moPieDataset.insertValue(i, oItem.getId(), oItem.getTotalWeight());
					rRemainingWeight -= oItem.getTotalWeight();
				}
				moPieDataset.insertValue(i, "Empty", rRemainingWeight);
			} catch (IndexOutOfBoundsException e) {
				System.out.println("InventoryItems: Index out of bound");
			}
				
		}
		
	}

	/* (non-Javadoc)
	 *
	 * @since 29.03.2013 16:06:02
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		// TODO (Jordakieva) - Auto-generated method stub
		
		try {
			if (isVisible()) {
				moText.setText(moInventory.toText());
				moText2.setText("hihi HAHA MÄCHTIG!!!!");
				moText3.setText("ober");
				inventoryItemsToChart ();
			}
		} catch (Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}

	}

}
