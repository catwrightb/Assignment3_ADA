import java.util.*;
import java.util.List;
import java.util.stream.IntStream;

public class RedBlackTreeAlt<T extends Comparable<T>> extends BinarySearchTree<T> {


    public ArrayList<Node<T>> rblist = new ArrayList<Node<T>>();
    public ArrayList<Node<T>> testlist = new ArrayList<Node<T>>();
//    protected ArrayList<Node<T>> versionRepository = new ArrayList<>();
//    protected ArrayList<Node<T>> treeRepository = new ArrayList<>();
//    protected Stack<Node<T>> visitedNodes;
    protected java.util.List<Node<T>> versionRepository;
    protected List<BinarySearchTree<T>> treeRepository;
    protected Stack<Node<T>> visitedNodes;
    Stack<Node<T>> test = new Stack<>();

    public Integer[] getVersionNos() {
        return IntStream.rangeClosed(0, this.getCurrentVersionNo()).boxed().toArray(Integer[]::new);
    }


    public int getCurrentVersionNo() {
        return versionRepository.size() - 1;
    }

    /* Gets a saved version of a tree. */
    public Node<T> getBranch(int versionNo) {
        return versionRepository.get(versionNo);
    }


    public RedBlackTreeAlt()
    {
        super();
        this.versionRepository = new ArrayList<>();
        this.versionRepository.add(this.root);
        this.treeRepository = new ArrayList<>();
        this.treeRepository.add(new BinarySearchTree<>(this.root));
        this.visitedNodes = new Stack<>();
    }

    public void printPostorder(Node<T> node)
    {
        if (node == null){
            return;
        }

        if (node.left != null){
            printPostorder((node.left));
        }

        if (node.right != null){
            printPostorder((node.right));
        }

        // now deal with the node
        Node<T> newNode = preModification(node);


        //if current node has 2 children
        if (!test.isEmpty() && newNode.right != null){
            if (test.peek().item == newNode.right.item ){
                newNode.right = test.pop();
            }

        }

        if (!test.isEmpty() && newNode.left != null){
            if (test.peek().item == newNode.left.item){
                newNode.left = test.pop();
            }

        }


        if (newNode.item == root.item){
            root = newNode;

        }
        test.add(newNode);

    }

    protected void helperForVersioning(int i){
        printPostorder(root);
        if (i == 2){
            postModification(root);
        }
        else {
           // this.root = root; // Update the current root node.
            this.visitedNodes.clear();
            this.test.clear();
        }

    }


    /****************************************************
     * insert
     *
     * insert a RBT red node
     ***************************************************/
    @Override
    protected void insertRB(T val) {
        Node<T> newNode = new Node<>(val, "RED");
        insertRBT(newNode);
    }


    /****************************************************
     * insert
     *
     * a helper method that inserts the given node
     ***************************************************/
    public void insertRBT(Node<T> newNode) {
        if (isEmpty() || root.item == newNode.item) {
            root = newNode;
            helperForVersioning(2);

        } else {
            //if it is not empty we must perform two steps:
            //Step 1: Find branch
            helperForVersioning(1);
            findBranch(newNode);

            //Step 2: fixUp
            fixUp(newNode);
            helperForVersioning(2);
        }
        root.color = "BLACK";

       // postModification(modified);
    } //end of insert

