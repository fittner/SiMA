/**
 * CHANGELOG
 *
 * 20.01.2016 Kollmann - File created
 *
 */
package base.datatypes.interfaces;

import java.util.Set;

import org.json.JSONObject;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 20.01.2016, 13:49:21
 * 
 */
public interface itfMapTreeNode {
    public itfMapTreeNode addChild(itfMapTreeNode child);
    public Set<itfMapTreeNode> getChildren();
    public String getData(String key);
    public void setData(String key, String value);
    public JSONObject toJson();
    public itfMapTree getTree();
}
