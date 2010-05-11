package du.itf.actions;

public class clsActionToInventory extends clsActionCommand {

	/**
	 * To Inventory command
	 * No parameters, the item currently carried will be put into the inventory 
	 * 
	 * @author Benny D�nz
	 * 03.07.2009, 22:39:05
	 * 
	 */
	
	@Override
	public String getLog() {
		return "<ToInventory></ToInventory>"; 
	}
	
}
