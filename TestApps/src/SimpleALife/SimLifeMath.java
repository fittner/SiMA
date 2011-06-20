package SimpleALife;

import sim.physics2D.util.*;
import sim.util.Bag;

public class SimLifeMath
{
	public static double modulo(double value, double range)
	{
		while (value >= range)
			value -= range;

		return value;
	}

    // returns the angle between the x-axis and the target vector (counter clockwise means positive)
    public static Angle getAngle (Double2D vector)
    {
    	Angle ang = new Angle(Math.abs(Math.atan(vector.y / vector.x)));
    	if (vector.getX() >= 0 && vector.getY() < 0)		// 1th quadrant
    		return ang;
    	else if (vector.getX() < 0 && vector.getY() < 0)	// 2nd quadrant
    		return new Angle(Math.PI - ang.radians);
    	else if (vector.getX() < 0 && vector.getY() >= 0)	// 3rd quadrant
    		return ang.add(Math.PI);
    	else												// 4th quadrant
			return new Angle(2*Math.PI - ang.radians);
    }

    // returns the angle between vector start-p1 and start-p2 (counter-clockwise positive)
    public static double getDiffAngle(Double2D start, Double2D p1, Double2D p2)
    {
    	Double2D line1 = p1.subtract(start);
		double a1 = getAngle(line1).radians;
		a1 = modulo(a1, 2*Math.PI);

		Double2D line2 = p2.subtract(start);
		double a2 = getAngle(line2).radians;
		a2 = modulo(a2, 2*Math.PI);

		double diffAngle = a2 - a1;
		if (diffAngle > Math.PI)
			diffAngle -= 2*Math.PI;
		else if (diffAngle <= -Math.PI)
			diffAngle += 2*Math.PI;

		return diffAngle;
    }

    // returns four Double2D values from the 4 Rock corners
    // : in this version only Rocks with angle 0 would be calculated right
	private static Double2D[] getRockCorners(Rock rock)
    {
    	Double2D center, point1, point2, point3, point4;
    	double width, height;

    	center = rock.getPosition();
    	width = (double)rock.getWidth();
    	height = (double)rock.getHeight();

    	point1 = center.add(new Double2D(-width/2, -height/2));
    	point2 = center.add(new Double2D(width/2, -height/2));
    	point3 = center.add(new Double2D(width/2, height/2));
    	point4 = center.add(new Double2D(-width/2, height/2));

    	return new Double2D[] {point1, point2, point3, point4};
    }

    // if the target lies outside our arena set a new target inside
    // not important : make a 2nd method with (pos, target, size) and scale the target vector to don't change the angle
	public static Double2D limitToBorders(Double2D target, double objsSize)
	{
		double x, y;
		double border = SimLifeArena.arenaWallThickness + objsSize/2 + 1;

		x = target.getX();
    	if (x <= border)
    		x = border;
    	else if (x >= SimLifeArena.arenaWidth - border)
    		x = SimLifeArena.arenaWidth - border;

    	y = target.getY();
    	if (y <= border)
    		y = border;
    	else if (y >= SimLifeArena.arenaHeight - border)
    		y = SimLifeArena.arenaHeight - border;

    	return new Double2D(x, y);
	}

	public static Double2D limitToBorders(Double2D pos, Double2D target, double objsSize)
	{
		double x1, x2, y1, y2, diffX = 0, diffY = 0;
		double border = SimLifeArena.arenaWallThickness + objsSize/2 + 1;

		x1 = pos.x;
		x2 = target.x;
    	if (x2 <= border)
    		diffX = (x1 - border) / (x1 - x2);
    	else if (x2 >= SimLifeArena.arenaWidth - border)
    		diffX = (SimLifeArena.arenaWidth - border - x1) / (x2 - x1);

    	y1 = pos.y;
    	y2 = target.y;
    	if (y2 <= border)
    		diffY = (y1 - border) / (y1 - y2);
    	else if (y2 >= SimLifeArena.arenaHeight - border)
    		diffY = (SimLifeArena.arenaHeight - border - y1) / (y2 - y1);

    	if (diffX == 0 && diffY == 0)
    		return new Double2D(x2, y2);
    	else if (diffX > 0 && diffY > 0)
    	{
    		if (diffX <= diffY)
    			return new Double2D(x2*diffX, y2*diffX);
    		else
    			return new Double2D(x2*diffY, y2*diffY);
    	}
    	else if (diffX > 0)
    		return new Double2D(x2*diffX, y2*diffX);
    	else
    		return new Double2D(x2*diffY, y2*diffY);
	}

