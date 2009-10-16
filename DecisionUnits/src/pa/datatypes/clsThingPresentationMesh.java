/**
 * clsThingPresentationMesh.java: DecisionUnits - pa.datatypes
 * 
 * @author zeilinger
 * 15.09.2009, 09:10:22
 */
package pa.datatypes;

import java.lang.reflect.Method;
import java.util.ArrayList;


/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 15.09.2009, 09:10:22
 * 
 */
public class clsThingPresentationMesh extends clsThingPresentation implements Cloneable {
	public String meContentName;
	public String meContentType = "";
	public Object moContent = null;
	
	public ArrayList<clsAssociationContext<clsThingPresentation>> moAssociations;
	
	public clsThingPresentationMesh() {
		moAssociations = new ArrayList<clsAssociationContext<clsThingPresentation>>();
	}
	
	@SuppressWarnings("unchecked")
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsThingPresentationMesh oClone = (clsThingPresentationMesh)super.clone();
        	
        	if (moContent != null) {
				try { 
					Class<?> clzz = this.moContent.getClass();
					Method   meth = clzz.getMethod("clone", new Class[0]);
					Object   dupl = meth.invoke(this.moContent, new Object[0]);
					oClone.moContent =  dupl; // unchecked warning
				} catch (Exception e) {
				   //...
				}
        	}
        	
    		oClone.moAssociations = new ArrayList<clsAssociationContext<clsThingPresentation>>();   	
    		for (clsAssociationContext<clsThingPresentation> oValue:moAssociations) {
    			oClone.moAssociations.add( (clsAssociationContext<clsThingPresentation>)oValue.clone() );
    		}
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}
	
	@Override
	public String toString() {
		String oResult = super.toString();
		
		oResult += " name:"+moContent+" type:"+meContentType+" content:"+moContent+" assoc:";
		
		for (clsAssociationContext<clsThingPresentation> entry:moAssociations) {
			oResult += " ("+entry+") / ";
		}
		
		if (moAssociations.size() > 0) {
			oResult = oResult.substring(0, oResult.length()-3);
		}
		
		oResult = "::TPM::"+oResult.substring(6);
		
		return oResult;
	}
	
	@Override
	public String toStringGraphDisplay() {
		return moContent.toString();
	}

}
