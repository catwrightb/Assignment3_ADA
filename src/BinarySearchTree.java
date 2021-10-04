/* a class for binary tree nodes
 * @author	Biagioni, Edoardo
 * @assignment	lecture 17
 * @date	March 12, 2008
 * @inspiration	William Albritton's binary search tree class,
 http://www2.hawaii.edu/~walbritt/ics211/treeBinarySearch/BinarySearchTree.java
 */

import java.awt.*;
import java.util.*;

public class BinarySearchTree<T extends Comparable<T>> {

    protected Node<T> root;

    public BinarySearchTree() {
        root = new Node<>();
    }

    public BinarySearchTree(T value) {
        root = new Node<>(value);
    }

    public BinarySearchTree(Node<T> newRoot) {
        root = newRoot;
    }

    public BinarySearchTree(Collection<? extends T> collection) {
        this();
        collection.forEach(this::add);
    }

    public Node<T> getRoot() {
        return this.root;
    }

    /**
     * Finds a node in the tree by iteration from a value.
     *
     * @param key identifies the node value desired
     * @return the node found, or null if not found
     */
    public Node<T> search(T key) {
        Node<T> node = root;
        while(node != null) {
            if(key.compareTo(node.item) == 0) {
                return node;
            } else if(key.compareTo(node.item) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return null;
    }

    /**
     * Method to see if the tree possesses the value passed in.     *
     *
     * @param key identifies the node value desired
     * @return True if node value found, or false if not found
     */
    public boolean contains(T key) {
        Node<T> node = root;
        while(node != null) {
            if(key.compareTo(node.item) == 0) {
                return true;
            } else if(key.compareTo(node.item) < 0) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        return false;
    }

    /**
     * Template method to add an element to the tree, it also calls
     * appropriate hook methods to add different functionalities
     * to the data structure.
     *
     * @param value to be inserted
     */
    public void add(T value) {
        //nodeTraversed(root);
        Node<T> moddedRoot = insert(value, root);
        postModification(moddedRoot);
    }

    /**
     * Inserts a value to the tree/sub-tree using recursion if necessary.
     *
     * @param value to be inserted
     * @param node  that is the root of the subtree in which to insert
     * @return the root node of the tree/subtree with updated links
     */
    protected Node<T> insert(T value, Node<T> node) {
        Node<T> updateNode;
        if(node == null || node.item == null) { // If node is null, or it's field is empty... (base case)
            updateNode = new Node<>(value);
//            nodeTraversed(updateNode);
        } else {
            updateNode = preModification(node); // Calls hook method to process node.
//            nodeTraversed(updateNode);
            if(value.compareTo(node.item) < 0) {    // Value smaller --> add to left subtree
                //nodeTraversed(updateNode.leftChild); // Hook method to track paths taken.
                updateNode.left = insert(value, node.left); // Recursively calls the insert method as it traverses down the subtree.
            } else if(value.compareTo(node.item) > 0) {  // Value larger --> add to right subtree
                //nodeTraversed(updateNode.rightChild); // Hook method to track paths taken.
                updateNode.right = insert(value, node.right); // Recursively calls the insert method as it traverses down the subtree.
            } // Value already exists in tree --> Ignore.
        }
        return updateNode;
    }

    /**
     * Template method which calls a hook method
     * to remove an element from the tree, if exists.
     *
     * @param key key/value to be removed from the tree/sub-tree.
     */
    public void remove(T key) {
        //nodeTraversed(root);
        Node<T> moddedRoot = remove(key, root);
        postModification(moddedRoot);
    }

    /**
     * Remove a value from the tree if it exists. Saves the paths it has
     * taken when traversing through nodes.
     *
     * @param value value to look for within the tree.
     * @param node  the head node of the subtree from which to remove the value from
     * @return node with updated links.
     */
    protected Node<T> remove(T value, Node<T> node) {
        if(node == null || node.item == null) {    // If passed in value not in tree (base case)...
            return null;
        }

        Node<T> updateNode = preModification(node);  // Calls hook method to process node.
        nodeTraversed(updateNode); // Record path.

        if(value.compareTo(node.item) == 0) { // remove this node
            if(node.left == null) { // replace this node with right child
                updateNode = node.right;
            } else if(node.right == null) { // replace with left child
                updateNode = node.left;
            } else {    // If there are two children...
                // Replace the value of the removal node with its predecessor.
                updateNode.item = getRightmost(node.left);
                //nodeTraversed(updateNode.leftChild); // Record path.
                // Recursively remove the predecessor node (found in the left subtree of the removal node).
                updateNode.left = remove(updateNode.item, node.left);
            }
        } else {    // remove from left or right subtree
            if(value.compareTo(node.item) < 0) {   // remove from left subtree
                //nodeTraversed(updateNode.leftChild); // Record path.
                updateNode.left = remove(value, node.left);
            } else if(value.compareTo(node.item) > 0) {  // remove from right subtree
                //nodeTraversed(updateNode.rightChild); // Record path.
                updateNode.right = remove(value, node.right);
            }
        }
        return updateNode;
    }

    /**
     * Helper method to traverse to the right of the Node passed-in.
     * Returns the element once it can go no further. This helper aids in
     * finding the predecessor of a head/root node.
     *
     * @param node : starting node
     * @return element the node is holding
     */
    protected T getRightmost(Node<T> node) {
        assert (node != null);
        Node<T> right = node.right;
        if(right == null) {
            return node.item;
        } else {
            return getRightmost(right);
        }
    }

    /* iterator, traverses the tree in order */
    public Iterator<T> iterator() {
        return new TreeIterator(root);
    }

    /* removes all elements from the collection */
    public void clear() {
        this.root = new Node<>(); // all children nodes will be garbage collected as well
    }

    /**
     * toString
     *
     * @return the string representation of the tree.
     */
    public String toString() {
        return toString(root);
    }

    protected String toString(Node<T> node) {
        if(node == null || node.item == null) {
            return "";
        }
        return node.item + "(" + toString(node.left) + ", " +
                toString(node.right) + ")";
    }

    /**
     * Hook method to keep track of the tracked path. Current implementation
     * does nothing useful. Implementation can be overridden by subclasses to
     * add more functionalities to them.
     *
     * @param currentNode : Current Node that program is visiting.
     */
    protected void nodeTraversed(Node<T> currentNode) {
    }

    /**
     * Hook method to handle the node that is passed in just before modifications
     * happening. Here, nothing happens, the method receives a Node and returns it
     * to the caller straightaway without processing or doing anything to it.
     * Implementation can be overridden by subclasses to add more functionalities
     * to suit data structure.
     *
     * @param node Node that is about to be modified.
     * @return a node object.
     */
    protected Node<T> preModification(Node<T> node) {
        return node; // Pass the object back to caller unmodified.
    }


    /**
     * Hook method to handle the node that is passed in after modifications
     * have happened. Here, it just updates the root node to the most recent one.
     * Implementation can be overridden by subclasses to add more functionalities
     * to suit data structure.
     *
     * @param modifiedRoot Root node that has been modified.
     */
    protected void postModification(Node<T> modifiedRoot) {
        this.root = modifiedRoot; // Update the reference to the root node.
    }

    // hooker methods
    protected void nodeFinished(Node<T> node) {
        //
    }

    // Inner class that represents an Iterator for a binary tree
    private class TreeIterator implements Iterator<T> {
        private LinkedList<T> list;
        private Iterator<T> iterator;

        public TreeIterator(Node<T> rootNode) {  // puts the elements in a linked list using inorder traversal
            list = new LinkedList<>();
            traverseInOrder(rootNode);
            iterator = list.iterator();
        }

        // recursive helper method that traverses the subtree from node
        // adding the elements to the list collection
        private void traverseInOrder(Node<T> node) {
            if(node != null) {
                traverseInOrder(node.left);
                list.add(node.item);
                traverseInOrder(node.right);
            }
        }

        public boolean hasNext() {
            return iterator.hasNext();
        }

        public T next() {
            return iterator.next();
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }
    }

    public static void main(String[] args) {  // create the binary search tree
        BinarySearchTree<String> tree = new BinarySearchTree<>();
        // build the tree
        tree.add("cow");
        tree.add("fly");
        tree.add("dog");
        tree.add("bat");
        tree.add("fox");
        tree.add("cat");
        tree.add("eel");
        tree.add("ant");
        System.out.println("Original Tree: " + TreeBuilder.visualise(tree.root));
        tree.remove("owl");
        tree.remove("cow");
        tree.add("owl");
        System.out.println("Modified Tree: " + TreeBuilder.visualise(tree.root));
    }


    static int drawBST(Graphics g, Node<?> current, int x, int level, int nodeCount, Map<Node<?>, Point> map, int BOX_SIZE) {

        if(current.left != null) {
            nodeCount = drawBST(g, current.left, x, level + 1, nodeCount, map, BOX_SIZE);
        }

        int currentX = x + nodeCount * BOX_SIZE;
        int currentY = level * 2 * BOX_SIZE + BOX_SIZE;
        nodeCount++;
        map.put(current, new Point(currentX, currentY));

        if(current.right != null) {
            nodeCount = drawBST(g, current.right, x, level + 1, nodeCount, map, BOX_SIZE);
        }

        g.setColor(Color.red);
        if(current.left != null) {
            Point leftPoint = map.get(current.left);
            g.drawLine(currentX, currentY, leftPoint.x, leftPoint.y - BOX_SIZE / 2);
        }
        if(current.right != null) {
            Point rightPoint = map.get(current.right);
            g.drawLine(currentX, currentY, rightPoint.x, rightPoint.y - BOX_SIZE / 2);

        }

        if(current instanceof RedBlackNode) {
            g.setColor(((RedBlackNode<?>) current).getColor());
        } else {
            g.setColor(Color.WHITE);
        }

        Point currentPoint = map.get(current);
        g.fillRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);
        g.setColor(Color.BLACK);
        g.drawRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);
        Font f = new Font("courier new", Font.BOLD, 16);
        g.setFont(f);
        g.drawString(current.toString(), currentPoint.x - 3, currentPoint.y);
        return nodeCount;
    }
}