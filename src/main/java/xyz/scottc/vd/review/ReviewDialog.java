package xyz.scottc.vd.review;

import xyz.scottc.vd.core.Input;
import xyz.scottc.vd.core.VDList;
import xyz.scottc.vd.review.cellRenderers.CorrectCellRenderer;
import xyz.scottc.vd.review.cellRenderers.IndexCellRenderer;
import xyz.scottc.vd.review.cellRenderers.StandardAnswerCellRenderer;
import xyz.scottc.vd.review.cellRenderers.UniversalCellRenderer;
import xyz.scottc.vd.utils.VDConstants;
import xyz.scottc.vd.utils.VDUtils;
import xyz.scottc.vd.utils.components.UtilJPanel;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Vector;

public class ReviewDialog extends JDialog {

    private final UtilJPanel rootPanel = new UtilJPanel();
    private final SpringLayout layout = new SpringLayout();

    private final String[] displayModes = {"All", "Not Answered", "Incorrect", "Unsure"};
    private final JComboBox<String> switcher = new JComboBox<>(this.displayModes);

    private final DefaultTableModel allDataModel = new DefaultTableModel();
    private final DefaultTableModel incorrectDataModel = new DefaultTableModel();
    private final DefaultTableModel notAnsweredDataModel = new DefaultTableModel();
    private final DefaultTableModel unsureDataModel = new DefaultTableModel();

    public final JTable table = new JTable(this.allDataModel) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    private final JScrollPane tableView = new JScrollPane(this.table);

    private final HashMap<Integer, Input.InputState> states = new HashMap<>(110);

    public ReviewDialog(Frame owner) {
        super(owner);
        this.setTitle("Review");
        this.setModal(false);
        int width = VDUtils.getScreenRectangle().width / 4 * 3;
        int height = VDUtils.getScreenRectangle().height / 4 * 3;
        this.setSize(width, height);
        this.setLocation(owner.getX() + (owner.getWidth() - width) / 2,
                owner.getY() + (owner.getHeight() - height) / 2);
        this.rootPanelHandler();
        this.layoutHandler();
    }

    private void rootPanelHandler() {
        this.setContentPane(this.rootPanel);

        this.rootPanel.add(this.switcher);
        this.switcher.setFont(VDConstants.MICROSOFT_YAHEI_BOLD_30);
        this.switcher.addActionListener(e -> this.update());

        this.rootPanel.add(this.tableView);
        this.table.setFillsViewportHeight(true);
        this.table.setRowSelectionAllowed(true);
        this.table.setColumnSelectionAllowed(false);
        this.table.setDragEnabled(false);
        this.table.setRowHeight(40);

        //set the property of table header
        this.table.getTableHeader().setFont(VDConstants.MICROSOFT_YAHEI_PLAIN_20);

        //init the models
        this.initDataModel(this.allDataModel, this.notAnsweredDataModel,
                this.incorrectDataModel, this.unsureDataModel);

        this.addRenderers();
    }

    public void addTableMouseLisener(MouseListener listener) {
        this.table.addMouseListener(listener);
    }

    public void setData(int row, VDList data) {
        this.allDataModel.setValueAt(data.getIndex() + 1, row, 0);
        this.allDataModel.setValueAt(data.getQuestion(), row, 1);
        this.allDataModel.setValueAt(data.getAnswer(), row, 2);
        this.allDataModel.setValueAt(data.getInput().getContent(), row, 3);
        this.allDataModel.setValueAt(data.getInput().getState(), row, 4);
        this.states.replace(data.getIndex(), data.getInput().getState());
        this.update();
    }

    private void update() {
        switch (Objects.requireNonNull(this.switcher.getSelectedItem()).toString()) {
            case "All":
                this.table.setModel(this.allDataModel);
                break;
            case "Not Answered":
                this.table.setModel(this.notAnsweredDataModel);
                this.clearNotAnswerDataModel();
                for (int i = 0; i < states.size(); i++) {
                    if (states.get(i) == Input.InputState.NOT_ANSWERED) {
                        this.notAnsweredDataModel.addRow(this.getVectorAt(i));
                    }
                }
                break;
            case "Incorrect":
                this.table.setModel(this.incorrectDataModel);
                this.clearIncorrectDataModel();
                for (int i = 0; i < states.size(); i++) {
                    if (states.get(i) == Input.InputState.INCORRECT) {
                        this.incorrectDataModel.addRow(this.getVectorAt(i));
                    }
                }
                break;
            case "Unsure":
                this.table.setModel(this.unsureDataModel);
                this.clearUnsureDataModel();
                for (int i = 0; i < states.size(); i++) {
                    if (states.get(i) == Input.InputState.UNSURE) {
                        this.unsureDataModel.addRow(this.getVectorAt(i));
                    }
                }
                break;
        }
        this.addRenderers();
    }

