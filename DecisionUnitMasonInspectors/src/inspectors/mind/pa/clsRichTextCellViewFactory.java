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
		 * Constructs a VertexView view for the specified object.
		 */
		@Override
		protected VertexView createVertexView(Object cell) {
			return new MultiLineVertexView(cell);//VertexView(cell);
		}
	}