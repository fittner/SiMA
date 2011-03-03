/**
 * NodeCell.java: DecisionUnitMasonInspectors - inspectors.mind.pa.functionalmodel
 * 
 * @author deutsch
 * 22.10.2009, 18:52:19
 */
package inspectors.mind.pa._v19.functionalmodel;

import org.jgraph.graph.DefaultGraphCell;

import com.jgraph.components.labels.RichTextBusinessObject;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.10.2009, 18:52:19
 * 
 */
public class NodeCell extends DefaultGraphCell {
	private static final long serialVersionUID = -1925166092650759316L;
	private final String id;

	public NodeCell(RichTextBusinessObject userObject, int idnumber) {
		super(userObject);
		
		if (idnumber<10) {
			id = "E0"+idnumber;
		} else {
			id = "E"+idnumber;
		}
	}
	
	public String getId() {
		return id;
	}
}