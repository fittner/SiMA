/**
 * itfSymbolAcoustic.java: DecisionUnits - pa.symbolization.representationsymbol
 * 
 * @author hi
 * 19.10.2009, 19:40:17
 */
package prementalapparatus.symbolization.representationsymbol;

import java.util.ArrayList;

/**
 * DOCUMENT (hi) - insert description 
 * 
 * @author hi
 * 
 */
public interface itfSymbolAcoustic extends itfSymbol {
    
    ArrayList<itfSymbolAcousticEntry> getEntries();
}
