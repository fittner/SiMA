package ReinforcedRobot;

public class clsIntrinsicMotivation {
	
	int fReward;
	int hReward;
	int currentMot;
	private final int APPROACH_FOOD = 1;
	private final int APPROACH_HOME = 2;
	
	public clsIntrinsicMotivation() {
		fReward=1000;
		hReward=1000;
	}
	
	public int intrinsicMotivation(){
	
		if (fReward<50)
		 currentMot=1;
			
		else if (hReward<50)
			currentMot=2;
			
		return currentMot;
	
	}
	
}
