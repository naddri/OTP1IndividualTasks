import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.util.ResourceBundle;

public class TemperatureController implements Initializable {

    @FXML private ComboBox<String> languageBox;
    @FXML private ComboBox<String> directionBox;
    @FXML private TextField inputField;
    @FXML private Label resultLabel;

    private final TemperatureConverter converter = new TemperatureConverter();
    private ResourceBundle bundle;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        languageBox.setItems(FXCollections.observableArrayList(TemperatureConverterApp.LANGUAGE_NAMES));
        languageBox.getSelectionModel().select(TemperatureConverterApp.currentLanguage);

        directionBox.getItems().addAll(rb.getString("fahrenheitToCelsius"), rb.getString("celsiusToFahrenheit"));
        directionBox.getSelectionModel().select(0);

    }

    @FXML
    private void onLanguage() {
        int index = languageBox.getSelectionModel().getSelectedIndex();
        if (index != TemperatureConverterApp.currentLanguage) {
            TemperatureConverterApp.currentLanguage = index;
            TemperatureConverterApp.showScene();
        }
    }

    @FXML
    private void onConvert() {
        try {
            double input = Double.parseDouble(inputField.getText());
            boolean fToC = directionBox.getSelectionModel().getSelectedIndex() == 0;
            double celsius = fToC ? converter.fahrenheitToCelsius(input) : input;
            String text = fToC
                    ? String.format("%.2f C", celsius)
                    : String.format("%.2f F", converter.celsiusToFahrenheit(input));
            if (converter.isExtremeTemperature(celsius)) {
                text += " (" + bundle.getString("extreme") + ")";
            }
            resultLabel.setText(text);
        } catch (NumberFormatException e) {
            resultLabel.setText(bundle.getString("input.invalid"));
        }
    }

}
