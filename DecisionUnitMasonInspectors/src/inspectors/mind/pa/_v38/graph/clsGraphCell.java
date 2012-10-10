/**
 * CHANGELOG
 *
 * Sep 20, 2012 herret - File created
 *
 */
package inspectors.mind.pa._v38.graph;

import java.awt.Color;

import org.jgraph.graph.DefaultGraphCell;
import org.jgraph.graph.GraphConstants;

import com.jgraph.components.labels.RichTextBusinessObject;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * Sep 20, 2012, 3:05:13 PM
 * 
 */
public class clsGraphCell extends DefaultGraphCell {

	/** DOCUMENT (herret) - insert description; @since Sep 20, 2012 3:07:39 PM */
	private static final long serialVersionUID = -5520371659176605219L;

	private Object moReference;
	private Color moOldColor= null;
	
	public clsGraphCell(Object poReference){
		super();
		moReference = poReference;	
	}
	public clsGraphCell(RichTextBusinessObject poUserObject, Object poReference){
		super(poUserObject);
		moReference = poReference;	
	}
	public clsGraphCell(String poName, Object poReference){
		super(poName);
		moReference = poReference;	
	}
	
	public clsGraphCell(RichTextBusinessObject poUserObject){
		super(poUserObject);
	}
	
	public void highlight (Color poColor){
		//save old Color
		if(moOldColor==null){
			moOldColor =(Color) getAttributes().get("backgroundColor");
		}
		//set highlight Color
		GraphConstants.setBackground(getAttributes(), poColor);
	}
	
	public void restoreOldColor(){
		//restore the old color 
		if(moOldColor!=null){
			GraphConstants.setBackground(getAttributes(), moOldColor);
			moOldColor=null;
		}
	}
	
	/**
	 * @since Sep 20, 2012 3:07:13 PM
	 * 
	 * @return the moReference
	 */
	public Object getReference() {
		return moReference;
	}

	/**
	 * @since Sep 20, 2012 3:07:13 PM
	 * 
	 * @param moReference the moReference to set
	 */
	public void setReference(Object moReference) {
		this.moReference = moReference;
	}

}
