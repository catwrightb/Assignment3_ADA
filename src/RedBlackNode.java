import java.awt.*;

public class RedBlackNode<T extends Comparable<T>> extends Node<T>{

    protected static final Color RED = Color.red;
    protected static final Color BLACK = Color.black;

    protected Color colour;

    public RedBlackNode() {
        super();
        this.colour = RED;
    }

    public RedBlackNode(T value) {
        super(value);
        this.colour = RED;
    }

    public RedBlackNode(T value, Color col) {
        super(value);
        this.colour = col;
    }

    protected RedBlackNode(T value, RedBlackNode<T> left, RedBlackNode<T> right, Color col) {
        this.item = value;
        this.leftChild = left;
        this.rightChild = right;
        this.colour = col;
    }

    /* Method to return a colour Object based on the Node's string rep */
    public Color getColor() {
        return this.colour;
    }

    /**
     * Method to deep clone a Node object.
     * @param rbNode Node instance to be copied.
     * @return a new instance of the cloned Node with original links and value
     */
    public RedBlackNode<T> deepClone(RedBlackNode<T> rbNode) {
        // Deep copies this node and return it to caller.
        return new RedBlackNode<T>(rbNode.item, (RedBlackNode<T>)rbNode.leftChild,  (RedBlackNode<T>)rbNode.rightChild, rbNode.colour);
    }

    @Override
    public String toString()
    {
        return super.toString();
    }
}
