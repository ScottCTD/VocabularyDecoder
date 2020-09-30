package xyz.scottc.utils;

import java.awt.*;

public class VDAmountDisplay extends UtilJLabel{

    private int currentAmount = 0;
    private int totalAmount = 0;

    public VDAmountDisplay(Font font) {
        super(font);
        this.setText(this.currentAmount + "/" + this.totalAmount);
    }

    private void update() {
        this.setText(this.currentAmount + "/" + this.totalAmount);
    }

    public void stop() {
        this.currentAmount = 0;
        this.totalAmount = 0;
        this.update();
    }

    public int getCurrentAmount() {
        return currentAmount;
    }

    public void setCurrentAmount(int currentAmount) {
        this.currentAmount = currentAmount;
        this.update();
    }

    public int getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(int totalAmount) {
        this.totalAmount = totalAmount;
        this.update();
    }
}
