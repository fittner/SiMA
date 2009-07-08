package lifeCycle.JAM;


import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.itfActionProcessor;

import sim.display.clsKeyListener;
import simple.remotecontrol.clsRemoteControl; //for testing purpose only! remove after test

public class clsHareMind extends clsRemoteControl { //should be derived from clsBaseDecisionUit

	@Override
	public void process(itfActionProcessor poActionProcessor) {

		clsKeyListener.getKeyPressed();
		
		//===========TESTING PURPOSE ONLY!
		super.process(poActionProcessor);
		
	   	switch( getKeyPressed() )
    	{
    	case 75: //'K'
    		break;
    	}
	  //=========== END
	   	
	}
}
