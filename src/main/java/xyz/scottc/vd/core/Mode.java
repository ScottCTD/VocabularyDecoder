package xyz.scottc.vd.core;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Mode {

    private String name;
    private String description;

    private static Mode selectedMode = null;

    public static final List<Mode> MODE_LIST = new ArrayList<>();

    public Mode() {
    }

    public Mode(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public static Mode getSelectedMode() {
        return selectedMode;
    }

    public static void setSelectedMode(Mode selectedMode) {
        Mode.selectedMode = selectedMode;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Mode)) return false;
        Mode mode = (Mode) o;
        return this.getName().equals(mode.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getName());
    }
}
