package nao.main;

public class clsSingletonNAOState {
	private static clsSingletonNAOState instance = null;
	private boolean keeprunning = false;
	    
	private clsSingletonNAOState(boolean keeprunning) {this.keeprunning = keeprunning;}
	private static clsSingletonNAOState getInstance() {
		if (instance == null) {
			instance = new clsSingletonNAOState(true);
		}
	    return instance;
	}
	
	public static boolean getKeeprunning() {
		return clsSingletonNAOState.getInstance().keeprunning;
	}

	public static void setKeeprunning(boolean keeprunning) {
		clsSingletonNAOState.getInstance().keeprunning = keeprunning;
	}
}
