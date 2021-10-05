import java.util.*;
import java.util.stream.IntStream;

public class PersistentBST<T extends Comparable<T>> extends BinarySearchTree<T> {
    protected List<Node<T>> versionRepository;
    protected Stack<Node<T>> visitedNodes;

    public PersistentBST()
    {
        super();
        this.versionRepository = new ArrayList<>();
        this.versionRepository.add(this.root);
        this.visitedNodes = new Stack<>();
    }

    public int getCurrentVersionNo() {
        return (versionRepository.size() == 0 ? 0 : versionRepository.size() - 1);
    }

    public Integer[] getVersionNos() {
        return IntStream.rangeClosed(0, this.getCurrentVersionNo()).boxed().toArray(Integer[]::new);
    }



    /* Gets a saved version of a tree. */
    public Node<T> getBranch(int versionNo) {
        return versionRepository.get(versionNo);
    }

    /* removes all elements from the collection */
    @Override
    public void clear() {
        this.root = new Node<>(); // old root and children nodes will be garbage collected.
        versionRepository.clear();
        versionRepository.add(this.root);
        visitedNodes.clear();
    }

    /**
     * Overridden hook method to enable "path-copying". Pushes the
     * node passed-in into a stack. Keeps track of traversed nodes.
     *
     * @param currentNode : Current Node that program is visiting.
     */
    @Override
    protected void nodeTraversed(Node<T> currentNode) {
        visitedNodes.push(currentNode);
    }

    /**
     * Hook method to handle the node that is passed in just before modification
     * happen. Here, the method receives a Node, performs a deep clone, then returns the
     * reference of the clone (new Node instance with old original values) to the caller.
     *
     * @param node Node that is about to be modified.
     * @return a copy of the Node that was passed in.
     */
    protected Node<T> preModification(Node<T> node) {
        return node.deepClone(node); // Pass the reference of the cloned Node back to caller.
    }

    /**
     * Overridden hook method to add the modified root node with updated links
     * to the version repo. This method is intended to keep record each version
     * of the root node after every modification that has happened.
     *
     * @param modifiedRoot Root node that has been modified.
     */
    @Override
    protected void postModification(Node<T> modifiedRoot) {
        this.versionRepository.add(modifiedRoot);
        this.root = modifiedRoot; // Update the current root node.
        this.visitedNodes.clear();
    }

}
