/**
 * clsActionLogger.java: DecisionUnits - pa._v30.datalogger
 * 
 * @author deutsch
 * 24.04.2011, 01:18:19
 */
package pa._v30.datalogger;

import java.util.ArrayList;

import pa._v30.tools.clsPair;
import pa._v30.tools.clsTripple;
import statictools.clsSimState;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 24.04.2011, 01:18:19
 * 
 */
public class clsActionLogger {
	public ArrayList<clsPair<Long, String>> actions;
	
	public static final long maxentries = 0; // all history sizes of moDataStorage entries drop old entries if their history exceeds this value. if maxentries=0, size of history is set to infinity.
	public static final String newline = System.getProperty("line.separator");
	
	private long first;
	private long last;
	
	public clsActionLogger() {
		actions = new ArrayList<clsPair<Long,String>>();
		first = Integer.MAX_VALUE;
		last = -1;
	}
	
	public void add(String poAction) {
		long act = clsSimState.getSteps();
		if (act < first) {first = act;}
		if (act > last) {last = act;}
		actions.add(new clsPair<Long, String>(act, poAction));
	}
	
	@SuppressWarnings("unused")
	private void enforceMaxLength() {
		if (actions.size() > maxentries) {
			while (actions.size() > maxentries) {
				actions.remove(0);
			}
			first = actions.get(0).a;
		}
	}
	
	public ArrayList<clsTripple<Long,Long,String>> compress() {
		ArrayList<clsTripple<Long,Long,String>> oResult = new ArrayList<clsTripple<Long,Long,String>>();
		
		long prev = -1;
		clsTripple<Long, Long, String> oEntry = null;
		
		for (int i=0; i<actions.size(); i++) {
			if (oEntry == null) {
				oEntry = new clsTripple<Long, Long, String>(actions.get(i).a, (long) -1, actions.get(i).b);
			}
			
			if (!oEntry.c.equals(actions.get(i).b)) {
				oEntry.b = prev;
				oResult.add( oEntry );
				oEntry = new clsTripple<Long, Long, String>(actions.get(i).a, (long) -1, actions.get(i).b);
			}
			
			prev = actions.get(i).a;
		}
		
		oEntry.b = prev;
		oResult.add( oEntry );
		
		return oResult;
	}
	
	public String toText() {
		String o = "";
		
		o += "** Action Logger ("+first+" to "+last+") **"+newline+newline;
		
		ArrayList<clsTripple<Long,Long,String>> oLines = compress();
		
		for (int i=oLines.size()-1;i>=0;i--) {
			clsTripple<Long,Long,String> oL = oLines.get(i);
			
			o += " - ["+oL.a+":";
			if (i==oLines.size()-1) {
				o+="__";
			} else {
				o+=""+oL.b;
			}
			o += "]: "+oL.c+newline;
		}
		
		return o;
	}
	
	
}
