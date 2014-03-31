package du.itf.actions;

/**
 * Eat command
 * No parameters, so anything eatable will be eaten... 
 * 
 * @author Benny Doenz
 * 15.04.2009, 16:31:13
 * 
 */
public class clsActionEat extends clsActionCommand {

	private final double mrBiteSize;
	
	public clsActionEat(){
		mrBiteSize =1.0;
	}
	
	public clsActionEat(double poBiteSize){
		mrBiteSize=poBiteSize;
	}
	@Override
	public String getLog() {
		return "<Eat></Eat>"; 
	}
	/**
	 * @since 25.09.2013 10:11:00
	 * 
	 * @return the mrBiteSize
	 */
	public double getMrBiteSize() {
		return mrBiteSize;
	}
	
	
}
