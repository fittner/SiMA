package du.itf.actions;

import java.util.ArrayList;

/**
 * iActionCommand
 * 
 * @author Benny Doenz
 * 15.04.2009, 18:40:19
 * 
 */
public abstract class clsActionCommand {
	
	public abstract String getLog();

	/*
	 * returns true if the command was completed and false if it is to continue to the next round  
	 */
	public boolean isComplete(int pnRound) {
		if (pnRound>=1) return true;
		return false;
	}
	
	/*
	 * Returns an Array of commands which should be executed in the given round (Starts with round "0")
	 * ->For simple commands it returns itself 
	 */
	public ArrayList<clsActionCommand> getCommands(int pnRound) {
		ArrayList<clsActionCommand> oReturnList = new ArrayList<clsActionCommand>();
		oReturnList.add(this);
		return oReturnList;
	}
	
	@Override
	public String toString() {
		return getLog();
	}
}
