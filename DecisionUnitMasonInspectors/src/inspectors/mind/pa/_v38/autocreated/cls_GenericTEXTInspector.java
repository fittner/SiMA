/**
 * clsE_GenericInspector.java: DecisionUnitMasonInspectors - inspectors.mind.pa._v38
 * 
 * @author deutsch
 * 14.04.2011, 14:57:39
 */
package inspectors.mind.pa._v38.autocreated;

import java.awt.BorderLayout;

import pa._v38.tools.toText;
import panels.TextOutputPanel;

import sim.portrayal.Inspector;
import statictools.clsExceptionUtils;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 14.04.2011, 14:57:39
 * 
 */
public abstract class cls_GenericTEXTInspector extends Inspector {
	/**
	 * DOCUMENT (deutsch) - insert description 
	 * 
	 * @author deutsch
	 * 14.04.2011, 15:02:15
	 */
	private static final long serialVersionUID = -2033800775072753378L;
	protected Object moObject;
	protected String moTitle;
	protected String moContent;
	TextOutputPanel moTEXTPane;
	
    public cls_GenericTEXTInspector(Object poObject)
    {
		moObject = poObject;
    	
    	setTitle();
    	updateContent();
    	
        setLayout(new BorderLayout());
    	moTEXTPane = new TextOutputPanel(getTEXT());
    	
    	try {
			add(moTEXTPane, BorderLayout.CENTER);
		} catch (java.lang.Exception e) {
			System.out.println(clsExceptionUtils.getCustomStackTrace(e));
		}
    }	
    
    protected abstract void setTitle();
    protected abstract void updateContent();
    
    private String getTEXT() {
    	String text;
    	
    	text  = "";
    	text += toText.h1(moTitle);	
    	text += toText.p(moContent);
    	
    	return text;
    }
    
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 13.08.2009, 01:46:51
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
		if (isVisible()) {
			updateContent();
			
			try {
				moTEXTPane.setText(getTEXT());
			} catch (java.lang.Exception e) {
				System.out.println(clsExceptionUtils.getCustomStackTrace(e));
			}
		}
	}    
}
