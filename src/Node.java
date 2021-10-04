import java.awt.*;

public class Node<T> {
    protected T item;
    public Node<T> left;
    public Node<T> right;
    public String color;

    /**
     * constructor to build an empty skeleton node
     */
    public Node() {
        item = null;
        left = null;
        right = null;
        color = null;
    }

    /**
     * constructor to build a node with no subtrees
     */
    public Node(T value) {
        item = value;
        left = null;
        right = null;
        color = null;
    }

    /**
     * constructor to build a node with a specified (perhaps null) subtrees
     */
    private Node(T value, Node<T> l, Node<T> r) {
        item = value;
        left = l;
        right = r;
        color = null;
    }

    /**
     * constructor to build an RB node with a specified (perhaps null) subtrees
     */
    private Node(T value, Node<T> l, Node<T> r, String col) {
        item = value;
        left = l;
        right = r;
        color = col;
    }

    public Color getColor() {
        Color toRet = new Color(70, 70, 70);
        if(color.equals("RED"))
            toRet = new Color(250, 70, 70);
        return toRet;
    }

    /**
     * Method to deep clone a Node object.
     *
     * @param node Node instance to be copied.
     * @return a new instance of the cloned Node with original links and value
     */
    public Node<T> deepClone(Node<T> node) {
        // Deep copies this node and return it to caller.
        return new Node<>(node.item, node.left, node.right, node.color);
    }

    @Override
    public String toString() {
        return String.valueOf(item);
    }
}
