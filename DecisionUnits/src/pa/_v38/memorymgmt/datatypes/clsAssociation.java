/**
 * clsAssociation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 */
package pa._v38.memorymgmt.datatypes;

import java.lang.reflect.Method;

import pa._v38.tools.clsTriple;
import pa._v38.memorymgmt.enums.eDataType;
import pa._v38.memorymgmt.datatypes.clsDataStructurePA;

/**
 * DOCUMENT (zeilinger) - insert description 
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
	public clsAssociation(clsTriple<Integer, eDataType, String> poDataStructureIdentifier, clsDataStructurePA poAssociationElementA, clsDataStructurePA poAssociationElementB) {
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
	/**
	 * special clone method. prevents infinite loops while cloning object A which has association X with object B.
	 * A.clone() calls X.clone() which - consecutively - creates a new clone from A ... Thus, references to A and its clone A' 
	 * are passed to the clone method from X. If X.moA refers to A, it is redirected to A'; if X.moB refers to B, it is 
	 * redirected to A'.
	 *
	 * @author deutsch
	 * 19.10.2009, 16:17:12
	 * 20.07.2010, 08:20:00	adapted by zeilinger for the use in pa._v38.memorymgmt.datatypes	 
	 *
	 * @param obj_orig
	 * @param obj_clon
	 * @return oClone
	 * @throws CloneNotSupportedException
	 */
	public Object clone(Object obj_orig, Object obj_clon) throws CloneNotSupportedException {
		//FIXME HZ; checkDepth Method lowers performance 
		//checkStackDepth();
		
		clsAssociation oClone = null;
	    try { 
	    	oClone = (clsAssociation) super.clone(); 
	    } catch (CloneNotSupportedException e) { 
	    	throw e; 
	    }
	    try {
	    	if (this.moAssociationElementA.equals(obj_orig)) {
	    		oClone.moAssociationElementA = (clsDataStructurePA) obj_clon;
	    	} else {
	    		Class<?> clzz = this.moAssociationElementA.getClass();
	    		Method   meth = clzz.getMethod("clone", new Class[0]);
				Object   dupl = meth.invoke(this.moAssociationElementA, new Object[0]);
				oClone.moAssociationElementA = (clsDataStructurePA) dupl; // unchecked warning
	    	}
	    } catch (Exception e) {
	    	 //...
	    }
	    try {
	    	if (this.moAssociationElementB.equals(obj_orig)) {
	    		oClone.moAssociationElementB = (clsDataStructurePA) obj_clon;
	    	} else {	    	
				Class<?> clzz = this.moAssociationElementB.getClass();
				Method   meth = clzz.getMethod("clone", new Class[0]);
				Object   dupl = meth.invoke(this.moAssociationElementB, new Object[0]);
				oClone.moAssociationElementB = (clsDataStructurePA) dupl; // unchecked warning
	    	}
	    } catch (Exception e) {
	    	//...
	    } 
		
	    return oClone;
	}
	
	@Override
	public String toString(){
		String oResult = "::"+this.moDataStructureType+"::";  
		oResult += this.moDS_ID + ":" + this.moContentType + "|";
		
		if(moAssociationElementA!=null){
			oResult += "elementA:";
			oResult += moAssociationElementA.moDataStructureType.toString() + ":";
			oResult += moAssociationElementA.moDS_ID;
		}
		oResult += ":"; 
		if(moAssociationElementB!=null){
			oResult += "elementB:";
			oResult += moAssociationElementB.moDataStructureType.toString() + ":";
			oResult += moAssociationElementB.moDS_ID;
		}
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
