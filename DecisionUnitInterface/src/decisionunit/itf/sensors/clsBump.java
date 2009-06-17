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
}
