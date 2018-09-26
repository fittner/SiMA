/**
 * CHANGELOG
 *
 * 20.01.2016 Kollmann - File created
 *
 */
package base.datatypes.interfaces;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 20.01.2016, 14:05:52
 * 
 */
public interface itfMapTree {
    public itfMapTreeNode setRoot(itfMapTreeNode root);
    public itfMapTreeNode getRoot();
    public itfMapTreeNode createNode(itfMapTreeNode parent);
}
