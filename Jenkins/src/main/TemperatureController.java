import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class TemperatureController implements Initializable {

    @FXML private ComboBox<String> languageBox;
    @FXML private ComboBox<Unit> fromUnitBox;
    @FXML private ComboBox<Unit> toUnitBox;
    @FXML private TextField inputField;
    @FXML private Label resultLabel;
    @FXML private Label unitDescriptionLabel;
    @FXML private ListView<String> historyList;

    private final TemperatureConverter converter = new TemperatureConverter();
    private ResourceBundle bundle;
    private UnitDao unitDao;
    private ConversionDao conversionDao;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bundle = rb;
        languageBox.setItems(FXCollections.observableArrayList(TemperatureConverterApp.LANGUAGE_NAMES));
        languageBox.getSelectionModel().select(TemperatureConverterApp.currentLanguage);
        unitDao = new UnitDao(TemperatureConverterApp.connection);
        conversionDao = new ConversionDao(TemperatureConverterApp.connection);
        loadUnitsAndHistory();
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
            Unit from = fromUnitBox.getValue();
            Unit to = toUnitBox.getValue();
            if (from == null || to == null) {
                resultLabel.setText(bundle.getString("units.invalid"));
                return;
            }
            double result = converter.convertTemperature(input, from.key(), to.key());
            String text = String.format("%.2f %s", result, to.name());
            double celsius = converter.convertTemperature(input, from.key(), "celsius");
            if (converter.isExtremeTemperature(celsius)) {
                text += " (" + bundle.getString("extreme") + ")";
            }
            conversionDao.save(input, from, to, result, languageCode());
            resultLabel.setText(bundle.getString("result.label") + ": " + text);
            loadHistory();
        } catch (NumberFormatException e) {
            resultLabel.setText(bundle.getString("input.invalid"));
        } catch (SQLException e) {
            throw new RuntimeException("Could not save or load conversion history", e);
        }
    }

    private void loadUnitsAndHistory() {
        try {
            List<Unit> units = unitDao.findByCategory("temperature", languageCode());
            fromUnitBox.setItems(FXCollections.observableArrayList(units));
            toUnitBox.setItems(FXCollections.observableArrayList(units));
            fromUnitBox.valueProperty().addListener((observable, oldValue, newValue) -> updateUnitDescription());
            toUnitBox.valueProperty().addListener((observable, oldValue, newValue) -> updateUnitDescription());
            fromUnitBox.getSelectionModel().selectFirst();
            toUnitBox.getSelectionModel().selectFirst();
            updateUnitDescription();
            loadHistory();
        } catch (SQLException e) {
            throw new RuntimeException("Could not load localized units", e);
        }
    }

    private void loadHistory() throws SQLException {
        historyList.setItems(FXCollections.observableArrayList(
                conversionDao.findLatestFive(languageCode())));
    }

    private String languageCode() {
        return switch (TemperatureConverterApp.currentLanguage) {
            case 1 -> "fi";
            case 2 -> "sv";
            case 3 -> "ja";
            case 4 -> "ar";
            default -> "en";
        };
    }

    private void updateUnitDescription() {
        Unit from = fromUnitBox.getValue();
        Unit to = toUnitBox.getValue();
        if (from != null && to != null) {
            unitDescriptionLabel.setText(from.description() + " → " + to.description());
        }
    }
}
