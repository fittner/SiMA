/**
 * Event.java: DecisionUnitInterface - statictools.eventlogger
 * 
 * @author deutsch
 * 22.04.2011, 21:34:17
 */
package statictools.eventlogger;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 22.04.2011, 21:34:17
 * 
 */
public class Event {
	@SuppressWarnings("rawtypes")
	public final Class caller;
	public final String uid;
	public final eEvent event;
	public final String content;
	private final String del = ";";
	public long step = -1;
	
	public Event(Object caller, String uid, eEvent event, String content) {
		this.caller = caller.getClass();
		this.uid = uid;
		this.event = event;
		this.content = content;
	}
	
	public Event(@SuppressWarnings("rawtypes") Class caller, String uid, eEvent event, String content) {
		this.caller = caller;
		this.uid = uid;
		this.event = event;
		this.content = content;
	}
	
	@Override
	public String toString()  {
		String o = "";
		
		o += step+del+caller.getSimpleName()+del+uid+del+event+del+content;
		
		return o;
	}
	
	public String toHtml() {
		String html = "";
		
		html = caller.getSimpleName()+del+uid+del+event+del+content+"<br/>";
		
		return html;
	}
}
