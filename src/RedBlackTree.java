import java.util.*;
import java.util.stream.IntStream;

public class RedBlackTree<T extends Comparable<T>> extends BinarySearchTree<T> {

    private RedBlackNode<T> root;
    protected List<RedBlackNode<T>> versionRepository;
    //    protected List<BinarySearchTree<T>> treeRepository;
//    protected Stack<RedBlackNode<T>> visitedNodes;
    public ArrayList<RedBlackNode<T>> rblist = new ArrayList<>();

    public RedBlackTree() {
        root = null;
    }

//    public RedBlackTree() {
//        this.root = new RedBlackNode<>();
//        this.versionRepository = new ArrayList<>();
//        this.versionRepository.add(this.root);
////        this.treeRepository = new ArrayList<>();
////        this.treeRepository.add(new BinarySearchTree<>(this.root));
//        this.visitedNodes = new Stack<>();
//    }

    public int getCurrentVersionNo() {
        return versionRepository.size() - 1;
    }

    public Integer[] getVersionNos() {
        return IntStream.rangeClosed(0, this.getCurrentVersionNo()).boxed().toArray(Integer[]::new);
    }

    /* Gets a saved version of a tree. */
    public RedBlackNode<T> getBranch(int versionNo) {
        return versionRepository.get(versionNo);
    }

    /* removes all elements from the collection */
    @Override
    public void clear() {
        this.root = new RedBlackNode<>(); // old root and children nodes will be garbage collected.
        versionRepository.clear();
        versionRepository.add(this.root);
//        treeRepository.clear();
//        treeRepository.add(new BinarySearchTree<>(this.root));
//        visitedNodes.clear();
    }

    /****************************************************
     * insert
     *
     * insert a red node
     ***************************************************/
    public void add(T data) {
        //insert(new RedBlackNode<>( null, null, "RED", data));
        insert(new RedBlackNode<>(data));
    }

    /****************************************************
     * insert
     *
     * a helper method that inserts the given node
     ***************************************************/
    public void insert(RedBlackNode<T> newNode) {
        if(isEmpty() || root.item == null) {
            root = newNode;
        } else {
            //if it is not empty we must perform two steps:
            //Step 1: Ordinary BST insert to put the node in the correct spot.
            bstInsert(newNode);

            //Step 2: fixUp
            fixUp(newNode);
        }
        root.colour = RedBlackNode.BLACK;
    } //end of insert

    /****************************************************
     * bstInsert
     *
     * A regular binary search tree insert.
     * Finds the correct placement off the nodes data and inserts.
     ***************************************************/
    public void bstInsert(RedBlackNode<T> newNode) {
        rblist.clear();
        RedBlackNode<T> curr = root;
        RedBlackNode<T> prev = null;
        while(curr != null) {
            rblist.add(curr);
            prev = curr;
            if(newNode.item.compareTo(curr.item) == 0) {
                return;
            } else if(newNode.item.compareTo(curr.item) < 0) {
                curr = (RedBlackNode<T>) curr.leftChild;
            } else if(newNode.item.compareTo(curr.item) > 0) {
                curr = (RedBlackNode<T>) curr.rightChild;
            }
        }

        if(prev == null) {
            //prev = newNode;
        } else if(newNode.item.compareTo(prev.item) < 0) {
            prev.leftChild = newNode;
        } else if(newNode.item.compareTo(prev.item) > 0) {
            prev.rightChild = newNode;
        }
        rblist.add(newNode);
    } //end of bstInsert


    /*
     * get update list
     * */
    public void traverAgain(RedBlackNode<T> newNode) {
        rblist.clear();
        RedBlackNode<T> curr = root;
        RedBlackNode<T> prev = null;
        while(curr != null) {
            rblist.add(curr);
            prev = curr;
            if(newNode.item.compareTo(curr.item) == 0) {
                return;
            } else if(newNode.item.compareTo(curr.item) < 0) {
                curr = (RedBlackNode<T>) curr.leftChild;
            } else if(newNode.item.compareTo(curr.item) > 0) {
                curr = (RedBlackNode<T>) curr.rightChild;
            }
        }
    }

