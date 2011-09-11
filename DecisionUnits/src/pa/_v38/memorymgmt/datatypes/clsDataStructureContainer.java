/**
 * clsDataStructureContainer.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 24.05.2010, 10:38:31
 */
package pa._v38.memorymgmt.datatypes;

import java.util.ArrayList;

/**
 * DOCUMENT (zeilinger) -
 * 
 * An object of type clsDataStructureContainer is a container which holds a psychoanalytically inspired data 
 * structure (moDataStructure) and data structures with which they are associated (moAssociatedDataStructures). 
 * For example: A TPM representing the entity “CAKE” is associated with a number of data structures. On the 
 * one hand, there are thing presentations that define shape, color, etc. of the entity. They are associations 
 * of the type “attribute association” and define the TPM itself. Hence, they define the content (moContent) of
 * the data structure TPM (see below). On the other hand, there exist data structures holding information 
 * which is associated with the entity “CAKE” but do not define it. E.g. a drive mesh which holds the information 
 * that the cake satisfies the drive NOURISH up to some certain level is associated with the TPM “CAKE” via a 
 * “drive mesh association”. However, while the different thing presentations are merged to a TPM, the combination 
 * of TPM and DM does not define a new type of data structure.
 * 
 * moDataStructure (clsDataStructurePA): Object of type clsDataStructurePA – root object for associated data structures
 * 
 * moAssociatedDataStructures (ArrayList): A List of associations which are all associated with moDataStructure
 * 
 * 
 * @author zeilinger
 * 24.05.2010, 10:38:31
 * 
 */
public abstract class clsDataStructureContainer implements Cloneable{
	protected clsDataStructurePA moDataStructure; 
	protected ArrayList<clsAssociation> moAssociatedDataStructures; 
	
	public clsDataStructureContainer(clsDataStructurePA poDataStructure, ArrayList<clsAssociation>poAssociationList){
		moDataStructure = poDataStructure; 
		moAssociatedDataStructures = new ArrayList<clsAssociation>(); 
		
		if(poAssociationList != null) {moAssociatedDataStructures = poAssociationList;}
	}

	
	public clsDataStructureContainer(String typ, String content) {
		
	}
	
	
	/**
	 * @author zeilinger
	 * 15.03.2011, 10:31:12
	 * 
	 * @return the moDataStructure
	 */
	public clsDataStructurePA getMoDataStructure() {
		return moDataStructure;
	}



	/**
	 * @author zeilinger
	 * 15.03.2011, 10:31:12
	 * 
	 * @param moDataStructure the moDataStructure to set
	 */
	public void setMoDataStructure(clsDataStructurePA poDataStructure) {
		this.moDataStructure = poDataStructure;
	}



	/**
	 * @author zeilinger
	 * 15.03.2011, 10:31:12
	 * 
	 * @return the moAssociatedDataStructures
	 */
	public ArrayList<clsAssociation> getMoAssociatedDataStructures() {
		return moAssociatedDataStructures;
	}



	/**
	 * @author zeilinger
	 * 15.03.2011, 10:31:12
	 * 
	 * @param moAssociatedDataStructures the moAssociatedDataStructures to set
	 */
	public void setMoAssociatedDataStructures(
			ArrayList<clsAssociation> poAssociatedDataStructures) {
		this.moAssociatedDataStructures = poAssociatedDataStructures;
	}
	
	public void addMoAssociatedDataStructure (clsAssociation poAssociatedDataStructure) {
		this.moAssociatedDataStructures.add(poAssociatedDataStructure);
	}

	/**
	 * This variant of getMoAssociatedDataStructures is only useful for containers with a "combined"
	 * DataStructure like a TemplateImage for example. Unlike the variant without an argument this
	 * version does not return all AssociatedDataStructures, but only the ones that are associated
	 * with the provided argument.If the DataStructure in the container is a single Item, like
	 * a ThingPresentationMesh, then the result will be the whole list of AssociatedDataStructures,
	 * just like the variant without an argument.<br>
	 * <br>
	 * <b>NOTE</b><br>
	 * Comparison between the argument and the RootElements of the AssociatedDataStructures is done
	 * on the basis of the moDS_ID. While this is sufficient for the problem that made this method
	 * necessary in the first place, this is in general <b>NOT</b> a suitable way of identifying
	 * individual objects! Therefore this method should either be made obsolete by replacing the use
	 * of containers with composite DataStructures by a better suited type or a new way of identifying
	 * individual DataStructures has to be introduced.  
	 *
	 * @author Zottl Marcus (e0226304),
	 * 28.06.2011, 18:47:52
	 *
	 * @param poAssociationRoot - an element of the DataStructure in the container for which you need
	 * the AssociatedDataStructures.
	 * @return an ArrayList of <i>clsAssociation</i>s that belong to the provided argument.
	 */
	public ArrayList<clsAssociation> getMoAssociatedDataStructures(clsDataStructurePA poAssociationRoot) {
		ArrayList<clsAssociation> oReturnList = new ArrayList<clsAssociation>();
		
		for (clsAssociation oEntry : moAssociatedDataStructures) {
			if ((oEntry.getRootElement().getMoDSInstance_ID() == poAssociationRoot.getMoDSInstance_ID()) && oEntry.getRootElement().moDSInstance_ID > 0) {
				oReturnList.add(oEntry);
			}
		}
		return oReturnList;
	}
	
	/**
	 * If the data structure can be found in the leaf or root of an association, it is taken, no matter if it is a part of the container
	 * (wendt)
	 *
	 * @since 15.08.2011 23:21:29
	 *
	 * @param poDS
	 * @return
	 */
	public ArrayList<clsAssociation> getAnyAssociatedDataStructures(clsDataStructurePA poDS) {
		ArrayList<clsAssociation> oReturnList = new ArrayList<clsAssociation>();
		
		for (clsAssociation oEntry : moAssociatedDataStructures) {
			if (((oEntry.getRootElement().getMoDSInstance_ID() == poDS.getMoDSInstance_ID()) || 
					(oEntry.getLeafElement().getMoDSInstance_ID() == poDS.getMoDSInstance_ID())) && 
					(poDS.getMoDSInstance_ID() > 0)) {
				oReturnList.add(oEntry);
			}
		}
		return oReturnList;
	}

	@Override
	public String toString(){
		String oRetVal = ""; 
		
		oRetVal += "DataStructureContainer:moDataStructure";
		oRetVal += moDataStructure.toString();
		for(clsAssociation oEntry : moAssociatedDataStructures){
			oRetVal += "\n	:AssociatedDataStructures:" + oEntry.toString(); 
		}
	
		return oRetVal;
	}
}
