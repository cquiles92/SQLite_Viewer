package viewer.controller.listener;

import viewer.controller.DatabaseManager;
import viewer.model.Database;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class OpenFileButtonListener implements ActionListener {
    private final JTextField fileNameTextField;
    private final JComboBox<String> tablesComboBox;
    private final JButton executeQueryButton;
    private final JTextArea queryTextArea;
    private final DatabaseManager databaseManager;


    public OpenFileButtonListener(JComboBox<String> tablesComboBox, JTextField fileNameTextField,
                                  JButton executeQueryButton, JTextArea queryTextArea) {
        databaseManager = DatabaseManager.getDatabaseManager();
        this.tablesComboBox = tablesComboBox;
        this.fileNameTextField = fileNameTextField;
        this.executeQueryButton = executeQueryButton;
        this.queryTextArea = queryTextArea;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        File databaseFile = new File(fileNameTextField.getText());
        tablesComboBox.removeAllItems();
        if (databaseFile.exists()) {
            //Without Multi-threading
//        databaseManager.getDatabase(fileNameTextField.getText());
//        Database currentDatabase = databaseManager.getCurrentDatabase();
//
//        if (currentDatabase != null) {
//            for (String tableName : currentDatabase.tableNames()) {
//                tablesComboBox.addItem(tableName);
//            }
//        }

            //MULTI THREADED ATTEMPT
            SwingWorker<Boolean, Void> swingWorker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() {
                    Database currentDatabase = databaseManager.getDatabase(fileNameTextField.getText());
                    if (currentDatabase != null) {
                        for (String tableName : currentDatabase.tableNames()) {
                            tablesComboBox.addItem(tableName);
                        }
                    }

                    executeQueryButton.setEnabled(true);
                    queryTextArea.setEnabled(true);
                    return Boolean.TRUE;
                }
            };
            swingWorker.execute();
        } else {
            executeQueryButton.setEnabled(false);
            queryTextArea.setEnabled(false);

            JOptionPane.showMessageDialog(new Frame(), "File doesn't exist!");
        }
    }
}