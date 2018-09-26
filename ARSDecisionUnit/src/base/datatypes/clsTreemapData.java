/**
 * CHANGELOG
 *
 * 21.01.2016 Kollmann - File created
 *
 */
package base.datatypes;

import java.awt.Color;

import base.datatypes.interfaces.itfMapTreeNode;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 21.01.2016, 11:03:05
 * 
 */
public class clsTreemapData extends clsMapTree {
    public itfMapTreeNode createNode(itfMapTreeNode parent, String id, String name, double size, double darkeningFactor, Color color) {
        clsMapTreeNode node = new clsMapTreeNode(this);
        
        node.setData("id", id);
        node.setData("name", name);
        node.setData("darkeningFactor", Double.toString(darkeningFactor));
        String hexColor = String.format("#%06X", (0xFFFFFF & color.getRGB()));
        node.setData("color", hexColor);
        node.setData("size", Double.toString(size));
        
        return node;
    }
    
    public itfMapTreeNode createNode(itfMapTreeNode parent, String id, double size, double darkeningFactor, String color) {
        return createNode(parent, id, id, size, darkeningFactor, Color.decode(color));
    }
}