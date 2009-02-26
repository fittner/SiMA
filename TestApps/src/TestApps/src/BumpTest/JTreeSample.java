package TestApps.src.BumpTest;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.tree.DefaultMutableTreeNode;
import java.io.File;
public class JTreeSample {
	static JTree tree;
	String NodeValue[]=new String[13]; 
	
//	HashMap<String, Integer>  EnergyConsumptionTable =  new HashMap<String, Integer>();
//	HashMap<String, Integer>  InternalSensorsTable =  new HashMap<String, Integer>();
//	HashMap<String, Integer>  ExternalSensorsTable =  new HashMap<String, Integer>();
//	HashMap<String, Integer>  InternalActuatorTable =  new HashMap<String, Integer>();
//	HashMap<String, Integer>  ExternalActuatorTable =  new HashMap<String, Integer>();
	
	public JTreeSample()
	{
		
		DefaultMutableTreeNode EnergyConsumptionNode = new DefaultMutableTreeNode("EnergyConsumption");
		DefaultMutableTreeNode EnergyConsumptionNodeIds = new DefaultMutableTreeNode("ID");
		
//		FileNode root=new FileNode("1","Library",1,0);
		createNodes(EnergyConsumptionNode);
		createNodesIds(EnergyConsumptionNodeIds);
		tree=new JTree(EnergyConsumptionNode);
	}
	public static JTree getTree()
	{
		new JTreeSample();
		return tree;
	}
	public void createNodes(DefaultMutableTreeNode poEnergyConsumptionNode/*FileNode root*/)
	{
		
		DefaultMutableTreeNode InternelSensorsNode = new DefaultMutableTreeNode("InternalSensors");
		DefaultMutableTreeNode InternelActuatorsNode = new DefaultMutableTreeNode("InternalActuators");
		DefaultMutableTreeNode ExternalSensorsNode = new DefaultMutableTreeNode("ExternalSensors");
		DefaultMutableTreeNode ExternalActuatorsNode = new DefaultMutableTreeNode("ExternalActuators");	
		
		poEnergyConsumptionNode.add(InternelSensorsNode);
		poEnergyConsumptionNode.add(InternelActuatorsNode);
		poEnergyConsumptionNode.add(ExternalSensorsNode);
		poEnergyConsumptionNode.add(ExternalActuatorsNode);
		
		DefaultMutableTreeNode DigestiveSensorNode = new DefaultMutableTreeNode("DigestiveSensor");
		DefaultMutableTreeNode BloodPressureSensorNode = new DefaultMutableTreeNode("BloodPressureSensor");
		InternelSensorsNode.add(DigestiveSensorNode);
		InternelSensorsNode.add(BloodPressureSensorNode);
		
		DefaultMutableTreeNode abcActuatorsNode = new DefaultMutableTreeNode("Abc");
		InternelActuatorsNode.add(abcActuatorsNode);
		
		DefaultMutableTreeNode VisionSensorsNode = new DefaultMutableTreeNode("VisionSensor");
		DefaultMutableTreeNode BumpSensorsNode = new DefaultMutableTreeNode("BumpSensor");
		DefaultMutableTreeNode OdometrySensorsNode = new DefaultMutableTreeNode("OdometrySensor");
		ExternalSensorsNode.add(VisionSensorsNode);
		ExternalSensorsNode.add(BumpSensorsNode);
		ExternalSensorsNode.add(OdometrySensorsNode);
		
		
		DefaultMutableTreeNode LeftHandActuatorsNode = new DefaultMutableTreeNode("LeftHandActuator");
		DefaultMutableTreeNode RightHandActuatorsNode = new DefaultMutableTreeNode("RightHandActuator");
		DefaultMutableTreeNode LegActuatorsNode = new DefaultMutableTreeNode("LegActuator");
		ExternalActuatorsNode.add(LeftHandActuatorsNode);
		ExternalActuatorsNode.add(RightHandActuatorsNode);
		ExternalActuatorsNode.add(LegActuatorsNode);
		
//	FileNode hall1=new FileNode("2","Hall1",2,0);
//	FileNode hall2=new FileNode("3","Hall2",3,0);
//	FileNode hall3=new FileNode("4","Hall3",4,0);
//	root.add(hall1);
//	root.add(hall2);
//	root.add(hall3);
	 
//	hall1.add(new FileNode("5","Room1",5,0));
//	hall1.add(new FileNode("6","Room2",6,0));
//	hall1.add(new FileNode("7","Room3",7,0));
//	hall2.add(new FileNode("8","Room4",8,0));
//	hall2.add(new FileNode("9","Room5",9,0));
//	hall3.add(new FileNode("10","Room6",10,0));
//	hall3.add(new FileNode("11","Room7",11,0));
//	hall3.add(new FileNode("12","Room8",12,0));
	}
	public void createNodesIds(DefaultMutableTreeNode poEnergyConsumptionNode/*FileNode root*/)
	{
		
		DefaultMutableTreeNode InternelSensorsNode = new DefaultMutableTreeNode("1");
		DefaultMutableTreeNode InternelActuatorsNode = new DefaultMutableTreeNode("2");
		DefaultMutableTreeNode ExternalSensorsNode = new DefaultMutableTreeNode("3");
		DefaultMutableTreeNode ExternalActuatorsNode = new DefaultMutableTreeNode("4");	
		
		poEnergyConsumptionNode.add(InternelSensorsNode);
		poEnergyConsumptionNode.add(InternelActuatorsNode);
		poEnergyConsumptionNode.add(ExternalSensorsNode);
		poEnergyConsumptionNode.add(ExternalActuatorsNode);
		
		DefaultMutableTreeNode DigestiveSensorNode = new DefaultMutableTreeNode("1.1");
		DefaultMutableTreeNode BloodPressureSensorNode = new DefaultMutableTreeNode("1.2");
		InternelSensorsNode.add(DigestiveSensorNode);
		InternelSensorsNode.add(BloodPressureSensorNode);
		
		DefaultMutableTreeNode abcActuatorsNode = new DefaultMutableTreeNode("2.1");
		InternelActuatorsNode.add(abcActuatorsNode);
		
		DefaultMutableTreeNode VisionSensorsNode = new DefaultMutableTreeNode("3.1");
		DefaultMutableTreeNode BumpSensorsNode = new DefaultMutableTreeNode("3.2");
		DefaultMutableTreeNode OdometrySensorsNode = new DefaultMutableTreeNode("3.3");
		ExternalSensorsNode.add(VisionSensorsNode);
		ExternalSensorsNode.add(BumpSensorsNode);
		ExternalSensorsNode.add(OdometrySensorsNode);
		
		
		DefaultMutableTreeNode LeftHandActuatorsNode = new DefaultMutableTreeNode("4.1");
		DefaultMutableTreeNode RightHandActuatorsNode = new DefaultMutableTreeNode("4.2");
		DefaultMutableTreeNode LegActuatorsNode = new DefaultMutableTreeNode("4.3");
		ExternalActuatorsNode.add(LeftHandActuatorsNode);
		ExternalActuatorsNode.add(RightHandActuatorsNode);
		ExternalActuatorsNode.add(LegActuatorsNode);
		
//	FileNode hall1=new FileNode("2","Hall1",2,0);
//	FileNode hall2=new FileNode("3","Hall2",3,0);
//	FileNode hall3=new FileNode("4","Hall3",4,0);
//	root.add(hall1);
//	root.add(hall2);
//	root.add(hall3);
	 
//	hall1.add(new FileNode("5","Room1",5,0));
//	hall1.add(new FileNode("6","Room2",6,0));
//	hall1.add(new FileNode("7","Room3",7,0));
//	hall2.add(new FileNode("8","Room4",8,0));
//	hall2.add(new FileNode("9","Room5",9,0));
//	hall3.add(new FileNode("10","Room6",10,0));
//	hall3.add(new FileNode("11","Room7",11,0));
//	hall3.add(new FileNode("12","Room8",12,0));
	}
	
	
	public static void main(String args[])
	{
	JFrame fr=new JFrame();
	new JTreeSample();
	JTree tree2=tree;
	fr.getContentPane().add(new JScrollPane(tree2));
	fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	fr.pack();
	fr.setVisible(true);
	
	}
	 
}
