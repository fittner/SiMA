/**
 * @author borer
 */
package students.borer.LocalizationOrientation;


/**
 * contains the data of a single step. the move containers for both crossing directions and area id's
 */
public class clsStep {
	
	private int prevArea;
	private int currentArea;
	private int nextarea;
	
	private clsMove PathToPrev;
	private clsMove PathToNext;
	
	public clsStep(){
		PathToPrev=null;
		PathToNext=null;
	}

	/**
	 * @return  the currentArea
	 */
	public int getCurrentArea() {
		return currentArea;
	}

	/**
	 * @param currentArea  the currentArea to set
	 */
	public void setCurrentArea(int currentArea) {
		this.currentArea = currentArea;
	}

	/**
	 * @return  the nextarea
	 */
	public int getNextarea() {
		return nextarea;
	}

	/**
	 * @param nextarea  the nextarea to set
	 */
	public void setNextarea(int nextarea) {
		this.nextarea = nextarea;
	}

	/**
	 * @return  the pathToNext
	 */
	public clsMove getPathToNext() {
		return PathToNext;
	}

	/**
	 * @param pathToNext  the pathToNext to set
	 */
	public void setPathToNext(clsMove pathToNext) {
		PathToNext = pathToNext;
	}

	/**
	 * @return  the pathToPrev
	 */
	public clsMove getPathToPrev() {
		return PathToPrev;
	}

	/**
	 * @param pathToPrev  the pathToPrev to set
	 */
	public void setPathToPrev(clsMove pathToPrev) {
		PathToPrev = pathToPrev;
	}

	/**
	 * @return  the prevArea
	 */
	public int getPrevArea() {
		return prevArea;
	}

	/**
	 * @param prevArea  the prevArea to set
	 */
	public void setPrevArea(int prevArea) {
		this.prevArea = prevArea;
	}
	
	public void resetReachedStatus(){
		PathToPrev.reached=false;
		PathToNext.reached=false;
	}
	
}
