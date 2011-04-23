/**
 * clsDriveInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v30.graph
 * 
 * @author deutsch
 * 22.04.2011, 18:26:26
 */
package inspectors.mind.pa._v30.graph;

import java.awt.Color;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import org.jgraph.graph.DefaultEdge;
import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;
import com.jgraph.components.labels.RichTextBusinessObject;
import com.jgraph.components.labels.RichTextValue;
import pa._v30.interfaces.itfInspectorDrives;
import pa._v30.memorymgmt.datatypes.clsDriveMesh;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.04.2011, 18:26:26
 * 
 */
public class clsDriveInspector extends clsGraphBase {
	private ArrayList<clsDriveMesh> moDriveList; 
	private itfInspectorDrives moObject;
	
	public clsDriveInspector(itfInspectorDrives poObject) {
		super();
		moObject = poObject;
		
		updateControl();	//loading data into the graph
	}
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 22.04.2011, 18:26:30
	 */
	private static final long serialVersionUID = -5931380754570369230L;

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.04.2011, 18:26:26
	 * 
	 * @see inspectors.mind.pa._v30.graph.clsGraphBase#updateinspectorData()
	 */
	@Override
	protected void updateinspectorData() {
		moDriveList = moObject.getDriveList();
	}

	/* (non-Javadoc)
	 *
	 * @author deutsch
	 * 22.04.2011, 18:26:26
	 * 
	 * @see inspectors.mind.pa._v30.graph.clsGraphBase#createGraph()
	 */
	@Override
	protected DefaultGraphCell createGraph() {
		DefaultGraphCell oRoot = createVertex("Drive Representaiton", 20, 20, 150, 40, Color.WHITE);
	
		DefaultGraphCell oLife = createVertex("Libido\n(Life instinct)", 20, 20, 150, 40, Color.GREEN);
		moCellList.add( oLife );
		createEdge(moCellList, oRoot, oLife, "");
		DefaultGraphCell oDeath = createVertex("Death instinct", 20, 20, 150, 40, Color.LIGHT_GRAY);
		moCellList.add( oDeath );
		createEdge(moCellList, oRoot, oDeath, "");
		
		//FIXME: this method works only iff the pairs-of-opposite are next to each other in the list AND iff life drive is before death drive in the array
		for (int i=0; i<moDriveList.size(); i+=2) {
			DefaultGraphCell oL = readDrive_new(moCellList, oLife, moDriveList.get(i), "", Color.GREEN);
			DefaultGraphCell oD = readDrive_new(moCellList, oDeath, moDriveList.get(i+1), "", Color.LIGHT_GRAY);
			
			createEdge(moCellList, oL, oD, "Pair of opposites"); //forward
			createEdge(moCellList, oD, oL, ""); //and backward
		}
		
		return oRoot;
	}
	

	/**
	 * DOCUMENT (zeilinger) - insert description
	 *
	 * @author zeilinger
	 * 02.11.2010, 21:01:56
	 *
	 * @param poCellList
	 * @param poParentLife
	 * @param oPrimPair
	 * @param poAssociationName
	 * @param green
	 * @return
	 */
	private DefaultGraphCell readDrive_new(
			ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParentLife, clsDriveMesh oDM,
			String poAssociationName, Color poNodeColor) {

		DefaultGraphCell oCell = null;
		oCell = readSingle_new(poCellList, poParentLife, oDM, poAssociationName, poNodeColor);
		
		String oVertexName =  "Affect: \n " + oDM.getPleasure();
		DefaultGraphCell oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40, poNodeColor);
		poCellList.add( oCurrentVertex );
		createEdge(poCellList, oCell, oCurrentVertex, poAssociationName);
		
		return oCell;
	}

	/**
	 * DOCUMENT (langr) - insert description
	 *
	 * @author langr
	 * 31.10.2009, 23:48:59
	 *
	 * @param poCellList
	 * @param poFrom
	 * @param poAssociationName
	 * @param poTo
	 */
	private void createEdge(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poFrom, DefaultGraphCell poTo, String poAssociationName) {
		DefaultEdge edge = new DefaultEdge(poAssociationName);
		edge.setSource(poFrom.getChildAt(0));
		edge.setTarget(poTo.getChildAt(0));
		poCellList.add(edge);
		
		GraphConstants.setLineEnd(edge.getAttributes(), GraphConstants.ARROW_CLASSIC);
		GraphConstants.setEndFill(edge.getAttributes(), true);
	}
	
	/**
	 * Creates a graph-cell for the clsThingPresentationSingle and connects the graph-cell 
	 * with an arrowed edge to the parent
	 *
	 * @author langr
	 * 15.10.2009, 22:29:11
	 *
	 * @param poCellList
	 * @param poParent
	 * @param poPrimSingle
	 * @param poAssociationName
	 */
//	private DefaultGraphCell readSingle(ArrayList<DefaultGraphCell> poCellList,
//			DefaultGraphCell poParent, clsPrimaryInformation poPrimSingle, String poAssociationName, Color poNodeColor) {
//
//		String oVertexName = poPrimSingle.moTP.meContentName + ": \n " + poPrimSingle.moTP.moContent;
//		DefaultGraphCell oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40, poNodeColor);
//		poCellList.add( oCurrentVertex );
//		createEdge(poCellList, poParent, oCurrentVertex, poAssociationName);
//		return oCurrentVertex;
//	}
//	
	private DefaultGraphCell readSingle_new(ArrayList<DefaultGraphCell> poCellList,
			DefaultGraphCell poParent, clsDriveMesh oDM, String poAssociationName, Color poNodeColor) {

		String oVertexName = oDM.getMoContentType() + ": \n " + oDM.getMoContent();
		DefaultGraphCell oCurrentVertex = createVertex(oVertexName, 20, 20, 150, 40, poNodeColor);
		poCellList.add( oCurrentVertex );
		createEdge(poCellList, poParent, oCurrentVertex, poAssociationName);
		return oCurrentVertex;
	}
	/**
	 * static helper to create a graph-cell and set the default style within this inspector
	 *
	 * @author langr
	 * 15.10.2009, 22:31:41
	 *
	 * @param name
	 * @param x
	 * @param y
	 * @param w
	 * @param h
	 * @return
	 */
	public DefaultGraphCell createVertex(String name, double x,
			double y, double w, double h, Color poNodeColor) {

		//Richtext to enable linebreaks
		RichTextBusinessObject userObject = new RichTextBusinessObject();
		RichTextValue textValue = new RichTextValue(name);
		userObject.setValue(textValue);
		
		// Create vertex with the given name
		DefaultGraphCell cell = new DefaultGraphCell(userObject);

		// Set bounds
		GraphConstants.setBounds(cell.getAttributes(), new Rectangle2D.Double(x, y, w, h));
		GraphConstants.setGradientColor( cell.getAttributes(), poNodeColor);
		//GraphConstants.setInset(cell.getAttributes(), 10);
		// Make sure the cell is resized on insert
		GraphConstants.setResize(cell.getAttributes(), true);
		GraphConstants.setAutoSize(cell.getAttributes(), true);
		GraphConstants.setOpaque(cell.getAttributes(), true);
		GraphConstants.setBorderColor(cell.getAttributes(), Color.black);
		GraphConstants.setBackground(cell.getAttributes(), new Color(240,240,240));
		
		// Add a Port
		cell.addPort();

		return cell;
	}	

}
