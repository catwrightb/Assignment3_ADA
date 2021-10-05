import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * This GUI class is an adaptation of Assignment 1's Question 6 problem,
 * from 2021's Semester 1's Data Structures & Algorithms course.
 *
 * Reference
 * -----------------------------------------------------------------------
 * Hall, S. (2021). Data structures & algorithms assignment 1 : question 6.
 *      AUT BCIS course. ExpressionTreeGUI.java
 */
public class TreeGUI extends JPanel implements ActionListener, KeyListener {

    public static int PANEL_H = 700;
    public static int PANEL_W = 900;
    private final int BOX_SIZE = 40;

    private JLabel versionLabel, treeTypeLabel, nodeCounterLabel;
    private int numberNodes = 0;
    private JComboBox<String> treeTypeDropDown;
    private String treeType;
    private JComboBox<Integer> versionNoDropDown; // Added
    private int versionSelected;

    private DrawPanel drawPanel;
    private JTextField addNodeTextField, removeNodeTextField;
    private JButton addNodeButton, removeNodeButton;
    private JButton clearButton;

    private Node<?> localRoot; //root node
    protected BinarySearchTree<Integer> binarySearchTree = new BinarySearchTree<>();
    protected PersistentBST<Integer> persistentBST = new PersistentBST<>();
    protected RedBlackTreeAlt<Integer> redBlackTree = new RedBlackTreeAlt<>();

    public TreeGUI() {
        super(new BorderLayout());
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch(Exception ignored) {
        }
        localRoot = null;

        super.setPreferredSize(new Dimension(PANEL_W, PANEL_H + 30));
        JPanel buttonPanel = new JPanel();
        buttonPanel.setPreferredSize(new Dimension(PANEL_W, 30));
        JPanel treeTypePanel = new JPanel();
        treeTypePanel.setPreferredSize(new Dimension(PANEL_W, 30));
        drawPanel = new DrawPanel();

        addNodeTextField = new JTextField(10);
        addNodeTextField.addKeyListener(this);

        addNodeButton = new JButton("Add to tree");
        addNodeButton.addActionListener((action) -> this.addNode());

        removeNodeTextField = new JTextField(10);
        removeNodeTextField.addKeyListener(this);

        removeNodeButton = new JButton("Remove");
        removeNodeButton.addActionListener((action) -> this.removeNode());

        clearButton = new JButton("Clear");
        clearButton.addActionListener((action) -> this.clear());

        buttonPanel.add(addNodeTextField);
        buttonPanel.add(addNodeButton);
        buttonPanel.add(removeNodeTextField);
        buttonPanel.add(removeNodeButton);
        buttonPanel.add(clearButton);

        treeTypeLabel = new JLabel("Type: ");
        String[] types = {"-", "Binary Search Tree", "Persistent", "Red and Black Tree"};
        treeTypeDropDown = new JComboBox<>(types);
        treeTypeDropDown.addActionListener(this);

        versionLabel = new JLabel("Ver: ");
        versionNoDropDown = new JComboBox<>();
        versionNoDropDown.addActionListener(this);
        versionNoDropDown.setEnabled(false);

        nodeCounterLabel = new JLabel("Number of Nodes: " + numberNodes);

        JPanel topPanel = new JPanel();
        topPanel.add(treeTypeLabel, BorderLayout.NORTH);
        topPanel.add(treeTypeDropDown, BorderLayout.NORTH);
        topPanel.add(versionLabel, BorderLayout.NORTH);
        topPanel.add(versionNoDropDown, BorderLayout.NORTH);
        topPanel.add(nodeCounterLabel, BorderLayout.SOUTH);

        super.add(drawPanel, BorderLayout.CENTER);
        super.add(buttonPanel, BorderLayout.SOUTH);
        super.add(topPanel, BorderLayout.NORTH);

    }

