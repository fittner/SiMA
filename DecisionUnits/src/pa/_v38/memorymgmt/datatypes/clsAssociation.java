/**
 * clsAssociation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 */
package pa._v38.memorymgmt.datatypes;

import java.lang.reflect.Method;
import java.util.ArrayList;

import pa._v38.tools.clsPair;
import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eContentType;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (zeilinger) - clsAssociation represents the base class for the four association types – attribute association, temporal association, word presentation association, drive mesh association (see Figure 3). Every association contains two objects of type clsDataStructurePA (Element A and Element B). In addition it holds a weight of the association. 
 * There is only one weight defined for the association. Hence, “Element A” has the same level of dependency to “Element B” as the other way round. Keep in mind that the Protégé database defines a bi-directional connection; however the second weight is ignored during loading operation
 * The imperative factor indicates if the association is defined as class association or instance association. This differentiation simplifies the search for data structures as well as influences the matching level (level of correspondence between two data structures being compared). For v30, the complexity of the entities is low => hence, the differentiation between class associations and instance associations is crucial; otherwise a lot of data structures would have the same matching factor.  
 * Class association: It is differentiate between associations that define a specific object class (e.g. object “CAKE”) and those which are true for an instance of these objects (e.g. “red cake”). Class associations represent the first type of association. A higher weight is assigned to them by definition. 
 * Instance association: Defines an association that is not true for the whole object type but only for single instances. 
 * 
 * The imperative factor is set at the initialization of the data structure. This is done in package pa._v30.memorymgmt.informationrepresentation.searchspace in class clsOntology loader. There the method setImperativeFactor(int variable) is invoked. Hence, the imperative factor can also be changed during run time. The information about the belonging to the group of class or instance associations is defined within the knowledge base (Protégé). 
 * moAssociationElementA (clsDataStructurePA)
 * moAssociationElementB (clsDataStructurePA)	
 * mrWeight (double)	
 * mrImperativeFactor (double)	
 * poDataStructureIdentifier (clsTripple)	Holds the data structure Id, the data type, as well as the content type. It is passed on to the super class

 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 * 
 */
public abstract class clsAssociation extends clsDataStructurePA{
	//private static final long mrMaxStackDepth = 5000;
	
