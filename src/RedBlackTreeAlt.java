import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.util.ArrayList;
import java.util.Map;
import java.util.Stack;

public class RedBlackTreeAlt<T extends Comparable<T>> extends BinarySearchTree<T> {

    protected Node<T> root;
    public ArrayList<Node<T>> rblist = new ArrayList<>();
    public ArrayList<Node<T>> testlist = new ArrayList<>();
    protected ArrayList<Node<T>> versionRepository = new ArrayList<>();
    int numberOfNodes;
    //protected ArrayList<Node<T>> treeRepository = new ArrayList<>();
//    protected Stack<Node<T>> visitedNodes;
//
//    public void addToMemory(RedBlackTree oldTree){
//        RedBlackTree tree = new RedBlackTree();
//        tree = oldTree;
//        root = tree.getRoot();
//        //treeRepository.add(tree);
//        versionRepository.add(tree.root);
//
//    }

    public RedBlackTreeAlt() {
        root = null;
    }

    public int getCurrentVersionNo() {
        return (versionRepository.size() == 0 ? 0 : versionRepository.size() - 1);
    }

    /* Gets a saved version of a tree. */
    public Node<T> getBranch(int versionNo) {
        return versionRepository.get(versionNo);
    }


    /****************************************************
     * insert
     *
     * insert a red node
     ***************************************************/
    public void insertRB(T data) {
        Node<T> newNode = new Node<>(data);
        nodeTraversed(newNode);
        insertRB(newNode);
    }


    /****************************************************
     * insert
     *
     * a helper method that inserts the given node
     ***************************************************/
    public void insertRB(Node<T> newNode) {
        if(isEmpty()) {
            root = newNode;
        } else {
            //if it is not empty we must perform two steps:
            //Step 1: BST insert to put the node in the correct spot.
            bstInsert(newNode);

            //Step 2: fixUp
            fixUp(newNode);
        }
        root.color = "BLACK";
    } //end of insert

    /****************************************************
     * bstInsert
     *
     * A regular binary search tree insert.
     * Finds the correct placement off the nodes data and inserts.
     ***************************************************/
    public void bstInsert(Node<T> newNode) {
        rblist.clear();

        // Deep copies the root at this point in time to keep all modifications made before.
        root = preModification(root); // TODO: Check Persistence Functionality (Amos)

        Node<T> prev = null;
        Node<T> curr = root;
        //int key = newNode.key; //the node value
        T key = newNode.item;
        //find the correct position of the node
        while(curr != null) {
            rblist.add(curr);
            prev = curr;
            if(key.compareTo(curr.item) < 1)
                curr = curr.left;
            else
                curr = curr.right;
//            if(key.compareTo(curr.item) < 1) { // TODO: Check Comparable functionality (Amos).
//                curr = preModification(curr.left); // Go left.
////                prev.left = curr;
//            } else {
//                curr = preModification(curr.right); // Go right.
//                prev.right = curr;
//            }
        } //end of while


        //insert the node in the correct spot
        if(prev == null) {
            //blank as prev should never be null
//            root = newNode; // TODO: Check the functionality of updated root node (Amos).
        } else if(key.compareTo(prev.item) < 1) {
            prev.left = newNode;
        } else
            prev.right = newNode;

        rblist.add(newNode);

    } //end of bstInsert


    /*
     * get update list by only traversing path from root to newNode
     * */
    public void traverAgain(Node<T> newNode) {
        rblist.clear();
        Node<T> curr = root;
        Node<T> prev = null;
        T item = newNode.item; //the node value
        //find the correct position of the node
        while(curr != null) {
            rblist.add(curr);
            prev = curr;
            if(item.compareTo(curr.item) < 1)
                curr = curr.left;
            else
                curr = curr.right;
        } //end of while
    }