    /****************************************************
     * fixUp
     *
     * Corrects the insertion to work for the red black tree.
     ***************************************************/
    public void fixUp(RedBlackNode<T> curr) {
        RedBlackNode<T> rootNode = rblist.get(0);
        int size = rblist.size() - 1;
        RedBlackNode<T> currParent = rblist.get(size - 1);
        // System.out.println(currParent);
        while(curr != root && !currParent.colour.equals(RedBlackNode.BLACK)) {
            //is currParent the left child of its parent?
            //Right side
            RedBlackNode<T> currGrandParent = rblist.get(size - 2);
            if(currParent == currGrandParent.leftChild) {
                RedBlackNode<T> currUncle = (RedBlackNode<T>) currGrandParent.rightChild;
                //case 1: currUncle is red
                if(currUncle != null && currUncle.colour.equals(RedBlackNode.RED)) {
                    currParent.colour = RedBlackNode.BLACK;
                    currUncle.colour = RedBlackNode.BLACK;
                    currGrandParent.colour = RedBlackNode.RED;
                    //push curr up to it's grandparent.
                    //curr = currParent.parent;
                    //currParent = curr.parent;
                } else {
                    //case 2: we are our parent's right child
                    if(curr == currParent.rightChild) {
                        curr = currParent;
                        leftRotate(curr, 2);
                        currParent = rblist.get(size - 1);
                        //currParent = curr;
                    }
                    //case 3: our sibling is a black node and we are red
                    currParent.colour = RedBlackNode.BLACK;
                    currGrandParent = rblist.get(size - 2);
                    currGrandParent.colour = RedBlackNode.RED;
                    rightRotate(currGrandParent, 3);
                } //end of if-else chain
            } else {
                //currParent is the right child
                //left side
                RedBlackNode<T> currUncle = (RedBlackNode<T>) currGrandParent.leftChild;
                //case 1: currUncle is red
                if(currUncle != null && currUncle.colour.equals(RedBlackNode.RED)) {
                    currParent.colour = RedBlackNode.BLACK;
                    currUncle.colour = RedBlackNode.BLACK;
                    currGrandParent.colour = RedBlackNode.RED;
                    //push curr up to it's grandparent.
                    //curr = currParent.parent;
                    //currParent = curr.parent;
                } else {
                    //case 2: we are our parent's right child
                    if(curr == currParent.leftChild) {
                        curr = currParent;
                        rightRotate(curr, 2);
                        currParent = rblist.get(size - 1);
                    }
                    //case 3: our sibling is a black node and we are red
                    currParent.colour = RedBlackNode.BLACK;
                    currGrandParent = rblist.get(size - 2);
                    currGrandParent.colour = RedBlackNode.RED;
                    leftRotate(currGrandParent, 3);
                } //end of else
            } //end of if-else
        } //end of while
        root.colour = RedBlackNode.BLACK;
    }//end of fixUp


    /****************************************************
     * leftRotate
     *
     * perform a left rotation on the tree
     ***************************************************/
    public void leftRotate(RedBlackNode<T> curr, int i) {
        //set up the variables
        int size = rblist.size() - 1;
        RedBlackNode<T> currGrandParent = rblist.get(size - i);
        rblist.clear();
        RedBlackNode<T> currRight = (RedBlackNode<T>) curr.rightChild;
        RedBlackNode<T> currRightLC = (RedBlackNode<T>) currRight.leftChild;
        RedBlackNode<T> temp = curr;
        //transfer the right node of curr into curr's position
        if(currGrandParent != null && currGrandParent.leftChild != null && currGrandParent.leftChild == curr) {
            currGrandParent.leftChild = currRight;
        } else if(currGrandParent != null && currGrandParent.rightChild != null && currGrandParent.rightChild == curr) {
            currGrandParent.rightChild = currRight;
        } //end of if
        curr = currRight;
        //fix new curr's children.
        curr.leftChild = temp;
        temp.rightChild = currRightLC;
        //reassign parent pointers.
//        if (temp.parent == null)
//            root = curr;
//        temp.parent = curr;
//        if (curr != null)
//            curr.parent = currGrandParent;
//        if (currRightLC != null)
//            currRightLC.parent = temp;
        System.out.println(temp.rightChild);
        traverAgain(temp);
//        rblist.add(root);
//        rblist.add(currGrandParent);
//        rblist.add(currRight);
//        rblist.add(temp);
        System.out.println("left " + rblist);
    } //end of left rotate


