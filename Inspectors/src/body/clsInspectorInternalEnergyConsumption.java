/**
 * clsInspectorInternalEnergyConsumption.java: BW - bw.utils.inspectors.body
 * 
 * @author deutsch
 * 17.09.2009, 17:16:38
 */
package body;

import java.awt.BorderLayout;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import complexbody.internalSystems.clsInternalEnergyConsumption;
import datatypes.clsMutableDouble;
import entities.enums.eBodyParts;

import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.util.gui.HTMLBrowser;
import singeltons.clsSingletonMasonGetter;

/**
 * DOCUMENT (deutsch) - insert description 
 * 
 * @author deutsch
 * 17.09.2009, 17:16:38
 * FIXME (MUCHITSCH): exchange html pane with jtable (e.g. http://www.roseindia.net/java/example/java/swing/JTableComponent.shtml)
 */
public class clsInspectorInternalEnergyConsumption extends Inspector  {
	private NumberFormat formatter = new DecimalFormat("#0.0000");
	
	class data {
		public double 	max = 0;
		public double 	tmpmin = Double.MAX_VALUE;
		private double 	tmpavg = 0; //avg where value!=0
		public int   	count = 0; //inc when value!=0
		
		public data() {}
		
		public data(data other) {
			this.max = other.max;
			this.tmpmin = other.tmpmin;
			this.tmpavg = other.tmpavg;
			this.count = other.count;
		}
		
		public void add(double value) { // only positive numbers 
			if (value>0) {
				if (value>max) {
					max = value;
				}
				if (value<tmpmin) {
					tmpmin = value;
				}
				tmpavg += value;
				count++;
			}
		}
		
		public double avg() {
			if (count > 0) {
				return tmpavg / count;
			} else {
				return 0;
			}
		}
		
		public double min() {
			if (tmpmin>max) {return max;}
			else {return tmpmin;}
		}
		
		@Override
		public String toString() {
			return "c:"+count+"; min:"+formatter.format(min())+"; max:"+formatter.format(max);
		}
	}

	/**
	 * @author deutsch
	 * 17.09.2009, 10:00:18
	 */
	private static final long serialVersionUID = -4921447755303330168L;
	
	public Inspector moOriginalInspector;
	private Set<eBodyParts> moKeyOnce; //reference
	private ArrayList<eBodyParts> moPartList;
	private clsInternalEnergyConsumption moInternalEnergyConsumption; //reference
	
	private TreeMap<Long, HashMap<eBodyParts, clsMutableDouble>> moHistory;
	private TreeMap<Long, clsMutableDouble> moEnergyHistory;
	
	private HashMap<eBodyParts, data> moDataShort;
	private HashMap<eBodyParts, data> moDataMedium;
	private HashMap<eBodyParts, data> moDataLong;
	private HashMap<Integer, HashMap<eBodyParts, clsMutableDouble>> moDataDetailed;
	
	private data moEnergyShort;
	private data moEnergyMedium;
	private data moEnergyLong;
	private HashMap<Integer, clsMutableDouble> moEnergyDetailed;	
	
	private static final int mnDetailedPeriod = 7;
	private static final int mnShortPeriod = 35;
	private static final int mnMediumPeriod = 90;
	private static final int mnLongPeriod = 200;
	
	HTMLBrowser moHTMLPane;
	
	public clsInspectorInternalEnergyConsumption(Inspector originalInspector,
            LocationWrapper wrapper,
            GUIState guiState,
            clsInternalEnergyConsumption poIntEnergyConsumption)
	{
		moOriginalInspector = originalInspector;
		moKeyOnce = poIntEnergyConsumption.getListOnce().keySet();
		moInternalEnergyConsumption = poIntEnergyConsumption;
		moPartList = poIntEnergyConsumption.getPartList();
		
		moDataShort = new HashMap<eBodyParts, data>();
		moDataMedium = new HashMap<eBodyParts, data>();
		moDataLong = new HashMap<eBodyParts, data>();
		moHistory = new TreeMap<Long, HashMap<eBodyParts, clsMutableDouble>>();
		moDataDetailed = new HashMap<Integer, HashMap<eBodyParts, clsMutableDouble>>();
		
		
		moEnergyShort = new data();	
		moEnergyMedium = new data();
		moEnergyLong = new data();
		moEnergyHistory = new TreeMap<Long, clsMutableDouble>();
		moEnergyDetailed = new HashMap<Integer, clsMutableDouble>();
		
        String contentData = "<html><head></head><body><p>";
        contentData+=generateContent();
        contentData+="</p></body></html>";
        
        setLayout(new BorderLayout());
    	moHTMLPane = new HTMLBrowser(contentData);
		add(moHTMLPane, BorderLayout.CENTER);
	}
	
