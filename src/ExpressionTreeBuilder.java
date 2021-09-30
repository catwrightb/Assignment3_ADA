import Archive.ExpNode;

public class ExpressionTreeBuilder {

    public static ExpNode buildExpressionTree(String[] arr, ExpNode root, int i ){

        // Base case for recursion
        if (i < arr.length) {
            root = new ExpNode(arr[i]);

            // insert left child
            root.leftChild = buildExpressionTree(arr, root.leftChild,
                    2 * i + 1);

            // insert right child
            root.rightChild = buildExpressionTree(arr, root.rightChild,
                    2 * i + 2);
        }
        return root;

    }


    public static void inOrder(Node root)
    {
        if (root != null) {
            inOrder(root.leftChild);
            System.out.print(root.item + ", ");
            inOrder(root.rightChild);
        }
    }
    

    //The method countNodes should recursively count the
    // number of nodes down the left and right
    // subtrees from the parameter Archive.ExpNode.
    public static int countNodes(Node node){
        if (node==null)
            return 0;
        else
            return countNodes(node.leftChild) + 1
                    + countNodes(node.rightChild);

    }

//    //The method toInfixString which can be used to recursively convert
//    // a binary expression tree into infix notation using brackets.
//    // inFix = a+b or a+b*c
//    // preFix = +ab or +a*bc
//    // postfix = ab+ or abc*+
//    public static String toInfixString(Archive.ExpNode node){
//
//        if (node == null) {
//            return "";
//        }
//        else if((node.leftChild == null && node.rightChild==null))
//        {
//            return toInfixString(node.leftChild)+node+toInfixString(node.rightChild);
//        }
//        else
//            {
//            return "("+toInfixString(node.leftChild)+node+toInfixString(node.rightChild)+")";
//        }
//
//    }

}
