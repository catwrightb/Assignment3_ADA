/* a class for binary tree nodes
 * @author	Biagioni, Edoardo
 * @assignment	lecture 17
 * @date	March 12, 2008
 * @inspiration	William Albritton's binary search tree class,
 http://www2.hawaii.edu/~walbritt/ics211/treeBinarySearch/BinarySearchTree.java
 */

import java.awt.*;
import java.util.Iterator;
import java.util.Map;
import java.util.Stack;

public class BinarySearchTree<T extends Comparable<T>> {

    protected Node<T> root;

    /**
     * Constructs an empty tree.
     */
    public BinarySearchTree() {
        root = null;
    }

    /**
     * Constructs a tree with one element, as given.
     *
     * @param value to be used for the one element in the tree
     */
    public BinarySearchTree(T value) {
        root = new Node<T>(value);
    }

    /**
     * Constructs a tree with the given node as root.
     *
     * @param newRoot to be used as the root of the new tree
     */
    public BinarySearchTree(Node<T> newRoot) {
        root = newRoot;
    }

    /**
     * Finds a value in the tree by iteration.
     *
     * @param key identifies the node value desired
     * @return the node value found, or null if not found
     */
    public T get(T key) {
        Node<T> node = root;
        while(node != null) {
            if(key.compareTo(node.item) == 0) {
                return node.item;
            } else if(key.compareTo(node.item) < 0) {
                node = node.leftChild;
            } else {
                node = node.rightChild;
            }
        }
        return null;
    }

    /**
     * Template method to add an element to the tree, it also calls
     * appropriate hook methods to add different functionalities
     * to the data structure.
     *
     * @param value to be inserted
     */
    public void add(T value) {
        nodeTraversed(root);
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
        Node<T> copy;
        if(node == null || node.item == null) {
            copy = new Node<T>(value);
        } else {
            copy = node.deepClone(node);
            if(value.compareTo(node.item) < 0) {    // add to left subtree
                nodeTraversed(node.leftChild);
                copy.leftChild = insert(value, node.leftChild);
            } else {        // add to right subtree
                nodeTraversed(node.rightChild);
                copy.rightChild = insert(value, node.rightChild);
            }
        }
        return copy;
    }

    /**
     * Template method which calls a hook method
     * to remove an element from the tree, if it exists.
     *
     * @param key key/value to be removed from the tree/sub-tree.
     */
    public void remove(T key) {
        nodeTraversed(root);
        Node<T> moddedRoot = remove(key, root);
        postModification(moddedRoot);
    }

