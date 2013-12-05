package students.borer.episodicMemory;

import students.borer.LocalizationOrientation.clsStep;

public class clsFeatureStep extends clsFeature {

	@SuppressWarnings("unchecked")
	public clsFeatureStep(clsStep Area){
	super();
	mnTriggerEncodingEnabled = true;
	mnSpontaneousRetrievalEnabled = false;
	
		moFeatureElements.add( new clsElementStep( Area ));
	}
	@Override
	public boolean checkIfSameType(clsFeature poFeature) {
		//  Auto-generated method stub
		return false;
	}

	@Override
	public Object getContainer() {
		//  Auto-generated method stub
		return null;
	}

}
