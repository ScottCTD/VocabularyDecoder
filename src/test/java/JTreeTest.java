import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.components.UtilJPanel;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;

public class JTreeTest extends JFrame {

    public static void main(String[] args) {
        //Initiate the Frame
        JTreeTest.initFrame();
    }

    private static void initFrame() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException | UnsupportedLookAndFeelException |
                IllegalAccessException e) {
            e.printStackTrace();
        }
        //MainFrame frame = new MainFrame("Vocabulary Decoder");
        JTreeTest frame = new JTreeTest();
        //ListSelection frame = new ListSelection("List Selection");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setBounds(0, 0, 500, 500);
        frame.setVisible(true);
    }

    public JTreeTest() throws HeadlessException {
        this.rootPanelHandler();
    }

    private void rootPanelHandler() {
        UtilJPanel rootPanel = new UtilJPanel();
        rootPanel.setLayout(new BorderLayout());
        this.setContentPane(rootPanel);

        DefaultMutableTreeNode rootNode = new DefaultMutableTreeNode("Root Node ");
        JTree tree = new JTree(rootNode);
        rootPanel.add(tree, BorderLayout.CENTER);

        tree.setFont(VDConstants.MICROSOFT_YAHEI_PLAIN_20);

        DefaultMutableTreeNode testNode01 = new DefaultMutableTreeNode("Test01");
        rootNode.add(testNode01);

        DefaultMutableTreeNode testNode02 = new DefaultMutableTreeNode("Test02");
        testNode01.add(testNode02);

        tree.addTreeSelectionListener(e -> {
            System.out.println(tree.getSelectionPath());
        });
    }
}
