import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Library {
    private int libraryId;
    private String name;
    private List<Department> departments;

    public Library(String name) {
        this.name = name;
        this.departments = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addDepartment(Department department) {
        departments.add(department);
    }

    public void removeDepartment(Department department) {
        departments.remove(department);
    }

    public List<Department> getDepartments() {
        return departments;
    }
}
public List<Edition> searchEditionsByYear(int year) {
    List<Edition> editions = new ArrayList<>();
    String query = "SELECT e.name, e.author, e.publication_year, d.name AS department_name " +
            "FROM Edition e " +
            "JOIN Department d ON e.department_id = d.department_id " +
            "WHERE e.publication_year = ?";

    try (Connection conn = DatabaseConnection.getConnection();
         PreparedStatement stmt = conn.prepareStatement(query)) {
        stmt.setInt(1, year);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            String name = rs.getString("name");
            String author = rs.getString("author");
            int publicationYear = rs.getInt("publication_year");
            String departmentName = rs.getString("department_name");

            Edition edition = new Edition(name, author, publicationYear, null);
            editions.add(edition);

            System.out.println("Found edition: " + name + " by " + author + " in " + departmentName);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return editions;
}
}

