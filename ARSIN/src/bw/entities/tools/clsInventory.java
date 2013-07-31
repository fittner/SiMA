/**
 * @author Benny D�nz
 * 03.07.2009, 20:01:49
 * 
 * $Rev::                      $: Revision of last commit
 * $Author::                   $: Author of last commit
 * $Date::                     $: Date of last commit
 */
package bw.entities.tools;

import java.util.ArrayList;
import java.util.Iterator;

import ARSsim.physics2D.shape.clsCircleImage;
import bw.body.io.actuators.actionProxies.itfAPCarryable;
import bw.entities.base.clsMobile;
import bw.exceptions.exInventoryFull;
import bw.factories.clsSingletonMasonGetter;
import bw.factories.clsRegisterEntity;
import bw.utils.enums.eBindingState;

import sim.physics2D.constraint.PinJoint;

/**
 * Class for managing an entities inventory. 
 * Items are added/removed via the ActionCommands PickUp, Drop, ToInventory, FromInventory
 *     
 * @author Benny D�nz
 * 03.07.2009, 19:25:16
 * 
 */
public class clsInventory {

	private int mnMaxItems;
	private double mrMaxMass;

	private ArrayList<clsMobile> moInventory=new ArrayList<clsMobile>();
	private clsMobile moEntity;
	private clsMobile moCarriedEntity=null;
	private PinJoint moJoint; 
	
	/*
	 * pnMaxItems = Max. Number of items which can be kept in the inventory 
	 *             (0=No inventory but 1 item can be carried, <0: No inventory and nothing can be carried)
	 * pnMaxMass = Max. Number/Weight of Items which can be kept in the inventory
	 */
	public clsInventory(clsMobile poEntity,int pnMaxItems, double prMaxMass) {
		moEntity=poEntity;
		mnMaxItems = pnMaxItems;
		mrMaxMass=prMaxMass;
	}

	/*
	 * Current/Max items
	 */
	public int getMaxItems() {
		return mnMaxItems;
	}
	public int getItemCount() {
		return moInventory.size();	
	}
	
	/*
	 * Current/Max mass
	 */
	public double getMaxMass() {
		return mrMaxMass;		
	}
	public double getMass() {
		double nTotalMass=0;
		Iterator<clsMobile> oIt = moInventory.iterator();
		while (oIt.hasNext()) {
			clsMobile oEntity = oIt.next();
			nTotalMass=nTotalMass+oEntity.getTotalWeight();
		}
		return nTotalMass;
	}

	/*
	 * Carried object. Setting a carried object which is currently in the inventory automatically removes it from the inventory
	 * If an object is already being carried then it will be dropped prior to carrying the new object
	 * Carried objects are joint to the entity by a pinjoint 
	 */
	public clsMobile getCarriedEntity() {
		return moCarriedEntity;
	}

		public void setCarriedEntity(clsMobile poEntity) {		
		//Can I carry anything?
		if (getMaxItems()<0) return;
		
		//drop anything we are currently carrying
		if (moCarriedEntity!=null ) {
			dropCarriedItem();
		}
		//If the item is from the inventory register in mason and remove from inventory
		if (moInventory.contains(poEntity)) {
			moInventory.remove(poEntity);
			moCarriedEntity = poEntity;
			moEntity.setCarriedItem(((clsCircleImage)moCarriedEntity.getMobileObject2D().getShape()).getImage());
		}
		//Pick up from environment
		else{
			moCarriedEntity = poEntity;
			((clsMobile)moCarriedEntity).incHolders();

			moEntity.setCarriedItem(((clsCircleImage)moCarriedEntity.getMobileObject2D().getShape()).getImage());
			clsRegisterEntity.unRegisterPhysicalObject2D(moCarriedEntity.getMobileObject2D());
			moCarriedEntity.setRegistered(false);
			((itfAPCarryable)moCarriedEntity).setCarriedBindingState(eBindingState.CARRIED);

		}


		/*
		//drop anything we are currently carrying
		if (moCarriedEntity!=null ) {
			clsSingletonMasonGetter.getPhysicsEngine2D().unRegister(moJoint);
			moCarriedEntity=null;
			moJoint=null;
		}
		if (poEntity==null) return;
		
		//If the item is from the inventory register in mason and remove from inventory
		if (moInventory.contains(poEntity)) {
			moInventory.remove(poEntity);
			clsRegisterEntity.registerPhysicalObject2D(poEntity.getMobileObject2D());
			poEntity.setRegistered(true);
		}

		//set carried item and pinjoint
		moCarriedEntity = poEntity;
		moJoint= new PinJoint(poEntity.getPosition(), (moEntity).getMobileObject2D(), poEntity.getMobileObject2D());
        clsSingletonMasonGetter.getPhysicsEngine2D().register(moJoint);
        
        */
	}
		
	public void dropCarriedItem(){
	//	clsCake x = (clsCake)moCarriedEntity;
	//	clsEntity neu = x.dublicate(x.moCreationProperties, 50, 0.5);
		
		
		moCarriedEntity.registerEntity();
		//clsRegisterEntity.registerMobileObject2D(moCarriedEntity.getMobileObject2D());
		moCarriedEntity.setRegistered(true);
		moCarriedEntity.decHolders();
		((itfAPCarryable)moCarriedEntity).setCarriedBindingState(eBindingState.NONE);
		moCarriedEntity=null;
		moEntity.setCarriedItem(null);
		
	}
	
	/*
	 * Changes the pinjoint of the carried object so it will be dragged to a given relative position
	 */
	// old inventory function
	
	public void moveCarriedEntity(sim.physics2D.util.Double2D poDestination) {
		if (moJoint == null) return;

		clsSingletonMasonGetter.getPhysicsEngine2D().unRegister(moJoint);
		moJoint = new PinJoint(poDestination ,  (moEntity).getMobileObject2D(),moCarriedEntity.getMobileObject2D());
        clsSingletonMasonGetter.getPhysicsEngine2D().register(moJoint);
		
	}

	/*
	 * Inventory items
	 * Items are removed from the simulation and stored in the inventory
	 */
	public clsMobile getInventoryItem(int pnIndex) {
		return moInventory.get(pnIndex);
	}
	public clsMobile moveCarriedToInventory() throws exInventoryFull {
		//Only carried objects can be moved to the inventory
		if (moCarriedEntity==null) return null;

		//Is there still space?
		if (getItemCount()>=getMaxItems()) throw (new exInventoryFull() );
		if ((getMass()+moCarriedEntity.getTotalWeight())>=getMaxMass()) throw (new exInventoryFull() );

		//move to inventory and remove from mason
		clsMobile oEntity=moCarriedEntity;
		moInventory.add(oEntity);
		setCarriedEntity(null);
		clsRegisterEntity.unRegisterPhysicalObject2D(oEntity.getMobileObject2D());
		oEntity.setRegistered(false);
		
		return oEntity;
	}

}
