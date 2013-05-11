/**
 * clsActionFacialExLensShape.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Doenz
 * 28.08.2009, 13:59:25
 */
package du.itf.actions;

import du.enums.eLensShape;

/**
 * Facial expression command: Lens Shape
 * Parameters
 * 	peShape = new lens shape to set
 * 
 * @author Benny Doenz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionFacialExLensShape extends clsActionCommand {

	private eLensShape meShape;

	public clsActionFacialExLensShape(eLensShape peShape) {
		meShape=peShape;
	}
	
	@Override
	public String getLog() {
		return "<FacialExLensShape>" + meShape.toString() + "</FacialExLensShape>"; 
	}

	public eLensShape getShape() {
		return meShape;
	}
	public void setShape(eLensShape peShape) {
		meShape=peShape;
	}
}