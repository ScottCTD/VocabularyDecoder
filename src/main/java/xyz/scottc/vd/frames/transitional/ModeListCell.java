package xyz.scottc.vd.frames.transitional;

public class ModeListCell {

    private String name;
    private String description;

    public ModeListCell() {
    }

    public ModeListCell(String name, String description) {
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
}
