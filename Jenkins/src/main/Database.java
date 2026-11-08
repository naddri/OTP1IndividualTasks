import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public final class Database {
    private Database() {
    }

    public static Connection open() throws SQLException, IOException {
        Files.createDirectories(Path.of("data"));
        Connection connection = DriverManager.getConnection("jdbc:h2:file:./data/temperature;MODE=MySQL", "sa", "");
        initialize(connection);
        return connection;
    }

    static void initialize(Connection connection) throws IOException, SQLException {
        try (InputStream input = Database.class.getResourceAsStream("/init.sql")) {
            if (input == null) {
                throw new IOException("Could not find init.sql");
            }
            String[] statements = new String(input.readAllBytes(), StandardCharsets.UTF_8).split(";");
            try (Statement statement = connection.createStatement()) {
                for (String sql : statements) {
                    if (!sql.isBlank()) {
                        statement.execute(sql);
                    }
                }
            }
        }
    }
}
