import java.util.*;

public class PersistentBST<T extends Comparable<T>> extends BinarySearchTree<T> {
    protected List<Node<T>> versionRepository;
    protected List<BinarySearchTree<T>> treeRepository;
    protected Stack<Node<T>> visitedNodes;

    public PersistentBST()
    {
        super();
        this.root = new Node<>();
        this.versionRepository = new ArrayList<>();
        this.versionRepository.add(this.root);
        this.treeRepository = new ArrayList<>();
        this.treeRepository.add(new BinarySearchTree<>(this.root));
        this.visitedNodes = new Stack<>();
    }

    public int currentVersionNo() {
        return versionRepository.size();
    }

    public Node<T> currentRoot() {
        return this.root;
    }

    /**
     * Overridden hook method to simulate/enable "path-copying".
     * Current implementation deep clones the parameter node and
     * passes a copy back to the caller. Result is the ability to
     * retain previous versions of the tree/sub-tree (pre-modification)
     * as previous links of the node are kept(unmodified).
     *
     * @param currentNode : Current Node that program is visiting.
     */
    @Override
    protected void nodeTraversed(Node<T> currentNode) {
        visitedNodes.push(currentNode);
    }

    /**
     * Overridden hook method to add the modified root node with updated links
     * to the version repo. This method is intended to record each version of the
     * root node after every modification that has happened.
     *
     * @param modifiedRoot Root node that has been modified.
     */
    @Override
    protected void postModification(Node<T> modifiedRoot) {
        this.versionRepository.add(modifiedRoot);
        this.treeRepository.add(new BinarySearchTree<>(modifiedRoot));
        this.root = modifiedRoot; // Update the current root node.
//        this.visitedNodes.clear();
    }

    public static void main(String[] args) {  // create the binary search tree
        PersistentBST<String> tree = new PersistentBST<String>();
        // build the tree
        tree.add("cow");
        tree.add("fly");
        tree.add("dog");
        tree.add("bat");
        tree.add("fox");
        tree.add("cat");
        tree.add("eel");
        tree.add("ant");
        System.out.println("Original Tree: " + tree);
        tree.remove("owl");
        tree.remove("cow");
        tree.add("owl");
        System.out.println("Modified Tree: " + tree);
        System.out.println();

        for(int i = 0; i < tree.versionRepository.size(); i++) {
            System.out.println("Tree["+i+"]: " + ExpressionTreeBuilder.visualise(tree.versionRepository.get(i)));
            //tree.visitedNodes.forEach((System.out::println));
        }
    }
}
