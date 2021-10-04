import java.awt.*;

public class Node<T>  {
    protected T item;
    public Node<T> left;
    public Node<T> right;
    public String color;


    /**
     * constructor to build a node with no subtrees
     */
    Node(T value) {
        item = value;
        left = null;
        right = null;
        color = null;
    }


    /**
     * constructor to build a node with a specified (perhaps null) subtrees
     *
     */
    private Node(T value, Node<T> l, Node<T> r) {
        item = value;
        left = l;
        right = r;
    }

    public Node() {
        item = null;
    }

    Color getColor() {
        Color toRet = new Color(70, 70, 70);
        if (color.equals("RED"))
            toRet = new Color(250, 70, 70);
        return toRet;
    }


    @Override
    public String toString()
    {
        return String.valueOf(item);
    }
}
