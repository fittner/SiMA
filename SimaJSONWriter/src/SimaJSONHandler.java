import java.io.IOException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SimaJSONHandler {

	FileWriter fileWriter;
	JSONArray treemapFull;
	
	public SimaJSONHandler(){
		try {
			fileWriter = new FileWriter();
			treemapFull = new JSONArray();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void testData(){

		for(int i = 0; i < 1; i++){
			JSONObject temp = generateTreemapNode(
					"id " + i, 
					"Name " + i,
					(double)((double)i/(double)10), 
					"#"+i+"000"+i+"0",  //hex color
					(double)(Math.random() * 1), 
					false);
			treemapFull.put(temp);
		}
		sendTreeMapData();
	}
	
	private JSONObject generateTreemapNode(String id, String name, double darkeningFactor, String color, double size, boolean hasChildren){
		
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
	
	/**
	 * Sends the JSONObject with the data to the filewriter.
	 */
	
	public void sendTreeMapData(){
		fileWriter.writeTreemapData(treemapFull);
	}
	
}
