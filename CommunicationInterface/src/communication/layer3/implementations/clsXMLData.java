package communication.layer3.implementations;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.StringReader;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.dom4j.io.DOMReader;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import communication.datatypes.clsDataContainer;
import communication.datatypes.clsDataPoint;

public class clsXMLData extends clsLayer3Base{

	@Override
	protected String processSend(clsDataContainer poInputData) {
		String oRetVal ="";
		Document document;
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder;

			builder = factory.newDocumentBuilder();

			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
		ArrayList<clsDataPoint> oDataPointList = poInputData.getData();
		Element root = document.createElement("Data");
		document.appendChild(root);

		for (clsDataPoint oDataPoint: oDataPointList){
			Element x = createElement(oDataPoint, document);
			if( x!=null) root.appendChild(x);
		}
		try{
	           DOMReader reader = new DOMReader();
	           org.dom4j.Document doc2 = reader.read(document);
//	           System.out.println(doc2.asXML());
	           oRetVal = doc2.asXML().toString();
//	           FileWriter ausgabestrom = new FileWriter("test_ausgabe1.xml"); 
//	           BufferedWriter output = new BufferedWriter(ausgabestrom); 
//	           output.write(doc2.asXML().toString()); 
//	           output.flush(); 
//	           output.close(); 
		}catch(Exception e){
			e.printStackTrace();
		}
		
		return oRetVal;
	}
	
	private Element createElement(clsDataPoint poDataPoint, Document poDoc){
		if(poDataPoint !=null){
			Element oRetVal = poDoc.createElement(poDataPoint.getType());
			if(!poDataPoint.getValue().equals("")){
				oRetVal.setAttribute("value", poDataPoint.getValue());
			}
			for (clsDataPoint oChild: poDataPoint.getAssociatedDataPoints()){
				Element x = createElement(oChild,poDoc);
				if(x!= null) oRetVal.appendChild(x);
				
			}
	
			return oRetVal;
		}
		else return null;
	}

	@Override
	protected clsDataContainer processRecv(String poInputData) {
		clsDataContainer oRetVal = new clsDataContainer();
		try {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();;
	    InputSource is = new InputSource(new StringReader(poInputData));
	    
	    Document document = builder.parse(is);
	    
	    NodeList x =document.getElementsByTagName("Data");
	    int x1 = x.getLength();
	    Node oData = x.item(0);
	    NodeList oChilds = oData.getChildNodes();
	    for(int i=0; i<oChilds.getLength();i++){
	    	if(!oChilds.item(i).getNodeName().equals("#text")){
	    		oRetVal.addDataPoint(createDataPoint(oChilds.item(i)));
	    	}
	    }
	    	//NodeList v1 =y1.getChildNodes();
	    //System.out.println(x.toString());
	    
		} catch (Exception e) {
			e.printStackTrace();
		}
		return oRetVal;
	}
	
	private clsDataPoint createDataPoint(Node oNode){
		String type = oNode.getNodeName().toString();
		String value="";
		if(oNode.getAttributes()!=null){
			if(oNode.getAttributes().getLength()!=0){
				if(oNode.getAttributes().getNamedItem("value")!=null){
					value = oNode.getAttributes().getNamedItem("value").getTextContent();
				}
			}
		}
		clsDataPoint oRetVal = new clsDataPoint(type,value);
		NodeList oChildList = oNode.getChildNodes();
		for (int i=0; i<oChildList.getLength();i++){
			if(!oChildList.item(i).getNodeName().equals("#text")){
				oRetVal.addAssociation(createDataPoint(oChildList.item(i)));
			}
		}

		
		return oRetVal;
	}
	



}
