package nao.body;



import nao.body.io.clsExternalIO;
import nao.utils.enums.eBodyType;
import nao.body.brainsocket.clsBrainSocket;
import nao.body.clsBaseBody;
import nao.body.itfGetBrain;


public class clsNAOBody extends clsBaseBody implements  itfGetBrain {
	
	protected clsBrainSocket moBrain;
    protected clsExternalIO  moExternalIO;
	
	public clsNAOBody() {
		super();
		
//		moInternalSystem 		= new clsInternalSystem(pre+P_INTERNAL, poProp);
//		moIntraBodySystem 		= new clsIntraBodySystem(pre+P_INTRABODY, poProp, moInternalSystem, poEntity);
//		moInterBodyWorldSystem 	= new clsInterBodyWorldSystem(pre+P_BODYWORLD, poProp, moInternalSystem, poEntity);
		
		moExternalIO	= new clsExternalIO(this);
//		moInternalIO 	= new clsInternalIO(this);
		moBrain 		= new clsBrainSocket( moExternalIO.getActionProcessor());
	}
	
	@Override
	public clsBrainSocket getBrain() {
		return moBrain;
	}

	@Override
	public void stepSensing() {
//		moExternalIO.stepSensing();
//		moInternalIO.stepSensing();
		
	}

	@Override
	public void stepUpdateInternalState() {
//		moInternalSystem.stepUpdateInternalState(); //call first!
//		moIntraBodySystem.stepUpdateInternalState();
//		moInterBodyWorldSystem.stepUpdateInternalState();
		
	}

	@Override
	public void stepProcessing() {
		moBrain.stepProcessing();
		
	}

	@Override
	public void stepExecution() {
//		moExternalIO.stepExecution();
//		moInternalIO.stepExecution();
		
	}

	@Override
	protected void setBodyType() {
		meBodyType = eBodyType.NAO;		
		
	}	

}