    private long getStep() {
    	return clsSingletonMasonGetter.getSimState().schedule.getSteps();
    }
    
    private void purgeOldEntries(long currentstep, long maxage) {
    	long minstep = currentstep -  maxage;
    	
    	while (moHistory.size()>0 && moHistory.firstKey().longValue() < minstep) {
    		moHistory.remove(moHistory.firstKey());
    	}

    	while (moEnergyHistory.size()>0 && moEnergyHistory.firstKey().longValue() < minstep) {
    		moEnergyHistory.remove(moEnergyHistory.firstKey());
    	}
    }
    
    private void addCurrentEntries() {
    	moHistory.put(getStep(), moInternalEnergyConsumption.getMergedList());
    	moEnergyHistory.put(getStep(), new clsMutableDouble(moInternalEnergyConsumption.getSum()));
    }
    
    private void updateData() {
    	long step = getStep();
    	addCurrentEntries();
    	purgeOldEntries(step, mnLongPeriod);
    	
		moEnergyShort = new data();	
		moEnergyMedium = new data();
		moEnergyLong = new data();
    	
    	moDataDetailed.clear();
    	moEnergyDetailed.clear();
		for (int i=0; i<mnDetailedPeriod; i++) {
			moDataDetailed.put(new Integer(i), new HashMap<eBodyParts, clsMutableDouble>());
			moEnergyDetailed.put(new Integer(i), new clsMutableDouble(0));
		}    	
    	
    	HashMap<eBodyParts, data> moDataTemp = new HashMap<eBodyParts, data>();
    	data moEnergyTemp = new data();
    	
    	for (eBodyParts e:moPartList) {
    		moDataTemp.put(e, new data()); //add empty entries for each bodypart
    		for (int i=0; i<mnDetailedPeriod; i++) {
    			HashMap<eBodyParts, clsMutableDouble> oTemp = moDataDetailed.get(i);
    			oTemp.put(e, new clsMutableDouble(0));
    		}
    	}
    	
    	Long oCurrentKey = step+1;
    	int phase = 0;
    	while ( (oCurrentKey = moHistory.lowerKey(oCurrentKey)) != null) {
    		//copy when phase shifts from short to medium and from medium to long.
    		if (phase == 0 && (step-oCurrentKey.longValue() >= mnDetailedPeriod)) {
    			phase++;
    		} else if (phase == 1 && (oCurrentKey.longValue() < step-mnShortPeriod)) {
    			phase ++;
    			moDataShort = moDataTemp;
    			moDataTemp = new HashMap<eBodyParts, data>();
    			for (eBodyParts e:moPartList) {
    	    		moDataTemp.put(e, new data()); //add empty entries for each bodypart
    			}
    			moEnergyShort = moEnergyTemp;
    			moEnergyTemp = new data();
    		} else if (phase == 2 && (oCurrentKey.longValue() < step-mnMediumPeriod)) {
    			phase ++;
    			moDataMedium = moDataTemp;
    			moDataTemp = new HashMap<eBodyParts, data>();
    			for (eBodyParts e:moPartList) {
    	    		moDataTemp.put(e, new data()); //add empty entries for each bodypart
    			}    			
    			moEnergyMedium = moEnergyTemp;
    			moEnergyTemp = new data();
    		} 

    		HashMap<eBodyParts, clsMutableDouble> oEntries = moHistory.get(oCurrentKey);
    		if (oEntries != null) {
    			if (phase == 0) {
    				//detailed data collection
    				int i = (int) (step-oCurrentKey.longValue());
    				if (i>(mnDetailedPeriod-1)) {
    					throw new java.lang.ArrayIndexOutOfBoundsException(i+">("+mnDetailedPeriod+"-1)");
    				}
        			for (Map.Entry<eBodyParts,clsMutableDouble> e:oEntries.entrySet()) {
        				HashMap<eBodyParts, clsMutableDouble> oTemp = moDataDetailed.get(i);
        				eBodyParts k = e.getKey();
        				clsMutableDouble v = e.getValue();
        				clsMutableDouble d = oTemp.get(k);
        				d.set(v);
        			} 		
    			} else {
	    			//culmulative data collection
	    			for (Map.Entry<eBodyParts,clsMutableDouble> e:oEntries.entrySet()) {
	    				moDataTemp.get(e.getKey()).add(e.getValue().doubleValue());
	    			} 		
    			}
    		}
    		
    		clsMutableDouble oData = moEnergyHistory.get(oCurrentKey);
    		if (oData != null) {
    			if (phase == 0) {
    				int i = (int) (step-oCurrentKey.longValue());
    				if (i>(mnDetailedPeriod-1)) {
    					throw new java.lang.ArrayIndexOutOfBoundsException(i+">("+mnDetailedPeriod+"-1)");
    				}
    				moEnergyDetailed.put(i, oData);
    			} else {
    				moEnergyTemp.add(oData.doubleValue());
    			}
    		}
    	}
    	
    	//store complete culmulative collection to long
    	if (phase == 3) {
    		moDataLong = moDataTemp;
    		moEnergyLong = moEnergyTemp;
    	}
    }
    
