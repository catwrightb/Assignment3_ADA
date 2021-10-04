import java.awt.*;
import java.util.Map;

public class TreeBuilder {
    /**
     * Helper method to return a text representation
     * of a binary search tree. Traverses inorder.
     *
     * @param node root node
     * @return text representation of the tree.
     */
    public static String visualise(Node<?> node) {
        String output = "[";
        if(node != null && node.item != null) {
            if(node.leftChild != null)
                output += "" + visualise(node.leftChild);
            output += "" + node.toString();
            if(node.rightChild != null)
                output += "" + visualise(node.rightChild);
        }
        output += "]";
        return output;
    }

    /**
     * Method to recursively count the number of nodes a tree has.
     *
     * @param node root node of the tree.
     * @return number of nodes in the tree
     */
    public static int countNodes(Node<?> node) {
        if(node == null)
            return 0;
        else
            return countNodes(node.leftChild) + 1 + countNodes(node.rightChild);
    }

    /*
     * draws the Red black tree
     * */
    public static int drawTree(Graphics g, Node<?> current, int x, int level, int nodeCount, Map<Node<?>, Point> map, int BOX_SIZE) {
        if (current.leftChild != null) {
            nodeCount = drawTree(g, current.leftChild, x, level + 1, nodeCount, map, BOX_SIZE);
        }

        int currentX = x + nodeCount * BOX_SIZE;
        int currentY = level * 2 * BOX_SIZE + BOX_SIZE;
        nodeCount++;
        map.put(current, new Point(currentX, currentY));

        if (current.rightChild != null) {
            nodeCount = drawTree(g, current.rightChild, x, level + 1, nodeCount, map, BOX_SIZE);
        }

        g.setColor(Color.RED);
        if (current.leftChild != null) {
            Point leftPoint = map.get(current.leftChild);
            g.drawLine(currentX, currentY, leftPoint.x, leftPoint.y - BOX_SIZE / 2);
        }

        if (current.rightChild!= null) {
            Point rightPoint = map.get(current.rightChild);
            g.drawLine(currentX, currentY, rightPoint.x, rightPoint.y - BOX_SIZE / 2);
        }

        if (current instanceof RedBlackNode) {
            g.setColor(((RedBlackNode<?>) current).getColor());
        } else {
            g.setColor(Color.WHITE);
        }

        Point currentPoint = map.get(current);
        g.fillRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);

        if(g.getColor().equals(Color.BLACK)) {
            g.setColor(Color.WHITE);
        } else {
            g.setColor(Color.BLACK);
        }

        g.drawRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);
        g.setFont(new Font("courier new", Font.BOLD, 16));
        g.drawString(current.toString(), currentPoint.x-current.toString().length()*4, currentPoint.y);
        return nodeCount;
    }
}