    /****************************************************
     * bstInsert
     *
     * A regular binary search tree insert.
     * Finds the correct placement off the nodes data and inserts.
     ***************************************************/
    public void findBranch(Node<T> newNode) {
        rblist.clear();
        Node<T> curr = root;
        Node<T> prev = null;
        //int key = newNode.key; //the node value
        T key = newNode.item;
        //find the correct position of the node
        while (curr != null) {

                rblist.add(curr);
                prev = curr;
                if (key.compareTo(curr.item) < 1)
                    curr = curr.left;
                else
                    curr = curr.right;


        } //end of while


        //insert the node in the correct spot
        if (prev == null){
            //blank as prev should never be null
        }
        else if (key.compareTo(prev.item) < 1)
            prev.left = newNode;
        else
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
        while (curr != null) {
            rblist.add(curr);
            prev = curr;
            if (item.compareTo(curr.item) < 1)
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
        Node rootNode = rblist.get(0);
        int size = rblist.size()-1;
        Node<T> currParent = findPreviousIndex(curr);
       // System.out.println(currParent);
        while (curr != root && !currParent.color.equals("BLACK")) {
            //is currParent the left child of its parent?
            //Right side
            Node<T> currGrandParent = findPreviousIndex(currParent);

            if (currParent == currGrandParent.left) {
                Node<T> currUncle = currGrandParent.right;
                //case 1: currUncle is red
                if (currUncle != null && currUncle.color.equals("RED")) {
                    currParent.color = "BLACK";
                    currUncle.color = "BLACK";
                    currGrandParent.color = "RED";

                    //push curr up to it's grandparent.
                    curr = currGrandParent;
                    //currParent = curr.parent;
                    Node<T> greatGrandP = findPreviousIndex(curr);
                    if (greatGrandP != null){
                        currParent = greatGrandP;
                    }

                } else {
                    //case 2: we are our parent's right child
                    if (curr == currParent.right) {
                        curr = currParent;
                        leftRotate(curr, 2);
                        currParent = findPreviousIndex(curr);
                        //currParent = curr;
                    }
                    //case 3: our sibling is a black node and we are red
                    currParent.color = "BLACK";
                    currGrandParent = findPreviousIndex(currParent);
                    currGrandParent.color = "RED";
                    if (currGrandParent == root){
                        rightRotate(currGrandParent, 2);
                    }
                    else {
                        rightRotate(currGrandParent, 3);
                    }

                } //end of if-else chain
            } else {
                //currParent is the right child
                //left side
                Node<T> currUncle = currGrandParent.left;
                //case 1: currUncle is red
                if (currUncle != null && currUncle.color.equals("RED")) {
                    currParent.color = "BLACK";
                    currUncle.color = "BLACK";
                    currGrandParent.color = "RED";

                    //push curr up to it's grandparent.
                    //curr = currParent.parent;
                    curr = currGrandParent;
                    //currParent = curr.parent;
                    Node<T> greatGrandP = findPreviousIndex(curr);
                    if (greatGrandP != null){
                        currParent = greatGrandP;
                    }

                } else {
                    //case 2: we are our parent's right child
                    if (curr == currParent.left) {
                        curr = currParent;
                        rightRotate(curr, 2);
                        currParent = findPreviousIndex(curr);
                    }
                    //case 3: our sibling is a black node and we are red
                    currParent.color = "BLACK";
                    currGrandParent = findPreviousIndex(currParent);
                    currGrandParent.color = "RED";
                    if (currGrandParent == root){
                        leftRotate(currGrandParent, 2);
                    }
                    else {
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
        int size = rblist.size()-1;
        Node<T> currGrandParent = findPreviousIndex(curr);
        rblist.clear();
        Node<T> currRight = curr.right;
        Node<T> currRightLC = currRight.left;
        Node<T> temp = curr;
        //transfer the right node of curr into curr's position
        if (currGrandParent != null && currGrandParent.left != null && currGrandParent.left == curr) {
            currGrandParent.left = currRight;
        } else if (currGrandParent != null && currGrandParent.right != null && currGrandParent.right == curr) {
            currGrandParent.right = currRight;
        } //end of if
        curr = currRight;
        //fix new curr's children.
        curr.left = temp;
        temp.right = currRightLC;

        //change root if present root has not children and the temp is the root
        if (temp == root){
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
        int size = rblist.size()-1;
        Node<T> currGrandParent = findPreviousIndex(curr); //6
        rblist.clear();
        Node<T> currLeft = curr.left;
        Node<T> currLeftRC = currLeft.right;
        Node<T> temp = curr;
        //transfer the right node of curr into curr's position
        if (currGrandParent != null && currGrandParent.left != null && currGrandParent.left == curr) {
            currGrandParent.left = currLeft;
        } else if (currGrandParent != null && currGrandParent.right != null && currGrandParent.right == curr) {
            currGrandParent.right = currLeft;
        }
        curr = currLeft;
        //fix new curr's children.
        curr.right = temp;
        temp.left = currLeftRC;

        //change root if present root has not children and the temp is the root
        if (temp == root){
            root = curr;
        }

        traverAgain(temp);

    } //end of right rotate


    public void setRoot(Node<T> root) {
        resetTree();
        this.root = root;
    }

    public Node<T> getRoot() {
        return root;
    }


    public void resetTree() {
        root = null;
    } //end of resetTree

    public int getDepth() {
        return this.getDepth(this.root);
    } //end of getDepth

    private int getDepth(Node<T> node) {
        if (node != null) {
            int right_depth;
            int left_depth = this.getDepth(node.left);
            return left_depth > (right_depth = this.getDepth(node.right)) ? left_depth + 1 : right_depth + 1;
        }
        return 0;
    } //end of getDepth



    public Node<T> findPreviousIndex(Node<T> find){
        Node<T> temp = find;
        if (find != root) {

            int position = -1;

            for (int i = 0; i < rblist.size(); i++) {
                if (find == rblist.get(i)) {
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

    @Override
    protected void postModification(Node<T> modifiedRoot) {
        this.versionRepository.add(modifiedRoot);
        this.treeRepository.add(new BinarySearchTree<>(modifiedRoot));
        this.root = modifiedRoot; // Update the current root node.
        this.visitedNodes.clear();
        this.test.clear();
    }


    protected Node<T> preModification(Node<T> node) {
        return node.deepClone(node); // Pass the reference of the cloned Node back to caller.
    }

    @Override
    public void clear() {
        this.root = new Node<>(); // old root and children nodes will be garbage collected.
        versionRepository.clear();
        versionRepository.add(this.root);
        visitedNodes.clear();
    }

    /* toString
     * @returns	the string representation of the tree.
     */
    public String toString() {
        return toString(root);
    }

    protected String toString(Node<T> node) {
        if (node == null) {
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
    @Override
    public void remove(T find){
        RedBlackTreeAlt tree2 = new RedBlackTreeAlt();
        this.addToList();

        for (int i = 0; i < testlist.size(); i++) {
            Node<T> node = testlist.get(i);

            if (find != node.item) {
               tree2.insertRB(node.item);
            }
        }


        root = tree2.root;
        this.postModification(root);


    }

    public void addToList() {
        addToList(root);
    }

    protected String addToList(Node<T> node) {
        if (node == null) {
            return "";
        }
        return addToList(node.item) + " (" + addToList(node.left) + ", " +
                addToList(node.right) + ") ";
    }

    private String addToList(T item) {
        testlist.add(new Node<T>(item));
        return String.valueOf(item);
    }


//    public static void main(String[] args) {
//        RedBlackTreeAlt tree = new RedBlackTreeAlt();
//
////TESTCASE
////        tree.insertRB(10);
////        tree.insertRB(5);
////        tree.insertRB(1);
////        tree.insertRB(2);
//
//
////        tree.insertRB(4);
////        tree.insertRB(6);
////        tree.insertRB(8);
////        tree.insertRB(20);
////        tree.insertRB(30);
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
//        tree.insertRB(10);
//        tree.insertRB(13);
//        tree.insertRB(8);
//        tree.insertRB(6);
//        tree.insertRB(7);
//        tree.insertRB(9);
//        tree.insertRB(11);
//        tree.insertRB(12);
//        tree.insertRB(16);
//        tree.insertRB(18);
//
//
//
//        tree.remove(8);
//
////        tree.insertRB(40);
//        for(int i = 0; i < tree.versionRepository.size(); i++) {
//            System.out.println("Tree["+i+"]: " + (tree.treeRepository.get(i)));
//            //tree.visitedNodes.forEach((System.out::println));
//        }
//      //  System.out.println("ORIGINAL TREE : " +tree);
//
//
//    }

}
