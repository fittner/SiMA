/**
 * CHANGELOG
 *
 * 25. Apr. 2016 Kollmann - File created
 *
 */
package base.datatypes;

import base.datatypes.interfaces.itfMapTreeNode;

/**
 * DOCUMENT (Kollmann) - insert description
 * 
 * @author Kollmann 25. Apr. 2016, 12:57:30
 * 
 */
public class clsDecisionTreeData extends clsMapTree {
    public itfMapTreeNode createNode(itfMapTreeNode parent, String id, String name, int value, int position) {
        clsMapTreeNode node = new clsMapTreeNode(this);
        
        node.setData("id", id);
        node.setData("level", "BLUE");
        node.setData("nameColor", "BLACK");
        node.setData("name", name);
        node.setData("position", Integer.toString(position));
        node.setData("type", "BLUE");
        node.setData("message", "BLACK");
        node.setData("groupname", "(subtitle)");
        node.setData("value", Integer.toString(value));
        
        return node;
    }

    public itfMapTreeNode createNode(itfMapTreeNode parent, String id, int value, int position) {
        return createNode(parent, id, id, value, position);
    }
}
