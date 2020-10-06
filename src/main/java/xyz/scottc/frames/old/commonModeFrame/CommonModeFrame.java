package xyz.scottc.frames.old.commonModeFrame;

import xyz.scottc.frames.old.commonModeFrame.component.FunctionPanel;
import xyz.scottc.frames.old.commonModeFrame.component.LeftPanel;
import xyz.scottc.frames.old.commonModeFrame.component.TopPanel;
import xyz.scottc.utils.components.LineSeparator;
import xyz.scottc.utils.components.UtilJPanel;
import xyz.scottc.utils.components.VDMenu;

import javax.swing.*;
import java.awt.*;

public class CommonModeFrame extends JFrame {

    private final UtilJPanel rootPanel = new UtilJPanel(new CommonModeLayout());
    private final TopPanel topPanel = new TopPanel(this);
    //private final FunctionPanel functionPanel = new FunctionPanel(this, this.topPanel);
    private final FunctionPanel functionPanel = new FunctionPanel(this, this.topPanel);
    private final LeftPanel leftPanel = new LeftPanel(this, this.functionPanel, this.topPanel);
    public final LineSeparator separator01 = new LineSeparator(LineSeparator.HORIZONTAL, Integer.MAX_VALUE);
    public final LineSeparator separator02 = new LineSeparator(LineSeparator.VERTICAL, 10000);
    public final LineSeparator separator03 = new LineSeparator(LineSeparator.HORIZONTAL, Integer.MAX_VALUE);

    private final JMenuBar menuBar = new JMenuBar();
    private final VDMenu fileMenu = new VDMenu("File");

    public CommonModeFrame(String title) throws HeadlessException {
        super(title);
        this.setContentPane(this.rootPanel);
        this.setJMenuBar(this.menuBar);

        //Adds Menu
        this.menuBarHandler();

        this.rootPanelHandler();
    }

    private void rootPanelHandler() {
        this.rootPanel.add(this.leftPanel);
        this.rootPanel.add(this.separator01);
        this.rootPanel.add(this.separator02);
        this.rootPanel.add(this.separator03);
        this.rootPanel.add(this.functionPanel);
        this.rootPanel.add(this.topPanel);
    }

    private void menuBarHandler() {
        this.menuBar.add(this.fileMenu);
    }

    private class CommonModeLayout implements LayoutManager {

        @Override
        public void layoutContainer(Container parent) {
            int width = parent.getWidth();
            int height = parent.getHeight();
            int marginX = width / 100;
            int marginY = height / 100;

            separator01.setBounds(0, 37 + marginY * 2, width, 1);
            separator02.setBounds(width / (1000 / 251) - 40, 0, 1, height);
            double ratio03 = 1000D / 829D;
            separator03.setBounds(0, (int) (height / ratio03) + 70, width, 1);

            topPanel.setBounds(separator02.getX() + marginX, marginY,
                    width - separator02.getX() - marginX, height - separator01.getY() - marginY);

            leftPanel.setBounds(marginX, marginY, width / (1000 / 251) - 40, height - marginY);

            functionPanel.setBounds(separator02.getX() + 1 + marginX, separator01.getY() + 1 + marginY,
                    width - (separator02.getX() + 1), (height - separator01.getY() + 1));
        }

        @Override
        public void addLayoutComponent(String name, Component comp) {

        }

        @Override
        public void removeLayoutComponent(Component comp) {

        }

        @Override
        public Dimension preferredLayoutSize(Container parent) {
            return null;
        }

        @Override
        public Dimension minimumLayoutSize(Container parent) {
            return null;
        }

    }
}
