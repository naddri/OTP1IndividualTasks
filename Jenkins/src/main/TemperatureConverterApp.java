import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

// JavaFX window wrapping TemperatureConverter so it can be seen via Xming
public class TemperatureConverterApp extends Application {

    private static final String FAHRENHEIT_TO_CELSIUS = "Fahrenheit \u2192 Celsius";
    private static final String CELSIUS_TO_FAHRENHEIT = "Celsius \u2192 Fahrenheit";

    private final TemperatureConverter converter = new TemperatureConverter();

    @Override
    public void start(Stage stage) {
        TextField inputField = new TextField();
        inputField.setPromptText("Enter temperature");

        ComboBox<String> directionBox = new ComboBox<>();
        directionBox.getItems().addAll(FAHRENHEIT_TO_CELSIUS, CELSIUS_TO_FAHRENHEIT);
        directionBox.setValue(FAHRENHEIT_TO_CELSIUS);

        Label resultLabel = new Label();

        Button convertButton = new Button("Convert");
        convertButton.setOnAction(e -> {
            try {
                double input = Double.parseDouble(inputField.getText());
                if (directionBox.getValue().equals(FAHRENHEIT_TO_CELSIUS)) {
                    double celsius = converter.fahrenheitToCelsius(input);
                    String extremeNote = converter.isExtremeTemperature(celsius) ? " (extreme!)" : "";
                    resultLabel.setText(String.format("%.2fC%s", celsius, extremeNote));
                } else {
                    double fahrenheit = converter.celsiusToFahrenheit(input);
                    String extremeNote = converter.isExtremeTemperature(input) ? " (extreme!)" : "";
                    resultLabel.setText(String.format("%.2fF%s", fahrenheit, extremeNote));
                }
            } catch (NumberFormatException ex) {
                resultLabel.setText("Enter a valid number");
            }
        });

        VBox root = new VBox(10, directionBox, inputField, convertButton, resultLabel);
        root.setPadding(new Insets(15));

        stage.setScene(new Scene(root, 320, 180));
        stage.setTitle("Temperature Converter");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
