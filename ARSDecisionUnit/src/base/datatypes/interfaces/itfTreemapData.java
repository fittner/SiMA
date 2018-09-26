/**
 * CHANGELOG
 *
 * 20.01.2016 Kollmann - File created
 *
 */
package base.datatypes.interfaces;

import java.awt.Color;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 20.01.2016, 14:53:01
 * 
 */
public interface itfTreemapData {
    public void setId(String id);
    public void setName(String name);
    public void setDarkening(double factor);
    public void setColor(Color color);
}
