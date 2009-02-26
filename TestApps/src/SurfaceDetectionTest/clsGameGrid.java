package SurfaceDetectionTest;

import java.util.HashMap;
import SurfaceDetectionTest.eMotivationType;

public class clsGameGrid {
	public HashMap<clsDiscreteLocation, clsGridCell> moGameGrid = new HashMap<clsDiscreteLocation, clsGridCell>();

	public clsGameGrid() {
		
		
		//ADDING ENTRIES
		
		clsDiscreteLocation oExampleAgentPosFood = new clsDiscreteLocation(1,2);
		clsDiscreteLocation oExampleDestinationFood = new clsDiscreteLocation(5,2);
		
		clsDiscreteLocation oExampleDestinationPlay = new clsDiscreteLocation(4,5);
		
		
		int nMotivationFood = 1;
		clsGridMotivation oExampleMotvationFood = new clsGridMotivation();
		clsGridReward oExampleRewardFood = new clsGridReward(8, oExampleDestinationFood); 
		int nSouth = 2; //use 2 for south, 3 for east and 4 for west (for example)
		oExampleMotvationFood.moGridRewardList.put(nSouth, oExampleRewardFood);
		
		clsGridCell oExampleGridCellFood = new clsGridCell();
		oExampleGridCellFood.moGridMotivations.put(nMotivationFood, oExampleMotvationFood);
		moGameGrid.put(oExampleAgentPosFood, oExampleGridCellFood);
				
		int nMotivationPlay = 2;
		clsGridMotivation oExampleMotvationPlay = new clsGridMotivation();
		clsGridReward oExampleRewardPlay = new clsGridReward(8, oExampleDestinationPlay); 
		int nNorth = 1; //use 2 for south, 3 for east and 4 for west (for example)
		oExampleMotvationFood.moGridRewardList.put(nNorth, oExampleRewardPlay);
		
		clsGridCell oExampleGridCellPlay = new clsGridCell();
		oExampleGridCellPlay.moGridMotivations.put(nMotivationPlay, oExampleMotvationPlay);
		moGameGrid.put(oExampleDestinationPlay, oExampleGridCellPlay);

		//TODO:Automatic addition of the values within the list 
		
		
		
		//ACCESS EXISTING ENTRIES
		
		clsGridCell oReturnedExampleCell = this.moGameGrid.get(oExampleAgentPosFood);
		clsGridMotivation oReturnedMotivation = oReturnedExampleCell.moGridMotivations.get(nMotivationFood);
		clsGridReward oReturnedReward = oReturnedMotivation.moGridRewardList.get(nNorth);
		
		int nReturnedReward = oReturnedReward.mnRewardValue;
				
		//TODO:Automatic retrival of the values 
	}
}
