/**
 * CHANGELOG
 *
 * 20.01.2016 Kollmann - File created
 *
 */
package base.datatypes;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import org.json.JSONException;
import org.json.JSONObject;

import base.datatypes.interfaces.itfMapTree;
import base.datatypes.interfaces.itfMapTreeNode;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 20.01.2016, 13:06:33
 * 
 */
public class clsMapTreeNode implements itfMapTreeNode, Comparable<itfMapTreeNode> {
    private Set<itfMapTreeNode> children = new TreeSet<>();
    private Map<String, String> data = new HashMap<>();
    private itfMapTree tree = null;
    
    protected <T> T notNull(T value, String message) {
        if(value == null) {
            throw new IllegalArgumentException(message);
        }
        
        return value;
    }
    
    clsMapTreeNode(itfMapTree tree) {
        this.tree = tree;
    }
    
    /* (non-Javadoc)
     *
     * @since 20.01.2016 13:52:23
     * 
     * @see base.datatypes.interfaces.itfMapTreeNode#addChild()
     */
    @Override
    public itfMapTreeNode addChild(itfMapTreeNode child) {
        children.add( notNull(child, "null can not be a child in an clsMapTreeNode"));
        return child;
    }

    /* (non-Javadoc)
     *
     * @since 20.01.2016 13:52:23
     * 
     * @see base.datatypes.interfaces.itfMapTreeNode#getChildren()
     */
    @Override
    public Set<itfMapTreeNode> getChildren() {
        return children;
    }

    /* (non-Javadoc)
     *
     * @since 20.01.2016 13:52:24
     * 
     * @see base.datatypes.interfaces.itfMapTreeNode#setData(java.lang.String, java.lang.String)
     */
    @Override
    public void setData(String key, String value) {
        data.put(key, value);
    }

    /* (non-Javadoc)
     *
     * @since 20.01.2016 13:52:24
     * 
     * @see base.datatypes.interfaces.itfMapTreeNode#getData(java.lang.String)
     */
    @Override
    public String getData(String key) {
        return data.get(key);
    }

    /* (non-Javadoc)
     *
     * @since 20.01.2016 15:01:52
     * 
     * @see base.datatypes.interfaces.itfMapTreeNode#getTree()
     */
    @Override
    public itfMapTree getTree() {
        return tree;
    }

    /* (non-Javadoc)
     *
     * @since 21.01.2016 11:34:29
     * 
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(itfMapTreeNode o) {
        String id1 = notNull(o.getData("id"), "itfMapTreeNode " + o + " has no ID and can thus not be sorted");
        String id2 = notNull(getData("id"),  "itfMapTreeNode " + this + " has no ID and can thus not be sorted");
        
        return id2.compareTo(id1);
    }

    /* (non-Javadoc)
     *
     * @since 25. Apr. 2016 12:43:28
     * 
     * @see base.datatypes.interfaces.itfMapTreeNode#toJson()
     */
    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject(data);
        for(itfMapTreeNode child : children) {
            try {
                json.append("children", child.toJson());
            } catch (JSONException e) {
                throw new RuntimeException("Could not parse child " + child + " to json, du to exception:", e);
            }
        }
        
        return json;
    }
}
