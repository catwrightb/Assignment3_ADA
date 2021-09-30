public class Node<T>  {
    protected T item;
    public Node<T> leftChild;
    public Node<T> rightChild;


    /**
     * constructor to build a node with no subtrees
     */
    Node(T value) {
        item = value;
        leftChild = null;
        rightChild = null;
    }


    /**
     * constructor to build a node with a specified (perhaps null) subtrees
     *
     */
    private Node(T value, Node<T> l, Node<T> r) {
        item = value;
        leftChild = l;
        rightChild = r;
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