    private Vector<Object> getVectorAt(int row) {
        Vector<Object> vector = new Vector<>(5);
        vector.add(this.allDataModel.getValueAt(row, 0));
        vector.add(this.allDataModel.getValueAt(row, 1));
        vector.add(this.allDataModel.getValueAt(row, 2));
        vector.add(this.allDataModel.getValueAt(row, 3));
        vector.add(this.allDataModel.getValueAt(row, 4));
        return vector;
    }

    /**
     * pre load all the index ,questions ,and answers
     *
     * @param list the VDList in the pre-init state
     */
    public void initData(VDList list) {
        List<String> Qs = list.getQs();
        List<String> As = list.getAs();
        List<Input> Is = list.getIs();
        for (int i = 0; i < Qs.size(); i++) {
            Vector<Object> buffer = new Vector<>(5);
            buffer.add(i + 1);
            buffer.add(Qs.get(i));
            buffer.add(As.get(i));
            buffer.add(Is.get(i).getContent());
            buffer.add(Is.get(i).getState());
            this.allDataModel.addRow(buffer);
            this.states.put(i, Is.get(i).getState());
        }
    }

/*    private Vector<Object> generateDataCollection(VDList data) {
        Vector<Object> dataCollection = new Vector<>(5);
        dataCollection.add(data.getIndex() + 1);
        dataCollection.add(data.getQuestion());
        dataCollection.add(data.getAnswer());
        dataCollection.add(data.getInput().getContent());
        dataCollection.add(data.getInput().getState());
        return dataCollection;
    }*/

    private void initDataModel(DefaultTableModel... models) {
        String[] titles = {"#", "Questions", "Standard Answers", "Your Answers", "Correct?"};
        for (DefaultTableModel model : models) {
            for (String title : titles) {
                model.addColumn(title);
            }
        }
    }

    private void clearNotAnswerDataModel() {
        for (int i = this.notAnsweredDataModel.getRowCount(); i > 0; i--) {
            this.notAnsweredDataModel.removeRow(i - 1);
        }
    }

    private void clearIncorrectDataModel() {
        for (int i = this.incorrectDataModel.getRowCount(); i > 0; i--) {
            this.incorrectDataModel.removeRow(i - 1);
        }
    }

    private void clearUnsureDataModel() {
        for (int i = this.unsureDataModel.getRowCount(); i > 0; i--) {
            this.unsureDataModel.removeRow(i - 1);
        }
    }

    private void clearAllDataModel() {
        for (int i = this.allDataModel.getRowCount(); i > 0; i--) {
            this.allDataModel.removeRow(i - 1);
        }
    }

    public void clear() {
        this.clearAllDataModel();
        this.clearNotAnswerDataModel();
        this.clearIncorrectDataModel();
        this.clearUnsureDataModel();
    }

    public void addRenderers() {
        this.table.getColumnModel().getColumn(0).setCellRenderer(new IndexCellRenderer());
        this.table.getColumnModel().getColumn(1).setCellRenderer(new UniversalCellRenderer());
        this.table.getColumnModel().getColumn(2).setCellRenderer(new StandardAnswerCellRenderer());
        this.table.getColumnModel().getColumn(3).setCellRenderer(new UniversalCellRenderer());
        this.table.getColumnModel().getColumn(4).setCellRenderer(new CorrectCellRenderer());
    }

    private void layoutHandler() {
        this.rootPanel.setLayout(this.layout);
        this.layout.putConstraint(SpringLayout.NORTH, this.switcher, 0, SpringLayout.NORTH, this.rootPanel);
        this.layout.putConstraint(SpringLayout.EAST, this.switcher, 0, SpringLayout.EAST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.WEST, this.switcher, 0, SpringLayout.WEST, this.rootPanel);

        this.layout.putConstraint(SpringLayout.NORTH, this.tableView, 0, SpringLayout.SOUTH, this.switcher);
        this.layout.putConstraint(SpringLayout.EAST, this.tableView, 0, SpringLayout.EAST, this.rootPanel);
        this.layout.putConstraint(SpringLayout.SOUTH, this.tableView, 0, SpringLayout.SOUTH, this.rootPanel);
        this.layout.putConstraint(SpringLayout.WEST, this.tableView, 0, SpringLayout.WEST, this.rootPanel);
    }
}
