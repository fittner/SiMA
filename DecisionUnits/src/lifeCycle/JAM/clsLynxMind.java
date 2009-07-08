package lifeCycle.JAM;

import decisionunit.clsBaseDecisionUnit;
import decisionunit.itf.actions.itfActionProcessor;
import sim.display.clsKeyListener;
import simple.remotecontrol.clsRemoteControl; //for testing purpose only! remove after test


public class clsLynxMind extends clsRemoteControl  {

	@Override
	public void process(itfActionProcessor poActionProcessor) {

		//===========TESTING PURPOSE ONLY!
		super.process(poActionProcessor);
		
	   	switch( getKeyPressed() )
    	{
    	case 75: //'K'
    		super.kill(poActionProcessor);
    		break;
    	}
	  //=========== END
	}
}