    public static boolean checkLineCrossing(Double2D start, Double2D end, Double2D p1, Double2D p2)
    {
    	boolean lineCrossing = false;

    	// : Replace names and make a sketch for them
    	Double2D line1 = end.subtract(start);	// global vector from start1 to target1
		Angle aZ = getAngle(line1);				// global angel to the target1 (from the x-axis)
		double aZr = aZ.radians;
		double distZ = start.subtract(end).length();

		Double2D line2 = p1.subtract(start);
		Angle a1 = getAngle(line2);
		double a1r = a1.radians;
		double dist1 = start.subtract(p1).length();

		Double2D line3 = p2.subtract(start);
		Angle aX = getAngle(line3);
		double aXr = aX.radians;
		double distX = start.subtract(p2).length();

		double bXr = aXr - aZr;					// angle between alpha1 (start1-end1) and alpha3 (start1-end2)

		Angle g = a1.add(Math.PI);				// global angle to a virtual line at alpha2+pi
		double gr = g.radians;

		// now check if they are crossing
		if (a1r == aZr && dist1 <= distZ)
			lineCrossing = true;
		else if (bXr == 0 && distX <= distZ)
			lineCrossing = true;
		else if (a1r <= Math.PI)
		{
			if (a1r > aZr && aZr >= 0)
			{
				if (bXr < 0 || (bXr > 0 && aXr >= gr))
					lineCrossing = true;
			}
			else if (a1r < aZr && aZr <= gr)
			{
				if ((bXr >= 0 && aXr <= gr) || (bXr == 0 && aXr == gr))
					lineCrossing = true;
			}
			else if (aZr > gr)
			{
				if (bXr < 0 && aXr >= gr)
					lineCrossing = true;
			}
		}
		else
		{
			if (gr > aZr && aZr >= gr)
			{
				if (bXr > 0 && aXr <= gr)
					lineCrossing = true;
			}
			else if (a1r > aZr && aZr >= gr)
			{
				if ((bXr < 0 && aXr >= gr) || (bXr == 0 && aXr == gr))
					lineCrossing = true;
			}
			else if (aZr > a1r)
			{
				if (bXr > 0 || aXr <= gr)
					lineCrossing = true;
			}
		}

		return lineCrossing;
    }

