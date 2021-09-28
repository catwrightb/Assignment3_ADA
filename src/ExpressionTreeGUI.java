import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
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
    private BinarySearchTree.Node root; //root node
    private BinarySearchTree.Node testroot; //USE THIS root node
    private RedBlackNode rbtroot; //USE THIS root node
    private int numberNodes = 0;
    private JTextField addNodeTextField;
    private JTextField removeNodeTextField;
    public static int PANEL_H = 500;
    public static int PANEL_W = 700;
    private JLabel nodeCounterLabel;
    private final int BOX_SIZE = 40;
    private JComboBox<String> treeTypeDropDown;
    private String treeType;
    private ArrayList<Integer> inputItemList = new ArrayList<>();
    RedBlackTree redBlackTree;

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
            inputItemList.clear();
            root = null;
            drawPanel.repaint();

        }
        else if (source == addNodeButton && addNodeTextField.getText() != null && treeType != null) {

            // COMPLETE ME!!!!!!!!!!
            //Use ExpressionTreeBuilder to build the tree
            String s = addNodeTextField.getText();
            String[] strings = s.split(" ");
            String firstInput = strings[0];

            if (!isNumber(firstInput)){
                JOptionPane.showMessageDialog(this, "Please enter numbers only", "INFO",
                        JOptionPane.ERROR_MESSAGE);
            }
            else {

                Integer i = Integer.parseInt(strings[0]);
                if (!s.isEmpty()) {
                    inputItemList.add(i);
                }


                // puts list in level order

                if (treeType.equals("Binary Search Tree")) {
                    BinarySearchTree<Integer> test = new BinarySearchTree<>();

                    for (Integer string : inputItemList) {
                        test.add(string);
                    }
                    System.out.println(test);
                    root = test.root;
                    testroot = test.root;

                } else if (treeType.equals("Red and Black Tree")) {
                    redBlackTree = new RedBlackTree();

                    for (Integer integer : inputItemList) {
                        redBlackTree.insert(integer);
                    }
                    System.out.println(redBlackTree);
                    rbtroot = redBlackTree.getRoot();


                } else if (treeType.equals("Persistent")) {

                }

            }

        }
        else if (source == addNodeButton && addNodeTextField.getText() == null
                || treeType != null || treeType == "-"){
            if (root == null) {
                JOptionPane.showMessageDialog(this, "Tree is null, not built", "INFO",
                        JOptionPane.ERROR_MESSAGE);
            }
        }

        //COMPLETE ME!!!
        //Update the number of nodes label in the tree - if any use ExpressionTreeBuilder

        nodeCounterLabel.setText("Number of Nodes: " + ExpressionTreeBuilder.countNodes(testroot)); //count from root LVR
        drawPanel.repaint();
    }

    static boolean isNumber(String s)
    {
        for (int i = 0; i < s.length(); i++)
            if (!Character.isDigit(s.charAt(i)))
                return false;

        return true;
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
            else if (rbtroot != null) { //TODO fix this logic this is only tempoaray logic
                drawTree(g, getWidth());
            }

        }

        public void drawTree(Graphics g, int width) {
            if (treeType.equals("Binary Search Tree")){
                BinarySearchTree.drawBST(g, testroot, BOX_SIZE, 0, 0, new HashMap<>(), BOX_SIZE);
            }
            else if (treeType.equals("Red and Black Tree"))
            {
                RedBlackTree.drawRBT(g, rbtroot, BOX_SIZE, 0, 0, new HashMap<>(), BOX_SIZE );
            }
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