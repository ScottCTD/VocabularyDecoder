package xyz.scottc.frames.old.commonModeFrame.dialog.review;

import xyz.scottc.VocabularyState;
import xyz.scottc.frames.old.commonModeFrame.dialog.review.cellRenderers.CorrectCellRenderer;
import xyz.scottc.frames.old.commonModeFrame.dialog.review.cellRenderers.NumCellRenderer;
import xyz.scottc.frames.old.commonModeFrame.dialog.review.cellRenderers.StandardAnswerCellRenderer;
import xyz.scottc.frames.old.commonModeFrame.dialog.review.cellRenderers.UniversalCellRenderer;
import xyz.scottc.utils.AfColumnLayout;
import xyz.scottc.utils.UtilJPanel;
import xyz.scottc.utils.VDConstantsUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.Vector;

public class DataPanel extends UtilJPanel {
    public final DefaultTableModel allDataModel = new DefaultTableModel();
    public final DefaultTableModel incorrectDataModel = new DefaultTableModel();
    public final JTable reviewTable = new JTable(this.allDataModel) {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    };
    public final JScrollPane reviewScrollPane = new JScrollPane(this.reviewTable);

    public ArrayList<ReviewData> allDataList = new ArrayList<>();

    public DataPanel() {
        this.setLayout(new AfColumnLayout());
        this.add(this.reviewScrollPane, "1w");

        this.reviewTable.setFillsViewportHeight(true);
        this.reviewTable.setRowSelectionAllowed(true);
        this.reviewTable.setColumnSelectionAllowed(false);
        this.reviewTable.setDragEnabled(false);
        this.reviewTable.setRowHeight(40);

        //set the property of table header
        this.reviewTable.getTableHeader().setFont(VDConstantsUtils.MICROSOFT_YAHEI_PLAIN_20);

        //All data model initialization
        this.allDataModel.addColumn("#");
        this.allDataModel.addColumn("Questions");
        this.allDataModel.addColumn("Standard Answers");
        this.allDataModel.addColumn("Your Answers");
        this.allDataModel.addColumn("Correct?");

        //Incorrect data model initialization
        this.incorrectDataModel.addColumn("#");
        this.incorrectDataModel.addColumn("Questions");
        this.incorrectDataModel.addColumn("Standard Answers");
        this.incorrectDataModel.addColumn("Your Answers");
        this.incorrectDataModel.addColumn("Correct?");

        this.addRenderers();
    }

    private Vector<Object> dataHandler(ReviewData datum) {
        Vector<Object> dataList = new Vector<>();
        dataList.add(datum.getSerialNumber());
        dataList.add(datum.getQuestion());
        dataList.add(datum.getAnswer());
        dataList.add(datum.getInput());
        dataList.add(datum.getCorrect());
        return dataList;
    }

    public void addData(ReviewData... data) {
        for (ReviewData datum : data) {
            this.allDataModel.addRow(this.dataHandler(datum));
            this.allDataList.add(datum);
        }
    }

    public void setData(DefaultTableModel dataModel, ReviewData data, int row) {
        dataModel.setValueAt(data.getSerialNumber(), row, 0);
        dataModel.setValueAt(data.getQuestion(), row, 1);
        dataModel.setValueAt(data.getAnswer(), row, 2);
        dataModel.setValueAt(data.getInput(), row, 3);
        dataModel.setValueAt(data.getCorrect(), row, 4);
        this.allDataList.set(row, data);
    }

    private void addIncorrectData(ReviewData... incorrectData) {
        for (ReviewData datum : incorrectData) {
            this.incorrectDataModel.addRow(this.dataHandler(datum));
        }
    }

    public void incorrectDataHandler() {
        this.clearIncorrectDataModel();
        for (ReviewData datum : this.allDataList) {
            if (datum.getCorrect() == VocabularyState.INCORRECT) {
                this.addIncorrectData(datum);
            }
        }
    }

    public void addRenderers() {
        this.reviewTable.getColumnModel().getColumn(0).setCellRenderer(new NumCellRenderer());
        this.reviewTable.getColumnModel().getColumn(1).setCellRenderer(new UniversalCellRenderer());
        this.reviewTable.getColumnModel().getColumn(2).setCellRenderer(new StandardAnswerCellRenderer());
        this.reviewTable.getColumnModel().getColumn(3).setCellRenderer(new UniversalCellRenderer());
        this.reviewTable.getColumnModel().getColumn(4).setCellRenderer(new CorrectCellRenderer());
    }

    public void clear() {
        this.allDataList.clear();
        for (int i = this.allDataModel.getRowCount(); i > 0; i--) {
            this.allDataModel.removeRow(i - 1);
        }
        this.clearIncorrectDataModel();
    }

    private void clearIncorrectDataModel() {
        for (int i = this.incorrectDataModel.getRowCount(); i > 0; i--) {
            this.incorrectDataModel.removeRow(i - 1);
        }
    }
}
