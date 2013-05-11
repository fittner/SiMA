package du.itf.sensors;

public abstract class clsDataBase implements Cloneable {
	abstract public String logXML();
	abstract public String logHTML();
	
	@Override
	abstract public String toString();
		
	protected String getClassName() {
		String name = this.getClass().getName();
		
		return stripClassPrefix( stripNamespace(name) );
	}
	
	static protected String stripNamespace(String name) {
		while (name.indexOf(".") > 0) {
			name = name.substring(name.indexOf(".")+1);
		}
		
		return name;
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

	@Override
	public Object clone() throws CloneNotSupportedException {
        try {
        	clsDataBase oClone = (clsDataBase)super.clone();

        	return oClone;
        } catch (CloneNotSupportedException e) {
           return e;
        }
	}	
}
