//import java.awt.*;
//import java.util.ArrayList;
//import java.util.Map;
//
//public class RedBlackTree extends BinarySearchTree{
//
//    private RedBlackNode root;
//    public ArrayList<RedBlackNode> rblist = new ArrayList<RedBlackNode>();
//
//    public RedBlackTree() {
//        root = null;
//    }
//
//    /****************************************************
//     * insert
//     *
//     * insert a red node
//     ***************************************************/
//    public void insert(Integer data) {
//        insert(new RedBlackNode( null, null, "RED", data));
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
//        } else {
//            //if it is not empty we must perform two steps:
//            //Step 1: Ordinary BST insert to put the node in the correct spot.
//            bstInsert(newNode);
//
//            //Step 2: fixUp
//            fixUp(newNode);
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
//    public void bstInsert(RedBlackNode newNode) {
//        rblist.clear();
//        RedBlackNode curr = root;
//        RedBlackNode prev = null;
//        int key = newNode.key; //the node value
//        //find the correct position of the node
//        while (curr != null) {
//            rblist.add(curr);
//            prev = curr;
//            if (key < curr.key)
//                curr = curr.left;
//            else
//                curr = curr.right;
//        } //end of while
//        //insert the node in the correct spot
////        newNode.parent = prev;
////        if (prev == null) {
////            prev = newNode;
////            newNode.parent = null;
////        } else
//        if (prev == null){
//
//        }
//        else if (key < prev.key)
//            prev.left = newNode;
//        else
//            prev.right = newNode;
//
//        rblist.add(newNode);
//    } //end of bstInsert
//
//
//    /*
//    * get update list
//    * */
//    public void traverAgain(RedBlackNode newNode) {
//        rblist.clear();
//        RedBlackNode curr = root;
//        RedBlackNode prev = null;
//        int key = newNode.key; //the node value
//        //find the correct position of the node
//        while (curr != null) {
//            rblist.add(curr);
//            prev = curr;
//            if (key < curr.key)
//                curr = curr.left;
//            else
//                curr = curr.right;
//        } //end of while
//        //insert the node in the correct spot
////        newNode.parent = prev;
////        if (prev == null) {
////            prev = newNode;
////            newNode.parent = null;
////        } else
////        if (prev == null) {
////
////        } else if (key < prev.key)
////            prev.left = newNode;
////        else
////            prev.right = newNode;
//
//        //rblist.add(newNode);
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
//        RedBlackNode currParent = rblist.get(size-1);
//       // System.out.println(currParent);
//        while (curr != root && !currParent.color.equals("BLACK")) {
//            //is currParent the left child of its parent?
//            //Right side
//            RedBlackNode currGrandParent = rblist.get(size-2);
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
////                    curr = currGrandParent;
////                    //currParent = curr.parent;
////                    RedBlackNode greatGrandP = findIndexInList(currGrandParent);
////                    if (greatGrandP != null){
////                        currParent = greatGrandP;
////                    }
//                } else {
//                    //case 2: we are our parent's right child
//                    if (curr == currParent.right) {
//                        curr = currParent;
//                        leftRotate(curr, 2);
//                        currParent = rblist.get(size-1);
//                        //currParent = curr;
//                    }
//                    //case 3: our sibling is a black node and we are red
//                    currParent.color = "BLACK";
//                    currGrandParent = rblist.get(size-2);
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
////                    curr = currGrandParent;
////                    //currParent = curr.parent;
////                    RedBlackNode greatGrandP = findIndexInList(currGrandParent);
////                    if (greatGrandP != null){
////                        currParent = greatGrandP;
////                    }
//
//                } else {
//                    //case 2: we are our parent's right child
//                    if (curr == currParent.left) {
//                        curr = currParent;
//                        rightRotate(curr, 2);
//                        currParent = rblist.get(size-1);
//                    }
//                    //case 3: our sibling is a black node and we are red
//                    currParent.color = "BLACK";
//                    currGrandParent = rblist.get(size-2);
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
//        RedBlackNode currGrandParent = rblist.get(size-i);
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
//        if (temp.left == null && temp.right == null && temp == root){
//            root = curr;
//        }
////            root = curr;
////        temp.parent = curr;
////        if (curr != null)
////            curr.parent = currGrandParent;
////        if (currRightLC != null)
////            currRightLC.parent = temp;
//        System.out.println(temp.right);
//        traverAgain(temp);
////        rblist.add(root);
////        rblist.add(currGrandParent);
////        rblist.add(currRight);
////        rblist.add(temp);
//        System.out.println("left "+ rblist);
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
//        RedBlackNode currGrandParent = rblist.get(size-i); //6
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
//        if (temp.left == null && temp.right == null && temp == root){
//            root = curr;
//        }
//        //reassign parent pointers.
////        if (temp.parent == null)
////            root = curr;
////        temp.parent = currLeft;
////        if (curr != null)
////            curr.parent = currGrandParent;
////        if (currLeftRC != null)
////            currLeftRC.parent = curr;
//
//
//        System.out.println(temp.right);
//        traverAgain(temp);
//        System.out.println("right "+rblist);
//    } //end of right rotate
//
//    public boolean isEmpty() {
//        return root == null;
//    } //end of isEmpty
//
//    public RedBlackNode search(int key) {
//        RedBlackNode curr = root;
//        RedBlackNode toRet = null;
//        while (curr != null && curr.key != key) {
//            if (curr.key < key) {
//                curr = curr.right;
//            } else {
//                curr = curr.left;
//            } //end of if-else
//        } //end of while
//        if (curr != null && curr.key == key)
//            toRet = curr;
//        return toRet;
//    } //end of search
//
//    public RedBlackNode getRoot() {
//        return root;
//    }//end of getRoot
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
//    public RedBlackNode findIndexInList(RedBlackNode find){
//        RedBlackNode temp = null;
//        int position = -1;
//
//        for (int i = 0; i < rblist.size(); i++) {
//            if (find == rblist.get(i)){
//                position = 1;
////                temp = rblist.get(i);
//            }
//        }
//
//       temp = rblist.get(position-1);
//
//        return temp;
//    }
//
//
//
//
//    @Override
//    protected void addNode(){
//        //
//    }
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
//    protected String toString(RedBlackNode node) {
//        if (node == null) {
//            return "";
//        }
//        return toString(node.key) + " (" + toString(node.left) + ", " +
//                toString(node.right) + ") ";
//    }
//
//    private String toString(int key) {
//        return String.valueOf(key);
//    }
//
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
//    public static void main(String[] args) {
//        RedBlackTree tree = new RedBlackTree();
//
////
////        tree.insert(10);
////        tree.insert(5);
////        tree.insert(1);
////        tree.insert(4);
////        tree.insert(6);
////        tree.insert(8);
////        tree.insert(20);
////        tree.insert(30);
////        tree.insert(40);
//
//        ArrayList<Integer>list = new ArrayList<Integer>();
//        list.add(5);
//        list.add(8);
//        list.add(20);
//        list.add(30);
//        list.add(40);
//
//        int find = 8;
//        int position = -1;
//
//        for (int i = 0; i < list.size(); i++) {
//            if (find == list.get(i)){
//                position = i-1;
//            }
//        }
//
//        if (position == -1){
//            System.out.println("NOT FOUND");
//        }
//        else {
//            System.out.println("FOUND at index "+ position+ ", number "+list.get(position));
//        }
//
////        System.out.println(list.size());
////        System.out.println(list.get(list.size()));
////        System.out.println(list.get(list.size()-2));
////        System.out.println(list.get(list.size()-3));
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
//        System.out.println(tree.rblist);
//        System.out.println(tree);
//    }
//
//}