	protected double mrImperativeFactor; 
	protected double mrWeight; 
	protected clsDataStructurePA moAssociationElementA;
	protected clsDataStructurePA moAssociationElementB;

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @return the mrImperativeFactor
	 */
	public double getMrImperativeFactor() {
		return mrImperativeFactor;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @param mrImperativeFactor the mrImperativeFactor to set
	 */
	public void setMrImperativeFactor(double mrImperativeFactor) {
		this.mrImperativeFactor = mrImperativeFactor;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @return the mrWeight
	 */
	public double getMrWeight() {
		return mrWeight;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @param mrWeight the mrWeight to set
	 */
	public void setMrWeight(double mrWeight) {
		this.mrWeight = mrWeight;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @return the moAssociationElementA
	 */
	public clsDataStructurePA getMoAssociationElementA() {
		return moAssociationElementA;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @param moAssociationElementA the moAssociationElementA to set
	 */
	public void setMoAssociationElementA(clsDataStructurePA moAssociationElementA) {
		this.moAssociationElementA = moAssociationElementA;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @return the moAssociationElementB
	 */
	public clsDataStructurePA getMoAssociationElementB() {
		return moAssociationElementB;
	}

	/**
	 * @author zeilinger
	 * 17.03.2011, 00:48:52
	 * 
	 * @param moAssociationElementB the moAssociationElementB to set
	 */
	public void setMoAssociationElementB(clsDataStructurePA moAssociationElementB) {
		this.moAssociationElementB = moAssociationElementB;
	}

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 15:50:41
	 *
	 */
	public clsAssociation(clsTriple<Integer, eDataType, eContentType> poDataStructureIdentifier, clsDataStructurePA poAssociationElementA, clsDataStructurePA poAssociationElementB) {
		super(poDataStructureIdentifier);
		mrImperativeFactor = 1.0; 
		mrWeight = 1.0;	//TODO HZ: The weight shall be possible to set in the constructor 
		moAssociationElementA = poAssociationElementA; 
		moAssociationElementB = poAssociationElementB; 
	}
	
	//Abstract method that has to be implemented by every Association object - however
	//the recall of the leaf element is different for every Association Type
	//FIXME HZ 17.08.2010: Refactor this method as it is different for every 
	//					   Association type. 
	public abstract clsDataStructurePA getLeafElement();
	
	public abstract clsDataStructurePA getRootElement();
	
	public abstract void setLeafElement(clsDataStructurePA poDS);
	
	public abstract void setRootElement(clsDataStructurePA poDS);
		
	/**
	 * Put "this" into this function to get the other element of the association
	 * 
	 * (wendt)
	 *
	 * @since 26.03.2012 21:12:23
	 *
	 * @param poSubjectElement
	 * @return
	 */
	public clsDataStructurePA getTheOtherElement(clsDataStructurePA poSubjectElement) {
		clsDataStructurePA oRetVal = null;
		
		if (this.moAssociationElementA.equals(poSubjectElement)) {
			oRetVal=this.moAssociationElementB;
		} else if (this.moAssociationElementB.equals(poSubjectElement)) {
			oRetVal=this.moAssociationElementA;
		}
		
		return oRetVal;
	}
	
	/**
	 * Set the other element of the association
	 * 
	 * (wendt)
	 *
	 * @since 08.09.2012 11:43:13
	 *
	 * @param poSubjectElement
	 * @param poSetOtherElement
	 */
	public void setTheOtherElement(clsDataStructurePA poSubjectElement, clsDataStructurePA poSetOtherElement) {
		
		if (this.moAssociationElementA.equals(poSubjectElement)) {
			this.moAssociationElementB=poSetOtherElement;
		} else if (this.moAssociationElementB.equals(poSubjectElement)) {
			this.moAssociationElementA = poSetOtherElement;
		}
		
	}
	
	
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		try {
			clsAssociation oClone = (clsAssociation)super.clone();
           	try {
    	    		Class<?> clzz = this.moAssociationElementA.getClass();
    	    		Method   meth = clzz.getMethod("clone", new Class[0]);
    				Object   dupl = meth.invoke(this.moAssociationElementA, new Object[0]);
    				oClone.moAssociationElementA = (clsDataStructurePA) dupl;
       	    } catch (Exception e) {
    	    	 //...
    	    }
    	    try {
    	    		Class<?> clzz = this.moAssociationElementB.getClass();
    	    		Method   meth = clzz.getMethod("clone", new Class[0]);
    				Object   dupl = meth.invoke(this.moAssociationElementB, new Object[0]);
    				oClone.moAssociationElementB = (clsDataStructurePA) dupl; 
    	    } catch (Exception e) {
    	    	 //...
    	    }
         	        	
         	return oClone;
		 } catch (CloneNotSupportedException e) {
			 return e;
		 }
	}		

//	/**
//	 * special clone method. prevents infinite loops while cloning object A which has association X with object B.
//	 * A.clone() calls X.clone() which - consecutively - creates a new clone from A ... Thus, references to A and its clone A' 
//	 * are passed to the clone method from X. If X.moA refers to A, it is redirected to A'; if X.moB refers to B, it is 
//	 * redirected to A'.
//	 *
//	 * @author deutsch
//	 * 19.10.2009, 16:17:12
//	 * 20.07.2010, 08:20:00	adapted by zeilinger for the use in pa._v38.memorymgmt.datatypes	 
//	 *
//	 * @param obj_orig
//	 * @param obj_clon
//	 * @return oClone
//	 * @throws CloneNotSupportedException
//	 */
//	public Object clone(Object obj_orig, Object obj_clon) throws CloneNotSupportedException {
//		//FIXME HZ; checkDepth Method lowers performance 
//		//checkStackDepth();
//		
//		clsAssociation oClone = null;
//	    try { 
//	    	oClone = (clsAssociation) super.clone(); 
//	    } catch (CloneNotSupportedException e) { 
//	    	throw e; 
//	    }
//	    try {
//	    	if (this.moAssociationElementA.equals(obj_orig)) {
//	    		oClone.moAssociationElementA = (clsDataStructurePA) obj_clon;
//	    	} else {
//	    		Class<?> clzz = this.moAssociationElementA.getClass();
//	    		Method   meth = clzz.getMethod("clone", new Class[0]);
//				Object   dupl = meth.invoke(this.moAssociationElementA, new Object[0]);
//				oClone.moAssociationElementA = (clsDataStructurePA) dupl; // unchecked warning
//	    	}
//	    } catch (Exception e) {
//	    	 //...
//	    }
//	    try {
//	    	if (this.moAssociationElementB.equals(obj_orig)) {
//	    		oClone.moAssociationElementB = (clsDataStructurePA) obj_clon;
//	    	} else {	    	
//				Class<?> clzz = this.moAssociationElementB.getClass();
//				Method   meth = clzz.getMethod("clone", new Class[0]);
//				Object   dupl = meth.invoke(this.moAssociationElementB, new Object[0]);
//				oClone.moAssociationElementB = (clsDataStructurePA) dupl; // unchecked warning
//	    	}
//	    } catch (Exception e) {
//	    	//...
//	    } 
//		
//	    return oClone;
//	}
	

			
			
	public Object clone(Object poOriginalObject, Object poClonedObject, ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>> poClonedNodeList) throws CloneNotSupportedException {
	//public Object cloneGraph(Object poOriginalObject, Object poClonedObject, ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>> poClonedNodeList) throws CloneNotSupportedException {
		clsAssociation oClone = null;
		
		//Clone the association itself
	    try { 
	    	//Clone the clsDataStructurePA for this association
	    	oClone = (clsAssociation) super.clone(); 
	    } catch (CloneNotSupportedException e) { 
	    	throw e; 
	    } catch (Exception e) {
	    	
	    }
	    
	    //Clone elementA
	    try {
	      	if (this.moAssociationElementA.equals(poOriginalObject)) {
	    		//If the element A is the origin element, set the clone as association for A
	    		oClone.moAssociationElementA = (clsDataStructurePA) poClonedObject;
	    	} else {
	    		//Check if element B exists in the list
	    		boolean bElementFound = false;
	    		for (clsPair<clsDataStructurePA, clsDataStructurePA> oObjectPair : poClonedNodeList) {
	    			if (this.moAssociationElementA.equals(oObjectPair.a)==true) {
	    				oClone.moAssociationElementA = oObjectPair.b;
	    				bElementFound=true;
	    				break;
	    			}
	    		}
	    		
	    		//Check if structure was not found
	    		if (bElementFound==false) {
	    			//The element was found in the list, only add its clone then
	    			Class<?> clzz = this.moAssociationElementA.getClass();
	    			Class[] argTypes = {Class.forName("java.util.ArrayList")};
	    			Method cloneGraphExtended = clzz.getDeclaredMethod("clone", argTypes);
	    			Object newDuplicate = cloneGraphExtended.invoke(this.moAssociationElementA, poClonedNodeList);
					//Object   dupl = meth.invoke(this.moAssociationElementA, new Object[0]);
					oClone.moAssociationElementA = (clsDataStructurePA) newDuplicate; // unchecked warning
	    		}
	    	}
	    } catch (Exception e) {
	    	
	    }
	    
	    //Clone ElementB
	    try {
	      	if (this.moAssociationElementB.equals(poOriginalObject)) {
	    		//If the element A is the origin element, set the clone as association for A
	    		oClone.moAssociationElementB = (clsDataStructurePA) poClonedObject;
	    	} else {
	    		//Check if element B exists in the list
	    		boolean bElementFound = false;
	    		for (clsPair<clsDataStructurePA, clsDataStructurePA> oObjectPair : poClonedNodeList) {
	    			if (this.moAssociationElementB.equals(oObjectPair.a)==true) {
	    				oClone.moAssociationElementB = oObjectPair.b;
	    				bElementFound=true;
	    				break;
	    			}
	    		}
	    		
	    		//Check if structure was not found
	    		if (bElementFound==false) {
	    			//The element was found in the list, only add its clone then
	    			Class<?> clzz = this.moAssociationElementB.getClass();
	    			Class[] argTypes = {Class.forName("java.util.ArrayList")};
	    			Method cloneGraphExtended = clzz.getDeclaredMethod("clone", argTypes);
	    			Object newDuplicate = cloneGraphExtended.invoke(this.moAssociationElementB, poClonedNodeList);
		    		//Method   meth = clzz.getMethod("cloneGraph(ArrayList<clsPair<clsDataStructurePA, clsDataStructurePA>> poClonedNodeList)", argTypes);
					//Object   dupl = meth.invoke(this.moAssociationElementB, new Object[0]);
					oClone.moAssociationElementB = (clsDataStructurePA) newDuplicate; // unchecked warning
	    		}
	    	}
	    } catch (Exception e) {
	    	
	    }
		
		return oClone;
	}
	
	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";  
		oResult += this.moDS_ID + ":" + this.moContentType + "|";
		
		oResult += associationToString("elementA:", moAssociationElementA);
		oResult += ":"; 
		oResult += associationToString("elementB:", moAssociationElementB);
		
		return oResult; 
	}
	
	/* (non-Javadoc)
	 *
	 * @author gelbard
	 * 14.09.2011, 10:32:48
	 * 
	 * converts the moContentType and moContent of an association to a String
	 */ 
	protected String associationToString (String element, clsDataStructurePA moAssociationElement)
	{
		String oResult = "";
		if(moAssociationElement != null){
			oResult += element;
			//oResult += moAssociationElement.moDataStructureType.toString() + ":";
			oResult += moAssociationElement.moDS_ID + ":";
			oResult += moAssociationElement.moContentType;
			if (moAssociationElement instanceof clsThingPresentationMesh) {
				oResult += ":" + ((clsThingPresentationMesh)moAssociationElement).getMoContent().toString();
			} else if (moAssociationElement instanceof clsThingPresentation) {
				oResult += ":" + ((clsThingPresentation)moAssociationElement).getMoContent().toString();
			} else if (moAssociationElement instanceof clsSecondaryDataStructure) {
				oResult += ":" + ((clsSecondaryDataStructure)moAssociationElement).getMoContent().toString();
			}

//			// find moContent
//			if(moAssociationElement instanceof clsThingPresentationMesh){
//				// check if it is for example an ARSin
//				if (((clsThingPresentationMesh)moAssociationElement).getMoContent() != null)
//					oResult += ":" + ((clsThingPresentationMesh)moAssociationElement).getMoContent();
//				else
//					oResult += ":-null-";
//					
//			}
		}
		else
			oResult = ":-null-";
		return oResult;
	}
	

	/**
	 * DOCUMENT (wendt) - insert description
	 *
	 * @author wendt
	 * 28.05.2011, 10:28:09
	 *
	 * @return
	 */
}
