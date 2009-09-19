package decisionunit.itf.actions;

import java.util.ArrayList;

/**
 * iActionCommand
 * 
 * @author Benny Dönz
 * 15.04.2009, 18:40:19
 * 
 */
public abstract class clsActionCommand {
	
	public abstract String getLog();

	/*
	 * Returns the number of rounds the sequence contains (1 for simple commands)
	 */
	public int getRounds() {
		return 1;
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
}
