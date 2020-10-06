package xyz.scottc.utils.components;

import javax.swing.*;
import java.awt.*;

public class VDTimer extends JLabel {

    Thread timer;

    private boolean init = false;
    private boolean suspend = false;

    private int second;
    private int minute;
    private int hour;

    public VDTimer() {
        this.setText("0:0:0");
        this.setFont(new Font("微软雅黑", Font.PLAIN, 23));
    }

    public void startFromZero() {
        this.start(0, 0, 0);
    }

    public void start(int second, int minute, int hour) {
        if (!this.init) {
            this.second = second;
            this.minute = minute;
            this.hour = hour;
            this.init = true;
            timer = new Thread(new TimerThread());
            timer.start();
        }
    }

    public void suspend() {
        if (this.init && !this.suspend) {
            this.suspend = true;
        }
    }

    public void continu() {
        if (this.init && this.suspend) {
            this.suspend = false;
        }
    }

    public void stop() {
        if (this.init) {
            this.init = false;
            this.second = 0;
            this.minute = 0;
            this.hour = 0;
            this.setText("0:0:0");
        }
    }

    public int getSecond() {
        return second;
    }

    public void setSecond(int second) {
        this.second = second;
    }

    public int getMinute() {
        return minute;
    }

    public void setMinute(int minute) {
        this.minute = minute;
    }

    public int getHour() {
        return hour;
    }

    public void setHour(int hour) {
        this.hour = hour;
    }

    private class TimerThread implements Runnable {

        @Override
        public void run() {
            while (init) {
                if (!suspend) {
                    try {
                        if (second + 1 >= 60) {
                            second = 0;
                            if (minute + 1 >= 60) {
                                minute = 0;
                                hour++;
                            } else {
                                minute++;
                            }
                        } else {
                            second++;
                        }
                        setText(hour + ":" + minute + ":" + second);
                        Thread.sleep(995);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    Thread.yield();
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
