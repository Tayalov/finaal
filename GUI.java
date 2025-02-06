import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LibraryGUI extends JFrame {
    private Library library;
    private JList<String> departmentList;
    private JList<String> editionList;

    public LibraryGUI() {
        setTitle("Library Management System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLayout(new BorderLayout());

        library = new Library("Central Library");

        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.add("Library", createLibraryPanel());
        tabbedPane.add("Departments", createDepartmentPanel());
        tabbedPane.add("Editions", createEditionPanel());

        add(tabbedPane, BorderLayout.CENTER);
    }

    private JPanel createLibraryPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JButton addLibraryButton = new JButton("Add Library");
        JTextField libraryNameField = new JTextField(20);

        addLibraryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = libraryNameField.getText();
                if (!name.isEmpty()) {
                    library.setName(name);
                    JOptionPane.showMessageDialog(LibraryGUI.this, "Library name set to: " + name);
                } else {
                    JOptionPane.showMessageDialog(LibraryGUI.this, "Please enter a library name.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel("Library Name:"));
        buttonPanel.add(libraryNameField);
        buttonPanel.add(addLibraryButton);

        panel.add(buttonPanel, BorderLayout.NORTH);
        return panel;
    }

    private JPanel createDepartmentPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextField departmentNameField = new JTextField(20);
        JButton addDepartmentButton = new JButton("Add Department");

        addDepartmentButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String genre = departmentNameField.getText();
                if (!genre.isEmpty()) {
                    Department department = new Department(genre);
                    library.addDepartment(department);
                    JOptionPane.showMessageDialog(LibraryGUI.this, "Department '" + genre + "' added.");
                    updateDepartmentList();
                } else {
                    JOptionPane.showMessageDialog(LibraryGUI.this, "Please enter a genre for the department.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel("Department Genre:"));
        buttonPanel.add(departmentNameField);
        buttonPanel.add(addDepartmentButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        departmentList = new JList<>();
        panel.add(new JScrollPane(departmentList), BorderLayout.CENTER);

        return panel;
    }

    private JPanel createEditionPanel() {
        JPanel panel = new JPanel(new BorderLayout());

        JTextField editionNameField = new JTextField(20);
        JTextField authorField = new JTextField(20);
        JTextField yearField = new JTextField(20);
        JButton addEditionButton = new JButton("Add Edition");

        addEditionButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String editionName = editionNameField.getText();
                String author = authorField.getText();
                String yearStr = yearField.getText();

                if (!editionName.isEmpty() && !author.isEmpty() && !yearStr.isEmpty()) {
                    try {
                        int year = Integer.parseInt(yearStr);
                        Edition edition = new Edition(editionName, author, year);
                        Department selectedDepartment = library.getDepartments().get(departmentList.getSelectedIndex());
                        selectedDepartment.addEdition(edition);
                        JOptionPane.showMessageDialog(LibraryGUI.this, "Edition added.");
                        updateEditionList();
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(LibraryGUI.this, "Invalid year format.");
                    }
                } else {
                    JOptionPane.showMessageDialog(LibraryGUI.this, "Please fill all fields.");
                }
            }
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(new JLabel("Edition Name:"));
        buttonPanel.add(editionNameField);
        buttonPanel.add(new JLabel("Author:"));
        buttonPanel.add(authorField);
        buttonPanel.add(new JLabel("Year:"));
        buttonPanel.add(yearField);
        buttonPanel.add(addEditionButton);

        panel.add(buttonPanel, BorderLayout.NORTH);

        editionList = new JList<>();
        panel.add(new JScrollPane(editionList), BorderLayout.CENTER);

        return panel;
    }

    private void updateDepartmentList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        for (Department dept : library.getDepartments()) {
            model.addElement(dept.getGenre());
        }
        departmentList.setModel(model);
    }

    private void updateEditionList() {
        DefaultListModel<String> model = new DefaultListModel<>();
        Department selectedDepartment = library.getDepartments().get(departmentList.getSelectedIndex());
        for (Edition edition : selectedDepartment.getEditions()) {
            model.addElement(edition.getName() + " by " + edition.getAuthor() + " (" + edition.getYearOfPublication() + ")");
        }
        editionList.setModel(model);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            LibraryGUI gui = new LibraryGUI();
            gui.setVisible(true);
        });
    }
}
