package decisionunit.itf.sensors;

public class clsBump extends clsSensorExtern {
	public boolean mnBumped = false;

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
}