    public void addNode() {
        if(!addNodeTextField.getText().isEmpty() && treeType != null) {
            String[] userInput = addNodeTextField.getText().trim().split(",");

            if(!isNumber(userInput[0])) {
                JOptionPane.showMessageDialog(this, "Please enter numbers only", "INFO", JOptionPane.ERROR_MESSAGE);
            } else {
                switch(treeType) {
                    case "Binary Search Tree":
                        // Add parsed user input(s) into BST.
                        Arrays.stream(userInput).forEach(element -> binarySearchTree.add(Integer.parseInt(element)));
                        localRoot = binarySearchTree.root;
//                        System.out.println(binarySearchTree);
                        break;
                    case "Persistent":
                        // Add parsed user input(s) into persistentBST.
                        Arrays.stream(userInput).forEach(element -> persistentBST.add(Integer.parseInt(element)));
                        localRoot = persistentBST.root;
                        versionSelected = persistentBST.getCurrentVersionNo();
//                        System.out.println(persistentBST);
                        break;
                    case "Red and Black Tree":
                        // Add parsed user input(s) into RBT.
                        Arrays.stream(userInput).forEach(element -> redBlackTree.insertRB(Integer.parseInt(element)));
                        localRoot = redBlackTree.root;
                        versionSelected = redBlackTree.getCurrentVersionNo();
//                        System.out.println(redBlackTree);
                        break;
                    default:
                        JOptionPane.showMessageDialog(this, "Please select a valid tree structure first!", "Error", JOptionPane.ERROR_MESSAGE);
                }
                addNodeTextField.setText("");
                nodeCounterLabel.setText("Number of Nodes: " + TreeBuilder.countNodes(localRoot));
                drawPanel.repaint();
                toggleVersionComboBox();
            }
        }
    }

    public int versionCheck(){
        int version = 0;
        switch (treeType) {
            case "Binary Search Tree":
                // Remove parsed user input(s) from BST.
                break;
            case "Persistent":
                // Add parsed user input(s) into persistentBST.
                version = persistentBST.getCurrentVersionNo();
                break;
            case "Red and Black Tree":
                // Add parsed user input(s) into RBT.
                version = redBlackTree.getCurrentVersionNo();
                break;
            default:
        }

        return version;
    }

