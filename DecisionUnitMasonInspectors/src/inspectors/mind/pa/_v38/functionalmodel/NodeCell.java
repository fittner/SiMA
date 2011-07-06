/**
 * NodeCell.java: DecisionUnitMasonInspectors - inspectors.mind.pa.functionalmodel
 * 
 * @author deutsch
 * 22.10.2009, 18:52:19
 */
package inspectors.mind.pa._v38.functionalmodel;

import org.jgraph.graph.DefaultGraphCell;

import com.jgraph.components.labels.RichTextBusinessObject;

/**
 * DOCUMENT (deutsch) - insert description 
 * When we change from E->F or alike again dont forget to change it here!!!
 * 
 * @author deutsch
 * 22.10.2009, 18:52:19
 * 
 */
public class NodeCell extends DefaultGraphCell implements itfMouseClick {
	private static final long serialVersionUID = -1925166092650759316L;
	private final String id;

	public NodeCell(RichTextBusinessObject userObject, int idnumber) {
		super(userObject);
		
		if (idnumber<10) {
			id = "F0"+idnumber;
		} else {
			id = "F"+idnumber;
		}
	}
	
	@Override
	public String getId() {
		return id;
	}
}