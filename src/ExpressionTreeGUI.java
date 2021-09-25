import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;

/**
 * Almost Complete GUI - just need to finish the code when pressing the buttons and updating
 * the number of nodes in the tree.. WIll only build once ExpNode subclasses are made
 * @author sehall
 */
public class ExpressionTreeGUI extends JPanel implements ActionListener {

    private final JButton evaluateButton, buildTreeButton;

    private DrawPanel drawPanel;
    private ExpNode root; //root node
    private BinarySearchTree.Node testroot; //USE THIS root node
    private int numberNodes = 0;
    private JTextField postFixField;
    public static int PANEL_H = 500;
    public static int PANEL_W = 700;
    private JLabel nodeCounterLabel;
    private final int BOX_SIZE = 40;

    public ExpressionTreeGUI() {
        super(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {}
        root = null;
        super.setPreferredSize(new Dimension(PANEL_W, PANEL_H + 30));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(PANEL_W, 30));
        drawPanel = new DrawPanel();

        evaluateButton = new JButton("Evaluate to infix");
        buildTreeButton = new JButton("Build Expression Tree");

        evaluateButton.addActionListener((ActionListener) this);
        buildTreeButton.addActionListener((ActionListener) this);

        postFixField = new JTextField(20);

        buttonPanel.add(postFixField);
        buttonPanel.add(buildTreeButton);
        buttonPanel.add(evaluateButton);

        super.add(drawPanel, BorderLayout.CENTER);
        super.add(buttonPanel, BorderLayout.SOUTH);

        nodeCounterLabel = new JLabel("Number of Nodes: " + 0);
        super.add(nodeCounterLabel, BorderLayout.NORTH);
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if (source == evaluateButton) {   //finish this button event to handle the evaluation and output to infix of the tree
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

        } else if (source == buildTreeButton && postFixField.getText() != null) {

            // COMPLETE ME!!!!!!!!!!
            //Use ExpressionTreeBuilder to build the tree
            String s = postFixField.getText();
            String[] strings = s.split(" ");

           //check the input before calling to buildtree 
           //   only want to allow numbers and symbols 
           //   if no symbol is present or no numbers are present then tree will not build 
//            String regex = "[0-9]+";
//
//            int number = 0;
//            int operator = 0;
//            int disconti = 0;
//
//            for (int i = 0; i < strings.length; i++) {
//                String c = strings[i];
//
//                if (c.matches(regex)){
//                    number++;
//                }
//                else if (c.equals("+") || c.equals("-") || c.equals("/") || c.equals("*") || c.equals("~")) {
//                    operator++;
//                }
//                else {
//                    disconti++;
//                }
//            }
//
//            if (disconti == 0 && number > 0 && operator > 0){
//                root = ExpressionTreeBuilder.buildExpressionTree(strings);
//            }
//            else if (disconti > 0){
//                JOptionPane.showMessageDialog(this, "Please enter a equation with proper symbols (digits or + , - , * , / and ~).", "INFO",
//                        JOptionPane.ERROR_MESSAGE);
//            }

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