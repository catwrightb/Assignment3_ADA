public class Node<T>  {
    protected T item;
    public Node<T> left;
    public Node<T> right;


    /**
     * constructor to build a node with no subtrees
     */
    Node(T value) {
        item = value;
        left = null;
        right = null;
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


    @Override
    public String toString()
    {
        return String.valueOf(item);
    }
}
