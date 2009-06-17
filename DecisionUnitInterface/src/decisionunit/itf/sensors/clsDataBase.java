package decisionunit.itf.sensors;

public abstract class clsDataBase {
	abstract public String logXML();
	abstract public String toString();
		
	protected String getClassName() {
		String name = this.getClass().getName();
		
		while (name.indexOf(".") > 0) {
			name = name.substring(name.indexOf(".")+1);
		}
		
		return stripClassPrefix(name);
	}
	
	static protected String stripClassPrefix(String name) {
		if (name.startsWith("cls", 0)) {
			name = name.substring(3);
		}
		
		return name;		
	}
	
	static protected String stripPrefix(String name) {
		return name.substring(2);
	}
	
	protected String addXMLTag(String content) {
		return addXMLTag(getClassName(), content);
	}
	
	static protected String addXMLTag(String name, String content) {
		String tag = "";

		tag = "<"+name+">"+content+"</"+name+">";
		
		return tag;
	}

}
