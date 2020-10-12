package xyz.scottc.vd.core;

import org.json.JSONObject;
import xyz.scottc.vd.Main;
import xyz.scottc.vd.utils.JSONUtils;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.VDUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.InvalidPathException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class VDList {

    private final List<String> Qs = new ArrayList<>();
    private final List<String> As = new ArrayList<>();
    private final List<Input> Is = new ArrayList<>();
    public boolean isVQ = true;
    /**
     * type : the folder contains the list
     * e.g. SAT/SAT3000/
     */
    private String type;
    private File file;
    private int index = 0;

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
        this.file = list;
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
        VDUtils.interconvertList(this.Qs, this.As);
        this.isVQ = false;
    }

    /**
     * Convert the VDList into two separated lists : Qs and As (Question List & Answer List)
     *
     * @return Whether the list is filled.
     */
    public boolean toQAList() {
        try {
            JSONObject jsonObject = (JSONObject) JSONUtils.fromFile(this.file);
            List<Object> questions = jsonObject.getJSONArray("questions").toList();
            List<Object> answers = jsonObject.getJSONArray("answers").toList();
            for (Object question : questions) {
                this.Qs.add(question.toString());
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

    public void loadInput() {
        try {
            JSONObject jsonObject = (JSONObject) JSONUtils.fromFile(this.file);
            List<Object> inputs;
            if (this.isVQ) {
                inputs = jsonObject.getJSONArray(VDConstants.KEY_INPUT_MEANINGS).toList();
            } else {
                inputs = jsonObject.getJSONArray(VDConstants.KEY_INPUT_VOCABULARIES).toList();
            }
            this.Is.clear();
            for (Object inputContent : inputs) {
                Input input = new Input(inputContent.toString());
                this.Is.add(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() throws IOException {
        List<String> saveList = new ArrayList<>(110);
        for (Input input : this.Is) {
            saveList.add(input.getContent());
        }
        if (this.isVQ) {
            JSONUtils.replaceByKey(this.file, VDConstants.KEY_INPUT_MEANINGS, saveList);
        } else {
            JSONUtils.replaceByKey(this.file, VDConstants.KEY_INPUT_VOCABULARIES, saveList);
        }
    }

    public void clear() throws IOException {
        List<String> emptyList = new ArrayList<>();
        for (int i = 0; i < this.Qs.size(); i++) {
            emptyList.add(VDConstants.EMPTY);
        }
        if (this.isVQ) {
            JSONUtils.replaceByKey(this.file, VDConstants.KEY_INPUT_MEANINGS, emptyList);
        } else {
            JSONUtils.replaceByKey(this.file, VDConstants.KEY_INPUT_VOCABULARIES, emptyList);
        }
    }

    public List<String> splitType() {
        List<String> list = new ArrayList<>();
        int index = 0;
        int start = 0;
        while ((index = this.type.indexOf("\\", index)) != -1) {
            String temp = this.type.substring(start, index);
            list.add(temp.replace("\\", VDConstants.EMPTY));
            start = index;
            index++;
        }
        return list;
    }

    /**
     * Parse the paths array and form a valid path string.
     *
     * @param paths The paths from DefaultMutableTreeNode.getPath().getPath;
     * @return A valid String path like 1/2/3/4.vd
     */
    public static String parsePaths(Object[] paths) {
        StringBuilder builder = new StringBuilder();
        if (paths.length > 1) {
            for (int i = 1; i < paths.length; i++) {
                builder.append(paths[i].toString()).append("\\");
            }
            return builder.substring(0, builder.toString().length() - 1);
        }
        return null;
    }

    public int getIndex() {
        return this.index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return this.file.getName();
    }

    public String getSimpleName() {
        return this.file.getName().replace(".vd", VDConstants.EMPTY);
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
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

    public void setInput(Input input) {
        this.Is.set(this.index, input);
    }

    public Input getInput(int index) {
        return this.Is.get(index);
    }

    public void setInputContent(String input) {
        this.Is.get(this.index).setContent(input);
    }

    public int getPercentAnswered() {
        int answered = 0;
        for (Input input : this.Is) {
            if (input.getState() != Input.InputState.NOT_ANSWERED) {
                answered++;
            }
        }
        int total = this.Qs.size();
        System.out.println((answered * 100) / (total * 100));
        return (answered * 100) / (total * 100);
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
