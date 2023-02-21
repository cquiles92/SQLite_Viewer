package viewer;

import javax.swing.*;
import java.awt.*;

/**
 * Main JFrame to view program
 */
public class SQLiteViewer extends JFrame {
    private final JPanel mainPanel;
    private final LayoutManager layout;

    public SQLiteViewer() {
        mainPanel = new SQLitePanel();
        layout = new BorderLayout();

        init();
    }

    private void init() {
        initFrame();
        addComponents();
    }

    private void initFrame() {
        //set up Frame UI
        setTitle("SQLite Viewer");
        setSize(700, 900);
        setLocationRelativeTo(null);
        setLayout(layout);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
    }

    private void addComponents() {
        //add UI Elements to Frame
        add(mainPanel, BorderLayout.CENTER);
    }
}