    public static Double2D getSubTargetPos(Double2D start, Double2D end, Bag objs)
    {
    	Bag nextObjs = new Bag(objs);
		Rock nearestRock = null;
		Rock tempRock;
		Double2D[] rockCorner = null;
		double dist = SimLifeArena.maxDist;
		double newDist;
		Double2D newEnd = end;
		boolean isObstacle = false;

		while (! isObstacle)
		{
			// Find the nearest Rock
			for (int i = 0; i < nextObjs.numObjs; i++)
			{
				if (nextObjs.objs[i] instanceof Rock)
				{
					tempRock = (Rock)nextObjs.objs[i];
					// don't look on area border Walls because they can't be an obstacle
					if (! tempRock.getAreanaLimit())
					{
						newDist = start.subtract(tempRock.getPosition()).length();
						if (newDist < dist)
						{
							dist = newDist;
							nearestRock = tempRock;
						}
					}
				}
			}

			// look if the nearest Rock lies in the way to the target
			// : don't check only the line of sight (LoS), check an object-width corridor and an start to target "trapez" (there for -> getSubTargetPos(start, end, objs, ownSize, targetSize))
			//		 (De: zb. LoS=fals -> isObstacle u. subTarget berechnung wie jetzt,
			//		  LoS=gut aber LoTrapez=false -> CarT nicht zum ziel sondern 90° auf die "gute" trapez seite eine botSize,
			//		  LoTrapez=gut aber LoCorridor=false -> ziel ist end+"90° auf trapezlinie" 1/2botSize auf "gute" corridor seite)
			// -> : find an exact subTarget (not +x°) .. eg. (corner+90°botSizeSteps+1)+"botSizsSteps in new direction"
			// -> : now we could search a new subTarget every step without corner collision (if() in CarT.goToCollisionFree), good for movable objects, but for performance we do it only every X steps (step counter++)
			if (nearestRock != null)
			{
				rockCorner = getRockCorners(nearestRock);

				// check if a line from the rock crosses the way to the target
				// : sometimes they find to many obstacles, sometimes they don't see one !!!!
				int j;
				for (int i = 0; i < rockCorner.length; i++)
				{
					j = i+1;
					if (j == rockCorner.length)
						j = 0;

					if (checkLineCrossing(start, end, rockCorner[i], rockCorner[j]))
					{
						isObstacle = true;
						break;
					}
				}

				// if the rock isn't an obstacle remove it from the Bag and check the next-nearest wall (in the loop)
				if (! isObstacle)
				{
					nextObjs.remove(nearestRock);
					nearestRock = null;
					tempRock = null;
				}
			}
			else
				break;
		}

		// if an obstacle were found, find a point to bypass it
		if (isObstacle)
		{
			double alpha, aPos = 0.0, aNeg = 0.0, distPos = 0.0, distNeg = 0.0;
			Double2D cornerPos = null, cornerNeg = null;
			Double2D cornerWay;
			boolean goPos;

			double addAngle = 0.15;	// ~ 9°
			int addSteps = 35;

			Double2D targetWay = end.subtract(start);	// a global vector from start to target
			double distTarget = targetWay.length();

//			System.out.println("Rock Corners "+rockCorner[0]+rockCorner[1]+rockCorner[2]+rockCorner[3]);	// debug

			for (int i = 0; i < rockCorner.length; i++)
			{
				alpha = getDiffAngle(start, end, rockCorner[i]);
//				System.out.println("DiffAngle "+alpha);			// debug

				if (alpha >= aPos)
				{
					aPos = alpha;
					cornerPos = rockCorner[i];
					distPos = cornerPos.subtract(start).length();
//					System.out.println("Pos0: aPos "+aPos+" corn "+cornerPos);		// debug
				}
				if (alpha <= aNeg)
				{
					aNeg = alpha;
					cornerNeg = rockCorner[i];
					distNeg = cornerNeg.subtract(start).length();
//					System.out.println("Neg0: aNeg "+aNeg+" corn "+cornerNeg);		// debug
				}

			}

			if (Math.abs(aPos) <= Math.abs(aNeg))
			{
				if (distPos >= distTarget && distTarget >= distNeg && distPos != distNeg)
					goPos = false;
				else
					goPos = true;
			}
			else
			{
				if (distNeg >= distTarget && distTarget >= distPos && distPos != distNeg)
					goPos = true;
				else
					goPos = false;
			}

			if (goPos)
				alpha = aPos;
			else
				alpha = aNeg;

			if (goPos)
			{
//				System.out.println("Pos1: start "+start);		// debug
				cornerWay = cornerPos.subtract(start);
//				System.out.println("Pos2: way "+cornerWay);		// debug
				newEnd = start.add(targetWay.rotate(-(aPos+addAngle)).setLength(cornerWay.length()+addSteps));	// point to the corner + addAngle° and + addSteps steps
//				System.out.println("Pos3: target "+newEnd);		// debug
			}
			else
			{
//				System.out.println("Neg1: start "+start);		// debug
				cornerWay = cornerNeg.subtract(start);
//				System.out.println("Neg2: way: "+cornerWay);	// debug
				newEnd = start.add(targetWay.rotate(-(aNeg-addAngle)).setLength(cornerWay.length()+addSteps));
//				System.out.println("Neg3: target "+newEnd);		// debug
			}
		}

    	return newEnd;
    }
}
