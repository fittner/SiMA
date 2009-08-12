//package memory;
//
//import LocalizationOrientation.clsArea;
//import LocalizationOrientation.clsStep;
//
///**
// * @author  monkfoodb
// */
//public class clsElementStep extends clsFeatureElement{
//
//	private clsStep Step;
//	
//	public clsElementStep(clsStep newStep){
//		this.Step=newStep;
//	}
//	
//	/**
//	 * @return  the step
//	 * @uml.property  name="step"
//	 */
//	public clsStep getStep(){
//		return Step;
//	}
//	
//	@Override
//	public boolean checkIfSameType(clsFeatureElement poFeatElem) {
//		return true; // there is only one Type
//	}
//
//	@Override
//	public clsMatchFeatureElement getMatch(clsFeatureElement poFeatureElement) {
//		clsElementStep oAreaCue = (clsElementStep)poFeatureElement;
//
//		return new clsMatchFeatureElement(oAreaCue, (Step.getCurrentArea() == oAreaCue.getStep().getCurrentArea()) ? 1 : 0 );
//	}
//
//	@Override
//	public boolean triggerEncoding(clsFeatureElement poPrevArea) {
//		clsElementStep oPrevArea = (clsElementStep)poPrevArea;
//		if (oPrevArea.getStep()==null)
//			return true;
//		if (this.Step.getCurrentArea()!=oPrevArea.getStep().getCurrentArea())
//			return true;
//		return false;
//	}
//
//}
