/**
 * clsRichTextCellViewFactory.java: DecisionUnitMasonInspectors - inspectors.mind.pa
 * 
 * @author langr
 * 16.10.2009, 00:44:32
 */
package inspectors.mind.pa;


import org.jgraph.graph.DefaultCellViewFactory;
import org.jgraph.graph.VertexView;
import com.jgraph.components.labels.MultiLineVertexView;

/**
 * DOCUMENT (langr) - insert description 
 * 
 * @author langr
 * 16.10.2009, 00:44:32
 * 
 */
public class clsRichTextCellViewFactory extends DefaultCellViewFactory {

		/**
	 * @author deutsch
	 * 10.08.2010, 18:01:08
	 */
	private static final long serialVersionUID = -4254080503279445738L;

		/**
		 * Constructs a VertexView view for the specified object.
		 */
		@Override
		protected VertexView createVertexView(Object cell) {
			return new MultiLineVertexView(cell);//VertexView(cell);
		}
	}
