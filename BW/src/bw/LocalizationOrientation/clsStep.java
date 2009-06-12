<<<<<<< .mine
package bw.LocalizationOrientation;


/**
 * @author  monkfoodb
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
	 * @uml.property  name="currentArea"
	 */
	public int getCurrentArea() {
		return currentArea;
	}

	/**
	 * @param currentArea  the currentArea to set
	 * @uml.property  name="currentArea"
	 */
	public void setCurrentArea(int currentArea) {
		this.currentArea = currentArea;
	}

	/**
	 * @return  the nextarea
	 * @uml.property  name="nextarea"
	 */
	public int getNextarea() {
		return nextarea;
	}

	/**
	 * @param nextarea  the nextarea to set
	 * @uml.property  name="nextarea"
	 */
	public void setNextarea(int nextarea) {
		this.nextarea = nextarea;
	}

	/**
	 * @return  the pathToNext
	 * @uml.property  name="pathToNext"
	 */
	public clsMove getPathToNext() {
		return PathToNext;
	}

	/**
	 * @param pathToNext  the pathToNext to set
	 * @uml.property  name="pathToNext"
	 */
	public void setPathToNext(clsMove pathToNext) {
		PathToNext = pathToNext;
	}

	/**
	 * @return  the pathToPrev
	 * @uml.property  name="pathToPrev"
	 */
	public clsMove getPathToPrev() {
		return PathToPrev;
	}

	/**
	 * @param pathToPrev  the pathToPrev to set
	 * @uml.property  name="pathToPrev"
	 */
	public void setPathToPrev(clsMove pathToPrev) {
		PathToPrev = pathToPrev;
	}

	/**
	 * @return  the prevArea
	 * @uml.property  name="prevArea"
	 */
	public int getPrevArea() {
		return prevArea;
	}

	/**
	 * @param prevArea  the prevArea to set
	 * @uml.property  name="prevArea"
	 */
	public void setPrevArea(int prevArea) {
		this.prevArea = prevArea;
	}
	
	public void resetReachedStatus(){
		PathToPrev.reached=false;
		PathToNext.reached=false;
	}
	
}
=======
package bw.LocalizationOrientation;

import sim.util.Bag;

/**
 * @author  monkfoodb
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
	 * @uml.property  name="currentArea"
	 */
	public int getCurrentArea() {
		return currentArea;
	}

	/**
	 * @param currentArea  the currentArea to set
	 * @uml.property  name="currentArea"
	 */
	public void setCurrentArea(int currentArea) {
		this.currentArea = currentArea;
	}

	/**
	 * @return  the nextarea
	 * @uml.property  name="nextarea"
	 */
	public int getNextarea() {
		return nextarea;
	}

	/**
	 * @param nextarea  the nextarea to set
	 * @uml.property  name="nextarea"
	 */
	public void setNextarea(int nextarea) {
		this.nextarea = nextarea;
	}

	/**
	 * @return  the pathToNext
	 * @uml.property  name="pathToNext"
	 */
	public clsMove getPathToNext() {
		return PathToNext;
	}

	/**
	 * @param pathToNext  the pathToNext to set
	 * @uml.property  name="pathToNext"
	 */
	public void setPathToNext(clsMove pathToNext) {
		PathToNext = pathToNext;
	}

	/**
	 * @return  the pathToPrev
	 * @uml.property  name="pathToPrev"
	 */
	public clsMove getPathToPrev() {
		return PathToPrev;
	}

	/**
	 * @param pathToPrev  the pathToPrev to set
	 * @uml.property  name="pathToPrev"
	 */
	public void setPathToPrev(clsMove pathToPrev) {
		PathToPrev = pathToPrev;
	}

	/**
	 * @return  the prevArea
	 * @uml.property  name="prevArea"
	 */
	public int getPrevArea() {
		return prevArea;
	}

	/**
	 * @param prevArea  the prevArea to set
	 * @uml.property  name="prevArea"
	 */
	public void setPrevArea(int prevArea) {
		this.prevArea = prevArea;
	}
	
	
}
>>>>>>> .r2653
