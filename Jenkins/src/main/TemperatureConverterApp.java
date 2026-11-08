import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.NodeOrientation;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TemperatureConverterApp extends Application {

    public static final List<String> LANGUAGE_NAMES =
            List.of("English", "Suomi", "Svenska", "日本語", "العربية");
    private static final List<Locale> LOCALES = List.of(
            Locale.US, Locale.forLanguageTag("fi-FI"), Locale.forLanguageTag("sv-SE"),
            Locale.JAPAN, Locale.forLanguageTag("ar"));

    public static int currentLanguage = 0;
    private static Stage stage;
    static Connection connection;

    @Override
    public void start(Stage primaryStage) throws Exception {
        stage = primaryStage;
        connection = Database.open();
        showScene();
        stage.show();
    }

    @Override
    public void stop() throws SQLException {
        if (connection != null) {
            connection.close();
        }
    }

    // loads the FXML again with the bundle of the selected language
    public static void showScene() {
        try {
            Locale locale = LOCALES.get(currentLanguage);
            ResourceBundle bundle = ResourceBundle.getBundle("MessagesBundle", locale);
            Parent root = FXMLLoader.load(
                    TemperatureConverterApp.class.getResource("/main.fxml"), bundle);
            if (locale.getLanguage().equals("ar")) {
                root.setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
            }
            stage.setScene(new Scene(root));
            stage.setTitle(bundle.getString("application.title"));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
