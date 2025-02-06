import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class GUI extends JFrame {

    private Connection connection;
    private JTable libraryTable, departmentTable, editionTable;

    public GUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        try {
            connection = DriverManager.getConnection("jdbc:postgresql://localhost:5432/LibraryDB", "postgres", "postgres");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error connecting to database: " + e.getMessage());
            System.exit(1);
        }

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Libraries", createLibraryPanel());
        tabbedPane.add("Departments", createDepartmentPanel());
        tabbedPane.add("Editions", createEditionPanel());
        tabbedPane.add("Search", createSearchPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createLibraryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JButton addButton = new JButton("Add Library");
        JButton deleteButton = new JButton("Delete Library");
        JButton updateButton = new JButton("Update Library");

        libraryTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Name"}, 0));
        refreshLibraries();

        addButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Enter Library ID:");
            String name = JOptionPane.showInputDialog("Enter Library Name:");
            if (idStr != null && name != null && !idStr.isEmpty() && !name.isEmpty()) {
                try {
                    int id = Integer.parseInt(idStr);
                    executeUpdate("INSERT INTO Library (id, name) VALUES (?, ?)", id, name);
                    refreshLibraries();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid ID format.");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int row = libraryTable.getSelectedRow();
            if (row != -1) {
                int id = (int) libraryTable.getValueAt(row, 0);
                executeUpdate("DELETE FROM Library WHERE id = ?", id);
                refreshLibraries();
            }
        });

        updateButton.addActionListener(e -> {
            int row = libraryTable.getSelectedRow();
            if (row != -1) {
                int id = (int) libraryTable.getValueAt(row, 0);
                String name = JOptionPane.showInputDialog("Enter new Library Name:");
                if (name != null && !name.isEmpty()) {
                    executeUpdate("UPDATE Library SET name = ? WHERE id = ?", name, id);
                    refreshLibraries();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        panel.add(new JScrollPane(libraryTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createDepartmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JButton addButton = new JButton("Add Department");
        JButton deleteButton = new JButton("Delete Department");
        JButton updateButton = new JButton("Update Department");

        departmentTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Genre", "Library ID"}, 0));
        refreshDepartments();

        addButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Enter Department ID:");
            String genre = JOptionPane.showInputDialog("Enter Department Genre:");
            String libraryIdStr = JOptionPane.showInputDialog("Enter Library ID:");

            if (idStr != null && genre != null && libraryIdStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    int libraryId = Integer.parseInt(libraryIdStr);
                    executeUpdate("INSERT INTO Department (id, genre, library_id) VALUES (?, ?, ?)", id, genre, libraryId);
                    refreshDepartments();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number format.");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int row = departmentTable.getSelectedRow();
            if (row != -1) {
                int id = (int) departmentTable.getValueAt(row, 0);
                executeUpdate("DELETE FROM Department WHERE id = ?", id);
                refreshDepartments();
            }
        });

        updateButton.addActionListener(e -> {
            int row = departmentTable.getSelectedRow();
            if (row != -1) {
                int id = (int) departmentTable.getValueAt(row, 0);
                String genre = JOptionPane.showInputDialog("Enter new Department Genre:");
                if (genre != null && !genre.isEmpty()) {
                    executeUpdate("UPDATE Department SET genre = ? WHERE id = ?", genre, id);
                    refreshDepartments();
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        panel.add(new JScrollPane(departmentTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createEditionPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JButton addButton = new JButton("Add Edition");
        JButton deleteButton = new JButton("Delete Edition");
        JButton updateButton = new JButton("Update Edition");

        editionTable = new JTable(new DefaultTableModel(new Object[]{"ID", "Name", "Author", "Year", "Department ID"}, 0));
        refreshEditions();

        addButton.addActionListener(e -> {
            String idStr = JOptionPane.showInputDialog("Enter Edition ID:");
            String name = JOptionPane.showInputDialog("Enter Edition Name:");
            String author = JOptionPane.showInputDialog("Enter Author Name:");
            String yearStr = JOptionPane.showInputDialog("Enter Year:");
            String departmentIdStr = JOptionPane.showInputDialog("Enter Department ID:");

            if (idStr != null && name != null && author != null && yearStr != null && departmentIdStr != null) {
                try {
                    int id = Integer.parseInt(idStr);
                    int year = Integer.parseInt(yearStr);
                    int departmentId = Integer.parseInt(departmentIdStr);
                    executeUpdate("INSERT INTO Edition (id, name, author, year, department_id) VALUES (?, ?, ?, ?, ?)",
                            id, name, author, year, departmentId);
                    refreshEditions();
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "Invalid number format.");
                }
            }
        });

        deleteButton.addActionListener(e -> {
            int row = editionTable.getSelectedRow();
            if (row != -1) {
                int id = (int) editionTable.getValueAt(row, 0);
                executeUpdate("DELETE FROM Edition WHERE id = ?", id);
                refreshEditions();
            }
        });

        updateButton.addActionListener(e -> {
            int row = editionTable.getSelectedRow();
            if (row != -1) {
                int id = (int) editionTable.getValueAt(row, 0);
                String name = JOptionPane.showInputDialog("Enter new Edition Name:");
                String author = JOptionPane.showInputDialog("Enter new Author Name:");
                String yearStr = JOptionPane.showInputDialog("Enter new Year:");

                if (name != null && author != null && yearStr != null) {
                    try {
                        int year = Integer.parseInt(yearStr);
                        executeUpdate("UPDATE Edition SET name = ?, author = ?, year = ? WHERE id = ?", name, author, year, id);
                        refreshEditions();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Invalid number format.");
                    }
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(updateButton);

        panel.add(new JScrollPane(editionTable), BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.SOUTH);

        return panel;
    }

    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout());

        JLabel searchLabel = new JLabel("Search Edition by Year:");
        JTextField searchField = new JTextField(15);
        JButton searchButton = new JButton("Search");

        searchButton.addActionListener(e -> {
            String searchQuery = searchField.getText().trim();
            if (!searchQuery.isEmpty()) {
                searchEditionsByYear(searchQuery);
            } else {
                JOptionPane.showMessageDialog(this, "Enter a year to search.");
            }
        });

        panel.add(searchLabel);
        panel.add(searchField);
        panel.add(searchButton);

        return panel;
    }

    private void refreshLibraries() {
        refreshTable("SELECT * FROM Library", libraryTable);
    }

    private void refreshDepartments() {
        refreshTable("SELECT * FROM Department", departmentTable);
    }

    private void refreshEditions() {
        refreshTable("SELECT * FROM Edition", editionTable);
    }

    private void refreshTable(String query, JTable table) {
        try (Statement stmt = connection.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            DefaultTableModel model = (DefaultTableModel) table.getModel();
            model.setRowCount(0);

            int columnCount = rs.getMetaData().getColumnCount();
            while (rs.next()) {
                Object[] rowData = new Object[columnCount];
                for (int i = 0; i < columnCount; i++) {
                    rowData[i] = rs.getObject(i + 1);
                }
                model.addRow(rowData);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error refreshing table: " + e.getMessage());
        }
    }

    private void executeUpdate(String sql, Object... params) {
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error executing query: " + e.getMessage());
        }
    }

    private void searchEditionsByYear(String year) {
        String sql = "SELECT * FROM Edition WHERE year = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, year);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                String editionName = rs.getString("name");
                String author = rs.getString("author");
                String departmentId = rs.getString("department_id");

                JOptionPane.showMessageDialog(this, "Edition Found:\nName: " + editionName + "\nAuthor: " + author + "\nDepartment ID: " + departmentId);
            } else {
                JOptionPane.showMessageDialog(this, "Edition not found.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error searching edition: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}
