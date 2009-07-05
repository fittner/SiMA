package decisionunit.itf.actions;

public class clsActionToInventory implements itfActionCommand {

	/**
	 * To Inventory command
	 * No parameters, the item currently carried will be put into the inventory 
	 * 
	 * @author Benny Dönz
	 * 03.07.2009, 22:39:05
	 * 
	 */
	
	public String getLog() {
		return "<ToInventory></ToInventory>"; 
	}
	
}
