import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * Almost Complete GUI - just need to finish the code when pressing the buttons and updating
 * the number of nodes in the tree.. WIll only build once ExpNode subclasses are made
 * @author sehall
 */
public class ExpressionTreeGUI extends JPanel implements ActionListener {

    private final JButton removeNodeButton, addNodeButton;

    private DrawPanel drawPanel;
    private ExpNode root; //root node
    private BinarySearchTree.Node testroot; //USE THIS root node
    private int numberNodes = 0;
    private JTextField addNodeTextField;
    private JTextField removeNodeTextField;
    public static int PANEL_H = 500;
    public static int PANEL_W = 700;
    private JLabel nodeCounterLabel;
    private final int BOX_SIZE = 40;
    private JComboBox<String> treeTypeDropDown;
    private String treeType;
    private String[] inputItemList;

    public ExpressionTreeGUI() {
        super(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        root = null;
        super.setPreferredSize(new Dimension(PANEL_W, PANEL_H + 30));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(PANEL_W, 30));
        JPanel treeTypePanel = new JPanel();
        treeTypePanel.setPreferredSize(new Dimension(PANEL_W, 30));
        drawPanel = new DrawPanel();

        removeNodeButton = new JButton("Remove");
        addNodeButton = new JButton("Add to tree");

        removeNodeButton.addActionListener((ActionListener) this);
        addNodeButton.addActionListener((ActionListener) this);


        addNodeTextField = new JTextField(10);
        removeNodeTextField = new JTextField(10);

        buttonPanel.add(addNodeTextField);
        buttonPanel.add(addNodeButton);
        buttonPanel.add(removeNodeTextField);
        buttonPanel.add(removeNodeButton);

        String[] types = {"-", "Binary Search Tree", "Persistent", "Red and Black Tree"};
        treeTypeDropDown = new JComboBox<>(types);
        treeTypeDropDown.addActionListener((ActionListener) this);

        JPanel topPanel = new JPanel();
        topPanel.add(treeTypeDropDown, BorderLayout.NORTH);

        super.add(drawPanel, BorderLayout.CENTER);
        super.add(buttonPanel, BorderLayout.SOUTH);

        nodeCounterLabel = new JLabel("Number of Nodes: " + 0);
        topPanel.add(nodeCounterLabel, BorderLayout.SOUTH);

        super.add(topPanel, BorderLayout.NORTH);

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == removeNodeButton) {   //finish this button event to handle the evaluation and output to infix of the tree
            if (root == null) {
                JOptionPane.showMessageDialog(this, "Tree is null, not built", "INFO",
                        JOptionPane.ERROR_MESSAGE);
            } else {


                // COMPLETE ME!!!!!!!!!!
                //OUTPUT THE INFIX EXPRESSION AND RESULT HERE - Use ExpressionTreeBuilder
//                JOptionPane.showMessageDialog(this, ExpressionTreeBuilder.toInfixString(root)+
//                                " = " + root.evaluate(), "Evaluation",
//                        JOptionPane.INFORMATION_MESSAGE);

            }

        }
        else if (source == treeTypeDropDown){
            treeType = Objects.requireNonNull(treeTypeDropDown.getSelectedItem()).toString();
            System.out.println(treeType);

        }
        else if (source == addNodeButton && addNodeTextField.getText() != null) {

            // COMPLETE ME!!!!!!!!!!
            //Use ExpressionTreeBuilder to build the tree
            String s = addNodeTextField.getText();
            String[] strings = s.split(" ");

           //check the input before calling to buildtree



            // puts list in level order

            BinarySearchTree<String> test = new BinarySearchTree<>();
            for (String string : strings) {
                test.add(string);
            }
            System.out.println(test);

            root = ExpressionTreeBuilder.buildExpressionTree(strings, root, 0);

            testroot = test.root;


        }

        //COMPLETE ME!!!
        //Update the number of nodes label in the tree - if any use ExpressionTreeBuilder

        nodeCounterLabel.setText("Number of Nodes: " + ExpressionTreeBuilder.countNodes(testroot)); //count from root LVR
        drawPanel.repaint();
    }

    private class DrawPanel extends JPanel {

        public DrawPanel() {
            super();
            super.setBackground(Color.WHITE);
            super.setPreferredSize(new Dimension(PANEL_W, PANEL_H));
        }

        @Override
        public void paintComponent(Graphics g) {
            super.paintComponent(g);

            if (root != null) {
                drawTree(g, getWidth());
            }
        }

        public void drawTree(Graphics g, int width) {
            drawNode(g, testroot, BOX_SIZE, 0, 0, new HashMap<>());
        }

        private int drawNode(Graphics g, BinarySearchTree.Node current,
                             int x, int level, int nodeCount, Map<BinarySearchTree.Node, Point> map) {


            if (current.leftChild != null) {
                nodeCount = drawNode(g, current.leftChild, x, level + 1, nodeCount, map);
            }

            int currentX = x + nodeCount * BOX_SIZE;
            int currentY = level * 2 * BOX_SIZE + BOX_SIZE;
            nodeCount++;
            map.put(current, new Point(currentX, currentY));

            if (current.rightChild != null) {
                nodeCount = drawNode(g, current.rightChild, x, level + 1, nodeCount, map);
            }

            g.setColor(Color.red);
            if (current.leftChild != null) {
                Point leftPoint = map.get(current.leftChild);
                g.drawLine(currentX, currentY, leftPoint.x, leftPoint.y - BOX_SIZE / 2);
            }
            if (current.rightChild != null) {
                Point rightPoint = map.get(current.rightChild);
                g.drawLine(currentX, currentY, rightPoint.x, rightPoint.y - BOX_SIZE / 2);

            }
            if (current instanceof BinarySearchTree.Node) {
                g.setColor(Color.WHITE);
            } else {
                g.setColor(Color.YELLOW);
            }

            Point currentPoint = map.get(current);
            g.fillRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);
            g.setColor(Color.BLACK);
            g.drawRect(currentPoint.x - BOX_SIZE / 2, currentPoint.y - BOX_SIZE / 2, BOX_SIZE, BOX_SIZE);
            Font f = new Font("courier new", Font.BOLD, 16);
            g.setFont(f);
//            if (current instanceof OperandNode)
//                g.drawString(current.toString(), currentPoint.x-current.toString().length()*4, currentPoint.y);
//            else
//                g.drawString(current.toString(), currentPoint.x-3, currentPoint.y);
            g.drawString(current.toString(), currentPoint.x-3, currentPoint.y);
            return nodeCount;

//            return 1;
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Expression Tree GUI builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ExpressionTreeGUI());
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenHeight = dimension.height;
        int screenWidth = dimension.width;
        frame.pack();             //resize frame apropriately for its content
        //positions frame in center of screen
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
                (screenHeight / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);
    }
}