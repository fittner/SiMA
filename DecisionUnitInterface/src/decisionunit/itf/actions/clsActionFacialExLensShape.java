/**
 * clsActionFacialExLensShape.java: DecisionUnitInterface - decisionunit.itf.actions
 * 
 * @author Benny Dönz
 * 28.08.2009, 13:59:25
 */
package decisionunit.itf.actions;

import enums.eLensShape;

/**
 * Facial expression command: Lens Shape
 * Parameters
 * 	peShape = new lens shape to set
 * 
 * @author Benny Dönz
 * 20.06.2009, 15:31:13
 * 
 */
public class clsActionFacialExLensShape implements itfActionCommand {

	private eLensShape meShape;

	public clsActionFacialExLensShape(eLensShape peShape) {
		meShape=peShape;
	}
	
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