//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Map;
//import java.util.Stack;
//
//public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {
//
//
//    private RedBlackNode<T> root;
//    public ArrayList<RedBlackNode> rblist = new ArrayList<RedBlackNode>();
//    public ArrayList<RedBlackNode> testlist = new ArrayList<RedBlackNode>();
//    protected ArrayList<RedBlackNode> versionRepository = new ArrayList<>();
//    protected ArrayList<RedBlackTree> treeRepository = new ArrayList<>();
//    protected Stack<RedBlackNode> visitedNodes;
//    int numberOfNodes;
////
////    public void addToMemory(RedBlackTree oldTree){
////        RedBlackTree tree = new RedBlackTree();
////        tree = oldTree;
////        root = tree.getRoot();
////        //treeRepository.add(tree);
////        versionRepository.add(tree.root);
////
////    }
//
//
//
//    public int getCurrentVersionNo() {
//        return versionRepository.size() - 1;
//    }
//
//    /* Gets a saved version of a tree. */
//    public RedBlackNode getBranch(int versionNo) {
//        return versionRepository.get(versionNo);
//    }
//
//    public RedBlackTree() {
//        root = null;
//
//    }
//
//
//
//    /****************************************************
//     * insert
//     *
//     * insert a red node
//     ***************************************************/
//    public void insert(T data) {
//        insert(new RedBlackNode(data));
//    }
//
//
//    /****************************************************
//     * insert
//     *
//     * a helper method that inserts the given node
//     ***************************************************/
//    public void insert(RedBlackNode newNode) {
//        if (isEmpty()) {
//            root = newNode;
//
//        } else {
//            //if it is not empty we must perform two steps:
//            //Step 1: BST insert to put the node in the correct spot.
//
//            bstInsert(newNode);
//
//            //Step 2: fixUp
//            fixUp(newNode);
//
//        }
//        root.color = "BLACK";
//    } //end of insert
//
//    /****************************************************
//     * bstInsert
//     *
//     * A regular binary search tree insert.
//     * Finds the correct placement off the nodes data and inserts.
//     ***************************************************/
//    public void bstInsert(RedBlackNode<T> newNode) {
//        rblist.clear();
//        RedBlackNode<T> curr = root;
//        RedBlackNode<T> prev = null;
//        //int key = newNode.key; //the node value
//        T key = newNode.key;
//        //find the correct position of the node
//        while (curr != null) {
//
//                rblist.add(curr);
//                prev = curr;
//                if (key.compareTo(curr.key) < 1)
//                    curr = curr.left;
//                else
//                    curr = curr.right;
//
//
//        } //end of while
//
//
//        //insert the node in the correct spot
//        if (prev == null){
//            //blank as prev should never be null
//        }
//        else if (key.compareTo(prev.key) < 1)
//            prev.left = newNode;
//        else
//            prev.right = newNode;
//
//        rblist.add(newNode);
//
//    } //end of bstInsert
//
//
//    /*
//    * get update list by only traversing path from root to newNode
//    * */
//    public void traverAgain(RedBlackNode<T> newNode) {
//        rblist.clear();
//        RedBlackNode<T> curr = root;
//        RedBlackNode<T> prev = null;
//        T key = newNode.key; //the node value
//        //find the correct position of the node
//        while (curr != null) {
//            rblist.add(curr);
//            prev = curr;
//            if (key.compareTo(curr.key) < 1)
//                curr = curr.left;
//            else
//                curr = curr.right;
//        } //end of while
//    }
//
//    /****************************************************
//     * fixUp
//     *
//     * Corrects the insertion to work for the red black tree.
//     ***************************************************/
//    public void fixUp(RedBlackNode curr) {
//        RedBlackNode rootNode = rblist.get(0);
//        int size = rblist.size()-1;
//        RedBlackNode currParent = findPreviousIndex(curr);
//       // System.out.println(currParent);
//        while (curr != root && !currParent.color.equals("BLACK")) {
//            //is currParent the left child of its parent?
//            //Right side
//            RedBlackNode currGrandParent = findPreviousIndex(currParent);
//
//            if (currParent == currGrandParent.left) {
//                RedBlackNode currUncle = currGrandParent.right;
//                //case 1: currUncle is red
//                if (currUncle != null && currUncle.color.equals("RED")) {
//                    currParent.color = "BLACK";
//                    currUncle.color = "BLACK";
//                    currGrandParent.color = "RED";
//
//                    //push curr up to it's grandparent.
//                    curr = currGrandParent;
//                    //currParent = curr.parent;
//                    RedBlackNode greatGrandP = findPreviousIndex(curr);
//                    if (greatGrandP != null){
//                        currParent = greatGrandP;
//                    }
//
//                } else {
//                    //case 2: we are our parent's right child
//                    if (curr == currParent.right) {
//                        curr = currParent;
//                        leftRotate(curr, 2);
//                        currParent = findPreviousIndex(curr);
//                        //currParent = curr;
//                    }
//                    //case 3: our sibling is a black node and we are red
//                    currParent.color = "BLACK";
//                    currGrandParent = findPreviousIndex(currParent);
//                    currGrandParent.color = "RED";
//                    if (currGrandParent == root){
//                        rightRotate(currGrandParent, 2);
//                    }
//                    else {
//                        rightRotate(currGrandParent, 3);
//                    }
//
//                } //end of if-else chain
//            } else {
//                //currParent is the right child
//                //left side
//                RedBlackNode currUncle = currGrandParent.left;
//                //case 1: currUncle is red
//                if (currUncle != null && currUncle.color.equals("RED")) {
//                    currParent.color = "BLACK";
//                    currUncle.color = "BLACK";
//                    currGrandParent.color = "RED";
//
//                    //push curr up to it's grandparent.
//                    //curr = currParent.parent;
//                    curr = currGrandParent;
//                    //currParent = curr.parent;
//                    RedBlackNode greatGrandP = findPreviousIndex(curr);
//                    if (greatGrandP != null){
//                        currParent = greatGrandP;
//                    }
//
//                } else {
//                    //case 2: we are our parent's right child
//                    if (curr == currParent.left) {
//                        curr = currParent;
//                        rightRotate(curr, 2);
//                        currParent = findPreviousIndex(curr);
//                    }
//                    //case 3: our sibling is a black node and we are red
//                    currParent.color = "BLACK";
//                    currGrandParent = findPreviousIndex(currParent);
//                    currGrandParent.color = "RED";
//                    if (currGrandParent == root){
//                        leftRotate(currGrandParent, 2);
//                    }
//                    else {
//                        leftRotate(currGrandParent, 3);
//                    }
//
//                } //end of else
//            } //end of if-else
//        } //end of while
//        root.color = "BLACK";
//    }//end of fixUp
//
//
//    /****************************************************
//     * leftRotate
//     *
//     * perform a left rotation on the tree
//     ***************************************************/
//    public void leftRotate(RedBlackNode curr, int i) {
//        //set up the variables
//        int size = rblist.size()-1;
//        RedBlackNode currGrandParent = findPreviousIndex(curr);
//        rblist.clear();
//        RedBlackNode currRight = curr.right;
//        RedBlackNode currRightLC = currRight.left;
//        RedBlackNode temp = curr;
//        //transfer the right node of curr into curr's position
//        if (currGrandParent != null && currGrandParent.left != null && currGrandParent.left == curr) {
//            currGrandParent.left = currRight;
//        } else if (currGrandParent != null && currGrandParent.right != null && currGrandParent.right == curr) {
//            currGrandParent.right = currRight;
//        } //end of if
//        curr = currRight;
//        //fix new curr's children.
//        curr.left = temp;
//        temp.right = currRightLC;
//
//        //change root if present root has not children and the temp is the root
//        if (temp == root){
//            root = curr;
//        }
//
//        traverAgain(temp);
//
//    } //end of left rotate
//
//
//    /****************************************************
//     * rightRotate
//     *
//     * perform a right rotation on the tree
//     ***************************************************/
//    public void rightRotate(RedBlackNode curr, int i) {
//        //set up the variables
//        int size = rblist.size()-1;
//        RedBlackNode currGrandParent = findPreviousIndex(curr); //6
//        rblist.clear();
//        RedBlackNode currLeft = curr.left;
//        RedBlackNode currLeftRC = currLeft.right;
//        RedBlackNode temp = curr;
//        //transfer the right node of curr into curr's position
//        if (currGrandParent != null && currGrandParent.left != null && currGrandParent.left == curr) {
//            currGrandParent.left = currLeft;
//        } else if (currGrandParent != null && currGrandParent.right != null && currGrandParent.right == curr) {
//            currGrandParent.right = currLeft;
//        }
//        curr = currLeft;
//        //fix new curr's children.
//        curr.right = temp;
//        temp.left = currLeftRC;
//
//        //change root if present root has not children and the temp is the root
//        if (temp == root){
//            root = curr;
//        }
//        //reassign parent pointers.
//
//        traverAgain(temp);
//
//    } //end of right rotate
//
//    public boolean isEmpty() {
//        return root == null;
//    } //end of isEmpty
//
//    public void setRoot(RedBlackNode root) {
//        resetTree();
//        this.root = root;
//    }
//
////    public RedBlackNode search(int key) {
////        RedBlackNode curr = root;
////        RedBlackNode toRet = null;
////        while (curr != null && curr.key != key) {
////            if (curr.key < key) {
////                curr = curr.right;
////            } else {
////                curr = curr.left;
////            } //end of if-else
////        } //end of while
////        if (curr != null && curr.key == key)
////            toRet = curr;
////        return toRet;
////    } //end of search
////
////    public RedBlackNode getRoot() {
////        return root;
////    }//end of getRoot
//
//    public void resetTree() {
//        root = null;
//    } //end of resetTree
//
//    public int getDepth() {
//        return this.getDepth(this.root);
//    } //end of getDepth
//
//    private int getDepth(RedBlackNode node) {
//        if (node != null) {
//            int right_depth;
//            int left_depth = this.getDepth(node.left);
//            return left_depth > (right_depth = this.getDepth(node.right)) ? left_depth + 1 : right_depth + 1;
//        }
//        return 0;
//    } //end of getDepth
//
//
//    public RedBlackNode findPreviousIndex(RedBlackNode find){
//        RedBlackNode temp = find;
//        if (find != root) {
//
//            int position = -1;
//
//            for (int i = 0; i < rblist.size(); i++) {
//                if (find == rblist.get(i)) {
//                    position = i;
//                }
//            }
//
//            temp = rblist.get(position - 1);
//        }
//        return temp;
//    }
//
//
//
//
//    @Override
//    protected void nodeFinished(Node node){
//        //
//    }
//
//    /* toString
//     * @returns	the string representation of the tree.
//     */
//    public String toString() {
//        return toString(root);
//    }
//
//    protected String toString(RedBlackNode<T> node) {
//        if (node == null) {
//            return "";
//        }
//        return toString(node.item) + " (" + toString(node.left) + ", " +
//                toString(node.right) + ") ";
//    }
//
//
//
//    private String toString(T key) {
//        return String.valueOf(key);
//    }
//
//
//    /*
//    * Remove methods
//    *
//    * */
//    public void remove(T find){
//        RedBlackTree tree2 = new RedBlackTree();
//        this.addToList();
//
//        for (RedBlackNode redBlackNode : testlist) {
//
//            if (find != redBlackNode.key) {
//                tree2.insert(redBlackNode.key);
//            }
//        }
//
//        this.setRoot(tree2.root);
//
//    }
//
//    public void addToList() {
//        addToList(root);
//    }
//
//    protected String addToList(RedBlackNode<T> node) {
//        if (node == null) {
//            return "";
//        }
//        return addToList(node.key) + " (" + addToList(node.left) + ", " +
//                addToList(node.right) + ") ";
//    }
//
//    private String addToList(T key) {
//        testlist.add(new RedBlackNode(key));
//        return String.valueOf(key);
//    }
//
//    /*
//    * draws the Red black tree
//    * */
//    static int drawRBT(Graphics g, RedBlackNode current,
//                       int x, int level, int nodeCount, Map<RedBlackNode, Point> map, int BOX_SIZE) {
//
//
//        if (current.left != null) {
//            nodeCount = drawRBT(g, current.left, x, level + 1, nodeCount, map, BOX_SIZE);
//        }
//
//        int currentX = x + nodeCount * BOX_SIZE;
//        int currentY = level * 2 * BOX_SIZE + BOX_SIZE;
//        nodeCount++;
//        map.put(current, new Point(currentX, currentY));
//
//        if (current.right != null) {
//            nodeCount = drawRBT(g, current.right, x, level + 1, nodeCount, map, BOX_SIZE);
//        }
//
//        g.setColor(current.getColor());
//        if (current.left != null) {
//            Point leftPoint = map.get(current.left);
//            g.drawLine(currentX, currentY, leftPoint.x, leftPoint.y - BOX_SIZE / 2);
//        }
//        if (current.right!= null) {
//            Point rightPoint = map.get(current.right);
//            g.drawLine(currentX, currentY, rightPoint.x, rightPoint.y - BOX_SIZE / 2);
//
//        }
//
//        Point currentPoint = map.get(current);
//        g.fillRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);
//        g.setColor(Color.BLACK);
//        g.drawRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);
//        Font f = new Font("courier new", Font.BOLD, 16);
//        g.setFont(f);
//        g.drawString(current.toString(), currentPoint.x-3, currentPoint.y);
//        return nodeCount;
//
//    }
//
//    public static void main(String[] args) {
//        RedBlackTree tree = new RedBlackTree();
//
////TESTCASE
//        tree.insert(10);
//
//        tree.insert(5);
//
//        tree.insert(1);
//
//        tree.insert(4);
//        tree.insert(6);
//        tree.insert(8);
//        tree.insert(20);
//        tree.insert(30);
//       // tree.insert(40);
//
//
////Test case
////        tree.insert(20);
////        tree.insert(10);
////        tree.insert(30);
////        tree.insert(5);
////        tree.insert(4);
////        tree.insert(3);
////        tree.insert(2);
////        tree.insert(1);
////        tree.insert(40);
////        tree.insert(50);
////        tree.insert(60);
////        tree.insert(70);
////        tree.insert(80);
//
//
//        ////test case 1
////        tree.insert(6);
////        tree.insert(12);
////        tree.insert(5);
////        tree.insert(11);
////        tree.insert(15);
////        tree.insert(13);
////        tree.insert(14);
////        tree.insert(16);
//
//////testcase 2
////            tree.insert(10);
////            tree.insert(8);
////            tree.insert(13);
////            tree.insert(6);
////            tree.insert(7);
//
//
//        ////test case 3
////        tree.insert(10);
////        tree.insert(13);
////        tree.insert(8);
////        tree.insert(6);
////        tree.insert(7);
////        tree.insert(9);
////        tree.insert(11);
////        tree.insert(12);
////        tree.insert(16);
////        tree.insert(18);
//
//
//
//        //tree.remove(8);
//        System.out.println("ORIGINAL TREE : " +tree);
//        tree.insert(40);
//        System.out.println("ORIGINAL TREE : " +tree);
//
//
//    }
//
//}