    /**
     *  Remove a value from the tree if it exists. Saves the paths it has when
     *  traversing through nodes.
     *
     * @param value value to look for within the tree.
     * @param node the head node of the subtree from which to remove the value from
     * @return an updated reference to the head node with the modifications done.
     */
    protected Node<T> remove(T value, Node<T> node) {
        if(node == null || node.item == null) {    // If passed in value not in tree...
            return null;
        }

        Node<T> copy = node.deepClone(node);

        if(value.compareTo(node.item) == 0) { // remove this node
            if(node.leftChild == null) { // replace this node with right child
                return node.rightChild;
            } else if(node.rightChild == null) { // replace with left child
                return node.leftChild;
            } else {    // If there are two children...
                // Replace the value in this node with the rightmost node of the left subtree (find the predecessor).
                copy.item = getRightmost(node.leftChild);
                nodeTraversed(node.leftChild); // Record path.
                // Recursively remove the rightmost node in the left subtree.
                copy.leftChild = remove(node.item, node.leftChild);
            }
        } else {    // remove from left or right subtree
            if(value.compareTo(node.item) < 0) {   // remove from left subtree
                nodeTraversed(node.leftChild); // Record path.
                copy.leftChild = remove(value, node.leftChild);
            } else {    // remove from right subtree
                nodeTraversed(node.rightChild); // Record path.
                copy.rightChild = remove(value, node.rightChild);
            }
        }
        return copy;
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
        Node<T> right = node.rightChild;
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

    /* traverses the tree in pre-order */
    public Iterator<T> preIterator() {
        return new TreeIterator(root, true);
    }

    /* traverses the tree in post-order */
    public Iterator<T> postIterator() {
        return new TreeIterator(root, false);
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
        return node.item.toString() + "(" + toString(node.leftChild) + ", " +
                toString(node.rightChild) + ")";
    }

    /**
     * Hook method to keep track of the tracked path. Current implementation
     * does nothing useful. Implementation can be overridden by subclasses to
     * augment data structures by adding more functionalities to the base
     * class' template methods.
     *
     * @param currentNode : Current Node that program is visiting.
     */
    protected void nodeTraversed(Node<T> currentNode) {
    }

    /**
     * Hook method to handle the node that is passed in after a modification
     * has happened. Here, it just updates the root node to the most recent one.
     * Implementation can be overridden by subclasses to augment data structures
     * by adding more functionalities to the base class' template methods.
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



    /* an iterator class to iterate over binary trees
     * @author	Biagioni, Edoardo
     * @assignment	lecture 17
     * @date	March 12, 2008
     */

    private class TreeIterator implements Iterator<T> {
        /* the class variables keep track of how much the iterator
         * has done so far, and what remains to be done.
         * root is null when the iterator has not been initialized,
         * or the entire tree has been visited.
         * the first stack keeps track of the last node to return
         * and all its ancestors
         * the second stack keeps track of whether the node visited
         * is to the left (false) or right (true) of its parent
         */
        protected Node<T> root = null;
        protected Stack<Node<T>> visiting = new Stack<Node<T>>();
        protected Stack<Boolean> visitingRightChild = new Stack<Boolean>();
        /* only one of these booleans can be true */
        boolean preorder = false;
        boolean inorder = true;
        boolean postorder = false;

        /* constructor for in-order traversal
         * @param	root of the tree to traverse
         */
        public TreeIterator(Node<T> root) {
            this.root = root;
            visiting = new Stack<Node<T>>();
            visitingRightChild = new Stack<Boolean>();
            preorder = false;
            inorder = true;
            postorder = false;
        }

        /* constructor for pre-order or post-order traversal
         * @param	root of the tree to traverse
         * @param	inPreorder true if pre-order, false if post-order
         */
        public TreeIterator(Node<T> root, boolean inPreorder) {
            this.root = root;
            visiting = new Stack<Node<T>>();
            visitingRightChild = new Stack<Boolean>();
            preorder = inPreorder;
            inorder = false;
            postorder = !preorder;
        }

        public boolean hasNext() {
            return (root != null);
        }

        public T next() {
            if(!hasNext()) {
                throw new java.util.NoSuchElementException("no more elements");
            }
            if(preorder) {
                return preorderNext();
            } else if(inorder) {
                return inorderNext();
            } else if(postorder) {
                return postorderNext();
            } else {
                assert (false);
                return null;
            }
        }

        // return the node at the top of the stack, push the next node if any
        private T preorderNext() {
            if(visiting.empty()) {    // at beginning of iterator
                visiting.push(root);
            }
            Node<T> node = visiting.pop();
            T result = node.item;
            // need to visit the left subtree first, then the right
            // since a stack is a LIFO, push the right subtree first, then
            // the left.  Only push non-null trees
            if(node.rightChild != null) {
                visiting.push(node.rightChild);
            }
            if(node.leftChild != null) {
                visiting.push(node.leftChild);
            }
            // may not have pushed anything.  If so, we are at the end
            if(visiting.empty()) { // no more nodes to visit
                root = null;
            }
            return node.item;
        }

        /* find the leftmost node from this root, pushing all the
         * intermediate nodes onto the visiting stack
         * @param	node the root of the subtree for which we
         *		are trying to reach the leftmost node
         * @changes	visiting takes all nodes between node and the leftmost
         */
        private void pushLeftmostNode(Node<T> node) {
            // find the leftmost node
            if(node != null) {
                visiting.push(node); // push this node
                pushLeftmostNode(node.leftChild); // recurse on next left node
            }
        }

        /* return the leftmost node that has not yet been visited
         * that node is normally on top of the stack
         * inorder traversal doesn't use the visitingRightChild stack
         */
        private T inorderNext() {
            if(visiting.empty()) {    // at beginning of iterator
                // find the leftmost node, pushing all the intermediate nodes
                // onto the visiting stack
                pushLeftmostNode(root);
            } // now the leftmost unvisited node is on top of the visiting stack
            Node<T> node = visiting.pop();
            T result = node.item; // this is the value to return
            // if the node has a right child, its leftmost node is next
            if(node.rightChild != null) {
                Node<T> right = node.rightChild;
                // find the leftmost node of the right child
                pushLeftmostNode(right);
                // note "node" has been replaced on the stack by its right child
            } // else: no right subtree, go back up the stack
            // next node on stack will be next returned
            if(visiting.empty()) { // no next node left
                root = null;
            }
            return result;
        }

        /* find the leftmost node from this root, pushing all the
         * intermediate nodes onto the visiting stack
         * and also stating that each is a left child of its parent
         * @param	node the root of the subtree for which we
         *		are trying to reach the leftmost node
         * @changes	visiting takes all nodes between node and the leftmost
         */
        private void pushLeftmostNodeRecord(Node<T> node) {
            // find the leftmost node
            if(node != null) {
                visiting.push(node); // push this node
                visitingRightChild.push(false); // record that it is on the left
                pushLeftmostNodeRecord(node.leftChild); // continue looping
            }
        }

        //
        private T postorderNext() {
            if(visiting.empty()) {    // at beginning of iterator
                // find the leftmost node, pushing all the intermediate nodes
                // onto the visiting stack
                pushLeftmostNodeRecord(root);
            } // the node on top of the visiting stack is the next one to be
            // visited, unless it has a right subtree
            if((visiting.peek().rightChild == null) || // no right subtree, or
                    (visitingRightChild.peek())) { // right subtree already visited
                // already visited right child, time to visit the node on top
                T result = visiting.pop().item;
                visitingRightChild.pop();
                if(visiting.empty()) {
                    root = null;
                }
                return result;
            } else { // now visit this node's right subtree
                // pop false and push true for visiting right child
                assert !visitingRightChild.pop();
                visitingRightChild.push(true);
                // now push everything down to the leftmost node
                // in the right subtree
                Node<T> right = visiting.peek().rightChild;
                assert (right != null);
                pushLeftmostNodeRecord(right);
                // use recursive call to visit that node
                return postorderNext();
            }
        }

        /* not implemented */
        public void remove() {
            throw new UnsupportedOperationException("remove");
        }

    }
//        /* give the entire state of the iterator: the tree and the two stacks */
//        public String toString() {
//            if (preorder) {
//                return "pre: " + toString(root) + "\n" + visiting + "\n";
//            }
//            if (inorder) {
//                return "in: " + toString(root) + "\n" + visiting + "\n";
//            }
//            if (postorder) {
//                return "post: " + toString(root) + "\n" + visiting + "\n" +
//                        visitingRightChild;
//            }
//            return "none of pre-order, in-order, or post-order are true";
//        }
//
//        private String toString(Node<T> node) {
//            if (node == null) {
//                return "";
//            } else {
//                return node.toString() + "(" + toString(node.leftChild) + ", " +
//                        toString(node.rightChild) + ")";
//            }
//        }


    public static void main(String[] args) {  // create the binary search tree
        BinarySearchTree<String> tree = new BinarySearchTree<String>();
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
    }


    static int drawBST(Graphics g, Node current, int x, int level, int nodeCount, Map<Node, Point> map, int BOX_SIZE) {

        if(current.leftChild != null) {
            nodeCount = drawBST(g, current.leftChild, x, level + 1, nodeCount, map, BOX_SIZE);
        }

        int currentX = x + nodeCount * BOX_SIZE;
        int currentY = level * 2 * BOX_SIZE + BOX_SIZE;
        nodeCount++;
        map.put(current, new Point(currentX, currentY));

        if(current.rightChild != null) {
            nodeCount = drawBST(g, current.rightChild, x, level + 1, nodeCount, map, BOX_SIZE);
        }

        g.setColor(Color.red);
        if(current.leftChild != null) {
            Point leftPoint = map.get(current.leftChild);
            g.drawLine(currentX, currentY, leftPoint.x, leftPoint.y - BOX_SIZE / 2);
        }
        if(current.rightChild != null) {
            Point rightPoint = map.get(current.rightChild);
            g.drawLine(currentX, currentY, rightPoint.x, rightPoint.y - BOX_SIZE / 2);

        }
        if(current instanceof Node) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.YELLOW);
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