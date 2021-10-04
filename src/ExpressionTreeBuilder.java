public class ExpressionTreeBuilder {
    

    //The method countNodes should recursively count the
    // number of nodes down the left and right
    // subtrees from the parameter Archive.ExpNode.
    public static int countNodes(Node<?> node){
        if (node==null)
            return 0;
        else
            return countNodes(node.left) + 1
                    + countNodes(node.right);

    }


}
