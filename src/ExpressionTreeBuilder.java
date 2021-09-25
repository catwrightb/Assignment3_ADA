public class ExpressionTreeBuilder {

    //The method buildExpressionTree which builds an
    // expression tree comprised of operator and operand
    // nodes by reading symbols from the postfix input array.
    /* PesudoCode
    * Create Stack S, Create parent ExpNode P;
      for(symbol in expression)
            if symbol is an operand
                push(operand) onto S
            if symbol is operator
                set P as new Operator
                pop two Nodes (or only one if unary operator) and set as P.left and P.right
                push(P) onto S
        return P.
      * */
    public static ExpNode buildExpressionTree(String[] arr, ExpNode root, int i ){
        //create stack

//        Stack<ExpNode> s = new Stack<>();
//        int end = postfixStrings.length-1;
//        int size = postfixStrings.length;
//
//
//        ExpNode p;
//
//        for (int i = 0; i < postfixStrings.length; i++) {
//
//            //if operand
//            if (!typeOperator(postfixStrings[i])){
//                p = new OperandNode(postfixStrings[i]);
//                s.push(p);
//
//            }
//            if (typeOperator(postfixStrings[i])){ //else operator
//                p = new OperatorNode(postfixStrings[i]);
//
//                if (postfixStrings[i].equals("~")){
//                   p.rightChild = s.pop();
//                }
//                else if (!postfixStrings[i].equals("~")){
//                    if (!s.isEmpty()){
//                        p.rightChild = s.pop();
//                        p.leftChild = s.pop();
//                    }
//
//                }
//
//                s.push(p);
//            }
//
////            if ((i+1) % 3 == 0 || i == end){
////                p = new OperatorNode(postfixStrings[i]);
////
////                if (s.size() >= 2){
////                    p.rightChild = s.pop();
////                    p.leftChild = s.pop();
////                }
////                else {
////                    p.rightChild = s.pop();
////                }
////                s.push(p);
////
////            }
////            else if (i % 2 == 0 && i != 0 && size % 2 == 0){
////                p = new OperatorNode(postfixStrings[i]);
////                p.rightChild = s.pop();
////                s.push(p);
////            }
////            else{
////                p = new OperandNode(postfixStrings[i]);
////                s.push(p);
////            }
//
//        }
//
  //      return s.peek();
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


//    public static Tree.Node insertLevelOrder(String[] arr, Tree.Node root,
//                                             int i)
//    {
//        // Base case for recursion
//        if (i < arr.length) {
//            Tree.Node temp = new Tree.Node(arr[i]);
//            root = temp;
//
//            // insert left child
//            root.left = insertLevelOrder(arr, root.left,
//                    2 * i + 1);
//
//            // insert right child
//            root.right = insertLevelOrder(arr, root.right,
//                    2 * i + 2);
//        }
//        return root;
//    }

//    public static boolean typeOperator(String symbol) {
//        if (symbol.equals("+") || symbol.equals("-") || symbol.equals("/") || symbol.equals("*") || symbol.equals("~")) {
//            return true;
//        } else {
//            return false;
//        }
//    }

    public static void inOrder(BinarySearchTree.Node root)
    {
        if (root != null) {
            inOrder(root.leftChild);
            System.out.print(root.item + ", ");
            inOrder(root.rightChild);
        }
    }
    

    //The method countNodes should recursively count the
    // number of nodes down the left and right
    // subtrees from the parameter ExpNode.
    public static int countNodes(BinarySearchTree.Node node){
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
//    public static String toInfixString(ExpNode node){
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
