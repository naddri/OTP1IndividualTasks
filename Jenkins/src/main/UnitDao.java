import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UnitDao {
    private final Connection connection;

    public UnitDao(Connection connection) {
        this.connection = connection;
    }

    public List<Unit> findByCategory(String category, String language) throws SQLException {
        String sql = """
                SELECT u.id, u.unit_key, u.category,
                       COALESCE(selected.name, english.name) AS name,
                       COALESCE(selected.description, english.description) AS description
                FROM unit u
                LEFT JOIN unit_translation selected
                    ON selected.unit_id = u.id AND selected.lang = ?
                LEFT JOIN unit_translation english
                    ON english.unit_id = u.id AND english.lang = 'en'
                WHERE u.category = ?
                ORDER BY u.id
                """;
        List<Unit> units = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, language);
            statement.setString(2, category);
            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    units.add(new Unit(results.getInt("id"), results.getString("unit_key"),
                            results.getString("category"), results.getString("name"),
                            results.getString("description")));
                }
            }
        }
        return units;
    }
}
