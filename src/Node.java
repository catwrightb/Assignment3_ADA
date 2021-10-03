/**
 * A regular node in a binary tree. Stores an element and pointers to its left & right children.
 * Type must be of
 * Reference
 * ---------------------------------------------------------------------------------------
 * https://www2.hawaii.edu/~esb/2011fall.ics211/BinarySearchTree.java.html
 */
public class Node<T extends Comparable<T>> {
    protected T item;
    public Node<T> leftChild;
    public Node<T> rightChild;

    public Node() {
        item = null;
        leftChild = null;
        rightChild = null;
    }

    /**
     * Constructor to build a node with no children.
     */
    public Node(T value) {
        item = value;
        leftChild = null;
        rightChild = null;
    }

    /**
     * Private constructor to build a node with specified children
     * for the use of cloning nodes.
     */
    protected Node(T value, Node<T> left, Node<T> right) {
        item = value;
        leftChild = left;
        rightChild = right;
    }

    public T getItem() {
        return this.item;
    }

    /**
     * Method to deep clone a Node object.
     * @param node Node instance to be copied.
     * @return a new instance of the cloned Node with original links and value
     */
    public Node<T> deepClone(Node<T> node) {
        // Deep copies this node and return it to caller.
        return new Node<T>(node.item, node.leftChild, node.rightChild);
    }

    @Override
    public String toString()
    {
        //return super.toString(); // Debugger, prints out the node's memory addresses.
        return String.valueOf(item);
    }
}
