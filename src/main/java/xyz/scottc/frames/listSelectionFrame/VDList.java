package xyz.scottc.frames.listSelectionFrame;

import xyz.scottc.utils.VDConstantsUtils;

import java.io.File;
import java.util.Objects;

public class VDList {

    public static final String SAT_TYPE = "SAT";
    public static final String TOEFL_TYPE = "TOEFL";

    private String type;
    private File VDList;

    public VDList() {
    }

    public VDList(String type, File VDList) {
        this.type = type;
        this.VDList = VDList;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public File getVDList() {
        return VDList;
    }

    public void setVDList(File VDList) {
        this.VDList = VDList;
    }

    @Override
    public String toString() {
        return this.VDList.getName().replace(".json", VDConstantsUtils.EMPTY);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof VDList)) return false;
        VDList vdList = (VDList) o;
        return (vdList.getType() + vdList.toString()).equals(this.getType() + this.toString());
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.getType(), this.toString());
    }
}