    /****************************************************
     * fixUp
     *
     * Corrects the insertion to work for the red black tree.
     ***************************************************/
    public void fixUp(Node<T> curr) {
        Node<T> rootNode = rblist.get(0);
        int size = rblist.size() - 1;
        Node<T> currParent = findPreviousIndex(curr);
//        Node<T> currParent = preModification(findPreviousIndex(curr)); // TODO: Check clone results. (Amos)
        // System.out.println(currParent);
        while(curr != root && !currParent.color.equals("BLACK")) {
            //is currParent the left child of its parent?
            //Right side
//            Node<T> currGrandParent = preModification(findPreviousIndex(currParent)); // TODO: Check clone results. (Amos)
            Node<T> currGrandParent =  findPreviousIndex(currParent);

            if(currParent == currGrandParent.left) {
                Node<T> currUncle = currGrandParent.right;
                //case 1: currUncle is red
                if(currUncle != null && currUncle.color.equals("RED")) {
                    currParent.color = "BLACK";
                    currUncle.color = "BLACK";
                    currGrandParent.color = "RED";

                    //push curr up to it's grandparent.
                    curr = currGrandParent;
                    //currParent = curr.parent;
                    Node<T> greatGrandP = findPreviousIndex(curr);
                    if(greatGrandP != null) {
                        currParent = greatGrandP;
                    }
                } else {
                    //case 2: we are our parent's right child
                    if(curr == currParent.right) {
                        curr = currParent;
                        leftRotate(curr, 2);
                        currParent = findPreviousIndex(curr);
                        //currParent = curr;
                    }
                    //case 3: our sibling is a black node and we are red
                    currParent.color = "BLACK";
                    currGrandParent = findPreviousIndex(currParent);
                    currGrandParent.color = "RED";
                    if(currGrandParent == root) {
                        rightRotate(currGrandParent, 2);
                    } else {
                        rightRotate(currGrandParent, 3);
                    }

                } //end of if-else chain
            } else {
                //currParent is the right child
                //left side
                Node<T> currUncle = currGrandParent.left;
                //case 1: currUncle is red
                if(currUncle != null && currUncle.color.equals("RED")) {
                    currParent.color = "BLACK";
                    currUncle.color = "BLACK";
                    currGrandParent.color = "RED";

                    //push curr up to it's grandparent.
                    //curr = currParent.parent;
                    curr = currGrandParent;
                    //currParent = curr.parent;
                    Node<T> greatGrandP = findPreviousIndex(curr);
                    if(greatGrandP != null) {
                        currParent = greatGrandP;
                    }

                } else {
                    //case 2: we are our parent's right child
                    if(curr == currParent.left) {
                        curr = currParent;
                        rightRotate(curr, 2);
                        currParent = findPreviousIndex(curr);
                    }
                    //case 3: our sibling is a black node and we are red
                    currParent.color = "BLACK";
                    currGrandParent = findPreviousIndex(currParent);
                    currGrandParent.color = "RED";
                    if(currGrandParent == root) {
                        leftRotate(currGrandParent, 2);
                    } else {
                        leftRotate(currGrandParent, 3);
                    }

                } //end of else
            } //end of if-else
        } //end of while
        root.color = "BLACK";
    }//end of fixUp


    /****************************************************
     * leftRotate
     *
     * perform a left rotation on the tree
     ***************************************************/
    public void leftRotate(Node<T> curr, int i) {
        //set up the variables
        int size = rblist.size() - 1;
        Node<T> currGrandParent = findPreviousIndex(curr);
        rblist.clear();
        Node<T> currRight = curr.right;
        Node<T> currRightLC = currRight.left;
        Node<T> temp = curr;
        //transfer the right node of curr into curr's position
        if(currGrandParent != null && currGrandParent.left != null && currGrandParent.left == curr) {
            currGrandParent.left = currRight;
        } else if(currGrandParent != null && currGrandParent.right != null && currGrandParent.right == curr) {
            currGrandParent.right = currRight;
        } //end of if
        curr = currRight;
        //fix new curr's children.
        curr.left = temp;
        temp.right = currRightLC;

        //change root if present root has not children and the temp is the root
        if(temp == root) {
            root = curr;
        }

        traverAgain(temp);

    } //end of left rotate


    /****************************************************
     * rightRotate
     *
     * perform a right rotation on the tree
     ***************************************************/
    public void rightRotate(Node<T> curr, int i) {
        //set up the variables
        int size = rblist.size() - 1;
        Node<T> currGrandParent = findPreviousIndex(curr); //6
        rblist.clear();
        Node<T> currLeft = curr.left;
        Node<T> currLeftRC = currLeft.right;
        Node<T> temp = curr;
        //transfer the right node of curr into curr's position
        if(currGrandParent != null && currGrandParent.left != null && currGrandParent.left == curr) {
            currGrandParent.left = currLeft;
        } else if(currGrandParent != null && currGrandParent.right != null && currGrandParent.right == curr) {
            currGrandParent.right = currLeft;
        }
        curr = currLeft;
        //fix new curr's children.
        curr.right = temp;
        temp.left = currLeftRC;

        //change root if present root has not children and the temp is the root
        if(temp == root) {
            root = curr;
        }
        //reassign parent pointers.

        traverAgain(temp);

    } //end of right rotate

    public boolean isEmpty() {
        return root == null;
    } //end of isEmpty

    public void setRoot(Node<T> root) {
        resetTree();
        this.root = root;
    }

    public Node<T> getRoot() {
        return root;
    }

    //    public RedBlackNode search(int item) {
//        RedBlackNode curr = root;
//        RedBlackNode toRet = null;
//        while (curr != null && curr.item != item) {
//            if (curr.item < item) {
//                curr = curr.right;
//            } else {
//                curr = curr.left;
//            } //end of if-else
//        } //end of while
//        if (curr != null && curr.item == item)
//            toRet = curr;
//        return toRet;
//    } //end of search
//
//    public RedBlackNode getRoot() {
//        return root;
//    }//end of getRoot

    public void resetTree() {
        root = null;
    } //end of resetTree

    public int getDepth() {
        return this.getDepth(this.root);
    } //end of getDepth

