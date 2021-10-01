/**
 * A regular node in a binary tree. Stores an element and pointers to its left & right child.
 *
 * Reference
 * ---------------------------------------------------------------------------------------
 * https://www2.hawaii.edu/~esb/2011fall.ics211/BinarySearchTree.java.html
 */
public class Node<T>  {
    protected T item;
    public Node<T> leftChild;
    public Node<T> rightChild;

    /**
     * Default constructor.
     */
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
    private Node(T value, Node<T> left, Node<T> right) {
        item = value;
        leftChild = left;
        rightChild = right;
    }

//    /**
//     * Method to deep clone a Node object.
//     * @return the cloned Node
//     */
//    @Override
//    public static Node<T> clone() {
//        // Deep copies this node and return it to caller.
//        return new Node<>(this.item, this.leftChild, this.rightChild);
//    }

    /**
     * Method to deep clone a Node object.
     * @return the cloned Node
     */
    public Node<T> deepClone(Node<T> node) {
        // Deep copies this node and return it to caller.
        return new Node<>(node.item, node.leftChild, node.rightChild);
    }

    @Override
    public String toString()
    {
        return super.toString();
        //return String.valueOf(item);
    }
}
