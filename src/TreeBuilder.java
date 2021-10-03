
public class TreeBuilder {
    /**
     * Helper method to return a text representation
     * of a binary search tree. Traverses inorder.
     *
     * @param node root node
     * @return text representation of the tree.
     */
    public static String visualise(Node<?> node) {
        String output = "[";
        if(node != null && node.item != null) {
            if(node.leftChild != null)
                output += "" + visualise(node.leftChild);
            output += "" + node.toString();
            if(node.rightChild != null)
                output += "" + visualise(node.rightChild);
        }
        output += "]";
        return output;
    }

    /**
     * Method to recursively count the number of nodes a tree has.
     *
     * @param node root node of the tree.
     * @return number of nodes in the tree
     */
    public static int countNodes(Node<?> node) {
        if(node == null)
            return 0;
        else
            return countNodes(node.leftChild) + 1 + countNodes(node.rightChild);
    }
}
