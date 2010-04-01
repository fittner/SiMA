/**
 * @author borer
 */
 package LocalizationOrientation;


/**
 * An extra class was created for the position in case the movement 
 * encoding is changed from a Double2d to something different
 */

public class clsMove {

	public double beta;
	public double alpha;
	public double B; 
	public double direction;
	public double A;
	public boolean reached; 
	
	public clsMove(){
		reached=false;
	}

	
	public boolean equals(){
		return false;
	}
}