	/* (non-Javadoc)
	 *
	 * @author langr
	 * 03.08.2009, 13:35:21
	 * 
	 * @see sim.portrayal.Inspector#updateInspector()
	 */
	@Override
	public void updateInspector() {
        String contentData = "<html><head><tr.font face='Courier'></head><body>";
        contentData+=generateContent();
        contentData+="</body></html>";
    	moHTMLPane.setText(contentData);
	}
	
	private String generateTableHeader() {
		String oContent = "";
		oContent+="<tr><th>BodyPart</th>";
		for (int i=0;i<mnDetailedPeriod; i++) {
			String t = ""+i;
			if (i>0) t="-"+i;
			oContent+="<th>t<sub>"+t+"</sub></th>";
		}
		
		//short phase
		int from = -mnDetailedPeriod;
		int to = -(mnShortPeriod-1);
		oContent +="<th>t<sub>"+from+"</sub> - t<sub>"+to+"</sub></th>";
		//short phase
		from = to-1;
		to = -(mnMediumPeriod-1);
		oContent +="<th>t<sub>"+from+"</sub> - t<sub>"+to+"</sub></th>";
		//short phase
		from = to-1;
		to = -(mnLongPeriod-1);
		oContent +="<th>t<sub>"+from+"</sub> - t<sub>"+to+"</sub></th>";
		oContent +="</tr>\n";
		return oContent;
	}
	
	private String generatePartName(eBodyParts pePart) {
		String oT = pePart.name();
		
		boolean italic = false;
		boolean bold = false;
		
		if (moKeyOnce.contains(pePart))  italic = true;
		if (moDataDetailed.get(0).get(pePart).doubleValue() > 0) bold = true;
		
		if (italic) oT="<i>"+oT+"</i>";
		if (bold) oT="<b>"+oT+"</b>";
			
		return "<td>"+oT+"</td>";		
	}
	
	private String generateDetailedParts(eBodyParts pePart) {
		String oC = "";
		for (int i=0;i<mnDetailedPeriod; i++) {
			oC+="<td>";
			HashMap<eBodyParts, clsMutableDouble> oTemp = moDataDetailed.get(i);
			oC+=formatter.format(oTemp.get(pePart).doubleValue());
			oC+="</td>";
		}
		return oC;
	}
	
	private String generateCulmulativePart(HashMap<eBodyParts, data> poData, eBodyParts pePart) {
		String oC = "";
		
		if (poData != null && poData.get(pePart) != null) {
			oC+="<td>"+poData.get(pePart)+"</td>";
		} else {
			oC+="<td>n/a</td>";
		}
		
		return oC;
	}
	
	private String generateEntry(eBodyParts pePart) {
		String oC = "<tr>";

		oC+=generatePartName(pePart);
		oC+=generateDetailedParts(pePart);

		oC+=generateCulmulativePart(moDataShort, pePart);
		oC+=generateCulmulativePart(moDataMedium, pePart);
		oC+=generateCulmulativePart(moDataLong, pePart);
		
		oC+="</tr>\n";
		
		return oC;
	}
	
	private String generateEnergyDetailedParts() {
		String oC = "";
		for (int i=0;i<mnDetailedPeriod; i++) {
			oC+="<td><u>";
			clsMutableDouble oTemp = moEnergyDetailed.get(i);
			oC+=formatter.format(oTemp.doubleValue());
			oC+="</u></td>";
		}
		return oC;
	}
	
	private String generateEnergyCulmulativePart(data poData) {
		String oC = "";
		
		if (poData != null) {
			oC+="<td><u>"+poData+"</u></td>";
		} else {
			oC+="<td><u>n/a</u></td>";
		}
		
		return oC;
	}
	
	private String generateEnergy() {
		String oC = "<tr>";
		
		oC += "<td><b><u>Total Energy</u></b></td>";
		
		oC+=generateEnergyDetailedParts();

		oC+=generateEnergyCulmulativePart(moEnergyShort);
		oC+=generateEnergyCulmulativePart(moEnergyMedium);
		oC+=generateEnergyCulmulativePart(moEnergyLong);
		
		return oC+"</tr>\n";
	}
	
	private String generateContent() {
		String oContent = "";
		
		updateData();
		
		oContent+="<table border=1>\n";
		oContent+=generateTableHeader();
		oContent+=generateEnergy();
		
		for (eBodyParts e:moPartList) {
			oContent+=generateEntry(e);
		}
		
		oContent+="</table>\n";
		
		return oContent;
	}
}