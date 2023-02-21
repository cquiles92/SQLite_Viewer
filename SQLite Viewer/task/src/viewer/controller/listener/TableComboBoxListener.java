package viewer.controller.listener;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TableComboBoxListener implements ActionListener {

    private final JTextArea queryTextArea;
    private final JComboBox<String> tablesComboBox;

    public TableComboBoxListener(JTextArea queryTextArea, JComboBox<String> tablesComboBox) {
        this.queryTextArea = queryTextArea;
        this.tablesComboBox = tablesComboBox;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

//      Without Multi-threading
//        queryTextArea.setText("");
//        if (tablesComboBox.getItemCount() > 0) {
//            String selectQuery = String.format("SELECT * FROM %s;", tablesComboBox.getSelectedItem());
//            queryTextArea.append(selectQuery);
//        }

        SwingWorker<Boolean, Void> swingWorker = new SwingWorker<>() {
            @Override
            protected Boolean doInBackground() {
                //synchronization block to fix bug of appending same SQL statement more than once
                synchronized (queryTextArea) {
                    queryTextArea.setText(null);
                    if (tablesComboBox.getItemCount() > 0) {
                        String selectQuery = String.format("SELECT * FROM %s;", tablesComboBox.getSelectedItem());
                        queryTextArea.append(selectQuery);
                        tablesComboBox.setEnabled(true);
                    } else {
                        tablesComboBox.setEnabled(false);
                    }
                }
                return Boolean.TRUE;
            }
        };
        swingWorker.execute();
    }
}
