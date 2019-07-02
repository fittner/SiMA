/**
 * CHANGELOG
 *
 * Oct 30, 2012 herret - File created
 *
 */
package graph;

import java.util.ArrayList;

import javax.swing.JSplitPane;

import base.modules.clsPsychicApparatus;
import modules.interfaces.eInterfaces;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Oct 30, 2012, 2:06:52 PM
 * 
 */
public class clsGraphInterface extends clsGraphWindow{

	/** DOCUMENT (herret) - insert description; @since Oct 30, 2012 2:09:39 PM */
	private static final long serialVersionUID = -528746893431334115L;
	
	protected ArrayList<eInterfaces> moInterfaces;
	
	protected clsPsychicApparatus moPA;
	
	public clsGraphInterface(clsPsychicApparatus poPA, ArrayList<eInterfaces> poInterfaces, boolean poOrientationVerticel){
		super(poOrientationVerticel);
		moInterfaces=poInterfaces;
		moPA =poPA;
		moGraphes.get(0).setRootNodeName("Node");
		updateGraphes(); //loading Data to the graph
		//moGraphInput.reset();
		//moGraphOutput.reset();
	}
	
	@Override
	protected void createGraphes(){
		moGraphes = new ArrayList<clsGraph>();
		moSplitPanes = new ArrayList<JSplitPane>();
		
		moGraphes.add(new clsGraph(mbOrientationVertical));		
	}
	
	
	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2012 2:54:21 PM
	 * 
	 * @see inspectors.mind.pa._v38.graph.clsGraphBase#updateinspectorData()
	 */
	@Override
	protected void updateInspectorData() {
		ArrayList<Object> oMesh = new ArrayList<Object>();
		if(moInterfaces!=null){
			for( eInterfaces oInter: moInterfaces){
				if(moPA.moInterfaceData.containsKey(oInter)){
					oMesh.addAll(moPA.moInterfaceData.get(oInter));
				}
	
			}

			moGraphes.get(0).setMoMesh(oMesh);
		}
		
	}
}
