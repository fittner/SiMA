/**
 * clsActionSequenceFactory.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 29.08.2009, 13:17:23
 */
package du.itf.actions;

import du.enums.eActionMoveDirection;
import du.enums.eActionTurnDirection;

/**
 * Factory for creating default action sequences 
 * 
 * @author Benny Doenz
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
	
	public static clsActionSequence getSeekingSequence(float prSpeed, int piDuration) {
		
		clsActionSequence oSeq = new clsActionSequence();
		double rRand1 = Math.random();
		double rRand2 = Math.random();
		double rRand3 = Math.random();
		
		for (int i=0;i<piDuration;i++) {
			oSeq.add(i*90, new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 10),9); // 90 deg right turn
			oSeq.add(i*90+10, new clsActionTurn(eActionTurnDirection.TURN_LEFT, 10),18); // 180 deg left turn
			oSeq.add(i*90+30, new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 10),9); // 90 deg right turn
			oSeq.add(i*90+40, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, prSpeed),15);
			oSeq.add(i*90+60, new clsActionTurn(eActionTurnDirection.TURN_LEFT,prSpeed*15*rRand2),5);
			oSeq.add(i*90+75, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, prSpeed),25);
			oSeq.add(i*90+105, new clsActionTurn(eActionTurnDirection.TURN_RIGHT,prSpeed*15*rRand3),5);
		}
		
		return oSeq;
	}

	
	public static clsActionSequence getStrafeLeftSequence(int piDistance) {
		
		clsActionSequence oSeq = new clsActionSequence();
		
		oSeq.add(10, new clsActionTurn(eActionTurnDirection.TURN_LEFT, 10),9); // 90 deg left turn
		oSeq.add(20, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, 1),piDistance);
		oSeq.add(30, new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 10),9); // 90 deg right turn
		
		return oSeq;
	}

	public static clsActionSequence getStrafeRightSequence(int piDistance) {
		
		clsActionSequence oSeq = new clsActionSequence();
		
		oSeq.add(10, new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 10),9); // 90 deg right turn
		oSeq.add(20, new clsActionMove(eActionMoveDirection.MOVE_FORWARD, 1),piDistance);
		oSeq.add(30, new clsActionTurn(eActionTurnDirection.TURN_LEFT, 10),9); // 90 deg left turn
		
		return oSeq;
	}
	
	public static clsActionSequence getFleeSequence(float turnAngel, int fleeDistance) {
		clsActionSequence oSeq = new clsActionSequence();
		
		// move Back and turn Right
		int moveBackDistance=fleeDistance/2;
		for (int i=0;i<moveBackDistance;i++) {
		  oSeq.add(i*2, new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,1.0f),1);
		  oSeq.add(i*2+1, new clsActionTurn(eActionTurnDirection.TURN_RIGHT, turnAngel/fleeDistance),1);
		}
        
		// move Forward and turn Right
		int moveForwardDistance=fleeDistance/2;
		for (int i=0;i<moveForwardDistance;i++) {
		  oSeq.add(moveBackDistance+1*2, new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0f),1);
		  oSeq.add(moveBackDistance+i*2+1, new clsActionTurn(eActionTurnDirection.TURN_RIGHT, turnAngel/fleeDistance),1);
		}
		return oSeq;
	}
	
	public static clsActionSequence getFleeSequence2(float turnAngel, int fleeDistance) {
		clsActionSequence oSeq = new clsActionSequence();
		
		// move back
		oSeq.add(0, new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,1.0f),fleeDistance/3);
		
		// turn Right
		oSeq.add(fleeDistance/3,new clsActionTurn(eActionTurnDirection.TURN_RIGHT, 3*turnAngel/fleeDistance),fleeDistance/3);

		// move Forward
		oSeq.add(fleeDistance/3*2,new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0f),fleeDistance/3);

		return oSeq;
	}
	
	public static clsActionSequence getFleeSequence3(float turnAngel, int fleeDistance) {
		clsActionSequence oSeq = new clsActionSequence();
		
		// move Back
		int moveBackDistance=fleeDistance/2;
		oSeq.add(0, new clsActionMove(eActionMoveDirection.MOVE_BACKWARD,1.0f),moveBackDistance);
		
		// turn Right
		double turnPerStepAngel=Math.sqrt(turnAngel);
		int turnSteps=(int)turnPerStepAngel;
		oSeq.add(moveBackDistance, new clsActionTurn(eActionTurnDirection.TURN_RIGHT, turnPerStepAngel*2.0f),turnSteps);
        
		// move Forward
		int moveForwardDistance=fleeDistance/2;
		oSeq.add(moveBackDistance+turnSteps, new clsActionMove(eActionMoveDirection.MOVE_FORWARD,1.0f),moveForwardDistance);
		
		// turn Around
		oSeq.add(moveBackDistance+turnSteps+moveForwardDistance, new clsActionTurn(eActionTurnDirection.TURN_LEFT, 26), 13);

		return oSeq;
	}	
}
