package xyz.scottc.vd.frames.old.mainFrame.component;

import xyz.scottc.vd.frames.old.commonModeFrame.CommonModeFrame;
import xyz.scottc.vd.frames.old.mainFrame.MainFrame;
import xyz.scottc.vd.utils.VDConstantsUtils;
import xyz.scottc.vd.utils.components.UtilJButton;
import xyz.scottc.vd.utils.components.UtilJLabel;
import xyz.scottc.vd.utils.components.UtilJPanel;
import xyz.scottc.vd.utils.components.VDTextArea;

import javax.swing.*;
import java.awt.*;

public class ModeSelectionPanel extends UtilJPanel {

    private final MainFrame parent;

    private final UtilJLabel modeSelectionLabel = new UtilJLabel("Mode Selection", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_40);
    private final DefaultListModel<String> modeListModel = new DefaultListModel<>();
    private final JList<String> modeList = new JList<>();
    private final JScrollPane modeListScrollPane = new JScrollPane(modeList);
    private final UtilJButton confirmButton = new UtilJButton("Confirm", VDConstantsUtils.MICROSOFT_YAHEI_BOLD_40);
    private final VDTextArea help = new VDTextArea(VDConstantsUtils.HELP, 0, 0, VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20, false, false);

    public ModeSelectionPanel(MainFrame parent) {
        this.setLayout(new ModeSelectionPanelLayout());
        this.parent = parent;

        //modeSelectionLabel
        this.add(this.modeSelectionLabel);

        //init modeListModel
        this.modeListModel.addElement("1. Common Mode ");

        //modeList
        this.modeList.setModel(this.modeListModel);
        this.add(this.modeListScrollPane);
        modeList.setFont(new Font("微软雅黑", Font.PLAIN, 30));
        this.modeList.grabFocus();

        //confirmButton
        this.add(this.confirmButton);
        this.confirmButton.setToolTipText("Click Confirm to join in selected mode!");
        this.confirmButton.addActionListener(e -> this.selectMode(this.parent));

        //help
        this.add(this.help);
        this.help.setBackground(this.getBackground());
    }

    private void selectMode(JFrame parent) {
        String mode = this.modeList.getSelectedValue();
        if (mode != null) {
            Point pos = parent.getLocation();
            switch (mode) {
                case "1. Common Mode ":
                    CommonModeFrame frame = new CommonModeFrame("Common Mode");
                    frame.setLocation(pos);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    frame.setSize(new Dimension(1000, 1000));
                    frame.setVisible(true);
                    break;
            }
            parent.dispose();
        }
    }

    private class ModeSelectionPanelLayout implements LayoutManager {

        @Override
        public void layoutContainer(Container parent) {
            int width = parent.getWidth();
            int height = parent.getHeight();
            int marginX = width / 100;
            int marginY = height / 100;

            //modeSelectionLabel
            Dimension modeSelectionLabelSize = modeSelectionLabel.getPreferredSize();
            modeSelectionLabel.setBounds((width - modeSelectionLabelSize.width) / 2, 0,
                    modeSelectionLabelSize.width, modeSelectionLabelSize.height);

            //modeList
            Dimension modeListSize = modeListScrollPane.getPreferredSize();
            modeListScrollPane.setBounds(0, modeSelectionLabel.getY() + modeSelectionLabel.getHeight() + marginY,
                    modeListSize.width, modeListSize.height);

            //help
            Dimension helpSize = help.getPreferredSize();
            help.setBounds(modeListScrollPane.getX() + modeListSize.width + marginX, modeSelectionLabel.getY() + modeSelectionLabel.getHeight() + marginY,
                    helpSize.width, helpSize.height);

            //confirmButton
            Dimension confirmButtonSize = confirmButton.getPreferredSize();
            confirmButton.setBounds((modeListSize.width - confirmButtonSize.width) / 2, modeListScrollPane.getY() + modeListSize.height + marginY,
                    confirmButtonSize.width, confirmButtonSize.height);
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
