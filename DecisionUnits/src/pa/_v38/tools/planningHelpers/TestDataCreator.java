/**
 * CHANGELOG
 *
 * 03.03.2012 perner - File created
 *
 */
package pa._v38.tools.planningHelpers;

import java.util.ArrayList;

import pa._v38.memorymgmt.datatypes.clsAct;
import pa._v38.memorymgmt.datatypes.clsImage;
import pa._v38.memorymgmt.datatypes.clsPlanFragment;
import pa._v38.memorymgmt.enums.eAction;

/**
 * DOCUMENT (perner) - insert description
 * 
 * @author perner 03.03.2012, 09:56:09
 * 
 */
public class TestDataCreator {

	/**
	 * test-data AW
	 * 
	 * @return
	 * 
	 * @since 19.07.2011 10:24:29
	 * 
	 */
	static public ArrayList<clsPlanFragment> generateTestPlans_AW() {

		ArrayList<clsPlanFragment> moAvailablePlanFragments = new ArrayList<clsPlanFragment>();

		/**
		 * test test dummy to fill internal database
		 */

		// EAT CAKE
		eEntity oEntity = eEntity.CAKE;
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_FORWARD), new clsImage(eDistance.FAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_FORWARD),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.EAT), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_BACKWARD), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_BACKWARD),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity), new clsImage(eDistance.FAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_LEFT), new clsImage(eDirection.LEFT, oEntity), new clsImage(
		    eDirection.MIDDLE_LEFT, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_LEFT), new clsImage(eDirection.MIDDLE_LEFT, oEntity), new clsImage(
		    eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT), new clsImage(eDirection.RIGHT, oEntity), new clsImage(
		    eDirection.MIDDLE_RIGHT, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT), new clsImage(eDirection.MIDDLE_RIGHT, oEntity),
		    new clsImage(eDirection.CENTER, oEntity)));

		// EAT REMOTEBOT
		oEntity = eEntity.CARROT;
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_FORWARD), new clsImage(eDistance.FAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_FORWARD),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.EAT), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_BACKWARD), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_BACKWARD),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity), new clsImage(eDistance.FAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_LEFT), new clsImage(eDirection.LEFT, oEntity), new clsImage(
		    eDirection.MIDDLE_LEFT, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_LEFT), new clsImage(eDirection.MIDDLE_LEFT, oEntity), new clsImage(
		    eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT), new clsImage(eDirection.RIGHT, oEntity), new clsImage(
		    eDirection.MIDDLE_RIGHT, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT), new clsImage(eDirection.MIDDLE_RIGHT, oEntity),
		    new clsImage(eDirection.CENTER, oEntity)));

		// EAT REMOTEBOT
		oEntity = eEntity.REMOTEBOT;
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_FORWARD), new clsImage(eDistance.FAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_FORWARD),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.EAT), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_BACKWARD), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_BACKWARD),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity), new clsImage(eDistance.FAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_LEFT), new clsImage(eDirection.LEFT, oEntity), new clsImage(
		    eDirection.MIDDLE_LEFT, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_LEFT), new clsImage(eDirection.MIDDLE_LEFT, oEntity), new clsImage(
		    eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT), new clsImage(eDirection.RIGHT, oEntity), new clsImage(
		    eDirection.MIDDLE_RIGHT, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT), new clsImage(eDirection.MIDDLE_RIGHT, oEntity),
		    new clsImage(eDirection.CENTER, oEntity)));

		// AW 20111122: Add Deposit at the STONE only, if the stone is besides of the agent
		// DEPOSIT STONE
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_FORWARD), new clsImage(eDistance.FAR, eDirection.CENTER,
		    eEntity.STONE), new clsImage(eDistance.MEDIUM, eDirection.CENTER, eEntity.STONE)));

		moAvailablePlanFragments
		    .add(new clsPlanFragment(new clsAct(eAction.DEPOSIT), new clsImage(eDistance.MEDIUM, eDirection.CENTER, eEntity.STONE), new clsImage(
		        eDistance.MEDIUM, eDirection.CENTER, eEntity.STONE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.DEPOSIT), new clsImage(eDistance.NEAR, eDirection.MIDDLE_LEFT,
		    eEntity.STONE), new clsImage(eDistance.NEAR, eDirection.MIDDLE_LEFT, eEntity.STONE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_LEFT), new clsImage(eDirection.LEFT, eEntity.STONE), new clsImage(
		    eDirection.MIDDLE_LEFT, eEntity.STONE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_LEFT), new clsImage(eDirection.MIDDLE_LEFT, eEntity.STONE),
		    new clsImage(eDirection.CENTER, eEntity.STONE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT), new clsImage(eDirection.RIGHT, eEntity.STONE), new clsImage(
		    eDirection.MIDDLE_RIGHT, eEntity.STONE)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT), new clsImage(eDirection.MIDDLE_RIGHT, eEntity.STONE),
		    new clsImage(eDirection.CENTER, eEntity.STONE)));

		// SLEEP
		oEntity = eEntity.EMPTYSPACE;
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SLEEP), new clsImage(eDistance.FAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SLEEP), new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SLEEP), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SLEEP), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SLEEP), new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.FAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SLEEP), new clsImage(eDirection.LEFT, oEntity), new clsImage(
		    eDirection.MIDDLE_LEFT, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SLEEP), new clsImage(eDirection.MIDDLE_LEFT, oEntity), new clsImage(
		    eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SLEEP), new clsImage(eDirection.RIGHT, oEntity), new clsImage(
		    eDirection.MIDDLE_RIGHT, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SLEEP), new clsImage(eDirection.MIDDLE_RIGHT, oEntity), new clsImage(
		    eDirection.CENTER, oEntity)));

		// RELAX
		oEntity = eEntity.EMPTYSPACE;
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.RELAX), new clsImage(eDistance.FAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.RELAX), new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.RELAX), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.RELAX), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.RELAX), new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.FAR, eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.RELAX), new clsImage(eDirection.LEFT, oEntity), new clsImage(
		    eDirection.MIDDLE_LEFT, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.RELAX), new clsImage(eDirection.MIDDLE_LEFT, oEntity), new clsImage(
		    eDirection.CENTER, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.RELAX), new clsImage(eDirection.RIGHT, oEntity), new clsImage(
		    eDirection.MIDDLE_RIGHT, oEntity)));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT), new clsImage(eDirection.MIDDLE_RIGHT, oEntity),
		    new clsImage(eDirection.CENTER, oEntity)));

		// UNREAL GET HEALTHPACK
		oEntity = eEntity.HEALTH;
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.UNREAL_MOVE_TO), new clsImage(eDirection.CENTER, oEntity),
		    new clsImage(eDirection.CENTER, oEntity)));
		
		// moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SLEEP"),
		// new clsImage(eDistance.NEAR, eDirection.CENTER, eEntity.EMPTYSPACE),
		// new clsImage(eDistance.NEAR, eDirection.CENTER, eEntity.EMPTYSPACE)));

		// moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.RELAX"),
		// new clsImage(eDistance.NEAR, eDirection.CENTER, eEntity.EMPTYSPACE),
		// new clsImage(eDistance.NEAR, eDirection.CENTER, eEntity.EMPTYSPACE)));

		/*
		 * moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SEARCH1"), new clsImage(eEntity.NONE), new clsImage(eDirection.CENTER,
		 * eEntity.CAKE)));
		 * 
		 * moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SEARCH2"), new clsImage(eEntity.NONE), new clsImage(eDirection.RIGHT,
		 * eEntity.CAKE)));
		 * 
		 * moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.SEARCH3"), new clsImage(eEntity.NONE), new clsImage(eDirection.LEFT,
		 * eEntity.CAKE)));
		 */

		// // AP: Who added this action? -> Entity none is not defined for searches!
		// moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.DEPOSIT"),
		// new clsImage(),
		// new clsImage()));

		// eEntity oEntityOld=oEntity;

		// oEntity=oEntityOld;

		// moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.FLEE"),
		// new clsImage(),
		// new clsImage()));

		// TODO (perner) add generic actions like right, left without objects

		return moAvailablePlanFragments;

	}

	/**
	 * test-data AW
	 * 
	 * @return
	 * 
	 * @since 19.07.2011 10:24:29
	 * 
	 */
	static public ArrayList<clsPlanFragment> generateTestPlans_AP() {

		ArrayList<clsPlanFragment> moAvailablePlanFragments = new ArrayList<clsPlanFragment>();

		/**
		 * test test dummy to fill internal database
		 */

		// EAT CAKE
		eEntity oEntity = eEntity.CAKE;
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.EAT), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity), "EAT"));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_FORWARD), new clsImage(eDistance.FAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity), "MOVE_FW_FAR_TO_MEDIUM"));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_FORWARD),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    "MOVE_FW_MEDIUM_TO_NEAR"));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_BACKWARD), new clsImage(eDistance.NEAR, eDirection.CENTER, oEntity),
		    new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity), "MOVE_BW_NEAR_TO_MEDIUM"));

		// moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.MOVE_BACKWARD"),
		// new clsImage(eDistance.MEDIUM, eDirection.CENTER, oEntity), new clsImage(eDistance.FAR, eDirection.CENTER, oEntity),
		// "MOVE_BW_MEDIUM_TO_FAR"));

		// moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_LEFT"), new clsImage(eDirection.LEFT, oEntity), new clsImage(
		// eDirection.MIDDLE_LEFT, oEntity), "TURN_L_LEFT_TO_MDL_LEFT"));
		//
		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_LEFT), new clsImage(eDirection.MIDDLE_LEFT, oEntity), new clsImage(
		    eDirection.CENTER, oEntity), "TURN_L_MDL_LEFT_TO_CENTER"));

		// moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT"), new clsImage(eDirection.RIGHT, oEntity), new clsImage(
		// eDirection.MIDDLE_RIGHT, oEntity), "TURN_R_RIGHT_TO_MDL_RIGHT"));

		moAvailablePlanFragments.add(new clsPlanFragment(new clsAct(eAction.TURN_RIGHT), new clsImage(eDirection.MIDDLE_RIGHT, oEntity),
		    new clsImage(eDirection.CENTER, oEntity), "TURN_R_MDL_R_TO_CENTER"));

		return moAvailablePlanFragments;
	}

}
