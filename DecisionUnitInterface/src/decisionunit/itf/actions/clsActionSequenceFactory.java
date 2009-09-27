/**
 * clsActionSequenceFactory.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 29.08.2009, 13:17:23
 */
package decisionunit.itf.actions;

import enums.eActionMoveDirection;
import enums.eActionTurnDirection;

/**
 * Factory for creating default action sequences 
 * 
 * @author Benny Dönz
 * 29.08.2009, 13:17:23
 * 
 */
public class clsActionSequenceFactory {

	public static clsActionSequence getSalsaSequence(float prSpeed, int piDuration) {
		
		clsActionSequence oSeq = new clsActionSequence();
		
		for (int i=0;i<piDuration;i++) {
			oSeq.add(i*120, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, prSpeed),15);
			oSeq.add(i*120+20, new clsActionTurn(eActionTurnDirection.TURN_RIGHT,18),10);
			oSeq.add(i*120+30, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, prSpeed),15);
			oSeq.add(i*120+50, new clsActionTurn(eActionTurnDirection.TURN_RIGHT,18),10);
			oSeq.add(i*120+60, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, prSpeed),15);
			oSeq.add(i*120+80, new clsActionTurn(eActionTurnDirection.TURN_RIGHT,18),10);
			oSeq.add(i*120+90, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, prSpeed),15);
			oSeq.add(i*120+110, new clsActionTurn(eActionTurnDirection.TURN_RIGHT,18),10);
		}
		
		return oSeq;
	}

	public static clsActionSequence getWalzSequence(float prSpeed, int piDuration) {
		
		clsActionSequence oSeq = new clsActionSequence();
		
		for (int i=0;i<piDuration;i++) {
			oSeq.add(i*120, new clsActionMove(eActionMoveDirection.MOVE_FORWARD,2),120);
			oSeq.add(i*120, new clsActionTurn(eActionTurnDirection.TURN_LEFT,12*prSpeed),60);
			oSeq.add(i*120+60, new clsActionTurn(eActionTurnDirection.TURN_RIGHT,12*prSpeed),60);
		}
		
		return oSeq;
	}

	public static clsActionSequence getTangoSequence(float prSpeed, int piDuration) {
		
		clsActionSequence oSeq = new clsActionSequence();
		
		for (int i=0;i<piDuration;i++) {
			oSeq.add(i*90, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, prSpeed),15);
			oSeq.add(i*90+20, new clsActionTurn(eActionTurnDirection.TURN_RIGHT,24),10);
			oSeq.add(i*90+30, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, prSpeed),15);
			oSeq.add(i*90+50, new clsActionTurn(eActionTurnDirection.TURN_RIGHT,24),10);
			oSeq.add(i*90+60, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, prSpeed),15);
			oSeq.add(i*90+80, new clsActionTurn(eActionTurnDirection.TURN_RIGHT,24),10);
		}
		
		return oSeq;
	}
	
}
