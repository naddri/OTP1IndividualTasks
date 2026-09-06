import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.*;

// test for the TemperatureConverter
class TemperatureConverterTest {

    TemperatureConverter converter = new TemperatureConverter();

    // fahrenheitToCelsius test

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
    @DisplayName("fahrenheitToCelsius() - Negative value: -40F should be -40C")
    void testFahrenheitToCelsius_NegativeValue() {
        assertEquals(-40, converter.fahrenheitToCelsius(-40), 0.01, "-40F should convert to -40C");
    }

    // celsiusToFahrenheit test

    @Test
    @DisplayName("celsiusToFahrenheit() - Freezing point: 0C should be 32F")
    void testCelsiusToFahrenheit_FreezingPoint() {
        assertEquals(32, converter.celsiusToFahrenheit(0), 0.01, "0C should convert to 32F");
    }

    @Test
    @DisplayName("celsiusToFahrenheit() - Boiling point: 100C should be 212F")
    void testCelsiusToFahrenheit_BoilingPoint() {
        assertEquals(212, converter.celsiusToFahrenheit(100), 0.01, "100C should convert to 212F");
    }

    @Test
    @DisplayName("celsiusToFahrenheit() - Negative value: -40C should be -40F")
    void testCelsiusToFahrenheit_NegativeValue() {
        assertEquals(-40, converter.celsiusToFahrenheit(-40), 0.01, "-40C should convert to -40F");
    }

    // isExtremeTemperature tests

    @Test
    @DisplayName("isExtremeTemperature() - Below -40C should be extreme")
    void testIsExtremeTemperature_BelowLowerBound() {
        assertTrue(converter.isExtremeTemperature(-41), "-41C should be considered extreme");
    }

    @Test
    @DisplayName("isExtremeTemperature() - Above 50C should be extreme")
    void testIsExtremeTemperature_AboveUpperBound() {
        assertTrue(converter.isExtremeTemperature(51), "51C should be considered extreme");
    }

    @Test
    @DisplayName("isExtremeTemperature() - Exactly -40C should NOT be extreme (boundary)")
    void testIsExtremeTemperature_LowerBoundary() {
        assertFalse(converter.isExtremeTemperature(-40), "-40C is the boundary and should not be extreme");
    }

    @Test
    @DisplayName("isExtremeTemperature() - Exactly 50C should NOT be extreme (boundary)")
    void testIsExtremeTemperature_UpperBoundary() {
        assertFalse(converter.isExtremeTemperature(50), "50C is the boundary and should not be extreme");
    }

    @Test
    @DisplayName("isExtremeTemperature() - Normal room temperature should NOT be extreme")
    void testIsExtremeTemperature_NormalTemperature() {
        assertFalse(converter.isExtremeTemperature(20), "20C is a normal temperature and should not be extreme");
    }
}