    private int getDepth(Node<T> node) {
        if(node != null) {
            int right_depth;
            int left_depth = this.getDepth(node.left);
            return left_depth > (right_depth = this.getDepth(node.right)) ? left_depth + 1 : right_depth + 1;
        }
        return 0;
    } //end of getDepth


    public Node<T> findPreviousIndex(Node<T> find) {
        Node<T> temp = find;
        if(find != root) {

            int position = -1;

            for(int i = 0; i < rblist.size(); i++) {
                if(find == rblist.get(i)) {
                    position = i;
                }
            }

            temp = rblist.get(position - 1);
        }
        return temp;
    }



    @Override
    protected void nodeTraversed(Node<T> currentNode) {
        currentNode.color = "RED";
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
        Node<T> updateNode = null;
        if(node != null && node.item != null) {
            updateNode = node.deepClone(node); // Copy node.
        }
        return updateNode; // Pass the reference of the cloned Node back to caller.
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
    }

    @Override
    protected void nodeFinished(Node<T> node) {
        //
    }

    /* toString
     * @returns	the string representation of the tree.
     */
    public String toString() {
        return toString(root);
    }

    protected String toString(Node<T> node) {
        if(node == null) {
            return "";
        }
        return toString(node.item) + " (" + toString(node.left) + ", " +
                toString(node.right) + ") ";
    }

    private String toString(T item) {
        return String.valueOf(item);
    }


    /*
     * Remove methods
     *
     * */
    public void remove(T find) {
        RedBlackTreeAlt<T> tree2 = new RedBlackTreeAlt<>();
        this.addToList();

        for(Node<T> node : testlist) {

            if(find != node.item) {
                tree2.insertRB(node.item);
            }
        }

        setRoot(tree2.root);

    }

    public void addToList() {
        addToList(root);
    }

    protected String addToList(Node<T> node) {
        if(node == null) {
            return "";
        }
        return addToList(node.item) + " (" + addToList(node.left) + ", " +
                addToList(node.right) + ") ";
    }

    private String addToList(T item) {
        testlist.add(new Node<T>(item));
        return String.valueOf(item);
    }

    /*
     * draws the Red black tree
     * */
    static int drawRBT(Graphics g, Node<?> current,
                       int x, int level, int nodeCount, Map<Node<?>, Point> map, int BOX_SIZE) {


        if(current.left != null) {
            nodeCount = drawRBT(g, current.left, x, level + 1, nodeCount, map, BOX_SIZE);
        }

        int currentX = x + nodeCount * BOX_SIZE;
        int currentY = level * 2 * BOX_SIZE + BOX_SIZE;
        nodeCount++;
        map.put(current, new Point(currentX, currentY));

        if(current.right != null) {
            nodeCount = drawRBT(g, current.right, x, level + 1, nodeCount, map, BOX_SIZE);
        }

        g.setColor(current.getColor());
        if(current.left != null) {
            Point leftPoint = map.get(current.left);
            g.drawLine(currentX, currentY, leftPoint.x, leftPoint.y - BOX_SIZE / 2);
        }
        if(current.right != null) {
            Point rightPoint = map.get(current.right);
            g.drawLine(currentX, currentY, rightPoint.x, rightPoint.y - BOX_SIZE / 2);

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

    public static void main(String[] args) {
        RedBlackTreeAlt<Integer> tree = new RedBlackTreeAlt<>();

//TESTCASE
        tree.insertRB(10);

        tree.insertRB(5);

        tree.insertRB(1);

        tree.insertRB(4);
        tree.insertRB(6);
        tree.insertRB(8);
        tree.insertRB(20);
        tree.insertRB(30);
        // tree.insert(40);


//Test case
//        tree.insert(20);
//        tree.insert(10);
//        tree.insert(30);
//        tree.insert(5);
//        tree.insert(4);
//        tree.insert(3);
//        tree.insert(2);
//        tree.insert(1);
//        tree.insert(40);
//        tree.insert(50);
//        tree.insert(60);
//        tree.insert(70);
//        tree.insert(80);


        ////test case 1
//        tree.insert(6);
//        tree.insert(12);
//        tree.insert(5);
//        tree.insert(11);
//        tree.insert(15);
//        tree.insert(13);
//        tree.insert(14);
//        tree.insert(16);

////testcase 2
//            tree.insert(10);
//            tree.insert(8);
//            tree.insert(13);
//            tree.insert(6);
//            tree.insert(7);


        ////test case 3
//        tree.insert(10);
//        tree.insert(13);
//        tree.insert(8);
//        tree.insert(6);
//        tree.insert(7);
//        tree.insert(9);
//        tree.insert(11);
//        tree.insert(12);
//        tree.insert(16);
//        tree.insert(18);


        //tree.remove(8);
        System.out.println("ORIGINAL TREE : " + tree);
        tree.insertRB(40);
        System.out.println("ORIGINAL TREE : " + tree);
        System.out.println(TreeBuilder.visualise(tree.getRoot()));
    }

}
