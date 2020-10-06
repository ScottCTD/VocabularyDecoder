import xyz.scottc.utils.components.UtilJPanel;

import javax.swing.*;

/*
left right bottom 9
top 38
 */
public class TestFrameSize extends JFrame {

    private final UtilJPanel rootPanel = new UtilJPanel();

    public static void main(String[] args) {
        TestFrameSize testFrameSize = new TestFrameSize();
        testFrameSize.init();
    }

    private void init() {
        this.setSize(1000, 1000);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setVisible(true);
        this.setContentPane(this.rootPanel);

        System.out.println(this.getHeight());
        System.out.println(this.getWidth());

        System.out.println("========================");
    }


}