    /****************************************************
     * rightRotate
     *
     * perform a right rotation on the tree
     ***************************************************/
    public void rightRotate(RedBlackNode<T> curr, int i) {
        //set up the variables
        int size = rblist.size() - 1;
        RedBlackNode<T> currGrandParent = rblist.get(size - i); //6
        rblist.clear();
        RedBlackNode<T> currLeft = (RedBlackNode<T>) curr.leftChild;
        RedBlackNode<T> currLeftRC = (RedBlackNode<T>) currLeft.rightChild;
        RedBlackNode<T> temp = curr;
        //transfer the right node of curr into curr's position
        if(currGrandParent != null && currGrandParent.leftChild != null && currGrandParent.leftChild == curr) {
            currGrandParent.leftChild = currLeft;
        } else if(currGrandParent != null && currGrandParent.rightChild != null && currGrandParent.rightChild == curr) {
            currGrandParent.rightChild = currLeft;
        }
        curr = currLeft;
        //fix new curr's children.
        curr.rightChild = temp;
        temp.leftChild = currLeftRC;
        //reassign parent pointers.
//        if (temp.parent == null)
//            root = curr;
//        temp.parent = currLeft;
//        if (curr != null)
//            curr.parent = currGrandParent;
//        if (currLeftRC != null)
//            currLeftRC.parent = curr;


        System.out.println(temp.rightChild);
        traverAgain(temp);
        System.out.println("right " + rblist);
    } //end of right rotate

    public boolean isEmpty() {
        return root == null;
    } //end of isEmpty

    public RedBlackNode<T> search(T key) {
        RedBlackNode<T> curr = root;
        while(curr != null) {
            if(key.compareTo(curr.item) == 0) {
                return curr;
            } else if(key.compareTo(curr.item) < 0) {
                curr = (RedBlackNode<T>) curr.leftChild;
            } else if(key.compareTo(curr.item) > 0) {
                curr = (RedBlackNode<T>) curr.rightChild;
            }
        }
        return null;
    } //end of search

    public RedBlackNode<T> getRoot() {
        return root;
    }//end of getRoot

    public void resetTree() {
        root = null;
    } //end of resetTree

    public int getDepth() {
        return this.getDepth(this.root);
    } //end of getDepth

    private int getDepth(Node<T> node) {
        if(node != null) {
            int right_depth;
            int left_depth = this.getDepth(node.leftChild);
            return left_depth > (right_depth = this.getDepth(node.rightChild)) ? left_depth + 1 : right_depth + 1;
        }
        return 0;
    } //end of getDepth


    /**
     * Hook method to keep track of the tracked path.
     * Implementation can be overriden by subclasses to
     * provide different functionalities.
     *
     * @param currentNode : Current Node that program is visiting.
     */
    @Override
    protected void nodeTraversed(Node<T> currentNode) {

    }

    /**
     * toString
     *
     * @return the string representation of the tree.
     */
    public String toString() {
        return toString(root);
    }

    protected String toString(RedBlackNode<T> node) {
        if(node == null || node.item == null) {
            return "";
        }
        return node.item + "(" + toString(node.leftChild) + ", " +
                toString(node.rightChild) + ")";
    }

    @Override
    protected Node<T> preModification(Node<T> node) {
        return node.deepClone(node); // Pass the reference of the cloned Node back to caller.
    }

    public static void main(String[] args) {
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        ////test case 1
        tree.add(12);
        tree.add(5);
        tree.add(11);
        tree.add(15);
        tree.add(13);
        tree.add(14);
        tree.add(16);

////testcase 2
        tree.add(10);
        tree.add(8);
        tree.add(13);
        tree.add(6);
        tree.add(7);


        //test case 3
        tree.add(10);
        tree.add(13);
        tree.add(8);
        tree.add(6);
        tree.add(7);
        tree.add(9);
        tree.add(11);
        tree.add(12);
        tree.add(16);
        tree.add(18);

        System.out.println(tree.rblist);
        System.out.println(TreeBuilder.visualise(tree.root));
    }

}
