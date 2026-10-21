import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

public class TemperatureConverterApp extends Application {

    private static final String FAHRENHEIT_TO_CELSIUS = "fahrenheitToCelsius";
    private static final String CELSIUS_TO_FAHRENHEIT = "celsiusToFahrenheit";
    private static final List<LanguageOption> LANGUAGES = List.of(
            new LanguageOption("English", Locale.US),
            new LanguageOption("Finnish", Locale.forLanguageTag("fi-FI")),
            new LanguageOption("Swedish", Locale.forLanguageTag("sv-SE")),
            new LanguageOption("Japanese", Locale.JAPAN));

    private final TemperatureConverter converter = new TemperatureConverter();

    @Override
    public void start(Stage stage) {
        String language = chooseLanguage();
        Locale locale = LANGUAGES.stream()
                .filter(option -> option.name().equals(language))
                .findFirst()
                .orElse(LANGUAGES.get(0))
                .locale();
        ResourceBundle messages = ResourceBundle.getBundle("MessagesBundle", locale);

        TextField inputField = new TextField();
        inputField.setPromptText(messages.getString("input.prompt"));

        ComboBox<String> directionBox = new ComboBox<>();
        directionBox.getItems().addAll(
                messages.getString(FAHRENHEIT_TO_CELSIUS),
                messages.getString(CELSIUS_TO_FAHRENHEIT));
        directionBox.setValue(messages.getString(FAHRENHEIT_TO_CELSIUS));

        Label resultLabel = new Label();

        Button convertButton = new Button(messages.getString("convert.button"));
        convertButton.setOnAction(e -> {
            try {
                double input = Double.parseDouble(inputField.getText());
                if (directionBox.getValue().equals(messages.getString(FAHRENHEIT_TO_CELSIUS))) {
                    double celsius = converter.fahrenheitToCelsius(input);
                    String extremeNote = converter.isExtremeTemperature(celsius)
                            ? " (" + messages.getString("extreme") + ")"
                            : "";
                    resultLabel.setText(String.format("%.2fC%s", celsius, extremeNote));
                } else {
                    double fahrenheit = converter.celsiusToFahrenheit(input);
                    String extremeNote = converter.isExtremeTemperature(input)
                            ? " (" + messages.getString("extreme") + ")"
                            : "";
                    resultLabel.setText(String.format("%.2fF%s", fahrenheit, extremeNote));
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText(messages.getString("input.invalid"));
            }
        });

        VBox root = new VBox(10, directionBox, inputField, convertButton, resultLabel);
        root.setPadding(new Insets(15));

        stage.setScene(new Scene(root, 320, 180));
        stage.setTitle(messages.getString("application.title"));
        stage.show();
    }

    private String chooseLanguage() {
        ChoiceDialog<String> languageDialog = new ChoiceDialog<>(
                LANGUAGES.get(0).name(),
                LANGUAGES.stream().map(LanguageOption::name).toList());
        languageDialog.setTitle("Language");
        languageDialog.setHeaderText("Select language");
        languageDialog.setContentText("Language:");
        return languageDialog.showAndWait().orElse(LANGUAGES.get(0).name());
    }

    public static void main(String[] args) {
        launch(args);
    }

    private record LanguageOption(String name, Locale locale) {
    }
}
