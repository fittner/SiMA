package base.tools;
import java.io.IOException;
import java.util.Set;

import logger.FileWriter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import base.datatypes.interfaces.itfMapTreeNode;

public class SimaJSONHandler {

    private itfMapTreeNode rawData = null;
    
	private FileWriter fileWriter;
	private JSONArray treemapFull;
	
	public SimaJSONHandler(){
		try {
			fileWriter = new FileWriter();
			treemapFull = new JSONArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void setData(itfMapTreeNode rawData) {
	    this.rawData = rawData;
	}
	
//	public void testData(){
//
//		for(int i = 0; i < 1; i++){
//			JSONObject temp = generateTreemapNode(
//					"id " + i, 
//					"Name " + i,
//					(double)((double)i/(double)10), 
//					"#"+i+"000"+i+"0",  //hex color
//					(double)(Math.random() * 1), 
//					false);
//			treemapFull.put(temp);
//		}
//		sendTreeMapData();
//	}
	
	protected JSONObject generateTreeMapNode(itfMapTreeNode node) {
		Set<itfMapTreeNode> children = node.getChildren();
		JSONObject jsonNode = generateTreemapNode(node.getData("id"), node.getData("name"), Double.parseDouble(node.getData("darkeningFactor")), node.getData("color"), Double.parseDouble(node.getData("size")), !children.isEmpty());
		JSONArray jsonArray = null;
        
		for(itfMapTreeNode child : children) {
			try {
                (jsonNode.getJSONArray("children")).put(generateTreeMapNode(child));
            } catch (JSONException e) {
                // TODO (kollmann) - Auto-generated catch block
                e.printStackTrace();
            }
		}
		
		return jsonNode;
	}
	
	protected JSONObject generateTreemapNode(String id, String name, double darkeningFactor, String color, double size, boolean hasChildren) {
		
		JSONObject treemapNode = new JSONObject();

		try {
			treemapNode.put("id", id);
	
		treemapNode.put("name", name);
		treemapNode.put("darkeningFactor", darkeningFactor);
		treemapNode.put("color", color);
		treemapNode.put("size", size);
		if(hasChildren)
			treemapNode.put("children", new JSONArray());
		
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return treemapNode;
		
	}
	
//	public class DecisionTreeNode extends JSONObject {
//	    public DecisionTreeNode(itfMapTreeNode data) {
//	        this.put("level", value)
//	    }
//	}
	
//	protected JSONObject generateDecisionTreeNode(String name, double value) {
//	    {
//            "level": "GREY",
//            "children": 
//            [
//                
//            ],
//
//            "nameColor": "green",
//            "name": "FactoryAgent 2",
//            "position": 4,
//            "type": "GREY",
//            "message": "GREEN",
//            "groupname": "Hard to change - high connectivity",
//            "value": 10
//        }
//	}
	
	protected void datapreperationMapTree(itfMapTreeNode root) {
	    //remove the 
	}
	
	public void writeDecisionTreeData(itfMapTreeNode root) {
	    JSONArray data = new JSONArray();
	    data.put(root.toJson());
	    
	    fileWriter.writeDecisionTreeData(data);
	}
	
	public void writeTreeMapData(itfMapTreeNode root) {
	    JSONArray data = new JSONArray();
        data.put(root.toJson());
        
        fileWriter.writeTreemapData(data);
    }
	
	/**
	 * Sends the JSONObject with the data to the filewriter.
	 */
	
	public void sendTreeMapData(itfMapTreeNode root){
		treemapFull.put(generateTreeMapNode(root));
		
		fileWriter.writeDecisionTreeData(treemapFull);
	}
	
}
