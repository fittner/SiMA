/**
 * @author borer
 */
 
package LocalizationOrientation;

public class clsDeadReckoning {

	/*
	 * The calcLink calculates the necessary angles and distances to 
	 * be able to calc the direction and distance to get to this transition point again
	 * This is angle beta(between mainObject-secondaryObject and mainObject-transitionPosition) 
	 * and distance between the mainObject and the secondaryObject
	 * as well as the heading direction relative to the mainObject at the point of transition
	 *
	 * @param area: the current area
	 * @param objects: the object data of the current environment
	 *
	 * @return the calculated clsMove contrainer with the necessary data to find the location again.
	 */	
	public clsMove calcLink(clsArea area,clsPerceivedObj objects){
		clsMove newLink = null;
		if ((area.getNumObj()>1)&&(objects.getNum()>1)){
			newLink=new clsMove();
			int i,mainObject=-1,secondaryObject=-1;
			
			//find main Object in perceived object list and set the angle as heading direction in newLink
			for (i=0;i<objects.getNum();i++){
				if (objects.getObject(i).equals(area.getObjects().getObject(0)))
					mainObject=i;
				if (objects.getObject(i).equals(area.getObjects().getObject(1)))
					secondaryObject=i;
			}
					
			
			//if no mainObject and secondaryObject was found, return null
			if((mainObject==-1)||(secondaryObject==-1))
				return null;
			
			newLink.direction=objects.getBearing(mainObject);
			
			double beta,alpha,B,A,C;
			A=objects.getDistance(mainObject);
			C=objects.getDistance(secondaryObject);
			alpha=(objects.getBearing(mainObject)>objects.getBearing(secondaryObject)) ? 
					(objects.getBearing(secondaryObject)-objects.getBearing(mainObject)+360) : 
						(objects.getBearing(secondaryObject)-objects.getBearing(mainObject));
						
			//calc B using the Law of Cosine
			B = Math.sqrt(Math.pow(A, 2)+Math.pow(C, 2)-(2*A*C*Math.cos(Math.toRadians(alpha))));
			//calc beta using the Law of Cosine
			beta= Math.toDegrees(Math.acos((Math.pow(A, 2)+Math.pow(B, 2)-Math.pow(C, 2))/(2*A*B)));
			//beta has to be adjusted for later usage when calculating the necessaty direction to get to the link point
			//!! no adjustment !! with 2 landmarks -> 2 possible positions for the transition to be. we say the closer one is sure to be the right one
			//beta = beta * (alpha>180?-1:1);
			newLink.B=B;
			newLink.beta=beta;
			newLink.A=A;
			newLink.alpha=alpha;
		}
		return newLink;
	}
}
