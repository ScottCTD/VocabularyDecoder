package xyz.scottc.vd;

import org.json.JSONObject;
import xyz.scottc.vd.utils.JSONUtils;
import xyz.scottc.vd.utils.VDConstantsUtils;

import java.io.File;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VDList {

    /**
     * type : the folder contains the list
     * e.g. SAT/SAT3000/
     */
    private String type;
    private File VDList;

    private final List<String> Qs = new ArrayList<>();
    private final List<String> As = new ArrayList<>();

    private int index = 0;

    private final List<Input> Is = new ArrayList<>();

    /**
     * auto create an instance by identifying the internal or external path
     *
     * @param list path should start with InternalLibrary.getAbsolutePath() or ExternalLibrary.getAbsolutePath()
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
        this.type = temp.substring(1, index + 1);
        this.VDList = list;
    }

    public boolean next() {
        if (this.index + 1 < this.Qs.size()) {
            index++;
            return true;
        }
        return false;
    }

    public boolean pre() {
        if (this.index > 0) {
            this.index--;
            return true;
        }
        return false;
    }

    public void setInputContent(String input) {
        this.Is.get(this.index).setContent(input);
    }

    public Input judgeEn() {
        Input input = this.getInput();
        if (input.toString().equals(this.getAnswer())) {
            input.setState(Input.InputState.CORRECT);
        } else {
            input.setState(Input.InputState.INCORRECT);
        }
        return input;
    }

    public void interconvertQAList() {
        VDConstantsUtils.interconvertList(this.Qs, this.As);
    }

    /**
     * Convert the VDList into two separated lists : Qs and As (Question List & Answer List)
     *
     * @return Whether the list is filled.
     */
    public boolean toQAList() {
        try {
            JSONObject jsonObject = (JSONObject) JSONUtils.fromFile(this.VDList, "UTF-8");
            List<Object> questions = jsonObject.getJSONArray("questions").toList();
            List<Object> answers = jsonObject.getJSONArray("answers").toList();
            for (Object question : questions) {
                this.Qs.add(question.toString());
                this.Is.add(new Input(VDConstantsUtils.EMPTY));
            }
            for (Object answer : answers) {
                this.As.add(answer.toString());
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    /**
     * Parse the paths array and form a valid path string.
     *
     * @param paths The paths from DefaultMutableTreeNode.getPath().getPath;
     * @return A valid String path like 1/2/3/4.json
     */
    public static String parsePaths(Object[] paths) {
        StringBuilder builder = new StringBuilder();
        if (paths.length > 1) {
            for (int i = 1; i < paths.length; i++) {
                builder.append(paths[i].toString()).append("\\");
            }
            return builder.substring(0, builder.toString().length() - 1);
        }
        return paths.toString();
    }

    public List<String> splitType() {
        List<String> list = new ArrayList<>();
        int index = 0;
        int start = 0;
        while ((index = this.type.indexOf("\\", index)) != -1) {
            String temp = this.type.substring(start, index);
            list.add(temp.replace("\\", VDConstantsUtils.EMPTY));
            start = index;
            index++;
        }
        return list;
    }

    public int getIndex() {
        return this.index;
    }

    public String getName() {
        return this.VDList.getName();
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

    public String getQuestion() {
        return this.Qs.get(this.index);
    }

    public String getQuestion(int index) {
        return this.Qs.get(index);
    }

    public String getAnswer() {
        return this.As.get(this.index);
    }

    public String getAnswer(int index) {
        return this.As.get(index);
    }

    public Input getInput() {
        return this.Is.get(this.index);
    }

    public Input getInput(int index) {
        return this.Is.get(index);
    }

    public void setInput(Input input) {
        this.Is.set(this.index, input);
    }

    public List<String> getQs() {
        return this.Qs;
    }

    public List<String> getAs() {
        return this.As;
    }

    public List<Input> getIs() {
        return this.Is;
    }

    @Override
    public String toString() {
        return this.type + this.getName();
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