    public void removeNode() {

        int version = versionCheck();

        if(!removeNodeTextField.getText().isEmpty() && treeType != null ) {
            String[] userInput = removeNodeTextField.getText().trim().split(",");

            if(!isNumber(userInput[0])) {
                JOptionPane.showMessageDialog(this, "Please enter numbers only", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {

                if (containedInTree(userInput[0])){
                    switch (treeType) {
                        case "Binary Search Tree":
                            // Remove parsed user input(s) from BST.
                            Arrays.stream(userInput).forEach(element -> binarySearchTree.remove(Integer.parseInt(element)));
                            localRoot = binarySearchTree.root;
//                            System.out.println(binarySearchTree);
                            break;
                        case "Persistent":
                            // Add parsed user input(s) into persistentBST.
                            if (version == versionSelected){
                                Arrays.stream(userInput).forEach(element -> persistentBST.remove(Integer.parseInt(element)));
                                localRoot = persistentBST.root;
                                versionSelected = persistentBST.getCurrentVersionNo();
//                                System.out.println(persistentBST);
                            }
                            else {
                                JOptionPane.showMessageDialog(this, "Please only remove from the latest branch!", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                            break;
                        case "Red and Black Tree":
                            // Add parsed user input(s) into RBT.
                            if (version == versionSelected){
                                Arrays.stream(userInput).forEach(element -> redBlackTree.remove(Integer.parseInt(element)));
                                localRoot = redBlackTree.root;
                                versionSelected = redBlackTree.getCurrentVersionNo();
//                                System.out.println(redBlackTree);
                            }
                            else {
                                JOptionPane.showMessageDialog(this, "Please only remove from the latest branch!", "Error", JOptionPane.ERROR_MESSAGE);
                            }

                            break;
                        default:
                            JOptionPane.showMessageDialog(this, "Please select a valid tree structure first!", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                    removeNodeTextField.setText("");
                    nodeCounterLabel.setText("Number of Nodes: " + TreeBuilder.countNodes(localRoot));
                    drawPanel.repaint();
                    toggleVersionComboBox();
                }
                else {
                    JOptionPane.showMessageDialog(this, "This value is not present.", "Error", JOptionPane.ERROR_MESSAGE);
                    removeNodeTextField.setText("");
                }
            }

        }
    }

    public void clear() {
        if(treeType != null) {
            addNodeTextField.setText("");
            removeNodeTextField.setText("");
            switch(treeType) {
                case "Binary Search Tree":
                    binarySearchTree.clear();
                    //System.out.println(binarySearchTree);
                    localRoot = binarySearchTree.getRoot();
                    break;
                case "Persistent":
                    persistentBST.clear();
                    //System.out.println(persistentBST);
                    localRoot = persistentBST.getRoot();
                    versionSelected = persistentBST.getCurrentVersionNo();
                    break;
                case "Red and Black Tree":
                    redBlackTree.clear();
                    //System.out.println(redBlackTree);
                    localRoot = redBlackTree.getRoot();
                    versionSelected = redBlackTree.getCurrentVersionNo();
                    break;
                default:
                    JOptionPane.showMessageDialog(this, "Please select a valid tree structure first!", "Error", JOptionPane.ERROR_MESSAGE);
            }
            numberNodes = 0;
            nodeCounterLabel.setText("Number of Nodes: " + numberNodes);
            drawPanel.repaint();
            toggleVersionComboBox();
        }
    }

    public boolean containedInTree(String userInput){
        boolean contained = true;
        switch (treeType) {
            case "Binary Search Tree":
                // Remove parsed user input(s) from BST.
                contained = binarySearchTree.contains(Integer.valueOf(userInput));
                break;
            case "Persistent":
                // Add parsed user input(s) into persistentBST.
                contained = persistentBST.contains(Integer.valueOf(userInput));
                break;
            case "Red and Black Tree":
                // Add parsed user input(s) into RBT.
                contained = redBlackTree.contains(Integer.valueOf(userInput));
                break;
            default:
        }
        return contained;
    }

    public void toggleVersionComboBox() {
        switch(treeType) {
            case "Persistent":
                versionNoDropDown.setEnabled(true);
                versionNoDropDown.setModel(new DefaultComboBoxModel<>(persistentBST.getVersionNos()));
                versionNoDropDown.setSelectedIndex(versionSelected);
                break;
            case "Red and Black Tree":
                versionNoDropDown.setEnabled(true);
                versionNoDropDown.setModel(new DefaultComboBoxModel<>(redBlackTree.getVersionNos()));
                versionNoDropDown.setSelectedIndex(versionSelected);
                break;
            default:
                versionNoDropDown.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        if(event.getSource() == treeTypeDropDown) {
            treeType = Objects.requireNonNull(treeTypeDropDown.getSelectedItem()).toString();

            switch(treeType) {
                case "Binary Search Tree":
                    localRoot = binarySearchTree.getRoot();
                    break;
                case "Persistent":
                    localRoot = persistentBST.getRoot();
                    versionSelected = persistentBST.getCurrentVersionNo();
                    break;
                case "Red and Black Tree":
                    localRoot = redBlackTree.getRoot();
                    versionSelected = redBlackTree.getCurrentVersionNo();
                    break;
                default:
                    localRoot = null;
            }
            toggleVersionComboBox();
        } else if(event.getSource() == versionNoDropDown) {
            versionSelected = versionNoDropDown.getSelectedIndex();
//            System.out.println(versionSelected);
            switch(treeType) {
                case "Persistent":
                    localRoot = persistentBST.getBranch(versionSelected);
                    break;
                case "Red and Black Tree":
                    localRoot = redBlackTree.getBranch(versionSelected);
                    break;
            }
            versionNoDropDown.setSelectedIndex(versionSelected);
        }

        nodeCounterLabel.setText("Number of Nodes: " + TreeBuilder.countNodes(localRoot));
        drawPanel.repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {    }

    @Override
    public void keyPressed(KeyEvent event) {
        if(event.getKeyCode() == KeyEvent.VK_ENTER) { // If enter is pressed in text field...
            if(event.getSource() == addNodeTextField) {
                this.addNode();
            } else if(event.getSource() == removeNodeTextField) {
                this.removeNode();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {    }

     static boolean isNumber(String s) {
        for(int i = 0; i < s.length(); i++)
            if(!Character.isDigit(s.charAt(i)))
                if(s.charAt(i) != '-') { // Accept negative symbols too.
                    return false;
                }

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

            if(localRoot != null && treeType != null && localRoot.item != null) {
                numberNodes = TreeBuilder.drawTree(g, localRoot, BOX_SIZE, 0, 0, new HashMap<>(), BOX_SIZE, treeType);
            }
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Tree GUI Builder");
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