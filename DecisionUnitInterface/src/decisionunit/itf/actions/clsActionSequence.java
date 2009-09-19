/**
 * @author Benny D�nz
 * 15.04.2009, 18:13:36
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package decisionunit.itf.actions;

import java.util.ArrayList;
import java.util.Iterator;
/**
 * This class is used to generate a sequence of actions which 
 * can then be passed to the processor. 
 * 
 * @author Benny D�nz
 * 15.04.2009, 18:13:36
 * 
 */
public class clsActionSequence implements itfActionCommand {

	private ArrayList<clsSequencedAction> moCommands = new ArrayList<clsSequencedAction>();
	
	/*
	 * Adds a command to the sequence which should be executed in the given round 
	 * (0=current round, 1=next round, 2=round after next, etc.). The duration can 
	 * be set to any number greater or equal to one and defines the number of simulation 
	 * cycles the command should be executed for
	 */
	public void add(int pnRound, itfActionCommand poAction, int pnDuration) {
		moCommands.add(new clsSequencedAction(pnRound,poAction,pnDuration));
	}
	
	/*
	 * 
	 */
	public String getLog() {
		String sLog = "";
		
		Iterator<clsSequencedAction> oItSeq = moCommands.iterator();
		while (oItSeq.hasNext()) {
			clsSequencedAction oSeqAction = oItSeq.next();
			sLog += oSeqAction.getAction().getLog();
		}
		
		return sLog;
	}
	
	/*
	 * Returns the number of rounds the sequence contains
	 */
	public int getRounds() {
		int nMaxRounds = 0;
		Iterator<clsSequencedAction> oItSeq = moCommands.iterator();
		while (oItSeq.hasNext()) {
			clsSequencedAction oSeqAction = oItSeq.next();
			if (nMaxRounds<(oSeqAction.getRound() + oSeqAction.getDuration())) nMaxRounds=(oSeqAction.getRound() + oSeqAction.getDuration()); 
		}
		return nMaxRounds;
	}
	
	/*
	 * Returns an Array of commands which should be executed in the given round (Starts with round "0") 
	 */
	public ArrayList<itfActionCommand> getCommands(int pnRound) {
		ArrayList<itfActionCommand> oReturnList = new ArrayList<itfActionCommand>();

		Iterator<clsSequencedAction> oItSeq = moCommands.iterator();
		while (oItSeq.hasNext()) {
			clsSequencedAction oSeqAction = oItSeq.next();
			if (oSeqAction.getRound() <= pnRound && (oSeqAction.getRound()+oSeqAction.getDuration()) > pnRound) {
				oReturnList.add(oSeqAction.getAction());
			}
		}
		
		return oReturnList;
	}
	
	/*
	 * Private class used for storing sequenced actions
	 */
	private class clsSequencedAction {
		private itfActionCommand moAction;
		private int mnDuration;
		private int mnRound;
		public clsSequencedAction(int pnRound, itfActionCommand poAction,int pnDuration) {
			if (pnDuration<0) pnDuration=0; //MinDuration=0 (no execution)
			if (pnRound<-1) pnRound=-1; //MinRound=-1 (no execution)
			moAction=poAction;
			mnDuration=pnDuration;
			mnRound=pnRound;
		}
		
		public int getDuration() {
			return mnDuration;
		}

		public int getRound() {
			return mnRound;
		}

		public itfActionCommand getAction() {
			return moAction;
		}
	

	}
}
