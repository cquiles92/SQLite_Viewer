package viewer.controller.listener;

import viewer.controller.DatabaseManager;
import viewer.model.Database;
import viewer.model.Table;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Optional;

public class ExecuteQueryButtonListener implements ActionListener {
    private final DatabaseManager databaseManager;
    private final JTextArea queryTextArea;
    private final JScrollPane scrollPane;
    private final JComboBox<String> tablesComboBox;


    public ExecuteQueryButtonListener(JTextArea queryTextArea, JScrollPane scrollPane, JComboBox<String> tablesComboBox) {
        this.queryTextArea = queryTextArea;
        this.scrollPane = scrollPane;
        this.tablesComboBox = tablesComboBox;
        databaseManager = DatabaseManager.getDatabaseManager();

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Database currentDatabase = databaseManager.getCurrentDatabase();
        if (currentDatabase != null) {


            //Without Multi-threading
//            Table currentTable = databaseManager.getTable(queryTextArea.getText());
//            DefaultTableModel currentModel = new DefaultTableModel(currentTable.getData(), currentTable.getColumns());
//            scrollPane.getViewport().removeAll();
//            JTable queryTable = new JTable(currentModel);
//            queryTable.setName("Table");
//            scrollPane.getViewport().add(queryTable);
//

            //MULTITHREADED ATTEMPT

            SwingWorker<Boolean, Void> swingWorker = new SwingWorker<>() {
                @Override
                protected Boolean doInBackground() throws SQLException {
                    String query = queryTextArea.getText();
                    boolean isNotSelect = !query.trim().toUpperCase().startsWith("SELECT");
                    String message = "";

                    //if I am running a statement other than SELECT
                    if (isNotSelect) {

                        //if successful
                        //else crash handle
                        int result = databaseManager.executeStatement(query);
                        if (result >= 0) {
                            //change query to  this
                            query = "SELECT * FROM " + tablesComboBox.getSelectedItem();
                            message = "Operation successful";
                        } else {
                            if (result == -1) {
                                message = "SQL Error occurred";
                            } else if (result == -404) {
                                message = "File Not Found";
                            }
                            JOptionPane.showMessageDialog(new Frame(), message);
                            return Boolean.TRUE;
                        }
                    }

                    //add table to panel if it exists
                    Optional<Table> tableOptional = databaseManager.getTable(query);

                    if (tableOptional.isPresent()) {
                        Table currentTable = tableOptional.get();
                        DefaultTableModel currentModel = new DefaultTableModel(currentTable.getData(), currentTable.getColumns());
                        JTable queryTable = new JTable(currentModel);

                        scrollPane.getViewport().removeAll();
                        queryTable.setName("Table");
                        scrollPane.getViewport().add(queryTable);
                    }

                    //pop up for operations other than select if successful
                    if (isNotSelect) {
                        JOptionPane.showMessageDialog(new Frame(), message);
                    }

                    return Boolean.TRUE;
                }
            };
            swingWorker.execute();
        }
    }
}