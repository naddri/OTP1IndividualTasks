import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

class TemperatureConverterTest {

    TemperatureConverter converter = new TemperatureConverter();

    @Test
    @DisplayName("fahrenheitToCelsius() - Freezing point: 32F should be 0C")
    void testFahrenheitToCelsius_FreezingPoint() {
        assertEquals(0, converter.fahrenheitToCelsius(32), 0.01, "32F should convert to 0C");
    }

    @Test
    @DisplayName("fahrenheitToCelsius() - Boiling point: 212F should be 100C")
    void testFahrenheitToCelsius_BoilingPoint() {
        assertEquals(100, converter.fahrenheitToCelsius(212), 0.01, "212F should convert to 100C");
    }

    @Test
    @DisplayName("celsiusToFahrenheit() - Freezing point: 0C should be 32F")
    void testCelsiusToFahrenheit_FreezingPoint() {
        assertEquals(32, converter.celsiusToFahrenheit(0), 0.01, "0C should convert to 32F");
    }

    @Test
    @DisplayName("kelvinToCelsius() - 300K should be 26.85C")
    void testKelvinToCelsius_300K() {
        assertEquals(26.85, converter.kelvinToCelsius(300), 0.01, "300K should convert to 26.85C");
    }

    @Test
    @DisplayName("kelvinToCelsius() - 273.15K should be 0C")
    void testKelvinToCelsius_ZeroCelsius() {
        assertEquals(0, converter.kelvinToCelsius(273.15), 0.01, "273.15K should convert to 0C");
    }

    @Test
    @DisplayName("isExtremeTemperature() - Above 50C should be extreme")
    void testIsExtremeTemperature_AboveUpperBound() {
        assertTrue(converter.isExtremeTemperature(51), "51C should be considered extreme");
    }

    @Test
    @DisplayName("isExtremeTemperature() - Normal room temperature should NOT be extreme")
    void testIsExtremeTemperature_NormalTemperature() {
        assertFalse(converter.isExtremeTemperature(20), "20C is a normal temperature and should not be extreme");
    }
}
