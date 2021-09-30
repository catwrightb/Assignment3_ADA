//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Map;
//
//public class RedBlackTreeTest extends BinarySearchTree {
//
//    protected RedBlackNodeTest rdRoot;
//    public ArrayList<RedBlackNodeTest> rblist = new ArrayList<RedBlackNodeTest>();
//
//    public RedBlackTreeTest() {
//        rdRoot = null;
//    }
//
//
//    /****************************************************
//     * insert
//     *
//     * insert a red node
//     ***************************************************/
//    public void insert(Object data) {
//        RedBlackNodeTest node = new RedBlackNodeTest(data);
//        insertNode(node);
//    }
//
//    /****************************************************
//     * insert
//     *
//     * a helper method that inserts the given node
//     ***************************************************/
//    public <T> void insertNode(RedBlackNodeTest newNode) {
//
//        if (isEmpty()) {
//            rdRoot = newNode;
//        } else {
//            //if it is not empty we must perform two steps:
//            //Step 1: Ordinary BST insert to put the node in the correct spot.
//           // bstInsert(newNode);
//            //add((Comparable) newNode.item);
//
//            //Step 2: fixUp
//            //fixUp(newNode);
//        }
//        rdRoot.color = "BLACK";
//    } //end of insert
//
//    /****************************************************
//     * bstInsert
//     *
//     * A regular binary search tree insert.
//     * Finds the correct placement off the nodes data and inserts.
//     ***************************************************/
////    public void bstInsert(RedBlackNodeTest newNode) {
////        RedBlackNodeTest curr = rdRoot;
////        RedBlackNodeTest prev = null;
////        int key = (int) newNode.item; //the node value
////        //find the correct position of the node
////        while (curr != null) {
////            prev = curr;
////            if (key < (int) curr.item)
////                curr = (RedBlackNodeTest) curr.leftChild;
////            else
////                curr = (RedBlackNodeTest) curr.rightChild;
////        } //end of while
////        //insert the node in the correct spot
////        newNode.parent = prev;
////        if (prev == null) {
////            prev = newNode;
////            newNode.parent = null;
////        } else if (key < prev.key)
////            prev.left = newNode;
////        else
////            prev.right = newNode;
////    } //end of bstInsert
//
//   /****************************************************
////     * fixUp
////     *
////     * Corrects the insertion to work for the red black tree.
////     ***************************************************/
////    public void fixUp(RedBlackNodeTest curr) {
////        RedBlackNode currParent = curr.parent;
////        while (curr != root && !currParent.color.equals("BLACK")) {
////            //is currParent the left child of its parent?
////            if (currParent == currParent.parent.left) {
////                RedBlackNode currUncle = currParent.parent.right;
////                //case 1: currUncle is red
////                if (currUncle != null && currUncle.color.equals("RED")) {
////                    currParent.color = "BLACK";
////                    currUncle.color = "BLACK";
////                    currParent.parent.color = "RED";
////                    //push curr up to it's grandparent.
////                    curr = currParent.parent;
////                    currParent = curr.parent;
////                } else {
////                    //case 2: we are our parent's right child
////                    if (curr == currParent.right) {
////                        curr = currParent;
////                        leftRotate(curr);
////                        currParent = curr.parent;
////                    }
////                    //case 3: our sibling is a black node and we are red
////                    currParent.color = "BLACK";
////                    currParent.parent.color = "RED";
////                    rightRotate(currParent.parent);
////                } //end of if-else chain
////            } else {
////                //currParent is the right child
////                RedBlackNode currUncle = currParent.parent.left;
////                //case 1: currUncle is red
////                if (currUncle != null && currUncle.color.equals("RED")) {
////                    currParent.color = "BLACK";
////                    currUncle.color = "BLACK";
////                    currParent.parent.color = "RED";
////                    //push curr up to it's grandparent.
////                    curr = currParent.parent;
////                    currParent = curr.parent;
////                } else {
////                    //case 2: we are our parent's right child
////                    if (curr == currParent.left) {
////                        curr = currParent;
////                        rightRotate(curr);
////                        currParent = curr.parent;
////                    }
////                    //case 3: our sibling is a black node and we are red
////                    currParent.color = "BLACK";
////                    currParent.parent.color = "RED";
////                    leftRotate(currParent.parent);
////                } //end of else
////            } //end of if-else
////        } //end of while
////        root.color = "BLACK";
////    }//end of fixUp
////
////    /****************************************************
////     * leftRotate
////     *
////     * perform a left rotation on the tree
////     ***************************************************/
////    public void leftRotate(RedBlackNodeTest curr) {
////        //set up the variables
////        RedBlackNode currParent = curr.parent;
////        RedBlackNode currRight = curr.right;
////        RedBlackNode currRightLC = currRight.left;
////        RedBlackNode temp = curr;
////        //transfer the right node of curr into curr's position
////        if (currParent != null && currParent.left != null && currParent.left == curr) {
////            currParent.left = currRight;
////        } else if (currParent != null && currParent.right != null && currParent.right == curr) {
////            currParent.right = currRight;
////        } //end of if
////        curr = currRight;
////        //fix new curr's children.
////        curr.left = temp;
////        temp.right = currRightLC;
////        //reassign parent pointers.
////        if (temp.parent == null)
////            root = curr;
////        temp.parent = curr;
////        if (curr != null)
////            curr.parent = currParent;
////        if (currRightLC != null)
////            currRightLC.parent = temp;
////    } //end of left rotate
////
////    /****************************************************
////     * rightRotate
////     *
////     * perform a right rotation on the tree
////     ***************************************************/
////    public void rightRotate(RedBlackNodeTest curr) {
////        //set up the variables
////        RedBlackNode currParent = curr.parent;
////        RedBlackNode currLeft = curr.left;
////        RedBlackNode currLeftRC = currLeft.right;
////        RedBlackNode temp = curr;
////        //transfer the right node of curr into curr's position
////        if (currParent != null && currParent.left != null && currParent.left == curr) {
////            currParent.left = currLeft;
////        } else if (currParent != null && currParent.right != null && currParent.right == curr) {
////            currParent.right = currLeft;
////        }
////        curr = currLeft;
////        //fix new curr's children.
////        curr.right = temp;
////        temp.left = currLeftRC;
////        //reassign parent pointers.
////        if (temp.parent == null)
////            root = curr;
////        temp.parent = currLeft;
////        if (curr != null)
////            curr.parent = currParent;
////        if (currLeftRC != null)
////            currLeftRC.parent = curr;
////    } //end of right rotate
//
//    public boolean isEmpty() {
//        return rdRoot == null;
//    } //end of isEmpty
//
////    public RedBlackNodeTest search(int key) {
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
//
//    public RedBlackNodeTest getRoot() {
//        return rdRoot;
//    }//end of getRoot
//
//    public void resetTree() {
//        rdRoot = null;
//    } //end of resetTree
//
//    public int getDepth() {
//        return this.getDepth(this.rdRoot);
//    } //end of getDepth
//
//    private int getDepth(RedBlackNodeTest node) {
//        if (node != null) {
//            int right_depth;
//            int left_depth = this.getDepth((RedBlackNodeTest) node.leftChild);
//            return left_depth > (right_depth = this.getDepth((RedBlackNodeTest) node.rightChild)) ? left_depth + 1 : right_depth + 1;
//        }
//        return 0;
//    } //end of getDepth
//
//
//
//    @Override
//    protected void nodeDiscovered(Node node){
//        if (!rblist.isEmpty()){
//            rblist.clear();
//        }
//
//
//        buildExpressionTree(node);
//
//    }
//
//    public Node buildExpressionTree( Node node){
//
//        rblist.add(new RedBlackNodeTest(node));
//
//
//        // insert left child
//        if (node.leftChild != null){
//            buildExpressionTree(node.leftChild);
//        }
//        else{
//            rblist.add(new RedBlackNodeTest());
//        }
//
//        if (node.rightChild != null){
//            // insert right child
//            buildExpressionTree(node.rightChild);
//        }
//        else {
//            rblist.add(new RedBlackNodeTest());
//        }
//
//
//
//        return node;
//
//    }
//
//    @Override
//    protected void nodeFinished(Node node){
//        //
//    }
//
////    /* toString
////     * @returns	the string representation of the tree.
////     */
////    public String toString() {
////        return toString(root);
////    }
////
////    protected String toString(RedBlackNode node) {
////        if (node == null) {
////            return "";
////        }
////        return toString(node.key) + "(" + toString(node.left) + ", " +
////                toString(node.right) + ")";
////    }
////
////    private String toString(int key) {
////        return String.valueOf(key);
////    }
//
////    public static void main(String[] args) {
////        RedBlackTree test = new RedBlackTree();
////        test.insert(3);
////        test.insert(5);
////        test.insert(10);
////        test.insert(20);
////        test.insert(12);
////        System.out.println(test);
////        test.insert(6);
////        System.out.println(test);
////
////    }
//
//    static int drawRBT(Graphics g, Node current,
//                       int x, int level, int nodeCount, Map<Node, Point> map, int BOX_SIZE) {
//
//
//        if (current.leftChild != null) {
//            nodeCount = drawRBT(g, current.leftChild, x, level + 1, nodeCount, map, BOX_SIZE);
//        }
//
//        int currentX = x + nodeCount * BOX_SIZE;
//        int currentY = level * 2 * BOX_SIZE + BOX_SIZE;
//        nodeCount++;
//        map.put(current, new Point(currentX, currentY));
//
//        if (current.rightChild != null) {
//            nodeCount = drawRBT(g, current.rightChild, x, level + 1, nodeCount, map, BOX_SIZE);
//        }
//
//        g.setColor(Color.RED);
//        if (current.leftChild != null) {
//            Point leftPoint = map.get(current.leftChild);
//            g.drawLine(currentX, currentY, leftPoint.x, leftPoint.y - BOX_SIZE / 2);
//        }
//        if (current.rightChild!= null) {
//            Point rightPoint = map.get(current.rightChild);
//            g.drawLine(currentX, currentY, rightPoint.x, rightPoint.y - BOX_SIZE / 2);
//
//        }
////        if (current instanceof RedBlackNode) {
////            g.setColor(Color.WHITE);
////        } else {
////            g.setColor(Color.YELLOW);
////        }
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
//
//    public static void main(String[] args) {
//        RedBlackTreeTest treeTest = new RedBlackTreeTest();
//        treeTest.add(5);
//        treeTest.add(2);
//        treeTest.add(8);
//        treeTest.add(10);
//        System.out.println(treeTest);
//        System.out.println(treeTest.root);
//        System.out.println(treeTest.rblist);
//
//        if (treeTest.rblist != null){
//
//        }
//    }
//}
