package complexbody.io.actuators.actionCommands;

public class clsActionFromInventory extends clsActionCommand {

	/**
	 * From Inventory
	 * Get an item from the inventory (this item will then be set as carried) 
	 * Parameter Index = 0-based index of the item to take from the inventory (default=0, i.e. the first)
	 * 
	 * @author Benny D�nz
	 * 03.07.2009, 22:39:05
	 * 
	 */
	
	private int mnIndex = 0 ;

	//Default => Index 0
	public clsActionFromInventory() {
	}
	public clsActionFromInventory(int pnIndex) {
		mnIndex=pnIndex;
	}
	
	public int getIndex() {
		return mnIndex;
	}
	public void setIndex(int pnIndex) {
		mnIndex=pnIndex;
	}
	
	@Override
	public String getLog() {
		return "<FromInventory>" + mnIndex + "</FromInventory>"; 
	}
	
}
