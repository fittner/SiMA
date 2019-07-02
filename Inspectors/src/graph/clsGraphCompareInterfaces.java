/**
 * CHANGELOG
 *
 * Sep 11, 2012 herret - File created
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
 * Sep 11, 2012, 2:54:21 PM
 * 
 */
public class clsGraphCompareInterfaces extends clsGraphWindow {

	
	
	/** DOCUMENT (herret) - insert description; @since Sep 11, 2012 2:54:53 PM */
	private static final long serialVersionUID = -2605585470395642213L;

	protected ArrayList<eInterfaces> moRecv;
	protected ArrayList<eInterfaces> moSend;
	
	protected clsPsychicApparatus moPA;
	public clsGraphCompareInterfaces(clsPsychicApparatus poPA, ArrayList<eInterfaces> poRecv,ArrayList<eInterfaces> poSend, boolean poOrientationVerticel){
		super(poOrientationVerticel);
		moRecv=poRecv;
		moSend= poSend;
		moPA =poPA;
		moGraphes.get(0).setRootNodeName("Input");
		moGraphes.get(1).setRootNodeName("Output");
		updateGraphes(); //loading Data to the graph
	}
	
	@Override
	protected void createGraphes(){
		moGraphes = new ArrayList<clsGraph>();
		moSplitPanes = new ArrayList<JSplitPane>();
		
		moGraphes.add(new clsGraph(mbOrientationVertical));
		moGraphes.add(new clsGraph(mbOrientationVertical));
		
	}
	
	
	/* (non-Javadoc)
	 *
	 * @since Sep 11, 2012 2:54:21 PM
	 * 
	 * @see inspectors.mind.pa._v38.graph.clsGraphBase#updateinspectorData()
	 */
	@SuppressWarnings("unchecked")
	@Override
	protected void updateInspectorData() {
		ArrayList<Object> oMesh = new ArrayList<Object>();
		if(moRecv!=null){
			for( eInterfaces oInter: moRecv){
				if(moPA.moInterfaceData.containsKey(oInter)){
					oMesh.addAll(moPA.moInterfaceData.get(oInter));
				}
	
			}

			moGraphes.get(0).setMoMesh((ArrayList<Object>) oMesh.clone());
		}
		oMesh = new ArrayList<Object>();
		for( eInterfaces oInter: moSend){
			if(moPA.moInterfaceData.containsKey(oInter)){
				oMesh.addAll(moPA.moInterfaceData.get(oInter));
			}
		}

		moGraphes.get(1).setMoMesh(oMesh);
		
	}
	
}
