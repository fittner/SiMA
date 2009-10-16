/**
 * clsThingPresentationSingle.java: DecisionUnits - pa.datatypes
 * 
 * @author zeilinger
 * 15.09.2009, 09:09:29
 */
package pa.datatypes;

import java.lang.reflect.Method;

/**
 * DOCUMENT (zeilinger) - insert description 
 * 
 * @author zeilinger
 * 15.09.2009, 09:09:29
 * 
 */
public class clsThingPresentationSingle extends clsThingPresentation implements Cloneable {

	public String meContentName;
	public String meContentType = "";
	public Object moContent = null;
	
	public clsThingPresentationSingle() {
		
	}
	
	public clsThingPresentationSingle(String poName, String poType, Object poContenObject) {
		meContentName = poName;
		meContentType = poType;
		moContent = poContenObject;		
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsThingPresentationSingle oClone = (clsThingPresentationSingle)super.clone();
        	
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
        	
        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
	
	@Override
	public String toString() {
		String oResult = super.toString();
		
		oResult += " name:"+moContent+" type:"+meContentType+" content:"+moContent;
		
		oResult = "::TPS::"+oResult.substring(6);
		
		return oResult;
	}	
	
	@Override
	public String toStringGraphDisplay() {
		return moContent.toString();
	}

}
