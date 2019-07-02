/**
 * CHANGELOG
 *
 * Oct 31, 2012 herret - File created
 *
 */
package graph;

import inspector.interfaces.itfGraphData;

import java.util.ArrayList;

import javax.swing.JSplitPane;

import base.modules.clsPsychicApparatus;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Oct 31, 2012, 10:12:14 AM
 * 
 */
public class clsGraphData extends clsGraphWindow{
	
	/** DOCUMENT (herret) - insert description; @since Oct 31, 2012 10:22:41 AM */
	private static final long serialVersionUID = -3875918121529258575L;
	private final boolean mbOrientationVertical= true;
	protected itfGraphData moModule;
	
	protected clsPsychicApparatus moPA;
	
	public clsGraphData(clsPsychicApparatus poPA, itfGraphData poModule, boolean poOrientationVerticel){
		super(poOrientationVerticel);
		moModule=poModule;
		moPA =poPA;
		moGraphes.get(0).setRootNodeName("Node");
		updateGraphes(); //loading Data to the graph
	}
	
	@Override
	protected void createGraphes(){
		moGraphes = new ArrayList<clsGraph>();
		moSplitPanes = new ArrayList<JSplitPane>();
		
		moGraphes.add(new clsGraph(mbOrientationVertical));		
	}
	
	@Override
	protected void updateInspectorData() {
		moGraphes.get(0).setMoMesh(moModule.getGraphData());		
	}

}
