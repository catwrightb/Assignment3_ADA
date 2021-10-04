//import java.awt.*;
//
//public class TestNode<T> extends Node<T>{
//
//    String color;
//
//    TestNode(T K) {
//        super(K);
//        color = "RED";
//
//    } //end of RedBlackNode
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
////    @Override
////    public String toString()
////    {
////        return String.valueOf(key);
////    }
//
//    public static void main(String[] args) {
//        TestNode<Integer> t = new TestNode<Integer>(3);
//        TestNode<Integer> t2 = new TestNode<Integer>(5);
//        t.left = t2;
//
//        System.out.println(t.left.item);
//    }
//}
