import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

/**
 * Almost Complete GUI - just need to finish the code when pressing the buttons and updating
 * the number of nodes in the tree. WIll only build once Archive.ExpNode subclasses are made
 *
 * @author sehall
 */
public class TreeGUI extends JPanel implements ActionListener {

    private final JButton removeNodeButton, addNodeButton;

    private DrawPanel drawPanel;
    private Node<?> root; //root node
    private Node<?> currPersistentBSTRoot; //USE THIS root node
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
    protected BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();;
    protected PersistentBST<Integer> persistentBST = new PersistentBST<>();;
    protected RedBlackTree redBlackTree = new RedBlackTree();

    public TreeGUI() {
        super(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ignored) {
        }
        root = null;

//        BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>(0);
//        PersistentBST<Integer> persistentBST = new PersistentBST<>();
//        RedBlackTree redBlackTree = new RedBlackTree();

        super.setPreferredSize(new Dimension(PANEL_W, PANEL_H + 30));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(PANEL_W, 30));
        JPanel treeTypePanel = new JPanel();
        treeTypePanel.setPreferredSize(new Dimension(PANEL_W, 30));
        drawPanel = new DrawPanel();

        addNodeTextField = new JTextField(10);
        addNodeButton = new JButton("Add to tree");
        addNodeButton.addActionListener((ActionListener) this);

        removeNodeTextField = new JTextField(10);
        removeNodeButton = new JButton("Remove");
        removeNodeButton.addActionListener((ActionListener) this);

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

