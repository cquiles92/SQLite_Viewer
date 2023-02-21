package viewer;

import viewer.controller.listener.ExecuteQueryButtonListener;
import viewer.controller.listener.OpenFileButtonListener;
import viewer.controller.listener.TableComboBoxListener;

import javax.swing.*;
import java.awt.*;

/**
 * Main JPanel of program
 */
public class SQLitePanel extends JPanel {
    private final LayoutManager layout;
    private final JButton openFileButton;
    private final JButton executeQueryButton;
    private final JComboBox<String> tablesComboBox;
    private final JTextField fileNameTextField;
    private final JTextArea queryTextArea;
    private final JTable table;
    private final JScrollPane scrollPane;


    public SQLitePanel() {
        //Initialize Components
        layout = new FlowLayout();
        fileNameTextField = new JTextField(50);
        queryTextArea = new JTextArea();
        openFileButton = new JButton("Open");
        executeQueryButton = new JButton("Execute");
        tablesComboBox = new JComboBox<>();
        table = new JTable();
        scrollPane = new JScrollPane(table);
        init();
    }

    private void init() {
        initFrame();
        addComponents();
    }

    private void initFrame() {
        setVisible(true);
        setLayout(layout);

        //default behavior of menu items is disabled
        fileNameTextField.setEnabled(true);
        openFileButton.setEnabled(true);
        tablesComboBox.setEnabled(true);
        queryTextArea.setEnabled(false);
        executeQueryButton.setEnabled(false);
    }

    private void addComponents() {
        //configure UI Elements

        //set element names
        fileNameTextField.setName("FileNameTextField");
        openFileButton.setName("OpenFileButton");
        tablesComboBox.setName("TablesComboBox");
        queryTextArea.setName("QueryTextArea");
        executeQueryButton.setName("ExecuteQueryButton");
        table.setName("Table");

        //set ui sizes
        tablesComboBox.setPreferredSize(new Dimension(650, 30));
        queryTextArea.setPreferredSize(new Dimension(550, 100));

        //add UI Elements to Frame
        add(fileNameTextField);
        add(openFileButton);
        add(tablesComboBox);
        add(queryTextArea);
        add(executeQueryButton);
        add(scrollPane);

        //actionListeners added here
        openFileButton.addActionListener(new OpenFileButtonListener(
                tablesComboBox, fileNameTextField, executeQueryButton, queryTextArea));
        tablesComboBox.addActionListener(new TableComboBoxListener(queryTextArea, tablesComboBox));
        executeQueryButton.addActionListener(new ExecuteQueryButtonListener(queryTextArea, scrollPane, tablesComboBox));
    }
}
