/**
 * CHANGELOG
 *
 * 25. Apr. 2016 Kollmann - File created
 *
 */
package base.datatypes;

import java.awt.Color;
import java.util.HashMap;
import java.util.Map;

import base.datatypes.interfaces.itfMapTreeNode;

/**
 * DOCUMENT (Kollmann) - insert description
 * 
 * @author Kollmann 25. Apr. 2016, 12:57:30
 * 
 */
public class clsDecisionTreeData extends clsMapTree {
    private Map<String, itfMapTreeNode> index = new HashMap<>();
    
    public itfMapTreeNode createNode(itfMapTreeNode parent, String id, String name, int value, int position, Color color, double darkening, int order) {
        clsMapTreeNode node = new clsMapTreeNode(this);
        
        node.setData("id", id);
        node.setData("level", "BLUE");
        node.setData("nameColor", "BLACK");
        node.setData("name", name);
        node.setData("position", Integer.toString(position));
        node.setData("type", "BLUE");
        node.setData("message", "BLACK");
        node.setData("groupname", "(" + value + ")");
        node.setData("value", Double.toString(value));
        node.setData("color", String.format("#%02x%02x%02x", color.getRed(), color.getGreen(), color.getBlue()));
        node.setData("darkeningFactor", Double.toString(darkening));
        node.setData("childOrder", Integer.toString(order));
        
        index.put(id, node);
        
        if(parent != null) {
            parent.addChild(node);
            node.setData("parent", parent.getData("id"));
        } else {
            setRoot(node);
        }
        
        return node;
    }

    public itfMapTreeNode createNode(itfMapTreeNode parent, String id, int value, int position, int order) {
        return createNode(parent, id, id, value, position, Color.BLACK, 0.0, order);
    }
    
    public itfMapTreeNode createNode(itfMapTreeNode parent, String id, int value, int position, Color color, double darkening, int order) {
        return createNode(parent, id, id, value, position, color, darkening, order);
    }
    
    public itfMapTreeNode getById(String id) {
        return index.get(id);
    }
}
