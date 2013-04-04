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

import bw.entities.clsMobile;
import bw.exceptions.exInventoryFull;
import bw.factories.clsSingletonMasonGetter;
import bw.factories.clsRegisterEntity;
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
	}
	
	/*
	 * Changes the pinjoint of the carried object so it will be dragged to a given relative position
	 */
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
	
	public String toText () {
		String oInventory = "";
		
		oInventory = "MaxItems: " + moInventory.size() + " von " + getMaxItems() + 
				"\nMaxMass: " + getMass () + " von " + getMaxMass() + "\n";
		if (moCarriedEntity != null) oInventory += "aktuelles Objekt: " + moCarriedEntity.getId() + "Gewicht: " + moCarriedEntity.getTotalWeight() + "\n";
		
		for (clsMobile oItem : moInventory) {
			oInventory += oItem.getId() + " Gewicht: " + oItem.getTotalWeight() + "\n";		
		}
						
		return oInventory;
	}

}
