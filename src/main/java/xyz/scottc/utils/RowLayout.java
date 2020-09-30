package xyz.scottc.utils;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class RowLayout implements LayoutManager2 {

    private List<Item> items = new ArrayList<>();

    @Override
    public void addLayoutComponent(Component comp, Object constraints) {
        Item item = new Item();
        item.component = comp;
        item.constraints = constraints.toString();
        items.add(item);
    }

    @Override
    public void addLayoutComponent(String name, Component comp) {
        Item item = new Item();
        item.component = comp;
        item.constraints = "auto";
        items.add(item);
    }

    @Override
    public void removeLayoutComponent(Component comp) {
        for (Item item : items) {
            if (item.component == comp) {
                items.remove(items.indexOf(item));
            }
        }
    }

    @Override
    public Dimension maximumLayoutSize(Container target) {
        return null;
    }

    @Override
    public float getLayoutAlignmentX(Container target) {
        return 0;
    }

    @Override
    public float getLayoutAlignmentY(Container target) {
        return 0;
    }

    @Override
    public void invalidateLayout(Container target) {

    }

    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return null;
    }

    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return null;
    }

    @Override
    public void layoutContainer(Container parent) {
        //得到内矩形
        Rectangle rectangle = parent.getBounds();

    }

    private static class Item {
        Component component;
        String constraints = "auto";
        int width = 0;
        int height = 0;
        int weight = 0;
    }

}
