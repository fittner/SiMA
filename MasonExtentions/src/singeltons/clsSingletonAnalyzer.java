package singeltons;

//import datageneration.clsAnalyzer;
import sim.engine.SimState;

public class clsSingletonAnalyzer {
	private static clsSingletonAnalyzer moInstance = null;
//	private clsAnalyzer moAnalyzer = null;
    
	//Hide default constructor
	private clsSingletonAnalyzer() {}
	
    static clsSingletonAnalyzer getInstance() {
       if (moInstance == null) {
            moInstance = new clsSingletonAnalyzer();
       }
       return moInstance;
    }

	/**
	 * @return the moAnalyzer
	 */
//	public clsAnalyzer getAnalyzer() {
//		return moAnalyzer;
//	}
//
//	/**
//	 * @param moAnalyzer the moAnalyzer to set
//	 */
//	public void setAnalyzer(clsAnalyzer poAnalyzer) {
//		if(poAnalyzer == null) {
//			throw new IllegalArgumentException("Analyzer provided to clsSingletonAnalyszer must not be null");
//		}
//		moAnalyzer = poAnalyzer;
//	}
}
