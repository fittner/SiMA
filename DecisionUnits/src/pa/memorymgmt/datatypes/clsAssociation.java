/**
 * clsAssociation.java: DecisionUnits - pa.informationrepresentation.datatypes
 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 */
package pa.memorymgmt.datatypes;

import java.lang.reflect.Method;

import pa.memorymgmt.enums.eDataType;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 23.05.2010, 21:44:04
 * 
 */
public abstract class clsAssociation extends clsDataStructurePA{
	private static final long mrMaxStackDepth = 5000;
	
	public double mrImperativeFactor; 
	public double mrWeight; 
	public clsDataStructurePA moAssociationElementA;
	public clsDataStructurePA moAssociationElementB;

	/**
	 * DOCUMENT (zeilinger) - insert description 
	 * 
	 * @author zeilinger
	 * 24.05.2010, 15:50:41
	 *
	 */
	public clsAssociation(String poAssociationID, eDataType poAssociationType, clsDataStructurePA poAssociationElementA, clsDataStructurePA poAssociationElementB) {
		super(poAssociationID, poAssociationType);
		mrImperativeFactor = 1.0; 
		mrWeight = 1.0; 
		moAssociationElementA = poAssociationElementA; 
		moAssociationElementB = poAssociationElementB; 
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
	/**
	 * special clone method. prevents infinite loops while cloning object A which has association X with object B.
	 * A.clone() calls X.clone() which - consecutively - creates a new clone from A ... Thus, references to A and its clone A' 
	 * are passed to the clone method from X. If X.moA refers to A, it is redirected to A'; if X.moB refers to B, it is 
	 * redirected to A'.
	 *
	 * @author deutsch
	 * 19.10.2009, 16:17:12
	 * 20.07.2010, 08:20:00	adapted by zeilinger for the use in pa.memorymgmt.datatypes	 
	 *
	 * @param obj_orig
	 * @param obj_clon
	 * @return oClone
	 * @throws CloneNotSupportedException
	 */
	public Object clone(Object obj_orig, Object obj_clon) throws CloneNotSupportedException {
		checkStackDepth();
		
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
	
	private void checkStackDepth() {
		long depth = Thread.currentThread().getStackTrace().length;
		
		if (depth > mrMaxStackDepth) {
			throw new StackOverflowError("max. call stack for clsAssociation<TYPE>.clone reached. either increase threshold constant or check code for an infinite clone loop. ("+depth+">"+mrMaxStackDepth+")");
		}
	}
	
	@Override
	public String toString(){
		String oResult = "::"+this.oDataStructureType+"::";  
		if(this.oDataStructureID != null){oResult += this.oDataStructureID + ":";}
		
		if(moAssociationElementA!=null){
			oResult += "elementA:";
			oResult += moAssociationElementA.oDataStructureType.toString() + ":";
			if(moAssociationElementA.oDataStructureID != null){oResult += moAssociationElementA.oDataStructureID;}
		}
		oResult += ":"; 
		if(moAssociationElementB!=null){
			oResult += "elementB:";
			oResult += moAssociationElementB.oDataStructureType.toString() + ":";
			if(moAssociationElementB.oDataStructureID != null){oResult += moAssociationElementB.oDataStructureID;} 
		}
		return oResult; 
	}
}
