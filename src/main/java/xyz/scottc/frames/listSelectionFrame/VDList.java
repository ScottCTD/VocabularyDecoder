package xyz.scottc.frames.listSelectionFrame;

import xyz.scottc.Main;
import xyz.scottc.utils.VDConstantsUtils;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.Objects;

public class VDList {

    /**
     * type : the folder contains the list
     * e.g. SAT/SAT3000/
     */
    private String type;
    private File VDList;

    public VDList() {
    }

    public VDList(String type, File VDList) {
        this.type = type;
        this.VDList = VDList;
    }

    /**
     * auto create an instance by identifying the internal or external path
     *
     * @param list InternalLibrary.getAbsolutePath() or ExternalLibrary.getAbsolutePath()
     */
    public VDList(File list) {
        String inLibPath = Main.internalLibrary.getAbsolutePath();
        String exLibPath = Main.externalLibrary.getAbsolutePath();
        String path = list.getAbsolutePath();
        String temp;
        if (path.startsWith(inLibPath)) {
            temp = path.substring(inLibPath.length());
        } else if (path.startsWith(exLibPath)) {
            temp = path.substring(exLibPath.length());
        } else {
            throw new InvalidPathException(path,
                    "Path should only be InternalLibrary.getAbsolutePath() or ExternalLibrary.getAbsolutePath()!");
        }
        int index = temp.lastIndexOf("\\");
        this.type = temp.substring(1, index);
        this.VDList = list;
    }

    public String getName() {
        return this.VDList.getName().replace(".json", VDConstantsUtils.EMPTY);
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
        return this.type + "\\" + this.getName();
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
        return Objects.hash(this.toString());
    }
}
