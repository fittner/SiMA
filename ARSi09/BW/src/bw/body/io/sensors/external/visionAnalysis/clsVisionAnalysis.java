//package bw.body.io.sensors.external.visionAnalysis;
//
//import LocalizationOrientation.clsPerceivedObj;
//import bw.physicalObjects.sensors.clsEntityPartVision;
//
//import java.util.HashMap;
//import java.util.Iterator;
//import sim.physics2D.physicalObject.PhysicalObject2D;
//
///**
// * @author  monkfoodb
// */
//public class clsVisionAnalysis {
//	
//	public clsObjectsSemanticMemory moSemanticMemory;
//	private double similarObjectsTolerance=0.01;
//	public clsVisionAnalysis(){
//		moSemanticMemory = new clsObjectsSemanticMemory();
//	}
//	
//	
//	public clsPerceivedObj calc_PerceivedObj(clsEntityPartVision vision, double pos_x, double pos_y){
//		HashMap<Integer, PhysicalObject2D>  VisionObj = vision.getMeVisionObj();
//		double vision_distance = vision.getMnRadius();
//		clsPerceivedObj mePerceiveObj = new clsPerceivedObj(VisionObj.values().size());
//		Iterator<PhysicalObject2D> ObjIterator = VisionObj.values().iterator();
//		while (ObjIterator.hasNext()){
//			PhysicalObject2D Obj = ObjIterator.next();
//			
//			//remember object in semantic memory
//			clsObject newObject = new clsObject(Obj);
//			newObject.setid(moSemanticMemory.rememberObject(newObject, similarObjectsTolerance));
//			
//			if(!(Obj instanceof clsEntityPartVision)){
//						double obj_x=Obj.getPosition().x;
//						double obj_y=Obj.getPosition().y;
//
//						//do not calc if object is bot itselfe
//						if (!( ( (pos_x-obj_x)+(pos_y-obj_y) )==0 )  ){
//								double distance = Math.sqrt(Math.pow(Math.abs(pos_x-obj_x),2) + Math.pow(Math.abs(pos_y-obj_y),2));
//								if (distance<=vision_distance){
//									double bearing = Math.toDegrees(Math.atan((obj_y-pos_y)/(obj_x-pos_x)));
//									if ((obj_x-pos_x)<0){
//										bearing+=180;
//									}else if((obj_y-pos_y)<0){
//										bearing+=360;}
//									bearing -= Math.toDegrees(vision.getOrientation().radians);
//									
//									if (bearing<0)
//										bearing+=360;
//									mePerceiveObj.addObject(newObject, distance, bearing);
//								}
//						}
//			}
//		}
//		//mePerceiveObj.String();
//		return mePerceiveObj;
//	}
//}
