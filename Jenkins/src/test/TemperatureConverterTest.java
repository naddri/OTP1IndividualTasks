import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TemperatureConverterTest {

    private final TemperatureConverter converter = new TemperatureConverter();

    @Test
    void convertsFahrenheitToCelsiusForSeveralTemperatures() {
        assertEquals(0, converter.fahrenheitToCelsius(32), 0.001);
        assertEquals(100, converter.fahrenheitToCelsius(212), 0.001);
        assertEquals(-40, converter.fahrenheitToCelsius(-40), 0.001);
        assertEquals(37, converter.fahrenheitToCelsius(98.6), 0.001);
    }

    @Test
    void convertsCelsiusToFahrenheitForSeveralTemperatures() {
        assertEquals(32, converter.celsiusToFahrenheit(0), 0.001);
        assertEquals(212, converter.celsiusToFahrenheit(100), 0.001);
        assertEquals(-40, converter.celsiusToFahrenheit(-40), 0.001);
        assertEquals(98.6, converter.celsiusToFahrenheit(37), 0.001);
    }

    @Test
    void identifiesExtremeTemperaturesAndExcludesBoundaries() {
        assertTrue(converter.isExtremeTemperature(-41));
        assertTrue(converter.isExtremeTemperature(51));
        assertFalse(converter.isExtremeTemperature(-40));
        assertFalse(converter.isExtremeTemperature(50));
        assertFalse(converter.isExtremeTemperature(20));
    }

    @Test
    void mainPrintsConversionResults() {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        try (PrintStream capturedOut = new PrintStream(output, true, StandardCharsets.UTF_8)) {
            System.setOut(capturedOut);
            TemperatureConverter.main(new String[0]);
        } finally {
            System.setOut(originalOut);
        }

        assertEquals(3, output.toString(StandardCharsets.UTF_8).lines().count());
        assertTrue(output.toString(StandardCharsets.UTF_8).contains("Extreme temperature? false"));
    }
}
