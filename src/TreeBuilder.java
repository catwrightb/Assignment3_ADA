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
            if(node.left != null)
                output += "" + visualise(node.left);
            output += "" + node;
            if(node.right != null)
                output += "" + visualise(node.right);
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
        if(node == null || node.item == null)
            return 0;
        else
            return countNodes(node.left) + 1 + countNodes(node.right);
    }

    /*
     * draws the Red black tree
     * */
    public static int drawTree(Graphics g, Node<?> current, int x, int level, int nodeCount, Map<Node<?>, Point> map, int BOX_SIZE, String treeType) {
        if (current.left != null) {
            nodeCount = drawTree(g, current.left, x, level + 1, nodeCount, map, BOX_SIZE, treeType);
        }

        int currentX = x + nodeCount * BOX_SIZE;
        int currentY = level * 2 * BOX_SIZE + BOX_SIZE;
        nodeCount++;
        map.put(current, new Point(currentX, currentY));

        if (current.right != null) {
            nodeCount = drawTree(g, current.right, x, level + 1, nodeCount, map, BOX_SIZE, treeType);
        }

        if (treeType.equals("Red and Black Tree")){
            g.setColor(current.getColor());
        }
        else if(treeType.equals("Persistent")){
            g.setColor(Color.BLUE);
        }
        else {
            g.setColor(Color.gray);
        }

        if (current.left != null) {
            Point leftPoint = map.get(current.left);
            g.drawLine(currentX, currentY, leftPoint.x, leftPoint.y - BOX_SIZE / 2);
        }

        if (current.right != null) {
            Point rightPoint = map.get(current.right);
            g.drawLine(currentX, currentY, rightPoint.x, rightPoint.y - BOX_SIZE / 2);
        }


        Point currentPoint = map.get(current);
        g.fillRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);

        g.setColor(Color.ORANGE);

        g.drawRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);
        g.setFont(new Font("courier new", Font.BOLD, 16));
        g.drawString(current.toString(), currentPoint.x-current.toString().length()*4, currentPoint.y);
        return nodeCount;
    }
}