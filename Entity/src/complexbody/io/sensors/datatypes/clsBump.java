package complexbody.io.sensors.datatypes;

import java.util.ArrayList;

public class clsBump extends clsSensorExtern implements Cloneable {
	protected boolean mnBumped = false;
		
	public clsBump() {
	}
	
	public clsBump(boolean nBumped) {
		mnBumped = nBumped;
	}

	public boolean getBumped() {
		return mnBumped;
	}
	public void setBumped(boolean pnBumped) {
		mnBumped =pnBumped;
	}
	
	@Override
	public String logXML() {
		return addXMLTag(new Boolean(mnBumped).toString());
	}

	@Override
	public String toString() {
		return getClassName()+": "+new Boolean(mnBumped).toString();
	}

	@Override
	public String logHTML() {
		
		String oRetVal = "";
		
		if(mnBumped) {
			oRetVal = "<tr><td>"+getClassName()+"</td><td color='FF0000'>"+new Boolean(mnBumped).toString()+"</td></tr>";
		}
		else {
			oRetVal = "<tr><td>"+getClassName()+"</td><td>"+new Boolean(mnBumped).toString()+"</td></tr>";
		}
			
		
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @author zeilinger
	 * 30.09.2009, 13:41:13
	 * 
	 * @see decisionunit.itf.sensors.clsDataBase#getDataObjects()
	 */
	@Override
	public ArrayList<clsSensorExtern> getDataObjects() {
		ArrayList<clsSensorExtern> oRetVal = new ArrayList<clsSensorExtern>();
		oRetVal.add(this);
		return oRetVal;
	}

	/* (non-Javadoc)
	 *
	 * @author langr
	 * 01.10.2009, 14:47:13
	 * 
	 * @see decisionunit.itf.sensors.clsSensorExtern#setDataObjects(java.util.ArrayList)
	 */
	@Override
	public boolean setDataObjects(ArrayList<clsSensorExtern> poSymbolData) {
		return false;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsBump oClone = (clsBump)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
