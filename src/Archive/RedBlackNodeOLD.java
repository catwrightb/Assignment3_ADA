//import java.awt.*;
//
//public class RedBlackNode<T extends Comparable<T>> extends Node<T>{
//    RedBlackNode left;
//    RedBlackNode right;
//    //RedBlackNode parent;
//    String color;
//    T key;
//
//
//    RedBlackNode(T K) {
//        super(K);
//        //parent = P;
//        key = K;
//        color = "RED";
//
//    } //end of RedBlackNode
//
//
//    /****************************************************
//     * getColor
//     *
//     * return the actual color value of the node.
//     ***************************************************/
//    Color getColor() {
//        Color toRet = new Color(70, 70, 70);
//        if (color.equals("RED"))
//            toRet = new Color(250, 70, 70);
//        return toRet;
//    }
//
//    @Override
//    public String toString()
//    {
//        return String.valueOf(key);
//    }
//}
