import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class GUI extends JFrame {
    private JTextField nameField, ageField, majorField;
    private JTextArea outputArea;
    private JButton addButton, updateButton, deleteButton, viewButton;

    private static final String URL = "jdbc:mysql://localhost:3306/university";  // URL базы данных
    private static final String USER = "root";  // Ваш MySQL username
    private static final String PASSWORD = "password"; // Ваш MySQL пароль

    public GUI() {
        // Настройка окна JFrame
        setTitle("Приложение для работы с базой данных студентов");
        setSize(400, 400);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Поля ввода
        nameField = new JTextField(15);
        ageField = new JTextField(15);
        majorField = new JTextField(15);

        // Кнопки
        addButton = new JButton("Добавить студента");
        updateButton = new JButton("Обновить студента");
        deleteButton = new JButton("Удалить студента");
        viewButton = new JButton("Просмотреть студентов");

        // Область вывода для отображения данных
        outputArea = new JTextArea(10, 30);
        outputArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(outputArea);

        // Добавление компонентов в окно
        add(new JLabel("Имя:"));
        add(nameField);
        add(new JLabel("Возраст:"));
        add(ageField);
        add(new JLabel("Специальность:"));
        add(majorField);

        add(addButton);
        add(updateButton);
        add(deleteButton);
        add(viewButton);

        add(scrollPane);

        // Добавление обработчиков событий для кнопок
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addStudent();
            }
        });

        updateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateStudent();
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteStudent();
            }
        });

        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                viewStudents();
            }
        });

        setVisible(true);
    }

    // Метод для добавления студента в базу данных
    private void addStudent() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());
        String major = majorField.getText();

        String query = "INSERT INTO students (name, age, major) VALUES (?, ?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);
            ps.setInt(2, age);
            ps.setString(3, major);

            int rowsInserted = ps.executeUpdate();
            if (rowsInserted > 0) {
                outputArea.append("Студент успешно добавлен!\n");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Метод для обновления информации о студенте
    private void updateStudent() {
        String name = nameField.getText();
        int age = Integer.parseInt(ageField.getText());

        String query = "UPDATE students SET age = ? WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setInt(1, age);
            ps.setString(2, name);

            int rowsUpdated = ps.executeUpdate();
            if (rowsUpdated > 0) {
                outputArea.append("Информация о студенте обновлена!\n");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Метод для удаления студента
    private void deleteStudent() {
        String name = nameField.getText();

        String query = "DELETE FROM students WHERE name = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement ps = conn.prepareStatement(query)) {

            ps.setString(1, name);

            int rowsDeleted = ps.executeUpdate();
            if (rowsDeleted > 0) {
                outputArea.append("Студент успешно удалён!\n");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Метод для просмотра всех студентов
    private void viewStudents() {
        String query = "SELECT * FROM students";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement statement = conn.createStatement();
             ResultSet resultSet = statement.executeQuery(query)) {

            outputArea.setText("Список студентов:\n");
            while (resultSet.next()) {
                outputArea.append("ID: " + resultSet.getInt("id") +
                        ", Имя: " + resultSet.getString("name") +
                        ", Возраст: " + resultSet.getInt("age") +
                        ", Специальность: " + resultSet.getString("major") + "\n");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    // Главный метод для запуска приложения
    public static void main(String[] args) {
        new GUI();
    }
}

