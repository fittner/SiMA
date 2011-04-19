/**
 * Edge.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30.functionalmodel
 * 
 * @author deutsch
 * 19.04.2011, 20:31:03
 */
package inspectors.mind.pa._v30.functionalmodel;

import org.jgraph.graph.DefaultEdge;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 19.04.2011, 20:31:03
 * 
 */
public class Edge extends DefaultEdge implements itfMouseClick {

	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 19.04.2011, 20:31:50
	 */
	private static final long serialVersionUID = -6912257153461054477L;
	private final String moId;
	
	public Edge(String poName, String poId) {
		super(poName);
		moId = poId;
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 19.04.2011, 20:37:03
	 * 
	 * @see inspectors.mind.pa._v30.functionalmodel.itfMouseClick#getId()
	 */
	@Override
	public String getId() {
		return moId.toString();
	}

}
