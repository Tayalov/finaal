import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LibraryDAO {
    public void addLibrary(Library library) {
        String query = "INSERT INTO Library (name) VALUES (?)";

        try (Connection conn = DatabaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, library.getName());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

