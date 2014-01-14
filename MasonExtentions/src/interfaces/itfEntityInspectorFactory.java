/**
 * CHANGELOG
 *
 * 31.07.2013 herret - File created
 *
 */
package interfaces;


import sim.display.GUIState;
import sim.portrayal.Inspector;
import sim.portrayal.LocationWrapper;
import sim.portrayal.inspector.TabbedInspector;

/**
 * DOCUMENT (herret) - insert description 
 * 
 * @author herret
 * 31.07.2013, 08:18:09
 * 
 */
public interface itfEntityInspectorFactory {
	public TabbedInspector getInspector(LocationWrapper poWrapper, GUIState state, Inspector poOriginalInspector, itfEntity poEntity);
}
