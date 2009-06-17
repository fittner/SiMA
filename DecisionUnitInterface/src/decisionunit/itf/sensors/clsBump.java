package decisionunit.itf.sensors;

public class clsBump extends clsSensorExtern {
	public boolean mnBumped = false;

	@Override
	public String logXML() {
		return addXMLTag(mnBumped+"");
	}

	@Override
	public String toString() {
		return getClassName()+": "+new Boolean(mnBumped).toString();
	}
}