    public void addNode() {
        if(!addNodeTextField.getText().isEmpty() && treeType != null) {
            String[] userInput = addNodeTextField.getText().split(" ");

            if(!isNumber(userInput[0])) {
                JOptionPane.showMessageDialog(this, "Please enter numbers only", "INFO", JOptionPane.ERROR_MESSAGE);
            } else {
                switch(treeType) {
                    case "Binary Search Tree":
                        // Add parsed user input(s) into BST.
                        Arrays.stream(userInput).forEach(element -> binarySearchTree.add(Integer.parseInt(element)));
                        System.out.println(binarySearchTree);
                        root = binarySearchTree.root;
                        break;
                    case "Persistent":
                        // Add parsed user input(s) into persistentBST.
                        Arrays.stream(userInput).forEach(element -> persistentBST.add(Integer.parseInt(element)));
                        System.out.println(persistentBST);
                        currPersistentBSTRoot = persistentBST.root;
                        break;
                    case "Red and Black Tree":
                        // Add parsed user input(s) into RBT.
                        Arrays.stream(userInput).forEach(element -> redBlackTree.insert(Integer.parseInt(element)));
                        System.out.println(redBlackTree);
                        rbtroot = redBlackTree.getRoot();
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + treeType);
                }
            }
        }
    }

    public void removeNode() {
        if(!removeNodeTextField.getText().trim().isEmpty() && treeType != null) {
            String[] userInput = removeNodeTextField.getText().split(" ,-;/");

            if(!isNumber(userInput[0])) {
                JOptionPane.showMessageDialog(this, "Please enter numbers only", "INFO", JOptionPane.ERROR_MESSAGE);
            } else {
                switch(treeType) {
                    case "Binary Search Tree":
                        // Remove parsed user input(s) from BST.
                        Arrays.stream(userInput).forEach(element -> binarySearchTree.remove(Integer.parseInt(element)));
                        System.out.println(binarySearchTree);
                        root = binarySearchTree.root;
                        break;
                    case "Persistent":
                        // Add parsed user input(s) into persistentBST.
                        Arrays.stream(userInput).forEach(element -> persistentBST.remove(Integer.parseInt(element)));
                        System.out.println(persistentBST);
                        currPersistentBSTRoot = persistentBST.root;
                        break;
                    case "Red and Black Tree":
                        // Add parsed user input(s) into RBT.
                        //Arrays.stream(userInput).forEach(element -> redBlackTree.remove(Integer.parseInt(element)));
                        System.out.println(redBlackTree);
                        rbtroot = redBlackTree.getRoot();
                        break;
                }
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        Object source = event.getSource();

        if(source == removeNodeButton) {   //finish this button event to handle the evaluation and output to infix of the tree
            if(root == null) {
                JOptionPane.showMessageDialog(this, "Tree is null, not built", "INFO",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                this.removeNode();
//                JOptionPane.showMessageDialog(this, TreeBuilder.toInfixString(root)+
//                                " = " + root.evaluate(), "Evaluation",
//                        JOptionPane.INFORMATION_MESSAGE);
            }

        } else if(source == treeTypeDropDown) {
            treeType = Objects.requireNonNull(treeTypeDropDown.getSelectedItem()).toString();
            System.out.println(treeType);
            inputItemList.clear();
            root = null;
            drawPanel.repaint();
        } else if(source == addNodeButton) {
            this.addNode();
            /*
            // COMPLETE ME!!!!!!!!!!
            //Use TreeBuilder to build the tree
            String userInput = addNodeTextField.getText();
            String[] strings = userInput.split(" ");
            String firstInput = strings[0];

            if(!isNumber(firstInput)) {
                JOptionPane.showMessageDialog(this, "Please enter numbers only", "INFO",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                Integer i = Integer.parseInt(strings[0]);
                if(!userInput.isEmpty()) {
                    inputItemList.add(i);
                }
                // puts list in level order

                if(treeType.equals("Binary Search Tree")) {
                    BinarySearchTree<Integer> test = new BinarySearchTree<>();

                    for(Integer string : inputItemList) {
                        test.add(string);
                    }
                    System.out.println(test);
                    root = test.root;
                    currPersistentBSTRoot = test.root;

                } else if(treeType.equals("Red and Black Tree")) {
                    RedBlackTree redBlackTree = new RedBlackTree();

                    for(Integer integer : inputItemList) {
                        redBlackTree.insert(integer);
                    }
                    System.out.println(redBlackTree);
                    rbtroot = redBlackTree.getRoot();

                } else if(treeType.equals("Persistent")) {

                }
            }
            */
        } else if(source == addNodeButton && addNodeTextField.getText() == null
                || treeType != null || treeType == "-") {
            if(root == null) {
                JOptionPane.showMessageDialog(this, "Tree is null, not built", "INFO",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
        nodeCounterLabel.setText("Number of Nodes: " + TreeBuilder.countNodes(currPersistentBSTRoot)); //count from root LVR
        drawPanel.repaint();
    }

    static boolean isNumber(String s) {
        for(int i = 0; i < s.length(); i++)
            if(!Character.isDigit(s.charAt(i)))
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

            if(root != null) {
                drawTree(g, getWidth());
            } else if(rbtroot != null) { //TODO fix this logic this is only tempoaray logic
                drawTree(g, getWidth());
            }

        }

        public void drawTree(Graphics g, int width) {
            if(treeType.equals("Binary Search Tree")) {
                BinarySearchTree.drawBST(g, root, BOX_SIZE, 0, 0, new HashMap<>(), BOX_SIZE);
            } else if(treeType.equals("Red and Black Tree")) {
                RedBlackTree.drawRBT(g, rbtroot, BOX_SIZE, 0, 0, new HashMap<>(), BOX_SIZE);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Expression Tree GUI builder");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new TreeGUI());
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension dimension = toolkit.getScreenSize();
        int screenHeight = dimension.height;
        int screenWidth = dimension.width;
        frame.pack();             //resize frame appropriately for its content
        //positions frame in center of screen
        frame.setLocation(new Point((screenWidth / 2) - (frame.getWidth() / 2),
                (screenHeight / 2) - (frame.getHeight() / 2)));
        frame.setVisible(true);
    }
}