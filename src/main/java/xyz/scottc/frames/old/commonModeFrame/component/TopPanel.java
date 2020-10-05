package xyz.scottc.frames.old.commonModeFrame.component;

import xyz.scottc.frames.old.commonModeFrame.CommonModeFrame;
import xyz.scottc.utils.*;

import java.awt.*;

public class TopPanel extends UtilJPanel {

    private final CommonModeFrame parentFrame;

    public final UtilJButton startButton = new UtilJButton("Start", 25);
    public final UtilJButton stopButton = new UtilJButton("Stop", 25);
    public final UtilJButton suspendButton = new UtilJButton("Suspend", 25);
    public final UtilJButton continueButton = new UtilJButton("Continue", 25);

    public final VDAmountDisplay amountDisplay = new VDAmountDisplay(new Font("微软雅黑", Font.PLAIN, 23));
    public final VDTimer timer = new VDTimer();

    public TopPanel(CommonModeFrame parent) {
        this.parentFrame = parent;
        this.setLayout(new TopPanelLayout());

        this.add(this.startButton);
        this.add(this.stopButton);
        this.add(this.suspendButton);
        this.add(this.continueButton);
        this.add(this.amountDisplay);
        this.add(this.timer);
    }

    private class TopPanelLayout implements LayoutManager {

        @Override
        public void layoutContainer(Container parent) {
            int width = parent.getWidth();
            int marginX = width / 100;

            //startButton
            Dimension startButtonSize = startButton.getPreferredSize();
            startButton.setBounds(0, 0, startButtonSize.width, startButtonSize.height);

            //stopButton
            Dimension stopButtonSize = stopButton.getPreferredSize();
            stopButton.setBounds(startButton.getX() + startButtonSize.width + marginX, 0,
                    stopButtonSize.width, stopButtonSize.height);

            //suspendButton
            Dimension suspendButtonSize = suspendButton.getPreferredSize();
            suspendButton.setBounds(stopButton.getX() + stopButtonSize.width + marginX, 0,
                    suspendButtonSize.width, suspendButtonSize.height);

            //continueButton
            Dimension continueButtonSize = continueButton.getPreferredSize();
            continueButton.setBounds(suspendButton.getX() + suspendButtonSize.width + marginX, 0,
                    continueButtonSize.width, continueButtonSize.height);

            //timer
            int panelHeight = parentFrame.separator01.getY();
            Dimension timerSize = timer.getPreferredSize();
            timer.setBounds(width - marginX * 4 - timerSize.width, (panelHeight - timerSize.height) / 2,
                    timerSize.width, timerSize.height);

            //amountDisplay
            Dimension amountDisplaySize = amountDisplay.getPreferredSize();
            amountDisplay.setBounds(continueButton.getX() + continueButtonSize.width + (timer.getX() - continueButton.getX() - continueButtonSize.width - amountDisplaySize.width) / 2,
                    timer.getY(),
                    amountDisplaySize.width, amountDisplaySize.height);
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
