import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ConversionDao {
    private final Connection connection;

    public ConversionDao(Connection connection) {
        this.connection = connection;
    }

    public void save(double value, Unit from, Unit to, double result, String language) throws SQLException {
        String sql = """
                INSERT INTO conversion_history
                    (input_value, from_unit_id, to_unit_id, result_value, language)
                VALUES (?, ?, ?, ?, ?)
                """;
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setDouble(1, value);
            statement.setInt(2, from.id());
            statement.setInt(3, to.id());
            statement.setDouble(4, result);
            statement.setString(5, language);
            statement.executeUpdate();
        }
    }

    public List<String> findLatestFive(String language) throws SQLException {
        String sql = """
                SELECT h.input_value, h.result_value, h.language, h.created_at,
                       COALESCE(from_selected.name, from_english.name) AS from_name,
                       COALESCE(to_selected.name, to_english.name) AS to_name
                FROM conversion_history h
                JOIN unit from_unit ON from_unit.id = h.from_unit_id
                JOIN unit to_unit ON to_unit.id = h.to_unit_id
                LEFT JOIN unit_translation from_selected
                    ON from_selected.unit_id = from_unit.id AND from_selected.lang = ?
                LEFT JOIN unit_translation from_english
                    ON from_english.unit_id = from_unit.id AND from_english.lang = 'en'
                LEFT JOIN unit_translation to_selected
                    ON to_selected.unit_id = to_unit.id AND to_selected.lang = ?
                LEFT JOIN unit_translation to_english
                    ON to_english.unit_id = to_unit.id AND to_english.lang = 'en'
                ORDER BY h.created_at DESC, h.id DESC
                LIMIT 5
                """;
        List<String> entries = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, language);
            statement.setString(2, language);
            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    entries.add(String.format("%.2f %s → %.2f %s (%s)",
                            results.getDouble("input_value"), results.getString("from_name"),
                            results.getDouble("result_value"), results.getString("to_name"),
                            results.getString("created_at")));
                }
            }
        }
        return entries;
    }
}
