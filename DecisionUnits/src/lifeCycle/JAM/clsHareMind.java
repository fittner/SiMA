package lifeCycle.JAM;


import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.itfActionProcessor;

import simple.remotecontrol.clsRemoteControl; //for testing purpose only! remove after test

public class clsHareMind extends clsRemoteControl { //should be derived from clsBaseDecisionUit

	@Override
	public void process(itfActionProcessor poActionProcessor) {

		//===========TESTING PURPOSE ONLY!
		super.process(poActionProcessor);
		
	   	switch( getKeyPressed() )
    	{
    	case 66:
    		break;
    	}
	  //=========== END
	   	
	}
}
