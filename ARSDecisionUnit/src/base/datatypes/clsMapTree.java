/**
 * CHANGELOG
 *
 * 20.01.2016 Kollmann - File created
 *
 */
package base.datatypes;

import base.datatypes.interfaces.itfMapTree;
import base.datatypes.interfaces.itfMapTreeNode;

/**
 * DOCUMENT (Kollmann) - insert description 
 * 
 * @author Kollmann
 * 20.01.2016, 13:06:15
 * 
 */
public class clsMapTree implements itfMapTree {
    itfMapTreeNode root = null;
    
    /* (non-Javadoc)
     *
     * @since 20.01.2016 14:06:46
     * 
     * @see base.datatypes.interfaces.itfMapTree#getNode()
     */
    @Override
    public itfMapTreeNode createNode(itfMapTreeNode parent) {
        return new clsMapTreeNode(this);
    }

    /* (non-Javadoc)
     *
     * @since 20.01.2016 14:27:11
     * 
     * @see base.datatypes.interfaces.itfMapTree#setRoot(base.datatypes.interfaces.itfMapTreeNode)
     */
    @Override
    public itfMapTreeNode setRoot(itfMapTreeNode root) {
        this.root = root;
        return root;
    }

    /* (non-Javadoc)
     *
     * @since 20.01.2016 14:27:46
     * 
     * @see base.datatypes.interfaces.itfMapTree#getRoot()
     */
    @Override
    public itfMapTreeNode getRoot() {
        return root;
    }
}
